
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ee.hois.xroad.rahvastikuregister.generated package. 
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
    private final static QName _PartyClass_QNAME = new QName("http://x-road.eu/xsd/representation.xsd", "partyClass");
    private final static QName _PartyCode_QNAME = new QName("http://x-road.eu/xsd/representation.xsd", "partyCode");
    private final static QName _RepresentedParty_QNAME = new QName("http://x-road.eu/xsd/representation.xsd", "representedParty");
    private final static QName _FindUsage_QNAME = new QName("http://rr.x-road.eu/producer", "findUsage");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ee.hois.xroad.rahvastikuregister.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link HooldusoiguseDokument }
     * 
     */
    public HooldusoiguseDokument createHooldusoiguseDokument() {
        return new HooldusoiguseDokument();
    }

    /**
     * Create an instance of {@link CtEHAK }
     * 
     */
    public CtEHAK createCtEHAK() {
        return new CtEHAK();
    }

    /**
     * Create an instance of {@link AadressAndmed }
     * 
     */
    public AadressAndmed createAadressAndmed() {
        return new AadressAndmed();
    }

    /**
     * Create an instance of {@link AadressAndmed.Aadress }
     * 
     */
    public AadressAndmed.Aadress createAadressAndmedAadress() {
        return new AadressAndmed.Aadress();
    }

    /**
     * Create an instance of {@link ee.hois.xroad.rahvastikuregister.generated.Sideandmed }
     * 
     */
    public ee.hois.xroad.rahvastikuregister.generated.Sideandmed createSideandmed() {
        return new ee.hois.xroad.rahvastikuregister.generated.Sideandmed();
    }

    /**
     * Create an instance of {@link ee.hois.xroad.rahvastikuregister.generated.Isik }
     * 
     */
    public ee.hois.xroad.rahvastikuregister.generated.Isik createIsik() {
        return new ee.hois.xroad.rahvastikuregister.generated.Isik();
    }

    /**
     * Create an instance of {@link ee.hois.xroad.rahvastikuregister.generated.Isikud }
     * 
     */
    public ee.hois.xroad.rahvastikuregister.generated.Isikud createIsikud() {
        return new ee.hois.xroad.rahvastikuregister.generated.Isikud();
    }

    /**
     * Create an instance of {@link ee.hois.xroad.rahvastikuregister.generated.Dokument }
     * 
     */
    public ee.hois.xroad.rahvastikuregister.generated.Dokument createDokument() {
        return new ee.hois.xroad.rahvastikuregister.generated.Dokument();
    }

    /**
     * Create an instance of {@link Citizenships }
     * 
     */
    public Citizenships createCitizenships() {
        return new Citizenships();
    }

    /**
     * Create an instance of {@link RR434ResponseType }
     * 
     */
    public RR434ResponseType createRR434ResponseType() {
        return new RR434ResponseType();
    }

    /**
     * Create an instance of {@link RR434ResponseType.Isikud }
     * 
     */
    public RR434ResponseType.Isikud createRR434ResponseTypeIsikud() {
        return new RR434ResponseType.Isikud();
    }

    /**
     * Create an instance of {@link RR434ResponseType.Isikud.Isik }
     * 
     */
    public RR434ResponseType.Isikud.Isik createRR434ResponseTypeIsikudIsik() {
        return new RR434ResponseType.Isikud.Isik();
    }

    /**
     * Create an instance of {@link RR434ResponseType.Isikud.Isik.Dokumendid }
     * 
     */
    public RR434ResponseType.Isikud.Isik.Dokumendid createRR434ResponseTypeIsikudIsikDokumendid() {
        return new RR434ResponseType.Isikud.Isik.Dokumendid();
    }

    /**
     * Create an instance of {@link RR434ResponseType.Isikud.Isik.Elukohad }
     * 
     */
    public RR434ResponseType.Isikud.Isik.Elukohad createRR434ResponseTypeIsikudIsikElukohad() {
        return new RR434ResponseType.Isikud.Isik.Elukohad();
    }

    /**
     * Create an instance of {@link RR434ResponseType.Isikud.Isik.IsikValisriigiIsikukoodid }
     * 
     */
    public RR434ResponseType.Isikud.Isik.IsikValisriigiIsikukoodid createRR434ResponseTypeIsikudIsikIsikValisriigiIsikukoodid() {
        return new RR434ResponseType.Isikud.Isik.IsikValisriigiIsikukoodid();
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
     * Create an instance of {@link XRoadRepresentedPartyType }
     * 
     */
    public XRoadRepresentedPartyType createXRoadRepresentedPartyType() {
        return new XRoadRepresentedPartyType();
    }

    /**
     * Create an instance of {@link QueryFields }
     * 
     */
    public QueryFields createQueryFields() {
        return new QueryFields();
    }

    /**
     * Create an instance of {@link FindUsageResponse }
     * 
     */
    public FindUsageResponse createFindUsageResponse() {
        return new FindUsageResponse();
    }

    /**
     * Create an instance of {@link ResponseFields }
     * 
     */
    public ResponseFields createResponseFields() {
        return new ResponseFields();
    }

    /**
     * Create an instance of {@link NumberOfNewbornsByDate }
     * 
     */
    public NumberOfNewbornsByDate createNumberOfNewbornsByDate() {
        return new NumberOfNewbornsByDate();
    }

    /**
     * Create an instance of {@link NumberOfNewbornsByDateRequestType }
     * 
     */
    public NumberOfNewbornsByDateRequestType createNumberOfNewbornsByDateRequestType() {
        return new NumberOfNewbornsByDateRequestType();
    }

    /**
     * Create an instance of {@link NumberOfNewbornsByDateResponse }
     * 
     */
    public NumberOfNewbornsByDateResponse createNumberOfNewbornsByDateResponse() {
        return new NumberOfNewbornsByDateResponse();
    }

    /**
     * Create an instance of {@link NumberOfNewbornsByDateResponseType }
     * 
     */
    public NumberOfNewbornsByDateResponseType createNumberOfNewbornsByDateResponseType() {
        return new NumberOfNewbornsByDateResponseType();
    }

    /**
     * Create an instance of {@link RR434 }
     * 
     */
    public RR434 createRR434() {
        return new RR434();
    }

    /**
     * Create an instance of {@link RR434RequestType }
     * 
     */
    public RR434RequestType createRR434RequestType() {
        return new RR434RequestType();
    }

    /**
     * Create an instance of {@link RR434Response }
     * 
     */
    public RR434Response createRR434Response() {
        return new RR434Response();
    }

    /**
     * Create an instance of {@link AdsTase }
     * 
     */
    public AdsTase createAdsTase() {
        return new AdsTase();
    }

    /**
     * Create an instance of {@link Incapacities }
     * 
     */
    public Incapacities createIncapacities() {
        return new Incapacities();
    }

    /**
     * Create an instance of {@link KodifK }
     * 
     */
    public KodifK createKodifK() {
        return new KodifK();
    }

    /**
     * Create an instance of {@link AddrComp }
     * 
     */
    public AddrComp createAddrComp() {
        return new AddrComp();
    }

    /**
     * Create an instance of {@link Kodif2 }
     * 
     */
    public Kodif2 createKodif2() {
        return new Kodif2();
    }

    /**
     * Create an instance of {@link Kodiff }
     * 
     */
    public Kodiff createKodiff() {
        return new Kodiff();
    }

    /**
     * Create an instance of {@link KMOIsikuTuvastamine }
     * 
     */
    public KMOIsikuTuvastamine createKMOIsikuTuvastamine() {
        return new KMOIsikuTuvastamine();
    }

    /**
     * Create an instance of {@link Kodif1 }
     * 
     */
    public Kodif1 createKodif1() {
        return new Kodif1();
    }

    /**
     * Create an instance of {@link Kodif3 }
     * 
     */
    public Kodif3 createKodif3() {
        return new Kodif3();
    }

    /**
     * Create an instance of {@link Kodif }
     * 
     */
    public Kodif createKodif() {
        return new Kodif();
    }

    /**
     * Create an instance of {@link Kodifee }
     * 
     */
    public Kodifee createKodifee() {
        return new Kodifee();
    }

    /**
     * Create an instance of {@link Riigi }
     * 
     */
    public Riigi createRiigi() {
        return new Riigi();
    }

    /**
     * Create an instance of {@link Kodife }
     * 
     */
    public Kodife createKodife() {
        return new Kodife();
    }

    /**
     * Create an instance of {@link Riigii }
     * 
     */
    public Riigii createRiigii() {
        return new Riigii();
    }

    /**
     * Create an instance of {@link ParentShort }
     * 
     */
    public ParentShort createParentShort() {
        return new ParentShort();
    }

    /**
     * Create an instance of {@link Riig }
     * 
     */
    public Riig createRiig() {
        return new Riig();
    }

    /**
     * Create an instance of {@link Kdliik }
     * 
     */
    public Kdliik createKdliik() {
        return new Kdliik();
    }

    /**
     * Create an instance of {@link Kdliik1 }
     * 
     */
    public Kdliik1 createKdliik1() {
        return new Kdliik1();
    }

    /**
     * Create an instance of {@link Kodak }
     * 
     */
    public Kodak createKodak() {
        return new Kodak();
    }

    /**
     * Create an instance of {@link HooldusoiguseDokument.Asutus }
     * 
     */
    public HooldusoiguseDokument.Asutus createHooldusoiguseDokumentAsutus() {
        return new HooldusoiguseDokument.Asutus();
    }

    /**
     * Create an instance of {@link CtEHAK.AlamEHAKud }
     * 
     */
    public CtEHAK.AlamEHAKud createCtEHAKAlamEHAKud() {
        return new CtEHAK.AlamEHAKud();
    }

    /**
     * Create an instance of {@link AadressAndmed.Aadress.AadressEST }
     * 
     */
    public AadressAndmed.Aadress.AadressEST createAadressAndmedAadressAadressEST() {
        return new AadressAndmed.Aadress.AadressEST();
    }

    /**
     * Create an instance of {@link ee.hois.xroad.rahvastikuregister.generated.Sideandmed.Kontakt }
     * 
     */
    public ee.hois.xroad.rahvastikuregister.generated.Sideandmed.Kontakt createSideandmedKontakt() {
        return new ee.hois.xroad.rahvastikuregister.generated.Sideandmed.Kontakt();
    }

    /**
     * Create an instance of {@link ee.hois.xroad.rahvastikuregister.generated.Isik.ValisriigiIK }
     * 
     */
    public ee.hois.xroad.rahvastikuregister.generated.Isik.ValisriigiIK createIsikValisriigiIK() {
        return new ee.hois.xroad.rahvastikuregister.generated.Isik.ValisriigiIK();
    }

    /**
     * Create an instance of {@link ee.hois.xroad.rahvastikuregister.generated.Isikud.Isik }
     * 
     */
    public ee.hois.xroad.rahvastikuregister.generated.Isikud.Isik createIsikudIsik() {
        return new ee.hois.xroad.rahvastikuregister.generated.Isikud.Isik();
    }

    /**
     * Create an instance of {@link ee.hois.xroad.rahvastikuregister.generated.Dokument.KoostanudAsutus }
     * 
     */
    public ee.hois.xroad.rahvastikuregister.generated.Dokument.KoostanudAsutus createDokumentKoostanudAsutus() {
        return new ee.hois.xroad.rahvastikuregister.generated.Dokument.KoostanudAsutus();
    }

    /**
     * Create an instance of {@link Citizenships.Citizenship }
     * 
     */
    public Citizenships.Citizenship createCitizenshipsCitizenship() {
        return new Citizenships.Citizenship();
    }

    /**
     * Create an instance of {@link RR434ResponseType.Isikud.Isik.IsikKodakondsused }
     * 
     */
    public RR434ResponseType.Isikud.Isik.IsikKodakondsused createRR434ResponseTypeIsikudIsikIsikKodakondsused() {
        return new RR434ResponseType.Isikud.Isik.IsikKodakondsused();
    }

    /**
     * Create an instance of {@link RR434ResponseType.Isikud.Isik.IsikMuudEesnimed }
     * 
     */
    public RR434ResponseType.Isikud.Isik.IsikMuudEesnimed createRR434ResponseTypeIsikudIsikIsikMuudEesnimed() {
        return new RR434ResponseType.Isikud.Isik.IsikMuudEesnimed();
    }

    /**
     * Create an instance of {@link RR434ResponseType.Isikud.Isik.IsikMuudPerenimed }
     * 
     */
    public RR434ResponseType.Isikud.Isik.IsikMuudPerenimed createRR434ResponseTypeIsikudIsikIsikMuudPerenimed() {
        return new RR434ResponseType.Isikud.Isik.IsikMuudPerenimed();
    }

    /**
     * Create an instance of {@link RR434ResponseType.Isikud.Isik.Dokumendid.Dokument }
     * 
     */
    public RR434ResponseType.Isikud.Isik.Dokumendid.Dokument createRR434ResponseTypeIsikudIsikDokumendidDokument() {
        return new RR434ResponseType.Isikud.Isik.Dokumendid.Dokument();
    }

    /**
     * Create an instance of {@link RR434ResponseType.Isikud.Isik.Elukohad.Elukoht }
     * 
     */
    public RR434ResponseType.Isikud.Isik.Elukohad.Elukoht createRR434ResponseTypeIsikudIsikElukohadElukoht() {
        return new RR434ResponseType.Isikud.Isik.Elukohad.Elukoht();
    }

    /**
     * Create an instance of {@link RR434ResponseType.Isikud.Isik.IsikValisriigiIsikukoodid.VrIK }
     * 
     */
    public RR434ResponseType.Isikud.Isik.IsikValisriigiIsikukoodid.VrIK createRR434ResponseTypeIsikudIsikIsikValisriigiIsikukoodidVrIK() {
        return new RR434ResponseType.Isikud.Isik.IsikValisriigiIsikukoodid.VrIK();
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
    @XmlElementDecl(namespace = "http://x-road.eu/xsd/representation.xsd", name = "partyClass")
    public JAXBElement<String> createPartyClass(String value) {
        return new JAXBElement<String>(_PartyClass_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://x-road.eu/xsd/representation.xsd", name = "partyCode")
    public JAXBElement<String> createPartyCode(String value) {
        return new JAXBElement<String>(_PartyCode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XRoadRepresentedPartyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://x-road.eu/xsd/representation.xsd", name = "representedParty")
    public JAXBElement<XRoadRepresentedPartyType> createRepresentedParty(XRoadRepresentedPartyType value) {
        return new JAXBElement<XRoadRepresentedPartyType>(_RepresentedParty_QNAME, XRoadRepresentedPartyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryFields }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://rr.x-road.eu/producer", name = "findUsage")
    public JAXBElement<QueryFields> createFindUsage(QueryFields value) {
        return new JAXBElement<QueryFields>(_FindUsage_QNAME, QueryFields.class, null, value);
    }

}
