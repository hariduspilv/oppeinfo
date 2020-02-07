
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tkV2Oppimine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tkV2Oppimine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oppeasutusNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeasutusRegKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekavaTase" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="algusKp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="loppKp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lahkumisePohjus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lopetamisePohjus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ennistamiseKp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppekavaKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppekavaNimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nomKestus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="akadPuhkus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tkV2AkadPuhkus" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="oppevormid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tkV2Oppevorm" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="oppekoormused" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tkV2Oppekoormus" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="finAllikad" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tkV2FinAllikas" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="oppekohtId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tkV2Oppimine", propOrder = {
    "oppeasutusNimetus",
    "oppeasutusRegKood",
    "oppekavaTase",
    "algusKp",
    "loppKp",
    "lahkumisePohjus",
    "lopetamisePohjus",
    "ennistamiseKp",
    "oppekavaKood",
    "oppekavaNimetus",
    "nomKestus",
    "akadPuhkus",
    "oppevormid",
    "oppekoormused",
    "finAllikad",
    "oppekohtId"
})
public class TkV2Oppimine {

    @XmlElement(required = true)
    protected String oppeasutusNimetus;
    @XmlElement(required = true)
    protected String oppeasutusRegKood;
    @XmlElement(required = true)
    protected String oppekavaTase;
    @XmlElement(required = true)
    protected String algusKp;
    protected String loppKp;
    protected String lahkumisePohjus;
    protected String lopetamisePohjus;
    protected String ennistamiseKp;
    protected String oppekavaKood;
    protected String oppekavaNimetus;
    protected String nomKestus;
    protected List<TkV2AkadPuhkus> akadPuhkus;
    protected List<TkV2Oppevorm> oppevormid;
    protected List<TkV2Oppekoormus> oppekoormused;
    protected List<TkV2FinAllikas> finAllikad;
    protected String oppekohtId;

    /**
     * Gets the value of the oppeasutusNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutusNimetus() {
        return oppeasutusNimetus;
    }

    /**
     * Sets the value of the oppeasutusNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutusNimetus(String value) {
        this.oppeasutusNimetus = value;
    }

    /**
     * Gets the value of the oppeasutusRegKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutusRegKood() {
        return oppeasutusRegKood;
    }

    /**
     * Sets the value of the oppeasutusRegKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutusRegKood(String value) {
        this.oppeasutusRegKood = value;
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
     * Gets the value of the algusKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlgusKp() {
        return algusKp;
    }

    /**
     * Sets the value of the algusKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlgusKp(String value) {
        this.algusKp = value;
    }

    /**
     * Gets the value of the loppKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoppKp() {
        return loppKp;
    }

    /**
     * Sets the value of the loppKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoppKp(String value) {
        this.loppKp = value;
    }

    /**
     * Gets the value of the lahkumisePohjus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLahkumisePohjus() {
        return lahkumisePohjus;
    }

    /**
     * Sets the value of the lahkumisePohjus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLahkumisePohjus(String value) {
        this.lahkumisePohjus = value;
    }

    /**
     * Gets the value of the lopetamisePohjus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLopetamisePohjus() {
        return lopetamisePohjus;
    }

    /**
     * Sets the value of the lopetamisePohjus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLopetamisePohjus(String value) {
        this.lopetamisePohjus = value;
    }

    /**
     * Gets the value of the ennistamiseKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnnistamiseKp() {
        return ennistamiseKp;
    }

    /**
     * Sets the value of the ennistamiseKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnnistamiseKp(String value) {
        this.ennistamiseKp = value;
    }

    /**
     * Gets the value of the oppekavaKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavaKood() {
        return oppekavaKood;
    }

    /**
     * Sets the value of the oppekavaKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavaKood(String value) {
        this.oppekavaKood = value;
    }

    /**
     * Gets the value of the oppekavaNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavaNimetus() {
        return oppekavaNimetus;
    }

    /**
     * Sets the value of the oppekavaNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavaNimetus(String value) {
        this.oppekavaNimetus = value;
    }

    /**
     * Gets the value of the nomKestus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomKestus() {
        return nomKestus;
    }

    /**
     * Sets the value of the nomKestus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomKestus(String value) {
        this.nomKestus = value;
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
     * {@link TkV2AkadPuhkus }
     * 
     * 
     */
    public List<TkV2AkadPuhkus> getAkadPuhkus() {
        if (akadPuhkus == null) {
            akadPuhkus = new ArrayList<TkV2AkadPuhkus>();
        }
        return this.akadPuhkus;
    }

    /**
     * Gets the value of the oppevormid property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oppevormid property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOppevormid().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TkV2Oppevorm }
     * 
     * 
     */
    public List<TkV2Oppevorm> getOppevormid() {
        if (oppevormid == null) {
            oppevormid = new ArrayList<TkV2Oppevorm>();
        }
        return this.oppevormid;
    }

    /**
     * Gets the value of the oppekoormused property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oppekoormused property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOppekoormused().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TkV2Oppekoormus }
     * 
     * 
     */
    public List<TkV2Oppekoormus> getOppekoormused() {
        if (oppekoormused == null) {
            oppekoormused = new ArrayList<TkV2Oppekoormus>();
        }
        return this.oppekoormused;
    }

    /**
     * Gets the value of the finAllikad property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the finAllikad property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFinAllikad().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TkV2FinAllikas }
     * 
     * 
     */
    public List<TkV2FinAllikas> getFinAllikad() {
        if (finAllikad == null) {
            finAllikad = new ArrayList<TkV2FinAllikas>();
        }
        return this.finAllikad;
    }

    /**
     * Gets the value of the oppekohtId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekohtId() {
        return oppekohtId;
    }

    /**
     * Sets the value of the oppekohtId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekohtId(String value) {
        this.oppekohtId = value;
    }

}
