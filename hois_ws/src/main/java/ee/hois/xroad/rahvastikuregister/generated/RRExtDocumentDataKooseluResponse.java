
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRExtDocumentDataKooseluResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRExtDocumentDataKooseluResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Teade" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/&gt;
 *         &lt;element name="Viga" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRExtDocumentDataKooseluResponse", propOrder = {
    "teade",
    "viga"
})
public class RRExtDocumentDataKooseluResponse {

    @XmlElement(name = "Teade")
    protected Object teade;
    @XmlElement(name = "Viga")
    protected Object viga;

    /**
     * Gets the value of the teade property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getTeade() {
        return teade;
    }

    /**
     * Sets the value of the teade property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setTeade(Object value) {
        this.teade = value;
    }

    /**
     * Gets the value of the viga property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getViga() {
        return viga;
    }

    /**
     * Sets the value of the viga property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setViga(Object value) {
        this.viga = value;
    }

}
