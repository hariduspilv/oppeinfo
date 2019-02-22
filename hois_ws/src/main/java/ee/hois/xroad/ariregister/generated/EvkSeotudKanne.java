
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for evk_seotud_kanne complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="evk_seotud_kanne"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ariregistri_kood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="esmakande_aeg" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="kande_aeg" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="kandeliigi_tahis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kande_nr" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="notaritehingu_kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="arinimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oigusliku_vormi_tahis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="loppstaatuse_tahis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kapital" type="{http://arireg.x-road.eu/producer/}evk_kapital" minOccurs="0"/&gt;
 *         &lt;element name="pohikiri" type="{http://arireg.x-road.eu/producer/}evk_pohikiri" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "evk_seotud_kanne", propOrder = {
    "ariregistriKood",
    "esmakandeAeg",
    "kandeAeg",
    "kandeliigiTahis",
    "kandeNr",
    "notaritehinguKood",
    "arinimi",
    "oiguslikuVormiTahis",
    "loppstaatuseTahis",
    "kapital",
    "pohikiri"
})
public class EvkSeotudKanne {

    @XmlElement(name = "ariregistri_kood", required = true)
    protected BigInteger ariregistriKood;
    @XmlElement(name = "esmakande_aeg")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar esmakandeAeg;
    @XmlElement(name = "kande_aeg")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar kandeAeg;
    @XmlElement(name = "kandeliigi_tahis")
    protected String kandeliigiTahis;
    @XmlElement(name = "kande_nr")
    protected Integer kandeNr;
    @XmlElement(name = "notaritehingu_kood")
    protected String notaritehinguKood;
    protected String arinimi;
    @XmlElement(name = "oigusliku_vormi_tahis")
    protected String oiguslikuVormiTahis;
    @XmlElement(name = "loppstaatuse_tahis")
    protected String loppstaatuseTahis;
    protected EvkKapital kapital;
    protected List<EvkPohikiri> pohikiri;

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
     * Gets the value of the esmakandeAeg property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEsmakandeAeg() {
        return esmakandeAeg;
    }

    /**
     * Sets the value of the esmakandeAeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEsmakandeAeg(XMLGregorianCalendar value) {
        this.esmakandeAeg = value;
    }

    /**
     * Gets the value of the kandeAeg property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getKandeAeg() {
        return kandeAeg;
    }

    /**
     * Sets the value of the kandeAeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setKandeAeg(XMLGregorianCalendar value) {
        this.kandeAeg = value;
    }

    /**
     * Gets the value of the kandeliigiTahis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKandeliigiTahis() {
        return kandeliigiTahis;
    }

    /**
     * Sets the value of the kandeliigiTahis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKandeliigiTahis(String value) {
        this.kandeliigiTahis = value;
    }

    /**
     * Gets the value of the kandeNr property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getKandeNr() {
        return kandeNr;
    }

    /**
     * Sets the value of the kandeNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setKandeNr(Integer value) {
        this.kandeNr = value;
    }

    /**
     * Gets the value of the notaritehinguKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotaritehinguKood() {
        return notaritehinguKood;
    }

    /**
     * Sets the value of the notaritehinguKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotaritehinguKood(String value) {
        this.notaritehinguKood = value;
    }

    /**
     * Gets the value of the arinimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArinimi() {
        return arinimi;
    }

    /**
     * Sets the value of the arinimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArinimi(String value) {
        this.arinimi = value;
    }

    /**
     * Gets the value of the oiguslikuVormiTahis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOiguslikuVormiTahis() {
        return oiguslikuVormiTahis;
    }

    /**
     * Sets the value of the oiguslikuVormiTahis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOiguslikuVormiTahis(String value) {
        this.oiguslikuVormiTahis = value;
    }

    /**
     * Gets the value of the loppstaatuseTahis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoppstaatuseTahis() {
        return loppstaatuseTahis;
    }

    /**
     * Sets the value of the loppstaatuseTahis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoppstaatuseTahis(String value) {
        this.loppstaatuseTahis = value;
    }

    /**
     * Gets the value of the kapital property.
     * 
     * @return
     *     possible object is
     *     {@link EvkKapital }
     *     
     */
    public EvkKapital getKapital() {
        return kapital;
    }

    /**
     * Sets the value of the kapital property.
     * 
     * @param value
     *     allowed object is
     *     {@link EvkKapital }
     *     
     */
    public void setKapital(EvkKapital value) {
        this.kapital = value;
    }

    /**
     * Gets the value of the pohikiri property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pohikiri property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPohikiri().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EvkPohikiri }
     * 
     * 
     */
    public List<EvkPohikiri> getPohikiri() {
        if (pohikiri == null) {
            pohikiri = new ArrayList<EvkPohikiri>();
        }
        return this.pohikiri;
    }

}
