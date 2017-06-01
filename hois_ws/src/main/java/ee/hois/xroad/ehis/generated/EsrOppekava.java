
package ee.hois.xroad.ehis.generated;

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
 * <p>Java class for esrOppekava complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="esrOppekava"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oppekavaKood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="valdkond" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="registrKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="sulgemiseKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="spordiala" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}esrSpordiala" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "esrOppekava", propOrder = {
    "oppekavaKood",
    "nimetus",
    "valdkond",
    "registrKp",
    "sulgemiseKp",
    "spordiala"
})
public class EsrOppekava {

    @XmlElement(required = true)
    protected BigInteger oppekavaKood;
    @XmlElement(required = true)
    protected String nimetus;
    protected String valdkond;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar registrKp;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar sulgemiseKp;
    protected List<EsrSpordiala> spordiala;

    /**
     * Gets the value of the oppekavaKood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOppekavaKood() {
        return oppekavaKood;
    }

    /**
     * Sets the value of the oppekavaKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOppekavaKood(BigInteger value) {
        this.oppekavaKood = value;
    }

    /**
     * Gets the value of the nimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNimetus() {
        return nimetus;
    }

    /**
     * Sets the value of the nimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNimetus(String value) {
        this.nimetus = value;
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
     * Gets the value of the registrKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRegistrKp() {
        return registrKp;
    }

    /**
     * Sets the value of the registrKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRegistrKp(XMLGregorianCalendar value) {
        this.registrKp = value;
    }

    /**
     * Gets the value of the sulgemiseKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSulgemiseKp() {
        return sulgemiseKp;
    }

    /**
     * Sets the value of the sulgemiseKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSulgemiseKp(XMLGregorianCalendar value) {
        this.sulgemiseKp = value;
    }

    /**
     * Gets the value of the spordiala property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the spordiala property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSpordiala().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EsrSpordiala }
     * 
     * 
     */
    public List<EsrSpordiala> getSpordiala() {
        if (spordiala == null) {
            spordiala = new ArrayList<EsrSpordiala>();
        }
        return this.spordiala;
    }

}
