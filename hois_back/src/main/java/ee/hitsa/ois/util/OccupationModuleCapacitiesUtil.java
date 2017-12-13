package ee.hitsa.ois.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.util.CollectionUtils;

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
        if(CollectionUtils.isEmpty(dto.getCapacities()) || dto.getCapacities().size() < capacityTypes.size()) {
            for(Classifier type : capacityTypes) {
                Optional<CurriculumVersionOccupationModuleCapacityDto> c = dto.getCapacities().stream()
                        .filter(cap -> cap.getCapacityType().equals(EntityUtil.getCode(type))).findFirst();
                if(!c.isPresent()) {
                    dto.getCapacities().add(CurriculumVersionOccupationModuleCapacityDto.empty(type));
                }
            }
        }
    }
    

    public static void setEmptyThemeCapacities(CurriculumVersionOccupationModuleThemeDto themeDto, List<Classifier> capacityTypes) {
        if(CollectionUtils.isEmpty(themeDto.getCapacities()) || themeDto.getCapacities().size() < capacityTypes.size()) {
            for(Classifier type : capacityTypes) {
                Optional<CurriculumVersionOccupationModuleThemeCapacityDto> c = themeDto.getCapacities().stream()
                        .filter(cap -> cap.getCapacityType().equals(EntityUtil.getCode(type))).findFirst();
                if(!c.isPresent()) {
                    themeDto.getCapacities().add(CurriculumVersionOccupationModuleThemeCapacityDto.empty(type));
                }
            }
        }  
    }
    
//    update module capacities
    
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
         
        for(CurriculumVersionOccupationModuleTheme theme : themes) {
            boolean result = false;
            result = theme.getCapacities().stream()
                    .anyMatch(c -> EntityUtil.getCode(type)
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

        Optional<CurriculumVersionOccupationModuleThemeCapacity> capacity =  theme.getCapacities().stream()
                .filter(c -> EntityUtil.getCode(type).equals(EntityUtil.getCode(c.getCapacityType()))).findFirst();
        
        if(!capacity.isPresent()) {
            return 0;
        }
        return capacity.get().getHours().shortValue();
    }

    
    private static CurriculumVersionOccupationModuleCapacity getCapacityByType(Classifier type, 
            CurriculumVersionOccupationModule occupationModule) {
        Optional<CurriculumVersionOccupationModuleCapacity> capacity = occupationModule.getCapacities().stream()
                .filter(c -> EntityUtil.getCode(type).equals(EntityUtil.getCode(c.getCapacityType()))).findFirst();
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
        List<Short> years = Arrays.asList((short) 1, (short) 2, (short) 3);
        
        for(Short year : years) {
            CurriculumVersionOccupationModuleYearCapacity capacity = getYearCapacity(occupationModule, year);
            BigDecimal credits = getThemesHours(occupationModule.getThemes(), year);
            capacity.setCredits(credits);
        }
    }

    private static BigDecimal getThemesHours(Set<CurriculumVersionOccupationModuleTheme> themes, Short year) {
        return themes.stream().filter(t -> year.equals(t.getStudyYearNumber()))
        .map(t -> t.getCredits()).reduce((t, s) -> s.add(t)).orElse(BigDecimal.ZERO);
    }

    private static CurriculumVersionOccupationModuleYearCapacity getYearCapacity(CurriculumVersionOccupationModule occupationModule, Short year) {
        return occupationModule.getYearCapacities().stream().filter(c -> c.getStudyYearNumber().equals(year)).findFirst().get();
    }
}
