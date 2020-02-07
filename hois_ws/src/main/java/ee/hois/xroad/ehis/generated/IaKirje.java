
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for iaKirje complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="iaKirje"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kirjeLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klfKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="algusKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="loppKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutusNimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutusId" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="oppeained" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}iaOppeained" minOccurs="0"/&gt;
 *         &lt;element name="kommentaar" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "iaKirje", propOrder = {
    "kirjeLiik",
    "nimetus",
    "klfKood",
    "algusKp",
    "loppKp",
    "oppeasutusNimi",
    "oppeasutusId",
    "oppeained",
    "kommentaar"
})
public class IaKirje {

    @XmlElement(required = true)
    protected String kirjeLiik;
    protected String nimetus;
    protected String klfKood;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar algusKp;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar loppKp;
    protected String oppeasutusNimi;
    protected BigInteger oppeasutusId;
    protected IaOppeained oppeained;
    protected String kommentaar;

    /**
     * Gets the value of the kirjeLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKirjeLiik() {
        return kirjeLiik;
    }

    /**
     * Sets the value of the kirjeLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKirjeLiik(String value) {
        this.kirjeLiik = value;
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
     * Gets the value of the klfKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlfKood() {
        return klfKood;
    }

    /**
     * Sets the value of the klfKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlfKood(String value) {
        this.klfKood = value;
    }

    /**
     * Gets the value of the algusKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAlgusKp() {
        return algusKp;
    }

    /**
     * Sets the value of the algusKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAlgusKp(XMLGregorianCalendar value) {
        this.algusKp = value;
    }

    /**
     * Gets the value of the loppKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLoppKp() {
        return loppKp;
    }

    /**
     * Sets the value of the loppKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLoppKp(XMLGregorianCalendar value) {
        this.loppKp = value;
    }

    /**
     * Gets the value of the oppeasutusNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutusNimi() {
        return oppeasutusNimi;
    }

    /**
     * Sets the value of the oppeasutusNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutusNimi(String value) {
        this.oppeasutusNimi = value;
    }

    /**
     * Gets the value of the oppeasutusId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOppeasutusId() {
        return oppeasutusId;
    }

    /**
     * Sets the value of the oppeasutusId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOppeasutusId(BigInteger value) {
        this.oppeasutusId = value;
    }

    /**
     * Gets the value of the oppeained property.
     * 
     * @return
     *     possible object is
     *     {@link IaOppeained }
     *     
     */
    public IaOppeained getOppeained() {
        return oppeained;
    }

    /**
     * Sets the value of the oppeained property.
     * 
     * @param value
     *     allowed object is
     *     {@link IaOppeained }
     *     
     */
    public void setOppeained(IaOppeained value) {
        this.oppeained = value;
    }

    /**
     * Gets the value of the kommentaar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKommentaar() {
        return kommentaar;
    }

    /**
     * Sets the value of the kommentaar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKommentaar(String value) {
        this.kommentaar = value;
    }

}
