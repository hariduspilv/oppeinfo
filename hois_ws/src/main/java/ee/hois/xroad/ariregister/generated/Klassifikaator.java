
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for klassifikaator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="klassifikaator"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="klassifikaatori_kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klassifikaatori_nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klassifikaatori_vaartused" type="{http://arireg.x-road.eu/producer/}klassifikaatori_vaartused"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "klassifikaator", propOrder = {
    "klassifikaatoriKood",
    "klassifikaatoriNimetus",
    "klassifikaatoriVaartused"
})
public class Klassifikaator {

    @XmlElement(name = "klassifikaatori_kood", required = true)
    protected String klassifikaatoriKood;
    @XmlElement(name = "klassifikaatori_nimetus", required = true)
    protected String klassifikaatoriNimetus;
    @XmlElement(name = "klassifikaatori_vaartused", required = true)
    protected KlassifikaatoriVaartused klassifikaatoriVaartused;

    /**
     * Gets the value of the klassifikaatoriKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlassifikaatoriKood() {
        return klassifikaatoriKood;
    }

    /**
     * Sets the value of the klassifikaatoriKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlassifikaatoriKood(String value) {
        this.klassifikaatoriKood = value;
    }

    /**
     * Gets the value of the klassifikaatoriNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlassifikaatoriNimetus() {
        return klassifikaatoriNimetus;
    }

    /**
     * Sets the value of the klassifikaatoriNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlassifikaatoriNimetus(String value) {
        this.klassifikaatoriNimetus = value;
    }

    /**
     * Gets the value of the klassifikaatoriVaartused property.
     * 
     * @return
     *     possible object is
     *     {@link KlassifikaatoriVaartused }
     *     
     */
    public KlassifikaatoriVaartused getKlassifikaatoriVaartused() {
        return klassifikaatoriVaartused;
    }

    /**
     * Sets the value of the klassifikaatoriVaartused property.
     * 
     * @param value
     *     allowed object is
     *     {@link KlassifikaatoriVaartused }
     *     
     */
    public void setKlassifikaatoriVaartused(KlassifikaatoriVaartused value) {
        this.klassifikaatoriVaartused = value;
    }

}
