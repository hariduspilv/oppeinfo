
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for yhlPuudulikudAastahinded complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="yhlPuudulikudAastahinded"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="muutusKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="puudulikAastahinne" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlPuudulikAastahinne" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "yhlPuudulikudAastahinded", propOrder = {
    "muutusKp",
    "puudulikAastahinne"
})
public class YhlPuudulikudAastahinded {

    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar muutusKp;
    protected List<YhlPuudulikAastahinne> puudulikAastahinne;

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
     * Gets the value of the puudulikAastahinne property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the puudulikAastahinne property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPuudulikAastahinne().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link YhlPuudulikAastahinne }
     * 
     * 
     */
    public List<YhlPuudulikAastahinne> getPuudulikAastahinne() {
        if (puudulikAastahinne == null) {
            puudulikAastahinne = new ArrayList<YhlPuudulikAastahinne>();
        }
        return this.puudulikAastahinne;
    }

}
