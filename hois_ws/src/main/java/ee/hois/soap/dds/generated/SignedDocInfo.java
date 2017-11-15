
package ee.hois.soap.dds.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SignedDocInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SignedDocInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Format" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DataFileInfo" type="{http://www.sk.ee/DigiDocService/DigiDocService_2_3.wsdl}DataFileInfo" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="SignatureInfo" type="{http://www.sk.ee/DigiDocService/DigiDocService_2_3.wsdl}SignatureInfo" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignedDocInfo", propOrder = {
    "format",
    "version",
    "dataFileInfo",
    "signatureInfo"
})
public class SignedDocInfo {

    @XmlElementRef(name = "Format", type = JAXBElement.class, required = false)
    protected JAXBElement<String> format;
    @XmlElementRef(name = "Version", type = JAXBElement.class, required = false)
    protected JAXBElement<String> version;
    @XmlElement(name = "DataFileInfo")
    protected List<DataFileInfo> dataFileInfo;
    @XmlElement(name = "SignatureInfo")
    protected List<SignatureInfo> signatureInfo;

    /**
     * Gets the value of the format property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFormat() {
        return format;
    }

    /**
     * Sets the value of the format property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFormat(JAXBElement<String> value) {
        this.format = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setVersion(JAXBElement<String> value) {
        this.version = value;
    }

    /**
     * Gets the value of the dataFileInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataFileInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataFileInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataFileInfo }
     * 
     * 
     */
    public List<DataFileInfo> getDataFileInfo() {
        if (dataFileInfo == null) {
            dataFileInfo = new ArrayList<DataFileInfo>();
        }
        return this.dataFileInfo;
    }

    /**
     * Gets the value of the signatureInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signatureInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignatureInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SignatureInfo }
     * 
     * 
     */
    public List<SignatureInfo> getSignatureInfo() {
        if (signatureInfo == null) {
            signatureInfo = new ArrayList<SignatureInfo>();
        }
        return this.signatureInfo;
    }

}
