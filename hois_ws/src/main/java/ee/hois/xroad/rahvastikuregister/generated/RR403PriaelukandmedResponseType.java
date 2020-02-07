
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR403PriaelukandmedResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR403PriaelukandmedResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikuolek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Veakood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="Veatekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Elukohad"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Elukoht" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Elukoht.Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Maakondkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Maakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Vald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Asula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Maja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Korter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Sihtnumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Kuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Sama_aadress"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Sama_aadressiga" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Sama_aadressiga.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Sama_aadressiga.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Sama_aadressiga.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR403PriaelukandmedResponseType", propOrder = {
    "isikukood",
    "eesnimi",
    "perenimi",
    "isikuolek",
    "veakood",
    "veatekst",
    "elukohad",
    "samaAadress"
})
public class RR403PriaelukandmedResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Isikukood", required = true)
    protected String isikukood;
    @XmlElement(name = "Eesnimi", required = true)
    protected String eesnimi;
    @XmlElement(name = "Perenimi", required = true)
    protected String perenimi;
    @XmlElement(name = "Isikuolek", required = true)
    protected String isikuolek;
    @XmlElement(name = "Veakood", required = true)
    protected BigInteger veakood;
    @XmlElement(name = "Veatekst", required = true)
    protected String veatekst;
    @XmlElement(name = "Elukohad", required = true)
    protected RR403PriaelukandmedResponseType.Elukohad elukohad;
    @XmlElement(name = "Sama_aadress", required = true)
    protected RR403PriaelukandmedResponseType.SamaAadress samaAadress;

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
     * Gets the value of the isikuolek property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuolek() {
        return isikuolek;
    }

    /**
     * Sets the value of the isikuolek property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuolek(String value) {
        this.isikuolek = value;
    }

    /**
     * Gets the value of the veakood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVeakood() {
        return veakood;
    }

    /**
     * Sets the value of the veakood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVeakood(BigInteger value) {
        this.veakood = value;
    }

    /**
     * Gets the value of the veatekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVeatekst() {
        return veatekst;
    }

    /**
     * Sets the value of the veatekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVeatekst(String value) {
        this.veatekst = value;
    }

    /**
     * Gets the value of the elukohad property.
     * 
     * @return
     *     possible object is
     *     {@link RR403PriaelukandmedResponseType.Elukohad }
     *     
     */
    public RR403PriaelukandmedResponseType.Elukohad getElukohad() {
        return elukohad;
    }

    /**
     * Sets the value of the elukohad property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR403PriaelukandmedResponseType.Elukohad }
     *     
     */
    public void setElukohad(RR403PriaelukandmedResponseType.Elukohad value) {
        this.elukohad = value;
    }

    /**
     * Gets the value of the samaAadress property.
     * 
     * @return
     *     possible object is
     *     {@link RR403PriaelukandmedResponseType.SamaAadress }
     *     
     */
    public RR403PriaelukandmedResponseType.SamaAadress getSamaAadress() {
        return samaAadress;
    }

    /**
     * Sets the value of the samaAadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR403PriaelukandmedResponseType.SamaAadress }
     *     
     */
    public void setSamaAadress(RR403PriaelukandmedResponseType.SamaAadress value) {
        this.samaAadress = value;
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
     *         &lt;element name="Elukoht" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Elukoht.Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Maakondkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Maakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Vald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Asula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Maja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Korter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Sihtnumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Kuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    @XmlType(name = "", propOrder = {
        "elukoht"
    })
    public static class Elukohad {

        @XmlElement(name = "Elukoht")
        protected List<RR403PriaelukandmedResponseType.Elukohad.Elukoht> elukoht;

        /**
         * Gets the value of the elukoht property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the elukoht property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getElukoht().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR403PriaelukandmedResponseType.Elukohad.Elukoht }
         * 
         * 
         */
        public List<RR403PriaelukandmedResponseType.Elukohad.Elukoht> getElukoht() {
            if (elukoht == null) {
                elukoht = new ArrayList<RR403PriaelukandmedResponseType.Elukohad.Elukoht>();
            }
            return this.elukoht;
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
         *         &lt;element name="Elukoht.Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Maakondkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Maakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Vald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Asula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Maja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Korter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Sihtnumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Kuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "elukohtRiik",
            "elukohtMaakondkd",
            "elukohtMaakond",
            "elukohtVald",
            "elukohtAsula",
            "elukohtTanav",
            "elukohtMaja",
            "elukohtKorter",
            "elukohtSihtnumber",
            "elukohtAlates",
            "elukohtKuni"
        })
        public static class Elukoht {

            @XmlElement(name = "Elukoht.Riik", required = true)
            protected String elukohtRiik;
            @XmlElement(name = "Elukoht.Maakondkd", required = true)
            protected String elukohtMaakondkd;
            @XmlElement(name = "Elukoht.Maakond", required = true)
            protected String elukohtMaakond;
            @XmlElement(name = "Elukoht.Vald", required = true)
            protected String elukohtVald;
            @XmlElement(name = "Elukoht.Asula", required = true)
            protected String elukohtAsula;
            @XmlElement(name = "Elukoht.Tanav", required = true)
            protected String elukohtTanav;
            @XmlElement(name = "Elukoht.Maja", required = true)
            protected String elukohtMaja;
            @XmlElement(name = "Elukoht.Korter", required = true)
            protected String elukohtKorter;
            @XmlElement(name = "Elukoht.Sihtnumber", required = true)
            protected String elukohtSihtnumber;
            @XmlElement(name = "Elukoht.Alates", required = true)
            protected String elukohtAlates;
            @XmlElement(name = "Elukoht.Kuni", required = true)
            protected String elukohtKuni;

            /**
             * Gets the value of the elukohtRiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtRiik() {
                return elukohtRiik;
            }

            /**
             * Sets the value of the elukohtRiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtRiik(String value) {
                this.elukohtRiik = value;
            }

            /**
             * Gets the value of the elukohtMaakondkd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtMaakondkd() {
                return elukohtMaakondkd;
            }

            /**
             * Sets the value of the elukohtMaakondkd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtMaakondkd(String value) {
                this.elukohtMaakondkd = value;
            }

            /**
             * Gets the value of the elukohtMaakond property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtMaakond() {
                return elukohtMaakond;
            }

            /**
             * Sets the value of the elukohtMaakond property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtMaakond(String value) {
                this.elukohtMaakond = value;
            }

            /**
             * Gets the value of the elukohtVald property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtVald() {
                return elukohtVald;
            }

            /**
             * Sets the value of the elukohtVald property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtVald(String value) {
                this.elukohtVald = value;
            }

            /**
             * Gets the value of the elukohtAsula property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtAsula() {
                return elukohtAsula;
            }

            /**
             * Sets the value of the elukohtAsula property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtAsula(String value) {
                this.elukohtAsula = value;
            }

            /**
             * Gets the value of the elukohtTanav property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtTanav() {
                return elukohtTanav;
            }

            /**
             * Sets the value of the elukohtTanav property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtTanav(String value) {
                this.elukohtTanav = value;
            }

            /**
             * Gets the value of the elukohtMaja property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtMaja() {
                return elukohtMaja;
            }

            /**
             * Sets the value of the elukohtMaja property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtMaja(String value) {
                this.elukohtMaja = value;
            }

            /**
             * Gets the value of the elukohtKorter property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtKorter() {
                return elukohtKorter;
            }

            /**
             * Sets the value of the elukohtKorter property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtKorter(String value) {
                this.elukohtKorter = value;
            }

            /**
             * Gets the value of the elukohtSihtnumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtSihtnumber() {
                return elukohtSihtnumber;
            }

            /**
             * Sets the value of the elukohtSihtnumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtSihtnumber(String value) {
                this.elukohtSihtnumber = value;
            }

            /**
             * Gets the value of the elukohtAlates property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtAlates() {
                return elukohtAlates;
            }

            /**
             * Sets the value of the elukohtAlates property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtAlates(String value) {
                this.elukohtAlates = value;
            }

            /**
             * Gets the value of the elukohtKuni property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtKuni() {
                return elukohtKuni;
            }

            /**
             * Sets the value of the elukohtKuni property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtKuni(String value) {
                this.elukohtKuni = value;
            }

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
     *         &lt;element name="Sama_aadressiga" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Sama_aadressiga.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Sama_aadressiga.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Sama_aadressiga.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    @XmlType(name = "", propOrder = {
        "samaAadressiga"
    })
    public static class SamaAadress {

        @XmlElement(name = "Sama_aadressiga")
        protected List<RR403PriaelukandmedResponseType.SamaAadress.SamaAadressiga> samaAadressiga;

        /**
         * Gets the value of the samaAadressiga property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the samaAadressiga property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSamaAadressiga().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR403PriaelukandmedResponseType.SamaAadress.SamaAadressiga }
         * 
         * 
         */
        public List<RR403PriaelukandmedResponseType.SamaAadress.SamaAadressiga> getSamaAadressiga() {
            if (samaAadressiga == null) {
                samaAadressiga = new ArrayList<RR403PriaelukandmedResponseType.SamaAadress.SamaAadressiga>();
            }
            return this.samaAadressiga;
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
         *         &lt;element name="Sama_aadressiga.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Sama_aadressiga.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Sama_aadressiga.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "samaAadressigaIsikukood",
            "samaAadressigaEesnimi",
            "samaAadressigaPerenimi"
        })
        public static class SamaAadressiga {

            @XmlElement(name = "Sama_aadressiga.Isikukood", required = true)
            protected String samaAadressigaIsikukood;
            @XmlElement(name = "Sama_aadressiga.Eesnimi", required = true)
            protected String samaAadressigaEesnimi;
            @XmlElement(name = "Sama_aadressiga.Perenimi", required = true)
            protected String samaAadressigaPerenimi;

            /**
             * Gets the value of the samaAadressigaIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSamaAadressigaIsikukood() {
                return samaAadressigaIsikukood;
            }

            /**
             * Sets the value of the samaAadressigaIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSamaAadressigaIsikukood(String value) {
                this.samaAadressigaIsikukood = value;
            }

            /**
             * Gets the value of the samaAadressigaEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSamaAadressigaEesnimi() {
                return samaAadressigaEesnimi;
            }

            /**
             * Sets the value of the samaAadressigaEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSamaAadressigaEesnimi(String value) {
                this.samaAadressigaEesnimi = value;
            }

            /**
             * Gets the value of the samaAadressigaPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSamaAadressigaPerenimi() {
                return samaAadressigaPerenimi;
            }

            /**
             * Sets the value of the samaAadressigaPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSamaAadressigaPerenimi(String value) {
                this.samaAadressigaPerenimi = value;
            }

        }

    }

}
