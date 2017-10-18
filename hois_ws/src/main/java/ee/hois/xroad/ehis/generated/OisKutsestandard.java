
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oisKutsestandard complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oisKutsestandard"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="standardReaId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="kutseSpetsialiseerumised" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oiskutseSpetsialiseerumine" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="osakutsed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oisOsakutse" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oisKutsestandard", propOrder = {
    "standardReaId",
    "kutseSpetsialiseerumised",
    "osakutsed"
})
public class OisKutsestandard {

    @XmlElement(required = true)
    protected BigInteger standardReaId;
    protected List<OiskutseSpetsialiseerumine> kutseSpetsialiseerumised;
    protected List<OisOsakutse> osakutsed;

    /**
     * Gets the value of the standardReaId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getStandardReaId() {
        return standardReaId;
    }

    /**
     * Sets the value of the standardReaId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setStandardReaId(BigInteger value) {
        this.standardReaId = value;
    }

    /**
     * Gets the value of the kutseSpetsialiseerumised property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the kutseSpetsialiseerumised property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKutseSpetsialiseerumised().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OiskutseSpetsialiseerumine }
     * 
     * 
     */
    public List<OiskutseSpetsialiseerumine> getKutseSpetsialiseerumised() {
        if (kutseSpetsialiseerumised == null) {
            kutseSpetsialiseerumised = new ArrayList<OiskutseSpetsialiseerumine>();
        }
        return this.kutseSpetsialiseerumised;
    }

    /**
     * Gets the value of the osakutsed property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the osakutsed property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOsakutsed().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OisOsakutse }
     * 
     * 
     */
    public List<OisOsakutse> getOsakutsed() {
        if (osakutsed == null) {
            osakutsed = new ArrayList<OisOsakutse>();
        }
        return this.osakutsed;
    }

}
