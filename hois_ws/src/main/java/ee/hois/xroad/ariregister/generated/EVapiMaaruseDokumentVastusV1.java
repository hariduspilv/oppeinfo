
package ee.hois.xroad.ariregister.generated;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttachmentRef;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EVapiMaaruseDokumentVastus_v1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EVapiMaaruseDokumentVastus_v1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="viide_manusele" type="{http://ws-i.org/profiles/basic/1.1/xsd}swaRef"/&gt;
 *         &lt;element name="failinimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EVapiMaaruseDokumentVastus_v1", propOrder = {
    "viideManusele",
    "failinimi"
})
public class EVapiMaaruseDokumentVastusV1 {

    @XmlElement(name = "viide_manusele", required = true, type = String.class)
    @XmlAttachmentRef
    @XmlSchemaType(name = "anyURI")
    protected DataHandler viideManusele;
    @XmlElement(required = true)
    protected String failinimi;

    /**
     * Gets the value of the viideManusele property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public DataHandler getViideManusele() {
        return viideManusele;
    }

    /**
     * Sets the value of the viideManusele property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViideManusele(DataHandler value) {
        this.viideManusele = value;
    }

    /**
     * Gets the value of the failinimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFailinimi() {
        return failinimi;
    }

    /**
     * Sets the value of the failinimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFailinimi(String value) {
        this.failinimi = value;
    }

}
