
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HooldusoiguseDokument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HooldusoiguseDokument"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Seeria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ValjaantudKp" type="{http://rr.x-road.eu/producer}date"/&gt;
 *         &lt;element name="KehtibAlatesKp" type="{http://rr.x-road.eu/producer}date"/&gt;
 *         &lt;element name="KehtibKuniKp" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *         &lt;element name="Asutus" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="RiigiNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="AsutusTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Olek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HooldusoiguseDokument", propOrder = {
    "nimetus",
    "liik",
    "seeria",
    "number",
    "valjaantudKp",
    "kehtibAlatesKp",
    "kehtibKuniKp",
    "asutus",
    "olek"
})
public class HooldusoiguseDokument {

    @XmlElement(name = "Nimetus")
    protected String nimetus;
    @XmlElement(name = "Liik", required = true)
    protected String liik;
    @XmlElement(name = "Seeria")
    protected String seeria;
    @XmlElement(name = "Number", required = true)
    protected String number;
    @XmlElement(name = "ValjaantudKp", required = true)
    protected String valjaantudKp;
    @XmlElement(name = "KehtibAlatesKp", required = true)
    protected String kehtibAlatesKp;
    @XmlElement(name = "KehtibKuniKp")
    protected String kehtibKuniKp;
    @XmlElement(name = "Asutus")
    protected HooldusoiguseDokument.Asutus asutus;
    @XmlElement(name = "Olek", required = true)
    protected String olek;

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
     * Gets the value of the liik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLiik() {
        return liik;
    }

    /**
     * Sets the value of the liik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLiik(String value) {
        this.liik = value;
    }

    /**
     * Gets the value of the seeria property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeeria() {
        return seeria;
    }

    /**
     * Sets the value of the seeria property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeeria(String value) {
        this.seeria = value;
    }

    /**
     * Gets the value of the number property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumber(String value) {
        this.number = value;
    }

    /**
     * Gets the value of the valjaantudKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValjaantudKp() {
        return valjaantudKp;
    }

    /**
     * Sets the value of the valjaantudKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValjaantudKp(String value) {
        this.valjaantudKp = value;
    }

    /**
     * Gets the value of the kehtibAlatesKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKehtibAlatesKp() {
        return kehtibAlatesKp;
    }

    /**
     * Sets the value of the kehtibAlatesKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKehtibAlatesKp(String value) {
        this.kehtibAlatesKp = value;
    }

    /**
     * Gets the value of the kehtibKuniKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKehtibKuniKp() {
        return kehtibKuniKp;
    }

    /**
     * Sets the value of the kehtibKuniKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKehtibKuniKp(String value) {
        this.kehtibKuniKp = value;
    }

    /**
     * Gets the value of the asutus property.
     * 
     * @return
     *     possible object is
     *     {@link HooldusoiguseDokument.Asutus }
     *     
     */
    public HooldusoiguseDokument.Asutus getAsutus() {
        return asutus;
    }

    /**
     * Sets the value of the asutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link HooldusoiguseDokument.Asutus }
     *     
     */
    public void setAsutus(HooldusoiguseDokument.Asutus value) {
        this.asutus = value;
    }

    /**
     * Gets the value of the olek property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOlek() {
        return olek;
    }

    /**
     * Sets the value of the olek property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOlek(String value) {
        this.olek = value;
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
     *         &lt;element name="RiigiNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="AsutusTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "riigiNimetus",
        "asutusTekst"
    })
    public static class Asutus {

        @XmlElement(name = "RiigiNimetus", required = true)
        protected String riigiNimetus;
        @XmlElement(name = "AsutusTekst", required = true)
        protected String asutusTekst;

        /**
         * Gets the value of the riigiNimetus property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRiigiNimetus() {
            return riigiNimetus;
        }

        /**
         * Sets the value of the riigiNimetus property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRiigiNimetus(String value) {
            this.riigiNimetus = value;
        }

        /**
         * Gets the value of the asutusTekst property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAsutusTekst() {
            return asutusTekst;
        }

        /**
         * Sets the value of the asutusTekst property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAsutusTekst(String value) {
            this.asutusTekst = value;
        }

    }

}
