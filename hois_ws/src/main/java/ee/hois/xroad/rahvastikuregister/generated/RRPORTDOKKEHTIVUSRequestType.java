
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRPORTDOK_KEHTIVUSRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRPORTDOK_KEHTIVUSRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DokumendiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DokumendiNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Pohjus" type="{http://rr.x-road.eu/producer}pohjuse_valik2"/&gt;
 *         &lt;element name="Selgitus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRPORTDOK_KEHTIVUSRequestType", propOrder = {
    "dokumendiLiik",
    "dokumendiNumber",
    "pohjus",
    "selgitus"
})
public class RRPORTDOKKEHTIVUSRequestType {

    @XmlElement(name = "DokumendiLiik", required = true)
    protected String dokumendiLiik;
    @XmlElement(name = "DokumendiNumber", required = true)
    protected String dokumendiNumber;
    @XmlElement(name = "Pohjus", required = true)
    @XmlSchemaType(name = "string")
    protected PohjuseValik2 pohjus;
    @XmlElement(name = "Selgitus", required = true)
    protected String selgitus;

    /**
     * Gets the value of the dokumendiLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiLiik() {
        return dokumendiLiik;
    }

    /**
     * Sets the value of the dokumendiLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiLiik(String value) {
        this.dokumendiLiik = value;
    }

    /**
     * Gets the value of the dokumendiNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiNumber() {
        return dokumendiNumber;
    }

    /**
     * Sets the value of the dokumendiNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiNumber(String value) {
        this.dokumendiNumber = value;
    }

    /**
     * Gets the value of the pohjus property.
     * 
     * @return
     *     possible object is
     *     {@link PohjuseValik2 }
     *     
     */
    public PohjuseValik2 getPohjus() {
        return pohjus;
    }

    /**
     * Sets the value of the pohjus property.
     * 
     * @param value
     *     allowed object is
     *     {@link PohjuseValik2 }
     *     
     */
    public void setPohjus(PohjuseValik2 value) {
        this.pohjus = value;
    }

    /**
     * Gets the value of the selgitus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelgitus() {
        return selgitus;
    }

    /**
     * Sets the value of the selgitus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelgitus(String value) {
        this.selgitus = value;
    }

}
