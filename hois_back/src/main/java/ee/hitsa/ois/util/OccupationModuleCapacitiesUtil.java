package ee.hitsa.ois.util;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        Set<CurriculumVersionOccupationModuleCapacity> capacities = new HashSet<>();

        for(Classifier type : capacityTypes) {
            Short hours = getAllThemesCapacitiesByType(type, occupationModule.getThemes());
            CurriculumVersionOccupationModuleCapacity c = getCapacityByType(type, occupationModule);
            c.setHours(hours);
            c.setContact(Boolean.valueOf(getContract(type, occupationModule.getThemes())));
            capacities.add(c);
        }
        occupationModule.setCapacities(capacities);
    }

    private static boolean getContract(Classifier type, Set<CurriculumVersionOccupationModuleTheme> themes) {
        String typeCode = EntityUtil.getCode(type);
        for(CurriculumVersionOccupationModuleTheme theme : themes) {
            boolean result = theme.getCapacities().stream()
                    .anyMatch(c -> typeCode
                            .equals(EntityUtil.getCode(c.getCapacityType())) && Boolean.TRUE.equals(c.getContact()));
            if(result) {
                return true;
            }
        }
        return false;
    }

    private static Short getAllThemesCapacitiesByType(Classifier type, Set<CurriculumVersionOccupationModuleTheme> themes) {
        short sum = 0;

        for(CurriculumVersionOccupationModuleTheme theme : themes) {
            sum += getThemesCapacity(theme, type);
        }
        return Short.valueOf(sum);
    }

    private static short getThemesCapacity(CurriculumVersionOccupationModuleTheme theme, Classifier type) {
        String typeCode = EntityUtil.getCode(type);
        Optional<CurriculumVersionOccupationModuleThemeCapacity> capacity =  theme.getCapacities().stream()
                .filter(c -> typeCode.equals(EntityUtil.getCode(c.getCapacityType()))).findAny();

        if(!capacity.isPresent()) {
            return 0;
        }
        return capacity.get().getHours().shortValue();
    }

    private static CurriculumVersionOccupationModuleCapacity getCapacityByType(Classifier type, 
            CurriculumVersionOccupationModule occupationModule) {
        String typeCode = EntityUtil.getCode(type);
        Optional<CurriculumVersionOccupationModuleCapacity> capacity = occupationModule.getCapacities().stream()
                .filter(c -> typeCode.equals(EntityUtil.getCode(c.getCapacityType()))).findAny();
        if(!capacity.isPresent()) {
            return createModuleCapacity(type, occupationModule);
        }
        return capacity.get();
    }

    private static CurriculumVersionOccupationModuleCapacity createModuleCapacity(Classifier type, CurriculumVersionOccupationModule occupationModule) {
        CurriculumVersionOccupationModuleCapacity newCapacity = new CurriculumVersionOccupationModuleCapacity();
        newCapacity.setCapacityType(type);
        newCapacity.setContact(Boolean.FALSE);
        newCapacity.setHours(Short.valueOf((short) 0));
        newCapacity.setModule(occupationModule);
        return newCapacity;
    }

    // update module year capacities
    public static void updateModuleYearCapacitiesHours(CurriculumVersionOccupationModule occupationModule) {
        int studyYears = CurriculumUtil.studyYears(occupationModule.getCurriculumVersion().getCurriculum());
        for(int year = 1; year <= studyYears; year++) {
            CurriculumVersionOccupationModuleYearCapacity capacity = getYearCapacity(occupationModule, year);
            BigDecimal credits = getThemesHours(occupationModule.getThemes(), year);
            capacity.setCredits(credits);
        }
    }

    private static BigDecimal getThemesHours(Set<CurriculumVersionOccupationModuleTheme> themes, int year) {
        return themes.stream().filter(t -> t.getStudyYearNumber() != null && year == t.getStudyYearNumber().intValue())
        .map(t -> t.getCredits()).reduce((t, s) -> s.add(t)).orElse(BigDecimal.ZERO);
    }

    private static CurriculumVersionOccupationModuleYearCapacity getYearCapacity(CurriculumVersionOccupationModule occupationModule, int year) {
        return occupationModule.getYearCapacities().stream().filter(c -> c.getStudyYearNumber().intValue() == year).findAny().get();
    }
}
