
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for klassifikaatori_vaartus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="klassifikaatori_vaartus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="klassifikaatori_vaartuse_kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klassifikaatori_vaartuse_nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klassifikaatori_vaartuse_algus_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="klassifikaatori_vaartuse_lopp_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "klassifikaatori_vaartus", propOrder = {
    "klassifikaatoriVaartuseKood",
    "klassifikaatoriVaartuseNimetus",
    "klassifikaatoriVaartuseAlgusKpv",
    "klassifikaatoriVaartuseLoppKpv"
})
public class KlassifikaatoriVaartus {

    @XmlElement(name = "klassifikaatori_vaartuse_kood", required = true)
    protected String klassifikaatoriVaartuseKood;
    @XmlElement(name = "klassifikaatori_vaartuse_nimetus", required = true)
    protected String klassifikaatoriVaartuseNimetus;
    @XmlElement(name = "klassifikaatori_vaartuse_algus_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar klassifikaatoriVaartuseAlgusKpv;
    @XmlElement(name = "klassifikaatori_vaartuse_lopp_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar klassifikaatoriVaartuseLoppKpv;

    /**
     * Gets the value of the klassifikaatoriVaartuseKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlassifikaatoriVaartuseKood() {
        return klassifikaatoriVaartuseKood;
    }

    /**
     * Sets the value of the klassifikaatoriVaartuseKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlassifikaatoriVaartuseKood(String value) {
        this.klassifikaatoriVaartuseKood = value;
    }

    /**
     * Gets the value of the klassifikaatoriVaartuseNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlassifikaatoriVaartuseNimetus() {
        return klassifikaatoriVaartuseNimetus;
    }

    /**
     * Sets the value of the klassifikaatoriVaartuseNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlassifikaatoriVaartuseNimetus(String value) {
        this.klassifikaatoriVaartuseNimetus = value;
    }

    /**
     * Gets the value of the klassifikaatoriVaartuseAlgusKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getKlassifikaatoriVaartuseAlgusKpv() {
        return klassifikaatoriVaartuseAlgusKpv;
    }

    /**
     * Sets the value of the klassifikaatoriVaartuseAlgusKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setKlassifikaatoriVaartuseAlgusKpv(XMLGregorianCalendar value) {
        this.klassifikaatoriVaartuseAlgusKpv = value;
    }

    /**
     * Gets the value of the klassifikaatoriVaartuseLoppKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getKlassifikaatoriVaartuseLoppKpv() {
        return klassifikaatoriVaartuseLoppKpv;
    }

    /**
     * Sets the value of the klassifikaatoriVaartuseLoppKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setKlassifikaatoriVaartuseLoppKpv(XMLGregorianCalendar value) {
        this.klassifikaatoriVaartuseLoppKpv = value;
    }

}
