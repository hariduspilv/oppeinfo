
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
 * <p>Java class for pedagoogAmetikohtType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pedagoogAmetikohtType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="klAmetikoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klLepinguLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="lepingAlgKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="lepingLoppKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="onLopetatud" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
 *         &lt;element name="koormus" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="klOppekeel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="onLapsepuhkus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean"/&gt;
 *         &lt;element name="vastavusKval" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean"/&gt;
 *         &lt;element name="klassiJuhataja" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
 *         &lt;element name="aine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}pedagoogAine" maxOccurs="100" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pedagoogAmetikohtType", propOrder = {
    "klAmetikoht",
    "klLepinguLiik",
    "lepingAlgKp",
    "lepingLoppKp",
    "onLopetatud",
    "koormus",
    "klOppekeel",
    "onLapsepuhkus",
    "vastavusKval",
    "klassiJuhataja",
    "aine"
})
public class PedagoogAmetikohtType {

    @XmlElement(required = true)
    protected String klAmetikoht;
    @XmlElement(required = true)
    protected String klLepinguLiik;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lepingAlgKp;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lepingLoppKp;
    protected String onLopetatud;
    protected double koormus;
    protected String klOppekeel;
    @XmlElement(required = true)
    protected String onLapsepuhkus;
    @XmlElement(required = true)
    protected String vastavusKval;
    protected String klassiJuhataja;
    protected List<PedagoogAine> aine;

    /**
     * Gets the value of the klAmetikoht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlAmetikoht() {
        return klAmetikoht;
    }

    /**
     * Sets the value of the klAmetikoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlAmetikoht(String value) {
        this.klAmetikoht = value;
    }

    /**
     * Gets the value of the klLepinguLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlLepinguLiik() {
        return klLepinguLiik;
    }

    /**
     * Sets the value of the klLepinguLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlLepinguLiik(String value) {
        this.klLepinguLiik = value;
    }

    /**
     * Gets the value of the lepingAlgKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLepingAlgKp() {
        return lepingAlgKp;
    }

    /**
     * Sets the value of the lepingAlgKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLepingAlgKp(XMLGregorianCalendar value) {
        this.lepingAlgKp = value;
    }

    /**
     * Gets the value of the lepingLoppKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLepingLoppKp() {
        return lepingLoppKp;
    }

    /**
     * Sets the value of the lepingLoppKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLepingLoppKp(XMLGregorianCalendar value) {
        this.lepingLoppKp = value;
    }

    /**
     * Gets the value of the onLopetatud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnLopetatud() {
        return onLopetatud;
    }

    /**
     * Sets the value of the onLopetatud property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnLopetatud(String value) {
        this.onLopetatud = value;
    }

    /**
     * Gets the value of the koormus property.
     * 
     */
    public double getKoormus() {
        return koormus;
    }

    /**
     * Sets the value of the koormus property.
     * 
     */
    public void setKoormus(double value) {
        this.koormus = value;
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
     * Gets the value of the onLapsepuhkus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnLapsepuhkus() {
        return onLapsepuhkus;
    }

    /**
     * Sets the value of the onLapsepuhkus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnLapsepuhkus(String value) {
        this.onLapsepuhkus = value;
    }

    /**
     * Gets the value of the vastavusKval property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVastavusKval() {
        return vastavusKval;
    }

    /**
     * Sets the value of the vastavusKval property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVastavusKval(String value) {
        this.vastavusKval = value;
    }

    /**
     * Gets the value of the klassiJuhataja property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlassiJuhataja() {
        return klassiJuhataja;
    }

    /**
     * Sets the value of the klassiJuhataja property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlassiJuhataja(String value) {
        this.klassiJuhataja = value;
    }

    /**
     * Gets the value of the aine property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the aine property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAine().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PedagoogAine }
     * 
     * 
     */
    public List<PedagoogAine> getAine() {
        if (aine == null) {
            aine = new ArrayList<PedagoogAine>();
        }
        return this.aine;
    }

}
