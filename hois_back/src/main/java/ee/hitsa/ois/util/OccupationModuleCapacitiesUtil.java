package ee.hitsa.ois.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleCapacity;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleThemeCapacity;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleYearCapacity;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleCapacityDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleThemeCapacityDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleThemeDto;
import io.jsonwebtoken.lang.Collections;

public abstract class OccupationModuleCapacitiesUtil {

    public static void setEmptyModuleCapacities(CurriculumVersionOccupationModuleDto dto, List<Classifier> capacityTypes) {
        if(dto.getCapacities().size() < capacityTypes.size()) {
            for(Classifier type : capacityTypes) {
                String typeCode = EntityUtil.getCode(type);
                Optional<CurriculumVersionOccupationModuleCapacityDto> c = dto.getCapacities().stream()
                        .filter(cap -> cap.getCapacityType().equals(typeCode)).findAny();
                if(!c.isPresent()) {
                    dto.getCapacities().add(CurriculumVersionOccupationModuleCapacityDto.empty(type));
                }
            }
        }
    }

    public static void setEmptyThemeCapacities(CurriculumVersionOccupationModuleThemeDto themeDto, List<Classifier> capacityTypes) {
        if(themeDto.getCapacities().size() < capacityTypes.size()) {
            for(Classifier type : capacityTypes) {
                String typeCode = EntityUtil.getCode(type);
                Optional<CurriculumVersionOccupationModuleThemeCapacityDto> c = themeDto.getCapacities().stream()
                        .filter(cap -> cap.getCapacityType().equals(typeCode)).findAny();
                if(!c.isPresent()) {
                    themeDto.getCapacities().add(CurriculumVersionOccupationModuleThemeCapacityDto.empty(type));
                }
            }
        }  
    }

    // update module capacities
    public static void updateModuleCapacities(CurriculumVersionOccupationModule occupationModule, List<Classifier> capacityTypes) {
        Set<CurriculumVersionOccupationModuleTheme> themes = occupationModule.getThemes();
        HashMap<Classifier, CurriculumVersionOccupationModuleCapacity> capacities = new HashMap<>();
        
        if (!Collections.isEmpty(capacityTypes)) {
            capacities.putAll(capacityTypes.stream().collect(Collectors.toMap(cap -> cap, cap -> createModuleCapacity(cap, Short.valueOf((short) 0), occupationModule))));
        }
        
        if (!themes.isEmpty()) {

            for (CurriculumVersionOccupationModuleTheme theme : themes) {
                for (CurriculumVersionOccupationModuleThemeCapacity cap : theme.getCapacities()) {
                    if (capacities.containsKey(cap.getCapacityType())) {
                        capacities.get(cap.getCapacityType()).setHours(
                            Short.valueOf((short)(capacities.get(cap.getCapacityType()).getHours().shortValue() + cap.getHours().shortValue()))
                        );
                    } else {
                        CurriculumVersionOccupationModuleCapacity newCap = createModuleCapacity(cap.getCapacityType(), cap.getHours(), occupationModule);
                        capacities.put(cap.getCapacityType(), newCap);
                    }
                    if (cap.getContact().equals(Boolean.TRUE)) {
                        capacities.get(cap.getCapacityType()).setContact(cap.getContact());
                    }
                }
            }
        } else {
            return;
        }
        occupationModule.setCapacities(new HashSet<>(capacities.values()));
    }

    private static CurriculumVersionOccupationModuleCapacity createModuleCapacity(Classifier type, Short hours, CurriculumVersionOccupationModule occupationModule) {
        CurriculumVersionOccupationModuleCapacity newCapacity = new CurriculumVersionOccupationModuleCapacity();
        newCapacity.setCapacityType(type);
        newCapacity.setContact(Boolean.FALSE);
        newCapacity.setHours(hours);
        newCapacity.setModule(occupationModule);
        return newCapacity;
    }

    // update module year capacities
    public static void updateModuleYearCapacitiesHours(CurriculumVersionOccupationModule occupationModule) {
        Map<Short, CurriculumVersionOccupationModuleYearCapacity> yearCapacities = StreamUtil.toMap(c -> c.getStudyYearNumber(),
                occupationModule.getYearCapacities());

        int studyYears = CurriculumUtil.studyYears(occupationModule.getCurriculumVersion().getCurriculum());
        for(int year = 1; year <= studyYears; year++) {
            Short studyYear = Short.valueOf((short)year);
            CurriculumVersionOccupationModuleYearCapacity capacity = yearCapacities.get(studyYear);
            if(capacity == null) {
                capacity = new CurriculumVersionOccupationModuleYearCapacity();
                capacity.setModule(occupationModule);
                capacity.setStudyYearNumber(studyYear);
                occupationModule.getYearCapacities().add(capacity);
            }
            BigDecimal credits = getThemesHours(occupationModule.getThemes(), year);
            capacity.setCredits(credits);
        }
    }

    private static BigDecimal getThemesHours(Set<CurriculumVersionOccupationModuleTheme> themes, int year) {
        return themes.stream().filter(t -> t.getStudyYearNumber() != null && year == t.getStudyYearNumber().intValue())
        .map(t -> t.getCredits()).reduce((t, s) -> s.add(t)).orElse(BigDecimal.ZERO);
    }
}
