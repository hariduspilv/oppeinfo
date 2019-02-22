
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
 * <p>Java class for majandusaasta_aruannete_kirjed_vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="majandusaasta_aruannete_kirjed_vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="aruande_liik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aruande_nimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="majandusaasta_algus" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="majandusaasta_lopp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="majandusaasta_aruanded_read" type="{http://arireg.x-road.eu/producer/}majandusaasta_aruanded_rida" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "majandusaasta_aruannete_kirjed_vastus", propOrder = {
    "aruandeLiik",
    "aruandeNimetus",
    "majandusaastaAlgus",
    "majandusaastaLopp",
    "majandusaastaAruandedRead"
})
public class MajandusaastaAruanneteKirjedVastus {

    @XmlElement(name = "aruande_liik")
    protected String aruandeLiik;
    @XmlElement(name = "aruande_nimetus")
    protected String aruandeNimetus;
    @XmlElement(name = "majandusaasta_algus")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar majandusaastaAlgus;
    @XmlElement(name = "majandusaasta_lopp")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar majandusaastaLopp;
    @XmlElement(name = "majandusaasta_aruanded_read")
    protected List<MajandusaastaAruandedRida> majandusaastaAruandedRead;

    /**
     * Gets the value of the aruandeLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAruandeLiik() {
        return aruandeLiik;
    }

    /**
     * Sets the value of the aruandeLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAruandeLiik(String value) {
        this.aruandeLiik = value;
    }

    /**
     * Gets the value of the aruandeNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAruandeNimetus() {
        return aruandeNimetus;
    }

    /**
     * Sets the value of the aruandeNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAruandeNimetus(String value) {
        this.aruandeNimetus = value;
    }

    /**
     * Gets the value of the majandusaastaAlgus property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMajandusaastaAlgus() {
        return majandusaastaAlgus;
    }

    /**
     * Sets the value of the majandusaastaAlgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMajandusaastaAlgus(XMLGregorianCalendar value) {
        this.majandusaastaAlgus = value;
    }

    /**
     * Gets the value of the majandusaastaLopp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMajandusaastaLopp() {
        return majandusaastaLopp;
    }

    /**
     * Sets the value of the majandusaastaLopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMajandusaastaLopp(XMLGregorianCalendar value) {
        this.majandusaastaLopp = value;
    }

    /**
     * Gets the value of the majandusaastaAruandedRead property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the majandusaastaAruandedRead property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMajandusaastaAruandedRead().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MajandusaastaAruandedRida }
     * 
     * 
     */
    public List<MajandusaastaAruandedRida> getMajandusaastaAruandedRead() {
        if (majandusaastaAruandedRead == null) {
            majandusaastaAruandedRead = new ArrayList<MajandusaastaAruandedRida>();
        }
        return this.majandusaastaAruandedRead;
    }

}
