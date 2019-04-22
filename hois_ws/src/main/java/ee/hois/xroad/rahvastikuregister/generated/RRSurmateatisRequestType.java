
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRSurmateatisRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRSurmateatisRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Tegevus" type="{http://rr.x-road.eu/producer}RRSurmSyndmusType"/&gt;
 *         &lt;element name="Dokument"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="DokumendiNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="ValjaandmiseKP" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                   &lt;element name="JoustumiseKP" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                   &lt;element name="KehtetuAlatesKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                   &lt;element name="KehtetuPohjus" type="{http://rr.x-road.eu/producer}max256Chars" minOccurs="0"/&gt;
 *                   &lt;element name="KoostanudAsutus"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="AsutuseRegNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AsutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="AmetnikuIsikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
 *                   &lt;element name="AmetnikuNimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="AmetnikuRegNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Isik"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Surmaaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                   &lt;element name="Surmakoht"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="RiigiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="MaakonnaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="OmavalitsuseKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *         &lt;element name="RRisSurmaKandeNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRSurmateatisRequestType", propOrder = {
    "tegevus",
    "dokument",
    "isik",
    "rRisSurmaKandeNumber"
})
public class RRSurmateatisRequestType {

    @XmlElement(name = "Tegevus", required = true)
    @XmlSchemaType(name = "string")
    protected RRSurmSyndmusType tegevus;
    @XmlElement(name = "Dokument", required = true)
    protected RRSurmateatisRequestType.Dokument dokument;
    @XmlElement(name = "Isik", required = true)
    protected RRSurmateatisRequestType.Isik isik;
    @XmlElement(name = "RRisSurmaKandeNumber")
    protected String rRisSurmaKandeNumber;

    /**
     * Gets the value of the tegevus property.
     * 
     * @return
     *     possible object is
     *     {@link RRSurmSyndmusType }
     *     
     */
    public RRSurmSyndmusType getTegevus() {
        return tegevus;
    }

    /**
     * Sets the value of the tegevus property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRSurmSyndmusType }
     *     
     */
    public void setTegevus(RRSurmSyndmusType value) {
        this.tegevus = value;
    }

    /**
     * Gets the value of the dokument property.
     * 
     * @return
     *     possible object is
     *     {@link RRSurmateatisRequestType.Dokument }
     *     
     */
    public RRSurmateatisRequestType.Dokument getDokument() {
        return dokument;
    }

    /**
     * Sets the value of the dokument property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRSurmateatisRequestType.Dokument }
     *     
     */
    public void setDokument(RRSurmateatisRequestType.Dokument value) {
        this.dokument = value;
    }

    /**
     * Gets the value of the isik property.
     * 
     * @return
     *     possible object is
     *     {@link RRSurmateatisRequestType.Isik }
     *     
     */
    public RRSurmateatisRequestType.Isik getIsik() {
        return isik;
    }

    /**
     * Sets the value of the isik property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRSurmateatisRequestType.Isik }
     *     
     */
    public void setIsik(RRSurmateatisRequestType.Isik value) {
        this.isik = value;
    }

    /**
     * Gets the value of the rRisSurmaKandeNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRRisSurmaKandeNumber() {
        return rRisSurmaKandeNumber;
    }

    /**
     * Sets the value of the rRisSurmaKandeNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRRisSurmaKandeNumber(String value) {
        this.rRisSurmaKandeNumber = value;
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
     *         &lt;element name="DokumendiNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="ValjaandmiseKP" type="{http://rr.x-road.eu/producer}date"/&gt;
     *         &lt;element name="JoustumiseKP" type="{http://rr.x-road.eu/producer}date"/&gt;
     *         &lt;element name="KehtetuAlatesKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *         &lt;element name="KehtetuPohjus" type="{http://rr.x-road.eu/producer}max256Chars" minOccurs="0"/&gt;
     *         &lt;element name="KoostanudAsutus"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="AsutuseRegNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AsutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="AmetnikuIsikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
     *         &lt;element name="AmetnikuNimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="AmetnikuRegNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "dokumendiNumber",
        "valjaandmiseKP",
        "joustumiseKP",
        "kehtetuAlatesKP",
        "kehtetuPohjus",
        "koostanudAsutus",
        "ametnikuIsikukood",
        "ametnikuNimed",
        "ametnikuRegNumber"
    })
    public static class Dokument {

        @XmlElement(name = "DokumendiNumber", required = true)
        protected String dokumendiNumber;
        @XmlElement(name = "ValjaandmiseKP", required = true)
        protected String valjaandmiseKP;
        @XmlElement(name = "JoustumiseKP", required = true)
        protected String joustumiseKP;
        @XmlElement(name = "KehtetuAlatesKP")
        protected String kehtetuAlatesKP;
        @XmlElement(name = "KehtetuPohjus")
        protected String kehtetuPohjus;
        @XmlElement(name = "KoostanudAsutus", required = true)
        protected RRSurmateatisRequestType.Dokument.KoostanudAsutus koostanudAsutus;
        @XmlElement(name = "AmetnikuIsikukood", required = true)
        protected String ametnikuIsikukood;
        @XmlElement(name = "AmetnikuNimed", required = true)
        protected String ametnikuNimed;
        @XmlElement(name = "AmetnikuRegNumber", required = true)
        protected String ametnikuRegNumber;

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
         * Gets the value of the kehtetuPohjus property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKehtetuPohjus() {
            return kehtetuPohjus;
        }

        /**
         * Sets the value of the kehtetuPohjus property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKehtetuPohjus(String value) {
            this.kehtetuPohjus = value;
        }

        /**
         * Gets the value of the koostanudAsutus property.
         * 
         * @return
         *     possible object is
         *     {@link RRSurmateatisRequestType.Dokument.KoostanudAsutus }
         *     
         */
        public RRSurmateatisRequestType.Dokument.KoostanudAsutus getKoostanudAsutus() {
            return koostanudAsutus;
        }

