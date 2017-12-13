
package ee.hois.xroad.kutseregister.generated;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Kutsetunnistuse taotluse lisamine
 * 
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="asutus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tyyp" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}tunnistuseTyypType"/&gt;
 *         &lt;element name="kutsestandard" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="spetsialiseerumine" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="osakutse" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="kompetentsid" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="kompetents" type="{http://www.w3.org/2001/XMLSchema}integer" maxOccurs="unbounded"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="lisavali" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="hariduslikkval" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kutseeksamikeel" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="valjastamisekpv" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="kehtivusealgus" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="kehtivuselopp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="allkirjastaja" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="nimipeidetud" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="synniaegpeidetud" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="tunnistuspeidetud" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *       &lt;/all&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "kutsetunnistuseLisamineParing")
public class KutsetunnistuseLisamineParing {

    protected String asutus;
    @XmlElement(required = true)
    protected String nimi;
    @XmlElement(required = true)
    protected String isikukood;
    @XmlElement(required = true)
    protected BigInteger tyyp;
    @XmlElement(required = true)
    protected BigInteger kutsestandard;
    protected BigInteger spetsialiseerumine;
    protected BigInteger osakutse;
    protected KutsetunnistuseLisamineParing.Kompetentsid kompetentsid;
    protected String lisavali;
    protected String hariduslikkval;
    protected BigInteger kutseeksamikeel;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate valjastamisekpv;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate kehtivusealgus;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate kehtivuselopp;
    @XmlElement(required = true)
    protected BigInteger allkirjastaja;
    @XmlElement(defaultValue = "false")
    protected Boolean nimipeidetud;
    @XmlElement(defaultValue = "false")
    protected Boolean synniaegpeidetud;
    @XmlElement(defaultValue = "false")
    protected Boolean tunnistuspeidetud;

    /**
     * Gets the value of the asutus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsutus() {
        return asutus;
    }

    /**
     * Sets the value of the asutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsutus(String value) {
        this.asutus = value;
    }

    /**
     * Gets the value of the nimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * Sets the value of the nimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNimi(String value) {
        this.nimi = value;
    }

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
     * Gets the value of the tyyp property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTyyp() {
        return tyyp;
    }

    /**
     * Sets the value of the tyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTyyp(BigInteger value) {
        this.tyyp = value;
    }

    /**
     * Gets the value of the kutsestandard property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKutsestandard() {
        return kutsestandard;
    }

    /**
     * Sets the value of the kutsestandard property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKutsestandard(BigInteger value) {
        this.kutsestandard = value;
    }

    /**
     * Gets the value of the spetsialiseerumine property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSpetsialiseerumine() {
        return spetsialiseerumine;
    }

    /**
     * Sets the value of the spetsialiseerumine property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSpetsialiseerumine(BigInteger value) {
        this.spetsialiseerumine = value;
    }

    /**
     * Gets the value of the osakutse property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOsakutse() {
        return osakutse;
    }

    /**
     * Sets the value of the osakutse property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOsakutse(BigInteger value) {
        this.osakutse = value;
    }

    /**
     * Gets the value of the kompetentsid property.
     * 
     * @return
     *     possible object is
     *     {@link KutsetunnistuseLisamineParing.Kompetentsid }
     *     
     */
    public KutsetunnistuseLisamineParing.Kompetentsid getKompetentsid() {
        return kompetentsid;
    }

    /**
     * Sets the value of the kompetentsid property.
     * 
     * @param value
     *     allowed object is
     *     {@link KutsetunnistuseLisamineParing.Kompetentsid }
     *     
     */
    public void setKompetentsid(KutsetunnistuseLisamineParing.Kompetentsid value) {
        this.kompetentsid = value;
    }

    /**
     * Gets the value of the lisavali property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLisavali() {
        return lisavali;
    }

    /**
     * Sets the value of the lisavali property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLisavali(String value) {
        this.lisavali = value;
    }

    /**
     * Gets the value of the hariduslikkval property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHariduslikkval() {
        return hariduslikkval;
    }

    /**
     * Sets the value of the hariduslikkval property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHariduslikkval(String value) {
        this.hariduslikkval = value;
    }

    /**
     * Gets the value of the kutseeksamikeel property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKutseeksamikeel() {
        return kutseeksamikeel;
    }

    /**
     * Sets the value of the kutseeksamikeel property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKutseeksamikeel(BigInteger value) {
        this.kutseeksamikeel = value;
    }

    /**
     * Gets the value of the valjastamisekpv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getValjastamisekpv() {
        return valjastamisekpv;
    }

    /**
     * Sets the value of the valjastamisekpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValjastamisekpv(LocalDate value) {
        this.valjastamisekpv = value;
    }

    /**
     * Gets the value of the kehtivusealgus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getKehtivusealgus() {
        return kehtivusealgus;
    }

    /**
     * Sets the value of the kehtivusealgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKehtivusealgus(LocalDate value) {
        this.kehtivusealgus = value;
    }

    /**
     * Gets the value of the kehtivuselopp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getKehtivuselopp() {
        return kehtivuselopp;
    }

    /**
     * Sets the value of the kehtivuselopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKehtivuselopp(LocalDate value) {
        this.kehtivuselopp = value;
    }

    /**
     * Gets the value of the allkirjastaja property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAllkirjastaja() {
        return allkirjastaja;
    }

    /**
     * Sets the value of the allkirjastaja property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAllkirjastaja(BigInteger value) {
        this.allkirjastaja = value;
    }

    /**
     * Gets the value of the nimipeidetud property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNimipeidetud() {
        return nimipeidetud;
    }

    /**
     * Sets the value of the nimipeidetud property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNimipeidetud(Boolean value) {
        this.nimipeidetud = value;
    }

    /**
     * Gets the value of the synniaegpeidetud property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSynniaegpeidetud() {
        return synniaegpeidetud;
    }

    /**
     * Sets the value of the synniaegpeidetud property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSynniaegpeidetud(Boolean value) {
        this.synniaegpeidetud = value;
    }

    /**
     * Gets the value of the tunnistuspeidetud property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTunnistuspeidetud() {
        return tunnistuspeidetud;
    }

    /**
     * Sets the value of the tunnistuspeidetud property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTunnistuspeidetud(Boolean value) {
        this.tunnistuspeidetud = value;
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
     *         &lt;element name="kompetents" type="{http://www.w3.org/2001/XMLSchema}integer" maxOccurs="unbounded"/&gt;
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
        "kompetents"
    })
    public static class Kompetentsid {

        @XmlElement(required = true)
        protected List<BigInteger> kompetents;

        /**
         * Gets the value of the kompetents property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the kompetents property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getKompetents().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link BigInteger }
         * 
         * 
         */
        public List<BigInteger> getKompetents() {
            if (kompetents == null) {
                kompetents = new ArrayList<BigInteger>();
            }
            return this.kompetents;
        }

    }

}
