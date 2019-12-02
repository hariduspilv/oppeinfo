package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.ClassifierConnect;
import ee.hitsa.ois.domain.FinalDocSigner;
import ee.hitsa.ois.domain.Form;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumGrade;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.diploma.Diploma;
import ee.hitsa.ois.domain.diploma.DiplomaSupplement;
import ee.hitsa.ois.domain.diploma.DiplomaSupplementForm;
import ee.hitsa.ois.domain.diploma.DiplomaSupplementStudyResult;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentCurriculumCompletion;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.DocumentStatus;
import ee.hitsa.ois.enums.FormStatus;
import ee.hitsa.ois.enums.FormType;
import ee.hitsa.ois.enums.HigherModuleType;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.StudyForm;
import ee.hitsa.ois.enums.StudyLanguage;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.report.diploma.ApelResultItem;
import ee.hitsa.ois.report.diploma.DiplomaSupplementReport;
import ee.hitsa.ois.report.diploma.DiplomaSupplementResultReport;
import ee.hitsa.ois.report.diploma.StudyResultItem;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.TranslateUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.document.DiplomaBaseForm;
import ee.hitsa.ois.web.commandobject.document.DiplomaConfirmForm;
import ee.hitsa.ois.web.commandobject.document.DiplomaForm;
import ee.hitsa.ois.web.commandobject.document.DiplomaSearchForm;
import ee.hitsa.ois.web.commandobject.document.SupplementForm;
import ee.hitsa.ois.web.commandobject.document.SupplementSearchForm;
import ee.hitsa.ois.web.dto.FinalDocSignerDto;
import ee.hitsa.ois.web.dto.document.DiplomaSearchDto;
import ee.hitsa.ois.web.dto.document.DirectiveDto;
import ee.hitsa.ois.web.dto.document.FormDto;
import ee.hitsa.ois.web.dto.document.StudentDto;
import ee.hitsa.ois.web.dto.document.SupplementDto;
import ee.hitsa.ois.web.dto.document.SupplementStudentDto;

@Transactional
@Service
public class DocumentService {
    
    public static final LocalDate DIRECTIVE_INSERTED_FROM = LocalDate.of(2018, 5, 1);
    
    private static final String DIPLOMA_TEMPLATE_NAME = "diploma.xhtml";
    private static final String SUPPLEMENT_TEMPLATE_NAME = "diploma.supplement.xhtml";
    private static final String SUPPLEMENT_EN_TEMPLATE_NAME = "diploma.supplement.en.xhtml";
    private static final String DIPLOMA_VOCATIONAL_TEMPLATE_NAME = "diploma.vocational.xhtml";
    private static final String SUPPLEMENT_VOCATIONAL_TEMPLATE_NAME = "diploma.supplement.vocational.xhtml";
    private static final int FREE_FORM_COUNT = 4;
    private static final String PROFESSIONAL_DIPLOMA_STUDY_LEVEL = "OPPEASTE_514";
    private static final String PROFESSIONAL_DIPLOMA_KEY = "diploma.qualification.name.professional";
    private static final String EXAM_OCCUPATION_KEY = "diploma.exam.occupation";
    private static final String EXAM_PARTOCCUPATION_KEY = "diploma.exam.partoccupation";

    @Autowired
    private EntityManager em;
    @Autowired
    private PdfService pdfService;
    @Autowired
    private XlsService xlsService;
    
