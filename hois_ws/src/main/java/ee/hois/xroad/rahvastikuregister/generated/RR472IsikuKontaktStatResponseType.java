
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR472isikuKontaktStatResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR472isikuKontaktStatResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Veatekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Isik"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Kontakt" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="KontaktLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="KontaktTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="KontaktOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="KontaktAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="KontaktKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="KontaktMuutmisKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="KontaktAsutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="ValisriigiIsikukood" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="VRIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="MuutmisKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="StatistilisedAndmed" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="RahvusElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Rahvus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="EmakeelElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Emakeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="HaridusElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Haridus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Laps" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="SuheIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="SuhePerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="SuheEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="SuheSuhtetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="HooldusoigusedLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="HooldusoigusedOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Kontakt" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="KontaktLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="KontaktTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="KontaktOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="KontaktAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="KontaktKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="KontaktMuutmisKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="KontaktAsutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="ValisriigiIsikukood" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="VRIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="MuutmisKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="StatistilisedAndmed" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="RahvusElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Rahvus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="EmakeelElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Emakeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="HaridusElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Haridus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Eestkostetav" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="SuheIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="SuhePerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="SuheEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="SuheSuhtetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Olek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Kontakt" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="KontaktLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="KontaktTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="KontaktOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="KontaktAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="KontaktKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="KontaktMuutmisKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="KontaktAsutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="ValisriigiIsikukood" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="VRIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="MuutmisKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="StatistilisedAndmed" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="RahvusElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Rahvus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="EmakeelElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Emakeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="HaridusElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Haridus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR472isikuKontaktStatResponseType", propOrder = {
    "veatekst",
    "isik",
    "laps",
    "eestkostetav"
})
public class RR472IsikuKontaktStatResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Veatekst")
    protected String veatekst;
    @XmlElement(name = "Isik", required = true)
    protected RR472IsikuKontaktStatResponseType.Isik isik;
    @XmlElement(name = "Laps")
    protected List<RR472IsikuKontaktStatResponseType.Laps> laps;
    @XmlElement(name = "Eestkostetav")
    protected List<RR472IsikuKontaktStatResponseType.Eestkostetav> eestkostetav;

    /**
     * Gets the value of the veatekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVeatekst() {
        return veatekst;
    }

    /**
     * Sets the value of the veatekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVeatekst(String value) {
        this.veatekst = value;
    }

    /**
     * Gets the value of the isik property.
     * 
     * @return
     *     possible object is
     *     {@link RR472IsikuKontaktStatResponseType.Isik }
     *     
     */
    public RR472IsikuKontaktStatResponseType.Isik getIsik() {
        return isik;
    }

    /**
     * Sets the value of the isik property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR472IsikuKontaktStatResponseType.Isik }
     *     
     */
    public void setIsik(RR472IsikuKontaktStatResponseType.Isik value) {
        this.isik = value;
    }

    /**
     * Gets the value of the laps property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the laps property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLaps().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RR472IsikuKontaktStatResponseType.Laps }
     * 
     * 
     */
    public List<RR472IsikuKontaktStatResponseType.Laps> getLaps() {
        if (laps == null) {
            laps = new ArrayList<RR472IsikuKontaktStatResponseType.Laps>();
        }
        return this.laps;
    }

    /**
     * Gets the value of the eestkostetav property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the eestkostetav property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEestkostetav().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RR472IsikuKontaktStatResponseType.Eestkostetav }
     * 
     * 
     */
    public List<RR472IsikuKontaktStatResponseType.Eestkostetav> getEestkostetav() {
        if (eestkostetav == null) {
            eestkostetav = new ArrayList<RR472IsikuKontaktStatResponseType.Eestkostetav>();
        }
        return this.eestkostetav;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="SuheIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="SuhePerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="SuheEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="SuheSuhtetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Olek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Kontakt" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="KontaktLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="KontaktTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="KontaktOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="KontaktAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="KontaktKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="KontaktMuutmisKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="KontaktAsutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="ValisriigiIsikukood" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="VRIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="MuutmisKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="StatistilisedAndmed" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="RahvusElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Rahvus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="EmakeelElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Emakeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="HaridusElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Haridus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "isikuStaatus",
        "suheIsikukood",
        "suhePerenimi",
        "suheEesnimi",
        "suheSuhtetyyp",
        "olek",
        "kontakt",
        "valisriigiIsikukood",
        "statistilisedAndmed"
    })
    public static class Eestkostetav {

        @XmlElement(name = "IsikuStaatus", required = true)
        protected String isikuStaatus;
        @XmlElement(name = "SuheIsikukood", required = true)
        protected String suheIsikukood;
        @XmlElement(name = "SuhePerenimi", required = true)
        protected String suhePerenimi;
        @XmlElement(name = "SuheEesnimi", required = true)
        protected String suheEesnimi;
        @XmlElement(name = "SuheSuhtetyyp", required = true)
        protected String suheSuhtetyyp;
        @XmlElement(name = "Olek", required = true)
        protected String olek;
        @XmlElement(name = "Kontakt")
        protected List<RR472IsikuKontaktStatResponseType.Eestkostetav.Kontakt> kontakt;
        @XmlElement(name = "ValisriigiIsikukood")
        protected List<RR472IsikuKontaktStatResponseType.Eestkostetav.ValisriigiIsikukood> valisriigiIsikukood;
        @XmlElement(name = "StatistilisedAndmed")
        protected List<RR472IsikuKontaktStatResponseType.Eestkostetav.StatistilisedAndmed> statistilisedAndmed;

        /**
         * Gets the value of the isikuStaatus property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIsikuStaatus() {
            return isikuStaatus;
        }

        /**
         * Sets the value of the isikuStaatus property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsikuStaatus(String value) {
            this.isikuStaatus = value;
        }

        /**
         * Gets the value of the suheIsikukood property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSuheIsikukood() {
            return suheIsikukood;
        }

        /**
         * Sets the value of the suheIsikukood property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSuheIsikukood(String value) {
            this.suheIsikukood = value;
        }

        /**
         * Gets the value of the suhePerenimi property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSuhePerenimi() {
            return suhePerenimi;
        }

        /**
         * Sets the value of the suhePerenimi property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSuhePerenimi(String value) {
            this.suhePerenimi = value;
        }

        /**
         * Gets the value of the suheEesnimi property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSuheEesnimi() {
            return suheEesnimi;
        }

        /**
         * Sets the value of the suheEesnimi property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSuheEesnimi(String value) {
            this.suheEesnimi = value;
        }

        /**
         * Gets the value of the suheSuhtetyyp property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSuheSuhtetyyp() {
            return suheSuhtetyyp;
        }

        /**
         * Sets the value of the suheSuhtetyyp property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSuheSuhtetyyp(String value) {
            this.suheSuhtetyyp = value;
        }

        /**
         * Gets the value of the olek property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOlek() {
            return olek;
        }

        /**
         * Sets the value of the olek property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOlek(String value) {
            this.olek = value;
        }

        /**
         * Gets the value of the kontakt property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the kontakt property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getKontakt().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR472IsikuKontaktStatResponseType.Eestkostetav.Kontakt }
         * 
         * 
         */
        public List<RR472IsikuKontaktStatResponseType.Eestkostetav.Kontakt> getKontakt() {
            if (kontakt == null) {
                kontakt = new ArrayList<RR472IsikuKontaktStatResponseType.Eestkostetav.Kontakt>();
            }
            return this.kontakt;
        }

        /**
         * Gets the value of the valisriigiIsikukood property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the valisriigiIsikukood property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getValisriigiIsikukood().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR472IsikuKontaktStatResponseType.Eestkostetav.ValisriigiIsikukood }
         * 
         * 
         */
        public List<RR472IsikuKontaktStatResponseType.Eestkostetav.ValisriigiIsikukood> getValisriigiIsikukood() {
            if (valisriigiIsikukood == null) {
                valisriigiIsikukood = new ArrayList<RR472IsikuKontaktStatResponseType.Eestkostetav.ValisriigiIsikukood>();
            }
            return this.valisriigiIsikukood;
        }

        /**
         * Gets the value of the statistilisedAndmed property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the statistilisedAndmed property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getStatistilisedAndmed().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR472IsikuKontaktStatResponseType.Eestkostetav.StatistilisedAndmed }
         * 
         * 
         */
        public List<RR472IsikuKontaktStatResponseType.Eestkostetav.StatistilisedAndmed> getStatistilisedAndmed() {
            if (statistilisedAndmed == null) {
                statistilisedAndmed = new ArrayList<RR472IsikuKontaktStatResponseType.Eestkostetav.StatistilisedAndmed>();
            }
            return this.statistilisedAndmed;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="KontaktLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="KontaktTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="KontaktOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="KontaktAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="KontaktKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="KontaktMuutmisKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="KontaktAsutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "kontaktLiik",
            "kontaktTekst",
            "kontaktOlek",
            "kontaktAlates",
            "kontaktKuni",
            "kontaktMuutmisKpv",
            "kontaktAsutus"
        })
        public static class Kontakt {

            @XmlElement(name = "KontaktLiik", required = true)
            protected String kontaktLiik;
            @XmlElement(name = "KontaktTekst", required = true)
            protected String kontaktTekst;
            @XmlElement(name = "KontaktOlek", required = true)
            protected String kontaktOlek;
            @XmlElement(name = "KontaktAlates", required = true)
            protected String kontaktAlates;
            @XmlElement(name = "KontaktKuni", required = true)
            protected String kontaktKuni;
            @XmlElement(name = "KontaktMuutmisKpv", required = true)
            protected String kontaktMuutmisKpv;
            @XmlElement(name = "KontaktAsutus", required = true)
            protected String kontaktAsutus;

            /**
             * Gets the value of the kontaktLiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktLiik() {
                return kontaktLiik;
            }

            /**
             * Sets the value of the kontaktLiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktLiik(String value) {
                this.kontaktLiik = value;
            }

            /**
             * Gets the value of the kontaktTekst property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktTekst() {
                return kontaktTekst;
            }

            /**
             * Sets the value of the kontaktTekst property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktTekst(String value) {
                this.kontaktTekst = value;
            }

            /**
             * Gets the value of the kontaktOlek property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktOlek() {
                return kontaktOlek;
            }

            /**
             * Sets the value of the kontaktOlek property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktOlek(String value) {
                this.kontaktOlek = value;
            }

            /**
             * Gets the value of the kontaktAlates property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktAlates() {
                return kontaktAlates;
            }

            /**
             * Sets the value of the kontaktAlates property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktAlates(String value) {
                this.kontaktAlates = value;
            }

            /**
             * Gets the value of the kontaktKuni property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktKuni() {
                return kontaktKuni;
            }

            /**
             * Sets the value of the kontaktKuni property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktKuni(String value) {
                this.kontaktKuni = value;
            }

            /**
             * Gets the value of the kontaktMuutmisKpv property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktMuutmisKpv() {
                return kontaktMuutmisKpv;
            }

            /**
             * Sets the value of the kontaktMuutmisKpv property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktMuutmisKpv(String value) {
                this.kontaktMuutmisKpv = value;
            }

            /**
             * Gets the value of the kontaktAsutus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktAsutus() {
                return kontaktAsutus;
            }

            /**
             * Sets the value of the kontaktAsutus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktAsutus(String value) {
                this.kontaktAsutus = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="RahvusElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Rahvus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="EmakeelElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Emakeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="HaridusElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Haridus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "rahvusElemendiKood",
            "rahvus",
            "emakeelElemendiKood",
            "emakeel",
            "haridusElemendiKood",
            "haridus"
        })
        public static class StatistilisedAndmed {

            @XmlElement(name = "RahvusElemendiKood", required = true)
            protected String rahvusElemendiKood;
            @XmlElement(name = "Rahvus", required = true)
            protected String rahvus;
            @XmlElement(name = "EmakeelElemendiKood", required = true)
            protected String emakeelElemendiKood;
            @XmlElement(name = "Emakeel", required = true)
            protected String emakeel;
            @XmlElement(name = "HaridusElemendiKood", required = true)
            protected String haridusElemendiKood;
            @XmlElement(name = "Haridus", required = true)
            protected String haridus;

            /**
             * Gets the value of the rahvusElemendiKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRahvusElemendiKood() {
                return rahvusElemendiKood;
            }

            /**
             * Sets the value of the rahvusElemendiKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRahvusElemendiKood(String value) {
                this.rahvusElemendiKood = value;
            }

            /**
             * Gets the value of the rahvus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRahvus() {
                return rahvus;
            }

            /**
             * Sets the value of the rahvus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRahvus(String value) {
                this.rahvus = value;
            }

            /**
             * Gets the value of the emakeelElemendiKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEmakeelElemendiKood() {
                return emakeelElemendiKood;
            }

            /**
             * Sets the value of the emakeelElemendiKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEmakeelElemendiKood(String value) {
                this.emakeelElemendiKood = value;
            }

            /**
             * Gets the value of the emakeel property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEmakeel() {
                return emakeel;
            }

            /**
             * Sets the value of the emakeel property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEmakeel(String value) {
                this.emakeel = value;
            }

            /**
             * Gets the value of the haridusElemendiKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHaridusElemendiKood() {
                return haridusElemendiKood;
            }

            /**
             * Sets the value of the haridusElemendiKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHaridusElemendiKood(String value) {
                this.haridusElemendiKood = value;
            }

            /**
             * Gets the value of the haridus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHaridus() {
                return haridus;
            }

            /**
             * Sets the value of the haridus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHaridus(String value) {
                this.haridus = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="VRIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="MuutmisKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "riik",
            "vrIsikukood",
            "muutmisKpv"
        })
        public static class ValisriigiIsikukood {

            @XmlElement(name = "Riik", required = true)
            protected String riik;
            @XmlElement(name = "VRIsikukood", required = true)
            protected String vrIsikukood;
            @XmlElement(name = "MuutmisKpv", required = true)
            protected String muutmisKpv;

            /**
             * Gets the value of the riik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRiik() {
                return riik;
            }

            /**
             * Sets the value of the riik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRiik(String value) {
                this.riik = value;
            }

            /**
             * Gets the value of the vrIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getVRIsikukood() {
                return vrIsikukood;
            }

            /**
             * Sets the value of the vrIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setVRIsikukood(String value) {
                this.vrIsikukood = value;
            }

            /**
             * Gets the value of the muutmisKpv property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMuutmisKpv() {
                return muutmisKpv;
            }

            /**
             * Sets the value of the muutmisKpv property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMuutmisKpv(String value) {
                this.muutmisKpv = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="Kontakt" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="KontaktLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="KontaktTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="KontaktOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="KontaktAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="KontaktKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="KontaktMuutmisKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="KontaktAsutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="ValisriigiIsikukood" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="VRIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="MuutmisKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="StatistilisedAndmed" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="RahvusElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Rahvus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="EmakeelElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Emakeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="HaridusElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Haridus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "kontakt",
        "valisriigiIsikukood",
        "statistilisedAndmed"
    })
    public static class Isik {

        @XmlElement(name = "Kontakt")
        protected List<RR472IsikuKontaktStatResponseType.Isik.Kontakt> kontakt;
        @XmlElement(name = "ValisriigiIsikukood")
        protected List<RR472IsikuKontaktStatResponseType.Isik.ValisriigiIsikukood> valisriigiIsikukood;
        @XmlElement(name = "StatistilisedAndmed")
        protected List<RR472IsikuKontaktStatResponseType.Isik.StatistilisedAndmed> statistilisedAndmed;

        /**
         * Gets the value of the kontakt property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the kontakt property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getKontakt().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR472IsikuKontaktStatResponseType.Isik.Kontakt }
         * 
         * 
         */
        public List<RR472IsikuKontaktStatResponseType.Isik.Kontakt> getKontakt() {
            if (kontakt == null) {
                kontakt = new ArrayList<RR472IsikuKontaktStatResponseType.Isik.Kontakt>();
            }
            return this.kontakt;
        }

        /**
         * Gets the value of the valisriigiIsikukood property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the valisriigiIsikukood property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getValisriigiIsikukood().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR472IsikuKontaktStatResponseType.Isik.ValisriigiIsikukood }
         * 
         * 
         */
        public List<RR472IsikuKontaktStatResponseType.Isik.ValisriigiIsikukood> getValisriigiIsikukood() {
            if (valisriigiIsikukood == null) {
                valisriigiIsikukood = new ArrayList<RR472IsikuKontaktStatResponseType.Isik.ValisriigiIsikukood>();
            }
            return this.valisriigiIsikukood;
        }

        /**
         * Gets the value of the statistilisedAndmed property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the statistilisedAndmed property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getStatistilisedAndmed().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR472IsikuKontaktStatResponseType.Isik.StatistilisedAndmed }
         * 
         * 
         */
        public List<RR472IsikuKontaktStatResponseType.Isik.StatistilisedAndmed> getStatistilisedAndmed() {
            if (statistilisedAndmed == null) {
                statistilisedAndmed = new ArrayList<RR472IsikuKontaktStatResponseType.Isik.StatistilisedAndmed>();
            }
            return this.statistilisedAndmed;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="KontaktLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="KontaktTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="KontaktOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="KontaktAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="KontaktKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="KontaktMuutmisKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="KontaktAsutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "kontaktLiik",
            "kontaktTekst",
            "kontaktOlek",
            "kontaktAlates",
            "kontaktKuni",
            "kontaktMuutmisKpv",
            "kontaktAsutus"
        })
        public static class Kontakt {

            @XmlElement(name = "KontaktLiik", required = true)
            protected String kontaktLiik;
            @XmlElement(name = "KontaktTekst", required = true)
            protected String kontaktTekst;
            @XmlElement(name = "KontaktOlek", required = true)
            protected String kontaktOlek;
            @XmlElement(name = "KontaktAlates", required = true)
            protected String kontaktAlates;
            @XmlElement(name = "KontaktKuni", required = true)
            protected String kontaktKuni;
            @XmlElement(name = "KontaktMuutmisKpv", required = true)
            protected String kontaktMuutmisKpv;
            @XmlElement(name = "KontaktAsutus", required = true)
            protected String kontaktAsutus;

            /**
             * Gets the value of the kontaktLiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktLiik() {
                return kontaktLiik;
            }

            /**
             * Sets the value of the kontaktLiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktLiik(String value) {
                this.kontaktLiik = value;
            }

            /**
             * Gets the value of the kontaktTekst property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktTekst() {
                return kontaktTekst;
            }

            /**
             * Sets the value of the kontaktTekst property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktTekst(String value) {
                this.kontaktTekst = value;
            }

            /**
             * Gets the value of the kontaktOlek property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktOlek() {
                return kontaktOlek;
            }

            /**
             * Sets the value of the kontaktOlek property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktOlek(String value) {
                this.kontaktOlek = value;
            }

            /**
             * Gets the value of the kontaktAlates property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktAlates() {
                return kontaktAlates;
            }

            /**
             * Sets the value of the kontaktAlates property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktAlates(String value) {
                this.kontaktAlates = value;
            }

            /**
             * Gets the value of the kontaktKuni property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktKuni() {
                return kontaktKuni;
            }

            /**
             * Sets the value of the kontaktKuni property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktKuni(String value) {
                this.kontaktKuni = value;
            }

            /**
             * Gets the value of the kontaktMuutmisKpv property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktMuutmisKpv() {
                return kontaktMuutmisKpv;
            }

            /**
             * Sets the value of the kontaktMuutmisKpv property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktMuutmisKpv(String value) {
                this.kontaktMuutmisKpv = value;
            }

            /**
             * Gets the value of the kontaktAsutus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktAsutus() {
                return kontaktAsutus;
            }

            /**
             * Sets the value of the kontaktAsutus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktAsutus(String value) {
                this.kontaktAsutus = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="RahvusElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Rahvus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="EmakeelElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Emakeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="HaridusElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Haridus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "rahvusElemendiKood",
            "rahvus",
            "emakeelElemendiKood",
            "emakeel",
            "haridusElemendiKood",
            "haridus"
        })
        public static class StatistilisedAndmed {

            @XmlElement(name = "RahvusElemendiKood", required = true)
            protected String rahvusElemendiKood;
            @XmlElement(name = "Rahvus", required = true)
            protected String rahvus;
            @XmlElement(name = "EmakeelElemendiKood", required = true)
            protected String emakeelElemendiKood;
            @XmlElement(name = "Emakeel", required = true)
            protected String emakeel;
            @XmlElement(name = "HaridusElemendiKood", required = true)
            protected String haridusElemendiKood;
            @XmlElement(name = "Haridus", required = true)
            protected String haridus;

            /**
             * Gets the value of the rahvusElemendiKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRahvusElemendiKood() {
                return rahvusElemendiKood;
            }

            /**
             * Sets the value of the rahvusElemendiKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRahvusElemendiKood(String value) {
                this.rahvusElemendiKood = value;
            }

            /**
             * Gets the value of the rahvus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRahvus() {
                return rahvus;
            }

            /**
             * Sets the value of the rahvus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRahvus(String value) {
                this.rahvus = value;
            }

            /**
             * Gets the value of the emakeelElemendiKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEmakeelElemendiKood() {
                return emakeelElemendiKood;
            }

            /**
             * Sets the value of the emakeelElemendiKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEmakeelElemendiKood(String value) {
                this.emakeelElemendiKood = value;
            }

            /**
             * Gets the value of the emakeel property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEmakeel() {
                return emakeel;
            }

            /**
             * Sets the value of the emakeel property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEmakeel(String value) {
                this.emakeel = value;
            }

            /**
             * Gets the value of the haridusElemendiKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHaridusElemendiKood() {
                return haridusElemendiKood;
            }

            /**
             * Sets the value of the haridusElemendiKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHaridusElemendiKood(String value) {
                this.haridusElemendiKood = value;
            }

            /**
             * Gets the value of the haridus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHaridus() {
                return haridus;
            }

            /**
             * Sets the value of the haridus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHaridus(String value) {
                this.haridus = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="VRIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="MuutmisKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "riik",
            "vrIsikukood",
            "muutmisKpv"
        })
        public static class ValisriigiIsikukood {

            @XmlElement(name = "Riik", required = true)
            protected String riik;
            @XmlElement(name = "VRIsikukood", required = true)
            protected String vrIsikukood;
            @XmlElement(name = "MuutmisKpv", required = true)
            protected String muutmisKpv;

            /**
             * Gets the value of the riik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRiik() {
                return riik;
            }

            /**
             * Sets the value of the riik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRiik(String value) {
                this.riik = value;
            }

            /**
             * Gets the value of the vrIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getVRIsikukood() {
                return vrIsikukood;
            }

            /**
             * Sets the value of the vrIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setVRIsikukood(String value) {
                this.vrIsikukood = value;
            }

            /**
             * Gets the value of the muutmisKpv property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMuutmisKpv() {
                return muutmisKpv;
            }

            /**
             * Sets the value of the muutmisKpv property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMuutmisKpv(String value) {
                this.muutmisKpv = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="SuheIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="SuhePerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="SuheEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="SuheSuhtetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="HooldusoigusedLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="HooldusoigusedOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Kontakt" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="KontaktLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="KontaktTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="KontaktOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="KontaktAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="KontaktKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="KontaktMuutmisKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="KontaktAsutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="ValisriigiIsikukood" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="VRIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="MuutmisKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="StatistilisedAndmed" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="RahvusElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Rahvus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="EmakeelElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Emakeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="HaridusElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Haridus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "isikuStaatus",
        "suheIsikukood",
        "suhePerenimi",
        "suheEesnimi",
        "suheSuhtetyyp",
        "hooldusoigusedLiik",
        "hooldusoigusedOlek",
        "kontakt",
        "valisriigiIsikukood",
        "statistilisedAndmed"
    })
    public static class Laps {

        @XmlElement(name = "IsikuStaatus", required = true)
        protected String isikuStaatus;
        @XmlElement(name = "SuheIsikukood", required = true)
        protected String suheIsikukood;
        @XmlElement(name = "SuhePerenimi", required = true)
        protected String suhePerenimi;
        @XmlElement(name = "SuheEesnimi", required = true)
        protected String suheEesnimi;
        @XmlElement(name = "SuheSuhtetyyp", required = true)
        protected String suheSuhtetyyp;
        @XmlElement(name = "HooldusoigusedLiik", required = true)
        protected String hooldusoigusedLiik;
        @XmlElement(name = "HooldusoigusedOlek", required = true)
        protected String hooldusoigusedOlek;
        @XmlElement(name = "Kontakt")
        protected List<RR472IsikuKontaktStatResponseType.Laps.Kontakt> kontakt;
        @XmlElement(name = "ValisriigiIsikukood")
        protected List<RR472IsikuKontaktStatResponseType.Laps.ValisriigiIsikukood> valisriigiIsikukood;
        @XmlElement(name = "StatistilisedAndmed")
        protected List<RR472IsikuKontaktStatResponseType.Laps.StatistilisedAndmed> statistilisedAndmed;

        /**
         * Gets the value of the isikuStaatus property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIsikuStaatus() {
            return isikuStaatus;
        }

        /**
         * Sets the value of the isikuStaatus property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsikuStaatus(String value) {
            this.isikuStaatus = value;
        }

        /**
         * Gets the value of the suheIsikukood property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSuheIsikukood() {
            return suheIsikukood;
        }

        /**
         * Sets the value of the suheIsikukood property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSuheIsikukood(String value) {
            this.suheIsikukood = value;
        }

        /**
         * Gets the value of the suhePerenimi property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSuhePerenimi() {
            return suhePerenimi;
        }

        /**
         * Sets the value of the suhePerenimi property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSuhePerenimi(String value) {
            this.suhePerenimi = value;
        }

        /**
         * Gets the value of the suheEesnimi property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSuheEesnimi() {
            return suheEesnimi;
        }

        /**
         * Sets the value of the suheEesnimi property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSuheEesnimi(String value) {
            this.suheEesnimi = value;
        }

        /**
         * Gets the value of the suheSuhtetyyp property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSuheSuhtetyyp() {
            return suheSuhtetyyp;
        }

        /**
         * Sets the value of the suheSuhtetyyp property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSuheSuhtetyyp(String value) {
            this.suheSuhtetyyp = value;
        }

        /**
         * Gets the value of the hooldusoigusedLiik property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHooldusoigusedLiik() {
            return hooldusoigusedLiik;
        }

        /**
         * Sets the value of the hooldusoigusedLiik property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHooldusoigusedLiik(String value) {
            this.hooldusoigusedLiik = value;
        }

        /**
         * Gets the value of the hooldusoigusedOlek property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHooldusoigusedOlek() {
            return hooldusoigusedOlek;
        }

        /**
         * Sets the value of the hooldusoigusedOlek property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHooldusoigusedOlek(String value) {
            this.hooldusoigusedOlek = value;
        }

        /**
         * Gets the value of the kontakt property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the kontakt property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getKontakt().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR472IsikuKontaktStatResponseType.Laps.Kontakt }
         * 
         * 
         */
        public List<RR472IsikuKontaktStatResponseType.Laps.Kontakt> getKontakt() {
            if (kontakt == null) {
                kontakt = new ArrayList<RR472IsikuKontaktStatResponseType.Laps.Kontakt>();
            }
            return this.kontakt;
        }

        /**
         * Gets the value of the valisriigiIsikukood property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the valisriigiIsikukood property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getValisriigiIsikukood().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR472IsikuKontaktStatResponseType.Laps.ValisriigiIsikukood }
         * 
         * 
         */
        public List<RR472IsikuKontaktStatResponseType.Laps.ValisriigiIsikukood> getValisriigiIsikukood() {
            if (valisriigiIsikukood == null) {
                valisriigiIsikukood = new ArrayList<RR472IsikuKontaktStatResponseType.Laps.ValisriigiIsikukood>();
            }
            return this.valisriigiIsikukood;
        }

        /**
         * Gets the value of the statistilisedAndmed property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the statistilisedAndmed property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getStatistilisedAndmed().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR472IsikuKontaktStatResponseType.Laps.StatistilisedAndmed }
         * 
         * 
         */
        public List<RR472IsikuKontaktStatResponseType.Laps.StatistilisedAndmed> getStatistilisedAndmed() {
            if (statistilisedAndmed == null) {
                statistilisedAndmed = new ArrayList<RR472IsikuKontaktStatResponseType.Laps.StatistilisedAndmed>();
            }
            return this.statistilisedAndmed;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="KontaktLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="KontaktTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="KontaktOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="KontaktAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="KontaktKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="KontaktMuutmisKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="KontaktAsutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "kontaktLiik",
            "kontaktTekst",
            "kontaktOlek",
            "kontaktAlates",
            "kontaktKuni",
            "kontaktMuutmisKpv",
            "kontaktAsutus"
        })
        public static class Kontakt {

            @XmlElement(name = "KontaktLiik", required = true)
            protected String kontaktLiik;
            @XmlElement(name = "KontaktTekst", required = true)
            protected String kontaktTekst;
            @XmlElement(name = "KontaktOlek", required = true)
            protected String kontaktOlek;
            @XmlElement(name = "KontaktAlates", required = true)
            protected String kontaktAlates;
            @XmlElement(name = "KontaktKuni", required = true)
            protected String kontaktKuni;
            @XmlElement(name = "KontaktMuutmisKpv", required = true)
            protected String kontaktMuutmisKpv;
            @XmlElement(name = "KontaktAsutus", required = true)
            protected String kontaktAsutus;

            /**
             * Gets the value of the kontaktLiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktLiik() {
                return kontaktLiik;
            }

            /**
             * Sets the value of the kontaktLiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktLiik(String value) {
                this.kontaktLiik = value;
            }

            /**
             * Gets the value of the kontaktTekst property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktTekst() {
                return kontaktTekst;
            }

            /**
             * Sets the value of the kontaktTekst property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktTekst(String value) {
                this.kontaktTekst = value;
            }

            /**
             * Gets the value of the kontaktOlek property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktOlek() {
                return kontaktOlek;
            }

            /**
             * Sets the value of the kontaktOlek property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktOlek(String value) {
                this.kontaktOlek = value;
            }

            /**
             * Gets the value of the kontaktAlates property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktAlates() {
                return kontaktAlates;
            }

            /**
             * Sets the value of the kontaktAlates property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktAlates(String value) {
                this.kontaktAlates = value;
            }

            /**
             * Gets the value of the kontaktKuni property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktKuni() {
                return kontaktKuni;
            }

            /**
             * Sets the value of the kontaktKuni property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktKuni(String value) {
                this.kontaktKuni = value;
            }

            /**
             * Gets the value of the kontaktMuutmisKpv property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktMuutmisKpv() {
                return kontaktMuutmisKpv;
            }

            /**
             * Sets the value of the kontaktMuutmisKpv property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktMuutmisKpv(String value) {
                this.kontaktMuutmisKpv = value;
            }

            /**
             * Gets the value of the kontaktAsutus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktAsutus() {
                return kontaktAsutus;
            }

            /**
             * Sets the value of the kontaktAsutus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktAsutus(String value) {
                this.kontaktAsutus = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="RahvusElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Rahvus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="EmakeelElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Emakeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="HaridusElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Haridus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "rahvusElemendiKood",
            "rahvus",
            "emakeelElemendiKood",
            "emakeel",
            "haridusElemendiKood",
            "haridus"
        })
        public static class StatistilisedAndmed {

            @XmlElement(name = "RahvusElemendiKood", required = true)
            protected String rahvusElemendiKood;
            @XmlElement(name = "Rahvus", required = true)
            protected String rahvus;
            @XmlElement(name = "EmakeelElemendiKood", required = true)
            protected String emakeelElemendiKood;
            @XmlElement(name = "Emakeel", required = true)
            protected String emakeel;
            @XmlElement(name = "HaridusElemendiKood", required = true)
            protected String haridusElemendiKood;
            @XmlElement(name = "Haridus", required = true)
            protected String haridus;

            /**
             * Gets the value of the rahvusElemendiKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRahvusElemendiKood() {
                return rahvusElemendiKood;
            }

            /**
             * Sets the value of the rahvusElemendiKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRahvusElemendiKood(String value) {
                this.rahvusElemendiKood = value;
            }

            /**
             * Gets the value of the rahvus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRahvus() {
                return rahvus;
            }

            /**
             * Sets the value of the rahvus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRahvus(String value) {
                this.rahvus = value;
            }

            /**
             * Gets the value of the emakeelElemendiKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEmakeelElemendiKood() {
                return emakeelElemendiKood;
            }

            /**
             * Sets the value of the emakeelElemendiKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEmakeelElemendiKood(String value) {
                this.emakeelElemendiKood = value;
            }

            /**
             * Gets the value of the emakeel property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEmakeel() {
                return emakeel;
            }

            /**
             * Sets the value of the emakeel property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEmakeel(String value) {
                this.emakeel = value;
            }

            /**
             * Gets the value of the haridusElemendiKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHaridusElemendiKood() {
                return haridusElemendiKood;
            }

            /**
             * Sets the value of the haridusElemendiKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHaridusElemendiKood(String value) {
                this.haridusElemendiKood = value;
            }

            /**
             * Gets the value of the haridus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHaridus() {
                return haridus;
            }

            /**
             * Sets the value of the haridus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHaridus(String value) {
                this.haridus = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="VRIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="MuutmisKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "riik",
            "vrIsikukood",
            "muutmisKpv"
        })
        public static class ValisriigiIsikukood {

            @XmlElement(name = "Riik", required = true)
            protected String riik;
            @XmlElement(name = "VRIsikukood", required = true)
            protected String vrIsikukood;
            @XmlElement(name = "MuutmisKpv", required = true)
            protected String muutmisKpv;

            /**
             * Gets the value of the riik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRiik() {
                return riik;
            }

            /**
             * Sets the value of the riik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRiik(String value) {
                this.riik = value;
            }

            /**
             * Gets the value of the vrIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getVRIsikukood() {
                return vrIsikukood;
            }

            /**
             * Sets the value of the vrIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setVRIsikukood(String value) {
                this.vrIsikukood = value;
            }

            /**
             * Gets the value of the muutmisKpv property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMuutmisKpv() {
                return muutmisKpv;
            }

            /**
             * Sets the value of the muutmisKpv property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMuutmisKpv(String value) {
                this.muutmisKpv = value;
            }

        }

    }

}
