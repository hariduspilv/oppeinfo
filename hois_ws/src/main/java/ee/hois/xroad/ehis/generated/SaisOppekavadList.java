
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for saisOppekavadList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saisOppekavadList"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oppekavaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekavaNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekavaNimetusEng" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekavataseNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekavataseId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="valdkond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeasutusNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeasutusId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekeeled"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="oppekeel" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oppekeel" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="nominaalkestusAasta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nominaalkestusKuud" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekavaRyhmKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekavaRyhmNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppevaldkondKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppevaldkondNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekavagruppKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekavagruppNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="yhisoppekavad"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="yhisoppekava" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhisope" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="oppekavaStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekavaStaatusId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekavaStaatusMaaramineKp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekavaVastuvotuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekavaVastuvotuStaatusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saisOppekavadList", propOrder = {
    "oppekavaKood",
    "oppekavaNimetus",
    "oppekavaNimetusEng",
    "oppekavataseNimetus",
    "oppekavataseId",
    "valdkond",
    "oppeasutusNimetus",
    "oppeasutusId",
    "oppekeeled",
    "nominaalkestusAasta",
    "nominaalkestusKuud",
    "oppekavaRyhmKood",
    "oppekavaRyhmNimetus",
    "oppevaldkondKood",
    "oppevaldkondNimetus",
    "oppekavagruppKood",
    "oppekavagruppNimetus",
    "yhisoppekavad",
    "oppekavaStaatus",
    "oppekavaStaatusId",
    "oppekavaStaatusMaaramineKp",
    "oppekavaVastuvotuStaatus",
    "oppekavaVastuvotuStaatusKood"
})
public class SaisOppekavadList {

    @XmlElement(required = true, nillable = true)
    protected String oppekavaKood;
    @XmlElement(required = true, nillable = true)
    protected String oppekavaNimetus;
    @XmlElement(required = true, nillable = true)
    protected String oppekavaNimetusEng;
    @XmlElement(required = true, nillable = true)
    protected String oppekavataseNimetus;
    @XmlElement(required = true, nillable = true)
    protected String oppekavataseId;
    @XmlElement(required = true, nillable = true)
    protected String valdkond;
    @XmlElement(required = true, nillable = true)
    protected String oppeasutusNimetus;
    @XmlElement(required = true, nillable = true)
    protected String oppeasutusId;
    @XmlElement(required = true)
    protected SaisOppekavadList.Oppekeeled oppekeeled;
    @XmlElement(required = true, nillable = true)
    protected String nominaalkestusAasta;
    @XmlElement(required = true, nillable = true)
    protected String nominaalkestusKuud;
    @XmlElement(required = true, nillable = true)
    protected String oppekavaRyhmKood;
    @XmlElement(required = true, nillable = true)
    protected String oppekavaRyhmNimetus;
    @XmlElement(required = true, nillable = true)
    protected String oppevaldkondKood;
    @XmlElement(required = true, nillable = true)
    protected String oppevaldkondNimetus;
    @XmlElement(required = true, nillable = true)
    protected String oppekavagruppKood;
    @XmlElement(required = true, nillable = true)
    protected String oppekavagruppNimetus;
    @XmlElement(required = true)
    protected SaisOppekavadList.Yhisoppekavad yhisoppekavad;
    @XmlElement(required = true, nillable = true)
    protected String oppekavaStaatus;
    @XmlElement(required = true, nillable = true)
    protected String oppekavaStaatusId;
    @XmlElement(required = true, nillable = true)
    protected String oppekavaStaatusMaaramineKp;
    @XmlElement(required = true, nillable = true)
    protected String oppekavaVastuvotuStaatus;
    @XmlElement(required = true, nillable = true)
    protected String oppekavaVastuvotuStaatusKood;

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
     * Gets the value of the oppekavaNimetusEng property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavaNimetusEng() {
        return oppekavaNimetusEng;
    }

    /**
     * Sets the value of the oppekavaNimetusEng property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavaNimetusEng(String value) {
        this.oppekavaNimetusEng = value;
    }

    /**
     * Gets the value of the oppekavataseNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavataseNimetus() {
        return oppekavataseNimetus;
    }

    /**
     * Sets the value of the oppekavataseNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavataseNimetus(String value) {
        this.oppekavataseNimetus = value;
    }

    /**
     * Gets the value of the oppekavataseId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavataseId() {
        return oppekavataseId;
    }

    /**
     * Sets the value of the oppekavataseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavataseId(String value) {
        this.oppekavataseId = value;
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
     * Gets the value of the oppeasutusId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutusId() {
        return oppeasutusId;
    }

    /**
     * Sets the value of the oppeasutusId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutusId(String value) {
        this.oppeasutusId = value;
    }

    /**
     * Gets the value of the oppekeeled property.
     * 
     * @return
     *     possible object is
     *     {@link SaisOppekavadList.Oppekeeled }
     *     
     */
    public SaisOppekavadList.Oppekeeled getOppekeeled() {
        return oppekeeled;
    }

    /**
     * Sets the value of the oppekeeled property.
     * 
     * @param value
     *     allowed object is
     *     {@link SaisOppekavadList.Oppekeeled }
     *     
     */
    public void setOppekeeled(SaisOppekavadList.Oppekeeled value) {
        this.oppekeeled = value;
    }

