
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRKohtuDokumentRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRKohtuDokumentRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Tegevus" type="{http://rr.x-road.eu/producer}KISSyndmusType"/&gt;
 *         &lt;element name="Dokument"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Liik" type="{http://rr.x-road.eu/producer}KodifK"/&gt;
 *                   &lt;element name="DokumendiNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="ValjaandmiseKP" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                   &lt;element name="JoustumiseKP" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                   &lt;element name="KehtetuAlatesKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                   &lt;element name="KoostanudAsutus"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Riik" type="{http://rr.x-road.eu/producer}KodifK"/&gt;
 *                             &lt;element name="AsutuseRegNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="AsutuseRRID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AsutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="AmetnikuIsikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
 *                   &lt;element name="AmetnikuNimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="AbieluDokument" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="DokumendiLiik" type="{http://rr.x-road.eu/producer}KodifK"/&gt;
 *                   &lt;element name="DokumendiNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="ValjaandmiseKP" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                   &lt;element name="JoustumiseKP" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Isikud"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Isik" maxOccurs="2" minOccurs="2"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="NimeMuudatus"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="PerenimiEnne" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="PerenimiParast" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                             &lt;element name="Sugu" type="{http://rr.x-road.eu/producer}SuguType"/&gt;
 *                             &lt;element name="PohiKodakondsus" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Riik" type="{http://rr.x-road.eu/producer}KodifK"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
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
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRKohtuDokumentRequestType", propOrder = {
    "tegevus",
    "dokument",
    "abieluDokument",
    "isikud"
})
public class RRKohtuDokumentRequestType {

    @XmlElement(name = "Tegevus", required = true)
    @XmlSchemaType(name = "string")
    protected KISSyndmusType tegevus;
    @XmlElement(name = "Dokument", required = true)
    protected RRKohtuDokumentRequestType.Dokument dokument;
    @XmlElement(name = "AbieluDokument")
    protected RRKohtuDokumentRequestType.AbieluDokument abieluDokument;
    @XmlElement(name = "Isikud", required = true)
    protected RRKohtuDokumentRequestType.Isikud isikud;

    /**
     * Gets the value of the tegevus property.
     * 
     * @return
     *     possible object is
     *     {@link KISSyndmusType }
     *     
     */
    public KISSyndmusType getTegevus() {
        return tegevus;
    }

    /**
     * Sets the value of the tegevus property.
     * 
     * @param value
     *     allowed object is
     *     {@link KISSyndmusType }
     *     
     */
    public void setTegevus(KISSyndmusType value) {
        this.tegevus = value;
    }

    /**
     * Gets the value of the dokument property.
     * 
     * @return
     *     possible object is
     *     {@link RRKohtuDokumentRequestType.Dokument }
     *     
     */
    public RRKohtuDokumentRequestType.Dokument getDokument() {
        return dokument;
    }

    /**
     * Sets the value of the dokument property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRKohtuDokumentRequestType.Dokument }
     *     
     */
    public void setDokument(RRKohtuDokumentRequestType.Dokument value) {
        this.dokument = value;
    }

    /**
     * Gets the value of the abieluDokument property.
     * 
     * @return
     *     possible object is
     *     {@link RRKohtuDokumentRequestType.AbieluDokument }
     *     
     */
    public RRKohtuDokumentRequestType.AbieluDokument getAbieluDokument() {
        return abieluDokument;
    }

    /**
     * Sets the value of the abieluDokument property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRKohtuDokumentRequestType.AbieluDokument }
     *     
     */
    public void setAbieluDokument(RRKohtuDokumentRequestType.AbieluDokument value) {
        this.abieluDokument = value;
    }

    /**
     * Gets the value of the isikud property.
     * 
     * @return
     *     possible object is
     *     {@link RRKohtuDokumentRequestType.Isikud }
     *     
     */
    public RRKohtuDokumentRequestType.Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRKohtuDokumentRequestType.Isikud }
     *     
     */
    public void setIsikud(RRKohtuDokumentRequestType.Isikud value) {
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
     *         &lt;element name="DokumendiLiik" type="{http://rr.x-road.eu/producer}KodifK"/&gt;
     *         &lt;element name="DokumendiNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="ValjaandmiseKP" type="{http://rr.x-road.eu/producer}date"/&gt;
     *         &lt;element name="JoustumiseKP" type="{http://rr.x-road.eu/producer}date"/&gt;
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
        "dokumendiLiik",
        "dokumendiNumber",
        "valjaandmiseKP",
        "joustumiseKP"
    })
    public static class AbieluDokument {

        @XmlElement(name = "DokumendiLiik", required = true)
        protected KodifK dokumendiLiik;
        @XmlElement(name = "DokumendiNumber", required = true)
        protected String dokumendiNumber;
        @XmlElement(name = "ValjaandmiseKP", required = true)
        protected String valjaandmiseKP;
        @XmlElement(name = "JoustumiseKP", required = true)
        protected String joustumiseKP;

        /**
         * Gets the value of the dokumendiLiik property.
         * 
         * @return
         *     possible object is
         *     {@link KodifK }
         *     
         */
        public KodifK getDokumendiLiik() {
            return dokumendiLiik;
        }

        /**
         * Sets the value of the dokumendiLiik property.
         * 
         * @param value
         *     allowed object is
         *     {@link KodifK }
         *     
         */
        public void setDokumendiLiik(KodifK value) {
            this.dokumendiLiik = value;
        }

        /**
         * Gets the value of the dokumendiNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDokumendiNumber() {
            return dokumendiNumber;
        }

        /**
         * Sets the value of the dokumendiNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDokumendiNumber(String value) {
            this.dokumendiNumber = value;
        }

        /**
         * Gets the value of the valjaandmiseKP property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValjaandmiseKP() {
            return valjaandmiseKP;
        }

        /**
         * Sets the value of the valjaandmiseKP property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValjaandmiseKP(String value) {
            this.valjaandmiseKP = value;
        }

        /**
         * Gets the value of the joustumiseKP property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getJoustumiseKP() {
            return joustumiseKP;
        }

        /**
         * Sets the value of the joustumiseKP property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setJoustumiseKP(String value) {
            this.joustumiseKP = value;
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
     *         &lt;element name="Liik" type="{http://rr.x-road.eu/producer}KodifK"/&gt;
     *         &lt;element name="DokumendiNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="ValjaandmiseKP" type="{http://rr.x-road.eu/producer}date"/&gt;
     *         &lt;element name="JoustumiseKP" type="{http://rr.x-road.eu/producer}date"/&gt;
     *         &lt;element name="KehtetuAlatesKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *         &lt;element name="KoostanudAsutus"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Riik" type="{http://rr.x-road.eu/producer}KodifK"/&gt;
     *                   &lt;element name="AsutuseRegNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="AsutuseRRID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AsutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="AmetnikuIsikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
     *         &lt;element name="AmetnikuNimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "liik",
        "dokumendiNumber",
        "valjaandmiseKP",
        "joustumiseKP",
        "kehtetuAlatesKP",
        "koostanudAsutus",
        "ametnikuIsikukood",
        "ametnikuNimed"
    })
    public static class Dokument {

        @XmlElement(name = "Liik", required = true)
        protected KodifK liik;
        @XmlElement(name = "DokumendiNumber", required = true)
        protected String dokumendiNumber;
        @XmlElement(name = "ValjaandmiseKP", required = true)
        protected String valjaandmiseKP;
        @XmlElement(name = "JoustumiseKP", required = true)
        protected String joustumiseKP;
        @XmlElement(name = "KehtetuAlatesKP")
        protected String kehtetuAlatesKP;
        @XmlElement(name = "KoostanudAsutus", required = true)
        protected RRKohtuDokumentRequestType.Dokument.KoostanudAsutus koostanudAsutus;
        @XmlElement(name = "AmetnikuIsikukood", required = true)
        protected String ametnikuIsikukood;
        @XmlElement(name = "AmetnikuNimed", required = true)
        protected String ametnikuNimed;

        /**
         * Gets the value of the liik property.
         * 
         * @return
         *     possible object is
         *     {@link KodifK }
         *     
         */
        public KodifK getLiik() {
            return liik;
        }

        /**
         * Sets the value of the liik property.
         * 
         * @param value
         *     allowed object is
         *     {@link KodifK }
         *     
         */
        public void setLiik(KodifK value) {
            this.liik = value;
        }

        /**
         * Gets the value of the dokumendiNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDokumendiNumber() {
            return dokumendiNumber;
        }

        /**
         * Sets the value of the dokumendiNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDokumendiNumber(String value) {
            this.dokumendiNumber = value;
        }

        /**
         * Gets the value of the valjaandmiseKP property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValjaandmiseKP() {
            return valjaandmiseKP;
        }

        /**
         * Sets the value of the valjaandmiseKP property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValjaandmiseKP(String value) {
            this.valjaandmiseKP = value;
        }

        /**
         * Gets the value of the joustumiseKP property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getJoustumiseKP() {
            return joustumiseKP;
        }

        /**
         * Sets the value of the joustumiseKP property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setJoustumiseKP(String value) {
            this.joustumiseKP = value;
        }

        /**
         * Gets the value of the kehtetuAlatesKP property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKehtetuAlatesKP() {
            return kehtetuAlatesKP;
        }

        /**
         * Sets the value of the kehtetuAlatesKP property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKehtetuAlatesKP(String value) {
            this.kehtetuAlatesKP = value;
        }

        /**
         * Gets the value of the koostanudAsutus property.
         * 
         * @return
         *     possible object is
         *     {@link RRKohtuDokumentRequestType.Dokument.KoostanudAsutus }
         *     
         */
        public RRKohtuDokumentRequestType.Dokument.KoostanudAsutus getKoostanudAsutus() {
            return koostanudAsutus;
        }

        /**
         * Sets the value of the koostanudAsutus property.
         * 
         * @param value
         *     allowed object is
         *     {@link RRKohtuDokumentRequestType.Dokument.KoostanudAsutus }
         *     
         */
        public void setKoostanudAsutus(RRKohtuDokumentRequestType.Dokument.KoostanudAsutus value) {
            this.koostanudAsutus = value;
        }

        /**
         * Gets the value of the ametnikuIsikukood property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAmetnikuIsikukood() {
            return ametnikuIsikukood;
        }

        /**
         * Sets the value of the ametnikuIsikukood property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAmetnikuIsikukood(String value) {
            this.ametnikuIsikukood = value;
        }

        /**
         * Gets the value of the ametnikuNimed property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAmetnikuNimed() {
            return ametnikuNimed;
        }

        /**
         * Sets the value of the ametnikuNimed property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAmetnikuNimed(String value) {
            this.ametnikuNimed = value;
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
         *         &lt;element name="Riik" type="{http://rr.x-road.eu/producer}KodifK"/&gt;
         *         &lt;element name="AsutuseRegNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="AsutuseRRID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AsutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "riik",
            "asutuseRegNumber",
            "asutuseRRID",
            "asutuseNimi"
        })
        public static class KoostanudAsutus {

            @XmlElement(name = "Riik", required = true)
            protected KodifK riik;
            @XmlElement(name = "AsutuseRegNumber")
            protected String asutuseRegNumber;
            @XmlElement(name = "AsutuseRRID", required = true)
            protected String asutuseRRID;
            @XmlElement(name = "AsutuseNimi", required = true)
            protected String asutuseNimi;

            /**
             * Gets the value of the riik property.
             * 
             * @return
             *     possible object is
             *     {@link KodifK }
             *     
             */
            public KodifK getRiik() {
                return riik;
            }

            /**
             * Sets the value of the riik property.
             * 
             * @param value
             *     allowed object is
             *     {@link KodifK }
             *     
             */
            public void setRiik(KodifK value) {
                this.riik = value;
            }

            /**
             * Gets the value of the asutuseRegNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAsutuseRegNumber() {
                return asutuseRegNumber;
            }

            /**
             * Sets the value of the asutuseRegNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAsutuseRegNumber(String value) {
                this.asutuseRegNumber = value;
            }

            /**
             * Gets the value of the asutuseRRID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAsutuseRRID() {
                return asutuseRRID;
            }

            /**
             * Sets the value of the asutuseRRID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAsutuseRRID(String value) {
                this.asutuseRRID = value;
            }

            /**
             * Gets the value of the asutuseNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAsutuseNimi() {
                return asutuseNimi;
            }

            /**
             * Sets the value of the asutuseNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAsutuseNimi(String value) {
                this.asutuseNimi = value;
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
     *         &lt;element name="Isik" maxOccurs="2" minOccurs="2"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="NimeMuudatus"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="PerenimiEnne" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="PerenimiParast" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
     *                   &lt;element name="Sugu" type="{http://rr.x-road.eu/producer}SuguType"/&gt;
     *                   &lt;element name="PohiKodakondsus" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Riik" type="{http://rr.x-road.eu/producer}KodifK"/&gt;
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

        @XmlElement(name = "Isik", required = true)
        protected List<RRKohtuDokumentRequestType.Isikud.Isik> isik;

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
         * {@link RRKohtuDokumentRequestType.Isikud.Isik }
         * 
         * 
         */
        public List<RRKohtuDokumentRequestType.Isikud.Isik> getIsik() {
            if (isik == null) {
                isik = new ArrayList<RRKohtuDokumentRequestType.Isikud.Isik>();
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
         *         &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="NimeMuudatus"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="PerenimiEnne" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="PerenimiParast" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
         *         &lt;element name="Sugu" type="{http://rr.x-road.eu/producer}SuguType"/&gt;
         *         &lt;element name="PohiKodakondsus" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Riik" type="{http://rr.x-road.eu/producer}KodifK"/&gt;
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
            "isikukood",
            "eesnimi",
            "nimeMuudatus",
            "synniaeg",
            "sugu",
            "pohiKodakondsus"
        })
        public static class Isik {

            @XmlElement(name = "Isikukood", required = true)
            protected String isikukood;
            @XmlElement(name = "Eesnimi", required = true)
            protected String eesnimi;
            @XmlElement(name = "NimeMuudatus", required = true)
            protected RRKohtuDokumentRequestType.Isikud.Isik.NimeMuudatus nimeMuudatus;
            @XmlElement(name = "Synniaeg", required = true)
            protected String synniaeg;
            @XmlElement(name = "Sugu", required = true)
            @XmlSchemaType(name = "string")
            protected SuguType sugu;
            @XmlElement(name = "PohiKodakondsus")
            protected RRKohtuDokumentRequestType.Isikud.Isik.PohiKodakondsus pohiKodakondsus;

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
             * Gets the value of the nimeMuudatus property.
             * 
             * @return
             *     possible object is
             *     {@link RRKohtuDokumentRequestType.Isikud.Isik.NimeMuudatus }
             *     
             */
            public RRKohtuDokumentRequestType.Isikud.Isik.NimeMuudatus getNimeMuudatus() {
                return nimeMuudatus;
            }

            /**
             * Sets the value of the nimeMuudatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link RRKohtuDokumentRequestType.Isikud.Isik.NimeMuudatus }
             *     
             */
            public void setNimeMuudatus(RRKohtuDokumentRequestType.Isikud.Isik.NimeMuudatus value) {
                this.nimeMuudatus = value;
            }

            /**
             * Gets the value of the synniaeg property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSynniaeg() {
                return synniaeg;
            }

            /**
             * Sets the value of the synniaeg property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSynniaeg(String value) {
                this.synniaeg = value;
            }

            /**
             * Gets the value of the sugu property.
             * 
             * @return
             *     possible object is
             *     {@link SuguType }
             *     
             */
            public SuguType getSugu() {
                return sugu;
            }

            /**
             * Sets the value of the sugu property.
             * 
             * @param value
             *     allowed object is
             *     {@link SuguType }
             *     
             */
            public void setSugu(SuguType value) {
                this.sugu = value;
            }

            /**
             * Gets the value of the pohiKodakondsus property.
             * 
             * @return
             *     possible object is
             *     {@link RRKohtuDokumentRequestType.Isikud.Isik.PohiKodakondsus }
             *     
             */
            public RRKohtuDokumentRequestType.Isikud.Isik.PohiKodakondsus getPohiKodakondsus() {
                return pohiKodakondsus;
            }

            /**
             * Sets the value of the pohiKodakondsus property.
             * 
             * @param value
             *     allowed object is
             *     {@link RRKohtuDokumentRequestType.Isikud.Isik.PohiKodakondsus }
             *     
             */
            public void setPohiKodakondsus(RRKohtuDokumentRequestType.Isikud.Isik.PohiKodakondsus value) {
                this.pohiKodakondsus = value;
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
             *         &lt;element name="PerenimiEnne" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="PerenimiParast" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                "perenimiEnne",
                "perenimiParast"
            })
            public static class NimeMuudatus {

                @XmlElement(name = "PerenimiEnne", required = true)
                protected String perenimiEnne;
                @XmlElement(name = "PerenimiParast", required = true)
                protected String perenimiParast;

                /**
                 * Gets the value of the perenimiEnne property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPerenimiEnne() {
                    return perenimiEnne;
                }

                /**
                 * Sets the value of the perenimiEnne property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPerenimiEnne(String value) {
                    this.perenimiEnne = value;
                }

                /**
                 * Gets the value of the perenimiParast property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPerenimiParast() {
                    return perenimiParast;
                }

                /**
                 * Sets the value of the perenimiParast property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPerenimiParast(String value) {
                    this.perenimiParast = value;
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
             *         &lt;element name="Riik" type="{http://rr.x-road.eu/producer}KodifK"/&gt;
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
                "riik"
            })
            public static class PohiKodakondsus {

                @XmlElement(name = "Riik", required = true)
                protected KodifK riik;

                /**
                 * Gets the value of the riik property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link KodifK }
                 *     
                 */
                public KodifK getRiik() {
                    return riik;
                }

                /**
                 * Sets the value of the riik property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link KodifK }
                 *     
                 */
                public void setRiik(KodifK value) {
                    this.riik = value;
                }

            }

        }

    }

}
