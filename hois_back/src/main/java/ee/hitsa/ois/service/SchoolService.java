package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.SearchUtil.propertyContains;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;

import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.school.SchoolStudyLevel;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.repository.OisFileRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.SchoolForm;
import ee.hitsa.ois.web.commandobject.SchoolSearchCommand;
import ee.hitsa.ois.web.dto.SchoolDto;

@Transactional
@Service
public class SchoolService {

    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private OisFileRepository oisFileRepository;
    @Autowired
    private SchoolRepository schoolRepository;

    public School create(SchoolForm schoolForm) {
        return save(new School(), schoolForm);
    }

    public School save(School school, SchoolForm schoolForm) {
        EntityUtil.bindToEntity(schoolForm, school, classifierRepository);
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
        // XXX data duplication
        school.setNameEt(school.getEhisSchool().getNameEt());
        return schoolRepository.save(school);
    }

    public School getOne(Long id) {
        return schoolRepository.getOne(id);
    }

    public Page<SchoolDto> search(SchoolSearchCommand searchCommand, Pageable pageable) {
        return schoolRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            String nameField = Language.EN.equals(searchCommand.getLang()) ? "nameEn" : "nameEt";
            propertyContains(() -> root.get(nameField), cb, searchCommand.getName(), filters::add);

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(SchoolDto::of);
    }

    public void delete(School school) {
        EntityUtil.deleteEntity(schoolRepository, school);
    }

    public School updateStudyLevels(School school, List<String> studyLevels) {
        if(studyLevels != null) {
            List<SchoolStudyLevel> storedStudyLevels = school.getStudyLevels();
            if(storedStudyLevels == null) {
                school.setStudyLevels(storedStudyLevels = new ArrayList<>());
            }
            EntityUtil.bindClassifierCollection(storedStudyLevels, sl -> EntityUtil.getCode(sl.getStudyLevel()), studyLevels, studyLevel -> {
                // add new link
                Classifier c = classifierRepository.getOne(studyLevel);
                // verify that domain code is from OPPEASTE and raise IllegalArgumentException if wrong
                if(!MainClassCode.OPPEASTE.name().equals(c.getMainClassCode())) {
                    throw new IllegalArgumentException("Wrong classifier code: "+c.getMainClassCode());
                }
                SchoolStudyLevel sl = new SchoolStudyLevel();
                sl.setSchool(school);
                sl.setStudyLevel(c);
                return sl;
            });
        }

        return schoolRepository.save(school);
    }
}
