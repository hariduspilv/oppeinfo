
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for khlOppeasutus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="khlOppeasutus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="koolId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;sequence&gt;
 *           &lt;element name="oppur" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlOppur" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element name="lyhiajaValisoppur" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlLyhiAjaValisOppur" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;/sequence&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "khlOppeasutus", propOrder = {
    "koolId",
    "oppur",
    "lyhiajaValisoppur"
})
public class KhlOppeasutus {

    @XmlElement(required = true)
    protected BigInteger koolId;
    protected List<KhlOppur> oppur;
    protected List<KhlLyhiAjaValisOppur> lyhiajaValisoppur;

    /**
     * Gets the value of the koolId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKoolId() {
        return koolId;
    }

    /**
     * Sets the value of the koolId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKoolId(BigInteger value) {
        this.koolId = value;
    }

    /**
     * Gets the value of the oppur property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oppur property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOppur().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KhlOppur }
     * 
     * 
     */
    public List<KhlOppur> getOppur() {
        if (oppur == null) {
            oppur = new ArrayList<KhlOppur>();
        }
        return this.oppur;
    }

    /**
     * Gets the value of the lyhiajaValisoppur property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lyhiajaValisoppur property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLyhiajaValisoppur().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KhlLyhiAjaValisOppur }
     * 
     * 
     */
    public List<KhlLyhiAjaValisOppur> getLyhiajaValisoppur() {
        if (lyhiajaValisoppur == null) {
            lyhiajaValisoppur = new ArrayList<KhlLyhiAjaValisOppur>();
        }
        return this.lyhiajaValisoppur;
    }

}
