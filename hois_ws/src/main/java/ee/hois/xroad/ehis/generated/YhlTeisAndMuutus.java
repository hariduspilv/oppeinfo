
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for yhlTeisAndMuutus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="yhlTeisAndMuutus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="muutusKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="kaugusOppeasutusest" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutusFil" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="opilaskoduKool" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="klOpilaskoduFin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pikapaevaruhm" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
 *         &lt;element name="pohjusetaPuudumine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlPohtaPuudumine" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="lisaandmed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlLisaandmed" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "yhlTeisAndMuutus", propOrder = {
    "muutusKp",
    "kaugusOppeasutusest",
    "oppeasutusFil",
    "opilaskoduKool",
    "klOpilaskoduFin",
    "pikapaevaruhm",
    "pohjusetaPuudumine",
    "lisaandmed"
})
public class YhlTeisAndMuutus {

    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar muutusKp;
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger kaugusOppeasutusest;
    protected BigInteger oppeasutusFil;
    protected BigInteger opilaskoduKool;
    protected String klOpilaskoduFin;
    protected String pikapaevaruhm;
    protected List<YhlPohtaPuudumine> pohjusetaPuudumine;
    protected YhlLisaandmed lisaandmed;

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
     * Gets the value of the kaugusOppeasutusest property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKaugusOppeasutusest() {
        return kaugusOppeasutusest;
    }

    /**
     * Sets the value of the kaugusOppeasutusest property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKaugusOppeasutusest(BigInteger value) {
        this.kaugusOppeasutusest = value;
    }

    /**
     * Gets the value of the oppeasutusFil property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOppeasutusFil() {
        return oppeasutusFil;
    }

    /**
     * Sets the value of the oppeasutusFil property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOppeasutusFil(BigInteger value) {
        this.oppeasutusFil = value;
    }

    /**
     * Gets the value of the opilaskoduKool property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOpilaskoduKool() {
        return opilaskoduKool;
    }

    /**
     * Sets the value of the opilaskoduKool property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOpilaskoduKool(BigInteger value) {
        this.opilaskoduKool = value;
    }

    /**
     * Gets the value of the klOpilaskoduFin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlOpilaskoduFin() {
        return klOpilaskoduFin;
    }

    /**
     * Sets the value of the klOpilaskoduFin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlOpilaskoduFin(String value) {
        this.klOpilaskoduFin = value;
    }

    /**
     * Gets the value of the pikapaevaruhm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPikapaevaruhm() {
        return pikapaevaruhm;
    }

    /**
     * Sets the value of the pikapaevaruhm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPikapaevaruhm(String value) {
        this.pikapaevaruhm = value;
    }

    /**
     * Gets the value of the pohjusetaPuudumine property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pohjusetaPuudumine property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPohjusetaPuudumine().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link YhlPohtaPuudumine }
     * 
     * 
     */
    public List<YhlPohtaPuudumine> getPohjusetaPuudumine() {
        if (pohjusetaPuudumine == null) {
            pohjusetaPuudumine = new ArrayList<YhlPohtaPuudumine>();
        }
        return this.pohjusetaPuudumine;
    }

    /**
     * Gets the value of the lisaandmed property.
     * 
     * @return
     *     possible object is
     *     {@link YhlLisaandmed }
     *     
     */
    public YhlLisaandmed getLisaandmed() {
        return lisaandmed;
    }

    /**
     * Sets the value of the lisaandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlLisaandmed }
     *     
     */
    public void setLisaandmed(YhlLisaandmed value) {
        this.lisaandmed = value;
    }

}
