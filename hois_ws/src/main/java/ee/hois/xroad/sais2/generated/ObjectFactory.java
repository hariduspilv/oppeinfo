
package ee.hois.xroad.sais2.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ee.hois.xroad.sais2.generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Client_QNAME = new QName("http://x-road.eu/xsd/xroad.xsd", "client");
    private final static QName _Service_QNAME = new QName("http://x-road.eu/xsd/xroad.xsd", "service");
    private final static QName _CentralService_QNAME = new QName("http://x-road.eu/xsd/xroad.xsd", "centralService");
    private final static QName _Id_QNAME = new QName("http://x-road.eu/xsd/xroad.xsd", "id");
    private final static QName _UserId_QNAME = new QName("http://x-road.eu/xsd/xroad.xsd", "userId");
    private final static QName _Issue_QNAME = new QName("http://x-road.eu/xsd/xroad.xsd", "issue");
    private final static QName _ProtocolVersion_QNAME = new QName("http://x-road.eu/xsd/xroad.xsd", "protocolVersion");
    private final static QName _Version_QNAME = new QName("http://x-road.eu/xsd/xroad.xsd", "version");
    private final static QName _XRoadInstance_QNAME = new QName("http://x-road.eu/xsd/identifiers", "xRoadInstance");
    private final static QName _MemberClass_QNAME = new QName("http://x-road.eu/xsd/identifiers", "memberClass");
    private final static QName _MemberCode_QNAME = new QName("http://x-road.eu/xsd/identifiers", "memberCode");
    private final static QName _SubsystemCode_QNAME = new QName("http://x-road.eu/xsd/identifiers", "subsystemCode");
    private final static QName _GroupCode_QNAME = new QName("http://x-road.eu/xsd/identifiers", "groupCode");
    private final static QName _ServiceCode_QNAME = new QName("http://x-road.eu/xsd/identifiers", "serviceCode");
    private final static QName _ServiceVersion_QNAME = new QName("http://x-road.eu/xsd/identifiers", "serviceVersion");
    private final static QName _SecurityCategoryCode_QNAME = new QName("http://x-road.eu/xsd/identifiers", "securityCategoryCode");
    private final static QName _ServerCode_QNAME = new QName("http://x-road.eu/xsd/identifiers", "serverCode");
    private final static QName _ListMethodsResponseResponse_QNAME = new QName("", "response");
    private final static QName _ApplicationResidencyPermitExpireDate_QNAME = new QName("http://sais2.x-road.eu/", "ResidencyPermitExpireDate");
    private final static QName _AllAppsExportRequestPage_QNAME = new QName("http://sais2.x-road.eu/", "Page");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ee.hois.xroad.sais2.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link XRoadClientIdentifierType }
     * 
     */
    public XRoadClientIdentifierType createXRoadClientIdentifierType() {
        return new XRoadClientIdentifierType();
    }

    /**
     * Create an instance of {@link XRoadServiceIdentifierType }
     * 
     */
    public XRoadServiceIdentifierType createXRoadServiceIdentifierType() {
        return new XRoadServiceIdentifierType();
    }

    /**
     * Create an instance of {@link XRoadCentralServiceIdentifierType }
     * 
     */
    public XRoadCentralServiceIdentifierType createXRoadCentralServiceIdentifierType() {
        return new XRoadCentralServiceIdentifierType();
    }

    /**
     * Create an instance of {@link RequestHash }
     * 
     */
    public RequestHash createRequestHash() {
        return new RequestHash();
    }

    /**
     * Create an instance of {@link Title }
     * 
     */
    public Title createTitle() {
        return new Title();
    }

    /**
     * Create an instance of {@link Notes }
     * 
     */
    public Notes createNotes() {
        return new Notes();
    }

    /**
     * Create an instance of {@link TechNotes }
     * 
     */
    public TechNotes createTechNotes() {
        return new TechNotes();
    }

    /**
     * Create an instance of {@link XRoadIdentifierType }
     * 
     */
    public XRoadIdentifierType createXRoadIdentifierType() {
        return new XRoadIdentifierType();
    }

    /**
     * Create an instance of {@link XRoadSecurityCategoryIdentifierType }
     * 
     */
    public XRoadSecurityCategoryIdentifierType createXRoadSecurityCategoryIdentifierType() {
        return new XRoadSecurityCategoryIdentifierType();
    }

    /**
     * Create an instance of {@link XRoadSecurityServerIdentifierType }
     * 
     */
    public XRoadSecurityServerIdentifierType createXRoadSecurityServerIdentifierType() {
        return new XRoadSecurityServerIdentifierType();
    }

    /**
     * Create an instance of {@link XRoadGlobalGroupIdentifierType }
     * 
     */
    public XRoadGlobalGroupIdentifierType createXRoadGlobalGroupIdentifierType() {
        return new XRoadGlobalGroupIdentifierType();
    }

    /**
     * Create an instance of {@link XRoadLocalGroupIdentifierType }
     * 
     */
    public XRoadLocalGroupIdentifierType createXRoadLocalGroupIdentifierType() {
        return new XRoadLocalGroupIdentifierType();
    }

    /**
     * Create an instance of {@link Base64Binary }
     * 
     */
    public Base64Binary createBase64Binary() {
        return new Base64Binary();
    }

    /**
     * Create an instance of {@link HexBinary }
     * 
     */
    public HexBinary createHexBinary() {
        return new HexBinary();
    }

    /**
     * Create an instance of {@link ListMethods }
     * 
     */
    public ListMethods createListMethods() {
        return new ListMethods();
    }

    /**
     * Create an instance of {@link ListMethodsRequest }
     * 
     */
    public ListMethodsRequest createListMethodsRequest() {
        return new ListMethodsRequest();
    }

    /**
     * Create an instance of {@link ListMethodsResponse }
     * 
     */
    public ListMethodsResponse createListMethodsResponse() {
        return new ListMethodsResponse();
    }

    /**
     * Create an instance of {@link TestSystem }
     * 
     */
    public TestSystem createTestSystem() {
        return new TestSystem();
    }

    /**
     * Create an instance of {@link TestMethodsRequest }
     * 
     */
    public TestMethodsRequest createTestMethodsRequest() {
        return new TestMethodsRequest();
    }

    /**
     * Create an instance of {@link TestSystemResponse }
     * 
     */
    public TestSystemResponse createTestSystemResponse() {
        return new TestSystemResponse();
    }

    /**
     * Create an instance of {@link ApplicationsExport }
     * 
     */
    public ApplicationsExport createApplicationsExport() {
        return new ApplicationsExport();
    }

    /**
     * Create an instance of {@link AppExportRequest }
     * 
     */
    public AppExportRequest createAppExportRequest() {
        return new AppExportRequest();
    }

    /**
     * Create an instance of {@link ApplicationsExportResponse }
     * 
     */
    public ApplicationsExportResponse createApplicationsExportResponse() {
        return new ApplicationsExportResponse();
    }

    /**
     * Create an instance of {@link AppExportResponse }
     * 
     */
    public AppExportResponse createAppExportResponse() {
        return new AppExportResponse();
    }

    /**
     * Create an instance of {@link AllApplicationsExport }
     * 
     */
    public AllApplicationsExport createAllApplicationsExport() {
        return new AllApplicationsExport();
    }

    /**
     * Create an instance of {@link AllAppsExportRequest }
     * 
     */
    public AllAppsExportRequest createAllAppsExportRequest() {
        return new AllAppsExportRequest();
    }

    /**
     * Create an instance of {@link AllApplicationsExportResponse }
     * 
     */
    public AllApplicationsExportResponse createAllApplicationsExportResponse() {
        return new AllApplicationsExportResponse();
    }

    /**
     * Create an instance of {@link AdmissionsExport }
     * 
     */
    public AdmissionsExport createAdmissionsExport() {
        return new AdmissionsExport();
    }

    /**
     * Create an instance of {@link AdmissionExportRequest }
     * 
     */
    public AdmissionExportRequest createAdmissionExportRequest() {
        return new AdmissionExportRequest();
    }

    /**
     * Create an instance of {@link AdmissionsExportResponse }
     * 
     */
    public AdmissionsExportResponse createAdmissionsExportResponse() {
        return new AdmissionsExportResponse();
    }

    /**
     * Create an instance of {@link AdmissionExportResponse }
     * 
     */
    public AdmissionExportResponse createAdmissionExportResponse() {
        return new AdmissionExportResponse();
    }

    /**
     * Create an instance of {@link AllAdmissionsExport }
     * 
     */
    public AllAdmissionsExport createAllAdmissionsExport() {
        return new AllAdmissionsExport();
    }

    /**
     * Create an instance of {@link AllAdmissionsExportRequest }
     * 
     */
    public AllAdmissionsExportRequest createAllAdmissionsExportRequest() {
        return new AllAdmissionsExportRequest();
    }

    /**
     * Create an instance of {@link AllAdmissionsExportResponse }
     * 
     */
    public AllAdmissionsExportResponse createAllAdmissionsExportResponse() {
        return new AllAdmissionsExportResponse();
    }

    /**
     * Create an instance of {@link ApplicationsImport }
     * 
     */
    public ApplicationsImport createApplicationsImport() {
        return new ApplicationsImport();
    }

    /**
     * Create an instance of {@link AppImportRequest }
     * 
     */
    public AppImportRequest createAppImportRequest() {
        return new AppImportRequest();
    }

    /**
     * Create an instance of {@link ApplicationsImportResponse }
     * 
     */
    public ApplicationsImportResponse createApplicationsImportResponse() {
        return new ApplicationsImportResponse();
    }

    /**
     * Create an instance of {@link AppImportResponse }
     * 
     */
    public AppImportResponse createAppImportResponse() {
        return new AppImportResponse();
    }

    /**
     * Create an instance of {@link ClassificationsExport }
     * 
     */
    public ClassificationsExport createClassificationsExport() {
        return new ClassificationsExport();
    }

    /**
     * Create an instance of {@link EmptyParameters }
     * 
     */
    public EmptyParameters createEmptyParameters() {
        return new EmptyParameters();
    }

    /**
     * Create an instance of {@link ClassificationsExportResponse }
     * 
     */
    public ClassificationsExportResponse createClassificationsExportResponse() {
        return new ClassificationsExportResponse();
    }

    /**
     * Create an instance of {@link ClassificationExport }
     * 
     */
    public ClassificationExport createClassificationExport() {
        return new ClassificationExport();
    }

    /**
     * Create an instance of {@link DismissApplication }
     * 
     */
    public DismissApplication createDismissApplication() {
        return new DismissApplication();
    }

    /**
     * Create an instance of {@link AppDismissRequest }
     * 
     */
    public AppDismissRequest createAppDismissRequest() {
        return new AppDismissRequest();
    }

    /**
     * Create an instance of {@link DismissApplicationResponse }
     * 
     */
    public DismissApplicationResponse createDismissApplicationResponse() {
        return new DismissApplicationResponse();
    }

    /**
     * Create an instance of {@link AppDismissResponse }
     * 
     */
    public AppDismissResponse createAppDismissResponse() {
        return new AppDismissResponse();
    }

    /**
     * Create an instance of {@link AppStatusExport }
     * 
     */
    public AppStatusExport createAppStatusExport() {
        return new AppStatusExport();
    }

    /**
     * Create an instance of {@link AppStatusExportRequest }
     * 
     */
    public AppStatusExportRequest createAppStatusExportRequest() {
        return new AppStatusExportRequest();
    }

    /**
     * Create an instance of {@link AppStatusExportResponse }
     * 
     */
    public AppStatusExportResponse createAppStatusExportResponse() {
        return new AppStatusExportResponse();
    }

    /**
     * Create an instance of {@link AppStatusExportResponseElement }
     * 
     */
    public AppStatusExportResponseElement createAppStatusExportResponseElement() {
        return new AppStatusExportResponseElement();
    }

    /**
     * Create an instance of {@link AppFilesExport }
     * 
     */
    public AppFilesExport createAppFilesExport() {
        return new AppFilesExport();
    }

    /**
     * Create an instance of {@link AppFilesRequest }
     * 
     */
    public AppFilesRequest createAppFilesRequest() {
        return new AppFilesRequest();
    }

    /**
     * Create an instance of {@link AppFilesExportResponse }
     * 
     */
    public AppFilesExportResponse createAppFilesExportResponse() {
        return new AppFilesExportResponse();
    }

    /**
     * Create an instance of {@link AppFilesResponse }
     * 
     */
    public AppFilesResponse createAppFilesResponse() {
        return new AppFilesResponse();
    }

    /**
     * Create an instance of {@link AllAppFilesExport }
     * 
     */
    public AllAppFilesExport createAllAppFilesExport() {
        return new AllAppFilesExport();
    }

    /**
     * Create an instance of {@link AllAppFilesExportResponse }
     * 
     */
    public AllAppFilesExportResponse createAllAppFilesExportResponse() {
        return new AllAppFilesExportResponse();
    }

    /**
     * Create an instance of {@link ArrayOfString }
     * 
     */
    public ArrayOfString createArrayOfString() {
        return new ArrayOfString();
    }

    /**
     * Create an instance of {@link ArrayOfApplication }
     * 
     */
    public ArrayOfApplication createArrayOfApplication() {
        return new ArrayOfApplication();
    }

    /**
     * Create an instance of {@link Application }
     * 
     */
    public Application createApplication() {
        return new Application();
    }

    /**
     * Create an instance of {@link SAISClassification }
     * 
     */
    public SAISClassification createSAISClassification() {
        return new SAISClassification();
    }

    /**
     * Create an instance of {@link ArrayOfKvp }
     * 
     */
    public ArrayOfKvp createArrayOfKvp() {
        return new ArrayOfKvp();
    }

    /**
     * Create an instance of {@link Kvp }
     * 
     */
    public Kvp createKvp() {
        return new Kvp();
    }

    /**
     * Create an instance of {@link ArrayOfCandidateEducation }
     * 
     */
    public ArrayOfCandidateEducation createArrayOfCandidateEducation() {
        return new ArrayOfCandidateEducation();
    }

    /**
     * Create an instance of {@link CandidateEducation }
     * 
     */
    public CandidateEducation createCandidateEducation() {
        return new CandidateEducation();
    }

    /**
     * Create an instance of {@link ArrayOfCandidateGrade }
     * 
     */
    public ArrayOfCandidateGrade createArrayOfCandidateGrade() {
        return new ArrayOfCandidateGrade();
    }

    /**
     * Create an instance of {@link CandidateGrade }
     * 
     */
    public CandidateGrade createCandidateGrade() {
        return new CandidateGrade();
    }

    /**
     * Create an instance of {@link ArrayOfCandidateStateExam }
     * 
     */
    public ArrayOfCandidateStateExam createArrayOfCandidateStateExam() {
        return new ArrayOfCandidateStateExam();
    }

    /**
     * Create an instance of {@link CandidateStateExam }
     * 
     */
    public CandidateStateExam createCandidateStateExam() {
        return new CandidateStateExam();
    }

    /**
     * Create an instance of {@link ArrayOfApplicationFormData }
     * 
     */
    public ArrayOfApplicationFormData createArrayOfApplicationFormData() {
        return new ArrayOfApplicationFormData();
    }

    /**
     * Create an instance of {@link ApplicationFormData }
     * 
     */
    public ApplicationFormData createApplicationFormData() {
        return new ApplicationFormData();
    }

    /**
     * Create an instance of {@link ArrayOfFormFieldOption }
     * 
     */
    public ArrayOfFormFieldOption createArrayOfFormFieldOption() {
        return new ArrayOfFormFieldOption();
    }

    /**
     * Create an instance of {@link FormFieldOption }
     * 
     */
    public FormFieldOption createFormFieldOption() {
        return new FormFieldOption();
    }

    /**
     * Create an instance of {@link ArrayOfCandidateIntTest }
     * 
     */
    public ArrayOfCandidateIntTest createArrayOfCandidateIntTest() {
        return new ArrayOfCandidateIntTest();
    }

    /**
     * Create an instance of {@link CandidateIntTest }
     * 
     */
    public CandidateIntTest createCandidateIntTest() {
        return new CandidateIntTest();
    }

    /**
     * Create an instance of {@link ArrayOfCandidateIntTestResult }
     * 
     */
    public ArrayOfCandidateIntTestResult createArrayOfCandidateIntTestResult() {
        return new ArrayOfCandidateIntTestResult();
    }

    /**
     * Create an instance of {@link CandidateIntTestResult }
     * 
     */
    public CandidateIntTestResult createCandidateIntTestResult() {
        return new CandidateIntTestResult();
    }

    /**
     * Create an instance of {@link ArrayOfCandidateAddress }
     * 
     */
    public ArrayOfCandidateAddress createArrayOfCandidateAddress() {
        return new ArrayOfCandidateAddress();
    }

    /**
     * Create an instance of {@link CandidateAddress }
     * 
     */
    public CandidateAddress createCandidateAddress() {
        return new CandidateAddress();
    }

    /**
     * Create an instance of {@link ArrayOfInt }
     * 
     */
    public ArrayOfInt createArrayOfInt() {
        return new ArrayOfInt();
    }

    /**
     * Create an instance of {@link ArrayOfAdmission }
     * 
     */
    public ArrayOfAdmission createArrayOfAdmission() {
        return new ArrayOfAdmission();
    }

    /**
     * Create an instance of {@link Admission }
     * 
     */
    public Admission createAdmission() {
        return new Admission();
    }

    /**
     * Create an instance of {@link ArrayOfSAISClassification }
     * 
     */
    public ArrayOfSAISClassification createArrayOfSAISClassification() {
        return new ArrayOfSAISClassification();
    }

    /**
     * Create an instance of {@link ArrayOfAdmissionCurriculum }
     * 
     */
    public ArrayOfAdmissionCurriculum createArrayOfAdmissionCurriculum() {
        return new ArrayOfAdmissionCurriculum();
    }

    /**
     * Create an instance of {@link AdmissionCurriculum }
     * 
     */
    public AdmissionCurriculum createAdmissionCurriculum() {
        return new AdmissionCurriculum();
    }

    /**
     * Create an instance of {@link ArrayOfAdmissionTuition }
     * 
     */
    public ArrayOfAdmissionTuition createArrayOfAdmissionTuition() {
        return new ArrayOfAdmissionTuition();
    }

    /**
     * Create an instance of {@link AdmissionTuition }
     * 
     */
    public AdmissionTuition createAdmissionTuition() {
        return new AdmissionTuition();
    }

    /**
     * Create an instance of {@link ArrayOfImportApp }
     * 
     */
    public ArrayOfImportApp createArrayOfImportApp() {
        return new ArrayOfImportApp();
    }

    /**
     * Create an instance of {@link ImportApp }
     * 
     */
    public ImportApp createImportApp() {
        return new ImportApp();
    }

    /**
     * Create an instance of {@link ArrayOfCandidateEducationItem }
     * 
     */
    public ArrayOfCandidateEducationItem createArrayOfCandidateEducationItem() {
        return new ArrayOfCandidateEducationItem();
    }

    /**
     * Create an instance of {@link CandidateEducationItem }
     * 
     */
    public CandidateEducationItem createCandidateEducationItem() {
        return new CandidateEducationItem();
    }

    /**
     * Create an instance of {@link ArrayOfIdPair }
     * 
     */
    public ArrayOfIdPair createArrayOfIdPair() {
        return new ArrayOfIdPair();
    }

    /**
     * Create an instance of {@link IdPair }
     * 
     */
    public IdPair createIdPair() {
        return new IdPair();
    }

    /**
     * Create an instance of {@link ArrayOfClassificationTypeItem }
     * 
     */
    public ArrayOfClassificationTypeItem createArrayOfClassificationTypeItem() {
        return new ArrayOfClassificationTypeItem();
    }

    /**
     * Create an instance of {@link ClassificationTypeItem }
     * 
     */
    public ClassificationTypeItem createClassificationTypeItem() {
        return new ClassificationTypeItem();
    }

    /**
     * Create an instance of {@link ArrayOfClassificationItem }
     * 
     */
    public ArrayOfClassificationItem createArrayOfClassificationItem() {
        return new ArrayOfClassificationItem();
    }

    /**
     * Create an instance of {@link ClassificationItem }
     * 
     */
    public ClassificationItem createClassificationItem() {
        return new ClassificationItem();
    }

    /**
     * Create an instance of {@link ArrayOfGuid }
     * 
     */
    public ArrayOfGuid createArrayOfGuid() {
        return new ArrayOfGuid();
    }

    /**
     * Create an instance of {@link ArrayOfApplicationStatus }
     * 
     */
    public ArrayOfApplicationStatus createArrayOfApplicationStatus() {
        return new ArrayOfApplicationStatus();
    }

    /**
     * Create an instance of {@link ApplicationStatus }
     * 
     */
    public ApplicationStatus createApplicationStatus() {
        return new ApplicationStatus();
    }

    /**
     * Create an instance of {@link ArrayOfApplicationFile }
     * 
     */
    public ArrayOfApplicationFile createArrayOfApplicationFile() {
        return new ArrayOfApplicationFile();
    }

    /**
     * Create an instance of {@link ApplicationFile }
     * 
     */
    public ApplicationFile createApplicationFile() {
        return new ApplicationFile();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XRoadClientIdentifierType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://x-road.eu/xsd/xroad.xsd", name = "client")
    public JAXBElement<XRoadClientIdentifierType> createClient(XRoadClientIdentifierType value) {
        return new JAXBElement<XRoadClientIdentifierType>(_Client_QNAME, XRoadClientIdentifierType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XRoadServiceIdentifierType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://x-road.eu/xsd/xroad.xsd", name = "service")
    public JAXBElement<XRoadServiceIdentifierType> createService(XRoadServiceIdentifierType value) {
        return new JAXBElement<XRoadServiceIdentifierType>(_Service_QNAME, XRoadServiceIdentifierType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XRoadCentralServiceIdentifierType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://x-road.eu/xsd/xroad.xsd", name = "centralService")
    public JAXBElement<XRoadCentralServiceIdentifierType> createCentralService(XRoadCentralServiceIdentifierType value) {
        return new JAXBElement<XRoadCentralServiceIdentifierType>(_CentralService_QNAME, XRoadCentralServiceIdentifierType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://x-road.eu/xsd/xroad.xsd", name = "id")
    public JAXBElement<String> createId(String value) {
        return new JAXBElement<String>(_Id_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://x-road.eu/xsd/xroad.xsd", name = "userId")
    public JAXBElement<String> createUserId(String value) {
        return new JAXBElement<String>(_UserId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://x-road.eu/xsd/xroad.xsd", name = "issue")
    public JAXBElement<String> createIssue(String value) {
        return new JAXBElement<String>(_Issue_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://x-road.eu/xsd/xroad.xsd", name = "protocolVersion")
    public JAXBElement<String> createProtocolVersion(String value) {
        return new JAXBElement<String>(_ProtocolVersion_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://x-road.eu/xsd/xroad.xsd", name = "version")
    public JAXBElement<String> createVersion(String value) {
        return new JAXBElement<String>(_Version_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://x-road.eu/xsd/identifiers", name = "xRoadInstance")
    public JAXBElement<String> createXRoadInstance(String value) {
        return new JAXBElement<String>(_XRoadInstance_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://x-road.eu/xsd/identifiers", name = "memberClass")
    public JAXBElement<String> createMemberClass(String value) {
        return new JAXBElement<String>(_MemberClass_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://x-road.eu/xsd/identifiers", name = "memberCode")
    public JAXBElement<String> createMemberCode(String value) {
        return new JAXBElement<String>(_MemberCode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://x-road.eu/xsd/identifiers", name = "subsystemCode")
    public JAXBElement<String> createSubsystemCode(String value) {
        return new JAXBElement<String>(_SubsystemCode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://x-road.eu/xsd/identifiers", name = "groupCode")
    public JAXBElement<String> createGroupCode(String value) {
        return new JAXBElement<String>(_GroupCode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://x-road.eu/xsd/identifiers", name = "serviceCode")
    public JAXBElement<String> createServiceCode(String value) {
        return new JAXBElement<String>(_ServiceCode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://x-road.eu/xsd/identifiers", name = "serviceVersion")
    public JAXBElement<String> createServiceVersion(String value) {
        return new JAXBElement<String>(_ServiceVersion_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://x-road.eu/xsd/identifiers", name = "securityCategoryCode")
    public JAXBElement<String> createSecurityCategoryCode(String value) {
        return new JAXBElement<String>(_SecurityCategoryCode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://x-road.eu/xsd/identifiers", name = "serverCode")
    public JAXBElement<String> createServerCode(String value) {
        return new JAXBElement<String>(_ServerCode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "response", scope = ListMethodsResponse.class)
    public JAXBElement<String> createListMethodsResponseResponse(String value) {
        return new JAXBElement<String>(_ListMethodsResponseResponse_QNAME, String.class, ListMethodsResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "response", scope = TestSystemResponse.class)
    public JAXBElement<String> createTestSystemResponseResponse(String value) {
        return new JAXBElement<String>(_ListMethodsResponseResponse_QNAME, String.class, TestSystemResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sais2.x-road.eu/", name = "ResidencyPermitExpireDate", scope = Application.class)
    public JAXBElement<XMLGregorianCalendar> createApplicationResidencyPermitExpireDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ApplicationResidencyPermitExpireDate_QNAME, XMLGregorianCalendar.class, Application.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sais2.x-road.eu/", name = "Page", scope = AllAppsExportRequest.class)
    public JAXBElement<Integer> createAllAppsExportRequestPage(Integer value) {
        return new JAXBElement<Integer>(_AllAppsExportRequestPage_QNAME, Integer.class, AllAppsExportRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sais2.x-road.eu/", name = "Page", scope = AppExportRequest.class)
    public JAXBElement<Integer> createAppExportRequestPage(Integer value) {
        return new JAXBElement<Integer>(_AllAppsExportRequestPage_QNAME, Integer.class, AppExportRequest.class, value);
    }

}
