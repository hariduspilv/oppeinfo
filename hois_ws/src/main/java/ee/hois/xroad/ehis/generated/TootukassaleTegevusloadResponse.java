
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oppeasutusRegistrikood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeasutusNimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="omanikRegistrikood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="omanikNimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="majandustegevusteated" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tkMajandustegevusteated" minOccurs="0"/&gt;
 *         &lt;element name="tegevusload" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tkTegevusload" minOccurs="0"/&gt;
 *         &lt;element name="teade" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "oppeasutusRegistrikood",
    "oppeasutusNimi",
    "omanikRegistrikood",
    "omanikNimi",
    "majandustegevusteated",
    "tegevusload",
    "teade"
})
@XmlRootElement(name = "tootukassaleTegevusloadResponse")
public class TootukassaleTegevusloadResponse {

    @XmlElement(required = true)
    protected String oppeasutusRegistrikood;
    protected String oppeasutusNimi;
    protected String omanikRegistrikood;
    protected String omanikNimi;
    protected TkMajandustegevusteated majandustegevusteated;
    protected TkTegevusload tegevusload;
    protected String teade;

    /**
     * Gets the value of the oppeasutusRegistrikood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutusRegistrikood() {
        return oppeasutusRegistrikood;
    }

    /**
     * Sets the value of the oppeasutusRegistrikood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutusRegistrikood(String value) {
        this.oppeasutusRegistrikood = value;
    }

    /**
     * Gets the value of the oppeasutusNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutusNimi() {
        return oppeasutusNimi;
    }

    /**
     * Sets the value of the oppeasutusNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutusNimi(String value) {
        this.oppeasutusNimi = value;
    }

    /**
     * Gets the value of the omanikRegistrikood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOmanikRegistrikood() {
        return omanikRegistrikood;
    }

    /**
     * Sets the value of the omanikRegistrikood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOmanikRegistrikood(String value) {
        this.omanikRegistrikood = value;
    }

    /**
     * Gets the value of the omanikNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOmanikNimi() {
        return omanikNimi;
    }

    /**
     * Sets the value of the omanikNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOmanikNimi(String value) {
        this.omanikNimi = value;
    }

    /**
     * Gets the value of the majandustegevusteated property.
     * 
     * @return
     *     possible object is
     *     {@link TkMajandustegevusteated }
     *     
     */
    public TkMajandustegevusteated getMajandustegevusteated() {
        return majandustegevusteated;
    }

    /**
     * Sets the value of the majandustegevusteated property.
     * 
     * @param value
     *     allowed object is
     *     {@link TkMajandustegevusteated }
     *     
     */
    public void setMajandustegevusteated(TkMajandustegevusteated value) {
        this.majandustegevusteated = value;
    }

    /**
     * Gets the value of the tegevusload property.
     * 
     * @return
     *     possible object is
     *     {@link TkTegevusload }
     *     
     */
    public TkTegevusload getTegevusload() {
        return tegevusload;
    }

    /**
     * Sets the value of the tegevusload property.
     * 
     * @param value
     *     allowed object is
     *     {@link TkTegevusload }
     *     
     */
    public void setTegevusload(TkTegevusload value) {
        this.tegevusload = value;
    }

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

}
