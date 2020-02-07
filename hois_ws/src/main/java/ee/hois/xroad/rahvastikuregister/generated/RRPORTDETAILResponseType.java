
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRPORTDETAILResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRPORTDETAILResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="veatekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="MPerenimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MEesnimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Perekonnaseis" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Synninimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Emakeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Haridus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Tegevusala" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRPORTDETAILResponseType", propOrder = {
    "veatekst",
    "mPerenimed",
    "mEesnimed",
    "isanimi",
    "sugu",
    "synniaeg",
    "kodakondsus",
    "synnikoht",
    "perekonnaseis",
    "synninimi",
    "emakeel",
    "haridus",
    "tegevusala"
})
public class RRPORTDETAILResponseType
    extends XRoadResponseBaseType
{

    protected String veatekst;
    @XmlElement(name = "MPerenimed", required = true)
    protected String mPerenimed;
    @XmlElement(name = "MEesnimed", required = true)
    protected String mEesnimed;
    @XmlElement(name = "Isanimi", required = true)
    protected String isanimi;
    @XmlElement(name = "Sugu", required = true)
    protected String sugu;
    @XmlElement(name = "Synniaeg", required = true)
    protected String synniaeg;
    @XmlElement(name = "Kodakondsus", required = true)
    protected String kodakondsus;
    @XmlElement(name = "Synnikoht", required = true)
    protected String synnikoht;
    @XmlElement(name = "Perekonnaseis", required = true)
    protected String perekonnaseis;
    @XmlElement(name = "Synninimi", required = true)
    protected String synninimi;
    @XmlElement(name = "Emakeel", required = true)
    protected String emakeel;
    @XmlElement(name = "Haridus", required = true)
    protected String haridus;
    @XmlElement(name = "Tegevusala", required = true)
    protected String tegevusala;

    /**
     * Gets the value of the veatekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVeatekst() {
        return veatekst;
    }

    /**
     * Sets the value of the veatekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVeatekst(String value) {
        this.veatekst = value;
    }

    /**
     * Gets the value of the mPerenimed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMPerenimed() {
        return mPerenimed;
    }

    /**
     * Sets the value of the mPerenimed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMPerenimed(String value) {
        this.mPerenimed = value;
    }

    /**
     * Gets the value of the mEesnimed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMEesnimed() {
        return mEesnimed;
    }

    /**
     * Sets the value of the mEesnimed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMEesnimed(String value) {
        this.mEesnimed = value;
    }

    /**
     * Gets the value of the isanimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsanimi() {
        return isanimi;
    }

    /**
     * Sets the value of the isanimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsanimi(String value) {
        this.isanimi = value;
    }

    /**
     * Gets the value of the sugu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSugu() {
        return sugu;
    }

    /**
     * Sets the value of the sugu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSugu(String value) {
        this.sugu = value;
    }

    /**
     * Gets the value of the synniaeg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSynniaeg() {
        return synniaeg;
    }

    /**
     * Sets the value of the synniaeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSynniaeg(String value) {
        this.synniaeg = value;
    }

    /**
     * Gets the value of the kodakondsus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKodakondsus() {
        return kodakondsus;
    }

    /**
     * Sets the value of the kodakondsus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKodakondsus(String value) {
        this.kodakondsus = value;
    }

    /**
     * Gets the value of the synnikoht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSynnikoht() {
        return synnikoht;
    }

    /**
     * Sets the value of the synnikoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSynnikoht(String value) {
        this.synnikoht = value;
    }

    /**
     * Gets the value of the perekonnaseis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerekonnaseis() {
        return perekonnaseis;
    }

    /**
     * Sets the value of the perekonnaseis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerekonnaseis(String value) {
        this.perekonnaseis = value;
    }

    /**
     * Gets the value of the synninimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSynninimi() {
        return synninimi;
    }

    /**
     * Sets the value of the synninimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSynninimi(String value) {
        this.synninimi = value;
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

}
