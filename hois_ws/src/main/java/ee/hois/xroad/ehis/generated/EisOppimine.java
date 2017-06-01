
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for eisOppimine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eisOppimine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="onLopetanud" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="koolId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="klassKursus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="paralleeliTunnus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppekeel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppevorm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekava" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="lopetAasta" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="tunnistusNr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tunnistusKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eisOppimine", propOrder = {
    "isikukood",
    "onLopetanud",
    "eesnimi",
    "perenimi",
    "koolId",
    "klassKursus",
    "paralleeliTunnus",
    "oppekeel",
    "oppevorm",
    "oppekava",
    "lopetAasta",
    "tunnistusNr",
    "tunnistusKp"
})
public class EisOppimine {

    @XmlElement(required = true)
    protected String isikukood;
    protected boolean onLopetanud;
    @XmlElement(required = true)
    protected String eesnimi;
    @XmlElement(required = true)
    protected String perenimi;
    @XmlElement(required = true)
    protected BigInteger koolId;
    protected String klassKursus;
    protected String paralleeliTunnus;
    protected String oppekeel;
    @XmlElement(required = true)
    protected String oppevorm;
    @XmlElement(required = true)
    protected String oppekava;
    protected BigInteger lopetAasta;
    protected String tunnistusNr;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar tunnistusKp;

    /**
     * Gets the value of the isikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukood() {
        return isikukood;
    }

    /**
     * Sets the value of the isikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukood(String value) {
        this.isikukood = value;
    }

    /**
     * Gets the value of the onLopetanud property.
     * 
     */
    public boolean isOnLopetanud() {
        return onLopetanud;
    }

    /**
     * Sets the value of the onLopetanud property.
     * 
     */
    public void setOnLopetanud(boolean value) {
        this.onLopetanud = value;
    }

    /**
     * Gets the value of the eesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEesnimi() {
        return eesnimi;
    }

    /**
     * Sets the value of the eesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEesnimi(String value) {
        this.eesnimi = value;
    }

    /**
     * Gets the value of the perenimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerenimi() {
        return perenimi;
    }

    /**
     * Sets the value of the perenimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerenimi(String value) {
        this.perenimi = value;
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
     * Gets the value of the klassKursus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlassKursus() {
        return klassKursus;
    }

    /**
     * Sets the value of the klassKursus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlassKursus(String value) {
        this.klassKursus = value;
    }

    /**
     * Gets the value of the paralleeliTunnus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParalleeliTunnus() {
        return paralleeliTunnus;
    }

    /**
     * Sets the value of the paralleeliTunnus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParalleeliTunnus(String value) {
        this.paralleeliTunnus = value;
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
     * Gets the value of the oppekava property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekava() {
        return oppekava;
    }

    /**
     * Sets the value of the oppekava property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekava(String value) {
        this.oppekava = value;
    }

    /**
     * Gets the value of the lopetAasta property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLopetAasta() {
        return lopetAasta;
    }

    /**
     * Sets the value of the lopetAasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLopetAasta(BigInteger value) {
        this.lopetAasta = value;
    }

    /**
     * Gets the value of the tunnistusNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTunnistusNr() {
        return tunnistusNr;
    }

    /**
     * Sets the value of the tunnistusNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTunnistusNr(String value) {
        this.tunnistusNr = value;
    }

    /**
     * Gets the value of the tunnistusKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTunnistusKp() {
        return tunnistusKp;
    }

    /**
     * Sets the value of the tunnistusKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTunnistusKp(XMLGregorianCalendar value) {
        this.tunnistusKp = value;
    }

}
