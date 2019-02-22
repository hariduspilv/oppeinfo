
package ee.hois.xroad.ariregister.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for sooritakanne_kapitali_tingimuslik_suurendamine_v4 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sooritakanne_kapitali_tingimuslik_suurendamine_v4"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="otsuse_kuupaev" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="suurus" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="valuuta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sooritakanne_kapitali_tingimuslik_suurendamine_v4", propOrder = {
    "otsuseKuupaev",
    "suurus",
    "valuuta"
})
public class SooritakanneKapitaliTingimuslikSuurendamineV4 {

    @XmlElement(name = "otsuse_kuupaev", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar otsuseKuupaev;
    @XmlElement(required = true)
    protected BigDecimal suurus;
    @XmlElement(required = true)
    protected String valuuta;

    /**
     * Gets the value of the otsuseKuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOtsuseKuupaev() {
        return otsuseKuupaev;
    }

    /**
     * Sets the value of the otsuseKuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOtsuseKuupaev(XMLGregorianCalendar value) {
        this.otsuseKuupaev = value;
    }

    /**
     * Gets the value of the suurus property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSuurus() {
        return suurus;
    }

    /**
     * Sets the value of the suurus property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSuurus(BigDecimal value) {
        this.suurus = value;
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

}
