
package ee.hois.xroad.ariregister.generated;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttachmentRef;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mtabilanssfv3_vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mtabilanssfv3_vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="manuse_id" type="{http://ws-i.org/profiles/basic/1.1/xsd}swaRef" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mtabilanssfv3_vastus", propOrder = {
    "manuseId"
})
public class Mtabilanssfv3Vastus {

    @XmlElement(name = "manuse_id", type = String.class)
    @XmlAttachmentRef
    @XmlSchemaType(name = "anyURI")
    protected DataHandler manuseId;

    /**
     * Gets the value of the manuseId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public DataHandler getManuseId() {
        return manuseId;
    }

    /**
     * Sets the value of the manuseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManuseId(DataHandler value) {
        this.manuseId = value;
    }

}
