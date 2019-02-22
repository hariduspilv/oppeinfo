
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for aruande_myygitulu_paring complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="aruande_myygitulu_paring"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ariregister_kasutajanimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ariregister_parool" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="esitatud_algus" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="esitatud_lopp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ariregistri_kood" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="aruande_aasta" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="aruande_aasta_algus" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="aruande_aasta_lopp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="oiguslik_vorm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lehekylg" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="keel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "aruande_myygitulu_paring", propOrder = {
    "ariregisterKasutajanimi",
    "ariregisterParool",
    "esitatudAlgus",
    "esitatudLopp",
    "ariregistriKood",
    "aruandeAasta",
    "aruandeAastaAlgus",
    "aruandeAastaLopp",
    "oiguslikVorm",
    "lehekylg",
    "keel"
})
public class AruandeMyygituluParing {

    @XmlElement(name = "ariregister_kasutajanimi")
    protected String ariregisterKasutajanimi;
    @XmlElement(name = "ariregister_parool")
    protected String ariregisterParool;
    @XmlElement(name = "esitatud_algus", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar esitatudAlgus;
    @XmlElement(name = "esitatud_lopp", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar esitatudLopp;
    @XmlElement(name = "ariregistri_kood")
    protected BigInteger ariregistriKood;
    @XmlElement(name = "aruande_aasta")
    protected BigInteger aruandeAasta;
    @XmlElement(name = "aruande_aasta_algus")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar aruandeAastaAlgus;
    @XmlElement(name = "aruande_aasta_lopp")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar aruandeAastaLopp;
    @XmlElement(name = "oiguslik_vorm")
    protected String oiguslikVorm;
    protected BigInteger lehekylg;
    protected String keel;

    /**
     * Gets the value of the ariregisterKasutajanimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAriregisterKasutajanimi() {
        return ariregisterKasutajanimi;
    }

    /**
     * Sets the value of the ariregisterKasutajanimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAriregisterKasutajanimi(String value) {
        this.ariregisterKasutajanimi = value;
    }

    /**
     * Gets the value of the ariregisterParool property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAriregisterParool() {
        return ariregisterParool;
    }

    /**
     * Sets the value of the ariregisterParool property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAriregisterParool(String value) {
        this.ariregisterParool = value;
    }

    /**
     * Gets the value of the esitatudAlgus property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEsitatudAlgus() {
        return esitatudAlgus;
    }

    /**
     * Sets the value of the esitatudAlgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEsitatudAlgus(XMLGregorianCalendar value) {
        this.esitatudAlgus = value;
    }

    /**
     * Gets the value of the esitatudLopp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEsitatudLopp() {
        return esitatudLopp;
    }

    /**
     * Sets the value of the esitatudLopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEsitatudLopp(XMLGregorianCalendar value) {
        this.esitatudLopp = value;
    }

    /**
     * Gets the value of the ariregistriKood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAriregistriKood() {
        return ariregistriKood;
    }

    /**
     * Sets the value of the ariregistriKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAriregistriKood(BigInteger value) {
        this.ariregistriKood = value;
    }

    /**
     * Gets the value of the aruandeAasta property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAruandeAasta() {
        return aruandeAasta;
    }

    /**
     * Sets the value of the aruandeAasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAruandeAasta(BigInteger value) {
        this.aruandeAasta = value;
    }

    /**
     * Gets the value of the aruandeAastaAlgus property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAruandeAastaAlgus() {
        return aruandeAastaAlgus;
    }

    /**
     * Sets the value of the aruandeAastaAlgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAruandeAastaAlgus(XMLGregorianCalendar value) {
        this.aruandeAastaAlgus = value;
    }

    /**
     * Gets the value of the aruandeAastaLopp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAruandeAastaLopp() {
        return aruandeAastaLopp;
    }

    /**
     * Sets the value of the aruandeAastaLopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAruandeAastaLopp(XMLGregorianCalendar value) {
        this.aruandeAastaLopp = value;
    }

    /**
     * Gets the value of the oiguslikVorm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOiguslikVorm() {
        return oiguslikVorm;
    }

    /**
     * Sets the value of the oiguslikVorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOiguslikVorm(String value) {
        this.oiguslikVorm = value;
    }

    /**
     * Gets the value of the lehekylg property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLehekylg() {
        return lehekylg;
    }

    /**
     * Sets the value of the lehekylg property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLehekylg(BigInteger value) {
        this.lehekylg = value;
    }

    /**
     * Gets the value of the keel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeel() {
        return keel;
    }

    /**
     * Sets the value of the keel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeel(String value) {
        this.keel = value;
    }

}
