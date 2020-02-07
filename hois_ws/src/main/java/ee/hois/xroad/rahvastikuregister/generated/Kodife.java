
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Kodife complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Kodife"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="KdElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="KdLyhikeNimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Kodife", propOrder = {
    "kdElemendiKood",
    "kdLyhikeNimi"
})
public class Kodife {

    @XmlElement(name = "KdElemendiKood", required = true)
    protected String kdElemendiKood;
    @XmlElement(name = "KdLyhikeNimi")
    protected String kdLyhikeNimi;

    /**
     * Gets the value of the kdElemendiKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKdElemendiKood() {
        return kdElemendiKood;
    }

    /**
     * Sets the value of the kdElemendiKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKdElemendiKood(String value) {
        this.kdElemendiKood = value;
    }

    /**
     * Gets the value of the kdLyhikeNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKdLyhikeNimi() {
        return kdLyhikeNimi;
    }

    /**
     * Sets the value of the kdLyhikeNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKdLyhikeNimi(String value) {
        this.kdLyhikeNimi = value;
    }

}
