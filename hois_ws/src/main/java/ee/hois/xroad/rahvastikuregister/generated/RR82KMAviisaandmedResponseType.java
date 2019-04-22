
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR82KMAviisaandmedResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR82KMAviisaandmedResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Veakood" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="Veatekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Isikud"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Isik" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Isikud.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Vanaeesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Vanaperenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Istaatuskd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Istaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Kstaatuskd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Kstaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.EOkuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.EOalates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.EOstaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Elukoht_Riikkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Elukoht_Maakondkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Elukoht_Maakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Elukoht_Linn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Elukoht_Asula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Elukoht_Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Elukoht_Maja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Elukoht_Korter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Elukoht_Sihtnumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Elukoht_Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Elukoht_Alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR82KMAviisaandmedResponseType", propOrder = {
    "veakood",
    "veatekst",
    "isikud"
})
public class RR82KMAviisaandmedResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Veakood")
    protected BigInteger veakood;
    @XmlElement(name = "Veatekst")
    protected String veatekst;
    @XmlElement(name = "Isikud", required = true)
    protected RR82KMAviisaandmedResponseType.Isikud isikud;

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
     * Gets the value of the isikud property.
     * 
     * @return
     *     possible object is
     *     {@link RR82KMAviisaandmedResponseType.Isikud }
     *     
     */
    public RR82KMAviisaandmedResponseType.Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR82KMAviisaandmedResponseType.Isikud }
     *     
     */
    public void setIsikud(RR82KMAviisaandmedResponseType.Isikud value) {
        this.isikud = value;
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
     *         &lt;element name="Isik" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Isikud.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Vanaeesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Vanaperenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Istaatuskd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Istaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Kstaatuskd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Kstaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.EOkuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.EOalates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.EOstaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Elukoht_Riikkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Elukoht_Maakondkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Elukoht_Maakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Elukoht_Linn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Elukoht_Asula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Elukoht_Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Elukoht_Maja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Elukoht_Korter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Elukoht_Sihtnumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Elukoht_Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Elukoht_Alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "isik"
    })
    public static class Isikud {

        @XmlElement(name = "Isik")
        protected List<RR82KMAviisaandmedResponseType.Isikud.Isik> isik;

        /**
         * Gets the value of the isik property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the isik property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIsik().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR82KMAviisaandmedResponseType.Isikud.Isik }
         * 
         * 
         */
        public List<RR82KMAviisaandmedResponseType.Isikud.Isik> getIsik() {
            if (isik == null) {
                isik = new ArrayList<RR82KMAviisaandmedResponseType.Isikud.Isik>();
            }
            return this.isik;
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
         *         &lt;element name="Isikud.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Vanaeesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Vanaperenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Istaatuskd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Istaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Kstaatuskd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Kstaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.EOkuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.EOalates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.EOstaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Elukoht_Riikkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Elukoht_Maakondkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Elukoht_Maakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Elukoht_Linn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Elukoht_Asula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Elukoht_Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Elukoht_Maja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Elukoht_Korter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Elukoht_Sihtnumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Elukoht_Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Elukoht_Alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "isikudEesnimi",
            "isikudPerenimi",
            "isikudIsikukood",
            "isikudSugu",
            "isikudSynniaeg",
            "isikudVanaeesnimi",
            "isikudVanaperenimi",
            "isikudIstaatuskd",
            "isikudIstaatus",
            "isikudKstaatuskd",
            "isikudKstaatus",
            "isikudKodakondsus",
            "isikudEOkuni",
            "isikudEOalates",
            "isikudEOstaatus",
            "isikudElukohtRiikkd",
            "isikudElukohtMaakondkd",
            "isikudElukohtMaakond",
            "isikudElukohtLinn",
            "isikudElukohtAsula",
            "isikudElukohtTanav",
            "isikudElukohtMaja",
            "isikudElukohtKorter",
            "isikudElukohtSihtnumber",
            "isikudElukohtStaatus",
            "isikudElukohtAlates"
        })
        public static class Isik {

            @XmlElement(name = "Isikud.Eesnimi", required = true)
            protected String isikudEesnimi;
            @XmlElement(name = "Isikud.Perenimi", required = true)
            protected String isikudPerenimi;
            @XmlElement(name = "Isikud.Isikukood", required = true)
            protected String isikudIsikukood;
            @XmlElement(name = "Isikud.Sugu", required = true)
            protected String isikudSugu;
            @XmlElement(name = "Isikud.Synniaeg", required = true)
            protected String isikudSynniaeg;
            @XmlElement(name = "Isikud.Vanaeesnimi", required = true)
            protected String isikudVanaeesnimi;
            @XmlElement(name = "Isikud.Vanaperenimi", required = true)
            protected String isikudVanaperenimi;
            @XmlElement(name = "Isikud.Istaatuskd", required = true)
            protected String isikudIstaatuskd;
            @XmlElement(name = "Isikud.Istaatus", required = true)
            protected String isikudIstaatus;
            @XmlElement(name = "Isikud.Kstaatuskd", required = true)
            protected String isikudKstaatuskd;
            @XmlElement(name = "Isikud.Kstaatus", required = true)
            protected String isikudKstaatus;
            @XmlElement(name = "Isikud.Kodakondsus", required = true)
            protected String isikudKodakondsus;
            @XmlElement(name = "Isikud.EOkuni", required = true)
            protected String isikudEOkuni;
            @XmlElement(name = "Isikud.EOalates", required = true)
            protected String isikudEOalates;
            @XmlElement(name = "Isikud.EOstaatus", required = true)
            protected String isikudEOstaatus;
            @XmlElement(name = "Isikud.Elukoht_Riikkd", required = true)
            protected String isikudElukohtRiikkd;
            @XmlElement(name = "Isikud.Elukoht_Maakondkd", required = true)
            protected String isikudElukohtMaakondkd;
            @XmlElement(name = "Isikud.Elukoht_Maakond", required = true)
            protected String isikudElukohtMaakond;
            @XmlElement(name = "Isikud.Elukoht_Linn", required = true)
            protected String isikudElukohtLinn;
            @XmlElement(name = "Isikud.Elukoht_Asula", required = true)
            protected String isikudElukohtAsula;
            @XmlElement(name = "Isikud.Elukoht_Tanav", required = true)
            protected String isikudElukohtTanav;
            @XmlElement(name = "Isikud.Elukoht_Maja", required = true)
            protected String isikudElukohtMaja;
            @XmlElement(name = "Isikud.Elukoht_Korter", required = true)
            protected String isikudElukohtKorter;
            @XmlElement(name = "Isikud.Elukoht_Sihtnumber", required = true)
            protected String isikudElukohtSihtnumber;
            @XmlElement(name = "Isikud.Elukoht_Staatus", required = true)
            protected String isikudElukohtStaatus;
            @XmlElement(name = "Isikud.Elukoht_Alates", required = true)
            protected String isikudElukohtAlates;

            /**
             * Gets the value of the isikudEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudEesnimi() {
                return isikudEesnimi;
            }

            /**
             * Sets the value of the isikudEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudEesnimi(String value) {
                this.isikudEesnimi = value;
            }

            /**
             * Gets the value of the isikudPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudPerenimi() {
                return isikudPerenimi;
            }

            /**
             * Sets the value of the isikudPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudPerenimi(String value) {
                this.isikudPerenimi = value;
            }

            /**
             * Gets the value of the isikudIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudIsikukood() {
                return isikudIsikukood;
            }

            /**
             * Sets the value of the isikudIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudIsikukood(String value) {
                this.isikudIsikukood = value;
            }

            /**
             * Gets the value of the isikudSugu property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudSugu() {
                return isikudSugu;
            }

            /**
             * Sets the value of the isikudSugu property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudSugu(String value) {
                this.isikudSugu = value;
            }

            /**
             * Gets the value of the isikudSynniaeg property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudSynniaeg() {
                return isikudSynniaeg;
            }

            /**
             * Sets the value of the isikudSynniaeg property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudSynniaeg(String value) {
                this.isikudSynniaeg = value;
            }

            /**
             * Gets the value of the isikudVanaeesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudVanaeesnimi() {
                return isikudVanaeesnimi;
            }

            /**
             * Sets the value of the isikudVanaeesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudVanaeesnimi(String value) {
                this.isikudVanaeesnimi = value;
            }

            /**
             * Gets the value of the isikudVanaperenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudVanaperenimi() {
                return isikudVanaperenimi;
            }

            /**
             * Sets the value of the isikudVanaperenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudVanaperenimi(String value) {
                this.isikudVanaperenimi = value;
            }

            /**
             * Gets the value of the isikudIstaatuskd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudIstaatuskd() {
                return isikudIstaatuskd;
            }

            /**
             * Sets the value of the isikudIstaatuskd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudIstaatuskd(String value) {
                this.isikudIstaatuskd = value;
            }

            /**
             * Gets the value of the isikudIstaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudIstaatus() {
                return isikudIstaatus;
            }

            /**
             * Sets the value of the isikudIstaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudIstaatus(String value) {
                this.isikudIstaatus = value;
            }

            /**
             * Gets the value of the isikudKstaatuskd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudKstaatuskd() {
                return isikudKstaatuskd;
            }

            /**
             * Sets the value of the isikudKstaatuskd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudKstaatuskd(String value) {
                this.isikudKstaatuskd = value;
            }

            /**
             * Gets the value of the isikudKstaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudKstaatus() {
                return isikudKstaatus;
            }

            /**
             * Sets the value of the isikudKstaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudKstaatus(String value) {
                this.isikudKstaatus = value;
            }

            /**
             * Gets the value of the isikudKodakondsus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudKodakondsus() {
                return isikudKodakondsus;
            }

            /**
             * Sets the value of the isikudKodakondsus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudKodakondsus(String value) {
                this.isikudKodakondsus = value;
            }

            /**
             * Gets the value of the isikudEOkuni property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudEOkuni() {
                return isikudEOkuni;
            }

            /**
             * Sets the value of the isikudEOkuni property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudEOkuni(String value) {
                this.isikudEOkuni = value;
            }

            /**
             * Gets the value of the isikudEOalates property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudEOalates() {
                return isikudEOalates;
            }

            /**
             * Sets the value of the isikudEOalates property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudEOalates(String value) {
                this.isikudEOalates = value;
            }

            /**
             * Gets the value of the isikudEOstaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudEOstaatus() {
                return isikudEOstaatus;
            }

            /**
             * Sets the value of the isikudEOstaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudEOstaatus(String value) {
                this.isikudEOstaatus = value;
            }

            /**
             * Gets the value of the isikudElukohtRiikkd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudElukohtRiikkd() {
                return isikudElukohtRiikkd;
            }

            /**
             * Sets the value of the isikudElukohtRiikkd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudElukohtRiikkd(String value) {
                this.isikudElukohtRiikkd = value;
            }

            /**
             * Gets the value of the isikudElukohtMaakondkd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudElukohtMaakondkd() {
                return isikudElukohtMaakondkd;
            }

            /**
             * Sets the value of the isikudElukohtMaakondkd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudElukohtMaakondkd(String value) {
                this.isikudElukohtMaakondkd = value;
            }

            /**
             * Gets the value of the isikudElukohtMaakond property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudElukohtMaakond() {
                return isikudElukohtMaakond;
            }

            /**
             * Sets the value of the isikudElukohtMaakond property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudElukohtMaakond(String value) {
                this.isikudElukohtMaakond = value;
            }

            /**
             * Gets the value of the isikudElukohtLinn property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudElukohtLinn() {
                return isikudElukohtLinn;
            }

            /**
             * Sets the value of the isikudElukohtLinn property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudElukohtLinn(String value) {
                this.isikudElukohtLinn = value;
            }

            /**
             * Gets the value of the isikudElukohtAsula property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudElukohtAsula() {
                return isikudElukohtAsula;
            }

            /**
             * Sets the value of the isikudElukohtAsula property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudElukohtAsula(String value) {
                this.isikudElukohtAsula = value;
            }

            /**
             * Gets the value of the isikudElukohtTanav property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudElukohtTanav() {
                return isikudElukohtTanav;
            }

            /**
             * Sets the value of the isikudElukohtTanav property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudElukohtTanav(String value) {
                this.isikudElukohtTanav = value;
            }

            /**
             * Gets the value of the isikudElukohtMaja property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudElukohtMaja() {
                return isikudElukohtMaja;
            }

            /**
             * Sets the value of the isikudElukohtMaja property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudElukohtMaja(String value) {
                this.isikudElukohtMaja = value;
            }

            /**
             * Gets the value of the isikudElukohtKorter property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudElukohtKorter() {
                return isikudElukohtKorter;
            }

            /**
             * Sets the value of the isikudElukohtKorter property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudElukohtKorter(String value) {
                this.isikudElukohtKorter = value;
            }

            /**
             * Gets the value of the isikudElukohtSihtnumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudElukohtSihtnumber() {
                return isikudElukohtSihtnumber;
            }

            /**
             * Sets the value of the isikudElukohtSihtnumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudElukohtSihtnumber(String value) {
                this.isikudElukohtSihtnumber = value;
            }

            /**
             * Gets the value of the isikudElukohtStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudElukohtStaatus() {
                return isikudElukohtStaatus;
            }

            /**
             * Sets the value of the isikudElukohtStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudElukohtStaatus(String value) {
                this.isikudElukohtStaatus = value;
            }

            /**
             * Gets the value of the isikudElukohtAlates property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudElukohtAlates() {
                return isikudElukohtAlates;
            }

            /**
             * Sets the value of the isikudElukohtAlates property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudElukohtAlates(String value) {
                this.isikudElukohtAlates = value;
            }

        }

    }

}
