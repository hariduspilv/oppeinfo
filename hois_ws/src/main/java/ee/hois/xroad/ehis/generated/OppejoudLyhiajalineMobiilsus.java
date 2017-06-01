
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for oppejoudLyhiajalineMobiilsus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oppejoudLyhiajalineMobiilsus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="lyhMobKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="perioodiAlgus" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="perioodiLopp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="klEesmark" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="sihtoppeasutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klSihtriik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kustutatud" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oppejoudLyhiajalineMobiilsus", propOrder = {
    "lyhMobKood",
    "perioodiAlgus",
    "perioodiLopp",
    "klEesmark",
    "sihtoppeasutus",
    "klSihtriik",
    "kustutatud"
})
public class OppejoudLyhiajalineMobiilsus {

    @XmlElementRef(name = "lyhMobKood", type = JAXBElement.class, required = false)
    protected JAXBElement<String> lyhMobKood;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar perioodiAlgus;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar perioodiLopp;
    @XmlElement(required = true)
    protected String klEesmark;
    @XmlElement(required = true)
    protected String sihtoppeasutus;
    @XmlElement(required = true)
    protected String klSihtriik;
    @XmlElementRef(name = "kustutatud", type = JAXBElement.class, required = false)
    protected JAXBElement<String> kustutatud;

    /**
     * Gets the value of the lyhMobKood property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getLyhMobKood() {
        return lyhMobKood;
    }

    /**
     * Sets the value of the lyhMobKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLyhMobKood(JAXBElement<String> value) {
        this.lyhMobKood = value;
    }

    /**
     * Gets the value of the perioodiAlgus property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPerioodiAlgus() {
        return perioodiAlgus;
    }

    /**
     * Sets the value of the perioodiAlgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPerioodiAlgus(XMLGregorianCalendar value) {
        this.perioodiAlgus = value;
    }

    /**
     * Gets the value of the perioodiLopp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPerioodiLopp() {
        return perioodiLopp;
    }

    /**
     * Sets the value of the perioodiLopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPerioodiLopp(XMLGregorianCalendar value) {
        this.perioodiLopp = value;
    }

    /**
     * Gets the value of the klEesmark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlEesmark() {
        return klEesmark;
    }

    /**
     * Sets the value of the klEesmark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlEesmark(String value) {
        this.klEesmark = value;
    }

    /**
     * Gets the value of the sihtoppeasutus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSihtoppeasutus() {
        return sihtoppeasutus;
    }

    /**
     * Sets the value of the sihtoppeasutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSihtoppeasutus(String value) {
        this.sihtoppeasutus = value;
    }

    /**
     * Gets the value of the klSihtriik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlSihtriik() {
        return klSihtriik;
    }

    /**
     * Sets the value of the klSihtriik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlSihtriik(String value) {
        this.klSihtriik = value;
    }

    /**
     * Gets the value of the kustutatud property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getKustutatud() {
        return kustutatud;
    }

    /**
     * Sets the value of the kustutatud property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setKustutatud(JAXBElement<String> value) {
        this.kustutatud = value;
    }

}
