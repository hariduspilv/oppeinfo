
package ee.hois.xroad.ariregister.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for NapTasumiseLisamineType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NapTasumiseLisamineType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NoudeIdAllikas" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="LaekId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="TasuId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="LaekKpv" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="MaksjaIsikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="EmkoOmanikIsikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="MaksjaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MaksjaKontoNR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="MakseSelgitus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TasumiseSumma" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="LaekumiseSumma" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="Viitenumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="MuutuseLiikKL" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NapTasumiseLisamineType", propOrder = {
    "noudeIdAllikas",
    "laekId",
    "tasuId",
    "laekKpv",
    "maksjaIsikukood",
    "emkoOmanikIsikukood",
    "maksjaNimi",
    "maksjaKontoNR",
    "makseSelgitus",
    "tasumiseSumma",
    "laekumiseSumma",
    "viitenumber",
    "muutuseLiikKL"
})
public class NapTasumiseLisamineType {

    @XmlElement(name = "NoudeIdAllikas", required = true)
    protected String noudeIdAllikas;
    @XmlElement(name = "LaekId", required = true)
    protected String laekId;
    @XmlElement(name = "TasuId", required = true)
    protected String tasuId;
    @XmlElement(name = "LaekKpv", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar laekKpv;
    @XmlElement(name = "MaksjaIsikukood")
    protected String maksjaIsikukood;
    @XmlElement(name = "EmkoOmanikIsikukood")
    protected String emkoOmanikIsikukood;
    @XmlElement(name = "MaksjaNimi", required = true)
    protected String maksjaNimi;
    @XmlElement(name = "MaksjaKontoNR")
    protected String maksjaKontoNR;
    @XmlElement(name = "MakseSelgitus")
    protected String makseSelgitus;
    @XmlElement(name = "TasumiseSumma", required = true)
    protected BigDecimal tasumiseSumma;
    @XmlElement(name = "LaekumiseSumma")
    protected BigDecimal laekumiseSumma;
    @XmlElement(name = "Viitenumber")
    protected String viitenumber;
    @XmlElement(name = "MuutuseLiikKL")
    protected int muutuseLiikKL;

    /**
     * Gets the value of the noudeIdAllikas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoudeIdAllikas() {
        return noudeIdAllikas;
    }

    /**
     * Sets the value of the noudeIdAllikas property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoudeIdAllikas(String value) {
        this.noudeIdAllikas = value;
    }

    /**
     * Gets the value of the laekId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLaekId() {
        return laekId;
    }

    /**
     * Sets the value of the laekId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLaekId(String value) {
        this.laekId = value;
    }

    /**
     * Gets the value of the tasuId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTasuId() {
        return tasuId;
    }

    /**
     * Sets the value of the tasuId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTasuId(String value) {
        this.tasuId = value;
    }

    /**
     * Gets the value of the laekKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLaekKpv() {
        return laekKpv;
    }

    /**
     * Sets the value of the laekKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLaekKpv(XMLGregorianCalendar value) {
        this.laekKpv = value;
    }

    /**
     * Gets the value of the maksjaIsikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaksjaIsikukood() {
        return maksjaIsikukood;
    }

    /**
     * Sets the value of the maksjaIsikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaksjaIsikukood(String value) {
        this.maksjaIsikukood = value;
    }

    /**
     * Gets the value of the emkoOmanikIsikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmkoOmanikIsikukood() {
        return emkoOmanikIsikukood;
    }

    /**
     * Sets the value of the emkoOmanikIsikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmkoOmanikIsikukood(String value) {
        this.emkoOmanikIsikukood = value;
    }

    /**
     * Gets the value of the maksjaNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaksjaNimi() {
        return maksjaNimi;
    }

    /**
     * Sets the value of the maksjaNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaksjaNimi(String value) {
        this.maksjaNimi = value;
    }

    /**
     * Gets the value of the maksjaKontoNR property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaksjaKontoNR() {
        return maksjaKontoNR;
    }

    /**
     * Sets the value of the maksjaKontoNR property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaksjaKontoNR(String value) {
        this.maksjaKontoNR = value;
    }

    /**
     * Gets the value of the makseSelgitus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMakseSelgitus() {
        return makseSelgitus;
    }

    /**
     * Sets the value of the makseSelgitus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMakseSelgitus(String value) {
        this.makseSelgitus = value;
    }

    /**
     * Gets the value of the tasumiseSumma property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTasumiseSumma() {
        return tasumiseSumma;
    }

    /**
     * Sets the value of the tasumiseSumma property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTasumiseSumma(BigDecimal value) {
        this.tasumiseSumma = value;
    }

    /**
     * Gets the value of the laekumiseSumma property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLaekumiseSumma() {
        return laekumiseSumma;
    }

    /**
     * Sets the value of the laekumiseSumma property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLaekumiseSumma(BigDecimal value) {
        this.laekumiseSumma = value;
    }

    /**
     * Gets the value of the viitenumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViitenumber() {
        return viitenumber;
    }

    /**
     * Sets the value of the viitenumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViitenumber(String value) {
        this.viitenumber = value;
    }

    /**
     * Gets the value of the muutuseLiikKL property.
     * 
     */
    public int getMuutuseLiikKL() {
        return muutuseLiikKL;
    }

    /**
     * Sets the value of the muutuseLiikKL property.
     * 
     */
    public void setMuutuseLiikKL(int value) {
        this.muutuseLiikKL = value;
    }

}
