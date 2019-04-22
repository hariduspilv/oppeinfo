
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRStatAndmeteKanneRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRStatAndmeteKanneRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="rahvus" type="{http://rr.x-road.eu/producer}codRahvused"/&gt;
 *         &lt;element name="emakeel" type="{http://rr.x-road.eu/producer}codKeeled"/&gt;
 *         &lt;element name="haridus" type="{http://rr.x-road.eu/producer}codHaridused"/&gt;
 *         &lt;element name="tegevusala" type="{http://rr.x-road.eu/producer}codTegevusalad"/&gt;
 *         &lt;element name="pohjendus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kontaktandmed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRStatAndmeteKanneRequestType", propOrder = {
    "rahvus",
    "emakeel",
    "haridus",
    "tegevusala",
    "pohjendus",
    "kontaktandmed"
})
public class RRStatAndmeteKanneRequestType {

    @XmlElement(required = true)
    protected String rahvus;
    @XmlElement(required = true)
    protected String emakeel;
    @XmlElement(required = true)
    protected String haridus;
    @XmlElement(required = true)
    protected String tegevusala;
    @XmlElement(required = true)
    protected String pohjendus;
    @XmlElement(required = true)
    protected String kontaktandmed;

    /**
     * Gets the value of the rahvus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRahvus() {
        return rahvus;
    }

    /**
     * Sets the value of the rahvus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRahvus(String value) {
        this.rahvus = value;
    }

    /**
     * Gets the value of the emakeel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmakeel() {
        return emakeel;
    }

    /**
     * Sets the value of the emakeel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmakeel(String value) {
        this.emakeel = value;
    }

    /**
     * Gets the value of the haridus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHaridus() {
        return haridus;
    }

    /**
     * Sets the value of the haridus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHaridus(String value) {
        this.haridus = value;
    }

    /**
     * Gets the value of the tegevusala property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTegevusala() {
        return tegevusala;
    }

    /**
     * Sets the value of the tegevusala property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTegevusala(String value) {
        this.tegevusala = value;
    }

    /**
     * Gets the value of the pohjendus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPohjendus() {
        return pohjendus;
    }

    /**
     * Sets the value of the pohjendus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPohjendus(String value) {
        this.pohjendus = value;
    }

    /**
     * Gets the value of the kontaktandmed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKontaktandmed() {
        return kontaktandmed;
    }

    /**
     * Sets the value of the kontaktandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKontaktandmed(String value) {
        this.kontaktandmed = value;
    }

}
