package ee.hitsa.ois.service.ehis;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.xml.datatype.DatatypeConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.WsEhisStudentLog;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.repository.DirectiveRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.web.commandobject.EhisStudentForm;
import ee.hitsa.ois.web.dto.EhisStudentReport;
import ee.hois.xroad.ehis.generated.KhlKorgharidusMuuda;
import ee.hois.xroad.ehis.generated.KhlOppeasutusList;
import ee.hois.xroad.ehis.generated.KhlOppekavaTaitmine;
import ee.hois.xroad.helpers.XRoadHeaderV4;

import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_LOPET;

@Transactional
@Service
public class EhisStudentService  extends EhisService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private DirectiveRepository directiveRepository;
    @Autowired
    private EhisDirectiveStudentService ehisDirectiveStudentService;

    public WsEhisStudentLog curriculumFulfillment(Student student) throws DatatypeConfigurationException {
        XRoadHeaderV4 xRoadHeaderV4 = getXroadHeader();

        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

        KhlOppekavaTaitmine oppekavaTaitmine = new KhlOppekavaTaitmine();
        oppekavaTaitmine.setMuutusKp(getDate(LocalDate.now(), student));
        // TODO currently no way to find
        oppekavaTaitmine.setTaitmiseProtsent(new BigDecimal(100));
        // TODO currently no way to find
        oppekavaTaitmine.setAinepunkte(new BigDecimal(50));

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setOppekavaTaitmine(oppekavaTaitmine);
        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        return makeRequest(student, xRoadHeaderV4, khlOppeasutusList);
    }

    private WsEhisStudentLog makeRequest(Student student, XRoadHeaderV4 xRoadHeaderV4, KhlOppeasutusList khlOppeasutusList) {
        WsEhisStudentLog wsEhisStudentLog = new WsEhisStudentLog();
        wsEhisStudentLog.setSchool(student.getSchool());

        return laeKorgharidused(xRoadHeaderV4, khlOppeasutusList, wsEhisStudentLog);
    }

    public EhisStudentReport exportStudents(EhisStudentForm ehisStudentForm) {
        EhisStudentReport ehisStudentReport = new EhisStudentReport();
        switch (ehisStudentForm.getDataType()) {
            case CURRICULA_FULFILMENT:

                List<Student> students = studentRepository.findAllBySchool_IdAndAndStatus_Code(
                        ehisStudentForm.getSchoolID(),
                        StudentStatus.OPPURSTAATUS_O.name()
                );
                List<EhisStudentReport.CurriculaFulfilment> fulfilment = new ArrayList<>(students.size());
                for (Student student : students) {
                    try {
                        fulfilment.add(
                                EhisStudentReport.CurriculaFulfilment.of(student, curriculumFulfillment(student)));
                    } catch (Exception e) {
                        fulfilment.add(EhisStudentReport.CurriculaFulfilment.of(student, bindingException(student, e)));
                    }
                }
                ehisStudentReport.setFulfilments(fulfilment);
                break;
            case FOREIGN_STUDY:
                throw new UnsupportedOperationException();
            case GRADUATION:
                List<EhisStudentReport.Graduation> graduations = new ArrayList<>();
                List<Directive> directives = directiveRepository.findDistinctBySchool_IdAndConfirmDateGreaterThanEqualAndConfirmDateLessThanEqualAndType_CodeEqualsAndConfirmDateIsNotNull(
                        ehisStudentForm.getSchoolID(),
                        ehisStudentForm.getFrom(),
                        ehisStudentForm.getThru(),
                        KASKKIRI_LOPET.name()
                );

                for (Directive directive : directives) {
                    for (DirectiveStudent directiveStudent : directive.getStudents()) {
                        // TODO check for printed status
                        try {
                            graduations.add(
                                    EhisStudentReport.Graduation.of(directiveStudent,
                                            ehisDirectiveStudentService.graduation(directiveStudent, directive)));
                        } catch (Exception e) {
                            graduations.add(
                                    EhisStudentReport.Graduation.of(directiveStudent, bindingException(directive, e)));
                        }
                    }
                }
                ehisStudentReport.setGraduations(graduations);
                break;
            case VOTA:
                throw new UnsupportedOperationException();
            default:
                break;
        }
        return ehisStudentReport;
    }

    @Override
    XRoadHeaderV4 getXroadHeader() {
        XRoadHeaderV4 header = super.getXroadHeader();
        header.getService().setServiceCode(LAE_KORGHARIDUS_SERVICE_CODE);
        return header;
    }
}
