
package ee.hois.soap.dds.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SignatureInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SignatureInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Error" type="{http://www.sk.ee/DigiDocService/DigiDocService_2_3.wsdl}Error" minOccurs="0"/&gt;
 *         &lt;element name="SigningTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="SignerRole" type="{http://www.sk.ee/DigiDocService/DigiDocService_2_3.wsdl}SignerRole" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="SignatureProductionPlace" type="{http://www.sk.ee/DigiDocService/DigiDocService_2_3.wsdl}SignatureProductionPlace" minOccurs="0"/&gt;
 *         &lt;element name="Signer" type="{http://www.sk.ee/DigiDocService/DigiDocService_2_3.wsdl}SignerInfo" minOccurs="0"/&gt;
 *         &lt;element name="Confirmation" type="{http://www.sk.ee/DigiDocService/DigiDocService_2_3.wsdl}ConfirmationInfo" minOccurs="0"/&gt;
 *         &lt;element name="Timestamps" type="{http://www.sk.ee/DigiDocService/DigiDocService_2_3.wsdl}TstInfo" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="CRLInfo" type="{http://www.sk.ee/DigiDocService/DigiDocService_2_3.wsdl}CRLInfo" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignatureInfo", propOrder = {
    "id",
    "status",
    "error",
    "signingTime",
    "signerRole",
    "signatureProductionPlace",
    "signer",
    "confirmation",
    "timestamps",
    "crlInfo"
})
public class SignatureInfo {

    @XmlElementRef(name = "Id", type = JAXBElement.class, required = false)
    protected JAXBElement<String> id;
    @XmlElementRef(name = "Status", type = JAXBElement.class, required = false)
    protected JAXBElement<String> status;
    @XmlElementRef(name = "Error", type = JAXBElement.class, required = false)
    protected JAXBElement<Error> error;
    @XmlElementRef(name = "SigningTime", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> signingTime;
    @XmlElement(name = "SignerRole")
    protected List<SignerRole> signerRole;
    @XmlElementRef(name = "SignatureProductionPlace", type = JAXBElement.class, required = false)
    protected JAXBElement<SignatureProductionPlace> signatureProductionPlace;
    @XmlElementRef(name = "Signer", type = JAXBElement.class, required = false)
    protected JAXBElement<SignerInfo> signer;
    @XmlElementRef(name = "Confirmation", type = JAXBElement.class, required = false)
    protected JAXBElement<ConfirmationInfo> confirmation;
    @XmlElement(name = "Timestamps")
    protected List<TstInfo> timestamps;
    @XmlElementRef(name = "CRLInfo", type = JAXBElement.class, required = false)
    protected JAXBElement<CRLInfo> crlInfo;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setId(JAXBElement<String> value) {
        this.id = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setStatus(JAXBElement<String> value) {
        this.status = value;
    }

    /**
     * Gets the value of the error property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Error }{@code >}
     *     
     */
    public JAXBElement<Error> getError() {
        return error;
    }

    /**
     * Sets the value of the error property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Error }{@code >}
     *     
     */
    public void setError(JAXBElement<Error> value) {
        this.error = value;
    }

    /**
     * Gets the value of the signingTime property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getSigningTime() {
        return signingTime;
    }

    /**
     * Sets the value of the signingTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setSigningTime(JAXBElement<XMLGregorianCalendar> value) {
        this.signingTime = value;
    }

    /**
     * Gets the value of the signerRole property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signerRole property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignerRole().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SignerRole }
     * 
     * 
     */
    public List<SignerRole> getSignerRole() {
        if (signerRole == null) {
            signerRole = new ArrayList<SignerRole>();
        }
        return this.signerRole;
    }

    /**
     * Gets the value of the signatureProductionPlace property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link SignatureProductionPlace }{@code >}
     *     
     */
    public JAXBElement<SignatureProductionPlace> getSignatureProductionPlace() {
        return signatureProductionPlace;
    }

    /**
     * Sets the value of the signatureProductionPlace property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link SignatureProductionPlace }{@code >}
     *     
     */
    public void setSignatureProductionPlace(JAXBElement<SignatureProductionPlace> value) {
        this.signatureProductionPlace = value;
    }

    /**
     * Gets the value of the signer property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link SignerInfo }{@code >}
     *     
     */
    public JAXBElement<SignerInfo> getSigner() {
        return signer;
    }

    /**
     * Sets the value of the signer property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link SignerInfo }{@code >}
     *     
     */
    public void setSigner(JAXBElement<SignerInfo> value) {
        this.signer = value;
    }

    /**
     * Gets the value of the confirmation property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ConfirmationInfo }{@code >}
     *     
     */
    public JAXBElement<ConfirmationInfo> getConfirmation() {
        return confirmation;
    }

    /**
     * Sets the value of the confirmation property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ConfirmationInfo }{@code >}
     *     
     */
    public void setConfirmation(JAXBElement<ConfirmationInfo> value) {
        this.confirmation = value;
    }

    /**
     * Gets the value of the timestamps property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the timestamps property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTimestamps().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TstInfo }
     * 
     * 
     */
    public List<TstInfo> getTimestamps() {
        if (timestamps == null) {
            timestamps = new ArrayList<TstInfo>();
        }
        return this.timestamps;
    }

    /**
     * Gets the value of the crlInfo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link CRLInfo }{@code >}
     *     
     */
    public JAXBElement<CRLInfo> getCRLInfo() {
        return crlInfo;
    }

    /**
     * Sets the value of the crlInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link CRLInfo }{@code >}
     *     
     */
    public void setCRLInfo(JAXBElement<CRLInfo> value) {
        this.crlInfo = value;
    }

}
