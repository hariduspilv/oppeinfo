
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
 * <p>Java class for koolOppimine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="koolOppimine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oasRegKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oasNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="algusKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="loppKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="oppimiseStaatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppekavaTase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppekavaTaseTekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="akadPuhkus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}koolAkadPuhkus" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="esf" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}kasutatudEsfRahastus" maxOccurs="10" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "koolOppimine", propOrder = {
    "oasRegKood",
    "oasNimetus",
    "oppekoht",
    "algusKp",
    "loppKp",
    "oppimiseStaatus",
    "oppekavaTase",
    "oppekavaTaseTekst",
    "akadPuhkus",
    "esf"
})
public class KoolOppimine {

    @XmlElement(required = true)
    protected String oasRegKood;
    @XmlElement(required = true)
    protected String oasNimetus;
    @XmlElement(required = true)
    protected String oppekoht;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar algusKp;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar loppKp;
    protected String oppimiseStaatus;
    protected String oppekavaTase;
    protected String oppekavaTaseTekst;
    protected List<KoolAkadPuhkus> akadPuhkus;
    protected List<KasutatudEsfRahastus> esf;

    /**
     * Gets the value of the oasRegKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOasRegKood() {
        return oasRegKood;
    }

    /**
     * Sets the value of the oasRegKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOasRegKood(String value) {
        this.oasRegKood = value;
    }

    /**
     * Gets the value of the oasNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOasNimetus() {
        return oasNimetus;
    }

    /**
     * Sets the value of the oasNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOasNimetus(String value) {
        this.oasNimetus = value;
    }

    /**
     * Gets the value of the oppekoht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekoht() {
        return oppekoht;
    }

    /**
     * Sets the value of the oppekoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekoht(String value) {
        this.oppekoht = value;
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
     * Gets the value of the oppimiseStaatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppimiseStaatus() {
        return oppimiseStaatus;
    }

    /**
     * Sets the value of the oppimiseStaatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppimiseStaatus(String value) {
        this.oppimiseStaatus = value;
    }

    /**
     * Gets the value of the oppekavaTase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavaTase() {
        return oppekavaTase;
    }

    /**
     * Sets the value of the oppekavaTase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavaTase(String value) {
        this.oppekavaTase = value;
    }

    /**
     * Gets the value of the oppekavaTaseTekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavaTaseTekst() {
        return oppekavaTaseTekst;
    }

    /**
     * Sets the value of the oppekavaTaseTekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavaTaseTekst(String value) {
        this.oppekavaTaseTekst = value;
    }

    /**
     * Gets the value of the akadPuhkus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the akadPuhkus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAkadPuhkus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KoolAkadPuhkus }
     * 
     * 
     */
    public List<KoolAkadPuhkus> getAkadPuhkus() {
        if (akadPuhkus == null) {
            akadPuhkus = new ArrayList<KoolAkadPuhkus>();
        }
        return this.akadPuhkus;
    }

    /**
     * Gets the value of the esf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the esf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEsf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KasutatudEsfRahastus }
     * 
     * 
     */
    public List<KasutatudEsfRahastus> getEsf() {
        if (esf == null) {
            esf = new ArrayList<KasutatudEsfRahastus>();
        }
        return this.esf;
    }

}
