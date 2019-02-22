
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SooritaKanneVastus_v4 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SooritaKanneVastus_v4"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="teade" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="legacy_lahendi_number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="paevikukande_id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ettevotja_id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/all&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SooritaKanneVastus_v4", propOrder = {

})
public class SooritaKanneVastusV4 {

    @XmlElement(required = true)
    protected String teade;
    @XmlElement(name = "legacy_lahendi_number", required = true, nillable = true)
    protected String legacyLahendiNumber;
    @XmlElement(name = "paevikukande_id", required = true, nillable = true)
    protected String paevikukandeId;
    @XmlElement(name = "ettevotja_id", required = true, nillable = true)
    protected String ettevotjaId;

    /**
     * Gets the value of the teade property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeade() {
        return teade;
    }

    /**
     * Sets the value of the teade property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeade(String value) {
        this.teade = value;
    }

    /**
     * Gets the value of the legacyLahendiNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegacyLahendiNumber() {
        return legacyLahendiNumber;
    }

    /**
     * Sets the value of the legacyLahendiNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegacyLahendiNumber(String value) {
        this.legacyLahendiNumber = value;
    }

    /**
     * Gets the value of the paevikukandeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaevikukandeId() {
        return paevikukandeId;
    }

    /**
     * Sets the value of the paevikukandeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaevikukandeId(String value) {
        this.paevikukandeId = value;
    }

    /**
     * Gets the value of the ettevotjaId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEttevotjaId() {
        return ettevotjaId;
    }

    /**
     * Sets the value of the ettevotjaId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEttevotjaId(String value) {
        this.ettevotjaId = value;
    }

}
