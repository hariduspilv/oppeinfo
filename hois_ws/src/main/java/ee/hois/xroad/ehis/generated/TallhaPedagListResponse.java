
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="teade" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="pedagoogid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tallhaPedagListType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "teade",
    "kood",
    "pedagoogid"
})
@XmlRootElement(name = "tallhaPedagListResponse")
public class TallhaPedagListResponse {

    @XmlElement(required = true, nillable = true)
    protected String teade;
    @XmlElement(required = true, nillable = true)
    protected BigInteger kood;
    @XmlElement(required = true)
    protected TallhaPedagListType pedagoogid;

    /**
     * Gets the value of the teade property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeade() {
        return teade;
    }

    /**
     * Sets the value of the teade property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeade(String value) {
        this.teade = value;
    }

    /**
     * Gets the value of the kood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKood() {
        return kood;
    }

    /**
     * Sets the value of the kood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKood(BigInteger value) {
        this.kood = value;
    }

    /**
     * Gets the value of the pedagoogid property.
     * 
     * @return
     *     possible object is
     *     {@link TallhaPedagListType }
     *     
     */
    public TallhaPedagListType getPedagoogid() {
        return pedagoogid;
    }

    /**
     * Sets the value of the pedagoogid property.
     * 
     * @param value
     *     allowed object is
     *     {@link TallhaPedagListType }
     *     
     */
    public void setPedagoogid(TallhaPedagListType value) {
        this.pedagoogid = value;
    }

}
