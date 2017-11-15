
package ee.hois.soap.dds.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConfirmationInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConfirmationInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ResponderID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ProducedAt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ResponderCertificate" type="{http://www.sk.ee/DigiDocService/DigiDocService_2_3.wsdl}CertificateInfo" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfirmationInfo", propOrder = {
    "responderID",
    "producedAt",
    "responderCertificate"
})
public class ConfirmationInfo {

    @XmlElementRef(name = "ResponderID", type = JAXBElement.class, required = false)
    protected JAXBElement<String> responderID;
    @XmlElementRef(name = "ProducedAt", type = JAXBElement.class, required = false)
    protected JAXBElement<String> producedAt;
    @XmlElementRef(name = "ResponderCertificate", type = JAXBElement.class, required = false)
    protected JAXBElement<CertificateInfo> responderCertificate;

    /**
     * Gets the value of the responderID property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getResponderID() {
        return responderID;
    }

    /**
     * Sets the value of the responderID property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setResponderID(JAXBElement<String> value) {
        this.responderID = value;
    }

    /**
     * Gets the value of the producedAt property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getProducedAt() {
        return producedAt;
    }

    /**
     * Sets the value of the producedAt property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setProducedAt(JAXBElement<String> value) {
        this.producedAt = value;
    }

    /**
     * Gets the value of the responderCertificate property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link CertificateInfo }{@code >}
     *     
     */
    public JAXBElement<CertificateInfo> getResponderCertificate() {
        return responderCertificate;
    }

    /**
     * Sets the value of the responderCertificate property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link CertificateInfo }{@code >}
     *     
     */
    public void setResponderCertificate(JAXBElement<CertificateInfo> value) {
        this.responderCertificate = value;
    }

}
