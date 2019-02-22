
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for esindusoigusType_v4 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="esindusoigusType_v4"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tegevus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="roll" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="liik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kirjeldus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="sisu_json" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sisu_eng" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "esindusoigusType_v4", propOrder = {
    "tegevus",
    "roll",
    "liik",
    "kirjeldus",
    "sisuJson",
    "sisuEng"
})
public class EsindusoigusTypeV4 {

    @XmlElement(required = true)
    protected String tegevus;
    protected String roll;
    protected String liik;
    @XmlElement(required = true)
    protected String kirjeldus;
    @XmlElement(name = "sisu_json")
    protected String sisuJson;
    @XmlElement(name = "sisu_eng")
    protected String sisuEng;

    /**
     * Gets the value of the tegevus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTegevus() {
        return tegevus;
    }

    /**
     * Sets the value of the tegevus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTegevus(String value) {
        this.tegevus = value;
    }

    /**
     * Gets the value of the roll property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoll() {
        return roll;
    }

    /**
     * Sets the value of the roll property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoll(String value) {
        this.roll = value;
    }

    /**
     * Gets the value of the liik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLiik() {
        return liik;
    }

    /**
     * Sets the value of the liik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLiik(String value) {
        this.liik = value;
    }

    /**
     * Gets the value of the kirjeldus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKirjeldus() {
        return kirjeldus;
    }

    /**
     * Sets the value of the kirjeldus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKirjeldus(String value) {
        this.kirjeldus = value;
    }

    /**
     * Gets the value of the sisuJson property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSisuJson() {
        return sisuJson;
    }

    /**
     * Sets the value of the sisuJson property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSisuJson(String value) {
        this.sisuJson = value;
    }

    /**
     * Gets the value of the sisuEng property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSisuEng() {
        return sisuEng;
    }

    /**
     * Sets the value of the sisuEng property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSisuEng(String value) {
        this.sisuEng = value;
    }

}
