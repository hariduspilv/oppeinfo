
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for khlStipendiumArr complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="khlStipendiumArr"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="muutusKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="stipendium" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlStipendium" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="kustutamine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlStipendiumKustutamine" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "khlStipendiumArr", propOrder = {
    "muutusKp",
    "stipendium",
    "kustutamine"
})
public class KhlStipendiumArr {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar muutusKp;
    protected List<KhlStipendium> stipendium;
    protected KhlStipendiumKustutamine kustutamine;

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
     * Gets the value of the stipendium property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stipendium property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStipendium().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KhlStipendium }
     * 
     * 
     */
    public List<KhlStipendium> getStipendium() {
        if (stipendium == null) {
            stipendium = new ArrayList<KhlStipendium>();
        }
        return this.stipendium;
    }

    /**
     * Gets the value of the kustutamine property.
     * 
     * @return
     *     possible object is
     *     {@link KhlStipendiumKustutamine }
     *     
     */
    public KhlStipendiumKustutamine getKustutamine() {
        return kustutamine;
    }

    /**
     * Sets the value of the kustutamine property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlStipendiumKustutamine }
     *     
     */
    public void setKustutamine(KhlStipendiumKustutamine value) {
        this.kustutamine = value;
    }

}
