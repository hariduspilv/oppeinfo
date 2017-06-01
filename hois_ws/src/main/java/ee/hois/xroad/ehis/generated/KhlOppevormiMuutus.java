
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for khlOppevormiMuutus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="khlOppevormiMuutus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="muutusKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="klOppevorm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klOppekoormus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klOppekeel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klRahastAllikas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="esimSemestriLoppKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="lisainfo1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lisainfo2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lisainfo3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "khlOppevormiMuutus", propOrder = {
    "muutusKp",
    "klOppevorm",
    "klOppekoormus",
    "klOppekeel",
    "klRahastAllikas",
    "esimSemestriLoppKp",
    "lisainfo1",
    "lisainfo2",
    "lisainfo3"
})
public class KhlOppevormiMuutus {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar muutusKp;
    @XmlElement(required = true)
    protected String klOppevorm;
    protected String klOppekoormus;
    protected String klOppekeel;
    protected String klRahastAllikas;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar esimSemestriLoppKp;
    protected String lisainfo1;
    protected String lisainfo2;
    protected String lisainfo3;

    /**
     * Gets the value of the muutusKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMuutusKp() {
        return muutusKp;
    }

    /**
     * Sets the value of the muutusKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMuutusKp(XMLGregorianCalendar value) {
        this.muutusKp = value;
    }

    /**
     * Gets the value of the klOppevorm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlOppevorm() {
        return klOppevorm;
    }

    /**
     * Sets the value of the klOppevorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlOppevorm(String value) {
        this.klOppevorm = value;
    }

    /**
     * Gets the value of the klOppekoormus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlOppekoormus() {
        return klOppekoormus;
    }

    /**
     * Sets the value of the klOppekoormus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlOppekoormus(String value) {
        this.klOppekoormus = value;
    }

    /**
     * Gets the value of the klOppekeel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlOppekeel() {
        return klOppekeel;
    }

    /**
     * Sets the value of the klOppekeel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlOppekeel(String value) {
        this.klOppekeel = value;
    }

    /**
     * Gets the value of the klRahastAllikas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlRahastAllikas() {
        return klRahastAllikas;
    }

    /**
     * Sets the value of the klRahastAllikas property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlRahastAllikas(String value) {
        this.klRahastAllikas = value;
    }

    /**
     * Gets the value of the esimSemestriLoppKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEsimSemestriLoppKp() {
        return esimSemestriLoppKp;
    }

    /**
     * Sets the value of the esimSemestriLoppKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEsimSemestriLoppKp(XMLGregorianCalendar value) {
        this.esimSemestriLoppKp = value;
    }

    /**
     * Gets the value of the lisainfo1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLisainfo1() {
        return lisainfo1;
    }

    /**
     * Sets the value of the lisainfo1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLisainfo1(String value) {
        this.lisainfo1 = value;
    }

    /**
     * Gets the value of the lisainfo2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLisainfo2() {
        return lisainfo2;
    }

    /**
     * Sets the value of the lisainfo2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLisainfo2(String value) {
        this.lisainfo2 = value;
    }

    /**
     * Gets the value of the lisainfo3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLisainfo3() {
        return lisainfo3;
    }

    /**
     * Sets the value of the lisainfo3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLisainfo3(String value) {
        this.lisainfo3 = value;
    }

}
