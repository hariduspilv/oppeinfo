package ee.hitsa.ois.service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.enums.CurriculumVersionStatus;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.Translatable;

@Transactional
@Service
public class PublicDataService {

    private static final Set<String> IGNORED_PROPERTIES = new HashSet<>(Arrays.asList("version", "inserted", "insertedBy", "changed", "changedBy", "class"));
    private static final Set<String> IGNORED_CLASSES = new HashSet<>(Arrays.asList("org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer"));

    @Autowired
    private EntityManager em;

    public Map<String, Object> curriculum(Long curriculumId) {
        Curriculum curriculum = em.getReference(Curriculum.class, curriculumId);
        assertVisibleToPublic(curriculum);
        Map<String, Object> dto = toJson(curriculum, "files", "versions");
        // show only confirmed versions
        dto.put("versions", curriculum.getVersions().stream().filter(PublicDataService::isVisibleToPublic).map(r -> toJson(r, "curriculum")).collect(Collectors.toList()));
        return dto;
    }

    public Map<String, Object> curriculumVersion(Long curriculumId, Long curriculumVersionId) {
        CurriculumVersion curriculumVersion = em.getReference(CurriculumVersion.class, curriculumVersionId);
        if(curriculumId == null || !curriculumId.equals(EntityUtil.getId(curriculumVersion.getCurriculum()))) {
            throw new EntityNotFoundException();
        }
        if(!isVisibleToPublic(curriculumVersion)) {
            throw new EntityNotFoundException();
        }
        return toJson(curriculumVersion, "curriculum");
    }

    private Map<String, Object> toJson(Object data, String...ignoredProperties) {
        return toJson(data, new IdentityHashMap<>(), ignoredProperties);
    }

    private Map<String, Object> toJson(Object data, Map<Object, Object> visited, String...ignoredProperties) {
        visited.put(data, null);
        List<String> ignored = Arrays.asList(ignoredProperties);
        Map<String, Object> dto = new LinkedHashMap<>();
        List<PropertyDescriptor> propertyDescriptors = Arrays.asList(BeanUtils.getPropertyDescriptors(data.getClass()));
        propertyDescriptors.sort(Comparator.comparing(PropertyDescriptor::getName));
        for(PropertyDescriptor spd : propertyDescriptors) {
            Method readMethod = spd.getReadMethod();
            String propertyName = spd.getName();
            if(readMethod != null && !IGNORED_PROPERTIES.contains(propertyName) && !ignored.contains(propertyName) && Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                try {
                    Object value = readMethod.invoke(data);
                    if(value != null && IGNORED_CLASSES.contains(value.getClass().getName())) {
                        // blacklisted value classes
                        continue;
                    }
                    if(value instanceof BaseEntityWithId && visited.containsKey(value)) {
                        // already printed out, backreference
                        continue;
                    }
                    if(value instanceof Translatable) {
                        value = ((Translatable) value).getNameEt();
                    } else if(value instanceof BaseEntityWithId) {
                        value = toJson(value, visited);
                    } else if(value instanceof Collection) {
                        value = StreamUtil.toMappedList(r -> toJson(r, visited), (Collection<?>)value);
                    }
                    dto.put(propertyName, value);
                } catch(Throwable e) {
                    throw new FatalBeanException("Could not read property '" + propertyName + "' from data", e);
                }
            }
        }
        return dto;
    }

    private static void assertVisibleToPublic(Curriculum curriculum) {
        if(!ClassifierUtil.equals(CurriculumStatus.OPPEKAVA_STAATUS_K, curriculum.getStatus())) {
            throw new EntityNotFoundException();
        }
    }

    private static boolean isVisibleToPublic(CurriculumVersion curriculumVersion) {
        return ClassifierUtil.equals(CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_K, curriculumVersion.getStatus());
    }
}
