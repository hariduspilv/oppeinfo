
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR443elukohtadegaResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR443elukohtadegaResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Veakood" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="Veatekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Isikuid"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Isikud" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Isikud.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Kstaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Muueesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Muuperenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *         &lt;element name="Aadresse"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Aadressid" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Aadressid.Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Aadressid.Aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Aadressid.Sihtnumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Aadressid.Olek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Aadressid.Periood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR443elukohtadegaResponseType", propOrder = {
    "veakood",
    "veatekst",
    "isikuid",
    "aadresse"
})
public class RR443ElukohtadegaResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Veakood")
    protected BigInteger veakood;
    @XmlElement(name = "Veatekst")
    protected String veatekst;
    @XmlElement(name = "Isikuid", required = true)
    protected RR443ElukohtadegaResponseType.Isikuid isikuid;
    @XmlElement(name = "Aadresse", required = true)
    protected RR443ElukohtadegaResponseType.Aadresse aadresse;

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
     * Gets the value of the isikuid property.
     * 
     * @return
     *     possible object is
     *     {@link RR443ElukohtadegaResponseType.Isikuid }
     *     
     */
    public RR443ElukohtadegaResponseType.Isikuid getIsikuid() {
        return isikuid;
    }

    /**
     * Sets the value of the isikuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR443ElukohtadegaResponseType.Isikuid }
     *     
     */
    public void setIsikuid(RR443ElukohtadegaResponseType.Isikuid value) {
        this.isikuid = value;
    }

    /**
     * Gets the value of the aadresse property.
     * 
     * @return
     *     possible object is
     *     {@link RR443ElukohtadegaResponseType.Aadresse }
     *     
     */
    public RR443ElukohtadegaResponseType.Aadresse getAadresse() {
        return aadresse;
    }

    /**
     * Sets the value of the aadresse property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR443ElukohtadegaResponseType.Aadresse }
     *     
     */
    public void setAadresse(RR443ElukohtadegaResponseType.Aadresse value) {
        this.aadresse = value;
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
     *         &lt;element name="Aadressid" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Aadressid.Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Aadressid.Aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Aadressid.Sihtnumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Aadressid.Olek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Aadressid.Periood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "aadressid"
    })
    public static class Aadresse {

        @XmlElement(name = "Aadressid")
        protected List<RR443ElukohtadegaResponseType.Aadresse.Aadressid> aadressid;

        /**
         * Gets the value of the aadressid property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the aadressid property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAadressid().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR443ElukohtadegaResponseType.Aadresse.Aadressid }
         * 
         * 
         */
        public List<RR443ElukohtadegaResponseType.Aadresse.Aadressid> getAadressid() {
            if (aadressid == null) {
                aadressid = new ArrayList<RR443ElukohtadegaResponseType.Aadresse.Aadressid>();
            }
            return this.aadressid;
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
         *         &lt;element name="Aadressid.Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Aadressid.Aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Aadressid.Sihtnumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Aadressid.Olek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Aadressid.Periood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "aadressidLiik",
            "aadressidAadress",
            "aadressidSihtnumber",
            "aadressidOlek",
            "aadressidPeriood"
        })
        public static class Aadressid {

            @XmlElement(name = "Aadressid.Liik", required = true)
            protected String aadressidLiik;
            @XmlElement(name = "Aadressid.Aadress", required = true)
            protected String aadressidAadress;
            @XmlElement(name = "Aadressid.Sihtnumber", required = true)
            protected String aadressidSihtnumber;
            @XmlElement(name = "Aadressid.Olek", required = true)
            protected String aadressidOlek;
            @XmlElement(name = "Aadressid.Periood", required = true)
            protected String aadressidPeriood;

            /**
             * Gets the value of the aadressidLiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressidLiik() {
                return aadressidLiik;
            }

            /**
             * Sets the value of the aadressidLiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressidLiik(String value) {
                this.aadressidLiik = value;
            }

            /**
             * Gets the value of the aadressidAadress property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressidAadress() {
                return aadressidAadress;
            }

            /**
             * Sets the value of the aadressidAadress property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressidAadress(String value) {
                this.aadressidAadress = value;
            }

            /**
             * Gets the value of the aadressidSihtnumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressidSihtnumber() {
                return aadressidSihtnumber;
            }

            /**
             * Sets the value of the aadressidSihtnumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressidSihtnumber(String value) {
                this.aadressidSihtnumber = value;
            }

            /**
             * Gets the value of the aadressidOlek property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressidOlek() {
                return aadressidOlek;
            }

            /**
             * Sets the value of the aadressidOlek property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressidOlek(String value) {
                this.aadressidOlek = value;
            }

            /**
             * Gets the value of the aadressidPeriood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressidPeriood() {
                return aadressidPeriood;
            }

            /**
             * Sets the value of the aadressidPeriood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressidPeriood(String value) {
                this.aadressidPeriood = value;
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
     *         &lt;element name="Isikud" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Isikud.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Kstaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Muueesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Muuperenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "isikud"
    })
    public static class Isikuid {

        @XmlElement(name = "Isikud")
        protected List<RR443ElukohtadegaResponseType.Isikuid.Isikud> isikud;

        /**
         * Gets the value of the isikud property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the isikud property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIsikud().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR443ElukohtadegaResponseType.Isikuid.Isikud }
         * 
         * 
         */
        public List<RR443ElukohtadegaResponseType.Isikuid.Isikud> getIsikud() {
            if (isikud == null) {
                isikud = new ArrayList<RR443ElukohtadegaResponseType.Isikuid.Isikud>();
            }
            return this.isikud;
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
         *         &lt;element name="Isikud.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Kstaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Muueesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Muuperenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "isikudSynniaeg",
            "isikudKstaatus",
            "isikudMuueesnimi",
            "isikudMuuperenimi"
        })
        public static class Isikud {

            @XmlElement(name = "Isikud.Eesnimi", required = true)
            protected String isikudEesnimi;
            @XmlElement(name = "Isikud.Perenimi", required = true)
            protected String isikudPerenimi;
            @XmlElement(name = "Isikud.Isikukood", required = true)
            protected String isikudIsikukood;
            @XmlElement(name = "Isikud.Synniaeg", required = true)
            protected String isikudSynniaeg;
            @XmlElement(name = "Isikud.Kstaatus", required = true)
            protected String isikudKstaatus;
            @XmlElement(name = "Isikud.Muueesnimi", required = true)
            protected String isikudMuueesnimi;
            @XmlElement(name = "Isikud.Muuperenimi", required = true)
            protected String isikudMuuperenimi;

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
             * Gets the value of the isikudMuueesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudMuueesnimi() {
                return isikudMuueesnimi;
            }

            /**
             * Sets the value of the isikudMuueesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudMuueesnimi(String value) {
                this.isikudMuueesnimi = value;
            }

            /**
             * Gets the value of the isikudMuuperenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudMuuperenimi() {
                return isikudMuuperenimi;
            }

            /**
             * Sets the value of the isikudMuuperenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudMuuperenimi(String value) {
                this.isikudMuuperenimi = value;
            }

        }

    }

}
