
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for andmed complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="andmed"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isikukood" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisIsikukood" minOccurs="0"/&gt;
 *         &lt;element name="haridustase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="muudatuseKpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="oppekeel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="staatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutuseKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "andmed", propOrder = {
    "isikukood",
    "haridustase",
    "muudatuseKpv",
    "oppekeel",
    "staatus",
    "oppeasutus",
    "oppeasutuseKood"
})
public class Andmed {

    protected String isikukood;
    protected String haridustase;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar muudatuseKpv;
    protected String oppekeel;
    protected String staatus;
    protected String oppeasutus;
    protected String oppeasutuseKood;

    /**
     * Gets the value of the isikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukood() {
        return isikukood;
    }

    /**
     * Sets the value of the isikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukood(String value) {
        this.isikukood = value;
    }

    /**
     * Gets the value of the haridustase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHaridustase() {
        return haridustase;
    }

    /**
     * Sets the value of the haridustase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHaridustase(String value) {
        this.haridustase = value;
    }

    /**
     * Gets the value of the muudatuseKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMuudatuseKpv() {
        return muudatuseKpv;
    }

    /**
     * Sets the value of the muudatuseKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMuudatuseKpv(XMLGregorianCalendar value) {
        this.muudatuseKpv = value;
    }

    /**
     * Gets the value of the oppekeel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekeel() {
        return oppekeel;
    }

    /**
     * Sets the value of the oppekeel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekeel(String value) {
        this.oppekeel = value;
    }

    /**
     * Gets the value of the staatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStaatus() {
        return staatus;
    }

    /**
     * Sets the value of the staatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStaatus(String value) {
        this.staatus = value;
    }

    /**
     * Gets the value of the oppeasutus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutus() {
        return oppeasutus;
    }

    /**
     * Sets the value of the oppeasutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutus(String value) {
        this.oppeasutus = value;
    }

    /**
     * Gets the value of the oppeasutuseKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutuseKood() {
        return oppeasutuseKood;
    }

    /**
     * Sets the value of the oppeasutuseKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutuseKood(String value) {
        this.oppeasutuseKood = value;
    }

}
