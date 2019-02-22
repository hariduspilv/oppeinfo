
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ettevotja_muudatused_tasuta_vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevotja_muudatused_tasuta_vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kanded" type="{http://arireg.x-road.eu/producer/}ettevotja_muudatus_kanded" minOccurs="0"/&gt;
 *         &lt;element name="kanded_arv" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="kandevalised" type="{http://arireg.x-road.eu/producer/}ettevotja_muudatus_kandevalised" minOccurs="0"/&gt;
 *         &lt;element name="kandevalised_arv" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="aruanded" type="{http://arireg.x-road.eu/producer/}ettevotja_muudatus_aruanded" minOccurs="0"/&gt;
 *         &lt;element name="aruanded_arv" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="maarused" type="{http://arireg.x-road.eu/producer/}ettevotja_muudatus_maarused" minOccurs="0"/&gt;
 *         &lt;element name="maarused_arv" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ettevotja_muudatused_tasuta_vastus", propOrder = {
    "kanded",
    "kandedArv",
    "kandevalised",
    "kandevalisedArv",
    "aruanded",
    "aruandedArv",
    "maarused",
    "maarusedArv"
})
public class EttevotjaMuudatusedTasutaVastus {

    protected EttevotjaMuudatusKanded kanded;
    @XmlElement(name = "kanded_arv")
    protected Integer kandedArv;
    protected EttevotjaMuudatusKandevalised kandevalised;
    @XmlElement(name = "kandevalised_arv")
    protected Integer kandevalisedArv;
    protected EttevotjaMuudatusAruanded aruanded;
    @XmlElement(name = "aruanded_arv")
    protected Integer aruandedArv;
    protected EttevotjaMuudatusMaarused maarused;
    @XmlElement(name = "maarused_arv")
    protected Integer maarusedArv;

    /**
     * Gets the value of the kanded property.
     * 
     * @return
     *     possible object is
     *     {@link EttevotjaMuudatusKanded }
     *     
     */
    public EttevotjaMuudatusKanded getKanded() {
        return kanded;
    }

    /**
     * Sets the value of the kanded property.
     * 
     * @param value
     *     allowed object is
     *     {@link EttevotjaMuudatusKanded }
     *     
     */
    public void setKanded(EttevotjaMuudatusKanded value) {
        this.kanded = value;
    }

    /**
     * Gets the value of the kandedArv property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getKandedArv() {
        return kandedArv;
    }

    /**
     * Sets the value of the kandedArv property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setKandedArv(Integer value) {
        this.kandedArv = value;
    }

    /**
     * Gets the value of the kandevalised property.
     * 
     * @return
     *     possible object is
     *     {@link EttevotjaMuudatusKandevalised }
     *     
     */
    public EttevotjaMuudatusKandevalised getKandevalised() {
        return kandevalised;
    }

    /**
     * Sets the value of the kandevalised property.
     * 
     * @param value
     *     allowed object is
     *     {@link EttevotjaMuudatusKandevalised }
     *     
     */
    public void setKandevalised(EttevotjaMuudatusKandevalised value) {
        this.kandevalised = value;
    }

    /**
     * Gets the value of the kandevalisedArv property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getKandevalisedArv() {
        return kandevalisedArv;
    }

    /**
     * Sets the value of the kandevalisedArv property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setKandevalisedArv(Integer value) {
        this.kandevalisedArv = value;
    }

    /**
     * Gets the value of the aruanded property.
     * 
     * @return
     *     possible object is
     *     {@link EttevotjaMuudatusAruanded }
     *     
     */
    public EttevotjaMuudatusAruanded getAruanded() {
        return aruanded;
    }

    /**
     * Sets the value of the aruanded property.
     * 
     * @param value
     *     allowed object is
     *     {@link EttevotjaMuudatusAruanded }
     *     
     */
    public void setAruanded(EttevotjaMuudatusAruanded value) {
        this.aruanded = value;
    }

    /**
     * Gets the value of the aruandedArv property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAruandedArv() {
        return aruandedArv;
    }

    /**
     * Sets the value of the aruandedArv property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAruandedArv(Integer value) {
        this.aruandedArv = value;
    }

    /**
     * Gets the value of the maarused property.
     * 
     * @return
     *     possible object is
     *     {@link EttevotjaMuudatusMaarused }
     *     
     */
    public EttevotjaMuudatusMaarused getMaarused() {
        return maarused;
    }

    /**
     * Sets the value of the maarused property.
     * 
     * @param value
     *     allowed object is
     *     {@link EttevotjaMuudatusMaarused }
     *     
     */
    public void setMaarused(EttevotjaMuudatusMaarused value) {
        this.maarused = value;
    }

    /**
     * Gets the value of the maarusedArv property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaarusedArv() {
        return maarusedArv;
    }

    /**
     * Sets the value of the maarusedArv property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaarusedArv(Integer value) {
        this.maarusedArv = value;
    }

}
