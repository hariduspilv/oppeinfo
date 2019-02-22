
package ee.hois.xroad.ariregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ettevotja_muudatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevotja_muudatus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ariregistri_kood" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="arinimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oiguslik_vorm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kandevalised_isikud" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kandevalised_sidevahendid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kandevalised_tegevusalad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kanded" type="{http://arireg.x-road.eu/producer/}ettevotja_muudatuse_kanne" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ettevotja_muudatus", propOrder = {
    "ariregistriKood",
    "arinimi",
    "oiguslikVorm",
    "kandevalisedIsikud",
    "kandevalisedSidevahendid",
    "kandevalisedTegevusalad",
    "kanded"
})
public class EttevotjaMuudatus {

    @XmlElement(name = "ariregistri_kood")
    protected Integer ariregistriKood;
    protected String arinimi;
    @XmlElement(name = "oiguslik_vorm")
    protected String oiguslikVorm;
    @XmlElement(name = "kandevalised_isikud")
    protected String kandevalisedIsikud;
    @XmlElement(name = "kandevalised_sidevahendid")
    protected String kandevalisedSidevahendid;
    @XmlElement(name = "kandevalised_tegevusalad")
    protected String kandevalisedTegevusalad;
    protected List<EttevotjaMuudatuseKanne> kanded;

    /**
     * Gets the value of the ariregistriKood property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAriregistriKood() {
        return ariregistriKood;
    }

    /**
     * Sets the value of the ariregistriKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAriregistriKood(Integer value) {
        this.ariregistriKood = value;
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
     * Gets the value of the kandevalisedIsikud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKandevalisedIsikud() {
        return kandevalisedIsikud;
    }

    /**
     * Sets the value of the kandevalisedIsikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKandevalisedIsikud(String value) {
        this.kandevalisedIsikud = value;
    }

    /**
     * Gets the value of the kandevalisedSidevahendid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKandevalisedSidevahendid() {
        return kandevalisedSidevahendid;
    }

    /**
     * Sets the value of the kandevalisedSidevahendid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKandevalisedSidevahendid(String value) {
        this.kandevalisedSidevahendid = value;
    }

    /**
     * Gets the value of the kandevalisedTegevusalad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKandevalisedTegevusalad() {
        return kandevalisedTegevusalad;
    }

    /**
     * Sets the value of the kandevalisedTegevusalad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKandevalisedTegevusalad(String value) {
        this.kandevalisedTegevusalad = value;
    }

    /**
     * Gets the value of the kanded property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the kanded property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKanded().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EttevotjaMuudatuseKanne }
     * 
     * 
     */
    public List<EttevotjaMuudatuseKanne> getKanded() {
        if (kanded == null) {
            kanded = new ArrayList<EttevotjaMuudatuseKanne>();
        }
        return this.kanded;
    }

}
