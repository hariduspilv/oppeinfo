
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for XRoadResponseBaseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="XRoadResponseBaseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="faultCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="faultString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XRoadResponseBaseType", propOrder = {
    "faultCode",
    "faultString"
})
@XmlSeeAlso({
    RR19IsikuElukohtResponseType.class,
    RR29IsikuElukohtResponseType.class,
    RR403PriaelukandmedResponseType.class,
    RR404IsikResponseType.class,
    RR405IsikNimiResponseType.class,
    RR406IsikSurmaDokResponseType.class,
    RR407HMIsikEestkResponseType.class,
    RR408IntegratsioonResponseType.class,
    RR409IsikEpiletResponseType.class,
    RR40IsikTaielikIsikukoodResponseType.class,
    RR410KeskkonnateabeResponseType.class,
    RR411ResponseType.class,
    RR412ResponseType.class,
    RR414ResponseType.class,
    RR415ResponseType.class,
    RR416ResponseType.class,
    RR417ResponseType.class,
    RR418ResponseType.class,
    RR41IsikPohiandmedResponseType.class,
    RR420KMOOnElukohaTeadePoleElamisOigustResponseType.class,
    RR421KMOElKodElukohaRegistreerimineResponseType.class,
    RR422KMOValjaregElKodanikudResponseType.class,
    RR423KMOSurmadResponseType.class,
    RR424KMOSynnidResponseType.class,
    RR425KMOTaotlejaAndmeteParingResponseType.class,
    RR426KMOTaotlejaHooldajateParingResponseType.class,
    RR427KMOTaotlejagaSeotudIsikudResponseType.class,
    RR428KMOStatistikaAruanneResponseType.class,
    RR42IsikAadressKoodResponseType.class,
    RR431ResponseType.class,
    RR432ResponseType.class,
    RR433ResponseType.class,
    RR434ResponseType.class,
    RR435ResponseType.class,
    RR436ResponseType.class,
    RR437ResponseType.class,
    RR438IsikAadrDokEresidResponseType.class,
    RR439IsikAadrYksiResponseType.class,
    RR43DokumentResponseType.class,
    RR440IsikYksiofflResponseType.class,
    RR441ResponseType.class,
    RR442ResponseType.class,
    RR443ElukohtadegaResponseType.class,
    RR444ResponseType.class,
    RR445ResponseType.class,
    RR446ResponseType.class,
    RR447ResponseType.class,
    RR448ResponseType.class,
    RR449ResponseType.class,
    RR44IsikKodanikResponseType.class,
    RR450ResponseType.class,
    RR451ResponseType.class,
    RR452ResponseType.class,
    RR453CRYPResponseType.class,
    RR455ResponseType.class,
    RR456ResponseType.class,
    RR458IsikuTDkontrollResponseType.class,
    RR459IsikuDokumentResponseType.class,
    RR45IsikPohiandmetegaIsikukoodResponseType.class,
    RR460DokKehtivusResponseType.class,
    RR461PereDokumentResponseType.class,
    RR462IsikuPohiandmedResponseType.class,
    RR463IsikuDetailandmedResponseType.class,
    RR464IsikuSuhtedResponseType.class,
    RR465IsikuHooldusoigusedResponseType.class,
    RR466IsikuTeovoimeResponseType.class,
    RR467IsikuAadressResponseType.class,
    RR468IsikuElukohtSideAadressResponseType.class,
    RR469IsikuKontaktResponseType.class,
    RR470Isikukoodid16Kuni26AResponseType.class,
    RR471IsikuPereliikmedResponseType.class,
    RR472IsikuKontaktStatResponseType.class,
    RR48IsikPRIAResponseType.class,
    RR49ParingHaridusametileResponseType.class,
    RR50SurnudIsikuteLeidmineResponseType.class,
    RR52ResponseType.class,
    RR56IsikuDokumendidIsikuAndmetegaResponseType.class,
    RR57ResponseType.class,
    RR58IsikEpiletResponseType.class,
    RR62IsikSotsikKPResponseType.class,
    RR63IsikAadrDokResponseType.class,
    RR64ResponseType.class,
    RR65HMIsikResponseType.class,
    RR66HMIsikEestkResponseType.class,
    RR67MuutusResponseType.class,
    RR68IsikTellResponseType.class,
    RR69SpetsisikTellResponseType.class,
    RR71FailDownloadResponseType.class,
    RR72IsikResponseType.class,
    RR73ResponseType.class,
    RR75ResponseType.class,
    RR76AndmIKResponseType.class,
    RR80KMAisikuandmedResponseType.class,
    RR81KMAisikkontrollResponseType.class,
    RR82KMAviisaandmedResponseType.class,
    RR83KMAsurmatunnusResponseType.class,
    RR84IsikuSeosedResponseType.class,
    RR86LapseVanemadResponseType.class,
    RR87IsikKoda898AnalResponseType.class,
    RR901ResponseType.class,
    RR91ESugulusSuhtedResponseType.class,
    RR92IsikuDokidP08ResponseType.class,
    RR93NimeStatistikaP09ResponseType.class,
    RR96IsikDokElukSuheResponseType.class,
    RRAddressResponseType.class,
    RRAddressIdResponseType.class,
    RRARKJUHILUBAResponseType.class,
    RRAVRResponseType.class,
    RRisikSotsikResponseType.class,
    RRisikuViibimiskohtResponseType.class,
    RRKMA1KmaIdokResponseType.class,
    RRKMA3KmaKodaResponseType.class,
    RRKMA4KmaElResponseType.class,
    RRKodifikaatorResponseType.class,
    RRKohtuDokumentResponseType.class,
    RRKooseluResponseType.class,
    RRPORTAADRESSResponseType.class,
    RRPORTDETAILResponseType.class,
    RRPORTDOKKEHTIVUSResponseType.class,
    RRPORTDOKUMENTResponseType.class,
    RRPORTELUKOHTResponseType.class,
    RRPORTHOOLDUSOIGUSResponseType.class,
    RRPORTISIKResponseType.class,
    RRPORTITDResponseType.class,
    RRPORTKONTAKTResponseType.class,
    RRPORTPARINGUTEPARINGResponseType.class,
    RRPORTPEREDOKResponseType.class,
    RRPORTSEOSEDResponseType.class,
    RRPORTTEOVOIMEResponseType.class,
    RRriikIdResponseType.class,
    RRs1090MeditsiinilineSunnitoendResponseType.class,
    RRs1301IsikukoodiParandamineVoiTuhistamineResponseType.class,
    RRs1305IsikukoodiTellimineResponseType.class,
    RRSIDEAADRESSSideDataResponseType.class,
    RRStatAndmeteKanneResponseType.class,
    RRSurmateatisResponseType.class,
    RRVKABIELUVOIMEVmT22ResponseType.class,
    RRVMELUKOHTVmEkResponseType.class,
    RRVMISIKUTTOENDAVDOKUMENTVmDpResponseType.class
})
public abstract class XRoadResponseBaseType {

    protected String faultCode;
    protected String faultString;

    /**
     * Gets the value of the faultCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaultCode() {
        return faultCode;
    }

    /**
     * Sets the value of the faultCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaultCode(String value) {
        this.faultCode = value;
    }

    /**
     * Gets the value of the faultString property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaultString() {
        return faultString;
    }

    /**
     * Sets the value of the faultString property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaultString(String value) {
        this.faultString = value;
    }

}
