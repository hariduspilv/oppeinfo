
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR29IsikuElukohtResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR29IsikuElukohtResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IsikuEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuSurmaaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuElukohaLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuLubaKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuElukohtAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuElukohtRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuElukohtMK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuElukohtMaakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuElukohtVK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuElukohtVald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuElukohtVKT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuElukohtKK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuElukohtAsula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuElukohtKKT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuElukohtTanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuElukohtMaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuElukohtKorter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuElukohtSihtnr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuElukohtVälisriigis" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Veakood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="Veatekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR29IsikuElukohtResponseType", propOrder = {
    "isikuEesnimi",
    "isikuPerenimi",
    "isikuStaatus",
    "isikuSurmaaeg",
    "isikuElukohaLiik",
    "isikuLubaKuni",
    "isikuElukohtAlates",
    "isikuElukohtRiik",
    "isikuElukohtMK",
    "isikuElukohtMaakond",
    "isikuElukohtVK",
    "isikuElukohtVald",
    "isikuElukohtVKT",
    "isikuElukohtKK",
    "isikuElukohtAsula",
    "isikuElukohtKKT",
    "isikuElukohtTanav",
    "isikuElukohtMaja",
    "isikuElukohtKorter",
    "isikuElukohtSihtnr",
    "isikuElukohtValisriigis",
    "veakood",
    "veatekst"
})
public class RR29IsikuElukohtResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "IsikuEesnimi", required = true)
    protected String isikuEesnimi;
    @XmlElement(name = "IsikuPerenimi", required = true)
    protected String isikuPerenimi;
    @XmlElement(name = "IsikuStaatus", required = true)
    protected String isikuStaatus;
    @XmlElement(name = "IsikuSurmaaeg", required = true)
    protected String isikuSurmaaeg;
    @XmlElement(name = "IsikuElukohaLiik", required = true)
    protected String isikuElukohaLiik;
    @XmlElement(name = "IsikuLubaKuni", required = true)
    protected String isikuLubaKuni;
    @XmlElement(name = "IsikuElukohtAlates", required = true)
    protected String isikuElukohtAlates;
    @XmlElement(name = "IsikuElukohtRiik", required = true)
    protected String isikuElukohtRiik;
    @XmlElement(name = "IsikuElukohtMK", required = true)
    protected String isikuElukohtMK;
    @XmlElement(name = "IsikuElukohtMaakond", required = true)
    protected String isikuElukohtMaakond;
    @XmlElement(name = "IsikuElukohtVK", required = true)
    protected String isikuElukohtVK;
    @XmlElement(name = "IsikuElukohtVald", required = true)
    protected String isikuElukohtVald;
    @XmlElement(name = "IsikuElukohtVKT", required = true)
    protected String isikuElukohtVKT;
    @XmlElement(name = "IsikuElukohtKK", required = true)
    protected String isikuElukohtKK;
    @XmlElement(name = "IsikuElukohtAsula", required = true)
    protected String isikuElukohtAsula;
    @XmlElement(name = "IsikuElukohtKKT", required = true)
    protected String isikuElukohtKKT;
    @XmlElement(name = "IsikuElukohtTanav", required = true)
    protected String isikuElukohtTanav;
    @XmlElement(name = "IsikuElukohtMaja", required = true)
    protected String isikuElukohtMaja;
    @XmlElement(name = "IsikuElukohtKorter", required = true)
    protected String isikuElukohtKorter;
    @XmlElement(name = "IsikuElukohtSihtnr", required = true)
    protected String isikuElukohtSihtnr;
    @XmlElement(name = "IsikuElukohtVälisriigis", required = true)
    protected String isikuElukohtValisriigis;
    @XmlElement(name = "Veakood", required = true)
    protected BigInteger veakood;
    @XmlElement(name = "Veatekst", required = true)
    protected String veatekst;

    /**
     * Gets the value of the isikuEesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuEesnimi() {
        return isikuEesnimi;
    }

    /**
     * Sets the value of the isikuEesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuEesnimi(String value) {
        this.isikuEesnimi = value;
    }

    /**
     * Gets the value of the isikuPerenimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuPerenimi() {
        return isikuPerenimi;
    }

    /**
     * Sets the value of the isikuPerenimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuPerenimi(String value) {
        this.isikuPerenimi = value;
    }

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
     * Gets the value of the isikuSurmaaeg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuSurmaaeg() {
        return isikuSurmaaeg;
    }

    /**
     * Sets the value of the isikuSurmaaeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuSurmaaeg(String value) {
        this.isikuSurmaaeg = value;
    }

    /**
     * Gets the value of the isikuElukohaLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuElukohaLiik() {
        return isikuElukohaLiik;
    }

    /**
     * Sets the value of the isikuElukohaLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuElukohaLiik(String value) {
        this.isikuElukohaLiik = value;
    }

    /**
     * Gets the value of the isikuLubaKuni property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuLubaKuni() {
        return isikuLubaKuni;
    }

    /**
     * Sets the value of the isikuLubaKuni property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuLubaKuni(String value) {
        this.isikuLubaKuni = value;
    }

    /**
     * Gets the value of the isikuElukohtAlates property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuElukohtAlates() {
        return isikuElukohtAlates;
    }

    /**
     * Sets the value of the isikuElukohtAlates property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuElukohtAlates(String value) {
        this.isikuElukohtAlates = value;
    }

    /**
     * Gets the value of the isikuElukohtRiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuElukohtRiik() {
        return isikuElukohtRiik;
    }

    /**
     * Sets the value of the isikuElukohtRiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuElukohtRiik(String value) {
        this.isikuElukohtRiik = value;
    }

    /**
     * Gets the value of the isikuElukohtMK property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuElukohtMK() {
        return isikuElukohtMK;
    }

    /**
     * Sets the value of the isikuElukohtMK property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuElukohtMK(String value) {
        this.isikuElukohtMK = value;
    }

    /**
     * Gets the value of the isikuElukohtMaakond property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuElukohtMaakond() {
        return isikuElukohtMaakond;
    }

    /**
     * Sets the value of the isikuElukohtMaakond property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuElukohtMaakond(String value) {
        this.isikuElukohtMaakond = value;
    }

    /**
     * Gets the value of the isikuElukohtVK property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuElukohtVK() {
        return isikuElukohtVK;
    }

    /**
     * Sets the value of the isikuElukohtVK property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuElukohtVK(String value) {
        this.isikuElukohtVK = value;
    }

    /**
     * Gets the value of the isikuElukohtVald property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuElukohtVald() {
        return isikuElukohtVald;
    }

    /**
     * Sets the value of the isikuElukohtVald property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuElukohtVald(String value) {
        this.isikuElukohtVald = value;
    }

    /**
     * Gets the value of the isikuElukohtVKT property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuElukohtVKT() {
        return isikuElukohtVKT;
    }

    /**
     * Sets the value of the isikuElukohtVKT property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuElukohtVKT(String value) {
        this.isikuElukohtVKT = value;
    }

    /**
     * Gets the value of the isikuElukohtKK property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuElukohtKK() {
        return isikuElukohtKK;
    }

    /**
     * Sets the value of the isikuElukohtKK property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuElukohtKK(String value) {
        this.isikuElukohtKK = value;
    }

    /**
     * Gets the value of the isikuElukohtAsula property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuElukohtAsula() {
        return isikuElukohtAsula;
    }

    /**
     * Sets the value of the isikuElukohtAsula property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuElukohtAsula(String value) {
        this.isikuElukohtAsula = value;
    }

    /**
     * Gets the value of the isikuElukohtKKT property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuElukohtKKT() {
        return isikuElukohtKKT;
    }

    /**
     * Sets the value of the isikuElukohtKKT property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuElukohtKKT(String value) {
        this.isikuElukohtKKT = value;
    }

    /**
     * Gets the value of the isikuElukohtTanav property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuElukohtTanav() {
        return isikuElukohtTanav;
    }

    /**
     * Sets the value of the isikuElukohtTanav property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuElukohtTanav(String value) {
        this.isikuElukohtTanav = value;
    }

    /**
     * Gets the value of the isikuElukohtMaja property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuElukohtMaja() {
        return isikuElukohtMaja;
    }

    /**
     * Sets the value of the isikuElukohtMaja property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuElukohtMaja(String value) {
        this.isikuElukohtMaja = value;
    }

    /**
     * Gets the value of the isikuElukohtKorter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuElukohtKorter() {
        return isikuElukohtKorter;
    }

    /**
     * Sets the value of the isikuElukohtKorter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuElukohtKorter(String value) {
        this.isikuElukohtKorter = value;
    }

    /**
     * Gets the value of the isikuElukohtSihtnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuElukohtSihtnr() {
        return isikuElukohtSihtnr;
    }

    /**
     * Sets the value of the isikuElukohtSihtnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuElukohtSihtnr(String value) {
        this.isikuElukohtSihtnr = value;
    }

    /**
     * Gets the value of the isikuElukohtV�lisriigis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuElukohtValisriigis() {
        return isikuElukohtValisriigis;
    }

    /**
     * Sets the value of the isikuElukohtV�lisriigis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuElukohtValisriigis(String value) {
        this.isikuElukohtValisriigis = value;
    }

    /**
     * Gets the value of the veakood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVeakood() {
        return veakood;
    }

    /**
     * Sets the value of the veakood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVeakood(BigInteger value) {
        this.veakood = value;
    }

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

}
