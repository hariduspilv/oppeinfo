
package ee.hois.xroad.ariregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ettevotja_muudatus_tootukassa_kanne complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevotja_muudatus_tootukassa_kanne"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ettevotja_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ettevotja_ariregistri_kood" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="ettevotja_arinimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ettevotja_oiguslik_vorm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ettevotja_staatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kande_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kande_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="kande_liik" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="kandeelement" type="{http://arireg.x-road.eu/producer/}ettevotja_muudatus_tootukassa_kandeelement" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ettevotja_muudatus_tootukassa_kanne", propOrder = {
    "ettevotjaId",
    "ettevotjaAriregistriKood",
    "ettevotjaArinimi",
    "ettevotjaOiguslikVorm",
    "ettevotjaStaatus",
    "kandeId",
    "kandeKpv",
    "kandeLiik",
    "kandeelement"
})
public class EttevotjaMuudatusTootukassaKanne {

    @XmlElement(name = "ettevotja_id")
    protected String ettevotjaId;
    @XmlElement(name = "ettevotja_ariregistri_kood")
    protected Integer ettevotjaAriregistriKood;
    @XmlElement(name = "ettevotja_arinimi")
    protected String ettevotjaArinimi;
    @XmlElement(name = "ettevotja_oiguslik_vorm")
    protected String ettevotjaOiguslikVorm;
    @XmlElement(name = "ettevotja_staatus")
    protected String ettevotjaStaatus;
    @XmlElement(name = "kande_id")
    protected String kandeId;
    @XmlElement(name = "kande_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar kandeKpv;
    @XmlElement(name = "kande_liik")
    protected Integer kandeLiik;
    protected List<EttevotjaMuudatusTootukassaKandeelement> kandeelement;

    /**
     * Gets the value of the ettevotjaId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEttevotjaId() {
        return ettevotjaId;
    }

    /**
     * Sets the value of the ettevotjaId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEttevotjaId(String value) {
        this.ettevotjaId = value;
    }

    /**
     * Gets the value of the ettevotjaAriregistriKood property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEttevotjaAriregistriKood() {
        return ettevotjaAriregistriKood;
    }

    /**
     * Sets the value of the ettevotjaAriregistriKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEttevotjaAriregistriKood(Integer value) {
        this.ettevotjaAriregistriKood = value;
    }

    /**
     * Gets the value of the ettevotjaArinimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEttevotjaArinimi() {
        return ettevotjaArinimi;
    }

    /**
     * Sets the value of the ettevotjaArinimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEttevotjaArinimi(String value) {
        this.ettevotjaArinimi = value;
    }

    /**
     * Gets the value of the ettevotjaOiguslikVorm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEttevotjaOiguslikVorm() {
        return ettevotjaOiguslikVorm;
    }

    /**
     * Sets the value of the ettevotjaOiguslikVorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEttevotjaOiguslikVorm(String value) {
        this.ettevotjaOiguslikVorm = value;
    }

    /**
     * Gets the value of the ettevotjaStaatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEttevotjaStaatus() {
        return ettevotjaStaatus;
    }

    /**
     * Sets the value of the ettevotjaStaatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEttevotjaStaatus(String value) {
        this.ettevotjaStaatus = value;
    }

    /**
     * Gets the value of the kandeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKandeId() {
        return kandeId;
    }

    /**
     * Sets the value of the kandeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKandeId(String value) {
        this.kandeId = value;
    }

    /**
     * Gets the value of the kandeKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getKandeKpv() {
        return kandeKpv;
    }

    /**
     * Sets the value of the kandeKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setKandeKpv(XMLGregorianCalendar value) {
        this.kandeKpv = value;
    }

    /**
     * Gets the value of the kandeLiik property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getKandeLiik() {
        return kandeLiik;
    }

    /**
     * Sets the value of the kandeLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setKandeLiik(Integer value) {
        this.kandeLiik = value;
    }

    /**
     * Gets the value of the kandeelement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the kandeelement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKandeelement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EttevotjaMuudatusTootukassaKandeelement }
     * 
     * 
     */
    public List<EttevotjaMuudatusTootukassaKandeelement> getKandeelement() {
        if (kandeelement == null) {
            kandeelement = new ArrayList<EttevotjaMuudatusTootukassaKandeelement>();
        }
        return this.kandeelement;
    }

}
