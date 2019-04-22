package ee.hois.soap.ekis.client.generated;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.1.14
 * 2019-03-29T14:09:30.259+02:00
 * Generated source version: 3.1.14
 * 
 */
@WebService(targetNamespace = "urn:ekis", name = "wdoisPortType")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface WdoisPortType {

    @WebMethod
    @WebResult(name = "return", targetNamespace = "urn:ekis", partName = "return")
    public Answer registerCertificate(
        @WebParam(partName = "q_guid", name = "q_guid")
        java.lang.String qGuid,
        @WebParam(partName = "ehis_id", name = "ehis_id")
        java.lang.String ehisId,
        @WebParam(partName = "ois_id", name = "ois_id")
        java.lang.String oisId,
        @WebParam(partName = "student", name = "student")
        java.lang.String student,
        @WebParam(partName = "email", name = "email")
        java.lang.String email,
        @WebParam(partName = "subject", name = "subject")
        java.lang.String subject,
        @WebParam(partName = "body", name = "body")
        java.lang.String body,
        @WebParam(partName = "item_creator", name = "item_creator")
        java.lang.String itemCreator,
        @WebParam(partName = "type", name = "type")
        java.lang.String type,
        @WebParam(partName = "institution", name = "institution")
        java.lang.String institution
    );

    @WebMethod
    @WebResult(name = "return", targetNamespace = "urn:ekis", partName = "return")
    public Answer registerDirective(
        @WebParam(partName = "q_guid", name = "q_guid")
        java.lang.String qGuid,
        @WebParam(partName = "ehis_id", name = "ehis_id")
        int ehisId,
        @WebParam(partName = "ois_id", name = "ois_id")
        java.lang.String oisId,
        @WebParam(partName = "directive_type", name = "directive_type")
        java.lang.String directiveType,
        @WebParam(partName = "title", name = "title")
        java.lang.String title,
        @WebParam(partName = "item_creator", name = "item_creator")
        java.lang.String itemCreator,
        @WebParam(partName = "create_time", name = "create_time")
        java.lang.String createTime,
        @WebParam(partName = "manager", name = "manager")
        java.lang.String manager,
        @WebParam(partName = "content", name = "content")
        ContentArray content,
        @WebParam(partName = "wd_id", name = "wd_id")
        int wdId
    );

    @WebMethod
    @WebResult(name = "return", targetNamespace = "urn:ekis", partName = "return")
    public Answer registerPracticeContract(
        @WebParam(partName = "q_guid", name = "q_guid")
        java.lang.String qGuid,
        @WebParam(partName = "ehis_id", name = "ehis_id")
        int ehisId,
        @WebParam(partName = "ois_id", name = "ois_id")
        java.lang.String oisId,
        @WebParam(partName = "manager", name = "manager")
        java.lang.String manager,
        @WebParam(partName = "st_id_code", name = "st_id_code")
        java.lang.String stIdCode,
        @WebParam(partName = "st_first_names", name = "st_first_names")
        java.lang.String stFirstNames,
        @WebParam(partName = "st_last_name", name = "st_last_name")
        java.lang.String stLastName,
        @WebParam(partName = "st_email", name = "st_email")
        java.lang.String stEmail,
        @WebParam(partName = "st_curricula", name = "st_curricula")
        java.lang.String stCurricula,
        @WebParam(partName = "st_form", name = "st_form")
        java.lang.String stForm,
        @WebParam(partName = "st_course", name = "st_course")
        java.lang.String stCourse,
        @WebParam(partName = "st_ekap", name = "st_ekap")
        java.lang.String stEkap,
        @WebParam(partName = "st_hours", name = "st_hours")
        java.lang.String stHours,
        @WebParam(partName = "st_module", name = "st_module")
        java.lang.String stModule,
        @WebParam(partName = "org_name", name = "org_name")
        java.lang.String orgName,
        @WebParam(partName = "org_code", name = "org_code")
        java.lang.String orgCode,
        @WebParam(partName = "org_contact_name", name = "org_contact_name")
        java.lang.String orgContactName,
        @WebParam(partName = "org_tel", name = "org_tel")
        java.lang.String orgTel,
        @WebParam(partName = "org_email", name = "org_email")
        java.lang.String orgEmail,
        @WebParam(partName = "org_tutor_name", name = "org_tutor_name")
        java.lang.String orgTutorName,
        @WebParam(partName = "org_tutor_tel", name = "org_tutor_tel")
        java.lang.String orgTutorTel,
        @WebParam(partName = "org_tutor_email", name = "org_tutor_email")
        java.lang.String orgTutorEmail,
        @WebParam(partName = "programme", name = "programme")
        java.lang.String programme,
        @WebParam(partName = "start_date", name = "start_date")
        java.lang.String startDate,
        @WebParam(partName = "end_date", name = "end_date")
        java.lang.String endDate,
        @WebParam(partName = "school_tutor_id", name = "school_tutor_id")
        java.lang.String schoolTutorId,
        @WebParam(partName = "place", name = "place")
        java.lang.String place,
        @WebParam(partName = "outcomes", name = "outcomes")
        java.lang.String outcomes,
        @WebParam(partName = "aim", name = "aim")
        java.lang.String aim
    );

    @WebMethod
    @WebResult(name = "return", targetNamespace = "urn:ekis", partName = "return")
    public Answer deleteDirective(
        @WebParam(partName = "q_guid", name = "q_guid")
        java.lang.String qGuid,
        @WebParam(partName = "ois_id", name = "ois_id")
        java.lang.String oisId,
        @WebParam(partName = "status", name = "status")
        java.lang.String status,
        @WebParam(partName = "wd_id", name = "wd_id")
        int wdId
    );
}
