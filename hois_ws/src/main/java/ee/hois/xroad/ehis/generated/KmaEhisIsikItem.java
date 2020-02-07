
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for kmaEhisIsikItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="kmaEhisIsikItem"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isikukoodKp" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisIsikukoodSynniKp" minOccurs="0"/&gt;
 *         &lt;element name="perenimiAlgus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisLikeStr" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "kmaEhisIsikItem", propOrder = {
    "isikukoodKp",
    "perenimiAlgus"
})
public class KmaEhisIsikItem {

    protected String isikukoodKp;
    protected String perenimiAlgus;

    /**
     * Gets the value of the isikukoodKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukoodKp() {
        return isikukoodKp;
    }

    /**
     * Sets the value of the isikukoodKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukoodKp(String value) {
        this.isikukoodKp = value;
    }

    /**
     * Gets the value of the perenimiAlgus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerenimiAlgus() {
        return perenimiAlgus;
    }

    /**
     * Sets the value of the perenimiAlgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerenimiAlgus(String value) {
        this.perenimiAlgus = value;
    }

}
