
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for majandusaasta_aruanded_rea_veerg complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="majandusaasta_aruanded_rea_veerg"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="veeru_kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="veeru_nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="vaartus" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "majandusaasta_aruanded_rea_veerg", propOrder = {
    "veeruKood",
    "veeruNimetus",
    "vaartus"
})
public class MajandusaastaAruandedReaVeerg {

    @XmlElement(name = "veeru_kood", required = true)
    protected String veeruKood;
    @XmlElement(name = "veeru_nimetus", required = true)
    protected String veeruNimetus;
    protected int vaartus;

    /**
     * Gets the value of the veeruKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVeeruKood() {
        return veeruKood;
    }

    /**
     * Sets the value of the veeruKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVeeruKood(String value) {
        this.veeruKood = value;
    }

    /**
     * Gets the value of the veeruNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVeeruNimetus() {
        return veeruNimetus;
    }

    /**
     * Sets the value of the veeruNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVeeruNimetus(String value) {
        this.veeruNimetus = value;
    }

    /**
     * Gets the value of the vaartus property.
     * 
     */
    public int getVaartus() {
        return vaartus;
    }

    /**
     * Sets the value of the vaartus property.
     * 
     */
    public void setVaartus(int value) {
        this.vaartus = value;
    }

}
