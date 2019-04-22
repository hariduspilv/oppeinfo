
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR48isikPRIAResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR48isikPRIAResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikupnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikuenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikuolek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikusurmakp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikuriik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikumaak" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikuvald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikuasula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikutn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikumaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikukrt" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Veakood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="veatekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR48isikPRIAResponseType", propOrder = {
    "isikukood",
    "isikupnimi",
    "isikuenimi",
    "isikuolek",
    "isikusurmakp",
    "isikuriik",
    "isikumaak",
    "isikuvald",
    "isikuasula",
    "isikutn",
    "isikumaja",
    "isikukrt",
    "veakood",
    "veatekst"
})
public class RR48IsikPRIAResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Isikukood", required = true)
    protected String isikukood;
    @XmlElement(name = "Isikupnimi", required = true)
    protected String isikupnimi;
    @XmlElement(name = "Isikuenimi", required = true)
    protected String isikuenimi;
    @XmlElement(name = "Isikuolek", required = true)
    protected String isikuolek;
    @XmlElement(name = "Isikusurmakp", required = true)
    protected String isikusurmakp;
    @XmlElement(name = "Isikuriik", required = true)
    protected String isikuriik;
    @XmlElement(name = "Isikumaak", required = true)
    protected String isikumaak;
    @XmlElement(name = "Isikuvald", required = true)
    protected String isikuvald;
    @XmlElement(name = "Isikuasula", required = true)
    protected String isikuasula;
    @XmlElement(name = "Isikutn", required = true)
    protected String isikutn;
    @XmlElement(name = "Isikumaja", required = true)
    protected String isikumaja;
    @XmlElement(name = "Isikukrt", required = true)
    protected String isikukrt;
    @XmlElement(name = "Veakood", required = true)
    protected BigInteger veakood;
    @XmlElement(required = true)
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
     * Gets the value of the isikuolek property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuolek() {
        return isikuolek;
    }

    /**
     * Sets the value of the isikuolek property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuolek(String value) {
        this.isikuolek = value;
    }

    /**
     * Gets the value of the isikusurmakp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikusurmakp() {
        return isikusurmakp;
    }

    /**
     * Sets the value of the isikusurmakp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikusurmakp(String value) {
        this.isikusurmakp = value;
    }

    /**
     * Gets the value of the isikuriik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuriik() {
        return isikuriik;
    }

    /**
     * Sets the value of the isikuriik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuriik(String value) {
        this.isikuriik = value;
    }

    /**
     * Gets the value of the isikumaak property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikumaak() {
        return isikumaak;
    }

    /**
     * Sets the value of the isikumaak property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikumaak(String value) {
        this.isikumaak = value;
    }

    /**
     * Gets the value of the isikuvald property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuvald() {
        return isikuvald;
    }

    /**
     * Sets the value of the isikuvald property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuvald(String value) {
        this.isikuvald = value;
    }

    /**
     * Gets the value of the isikuasula property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuasula() {
        return isikuasula;
    }

    /**
     * Sets the value of the isikuasula property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuasula(String value) {
        this.isikuasula = value;
    }

    /**
     * Gets the value of the isikutn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikutn() {
        return isikutn;
    }

    /**
     * Sets the value of the isikutn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikutn(String value) {
        this.isikutn = value;
    }

    /**
     * Gets the value of the isikumaja property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikumaja() {
        return isikumaja;
    }

    /**
     * Sets the value of the isikumaja property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikumaja(String value) {
        this.isikumaja = value;
    }

    /**
     * Gets the value of the isikukrt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukrt() {
        return isikukrt;
    }

    /**
     * Sets the value of the isikukrt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukrt(String value) {
        this.isikukrt = value;
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
