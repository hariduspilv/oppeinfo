package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Contract;
import ee.hitsa.ois.enums.ContractStatus;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.ContractRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.commandobject.ContractForm;
import ee.hitsa.ois.web.commandobject.ContractSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ContractDto;
import ee.hitsa.ois.web.dto.ContractSearchDto;
import ee.hitsa.ois.web.dto.ContractStudentModuleDto;
import ee.hitsa.ois.web.dto.ContractStudentThemeDto;

@Transactional
@Service
public class ContractService {

    @Autowired
    private EntityManager em;
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ClassifierRepository classifierRepository;

    private static final String CONTRACT_FROM = "from contract contract "
            + "inner join student student on contract.student_id = student.id "
            + "inner join person student_person on student.person_id = student_person.id "
            + "inner join enterprise enterprise on contract.enterprise_id = enterprise.id "
            + "inner join teacher teacher on contract.teacher_id = teacher.id "
            + "inner join person teacher_person on teacher.person_id = teacher_person.id "
            + "inner join curriculum_version_omodule curriculum_version_omodule on contract.curriculum_version_omodule_id = curriculum_version_omodule.id ";

    private static final String CONTRACT_SELECT = "contract.id contract_id, contract.status_code, contract.start, contract.end, contract.confirm_date, "
            + "student.id student_id, student_person.firstname student_person_firstname, student_person.lastname student_person_lastname, "
            + "enterprise.name, enterprise.contact_person_name, "
            + "teacher.id teacher_id, teacher_person.firstname teacher_person_firstname, teacher_person.lastname teacher_person_lastname ";

    public Page<ContractSearchDto> search(HoisUserDetails user, ContractSearchCommand command, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(CONTRACT_FROM).sort(pageable);
        qb.requiredCriteria("student.school_id = :schoolId", "schoolId", user.getSchoolId());

        qb.optionalCriteria("contract.start >= :startFrom", "startFrom", command.getStartFrom());
        qb.optionalCriteria("contract.start <= :startThru", "startThru", command.getStartThru());
        qb.optionalCriteria("contract.end >= :endFrom", "endFrom", command.getEndFrom());
        qb.optionalCriteria("contract.end <= :endThru", "endThru", command.getEndThru());
        qb.optionalContains(Arrays.asList("student_person.firstname", "student_person.lastname",
                "student_person.firstname || ' ' || student_person.lastname"), "name", command.getStudentName());
        qb.optionalCriteria("curriculum_version_omodule.curriculum_version_id = :curriculumVersionId", "curriculumVersionId", command.getCurriculumVersion());
        qb.optionalCriteria("student.student_group_id = :studentGroupId", "studentGroupId", command.getStudentGroup());
        qb.optionalContains("enterprise.name", "enterpriseName", command.getEnterpriseName());
        qb.optionalContains("enterprise.contact_person_name", "enterpriseContactPersonName", command.getEnterpriseContactPersonName());
        qb.optionalCriteria("contract.teacher_id = :teacherId", "teacherId", command.getTeacher());
        qb.optionalCriteria("contract.status_code = :status", "status", command.getStatus());

        return JpaQueryUtil.pagingResult(qb, CONTRACT_SELECT, em, pageable).map(r -> {
            ContractSearchDto dto = new ContractSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setStatus(resultAsString(r, 1));
            dto.setStart(resultAsLocalDate(r, 2));
            dto.setEnd(resultAsLocalDate(r, 3));
            dto.setConfirmDate(resultAsLocalDate(r, 4));
            //TODO: dto.setContractNumber(contractNumber);

            String studentName = PersonUtil.fullname(resultAsString(r, 6), resultAsString(r, 7));
            dto.setStudent(new AutocompleteResult(resultAsLong(r, 5), studentName, studentName));

            dto.setEnterpriseName(resultAsString(r, 8));
            dto.setEnterpriseContactPersonName(resultAsString(r, 9));

            String teacherName = PersonUtil.fullname(resultAsString(r, 11), resultAsString(r, 12));
            dto.setTeacher(new AutocompleteResult(resultAsLong(r, 10), teacherName, teacherName));
            return dto;
        });
    }

    public Collection<ContractStudentModuleDto> studentPracticeModules(HoisUserDetails user, Long studentId) {
        String from = "from student s "
                + "inner join curriculum_version cv on cv.id = s.curriculum_version_id "
                + "inner join curriculum c on c.id = cv.curriculum_id "
                + "inner join curriculum_version_omodule cvo on cvo.curriculum_version_id = s.curriculum_version_id "
                + "inner join curriculum_module cm on cm.id = cvo.curriculum_module_id "
                + "inner join classifier mcl on mcl.code = cm.module_code "
                + "left join curriculum_version_omodule_theme cvot on cvot.curriculum_version_omodule_id = cvo.id";
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(from);
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("s.id = :studentId", "studentId", studentId);

        //qb.filter("cm.practice = true");
        //TODO: kellel puudub vastavas moodulis positiivne tulemus.

        String select = "cvo.id as cvo_id, cv.code, cm.name_et as cm_name_et, mcl.name_et as mcl_name_et, "
                + "cm.name_en as cm_name_en, mcl.name_en as mcl_name_en, "
                + "cvot.id as cvot_id, cvot.name_et as cvot_name_et, cvot.credits ";
        List<?> data = qb.select(select,em).getResultList();

        Map<Long, ContractStudentModuleDto> modulesById = new HashMap<>();
        for (Object r : data) {
            ContractStudentModuleDto dto = modulesById.get(resultAsLong(r, 0));
            if (dto == null) {
                dto = new ContractStudentModuleDto();
                AutocompleteResult module = new AutocompleteResult(resultAsLong(r, 0),
                        CurriculumUtil.moduleName(resultAsString(r, 2), resultAsString(r, 3), resultAsString(r, 1)),
                        CurriculumUtil.moduleName(resultAsString(r, 4), resultAsString(r, 5), resultAsString(r, 1)));
                dto.setModule(module);
                //TODO dto.setCredits()
                modulesById.put(resultAsLong(r, 0), dto);
            }

            if (resultAsLong(r, 6) != null) {
                ContractStudentThemeDto themeDto = new ContractStudentThemeDto();
                themeDto.setTheme(new AutocompleteResult(resultAsLong(r, 6), resultAsString(r, 7), resultAsString(r, 7)));
                themeDto.setCredits((resultAsDecimal(r, 8)));
                dto.getThemes().add(themeDto);
            }
        }

        return modulesById.values();
    }

    public ContractDto get(Contract contract) {
        return ContractDto.of(contract);
    }

    public Contract create(ContractForm contractForm) {
        Contract contract = new Contract();
        contract.setStatus(classifierRepository.getOne(ContractStatus.LEPING_STAATUS_S.name()));
        return save(contract, contractForm);
    }

    public Contract save(Contract contract, ContractForm contractForm) {
        Contract changedContract = EntityUtil.bindToEntity(contractForm, contract, "student");
        changedContract.setStudent(studentRepository.getOne(contractForm.getStudent().getId()));
        return contractRepository.save(changedContract);
    }

}
