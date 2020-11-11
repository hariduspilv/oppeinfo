package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.gradingschema.GradingSchema;
import ee.hitsa.ois.domain.gradingschema.GradingSchemaRow;
import ee.hitsa.ois.domain.gradingschema.GradingSchemaStudyYear;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.GradingSchemaType;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.gradingschema.GradingSchemaForm;
import ee.hitsa.ois.web.commandobject.gradingschema.GradingSchemaRowForm;
import ee.hitsa.ois.web.dto.gradingSchema.GradingSchemaDto;
import ee.hitsa.ois.web.dto.gradingSchema.GradingSchemaRowDto;
import ee.hitsa.ois.web.dto.gradingSchema.SchoolExistingGradingSchemasDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional
@Service
public class GradingSchemaService {

    @Autowired
    private EntityManager em;
    @Autowired
    private SchoolService schoolService;

    private static final String SCHEMA_ROW_IN_USE = "exists (" +
            "select 1 from journal_entry_student jes where jes.grading_schema_row_id = gsr.id " +
            "union all select 1 from journal_entry_student_history jesh where jesh.grading_schema_row_id = gsr.id " +
            "union all select 1 from student_curriculum_module_outcomes_result scmor where scmor.grading_schema_row_id = gsr.id " +
            "union all select 1 from student_curriculum_module_outcomes_result_history scmorh where scmorh.grading_schema_row_id = gsr.id " +
            "union all select 1 from protocol_student ps where ps.grading_schema_row_id = gsr.id " +
            "union all select 1 from protocol_student_history psh where psh.grading_schema_row_id = gsr.id" +
            ")";

    public GradingSchemaDto get(GradingSchema gradingSchema) {
        GradingSchemaDto dto = GradingSchemaDto.of(gradingSchema);
        dto.setStudyYears(StreamUtil.toMappedList(gsy -> EntityUtil.getId(gsy.getStudyYear()),
                gradingSchema.getGradingSchemaStudyYears()));
        setGradingSchemaDtoRows(dto);
        return dto;
    }

    private void setGradingSchemaDtoRows(GradingSchemaDto dto) {
        List<?> data = em.createNativeQuery("select gsr.id, gsr.grade, gsr.grade_en, gsr.grade_real_code, " +
                "gsr.is_valid, " + SCHEMA_ROW_IN_USE + "in_use from grading_schema gs " +
                "join grading_schema_row gsr on gsr.grading_schema_id = gs.id " +
                "where gs.id = ?1 order by gsr.grade_real_code, gsr.id")
                .setParameter(1, dto.getId())
                .getResultList();
        dto.setGradingSchemaRows(StreamUtil.toMappedList(r -> new GradingSchemaRowDto(resultAsLong(r, 0),
                resultAsString(r, 1), resultAsString(r, 2), resultAsString(r, 3), resultAsBoolean(r, 4),
                resultAsBoolean(r, 5)), data));
    }

    public List<GradingSchemaDto> typeSchemas(Long schoolId, String type) {
        List<GradingSchema> gradingSchemas = em.createQuery("select gs from GradingSchema gs " +
                "where gs.school.id = :schoolId " +
                "and gs.isVocational = :isVocational and gs.isHigher = :isHigher " +
                "and gs.isBasic = :isBasic and gs.isSecondary = :isSecondary " +
                "order by gs.inserted", GradingSchema.class)
                .setParameter("schoolId", schoolId)
                .setParameter("isVocational", type.equals(GradingSchemaType.VOCATIONAL.name().toLowerCase()))
                .setParameter("isHigher", type.equals(GradingSchemaType.HIGHER.name().toLowerCase()))
                .setParameter("isBasic", type.equals(GradingSchemaType.BASIC.name().toLowerCase()))
                .setParameter("isSecondary", type.equals(GradingSchemaType.SECONDARY.name().toLowerCase()))
                .getResultList();

        List<GradingSchemaDto> schemaDtos = StreamUtil.toMappedList(GradingSchemaDto::of, gradingSchemas);
        if (!schemaDtos.isEmpty()) {
            setTypeSchemaStudyYears(schemaDtos);
            setTypeSchemaRows(schemaDtos);
        }
        return schemaDtos;
    }

