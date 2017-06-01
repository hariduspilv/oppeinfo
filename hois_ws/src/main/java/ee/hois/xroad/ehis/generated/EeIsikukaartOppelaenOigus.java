
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for eeIsikukaartOppelaenOigus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eeIsikukaartOppelaenOigus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oigus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pohjus" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eeIsikukaartOppelaenOigus", propOrder = {
    "oigus",
    "pohjus"
})
public class EeIsikukaartOppelaenOigus {

    protected String oigus;
    protected List<String> pohjus;

    /**
     * Gets the value of the oigus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOigus() {
        return oigus;
    }

    /**
     * Sets the value of the oigus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOigus(String value) {
        this.oigus = value;
    }

    /**
     * Gets the value of the pohjus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pohjus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPohjus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPohjus() {
        if (pohjus == null) {
            pohjus = new ArrayList<String>();
        }
        return this.pohjus;
    }

}
