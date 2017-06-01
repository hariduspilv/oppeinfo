
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for khlLisamine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="khlLisamine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isikuandmed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlIsikuandmedLisa"/&gt;
 *         &lt;element name="korgharidus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlKorgharidusLisa"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "khlLisamine", propOrder = {
    "isikuandmed",
    "korgharidus"
})
public class KhlLisamine {

    @XmlElement(required = true)
    protected KhlIsikuandmedLisa isikuandmed;
    @XmlElement(required = true)
    protected KhlKorgharidusLisa korgharidus;

    /**
     * Gets the value of the isikuandmed property.
     * 
     * @return
     *     possible object is
     *     {@link KhlIsikuandmedLisa }
     *     
     */
    public KhlIsikuandmedLisa getIsikuandmed() {
        return isikuandmed;
    }

    /**
     * Sets the value of the isikuandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlIsikuandmedLisa }
     *     
     */
    public void setIsikuandmed(KhlIsikuandmedLisa value) {
        this.isikuandmed = value;
    }

    /**
     * Gets the value of the korgharidus property.
     * 
     * @return
     *     possible object is
     *     {@link KhlKorgharidusLisa }
     *     
     */
    public KhlKorgharidusLisa getKorgharidus() {
        return korgharidus;
    }

    /**
     * Sets the value of the korgharidus property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlKorgharidusLisa }
     *     
     */
    public void setKorgharidus(KhlKorgharidusLisa value) {
        this.korgharidus = value;
    }

}
