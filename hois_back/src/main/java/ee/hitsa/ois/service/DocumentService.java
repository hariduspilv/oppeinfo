package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.ClassifierConnect;
import ee.hitsa.ois.domain.FinalDocSigner;
import ee.hitsa.ois.domain.Form;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.diploma.Diploma;
import ee.hitsa.ois.domain.diploma.DiplomaSupplement;
import ee.hitsa.ois.domain.diploma.DiplomaSupplementForm;
import ee.hitsa.ois.domain.diploma.DiplomaSupplementStudyResult;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.DocumentStatus;
import ee.hitsa.ois.enums.FormStatus;
import ee.hitsa.ois.enums.FormType;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.StudyForm;
import ee.hitsa.ois.enums.StudyLanguage;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.report.DiplomaSupplementReport;
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
import ee.hitsa.ois.web.commandobject.document.SupplementForm;
import ee.hitsa.ois.web.commandobject.document.SupplementSearchForm;
import ee.hitsa.ois.web.dto.FinalDocSignerDto;
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
    private static final int FREE_FORM_COUNT = 4;

    @Autowired
    private EntityManager em;
    @Autowired
    private PdfService pdfService;
    
    public List<DirectiveDto> diplomaDirectives(HoisUserDetails user) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive d");
        directiveCriteria(qb, user);
        qb.requiredCriteria("(not exists (select 1 from diploma dip where dip.directive_id = d.id)"
                + " or exists (select 1 from diploma dip where dip.directive_id = d.id and dip.status_code = :dipstatus))",
                "dipstatus", DocumentStatus.LOPUDOK_STAATUS_K.name());
        return toDirectiveDtoList(qb);
    }
    
    public List<DirectiveDto> supplementDirectives(HoisUserDetails user) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive d");
        directiveCriteria(qb, user);
        qb.filter("exists (select 1 from diploma dip where dip.directive_id = d.id)");
        qb.requiredCriteria("(not exists (select 1 from diploma_supplement sup join diploma dip on dip.id = sup.diploma_id"
                    + " where dip.directive_id = d.id)"
                + " or exists (select 1 from diploma_supplement sup join diploma dip on dip.id = sup.diploma_id"
                    + " where dip.directive_id = d.id and sup.status_code = :supstatus))",
                "supstatus", DocumentStatus.LOPUDOK_STAATUS_K.name());
        return toDirectiveDtoList(qb);
    }
    
    private static void directiveCriteria(JpaNativeQueryBuilder qb, HoisUserDetails user) {
        qb.requiredCriteria("d.school_id = :schoolId", "schoolId", user.getSchoolId());
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

    public Page<SupplementStudentDto> supplementStudents(HoisUserDetails user, SupplementSearchForm criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive_student ds"
                + " join directive d on d.id = ds.directive_id"
                + " join diploma dip on dip.directive_id = ds.directive_id and dip.student_id = ds.student_id"
                + " join student s on s.id = ds.student_id"
                + " join person p on p.id = s.person_id"
                + " left join form f on f.id = dip.form_id"
                + " left join diploma_supplement sup on sup.diploma_id = dip.id").sort(pageable);
        directiveCriteria(qb, user);
        qb.filter("ds.canceled = false");
        qb.requiredCriteria("(sup.id is null or sup.status_code = :supplementStatus)", "supplementStatus", DocumentStatus.LOPUDOK_STAATUS_K.name());
        qb.optionalCriteria("d.id = :directiveId", "directiveId", criteria.getDirectiveId());
        qb.optionalCriteria("dip.status_code = :diplomaStatus", "diplomaStatus", criteria.getDiplomaStatus());
        qb.optionalCriteria("ds.student_id = :studentId", "studentId", criteria.getStudentId());
        qb.optionalCriteria("ds.curriculum_version_id = :curriculumVersionId", "curriculumVersionId", criteria.getCurriculumVersionId());
        Page<Object> result = JpaQueryUtil.pagingResult(qb, "ds.id, p.firstname, p.lastname"
                + ", f.full_code, dip.status_code as diploma_status, sup.status_code as supplement_status", em, pageable);
        return result.map(r -> {
            SupplementStudentDto dto = new SupplementStudentDto();
            dto.setDirectiveStudentId(resultAsLong(r, 0));
            dto.setFullname(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            dto.setDiplomaNr(resultAsString(r, 3));
            dto.setDiplomaStatus(resultAsString(r, 4));
            dto.setSupplementStatus(resultAsString(r, 5));
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

    public void createUpdateDiplomas(HoisUserDetails user, DiplomaForm form) {
        createUpdateDiplomas(user, form, Language.ET);
    }
    
    public void createUpdateDiplomas(HoisUserDetails user, DiplomaForm form, Language lang) {
        Long directiveId = form.getDirectiveId();
        Directive directive = directive(user, directiveId);
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
        Map<Long, Diploma> existing = StreamUtil.toMap(d -> EntityUtil.getId(d.getStudent()), getDiplomas(user, form));
        Query occupationQuery = createOccupationQuery("occupation_code", directiveId);
        Query partOccupationQuery = createOccupationQuery("part_occupation_code", directiveId);
        List<?> result = em.createNativeQuery("select ds.student_id, upper(p.firstname) firstname, upper(p.lastname) lastname, p.idcode, p.birthdate"
                + ", ds.is_cum_laude, c.mer_code, c.name_et, ekr.value"
                + " from directive_student ds"
                + " join curriculum_version cv on cv.id = ds.curriculum_version_id"
                + " join curriculum c on c.id = cv.curriculum_id"
                + " join classifier_connect cc on cc.main_classifier_code = ?3 and cc.classifier_code = c.orig_study_level_code"
                + " join classifier ekr on ekr.code = cc.connect_classifier_code"
                + " join student s on s.id = ds.student_id"
                + " join person p on p.id = s.person_id"
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
            diploma.setMerCode(resultAsString(r, 6));
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
    
    public List<Diploma> getDiplomas(HoisUserDetails user, DiplomaBaseForm form) {
        Long directiveId = form.getDirectiveId();
        directive(user, directiveId);
        return em.createQuery("select d from Diploma d"
                + " where d.directive.id = ?1 and d.type.code = ?2"
                + " and d.student.id in ?3"
                + " order by d.lastname, d.firstname", Diploma.class)
                .setParameter(1, directiveId)
                .setParameter(2, form.getFormType())
                .setParameter(3, form.getStudentIds())
                .getResultList();
    }

    public byte[] diplomaPrintView(HoisUserDetails user, DiplomaForm form) {
        return pdfService.generate(DIPLOMA_TEMPLATE_NAME, getDiplomas(user, form));
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
        requireFreeForms(user, form);
        return pdfService.generate(DIPLOMA_TEMPLATE_NAME, getDiplomas(user, form));
    }
    
    public void diplomaPrintConfirm(HoisUserDetails user, DiplomaConfirmForm confirmForm) {
        List<Form> forms = requireFreeForms(user, FormType.valueOf(confirmForm.getFormType()), confirmForm.getNumerals());
        Classifier diplomaStatus = em.getReference(Classifier.class, DocumentStatus.LOPUDOK_STAATUS_T.name());
        Classifier formStatus = em.getReference(Classifier.class, FormStatus.LOPUBLANKETT_STAATUS_T.name());
        LocalDate now = LocalDate.now();
        Iterator<Form> iterator = forms.iterator();
        for (Diploma diploma : getDiplomas(user, confirmForm)) {
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

    public void createUpdateSupplement(HoisUserDetails user, Long directiveStudentId, Long signer1Id) {
        DiplomaSupplement supplement = getSupplement(user, directiveStudentId);
        if (supplement == null) {
            supplement = new DiplomaSupplement();
        } else {
            AssertionFailedException.throwIf(!ClassifierUtil.equals(DocumentStatus.LOPUDOK_STAATUS_K, supplement.getStatus()), 
                    "supplement is already used");
        }
        DirectiveStudent directiveStudent = directiveStudent(user, directiveStudentId);
        Directive directive = directiveStudent.getDirective();
        Student student = directiveStudent.getStudent();
        Long studentId = EntityUtil.getId(student);
        Diploma diploma = em.createQuery("select d from Diploma d where d.directive = ?1 and d.student = ?2", 
                Diploma.class)
                .setParameter(1, directive)
                .setParameter(2, student)
                .getSingleResult();
        School school = directive.getSchool();
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
        FinalDocSigner signer1 = em.getReference(FinalDocSigner.class, signer1Id);
        UserUtil.assertSameSchool(user, signer1.getSchool());
        Classifier status = em.getReference(Classifier.class, DocumentStatus.LOPUDOK_STAATUS_K.name());
        supplement.setDiploma(diploma);
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
        supplement.setSigner1Name(signer1.getName());
        supplement.setSigner1Position(signer1.getPosition());
        supplement.setStatus(status);
        setStudyResults(supplement, studentId);
        
        supplement.setPgNrEt(Integer.valueOf(pdfService.getPageCount(SUPPLEMENT_TEMPLATE_NAME, getViewReport(supplement))));
        
        EntityUtil.save(supplement, em);
    }

    private void setStudyResults(DiplomaSupplement supplement, Long studentId) {
        List<?> result = em.createNativeQuery("SELECT sv.module_name_et,sv.module_name_en,sv.credits,sv.grade,lower(clf.name_et) as grade_name_et,lower(clf.name_en) as grade_name_en,sv.teachers,"
                + " case when (select count (*) from apel_application_formal_subject_or_module aaf where sv.apel_application_record_id=aaf.apel_application_record_id) > 0 then true else false end as is_apel_formal,"
                + " case when (select count (*) from apel_application_informal_subject_or_module aaf where sv.apel_application_record_id=aaf.apel_application_record_id) > 0 then true else false end as is_apel_informal,"
                + " aps.name_et,aps.name_en,case when pp.is_final=true then true else false end as is_final"
                + " from student_vocational_result sv"
                + " join ("
                + " select coalesce(svm.curriculum_version_omodule_id,sv.curriculum_version_omodule_id) as curriculum_version_omodule_id, "
                + "   first_value(sv.id) over(partition by student_id, coalesce(svm.curriculum_version_omodule_id,sv.curriculum_version_omodule_id) order by sv.grade_date desc,sv.grade desc) as id"
                + " from student_vocational_result sv"
                + " left join student_vocational_result_omodule svm on sv.id=svm.student_vocational_result_id"
                + " where sv.student_id=?1 and grade in ('A','3','4','5') and (svm.student_vocational_result_id is not null or sv.arr_modules is null)"
                + " union"
                + " select distinct 0, sv.id"
                + " from student_vocational_result sv"
                + " left join student_vocational_result_omodule svm on sv.id=svm.student_vocational_result_id"
                + " join curriculum_version_omodule cm on cm.id=any(sv.arr_modules)"
                + " where sv.student_id=?1 and grade in ('A','3','4','5') and sv.arr_modules is not null and svm.student_vocational_result_id is null) x on x.id=sv.id"
                + " join classifier clf on clf.code=sv.grade_code"
                + " left join apel_school aps on sv.apel_school_id=aps.id"
                + " left join (protocol_student ps join protocol pp on ps.protocol_id=pp.id)  on ps.id=sv.protocol_student_id"
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
            studyResult.setIsApelFormal(resultAsBoolean(r, 7));
            studyResult.setIsApelInformal(resultAsBoolean(r, 8));
            studyResult.setApelSchoolNameEt(resultAsString(r, 9));
            studyResult.setApelSchoolNameEn(resultAsString(r, 10));
            studyResult.setIsFinal(resultAsBoolean(r, 11));
            return studyResult;
        }, result));
    }

    public DiplomaSupplement getSupplement(HoisUserDetails user, Long directiveStudentId) {
        DirectiveStudent directiveStudent = directiveStudent(user, directiveStudentId);
        List<DiplomaSupplement> result = em.createQuery("select sup from DiplomaSupplement sup"
                + " where sup.student.id = ?1 and sup.diploma.directive.id = ?2", DiplomaSupplement.class)
                .setParameter(1, EntityUtil.getId(directiveStudent.getStudent()))
                .setParameter(2, EntityUtil.getId(directiveStudent.getDirective()))
                .setMaxResults(1)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public SupplementDto supplement(HoisUserDetails user, Long directiveStudentId) {
        Object r = em.createNativeQuery("select s.id, p.firstname, p.lastname, dip.status_code, f.full_code"
                + " from directive_student ds"
                + " join directive d on d.id = ds.directive_id"
                + " join diploma dip on dip.directive_id = ds.directive_id and dip.student_id = ds.student_id"
                + " join student s on s.id = ds.student_id"
                + " join person p on p.id = s.person_id"
                + " left join form f on f.id = dip.form_id"
                + " where d.school_id = ?1 and ds.id = ?2 and ds.canceled = false")
                .setParameter(1, user.getSchoolId())
                .setParameter(2, directiveStudentId)
                .getSingleResult();
        SupplementDto dto = new SupplementDto();
        dto.setFullname(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
        dto.setDiplomaStatus(resultAsString(r, 3));
        dto.setDiplomaNr(resultAsString(r, 4));
        dto.setFreeForms(getFreeFormDtos(user, FormType.LOPUBLANKETT_HIN));
        dto.setFreeExtraForms(getFreeFormDtos(user, FormType.LOPUBLANKETT_HINL));
        return dto;
    }
    
    public byte[] supplementPrintView(HoisUserDetails user, Long directiveStudentId) {
        return pdfService.generate(SUPPLEMENT_TEMPLATE_NAME, getViewReport(getSupplement(user, directiveStudentId)));
    }

    private static DiplomaSupplementReport getViewReport(DiplomaSupplement supplement) {
        return new DiplomaSupplementReport(supplement, Collections.singletonList("XXXXXX"));
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

    private List<Form> requireExtraForms(HoisUserDetails user, Long directiveStudentId, SupplementForm form) {
        DiplomaSupplement supplement = getSupplement(user, directiveStudentId);
        int formCount = getAdditionalForms(supplement.getPgNrEt().intValue());
        if (formCount == 0) {
            return Collections.emptyList();
        }
        return requireFreeForms(user, FormType.LOPUBLANKETT_HINL, form.getAdditionalNumeral(), formCount);
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
            throw new ValidationFailedException("document.error.formNotFound");
        }
        return result;
    }

    private List<Form> requireForms(HoisUserDetails user, List<Long> formIds) {
        List<Form> result = em.createQuery("select f from Form f"
                + " where f.school.id = ?1 and f.id in ?2", Form.class)
                .setParameter(1, user.getSchoolId())
                .setParameter(2, formIds)
                .getResultList();
        if (result.size() != formIds.size()) {
            throw new ValidationFailedException("document.error.formNotFound");
        }
        return result;
    }

    private List<Form> requireFreeForms(HoisUserDetails user, Long numeral, List<Long> additionalNumerals) {
        List<Form> forms = requireFreeForms(user, FormType.LOPUBLANKETT_HIN, Collections.singletonList(numeral));
        forms.addAll(requireFreeForms(user, FormType.LOPUBLANKETT_HINL, additionalNumerals));
        return forms;
    }
    
    private List<Form> requireFreeForms(HoisUserDetails user, SupplementForm form) {
        return requireFreeForms(user, form.getNumeral(), Collections.singletonList(form.getAdditionalNumeral()));
    }
    
    public List<FormDto> calculate(HoisUserDetails user, Long directiveStudentId, SupplementForm form) {
        DiplomaSupplement supplement = getSupplement(user, directiveStudentId);
        AssertionFailedException.throwIf(ClassifierUtil.equals(DocumentStatus.LOPUDOK_STAATUS_K, supplement.getDiploma().getStatus()), 
                "diploma is not printed");
        return Stream.concat(requireFreeForms(user, FormType.LOPUBLANKETT_HIN, Collections.singletonList(form.getNumeral())).stream(), 
                requireExtraForms(user, directiveStudentId, form).stream())
                .map(FormDto::of).collect(Collectors.toList());
    }
    
    public byte[] supplementPrint(HoisUserDetails user, Long directiveStudentId, SupplementForm form) {
        DiplomaSupplement supplement = getSupplement(user, directiveStudentId);
        AssertionFailedException.throwIf(ClassifierUtil.equals(DocumentStatus.LOPUDOK_STAATUS_K, supplement.getDiploma().getStatus()), 
                "diploma is not printed");
        requireFreeForms(user, form);
        return pdfService.generate(SUPPLEMENT_TEMPLATE_NAME, new DiplomaSupplementReport(supplement, 
                requireExtraForms(user, directiveStudentId, form).stream()
                    .map(Form::getFullCode).collect(Collectors.toList())));
    }
    
    public void supplementPrintConfirm(HoisUserDetails user, Long directiveStudentId, List<Long> formIds) {
        DiplomaSupplement supplement = getSupplement(user, directiveStudentId);
        AssertionFailedException.throwIf(!ClassifierUtil.equals(DocumentStatus.LOPUDOK_STAATUS_K, supplement.getStatus()), 
                "supplement is already printed");
        supplement.setStatus(em.getReference(Classifier.class, DocumentStatus.LOPUDOK_STAATUS_T.name()));
        EntityUtil.save(supplement, em);
        
        Classifier status = em.getReference(Classifier.class, FormStatus.LOPUBLANKETT_STAATUS_T.name());
        for (Form form : requireForms(user, formIds)) {
            AssertionFailedException.throwIf(!ClassifierUtil.equals(FormStatus.LOPUBLANKETT_STAATUS_K, form.getStatus()), 
                    "form is already used");
            form.setStatus(status);
            EntityUtil.save(form, em);
            DiplomaSupplementForm diplomaSupplementForm = new DiplomaSupplementForm();
            diplomaSupplementForm.setForm(form);
            diplomaSupplementForm.setDiplomaSupplement(supplement);
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
    
    private static String toNullableUpperCase(String string) {
        return string == null ? null : string.toUpperCase();
    }
    
    private static String toNullableLowerCase(String string) {
        return string == null ? null : string.toLowerCase();
    }
}
