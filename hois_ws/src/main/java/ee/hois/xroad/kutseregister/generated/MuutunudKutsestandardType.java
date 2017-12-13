
package ee.hois.xroad.kutseregister.generated;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for muutunudKutsestandardType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="muutunudKutsestandardType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="grupp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="versioon" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ekrtase" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}taseType"/&gt;
 *         &lt;element name="kehtivusealgus" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="kehtivuselopp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="valdkond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="osakutsed" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="osakutse" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}idNimetusType" maxOccurs="unbounded"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="spetsialiseerumised" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="spetsialiseerumine" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}idNimetusType" maxOccurs="unbounded"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "muutunudKutsestandardType", propOrder = {
    "id",
    "grupp",
    "versioon",
    "nimetus",
    "ekrtase",
    "kehtivusealgus",
    "kehtivuselopp",
    "valdkond",
    "osakutsed",
    "spetsialiseerumised"
})
public class MuutunudKutsestandardType {

    @XmlElement(required = true)
    protected BigInteger id;
    @XmlElement(required = true)
    protected String grupp;
    @XmlElement(required = true)
    protected BigInteger versioon;
    @XmlElement(required = true)
    protected String nimetus;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(Adapter2 .class)
    @XmlSchemaType(name = "integer")
    protected String ekrtase;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate kehtivusealgus;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate kehtivuselopp;
    @XmlElement(required = true)
    protected String valdkond;
    protected MuutunudKutsestandardType.Osakutsed osakutsed;
    protected MuutunudKutsestandardType.Spetsialiseerumised spetsialiseerumised;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setId(BigInteger value) {
        this.id = value;
    }

    /**
     * Gets the value of the grupp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrupp() {
        return grupp;
    }

    /**
     * Sets the value of the grupp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrupp(String value) {
        this.grupp = value;
    }

    /**
     * Gets the value of the versioon property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVersioon() {
        return versioon;
    }

    /**
     * Sets the value of the versioon property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVersioon(BigInteger value) {
        this.versioon = value;
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
     * Gets the value of the osakutsed property.
     * 
     * @return
     *     possible object is
     *     {@link MuutunudKutsestandardType.Osakutsed }
     *     
     */
    public MuutunudKutsestandardType.Osakutsed getOsakutsed() {
        return osakutsed;
    }

    /**
     * Sets the value of the osakutsed property.
     * 
     * @param value
     *     allowed object is
     *     {@link MuutunudKutsestandardType.Osakutsed }
     *     
     */
    public void setOsakutsed(MuutunudKutsestandardType.Osakutsed value) {
        this.osakutsed = value;
    }

    /**
     * Gets the value of the spetsialiseerumised property.
     * 
     * @return
     *     possible object is
     *     {@link MuutunudKutsestandardType.Spetsialiseerumised }
     *     
     */
    public MuutunudKutsestandardType.Spetsialiseerumised getSpetsialiseerumised() {
        return spetsialiseerumised;
    }

    /**
     * Sets the value of the spetsialiseerumised property.
     * 
     * @param value
     *     allowed object is
     *     {@link MuutunudKutsestandardType.Spetsialiseerumised }
     *     
     */
    public void setSpetsialiseerumised(MuutunudKutsestandardType.Spetsialiseerumised value) {
        this.spetsialiseerumised = value;
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
     *         &lt;element name="osakutse" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}idNimetusType" maxOccurs="unbounded"/&gt;
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
        "osakutse"
    })
    public static class Osakutsed {

        @XmlElement(namespace = "", required = true)
        protected List<IdNimetusType> osakutse;

        /**
         * Gets the value of the osakutse property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the osakutse property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOsakutse().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link IdNimetusType }
         * 
         * 
         */
        public List<IdNimetusType> getOsakutse() {
            if (osakutse == null) {
                osakutse = new ArrayList<IdNimetusType>();
            }
            return this.osakutse;
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
     *         &lt;element name="spetsialiseerumine" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}idNimetusType" maxOccurs="unbounded"/&gt;
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
        "spetsialiseerumine"
    })
    public static class Spetsialiseerumised {

        @XmlElement(namespace = "", required = true)
        protected List<IdNimetusType> spetsialiseerumine;

        /**
         * Gets the value of the spetsialiseerumine property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the spetsialiseerumine property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSpetsialiseerumine().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link IdNimetusType }
         * 
         * 
         */
        public List<IdNimetusType> getSpetsialiseerumine() {
            if (spetsialiseerumine == null) {
                spetsialiseerumine = new ArrayList<IdNimetusType>();
            }
            return this.spetsialiseerumine;
        }

    }

}
