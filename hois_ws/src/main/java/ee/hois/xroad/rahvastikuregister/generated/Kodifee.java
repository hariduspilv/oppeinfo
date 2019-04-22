
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Kodifee complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Kodifee"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Nimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Kodifee", propOrder = {
    "kood",
    "nimi"
})
public class Kodifee {

    @XmlElement(name = "Kood", required = true)
    protected String kood;
    @XmlElement(name = "Nimi")
    protected String nimi;

    /**
     * Gets the value of the kood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKood() {
        return kood;
    }

    /**
     * Sets the value of the kood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKood(String value) {
        this.kood = value;
    }

    /**
     * Gets the value of the nimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * Sets the value of the nimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNimi(String value) {
        this.nimi = value;
    }

}
