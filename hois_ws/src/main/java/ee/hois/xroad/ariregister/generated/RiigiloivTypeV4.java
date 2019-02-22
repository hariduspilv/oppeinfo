
package ee.hois.xroad.ariregister.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for riigiloivType_v4 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="riigiloivType_v4"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="summa" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="valuuta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="viitenumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="selgitus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kuupaev" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "riigiloivType_v4", propOrder = {
    "summa",
    "valuuta",
    "viitenumber",
    "selgitus",
    "kuupaev"
})
public class RiigiloivTypeV4 {

    @XmlElement(required = true)
    protected BigDecimal summa;
    @XmlElement(required = true)
    protected String valuuta;
    protected String viitenumber;
    @XmlElement(required = true)
    protected String selgitus;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar kuupaev;

    /**
     * Gets the value of the summa property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSumma() {
        return summa;
    }

    /**
     * Sets the value of the summa property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSumma(BigDecimal value) {
        this.summa = value;
    }

    /**
     * Gets the value of the valuuta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValuuta() {
        return valuuta;
    }

    /**
     * Sets the value of the valuuta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValuuta(String value) {
        this.valuuta = value;
    }

    /**
     * Gets the value of the viitenumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViitenumber() {
        return viitenumber;
    }

    /**
     * Sets the value of the viitenumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViitenumber(String value) {
        this.viitenumber = value;
    }

    /**
     * Gets the value of the selgitus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelgitus() {
        return selgitus;
    }

    /**
     * Sets the value of the selgitus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelgitus(String value) {
        this.selgitus = value;
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
