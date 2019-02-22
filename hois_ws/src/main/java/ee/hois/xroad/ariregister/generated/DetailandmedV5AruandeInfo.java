
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for detailandmed_v5_aruande_info complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="detailandmed_v5_aruande_info"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kirje_id" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="majandusaasta_perioodi_algus_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="majandusaasta_perioodi_lopp_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="tootajate_arv" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="ettevotja_aadress_aruandes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tegevusala_emtak_kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tegevusala_emtak_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tegevusala_emtak_versioon" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="tegevusala_emtak_versioon_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tegevusala_nace_kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detailandmed_v5_aruande_info", propOrder = {
    "kirjeId",
    "majandusaastaPerioodiAlgusKpv",
    "majandusaastaPerioodiLoppKpv",
    "tootajateArv",
    "ettevotjaAadressAruandes",
    "tegevusalaEmtakKood",
    "tegevusalaEmtakTekstina",
    "tegevusalaEmtakVersioon",
    "tegevusalaEmtakVersioonTekstina",
    "tegevusalaNaceKood"
})
public class DetailandmedV5AruandeInfo {

    @XmlElement(name = "kirje_id")
    protected BigInteger kirjeId;
    @XmlElement(name = "majandusaasta_perioodi_algus_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar majandusaastaPerioodiAlgusKpv;
    @XmlElement(name = "majandusaasta_perioodi_lopp_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar majandusaastaPerioodiLoppKpv;
    @XmlElement(name = "tootajate_arv")
    protected BigInteger tootajateArv;
    @XmlElement(name = "ettevotja_aadress_aruandes")
    protected String ettevotjaAadressAruandes;
    @XmlElement(name = "tegevusala_emtak_kood")
    protected String tegevusalaEmtakKood;
    @XmlElement(name = "tegevusala_emtak_tekstina")
    protected String tegevusalaEmtakTekstina;
    @XmlElement(name = "tegevusala_emtak_versioon")
    protected BigInteger tegevusalaEmtakVersioon;
    @XmlElement(name = "tegevusala_emtak_versioon_tekstina")
    protected String tegevusalaEmtakVersioonTekstina;
    @XmlElement(name = "tegevusala_nace_kood")
    protected String tegevusalaNaceKood;

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
     * Gets the value of the majandusaastaPerioodiAlgusKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMajandusaastaPerioodiAlgusKpv() {
        return majandusaastaPerioodiAlgusKpv;
    }

    /**
     * Sets the value of the majandusaastaPerioodiAlgusKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMajandusaastaPerioodiAlgusKpv(XMLGregorianCalendar value) {
        this.majandusaastaPerioodiAlgusKpv = value;
    }

    /**
     * Gets the value of the majandusaastaPerioodiLoppKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMajandusaastaPerioodiLoppKpv() {
        return majandusaastaPerioodiLoppKpv;
    }

    /**
     * Sets the value of the majandusaastaPerioodiLoppKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMajandusaastaPerioodiLoppKpv(XMLGregorianCalendar value) {
        this.majandusaastaPerioodiLoppKpv = value;
    }

    /**
     * Gets the value of the tootajateArv property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTootajateArv() {
        return tootajateArv;
    }

    /**
     * Sets the value of the tootajateArv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTootajateArv(BigInteger value) {
        this.tootajateArv = value;
    }

    /**
     * Gets the value of the ettevotjaAadressAruandes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEttevotjaAadressAruandes() {
        return ettevotjaAadressAruandes;
    }

    /**
     * Sets the value of the ettevotjaAadressAruandes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEttevotjaAadressAruandes(String value) {
        this.ettevotjaAadressAruandes = value;
    }

    /**
     * Gets the value of the tegevusalaEmtakKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTegevusalaEmtakKood() {
        return tegevusalaEmtakKood;
    }

    /**
     * Sets the value of the tegevusalaEmtakKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTegevusalaEmtakKood(String value) {
        this.tegevusalaEmtakKood = value;
    }

    /**
     * Gets the value of the tegevusalaEmtakTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTegevusalaEmtakTekstina() {
        return tegevusalaEmtakTekstina;
    }

    /**
     * Sets the value of the tegevusalaEmtakTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTegevusalaEmtakTekstina(String value) {
        this.tegevusalaEmtakTekstina = value;
    }

    /**
     * Gets the value of the tegevusalaEmtakVersioon property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTegevusalaEmtakVersioon() {
        return tegevusalaEmtakVersioon;
    }

    /**
     * Sets the value of the tegevusalaEmtakVersioon property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTegevusalaEmtakVersioon(BigInteger value) {
        this.tegevusalaEmtakVersioon = value;
    }

    /**
     * Gets the value of the tegevusalaEmtakVersioonTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTegevusalaEmtakVersioonTekstina() {
        return tegevusalaEmtakVersioonTekstina;
    }

    /**
     * Sets the value of the tegevusalaEmtakVersioonTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTegevusalaEmtakVersioonTekstina(String value) {
        this.tegevusalaEmtakVersioonTekstina = value;
    }

    /**
     * Gets the value of the tegevusalaNaceKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTegevusalaNaceKood() {
        return tegevusalaNaceKood;
    }

    /**
     * Sets the value of the tegevusalaNaceKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTegevusalaNaceKood(String value) {
        this.tegevusalaNaceKood = value;
    }

}