    private void setTypeSchemaStudyYears(List<GradingSchemaDto> schemaDtos) {
        List<?> data = em.createNativeQuery("select gs.id gs_id, sy.id sy_id from grading_schema gs " +
                "join grading_schema_study_year gsy on gsy.grading_schema_id = gs.id " +
                "join study_year sy on sy.id = gsy.study_year_id " +
                "where gs.id in (?1)")
                .setParameter(1, StreamUtil.toMappedList(GradingSchemaDto::getId, schemaDtos))
                .getResultList();
        Map<Long, List<Long>> studyYearsBySchema = data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> resultAsLong(r, 1), Collectors.toList())));
        for (GradingSchemaDto dto : schemaDtos) {
            dto.setStudyYears(studyYearsBySchema.get(dto.getId()));
        }
    }

    private void setTypeSchemaRows(List<GradingSchemaDto> schemaDtos) {
        List<?> data = em.createNativeQuery("select gs.id gs_id, gsr.id gsr_id, gsr.grade, gsr.grade_en, " +
                "gsr.grade_real_code, gsr.is_valid from grading_schema gs " +
                "join grading_schema_row gsr on gsr.grading_schema_id = gs.id " +
                "where gs.id in (?1) order by gsr.grade_real_code, gsr.id")
                .setParameter(1, StreamUtil.toMappedList(GradingSchemaDto::getId, schemaDtos))
                .getResultList();
        Map<Long, List<GradingSchemaRowForm>> rowsBySchema = data.stream().collect(
                Collectors.groupingBy(r -> resultAsLong(r, 0),
                        Collectors.mapping(r -> new GradingSchemaRowForm(resultAsLong(r, 1), resultAsString(r, 2),
                                resultAsString(r, 3), resultAsString(r, 4), resultAsBoolean(r, 5)), Collectors.toList())));
        for (GradingSchemaDto dto : schemaDtos) {
            dto.setGradingSchemaRows(rowsBySchema.get(dto.getId()));
        }
    }

    public GradingSchema create(HoisUserDetails user, GradingSchemaForm form) {
        School school = em.getReference(School.class, user.getSchoolId());
        SchoolService.SchoolType schoolType = schoolService.schoolType(user.getSchoolId());

        GradingSchema gradingSchema = new GradingSchema();
        gradingSchema.setSchool(school);
        if (Boolean.TRUE.equals(form.getIsVocational())) {
            AssertionFailedException.throwIf(!schoolType.isVocational(), "Not vocational school");
            gradingSchema.setIsVocational(Boolean.TRUE);
        } else if (Boolean.TRUE.equals(form.getIsHigher())) {
            AssertionFailedException.throwIf(!schoolType.isHigher(), "Not higher school");
            gradingSchema.setIsHigher(Boolean.TRUE);
        } else if (Boolean.TRUE.equals(form.getIsBasic())) {
            AssertionFailedException.throwIf(!schoolType.isBasic(), "Not basic school");
            gradingSchema.setIsBasic(Boolean.TRUE);
        } else {
            AssertionFailedException.throwIf(!schoolType.isSecondary(), "Not secondary school");
            gradingSchema.setIsSecondary(Boolean.TRUE);
        }

        return save(gradingSchema, form);
    }

    public GradingSchema save(GradingSchema gradingSchema, GradingSchemaForm form) {
        ValidationFailedException.throwIf(!areStudyYearsUnique(gradingSchema, form),
                "schoolGradingSchema.error.studyYearsInUse");

        gradingSchema.setIsVerbal(form.getIsVerbal());
        gradingSchema.setIsGrade(form.getIsGrade());

        EntityUtil.bindEntityCollection(gradingSchema.getGradingSchemaStudyYears(),
                gsy -> EntityUtil.getId(gsy.getStudyYear()), form.getStudyYears(), sy -> sy, r -> {
                    GradingSchemaStudyYear studyYear = new GradingSchemaStudyYear();
                    studyYear.setGradingSchema(gradingSchema);
                    studyYear.setStudyYear(em.getReference(StudyYear.class, r));
                    return studyYear;
                });

        EntityUtil.bindEntityCollection(gradingSchema.getGradingSchemaRows(), EntityUtil::getId,
                form.getGradingSchemaRows(), GradingSchemaRowForm::getId, r -> {
                    GradingSchemaRow row = EntityUtil.bindToEntity(r, new GradingSchemaRow());
                    row.setGradingSchema(gradingSchema);
                    row.setGradeReal(em.getReference(Classifier.class, r.getGradeReal()));
                    return row;
                }, (r, row) -> {
                    EntityUtil.bindToEntity(r, row);
                    row.setGradeReal(em.getReference(Classifier.class, r.getGradeReal()));
                });

        return EntityUtil.save(gradingSchema, em);
    }

    public boolean areStudyYearsUnique(GradingSchema gradingSchema, GradingSchemaForm form) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from grading_schema gs " +
                "join grading_schema_study_year gsy on gsy.grading_schema_id = gs.id");

        qb.optionalCriteria("gs.id != :gradingSchemaId", "gradingSchemaId", gradingSchema.getId());
        qb.requiredCriteria("gs.school_id = :schoolId", "schoolId", EntityUtil.getId(gradingSchema.getSchool()));
        qb.requiredCriteria("gs.is_vocational = :isVocational", "isVocational", gradingSchema.getIsVocational());
        qb.requiredCriteria("gs.is_higher = :isHigher", "isHigher", gradingSchema.getIsHigher());
        qb.requiredCriteria("gs.is_basic = :isBasic", "isBasic", gradingSchema.getIsBasic());
        qb.requiredCriteria("gs.is_secondary = :isSecondary", "isSecondary", gradingSchema.getIsSecondary());
        qb.requiredCriteria("gsy.study_year_id in (:studyYearIds)", "studyYearIds", form.getStudyYears());

        List<?> data = qb.select("gsy.id", em).getResultList();
        return data.isEmpty();
    }

    public boolean isInUse(GradingSchema gradingSchema) {
        List<?> data = em.createNativeQuery("select gs.id from grading_schema gs " +
                "join grading_schema_row gsr on gsr.grading_schema_id = gs.id " +
                "where gs.id = ?1 and " + SCHEMA_ROW_IN_USE)
                .setParameter(1, gradingSchema.getId())
                .getResultList();
        return !data.isEmpty();
    }

    public void delete(HoisUserDetails user, GradingSchema gradingSchema) {
        if (isInUse(gradingSchema)) {
            throw new ValidationFailedException("schoolGradingSchema.error.inUse");
        }
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(gradingSchema, em);
    }

    public void deleteSchemaRow(HoisUserDetails user, GradingSchemaRow row) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(row, em);
    }

    public SchoolExistingGradingSchemasDto schoolExistingGradingSchemas(Long schoolId) {
        List<?> data = em.createNativeQuery("select count(case when gs.is_vocational then gs.id end) > 0 is_vocational, " +
                "count(case when gs.is_higher then gs.id end) > 0 is_higher, " +
                "count(case when gs.is_basic then gs.id end) > 0 is_basic, " +
                "count(case when gs.is_secondary then gs.id end) > 0 is_secondary " +
                "from grading_schema gs where gs.school_id = ?1")
                .setParameter(1, schoolId)
                .getResultList();
         return new SchoolExistingGradingSchemasDto(resultAsBoolean(data.get(0), 0), resultAsBoolean(data.get(0), 1),
            resultAsBoolean(data.get(0), 2), resultAsBoolean(data.get(0), 3));
    }
}
