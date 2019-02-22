
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oisKutsestandardid complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oisKutsestandardid"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="puudubKehtivKutsestandard" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oneOnlyInteger" minOccurs="0"/&gt;
 *           &lt;element name="kutsestandard" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oisKutsestandard" maxOccurs="unbounded" minOccurs="0"/&gt;
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
@XmlType(name = "oisKutsestandardid", propOrder = {
    "puudubKehtivKutsestandard",
    "kutsestandard"
})
public class OisKutsestandardid {

    @XmlSchemaType(name = "integer")
    protected Integer puudubKehtivKutsestandard;
    protected List<OisKutsestandard> kutsestandard;

    /**
     * Gets the value of the puudubKehtivKutsestandard property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPuudubKehtivKutsestandard() {
        return puudubKehtivKutsestandard;
    }

    /**
     * Sets the value of the puudubKehtivKutsestandard property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPuudubKehtivKutsestandard(Integer value) {
        this.puudubKehtivKutsestandard = value;
    }

    /**
     * Gets the value of the kutsestandard property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the kutsestandard property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKutsestandard().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OisKutsestandard }
     * 
     * 
     */
    public List<OisKutsestandard> getKutsestandard() {
        if (kutsestandard == null) {
            kutsestandard = new ArrayList<OisKutsestandard>();
        }
        return this.kutsestandard;
    }

}
