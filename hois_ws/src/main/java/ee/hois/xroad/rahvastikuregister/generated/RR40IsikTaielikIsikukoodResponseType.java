
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR40isikTaielikIsikukoodResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR40isikTaielikIsikukoodResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="veakood" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="veatekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isikud"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="isik" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isik.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isik.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isik.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isik.EestiAadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isik.DokumendiTyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isik.DokumendiSeeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isik.DokumendiNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isik.DokumendiValjastKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isik.DokumendiKehtivuseAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isik.DokumendiKehtivuseLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isik.DokumendiStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isik.Rahvus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isik.Emakeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isik.Haridus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isik.Tegevusala" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *         &lt;element name="suhted"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="suhe" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="suhe.ssuhtetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="suhe.sIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="suhe.sPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="suhe.sEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="suhe.sIsanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="suhe.sEesti_aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="suhe.sStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR40isikTaielikIsikukoodResponseType", propOrder = {
    "veakood",
    "veatekst",
    "isikud",
    "suhted"
})
public class RR40IsikTaielikIsikukoodResponseType
    extends XRoadResponseBaseType
{

    protected Integer veakood;
    protected String veatekst;
    @XmlElement(required = true)
    protected RR40IsikTaielikIsikukoodResponseType.Isikud isikud;
    @XmlElement(required = true)
    protected RR40IsikTaielikIsikukoodResponseType.Suhted suhted;

    /**
     * Gets the value of the veakood property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVeakood() {
        return veakood;
    }

    /**
     * Sets the value of the veakood property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVeakood(Integer value) {
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
     *     {@link RR40IsikTaielikIsikukoodResponseType.Isikud }
     *     
     */
    public RR40IsikTaielikIsikukoodResponseType.Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR40IsikTaielikIsikukoodResponseType.Isikud }
     *     
     */
    public void setIsikud(RR40IsikTaielikIsikukoodResponseType.Isikud value) {
        this.isikud = value;
    }

    /**
     * Gets the value of the suhted property.
     * 
     * @return
     *     possible object is
     *     {@link RR40IsikTaielikIsikukoodResponseType.Suhted }
     *     
     */
    public RR40IsikTaielikIsikukoodResponseType.Suhted getSuhted() {
        return suhted;
    }

    /**
     * Sets the value of the suhted property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR40IsikTaielikIsikukoodResponseType.Suhted }
     *     
     */
    public void setSuhted(RR40IsikTaielikIsikukoodResponseType.Suhted value) {
        this.suhted = value;
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
     *         &lt;element name="isik" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.EestiAadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.DokumendiTyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.DokumendiSeeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.DokumendiNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.DokumendiValjastKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.DokumendiKehtivuseAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.DokumendiKehtivuseLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.DokumendiStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.Rahvus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.Emakeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.Haridus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.Tegevusala" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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

        protected List<RR40IsikTaielikIsikukoodResponseType.Isikud.Isik> isik;

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
         * {@link RR40IsikTaielikIsikukoodResponseType.Isikud.Isik }
         * 
         * 
         */
        public List<RR40IsikTaielikIsikukoodResponseType.Isikud.Isik> getIsik() {
            if (isik == null) {
                isik = new ArrayList<RR40IsikTaielikIsikukoodResponseType.Isikud.Isik>();
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
         *         &lt;element name="isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isik.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isik.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isik.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isik.EestiAadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isik.DokumendiTyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isik.DokumendiSeeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isik.DokumendiNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isik.DokumendiValjastKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isik.DokumendiKehtivuseAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isik.DokumendiKehtivuseLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isik.DokumendiStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isik.Rahvus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isik.Emakeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isik.Haridus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isik.Tegevusala" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "isikPerenimi",
            "isikEesnimi",
            "isikSugu",
            "isikSynniaeg",
            "isikStaatus",
            "isikEestiAadress",
            "isikDokumendiTyyp",
            "isikDokumendiSeeria",
            "isikDokumendiNumber",
            "isikDokumendiValjastKuup",
            "isikDokumendiKehtivuseAlgus",
            "isikDokumendiKehtivuseLopp",
            "isikDokumendiStaatus",
            "isikRahvus",
            "isikEmakeel",
            "isikHaridus",
            "isikTegevusala"
        })
        public static class Isik {

            @XmlElement(name = "isik.Perenimi", required = true)
            protected String isikPerenimi;
            @XmlElement(name = "isik.Eesnimi", required = true)
            protected String isikEesnimi;
            @XmlElement(name = "isik.Sugu", required = true)
            protected String isikSugu;
            @XmlElement(name = "isik.Synniaeg", required = true)
            protected String isikSynniaeg;
            @XmlElement(name = "isik.Staatus", required = true)
            protected String isikStaatus;
            @XmlElement(name = "isik.EestiAadress", required = true)
            protected String isikEestiAadress;
            @XmlElement(name = "isik.DokumendiTyyp", required = true)
            protected String isikDokumendiTyyp;
            @XmlElement(name = "isik.DokumendiSeeria", required = true)
            protected String isikDokumendiSeeria;
            @XmlElement(name = "isik.DokumendiNumber", required = true)
            protected String isikDokumendiNumber;
            @XmlElement(name = "isik.DokumendiValjastKuup", required = true)
            protected String isikDokumendiValjastKuup;
            @XmlElement(name = "isik.DokumendiKehtivuseAlgus", required = true)
            protected String isikDokumendiKehtivuseAlgus;
            @XmlElement(name = "isik.DokumendiKehtivuseLopp", required = true)
            protected String isikDokumendiKehtivuseLopp;
            @XmlElement(name = "isik.DokumendiStaatus", required = true)
            protected String isikDokumendiStaatus;
            @XmlElement(name = "isik.Rahvus", required = true)
            protected String isikRahvus;
            @XmlElement(name = "isik.Emakeel", required = true)
            protected String isikEmakeel;
            @XmlElement(name = "isik.Haridus", required = true)
            protected String isikHaridus;
            @XmlElement(name = "isik.Tegevusala", required = true)
            protected String isikTegevusala;

            /**
             * Gets the value of the isikPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikPerenimi() {
                return isikPerenimi;
            }

            /**
             * Sets the value of the isikPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikPerenimi(String value) {
                this.isikPerenimi = value;
            }

            /**
             * Gets the value of the isikEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEesnimi() {
                return isikEesnimi;
            }

            /**
             * Sets the value of the isikEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEesnimi(String value) {
                this.isikEesnimi = value;
            }

            /**
             * Gets the value of the isikSugu property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSugu() {
                return isikSugu;
            }

            /**
             * Sets the value of the isikSugu property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSugu(String value) {
                this.isikSugu = value;
            }

            /**
             * Gets the value of the isikSynniaeg property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSynniaeg() {
                return isikSynniaeg;
            }

            /**
             * Sets the value of the isikSynniaeg property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSynniaeg(String value) {
                this.isikSynniaeg = value;
            }

            /**
             * Gets the value of the isikStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikStaatus() {
                return isikStaatus;
            }

            /**
             * Sets the value of the isikStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikStaatus(String value) {
                this.isikStaatus = value;
            }

            /**
             * Gets the value of the isikEestiAadress property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEestiAadress() {
                return isikEestiAadress;
            }

            /**
             * Sets the value of the isikEestiAadress property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEestiAadress(String value) {
                this.isikEestiAadress = value;
            }

            /**
             * Gets the value of the isikDokumendiTyyp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikDokumendiTyyp() {
                return isikDokumendiTyyp;
            }

            /**
             * Sets the value of the isikDokumendiTyyp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikDokumendiTyyp(String value) {
                this.isikDokumendiTyyp = value;
            }

            /**
             * Gets the value of the isikDokumendiSeeria property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikDokumendiSeeria() {
                return isikDokumendiSeeria;
            }

            /**
             * Sets the value of the isikDokumendiSeeria property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikDokumendiSeeria(String value) {
                this.isikDokumendiSeeria = value;
            }

            /**
             * Gets the value of the isikDokumendiNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikDokumendiNumber() {
                return isikDokumendiNumber;
            }

            /**
             * Sets the value of the isikDokumendiNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikDokumendiNumber(String value) {
                this.isikDokumendiNumber = value;
            }

            /**
             * Gets the value of the isikDokumendiValjastKuup property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikDokumendiValjastKuup() {
                return isikDokumendiValjastKuup;
            }

            /**
             * Sets the value of the isikDokumendiValjastKuup property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikDokumendiValjastKuup(String value) {
                this.isikDokumendiValjastKuup = value;
            }

            /**
             * Gets the value of the isikDokumendiKehtivuseAlgus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikDokumendiKehtivuseAlgus() {
                return isikDokumendiKehtivuseAlgus;
            }

            /**
             * Sets the value of the isikDokumendiKehtivuseAlgus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikDokumendiKehtivuseAlgus(String value) {
                this.isikDokumendiKehtivuseAlgus = value;
            }

            /**
             * Gets the value of the isikDokumendiKehtivuseLopp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikDokumendiKehtivuseLopp() {
                return isikDokumendiKehtivuseLopp;
            }

            /**
             * Sets the value of the isikDokumendiKehtivuseLopp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikDokumendiKehtivuseLopp(String value) {
                this.isikDokumendiKehtivuseLopp = value;
            }

            /**
             * Gets the value of the isikDokumendiStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikDokumendiStaatus() {
                return isikDokumendiStaatus;
            }

            /**
             * Sets the value of the isikDokumendiStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikDokumendiStaatus(String value) {
                this.isikDokumendiStaatus = value;
            }

            /**
             * Gets the value of the isikRahvus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikRahvus() {
                return isikRahvus;
            }

            /**
             * Sets the value of the isikRahvus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikRahvus(String value) {
                this.isikRahvus = value;
            }

            /**
             * Gets the value of the isikEmakeel property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEmakeel() {
                return isikEmakeel;
            }

            /**
             * Sets the value of the isikEmakeel property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEmakeel(String value) {
                this.isikEmakeel = value;
            }

            /**
             * Gets the value of the isikHaridus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikHaridus() {
                return isikHaridus;
            }

            /**
             * Sets the value of the isikHaridus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikHaridus(String value) {
                this.isikHaridus = value;
            }

            /**
             * Gets the value of the isikTegevusala property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikTegevusala() {
                return isikTegevusala;
            }

            /**
             * Sets the value of the isikTegevusala property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikTegevusala(String value) {
                this.isikTegevusala = value;
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
     *         &lt;element name="suhe" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="suhe.ssuhtetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="suhe.sIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="suhe.sPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="suhe.sEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="suhe.sIsanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="suhe.sEesti_aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="suhe.sStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "suhe"
    })
    public static class Suhted {

        protected List<RR40IsikTaielikIsikukoodResponseType.Suhted.Suhe> suhe;

        /**
         * Gets the value of the suhe property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the suhe property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSuhe().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR40IsikTaielikIsikukoodResponseType.Suhted.Suhe }
         * 
         * 
         */
        public List<RR40IsikTaielikIsikukoodResponseType.Suhted.Suhe> getSuhe() {
            if (suhe == null) {
                suhe = new ArrayList<RR40IsikTaielikIsikukoodResponseType.Suhted.Suhe>();
            }
            return this.suhe;
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
         *         &lt;element name="suhe.ssuhtetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="suhe.sIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="suhe.sPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="suhe.sEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="suhe.sIsanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="suhe.sEesti_aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="suhe.sStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "suheSsuhtetyyp",
            "suheSIsikukood",
            "suheSPerenimi",
            "suheSEesnimi",
            "suheSIsanimi",
            "suheSEestiAadress",
            "suheSStaatus"
        })
        public static class Suhe {

            @XmlElement(name = "suhe.ssuhtetyyp", required = true)
            protected String suheSsuhtetyyp;
            @XmlElement(name = "suhe.sIsikukood", required = true)
            protected String suheSIsikukood;
            @XmlElement(name = "suhe.sPerenimi", required = true)
            protected String suheSPerenimi;
            @XmlElement(name = "suhe.sEesnimi", required = true)
            protected String suheSEesnimi;
            @XmlElement(name = "suhe.sIsanimi", required = true)
            protected String suheSIsanimi;
            @XmlElement(name = "suhe.sEesti_aadress", required = true)
            protected String suheSEestiAadress;
            @XmlElement(name = "suhe.sStaatus", required = true)
            protected String suheSStaatus;

            /**
             * Gets the value of the suheSsuhtetyyp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheSsuhtetyyp() {
                return suheSsuhtetyyp;
            }

            /**
             * Sets the value of the suheSsuhtetyyp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheSsuhtetyyp(String value) {
                this.suheSsuhtetyyp = value;
            }

            /**
             * Gets the value of the suheSIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheSIsikukood() {
                return suheSIsikukood;
            }

            /**
             * Sets the value of the suheSIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheSIsikukood(String value) {
                this.suheSIsikukood = value;
            }

            /**
             * Gets the value of the suheSPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheSPerenimi() {
                return suheSPerenimi;
            }

            /**
             * Sets the value of the suheSPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheSPerenimi(String value) {
                this.suheSPerenimi = value;
            }

            /**
             * Gets the value of the suheSEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheSEesnimi() {
                return suheSEesnimi;
            }

            /**
             * Sets the value of the suheSEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheSEesnimi(String value) {
                this.suheSEesnimi = value;
            }

            /**
             * Gets the value of the suheSIsanimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheSIsanimi() {
                return suheSIsanimi;
            }

            /**
             * Sets the value of the suheSIsanimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheSIsanimi(String value) {
                this.suheSIsanimi = value;
            }

            /**
             * Gets the value of the suheSEestiAadress property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheSEestiAadress() {
                return suheSEestiAadress;
            }

            /**
             * Sets the value of the suheSEestiAadress property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheSEestiAadress(String value) {
                this.suheSEestiAadress = value;
            }

            /**
             * Gets the value of the suheSStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheSStaatus() {
                return suheSStaatus;
            }

            /**
             * Sets the value of the suheSStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheSStaatus(String value) {
                this.suheSStaatus = value;
            }

        }

    }

}
