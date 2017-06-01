
package ee.hois.xroad.ehis.generated;

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
 *         &lt;element name="oppur" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="isikukood" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisIsikukood"/&gt;
 *         &lt;element name="muudatusteAjaluguList" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}kodOppurAjaluguMuudatused"/&gt;
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
    "oppur",
    "isikukood",
    "muudatusteAjaluguList"
})
@XmlRootElement(name = "kodOppurAjaluguResponse")
public class KodOppurAjaluguResponse {

    @XmlElement(required = true, nillable = true)
    protected String oppur;
    @XmlElement(required = true, nillable = true)
    protected String isikukood;
    @XmlElement(required = true, nillable = true)
    protected KodOppurAjaluguMuudatused muudatusteAjaluguList;

    /**
     * Gets the value of the oppur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppur() {
        return oppur;
    }

    /**
     * Sets the value of the oppur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppur(String value) {
        this.oppur = value;
    }

    /**
     * Gets the value of the isikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukood() {
        return isikukood;
    }

    /**
     * Sets the value of the isikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukood(String value) {
        this.isikukood = value;
    }

    /**
     * Gets the value of the muudatusteAjaluguList property.
     * 
     * @return
     *     possible object is
     *     {@link KodOppurAjaluguMuudatused }
     *     
     */
    public KodOppurAjaluguMuudatused getMuudatusteAjaluguList() {
        return muudatusteAjaluguList;
    }

    /**
     * Sets the value of the muudatusteAjaluguList property.
     * 
     * @param value
     *     allowed object is
     *     {@link KodOppurAjaluguMuudatused }
     *     
     */
    public void setMuudatusteAjaluguList(KodOppurAjaluguMuudatused value) {
        this.muudatusteAjaluguList = value;
    }

}
