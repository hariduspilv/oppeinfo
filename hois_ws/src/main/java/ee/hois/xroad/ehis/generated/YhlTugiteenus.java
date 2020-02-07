
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for yhlTugiteenus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="yhlTugiteenus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="klTugiteenus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Oppeained" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oppeained" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "yhlTugiteenus", propOrder = {
    "klTugiteenus",
    "oppeained"
})
public class YhlTugiteenus {

    @XmlElement(required = true)
    protected String klTugiteenus;
    @XmlElement(name = "Oppeained")
    protected Oppeained oppeained;

    /**
     * Gets the value of the klTugiteenus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlTugiteenus() {
        return klTugiteenus;
    }

    /**
     * Sets the value of the klTugiteenus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlTugiteenus(String value) {
        this.klTugiteenus = value;
    }

    /**
     * Gets the value of the oppeained property.
     * 
     * @return
     *     possible object is
     *     {@link Oppeained }
     *     
     */
    public Oppeained getOppeained() {
        return oppeained;
    }

    /**
     * Sets the value of the oppeained property.
     * 
     * @param value
     *     allowed object is
     *     {@link Oppeained }
     *     
     */
    public void setOppeained(Oppeained value) {
        this.oppeained = value;
    }

}
