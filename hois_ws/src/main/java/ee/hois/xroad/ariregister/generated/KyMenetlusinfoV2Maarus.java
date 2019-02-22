
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ky_menetlusinfo_v2_Maarus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ky_menetlusinfo_v2_Maarus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="liik" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="liik_tekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oleku_aeg" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ky_menetlusinfo_v2_Maarus", propOrder = {
    "number",
    "tekst",
    "liik",
    "liikTekstina",
    "olekuAeg"
})
public class KyMenetlusinfoV2Maarus {

    @XmlElement(required = true)
    protected String number;
    @XmlElement(required = true)
    protected String tekst;
    @XmlElement(required = true)
    protected BigInteger liik;
    @XmlElement(name = "liik_tekstina", required = true)
    protected String liikTekstina;
    @XmlElement(name = "oleku_aeg", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar olekuAeg;

    /**
     * Gets the value of the number property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumber(String value) {
        this.number = value;
    }

    /**
     * Gets the value of the tekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTekst() {
        return tekst;
    }

    /**
     * Sets the value of the tekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTekst(String value) {
        this.tekst = value;
    }

    /**
     * Gets the value of the liik property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLiik() {
        return liik;
    }

    /**
     * Sets the value of the liik property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLiik(BigInteger value) {
        this.liik = value;
    }

    /**
     * Gets the value of the liikTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLiikTekstina() {
        return liikTekstina;
    }

    /**
     * Sets the value of the liikTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLiikTekstina(String value) {
        this.liikTekstina = value;
    }

    /**
     * Gets the value of the olekuAeg property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOlekuAeg() {
        return olekuAeg;
    }

    /**
     * Sets the value of the olekuAeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOlekuAeg(XMLGregorianCalendar value) {
        this.olekuAeg = value;
    }

}
