package ee.hitsa.ois.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
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
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.persistence.EntityManager;
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
import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;
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
        Map<String, ClassifierRestriction> classifierProperties = new HashMap<>();
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
                            if (value instanceof String) {
                                value = ((String) value).trim();
                                if(((String)value).isEmpty()) {
                                    value = null;
                                }
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
                                classifierProperties.put(propertyName, restriction);
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
                            if("insertedBy".equals(propertyName) || "changedBy".equals(propertyName) && value instanceof String) {
                                // strip possible idcode from actual value
                                value = PersonUtil.stripIdcodeFromFullnameAndIdcode((String)value);
                            }
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

    /**
     * child collection binding when only id is required to set (typically classifier reference).
     *
     * @param storedValues
     * @param idExtractor
     * @param newIds
     * @param newValueFactory
     */
    public static <SV, ID> void bindEntityCollection(Collection<SV> storedValues, Function<SV, ID> idExtractor, Collection<ID> newIds, Function<ID, SV> newValueFactory) {
        Set<ID> storedIds = StreamUtil.toMappedSet(idExtractor, storedValues);

        if(newIds != null) {
            for(ID id : newIds) {
                if(!storedIds.remove(id)) {
                    storedValues.add(newValueFactory.apply(id));
                }
            }
        }

        // remove possible leftovers
        storedValues.removeIf(t -> storedIds.contains(idExtractor.apply(t)));
    }

    /**
     * child collection binding with create, update and remove operations.
     *
     * @param storedValues
     * @param storedIdExtractor
     * @param newValues
     * @param newIdExtractor
     * @param newValueFactory
     * @param updater
     */
    public static <SV, ID, NV> void bindEntityCollection(Collection<SV> storedValues, Function<SV, ID> storedIdExtractor, Collection<NV> newValues, Function<NV, ID> newIdExtractor, Function<NV, SV> newValueFactory, BiConsumer<NV, SV> updater) {
        Map<ID, SV> mappedStoredValues = StreamUtil.toMap(storedIdExtractor, storedValues);

        if(newValues != null) {
            for(NV newValue : newValues) {
                ID id = newIdExtractor.apply(newValue);
                if(id == null) {
                    storedValues.add(newValueFactory.apply(newValue));
                } else {
                    SV storedValue = mappedStoredValues.remove(id);
                    if(storedValue == null) {
                        throw new AssertionFailedException("Cannot find existing entity with id: " + id);
                    }
                    if(updater != null) {
                        updater.accept(newValue, storedValue);
                    }
                }
            }
        }

        // remove possible leftovers
        storedValues.removeAll(mappedStoredValues.values());
    }

    public static void bindClassifiers(Object command, Object entity, Map<String, ClassifierRestriction> properties, ClassifierRepository loader) {
        PropertyAccessor source = PropertyAccessorFactory.forBeanPropertyAccess(command);
        PropertyAccessor destination = PropertyAccessorFactory.forBeanPropertyAccess(entity);
        properties.forEach((p, r) -> {
            MainClassCode[] t = r.value();

            String classCodeOrValue = (String)source.getPropertyValue(p);
            Classifier current = (Classifier)destination.getPropertyValue(p);

            String currentClassCodeOrValue = null;
            if (current != null) {
                currentClassCodeOrValue = r.useClassifierValue() ? current.getValue() : getCode(current);
            }

            if(!Objects.equals(classCodeOrValue, currentClassCodeOrValue)) {
                Classifier c;
                if(classCodeOrValue == null || classCodeOrValue.isEmpty()) {
                    c = null;
                } else {
                    if (r.useClassifierValue()) {
                        if (t.length != 1) {
                            throw new RuntimeException("only one mainClassCode is allowed, when useClassifierValue=true.");
                        }
                        c = loader.findByValueAndMainClassCode(classCodeOrValue, t[0].name());
                    } else {
                        c = loader.getOne(classCodeOrValue);
                    }
                    validateClassifier(c, t);

                }
                destination.setPropertyValue(p, c);
            }
        });
    }

    public static Classifier validateClassifier(Classifier c, MainClassCode... domains) {
        if (c == null) {
            return null;
        }

        String mainClassCode = c.getMainClassCode();
        for(MainClassCode domain : domains) {
            if(domain.name().equals(mainClassCode)) {
                return c;
            }
        }
        throw new AssertionFailedException("Wrong classifier main class code: " + mainClassCode);
    }

    public static void setEntityFromRepository(Object command, Object entity,
            JpaRepository<?, Long> repository, String... properties) {
        PropertyAccessor source = PropertyAccessorFactory.forBeanPropertyAccess(command);
        PropertyAccessor destination = PropertyAccessorFactory.forBeanPropertyAccess(entity);
        for(String property : properties) {
            //usually Long is used in DTO classes to have references to other objects, but
            //sometimes ee.hitsa.ois.web.dto.AutocompleteResult is also used.
            Long id = getIdFromValue(source.getPropertyValue(property));
            destination.setPropertyValue(property, id != null ? repository.getOne(id) : null);
        }
    }

    private static Long getIdFromValue(Object value) {
        if(value instanceof Long || value == null) {
            return (Long)value;
        }
        if(value instanceof EntityConnectionCommand) {
            return ((EntityConnectionCommand)value).getId();
        }
        throw new FatalBeanException("Unknown value type: " + value.getClass().getName());
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
     * Return reference to entity of given class or null
     * @param entityClass
     * @param id
     * @param em
     * @return null if id is null
     */
    public static <T extends BaseEntityWithId> T getOptionalOne(Class<T> entityClass, Long id, EntityManager em) {
        return id != null ? em.getReference(entityClass, id) : null;
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
