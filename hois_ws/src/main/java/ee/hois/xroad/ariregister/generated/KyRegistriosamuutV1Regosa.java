
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ky_registriosamuut_v1_Regosa complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ky_registriosamuut_v1_Regosa"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="registriosa_number" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="kande_number" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="kande_aeg" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="kandeliigi_tahis" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ky_registriosamuut_v1_Regosa", propOrder = {
    "registriosaNumber",
    "kandeNumber",
    "kandeAeg",
    "kandeliigiTahis"
})
public class KyRegistriosamuutV1Regosa {

    @XmlElement(name = "registriosa_number", required = true)
    protected BigInteger registriosaNumber;
    @XmlElement(name = "kande_number", required = true)
    protected BigInteger kandeNumber;
    @XmlElement(name = "kande_aeg", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar kandeAeg;
    @XmlElement(name = "kandeliigi_tahis", required = true)
    protected String kandeliigiTahis;

    /**
     * Gets the value of the registriosaNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRegistriosaNumber() {
        return registriosaNumber;
    }

    /**
     * Sets the value of the registriosaNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRegistriosaNumber(BigInteger value) {
        this.registriosaNumber = value;
    }

    /**
     * Gets the value of the kandeNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKandeNumber() {
        return kandeNumber;
    }

    /**
     * Sets the value of the kandeNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKandeNumber(BigInteger value) {
        this.kandeNumber = value;
    }

    /**
     * Gets the value of the kandeAeg property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getKandeAeg() {
        return kandeAeg;
    }

    /**
     * Sets the value of the kandeAeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setKandeAeg(XMLGregorianCalendar value) {
        this.kandeAeg = value;
    }

    /**
     * Gets the value of the kandeliigiTahis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKandeliigiTahis() {
        return kandeliigiTahis;
    }

    /**
     * Sets the value of the kandeliigiTahis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKandeliigiTahis(String value) {
        this.kandeliigiTahis = value;
    }

}
