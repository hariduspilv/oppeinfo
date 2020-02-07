
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR52ResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR52ResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="veakood" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
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
 *                             &lt;element name="isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isik.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isik.Eesti_aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isik.Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isik.Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isik.Perekonnaseis" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isik.Teovoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *         &lt;element name="dokumendid"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="dok" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="dok.DokTyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dok.DokSeeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dok.DokNr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dok.DokAsutusRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dok.DokAsutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dok.DokValjastamisKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dok.DokKehtAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dok.DokKehtLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dok.DokStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *                             &lt;element name="suhe.Suhtetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="suhe.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="suhe.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="suhe.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="suhe.Isanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="suhe.Eesti_aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="suhe.Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="suhe.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="suhe.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR52ResponseType", propOrder = {
    "veakood",
    "veatekst",
    "isikud",
    "dokumendid",
    "suhted"
})
public class RR52ResponseType
    extends XRoadResponseBaseType
{

    protected BigInteger veakood;
    protected String veatekst;
    @XmlElement(required = true)
    protected RR52ResponseType.Isikud isikud;
    @XmlElement(required = true)
    protected RR52ResponseType.Dokumendid dokumendid;
    @XmlElement(required = true)
    protected RR52ResponseType.Suhted suhted;

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
     *     {@link RR52ResponseType.Isikud }
     *     
     */
    public RR52ResponseType.Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR52ResponseType.Isikud }
     *     
     */
    public void setIsikud(RR52ResponseType.Isikud value) {
        this.isikud = value;
    }

    /**
     * Gets the value of the dokumendid property.
     * 
     * @return
     *     possible object is
     *     {@link RR52ResponseType.Dokumendid }
     *     
     */
    public RR52ResponseType.Dokumendid getDokumendid() {
        return dokumendid;
    }

    /**
     * Sets the value of the dokumendid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR52ResponseType.Dokumendid }
     *     
     */
    public void setDokumendid(RR52ResponseType.Dokumendid value) {
        this.dokumendid = value;
    }

    /**
     * Gets the value of the suhted property.
     * 
     * @return
     *     possible object is
     *     {@link RR52ResponseType.Suhted }
     *     
     */
    public RR52ResponseType.Suhted getSuhted() {
        return suhted;
    }

    /**
     * Sets the value of the suhted property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR52ResponseType.Suhted }
     *     
     */
    public void setSuhted(RR52ResponseType.Suhted value) {
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
     *         &lt;element name="dok" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="dok.DokTyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dok.DokSeeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dok.DokNr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dok.DokAsutusRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dok.DokAsutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dok.DokValjastamisKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dok.DokKehtAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dok.DokKehtLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dok.DokStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "dok"
    })
    public static class Dokumendid {

        protected List<RR52ResponseType.Dokumendid.Dok> dok;

        /**
         * Gets the value of the dok property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the dok property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDok().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR52ResponseType.Dokumendid.Dok }
         * 
         * 
         */
        public List<RR52ResponseType.Dokumendid.Dok> getDok() {
            if (dok == null) {
                dok = new ArrayList<RR52ResponseType.Dokumendid.Dok>();
            }
            return this.dok;
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
         *         &lt;element name="dok.DokTyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dok.DokSeeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dok.DokNr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dok.DokAsutusRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dok.DokAsutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dok.DokValjastamisKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dok.DokKehtAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dok.DokKehtLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dok.DokStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "dokDokTyyp",
            "dokDokSeeria",
            "dokDokNr",
            "dokDokAsutusRiik",
            "dokDokAsutus",
            "dokDokValjastamisKuup",
            "dokDokKehtAlgus",
            "dokDokKehtLopp",
            "dokDokStaatus"
        })
        public static class Dok {

            @XmlElement(name = "dok.DokTyyp", required = true)
            protected String dokDokTyyp;
            @XmlElement(name = "dok.DokSeeria", required = true)
            protected String dokDokSeeria;
            @XmlElement(name = "dok.DokNr", required = true)
            protected String dokDokNr;
            @XmlElement(name = "dok.DokAsutusRiik", required = true)
            protected String dokDokAsutusRiik;
            @XmlElement(name = "dok.DokAsutus", required = true)
            protected String dokDokAsutus;
            @XmlElement(name = "dok.DokValjastamisKuup", required = true)
            protected String dokDokValjastamisKuup;
            @XmlElement(name = "dok.DokKehtAlgus", required = true)
            protected String dokDokKehtAlgus;
            @XmlElement(name = "dok.DokKehtLopp", required = true)
            protected String dokDokKehtLopp;
            @XmlElement(name = "dok.DokStaatus", required = true)
            protected String dokDokStaatus;

            /**
             * Gets the value of the dokDokTyyp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokTyyp() {
                return dokDokTyyp;
            }

            /**
             * Sets the value of the dokDokTyyp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokTyyp(String value) {
                this.dokDokTyyp = value;
            }

            /**
             * Gets the value of the dokDokSeeria property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokSeeria() {
                return dokDokSeeria;
            }

            /**
             * Sets the value of the dokDokSeeria property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokSeeria(String value) {
                this.dokDokSeeria = value;
            }

            /**
             * Gets the value of the dokDokNr property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokNr() {
                return dokDokNr;
            }

            /**
             * Sets the value of the dokDokNr property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokNr(String value) {
                this.dokDokNr = value;
            }

            /**
             * Gets the value of the dokDokAsutusRiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokAsutusRiik() {
                return dokDokAsutusRiik;
            }

            /**
             * Sets the value of the dokDokAsutusRiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokAsutusRiik(String value) {
                this.dokDokAsutusRiik = value;
            }

            /**
             * Gets the value of the dokDokAsutus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokAsutus() {
                return dokDokAsutus;
            }

            /**
             * Sets the value of the dokDokAsutus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokAsutus(String value) {
                this.dokDokAsutus = value;
            }

            /**
             * Gets the value of the dokDokValjastamisKuup property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokValjastamisKuup() {
                return dokDokValjastamisKuup;
            }

            /**
             * Sets the value of the dokDokValjastamisKuup property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokValjastamisKuup(String value) {
                this.dokDokValjastamisKuup = value;
            }

            /**
             * Gets the value of the dokDokKehtAlgus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokKehtAlgus() {
                return dokDokKehtAlgus;
            }

            /**
             * Sets the value of the dokDokKehtAlgus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokKehtAlgus(String value) {
                this.dokDokKehtAlgus = value;
            }

            /**
             * Gets the value of the dokDokKehtLopp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokKehtLopp() {
                return dokDokKehtLopp;
            }

            /**
             * Sets the value of the dokDokKehtLopp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokKehtLopp(String value) {
                this.dokDokKehtLopp = value;
            }

            /**
             * Gets the value of the dokDokStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokStaatus() {
                return dokDokStaatus;
            }

            /**
             * Sets the value of the dokDokStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokStaatus(String value) {
                this.dokDokStaatus = value;
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
     *         &lt;element name="isik" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.Eesti_aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.Perekonnaseis" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.Teovoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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

        protected List<RR52ResponseType.Isikud.Isik> isik;

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
         * {@link RR52ResponseType.Isikud.Isik }
         * 
         * 
         */
        public List<RR52ResponseType.Isikud.Isik> getIsik() {
            if (isik == null) {
                isik = new ArrayList<RR52ResponseType.Isikud.Isik>();
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
         *         &lt;element name="isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isik.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isik.Eesti_aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isik.Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isik.Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isik.Perekonnaseis" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isik.Teovoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "isikIsikukood",
            "isikStaatus",
            "isikEestiAadress",
            "isikSynnikoht",
            "isikKodakondsus",
            "isikPerekonnaseis",
            "isikTeovoime"
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
            @XmlElement(name = "isik.Isikukood", required = true)
            protected String isikIsikukood;
            @XmlElement(name = "isik.Staatus", required = true)
            protected String isikStaatus;
            @XmlElement(name = "isik.Eesti_aadress", required = true)
            protected String isikEestiAadress;
            @XmlElement(name = "isik.Synnikoht", required = true)
            protected String isikSynnikoht;
            @XmlElement(name = "isik.Kodakondsus", required = true)
            protected String isikKodakondsus;
            @XmlElement(name = "isik.Perekonnaseis", required = true)
            protected String isikPerekonnaseis;
            @XmlElement(name = "isik.Teovoime", required = true)
            protected String isikTeovoime;

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
             * Gets the value of the isikIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikIsikukood() {
                return isikIsikukood;
            }

            /**
             * Sets the value of the isikIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikIsikukood(String value) {
                this.isikIsikukood = value;
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
             * Gets the value of the isikSynnikoht property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSynnikoht() {
                return isikSynnikoht;
            }

            /**
             * Sets the value of the isikSynnikoht property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSynnikoht(String value) {
                this.isikSynnikoht = value;
            }

            /**
             * Gets the value of the isikKodakondsus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikKodakondsus() {
                return isikKodakondsus;
            }

            /**
             * Sets the value of the isikKodakondsus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikKodakondsus(String value) {
                this.isikKodakondsus = value;
            }

            /**
             * Gets the value of the isikPerekonnaseis property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikPerekonnaseis() {
                return isikPerekonnaseis;
            }

            /**
             * Sets the value of the isikPerekonnaseis property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikPerekonnaseis(String value) {
                this.isikPerekonnaseis = value;
            }

            /**
             * Gets the value of the isikTeovoime property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikTeovoime() {
                return isikTeovoime;
            }

            /**
             * Sets the value of the isikTeovoime property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikTeovoime(String value) {
                this.isikTeovoime = value;
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
     *                   &lt;element name="suhe.Suhtetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="suhe.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="suhe.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="suhe.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="suhe.Isanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="suhe.Eesti_aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="suhe.Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="suhe.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="suhe.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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

        protected List<RR52ResponseType.Suhted.Suhe> suhe;

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
         * {@link RR52ResponseType.Suhted.Suhe }
         * 
         * 
         */
        public List<RR52ResponseType.Suhted.Suhe> getSuhe() {
            if (suhe == null) {
                suhe = new ArrayList<RR52ResponseType.Suhted.Suhe>();
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
         *         &lt;element name="suhe.Suhtetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="suhe.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="suhe.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="suhe.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="suhe.Isanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="suhe.Eesti_aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="suhe.Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="suhe.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="suhe.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "suheSuhtetyyp",
            "suheIsikukood",
            "suhePerenimi",
            "suheEesnimi",
            "suheIsanimi",
            "suheEestiAadress",
            "suheSynnikoht",
            "suheIsikuStaatus",
            "suheStaatus"
        })
        public static class Suhe {

            @XmlElement(name = "suhe.Suhtetyyp", required = true)
            protected String suheSuhtetyyp;
            @XmlElement(name = "suhe.Isikukood", required = true)
            protected String suheIsikukood;
            @XmlElement(name = "suhe.Perenimi", required = true)
            protected String suhePerenimi;
            @XmlElement(name = "suhe.Eesnimi", required = true)
            protected String suheEesnimi;
            @XmlElement(name = "suhe.Isanimi", required = true)
            protected String suheIsanimi;
            @XmlElement(name = "suhe.Eesti_aadress", required = true)
            protected String suheEestiAadress;
            @XmlElement(name = "suhe.Synnikoht", required = true)
            protected String suheSynnikoht;
            @XmlElement(name = "suhe.IsikuStaatus", required = true)
            protected String suheIsikuStaatus;
            @XmlElement(name = "suhe.Staatus", required = true)
            protected String suheStaatus;

            /**
             * Gets the value of the suheSuhtetyyp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheSuhtetyyp() {
                return suheSuhtetyyp;
            }

            /**
             * Sets the value of the suheSuhtetyyp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheSuhtetyyp(String value) {
                this.suheSuhtetyyp = value;
            }

            /**
             * Gets the value of the suheIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheIsikukood() {
                return suheIsikukood;
            }

            /**
             * Sets the value of the suheIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheIsikukood(String value) {
                this.suheIsikukood = value;
            }

            /**
             * Gets the value of the suhePerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuhePerenimi() {
                return suhePerenimi;
            }

            /**
             * Sets the value of the suhePerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuhePerenimi(String value) {
                this.suhePerenimi = value;
            }

            /**
             * Gets the value of the suheEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheEesnimi() {
                return suheEesnimi;
            }

            /**
             * Sets the value of the suheEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheEesnimi(String value) {
                this.suheEesnimi = value;
            }

            /**
             * Gets the value of the suheIsanimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheIsanimi() {
                return suheIsanimi;
            }

            /**
             * Sets the value of the suheIsanimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheIsanimi(String value) {
                this.suheIsanimi = value;
            }

            /**
             * Gets the value of the suheEestiAadress property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheEestiAadress() {
                return suheEestiAadress;
            }

            /**
             * Sets the value of the suheEestiAadress property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheEestiAadress(String value) {
                this.suheEestiAadress = value;
            }

            /**
             * Gets the value of the suheSynnikoht property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheSynnikoht() {
                return suheSynnikoht;
            }

            /**
             * Sets the value of the suheSynnikoht property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheSynnikoht(String value) {
                this.suheSynnikoht = value;
            }

            /**
             * Gets the value of the suheIsikuStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheIsikuStaatus() {
                return suheIsikuStaatus;
            }

            /**
             * Sets the value of the suheIsikuStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheIsikuStaatus(String value) {
                this.suheIsikuStaatus = value;
            }

            /**
             * Gets the value of the suheStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheStaatus() {
                return suheStaatus;
            }

            /**
             * Sets the value of the suheStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheStaatus(String value) {
                this.suheStaatus = value;
            }

        }

    }

}
