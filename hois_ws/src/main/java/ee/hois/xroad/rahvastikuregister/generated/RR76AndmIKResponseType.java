
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR76AndmIKResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR76AndmIKResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Isikupnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Isikuenimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Isikuolek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Kirjestaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikusugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikusynnikp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikusurmakp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Isikukodak" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Isikukodaknm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Isikusynnivald" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Isikusynnikoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Isikuvald" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Isikuelukoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="poiVeaKood" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="pocVeaTekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR76AndmIKResponseType", propOrder = {
    "isikupnimi",
    "isikuenimi",
    "isikuolek",
    "kirjestaatus",
    "isikusugu",
    "isikusynnikp",
    "isikusurmakp",
    "isikukodak",
    "isikukodaknm",
    "isikusynnivald",
    "isikusynnikoht",
    "isikuvald",
    "isikuelukoht",
    "poiVeaKood",
    "pocVeaTekst"
})
public class RR76AndmIKResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Isikupnimi")
    protected String isikupnimi;
    @XmlElement(name = "Isikuenimi")
    protected String isikuenimi;
    @XmlElement(name = "Isikuolek", required = true)
    protected String isikuolek;
    @XmlElement(name = "Kirjestaatus", required = true)
    protected String kirjestaatus;
    @XmlElement(name = "Isikusugu", required = true)
    protected String isikusugu;
    @XmlElement(name = "Isikusynnikp", required = true)
    protected String isikusynnikp;
    @XmlElement(name = "Isikusurmakp")
    protected String isikusurmakp;
    @XmlElement(name = "Isikukodak")
    protected String isikukodak;
    @XmlElement(name = "Isikukodaknm")
    protected String isikukodaknm;
    @XmlElement(name = "Isikusynnivald")
    protected String isikusynnivald;
    @XmlElement(name = "Isikusynnikoht")
    protected String isikusynnikoht;
    @XmlElement(name = "Isikuvald")
    protected String isikuvald;
    @XmlElement(name = "Isikuelukoht")
    protected String isikuelukoht;
    protected BigInteger poiVeaKood;
    protected String pocVeaTekst;

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
     * Gets the value of the kirjestaatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKirjestaatus() {
        return kirjestaatus;
    }

    /**
     * Sets the value of the kirjestaatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKirjestaatus(String value) {
        this.kirjestaatus = value;
    }

    /**
     * Gets the value of the isikusugu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikusugu() {
        return isikusugu;
    }

    /**
     * Sets the value of the isikusugu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikusugu(String value) {
        this.isikusugu = value;
    }

    /**
     * Gets the value of the isikusynnikp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikusynnikp() {
        return isikusynnikp;
    }

    /**
     * Sets the value of the isikusynnikp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikusynnikp(String value) {
        this.isikusynnikp = value;
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
     * Gets the value of the isikukodak property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukodak() {
        return isikukodak;
    }

    /**
     * Sets the value of the isikukodak property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukodak(String value) {
        this.isikukodak = value;
    }

    /**
     * Gets the value of the isikukodaknm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukodaknm() {
        return isikukodaknm;
    }

    /**
     * Sets the value of the isikukodaknm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukodaknm(String value) {
        this.isikukodaknm = value;
    }

    /**
     * Gets the value of the isikusynnivald property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikusynnivald() {
        return isikusynnivald;
    }

    /**
     * Sets the value of the isikusynnivald property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikusynnivald(String value) {
        this.isikusynnivald = value;
    }

    /**
     * Gets the value of the isikusynnikoht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikusynnikoht() {
        return isikusynnikoht;
    }

    /**
     * Sets the value of the isikusynnikoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikusynnikoht(String value) {
        this.isikusynnikoht = value;
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
     * Gets the value of the isikuelukoht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuelukoht() {
        return isikuelukoht;
    }

    /**
     * Sets the value of the isikuelukoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuelukoht(String value) {
        this.isikuelukoht = value;
    }

    /**
     * Gets the value of the poiVeaKood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPoiVeaKood() {
        return poiVeaKood;
    }

    /**
     * Sets the value of the poiVeaKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPoiVeaKood(BigInteger value) {
        this.poiVeaKood = value;
    }

    /**
     * Gets the value of the pocVeaTekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPocVeaTekst() {
        return pocVeaTekst;
    }

    /**
     * Sets the value of the pocVeaTekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPocVeaTekst(String value) {
        this.pocVeaTekst = value;
    }

}
