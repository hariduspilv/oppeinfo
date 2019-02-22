
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ariregistri_toimik_Request complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ariregistri_toimik_Request"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ariregistri_kood" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="keel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="formaat" type="{http://arireg.x-road.eu/producer/}x_formaat" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ariregistri_toimik_Request", propOrder = {
    "ariregistriKood",
    "keel",
    "formaat"
})
public class AriregistriToimikRequest {

    @XmlElement(name = "ariregistri_kood")
    protected int ariregistriKood;
    @XmlElementRef(name = "keel", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> keel;
    @XmlElementRef(name = "formaat", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<XFormaat> formaat;

    /**
     * Gets the value of the ariregistriKood property.
     * 
     */
    public int getAriregistriKood() {
        return ariregistriKood;
    }

    /**
     * Sets the value of the ariregistriKood property.
     * 
     */
    public void setAriregistriKood(int value) {
        this.ariregistriKood = value;
    }

    /**
     * Gets the value of the keel property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getKeel() {
        return keel;
    }

    /**
     * Sets the value of the keel property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setKeel(JAXBElement<String> value) {
        this.keel = value;
    }

    /**
     * Gets the value of the formaat property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XFormaat }{@code >}
     *     
     */
    public JAXBElement<XFormaat> getFormaat() {
        return formaat;
    }

    /**
     * Sets the value of the formaat property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XFormaat }{@code >}
     *     
     */
    public void setFormaat(JAXBElement<XFormaat> value) {
        this.formaat = value;
    }

}
