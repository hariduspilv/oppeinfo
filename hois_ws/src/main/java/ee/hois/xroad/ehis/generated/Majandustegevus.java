
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for majandustegevus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="majandustegevus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="emtak" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
 *         &lt;element name="alates" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="kuni" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="valdkond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tegevusala" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="lisainfo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "majandustegevus", propOrder = {
    "id",
    "number",
    "emtak",
    "alates",
    "kuni",
    "liik",
    "valdkond",
    "tegevusala",
    "lisainfo",
    "url"
})
public class Majandustegevus {

    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    protected String number;
    @XmlElement(required = true)
    protected List<String> emtak;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar alates;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar kuni;
    @XmlElement(required = true)
    protected String liik;
    @XmlElement(required = true)
    protected String valdkond;
    @XmlElement(required = true)
    protected String tegevusala;
    @XmlElement(required = true)
    protected String lisainfo;
    @XmlElement(required = true)
    protected String url;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the number property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumber(String value) {
        this.number = value;
    }

    /**
     * Gets the value of the emtak property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the emtak property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEmtak().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getEmtak() {
        if (emtak == null) {
            emtak = new ArrayList<String>();
        }
        return this.emtak;
    }

    /**
     * Gets the value of the alates property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAlates() {
        return alates;
    }

    /**
     * Sets the value of the alates property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAlates(XMLGregorianCalendar value) {
        this.alates = value;
    }

    /**
     * Gets the value of the kuni property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getKuni() {
        return kuni;
    }

    /**
     * Sets the value of the kuni property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setKuni(XMLGregorianCalendar value) {
        this.kuni = value;
    }

    /**
     * Gets the value of the liik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLiik() {
        return liik;
    }

    /**
     * Sets the value of the liik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLiik(String value) {
        this.liik = value;
    }

    /**
     * Gets the value of the valdkond property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValdkond() {
        return valdkond;
    }

    /**
     * Sets the value of the valdkond property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValdkond(String value) {
        this.valdkond = value;
    }

    /**
     * Gets the value of the tegevusala property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTegevusala() {
        return tegevusala;
    }

    /**
     * Sets the value of the tegevusala property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTegevusala(String value) {
        this.tegevusala = value;
    }

    /**
     * Gets the value of the lisainfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLisainfo() {
        return lisainfo;
    }

    /**
     * Sets the value of the lisainfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLisainfo(String value) {
        this.lisainfo = value;
    }

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
    }

}
