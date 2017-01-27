package ee.hitsa.ois.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.enums.Language;

public abstract class EntityUtil {

    private static final Set<String> IGNORED_ENTITY_PROPERTIES = new HashSet<>(Arrays.asList("version", "inserted", "insertedBy", "changed", "changedBy", "id"));

    /**
     * Copy properties from command to entity.
     * Only properties with public getter/setter are copied. Empty strings are copied as nulls.
     * See IGNORED_ENTITY_PROPERTIES for list of properties which are not copied
     *
     * @param command
     * @param entity
     * @return entity with properties copied
     * @throws FatalBeanException if the copying failed
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <E> E bindToEntity(Object command, E entity, String...ignoredProperties) {
        List<String> ignored = Arrays.asList(ignoredProperties);
        for(PropertyDescriptor spd : BeanUtils.getPropertyDescriptors(command.getClass())) {
            Method readMethod = spd.getReadMethod();
            String propertyName = spd.getName();
            if(readMethod != null && !IGNORED_ENTITY_PROPERTIES.contains(propertyName) &&!ignored.contains(propertyName) && Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                PropertyDescriptor tpd = BeanUtils.getPropertyDescriptor(entity.getClass(), propertyName);
                if(tpd == null) {
                    continue;
                }
                Method writeMethod = tpd.getWriteMethod();
                if (writeMethod != null && Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers()) && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                    try {
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
                    } catch (Throwable e) {
                        throw new FatalBeanException("Could not copy property '" + propertyName + "' from command to entity", e);
                    }
                }
            }
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
        for(PropertyDescriptor spd : BeanUtils.getPropertyDescriptors(entity.getClass())) {
            Method readMethod = spd.getReadMethod();
            String propertyName = spd.getName();
            if(readMethod != null && !ignored.contains(propertyName) && Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                PropertyDescriptor tpd = BeanUtils.getPropertyDescriptor(dto.getClass(), propertyName);
                if(tpd == null) {
                    continue;
                }
                Method writeMethod = tpd.getWriteMethod();
                if (writeMethod != null && Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers()) && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                    try {
                        Object value = readMethod.invoke(entity);
                        writeMethod.invoke(dto, value);
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
     * Verify entity version.
     *
     * @param e
     * @param version
     * @throws OptimisticLockException if version is not same
     */
    public static void assertEntityVersion(Versioned e, Long version) {
        if(version == null || !version.equals(e.getVersion())) {
            throw new OptimisticLockException();
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
