
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR62isikSotsikKPResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR62isikSotsikKPResponseType"&gt;
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
 *                             &lt;element name="Isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Vallakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Kylakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Vaikekoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Tanavakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Tanavanm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Majanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Korternr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Viimatipar" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Mperenm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Meesnm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.IsikKoda" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.SaabEestisse" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.ElukRegpe" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.KirjeStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Surmaaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *         &lt;element name="Dokumendid"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Dok" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Dok.Isik_iskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dok.Dok_iskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dok.Dok_perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dok.Dok_eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dok.Dok_osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dok.Teised_Isik_iskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dok.Teised_Dok_iskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dok.Teised_Dok_perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dok.Teised_Dok_eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dok.Teised_Dok_osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dok.Dok_Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dok.Dok_Seeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dok.Dok_Nr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dok.Dok_kehtiv_alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dok.Dok_kehtiv_kuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dok.Dok_kp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR62isikSotsikKPResponseType", propOrder = {
    "veakood",
    "veatekst",
    "isikud",
    "dokumendid"
})
public class RR62IsikSotsikKPResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Veakood")
    protected BigInteger veakood;
    @XmlElement(name = "Veatekst")
    protected String veatekst;
    @XmlElement(name = "Isikud", required = true)
    protected RR62IsikSotsikKPResponseType.Isikud isikud;
    @XmlElement(name = "Dokumendid", required = true)
    protected RR62IsikSotsikKPResponseType.Dokumendid dokumendid;

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
     *     {@link RR62IsikSotsikKPResponseType.Isikud }
     *     
     */
    public RR62IsikSotsikKPResponseType.Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR62IsikSotsikKPResponseType.Isikud }
     *     
     */
    public void setIsikud(RR62IsikSotsikKPResponseType.Isikud value) {
        this.isikud = value;
    }

    /**
     * Gets the value of the dokumendid property.
     * 
     * @return
     *     possible object is
     *     {@link RR62IsikSotsikKPResponseType.Dokumendid }
     *     
     */
    public RR62IsikSotsikKPResponseType.Dokumendid getDokumendid() {
        return dokumendid;
    }

    /**
     * Sets the value of the dokumendid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR62IsikSotsikKPResponseType.Dokumendid }
     *     
     */
    public void setDokumendid(RR62IsikSotsikKPResponseType.Dokumendid value) {
        this.dokumendid = value;
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
     *         &lt;element name="Dok" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Dok.Isik_iskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dok.Dok_iskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dok.Dok_perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dok.Dok_eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dok.Dok_osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dok.Teised_Isik_iskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dok.Teised_Dok_iskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dok.Teised_Dok_perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dok.Teised_Dok_eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dok.Teised_Dok_osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dok.Dok_Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dok.Dok_Seeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dok.Dok_Nr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dok.Dok_kehtiv_alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dok.Dok_kehtiv_kuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dok.Dok_kp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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

        @XmlElement(name = "Dok")
        protected List<RR62IsikSotsikKPResponseType.Dokumendid.Dok> dok;

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
         * {@link RR62IsikSotsikKPResponseType.Dokumendid.Dok }
         * 
         * 
         */
        public List<RR62IsikSotsikKPResponseType.Dokumendid.Dok> getDok() {
            if (dok == null) {
                dok = new ArrayList<RR62IsikSotsikKPResponseType.Dokumendid.Dok>();
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
         *         &lt;element name="Dok.Isik_iskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dok.Dok_iskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dok.Dok_perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dok.Dok_eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dok.Dok_osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dok.Teised_Isik_iskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dok.Teised_Dok_iskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dok.Teised_Dok_perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dok.Teised_Dok_eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dok.Teised_Dok_osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dok.Dok_Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dok.Dok_Seeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dok.Dok_Nr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dok.Dok_kehtiv_alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dok.Dok_kehtiv_kuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dok.Dok_kp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "dokIsikIskood",
            "dokDokIskood",
            "dokDokPerenimi",
            "dokDokEesnimi",
            "dokDokOsalus",
            "dokTeisedIsikIskood",
            "dokTeisedDokIskood",
            "dokTeisedDokPerenimi",
            "dokTeisedDokEesnimi",
            "dokTeisedDokOsalus",
            "dokDokNimi",
            "dokDokSeeria",
            "dokDokNr",
            "dokDokKehtivAlates",
            "dokDokKehtivKuni",
            "dokDokKp"
        })
        public static class Dok {

            @XmlElement(name = "Dok.Isik_iskood", required = true)
            protected String dokIsikIskood;
            @XmlElement(name = "Dok.Dok_iskood", required = true)
            protected String dokDokIskood;
            @XmlElement(name = "Dok.Dok_perenimi", required = true)
            protected String dokDokPerenimi;
            @XmlElement(name = "Dok.Dok_eesnimi", required = true)
            protected String dokDokEesnimi;
            @XmlElement(name = "Dok.Dok_osalus", required = true)
            protected String dokDokOsalus;
            @XmlElement(name = "Dok.Teised_Isik_iskood", required = true)
            protected String dokTeisedIsikIskood;
            @XmlElement(name = "Dok.Teised_Dok_iskood", required = true)
            protected String dokTeisedDokIskood;
            @XmlElement(name = "Dok.Teised_Dok_perenimi", required = true)
            protected String dokTeisedDokPerenimi;
            @XmlElement(name = "Dok.Teised_Dok_eesnimi", required = true)
            protected String dokTeisedDokEesnimi;
            @XmlElement(name = "Dok.Teised_Dok_osalus", required = true)
            protected String dokTeisedDokOsalus;
            @XmlElement(name = "Dok.Dok_Nimi", required = true)
            protected String dokDokNimi;
            @XmlElement(name = "Dok.Dok_Seeria", required = true)
            protected String dokDokSeeria;
            @XmlElement(name = "Dok.Dok_Nr", required = true)
            protected String dokDokNr;
            @XmlElement(name = "Dok.Dok_kehtiv_alates", required = true)
            protected String dokDokKehtivAlates;
            @XmlElement(name = "Dok.Dok_kehtiv_kuni", required = true)
            protected String dokDokKehtivKuni;
            @XmlElement(name = "Dok.Dok_kp", required = true)
            protected String dokDokKp;

            /**
             * Gets the value of the dokIsikIskood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokIsikIskood() {
                return dokIsikIskood;
            }

            /**
             * Sets the value of the dokIsikIskood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokIsikIskood(String value) {
                this.dokIsikIskood = value;
            }

            /**
             * Gets the value of the dokDokIskood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokIskood() {
                return dokDokIskood;
            }

            /**
             * Sets the value of the dokDokIskood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokIskood(String value) {
                this.dokDokIskood = value;
            }

            /**
             * Gets the value of the dokDokPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokPerenimi() {
                return dokDokPerenimi;
            }

            /**
             * Sets the value of the dokDokPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokPerenimi(String value) {
                this.dokDokPerenimi = value;
            }

            /**
             * Gets the value of the dokDokEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokEesnimi() {
                return dokDokEesnimi;
            }

            /**
             * Sets the value of the dokDokEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokEesnimi(String value) {
                this.dokDokEesnimi = value;
            }

            /**
             * Gets the value of the dokDokOsalus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokOsalus() {
                return dokDokOsalus;
            }

            /**
             * Sets the value of the dokDokOsalus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokOsalus(String value) {
                this.dokDokOsalus = value;
            }

            /**
             * Gets the value of the dokTeisedIsikIskood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokTeisedIsikIskood() {
                return dokTeisedIsikIskood;
            }

            /**
             * Sets the value of the dokTeisedIsikIskood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokTeisedIsikIskood(String value) {
                this.dokTeisedIsikIskood = value;
            }

            /**
             * Gets the value of the dokTeisedDokIskood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokTeisedDokIskood() {
                return dokTeisedDokIskood;
            }

            /**
             * Sets the value of the dokTeisedDokIskood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokTeisedDokIskood(String value) {
                this.dokTeisedDokIskood = value;
            }

            /**
             * Gets the value of the dokTeisedDokPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokTeisedDokPerenimi() {
                return dokTeisedDokPerenimi;
            }

            /**
             * Sets the value of the dokTeisedDokPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokTeisedDokPerenimi(String value) {
                this.dokTeisedDokPerenimi = value;
            }

            /**
             * Gets the value of the dokTeisedDokEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokTeisedDokEesnimi() {
                return dokTeisedDokEesnimi;
            }

            /**
             * Sets the value of the dokTeisedDokEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokTeisedDokEesnimi(String value) {
                this.dokTeisedDokEesnimi = value;
            }

            /**
             * Gets the value of the dokTeisedDokOsalus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokTeisedDokOsalus() {
                return dokTeisedDokOsalus;
            }

            /**
             * Sets the value of the dokTeisedDokOsalus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokTeisedDokOsalus(String value) {
                this.dokTeisedDokOsalus = value;
            }

            /**
             * Gets the value of the dokDokNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokNimi() {
                return dokDokNimi;
            }

            /**
             * Sets the value of the dokDokNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokNimi(String value) {
                this.dokDokNimi = value;
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
             * Gets the value of the dokDokKehtivAlates property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokKehtivAlates() {
                return dokDokKehtivAlates;
            }

            /**
             * Sets the value of the dokDokKehtivAlates property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokKehtivAlates(String value) {
                this.dokDokKehtivAlates = value;
            }

            /**
             * Gets the value of the dokDokKehtivKuni property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokKehtivKuni() {
                return dokDokKehtivKuni;
            }

            /**
             * Sets the value of the dokDokKehtivKuni property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokKehtivKuni(String value) {
                this.dokDokKehtivKuni = value;
            }

            /**
             * Gets the value of the dokDokKp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokKp() {
                return dokDokKp;
            }

            /**
             * Sets the value of the dokDokKp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokKp(String value) {
                this.dokDokKp = value;
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
     *         &lt;element name="Isik" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Vallakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Kylakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Vaikekoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Tanavakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Tanavanm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Majanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Korternr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Viimatipar" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Mperenm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Meesnm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.IsikKoda" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.SaabEestisse" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.ElukRegpe" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.KirjeStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Surmaaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        protected List<RR62IsikSotsikKPResponseType.Isikud.Isik> isik;

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
         * {@link RR62IsikSotsikKPResponseType.Isikud.Isik }
         * 
         * 
         */
        public List<RR62IsikSotsikKPResponseType.Isikud.Isik> getIsik() {
            if (isik == null) {
                isik = new ArrayList<RR62IsikSotsikKPResponseType.Isikud.Isik>();
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
         *         &lt;element name="Isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Vallakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Kylakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Vaikekoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Tanavakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Tanavanm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Majanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Korternr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Viimatipar" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Mperenm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Meesnm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.IsikKoda" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.SaabEestisse" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.ElukRegpe" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.KirjeStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Surmaaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "isikIsikukood",
            "isikPerenimi",
            "isikEesnimi",
            "isikVallakd",
            "isikKylakd",
            "isikVaikekoht",
            "isikTanavakd",
            "isikTanavanm",
            "isikNimi",
            "isikMajanr",
            "isikKorternr",
            "isikViimatipar",
            "isikMperenm",
            "isikMeesnm",
            "isikIsikKoda",
            "isikSaabEestisse",
            "isikPostiindeks",
            "isikElukRegpe",
            "isikIsikuStaatus",
            "isikKirjeStaatus",
            "isikSynniaeg",
            "isikSurmaaeg"
        })
        public static class Isik {

            @XmlElement(name = "Isik.Isikukood", required = true)
            protected String isikIsikukood;
            @XmlElement(name = "Isik.Perenimi", required = true)
            protected String isikPerenimi;
            @XmlElement(name = "Isik.Eesnimi", required = true)
            protected String isikEesnimi;
            @XmlElement(name = "Isik.Vallakd", required = true)
            protected String isikVallakd;
            @XmlElement(name = "Isik.Kylakd", required = true)
            protected String isikKylakd;
            @XmlElement(name = "Isik.Vaikekoht", required = true)
            protected String isikVaikekoht;
            @XmlElement(name = "Isik.Tanavakd", required = true)
            protected String isikTanavakd;
            @XmlElement(name = "Isik.Tanavanm", required = true)
            protected String isikTanavanm;
            @XmlElement(name = "Isik.Nimi", required = true)
            protected String isikNimi;
            @XmlElement(name = "Isik.Majanr", required = true)
            protected String isikMajanr;
            @XmlElement(name = "Isik.Korternr", required = true)
            protected String isikKorternr;
            @XmlElement(name = "Isik.Viimatipar", required = true)
            protected String isikViimatipar;
            @XmlElement(name = "Isik.Mperenm", required = true)
            protected String isikMperenm;
            @XmlElement(name = "Isik.Meesnm", required = true)
            protected String isikMeesnm;
            @XmlElement(name = "Isik.IsikKoda", required = true)
            protected String isikIsikKoda;
            @XmlElement(name = "Isik.SaabEestisse", required = true)
            protected String isikSaabEestisse;
            @XmlElement(name = "Isik.Postiindeks", required = true)
            protected String isikPostiindeks;
            @XmlElement(name = "Isik.ElukRegpe", required = true)
            protected String isikElukRegpe;
            @XmlElement(name = "Isik.IsikuStaatus", required = true)
            protected String isikIsikuStaatus;
            @XmlElement(name = "Isik.KirjeStaatus", required = true)
            protected String isikKirjeStaatus;
            @XmlElement(name = "Isik.Synniaeg", required = true)
            protected String isikSynniaeg;
            @XmlElement(name = "Isik.Surmaaeg", required = true)
            protected String isikSurmaaeg;

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
             * Gets the value of the isikVallakd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikVallakd() {
                return isikVallakd;
            }

            /**
             * Sets the value of the isikVallakd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikVallakd(String value) {
                this.isikVallakd = value;
            }

            /**
             * Gets the value of the isikKylakd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikKylakd() {
                return isikKylakd;
            }

            /**
             * Sets the value of the isikKylakd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikKylakd(String value) {
                this.isikKylakd = value;
            }

            /**
             * Gets the value of the isikVaikekoht property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikVaikekoht() {
                return isikVaikekoht;
            }

            /**
             * Sets the value of the isikVaikekoht property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikVaikekoht(String value) {
                this.isikVaikekoht = value;
            }

            /**
             * Gets the value of the isikTanavakd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikTanavakd() {
                return isikTanavakd;
            }

            /**
             * Sets the value of the isikTanavakd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikTanavakd(String value) {
                this.isikTanavakd = value;
            }

            /**
             * Gets the value of the isikTanavanm property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikTanavanm() {
                return isikTanavanm;
            }

            /**
             * Sets the value of the isikTanavanm property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikTanavanm(String value) {
                this.isikTanavanm = value;
            }

            /**
             * Gets the value of the isikNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikNimi() {
                return isikNimi;
            }

            /**
             * Sets the value of the isikNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikNimi(String value) {
                this.isikNimi = value;
            }

            /**
             * Gets the value of the isikMajanr property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikMajanr() {
                return isikMajanr;
            }

            /**
             * Sets the value of the isikMajanr property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikMajanr(String value) {
                this.isikMajanr = value;
            }

            /**
             * Gets the value of the isikKorternr property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikKorternr() {
                return isikKorternr;
            }

            /**
             * Sets the value of the isikKorternr property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikKorternr(String value) {
                this.isikKorternr = value;
            }

            /**
             * Gets the value of the isikViimatipar property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikViimatipar() {
                return isikViimatipar;
            }

            /**
             * Sets the value of the isikViimatipar property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikViimatipar(String value) {
                this.isikViimatipar = value;
            }

            /**
             * Gets the value of the isikMperenm property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikMperenm() {
                return isikMperenm;
            }

            /**
             * Sets the value of the isikMperenm property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikMperenm(String value) {
                this.isikMperenm = value;
            }

            /**
             * Gets the value of the isikMeesnm property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikMeesnm() {
                return isikMeesnm;
            }

            /**
             * Sets the value of the isikMeesnm property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikMeesnm(String value) {
                this.isikMeesnm = value;
            }

            /**
             * Gets the value of the isikIsikKoda property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikIsikKoda() {
                return isikIsikKoda;
            }

            /**
             * Sets the value of the isikIsikKoda property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikIsikKoda(String value) {
                this.isikIsikKoda = value;
            }

            /**
             * Gets the value of the isikSaabEestisse property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSaabEestisse() {
                return isikSaabEestisse;
            }

            /**
             * Sets the value of the isikSaabEestisse property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSaabEestisse(String value) {
                this.isikSaabEestisse = value;
            }

            /**
             * Gets the value of the isikPostiindeks property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikPostiindeks() {
                return isikPostiindeks;
            }

            /**
             * Sets the value of the isikPostiindeks property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikPostiindeks(String value) {
                this.isikPostiindeks = value;
            }

            /**
             * Gets the value of the isikElukRegpe property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikElukRegpe() {
                return isikElukRegpe;
            }

            /**
             * Sets the value of the isikElukRegpe property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikElukRegpe(String value) {
                this.isikElukRegpe = value;
            }

            /**
             * Gets the value of the isikIsikuStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikIsikuStaatus() {
                return isikIsikuStaatus;
            }

            /**
             * Sets the value of the isikIsikuStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikIsikuStaatus(String value) {
                this.isikIsikuStaatus = value;
            }

            /**
             * Gets the value of the isikKirjeStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikKirjeStaatus() {
                return isikKirjeStaatus;
            }

            /**
             * Sets the value of the isikKirjeStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikKirjeStaatus(String value) {
                this.isikKirjeStaatus = value;
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
             * Gets the value of the isikSurmaaeg property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSurmaaeg() {
                return isikSurmaaeg;
            }

            /**
             * Sets the value of the isikSurmaaeg property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSurmaaeg(String value) {
                this.isikSurmaaeg = value;
            }

        }

    }

}
