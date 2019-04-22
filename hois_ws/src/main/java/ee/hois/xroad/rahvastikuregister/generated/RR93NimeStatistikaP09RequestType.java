
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR93NimeStatistikaP09RequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR93NimeStatistikaP09RequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IsikuIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuPerekonnanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Teenusekood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Perekonnanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR93NimeStatistikaP09RequestType", propOrder = {
    "isikuIsikukood",
    "isikuEesnimi",
    "isikuPerekonnanimi",
    "teenusekood",
    "eesnimi",
    "perekonnanimi"
})
public class RR93NimeStatistikaP09RequestType {

    @XmlElement(name = "IsikuIsikukood", required = true)
    protected String isikuIsikukood;
    @XmlElement(name = "IsikuEesnimi", required = true)
    protected String isikuEesnimi;
    @XmlElement(name = "IsikuPerekonnanimi", required = true)
    protected String isikuPerekonnanimi;
    @XmlElement(name = "Teenusekood", required = true)
    protected String teenusekood;
    @XmlElement(name = "Eesnimi", required = true)
    protected String eesnimi;
    @XmlElement(name = "Perekonnanimi", required = true)
    protected String perekonnanimi;

    /**
     * Gets the value of the isikuIsikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuIsikukood() {
        return isikuIsikukood;
    }

    /**
     * Sets the value of the isikuIsikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuIsikukood(String value) {
        this.isikuIsikukood = value;
    }

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
     * Gets the value of the isikuPerekonnanimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuPerekonnanimi() {
        return isikuPerekonnanimi;
    }

    /**
     * Sets the value of the isikuPerekonnanimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuPerekonnanimi(String value) {
        this.isikuPerekonnanimi = value;
    }

    /**
     * Gets the value of the teenusekood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeenusekood() {
        return teenusekood;
    }

    /**
     * Sets the value of the teenusekood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeenusekood(String value) {
        this.teenusekood = value;
    }

    /**
     * Gets the value of the eesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEesnimi() {
        return eesnimi;
    }

    /**
     * Sets the value of the eesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEesnimi(String value) {
        this.eesnimi = value;
    }

    /**
     * Gets the value of the perekonnanimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerekonnanimi() {
        return perekonnanimi;
    }

    /**
     * Sets the value of the perekonnanimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerekonnanimi(String value) {
        this.perekonnanimi = value;
    }

}