    /**
     * Gets the value of the nominaalkestusAasta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNominaalkestusAasta() {
        return nominaalkestusAasta;
    }

    /**
     * Sets the value of the nominaalkestusAasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNominaalkestusAasta(String value) {
        this.nominaalkestusAasta = value;
    }

    /**
     * Gets the value of the nominaalkestusKuud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNominaalkestusKuud() {
        return nominaalkestusKuud;
    }

    /**
     * Sets the value of the nominaalkestusKuud property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNominaalkestusKuud(String value) {
        this.nominaalkestusKuud = value;
    }

    /**
     * Gets the value of the oppekavaRyhmKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavaRyhmKood() {
        return oppekavaRyhmKood;
    }

    /**
     * Sets the value of the oppekavaRyhmKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavaRyhmKood(String value) {
        this.oppekavaRyhmKood = value;
    }

    /**
     * Gets the value of the oppekavaRyhmNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavaRyhmNimetus() {
        return oppekavaRyhmNimetus;
    }

    /**
     * Sets the value of the oppekavaRyhmNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavaRyhmNimetus(String value) {
        this.oppekavaRyhmNimetus = value;
    }

    /**
     * Gets the value of the oppevaldkondKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppevaldkondKood() {
        return oppevaldkondKood;
    }

    /**
     * Sets the value of the oppevaldkondKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppevaldkondKood(String value) {
        this.oppevaldkondKood = value;
    }

    /**
     * Gets the value of the oppevaldkondNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppevaldkondNimetus() {
        return oppevaldkondNimetus;
    }

    /**
     * Sets the value of the oppevaldkondNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppevaldkondNimetus(String value) {
        this.oppevaldkondNimetus = value;
    }

    /**
     * Gets the value of the oppekavagruppKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavagruppKood() {
        return oppekavagruppKood;
    }

    /**
     * Sets the value of the oppekavagruppKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavagruppKood(String value) {
        this.oppekavagruppKood = value;
    }

    /**
     * Gets the value of the oppekavagruppNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavagruppNimetus() {
        return oppekavagruppNimetus;
    }

    /**
     * Sets the value of the oppekavagruppNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavagruppNimetus(String value) {
        this.oppekavagruppNimetus = value;
    }

    /**
     * Gets the value of the yhisoppekavad property.
     * 
     * @return
     *     possible object is
     *     {@link SaisOppekavadList.Yhisoppekavad }
     *     
     */
    public SaisOppekavadList.Yhisoppekavad getYhisoppekavad() {
        return yhisoppekavad;
    }

    /**
     * Sets the value of the yhisoppekavad property.
     * 
     * @param value
     *     allowed object is
     *     {@link SaisOppekavadList.Yhisoppekavad }
     *     
     */
    public void setYhisoppekavad(SaisOppekavadList.Yhisoppekavad value) {
        this.yhisoppekavad = value;
    }

    /**
     * Gets the value of the oppekavaStaatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavaStaatus() {
        return oppekavaStaatus;
    }

    /**
     * Sets the value of the oppekavaStaatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavaStaatus(String value) {
        this.oppekavaStaatus = value;
    }

    /**
     * Gets the value of the oppekavaStaatusId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavaStaatusId() {
        return oppekavaStaatusId;
    }

    /**
     * Sets the value of the oppekavaStaatusId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavaStaatusId(String value) {
        this.oppekavaStaatusId = value;
    }

    /**
     * Gets the value of the oppekavaStaatusMaaramineKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavaStaatusMaaramineKp() {
        return oppekavaStaatusMaaramineKp;
    }

    /**
     * Sets the value of the oppekavaStaatusMaaramineKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavaStaatusMaaramineKp(String value) {
        this.oppekavaStaatusMaaramineKp = value;
    }

    /**
     * Gets the value of the oppekavaVastuvotuStaatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavaVastuvotuStaatus() {
        return oppekavaVastuvotuStaatus;
    }

    /**
     * Sets the value of the oppekavaVastuvotuStaatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavaVastuvotuStaatus(String value) {
        this.oppekavaVastuvotuStaatus = value;
    }

    /**
     * Gets the value of the oppekavaVastuvotuStaatusKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavaVastuvotuStaatusKood() {
        return oppekavaVastuvotuStaatusKood;
    }

    /**
     * Sets the value of the oppekavaVastuvotuStaatusKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavaVastuvotuStaatusKood(String value) {
        this.oppekavaVastuvotuStaatusKood = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="oppekeel" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oppekeel" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "oppekeel"
    })
    public static class Oppekeeled {

        @XmlElement(nillable = true)
        protected List<Oppekeel> oppekeel;

        /**
         * Gets the value of the oppekeel property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the oppekeel property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOppekeel().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Oppekeel }
         * 
         * 
         */
        public List<Oppekeel> getOppekeel() {
            if (oppekeel == null) {
                oppekeel = new ArrayList<Oppekeel>();
            }
            return this.oppekeel;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="yhisoppekava" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhisope" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "yhisoppekava"
    })
    public static class Yhisoppekavad {

        @XmlElement(nillable = true)
        protected List<Yhisope> yhisoppekava;

        /**
         * Gets the value of the yhisoppekava property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the yhisoppekava property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getYhisoppekava().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Yhisope }
         * 
         * 
         */
        public List<Yhisope> getYhisoppekava() {
            if (yhisoppekava == null) {
                yhisoppekava = new ArrayList<Yhisope>();
            }
            return this.yhisoppekava;
        }

    }

}
