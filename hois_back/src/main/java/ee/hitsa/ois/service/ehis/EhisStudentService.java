package ee.hitsa.ois.service.ehis;

import ee.hitsa.ois.domain.WsEhisStudentLog;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.repository.DirectiveRepository;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.web.commandobject.EhisStudentForm;
import ee.hois.xroad.ehis.generated.KhlKorgharidusMuuda;
import ee.hois.xroad.ehis.generated.KhlOppeasutusList;
import ee.hois.xroad.ehis.generated.KhlOppekavaTaitmine;
import ee.hois.xroad.helpers.XRoadHeaderV4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.xml.datatype.DatatypeConfigurationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_LOPET;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

@Transactional
@Service
public class EhisStudentService  extends EhisService {

    @Autowired
    private DirectiveRepository directiveRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private EhisDirectiveStudentService ehisDirectiveStudentService;

    public void curriculumFulfillment(Student student) throws DatatypeConfigurationException {
        XRoadHeaderV4 xRoadHeaderV4 = getXroadHeader();
        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

        KhlOppekavaTaitmine oppekavaTaitmine = new KhlOppekavaTaitmine();
        oppekavaTaitmine.setMuutusKp(getDate(LocalDate.now(), student));
        // todo currently no way to find
        oppekavaTaitmine.setTaitmiseProtsent(new BigDecimal(100));
        // todo currently no way to find
        oppekavaTaitmine.setAinepunkte(new BigDecimal(50));

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setOppekavaTaitmine(oppekavaTaitmine);
        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        makeRequest(student, xRoadHeaderV4, khlOppeasutusList);
    }

    private void makeRequest(Student student, XRoadHeaderV4 xRoadHeaderV4, KhlOppeasutusList khlOppeasutusList) {
        WsEhisStudentLog wsEhisStudentLog = new WsEhisStudentLog();
        wsEhisStudentLog.setSchool(student.getSchool());

        laeKorgharidused(xRoadHeaderV4, khlOppeasutusList, wsEhisStudentLog);
    }

    public void exportStudents(EhisStudentForm ehisStudentForm) {
        switch (ehisStudentForm.getDataType()) {
            case CURRICULA_FULFILMENT:
                // todo: find people and do request
                //curriculumFulfillment();
            case FOREIGN_STUDY:
                throw new UnsupportedOperationException();
            case GRADUATION:
                JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from directive d");
                qb.requiredCriteria("d.school_id = :schoolId", "schoolId", ehisStudentForm.getSchoolID());
                qb.requiredCriteria("d.confirm_date >= :from", "from", ehisStudentForm.getFrom());
                qb.requiredCriteria("d.confirm_date >= :thru", "thru", ehisStudentForm.getThru());
                qb.requiredCriteria("d.type_code = :type", "type", KASKKIRI_LOPET.name());
                qb.filter("d.directive_nr IS NOT NULL");
                List<?> data = qb.select("d.id", em).getResultList();
                for(Object r : data) {
                    Long directiveID = resultAsLong(r, 0);
                    ehisDirectiveStudentService.updateStudents(directiveID);
                }
            case VOTA:
                throw new UnsupportedOperationException();
            default:
                break;
        }
    }
}
