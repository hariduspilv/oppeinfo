
package ee.hois.xroad.sais2.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IdPair complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdPair"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ImportId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SaisId" type="{http://microsoft.com/wsdl/types/}guid"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdPair", propOrder = {
    "importId",
    "saisId"
})
public class IdPair {

    @XmlElement(name = "ImportId")
    protected String importId;
    @XmlElement(name = "SaisId", required = true)
    protected String saisId;

    /**
     * Gets the value of the importId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImportId() {
        return importId;
    }

    /**
     * Sets the value of the importId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImportId(String value) {
        this.importId = value;
    }

    /**
     * Gets the value of the saisId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSaisId() {
        return saisId;
    }

    /**
     * Sets the value of the saisId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSaisId(String value) {
        this.saisId = value;
    }

}
