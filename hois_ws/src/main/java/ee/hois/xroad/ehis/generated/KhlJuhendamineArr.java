
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for khlJuhendamineArr complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="khlJuhendamineArr"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="muutusKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="juhendamine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlJuhendamineType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "khlJuhendamineArr", propOrder = {
    "muutusKp",
    "juhendamine"
})
public class KhlJuhendamineArr {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar muutusKp;
    @XmlElement(required = true)
    protected KhlJuhendamineType juhendamine;

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
     * Gets the value of the juhendamine property.
     * 
     * @return
     *     possible object is
     *     {@link KhlJuhendamineType }
     *     
     */
    public KhlJuhendamineType getJuhendamine() {
        return juhendamine;
    }

    /**
     * Sets the value of the juhendamine property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlJuhendamineType }
     *     
     */
    public void setJuhendamine(KhlJuhendamineType value) {
        this.juhendamine = value;
    }

}
