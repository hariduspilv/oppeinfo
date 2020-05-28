
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for eeIsikukaartOping complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eeIsikukaartOping"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="haridustase" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeasutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppAlgus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppLopp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppekava" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}eeIsikukaartOppekava" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="oppekeel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="opeKlass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="opeParallel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klassiLiik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klassAste" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppevorm" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}isikukaartPeriodicalAndmed" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="koormus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}isikukaartPeriodicalAndmed" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="kestus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppekavataitmine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}eeIsikukaartOppekavaTaitmine" minOccurs="0"/&gt;
 *         &lt;element name="ryhmaLiik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="koht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="finAllikas" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}isikukaartPeriodicalAndmed" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="akadPuhkus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}isikukaartPeriodicalAndmed" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="ennistamine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}isikukaartPeriodicalAndmed" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="puudumised" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="staatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tunnistusDiplom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kutseKoolitus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}eeIsikukaartOpingKutseEelkoolitus" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eeIsikukaartOping", propOrder = {
    "id",
    "haridustase",
    "oppeasutus",
    "oppAlgus",
    "oppLopp",
    "oppekava",
    "oppekeel",
    "opeKlass",
    "opeParallel",
    "klassiLiik",
    "klassAste",
    "oppevorm",
    "koormus",
    "kestus",
    "oppekavataitmine",
    "ryhmaLiik",
    "nimetus",
    "koht",
    "finAllikas",
    "akadPuhkus",
    "ennistamine",
    "puudumised",
    "staatus",
    "tunnistusDiplom",
    "kutseKoolitus"
})
public class EeIsikukaartOping {

    protected String id;
    @XmlElement(required = true)
    protected String haridustase;
    @XmlElement(required = true)
    protected String oppeasutus;
    protected String oppAlgus;
    protected String oppLopp;
    protected List<EeIsikukaartOppekava> oppekava;
    protected String oppekeel;
    protected String opeKlass;
    protected String opeParallel;
    protected String klassiLiik;
    protected String klassAste;
    protected List<IsikukaartPeriodicalAndmed> oppevorm;
    protected List<IsikukaartPeriodicalAndmed> koormus;
    protected String kestus;
    protected EeIsikukaartOppekavaTaitmine oppekavataitmine;
    protected String ryhmaLiik;
    protected String nimetus;
    protected String koht;
    protected List<IsikukaartPeriodicalAndmed> finAllikas;
    protected List<IsikukaartPeriodicalAndmed> akadPuhkus;
    protected List<IsikukaartPeriodicalAndmed> ennistamine;
    protected String puudumised;
    protected String staatus;
    protected String tunnistusDiplom;
    protected List<EeIsikukaartOpingKutseEelkoolitus> kutseKoolitus;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
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
     * Gets the value of the oppAlgus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppAlgus() {
        return oppAlgus;
    }

