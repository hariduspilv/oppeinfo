
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oisToimumiseKohad complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oisToimumiseKohad"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="toimumiseKohtEHAK" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="toimumiseKohtValismaa" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oneOnlyInteger" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oisToimumiseKohad", propOrder = {
    "toimumiseKohtEHAK",
    "toimumiseKohtValismaa"
})
public class OisToimumiseKohad {

    protected List<String> toimumiseKohtEHAK;
    @XmlSchemaType(name = "integer")
    protected Integer toimumiseKohtValismaa;

    /**
     * Gets the value of the toimumiseKohtEHAK property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the toimumiseKohtEHAK property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getToimumiseKohtEHAK().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getToimumiseKohtEHAK() {
        if (toimumiseKohtEHAK == null) {
            toimumiseKohtEHAK = new ArrayList<String>();
        }
        return this.toimumiseKohtEHAK;
    }

    /**
     * Gets the value of the toimumiseKohtValismaa property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getToimumiseKohtValismaa() {
        return toimumiseKohtValismaa;
    }

    /**
     * Sets the value of the toimumiseKohtValismaa property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setToimumiseKohtValismaa(Integer value) {
        this.toimumiseKohtValismaa = value;
    }

}
