package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.studymaterial.StudyMaterial;
import ee.hitsa.ois.domain.studymaterial.StudyMaterialConnect;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.web.commandobject.OisFileCommand;
import ee.hitsa.ois.web.commandobject.OisFileViewDto;
import ee.hitsa.ois.web.commandobject.studymaterial.JournalSearchCommand;
import ee.hitsa.ois.web.commandobject.studymaterial.StudyMaterialForm;
import ee.hitsa.ois.web.commandobject.studymaterial.SubjectStudyPeriodSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.studymaterial.JournalDto;
import ee.hitsa.ois.web.dto.studymaterial.JournalSearchDto;
import ee.hitsa.ois.web.dto.studymaterial.StudyMaterialConnectDto;
import ee.hitsa.ois.web.dto.studymaterial.StudyMaterialDto;
import ee.hitsa.ois.web.dto.studymaterial.StudyMaterialSearchDto;
import ee.hitsa.ois.web.dto.studymaterial.SubjectStudyPeriodDto;
import ee.hitsa.ois.web.dto.studymaterial.SubjectStudyPeriodSearchDto;

@Transactional
@Service
public class StudyMaterialService {

    @Autowired
    private EntityManager em;

    private static final String SUBJECT_STUDY_PERIOD_FROM = "from subject_study_period ssp"
            + " inner join subject s on s.id = ssp.subject_id"
            + " inner join study_period sp on sp.id = ssp.study_period_id";
    private static final String SUBJECT_STUDY_PERIOD_SELECT = "ssp.id as subject_study_period_id"
            + ", s.id as subject_id, s.name_et as subject_name_et, s.name_en as subject_name_en"
            + ", s.code as subject_code, s.credits as subject_credits"
            + ", sp.id as study_period_id, sp.name_et as study_period_name_et, sp.name_en as study_period_name_en"
    + ", (select count(*) from study_material_connect where subject_study_period_id = ssp.id) as material_count";

    private static final String JOURNAL_FROM = "from journal j"
            + " inner join study_year sy on sy.id = j.study_year_id"
            + " inner join classifier c on sy.year_code = c.code";
    private static final String JOURNAL_SELECT = "j.id as journal_id, j.name_et as journal_name"
            + ", j.study_year_id as study_year_id, c.name_et as study_year_name_et, c.name_en as study_year_name_en"
            + ", (select count(*) from study_material_connect where journal_id = j.id) as material_count";

    private static final String MATERIAL_FROM = "from study_material m"
            + " left join ois_file f on f.id = m.ois_file_id"
            + " inner join study_material_connect mc on mc.study_material_id = m.id";
    private static final String MATERIAL_SELECT = "m.id as material_id, m.name_et as material_name"
            + ", m.type_code, m.is_public, m.is_visible_to_students, f.id as file_id, f.fname, f.ftype, m.url"
            + ", mc.id as connect_id, mc.version as connect_version"
            + ", (select count(*) from study_material_connect where study_material_id = m.id) as journal_count";
    
    private static final String MATERIAL_DELETE = "delete from study_material"
            + " where id in (select m.id from study_material m"
            + " left join study_material_connect mc on mc.study_material_id = m.id"
            + " where mc.id is null)";

    public Page<SubjectStudyPeriodSearchDto> searchSubjectStudyPeriods(HoisUserDetails user, 
            SubjectStudyPeriodSearchCommand command, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SUBJECT_STUDY_PERIOD_FROM).sort(pageable);
        
        qb.requiredCriteria("s.school_id = :school_id", "school_id", user.getSchoolId());
        
        qb.optionalCriteria("ssp.subject_id = :subject_id", "subject_id", command.getSubject());
        qb.optionalCriteria("ssp.study_period_id = :study_period_id", "study_period_id", command.getStudyPeriod());
        qb.optionalCriteria("ssp.id in (select subject_study_period_id from subject_study_period_teacher where teacher_id = :teacher_id)", 
                "teacher_id", command.getTeacher());
        
