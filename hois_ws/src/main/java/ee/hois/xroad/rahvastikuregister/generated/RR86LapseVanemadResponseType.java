
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR86LapseVanemadResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR86LapseVanemadResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IsikuIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuTeovoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Veakood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="Veatekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Dokumendid"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Dokumendiandmed" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Dokumendiandmed.Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Seeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.DokStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Asutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.DokValjastamisKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.KehtivAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.KehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.DokMarkus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.DokIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.DokPerekonnanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.DokEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.DokOsalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *         &lt;element name="Suhted"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Suhe" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Suhe.Suhtetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.IsikuTeovoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Alguskp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR86LapseVanemadResponseType", propOrder = {
    "isikuIsikukood",
    "isikuEesnimi",
    "isikuPerenimi",
    "isikuStaatus",
    "isikuTeovoime",
    "veakood",
    "veatekst",
    "dokumendid",
    "suhted"
})
public class RR86LapseVanemadResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "IsikuIsikukood", required = true)
    protected String isikuIsikukood;
    @XmlElement(name = "IsikuEesnimi", required = true)
    protected String isikuEesnimi;
    @XmlElement(name = "IsikuPerenimi", required = true)
    protected String isikuPerenimi;
    @XmlElement(name = "IsikuStaatus", required = true)
    protected String isikuStaatus;
    @XmlElement(name = "IsikuTeovoime", required = true)
    protected String isikuTeovoime;
    @XmlElement(name = "Veakood", required = true)
    protected BigInteger veakood;
    @XmlElement(name = "Veatekst", required = true)
    protected String veatekst;
    @XmlElement(name = "Dokumendid", required = true)
    protected RR86LapseVanemadResponseType.Dokumendid dokumendid;
    @XmlElement(name = "Suhted", required = true)
    protected RR86LapseVanemadResponseType.Suhted suhted;

    /**
     * Gets the value of the isikuIsikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuIsikukood() {
        return isikuIsikukood;
    }

    /**
     * Sets the value of the isikuIsikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuIsikukood(String value) {
        this.isikuIsikukood = value;
    }

    /**
     * Gets the value of the isikuEesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuEesnimi() {
        return isikuEesnimi;
    }

    /**
     * Sets the value of the isikuEesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuEesnimi(String value) {
        this.isikuEesnimi = value;
    }

    /**
     * Gets the value of the isikuPerenimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuPerenimi() {
        return isikuPerenimi;
    }

    /**
     * Sets the value of the isikuPerenimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuPerenimi(String value) {
        this.isikuPerenimi = value;
    }

    /**
     * Gets the value of the isikuStaatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuStaatus() {
        return isikuStaatus;
    }

    /**
     * Sets the value of the isikuStaatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuStaatus(String value) {
        this.isikuStaatus = value;
    }

    /**
     * Gets the value of the isikuTeovoime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuTeovoime() {
        return isikuTeovoime;
    }

    /**
     * Sets the value of the isikuTeovoime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuTeovoime(String value) {
        this.isikuTeovoime = value;
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
     * Gets the value of the dokumendid property.
     * 
     * @return
     *     possible object is
     *     {@link RR86LapseVanemadResponseType.Dokumendid }
     *     
     */
    public RR86LapseVanemadResponseType.Dokumendid getDokumendid() {
        return dokumendid;
    }

    /**
     * Sets the value of the dokumendid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR86LapseVanemadResponseType.Dokumendid }
     *     
     */
    public void setDokumendid(RR86LapseVanemadResponseType.Dokumendid value) {
        this.dokumendid = value;
    }

    /**
     * Gets the value of the suhted property.
     * 
     * @return
     *     possible object is
     *     {@link RR86LapseVanemadResponseType.Suhted }
     *     
     */
    public RR86LapseVanemadResponseType.Suhted getSuhted() {
        return suhted;
    }

    /**
     * Sets the value of the suhted property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR86LapseVanemadResponseType.Suhted }
     *     
     */
    public void setSuhted(RR86LapseVanemadResponseType.Suhted value) {
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
     *         &lt;element name="Dokumendiandmed" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Dokumendiandmed.Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Seeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.DokStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Asutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.DokValjastamisKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.KehtivAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.KehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.DokMarkus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.DokIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.DokPerekonnanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.DokEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.DokOsalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "dokumendiandmed"
    })
    public static class Dokumendid {

        @XmlElement(name = "Dokumendiandmed")
        protected List<RR86LapseVanemadResponseType.Dokumendid.Dokumendiandmed> dokumendiandmed;

        /**
         * Gets the value of the dokumendiandmed property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the dokumendiandmed property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDokumendiandmed().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR86LapseVanemadResponseType.Dokumendid.Dokumendiandmed }
         * 
         * 
         */
        public List<RR86LapseVanemadResponseType.Dokumendid.Dokumendiandmed> getDokumendiandmed() {
            if (dokumendiandmed == null) {
                dokumendiandmed = new ArrayList<RR86LapseVanemadResponseType.Dokumendid.Dokumendiandmed>();
            }
            return this.dokumendiandmed;
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
         *         &lt;element name="Dokumendiandmed.Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Seeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.DokStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Asutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.DokValjastamisKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.KehtivAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.KehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.DokMarkus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.DokIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.DokPerekonnanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.DokEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.DokOsalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "dokumendiandmedKood",
            "dokumendiandmedNimetus",
            "dokumendiandmedSeeria",
            "dokumendiandmedNumber",
            "dokumendiandmedDokStaatus",
            "dokumendiandmedAsutus",
            "dokumendiandmedDokValjastamisKuup",
            "dokumendiandmedKehtivAlates",
            "dokumendiandmedKehtivKuni",
            "dokumendiandmedDokMarkus",
            "dokumendiandmedDokIsikukood",
            "dokumendiandmedDokPerekonnanimi",
            "dokumendiandmedDokEesnimi",
            "dokumendiandmedDokOsalus"
        })
        public static class Dokumendiandmed {

            @XmlElement(name = "Dokumendiandmed.Kood", required = true)
            protected String dokumendiandmedKood;
            @XmlElement(name = "Dokumendiandmed.Nimetus", required = true)
            protected String dokumendiandmedNimetus;
            @XmlElement(name = "Dokumendiandmed.Seeria", required = true)
            protected String dokumendiandmedSeeria;
            @XmlElement(name = "Dokumendiandmed.Number", required = true)
            protected String dokumendiandmedNumber;
            @XmlElement(name = "Dokumendiandmed.DokStaatus", required = true)
            protected String dokumendiandmedDokStaatus;
            @XmlElement(name = "Dokumendiandmed.Asutus", required = true)
            protected String dokumendiandmedAsutus;
            @XmlElement(name = "Dokumendiandmed.DokValjastamisKuup", required = true)
            protected String dokumendiandmedDokValjastamisKuup;
            @XmlElement(name = "Dokumendiandmed.KehtivAlates", required = true)
            protected String dokumendiandmedKehtivAlates;
            @XmlElement(name = "Dokumendiandmed.KehtivKuni", required = true)
            protected String dokumendiandmedKehtivKuni;
            @XmlElement(name = "Dokumendiandmed.DokMarkus", required = true)
            protected String dokumendiandmedDokMarkus;
            @XmlElement(name = "Dokumendiandmed.DokIsikukood", required = true)
            protected String dokumendiandmedDokIsikukood;
            @XmlElement(name = "Dokumendiandmed.DokPerekonnanimi", required = true)
            protected String dokumendiandmedDokPerekonnanimi;
            @XmlElement(name = "Dokumendiandmed.DokEesnimi", required = true)
            protected String dokumendiandmedDokEesnimi;
            @XmlElement(name = "Dokumendiandmed.DokOsalus", required = true)
            protected String dokumendiandmedDokOsalus;

            /**
             * Gets the value of the dokumendiandmedKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedKood() {
                return dokumendiandmedKood;
            }

            /**
             * Sets the value of the dokumendiandmedKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedKood(String value) {
                this.dokumendiandmedKood = value;
            }

            /**
             * Gets the value of the dokumendiandmedNimetus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedNimetus() {
                return dokumendiandmedNimetus;
            }

            /**
             * Sets the value of the dokumendiandmedNimetus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedNimetus(String value) {
                this.dokumendiandmedNimetus = value;
            }

            /**
             * Gets the value of the dokumendiandmedSeeria property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedSeeria() {
                return dokumendiandmedSeeria;
            }

            /**
             * Sets the value of the dokumendiandmedSeeria property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedSeeria(String value) {
                this.dokumendiandmedSeeria = value;
            }

            /**
             * Gets the value of the dokumendiandmedNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedNumber() {
                return dokumendiandmedNumber;
            }

            /**
             * Sets the value of the dokumendiandmedNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedNumber(String value) {
                this.dokumendiandmedNumber = value;
            }

            /**
             * Gets the value of the dokumendiandmedDokStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedDokStaatus() {
                return dokumendiandmedDokStaatus;
            }

            /**
             * Sets the value of the dokumendiandmedDokStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedDokStaatus(String value) {
                this.dokumendiandmedDokStaatus = value;
            }

            /**
             * Gets the value of the dokumendiandmedAsutus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedAsutus() {
                return dokumendiandmedAsutus;
            }

            /**
             * Sets the value of the dokumendiandmedAsutus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedAsutus(String value) {
                this.dokumendiandmedAsutus = value;
            }

            /**
             * Gets the value of the dokumendiandmedDokValjastamisKuup property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedDokValjastamisKuup() {
                return dokumendiandmedDokValjastamisKuup;
            }

            /**
             * Sets the value of the dokumendiandmedDokValjastamisKuup property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedDokValjastamisKuup(String value) {
                this.dokumendiandmedDokValjastamisKuup = value;
            }

            /**
             * Gets the value of the dokumendiandmedKehtivAlates property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedKehtivAlates() {
                return dokumendiandmedKehtivAlates;
            }

            /**
             * Sets the value of the dokumendiandmedKehtivAlates property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedKehtivAlates(String value) {
                this.dokumendiandmedKehtivAlates = value;
            }

            /**
             * Gets the value of the dokumendiandmedKehtivKuni property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedKehtivKuni() {
                return dokumendiandmedKehtivKuni;
            }

            /**
             * Sets the value of the dokumendiandmedKehtivKuni property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedKehtivKuni(String value) {
                this.dokumendiandmedKehtivKuni = value;
            }

            /**
             * Gets the value of the dokumendiandmedDokMarkus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedDokMarkus() {
                return dokumendiandmedDokMarkus;
            }

            /**
             * Sets the value of the dokumendiandmedDokMarkus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedDokMarkus(String value) {
                this.dokumendiandmedDokMarkus = value;
            }

            /**
             * Gets the value of the dokumendiandmedDokIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedDokIsikukood() {
                return dokumendiandmedDokIsikukood;
            }

            /**
             * Sets the value of the dokumendiandmedDokIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedDokIsikukood(String value) {
                this.dokumendiandmedDokIsikukood = value;
            }

            /**
             * Gets the value of the dokumendiandmedDokPerekonnanimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedDokPerekonnanimi() {
                return dokumendiandmedDokPerekonnanimi;
            }

            /**
             * Sets the value of the dokumendiandmedDokPerekonnanimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedDokPerekonnanimi(String value) {
                this.dokumendiandmedDokPerekonnanimi = value;
            }

            /**
             * Gets the value of the dokumendiandmedDokEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedDokEesnimi() {
                return dokumendiandmedDokEesnimi;
            }

            /**
             * Sets the value of the dokumendiandmedDokEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedDokEesnimi(String value) {
                this.dokumendiandmedDokEesnimi = value;
            }

            /**
             * Gets the value of the dokumendiandmedDokOsalus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedDokOsalus() {
                return dokumendiandmedDokOsalus;
            }

            /**
             * Sets the value of the dokumendiandmedDokOsalus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedDokOsalus(String value) {
                this.dokumendiandmedDokOsalus = value;
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
     *         &lt;element name="Suhe" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Suhe.Suhtetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.IsikuTeovoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Alguskp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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

        @XmlElement(name = "Suhe")
        protected List<RR86LapseVanemadResponseType.Suhted.Suhe> suhe;

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
         * {@link RR86LapseVanemadResponseType.Suhted.Suhe }
         * 
         * 
         */
        public List<RR86LapseVanemadResponseType.Suhted.Suhe> getSuhe() {
            if (suhe == null) {
                suhe = new ArrayList<RR86LapseVanemadResponseType.Suhted.Suhe>();
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
         *         &lt;element name="Suhe.Suhtetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.IsikuTeovoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Alguskp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "suheIsikuStaatus",
            "suheIsikuTeovoime",
            "suheAlguskp"
        })
        public static class Suhe {

            @XmlElement(name = "Suhe.Suhtetyyp", required = true)
            protected String suheSuhtetyyp;
            @XmlElement(name = "Suhe.Isikukood", required = true)
            protected String suheIsikukood;
            @XmlElement(name = "Suhe.Perenimi", required = true)
            protected String suhePerenimi;
            @XmlElement(name = "Suhe.Eesnimi", required = true)
            protected String suheEesnimi;
            @XmlElement(name = "Suhe.IsikuStaatus", required = true)
            protected String suheIsikuStaatus;
            @XmlElement(name = "Suhe.IsikuTeovoime", required = true)
            protected String suheIsikuTeovoime;
            @XmlElement(name = "Suhe.Alguskp", required = true)
            protected String suheAlguskp;

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
             * Gets the value of the suheIsikuTeovoime property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheIsikuTeovoime() {
                return suheIsikuTeovoime;
            }

            /**
             * Sets the value of the suheIsikuTeovoime property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheIsikuTeovoime(String value) {
                this.suheIsikuTeovoime = value;
            }

            /**
             * Gets the value of the suheAlguskp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheAlguskp() {
                return suheAlguskp;
            }

            /**
             * Sets the value of the suheAlguskp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheAlguskp(String value) {
                this.suheAlguskp = value;
            }

        }

    }

}
