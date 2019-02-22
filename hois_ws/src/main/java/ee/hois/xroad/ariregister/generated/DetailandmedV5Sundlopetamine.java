
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for detailandmed_v5_sundlopetamine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="detailandmed_v5_sundlopetamine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="alus" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="alus_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="alajaotus" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="alajaotus_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="alajaotuse_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="teade" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="teate_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="teate_avaldaja_registriosakond" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="teate_avaldaja_registriosakond_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="asukohajargne_kohus" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="asukohajargne_kohus_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detailandmed_v5_sundlopetamine", propOrder = {
    "alus",
    "alusTekstina",
    "alajaotus",
    "alajaotusTekstina",
    "alajaotuseKpv",
    "teade",
    "teateKpv",
    "teateAvaldajaRegistriosakond",
    "teateAvaldajaRegistriosakondTekstina",
    "asukohajargneKohus",
    "asukohajargneKohusTekstina"
})
public class DetailandmedV5Sundlopetamine {

    protected BigInteger alus;
    @XmlElement(name = "alus_tekstina")
    protected String alusTekstina;
    protected BigInteger alajaotus;
    @XmlElement(name = "alajaotus_tekstina")
    protected String alajaotusTekstina;
    @XmlElement(name = "alajaotuse_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar alajaotuseKpv;
    protected String teade;
    @XmlElement(name = "teate_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar teateKpv;
    @XmlElement(name = "teate_avaldaja_registriosakond")
    protected BigInteger teateAvaldajaRegistriosakond;
    @XmlElement(name = "teate_avaldaja_registriosakond_tekstina")
    protected String teateAvaldajaRegistriosakondTekstina;
    @XmlElement(name = "asukohajargne_kohus")
    protected BigInteger asukohajargneKohus;
    @XmlElement(name = "asukohajargne_kohus_tekstina")
    protected String asukohajargneKohusTekstina;

    /**
     * Gets the value of the alus property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAlus() {
        return alus;
    }

    /**
     * Sets the value of the alus property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAlus(BigInteger value) {
        this.alus = value;
    }

    /**
     * Gets the value of the alusTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlusTekstina() {
        return alusTekstina;
    }

    /**
     * Sets the value of the alusTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlusTekstina(String value) {
        this.alusTekstina = value;
    }

    /**
     * Gets the value of the alajaotus property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAlajaotus() {
        return alajaotus;
    }

    /**
     * Sets the value of the alajaotus property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAlajaotus(BigInteger value) {
        this.alajaotus = value;
    }

    /**
     * Gets the value of the alajaotusTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlajaotusTekstina() {
        return alajaotusTekstina;
    }

    /**
     * Sets the value of the alajaotusTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlajaotusTekstina(String value) {
        this.alajaotusTekstina = value;
    }

    /**
     * Gets the value of the alajaotuseKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAlajaotuseKpv() {
        return alajaotuseKpv;
    }

    /**
     * Sets the value of the alajaotuseKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAlajaotuseKpv(XMLGregorianCalendar value) {
        this.alajaotuseKpv = value;
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

    /**
     * Gets the value of the teateKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTeateKpv() {
        return teateKpv;
    }

    /**
     * Sets the value of the teateKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTeateKpv(XMLGregorianCalendar value) {
        this.teateKpv = value;
    }

    /**
     * Gets the value of the teateAvaldajaRegistriosakond property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTeateAvaldajaRegistriosakond() {
        return teateAvaldajaRegistriosakond;
    }

    /**
     * Sets the value of the teateAvaldajaRegistriosakond property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTeateAvaldajaRegistriosakond(BigInteger value) {
        this.teateAvaldajaRegistriosakond = value;
    }

    /**
     * Gets the value of the teateAvaldajaRegistriosakondTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeateAvaldajaRegistriosakondTekstina() {
        return teateAvaldajaRegistriosakondTekstina;
    }

    /**
     * Sets the value of the teateAvaldajaRegistriosakondTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeateAvaldajaRegistriosakondTekstina(String value) {
        this.teateAvaldajaRegistriosakondTekstina = value;
    }

    /**
     * Gets the value of the asukohajargneKohus property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAsukohajargneKohus() {
        return asukohajargneKohus;
    }

    /**
     * Sets the value of the asukohajargneKohus property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAsukohajargneKohus(BigInteger value) {
        this.asukohajargneKohus = value;
    }

    /**
     * Gets the value of the asukohajargneKohusTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsukohajargneKohusTekstina() {
        return asukohajargneKohusTekstina;
    }

    /**
     * Sets the value of the asukohajargneKohusTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsukohajargneKohusTekstina(String value) {
        this.asukohajargneKohusTekstina = value;
    }

}
