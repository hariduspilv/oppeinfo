
package ee.hois.xroad.kutseregister.generated;

import java.math.BigInteger;
import java.time.LocalDate;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Kutseregistri kutsetunnistuste otsingu sisendparameetrid
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
 *         &lt;element name="registrinumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tyyp" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}tunnistuseTyypType" minOccurs="0"/&gt;
 *         &lt;element name="kutsestandard" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="osakutse" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ekrtase" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}taseType" minOccurs="0"/&gt;
 *         &lt;element name="eqftase" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}taseType" minOccurs="0"/&gt;
 *         &lt;element name="valjastaja" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="valjastatudalgus" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="valjastatudlopp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="kehtibalatesalgus" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="kehtibalateslopp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="kehtibkunialgus" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="kehtibkunilopp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="kirjetearv" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}kirjeteArvType" minOccurs="0"/&gt;
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
@XmlRootElement(name = "kutsetunnistus")
public class Kutsetunnistus {

    protected String registrinumber;
    protected String nimi;
    protected String isikukood;
    protected BigInteger tyyp;
    protected String kutsestandard;
    protected String osakutse;
    @XmlJavaTypeAdapter(Adapter2 .class)
    @XmlSchemaType(name = "integer")
    protected String ekrtase;
    @XmlJavaTypeAdapter(Adapter2 .class)
    @XmlSchemaType(name = "integer")
    protected String eqftase;
    protected String valjastaja;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate valjastatudalgus;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate valjastatudlopp;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate kehtibalatesalgus;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate kehtibalateslopp;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate kehtibkunialgus;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate kehtibkunilopp;
    protected BigInteger kirjetearv;

    /**
     * Gets the value of the registrinumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistrinumber() {
        return registrinumber;
    }

    /**
     * Sets the value of the registrinumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistrinumber(String value) {
        this.registrinumber = value;
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
     *     {@link String }
     *     
     */
    public String getKutsestandard() {
        return kutsestandard;
    }

    /**
     * Sets the value of the kutsestandard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKutsestandard(String value) {
        this.kutsestandard = value;
    }

    /**
     * Gets the value of the osakutse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOsakutse() {
        return osakutse;
    }

    /**
     * Sets the value of the osakutse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOsakutse(String value) {
        this.osakutse = value;
    }

    /**
     * Gets the value of the ekrtase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEkrtase() {
        return ekrtase;
    }

    /**
     * Sets the value of the ekrtase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEkrtase(String value) {
        this.ekrtase = value;
    }

    /**
     * Gets the value of the eqftase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEqftase() {
        return eqftase;
    }

    /**
     * Sets the value of the eqftase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEqftase(String value) {
        this.eqftase = value;
    }

    /**
     * Gets the value of the valjastaja property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValjastaja() {
        return valjastaja;
    }

    /**
     * Sets the value of the valjastaja property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValjastaja(String value) {
        this.valjastaja = value;
    }

    /**
     * Gets the value of the valjastatudalgus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getValjastatudalgus() {
        return valjastatudalgus;
    }

    /**
     * Sets the value of the valjastatudalgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValjastatudalgus(LocalDate value) {
        this.valjastatudalgus = value;
    }

    /**
     * Gets the value of the valjastatudlopp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getValjastatudlopp() {
        return valjastatudlopp;
    }

    /**
     * Sets the value of the valjastatudlopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValjastatudlopp(LocalDate value) {
        this.valjastatudlopp = value;
    }

    /**
     * Gets the value of the kehtibalatesalgus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getKehtibalatesalgus() {
        return kehtibalatesalgus;
    }

    /**
     * Sets the value of the kehtibalatesalgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKehtibalatesalgus(LocalDate value) {
        this.kehtibalatesalgus = value;
    }

    /**
     * Gets the value of the kehtibalateslopp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getKehtibalateslopp() {
        return kehtibalateslopp;
    }

    /**
     * Sets the value of the kehtibalateslopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKehtibalateslopp(LocalDate value) {
        this.kehtibalateslopp = value;
    }

    /**
     * Gets the value of the kehtibkunialgus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getKehtibkunialgus() {
        return kehtibkunialgus;
    }

    /**
     * Sets the value of the kehtibkunialgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKehtibkunialgus(LocalDate value) {
        this.kehtibkunialgus = value;
    }

    /**
     * Gets the value of the kehtibkunilopp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getKehtibkunilopp() {
        return kehtibkunilopp;
    }

    /**
     * Sets the value of the kehtibkunilopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKehtibkunilopp(LocalDate value) {
        this.kehtibkunilopp = value;
    }

    /**
     * Gets the value of the kirjetearv property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKirjetearv() {
        return kirjetearv;
    }

    /**
     * Sets the value of the kirjetearv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKirjetearv(BigInteger value) {
        this.kirjetearv = value;
    }

}
