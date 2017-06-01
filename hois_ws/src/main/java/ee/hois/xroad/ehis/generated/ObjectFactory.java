
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ee.hois.xroad.ehis.generated package. 
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
    private final static QName _EisOppeasutused_QNAME = new QName("http://producers.ehis.xtee.riik.ee/producer/ehis", "eisOppeasutused");
    private final static QName _KvkLopetatud_QNAME = new QName("http://producers.ehis.xtee.riik.ee/producer/ehis", "kvkLopetatud");
    private final static QName _KvkOppimine_QNAME = new QName("http://producers.ehis.xtee.riik.ee/producer/ehis", "kvkOppimine");
    private final static QName _MtsysKlfTeenus_QNAME = new QName("http://producers.ehis.xtee.riik.ee/producer/ehis", "mtsysKlfTeenus");
    private final static QName _VpTaotlusOpingudTaotluseId_QNAME = new QName("", "taotluseId");
    private final static QName _VpTaotlusOpingudResponseHoiatusDto_QNAME = new QName("", "hoiatusDto");
    private final static QName _VpTaotlusSissetulekudResponseLisatudIsikuteSissetulek_QNAME = new QName("", "lisatudIsikuteSissetulek");
    private final static QName _VpTaotlusSissetulekudResponseNonResidentSissetulek_QNAME = new QName("", "nonResidentSissetulek");
    private final static QName _VpTaotlusEsitamineTelefoniNumber_QNAME = new QName("", "telefoniNumber");
    private final static QName _VpTaotlusEsitamineResponseTaotlusInfoDto_QNAME = new QName("", "taotlusInfoDto");
    private final static QName _KmaLopetanudResponseTeade_QNAME = new QName("", "teade");
    private final static QName _KmaLopetanudResponseKood_QNAME = new QName("", "kood");
    private final static QName _KmaLopetanudResponseData_QNAME = new QName("", "data");
    private final static QName _MtsysEsitaTegevuslubaMenetlusKommentaar_QNAME = new QName("", "menetlusKommentaar");
    private final static QName _MtsysEsitaTegevuslubaAlgusKp_QNAME = new QName("", "algusKp");
    private final static QName _MtsysEsitaTegevuslubaLoppKp_QNAME = new QName("", "loppKp");
    private final static QName _MtsysKlfAdsTeenusId_QNAME = new QName("", "id");
    private final static QName _MtsysKlfTeenusResponseTegevusloaLiigid_QNAME = new QName("", "tegevusloaLiigid");
    private final static QName _MtsysKlfTeenusResponseOppekavaStaatused_QNAME = new QName("", "oppekavaStaatused");
    private final static QName _MtsysKlfTeenusResponseOppekavaOppetasemed_QNAME = new QName("", "oppekavaOppetasemed");
    private final static QName _MtsysKlfTeenusResponseSoidukiKategooriad_QNAME = new QName("", "soidukiKategooriad");
    private final static QName _MtsysKlfTeenusResponseOppeasutuseOmandivormid_QNAME = new QName("", "oppeasutuseOmandivormid");
    private final static QName _MtsysKlfTeenusResponseOppeasutuseLiigid_QNAME = new QName("", "oppeasutuseLiigid");
    private final static QName _MtsysKlfTeenusResponsePidajaLiigid_QNAME = new QName("", "pidajaLiigid");
    private final static QName _MtsysKlfTeenusResponseFailiTyybid_QNAME = new QName("", "failiTyybid");
    private final static QName _MtsysKlfTeenusResponseTkkLiigid_QNAME = new QName("", "tkkLiigid");
    private final static QName _MtsysKlfTeenusResponseEestiKeeleTasemed_QNAME = new QName("", "eestiKeeleTasemed");
    private final static QName _MtsysKlfTeenusResponseOpperyhmad_QNAME = new QName("", "opperyhmad");
    private final static QName _MtsysKlfTeenusResponseTegevusnaitajaTyybid_QNAME = new QName("", "tegevusnaitajaTyybid");
    private final static QName _PolOppurAlgKpv_QNAME = new QName("", "algKpv");
    private final static QName _PolOppurLoppKpv_QNAME = new QName("", "loppKpv");
    private final static QName _MtsysOppeasutusKontaktandmedOppeasutuseYldtelefon_QNAME = new QName("", "oppeasutuseYldtelefon");
    private final static QName _MtsysOppeasutusKontaktandmedOppeasutuseEpost_QNAME = new QName("", "oppeasutuseEpost");
    private final static QName _MtsysOppeasutusKontaktandmedKoduleheAadress_QNAME = new QName("", "koduleheAadress");
    private final static QName _DokumentDokumentId_QNAME = new QName("", "dokumentId");
    private final static QName _DokumentKommentaar_QNAME = new QName("", "kommentaar");
    private final static QName _DokumentContent_QNAME = new QName("", "content");
    private final static QName _YhisopeAsutusKood_QNAME = new QName("", "asutusKood");
    private final static QName _OppeasutusTegevusloadNimetus_QNAME = new QName("", "nimetus");
    private final static QName _OppejoudLyhiajalineMobiilsusLyhMobKood_QNAME = new QName("", "lyhMobKood");
    private final static QName _OppejoudLyhiajalineMobiilsusKustutatud_QNAME = new QName("", "kustutatud");
    private final static QName _EylIsicParingIsikukood_QNAME = new QName("", "isikukood");
    private final static QName _EylIsicParingEesnimi_QNAME = new QName("", "eesnimi");
    private final static QName _EylIsicParingPerenimi_QNAME = new QName("", "perenimi");
    private final static QName _TegevuslubaDetailTaotlusId_QNAME = new QName("", "taotlusId");
    private final static QName _TegevuslubaDetailKohtadeArvLaagris_QNAME = new QName("", "kohtadeArvLaagris");
    private final static QName _OppeasutusDetailKontaktandmed_QNAME = new QName("", "kontaktandmed");
    private final static QName _MtsysTaotlusKontaktandmedKontaktisik_QNAME = new QName("", "kontaktisik");
    private final static QName _MtsysTaotlusKontaktandmedKoduleht_QNAME = new QName("", "koduleht");
    private final static QName _TaotlusKehtibAlates_QNAME = new QName("", "kehtibAlates");
    private final static QName _TaotlusKehtibKuni_QNAME = new QName("", "kehtibKuni");
    private final static QName _TaotlusKlTkkLiik_QNAME = new QName("", "klTkkLiik");
    private final static QName _TaotlusKlEkTase_QNAME = new QName("", "klEkTase");
    private final static QName _TaotlusKlSoidukiKategooria_QNAME = new QName("", "klSoidukiKategooria");
    private final static QName _IsikInfoDtoSynniaeg_QNAME = new QName("", "synniaeg");
    private final static QName _TaotlusInfoDtoEsitamiseKuupaev_QNAME = new QName("", "esitamiseKuupaev");
    private final static QName _OppimineDtoOppeTyypKL_QNAME = new QName("", "oppeTyypKL");
    private final static QName _OppimineDtoOppekoormusTyypKL_QNAME = new QName("", "oppekoormusTyypKL");
    private final static QName _OppimineDtoAkadeemilisePuhkuseAlustamiseKuupaev_QNAME = new QName("", "akadeemilisePuhkuseAlustamiseKuupaev");
    private final static QName _OppimineDtoEsimeseSemestriLoppKp_QNAME = new QName("", "esimeseSemestriLoppKp");
    private final static QName _AadressAdsId_QNAME = new QName("", "adsId");
    private final static QName _AadressAdsOid_QNAME = new QName("", "adsOid");
    private final static QName _AadressKlElukoht_QNAME = new QName("", "klElukoht");
    private final static QName _AadressMaakond_QNAME = new QName("", "maakond");
    private final static QName _AadressOmavalitsus_QNAME = new QName("", "omavalitsus");
    private final static QName _AadressAsula_QNAME = new QName("", "asula");
    private final static QName _AadressTaisAadress_QNAME = new QName("", "taisAadress");
    private final static QName _AadressAdsAadress_QNAME = new QName("", "adsAadress");
    private final static QName _AadressAdsAadressHumanReadable_QNAME = new QName("", "adsAadressHumanReadable");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ee.hois.xroad.ehis.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SaisOppekavadList }
     * 
     */
    public SaisOppekavadList createSaisOppekavadList() {
        return new SaisOppekavadList();
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
     * Create an instance of {@link AdsTeenus }
     * 
     */
    public AdsTeenus createAdsTeenus() {
        return new AdsTeenus();
    }

    /**
     * Create an instance of {@link AdsTeenusResponse }
     * 
     */
    public AdsTeenusResponse createAdsTeenusResponse() {
        return new AdsTeenusResponse();
    }

    /**
     * Create an instance of {@link Aadress }
     * 
     */
    public Aadress createAadress() {
        return new Aadress();
    }

    /**
     * Create an instance of {@link EisOppeasutused }
     * 
     */
    public EisOppeasutused createEisOppeasutused() {
        return new EisOppeasutused();
    }

    /**
     * Create an instance of {@link EisOppeasutusedResponse }
     * 
     */
    public EisOppeasutusedResponse createEisOppeasutusedResponse() {
        return new EisOppeasutusedResponse();
    }

    /**
     * Create an instance of {@link EisOppeasutusedList }
     * 
     */
    public EisOppeasutusedList createEisOppeasutusedList() {
        return new EisOppeasutusedList();
    }

    /**
     * Create an instance of {@link EsrOppeasutused }
     * 
     */
    public EsrOppeasutused createEsrOppeasutused() {
        return new EsrOppeasutused();
    }

    /**
     * Create an instance of {@link EsrOppeasutusedResponse }
     * 
     */
    public EsrOppeasutusedResponse createEsrOppeasutusedResponse() {
        return new EsrOppeasutusedResponse();
    }

    /**
     * Create an instance of {@link EsrOppeasutusedList }
     * 
     */
    public EsrOppeasutusedList createEsrOppeasutusedList() {
        return new EsrOppeasutusedList();
    }

    /**
     * Create an instance of {@link EsrPedagoogid }
     * 
     */
    public EsrPedagoogid createEsrPedagoogid() {
        return new EsrPedagoogid();
    }

    /**
     * Create an instance of {@link EsrPedagoogidResponse }
     * 
     */
    public EsrPedagoogidResponse createEsrPedagoogidResponse() {
        return new EsrPedagoogidResponse();
    }

    /**
     * Create an instance of {@link EsrPedagoogidList }
     * 
     */
    public EsrPedagoogidList createEsrPedagoogidList() {
        return new EsrPedagoogidList();
    }

    /**
     * Create an instance of {@link VpTaotlusOpingud }
     * 
     */
    public VpTaotlusOpingud createVpTaotlusOpingud() {
        return new VpTaotlusOpingud();
    }

    /**
     * Create an instance of {@link VpTaotlusOpingudResponse }
     * 
     */
    public VpTaotlusOpingudResponse createVpTaotlusOpingudResponse() {
        return new VpTaotlusOpingudResponse();
    }

    /**
     * Create an instance of {@link HoiatusDto }
     * 
     */
    public HoiatusDto createHoiatusDto() {
        return new HoiatusDto();
    }

    /**
     * Create an instance of {@link OppimineDto }
     * 
     */
    public OppimineDto createOppimineDto() {
        return new OppimineDto();
    }

    /**
     * Create an instance of {@link TaotlusInfoDto }
     * 
     */
    public TaotlusInfoDto createTaotlusInfoDto() {
        return new TaotlusInfoDto();
    }

    /**
     * Create an instance of {@link VpTaotlusIsikud }
     * 
     */
    public VpTaotlusIsikud createVpTaotlusIsikud() {
        return new VpTaotlusIsikud();
    }

    /**
     * Create an instance of {@link VpTaotlusIsikudResponse }
     * 
     */
    public VpTaotlusIsikudResponse createVpTaotlusIsikudResponse() {
        return new VpTaotlusIsikudResponse();
    }

    /**
     * Create an instance of {@link IsikInfoDto }
     * 
     */
    public IsikInfoDto createIsikInfoDto() {
        return new IsikInfoDto();
    }

    /**
     * Create an instance of {@link VpTaotlusSissetulekud }
     * 
     */
    public VpTaotlusSissetulekud createVpTaotlusSissetulekud() {
        return new VpTaotlusSissetulekud();
    }

    /**
     * Create an instance of {@link FailInfoDto }
     * 
     */
    public FailInfoDto createFailInfoDto() {
        return new FailInfoDto();
    }

    /**
     * Create an instance of {@link VpTaotlusSissetulekudResponse }
     * 
     */
    public VpTaotlusSissetulekudResponse createVpTaotlusSissetulekudResponse() {
        return new VpTaotlusSissetulekudResponse();
    }

    /**
     * Create an instance of {@link VpTaotlusKontakt }
     * 
     */
    public VpTaotlusKontakt createVpTaotlusKontakt() {
        return new VpTaotlusKontakt();
    }

    /**
     * Create an instance of {@link VpTaotlusKontaktResponse }
     * 
     */
    public VpTaotlusKontaktResponse createVpTaotlusKontaktResponse() {
        return new VpTaotlusKontaktResponse();
    }

    /**
     * Create an instance of {@link VpTaotlusEsitamine }
     * 
     */
    public VpTaotlusEsitamine createVpTaotlusEsitamine() {
        return new VpTaotlusEsitamine();
    }

    /**
     * Create an instance of {@link VpTaotlusEsitamineResponse }
     * 
     */
    public VpTaotlusEsitamineResponse createVpTaotlusEsitamineResponse() {
        return new VpTaotlusEsitamineResponse();
    }

    /**
     * Create an instance of {@link VpTaotlusDokument }
     * 
     */
    public VpTaotlusDokument createVpTaotlusDokument() {
        return new VpTaotlusDokument();
    }

    /**
     * Create an instance of {@link VpTaotlusDokumentResponse }
     * 
     */
    public VpTaotlusDokumentResponse createVpTaotlusDokumentResponse() {
        return new VpTaotlusDokumentResponse();
    }

    /**
     * Create an instance of {@link EeIsikukaart }
     * 
     */
    public EeIsikukaart createEeIsikukaart() {
        return new EeIsikukaart();
    }

    /**
     * Create an instance of {@link EeIsikukaartResponse }
     * 
     */
    public EeIsikukaartResponse createEeIsikukaartResponse() {
        return new EeIsikukaartResponse();
    }

    /**
     * Create an instance of {@link EeIsikukaartBean }
     * 
     */
    public EeIsikukaartBean createEeIsikukaartBean() {
        return new EeIsikukaartBean();
    }

    /**
     * Create an instance of {@link EylIsic }
     * 
     */
    public EylIsic createEylIsic() {
        return new EylIsic();
    }

    /**
     * Create an instance of {@link EylIsicList }
     * 
     */
    public EylIsicList createEylIsicList() {
        return new EylIsicList();
    }

    /**
     * Create an instance of {@link EylIsicResponse }
     * 
     */
    public EylIsicResponse createEylIsicResponse() {
        return new EylIsicResponse();
    }

    /**
     * Create an instance of {@link EylIsicResponseList }
     * 
     */
    public EylIsicResponseList createEylIsicResponseList() {
        return new EylIsicResponseList();
    }

    /**
     * Create an instance of {@link Idpilet2 }
     * 
     */
    public Idpilet2 createIdpilet2() {
        return new Idpilet2();
    }

    /**
     * Create an instance of {@link Idpilet2Response }
     * 
     */
    public Idpilet2Response createIdpilet2Response() {
        return new Idpilet2Response();
    }

    /**
     * Create an instance of {@link Idpilet2ItemsList }
     * 
     */
    public Idpilet2ItemsList createIdpilet2ItemsList() {
        return new Idpilet2ItemsList();
    }

    /**
     * Create an instance of {@link IsikuRollid }
     * 
     */
    public IsikuRollid createIsikuRollid() {
        return new IsikuRollid();
    }

    /**
     * Create an instance of {@link IsikuRollidResponse }
     * 
     */
    public IsikuRollidResponse createIsikuRollidResponse() {
        return new IsikuRollidResponse();
    }

    /**
     * Create an instance of {@link Isik }
     * 
     */
    public Isik createIsik() {
        return new Isik();
    }

    /**
     * Create an instance of {@link KinnitatavMajandustegevus }
     * 
     */
    public KinnitatavMajandustegevus createKinnitatavMajandustegevus() {
        return new KinnitatavMajandustegevus();
    }

    /**
     * Create an instance of {@link KinnitatavMajandustegevusResponse }
     * 
     */
    public KinnitatavMajandustegevusResponse createKinnitatavMajandustegevusResponse() {
        return new KinnitatavMajandustegevusResponse();
    }

    /**
     * Create an instance of {@link Majandustegevus }
     * 
     */
    public Majandustegevus createMajandustegevus() {
        return new Majandustegevus();
    }

    /**
     * Create an instance of {@link KinnitaMajandustegevus }
     * 
     */
    public KinnitaMajandustegevus createKinnitaMajandustegevus() {
        return new KinnitaMajandustegevus();
    }

    /**
     * Create an instance of {@link KinnitaMajandustegevusResponse }
     * 
     */
    public KinnitaMajandustegevusResponse createKinnitaMajandustegevusResponse() {
        return new KinnitaMajandustegevusResponse();
    }

    /**
     * Create an instance of {@link KmaLopetanud }
     * 
     */
    public KmaLopetanud createKmaLopetanud() {
        return new KmaLopetanud();
    }

    /**
     * Create an instance of {@link KmaLopetanudIsikList }
     * 
     */
    public KmaLopetanudIsikList createKmaLopetanudIsikList() {
        return new KmaLopetanudIsikList();
    }

    /**
     * Create an instance of {@link KmaLopetanudResponse }
     * 
     */
    public KmaLopetanudResponse createKmaLopetanudResponse() {
        return new KmaLopetanudResponse();
    }

    /**
     * Create an instance of {@link KmaLopetanudAndmed }
     * 
     */
    public KmaLopetanudAndmed createKmaLopetanudAndmed() {
        return new KmaLopetanudAndmed();
    }

    /**
     * Create an instance of {@link KodOppur }
     * 
     */
    public KodOppur createKodOppur() {
        return new KodOppur();
    }

    /**
     * Create an instance of {@link KodOppurResponse }
     * 
     */
    public KodOppurResponse createKodOppurResponse() {
        return new KodOppurResponse();
    }

    /**
     * Create an instance of {@link Yldandmed }
     * 
     */
    public Yldandmed createYldandmed() {
        return new Yldandmed();
    }

    /**
     * Create an instance of {@link TegelikElukoht }
     * 
     */
    public TegelikElukoht createTegelikElukoht() {
        return new TegelikElukoht();
    }

    /**
     * Create an instance of {@link YldhOppetoo }
     * 
     */
    public YldhOppetoo createYldhOppetoo() {
        return new YldhOppetoo();
    }

    /**
     * Create an instance of {@link KodOppurOppetood }
     * 
     */
    public KodOppurOppetood createKodOppurOppetood() {
        return new KodOppurOppetood();
    }

    /**
     * Create an instance of {@link KodOppurAjalugu }
     * 
     */
    public KodOppurAjalugu createKodOppurAjalugu() {
        return new KodOppurAjalugu();
    }

    /**
     * Create an instance of {@link KodOppurAjaluguResponse }
     * 
     */
    public KodOppurAjaluguResponse createKodOppurAjaluguResponse() {
        return new KodOppurAjaluguResponse();
    }

    /**
     * Create an instance of {@link KodOppurAjaluguMuudatused }
     * 
     */
    public KodOppurAjaluguMuudatused createKodOppurAjaluguMuudatused() {
        return new KodOppurAjaluguMuudatused();
    }

    /**
     * Create an instance of {@link KodLoputunnistus }
     * 
     */
    public KodLoputunnistus createKodLoputunnistus() {
        return new KodLoputunnistus();
    }

    /**
     * Create an instance of {@link KodLoputunnistusResponse }
     * 
     */
    public KodLoputunnistusResponse createKodLoputunnistusResponse() {
        return new KodLoputunnistusResponse();
    }

    /**
     * Create an instance of {@link LoputunnistusTunnistused }
     * 
     */
    public LoputunnistusTunnistused createLoputunnistusTunnistused() {
        return new LoputunnistusTunnistused();
    }

    /**
     * Create an instance of {@link KoolideleKehtivad }
     * 
     */
    public KoolideleKehtivad createKoolideleKehtivad() {
        return new KoolideleKehtivad();
    }

    /**
     * Create an instance of {@link KoolideleKehtivadIsikukoodid }
     * 
     */
    public KoolideleKehtivadIsikukoodid createKoolideleKehtivadIsikukoodid() {
        return new KoolideleKehtivadIsikukoodid();
    }

    /**
     * Create an instance of {@link KoolideleKehtivadResponse }
     * 
     */
    public KoolideleKehtivadResponse createKoolideleKehtivadResponse() {
        return new KoolideleKehtivadResponse();
    }

    /**
     * Create an instance of {@link KoolideleKehtivadIsikud }
     * 
     */
    public KoolideleKehtivadIsikud createKoolideleKehtivadIsikud() {
        return new KoolideleKehtivadIsikud();
    }

    /**
     * Create an instance of {@link Kvk }
     * 
     */
    public Kvk createKvk() {
        return new Kvk();
    }

    /**
     * Create an instance of {@link KvkResponse }
     * 
     */
    public KvkResponse createKvkResponse() {
        return new KvkResponse();
    }

    /**
     * Create an instance of {@link LaeAlusharidusV2 }
     * 
     */
    public LaeAlusharidusV2 createLaeAlusharidusV2() {
        return new LaeAlusharidusV2();
    }

    /**
     * Create an instance of {@link AlusOppeasutusList }
     * 
     */
    public AlusOppeasutusList createAlusOppeasutusList() {
        return new AlusOppeasutusList();
    }

    /**
     * Create an instance of {@link LaeAlusharidusV2Response }
     * 
     */
    public LaeAlusharidusV2Response createLaeAlusharidusV2Response() {
        return new LaeAlusharidusV2Response();
    }

    /**
     * Create an instance of {@link StrArray }
     * 
     */
    public StrArray createStrArray() {
        return new StrArray();
    }

    /**
     * Create an instance of {@link LaeKorgharidus }
     * 
     */
    public LaeKorgharidus createLaeKorgharidus() {
        return new LaeKorgharidus();
    }

    /**
     * Create an instance of {@link KhlOppeasutusList }
     * 
     */
    public KhlOppeasutusList createKhlOppeasutusList() {
        return new KhlOppeasutusList();
    }

    /**
     * Create an instance of {@link LaeKorgharidusResponse }
     * 
     */
    public LaeKorgharidusResponse createLaeKorgharidusResponse() {
        return new LaeKorgharidusResponse();
    }

    /**
     * Create an instance of {@link LaeOppejoud }
     * 
     */
    public LaeOppejoud createLaeOppejoud() {
        return new LaeOppejoud();
    }

    /**
     * Create an instance of {@link OppejoudList }
     * 
     */
    public OppejoudList createOppejoudList() {
        return new OppejoudList();
    }

    /**
     * Create an instance of {@link LaeOppejoudResponse }
     * 
     */
    public LaeOppejoudResponse createLaeOppejoudResponse() {
        return new LaeOppejoudResponse();
    }

    /**
     * Create an instance of {@link LaePedagoogid }
     * 
     */
    public LaePedagoogid createLaePedagoogid() {
        return new LaePedagoogid();
    }

    /**
     * Create an instance of {@link OppeasutusList }
     * 
     */
    public OppeasutusList createOppeasutusList() {
        return new OppeasutusList();
    }

    /**
     * Create an instance of {@link LaePedagoogidResponse }
     * 
     */
    public LaePedagoogidResponse createLaePedagoogidResponse() {
        return new LaePedagoogidResponse();
    }

    /**
     * Create an instance of {@link LaeYldharidus }
     * 
     */
    public LaeYldharidus createLaeYldharidus() {
        return new LaeYldharidus();
    }

    /**
     * Create an instance of {@link YhlOppeasutusList }
     * 
     */
    public YhlOppeasutusList createYhlOppeasutusList() {
        return new YhlOppeasutusList();
    }

    /**
     * Create an instance of {@link LaeYldharidusResponse }
     * 
     */
    public LaeYldharidusResponse createLaeYldharidusResponse() {
        return new LaeYldharidusResponse();
    }

    /**
     * Create an instance of {@link MkmLopetamised }
     * 
     */
    public MkmLopetamised createMkmLopetamised() {
        return new MkmLopetamised();
    }

    /**
     * Create an instance of {@link MkmLopetamisedResponse }
     * 
     */
    public MkmLopetamisedResponse createMkmLopetamisedResponse() {
        return new MkmLopetamisedResponse();
    }

    /**
     * Create an instance of {@link MkmLopetatudKorgharidused }
     * 
     */
    public MkmLopetatudKorgharidused createMkmLopetatudKorgharidused() {
        return new MkmLopetatudKorgharidused();
    }

    /**
     * Create an instance of {@link MtsysEsitaTegevusluba }
     * 
     */
    public MtsysEsitaTegevusluba createMtsysEsitaTegevusluba() {
        return new MtsysEsitaTegevusluba();
    }

    /**
     * Create an instance of {@link MtsysEsitaTegevuslubaResponse }
     * 
     */
    public MtsysEsitaTegevuslubaResponse createMtsysEsitaTegevuslubaResponse() {
        return new MtsysEsitaTegevuslubaResponse();
    }

    /**
     * Create an instance of {@link MtsysDokument }
     * 
     */
    public MtsysDokument createMtsysDokument() {
        return new MtsysDokument();
    }

    /**
     * Create an instance of {@link MtsysDokumentResponse }
     * 
     */
    public MtsysDokumentResponse createMtsysDokumentResponse() {
        return new MtsysDokumentResponse();
    }

    /**
     * Create an instance of {@link MtsysKlfAdsTeenus }
     * 
     */
    public MtsysKlfAdsTeenus createMtsysKlfAdsTeenus() {
        return new MtsysKlfAdsTeenus();
    }

    /**
     * Create an instance of {@link MtsysKlfAdsTeenusResponse }
     * 
     */
    public MtsysKlfAdsTeenusResponse createMtsysKlfAdsTeenusResponse() {
        return new MtsysKlfAdsTeenusResponse();
    }

    /**
     * Create an instance of {@link Maakonnad }
     * 
     */
    public Maakonnad createMaakonnad() {
        return new Maakonnad();
    }

    /**
     * Create an instance of {@link MtsysKlfTeenusResponse }
     * 
     */
    public MtsysKlfTeenusResponse createMtsysKlfTeenusResponse() {
        return new MtsysKlfTeenusResponse();
    }

    /**
     * Create an instance of {@link TegevusloaLiigid }
     * 
     */
    public TegevusloaLiigid createTegevusloaLiigid() {
        return new TegevusloaLiigid();
    }

    /**
     * Create an instance of {@link OppekavaStaatused }
     * 
     */
    public OppekavaStaatused createOppekavaStaatused() {
        return new OppekavaStaatused();
    }

    /**
     * Create an instance of {@link OppekavaOppetasemed }
     * 
     */
    public OppekavaOppetasemed createOppekavaOppetasemed() {
        return new OppekavaOppetasemed();
    }

    /**
     * Create an instance of {@link SoidukiKategooriad }
     * 
     */
    public SoidukiKategooriad createSoidukiKategooriad() {
        return new SoidukiKategooriad();
    }

    /**
     * Create an instance of {@link OppeasutuseOmandivormid }
     * 
     */
    public OppeasutuseOmandivormid createOppeasutuseOmandivormid() {
        return new OppeasutuseOmandivormid();
    }

    /**
     * Create an instance of {@link OppeasutuseLiigid }
     * 
     */
    public OppeasutuseLiigid createOppeasutuseLiigid() {
        return new OppeasutuseLiigid();
    }

    /**
     * Create an instance of {@link PidajaLiigid }
     * 
     */
    public PidajaLiigid createPidajaLiigid() {
        return new PidajaLiigid();
    }

    /**
     * Create an instance of {@link FailiTyybid }
     * 
     */
    public FailiTyybid createFailiTyybid() {
        return new FailiTyybid();
    }

    /**
     * Create an instance of {@link TkkLiigid }
     * 
     */
    public TkkLiigid createTkkLiigid() {
        return new TkkLiigid();
    }

    /**
     * Create an instance of {@link EestiKeeleTasemed }
     * 
     */
    public EestiKeeleTasemed createEestiKeeleTasemed() {
        return new EestiKeeleTasemed();
    }

    /**
     * Create an instance of {@link Opperyhmad }
     * 
     */
    public Opperyhmad createOpperyhmad() {
        return new Opperyhmad();
    }

    /**
     * Create an instance of {@link TegevusnaitajaTyybid }
     * 
     */
    public TegevusnaitajaTyybid createTegevusnaitajaTyybid() {
        return new TegevusnaitajaTyybid();
    }

    /**
     * Create an instance of {@link MtsysKustutaTegevusluba }
     * 
     */
    public MtsysKustutaTegevusluba createMtsysKustutaTegevusluba() {
        return new MtsysKustutaTegevusluba();
    }

    /**
     * Create an instance of {@link MtsysKustutaTegevuslubaResponse }
     * 
     */
    public MtsysKustutaTegevuslubaResponse createMtsysKustutaTegevuslubaResponse() {
        return new MtsysKustutaTegevuslubaResponse();
    }

    /**
     * Create an instance of {@link MtsysLaeTegevusluba }
     * 
     */
    public MtsysLaeTegevusluba createMtsysLaeTegevusluba() {
        return new MtsysLaeTegevusluba();
    }

    /**
     * Create an instance of {@link Oppekava }
     * 
     */
    public Oppekava createOppekava() {
        return new Oppekava();
    }

    /**
     * Create an instance of {@link Taotlus }
     * 
     */
    public Taotlus createTaotlus() {
        return new Taotlus();
    }

    /**
     * Create an instance of {@link MtsysTaotlusKontaktandmed }
     * 
     */
    public MtsysTaotlusKontaktandmed createMtsysTaotlusKontaktandmed() {
        return new MtsysTaotlusKontaktandmed();
    }

    /**
     * Create an instance of {@link Dokumendid }
     * 
     */
    public Dokumendid createDokumendid() {
        return new Dokumendid();
    }

    /**
     * Create an instance of {@link MtsysLaeTegevuslubaResponse }
     * 
     */
    public MtsysLaeTegevuslubaResponse createMtsysLaeTegevuslubaResponse() {
        return new MtsysLaeTegevuslubaResponse();
    }

    /**
     * Create an instance of {@link MtsysLaeOppeasutus }
     * 
     */
    public MtsysLaeOppeasutus createMtsysLaeOppeasutus() {
        return new MtsysLaeOppeasutus();
    }

    /**
     * Create an instance of {@link OppeasutusDetail }
     * 
     */
    public OppeasutusDetail createOppeasutusDetail() {
        return new OppeasutusDetail();
    }

    /**
     * Create an instance of {@link MtsysLaeOppeasutusResponse }
     * 
     */
    public MtsysLaeOppeasutusResponse createMtsysLaeOppeasutusResponse() {
        return new MtsysLaeOppeasutusResponse();
    }

    /**
     * Create an instance of {@link MtsysTegevusload }
     * 
     */
    public MtsysTegevusload createMtsysTegevusload() {
        return new MtsysTegevusload();
    }

    /**
     * Create an instance of {@link MtsysTegevusloadResponse }
     * 
     */
    public MtsysTegevusloadResponse createMtsysTegevusloadResponse() {
        return new MtsysTegevusloadResponse();
    }

    /**
     * Create an instance of {@link MtsysAsutus }
     * 
     */
    public MtsysAsutus createMtsysAsutus() {
        return new MtsysAsutus();
    }

    /**
     * Create an instance of {@link MtsysTegevusluba }
     * 
     */
    public MtsysTegevusluba createMtsysTegevusluba() {
        return new MtsysTegevusluba();
    }

    /**
     * Create an instance of {@link MtsysTegevuslubaResponse }
     * 
     */
    public MtsysTegevuslubaResponse createMtsysTegevuslubaResponse() {
        return new MtsysTegevuslubaResponse();
    }

    /**
     * Create an instance of {@link TegevuslubaDetail }
     * 
     */
    public TegevuslubaDetail createTegevuslubaDetail() {
        return new TegevuslubaDetail();
    }

    /**
     * Create an instance of {@link Peatamised }
     * 
     */
    public Peatamised createPeatamised() {
        return new Peatamised();
    }

    /**
     * Create an instance of {@link MtsysOppeasutus }
     * 
     */
    public MtsysOppeasutus createMtsysOppeasutus() {
        return new MtsysOppeasutus();
    }

    /**
     * Create an instance of {@link MtsysOppeasutusResponse }
     * 
     */
    public MtsysOppeasutusResponse createMtsysOppeasutusResponse() {
        return new MtsysOppeasutusResponse();
    }

    /**
     * Create an instance of {@link MtsysTegevusnaitaja }
     * 
     */
    public MtsysTegevusnaitaja createMtsysTegevusnaitaja() {
        return new MtsysTegevusnaitaja();
    }

    /**
     * Create an instance of {@link MtsysTegevusnaitajaResponse }
     * 
     */
    public MtsysTegevusnaitajaResponse createMtsysTegevusnaitajaResponse() {
        return new MtsysTegevusnaitajaResponse();
    }

    /**
     * Create an instance of {@link EhisKlassifikaator }
     * 
     */
    public EhisKlassifikaator createEhisKlassifikaator() {
        return new EhisKlassifikaator();
    }

    /**
     * Create an instance of {@link Naitajad }
     * 
     */
    public Naitajad createNaitajad() {
        return new Naitajad();
    }

    /**
     * Create an instance of {@link Aadressid }
     * 
     */
    public Aadressid createAadressid() {
        return new Aadressid();
    }

    /**
     * Create an instance of {@link ContentBase64 }
     * 
     */
    public ContentBase64 createContentBase64() {
        return new ContentBase64();
    }

    /**
     * Create an instance of {@link MtsysLaeTegevusnaitajad }
     * 
     */
    public MtsysLaeTegevusnaitajad createMtsysLaeTegevusnaitajad() {
        return new MtsysLaeTegevusnaitajad();
    }

    /**
     * Create an instance of {@link MtsysEsitaTegevusnaitajad }
     * 
     */
    public MtsysEsitaTegevusnaitajad createMtsysEsitaTegevusnaitajad() {
        return new MtsysEsitaTegevusnaitajad();
    }

    /**
     * Create an instance of {@link OppeasutusedKovPortaali }
     * 
     */
    public OppeasutusedKovPortaali createOppeasutusedKovPortaali() {
        return new OppeasutusedKovPortaali();
    }

    /**
     * Create an instance of {@link OppeasutusedKovPortaaliResponse }
     * 
     */
    public OppeasutusedKovPortaaliResponse createOppeasutusedKovPortaaliResponse() {
        return new OppeasutusedKovPortaaliResponse();
    }

    /**
     * Create an instance of {@link KovPortaaliVastusOppeasutused }
     * 
     */
    public KovPortaaliVastusOppeasutused createKovPortaaliVastusOppeasutused() {
        return new KovPortaaliVastusOppeasutused();
    }

    /**
     * Create an instance of {@link Oppurid }
     * 
     */
    public Oppurid createOppurid() {
        return new Oppurid();
    }

    /**
     * Create an instance of {@link EisOppuridList }
     * 
     */
    public EisOppuridList createEisOppuridList() {
        return new EisOppuridList();
    }

    /**
     * Create an instance of {@link OppuridResponse }
     * 
     */
    public OppuridResponse createOppuridResponse() {
        return new OppuridResponse();
    }

    /**
     * Create an instance of {@link EisOppimised }
     * 
     */
    public EisOppimised createEisOppimised() {
        return new EisOppimised();
    }

    /**
     * Create an instance of {@link OppuridKovPortaali }
     * 
     */
    public OppuridKovPortaali createOppuridKovPortaali() {
        return new OppuridKovPortaali();
    }

    /**
     * Create an instance of {@link OppuridKovPortaaliResponse }
     * 
     */
    public OppuridKovPortaaliResponse createOppuridKovPortaaliResponse() {
        return new OppuridKovPortaaliResponse();
    }

    /**
     * Create an instance of {@link Oppur }
     * 
     */
    public Oppur createOppur() {
        return new Oppur();
    }

    /**
     * Create an instance of {@link PankOppurLaenList }
     * 
     */
    public PankOppurLaenList createPankOppurLaenList() {
        return new PankOppurLaenList();
    }

    /**
     * Create an instance of {@link PankOppurLaenListResponse }
     * 
     */
    public PankOppurLaenListResponse createPankOppurLaenListResponse() {
        return new PankOppurLaenListResponse();
    }

    /**
     * Create an instance of {@link PankOppelaenTase }
     * 
     */
    public PankOppelaenTase createPankOppelaenTase() {
        return new PankOppelaenTase();
    }

    /**
     * Create an instance of {@link PankOppelaenTaseResponse }
     * 
     */
    public PankOppelaenTaseResponse createPankOppelaenTaseResponse() {
        return new PankOppelaenTaseResponse();
    }

    /**
     * Create an instance of {@link PedagoogAmetikoht }
     * 
     */
    public PedagoogAmetikoht createPedagoogAmetikoht() {
        return new PedagoogAmetikoht();
    }

    /**
     * Create an instance of {@link EisAmetikohadParingList }
     * 
     */
    public EisAmetikohadParingList createEisAmetikohadParingList() {
        return new EisAmetikohadParingList();
    }

    /**
     * Create an instance of {@link PedagoogAmetikohtResponse }
     * 
     */
    public PedagoogAmetikohtResponse createPedagoogAmetikohtResponse() {
        return new PedagoogAmetikohtResponse();
    }

    /**
     * Create an instance of {@link EisAmetikohad }
     * 
     */
    public EisAmetikohad createEisAmetikohad() {
        return new EisAmetikohad();
    }

    /**
     * Create an instance of {@link PolOppur }
     * 
     */
    public PolOppur createPolOppur() {
        return new PolOppur();
    }

    /**
     * Create an instance of {@link PolOppurResponse }
     * 
     */
    public PolOppurResponse createPolOppurResponse() {
        return new PolOppurResponse();
    }

    /**
     * Create an instance of {@link PolOppurIsikud }
     * 
     */
    public PolOppurIsikud createPolOppurIsikud() {
        return new PolOppurIsikud();
    }

    /**
     * Create an instance of {@link PolOppurOppetoodYldh }
     * 
     */
    public PolOppurOppetoodYldh createPolOppurOppetoodYldh() {
        return new PolOppurOppetoodYldh();
    }

    /**
     * Create an instance of {@link PolOppurOppetoodKorgk }
     * 
     */
    public PolOppurOppetoodKorgk createPolOppurOppetoodKorgk() {
        return new PolOppurOppetoodKorgk();
    }

    /**
     * Create an instance of {@link SaisEhisOppeasutus }
     * 
     */
    public SaisEhisOppeasutus createSaisEhisOppeasutus() {
        return new SaisEhisOppeasutus();
    }

    /**
     * Create an instance of {@link SaisEhisOppeasutusResponse }
     * 
     */
    public SaisEhisOppeasutusResponse createSaisEhisOppeasutusResponse() {
        return new SaisEhisOppeasutusResponse();
    }

    /**
     * Create an instance of {@link SaisEhisOppeasutused }
     * 
     */
    public SaisEhisOppeasutused createSaisEhisOppeasutused() {
        return new SaisEhisOppeasutused();
    }

    /**
     * Create an instance of {@link SaisHinded }
     * 
     */
    public SaisHinded createSaisHinded() {
        return new SaisHinded();
    }

    /**
     * Create an instance of {@link SaisHindedResponse }
     * 
     */
    public SaisHindedResponse createSaisHindedResponse() {
        return new SaisHindedResponse();
    }

    /**
     * Create an instance of {@link SaisPohiTunnistused }
     * 
     */
    public SaisPohiTunnistused createSaisPohiTunnistused() {
        return new SaisPohiTunnistused();
    }

    /**
     * Create an instance of {@link SaisKorgk }
     * 
     */
    public SaisKorgk createSaisKorgk() {
        return new SaisKorgk();
    }

    /**
     * Create an instance of {@link SaisKorgkResponse }
     * 
     */
    public SaisKorgkResponse createSaisKorgkResponse() {
        return new SaisKorgkResponse();
    }

    /**
     * Create an instance of {@link SaisKorgkOpingud }
     * 
     */
    public SaisKorgkOpingud createSaisKorgkOpingud() {
        return new SaisKorgkOpingud();
    }

    /**
     * Create an instance of {@link SaisOppekavad }
     * 
     */
    public SaisOppekavad createSaisOppekavad() {
        return new SaisOppekavad();
    }

    /**
     * Create an instance of {@link SaisOppekavadResponse }
     * 
     */
    public SaisOppekavadResponse createSaisOppekavadResponse() {
        return new SaisOppekavadResponse();
    }

    /**
     * Create an instance of {@link SaisPohi }
     * 
     */
    public SaisPohi createSaisPohi() {
        return new SaisPohi();
    }

    /**
     * Create an instance of {@link SaisPohiResponse }
     * 
     */
    public SaisPohiResponse createSaisPohiResponse() {
        return new SaisPohiResponse();
    }

    /**
     * Create an instance of {@link SaisPohiOpingud }
     * 
     */
    public SaisPohiOpingud createSaisPohiOpingud() {
        return new SaisPohiOpingud();
    }

    /**
     * Create an instance of {@link SaisYldh }
     * 
     */
    public SaisYldh createSaisYldh() {
        return new SaisYldh();
    }

    /**
     * Create an instance of {@link SaisYldhResponse }
     * 
     */
    public SaisYldhResponse createSaisYldhResponse() {
        return new SaisYldhResponse();
    }

    /**
     * Create an instance of {@link SotsList1 }
     * 
     */
    public SotsList1 createSotsList1() {
        return new SotsList1();
    }

    /**
     * Create an instance of {@link SotsList1Response }
     * 
     */
    public SotsList1Response createSotsList1Response() {
        return new SotsList1Response();
    }

    /**
     * Create an instance of {@link SotsList2 }
     * 
     */
    public SotsList2 createSotsList2() {
        return new SotsList2();
    }

    /**
     * Create an instance of {@link SotsList2Response }
     * 
     */
    public SotsList2Response createSotsList2Response() {
        return new SotsList2Response();
    }

    /**
     * Create an instance of {@link SotsOppur1 }
     * 
     */
    public SotsOppur1 createSotsOppur1() {
        return new SotsOppur1();
    }

    /**
     * Create an instance of {@link SotsOppur1Response }
     * 
     */
    public SotsOppur1Response createSotsOppur1Response() {
        return new SotsOppur1Response();
    }

    /**
     * Create an instance of {@link SotsOppurOpingud }
     * 
     */
    public SotsOppurOpingud createSotsOppurOpingud() {
        return new SotsOppurOpingud();
    }

    /**
     * Create an instance of {@link TallhaYldhList }
     * 
     */
    public TallhaYldhList createTallhaYldhList() {
        return new TallhaYldhList();
    }

    /**
     * Create an instance of {@link TallhaYldhListResponse }
     * 
     */
    public TallhaYldhListResponse createTallhaYldhListResponse() {
        return new TallhaYldhListResponse();
    }

    /**
     * Create an instance of {@link TallhaYldhListType }
     * 
     */
    public TallhaYldhListType createTallhaYldhListType() {
        return new TallhaYldhListType();
    }

    /**
     * Create an instance of {@link TallhaYldhList2 }
     * 
     */
    public TallhaYldhList2 createTallhaYldhList2() {
        return new TallhaYldhList2();
    }

    /**
     * Create an instance of {@link TallhaYldhList2Response }
     * 
     */
    public TallhaYldhList2Response createTallhaYldhList2Response() {
        return new TallhaYldhList2Response();
    }

    /**
     * Create an instance of {@link TallhaPedagList }
     * 
     */
    public TallhaPedagList createTallhaPedagList() {
        return new TallhaPedagList();
    }

    /**
     * Create an instance of {@link TallhaPedagListResponse }
     * 
     */
    public TallhaPedagListResponse createTallhaPedagListResponse() {
        return new TallhaPedagListResponse();
    }

    /**
     * Create an instance of {@link TallhaPedagListType }
     * 
     */
    public TallhaPedagListType createTallhaPedagListType() {
        return new TallhaPedagListType();
    }

    /**
     * Create an instance of {@link TallhaOppimineList }
     * 
     */
    public TallhaOppimineList createTallhaOppimineList() {
        return new TallhaOppimineList();
    }

    /**
     * Create an instance of {@link TallhaOppimineListResponse }
     * 
     */
    public TallhaOppimineListResponse createTallhaOppimineListResponse() {
        return new TallhaOppimineListResponse();
    }

    /**
     * Create an instance of {@link TallhaOppimineListType }
     * 
     */
    public TallhaOppimineListType createTallhaOppimineListType() {
        return new TallhaOppimineListType();
    }

    /**
     * Create an instance of {@link TervishoiuametileOppeasutused }
     * 
     */
    public TervishoiuametileOppeasutused createTervishoiuametileOppeasutused() {
        return new TervishoiuametileOppeasutused();
    }

    /**
     * Create an instance of {@link TervishoiuametileOppeasutusedResponse }
     * 
     */
    public TervishoiuametileOppeasutusedResponse createTervishoiuametileOppeasutusedResponse() {
        return new TervishoiuametileOppeasutusedResponse();
    }

    /**
     * Create an instance of {@link TervishoiuametileOppeasutusedList }
     * 
     */
    public TervishoiuametileOppeasutusedList createTervishoiuametileOppeasutusedList() {
        return new TervishoiuametileOppeasutusedList();
    }

    /**
     * Create an instance of {@link TervishoiuametileOppurid }
     * 
     */
    public TervishoiuametileOppurid createTervishoiuametileOppurid() {
        return new TervishoiuametileOppurid();
    }

    /**
     * Create an instance of {@link OasEhisKoodid }
     * 
     */
    public OasEhisKoodid createOasEhisKoodid() {
        return new OasEhisKoodid();
    }

    /**
     * Create an instance of {@link TervishoiuametileOppuridResponse }
     * 
     */
    public TervishoiuametileOppuridResponse createTervishoiuametileOppuridResponse() {
        return new TervishoiuametileOppuridResponse();
    }

    /**
     * Create an instance of {@link TervishoiuametileOppuridType }
     * 
     */
    public TervishoiuametileOppuridType createTervishoiuametileOppuridType() {
        return new TervishoiuametileOppuridType();
    }

    /**
     * Create an instance of {@link TootukassaleKehtivad }
     * 
     */
    public TootukassaleKehtivad createTootukassaleKehtivad() {
        return new TootukassaleKehtivad();
    }

    /**
     * Create an instance of {@link TootukassaleKehtivadIsikukoodid }
     * 
     */
    public TootukassaleKehtivadIsikukoodid createTootukassaleKehtivadIsikukoodid() {
        return new TootukassaleKehtivadIsikukoodid();
    }

    /**
     * Create an instance of {@link TootukassaleKehtivadResponse }
     * 
     */
    public TootukassaleKehtivadResponse createTootukassaleKehtivadResponse() {
        return new TootukassaleKehtivadResponse();
    }

    /**
     * Create an instance of {@link TootukassaleKehtivadIsikud }
     * 
     */
    public TootukassaleKehtivadIsikud createTootukassaleKehtivadIsikud() {
        return new TootukassaleKehtivadIsikud();
    }

    /**
     * Create an instance of {@link TootukassaleKehtivadV2 }
     * 
     */
    public TootukassaleKehtivadV2 createTootukassaleKehtivadV2() {
        return new TootukassaleKehtivadV2();
    }

    /**
     * Create an instance of {@link TootukassaleKehtivadV2Isikukoodid }
     * 
     */
    public TootukassaleKehtivadV2Isikukoodid createTootukassaleKehtivadV2Isikukoodid() {
        return new TootukassaleKehtivadV2Isikukoodid();
    }

    /**
     * Create an instance of {@link TootukassaleKehtivadV2Response }
     * 
     */
    public TootukassaleKehtivadV2Response createTootukassaleKehtivadV2Response() {
        return new TootukassaleKehtivadV2Response();
    }

    /**
     * Create an instance of {@link TootukassaleKehtivadV2Isikud }
     * 
     */
    public TootukassaleKehtivadV2Isikud createTootukassaleKehtivadV2Isikud() {
        return new TootukassaleKehtivadV2Isikud();
    }

    /**
     * Create an instance of {@link TootukassaleOppimisedTellimus }
     * 
     */
    public TootukassaleOppimisedTellimus createTootukassaleOppimisedTellimus() {
        return new TootukassaleOppimisedTellimus();
    }

    /**
     * Create an instance of {@link TootukassaleIsikukoodidTellimus }
     * 
     */
    public TootukassaleIsikukoodidTellimus createTootukassaleIsikukoodidTellimus() {
        return new TootukassaleIsikukoodidTellimus();
    }

    /**
     * Create an instance of {@link TootukassaleOppimisedTellimusResponse }
     * 
     */
    public TootukassaleOppimisedTellimusResponse createTootukassaleOppimisedTellimusResponse() {
        return new TootukassaleOppimisedTellimusResponse();
    }

    /**
     * Create an instance of {@link TootukassaleOppimisedVastus }
     * 
     */
    public TootukassaleOppimisedVastus createTootukassaleOppimisedVastus() {
        return new TootukassaleOppimisedVastus();
    }

    /**
     * Create an instance of {@link TootukassaleOppimisedVastusResponse }
     * 
     */
    public TootukassaleOppimisedVastusResponse createTootukassaleOppimisedVastusResponse() {
        return new TootukassaleOppimisedVastusResponse();
    }

    /**
     * Create an instance of {@link TootukassaleTegevusload }
     * 
     */
    public TootukassaleTegevusload createTootukassaleTegevusload() {
        return new TootukassaleTegevusload();
    }

    /**
     * Create an instance of {@link TootukassaleTegevusloadResponse }
     * 
     */
    public TootukassaleTegevusloadResponse createTootukassaleTegevusloadResponse() {
        return new TootukassaleTegevusloadResponse();
    }

    /**
     * Create an instance of {@link TkMajandustegevusteated }
     * 
     */
    public TkMajandustegevusteated createTkMajandustegevusteated() {
        return new TkMajandustegevusteated();
    }

    /**
     * Create an instance of {@link TkTegevusload }
     * 
     */
    public TkTegevusload createTkTegevusload() {
        return new TkTegevusload();
    }

    /**
     * Create an instance of {@link MtsysTegevusnaitajateResponse }
     * 
     */
    public MtsysTegevusnaitajateResponse createMtsysTegevusnaitajateResponse() {
        return new MtsysTegevusnaitajateResponse();
    }

    /**
     * Create an instance of {@link EisOppeasutus }
     * 
     */
    public EisOppeasutus createEisOppeasutus() {
        return new EisOppeasutus();
    }

    /**
     * Create an instance of {@link OppekeeledList }
     * 
     */
    public OppekeeledList createOppekeeledList() {
        return new OppekeeledList();
    }

    /**
     * Create an instance of {@link OppetasemedList }
     * 
     */
    public OppetasemedList createOppetasemedList() {
        return new OppetasemedList();
    }

    /**
     * Create an instance of {@link EsrOppeasutus }
     * 
     */
    public EsrOppeasutus createEsrOppeasutus() {
        return new EsrOppeasutus();
    }

    /**
     * Create an instance of {@link EsrOppekava }
     * 
     */
    public EsrOppekava createEsrOppekava() {
        return new EsrOppekava();
    }

    /**
     * Create an instance of {@link EsrSpordiala }
     * 
     */
    public EsrSpordiala createEsrSpordiala() {
        return new EsrSpordiala();
    }

    /**
     * Create an instance of {@link EsrPedagoog }
     * 
     */
    public EsrPedagoog createEsrPedagoog() {
        return new EsrPedagoog();
    }

    /**
     * Create an instance of {@link EeIsikukaartIsikuandmed }
     * 
     */
    public EeIsikukaartIsikuandmed createEeIsikukaartIsikuandmed() {
        return new EeIsikukaartIsikuandmed();
    }

    /**
     * Create an instance of {@link EeIsikukaartOppelaenOigus }
     * 
     */
    public EeIsikukaartOppelaenOigus createEeIsikukaartOppelaenOigus() {
        return new EeIsikukaartOppelaenOigus();
    }

    /**
     * Create an instance of {@link EeIsikukaartOping }
     * 
     */
    public EeIsikukaartOping createEeIsikukaartOping() {
        return new EeIsikukaartOping();
    }

    /**
     * Create an instance of {@link EeIsikukaartOpingKutseEelkoolitus }
     * 
     */
    public EeIsikukaartOpingKutseEelkoolitus createEeIsikukaartOpingKutseEelkoolitus() {
        return new EeIsikukaartOpingKutseEelkoolitus();
    }

    /**
     * Create an instance of {@link EeIsikukaartOppekava }
     * 
     */
    public EeIsikukaartOppekava createEeIsikukaartOppekava() {
        return new EeIsikukaartOppekava();
    }

    /**
     * Create an instance of {@link IsikukaartPeriodicalAndmed }
     * 
     */
    public IsikukaartPeriodicalAndmed createIsikukaartPeriodicalAndmed() {
        return new IsikukaartPeriodicalAndmed();
    }

    /**
     * Create an instance of {@link EeIsikukaartOppekavaTaitmine }
     * 
     */
    public EeIsikukaartOppekavaTaitmine createEeIsikukaartOppekavaTaitmine() {
        return new EeIsikukaartOppekavaTaitmine();
    }

    /**
     * Create an instance of {@link EeIsikukaartTootamine }
     * 
     */
    public EeIsikukaartTootamine createEeIsikukaartTootamine() {
        return new EeIsikukaartTootamine();
    }

    /**
     * Create an instance of {@link EeIsikukaartOppeaine }
     * 
     */
    public EeIsikukaartOppeaine createEeIsikukaartOppeaine() {
        return new EeIsikukaartOppeaine();
    }

    /**
     * Create an instance of {@link EeIsikukaartTaiendkoolitus }
     * 
     */
    public EeIsikukaartTaiendkoolitus createEeIsikukaartTaiendkoolitus() {
        return new EeIsikukaartTaiendkoolitus();
    }

    /**
     * Create an instance of {@link EeIsikukaartTasemeharidus }
     * 
     */
    public EeIsikukaartTasemeharidus createEeIsikukaartTasemeharidus() {
        return new EeIsikukaartTasemeharidus();
    }

    /**
     * Create an instance of {@link EeIsikukaartKvalifikatsioon }
     * 
     */
    public EeIsikukaartKvalifikatsioon createEeIsikukaartKvalifikatsioon() {
        return new EeIsikukaartKvalifikatsioon();
    }

    /**
     * Create an instance of {@link EylIsicParing }
     * 
     */
    public EylIsicParing createEylIsicParing() {
        return new EylIsicParing();
    }

    /**
     * Create an instance of {@link EylIsicVastus }
     * 
     */
    public EylIsicVastus createEylIsicVastus() {
        return new EylIsicVastus();
    }

    /**
     * Create an instance of {@link EylOppimine }
     * 
     */
    public EylOppimine createEylOppimine() {
        return new EylOppimine();
    }

    /**
     * Create an instance of {@link EylOpetamine }
     * 
     */
    public EylOpetamine createEylOpetamine() {
        return new EylOpetamine();
    }

    /**
     * Create an instance of {@link EylOpetamineAmetikoht }
     * 
     */
    public EylOpetamineAmetikoht createEylOpetamineAmetikoht() {
        return new EylOpetamineAmetikoht();
    }

    /**
     * Create an instance of {@link IrOppeasutused }
     * 
     */
    public IrOppeasutused createIrOppeasutused() {
        return new IrOppeasutused();
    }

    /**
     * Create an instance of {@link IrOppeasutus }
     * 
     */
    public IrOppeasutus createIrOppeasutus() {
        return new IrOppeasutus();
    }

    /**
     * Create an instance of {@link Rollid }
     * 
     */
    public Rollid createRollid() {
        return new Rollid();
    }

    /**
     * Create an instance of {@link Roll }
     * 
     */
    public Roll createRoll() {
        return new Roll();
    }

    /**
     * Create an instance of {@link KmaLopetanudIsikItem }
     * 
     */
    public KmaLopetanudIsikItem createKmaLopetanudIsikItem() {
        return new KmaLopetanudIsikItem();
    }

    /**
     * Create an instance of {@link KmaLopetanudItem }
     * 
     */
    public KmaLopetanudItem createKmaLopetanudItem() {
        return new KmaLopetanudItem();
    }

    /**
     * Create an instance of {@link MuudatusteAjalugu }
     * 
     */
    public MuudatusteAjalugu createMuudatusteAjalugu() {
        return new MuudatusteAjalugu();
    }

    /**
     * Create an instance of {@link Tunnistus }
     * 
     */
    public Tunnistus createTunnistus() {
        return new Tunnistus();
    }

    /**
     * Create an instance of {@link TunnistusHindedKohustuslik }
     * 
     */
    public TunnistusHindedKohustuslik createTunnistusHindedKohustuslik() {
        return new TunnistusHindedKohustuslik();
    }

    /**
     * Create an instance of {@link TunnistusHindedValik }
     * 
     */
    public TunnistusHindedValik createTunnistusHindedValik() {
        return new TunnistusHindedValik();
    }

    /**
     * Create an instance of {@link TunnistusHindedEksam }
     * 
     */
    public TunnistusHindedEksam createTunnistusHindedEksam() {
        return new TunnistusHindedEksam();
    }

    /**
     * Create an instance of {@link KohustuslikHinne }
     * 
     */
    public KohustuslikHinne createKohustuslikHinne() {
        return new KohustuslikHinne();
    }

    /**
     * Create an instance of {@link ValikHinne }
     * 
     */
    public ValikHinne createValikHinne() {
        return new ValikHinne();
    }

    /**
     * Create an instance of {@link EksamHinne }
     * 
     */
    public EksamHinne createEksamHinne() {
        return new EksamHinne();
    }

    /**
     * Create an instance of {@link KoolideleKehtivadIsik }
     * 
     */
    public KoolideleKehtivadIsik createKoolideleKehtivadIsik() {
        return new KoolideleKehtivadIsik();
    }

    /**
     * Create an instance of {@link KoolideleIsik }
     * 
     */
    public KoolideleIsik createKoolideleIsik() {
        return new KoolideleIsik();
    }

    /**
     * Create an instance of {@link KoolOppimine }
     * 
     */
    public KoolOppimine createKoolOppimine() {
        return new KoolOppimine();
    }

    /**
     * Create an instance of {@link KoolAkadPuhkus }
     * 
     */
    public KoolAkadPuhkus createKoolAkadPuhkus() {
        return new KoolAkadPuhkus();
    }

    /**
     * Create an instance of {@link KasutatudEsfRahastus }
     * 
     */
    public KasutatudEsfRahastus createKasutatudEsfRahastus() {
        return new KasutatudEsfRahastus();
    }

    /**
     * Create an instance of {@link AlusOppeasutus }
     * 
     */
    public AlusOppeasutus createAlusOppeasutus() {
        return new AlusOppeasutus();
    }

    /**
     * Create an instance of {@link AlusRyhm }
     * 
     */
    public AlusRyhm createAlusRyhm() {
        return new AlusRyhm();
    }

    /**
     * Create an instance of {@link AlusOppur }
     * 
     */
    public AlusOppur createAlusOppur() {
        return new AlusOppur();
    }

    /**
     * Create an instance of {@link AlusIsikuandmed }
     * 
     */
    public AlusIsikuandmed createAlusIsikuandmed() {
        return new AlusIsikuandmed();
    }

    /**
     * Create an instance of {@link AlusLisa }
     * 
     */
    public AlusLisa createAlusLisa() {
        return new AlusLisa();
    }

    /**
     * Create an instance of {@link AlusMuuda }
     * 
     */
    public AlusMuuda createAlusMuuda() {
        return new AlusMuuda();
    }

    /**
     * Create an instance of {@link AlusIsikuandmedDetail }
     * 
     */
    public AlusIsikuandmedDetail createAlusIsikuandmedDetail() {
        return new AlusIsikuandmedDetail();
    }

    /**
     * Create an instance of {@link AlusOppurOppimine }
     * 
     */
    public AlusOppurOppimine createAlusOppurOppimine() {
        return new AlusOppurOppimine();
    }

    /**
     * Create an instance of {@link AlusOppurFiliaal }
     * 
     */
    public AlusOppurFiliaal createAlusOppurFiliaal() {
        return new AlusOppurFiliaal();
    }

    /**
     * Create an instance of {@link AlusOppurTugiteenus }
     * 
     */
    public AlusOppurTugiteenus createAlusOppurTugiteenus() {
        return new AlusOppurTugiteenus();
    }

    /**
     * Create an instance of {@link AlusOppurLopeta }
     * 
     */
    public AlusOppurLopeta createAlusOppurLopeta() {
        return new AlusOppurLopeta();
    }

    /**
     * Create an instance of {@link AlusOppurKoolIsikId }
     * 
     */
    public AlusOppurKoolIsikId createAlusOppurKoolIsikId() {
        return new AlusOppurKoolIsikId();
    }

    /**
     * Create an instance of {@link AlusOppurIsikukood }
     * 
     */
    public AlusOppurIsikukood createAlusOppurIsikukood() {
        return new AlusOppurIsikukood();
    }

    /**
     * Create an instance of {@link KhlOppeasutus }
     * 
     */
    public KhlOppeasutus createKhlOppeasutus() {
        return new KhlOppeasutus();
    }

    /**
     * Create an instance of {@link KhlOppur }
     * 
     */
    public KhlOppur createKhlOppur() {
        return new KhlOppur();
    }

    /**
     * Create an instance of {@link KhlLisamine }
     * 
     */
    public KhlLisamine createKhlLisamine() {
        return new KhlLisamine();
    }

    /**
     * Create an instance of {@link KhlIsikuandmedLisa }
     * 
     */
    public KhlIsikuandmedLisa createKhlIsikuandmedLisa() {
        return new KhlIsikuandmedLisa();
    }

    /**
     * Create an instance of {@link KhlKorgharidusLisa }
     * 
     */
    public KhlKorgharidusLisa createKhlKorgharidusLisa() {
        return new KhlKorgharidusLisa();
    }

    /**
     * Create an instance of {@link KhlMuutmine }
     * 
     */
    public KhlMuutmine createKhlMuutmine() {
        return new KhlMuutmine();
    }

    /**
     * Create an instance of {@link KhlMuutmineIdList }
     * 
     */
    public KhlMuutmineIdList createKhlMuutmineIdList() {
        return new KhlMuutmineIdList();
    }

    /**
     * Create an instance of {@link KhlMuutmineIsikukood }
     * 
     */
    public KhlMuutmineIsikukood createKhlMuutmineIsikukood() {
        return new KhlMuutmineIsikukood();
    }

    /**
     * Create an instance of {@link KhlKorgharidusMuuda }
     * 
     */
    public KhlKorgharidusMuuda createKhlKorgharidusMuuda() {
        return new KhlKorgharidusMuuda();
    }

    /**
     * Create an instance of {@link KhlIsikuandmeteMuutus }
     * 
     */
    public KhlIsikuandmeteMuutus createKhlIsikuandmeteMuutus() {
        return new KhlIsikuandmeteMuutus();
    }

    /**
     * Create an instance of {@link KhlRkKohaleVastuvotmine }
     * 
     */
    public KhlRkKohaleVastuvotmine createKhlRkKohaleVastuvotmine() {
        return new KhlRkKohaleVastuvotmine();
    }

    /**
     * Create an instance of {@link KhlKursuseMuutus }
     * 
     */
    public KhlKursuseMuutus createKhlKursuseMuutus() {
        return new KhlKursuseMuutus();
    }

    /**
     * Create an instance of {@link KhlOppevormiMuutus }
     * 
     */
    public KhlOppevormiMuutus createKhlOppevormiMuutus() {
        return new KhlOppevormiMuutus();
    }

    /**
     * Create an instance of {@link KhlOppekavaMuutus }
     * 
     */
    public KhlOppekavaMuutus createKhlOppekavaMuutus() {
        return new KhlOppekavaMuutus();
    }

    /**
     * Create an instance of {@link KhlOppeasutuseLopetamine }
     * 
     */
    public KhlOppeasutuseLopetamine createKhlOppeasutuseLopetamine() {
        return new KhlOppeasutuseLopetamine();
    }

    /**
     * Create an instance of {@link KhlOppeasutusestValjaarvamine }
     * 
     */
    public KhlOppeasutusestValjaarvamine createKhlOppeasutusestValjaarvamine() {
        return new KhlOppeasutusestValjaarvamine();
    }

    /**
     * Create an instance of {@link KhlAkadPuhkusAlgus }
     * 
     */
    public KhlAkadPuhkusAlgus createKhlAkadPuhkusAlgus() {
        return new KhlAkadPuhkusAlgus();
    }

    /**
     * Create an instance of {@link KhlErivajadusedArr }
     * 
     */
    public KhlErivajadusedArr createKhlErivajadusedArr() {
        return new KhlErivajadusedArr();
    }

    /**
     * Create an instance of {@link KhlMuudAndmedMuutmine }
     * 
     */
    public KhlMuudAndmedMuutmine createKhlMuudAndmedMuutmine() {
        return new KhlMuudAndmedMuutmine();
    }

    /**
     * Create an instance of {@link KhlEnnistamine }
     * 
     */
    public KhlEnnistamine createKhlEnnistamine() {
        return new KhlEnnistamine();
    }

    /**
     * Create an instance of {@link KhlOiendType }
     * 
     */
    public KhlOiendType createKhlOiendType() {
        return new KhlOiendType();
    }

    /**
     * Create an instance of {@link KhlVoorkeeledArr }
     * 
     */
    public KhlVoorkeeledArr createKhlVoorkeeledArr() {
        return new KhlVoorkeeledArr();
    }

    /**
     * Create an instance of {@link KhlJuhendamineArr }
     * 
     */
    public KhlJuhendamineArr createKhlJuhendamineArr() {
        return new KhlJuhendamineArr();
    }

    /**
     * Create an instance of {@link KhlJuhendamineType }
     * 
     */
    public KhlJuhendamineType createKhlJuhendamineType() {
        return new KhlJuhendamineType();
    }

    /**
     * Create an instance of {@link KhlOppejoudType }
     * 
     */
    public KhlOppejoudType createKhlOppejoudType() {
        return new KhlOppejoudType();
    }

    /**
     * Create an instance of {@link KhlOppejoudIsikuandmedType }
     * 
     */
    public KhlOppejoudIsikuandmedType createKhlOppejoudIsikuandmedType() {
        return new KhlOppejoudIsikuandmedType();
    }

    /**
     * Create an instance of {@link KhlOppekavaTaitmine }
     * 
     */
    public KhlOppekavaTaitmine createKhlOppekavaTaitmine() {
        return new KhlOppekavaTaitmine();
    }

    /**
     * Create an instance of {@link KhlLyhiajaliseltValismaal }
     * 
     */
    public KhlLyhiajaliseltValismaal createKhlLyhiajaliseltValismaal() {
        return new KhlLyhiajaliseltValismaal();
    }

    /**
     * Create an instance of {@link KhlVOTAArr }
     * 
     */
    public KhlVOTAArr createKhlVOTAArr() {
        return new KhlVOTAArr();
    }

    /**
     * Create an instance of {@link KhlVOTA }
     * 
     */
    public KhlVOTA createKhlVOTA() {
        return new KhlVOTA();
    }

    /**
     * Create an instance of {@link KhlStipendiumArr }
     * 
     */
    public KhlStipendiumArr createKhlStipendiumArr() {
        return new KhlStipendiumArr();
    }

    /**
     * Create an instance of {@link KhlStipendium }
     * 
     */
    public KhlStipendium createKhlStipendium() {
        return new KhlStipendium();
    }

    /**
     * Create an instance of {@link KhlStipendiumKustutamine }
     * 
     */
    public KhlStipendiumKustutamine createKhlStipendiumKustutamine() {
        return new KhlStipendiumKustutamine();
    }

    /**
     * Create an instance of {@link KhlLyhiAjaValisOppur }
     * 
     */
    public KhlLyhiAjaValisOppur createKhlLyhiAjaValisOppur() {
        return new KhlLyhiAjaValisOppur();
    }

    /**
     * Create an instance of {@link KhlLyhiAjaValisOppurSalvestamine }
     * 
     */
    public KhlLyhiAjaValisOppurSalvestamine createKhlLyhiAjaValisOppurSalvestamine() {
        return new KhlLyhiAjaValisOppurSalvestamine();
    }

    /**
     * Create an instance of {@link KhlLyhiAjaValisOppurKustutamine }
     * 
     */
    public KhlLyhiAjaValisOppurKustutamine createKhlLyhiAjaValisOppurKustutamine() {
        return new KhlLyhiAjaValisOppurKustutamine();
    }

    /**
     * Create an instance of {@link Oppejoud }
     * 
     */
    public Oppejoud createOppejoud() {
        return new Oppejoud();
    }

    /**
     * Create an instance of {@link OppejoudIsikuandmed }
     * 
     */
    public OppejoudIsikuandmed createOppejoudIsikuandmed() {
        return new OppejoudIsikuandmed();
    }

    /**
     * Create an instance of {@link OppejoudAmetikoht }
     * 
     */
    public OppejoudAmetikoht createOppejoudAmetikoht() {
        return new OppejoudAmetikoht();
    }

    /**
     * Create an instance of {@link Oppeaine }
     * 
     */
    public Oppeaine createOppeaine() {
        return new Oppeaine();
    }

    /**
     * Create an instance of {@link OppejoudKvalifikatsioon }
     * 
     */
    public OppejoudKvalifikatsioon createOppejoudKvalifikatsioon() {
        return new OppejoudKvalifikatsioon();
    }

    /**
     * Create an instance of {@link OppejoudLyhiajalineMobiilsus }
     * 
     */
    public OppejoudLyhiajalineMobiilsus createOppejoudLyhiajalineMobiilsus() {
        return new OppejoudLyhiajalineMobiilsus();
    }

    /**
     * Create an instance of {@link Oppeasutus }
     * 
     */
    public Oppeasutus createOppeasutus() {
        return new Oppeasutus();
    }

    /**
     * Create an instance of {@link PedagoogKustutaAlam }
     * 
     */
    public PedagoogKustutaAlam createPedagoogKustutaAlam() {
        return new PedagoogKustutaAlam();
    }

    /**
     * Create an instance of {@link PedagoogAmetikohtType }
     * 
     */
    public PedagoogAmetikohtType createPedagoogAmetikohtType() {
        return new PedagoogAmetikohtType();
    }

    /**
     * Create an instance of {@link PedagoogAine }
     * 
     */
    public PedagoogAine createPedagoogAine() {
        return new PedagoogAine();
    }

    /**
     * Create an instance of {@link PedagoogAmetikohtId }
     * 
     */
    public PedagoogAmetikohtId createPedagoogAmetikohtId() {
        return new PedagoogAmetikohtId();
    }

    /**
     * Create an instance of {@link PedagoogTaiendkoolitus }
     * 
     */
    public PedagoogTaiendkoolitus createPedagoogTaiendkoolitus() {
        return new PedagoogTaiendkoolitus();
    }

    /**
     * Create an instance of {@link PedagoogTaiendkoolitusId }
     * 
     */
    public PedagoogTaiendkoolitusId createPedagoogTaiendkoolitusId() {
        return new PedagoogTaiendkoolitusId();
    }

    /**
     * Create an instance of {@link PedagoogTasemekoolitus }
     * 
     */
    public PedagoogTasemekoolitus createPedagoogTasemekoolitus() {
        return new PedagoogTasemekoolitus();
    }

    /**
     * Create an instance of {@link PedagoogTasemekoolitusId }
     * 
     */
    public PedagoogTasemekoolitusId createPedagoogTasemekoolitusId() {
        return new PedagoogTasemekoolitusId();
    }

    /**
     * Create an instance of {@link PedagoogAmetijark }
     * 
     */
    public PedagoogAmetijark createPedagoogAmetijark() {
        return new PedagoogAmetijark();
    }

    /**
     * Create an instance of {@link PedagoogAmetijarkId }
     * 
     */
    public PedagoogAmetijarkId createPedagoogAmetijarkId() {
        return new PedagoogAmetijarkId();
    }

    /**
     * Create an instance of {@link PedagoogRiigikeel }
     * 
     */
    public PedagoogRiigikeel createPedagoogRiigikeel() {
        return new PedagoogRiigikeel();
    }

    /**
     * Create an instance of {@link PedagoogRiigikeelId }
     * 
     */
    public PedagoogRiigikeelId createPedagoogRiigikeelId() {
        return new PedagoogRiigikeelId();
    }

    /**
     * Create an instance of {@link Pedagoog }
     * 
     */
    public Pedagoog createPedagoog() {
        return new Pedagoog();
    }

    /**
     * Create an instance of {@link YhlOppeasutus }
     * 
     */
    public YhlOppeasutus createYhlOppeasutus() {
        return new YhlOppeasutus();
    }

    /**
     * Create an instance of {@link YhlOppur }
     * 
     */
    public YhlOppur createYhlOppur() {
        return new YhlOppur();
    }

    /**
     * Create an instance of {@link YhlIsikuandmed }
     * 
     */
    public YhlIsikuandmed createYhlIsikuandmed() {
        return new YhlIsikuandmed();
    }

    /**
     * Create an instance of {@link YhlOppurIsikuandmed }
     * 
     */
    public YhlOppurIsikuandmed createYhlOppurIsikuandmed() {
        return new YhlOppurIsikuandmed();
    }

    /**
     * Create an instance of {@link YhlMuutmineIds }
     * 
     */
    public YhlMuutmineIds createYhlMuutmineIds() {
        return new YhlMuutmineIds();
    }

    /**
     * Create an instance of {@link YhlMuutmineIsikukood }
     * 
     */
    public YhlMuutmineIsikukood createYhlMuutmineIsikukood() {
        return new YhlMuutmineIsikukood();
    }

    /**
     * Create an instance of {@link YhlElukoht }
     * 
     */
    public YhlElukoht createYhlElukoht() {
        return new YhlElukoht();
    }

    /**
     * Create an instance of {@link YhlVoorkeeled }
     * 
     */
    public YhlVoorkeeled createYhlVoorkeeled() {
        return new YhlVoorkeeled();
    }

    /**
     * Create an instance of {@link YhlVoorkeel }
     * 
     */
    public YhlVoorkeel createYhlVoorkeel() {
        return new YhlVoorkeel();
    }

    /**
     * Create an instance of {@link YhlLisaandmed }
     * 
     */
    public YhlLisaandmed createYhlLisaandmed() {
        return new YhlLisaandmed();
    }

    /**
     * Create an instance of {@link YhlPuudulikudAastahinded }
     * 
     */
    public YhlPuudulikudAastahinded createYhlPuudulikudAastahinded() {
        return new YhlPuudulikudAastahinded();
    }

    /**
     * Create an instance of {@link YhlPuudulikAastahinne }
     * 
     */
    public YhlPuudulikAastahinne createYhlPuudulikAastahinne() {
        return new YhlPuudulikAastahinne();
    }

    /**
     * Create an instance of {@link YhlErivajadusedTugiteenused }
     * 
     */
    public YhlErivajadusedTugiteenused createYhlErivajadusedTugiteenused() {
        return new YhlErivajadusedTugiteenused();
    }

    /**
     * Create an instance of {@link YhlErivajadused }
     * 
     */
    public YhlErivajadused createYhlErivajadused() {
        return new YhlErivajadused();
    }

    /**
     * Create an instance of {@link YhlTugiteenused }
     * 
     */
    public YhlTugiteenused createYhlTugiteenused() {
        return new YhlTugiteenused();
    }

    /**
     * Create an instance of {@link YhlTugiteenus }
     * 
     */
    public YhlTugiteenus createYhlTugiteenus() {
        return new YhlTugiteenus();
    }

    /**
     * Create an instance of {@link YhlYldharidusLisa }
     * 
     */
    public YhlYldharidusLisa createYhlYldharidusLisa() {
        return new YhlYldharidusLisa();
    }

    /**
     * Create an instance of {@link YhlYldharidusMuuda }
     * 
     */
    public YhlYldharidusMuuda createYhlYldharidusMuuda() {
        return new YhlYldharidusMuuda();
    }

    /**
     * Create an instance of {@link YhlJargmiseKlassi }
     * 
     */
    public YhlJargmiseKlassi createYhlJargmiseKlassi() {
        return new YhlJargmiseKlassi();
    }

    /**
     * Create an instance of {@link YhlKlassiOkMuutus }
     * 
     */
    public YhlKlassiOkMuutus createYhlKlassiOkMuutus() {
        return new YhlKlassiOkMuutus();
    }

    /**
     * Create an instance of {@link YhlOppekeelMuutus }
     * 
     */
    public YhlOppekeelMuutus createYhlOppekeelMuutus() {
        return new YhlOppekeelMuutus();
    }

    /**
     * Create an instance of {@link YhlOppevormMuutus }
     * 
     */
    public YhlOppevormMuutus createYhlOppevormMuutus() {
        return new YhlOppevormMuutus();
    }

    /**
     * Create an instance of {@link YhlOVMOppeained }
     * 
     */
    public YhlOVMOppeained createYhlOVMOppeained() {
        return new YhlOVMOppeained();
    }

    /**
     * Create an instance of {@link YhlOVMOppeaine }
     * 
     */
    public YhlOVMOppeaine createYhlOVMOppeaine() {
        return new YhlOVMOppeaine();
    }

    /**
     * Create an instance of {@link YhlOppeasLahkumine }
     * 
     */
    public YhlOppeasLahkumine createYhlOppeasLahkumine() {
        return new YhlOppeasLahkumine();
    }

    /**
     * Create an instance of {@link YhlTeisAndMuutus }
     * 
     */
    public YhlTeisAndMuutus createYhlTeisAndMuutus() {
        return new YhlTeisAndMuutus();
    }

    /**
     * Create an instance of {@link YhlPohtaPuudumine }
     * 
     */
    public YhlPohtaPuudumine createYhlPohtaPuudumine() {
        return new YhlPohtaPuudumine();
    }

    /**
     * Create an instance of {@link MkmLopetanudKorgharidus }
     * 
     */
    public MkmLopetanudKorgharidus createMkmLopetanudKorgharidus() {
        return new MkmLopetanudKorgharidus();
    }

    /**
     * Create an instance of {@link Maakond }
     * 
     */
    public Maakond createMaakond() {
        return new Maakond();
    }

    /**
     * Create an instance of {@link Omavalitsused }
     * 
     */
    public Omavalitsused createOmavalitsused() {
        return new Omavalitsused();
    }

    /**
     * Create an instance of {@link Omavalitsus }
     * 
     */
    public Omavalitsus createOmavalitsus() {
        return new Omavalitsus();
    }

    /**
     * Create an instance of {@link Asulad }
     * 
     */
    public Asulad createAsulad() {
        return new Asulad();
    }

    /**
     * Create an instance of {@link Asula }
     * 
     */
    public Asula createAsula() {
        return new Asula();
    }

    /**
     * Create an instance of {@link FailiTyyp }
     * 
     */
    public FailiTyyp createFailiTyyp() {
        return new FailiTyyp();
    }

    /**
     * Create an instance of {@link Oppeasutused }
     * 
     */
    public Oppeasutused createOppeasutused() {
        return new Oppeasutused();
    }

    /**
     * Create an instance of {@link OppeasutusTegevusload }
     * 
     */
    public OppeasutusTegevusload createOppeasutusTegevusload() {
        return new OppeasutusTegevusload();
    }

    /**
     * Create an instance of {@link Tegevusload }
     * 
     */
    public Tegevusload createTegevusload() {
        return new Tegevusload();
    }

    /**
     * Create an instance of {@link Tegevusluba }
     * 
     */
    public Tegevusluba createTegevusluba() {
        return new Tegevusluba();
    }

    /**
     * Create an instance of {@link Tegevusnaitajad }
     * 
     */
    public Tegevusnaitajad createTegevusnaitajad() {
        return new Tegevusnaitajad();
    }

    /**
     * Create an instance of {@link Tegevusnaitaja }
     * 
     */
    public Tegevusnaitaja createTegevusnaitaja() {
        return new Tegevusnaitaja();
    }

    /**
     * Create an instance of {@link Peatamine }
     * 
     */
    public Peatamine createPeatamine() {
        return new Peatamine();
    }

    /**
     * Create an instance of {@link KovPortaaliVastusOppeasutus }
     * 
     */
    public KovPortaaliVastusOppeasutus createKovPortaaliVastusOppeasutus() {
        return new KovPortaaliVastusOppeasutus();
    }

    /**
     * Create an instance of {@link KovPortaaliVastusKeeled }
     * 
     */
    public KovPortaaliVastusKeeled createKovPortaaliVastusKeeled() {
        return new KovPortaaliVastusKeeled();
    }

    /**
     * Create an instance of {@link OasHaldaja }
     * 
     */
    public OasHaldaja createOasHaldaja() {
        return new OasHaldaja();
    }

    /**
     * Create an instance of {@link EisOppuridOppeasutused }
     * 
     */
    public EisOppuridOppeasutused createEisOppuridOppeasutused() {
        return new EisOppuridOppeasutused();
    }

    /**
     * Create an instance of {@link EisOppimine }
     * 
     */
    public EisOppimine createEisOppimine() {
        return new EisOppimine();
    }

    /**
     * Create an instance of {@link EisAmetikoht }
     * 
     */
    public EisAmetikoht createEisAmetikoht() {
        return new EisAmetikoht();
    }

    /**
     * Create an instance of {@link OppeainedList }
     * 
     */
    public OppeainedList createOppeainedList() {
        return new OppeainedList();
    }

    /**
     * Create an instance of {@link PolYld }
     * 
     */
    public PolYld createPolYld() {
        return new PolYld();
    }

    /**
     * Create an instance of {@link SaisOppeasutus }
     * 
     */
    public SaisOppeasutus createSaisOppeasutus() {
        return new SaisOppeasutus();
    }

    /**
     * Create an instance of {@link SaisKorgkOping }
     * 
     */
    public SaisKorgkOping createSaisKorgkOping() {
        return new SaisKorgkOping();
    }

    /**
     * Create an instance of {@link Oppekeel }
     * 
     */
    public Oppekeel createOppekeel() {
        return new Oppekeel();
    }

    /**
     * Create an instance of {@link Yhisope }
     * 
     */
    public Yhisope createYhisope() {
        return new Yhisope();
    }

    /**
     * Create an instance of {@link Oppimine }
     * 
     */
    public Oppimine createOppimine() {
        return new Oppimine();
    }

    /**
     * Create an instance of {@link TallhaIsik }
     * 
     */
    public TallhaIsik createTallhaIsik() {
        return new TallhaIsik();
    }

    /**
     * Create an instance of {@link TallhaPedagoog }
     * 
     */
    public TallhaPedagoog createTallhaPedagoog() {
        return new TallhaPedagoog();
    }

    /**
     * Create an instance of {@link TallhaOppimine }
     * 
     */
    public TallhaOppimine createTallhaOppimine() {
        return new TallhaOppimine();
    }

    /**
     * Create an instance of {@link TervishoiuametileOppeasutus }
     * 
     */
    public TervishoiuametileOppeasutus createTervishoiuametileOppeasutus() {
        return new TervishoiuametileOppeasutus();
    }

    /**
     * Create an instance of {@link TervishoiuametileKeeled }
     * 
     */
    public TervishoiuametileKeeled createTervishoiuametileKeeled() {
        return new TervishoiuametileKeeled();
    }

    /**
     * Create an instance of {@link TervishoiuametileOppur }
     * 
     */
    public TervishoiuametileOppur createTervishoiuametileOppur() {
        return new TervishoiuametileOppur();
    }

    /**
     * Create an instance of {@link TootukassaleKehtivadIsik }
     * 
     */
    public TootukassaleKehtivadIsik createTootukassaleKehtivadIsik() {
        return new TootukassaleKehtivadIsik();
    }

    /**
     * Create an instance of {@link TkOppimine }
     * 
     */
    public TkOppimine createTkOppimine() {
        return new TkOppimine();
    }

    /**
     * Create an instance of {@link TkAkadPuhkus }
     * 
     */
    public TkAkadPuhkus createTkAkadPuhkus() {
        return new TkAkadPuhkus();
    }

    /**
     * Create an instance of {@link TootukassaleKehtivadV2Isik }
     * 
     */
    public TootukassaleKehtivadV2Isik createTootukassaleKehtivadV2Isik() {
        return new TootukassaleKehtivadV2Isik();
    }

    /**
     * Create an instance of {@link TkV2Oppimine }
     * 
     */
    public TkV2Oppimine createTkV2Oppimine() {
        return new TkV2Oppimine();
    }

    /**
     * Create an instance of {@link TkV2AkadPuhkus }
     * 
     */
    public TkV2AkadPuhkus createTkV2AkadPuhkus() {
        return new TkV2AkadPuhkus();
    }

    /**
     * Create an instance of {@link TkV2Oppevorm }
     * 
     */
    public TkV2Oppevorm createTkV2Oppevorm() {
        return new TkV2Oppevorm();
    }

    /**
     * Create an instance of {@link TkV2Oppekoormus }
     * 
     */
    public TkV2Oppekoormus createTkV2Oppekoormus() {
        return new TkV2Oppekoormus();
    }

    /**
     * Create an instance of {@link TkV2FinAllikas }
     * 
     */
    public TkV2FinAllikas createTkV2FinAllikas() {
        return new TkV2FinAllikas();
    }

    /**
     * Create an instance of {@link TkViga }
     * 
     */
    public TkViga createTkViga() {
        return new TkViga();
    }

    /**
     * Create an instance of {@link TkMajandustegevusteade }
     * 
     */
    public TkMajandustegevusteade createTkMajandustegevusteade() {
        return new TkMajandustegevusteade();
    }

    /**
     * Create an instance of {@link TkTegevusluba }
     * 
     */
    public TkTegevusluba createTkTegevusluba() {
        return new TkTegevusluba();
    }

    /**
     * Create an instance of {@link Juriidiline }
     * 
     */
    public Juriidiline createJuriidiline() {
        return new Juriidiline();
    }

    /**
     * Create an instance of {@link Kontakt }
     * 
     */
    public Kontakt createKontakt() {
        return new Kontakt();
    }

    /**
     * Create an instance of {@link Message }
     * 
     */
    public Message createMessage() {
        return new Message();
    }

    /**
     * Create an instance of {@link KorgkOppetoo }
     * 
     */
    public KorgkOppetoo createKorgkOppetoo() {
        return new KorgkOppetoo();
    }

    /**
     * Create an instance of {@link Dokument }
     * 
     */
    public Dokument createDokument() {
        return new Dokument();
    }

    /**
     * Create an instance of {@link MtsysOppeasutusAndmed }
     * 
     */
    public MtsysOppeasutusAndmed createMtsysOppeasutusAndmed() {
        return new MtsysOppeasutusAndmed();
    }

    /**
     * Create an instance of {@link MtsysOppeasutusKontaktandmed }
     * 
     */
    public MtsysOppeasutusKontaktandmed createMtsysOppeasutusKontaktandmed() {
        return new MtsysOppeasutusKontaktandmed();
    }

    /**
     * Create an instance of {@link TnItem }
     * 
     */
    public TnItem createTnItem() {
        return new TnItem();
    }

    /**
     * Create an instance of {@link SaisTunnistus }
     * 
     */
    public SaisTunnistus createSaisTunnistus() {
        return new SaisTunnistus();
    }

    /**
     * Create an instance of {@link SaisPohiHinded }
     * 
     */
    public SaisPohiHinded createSaisPohiHinded() {
        return new SaisPohiHinded();
    }

    /**
     * Create an instance of {@link SaisHinne }
     * 
     */
    public SaisHinne createSaisHinne() {
        return new SaisHinne();
    }

    /**
     * Create an instance of {@link SaisYldhOping }
     * 
     */
    public SaisYldhOping createSaisYldhOping() {
        return new SaisYldhOping();
    }

    /**
     * Create an instance of {@link SaisOppekavadList.Oppekeeled }
     * 
     */
    public SaisOppekavadList.Oppekeeled createSaisOppekavadListOppekeeled() {
        return new SaisOppekavadList.Oppekeeled();
    }

    /**
     * Create an instance of {@link SaisOppekavadList.Yhisoppekavad }
     * 
     */
    public SaisOppekavadList.Yhisoppekavad createSaisOppekavadListYhisoppekavad() {
        return new SaisOppekavadList.Yhisoppekavad();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link EisOppeasutused }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://producers.ehis.xtee.riik.ee/producer/ehis", name = "eisOppeasutused")
    public JAXBElement<EisOppeasutused> createEisOppeasutused(EisOppeasutused value) {
        return new JAXBElement<EisOppeasutused>(_EisOppeasutused_QNAME, EisOppeasutused.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Kvk }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://producers.ehis.xtee.riik.ee/producer/ehis", name = "kvkLopetatud")
    public JAXBElement<Kvk> createKvkLopetatud(Kvk value) {
        return new JAXBElement<Kvk>(_KvkLopetatud_QNAME, Kvk.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Kvk }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://producers.ehis.xtee.riik.ee/producer/ehis", name = "kvkOppimine")
    public JAXBElement<Kvk> createKvkOppimine(Kvk value) {
        return new JAXBElement<Kvk>(_KvkOppimine_QNAME, Kvk.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://producers.ehis.xtee.riik.ee/producer/ehis", name = "mtsysKlfTeenus")
    public JAXBElement<Object> createMtsysKlfTeenus(Object value) {
        return new JAXBElement<Object>(_MtsysKlfTeenus_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "taotluseId", scope = VpTaotlusOpingud.class)
    public JAXBElement<String> createVpTaotlusOpingudTaotluseId(String value) {
        return new JAXBElement<String>(_VpTaotlusOpingudTaotluseId_QNAME, String.class, VpTaotlusOpingud.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "taotluseId", scope = VpTaotlusOpingudResponse.class)
    public JAXBElement<String> createVpTaotlusOpingudResponseTaotluseId(String value) {
        return new JAXBElement<String>(_VpTaotlusOpingudTaotluseId_QNAME, String.class, VpTaotlusOpingudResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HoiatusDto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "hoiatusDto", scope = VpTaotlusOpingudResponse.class)
    public JAXBElement<HoiatusDto> createVpTaotlusOpingudResponseHoiatusDto(HoiatusDto value) {
        return new JAXBElement<HoiatusDto>(_VpTaotlusOpingudResponseHoiatusDto_QNAME, HoiatusDto.class, VpTaotlusOpingudResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "taotluseId", scope = VpTaotlusIsikud.class)
    public JAXBElement<String> createVpTaotlusIsikudTaotluseId(String value) {
        return new JAXBElement<String>(_VpTaotlusOpingudTaotluseId_QNAME, String.class, VpTaotlusIsikud.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HoiatusDto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "hoiatusDto", scope = VpTaotlusIsikudResponse.class)
    public JAXBElement<HoiatusDto> createVpTaotlusIsikudResponseHoiatusDto(HoiatusDto value) {
        return new JAXBElement<HoiatusDto>(_VpTaotlusOpingudResponseHoiatusDto_QNAME, HoiatusDto.class, VpTaotlusIsikudResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HoiatusDto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "hoiatusDto", scope = VpTaotlusSissetulekudResponse.class)
    public JAXBElement<HoiatusDto> createVpTaotlusSissetulekudResponseHoiatusDto(HoiatusDto value) {
        return new JAXBElement<HoiatusDto>(_VpTaotlusOpingudResponseHoiatusDto_QNAME, HoiatusDto.class, VpTaotlusSissetulekudResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "lisatudIsikuteSissetulek", scope = VpTaotlusSissetulekudResponse.class)
    public JAXBElement<String> createVpTaotlusSissetulekudResponseLisatudIsikuteSissetulek(String value) {
        return new JAXBElement<String>(_VpTaotlusSissetulekudResponseLisatudIsikuteSissetulek_QNAME, String.class, VpTaotlusSissetulekudResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "nonResidentSissetulek", scope = VpTaotlusSissetulekudResponse.class)
    public JAXBElement<String> createVpTaotlusSissetulekudResponseNonResidentSissetulek(String value) {
        return new JAXBElement<String>(_VpTaotlusSissetulekudResponseNonResidentSissetulek_QNAME, String.class, VpTaotlusSissetulekudResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "nonResidentSissetulek", scope = VpTaotlusKontakt.class)
    public JAXBElement<String> createVpTaotlusKontaktNonResidentSissetulek(String value) {
        return new JAXBElement<String>(_VpTaotlusSissetulekudResponseNonResidentSissetulek_QNAME, String.class, VpTaotlusKontakt.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "lisatudIsikuteSissetulek", scope = VpTaotlusKontakt.class)
    public JAXBElement<String> createVpTaotlusKontaktLisatudIsikuteSissetulek(String value) {
        return new JAXBElement<String>(_VpTaotlusSissetulekudResponseLisatudIsikuteSissetulek_QNAME, String.class, VpTaotlusKontakt.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HoiatusDto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "hoiatusDto", scope = VpTaotlusKontaktResponse.class)
    public JAXBElement<HoiatusDto> createVpTaotlusKontaktResponseHoiatusDto(HoiatusDto value) {
        return new JAXBElement<HoiatusDto>(_VpTaotlusOpingudResponseHoiatusDto_QNAME, HoiatusDto.class, VpTaotlusKontaktResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "telefoniNumber", scope = VpTaotlusEsitamine.class)
    public JAXBElement<String> createVpTaotlusEsitamineTelefoniNumber(String value) {
        return new JAXBElement<String>(_VpTaotlusEsitamineTelefoniNumber_QNAME, String.class, VpTaotlusEsitamine.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HoiatusDto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "hoiatusDto", scope = VpTaotlusEsitamineResponse.class)
    public JAXBElement<HoiatusDto> createVpTaotlusEsitamineResponseHoiatusDto(HoiatusDto value) {
        return new JAXBElement<HoiatusDto>(_VpTaotlusOpingudResponseHoiatusDto_QNAME, HoiatusDto.class, VpTaotlusEsitamineResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TaotlusInfoDto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "taotlusInfoDto", scope = VpTaotlusEsitamineResponse.class)
    public JAXBElement<TaotlusInfoDto> createVpTaotlusEsitamineResponseTaotlusInfoDto(TaotlusInfoDto value) {
        return new JAXBElement<TaotlusInfoDto>(_VpTaotlusEsitamineResponseTaotlusInfoDto_QNAME, TaotlusInfoDto.class, VpTaotlusEsitamineResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "teade", scope = KmaLopetanudResponse.class)
    public JAXBElement<String> createKmaLopetanudResponseTeade(String value) {
        return new JAXBElement<String>(_KmaLopetanudResponseTeade_QNAME, String.class, KmaLopetanudResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "kood", scope = KmaLopetanudResponse.class)
    public JAXBElement<BigInteger> createKmaLopetanudResponseKood(BigInteger value) {
        return new JAXBElement<BigInteger>(_KmaLopetanudResponseKood_QNAME, BigInteger.class, KmaLopetanudResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link KmaLopetanudAndmed }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "data", scope = KmaLopetanudResponse.class)
    public JAXBElement<KmaLopetanudAndmed> createKmaLopetanudResponseData(KmaLopetanudAndmed value) {
        return new JAXBElement<KmaLopetanudAndmed>(_KmaLopetanudResponseData_QNAME, KmaLopetanudAndmed.class, KmaLopetanudResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "teade", scope = LaeAlusharidusV2Response.class)
    public JAXBElement<String> createLaeAlusharidusV2ResponseTeade(String value) {
        return new JAXBElement<String>(_KmaLopetanudResponseTeade_QNAME, String.class, LaeAlusharidusV2Response.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "kood", scope = LaeAlusharidusV2Response.class)
    public JAXBElement<BigInteger> createLaeAlusharidusV2ResponseKood(BigInteger value) {
        return new JAXBElement<BigInteger>(_KmaLopetanudResponseKood_QNAME, BigInteger.class, LaeAlusharidusV2Response.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StrArray }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "data", scope = LaeAlusharidusV2Response.class)
    public JAXBElement<StrArray> createLaeAlusharidusV2ResponseData(StrArray value) {
        return new JAXBElement<StrArray>(_KmaLopetanudResponseData_QNAME, StrArray.class, LaeAlusharidusV2Response.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "teade", scope = LaeKorgharidusResponse.class)
    public JAXBElement<String> createLaeKorgharidusResponseTeade(String value) {
        return new JAXBElement<String>(_KmaLopetanudResponseTeade_QNAME, String.class, LaeKorgharidusResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "kood", scope = LaeKorgharidusResponse.class)
    public JAXBElement<BigInteger> createLaeKorgharidusResponseKood(BigInteger value) {
        return new JAXBElement<BigInteger>(_KmaLopetanudResponseKood_QNAME, BigInteger.class, LaeKorgharidusResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StrArray }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "data", scope = LaeKorgharidusResponse.class)
    public JAXBElement<StrArray> createLaeKorgharidusResponseData(StrArray value) {
        return new JAXBElement<StrArray>(_KmaLopetanudResponseData_QNAME, StrArray.class, LaeKorgharidusResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "teade", scope = LaeOppejoudResponse.class)
    public JAXBElement<String> createLaeOppejoudResponseTeade(String value) {
        return new JAXBElement<String>(_KmaLopetanudResponseTeade_QNAME, String.class, LaeOppejoudResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "kood", scope = LaeOppejoudResponse.class)
    public JAXBElement<BigInteger> createLaeOppejoudResponseKood(BigInteger value) {
        return new JAXBElement<BigInteger>(_KmaLopetanudResponseKood_QNAME, BigInteger.class, LaeOppejoudResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StrArray }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "data", scope = LaeOppejoudResponse.class)
    public JAXBElement<StrArray> createLaeOppejoudResponseData(StrArray value) {
        return new JAXBElement<StrArray>(_KmaLopetanudResponseData_QNAME, StrArray.class, LaeOppejoudResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "teade", scope = LaePedagoogidResponse.class)
    public JAXBElement<String> createLaePedagoogidResponseTeade(String value) {
        return new JAXBElement<String>(_KmaLopetanudResponseTeade_QNAME, String.class, LaePedagoogidResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "kood", scope = LaePedagoogidResponse.class)
    public JAXBElement<BigInteger> createLaePedagoogidResponseKood(BigInteger value) {
        return new JAXBElement<BigInteger>(_KmaLopetanudResponseKood_QNAME, BigInteger.class, LaePedagoogidResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StrArray }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "data", scope = LaePedagoogidResponse.class)
    public JAXBElement<StrArray> createLaePedagoogidResponseData(StrArray value) {
        return new JAXBElement<StrArray>(_KmaLopetanudResponseData_QNAME, StrArray.class, LaePedagoogidResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "teade", scope = LaeYldharidusResponse.class)
    public JAXBElement<String> createLaeYldharidusResponseTeade(String value) {
        return new JAXBElement<String>(_KmaLopetanudResponseTeade_QNAME, String.class, LaeYldharidusResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "kood", scope = LaeYldharidusResponse.class)
    public JAXBElement<BigInteger> createLaeYldharidusResponseKood(BigInteger value) {
        return new JAXBElement<BigInteger>(_KmaLopetanudResponseKood_QNAME, BigInteger.class, LaeYldharidusResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StrArray }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "data", scope = LaeYldharidusResponse.class)
    public JAXBElement<StrArray> createLaeYldharidusResponseData(StrArray value) {
        return new JAXBElement<StrArray>(_KmaLopetanudResponseData_QNAME, StrArray.class, LaeYldharidusResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "teade", scope = MkmLopetamisedResponse.class)
    public JAXBElement<String> createMkmLopetamisedResponseTeade(String value) {
        return new JAXBElement<String>(_KmaLopetanudResponseTeade_QNAME, String.class, MkmLopetamisedResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MkmLopetatudKorgharidused }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "data", scope = MkmLopetamisedResponse.class)
    public JAXBElement<MkmLopetatudKorgharidused> createMkmLopetamisedResponseData(MkmLopetatudKorgharidused value) {
        return new JAXBElement<MkmLopetatudKorgharidused>(_KmaLopetanudResponseData_QNAME, MkmLopetatudKorgharidused.class, MkmLopetamisedResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "menetlusKommentaar", scope = MtsysEsitaTegevusluba.class)
    public JAXBElement<String> createMtsysEsitaTegevuslubaMenetlusKommentaar(String value) {
        return new JAXBElement<String>(_MtsysEsitaTegevuslubaMenetlusKommentaar_QNAME, String.class, MtsysEsitaTegevusluba.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "algusKp", scope = MtsysEsitaTegevusluba.class)
    public JAXBElement<XMLGregorianCalendar> createMtsysEsitaTegevuslubaAlgusKp(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_MtsysEsitaTegevuslubaAlgusKp_QNAME, XMLGregorianCalendar.class, MtsysEsitaTegevusluba.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "loppKp", scope = MtsysEsitaTegevusluba.class)
    public JAXBElement<XMLGregorianCalendar> createMtsysEsitaTegevuslubaLoppKp(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_MtsysEsitaTegevuslubaLoppKp_QNAME, XMLGregorianCalendar.class, MtsysEsitaTegevusluba.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "id", scope = MtsysKlfAdsTeenus.class)
    public JAXBElement<BigInteger> createMtsysKlfAdsTeenusId(BigInteger value) {
        return new JAXBElement<BigInteger>(_MtsysKlfAdsTeenusId_QNAME, BigInteger.class, MtsysKlfAdsTeenus.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TegevusloaLiigid }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "tegevusloaLiigid", scope = MtsysKlfTeenusResponse.class)
    public JAXBElement<TegevusloaLiigid> createMtsysKlfTeenusResponseTegevusloaLiigid(TegevusloaLiigid value) {
        return new JAXBElement<TegevusloaLiigid>(_MtsysKlfTeenusResponseTegevusloaLiigid_QNAME, TegevusloaLiigid.class, MtsysKlfTeenusResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OppekavaStaatused }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "oppekavaStaatused", scope = MtsysKlfTeenusResponse.class)
    public JAXBElement<OppekavaStaatused> createMtsysKlfTeenusResponseOppekavaStaatused(OppekavaStaatused value) {
        return new JAXBElement<OppekavaStaatused>(_MtsysKlfTeenusResponseOppekavaStaatused_QNAME, OppekavaStaatused.class, MtsysKlfTeenusResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OppekavaOppetasemed }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "oppekavaOppetasemed", scope = MtsysKlfTeenusResponse.class)
    public JAXBElement<OppekavaOppetasemed> createMtsysKlfTeenusResponseOppekavaOppetasemed(OppekavaOppetasemed value) {
        return new JAXBElement<OppekavaOppetasemed>(_MtsysKlfTeenusResponseOppekavaOppetasemed_QNAME, OppekavaOppetasemed.class, MtsysKlfTeenusResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoidukiKategooriad }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "soidukiKategooriad", scope = MtsysKlfTeenusResponse.class)
    public JAXBElement<SoidukiKategooriad> createMtsysKlfTeenusResponseSoidukiKategooriad(SoidukiKategooriad value) {
        return new JAXBElement<SoidukiKategooriad>(_MtsysKlfTeenusResponseSoidukiKategooriad_QNAME, SoidukiKategooriad.class, MtsysKlfTeenusResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OppeasutuseOmandivormid }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "oppeasutuseOmandivormid", scope = MtsysKlfTeenusResponse.class)
    public JAXBElement<OppeasutuseOmandivormid> createMtsysKlfTeenusResponseOppeasutuseOmandivormid(OppeasutuseOmandivormid value) {
        return new JAXBElement<OppeasutuseOmandivormid>(_MtsysKlfTeenusResponseOppeasutuseOmandivormid_QNAME, OppeasutuseOmandivormid.class, MtsysKlfTeenusResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OppeasutuseLiigid }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "oppeasutuseLiigid", scope = MtsysKlfTeenusResponse.class)
    public JAXBElement<OppeasutuseLiigid> createMtsysKlfTeenusResponseOppeasutuseLiigid(OppeasutuseLiigid value) {
        return new JAXBElement<OppeasutuseLiigid>(_MtsysKlfTeenusResponseOppeasutuseLiigid_QNAME, OppeasutuseLiigid.class, MtsysKlfTeenusResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PidajaLiigid }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "pidajaLiigid", scope = MtsysKlfTeenusResponse.class)
    public JAXBElement<PidajaLiigid> createMtsysKlfTeenusResponsePidajaLiigid(PidajaLiigid value) {
        return new JAXBElement<PidajaLiigid>(_MtsysKlfTeenusResponsePidajaLiigid_QNAME, PidajaLiigid.class, MtsysKlfTeenusResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FailiTyybid }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "failiTyybid", scope = MtsysKlfTeenusResponse.class)
    public JAXBElement<FailiTyybid> createMtsysKlfTeenusResponseFailiTyybid(FailiTyybid value) {
        return new JAXBElement<FailiTyybid>(_MtsysKlfTeenusResponseFailiTyybid_QNAME, FailiTyybid.class, MtsysKlfTeenusResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TkkLiigid }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "tkkLiigid", scope = MtsysKlfTeenusResponse.class)
    public JAXBElement<TkkLiigid> createMtsysKlfTeenusResponseTkkLiigid(TkkLiigid value) {
        return new JAXBElement<TkkLiigid>(_MtsysKlfTeenusResponseTkkLiigid_QNAME, TkkLiigid.class, MtsysKlfTeenusResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EestiKeeleTasemed }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "eestiKeeleTasemed", scope = MtsysKlfTeenusResponse.class)
    public JAXBElement<EestiKeeleTasemed> createMtsysKlfTeenusResponseEestiKeeleTasemed(EestiKeeleTasemed value) {
        return new JAXBElement<EestiKeeleTasemed>(_MtsysKlfTeenusResponseEestiKeeleTasemed_QNAME, EestiKeeleTasemed.class, MtsysKlfTeenusResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Opperyhmad }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "opperyhmad", scope = MtsysKlfTeenusResponse.class)
    public JAXBElement<Opperyhmad> createMtsysKlfTeenusResponseOpperyhmad(Opperyhmad value) {
        return new JAXBElement<Opperyhmad>(_MtsysKlfTeenusResponseOpperyhmad_QNAME, Opperyhmad.class, MtsysKlfTeenusResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TegevusnaitajaTyybid }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "tegevusnaitajaTyybid", scope = MtsysKlfTeenusResponse.class)
    public JAXBElement<TegevusnaitajaTyybid> createMtsysKlfTeenusResponseTegevusnaitajaTyybid(TegevusnaitajaTyybid value) {
        return new JAXBElement<TegevusnaitajaTyybid>(_MtsysKlfTeenusResponseTegevusnaitajaTyybid_QNAME, TegevusnaitajaTyybid.class, MtsysKlfTeenusResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "menetlusKommentaar", scope = MtsysEsitaTegevusnaitajad.class)
    public JAXBElement<String> createMtsysEsitaTegevusnaitajadMenetlusKommentaar(String value) {
        return new JAXBElement<String>(_MtsysEsitaTegevuslubaMenetlusKommentaar_QNAME, String.class, MtsysEsitaTegevusnaitajad.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "algKpv", scope = PolOppur.class)
    public JAXBElement<XMLGregorianCalendar> createPolOppurAlgKpv(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_PolOppurAlgKpv_QNAME, XMLGregorianCalendar.class, PolOppur.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "loppKpv", scope = PolOppur.class)
    public JAXBElement<XMLGregorianCalendar> createPolOppurLoppKpv(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_PolOppurLoppKpv_QNAME, XMLGregorianCalendar.class, PolOppur.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "oppeasutuseYldtelefon", scope = MtsysOppeasutusKontaktandmed.class)
    public JAXBElement<String> createMtsysOppeasutusKontaktandmedOppeasutuseYldtelefon(String value) {
        return new JAXBElement<String>(_MtsysOppeasutusKontaktandmedOppeasutuseYldtelefon_QNAME, String.class, MtsysOppeasutusKontaktandmed.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "oppeasutuseEpost", scope = MtsysOppeasutusKontaktandmed.class)
    public JAXBElement<String> createMtsysOppeasutusKontaktandmedOppeasutuseEpost(String value) {
        return new JAXBElement<String>(_MtsysOppeasutusKontaktandmedOppeasutuseEpost_QNAME, String.class, MtsysOppeasutusKontaktandmed.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "koduleheAadress", scope = MtsysOppeasutusKontaktandmed.class)
    public JAXBElement<String> createMtsysOppeasutusKontaktandmedKoduleheAadress(String value) {
        return new JAXBElement<String>(_MtsysOppeasutusKontaktandmedKoduleheAadress_QNAME, String.class, MtsysOppeasutusKontaktandmed.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "dokumentId", scope = Dokument.class)
    public JAXBElement<Long> createDokumentDokumentId(Long value) {
        return new JAXBElement<Long>(_DokumentDokumentId_QNAME, Long.class, Dokument.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "kommentaar", scope = Dokument.class)
    public JAXBElement<String> createDokumentKommentaar(String value) {
        return new JAXBElement<String>(_DokumentKommentaar_QNAME, String.class, Dokument.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "content", scope = Dokument.class)
    public JAXBElement<byte[]> createDokumentContent(byte[] value) {
        return new JAXBElement<byte[]>(_DokumentContent_QNAME, byte[].class, Dokument.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "asutusKood", scope = Yhisope.class)
    public JAXBElement<String> createYhisopeAsutusKood(String value) {
        return new JAXBElement<String>(_YhisopeAsutusKood_QNAME, String.class, Yhisope.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "algusKp", scope = Peatamine.class)
    public JAXBElement<String> createPeatamineAlgusKp(String value) {
        return new JAXBElement<String>(_MtsysEsitaTegevuslubaAlgusKp_QNAME, String.class, Peatamine.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "loppKp", scope = Peatamine.class)
    public JAXBElement<String> createPeatamineLoppKp(String value) {
        return new JAXBElement<String>(_MtsysEsitaTegevuslubaLoppKp_QNAME, String.class, Peatamine.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "nimetus", scope = OppeasutusTegevusload.class)
    public JAXBElement<String> createOppeasutusTegevusloadNimetus(String value) {
        return new JAXBElement<String>(_OppeasutusTegevusloadNimetus_QNAME, String.class, OppeasutusTegevusload.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "lyhMobKood", scope = OppejoudLyhiajalineMobiilsus.class)
    public JAXBElement<String> createOppejoudLyhiajalineMobiilsusLyhMobKood(String value) {
        return new JAXBElement<String>(_OppejoudLyhiajalineMobiilsusLyhMobKood_QNAME, String.class, OppejoudLyhiajalineMobiilsus.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "kustutatud", scope = OppejoudLyhiajalineMobiilsus.class)
    public JAXBElement<String> createOppejoudLyhiajalineMobiilsusKustutatud(String value) {
        return new JAXBElement<String>(_OppejoudLyhiajalineMobiilsusKustutatud_QNAME, String.class, OppejoudLyhiajalineMobiilsus.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "isikukood", scope = EylIsicParing.class)
    public JAXBElement<String> createEylIsicParingIsikukood(String value) {
        return new JAXBElement<String>(_EylIsicParingIsikukood_QNAME, String.class, EylIsicParing.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "eesnimi", scope = EylIsicParing.class)
    public JAXBElement<String> createEylIsicParingEesnimi(String value) {
        return new JAXBElement<String>(_EylIsicParingEesnimi_QNAME, String.class, EylIsicParing.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "perenimi", scope = EylIsicParing.class)
    public JAXBElement<String> createEylIsicParingPerenimi(String value) {
        return new JAXBElement<String>(_EylIsicParingPerenimi_QNAME, String.class, EylIsicParing.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "taotlusId", scope = TegevuslubaDetail.class)
    public JAXBElement<BigInteger> createTegevuslubaDetailTaotlusId(BigInteger value) {
        return new JAXBElement<BigInteger>(_TegevuslubaDetailTaotlusId_QNAME, BigInteger.class, TegevuslubaDetail.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "kohtadeArvLaagris", scope = TegevuslubaDetail.class)
    public JAXBElement<BigInteger> createTegevuslubaDetailKohtadeArvLaagris(BigInteger value) {
        return new JAXBElement<BigInteger>(_TegevuslubaDetailKohtadeArvLaagris_QNAME, BigInteger.class, TegevuslubaDetail.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MtsysOppeasutusKontaktandmed }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "kontaktandmed", scope = OppeasutusDetail.class)
    public JAXBElement<MtsysOppeasutusKontaktandmed> createOppeasutusDetailKontaktandmed(MtsysOppeasutusKontaktandmed value) {
        return new JAXBElement<MtsysOppeasutusKontaktandmed>(_OppeasutusDetailKontaktandmed_QNAME, MtsysOppeasutusKontaktandmed.class, OppeasutusDetail.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "kontaktisik", scope = MtsysTaotlusKontaktandmed.class)
    public JAXBElement<String> createMtsysTaotlusKontaktandmedKontaktisik(String value) {
        return new JAXBElement<String>(_MtsysTaotlusKontaktandmedKontaktisik_QNAME, String.class, MtsysTaotlusKontaktandmed.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "oppeasutuseYldtelefon", scope = MtsysTaotlusKontaktandmed.class)
    public JAXBElement<String> createMtsysTaotlusKontaktandmedOppeasutuseYldtelefon(String value) {
        return new JAXBElement<String>(_MtsysOppeasutusKontaktandmedOppeasutuseYldtelefon_QNAME, String.class, MtsysTaotlusKontaktandmed.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "oppeasutuseEpost", scope = MtsysTaotlusKontaktandmed.class)
    public JAXBElement<String> createMtsysTaotlusKontaktandmedOppeasutuseEpost(String value) {
        return new JAXBElement<String>(_MtsysOppeasutusKontaktandmedOppeasutuseEpost_QNAME, String.class, MtsysTaotlusKontaktandmed.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "koduleht", scope = MtsysTaotlusKontaktandmed.class)
    public JAXBElement<String> createMtsysTaotlusKontaktandmedKoduleht(String value) {
        return new JAXBElement<String>(_MtsysTaotlusKontaktandmedKoduleht_QNAME, String.class, MtsysTaotlusKontaktandmed.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "kohtadeArvLaagris", scope = Taotlus.class)
    public JAXBElement<BigInteger> createTaotlusKohtadeArvLaagris(BigInteger value) {
        return new JAXBElement<BigInteger>(_TegevuslubaDetailKohtadeArvLaagris_QNAME, BigInteger.class, Taotlus.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "kehtibAlates", scope = Taotlus.class)
    public JAXBElement<XMLGregorianCalendar> createTaotlusKehtibAlates(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_TaotlusKehtibAlates_QNAME, XMLGregorianCalendar.class, Taotlus.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "kehtibKuni", scope = Taotlus.class)
    public JAXBElement<XMLGregorianCalendar> createTaotlusKehtibKuni(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_TaotlusKehtibKuni_QNAME, XMLGregorianCalendar.class, Taotlus.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "klTkkLiik", scope = Taotlus.class)
    public JAXBElement<BigInteger> createTaotlusKlTkkLiik(BigInteger value) {
        return new JAXBElement<BigInteger>(_TaotlusKlTkkLiik_QNAME, BigInteger.class, Taotlus.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "klEkTase", scope = Taotlus.class)
    public JAXBElement<BigInteger> createTaotlusKlEkTase(BigInteger value) {
        return new JAXBElement<BigInteger>(_TaotlusKlEkTase_QNAME, BigInteger.class, Taotlus.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "klSoidukiKategooria", scope = Taotlus.class)
    public JAXBElement<BigInteger> createTaotlusKlSoidukiKategooria(BigInteger value) {
        return new JAXBElement<BigInteger>(_TaotlusKlSoidukiKategooria_QNAME, BigInteger.class, Taotlus.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "id", scope = Oppekava.class)
    public JAXBElement<BigInteger> createOppekavaId(BigInteger value) {
        return new JAXBElement<BigInteger>(_MtsysKlfAdsTeenusId_QNAME, BigInteger.class, Oppekava.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "id", scope = FailInfoDto.class)
    public JAXBElement<String> createFailInfoDtoId(String value) {
        return new JAXBElement<String>(_MtsysKlfAdsTeenusId_QNAME, String.class, FailInfoDto.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "content", scope = FailInfoDto.class)
    public JAXBElement<byte[]> createFailInfoDtoContent(byte[] value) {
        return new JAXBElement<byte[]>(_DokumentContent_QNAME, byte[].class, FailInfoDto.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "isikukood", scope = IsikInfoDto.class)
    public JAXBElement<String> createIsikInfoDtoIsikukood(String value) {
        return new JAXBElement<String>(_EylIsicParingIsikukood_QNAME, String.class, IsikInfoDto.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "synniaeg", scope = IsikInfoDto.class)
    public JAXBElement<String> createIsikInfoDtoSynniaeg(String value) {
        return new JAXBElement<String>(_IsikInfoDtoSynniaeg_QNAME, String.class, IsikInfoDto.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "esitamiseKuupaev", scope = TaotlusInfoDto.class)
    public JAXBElement<String> createTaotlusInfoDtoEsitamiseKuupaev(String value) {
        return new JAXBElement<String>(_TaotlusInfoDtoEsitamiseKuupaev_QNAME, String.class, TaotlusInfoDto.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "oppeTyypKL", scope = OppimineDto.class)
    public JAXBElement<String> createOppimineDtoOppeTyypKL(String value) {
        return new JAXBElement<String>(_OppimineDtoOppeTyypKL_QNAME, String.class, OppimineDto.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "oppekoormusTyypKL", scope = OppimineDto.class)
    public JAXBElement<String> createOppimineDtoOppekoormusTyypKL(String value) {
        return new JAXBElement<String>(_OppimineDtoOppekoormusTyypKL_QNAME, String.class, OppimineDto.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "akadeemilisePuhkuseAlustamiseKuupaev", scope = OppimineDto.class)
    public JAXBElement<String> createOppimineDtoAkadeemilisePuhkuseAlustamiseKuupaev(String value) {
        return new JAXBElement<String>(_OppimineDtoAkadeemilisePuhkuseAlustamiseKuupaev_QNAME, String.class, OppimineDto.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "esimeseSemestriLoppKp", scope = OppimineDto.class)
    public JAXBElement<String> createOppimineDtoEsimeseSemestriLoppKp(String value) {
        return new JAXBElement<String>(_OppimineDtoEsimeseSemestriLoppKp_QNAME, String.class, OppimineDto.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "adsId", scope = Aadress.class)
    public JAXBElement<BigInteger> createAadressAdsId(BigInteger value) {
        return new JAXBElement<BigInteger>(_AadressAdsId_QNAME, BigInteger.class, Aadress.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "adsOid", scope = Aadress.class)
    public JAXBElement<String> createAadressAdsOid(String value) {
        return new JAXBElement<String>(_AadressAdsOid_QNAME, String.class, Aadress.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "klElukoht", scope = Aadress.class)
    public JAXBElement<BigInteger> createAadressKlElukoht(BigInteger value) {
        return new JAXBElement<BigInteger>(_AadressKlElukoht_QNAME, BigInteger.class, Aadress.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "maakond", scope = Aadress.class)
    public JAXBElement<String> createAadressMaakond(String value) {
        return new JAXBElement<String>(_AadressMaakond_QNAME, String.class, Aadress.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "omavalitsus", scope = Aadress.class)
    public JAXBElement<String> createAadressOmavalitsus(String value) {
        return new JAXBElement<String>(_AadressOmavalitsus_QNAME, String.class, Aadress.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "asula", scope = Aadress.class)
    public JAXBElement<String> createAadressAsula(String value) {
        return new JAXBElement<String>(_AadressAsula_QNAME, String.class, Aadress.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "taisAadress", scope = Aadress.class)
    public JAXBElement<String> createAadressTaisAadress(String value) {
        return new JAXBElement<String>(_AadressTaisAadress_QNAME, String.class, Aadress.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "adsAadress", scope = Aadress.class)
    public JAXBElement<String> createAadressAdsAadress(String value) {
        return new JAXBElement<String>(_AadressAdsAadress_QNAME, String.class, Aadress.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "adsAadressHumanReadable", scope = Aadress.class)
    public JAXBElement<String> createAadressAdsAadressHumanReadable(String value) {
        return new JAXBElement<String>(_AadressAdsAadressHumanReadable_QNAME, String.class, Aadress.class, value);
    }

}
