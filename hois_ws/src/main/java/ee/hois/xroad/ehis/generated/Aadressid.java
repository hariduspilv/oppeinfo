
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for aadressid complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="aadressid"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="onValismaa" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="aadress" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}aadress" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "aadressid", propOrder = {
    "onValismaa",
    "aadress"
})
public class Aadressid {

    protected boolean onValismaa;
    protected List<Aadress> aadress;

    /**
     * Gets the value of the onValismaa property.
     * 
     */
    public boolean isOnValismaa() {
        return onValismaa;
    }

    /**
     * Sets the value of the onValismaa property.
     * 
     */
    public void setOnValismaa(boolean value) {
        this.onValismaa = value;
    }

    /**
     * Gets the value of the aadress property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the aadress property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAadress().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Aadress }
     * 
     * 
     */
    public List<Aadress> getAadress() {
        if (aadress == null) {
            aadress = new ArrayList<Aadress>();
        }
        return this.aadress;
    }

}