    /**
     * Sets the value of the oppAlgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppAlgus(String value) {
        this.oppAlgus = value;
    }

    /**
     * Gets the value of the oppLopp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppLopp() {
        return oppLopp;
    }

    /**
     * Sets the value of the oppLopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppLopp(String value) {
        this.oppLopp = value;
    }

    /**
     * Gets the value of the oppekava property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oppekava property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOppekava().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EeIsikukaartOppekava }
     * 
     * 
     */
    public List<EeIsikukaartOppekava> getOppekava() {
        if (oppekava == null) {
            oppekava = new ArrayList<EeIsikukaartOppekava>();
        }
        return this.oppekava;
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
     * Gets the value of the opeKlass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOpeKlass() {
        return opeKlass;
    }

    /**
     * Sets the value of the opeKlass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOpeKlass(String value) {
        this.opeKlass = value;
    }

    /**
     * Gets the value of the opeParallel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOpeParallel() {
        return opeParallel;
    }

    /**
     * Sets the value of the opeParallel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOpeParallel(String value) {
        this.opeParallel = value;
    }

    /**
     * Gets the value of the klassiLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlassiLiik() {
        return klassiLiik;
    }

    /**
     * Sets the value of the klassiLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlassiLiik(String value) {
        this.klassiLiik = value;
    }

    /**
     * Gets the value of the klassAste property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlassAste() {
        return klassAste;
    }

    /**
     * Sets the value of the klassAste property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlassAste(String value) {
        this.klassAste = value;
    }

    /**
     * Gets the value of the oppevorm property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oppevorm property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOppevorm().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IsikukaartPeriodicalAndmed }
     * 
     * 
     */
    public List<IsikukaartPeriodicalAndmed> getOppevorm() {
        if (oppevorm == null) {
            oppevorm = new ArrayList<IsikukaartPeriodicalAndmed>();
        }
        return this.oppevorm;
    }

    /**
     * Gets the value of the koormus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the koormus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKoormus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IsikukaartPeriodicalAndmed }
     * 
     * 
     */
    public List<IsikukaartPeriodicalAndmed> getKoormus() {
        if (koormus == null) {
            koormus = new ArrayList<IsikukaartPeriodicalAndmed>();
        }
        return this.koormus;
    }

    /**
     * Gets the value of the kestus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKestus() {
        return kestus;
    }

    /**
     * Sets the value of the kestus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKestus(String value) {
        this.kestus = value;
    }

    /**
     * Gets the value of the oppekavataitmine property.
     * 
     * @return
     *     possible object is
     *     {@link EeIsikukaartOppekavaTaitmine }
     *     
     */
    public EeIsikukaartOppekavaTaitmine getOppekavataitmine() {
        return oppekavataitmine;
    }

    /**
     * Sets the value of the oppekavataitmine property.
     * 
     * @param value
     *     allowed object is
     *     {@link EeIsikukaartOppekavaTaitmine }
     *     
     */
    public void setOppekavataitmine(EeIsikukaartOppekavaTaitmine value) {
        this.oppekavataitmine = value;
    }

    /**
     * Gets the value of the ryhmaLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRyhmaLiik() {
        return ryhmaLiik;
    }

    /**
     * Sets the value of the ryhmaLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRyhmaLiik(String value) {
        this.ryhmaLiik = value;
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
     * Gets the value of the koht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKoht() {
        return koht;
    }

    /**
     * Sets the value of the koht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKoht(String value) {
        this.koht = value;
    }

    /**
     * Gets the value of the finAllikas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the finAllikas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFinAllikas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IsikukaartPeriodicalAndmed }
     * 
     * 
     */
    public List<IsikukaartPeriodicalAndmed> getFinAllikas() {
        if (finAllikas == null) {
            finAllikas = new ArrayList<IsikukaartPeriodicalAndmed>();
        }
        return this.finAllikas;
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
     * {@link IsikukaartPeriodicalAndmed }
     * 
     * 
     */
    public List<IsikukaartPeriodicalAndmed> getAkadPuhkus() {
        if (akadPuhkus == null) {
            akadPuhkus = new ArrayList<IsikukaartPeriodicalAndmed>();
        }
        return this.akadPuhkus;
    }

    /**
     * Gets the value of the ennistamine property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ennistamine property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEnnistamine().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IsikukaartPeriodicalAndmed }
     * 
     * 
     */
    public List<IsikukaartPeriodicalAndmed> getEnnistamine() {
        if (ennistamine == null) {
            ennistamine = new ArrayList<IsikukaartPeriodicalAndmed>();
        }
        return this.ennistamine;
    }

    /**
     * Gets the value of the puudumised property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPuudumised() {
        return puudumised;
    }

    /**
     * Sets the value of the puudumised property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPuudumised(String value) {
        this.puudumised = value;
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
     * Gets the value of the tunnistusDiplom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTunnistusDiplom() {
        return tunnistusDiplom;
    }

    /**
     * Sets the value of the tunnistusDiplom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTunnistusDiplom(String value) {
        this.tunnistusDiplom = value;
    }

    /**
     * Gets the value of the kutseKoolitus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the kutseKoolitus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKutseKoolitus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EeIsikukaartOpingKutseEelkoolitus }
     * 
     * 
     */
    public List<EeIsikukaartOpingKutseEelkoolitus> getKutseKoolitus() {
        if (kutseKoolitus == null) {
            kutseKoolitus = new ArrayList<EeIsikukaartOpingKutseEelkoolitus>();
        }
        return this.kutseKoolitus;
    }

}
