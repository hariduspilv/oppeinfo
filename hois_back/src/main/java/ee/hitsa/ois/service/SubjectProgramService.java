package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgram;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgramStudyContent;
import ee.hitsa.ois.enums.SubjectAssessment;
import ee.hitsa.ois.enums.SubjectProgramStatus;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.SubjectProgramForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectProgramDto;
import ee.hitsa.ois.web.dto.SubjectProgramStudyContentDto;

@Transactional
@Service
public class SubjectProgramService {

    @Autowired
    private EntityManager em;
    @Autowired
    private ClassifierRepository classifierRepository;

    public SubjectProgramDto get(SubjectProgram program) {
        return SubjectProgramDto.of(program);
    }

    public SubjectProgram create(HoisUserDetails user, SubjectProgramForm form) {
        SubjectProgram program = new SubjectProgram();
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period ssp "
                + "inner join subject s on s.id = ssp.subject_id "
                + "inner join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id "
                + "inner join teacher t on t.id = sspt.teacher_id").limit(1);
        qb.requiredCriteria("t.id = :teacherId", "teacherId", user.getTeacherId());
        qb.requiredCriteria("s.id = :sId", "sId", form.getSubjectId());
        qb.requiredCriteria("ssp.id = :sspId", "sspId", form.getSubjectStudyPeriodId());
        List<?> results = qb.select("sspt.id", em).getResultList();
        if (results.size() != 1) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
        SubjectStudyPeriodTeacher reference = em.getReference(SubjectStudyPeriodTeacher.class, resultAsLong(results.get(0), 0));
        if (reference.getSubjectPrograms().size() > 0) {
            throw new ValidationFailedException("subjectProgram.error.hasalreadysubjectprogram");
        }
        program.setSubjectStudyPeriodTeacher(reference);
        return save(user, program, form);
    }

    public SubjectProgram save(HoisUserDetails user, SubjectProgram program, SubjectProgramForm form) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.bindToEntity(form, program, classifierRepository, "studyContents", "subjectId", "subjectStudyPeriodId");
        program.setStatus(em.getReference(Classifier.class, SubjectProgramStatus.AINEPROGRAMM_STAATUS_I.name()));

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject s "
                + "inner join subject_study_period ssp on ssp.subject_id = s.id "
                + "inner join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id").limit(1);
        qb.requiredCriteria("sspt.id = :ssptId", "ssptId", program.getSubjectStudyPeriodTeacher().getId());
        List<?> results = qb.select("s.assessment_code", em).getResultList();
        if (results.size() != 1) {
            throw new ValidationFailedException("subjectProgram.error.nosubjectassessment");
        }
        Classifier assessment = em.getReference(Classifier.class, resultAsString(results.get(0), 0));
        if (ClassifierUtil.equals(SubjectAssessment.HINDAMISVIIS_A, assessment)) {
            program.setGrade0Description(null);
            program.setGrade1Description(null);
            program.setGrade2Description(null);
            program.setGrade3Description(null);
            program.setGrade4Description(null);
            program.setGrade5Description(null);
        } else if (ClassifierUtil.equals(SubjectAssessment.HINDAMISVIIS_E, assessment)) {
            program.setPassDescription(null);
            program.setNpassDescription(null);
        } else if (ClassifierUtil.equals(SubjectAssessment.HINDAMISVIIS_H, assessment)) {
            program.setPassDescription(null);
            program.setNpassDescription(null);
            program.setGrade0Description(null);
            program.setGrade1Description(null);
            program.setGrade2Description(null);
        }
        
        updateStudyContents(program, form.getStudyContents());
        return EntityUtil.save(program, em);
    }

    public void delete(SubjectProgram program) {
        EntityUtil.deleteEntity(program, em);
    }

    public Set<AutocompleteResult> getProgramsRelatedToTeacher(HoisUserDetails user, Subject subject) {
        subject.getSubjectStudyPeriods().stream().flatMap(ssp -> ssp.getTeachers().stream()).filter(t -> t.getId() == user.getTeacherId()).map(sspt -> sspt.getSubjectPrograms()).collect(Collectors.toList());
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_program sp "
                + "inner join subject_study_period_teacher sspt on sspt.id = sp.subject_study_period_teacher_id "
                + "inner join teacher t on t.id = sspt.teacher_id "
                + "inner join subject_study_period ssp on ssp.id = sspt.subject_study_period_id "
                + "inner join study_period sper on sper.id = ssp.study_period_id "
                + "inner join study_year sy on sy.id = sper.study_year_id "
                + "inner join classifier cl on cl.code = sy.year_code "
                + "inner join subject s on s.id = ssp.subject_id ");
        qb.requiredCriteria("t.id = :teacherId", "teacherId", user.getTeacherId());
        List<?> results = qb.select("sp.id, s.name_et as sEt, s.name_en as sEn, sper.name_et as spEt, sper.name_en as spEn, cl.name_et as clEt, cl.name_en as clEn", em).getResultList();
        return StreamUtil.toMappedSet(r -> {
            return new AutocompleteResult(resultAsLong(r, 0),
                    resultAsString(r, 1) + " - " + resultAsString(r, 3) + " (" + resultAsString(r, 5) + ")",
                    resultAsString(r, 2) + " - " + resultAsString(r, 4) + " (" + resultAsString(r, 6) + ")");
        }, results);
    }

    public SubjectProgram confirm(HoisUserDetails user, SubjectProgram program) {
        EntityUtil.setUsername(user.getUsername(), em);
        program.setConfirmed(LocalDateTime.now());
        program.setConfirmedBy(user.getUsername());
        program.setStatus(em.getReference(Classifier.class, SubjectProgramStatus.AINEPROGRAMM_STAATUS_K.name()));
        return EntityUtil.save(program, em);
    }

    public SubjectProgram complete(HoisUserDetails user, SubjectProgram program) {
        EntityUtil.setUsername(user.getUsername(), em);
        program.setStatus(em.getReference(Classifier.class, SubjectProgramStatus.AINEPROGRAMM_STAATUS_V.name()));
        return EntityUtil.save(program, em);
    }

    private void updateStudyContents(SubjectProgram program, Set<SubjectProgramStudyContentDto> studyContents) {
        EntityUtil.bindEntityCollection(program.getStudyContents(), SubjectProgramStudyContent::getId,
                studyContents, SubjectProgramStudyContentDto::getId,
                dto -> createStudyContent(program, dto), this::updateStudyContent);
    }
    
    private void updateStudyContent(SubjectProgramStudyContentDto dto, SubjectProgramStudyContent content) {
        EntityUtil.bindToEntity(dto, content, classifierRepository);
    }
    
    private static SubjectProgramStudyContent createStudyContent(SubjectProgram program, SubjectProgramStudyContentDto dto) {
        SubjectProgramStudyContent entity = new SubjectProgramStudyContent();
        entity.setSubjectProgram(program);
        return EntityUtil.bindToEntity(dto, entity);
    }
}
