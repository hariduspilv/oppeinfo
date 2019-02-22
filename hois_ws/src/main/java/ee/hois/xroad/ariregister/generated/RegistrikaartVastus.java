
package ee.hois.xroad.ariregister.generated;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttachmentRef;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for registrikaart_vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="registrikaart_vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ariregistri_kood" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="viide_manusele" type="{http://ws-i.org/profiles/basic/1.1/xsd}swaRef"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registrikaart_vastus", propOrder = {
    "ariregistriKood",
    "viideManusele"
})
public class RegistrikaartVastus {

    @XmlElement(name = "ariregistri_kood")
    protected int ariregistriKood;
    @XmlElement(name = "viide_manusele", required = true, type = String.class)
    @XmlAttachmentRef
    @XmlSchemaType(name = "anyURI")
    protected DataHandler viideManusele;

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

}
