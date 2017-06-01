
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tegelikElukoht complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tegelikElukoht"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="elukohamaa" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="elukoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="majaNr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="korteriNr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tegelikElukoht", propOrder = {
    "elukohamaa",
    "elukoht",
    "aadress",
    "majaNr",
    "korteriNr",
    "postiindeks"
})
public class TegelikElukoht {

    @XmlElement(required = true, nillable = true)
    protected String elukohamaa;
    @XmlElement(required = true, nillable = true)
    protected String elukoht;
    @XmlElement(required = true, nillable = true)
    protected String aadress;
    @XmlElement(required = true, nillable = true)
    protected String majaNr;
    @XmlElement(required = true, nillable = true)
    protected String korteriNr;
    @XmlElement(required = true, nillable = true)
    protected String postiindeks;

    /**
     * Gets the value of the elukohamaa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElukohamaa() {
        return elukohamaa;
    }

    /**
     * Sets the value of the elukohamaa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElukohamaa(String value) {
        this.elukohamaa = value;
    }

    /**
     * Gets the value of the elukoht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElukoht() {
        return elukoht;
    }

    /**
     * Sets the value of the elukoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElukoht(String value) {
        this.elukoht = value;
    }

    /**
     * Gets the value of the aadress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadress() {
        return aadress;
    }

    /**
     * Sets the value of the aadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadress(String value) {
        this.aadress = value;
    }

    /**
     * Gets the value of the majaNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMajaNr() {
        return majaNr;
    }

    /**
     * Sets the value of the majaNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMajaNr(String value) {
        this.majaNr = value;
    }

    /**
     * Gets the value of the korteriNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKorteriNr() {
        return korteriNr;
    }

    /**
     * Sets the value of the korteriNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKorteriNr(String value) {
        this.korteriNr = value;
    }

    /**
     * Gets the value of the postiindeks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostiindeks() {
        return postiindeks;
    }

    /**
     * Sets the value of the postiindeks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostiindeks(String value) {
        this.postiindeks = value;
    }

}
