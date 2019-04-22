
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR406IsikSurmaDokResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR406IsikSurmaDokResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikupnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikuenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikusynnikuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikusurmakuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikudoknr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikudokasutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ElukohaEHAK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Elukohttekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AvaldajaDokumendil" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Info" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR406IsikSurmaDokResponseType", propOrder = {
    "isikukood",
    "isikupnimi",
    "isikuenimi",
    "isikusynnikuup",
    "isikusurmakuup",
    "isikudoknr",
    "isikudokasutus",
    "elukohaEHAK",
    "elukohttekst",
    "avaldajaDokumendil",
    "info",
    "veakood",
    "veatekst"
})
public class RR406IsikSurmaDokResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Isikukood", required = true)
    protected String isikukood;
    @XmlElement(name = "Isikupnimi", required = true)
    protected String isikupnimi;
    @XmlElement(name = "Isikuenimi", required = true)
    protected String isikuenimi;
    @XmlElement(name = "Isikusynnikuup", required = true)
    protected String isikusynnikuup;
    @XmlElement(name = "Isikusurmakuup", required = true)
    protected String isikusurmakuup;
    @XmlElement(name = "Isikudoknr", required = true)
    protected String isikudoknr;
    @XmlElement(name = "Isikudokasutus", required = true)
    protected String isikudokasutus;
    @XmlElement(name = "ElukohaEHAK", required = true)
    protected String elukohaEHAK;
    @XmlElement(name = "Elukohttekst", required = true)
    protected String elukohttekst;
    @XmlElement(name = "AvaldajaDokumendil", required = true)
    protected String avaldajaDokumendil;
    @XmlElement(name = "Info", required = true)
    protected String info;
    @XmlElement(name = "Veakood", required = true)
    protected BigInteger veakood;
    @XmlElement(name = "Veatekst", required = true)
    protected String veatekst;

    /**
     * Gets the value of the isikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukood() {
        return isikukood;
    }

    /**
     * Sets the value of the isikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukood(String value) {
        this.isikukood = value;
    }

    /**
     * Gets the value of the isikupnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikupnimi() {
        return isikupnimi;
    }

    /**
     * Sets the value of the isikupnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikupnimi(String value) {
        this.isikupnimi = value;
    }

    /**
     * Gets the value of the isikuenimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuenimi() {
        return isikuenimi;
    }

    /**
     * Sets the value of the isikuenimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuenimi(String value) {
        this.isikuenimi = value;
    }

    /**
     * Gets the value of the isikusynnikuup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikusynnikuup() {
        return isikusynnikuup;
    }

    /**
     * Sets the value of the isikusynnikuup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikusynnikuup(String value) {
        this.isikusynnikuup = value;
    }

    /**
     * Gets the value of the isikusurmakuup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikusurmakuup() {
        return isikusurmakuup;
    }

    /**
     * Sets the value of the isikusurmakuup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikusurmakuup(String value) {
        this.isikusurmakuup = value;
    }

    /**
     * Gets the value of the isikudoknr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikudoknr() {
        return isikudoknr;
    }

    /**
     * Sets the value of the isikudoknr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikudoknr(String value) {
        this.isikudoknr = value;
    }

    /**
     * Gets the value of the isikudokasutus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikudokasutus() {
        return isikudokasutus;
    }

    /**
     * Sets the value of the isikudokasutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikudokasutus(String value) {
        this.isikudokasutus = value;
    }

    /**
     * Gets the value of the elukohaEHAK property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElukohaEHAK() {
        return elukohaEHAK;
    }

    /**
     * Sets the value of the elukohaEHAK property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElukohaEHAK(String value) {
        this.elukohaEHAK = value;
    }

    /**
     * Gets the value of the elukohttekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElukohttekst() {
        return elukohttekst;
    }

    /**
     * Sets the value of the elukohttekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElukohttekst(String value) {
        this.elukohttekst = value;
    }

    /**
     * Gets the value of the avaldajaDokumendil property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAvaldajaDokumendil() {
        return avaldajaDokumendil;
    }

    /**
     * Sets the value of the avaldajaDokumendil property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAvaldajaDokumendil(String value) {
        this.avaldajaDokumendil = value;
    }

    /**
     * Gets the value of the info property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfo() {
        return info;
    }

    /**
     * Sets the value of the info property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfo(String value) {
        this.info = value;
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
