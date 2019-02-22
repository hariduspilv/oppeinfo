
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for mtamaruosad_osa complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mtamaruosad_osa"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="rea_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="mosa_tyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kuupaev" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mtamaruosad_osa", propOrder = {
    "reaId",
    "mosaTyyp",
    "kuupaev"
})
public class MtamaruosadOsa {

    @XmlElement(name = "rea_id", required = true)
    protected BigInteger reaId;
    @XmlElement(name = "mosa_tyyp", required = true)
    protected String mosaTyyp;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar kuupaev;

    /**
     * Gets the value of the reaId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getReaId() {
        return reaId;
    }

    /**
     * Sets the value of the reaId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setReaId(BigInteger value) {
        this.reaId = value;
    }

    /**
     * Gets the value of the mosaTyyp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMosaTyyp() {
        return mosaTyyp;
    }

    /**
     * Sets the value of the mosaTyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMosaTyyp(String value) {
        this.mosaTyyp = value;
    }

    /**
     * Gets the value of the kuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getKuupaev() {
        return kuupaev;
    }

    /**
     * Sets the value of the kuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setKuupaev(XMLGregorianCalendar value) {
        this.kuupaev = value;
    }

}
