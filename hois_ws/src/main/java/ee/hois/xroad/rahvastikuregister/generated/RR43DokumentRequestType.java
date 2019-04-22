
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR43dokumentRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR43dokumentRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DokumendiTyyp" type="{http://rr.x-road.eu/producer}dokumendiTyyp2"/&gt;
 *         &lt;element name="DokumendiSeeria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DokumendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR43dokumentRequestType", propOrder = {
    "dokumendiTyyp",
    "dokumendiSeeria",
    "dokumendiKood"
})
public class RR43DokumentRequestType {

    @XmlElement(name = "DokumendiTyyp", required = true)
    @XmlSchemaType(name = "string")
    protected DokumendiTyyp2 dokumendiTyyp;
    @XmlElement(name = "DokumendiSeeria")
    protected String dokumendiSeeria;
    @XmlElement(name = "DokumendiKood", required = true)
    protected String dokumendiKood;

    /**
     * Gets the value of the dokumendiTyyp property.
     * 
     * @return
     *     possible object is
     *     {@link DokumendiTyyp2 }
     *     
     */
    public DokumendiTyyp2 getDokumendiTyyp() {
        return dokumendiTyyp;
    }

    /**
     * Sets the value of the dokumendiTyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link DokumendiTyyp2 }
     *     
     */
    public void setDokumendiTyyp(DokumendiTyyp2 value) {
        this.dokumendiTyyp = value;
    }

    /**
     * Gets the value of the dokumendiSeeria property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiSeeria() {
        return dokumendiSeeria;
    }

    /**
     * Sets the value of the dokumendiSeeria property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiSeeria(String value) {
        this.dokumendiSeeria = value;
    }

    /**
     * Gets the value of the dokumendiKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiKood() {
        return dokumendiKood;
    }

    /**
     * Sets the value of the dokumendiKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiKood(String value) {
        this.dokumendiKood = value;
    }

}
