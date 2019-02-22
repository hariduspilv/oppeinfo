
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
 * <p>Java class for ettevotja_muudatused_tasuta_paring complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevotja_muudatused_tasuta_paring"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kuupaev" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="muudatuste_valik" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="kandevalised_valik" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="kande_kandeliigid" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="kande_kandeosad" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="kande_eelmine_staatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kande_uus_staatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tulemuste_lk" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ettevotja_muudatused_tasuta_paring", propOrder = {
    "kuupaev",
    "muudatusteValik",
    "kandevalisedValik",
    "kandeKandeliigid",
    "kandeKandeosad",
    "kandeEelmineStaatus",
    "kandeUusStaatus",
    "tulemusteLk"
})
public class EttevotjaMuudatusedTasutaParing {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar kuupaev;
    @XmlElement(name = "muudatuste_valik")
    protected List<String> muudatusteValik;
    @XmlElement(name = "kandevalised_valik")
    protected List<String> kandevalisedValik;
    @XmlElement(name = "kande_kandeliigid", type = Integer.class)
    protected List<Integer> kandeKandeliigid;
    @XmlElement(name = "kande_kandeosad")
    protected List<String> kandeKandeosad;
    @XmlElement(name = "kande_eelmine_staatus")
    protected String kandeEelmineStaatus;
    @XmlElement(name = "kande_uus_staatus")
    protected String kandeUusStaatus;
    @XmlElement(name = "tulemuste_lk")
    protected Integer tulemusteLk;

    /**
     * Gets the value of the kuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getKuupaev() {
        return kuupaev;
    }

    /**
     * Sets the value of the kuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setKuupaev(XMLGregorianCalendar value) {
        this.kuupaev = value;
    }

    /**
     * Gets the value of the muudatusteValik property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the muudatusteValik property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMuudatusteValik().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getMuudatusteValik() {
        if (muudatusteValik == null) {
            muudatusteValik = new ArrayList<String>();
        }
        return this.muudatusteValik;
    }

    /**
     * Gets the value of the kandevalisedValik property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the kandevalisedValik property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKandevalisedValik().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getKandevalisedValik() {
        if (kandevalisedValik == null) {
            kandevalisedValik = new ArrayList<String>();
        }
        return this.kandevalisedValik;
    }

    /**
     * Gets the value of the kandeKandeliigid property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the kandeKandeliigid property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKandeKandeliigid().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getKandeKandeliigid() {
        if (kandeKandeliigid == null) {
            kandeKandeliigid = new ArrayList<Integer>();
        }
        return this.kandeKandeliigid;
    }

    /**
     * Gets the value of the kandeKandeosad property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the kandeKandeosad property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKandeKandeosad().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getKandeKandeosad() {
        if (kandeKandeosad == null) {
            kandeKandeosad = new ArrayList<String>();
        }
        return this.kandeKandeosad;
    }

    /**
     * Gets the value of the kandeEelmineStaatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKandeEelmineStaatus() {
        return kandeEelmineStaatus;
    }

    /**
     * Sets the value of the kandeEelmineStaatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKandeEelmineStaatus(String value) {
        this.kandeEelmineStaatus = value;
    }

    /**
     * Gets the value of the kandeUusStaatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKandeUusStaatus() {
        return kandeUusStaatus;
    }

    /**
     * Sets the value of the kandeUusStaatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKandeUusStaatus(String value) {
        this.kandeUusStaatus = value;
    }

    /**
     * Gets the value of the tulemusteLk property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTulemusteLk() {
        return tulemusteLk;
    }

    /**
     * Sets the value of the tulemusteLk property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTulemusteLk(Integer value) {
        this.tulemusteLk = value;
    }

}
