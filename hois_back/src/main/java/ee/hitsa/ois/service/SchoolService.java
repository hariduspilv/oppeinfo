package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.SearchUtil.propertyContains;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;

import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.School;
import ee.hitsa.ois.domain.SchoolStudyLevel;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.repository.OisFileRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.SchoolForm;
import ee.hitsa.ois.web.commandobject.SchoolSearchCommand;
import ee.hitsa.ois.web.dto.SchoolWithoutLogo;

@Transactional
@Service
public class SchoolService {

    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private OisFileRepository oisFileRepository;
    @Autowired
    private SchoolRepository schoolRepository;

    public School save(School school, SchoolForm schoolForm) {
        OisFile logo = school.getLogo();
        if(Boolean.TRUE.equals(schoolForm.getDeleteCurrentLogo())) {
            if(logo != null) {
                oisFileRepository.delete(logo);
                school.setLogo(null);
            }
        } else if(schoolForm.getLogo() != null) {
            if(logo == null) {
                logo = new OisFile();
            }
            EntityUtil.bindToEntity(schoolForm.getLogo(), logo);
            logo = oisFileRepository.save(logo);
            school.setLogo(logo);
        }
        String ehisSchoolCode = schoolForm.getEhisSchool();
        Classifier ehisSchool = classifierRepository.getOne(ehisSchoolCode);
        // verify that domain code is from EHIS_KOOL and raise IllegalArgumentException if wrong
        if(!MainClassCode.EHIS_KOOL.name().equals(ehisSchool.getMainClassCode())) {
            throw new IllegalArgumentException("Wrong classifier code: "+ehisSchool.getMainClassCode());
        }
        school.setEhisSchool(ehisSchool);
        // XXX data duplication
        school.setNameEt(ehisSchool.getNameEt());
        return schoolRepository.save(school);
    }

    public School findOne(Long id) {
        return schoolRepository.findOne(id);
    }

    public Page<School> search(SchoolSearchCommand searchCommand, Pageable pageable) {
        return schoolRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            String nameField = Language.EN.equals(searchCommand.getLang()) ? "nameEn" : "nameEt";
            propertyContains(() -> root.get(nameField), cb, searchCommand.getName(), filters::add);

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable);
    }

    public void delete(School school) {
        schoolRepository.delete(school);
    }

    public List<SchoolWithoutLogo> findAll() {
        return schoolRepository.findAllSchools();
    }

    public School updateStudyLevels(School school, List<String> studyLevels) {
        if(studyLevels != null) {
            List<SchoolStudyLevel> storedStudyLevels = school.getStudyLevels();
            Set<String> studyLevelCodes = storedStudyLevels.stream().map(SchoolStudyLevel::getStudyLevel).map(Classifier::getCode).collect(Collectors.toSet());

            for(String studyLevel : studyLevels) {
                if(!studyLevelCodes.remove(studyLevel)) {
                    // add new link
                    Classifier c = classifierRepository.getOne(studyLevel);
                    // verify that domain code is from OPPEASTE and raise IllegalArgumentException if wrong
                    if(!MainClassCode.OPPEASTE.name().equals(c.getMainClassCode())) {
                        throw new IllegalArgumentException("Wrong classifier code: "+c.getMainClassCode());
                    }
                    SchoolStudyLevel sl = new SchoolStudyLevel();
                    sl.setSchool(school);
                    sl.setStudyLevel(c);
                    storedStudyLevels.add(sl);
                }
            }

            // remove possible letfovers
            storedStudyLevels.removeIf(sl -> !studyLevels.contains(sl.getStudyLevel().getCode()));
        }

        return schoolRepository.save(school);
    }
}
