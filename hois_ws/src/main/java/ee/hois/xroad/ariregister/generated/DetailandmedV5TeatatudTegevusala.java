
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for detailandmed_v5_teatatud_tegevusala complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="detailandmed_v5_teatatud_tegevusala"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kirje_id" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="emtak_kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="emtak_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="emtak_versioon" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="emtak_versioon_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nace_kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="on_pohitegevusala" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="algus_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="lopp_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detailandmed_v5_teatatud_tegevusala", propOrder = {
    "kirjeId",
    "emtakKood",
    "emtakTekstina",
    "emtakVersioon",
    "emtakVersioonTekstina",
    "naceKood",
    "onPohitegevusala",
    "algusKpv",
    "loppKpv"
})
public class DetailandmedV5TeatatudTegevusala {

    @XmlElement(name = "kirje_id")
    protected BigInteger kirjeId;
    @XmlElement(name = "emtak_kood")
    protected String emtakKood;
    @XmlElement(name = "emtak_tekstina")
    protected String emtakTekstina;
    @XmlElement(name = "emtak_versioon")
    protected BigInteger emtakVersioon;
    @XmlElement(name = "emtak_versioon_tekstina")
    protected String emtakVersioonTekstina;
    @XmlElement(name = "nace_kood")
    protected String naceKood;
    @XmlElement(name = "on_pohitegevusala")
    protected Boolean onPohitegevusala;
    @XmlElement(name = "algus_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar algusKpv;
    @XmlElement(name = "lopp_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar loppKpv;

    /**
     * Gets the value of the kirjeId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKirjeId() {
        return kirjeId;
    }

    /**
     * Sets the value of the kirjeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKirjeId(BigInteger value) {
        this.kirjeId = value;
    }

    /**
     * Gets the value of the emtakKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmtakKood() {
        return emtakKood;
    }

    /**
     * Sets the value of the emtakKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmtakKood(String value) {
        this.emtakKood = value;
    }

    /**
     * Gets the value of the emtakTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmtakTekstina() {
        return emtakTekstina;
    }

    /**
     * Sets the value of the emtakTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmtakTekstina(String value) {
        this.emtakTekstina = value;
    }

    /**
     * Gets the value of the emtakVersioon property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEmtakVersioon() {
        return emtakVersioon;
    }

    /**
     * Sets the value of the emtakVersioon property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEmtakVersioon(BigInteger value) {
        this.emtakVersioon = value;
    }

    /**
     * Gets the value of the emtakVersioonTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmtakVersioonTekstina() {
        return emtakVersioonTekstina;
    }

    /**
     * Sets the value of the emtakVersioonTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmtakVersioonTekstina(String value) {
        this.emtakVersioonTekstina = value;
    }

    /**
     * Gets the value of the naceKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaceKood() {
        return naceKood;
    }

    /**
     * Sets the value of the naceKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaceKood(String value) {
        this.naceKood = value;
    }

    /**
     * Gets the value of the onPohitegevusala property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOnPohitegevusala() {
        return onPohitegevusala;
    }

    /**
     * Sets the value of the onPohitegevusala property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOnPohitegevusala(Boolean value) {
        this.onPohitegevusala = value;
    }

    /**
     * Gets the value of the algusKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAlgusKpv() {
        return algusKpv;
    }

    /**
     * Sets the value of the algusKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAlgusKpv(XMLGregorianCalendar value) {
        this.algusKpv = value;
    }

    /**
     * Gets the value of the loppKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLoppKpv() {
        return loppKpv;
    }

    /**
     * Sets the value of the loppKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLoppKpv(XMLGregorianCalendar value) {
        this.loppKpv = value;
    }

}
