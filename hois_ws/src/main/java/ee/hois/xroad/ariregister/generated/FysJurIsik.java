
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for fys_jur_isik complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="fys_jur_isik"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="fys_eesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fys_perenimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fys_synniaeg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fys_isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="jur_arinimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="jur_ark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="jur_fys_valiskood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="jur_fys_valiskoodi_riik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fys_jur_isik", propOrder = {
    "fysEesnimi",
    "fysPerenimi",
    "fysSynniaeg",
    "fysIsikukood",
    "jurArinimi",
    "jurArk",
    "jurFysValiskood",
    "jurFysValiskoodiRiik"
})
public class FysJurIsik {

    @XmlElement(name = "fys_eesnimi")
    protected String fysEesnimi;
    @XmlElement(name = "fys_perenimi")
    protected String fysPerenimi;
    @XmlElement(name = "fys_synniaeg")
    protected String fysSynniaeg;
    @XmlElement(name = "fys_isikukood")
    protected String fysIsikukood;
    @XmlElement(name = "jur_arinimi")
    protected String jurArinimi;
    @XmlElement(name = "jur_ark")
    protected String jurArk;
    @XmlElement(name = "jur_fys_valiskood")
    protected String jurFysValiskood;
    @XmlElement(name = "jur_fys_valiskoodi_riik")
    protected String jurFysValiskoodiRiik;

    /**
     * Gets the value of the fysEesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFysEesnimi() {
        return fysEesnimi;
    }

    /**
     * Sets the value of the fysEesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFysEesnimi(String value) {
        this.fysEesnimi = value;
    }

    /**
     * Gets the value of the fysPerenimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFysPerenimi() {
        return fysPerenimi;
    }

    /**
     * Sets the value of the fysPerenimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFysPerenimi(String value) {
        this.fysPerenimi = value;
    }

    /**
     * Gets the value of the fysSynniaeg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFysSynniaeg() {
        return fysSynniaeg;
    }

    /**
     * Sets the value of the fysSynniaeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFysSynniaeg(String value) {
        this.fysSynniaeg = value;
    }

    /**
     * Gets the value of the fysIsikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFysIsikukood() {
        return fysIsikukood;
    }

    /**
     * Sets the value of the fysIsikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFysIsikukood(String value) {
        this.fysIsikukood = value;
    }

    /**
     * Gets the value of the jurArinimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJurArinimi() {
        return jurArinimi;
    }

    /**
     * Sets the value of the jurArinimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJurArinimi(String value) {
        this.jurArinimi = value;
    }

    /**
     * Gets the value of the jurArk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJurArk() {
        return jurArk;
    }

    /**
     * Sets the value of the jurArk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJurArk(String value) {
        this.jurArk = value;
    }

    /**
     * Gets the value of the jurFysValiskood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJurFysValiskood() {
        return jurFysValiskood;
    }

    /**
     * Sets the value of the jurFysValiskood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJurFysValiskood(String value) {
        this.jurFysValiskood = value;
    }

    /**
     * Gets the value of the jurFysValiskoodiRiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJurFysValiskoodiRiik() {
        return jurFysValiskoodiRiik;
    }

    /**
     * Sets the value of the jurFysValiskoodiRiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJurFysValiskoodiRiik(String value) {
        this.jurFysValiskoodiRiik = value;
    }

}
