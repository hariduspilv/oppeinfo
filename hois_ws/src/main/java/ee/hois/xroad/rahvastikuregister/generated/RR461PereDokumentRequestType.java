
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR461pereDokumentRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR461pereDokumentRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DokumendiTyyp" type="{http://rr.x-road.eu/producer}dokumendiTyyp" minOccurs="0"/&gt;
 *         &lt;element name="DokumendiStaatus" type="{http://rr.x-road.eu/producer}dokumendi_staatus" minOccurs="0"/&gt;
 *         &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
 *         &lt;element name="pohjus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR461pereDokumentRequestType", propOrder = {
    "dokumendiTyyp",
    "dokumendiStaatus",
    "isikukood",
    "pohjus"
})
public class RR461PereDokumentRequestType {

    @XmlElement(name = "DokumendiTyyp")
    @XmlSchemaType(name = "string")
    protected DokumendiTyyp dokumendiTyyp;
    @XmlElement(name = "DokumendiStaatus")
    @XmlSchemaType(name = "string")
    protected DokumendiStaatus dokumendiStaatus;
    @XmlElement(name = "Isikukood", required = true)
    protected String isikukood;
    protected String pohjus;

    /**
     * Gets the value of the dokumendiTyyp property.
     * 
     * @return
     *     possible object is
     *     {@link DokumendiTyyp }
     *     
     */
    public DokumendiTyyp getDokumendiTyyp() {
        return dokumendiTyyp;
    }

    /**
     * Sets the value of the dokumendiTyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link DokumendiTyyp }
     *     
     */
    public void setDokumendiTyyp(DokumendiTyyp value) {
        this.dokumendiTyyp = value;
    }

    /**
     * Gets the value of the dokumendiStaatus property.
     * 
     * @return
     *     possible object is
     *     {@link DokumendiStaatus }
     *     
     */
    public DokumendiStaatus getDokumendiStaatus() {
        return dokumendiStaatus;
    }

    /**
     * Sets the value of the dokumendiStaatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link DokumendiStaatus }
     *     
     */
    public void setDokumendiStaatus(DokumendiStaatus value) {
        this.dokumendiStaatus = value;
    }

    /**
     * Gets the value of the isikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukood() {
        return isikukood;
    }

    /**
     * Sets the value of the isikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukood(String value) {
        this.isikukood = value;
    }

    /**
     * Gets the value of the pohjus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPohjus() {
        return pohjus;
    }

    /**
     * Sets the value of the pohjus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPohjus(String value) {
        this.pohjus = value;
    }

}
