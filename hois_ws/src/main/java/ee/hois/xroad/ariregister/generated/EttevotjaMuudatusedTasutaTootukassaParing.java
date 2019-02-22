
package ee.hois.xroad.ariregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ettevotja_muudatused_tasuta_tootukassa_paring complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevotja_muudatused_tasuta_tootukassa_paring"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="alguskuupaev" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="loppkuupaev" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="kandes_isikud_rollid" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="100"/&gt;
 *         &lt;element name="tulemuste_lk" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ettevotja_muudatused_tasuta_tootukassa_paring", propOrder = {
    "alguskuupaev",
    "loppkuupaev",
    "kandesIsikudRollid",
    "tulemusteLk"
})
public class EttevotjaMuudatusedTasutaTootukassaParing {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar alguskuupaev;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar loppkuupaev;
    @XmlElement(name = "kandes_isikud_rollid", required = true)
    protected List<String> kandesIsikudRollid;
    @XmlElement(name = "tulemuste_lk")
    protected Integer tulemusteLk;

    /**
     * Gets the value of the alguskuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAlguskuupaev() {
        return alguskuupaev;
    }

    /**
     * Sets the value of the alguskuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAlguskuupaev(XMLGregorianCalendar value) {
        this.alguskuupaev = value;
    }

    /**
     * Gets the value of the loppkuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLoppkuupaev() {
        return loppkuupaev;
    }

    /**
     * Sets the value of the loppkuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLoppkuupaev(XMLGregorianCalendar value) {
        this.loppkuupaev = value;
    }

    /**
     * Gets the value of the kandesIsikudRollid property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the kandesIsikudRollid property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKandesIsikudRollid().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getKandesIsikudRollid() {
        if (kandesIsikudRollid == null) {
            kandesIsikudRollid = new ArrayList<String>();
        }
        return this.kandesIsikudRollid;
    }

    /**
     * Gets the value of the tulemusteLk property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTulemusteLk() {
        return tulemusteLk;
    }

    /**
     * Sets the value of the tulemusteLk property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTulemusteLk(Integer value) {
        this.tulemusteLk = value;
    }

}
