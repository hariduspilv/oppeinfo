
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for alusOppur complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="alusOppur"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="isikukood" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisIsikukood" minOccurs="0"/&gt;
 *           &lt;element name="isikuandmed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}alusIsikuandmed" minOccurs="0"/&gt;
 *           &lt;element name="koolIsikId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;/choice&gt;
 *         &lt;choice&gt;
 *           &lt;element name="lisamine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}alusLisa" minOccurs="0"/&gt;
 *           &lt;element name="muutmine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}alusMuuda" minOccurs="0"/&gt;
 *           &lt;element name="muutmineId" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}alusOppurKoolIsikId" minOccurs="0"/&gt;
 *           &lt;element name="muutmineIsikukood" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}alusOppurIsikukood" minOccurs="0"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "alusOppur", propOrder = {
    "isikukood",
    "isikuandmed",
    "koolIsikId",
    "lisamine",
    "muutmine",
    "muutmineId",
    "muutmineIsikukood"
})
public class AlusOppur {

    protected String isikukood;
    protected AlusIsikuandmed isikuandmed;
    protected String koolIsikId;
    protected AlusLisa lisamine;
    protected AlusMuuda muutmine;
    protected AlusOppurKoolIsikId muutmineId;
    protected AlusOppurIsikukood muutmineIsikukood;

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
     * Gets the value of the isikuandmed property.
     * 
     * @return
     *     possible object is
     *     {@link AlusIsikuandmed }
     *     
     */
    public AlusIsikuandmed getIsikuandmed() {
        return isikuandmed;
    }

    /**
     * Sets the value of the isikuandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlusIsikuandmed }
     *     
     */
    public void setIsikuandmed(AlusIsikuandmed value) {
        this.isikuandmed = value;
    }

    /**
     * Gets the value of the koolIsikId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKoolIsikId() {
        return koolIsikId;
    }

    /**
     * Sets the value of the koolIsikId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKoolIsikId(String value) {
        this.koolIsikId = value;
    }

    /**
     * Gets the value of the lisamine property.
     * 
     * @return
     *     possible object is
     *     {@link AlusLisa }
     *     
     */
    public AlusLisa getLisamine() {
        return lisamine;
    }

    /**
     * Sets the value of the lisamine property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlusLisa }
     *     
     */
    public void setLisamine(AlusLisa value) {
        this.lisamine = value;
    }

    /**
     * Gets the value of the muutmine property.
     * 
     * @return
     *     possible object is
     *     {@link AlusMuuda }
     *     
     */
    public AlusMuuda getMuutmine() {
        return muutmine;
    }

    /**
     * Sets the value of the muutmine property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlusMuuda }
     *     
     */
    public void setMuutmine(AlusMuuda value) {
        this.muutmine = value;
    }

    /**
     * Gets the value of the muutmineId property.
     * 
     * @return
     *     possible object is
     *     {@link AlusOppurKoolIsikId }
     *     
     */
    public AlusOppurKoolIsikId getMuutmineId() {
        return muutmineId;
    }

    /**
     * Sets the value of the muutmineId property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlusOppurKoolIsikId }
     *     
     */
    public void setMuutmineId(AlusOppurKoolIsikId value) {
        this.muutmineId = value;
    }

    /**
     * Gets the value of the muutmineIsikukood property.
     * 
     * @return
     *     possible object is
     *     {@link AlusOppurIsikukood }
     *     
     */
    public AlusOppurIsikukood getMuutmineIsikukood() {
        return muutmineIsikukood;
    }

    /**
     * Sets the value of the muutmineIsikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlusOppurIsikukood }
     *     
     */
    public void setMuutmineIsikukood(AlusOppurIsikukood value) {
        this.muutmineIsikukood = value;
    }

}
