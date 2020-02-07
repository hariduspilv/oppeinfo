
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for KMOIsikuTuvastamine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="KMOIsikuTuvastamine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IsikID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode" minOccurs="0"/&gt;
 *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *         &lt;element name="Sugu" type="{http://rr.x-road.eu/producer}Sugu" minOccurs="0"/&gt;
 *         &lt;element name="Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="MuuNimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsaEesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KMOIsikuTuvastamine", propOrder = {
    "isikID",
    "isikukood",
    "eesnimi",
    "perenimi",
    "synniaeg",
    "sugu",
    "kodakondsus",
    "muuNimi",
    "isaEesnimi"
})
public class KMOIsikuTuvastamine {

    @XmlElement(name = "IsikID")
    protected Integer isikID;
    @XmlElement(name = "Isikukood")
    protected String isikukood;
    @XmlElement(name = "Eesnimi")
    protected String eesnimi;
    @XmlElement(name = "Perenimi")
    protected String perenimi;
    @XmlElement(name = "Synniaeg")
    protected String synniaeg;
    @XmlElement(name = "Sugu")
    @XmlSchemaType(name = "string")
    protected Sugu sugu;
    @XmlElement(name = "Kodakondsus")
    protected String kodakondsus;
    @XmlElement(name = "MuuNimi")
    protected String muuNimi;
    @XmlElement(name = "IsaEesnimi")
    protected String isaEesnimi;

    /**
     * Gets the value of the isikID property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIsikID() {
        return isikID;
    }

    /**
     * Sets the value of the isikID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIsikID(Integer value) {
        this.isikID = value;
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
     * Gets the value of the eesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEesnimi() {
        return eesnimi;
    }

    /**
     * Sets the value of the eesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEesnimi(String value) {
        this.eesnimi = value;
    }

    /**
     * Gets the value of the perenimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerenimi() {
        return perenimi;
    }

    /**
     * Sets the value of the perenimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerenimi(String value) {
        this.perenimi = value;
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
     * Gets the value of the sugu property.
     * 
     * @return
     *     possible object is
     *     {@link Sugu }
     *     
     */
    public Sugu getSugu() {
        return sugu;
    }

    /**
     * Sets the value of the sugu property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sugu }
     *     
     */
    public void setSugu(Sugu value) {
        this.sugu = value;
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
     * Gets the value of the muuNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMuuNimi() {
        return muuNimi;
    }

    /**
     * Sets the value of the muuNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMuuNimi(String value) {
        this.muuNimi = value;
    }

    /**
     * Gets the value of the isaEesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsaEesnimi() {
        return isaEesnimi;
    }

    /**
     * Sets the value of the isaEesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsaEesnimi(String value) {
        this.isaEesnimi = value;
    }

}
