
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for irOppeasutus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="irOppeasutus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="regNr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="rollid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}rollid"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "irOppeasutus", propOrder = {
    "id",
    "regNr",
    "nimetus",
    "rollid"
})
public class IrOppeasutus {

    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    protected String regNr;
    @XmlElement(required = true)
    protected String nimetus;
    @XmlElement(required = true)
    protected Rollid rollid;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the regNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegNr() {
        return regNr;
    }

    /**
     * Sets the value of the regNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegNr(String value) {
        this.regNr = value;
    }

    /**
     * Gets the value of the nimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNimetus() {
        return nimetus;
    }

    /**
     * Sets the value of the nimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNimetus(String value) {
        this.nimetus = value;
    }

    /**
     * Gets the value of the rollid property.
     * 
     * @return
     *     possible object is
     *     {@link Rollid }
     *     
     */
    public Rollid getRollid() {
        return rollid;
    }

    /**
     * Sets the value of the rollid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Rollid }
     *     
     */
    public void setRollid(Rollid value) {
        this.rollid = value;
    }

}
