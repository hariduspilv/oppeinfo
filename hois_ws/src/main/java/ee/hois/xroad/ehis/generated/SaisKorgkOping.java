
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for saisKorgkOping complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saisKorgkOping"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oppeasutuseKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeasutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppimaAsumiseKuupaev" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="lopetamiseKuupaev" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="tase" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="erialaKood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="eriala" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="keeleKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="staatus" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="finantseerimiseAllikas" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppevorm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="koolId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="nomA" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="diplom" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cumlaude" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="tasutaPaevad" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="nominaal" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}saisKorkNominaal"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saisKorgkOping", propOrder = {
    "oppeasutuseKood",
    "oppeasutuseNimi",
    "oppimaAsumiseKuupaev",
    "lopetamiseKuupaev",
    "tase",
    "erialaKood",
    "eriala",
    "keeleKood",
    "staatus",
    "finantseerimiseAllikas",
    "oppevorm",
    "koolId",
    "nomA",
    "diplom",
    "cumlaude",
    "tasutaPaevad",
    "nominaal"
})
public class SaisKorgkOping {

    @XmlElement(required = true, nillable = true)
    protected String oppeasutuseKood;
    @XmlElement(required = true, nillable = true)
    protected String oppeasutuseNimi;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar oppimaAsumiseKuupaev;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lopetamiseKuupaev;
    @XmlElement(required = true, nillable = true)
    protected String tase;
    @XmlElement(required = true, nillable = true)
    protected BigInteger erialaKood;
    @XmlElement(required = true, nillable = true)
    protected String eriala;
    @XmlElement(required = true, nillable = true)
    protected String keeleKood;
    @XmlElement(required = true, nillable = true)
    protected BigInteger staatus;
    @XmlElement(required = true, nillable = true)
    protected String finantseerimiseAllikas;
    @XmlElement(required = true, nillable = true)
    protected String oppevorm;
    @XmlElement(required = true, nillable = true)
    protected BigInteger koolId;
    @XmlElement(required = true, nillable = true)
    protected String nomA;
    @XmlElement(required = true, nillable = true)
    protected String diplom;
    @XmlElement(required = true, nillable = true)
    protected BigInteger cumlaude;
    @XmlElement(required = true, nillable = true)
    protected BigInteger tasutaPaevad;
    @XmlElement(required = true, nillable = true)
    protected SaisKorkNominaal nominaal;

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

    /**
     * Gets the value of the oppeasutuseNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutuseNimi() {
        return oppeasutuseNimi;
    }

    /**
     * Sets the value of the oppeasutuseNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutuseNimi(String value) {
        this.oppeasutuseNimi = value;
    }

    /**
     * Gets the value of the oppimaAsumiseKuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOppimaAsumiseKuupaev() {
        return oppimaAsumiseKuupaev;
    }

    /**
     * Sets the value of the oppimaAsumiseKuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOppimaAsumiseKuupaev(XMLGregorianCalendar value) {
        this.oppimaAsumiseKuupaev = value;
    }

    /**
     * Gets the value of the lopetamiseKuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLopetamiseKuupaev() {
        return lopetamiseKuupaev;
    }

    /**
     * Sets the value of the lopetamiseKuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLopetamiseKuupaev(XMLGregorianCalendar value) {
        this.lopetamiseKuupaev = value;
    }

    /**
     * Gets the value of the tase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTase() {
        return tase;
    }

    /**
     * Sets the value of the tase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTase(String value) {
        this.tase = value;
    }

    /**
     * Gets the value of the erialaKood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getErialaKood() {
        return erialaKood;
    }

    /**
     * Sets the value of the erialaKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setErialaKood(BigInteger value) {
        this.erialaKood = value;
    }

    /**
     * Gets the value of the eriala property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEriala() {
        return eriala;
    }

    /**
     * Sets the value of the eriala property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEriala(String value) {
        this.eriala = value;
    }

    /**
     * Gets the value of the keeleKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeeleKood() {
        return keeleKood;
    }

    /**
     * Sets the value of the keeleKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeeleKood(String value) {
        this.keeleKood = value;
    }

    /**
     * Gets the value of the staatus property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getStaatus() {
        return staatus;
    }

    /**
     * Sets the value of the staatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setStaatus(BigInteger value) {
        this.staatus = value;
    }

    /**
     * Gets the value of the finantseerimiseAllikas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFinantseerimiseAllikas() {
        return finantseerimiseAllikas;
    }

    /**
     * Sets the value of the finantseerimiseAllikas property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFinantseerimiseAllikas(String value) {
        this.finantseerimiseAllikas = value;
    }

    /**
     * Gets the value of the oppevorm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppevorm() {
        return oppevorm;
    }

    /**
     * Sets the value of the oppevorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppevorm(String value) {
        this.oppevorm = value;
    }

    /**
     * Gets the value of the koolId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKoolId() {
        return koolId;
    }

    /**
     * Sets the value of the koolId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKoolId(BigInteger value) {
        this.koolId = value;
    }

    /**
     * Gets the value of the nomA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomA() {
        return nomA;
    }

    /**
     * Sets the value of the nomA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomA(String value) {
        this.nomA = value;
    }

    /**
     * Gets the value of the diplom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiplom() {
        return diplom;
    }

    /**
     * Sets the value of the diplom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiplom(String value) {
        this.diplom = value;
    }

    /**
     * Gets the value of the cumlaude property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCumlaude() {
        return cumlaude;
    }

    /**
     * Sets the value of the cumlaude property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCumlaude(BigInteger value) {
        this.cumlaude = value;
    }

    /**
     * Gets the value of the tasutaPaevad property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTasutaPaevad() {
        return tasutaPaevad;
    }

    /**
     * Sets the value of the tasutaPaevad property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTasutaPaevad(BigInteger value) {
        this.tasutaPaevad = value;
    }

    /**
     * Gets the value of the nominaal property.
     * 
     * @return
     *     possible object is
     *     {@link SaisKorkNominaal }
     *     
     */
    public SaisKorkNominaal getNominaal() {
        return nominaal;
    }

    /**
     * Sets the value of the nominaal property.
     * 
     * @param value
     *     allowed object is
     *     {@link SaisKorkNominaal }
     *     
     */
    public void setNominaal(SaisKorkNominaal value) {
        this.nominaal = value;
    }

}
