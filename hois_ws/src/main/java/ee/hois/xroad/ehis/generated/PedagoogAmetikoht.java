
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
 *         &lt;element name="isikukoodid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}eisAmetikohadParingList"/&gt;
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
    "isikukoodid"
})
@XmlRootElement(name = "pedagoogAmetikoht")
public class PedagoogAmetikoht {

    @XmlElement(required = true)
    protected EisAmetikohadParingList isikukoodid;

    /**
     * Gets the value of the isikukoodid property.
     * 
     * @return
     *     possible object is
     *     {@link EisAmetikohadParingList }
     *     
     */
    public EisAmetikohadParingList getIsikukoodid() {
        return isikukoodid;
    }

    /**
     * Sets the value of the isikukoodid property.
     * 
     * @param value
     *     allowed object is
     *     {@link EisAmetikohadParingList }
     *     
     */
    public void setIsikukoodid(EisAmetikohadParingList value) {
        this.isikukoodid = value;
    }

}
