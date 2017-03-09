package ee.hitsa.ois.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;

import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public abstract class EntityUtil {

    private static final Set<String> IGNORED_ENTITY_PROPERTIES = new HashSet<>(Arrays.asList("version", "inserted", "insertedBy", "changed", "changedBy", "id", "class"));

    /**
     * Copy properties from command to entity.
     * Only properties with public getter/setter are copied. Empty strings are copied as nulls.
     * See IGNORED_ENTITY_PROPERTIES for list of properties which are not copied.
     * Classifiers are not set in this variant.
     *
     * @param command
     * @param entity
     * @param ignoredProperties optional additional ignored properties
     * @return entity with properties copied
     * @throws FatalBeanException if the copying failed
     */
    public static <E> E bindToEntity(Object command, E entity, String...ignoredProperties) {
        return bindToEntity(command, entity, null, ignoredProperties);
    }

    /**
     * Copy properties from command to entity.
     * Only properties with public getter/setter are copied. Empty strings are copied as nulls.
     * See IGNORED_ENTITY_PROPERTIES for list of properties which are not copied.
     * If command object has @ClassifierRestriction annotations, then corresponding
     * Classifier fields in entity are set from string values in command object
     *
     * @param command
     * @param entity
     * @param repository repository for loading classifiers
     * @param ignoredProperties optional additional ignored properties
     * @return entity with properties copied
     * @throws FatalBeanException if the copying failed
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <E> E bindToEntity(Object command, E entity, ClassifierRepository repository, String...ignoredProperties) {
        List<String> ignored = Arrays.asList(ignoredProperties);
        Map<String, MainClassCode[]> classifierProperties = new HashMap<>();
        for(PropertyDescriptor spd : BeanUtils.getPropertyDescriptors(command.getClass())) {
            Method readMethod = spd.getReadMethod();
            String propertyName = spd.getName();
            if(readMethod != null && !IGNORED_ENTITY_PROPERTIES.contains(propertyName) &&!ignored.contains(propertyName) && Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                PropertyDescriptor tpd = BeanUtils.getPropertyDescriptor(entity.getClass(), propertyName);
                if(tpd == null) {
                    continue;
                }
                Method writeMethod = tpd.getWriteMethod();
                if (writeMethod != null && Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                    try {
                        if(ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                            Object value = readMethod.invoke(command);
                            // FIXME check for CharSequence?
                            if (value instanceof String && ((String)value).isEmpty()) {
                                value = null;
                            } else if(Boolean.class.equals(tpd.getPropertyType()) && value == null) {
                                // convert null Booleans to false
                                value = Boolean.FALSE;
                            }

                            //for collection add elements instead of setting entire collection
                            //(this is here because error occurred (orphans are not referenced...) when persistent set was replaced with null)
                            if(Collection.class.isAssignableFrom(tpd.getPropertyType())) {
                                Collection entityValue = (Collection) tpd.getReadMethod().invoke(entity);
                                if(entityValue != null) {
                                    //if objects inside persistent set are changed then we should add updated ones
                                    entityValue.clear();
                                    if(value instanceof Collection) {
                                        entityValue.addAll((Collection) value);
                                    }
                                    continue;
                                }
                            }
                            writeMethod.invoke(entity, value);
                        } else if(repository != null && Classifier.class.isAssignableFrom(writeMethod.getParameterTypes()[0]) && String.class.isAssignableFrom(readMethod.getReturnType())) {
                            // check for @ClassifierRestriction on field in command
                            Field propertyField = spd.getReadMethod().getDeclaringClass().getDeclaredField(propertyName);
                            ClassifierRestriction restriction = propertyField.getAnnotation(ClassifierRestriction.class);
                            if(restriction != null) {
                                classifierProperties.put(propertyName, restriction.value());
                            }
                        }
                    } catch (Throwable e) {
                        throw new FatalBeanException("Could not copy property '" + propertyName + "' from command to entity", e);
                    }
                }
            }
        }
        if(!classifierProperties.isEmpty() && repository != null) {
            bindClassifiers(command, entity, classifierProperties, repository);
        }

        return entity;
    }

    /**
     * Copy properties from entity to dto.
     * Only properties with public getter/setter are copied.
     *
     * @param entity
     * @param dto
     * @param ignoredProperties
     * @return dto with properties copied
     * @throws FatalBeanException if the copying failed
     */
    public static <DTO> DTO bindToDto(Object entity, DTO dto, String...ignoredProperties) {
        List<String> ignored = Arrays.asList(ignoredProperties);
        // TODO iterate over dto properties as dto is probably subset of entity and has fewer properties
        for(PropertyDescriptor spd : BeanUtils.getPropertyDescriptors(entity.getClass())) {
            Method readMethod = spd.getReadMethod();
            String propertyName = spd.getName();
            if(readMethod != null && !ignored.contains(propertyName) && Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                PropertyDescriptor tpd = BeanUtils.getPropertyDescriptor(dto.getClass(), propertyName);
                if(tpd == null) {
                    continue;
                }
                Method writeMethod = tpd.getWriteMethod();
                if (writeMethod != null && Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                    try {
                        Class<?> sourcePropertyType = readMethod.getReturnType();
                        Class<?> targetPropertyType = writeMethod.getParameterTypes()[0];
                        if(ClassUtils.isAssignable(targetPropertyType, sourcePropertyType)) {
                            Object value = readMethod.invoke(entity);
                            writeMethod.invoke(dto, value);
                        } else {
                            // special handling for Classifier -> String and BaseEntityWithId -> Long and BaseEntityWithId -> AutocompleteResult
                            if(Classifier.class.isAssignableFrom(sourcePropertyType) && String.class.isAssignableFrom(targetPropertyType)) {
                                Object value = readMethod.invoke(entity);
                                writeMethod.invoke(dto, getNullableCode((Classifier)value));
                            }else if(BaseEntityWithId.class.isAssignableFrom(sourcePropertyType) && Long.class.isAssignableFrom(targetPropertyType)) {
                                Object value = readMethod.invoke(entity);
                                writeMethod.invoke(dto, getNullableId((BaseEntityWithId)value));
                            }else if(BaseEntityWithId.class.isAssignableFrom(sourcePropertyType) && AutocompleteResult.class.isAssignableFrom(targetPropertyType)) {
                                Method m = ReflectionUtils.findMethod(AutocompleteResult.class, "of", sourcePropertyType);
                                if(m != null && Modifier.isStatic(m.getModifiers()) && Modifier.isPublic(m.getModifiers())) {
                                    Object value = readMethod.invoke(entity);
                                    if(value != null) {
                                        value = m.invoke(null, value);
                                    }
                                    writeMethod.invoke(dto, value);
                                }
                            }
                        }
                     } catch (Throwable e) {
                        throw new FatalBeanException("Could not copy property '" + propertyName + "' from command to entity", e);
                    }
                }
            }
        }

        return dto;
    }

    public static <SV, ID> void bindClassifierCollection(Collection<SV> storedValues, Function<SV, ID> idExtractor, Collection<ID> newIds, Function<ID, SV> newValueFactory) {
        Set<ID> storedIds = storedValues.stream().map(idExtractor).collect(Collectors.toSet());

        for(ID id : newIds) {
            if(!storedIds.remove(id)) {
                storedValues.add(newValueFactory.apply(id));
            }
        }

        // remove possible letfovers
        storedValues.removeIf(t -> !newIds.contains(idExtractor.apply(t)));
    }

    public static void bindClassifiers(Object command, Object entity, Map<String, MainClassCode[]> properties, ClassifierRepository loader) {
        PropertyAccessor source = PropertyAccessorFactory.forBeanPropertyAccess(command);
        PropertyAccessor destination = PropertyAccessorFactory.forBeanPropertyAccess(entity);
        properties.forEach((p, t) -> {
            String classCode = (String)source.getPropertyValue(p);
            Classifier current = (Classifier)destination.getPropertyValue(p);
            if(!Objects.equals(classCode, current != null ? EntityUtil.getCode(current) : null)) {
                Classifier c;
                if(classCode == null || classCode.isEmpty()) {
                    c = null;
                } else {
                    c = loader.getOne(classCode);
                    if(!Arrays.stream(t).anyMatch(cc -> cc.name().equals(c.getMainClassCode()))) {
                        throw new IllegalArgumentException();
                    }
                }
                destination.setPropertyValue(p, c);
            }
        });
    }

    public static void setEntityFromRepository(Object command, Object entity,
            JpaRepository<?, Long> repository, String...fields) {
        List<String> fieldsList = Arrays.asList(fields);
        for(PropertyDescriptor spd : BeanUtils.getPropertyDescriptors(command.getClass())) {
            Method readMethod = spd.getReadMethod();
            String propertyName = spd.getName();
            if(readMethod != null && fieldsList.contains(propertyName) && Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                PropertyDescriptor tpd = BeanUtils.getPropertyDescriptor(entity.getClass(), propertyName);
                if(tpd == null) {
                    continue;
                }
                Method writeMethod = tpd.getWriteMethod();
                if (writeMethod != null && Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                    try {
                        Long id = null;
                        //usually Long is used in DTO classes to have references to other objects, but
                        //sometimes ee.hitsa.ois.web.dto.AutoCompleteResult is also used.
                        Object wrapper = readMethod.invoke(command);
                        if (wrapper != null) {
                            if (wrapper instanceof Long) {
                                id = (Long) wrapper;
                            } else {
                                id = getIdFromWrapper(wrapper);
                            }
                        }

                        if (id != null) {
                            writeMethod.invoke(entity, repository.getOne(id));
                        }
                    } catch (Throwable e) {
                        throw new FatalBeanException("Could not copy property '" + propertyName + "' from command to entity", e);
                    }
                }
            }
        }
    }

    private static Long getIdFromWrapper(Object wrapper)
            throws IllegalAccessException, InvocationTargetException {
        Long id = null;
        Method getIdMethod = BeanUtils.findMethod(wrapper.getClass(), "getId");
        if(getIdMethod != null && Modifier.isPublic(getIdMethod.getDeclaringClass().getModifiers())
                && getIdMethod.getReturnType() == Long.class) {
            id = (Long) getIdMethod.invoke(wrapper);
        }
        return id;
    }

    /**
     * Apply supplied function to loaded entity.
     *
     * @param id entity id
     * @param loader entity loader
     * @param function
     * @return the function result
     * @throws EntityNotFoundException if entity is not found
     */
    public static <E, R, ID> R withEntity(ID id, Function<ID, E> loader, Function<E, R> function) {
        E entity = loader.apply(id);
        if(entity == null) {
            throw new EntityNotFoundException();
        }
        return function.apply(entity);
    }

    /**
     * Apply supplied consumer to loaded entity.
     *
     * @param id entity id
     * @param loader entity loader
     * @param consumer
     * @throws EntityNotFoundException if entity is not found
     */
    public static <E, ID> void withEntity(ID id, Function<ID, E> loader, Consumer<E> consumer) {
        E entity = loader.apply(id);
        if(entity == null) {
            throw new EntityNotFoundException();
        }
        consumer.accept(entity);
    }

    /**
     * try to delete entity, catching data integrity violation exception.
     * As there is no easy way to know from exception which of delete or update operation was tried,
     * we are using simple helper for delete to map data integrity violation to another exception.
     * Usually we get exception only when data is flushed to database, so here we flush it manually.
     *
     * @param remover
     * @param entity
     * @param errorCode
     */
    public static <E> void deleteEntity(JpaRepository<E, ?> repository, E entity, String errorCode) {
        try {
            repository.delete(entity);
            repository.flush();
        } catch(DataIntegrityViolationException e) {
            throw new EntityRemoveException(errorCode, e.getCause());
        }
    }

    public static <E> void deleteEntity(JpaRepository<E, ?> repository, E entity) {
        deleteEntity(repository, entity, null);
    }

    /**
     * Helper to get id from proxy without initializing entity.
     * https://hibernate.atlassian.net/browse/HHH-3718
     *
     * @param entity can be null
     * @return
     */
    public static Long getNullableId(BaseEntityWithId entity) {
        return entity != null ? getId(entity) : null;
    }

    /**
     * Helper to get id from proxy without initializing entity.
     * https://hibernate.atlassian.net/browse/HHH-3718
     *
     * @param entity
     * @return
     * @throws NullPointerException if entity is null
     */
    public static Long getId(BaseEntityWithId entity) {
        if (entity instanceof HibernateProxy) {
            LazyInitializer lazyInitializer = ((HibernateProxy) entity).getHibernateLazyInitializer();
            if (lazyInitializer.isUninitialized()) {
                return (Long) lazyInitializer.getIdentifier();
            }
        }
        return entity.getId();
    }

    /**
     * Helper to get code from proxy without initializing entity.
     * https://hibernate.atlassian.net/browse/HHH-3718
     *
     * @param entity can be null
     * @return
     */
    public static String getNullableCode(Classifier entity) {
        return entity != null ? getCode(entity) : null;
    }

    /**
     * Helper to get code from proxy without initializing entity.
     * https://hibernate.atlassian.net/browse/HHH-3718
     *
     * @param entity
     * @return
     * @throws NullPointerException if entity is null
     */
    public static String getCode(Classifier entity) {
        if (entity instanceof HibernateProxy) {
            LazyInitializer lazyInitializer = ((HibernateProxy) entity).getHibernateLazyInitializer();
            if (lazyInitializer.isUninitialized()) {
                return (String) lazyInitializer.getIdentifier();
            }
        }
        return entity.getCode();
    }

    /**
     * Verify entity version.
     *
     * @param e
     * @param version
     * @throws OptimisticLockException if version is not same
     */
    public static void assertEntityVersion(Versioned e, Long version) {
        if(version == null) {
            throw new OptimisticLockException(String.format("no version for entity %s", e.getClass()));
        } else if (!version.equals(e.getVersion())) {
            throw new OptimisticLockException(String.format("version mismatch for entity %s (is %d, but request has %d)", e.getClass(), e.getVersion(), version));
        }
    }

    /**
     * get language-specific property name
     * @param namePrefix
     * @param language if null, then estonian is used
     * @return field name with language suffix added
     */
    public static String propertyName(String namePrefix, Language language) {
        return PROPERTY_NAME_CACHE.computeIfAbsent(namePrefix, key -> new ConcurrentHashMap<>())
                                  .computeIfAbsent(language != null ? language : Language.ET, lang -> namePrefix + StringUtils.capitalize(lang.name().toLowerCase()));
    }

    private static final ConcurrentMap<String, ConcurrentMap<Language, String>> PROPERTY_NAME_CACHE = new ConcurrentHashMap<>();

}
