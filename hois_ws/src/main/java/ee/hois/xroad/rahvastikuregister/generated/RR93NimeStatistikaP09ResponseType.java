
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR93NimeStatistikaP09ResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR93NimeStatistikaP09ResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="pocIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="pocIsikuEnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="pocIsikuPnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="pocTeenusekood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AVVeaTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Vastuse_Tekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Veakood" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="Veatekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR93NimeStatistikaP09ResponseType", propOrder = {
    "pocIsikukood",
    "pocIsikuEnimi",
    "pocIsikuPnimi",
    "pocTeenusekood",
    "avVeaTekst",
    "vastuseTekst",
    "veakood",
    "veatekst"
})
public class RR93NimeStatistikaP09ResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(required = true)
    protected String pocIsikukood;
    @XmlElement(required = true)
    protected String pocIsikuEnimi;
    @XmlElement(required = true)
    protected String pocIsikuPnimi;
    @XmlElement(required = true)
    protected String pocTeenusekood;
    @XmlElement(name = "AVVeaTekst", required = true)
    protected String avVeaTekst;
    @XmlElement(name = "Vastuse_Tekst", required = true)
    protected String vastuseTekst;
    @XmlElement(name = "Veakood")
    protected Integer veakood;
    @XmlElement(name = "Veatekst")
    protected String veatekst;

    /**
     * Gets the value of the pocIsikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPocIsikukood() {
        return pocIsikukood;
    }

    /**
     * Sets the value of the pocIsikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPocIsikukood(String value) {
        this.pocIsikukood = value;
    }

    /**
     * Gets the value of the pocIsikuEnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPocIsikuEnimi() {
        return pocIsikuEnimi;
    }

    /**
     * Sets the value of the pocIsikuEnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPocIsikuEnimi(String value) {
        this.pocIsikuEnimi = value;
    }

    /**
     * Gets the value of the pocIsikuPnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPocIsikuPnimi() {
        return pocIsikuPnimi;
    }

    /**
     * Sets the value of the pocIsikuPnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPocIsikuPnimi(String value) {
        this.pocIsikuPnimi = value;
    }

    /**
     * Gets the value of the pocTeenusekood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPocTeenusekood() {
        return pocTeenusekood;
    }

    /**
     * Sets the value of the pocTeenusekood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPocTeenusekood(String value) {
        this.pocTeenusekood = value;
    }

    /**
     * Gets the value of the avVeaTekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAVVeaTekst() {
        return avVeaTekst;
    }

    /**
     * Sets the value of the avVeaTekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAVVeaTekst(String value) {
        this.avVeaTekst = value;
    }

    /**
     * Gets the value of the vastuseTekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVastuseTekst() {
        return vastuseTekst;
    }

    /**
     * Sets the value of the vastuseTekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVastuseTekst(String value) {
        this.vastuseTekst = value;
    }

    /**
     * Gets the value of the veakood property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVeakood() {
        return veakood;
    }

    /**
     * Sets the value of the veakood property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVeakood(Integer value) {
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
