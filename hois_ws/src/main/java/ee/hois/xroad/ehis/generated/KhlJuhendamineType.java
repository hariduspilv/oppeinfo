
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for khlJuhendamineType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="khlJuhendamineType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="juhendaja" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlOppejoudType" maxOccurs="100"/&gt;
 *         &lt;element name="oppekava" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/&gt;
 *         &lt;element name="loputooNimetusEesti" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="loputooNimetusInglise" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="loputooOriginaalkeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nimetusOriginaalkeeles" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kaitsmiseAasta" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisAastaType" minOccurs="0"/&gt;
 *         &lt;element name="kaitsmiseKoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="struktyksuseId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="struktyksuseNimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klTeaduseriala" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="100" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "khlJuhendamineType", propOrder = {
    "juhendaja",
    "oppekava",
    "loputooNimetusEesti",
    "loputooNimetusInglise",
    "loputooOriginaalkeel",
    "nimetusOriginaalkeeles",
    "kaitsmiseAasta",
    "kaitsmiseKoht",
    "struktyksuseId",
    "struktyksuseNimetus",
    "klTeaduseriala"
})
public class KhlJuhendamineType {

    @XmlElement(required = true)
    protected List<KhlOppejoudType> juhendaja;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger oppekava;
    @XmlElement(required = true)
    protected String loputooNimetusEesti;
    @XmlElement(required = true)
    protected String loputooNimetusInglise;
    @XmlElement(required = true)
    protected String loputooOriginaalkeel;
    protected String nimetusOriginaalkeeles;
    @XmlSchemaType(name = "positiveInteger")
    protected Integer kaitsmiseAasta;
    protected String kaitsmiseKoht;
    protected String struktyksuseId;
    protected String struktyksuseNimetus;
    protected List<String> klTeaduseriala;

    /**
     * Gets the value of the juhendaja property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the juhendaja property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getJuhendaja().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KhlOppejoudType }
     * 
     * 
     */
    public List<KhlOppejoudType> getJuhendaja() {
        if (juhendaja == null) {
            juhendaja = new ArrayList<KhlOppejoudType>();
        }
        return this.juhendaja;
    }

    /**
     * Gets the value of the oppekava property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOppekava() {
        return oppekava;
    }

    /**
     * Sets the value of the oppekava property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOppekava(BigInteger value) {
        this.oppekava = value;
    }

    /**
     * Gets the value of the loputooNimetusEesti property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoputooNimetusEesti() {
        return loputooNimetusEesti;
    }

    /**
     * Sets the value of the loputooNimetusEesti property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoputooNimetusEesti(String value) {
        this.loputooNimetusEesti = value;
    }

    /**
     * Gets the value of the loputooNimetusInglise property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoputooNimetusInglise() {
        return loputooNimetusInglise;
    }

    /**
     * Sets the value of the loputooNimetusInglise property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoputooNimetusInglise(String value) {
        this.loputooNimetusInglise = value;
    }

    /**
     * Gets the value of the loputooOriginaalkeel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoputooOriginaalkeel() {
        return loputooOriginaalkeel;
    }

    /**
     * Sets the value of the loputooOriginaalkeel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoputooOriginaalkeel(String value) {
        this.loputooOriginaalkeel = value;
    }

    /**
     * Gets the value of the nimetusOriginaalkeeles property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNimetusOriginaalkeeles() {
        return nimetusOriginaalkeeles;
    }

    /**
     * Sets the value of the nimetusOriginaalkeeles property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNimetusOriginaalkeeles(String value) {
        this.nimetusOriginaalkeeles = value;
    }

    /**
     * Gets the value of the kaitsmiseAasta property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getKaitsmiseAasta() {
        return kaitsmiseAasta;
    }

    /**
     * Sets the value of the kaitsmiseAasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setKaitsmiseAasta(Integer value) {
        this.kaitsmiseAasta = value;
    }

    /**
     * Gets the value of the kaitsmiseKoht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKaitsmiseKoht() {
        return kaitsmiseKoht;
    }

    /**
     * Sets the value of the kaitsmiseKoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKaitsmiseKoht(String value) {
        this.kaitsmiseKoht = value;
    }

    /**
     * Gets the value of the struktyksuseId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStruktyksuseId() {
        return struktyksuseId;
    }

    /**
     * Sets the value of the struktyksuseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStruktyksuseId(String value) {
        this.struktyksuseId = value;
    }

    /**
     * Gets the value of the struktyksuseNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStruktyksuseNimetus() {
        return struktyksuseNimetus;
    }

    /**
     * Sets the value of the struktyksuseNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStruktyksuseNimetus(String value) {
        this.struktyksuseNimetus = value;
    }

    /**
     * Gets the value of the klTeaduseriala property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the klTeaduseriala property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKlTeaduseriala().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getKlTeaduseriala() {
        if (klTeaduseriala == null) {
            klTeaduseriala = new ArrayList<String>();
        }
        return this.klTeaduseriala;
    }

}