        return JpaQueryUtil.pagingResult(qb, SUBJECT_STUDY_PERIOD_SELECT, em, pageable).map(r -> {
            SubjectStudyPeriodSearchDto dto = new SubjectStudyPeriodSearchDto();
            dto.setId(resultAsLong(r, 0));
            String code = resultAsString(r, 4);
            BigDecimal credits = resultAsDecimal(r, 5);
            dto.setSubject(new AutocompleteResult(resultAsLong(r, 1), 
                    SubjectUtil.subjectName(code, resultAsString(r, 2), credits), 
                    SubjectUtil.subjectName(code, resultAsString(r, 3), credits)));
            dto.setStudyPeriod(new AutocompleteResult(resultAsLong(r, 6), resultAsString(r, 7), resultAsString(r, 8)));
            dto.setMaterialCount(resultAsLong(r, 9));
            return dto;
        });
    }

    public SubjectStudyPeriodDto getSubjectStudyPeriod(SubjectStudyPeriod subjectStudyPeriod) {
        return SubjectStudyPeriodDto.of(subjectStudyPeriod);
    }
    
    public Page<JournalSearchDto> searchJournals(HoisUserDetails user, JournalSearchCommand command, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(JOURNAL_FROM).sort(pageable);
        
        qb.requiredCriteria("j.school_id = :school_id", "school_id", user.getSchoolId());
        
        qb.optionalCriteria("j.id = :journal_id", "journal_id", command.getJournal());
        qb.optionalCriteria("j.study_year_id = :study_year_id", "study_year_id", command.getStudyYear());
        qb.optionalCriteria("j.id in (select journal_id from journal_teacher where teacher_id = :teacher_id)", 
                "teacher_id", command.getTeacher());
        
        return JpaQueryUtil.pagingResult(qb, JOURNAL_SELECT, em, pageable).map(r -> {
            JournalSearchDto dto = new JournalSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setNameEt(resultAsString(r, 1));
            dto.setStudyYear(new AutocompleteResult(resultAsLong(r, 2), resultAsString(r, 3), resultAsString(r, 4)));
            dto.setMaterialCount(resultAsLong(r, 5));
            return dto;
        });
    }

    public JournalDto getJournal(Journal journal) {
        return JournalDto.of(journal);
    }
    
    public List<StudyMaterialSearchDto> materials(Long subjectStudyPeriodId, Long journalId, 
            Boolean isPublic, Boolean isVisibleToStudents) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(MATERIAL_FROM).sort("m.name_et");
        
        qb.optionalCriteria("mc.subject_study_period_id = :subject_study_period_id", "subject_study_period_id", 
                subjectStudyPeriodId);
        qb.optionalCriteria("mc.journal_id = :journal_id", "journal_id", journalId);
        qb.optionalCriteria("m.is_public = :is_public", "is_public", isPublic);
        qb.optionalCriteria("m.is_visible_to_students = :is_visible_to_students", "is_visible_to_students", 
                isVisibleToStudents);
        
        List<?> data = qb.select(MATERIAL_SELECT, em).getResultList();
        return StreamUtil.toMappedList(r -> {
            StudyMaterialSearchDto dto = new StudyMaterialSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setNameEt(resultAsString(r, 1));
            dto.setTypeCode(resultAsString(r, 2));
            dto.setIsPublic(resultAsBoolean(r, 3));
            dto.setIsVisibleToStudents(resultAsBoolean(r, 4));
            
            OisFileViewDto oisFileDto = new OisFileViewDto();
            oisFileDto.setLongId(resultAsLong(r, 5));
            oisFileDto.setFname(resultAsString(r, 6));
            oisFileDto.setFtype(resultAsString(r, 7));
            dto.setOisFile(oisFileDto);
            
            dto.setUrl(resultAsString(r, 8));
            
            StudyMaterialConnectDto connectDto = new StudyMaterialConnectDto();
            connectDto.setId(resultAsLong(r, 9));
            connectDto.setVersion(resultAsLong(r, 10));
            dto.setConnect(connectDto);
            
            dto.setJournalCount(resultAsLong(r, 11));
            return dto;
        }, data);
    }
    
    public StudyMaterialDto get(StudyMaterial material) {
        return StudyMaterialDto.of(material);
    }

    public StudyMaterial createSubjectStudyPeriodMaterial(HoisUserDetails user, StudyMaterialForm materialForm) {
        StudyMaterial material = create(user, materialForm);
        connect(user, material.getId(), materialForm.getSubjectStudyPeriod(), null);
        return material;
    }

    public StudyMaterial createJournalMaterial(HoisUserDetails user, StudyMaterialForm materialForm) {
        StudyMaterial material = create(user, materialForm);
        connect(user, material.getId(), null, materialForm.getJournal());
        return material;
    }

    private StudyMaterial create(HoisUserDetails user, StudyMaterialForm materialForm) {
        StudyMaterial material = new StudyMaterial();
        material.setSchool(em.getReference(School.class, user.getSchoolId()));
        material.setOisFile(createFile(user, materialForm.getOisFile()));
        return save(user, material, materialForm);
    }

    public StudyMaterial save(HoisUserDetails user, StudyMaterial material, StudyMaterialForm materialForm) {
        EntityUtil.setUsername(user.getUsername(), em);
        material.setTeacher(em.getReference(Teacher.class, materialForm.getTeacher()));
        EntityUtil.bindToEntity(materialForm, material);
        return EntityUtil.save(material, em);
    }

    private OisFile createFile(HoisUserDetails user, OisFileCommand fileForm) {
        if (fileForm == null) {
            return null;
        }
        OisFile oisFile = new OisFile();
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.bindToEntity(fileForm, oisFile);
        return EntityUtil.save(oisFile, em);
    }
    
    public StudyMaterialConnect connect(HoisUserDetails user, Long materialId, Long subjectStudyPeriodId, Long journalId) {
        EntityUtil.setUsername(user.getUsername(), em);
        StudyMaterialConnect materialConnect = new StudyMaterialConnect();
        materialConnect.setStudyMaterial(em.getReference(StudyMaterial.class, materialId));
        if (subjectStudyPeriodId != null) {
            materialConnect.setSubjectStudyPeriod(em.getReference(SubjectStudyPeriod.class, subjectStudyPeriodId));
        }
        if (journalId != null) {
            materialConnect.setJournal(em.getReference(Journal.class, journalId));
        }
        return EntityUtil.save(materialConnect, em);
    }

    public void delete(HoisUserDetails user, StudyMaterialConnect materialConnect) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(materialConnect, em);
        em.createNativeQuery(MATERIAL_DELETE).executeUpdate();
    }

}
