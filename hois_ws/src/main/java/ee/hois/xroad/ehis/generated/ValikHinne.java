
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for valikHinne complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="valikHinne"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="aine" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kursusi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="hinne" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "valikHinne", propOrder = {
    "aine",
    "kursusi",
    "hinne"
})
public class ValikHinne {

    @XmlElement(required = true, nillable = true)
    protected String aine;
    @XmlElement(required = true, nillable = true)
    protected String kursusi;
    @XmlElement(required = true, nillable = true)
    protected String hinne;

    /**
     * Gets the value of the aine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAine() {
        return aine;
    }

    /**
     * Sets the value of the aine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAine(String value) {
        this.aine = value;
    }

    /**
     * Gets the value of the kursusi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKursusi() {
        return kursusi;
    }

    /**
     * Sets the value of the kursusi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKursusi(String value) {
        this.kursusi = value;
    }

    /**
     * Gets the value of the hinne property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHinne() {
        return hinne;
    }

    /**
     * Sets the value of the hinne property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHinne(String value) {
        this.hinne = value;
    }

}