        /**
         * Sets the value of the koostanudAsutus property.
         * 
         * @param value
         *     allowed object is
         *     {@link RRSurmateatisRequestType.Dokument.KoostanudAsutus }
         *     
         */
        public void setKoostanudAsutus(RRSurmateatisRequestType.Dokument.KoostanudAsutus value) {
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
         * Gets the value of the ametnikuRegNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAmetnikuRegNumber() {
            return ametnikuRegNumber;
        }

        /**
         * Sets the value of the ametnikuRegNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAmetnikuRegNumber(String value) {
            this.ametnikuRegNumber = value;
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
         *         &lt;element name="AsutuseRegNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "asutuseRegNumber",
            "asutuseNimi"
        })
        public static class KoostanudAsutus {

            @XmlElement(name = "AsutuseRegNumber", required = true)
            protected String asutuseRegNumber;
            @XmlElement(name = "AsutuseNimi", required = true)
            protected String asutuseNimi;

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
     *         &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Surmaaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
     *         &lt;element name="Surmakoht"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="RiigiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="MaakonnaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="OmavalitsuseKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "perenimi",
        "surmaaeg",
        "surmakoht"
    })
    public static class Isik {

        @XmlElement(name = "Isikukood", required = true)
        protected String isikukood;
        @XmlElement(name = "Eesnimi", required = true)
        protected String eesnimi;
        @XmlElement(name = "Perenimi", required = true)
        protected String perenimi;
        @XmlElement(name = "Surmaaeg", required = true)
        protected String surmaaeg;
        @XmlElement(name = "Surmakoht", required = true)
        protected RRSurmateatisRequestType.Isik.Surmakoht surmakoht;

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
         * Gets the value of the surmaaeg property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSurmaaeg() {
            return surmaaeg;
        }

        /**
         * Sets the value of the surmaaeg property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSurmaaeg(String value) {
            this.surmaaeg = value;
        }

        /**
         * Gets the value of the surmakoht property.
         * 
         * @return
         *     possible object is
         *     {@link RRSurmateatisRequestType.Isik.Surmakoht }
         *     
         */
        public RRSurmateatisRequestType.Isik.Surmakoht getSurmakoht() {
            return surmakoht;
        }

        /**
         * Sets the value of the surmakoht property.
         * 
         * @param value
         *     allowed object is
         *     {@link RRSurmateatisRequestType.Isik.Surmakoht }
         *     
         */
        public void setSurmakoht(RRSurmateatisRequestType.Isik.Surmakoht value) {
            this.surmakoht = value;
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
         *         &lt;element name="RiigiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="MaakonnaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="OmavalitsuseKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "riigiKood",
            "maakonnaKood",
            "omavalitsuseKood",
            "aadressTekstina"
        })
        public static class Surmakoht {

            @XmlElement(name = "RiigiKood", required = true)
            protected String riigiKood;
            @XmlElement(name = "MaakonnaKood", required = true)
            protected String maakonnaKood;
            @XmlElement(name = "OmavalitsuseKood", required = true)
            protected String omavalitsuseKood;
            @XmlElement(name = "AadressTekstina", required = true)
            protected String aadressTekstina;

            /**
             * Gets the value of the riigiKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRiigiKood() {
                return riigiKood;
            }

            /**
             * Sets the value of the riigiKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRiigiKood(String value) {
                this.riigiKood = value;
            }

            /**
             * Gets the value of the maakonnaKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMaakonnaKood() {
                return maakonnaKood;
            }

            /**
             * Sets the value of the maakonnaKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMaakonnaKood(String value) {
                this.maakonnaKood = value;
            }

            /**
             * Gets the value of the omavalitsuseKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getOmavalitsuseKood() {
                return omavalitsuseKood;
            }

            /**
             * Sets the value of the omavalitsuseKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setOmavalitsuseKood(String value) {
                this.omavalitsuseKood = value;
            }

            /**
             * Gets the value of the aadressTekstina property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressTekstina() {
                return aadressTekstina;
            }

            /**
             * Sets the value of the aadressTekstina property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressTekstina(String value) {
                this.aadressTekstina = value;
            }

        }

    }

}
