
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Kodak complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Kodak"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="riik_kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="riik_nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Kodak", propOrder = {
    "riikKood",
    "riikNimi"
})
public class Kodak {

    @XmlElement(name = "riik_kood", required = true)
    protected String riikKood;
    @XmlElement(name = "riik_nimi", required = true)
    protected String riikNimi;

    /**
     * Gets the value of the riikKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiikKood() {
        return riikKood;
    }

    /**
     * Sets the value of the riikKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiikKood(String value) {
        this.riikKood = value;
    }

    /**
     * Gets the value of the riikNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiikNimi() {
        return riikNimi;
    }

    /**
     * Sets the value of the riikNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiikNimi(String value) {
        this.riikNimi = value;
    }

}
