
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
 *         &lt;element name="tallhaOppimineList" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tallhaOppimineListType"/&gt;
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
    "tallhaOppimineList"
})
@XmlRootElement(name = "tallhaOppimineListResponse")
public class TallhaOppimineListResponse {

    @XmlElement(required = true)
    protected TallhaOppimineListType tallhaOppimineList;

    /**
     * Gets the value of the tallhaOppimineList property.
     * 
     * @return
     *     possible object is
     *     {@link TallhaOppimineListType }
     *     
     */
    public TallhaOppimineListType getTallhaOppimineList() {
        return tallhaOppimineList;
    }

    /**
     * Sets the value of the tallhaOppimineList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TallhaOppimineListType }
     *     
     */
    public void setTallhaOppimineList(TallhaOppimineListType value) {
        this.tallhaOppimineList = value;
    }

}
