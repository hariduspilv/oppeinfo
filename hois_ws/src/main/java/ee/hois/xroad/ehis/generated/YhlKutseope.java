
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for yhlKutseope complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="yhlKutseope"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="muutusKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;choice&gt;
 *           &lt;element name="kustuta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *           &lt;element name="oppekava" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "yhlKutseope", propOrder = {
    "muutusKp",
    "kustuta",
    "oppekava"
})
public class YhlKutseope {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar muutusKp;
    protected String kustuta;
    protected String oppekava;

    /**
     * Gets the value of the muutusKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMuutusKp() {
        return muutusKp;
    }

    /**
     * Sets the value of the muutusKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMuutusKp(XMLGregorianCalendar value) {
        this.muutusKp = value;
    }

    /**
     * Gets the value of the kustuta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKustuta() {
        return kustuta;
    }

    /**
     * Sets the value of the kustuta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKustuta(String value) {
        this.kustuta = value;
    }

    /**
     * Gets the value of the oppekava property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekava() {
        return oppekava;
    }

    /**
     * Sets the value of the oppekava property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekava(String value) {
        this.oppekava = value;
    }

}