    public List<DirectiveDto> diplomaDirectives(HoisUserDetails user, Boolean isHigher) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive d");
        directiveCriteria(qb, user, isHigher);
        qb.requiredCriteria("exists (select 1 from directive_student ds "
                + " left join diploma dip on ds.student_id = dip.student_id"
                + " where ds.directive_id = d.id and ds.canceled = false and coalesce(dip.status_code,'LOPUDOK_STAATUS_K') = :dipstatus)",
                "dipstatus", DocumentStatus.LOPUDOK_STAATUS_K.name());
        return toDirectiveDtoList(qb);
    }
    
    public List<DirectiveDto> supplementDirectives(HoisUserDetails user, Boolean isHigher) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive d");
        directiveCriteria(qb, user, isHigher);
        qb.requiredCriteria("exists (select 1 from directive_student ds"
                + " join diploma dip on ds.student_id = dip.student_id"
                + " left join diploma_supplement sup on sup.diploma_id = dip.id"
                + " where ds.directive_id = d.id and ds.canceled = false and coalesce(sup.status_code,'LOPUDOK_STAATUS_K') = :supstatus)",
                "supstatus", DocumentStatus.LOPUDOK_STAATUS_K.name());
        return toDirectiveDtoList(qb);
    }
    
    private static void directiveCriteria(JpaNativeQueryBuilder qb, HoisUserDetails user, Boolean isHigher) {
        qb.requiredCriteria("d.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("d.is_higher = :isHigher", "isHigher", isHigher);
        qb.requiredCriteria("d.type_code = :type", "type", DirectiveType.KASKKIRI_LOPET.name());
        qb.requiredCriteria("d.status_code != :status", "status", DirectiveStatus.KASKKIRI_STAATUS_TYHISTATUD.name());
        qb.requiredCriteria("d.inserted >= :from", "from", DIRECTIVE_INSERTED_FROM);
    }

    private List<DirectiveDto> toDirectiveDtoList(JpaNativeQueryBuilder qb) {
        List<?> result = qb.select("d.id, d.directive_nr, d.status_code, d.inserted, d.confirm_date", em)
                .getResultList();
        return StreamUtil.toMappedList(r -> {
            DirectiveDto dto = new DirectiveDto();
            dto.setId(resultAsLong(r, 0));
            dto.setNumber(resultAsString(r, 1));
            dto.setStatus(resultAsString(r, 2));
            LocalDate confirmDate = resultAsLocalDate(r, 4);
            dto.setDate(confirmDate != null ? confirmDate : resultAsLocalDate(r, 3));
            return dto;
        }, result);
    }
    
    public List<String> formTypes(HoisUserDetails user, DiplomaForm form) {
        Long directiveId = form.getDirectiveId();
        directive(user, directiveId);
        List<?> result = em.createNativeQuery("select distinct cc.connect_classifier_code"
                + " from directive_student ds"
                + " join curriculum_version cv on cv.id = ds.curriculum_version_id"
                + " join curriculum c on c.id = cv.curriculum_id"
                + " join classifier_connect cc on cc.main_classifier_code = ?1 and cc.classifier_code = c.orig_study_level_code"
                + " where ds.directive_id = ?2 and ds.canceled = false")
                .setParameter(1, MainClassCode.LOPUBLANKETT.name())
                .setParameter(2, directiveId)
                .getResultList();
        return StreamUtil.toMappedList(r -> resultAsString(r, 0), result);
    }
    
    public List<StudentDto> diplomaStudents(HoisUserDetails user, DiplomaForm form) {
        Long directiveId = form.getDirectiveId();
        directive(user, directiveId);
        List<?> result = em.createNativeQuery("select s.id, p.firstname, p.lastname, p.idcode, p.birthdate"
                + " from directive_student ds"
                + " join curriculum_version cv on cv.id = ds.curriculum_version_id"
                + " join curriculum c on c.id = cv.curriculum_id"
                + " join student s on s.id = ds.student_id"
                + " join person p on p.id = s.person_id"
                + " where ds.directive_id = ?1 and ds.canceled = false"
                + " and c.orig_study_level_code in (select cc.classifier_code from classifier_connect cc"
                    + " where cc.main_classifier_code = ?2 and cc.connect_classifier_code = ?3)"
                + " and not exists (select 1 from diploma d where d.student_id = ds.student_id and d.status_code != ?4)"
                + " order by p.lastname, p.firstname")
                .setParameter(1, directiveId)
                .setParameter(2, MainClassCode.LOPUBLANKETT.name())
                .setParameter(3, form.getFormType())
                .setParameter(4, DocumentStatus.LOPUDOK_STAATUS_K.name())
                .getResultList();
        return StreamUtil.toMappedList(r -> {
            StudentDto dto = new StudentDto();
            dto.setId(resultAsLong(r, 0));
            dto.setFullname(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            dto.setIdcode(resultAsString(r, 3));
            dto.setBirthdate(resultAsLocalDate(r, 4));
            return dto;
        }, result);
    }

    public Page<SupplementStudentDto> supplementStudents(HoisUserDetails user, Boolean isHigher, 
            SupplementSearchForm criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive_student ds"
                + " join directive d on d.id = ds.directive_id"
                + " join diploma dip on dip.directive_id = ds.directive_id and dip.student_id = ds.student_id"
                + " join student s on s.id = ds.student_id"
                + " join person p on p.id = s.person_id"
                + " left join form f on f.id = dip.form_id"
                + " left join diploma_supplement sup on sup.diploma_id = dip.id").sort(pageable);
        directiveCriteria(qb, user, isHigher);
        qb.filter("ds.canceled = false");
        if (Boolean.TRUE.equals(isHigher)) {
            qb.requiredCriteria("(sup.id is null or sup.status_code = :supplementStatus"
                    + " or sup.status_en_code is null or sup.status_en_code = :supplementStatus)", 
                    "supplementStatus", DocumentStatus.LOPUDOK_STAATUS_K.name());
        } else {
            qb.requiredCriteria("(sup.id is null or sup.status_code = :supplementStatus)", 
                    "supplementStatus", DocumentStatus.LOPUDOK_STAATUS_K.name());
        }
        qb.optionalCriteria("d.id = :directiveId", "directiveId", criteria.getDirectiveId());
        qb.optionalCriteria("dip.status_code = :diplomaStatus", "diplomaStatus", criteria.getDiplomaStatus());
        qb.optionalCriteria("ds.student_id = :studentId", "studentId", criteria.getStudentId());
        qb.optionalCriteria("ds.curriculum_version_id = :curriculumVersionId", "curriculumVersionId", criteria.getCurriculumVersionId());
        Page<Object> result = JpaQueryUtil.pagingResult(qb, "ds.id, p.firstname, p.lastname"
                + ", f.full_code, dip.status_code as diploma_status, sup.status_code as supplement_status"
                + ", sup.status_en_code as supplement_status_en", em, pageable);
        return result.map(r -> {
            SupplementStudentDto dto = new SupplementStudentDto();
            dto.setDirectiveStudentId(resultAsLong(r, 0));
            dto.setFullname(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            dto.setDiplomaNr(resultAsString(r, 3));
            dto.setDiplomaStatus(resultAsString(r, 4));
            dto.setSupplementStatus(resultAsString(r, 5));
            dto.setSupplementStatusEn(resultAsString(r, 6));
            return dto;
        });
    }
    
    public List<FinalDocSignerDto> signers(HoisUserDetails user) {
        List<?> result = em.createNativeQuery("select fds.id, fds.name, fds.position, fds.is_first"
                + " from final_doc_signer fds"
                + " where fds.school_id = ?1 and fds.is_valid = true"
                + " order by fds.name, fds.position")
                .setParameter(1, user.getSchoolId())
                .getResultList();
        return StreamUtil.toMappedList(r -> {
            FinalDocSignerDto dto = new FinalDocSignerDto();
            dto.setId(resultAsLong(r, 0));
            dto.setName(resultAsString(r, 1));
            dto.setPosition(resultAsString(r, 2));
            dto.setIsFirst(resultAsBoolean(r, 3));
            dto.setIsValid(Boolean.TRUE);
            return dto;
        }, result);
    }

    public Page<DiplomaSearchDto> search(HoisUserDetails user, DiplomaSearchForm criteria, Pageable pageable) {
        Query supplementQuery = em.createNativeQuery("select f.full_code, dsf.is_english"
                + " from form f"
                + " join diploma_supplement_form dsf on dsf.form_id = f.id"
                + " where dsf.diploma_supplement_id = ?1 and f.status_code = ?2"
                + " order by f.numeral")
                .setParameter(2, FormStatus.LOPUBLANKETT_STAATUS_T.name());
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive_student ds"
                + " join directive d on d.id = ds.directive_id"
                + " join diploma dip on dip.directive_id = ds.directive_id and dip.student_id = ds.student_id"
                + " join student s on s.id = ds.student_id"
                + " join person p on p.id = s.person_id"
                + " join curriculum_version cv on cv.id = s.curriculum_version_id"
                + " join curriculum c on c.id = cv.curriculum_id"
                + " left join form dip_f on dip_f.id = dip.form_id"
                + " left join diploma_supplement sup on sup.diploma_id = dip.id").sort(pageable);
        qb.requiredCriteria("d.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.filter("ds.canceled = false");
        qb.optionalContains("p.firstname || ' ' || p.lastname", "name", criteria.getStudent());
        qb.optionalCriteria("c.id = :curriculum", "curriculum", criteria.getCurriculum());
        qb.optionalCriteria("d.confirm_date >= :from", "from", criteria.getFrom());
        qb.optionalCriteria("d.confirm_date <= :thru", "thru", criteria.getThru());
        return JpaQueryUtil.pagingResult(qb, "s.id as student_id, p.firstname, p.lastname, p.idcode, c.mer_code, c.name_et"
                + ", dip.type_code, dip.id as dip_id, dip.status_code as dip_status, dip_f.full_code"
                + ", sup.id as sup_id, sup.status_code as sup_status, sup.status_en_code as sup_status_en",
                em, pageable).map(r -> {
            DiplomaSearchDto dto = new DiplomaSearchDto();
            dto.setStudentId(resultAsLong(r, 0));
            dto.setFullname(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            dto.setFirstname(resultAsString(r, 1));
            dto.setLastname(resultAsString(r, 2));
            dto.setIdcode(resultAsString(r, 3));
            dto.setMerCode(resultAsString(r, 4));
            dto.setCurriculum(resultAsString(r, 5));
            dto.setType(resultAsString(r, 6));
            dto.setDiplomaId(resultAsLong(r, 7));
            dto.setDiplomaStatus(resultAsString(r, 8));
            dto.setDiplomaNr(resultAsString(r, 9));
            Long supplementId = resultAsLong(r, 10);
            if (supplementId != null) {
                dto.setSupplementId(supplementId);
                dto.setSupplementStatus(resultAsString(r, 11));
                dto.setSupplementStatusEn(resultAsString(r, 12));
                List<?> supplementResult = supplementQuery
                        .setParameter(1, supplementId)
                        .getResultList();
                dto.setSupplementNr(supplementResult.stream()
                        .filter(sr -> !Boolean.TRUE.equals(resultAsBoolean(sr, 1)))
                        .map(sr -> resultAsString(sr, 0)).collect(Collectors.joining(", ")));
                dto.setSupplementNrEn(supplementResult.stream()
                        .filter(sr -> Boolean.TRUE.equals(resultAsBoolean(sr, 1)))
                        .map(sr -> resultAsString(sr, 0)).collect(Collectors.joining(", ")));
            }
            return dto;
        });
    }
    
    public byte[] searchExcel(HoisUserDetails user, DiplomaSearchForm criteria) {
        Map<String, Object> data = new HashMap<>();
        data.put("criteria", criteria);
        data.put("documents", search(user, criteria, new PageRequest(0, Integer.MAX_VALUE)));
        return xlsService.generate("documents.xls", data);
    }

    public void createUpdateDiplomas(HoisUserDetails user, DiplomaForm form) {
        createUpdateDiplomas(user, form, Language.ET);
    }
    
    public void createUpdateDiplomas(HoisUserDetails user, DiplomaForm form, Language lang) {
        Directive directive = directive(user, form.getDirectiveId());
        if (Boolean.TRUE.equals(directive.getIsHigher())) {
            createUpdateDiplomasHigher(user, directive, form);
        } else {
            createUpdateDiplomasVocational(user, directive, form, lang);
        }
    }
    
    private void createUpdateDiplomasHigher(HoisUserDetails user, Directive directive, DiplomaForm form) {
        Long directiveId = form.getDirectiveId();
        School school = em.getReference(School.class, user.getSchoolId());
        String schoolNameEt = school.getNameEt();
        Classifier formType = em.getReference(Classifier.class, form.getFormType());
        Classifier status = em.getReference(Classifier.class, DocumentStatus.LOPUDOK_STAATUS_K.name());
        FinalDocSigner signer1 = em.getReference(FinalDocSigner.class, form.getSigner1Id());
        UserUtil.assertSameSchool(user, signer1.getSchool());
        String signer1Name = signer1.getName();
        String signer1Position = signer1.getPosition();
        FinalDocSigner signer2 = em.getReference(FinalDocSigner.class, form.getSigner2Id());
        UserUtil.assertSameSchool(user, signer2.getSchool());
        String signer2Name = signer2.getName();
        String signer2Position = signer2.getPosition();
        Map<Long, Diploma> existing = StreamUtil.toMap(d -> EntityUtil.getId(d.getStudent()), getDiplomas(form));
        List<?> result = em.createNativeQuery("select ds.student_id, upper(p.firstname) firstname, upper(p.lastname) lastname, p.idcode, p.birthdate"
                + ", ds.is_cum_laude, c.mer_code, c.name_et, level.extraval2, cg.name_et grade_name_et, cg.name_en grade_name_en"
                + " from directive_student ds"
                + " join curriculum_version cv on cv.id = ds.curriculum_version_id"
                + " join curriculum c on c.id = cv.curriculum_id"
                + " join classifier level on level.code = c.orig_study_level_code"
                + " join student s on s.id = ds.student_id"
                + " join person p on p.id = s.person_id"
                + " left join curriculum_grade cg on cg.id = ds.curriculum_grade_id"
                + " where ds.directive_id = ?1 and ds.student_id in ?2 and ds.canceled = false")
                .setParameter(1, directiveId)
                .setParameter(2, form.getStudentIds())
                .getResultList();
        for (Object r : result) {
            Long studentId = resultAsLong(r, 0);
            Diploma diploma = existing.get(studentId);
            if (diploma == null) {
                diploma = new Diploma();
            } else {
                AssertionFailedException.throwIf(!ClassifierUtil.equals(DocumentStatus.LOPUDOK_STAATUS_K, diploma.getStatus()), 
                        "diploma is already used");
            }
            diploma.setSchool(school);
            diploma.setFirstname(resultAsString(r, 1));
            diploma.setLastname(resultAsString(r, 2));
            diploma.setIdcode(resultAsString(r, 3));
            diploma.setBirthdate(resultAsLocalDate(r, 4));
            diploma.setIsCumLaude(resultAsBoolean(r, 5));
            String merCode = resultAsString(r, 6);
            if (merCode == null) {
                throw new ValidationFailedException("document.error.missingEhisCode");
            }
            diploma.setMerCode(merCode);
            diploma.setCurriculumNameEt(resultAsString(r, 7));
            diploma.setSchoolNameEt(schoolNameEt);
            diploma.setType(formType);
            diploma.setStatus(status);
            diploma.setStudent(em.getReference(Student.class, studentId));
            diploma.setDirective(directive);
            diploma.setLevel(resultAsString(r, 8));
            diploma.setSigner1Name(signer1Name);
            diploma.setSigner1Position(signer1Position);
            diploma.setSigner2Name(signer2Name);
            diploma.setSigner2Position(signer2Position);
            diploma.setCurriculumGradeNameEt(resultAsString(r, 9));
            diploma.setCurriculumGradeNameEn(resultAsString(r, 10));
            diploma.setCity(form.getCity());
            diploma.setIsOccupation(Boolean.FALSE);
            diploma.setIsPartoccupation(Boolean.FALSE);
            EntityUtil.save(diploma, em);
        }
    }
    
    private void createUpdateDiplomasVocational(HoisUserDetails user, Directive directive, DiplomaForm form, Language lang) {
        Long directiveId = form.getDirectiveId();
        School school = em.getReference(School.class, user.getSchoolId());
        String schoolNameGenitiveEt = school.getNameGenitiveEt();
        if (schoolNameGenitiveEt == null) {
            schoolNameGenitiveEt = school.getNameEt();
        }
        Classifier formType = em.getReference(Classifier.class, form.getFormType());
        Classifier status = em.getReference(Classifier.class, DocumentStatus.LOPUDOK_STAATUS_K.name());
        FinalDocSigner signer1 = em.getReference(FinalDocSigner.class, form.getSigner1Id());
        UserUtil.assertSameSchool(user, signer1.getSchool());
        String signer1Name = signer1.getName();
        String signer1Position = signer1.getPosition();
        FinalDocSigner signer2 = em.getReference(FinalDocSigner.class, form.getSigner2Id());
        UserUtil.assertSameSchool(user, signer2.getSchool());
        String signer2Name = signer2.getName();
        String signer2Position = signer2.getPosition();
        Map<Long, Diploma> existing = StreamUtil.toMap(d -> EntityUtil.getId(d.getStudent()), getDiplomas(form));
        Query occupationQuery = createOccupationQuery("occupation_code", directiveId);
        Query partOccupationQuery = createOccupationQuery("part_occupation_code", directiveId);
        List<?> result = em.createNativeQuery("select ds.student_id, upper(p.firstname) firstname, upper(p.lastname) lastname, p.idcode, p.birthdate"
                + ", ds.is_cum_laude, c.mer_code, c.name_et, ekr.value"
                + " from directive_student ds"
                + " join student s on s.id = ds.student_id"
                + " join person p on p.id = s.person_id"
                + " join curriculum_version cv on cv.id = s.curriculum_version_id"
                + " join curriculum c on c.id = cv.curriculum_id"
                + " join classifier_connect cc on cc.main_classifier_code = ?3 and cc.classifier_code = c.orig_study_level_code"
                + " join classifier ekr on ekr.code = cc.connect_classifier_code"
                + " where ds.directive_id = ?1 and ds.student_id in ?2 and ds.canceled = false")
                .setParameter(1, directiveId)
                .setParameter(2, form.getStudentIds())
                .setParameter(3, MainClassCode.EKR.name())
                .getResultList();
        for (Object r : result) {
            Long studentId = resultAsLong(r, 0);
            Diploma diploma = existing.get(studentId);
            if (diploma == null) {
                diploma = new Diploma();
            } else {
                AssertionFailedException.throwIf(!ClassifierUtil.equals(DocumentStatus.LOPUDOK_STAATUS_K, diploma.getStatus()), 
                        "diploma is already used");
            }
            diploma.setSchool(school);
            diploma.setFirstname(resultAsString(r, 1));
            diploma.setLastname(resultAsString(r, 2));
            diploma.setIdcode(resultAsString(r, 3));
            diploma.setBirthdate(resultAsLocalDate(r, 4));
            diploma.setIsCumLaude(resultAsBoolean(r, 5));
            String merCode = resultAsString(r, 6);
            if (merCode == null) {
                throw new ValidationFailedException("document.error.missingEhisCode");
            }
            diploma.setMerCode(merCode);
            diploma.setCurriculumNameEt(resultAsString(r, 7));
            diploma.setSchoolNameGenitiveEt(schoolNameGenitiveEt);
            diploma.setType(formType);
            diploma.setStatus(status);
            diploma.setStudent(em.getReference(Student.class, studentId));
            diploma.setDirective(directive);
            diploma.setLevel(getLevelText(formType.getValue(), resultAsString(r, 8), lang));
            diploma.setSigner1Name(signer1Name);
            diploma.setSigner1Position(signer1Position);
            diploma.setSigner2Name(signer2Name);
            diploma.setSigner2Position(signer2Position);
            List<?> occupationResult = occupationQuery
                    .setParameter(2, studentId)
                    .getResultList();
            diploma.setOccupationText(occupationResult.stream()
                    .map(or -> resultAsString(or, 0) + " (" + resultAsString(or, 1) + ")")
                    .collect(Collectors.joining(", ")));
            diploma.setIsOccupation(Boolean.valueOf(!diploma.getOccupationText().isEmpty()));
            List<?> partOccupationResult = partOccupationQuery
                    .setParameter(2, studentId)
                    .getResultList();
            diploma.setPartoccupationText(partOccupationResult.stream()
                    .map(or -> resultAsString(or, 0) + " (" + resultAsString(or, 1) + ")")
                    .collect(Collectors.joining(", ")));
            diploma.setIsPartoccupation(Boolean.valueOf(!diploma.getPartoccupationText().isEmpty()));
            EntityUtil.save(diploma, em);
        }
    }

    private Query createOccupationQuery(String certificateField, Long directiveId) {
        return em.createNativeQuery("select distinct c.name_et, soc.certificate_nr"
                + " from directive_student ds"
                + " join curriculum_version cv on cv.id = ds.curriculum_version_id"
                + " join curriculum_occupation co on co.curriculum_id = cv.curriculum_id"
                + " join directive_student_occupation dso on dso.directive_student_id = ds.id and dso.occupation_code = co.occupation_code"
                + " join classifier c on c.code = dso.occupation_code"
                + " join student_occupation_certificate soc on soc.student_id = ds.student_id and dso.occupation_code = soc." + certificateField
                    + " and coalesce(soc.valid_thru, cast('infinity' as date)) >= cast(now() as date)"
                + " where ds.directive_id = ?1 and ds.student_id = ?2 and ds.canceled = false and co.is_occupation_grant = true")
                .setParameter(1, directiveId);
    }
    
    private static String getLevelText(String formType, String ekr, Language lang) {
        String result = TranslateUtil.translate("diploma.level." + formType, lang);
        if ("K".equals(formType)) {
            result = TranslateUtil.translate("diploma.level." + ekr, lang) + " " + result;
        }
        return result;
    }
    
    public List<Diploma> getDiplomas(DiplomaBaseForm form) {
        return em.createQuery("select d from Diploma d"
                + " where d.directive.id = ?1 and d.type.code = ?2"
                + " and d.student.id in ?3"
                + " order by d.lastname, d.firstname", Diploma.class)
                .setParameter(1, form.getDirectiveId())
                .setParameter(2, form.getFormType())
                .setParameter(3, form.getStudentIds())
                .getResultList();
    }
    
    private static String getDiplomaTemplateName(Directive directive) {
        return Boolean.TRUE.equals(directive.getIsHigher()) ? DIPLOMA_TEMPLATE_NAME : DIPLOMA_VOCATIONAL_TEMPLATE_NAME;
    }

    public byte[] diplomaPrintView(HoisUserDetails user, DiplomaForm form) {
        Directive directive = directive(user, form.getDirectiveId());
        return pdfService.generate(getDiplomaTemplateName(directive), getDiplomas(form));
    }
    
    public List<FormDto> diplomaForms(HoisUserDetails user, String formType) {
        return getFreeFormDtos(user, FormType.valueOf(formType));
    }

    private List<Form> requireFreeForms(HoisUserDetails user, DiplomaForm form) {
        return requireFreeForms(user, FormType.valueOf(form.getFormType()), 
                form.getNumeral(), form.getStudentIds().size());
    }
    
    public List<FormDto> calculate(HoisUserDetails user, DiplomaForm form) {
        return StreamUtil.toMappedList(FormDto::of, requireFreeForms(user, form));
    }

    public byte[] diplomaPrint(HoisUserDetails user, DiplomaForm form) {
        Directive directive = directive(user, form.getDirectiveId());
        requireFreeForms(user, form);
        return pdfService.generate(getDiplomaTemplateName(directive), getDiplomas(form));
    }

    public byte[] viewDiplomaPdf(HoisUserDetails user, Long diplomaId) {
        Diploma diploma = diploma(user, diplomaId);
        return pdfService.generate(getDiplomaTemplateName(diploma.getDirective()), Collections.singletonList(diploma));
    }
    
    public void diplomaPrintConfirm(HoisUserDetails user, DiplomaConfirmForm confirmForm) {
        Directive directive = directive(user, confirmForm.getDirectiveId());
        AssertionFailedException.throwIf(!ClassifierUtil.equals(DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD, directive.getStatus()), 
                "Directive is not confirmed");
        List<Form> forms = requireFreeForms(user, FormType.valueOf(confirmForm.getFormType()), confirmForm.getNumerals());
        Classifier diplomaStatus = em.getReference(Classifier.class, DocumentStatus.LOPUDOK_STAATUS_T.name());
        Classifier formStatus = em.getReference(Classifier.class, FormStatus.LOPUBLANKETT_STAATUS_T.name());
        LocalDate now = LocalDate.now();
        Iterator<Form> iterator = forms.iterator();
        for (Diploma diploma : getDiplomas(confirmForm)) {
            AssertionFailedException.throwIf(!ClassifierUtil.equals(DocumentStatus.LOPUDOK_STAATUS_K, diploma.getStatus()), 
                    "diploma is already printed");
            Form form = iterator.next();
            AssertionFailedException.throwIf(!ClassifierUtil.equals(FormStatus.LOPUBLANKETT_STAATUS_K, form.getStatus()), 
                    "form is already used");
            form.setStatus(formStatus);
            form.setPrinted(now);
            EntityUtil.save(form, em);
            diploma.setForm(form);
            diploma.setStatus(diplomaStatus);
            EntityUtil.save(diploma, em);
        }
        AssertionFailedException.throwIf(iterator.hasNext(), "more forms than diplomas");
    }

    public void createUpdateSupplement(HoisUserDetails user, Long directiveStudentId, SupplementForm form) {
        DirectiveStudent directiveStudent = directiveStudent(user, directiveStudentId);
        if (Boolean.TRUE.equals(directiveStudent.getDirective().getIsHigher())) {
            createUpdateSupplementHigher(user, directiveStudent, form);
        } else {
            createUpdateSupplementVocational(user, directiveStudent, form);
        }
    }

    private void createUpdateSupplementHigher(HoisUserDetails user, DirectiveStudent directiveStudent, SupplementForm form) {
        boolean estonianFields = true;
        boolean englishFields = true;
        DiplomaSupplement supplement = getSupplement(directiveStudent);
        if (supplement == null) {
            supplement = new DiplomaSupplement();
        } else {
            boolean estonianPrinted = isPrinted(EntityUtil.getNullableCode(supplement.getStatus()));
            boolean englishPrinted = isPrinted(EntityUtil.getNullableCode(supplement.getStatusEn()));
            if (Language.EN.equals(form.getLang())) {
                AssertionFailedException.throwIf(englishPrinted, "supplement is already used");
            } else {
                AssertionFailedException.throwIf(estonianPrinted, "supplement is already used");
            }
            estonianFields = !estonianPrinted;
            englishFields = !englishPrinted;
        }
        Directive directive = directiveStudent.getDirective();
        Student student = directiveStudent.getStudent();
        Diploma diploma = em.createQuery("select d from Diploma d where d.directive = ?1 and d.student = ?2", 
                Diploma.class)
                .setParameter(1, directive)
                .setParameter(2, student)
                .getSingleResult();
        FinalDocSigner signer1 = em.getReference(FinalDocSigner.class, form.getSigner1Id());
        UserUtil.assertSameSchool(user, signer1.getSchool());
        FinalDocSigner signer2 = em.getReference(FinalDocSigner.class, form.getSigner2Id());
        UserUtil.assertSameSchool(user, signer2.getSchool());
        
        updateSupplementHigher(supplement, student, estonianFields, englishFields, form.getShowSubjectCode(), form.getShowTeacher(),
                directiveStudent.getCurriculumGrade());
        
        if (estonianFields && englishFields) {
            supplement.setDiploma(diploma);
            supplement.setSigner1Name(signer1.getName());
            supplement.setSigner2Name(signer2.getName());
        }
        if (estonianFields) {
            supplement.setSigner1Position(signer1.getPosition());
            supplement.setSigner2Position(signer2.getPosition());
        }
        if (englishFields) {
            supplement.setSigner1PositionEn(signer1.getPositionEn());
            supplement.setSigner2PositionEn(signer2.getPositionEn());
        }
        
        EntityUtil.save(supplement, em);
    }
    
    private void updateSupplementHigher(DiplomaSupplement supplement, Student student, 
            boolean estonianFields, boolean englishFields, Boolean showSubjectCode, Boolean showTeacher,
            CurriculumGrade curriculumGrade) {
        Long studentId = EntityUtil.getId(student);
        School school = student.getSchool();
        Person person = student.getPerson();
        Curriculum curriculum = student.getCurriculumVersion().getCurriculum();
        Classifier studyLoad = student.getStudyLoad();
        Classifier studyLanguage = student.getLanguage();
        if (studyLanguage == null) {
            studyLanguage = em.getReference(Classifier.class, StudyLanguage.OPPEKEEL_E.name());
        }
        StudentCurriculumCompletion completion = getStudentCurriculumCompletion(student);
        Classifier status = em.getReference(Classifier.class, DocumentStatus.LOPUDOK_STAATUS_K.name());
        if (estonianFields && englishFields) {
            supplement.setSchoolNameEt(school.getNameEt());
            supplement.setStudent(student);
            supplement.setFirstname(person.getFirstname());
            supplement.setLastname(person.getLastname());
            supplement.setIdcode(person.getIdcode());
            supplement.setBirthdate(person.getBirthdate());
            supplement.setMerCode(curriculum.getMerCode());
            supplement.setCredits(curriculum.getCredits());
            supplement.setCurriculumMerRegDate(curriculum.getMerRegDate());
            if (completion != null) {
                supplement.setAverageMark(completion.getAverageMark());
            }
            supplement.setStudyPeriod(curriculum.getStudyPeriod());
            supplement.setPrinted(LocalDate.now());
            setStudyResultsHigher(supplement, studentId);
        }
        if (estonianFields) {
            supplement.setCurriculumNameEt(curriculum.getNameEt());
            supplement.setStudyLanguageNameEt(toNullableLowerCase(studyLanguage.getNameEt()));
            supplement.setSchoolType(school.getFinalSchoolType());
            if (studyLoad != null) {
                supplement.setStudyLoadNameEt(toNullableLowerCase(studyLoad.getNameEt()));
            }
            supplement.setOutcomesEt(curriculum.getOutcomesEt());
            supplement.setCurriculumCompletion(getCurriculumCompletion(student.getCurriculumVersion(), Language.ET));
            if (PROFESSIONAL_DIPLOMA_STUDY_LEVEL.equals(EntityUtil.getCode(curriculum.getOrigStudyLevel()))) {
                supplement.setFinal21(TranslateUtil.translate(PROFESSIONAL_DIPLOMA_KEY, Language.ET));
            } else {
                if (curriculumGrade != null) {
                    supplement.setFinal21(curriculumGrade.getNameEt());
                }
            }
            supplement.setFinal31(curriculum.getFinal31());
            supplement.setFinal33(curriculum.getFinal33());
            supplement.setFinal51(curriculum.getFinal51());
            supplement.setFinal52(curriculum.getFinal52());
            supplement.setFinal61(curriculum.getFinal61());
            supplement.setFinal62(school.getFinal62());
            supplement.setStatus(status);
            supplement.setPgNrEt(Integer.valueOf(pdfService.getPageCount(SUPPLEMENT_TEMPLATE_NAME, 
                    getViewReport(supplement, showSubjectCode, showTeacher,
                            isSchoolUsingLetterGrades(student), Language.ET),
                    Language.ET)));
        }
        if (englishFields) {
            supplement.setSchoolNameEn(school.getNameEn());
            supplement.setCurriculumNameEn(curriculum.getNameEn());
            supplement.setStudyLanguageNameEn(studyLanguage.getNameEn());
            supplement.setSchoolTypeEn(school.getFinalSchoolTypeEn());
            if (studyLoad != null) {
                supplement.setStudyLoadNameEn(toNullableLowerCase(studyLoad.getNameEn()));
            }
            supplement.setOutcomesEn(curriculum.getOutcomesEn());
            supplement.setCurriculumCompletionEn(getCurriculumCompletion(student.getCurriculumVersion(), Language.EN));
            if (PROFESSIONAL_DIPLOMA_STUDY_LEVEL.equals(EntityUtil.getCode(curriculum.getOrigStudyLevel()))) {
                supplement.setFinalEn21(TranslateUtil.translate(PROFESSIONAL_DIPLOMA_KEY, Language.ET) + "\n" +
                        TranslateUtil.translate(PROFESSIONAL_DIPLOMA_KEY, Language.EN));
            } else {
                if (curriculumGrade != null) {
                    supplement.setFinalEn21(curriculumGrade.getNameEt() + "\n" + curriculumGrade.getNameEn());
                }
            }
            supplement.setFinalEn31(curriculum.getFinalEn31());
            supplement.setFinalEn33(curriculum.getFinalEn33());
            supplement.setFinalEn51(curriculum.getFinalEn51());
            supplement.setFinalEn52(curriculum.getFinalEn52());
            supplement.setFinalEn61(curriculum.getFinalEn61());
            supplement.setFinalEn62(school.getFinalEn62());
            supplement.setStatusEn(status);
            supplement.setPgNrEn(Integer.valueOf(pdfService.getPageCount(SUPPLEMENT_EN_TEMPLATE_NAME, 
                    getViewReport(supplement, showSubjectCode, showTeacher,
                            isSchoolUsingLetterGrades(student), Language.EN),
                    Language.EN)));
        }
    }

    private StudentCurriculumCompletion getStudentCurriculumCompletion(Student student) {
        List<StudentCurriculumCompletion> result = em.createQuery("select scc from StudentCurriculumCompletion scc where scc.student = ?1",
                StudentCurriculumCompletion.class)
                .setParameter(1, student)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
    
    private static String getCurriculumCompletion(CurriculumVersion curriculumVersion, Language lang) {
        Map<String, BigDecimal> moduleCredits = new HashMap<>();
        for (CurriculumVersionHigherModule module : curriculumVersion.getModules()) {
            if (!Boolean.TRUE.equals(module.getMinorSpeciality())) {
                Classifier type = module.getType();
                String name = ClassifierUtil.equals(HigherModuleType.KORGMOODUL_M, type) ?
                        getModuleName(module.getTypeNameEt(), module.getTypeNameEn(), lang) :
                            getModuleName(type.getNameEt(), type.getNameEn(), lang);
                moduleCredits.put(name, moduleCredits.computeIfAbsent(name, k -> BigDecimal.valueOf(0))
                        .add(module.getTotalCredits()));
            }
        }
        return moduleCredits.entrySet().stream()
                .map(e -> e.getKey() + " " + e.getValue() + " EAP")
                .collect(Collectors.joining(", "));
    }
    
    private static String getModuleName(String nameEt, String nameEn, Language lang) {
        return Language.EN.equals(lang) ? StringUtils.defaultIfEmpty(nameEn, nameEt) : nameEt;
    }

    private void setStudyResultsHigher(DiplomaSupplement supplement, Long studentId) {
        List<?> result = em.createNativeQuery("SELECT sv.subject_name_et,sv.subject_name_en,sv.credits,sv.grade,lower(clf.name_et) as grade_name_et,lower(clf.name_en) as grade_name_en,sv.teachers,"
                + " sv.grade_date,sv.subject_code,"
                + " case when (select count (*) from apel_application_formal_subject_or_module aaf where sv.apel_application_record_id=aaf.apel_application_record_id and not aaf.is_my_school) > 0 then true else false end as is_apel_formal,"
                + " case when (select count (*) from apel_application_informal_subject_or_module aaf where sv.apel_application_record_id=aaf.apel_application_record_id) > 0 then true else false end as is_apel_informal,"
                + " aps.name_et as aps_name_et,aps.name_en as aps_name_en, apc.code as apc_code,apc.name_et as apc_name_et,apc.name_en as apc_name_en,"
                + " case when pp.is_final=true then true else false end as is_final,"
                + " case when pp.is_final_thesis=true then true else false end as is_final_thesis,"
                + " ft.theme_et,ft.theme_en"
                + " from student_higher_result sv"
                + " join ("
                + " select coalesce(svm.curriculum_version_hmodule_id,sv.curriculum_version_hmodule_id) as curriculum_version_hmodule_id, "
                + "   first_value(sv.id) over(partition by student_id, coalesce(svm.curriculum_version_hmodule_id,sv.curriculum_version_hmodule_id) order by sv.grade_date desc,sv.grade desc) as id"
                + " from student_higher_result sv"
                + " left join student_higher_result_module svm on sv.id=svm.student_higher_result_id"
                + " where sv.student_id=?1 and sv.is_active = true and grade_code in ('KORGHINDAMINE_A','KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5')"
                + " and (svm.student_higher_result_id is not null)"
                + " union"
                + " select distinct 0, sv.id"
                + " from student_higher_result sv"
                + " left join student_higher_result_module svm on sv.id=svm.student_higher_result_id"
                + " where sv.student_id=?1 and sv.is_active = true and grade_code in ('KORGHINDAMINE_A','KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5')"
                + " and svm.student_higher_result_id is null) x on x.id=sv.id"
                + " join classifier clf on clf.code=sv.grade_code"
                + " left join (apel_school aps join classifier apc on apc.code = aps.country_code) on sv.apel_school_id=aps.id"
                + " left join final_thesis ft on ft.student_id = sv.student_id"
                + " left join (protocol_student ps join protocol pp on ps.protocol_id=pp.id) on ps.id=sv.protocol_student_id"
                + " order by sv.grade_date asc")
                .setParameter(1, studentId)
                .getResultList();
        List<DiplomaSupplementStudyResult> studyResults = supplement.getStudyResults();
        if (studyResults == null) {
            supplement.setStudyResults(studyResults = new ArrayList<>());
        } else {
            studyResults.clear();
        }
        studyResults.addAll(StreamUtil.toMappedList(r -> {
            DiplomaSupplementStudyResult studyResult = new DiplomaSupplementStudyResult();
            studyResult.setDiplomaSupplement(supplement);
            studyResult.setNameEt(resultAsString(r, 0));
            studyResult.setNameEn(resultAsString(r, 1));
            studyResult.setCredits(resultAsDecimal(r, 2));
            studyResult.setGrade(resultAsString(r, 3));
            studyResult.setGradeNameEt(resultAsString(r, 4));
            studyResult.setGradeNameEn(resultAsString(r, 5));
            studyResult.setTeacher(resultAsString(r, 6));
            studyResult.setGradeDate(resultAsLocalDate(r, 7));
            studyResult.setSubjectCode(resultAsString(r, 8));
            Boolean isApelFormal = resultAsBoolean(r, 9);
            studyResult.setIsApelFormal(isApelFormal);
            studyResult.setIsApelInformal(resultAsBoolean(r, 10));
            if (Boolean.TRUE.equals(isApelFormal)) {
                String apelSchoolNameEt = resultAsString(r, 11);
                String apelSchoolNameEn = resultAsString(r, 12);
                // school name should be in the original language, in its absences in English
                if (ClassifierUtil.COUNTRY_ESTONIA.equals(resultAsString(r, 13))) {
                    studyResult.setApelSchoolNameEt(apelSchoolNameEt);
                    studyResult.setApelSchoolNameEn(apelSchoolNameEt);
                } else {
                    studyResult.setApelSchoolNameEt(apelSchoolNameEn + " (" + resultAsString(r, 14) + ")");
                    studyResult.setApelSchoolNameEn(apelSchoolNameEn + " (" + resultAsString(r, 15) + ")");
                }
            }
            studyResult.setIsFinal(resultAsBoolean(r, 16));
            Boolean isFinalThesis = resultAsBoolean(r, 17);
            studyResult.setIsFinalThesis(isFinalThesis);
            if (Boolean.TRUE.equals(isFinalThesis)) {
                studyResult.setNameEt(resultAsString(r, 18));
                studyResult.setNameEn(resultAsString(r, 19));
            }
            return studyResult;
        }, result));
    }

    private void createUpdateSupplementVocational(HoisUserDetails user, DirectiveStudent directiveStudent, SupplementForm form) {
        DiplomaSupplement supplement = getSupplement(directiveStudent);
        if (supplement == null) {
            supplement = new DiplomaSupplement();
        } else {
            AssertionFailedException.throwIf(!ClassifierUtil.equals(DocumentStatus.LOPUDOK_STAATUS_K, supplement.getStatus()), 
                    "supplement is already used");
        }
        Directive directive = directiveStudent.getDirective();
        Student student = directiveStudent.getStudent();
        Diploma diploma = em.createQuery("select d from Diploma d where d.directive = ?1 and d.student = ?2", 
                Diploma.class)
                .setParameter(1, directive)
                .setParameter(2, student)
                .getSingleResult();
        FinalDocSigner signer1 = em.getReference(FinalDocSigner.class, form.getSigner1Id());
        UserUtil.assertSameSchool(user, signer1.getSchool());
        supplement.setDiploma(diploma);
        
        updateSupplementVocational(supplement, student);
        
        supplement.setSigner1Name(signer1.getName());
        supplement.setSigner1Position(signer1.getPosition());
        
        EntityUtil.save(supplement, em);
    }
    
    private void updateSupplementVocational(DiplomaSupplement supplement, Student student) {
        Long studentId = EntityUtil.getId(student);
        School school = student.getSchool();
        Person person = student.getPerson();
        Curriculum curriculum = student.getCurriculumVersion().getCurriculum();
        Classifier origStudyLevel = curriculum.getOrigStudyLevel();
        Optional<ClassifierConnect> ekr = origStudyLevel.getClassifierConnects().stream()
                .filter(c -> MainClassCode.EKR.name().equals(c.getMainClassifierCode()))
                .findAny();
        Classifier studyForm = student.getStudyForm();
        Classifier studyLanguage = student.getLanguage();
        if (studyLanguage == null) {
            studyLanguage = em.getReference(Classifier.class, StudyLanguage.OPPEKEEL_E.name());
        }
        Classifier status = em.getReference(Classifier.class, DocumentStatus.LOPUDOK_STAATUS_K.name());
        
        supplement.setStudent(student);
        supplement.setSchoolNameEt(school.getNameEt());
        supplement.setSchoolNameEn(school.getNameEn());
        supplement.setFirstname(toNullableUpperCase(person.getFirstname()));
        supplement.setLastname(toNullableUpperCase(person.getLastname()));
        supplement.setIdcode(person.getIdcode());
        supplement.setBirthdate(person.getBirthdate());
        supplement.setCurriculumNameEt(curriculum.getNameEt());
        supplement.setCurriculumNameEn(curriculum.getNameEn());
        supplement.setMerCode(curriculum.getMerCode());
        if (ekr.isPresent()) {
            supplement.setEkr(ekr.get().getConnectClassifier().getValue());
        }
        supplement.setCredits(curriculum.getCredits());
        supplement.setVocationalCurriculumType(toNullableLowerCase(origStudyLevel.getExtraval1()));
        supplement.setStudyPeriod(curriculum.getStudyPeriod());
        if (studyForm != null) {
            supplement.setStudyFormNameEt(toNullableLowerCase(studyForm.getNameEt()));
            supplement.setStudyFormNameEn(toNullableLowerCase(studyForm.getNameEn()));
            if (ClassifierUtil.equals(StudyForm.OPPEVORM_W, studyForm)) {
                supplement.setStudyCompany(student.getStudyCompany());
            }
        }
        supplement.setStudyLanguageNameEt(toNullableLowerCase(studyLanguage.getNameEt()));
        supplement.setStudyLanguageNameEn(toNullableLowerCase(studyLanguage.getNameEn()));
        supplement.setOutcomesEt(curriculum.getOutcomesEt());
        supplement.setStatus(status);
        
        setStudyResultsVocational(supplement, studentId);
        
        supplement.setPgNrEt(Integer.valueOf(pdfService.getPageCount(SUPPLEMENT_VOCATIONAL_TEMPLATE_NAME, getViewReport(supplement))));
    }

    public Map<Long, List<DiplomaSupplementStudyResult>> getStudyResultsVocational(List<Long> studentIds) {
        List<?> result = em.createNativeQuery("SELECT sv.student_id, sv.module_name_et,sv.module_name_en,sv.credits,sv.grade,lower(clf.name_et) as grade_name_et,lower(clf.name_en) as grade_name_en, sv.teachers, sv.grade_date,"
                + " case when (select count (*) from apel_application_formal_subject_or_module aaf where sv.apel_application_record_id=aaf.apel_application_record_id and not aaf.is_my_school) > 0 then true else false end as is_apel_formal,"
                + " case when (select count (*) from apel_application_informal_subject_or_module aaf where sv.apel_application_record_id=aaf.apel_application_record_id) > 0 then true else false end as is_apel_informal,"
                + " aps.name_et,aps.name_en,case when pp.is_final=true then true else false end as is_final,"
                + " occup.name_et as occup_name_et, partoccup.name_et as partoccup_name_et, speciality.name_et as speciality_name_et"
                + " from student_vocational_result sv"
                + " join ("
                + " select coalesce(svm.curriculum_version_omodule_id,sv.curriculum_version_omodule_id) as curriculum_version_omodule_id, "
                + "   first_value(sv.id) over(partition by student_id, coalesce(svm.curriculum_version_omodule_id,sv.curriculum_version_omodule_id) order by sv.grade_date desc,sv.grade desc) as id,"
                + " cmm.module_code as md"
                + " from student_vocational_result sv"
                + " left join student_vocational_result_omodule svm on sv.id=svm.student_vocational_result_id"
                + " left join curriculum_version_omodule cm on cm.id=coalesce(svm.curriculum_version_omodule_id,sv.curriculum_version_omodule_id)" 
                + " left join curriculum_module cmm on cm.curriculum_module_id=cmm.id" 
                + " where sv.student_id in (?1) and grade in ('A','3','4','5') and (svm.student_vocational_result_id is not null or sv.arr_modules is null)"
                + " union"
                + " select distinct 0, sv.id, cmm.module_code"
                + " from student_vocational_result sv"
                + " left join student_vocational_result_omodule svm on sv.id=svm.student_vocational_result_id"
                + " join curriculum_version_omodule cm on cm.id=any(sv.arr_modules)"
                + " join curriculum_module cmm on cm.curriculum_module_id=cmm.id"
                + " where sv.student_id in (?1) and grade in ('A','3','4','5') and sv.arr_modules is not null and svm.student_vocational_result_id is null) x on x.id=sv.id"
                + " join classifier clf on clf.code=sv.grade_code"
                + " left join apel_school aps on sv.apel_school_id=aps.id"
                + " left join (protocol_student ps join protocol pp on ps.protocol_id=pp.id)  on ps.id=sv.protocol_student_id"
                + " left join (protocol_student_occupation pso join student_occupation_certificate soc on pso.student_occupation_certificate_id = soc.id) on pso.protocol_student_id = ps.id"
                + " left join classifier occup on occup.code = soc.occupation_code"
                + " left join classifier partoccup on partoccup.code = soc.part_occupation_code"
                + " left join classifier speciality on speciality.code = soc.speciality_code"
                + " order by case coalesce(x.md,'KUTSEMOODUL_V') when 'KUTSEMOODUL_P' then 1  when 'KUTSEMOODUL_Y' then 2  when 'KUTSEMOODUL_V' then 3  when 'KUTSEMOODUL_L' then 4 else 3 end, "
                + " upper(sv.module_name_et), sv.grade_date asc")
                .setParameter(1, studentIds)
                .getResultList();

        return StreamUtil.nullSafeList(result).stream()
                .collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> {
                    DiplomaSupplementStudyResult studyResult = new DiplomaSupplementStudyResult();
                    studyResult.setNameEt(resultAsString(r, 1));
                    studyResult.setNameEn(resultAsString(r, 2));
                    studyResult.setCredits(resultAsDecimal(r, 3));
                    studyResult.setGrade(resultAsString(r, 4));
                    studyResult.setGradeNameEt(resultAsString(r, 5));
                    studyResult.setGradeNameEn(resultAsString(r, 6));
                    studyResult.setTeacher(resultAsString(r, 7));
                    studyResult.setGradeDate(resultAsLocalDate(r, 8));
                    studyResult.setIsApelFormal(resultAsBoolean(r, 9));
                    studyResult.setIsApelInformal(resultAsBoolean(r, 10));
                    studyResult.setApelSchoolNameEt(resultAsString(r, 11));
                    studyResult.setApelSchoolNameEn(resultAsString(r, 12));
                    Boolean isFinal = resultAsBoolean(r, 13);
                    studyResult.setIsFinal(isFinal);
                    if (Boolean.TRUE.equals(isFinal)) {
                        String occupation = resultAsString(r, 14);
                        String partoccupation = resultAsString(r, 15);
                        StringBuilder nameBuilder = new StringBuilder();
                        if (!StringUtils.isEmpty(partoccupation)) {
                            nameBuilder.append(TranslateUtil.translate(EXAM_PARTOCCUPATION_KEY, Language.ET))
                                    .append(" - ").append(partoccupation);
                        } else if (!StringUtils.isEmpty(occupation)) {
                            nameBuilder.append(TranslateUtil.translate(EXAM_OCCUPATION_KEY, Language.ET)).append(" - ")
                                    .append(occupation);
                        }
                        if (nameBuilder.length() != 0) {
                            String speciality = resultAsString(r, 16);
                            if (!StringUtils.isEmpty(speciality)) {
                                nameBuilder.append(", ").append(speciality);
                            }
                            studyResult.setNameEt(nameBuilder.toString());
                        }
                    }
                    return studyResult;
                }, Collectors.toList())));
    }

    private void setStudyResultsVocational(DiplomaSupplement supplement, Long studentId) {
        Map<Long, List<DiplomaSupplementStudyResult>> vocationalResults = getStudyResultsVocational(
                Arrays.asList(studentId));
        List<DiplomaSupplementStudyResult> studentVocationalResults = vocationalResults.containsKey(studentId)
                ? vocationalResults.get(studentId)
                : new ArrayList<>();

        List<DiplomaSupplementStudyResult> studyResults = supplement.getStudyResults();
        if (studyResults == null) {
            supplement.setStudyResults(studyResults = new ArrayList<>());
        } else {
            studyResults.clear();
        }

        studyResults.addAll(StreamUtil.toMappedList(r -> {
            r.setDiplomaSupplement(supplement);
            return r;
        }, studentVocationalResults));
    }

    private DiplomaSupplement getSupplement(DirectiveStudent directiveStudent) {
        List<DiplomaSupplement> result = em.createQuery("select sup from DiplomaSupplement sup"
                + " where sup.student.id = ?1 and sup.diploma.directive.id = ?2", DiplomaSupplement.class)
                .setParameter(1, EntityUtil.getId(directiveStudent.getStudent()))
                .setParameter(2, EntityUtil.getId(directiveStudent.getDirective()))
                .setMaxResults(1)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public SupplementDto supplementDto(HoisUserDetails user, Long directiveStudentId) {
        DirectiveStudent directiveStudent = directiveStudent(user, directiveStudentId);
        Object r = em.createNativeQuery("select s.id, p.firstname, p.lastname, dip.status_code as diploma_status, f.full_code"
                + ", sup.id as sup_id, sup.status_code as supplement_status, sup.status_en_code as supplement_status_en"
                + " from directive_student ds"
                + " join directive d on d.id = ds.directive_id"
                + " join diploma dip on dip.directive_id = ds.directive_id and dip.student_id = ds.student_id"
                + " join student s on s.id = ds.student_id"
                + " join person p on p.id = s.person_id"
                + " left join form f on f.id = dip.form_id"
                + " left join diploma_supplement sup on sup.diploma_id = dip.id"
                + " where d.school_id = ?1 and ds.id = ?2 and ds.canceled = false")
                .setParameter(1, user.getSchoolId())
                .setParameter(2, directiveStudentId)
                .getSingleResult();
        SupplementDto dto = new SupplementDto();
        dto.setFullname(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
        dto.setDiplomaStatus(resultAsString(r, 3));
        dto.setDiplomaNr(resultAsString(r, 4));
        Long supplementId = resultAsLong(r, 5);
        String supplementStatusCode = resultAsString(r, 6);
        dto.setSupplementStatus(supplementStatusCode);
        String supplementStatusEnCode = resultAsString(r, 7);
        dto.setSupplementStatusEn(supplementStatusEnCode);
        FormType formType = getSupplementFormType(directiveStudent, Language.ET);
        FormType extraFormType = getSupplementExtraFormType(directiveStudent);
        dto.setForms(isPrinted(supplementStatusCode) ? 
                StreamUtil.toMappedList(FormDto::of, getSupplementForms(supplementId, formType, extraFormType)) : 
                    getFreeFormDtos(user, formType));
        if (Boolean.TRUE.equals(directiveStudent.getDirective().getIsHigher())) {
            dto.setFormsEn(isPrinted(supplementStatusEnCode) ? 
                    StreamUtil.toMappedList(FormDto::of, getSupplementForms(supplementId, FormType.LOPUBLANKETT_DS, FormType.LOPUBLANKETT_S)) : 
                        getFreeFormDtos(user, FormType.LOPUBLANKETT_DS));
        }
        dto.setFreeExtraForms(getFreeFormDtos(user, extraFormType));
        return dto;
    }

    private static boolean isPrinted(String supplementStatusCode) {
        return supplementStatusCode != null && !DocumentStatus.LOPUDOK_STAATUS_K.name().equals(supplementStatusCode);
    }

    private List<Form> getSupplementForms(Long supplementId, FormType formType) {
        return em.createQuery("select dsf.form from DiplomaSupplementForm dsf"
                + " where dsf.diplomaSupplement.id = ?1 and dsf.form.type.code = ?2 and dsf.form.status.code = ?3"
                + " order by dsf.form.numeral", Form.class)
                .setParameter(1, supplementId)
                .setParameter(2, formType.name())
                .setParameter(3, FormStatus.LOPUBLANKETT_STAATUS_T.name())
                .getResultList();
    }

    private List<Form> getSupplementForms(Long supplementId, FormType formType, FormType extraFormType) {
        return Stream.concat(getSupplementForms(supplementId, formType).stream(), 
                getSupplementForms(supplementId, extraFormType).stream())
                .collect(Collectors.toList());
    }

    private static String getSupplementTemplateName(Boolean isHigher, Language lang) {
        return Boolean.TRUE.equals(isHigher) ? 
                (Language.EN.equals(lang) ? SUPPLEMENT_EN_TEMPLATE_NAME : SUPPLEMENT_TEMPLATE_NAME) : SUPPLEMENT_VOCATIONAL_TEMPLATE_NAME;
    }

    private static String getSupplementTemplateName(Directive directive, Language lang) {
        return getSupplementTemplateName(directive.getIsHigher(), lang);
    }

    private static String getSupplementTemplateName(DirectiveStudent directiveStudent, Language lang) {
        return getSupplementTemplateName(directiveStudent.getDirective(), lang);
    }

    private static FormType getSupplementFormType(DirectiveStudent directiveStudent, Language lang) {
        return Boolean.TRUE.equals(directiveStudent.getDirective().getIsHigher()) ? 
                (Language.EN.equals(lang) ? FormType.LOPUBLANKETT_DS : FormType.LOPUBLANKETT_R) : FormType.LOPUBLANKETT_HIN;
    }

    private static FormType getSupplementExtraFormType(Directive directive) {
        return Boolean.TRUE.equals(directive.getIsHigher()) ? 
                FormType.LOPUBLANKETT_S : FormType.LOPUBLANKETT_HINL;
    }

    private static FormType getSupplementExtraFormType(DirectiveStudent directiveStudent) {
        return getSupplementExtraFormType(directiveStudent.getDirective());
    }

    public byte[] supplementPrintView(HoisUserDetails user, Long directiveStudentId, SupplementForm form) {
        DirectiveStudent directiveStudent = directiveStudent(user, directiveStudentId);
        return pdfService.generate(getSupplementTemplateName(directiveStudent, form.getLang()), 
                getViewReport(getSupplement(directiveStudent), form.getShowSubjectCode(), form.getShowTeacher(),
                        isSchoolUsingLetterGrades(directiveStudent.getStudent()), form.getLang()), form.getLang());
    }
    
    public byte[] supplementPreview(HoisUserDetails user, Long studentId, Boolean isHigher, Language language) {
        Student student = student(user, studentId);
        Language lang = language == null ? Language.ET : language;
        DiplomaSupplement supplement = new DiplomaSupplement();
        updateSupplement(supplement, student, isHigher);
        return pdfService.generate(getSupplementTemplateName(isHigher, lang), 
                getViewReport(supplement, Boolean.TRUE, Boolean.TRUE,
                        isSchoolUsingLetterGrades(student), lang), lang);
    }
    
    private void updateSupplement(DiplomaSupplement supplement, Student student, Boolean isHigher) {
        if (Boolean.TRUE.equals(isHigher)) {
            updateSupplementHigher(supplement, student, true, true, Boolean.TRUE, Boolean.TRUE, null);
        } else {
            updateSupplementVocational(supplement, student);
        }
    }

    private DiplomaSupplementReport getViewReport(DiplomaSupplement supplement) {
        DiplomaSupplementReport report = new DiplomaSupplementReport(supplement, Collections.singletonList("XXXXXX"));
        setReportResults(report, supplement.getStudyResults(), Boolean.FALSE, Boolean.FALSE, Language.ET);
        return report;
    }

    private DiplomaSupplementReport getViewReport(DiplomaSupplement supplement, 
            Boolean showSubjectCode, Boolean showTeacher, Boolean isLetterGrades, Language lang) {
        DiplomaSupplementReport report = new DiplomaSupplementReport(supplement, Collections.singletonList("XXXXXX"),
                showSubjectCode, showTeacher, isLetterGrades, lang);
        setReportResults(report, supplement.getStudyResults(), showSubjectCode, showTeacher, lang);
        return report;
    }

    private void setReportResults(DiplomaSupplementReport report, List<DiplomaSupplementStudyResult> studyResults,
            Boolean showSubjectCode, Boolean showTeacher, Language lang) {
        DiplomaSupplementResultReport results = getReportResults(studyResults, showSubjectCode, showTeacher, lang);
        report.setApels(results.getApels());
        report.setFinalResults(results.getFinalResults());
        report.setFinalThesis(results.getFinalThesis());
        report.setStudyResults(results.getStudyResults());
        report.setTotalCredits(results.getTotalCredits());
    }

    public DiplomaSupplementResultReport getReportResults(List<DiplomaSupplementStudyResult> studyResults,
            Boolean showSubjectCode, Boolean showTeacher, Language lang) {
        DiplomaSupplementResultReport resultReport = new DiplomaSupplementResultReport();

        Map<String, Integer> apelFormal = new HashMap<>();
        Map<String, Integer> apelInformal = new HashMap<>();
        Supplier<Integer> numberSupplier = () -> Integer.valueOf(apelFormal.size() + apelInformal.size() + 1);
        for (DiplomaSupplementStudyResult studyResult : studyResults) {
            resultReport.setTotalCredits(resultReport.getTotalCredits().add(studyResult.getCredits()));
            StudyResultItem resultItem = new StudyResultItem();
            if (Boolean.TRUE.equals(showSubjectCode)) {
                resultItem.setSubjectCode(studyResult.getSubjectCode());
            }
            resultItem.setName(Language.EN.equals(lang) ? studyResult.getNameEn() : studyResult.getNameEt());
            resultItem.setCredits(studyResult.getCredits());
            resultItem.setGrade(studyResult.getGrade());
            resultItem.setGradeName(
                    Language.EN.equals(lang) ? studyResult.getGradeNameEn() : studyResult.getGradeNameEt());
            resultItem.setGradeDate(studyResult.getGradeDate());
            if (Boolean.TRUE.equals(showTeacher)) {
                resultItem.setTeacher(studyResult.getTeacher());
            }
            if (Boolean.TRUE.equals(studyResult.getIsFinal())) {
                if (Boolean.TRUE.equals(studyResult.getIsFinalThesis())) {
                    resultReport.getFinalThesis().add(resultItem);
                } else {
                    resultReport.getFinalResults().add(resultItem);
                }
                continue;
            }
            if (Boolean.TRUE.equals(studyResult.getIsApelFormal())) {
                addApel(resultReport, resultItem, studyResult, Boolean.TRUE, apelFormal, numberSupplier, lang);
            }
            if (Boolean.TRUE.equals(studyResult.getIsApelInformal())) {
                addApel(resultReport, resultItem, studyResult, Boolean.FALSE, apelInformal, numberSupplier, lang);
            }
            resultReport.getStudyResults().add(resultItem);
        }
        return resultReport;
    }

    private static void addApel(DiplomaSupplementResultReport resultReport, StudyResultItem resultItem,
            DiplomaSupplementStudyResult studyResult, Boolean isFormal, Map<String, Integer> map,
            Supplier<Integer> numberSupplier, Language lang) {
        String apelSchoolName = Language.EN.equals(lang) ? studyResult.getApelSchoolNameEn()
                : studyResult.getApelSchoolNameEt();
        Integer number = map.get(apelSchoolName);
        if (number == null) {
            map.put(apelSchoolName, number = numberSupplier.get());
            ApelResultItem apelItem = new ApelResultItem();
            apelItem.setName(apelSchoolName);
            apelItem.setIsFormal(isFormal);
            resultReport.getApels().add(apelItem);
        }
        resultItem.setName((Language.EN.equals(lang) ? studyResult.getNameEn() : studyResult.getNameEt()) + " *" + number);
    }

    private List<Form> getFreeForms(HoisUserDetails user, FormType formType, Long start, int max) {
        return em.createQuery("select f from Form f"
                + " where f.school.id = ?1 and f.type.code = ?2 and f.status.code = ?3"
                + " and f.numeral >= ?4"
                + " order by numeral", Form.class)
                .setParameter(1, user.getSchoolId())
                .setParameter(2, formType.name())
                .setParameter(3, FormStatus.LOPUBLANKETT_STAATUS_K.name())
                .setParameter(4, start)
                .setMaxResults(max)
                .getResultList();
    }

    private List<Form> requireFreeForms(HoisUserDetails user, FormType formType, Long start, int count) {
        List<Form> result = getFreeForms(user, formType, start, count);
        if (result.size() != count) {
            throw new ValidationFailedException("document.error.notEnoughForms");
        }
        return result;
    }
    
    private List<FormDto> getFreeFormDtos(HoisUserDetails user, FormType formType) {
        return StreamUtil.toMappedList(FormDto::of, getFreeForms(user, formType, Long.valueOf(0), FREE_FORM_COUNT));
    }

    private List<Form> requireExtraForms(HoisUserDetails user, DirectiveStudent directiveStudent, SupplementForm form) {
        DiplomaSupplement supplement = getSupplement(directiveStudent);
        int formCount = getAdditionalForms((Language.EN.equals(form.getLang()) ? supplement.getPgNrEn() : 
            supplement.getPgNrEt()).intValue());
        if (formCount == 0) {
            return Collections.emptyList();
        }
        return requireFreeForms(user, getSupplementExtraFormType(directiveStudent), form.getAdditionalNumeral(), formCount);
    }

    private List<Form> requireFreeForms(HoisUserDetails user, FormType formType, List<Long> numerals) {
        List<Form> result = em.createQuery("select f from Form f"
                + " where f.school.id = ?1 and f.type.code = ?2 and f.status.code = ?3"
                + " and f.numeral in ?4", Form.class)
                .setParameter(1, user.getSchoolId())
                .setParameter(2, formType.name())
                .setParameter(3, FormStatus.LOPUBLANKETT_STAATUS_K.name())
                .setParameter(4, numerals)
                .getResultList();
        if (result.size() != numerals.size()) {
            Set<Long> existing = result.stream().map(Form::getNumeral).collect(Collectors.toSet());
            throw new ValidationFailedException("document.error.formNotFound", 
                            Collections.singletonMap("nrs", numerals.stream()
                                    .filter(n -> !existing.contains(n)).map(n -> n.toString())
                                    .collect(Collectors.joining(", "))));
        }
        return result;
    }

    private List<Form> requireForms(HoisUserDetails user, List<Long> formIds) {
        List<Form> result = em.createQuery("select f from Form f"
                + " where f.school.id = ?1 and f.id in ?2", Form.class)
                .setParameter(1, user.getSchoolId())
                .setParameter(2, formIds)
                .getResultList();
        AssertionFailedException.throwIf(result.size() != formIds.size(), "Invalid form ids: " + formIds);
        return result;
    }

    public List<FormDto> calculate(HoisUserDetails user, Long directiveStudentId, SupplementForm form) {
        DirectiveStudent directiveStudent = directiveStudent(user, directiveStudentId);
        DiplomaSupplement supplement = getSupplement(directiveStudent);
        AssertionFailedException.throwIf(ClassifierUtil.equals(DocumentStatus.LOPUDOK_STAATUS_K, supplement.getDiploma().getStatus()), 
                "diploma is not printed");
        return Stream.concat(requireFreeForms(user, getSupplementFormType(directiveStudent, form.getLang()), Collections.singletonList(form.getNumeral())).stream(), 
                requireExtraForms(user, directiveStudent, form).stream())
                .map(FormDto::of).collect(Collectors.toList());
    }
    
    public byte[] supplementPrint(HoisUserDetails user, Long directiveStudentId, SupplementForm form) {
        DirectiveStudent directiveStudent = directiveStudent(user, directiveStudentId);
        DiplomaSupplement supplement = getSupplement(directiveStudent);
        AssertionFailedException.throwIf(ClassifierUtil.equals(DocumentStatus.LOPUDOK_STAATUS_K, supplement.getDiploma().getStatus()), 
                "diploma is not printed");
        requireFreeForms(user, getSupplementFormType(directiveStudent, form.getLang()), Collections.singletonList(form.getNumeral()));
        
        DiplomaSupplementReport report = new DiplomaSupplementReport(supplement, 
                requireExtraForms(user, directiveStudent, form).stream()
                    .map(Form::getFullCode).collect(Collectors.toList()), form.getShowSubjectCode(), form.getShowTeacher(), 
                    isSchoolUsingLetterGrades(directiveStudent.getStudent()), form.getLang());
        setReportResults(report, supplement.getStudyResults(), form.getShowSubjectCode(), form.getShowTeacher(), form.getLang());
        return pdfService.generate(getSupplementTemplateName(directiveStudent, form.getLang()), report, form.getLang());
    }
    
    private List<String> getSupplementForms(DiplomaSupplement supplement, Language lang) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from form f"
                + " join diploma_supplement_form dsf on dsf.form_id = f.id").sort("f.numeral");
        qb.requiredCriteria("dsf.diploma_supplement_id = :supplement", "supplement", EntityUtil.getId(supplement));
        qb.requiredCriteria("f.status_code = :status", "status", FormStatus.LOPUBLANKETT_STAATUS_T.name());
        qb.requiredCriteria("f.type_code = :formType", "formType", getSupplementExtraFormType(supplement.getDiploma().getDirective()).name());
        if (Language.EN.equals(lang)) {
            qb.filter("dsf.is_english");
        } else {
            qb.filter("dsf.is_english is not true");
        }
        List<?> result = qb.select("f.full_code", em).getResultList();
        return StreamUtil.toMappedList(r -> resultAsString(r, 0), result);
    }

    public byte[] viewSupplementPdf(HoisUserDetails user, Long supplementId, Language language) {
        DiplomaSupplement supplement = supplement(user, supplementId);
        AssertionFailedException.throwIf(ClassifierUtil.equals(DocumentStatus.LOPUDOK_STAATUS_K, supplement.getStatus()), 
                "diploma is not printed");
        Language lang = language == null ? Language.ET : language;

        DiplomaSupplementReport report = new DiplomaSupplementReport(supplement, getSupplementForms(supplement, lang),
                Boolean.TRUE, Boolean.TRUE, isSchoolUsingLetterGrades(supplement.getStudent()), lang);
        setReportResults(report, supplement.getStudyResults(), Boolean.TRUE, Boolean.TRUE, lang);
        return pdfService.generate(getSupplementTemplateName(supplement.getDiploma().getDirective(), lang), report,
                lang);
    }
    
    public void supplementPrintConfirm(HoisUserDetails user, Long directiveStudentId, List<Long> formIds, Language lang) {
        DirectiveStudent directiveStudent = directiveStudent(user, directiveStudentId);
        DiplomaSupplement supplement = getSupplement(directiveStudent);
        AssertionFailedException.throwIf(!ClassifierUtil.equals(DocumentStatus.LOPUDOK_STAATUS_T, 
                supplement.getDiploma().getStatus()), "Diploma is not printed");
        if (Language.EN.equals(lang)) {
            AssertionFailedException.throwIf(!ClassifierUtil.equals(DocumentStatus.LOPUDOK_STAATUS_K, supplement.getStatusEn()), 
                    "supplement is already printed");
            supplement.setStatusEn(em.getReference(Classifier.class, DocumentStatus.LOPUDOK_STAATUS_T.name()));
        } else {
            AssertionFailedException.throwIf(!ClassifierUtil.equals(DocumentStatus.LOPUDOK_STAATUS_K, supplement.getStatus()), 
                    "supplement is already printed");
            supplement.setStatus(em.getReference(Classifier.class, DocumentStatus.LOPUDOK_STAATUS_T.name()));
        }
        EntityUtil.save(supplement, em);
        
        Classifier status = em.getReference(Classifier.class, FormStatus.LOPUBLANKETT_STAATUS_T.name());
        LocalDate now = LocalDate.now();
        for (Form form : requireForms(user, formIds)) {
            AssertionFailedException.throwIf(!ClassifierUtil.equals(FormStatus.LOPUBLANKETT_STAATUS_K, form.getStatus()), 
                    "form is already used");
            form.setStatus(status);
            form.setPrinted(now);
            EntityUtil.save(form, em);
            DiplomaSupplementForm diplomaSupplementForm = new DiplomaSupplementForm();
            diplomaSupplementForm.setForm(form);
            diplomaSupplementForm.setDiplomaSupplement(supplement);
            if (Language.EN.equals(lang)) {
                diplomaSupplementForm.setIsEnglish(Boolean.TRUE);
            }
            em.persist(diplomaSupplementForm);
        }
    }

    private static int getAdditionalForms(int pages) {
        return ((pages + 1) / 2) - 1;
    }
    
    private Directive directive(HoisUserDetails user, Long directiveId) {
        Directive directive = em.getReference(Directive.class, directiveId);
        UserUtil.assertSameSchool(user, directive.getSchool());
        return directive;
    }
    
    private DirectiveStudent directiveStudent(HoisUserDetails user, Long directiveStudentId) {
        DirectiveStudent directiveStudent = em.getReference(DirectiveStudent.class, directiveStudentId);
        UserUtil.assertSameSchool(user, directiveStudent.getDirective().getSchool());
        return directiveStudent;
    }

    private Diploma diploma(HoisUserDetails user, Long diplomaId) {
        Diploma diploma = em.getReference(Diploma.class, diplomaId);
        UserUtil.assertSameSchool(user, diploma.getDirective().getSchool());
        return diploma;
    }

    private DiplomaSupplement supplement(HoisUserDetails user, Long supplementId) {
        DiplomaSupplement supplement = em.getReference(DiplomaSupplement.class, supplementId);
        UserUtil.assertSameSchool(user, supplement.getDiploma().getDirective().getSchool());
        return supplement;
    }

    private Student student(HoisUserDetails user, Long studentId) {
        Student student = em.getReference(Student.class, studentId);
        UserUtil.assertSameSchool(user, student.getSchool());
        return student;
    }

    private Boolean isSchoolUsingLetterGrades(Student student) {
        List<?> data = em.createNativeQuery("select sch.is_letter_grade from student stu"
                + " join school sch on stu.school_id = sch.id"
                + " where stu.id = ?1")
                .setParameter(1, EntityUtil.getId(student))
                .getResultList();
        return resultAsBoolean(data.get(0), 0);
    }

    private static String toNullableUpperCase(String string) {
        return string == null ? null : string.toUpperCase();
    }

    private static String toNullableLowerCase(String string) {
        return string == null ? null : string.toLowerCase();
    }
}
