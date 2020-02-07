
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RRExtDocumentDataKooseluRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRExtDocumentDataKooseluRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Tegevus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Dokumendid"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Dokument"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Liik" type="{http://rr.x-road.eu/producer}Kodifee"/&gt;
 *                             &lt;element name="DokumendiSeeria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="DokumendiNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ValjaandmiseKP" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                             &lt;element name="JoustumiseKP" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                             &lt;element name="Joustumisekell" type="{http://www.w3.org/2001/XMLSchema}time"/&gt;
 *                             &lt;element name="KehtivKuniKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                             &lt;element name="KehtetuAlatesKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                             &lt;element name="KoostanudAsutus"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodifee"/&gt;
 *                                       &lt;element name="AsutuseRegNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="AsutuseKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="AsutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                       &lt;element name="AsutuseAadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="AmetnikuIsikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
 *                             &lt;element name="AmetnikuNimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *         &lt;element name="Pohiosa"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Isikud"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Isik" maxOccurs="unbounded"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
 *                                       &lt;element name="ValisriigiIK" maxOccurs="unbounded" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodifee"/&gt;
 *                                                 &lt;element name="ValisriigiIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                       &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="MuudEesnimed" maxOccurs="unbounded" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="MuuEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                       &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="MuudPerenimed" maxOccurs="unbounded" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="MuuPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                       &lt;element name="PohiKodakondsus"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Riigi"/&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                       &lt;element name="Kodakondsused"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="Kodakondsus" maxOccurs="unbounded" minOccurs="0"&gt;
 *                                                   &lt;complexType&gt;
 *                                                     &lt;complexContent&gt;
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                                         &lt;sequence&gt;
 *                                                           &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Riigi"/&gt;
 *                                                         &lt;/sequence&gt;
 *                                                       &lt;/restriction&gt;
 *                                                     &lt;/complexContent&gt;
 *                                                   &lt;/complexType&gt;
 *                                                 &lt;/element&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                       &lt;element name="Lisainfo"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                                                 &lt;element name="Sugu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
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
@XmlType(name = "RRExtDocumentDataKooseluRequest", propOrder = {
    "tegevus",
    "dokumendid",
    "pohiosa"
})
public class RRExtDocumentDataKooseluRequest {

    @XmlElement(name = "Tegevus", required = true)
    protected String tegevus;
    @XmlElement(name = "Dokumendid", required = true)
    protected RRExtDocumentDataKooseluRequest.Dokumendid dokumendid;
    @XmlElement(name = "Pohiosa", required = true)
    protected RRExtDocumentDataKooseluRequest.Pohiosa pohiosa;

    /**
     * Gets the value of the tegevus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTegevus() {
        return tegevus;
    }

    /**
     * Sets the value of the tegevus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTegevus(String value) {
        this.tegevus = value;
    }

    /**
     * Gets the value of the dokumendid property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentDataKooseluRequest.Dokumendid }
     *     
     */
    public RRExtDocumentDataKooseluRequest.Dokumendid getDokumendid() {
        return dokumendid;
    }

    /**
     * Sets the value of the dokumendid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentDataKooseluRequest.Dokumendid }
     *     
     */
    public void setDokumendid(RRExtDocumentDataKooseluRequest.Dokumendid value) {
        this.dokumendid = value;
    }

    /**
     * Gets the value of the pohiosa property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentDataKooseluRequest.Pohiosa }
     *     
     */
    public RRExtDocumentDataKooseluRequest.Pohiosa getPohiosa() {
        return pohiosa;
    }

    /**
     * Sets the value of the pohiosa property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentDataKooseluRequest.Pohiosa }
     *     
     */
    public void setPohiosa(RRExtDocumentDataKooseluRequest.Pohiosa value) {
        this.pohiosa = value;
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
     *         &lt;element name="Dokument"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Liik" type="{http://rr.x-road.eu/producer}Kodifee"/&gt;
     *                   &lt;element name="DokumendiSeeria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="DokumendiNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ValjaandmiseKP" type="{http://rr.x-road.eu/producer}date"/&gt;
     *                   &lt;element name="JoustumiseKP" type="{http://rr.x-road.eu/producer}date"/&gt;
     *                   &lt;element name="Joustumisekell" type="{http://www.w3.org/2001/XMLSchema}time"/&gt;
     *                   &lt;element name="KehtivKuniKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                   &lt;element name="KehtetuAlatesKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                   &lt;element name="KoostanudAsutus"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodifee"/&gt;
     *                             &lt;element name="AsutuseRegNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="AsutuseKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="AsutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                             &lt;element name="AsutuseAadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "dokument"
    })
    public static class Dokumendid {

        @XmlElement(name = "Dokument", required = true)
        protected RRExtDocumentDataKooseluRequest.Dokumendid.Dokument dokument;

        /**
         * Gets the value of the dokument property.
         * 
         * @return
         *     possible object is
         *     {@link RRExtDocumentDataKooseluRequest.Dokumendid.Dokument }
         *     
         */
        public RRExtDocumentDataKooseluRequest.Dokumendid.Dokument getDokument() {
            return dokument;
        }

        /**
         * Sets the value of the dokument property.
         * 
         * @param value
         *     allowed object is
         *     {@link RRExtDocumentDataKooseluRequest.Dokumendid.Dokument }
         *     
         */
        public void setDokument(RRExtDocumentDataKooseluRequest.Dokumendid.Dokument value) {
            this.dokument = value;
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
         *         &lt;element name="Liik" type="{http://rr.x-road.eu/producer}Kodifee"/&gt;
         *         &lt;element name="DokumendiSeeria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="DokumendiNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ValjaandmiseKP" type="{http://rr.x-road.eu/producer}date"/&gt;
         *         &lt;element name="JoustumiseKP" type="{http://rr.x-road.eu/producer}date"/&gt;
         *         &lt;element name="Joustumisekell" type="{http://www.w3.org/2001/XMLSchema}time"/&gt;
         *         &lt;element name="KehtivKuniKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *         &lt;element name="KehtetuAlatesKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *         &lt;element name="KoostanudAsutus"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodifee"/&gt;
         *                   &lt;element name="AsutuseRegNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="AsutuseKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="AsutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                   &lt;element name="AsutuseAadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "dokumendiSeeria",
            "dokumendiNumber",
            "valjaandmiseKP",
            "joustumiseKP",
            "joustumisekell",
            "kehtivKuniKP",
            "kehtetuAlatesKP",
            "koostanudAsutus",
            "ametnikuIsikukood",
            "ametnikuNimed"
        })
        public static class Dokument {

            @XmlElement(name = "Liik", required = true)
            protected Kodifee liik;
            @XmlElement(name = "DokumendiSeeria")
            protected String dokumendiSeeria;
            @XmlElement(name = "DokumendiNumber", required = true)
            protected String dokumendiNumber;
            @XmlElement(name = "ValjaandmiseKP", required = true)
            protected String valjaandmiseKP;
            @XmlElement(name = "JoustumiseKP", required = true)
            protected String joustumiseKP;
            @XmlElement(name = "Joustumisekell", required = true)
            @XmlSchemaType(name = "time")
            protected XMLGregorianCalendar joustumisekell;
            @XmlElement(name = "KehtivKuniKP")
            protected String kehtivKuniKP;
            @XmlElement(name = "KehtetuAlatesKP")
            protected String kehtetuAlatesKP;
            @XmlElement(name = "KoostanudAsutus", required = true)
            protected RRExtDocumentDataKooseluRequest.Dokumendid.Dokument.KoostanudAsutus koostanudAsutus;
            @XmlElement(name = "AmetnikuIsikukood", required = true)
            protected String ametnikuIsikukood;
            @XmlElement(name = "AmetnikuNimed", required = true)
            protected String ametnikuNimed;

            /**
             * Gets the value of the liik property.
             * 
             * @return
             *     possible object is
             *     {@link Kodifee }
             *     
             */
            public Kodifee getLiik() {
                return liik;
            }

            /**
             * Sets the value of the liik property.
             * 
             * @param value
             *     allowed object is
             *     {@link Kodifee }
             *     
             */
            public void setLiik(Kodifee value) {
                this.liik = value;
            }

            /**
             * Gets the value of the dokumendiSeeria property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiSeeria() {
                return dokumendiSeeria;
            }

            /**
             * Sets the value of the dokumendiSeeria property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiSeeria(String value) {
                this.dokumendiSeeria = value;
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
             * Gets the value of the joustumisekell property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getJoustumisekell() {
                return joustumisekell;
            }

            /**
             * Sets the value of the joustumisekell property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setJoustumisekell(XMLGregorianCalendar value) {
                this.joustumisekell = value;
            }

            /**
             * Gets the value of the kehtivKuniKP property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKehtivKuniKP() {
                return kehtivKuniKP;
            }

            /**
             * Sets the value of the kehtivKuniKP property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKehtivKuniKP(String value) {
                this.kehtivKuniKP = value;
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
             *     {@link RRExtDocumentDataKooseluRequest.Dokumendid.Dokument.KoostanudAsutus }
             *     
             */
            public RRExtDocumentDataKooseluRequest.Dokumendid.Dokument.KoostanudAsutus getKoostanudAsutus() {
                return koostanudAsutus;
            }

            /**
             * Sets the value of the koostanudAsutus property.
             * 
             * @param value
             *     allowed object is
             *     {@link RRExtDocumentDataKooseluRequest.Dokumendid.Dokument.KoostanudAsutus }
             *     
             */
            public void setKoostanudAsutus(RRExtDocumentDataKooseluRequest.Dokumendid.Dokument.KoostanudAsutus value) {
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
             *         &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodifee"/&gt;
             *         &lt;element name="AsutuseRegNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="AsutuseKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="AsutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *         &lt;element name="AsutuseAadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                "asutuseKood",
                "asutuseNimi",
                "asutuseAadress"
            })
            public static class KoostanudAsutus {

                @XmlElement(name = "Riik", required = true)
                protected Kodifee riik;
                @XmlElement(name = "AsutuseRegNumber", required = true)
                protected String asutuseRegNumber;
                @XmlElement(name = "AsutuseKood", required = true)
                protected String asutuseKood;
                @XmlElement(name = "AsutuseNimi")
                protected String asutuseNimi;
                @XmlElement(name = "AsutuseAadress", required = true)
                protected String asutuseAadress;

                /**
                 * Gets the value of the riik property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Kodifee }
                 *     
                 */
                public Kodifee getRiik() {
                    return riik;
                }

                /**
                 * Sets the value of the riik property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Kodifee }
                 *     
                 */
                public void setRiik(Kodifee value) {
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
                 * Gets the value of the asutuseKood property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAsutuseKood() {
                    return asutuseKood;
                }

                /**
                 * Sets the value of the asutuseKood property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAsutuseKood(String value) {
                    this.asutuseKood = value;
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

                /**
                 * Gets the value of the asutuseAadress property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAsutuseAadress() {
                    return asutuseAadress;
                }

                /**
                 * Sets the value of the asutuseAadress property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAsutuseAadress(String value) {
                    this.asutuseAadress = value;
                }

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
     *         &lt;element name="Isikud"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Isik" maxOccurs="unbounded"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
     *                             &lt;element name="ValisriigiIK" maxOccurs="unbounded" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodifee"/&gt;
     *                                       &lt;element name="ValisriigiIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                             &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="MuudEesnimed" maxOccurs="unbounded" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="MuuEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                             &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="MuudPerenimed" maxOccurs="unbounded" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="MuuPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                             &lt;element name="PohiKodakondsus"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Riigi"/&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                             &lt;element name="Kodakondsused"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="Kodakondsus" maxOccurs="unbounded" minOccurs="0"&gt;
     *                                         &lt;complexType&gt;
     *                                           &lt;complexContent&gt;
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                               &lt;sequence&gt;
     *                                                 &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Riigi"/&gt;
     *                                               &lt;/sequence&gt;
     *                                             &lt;/restriction&gt;
     *                                           &lt;/complexContent&gt;
     *                                         &lt;/complexType&gt;
     *                                       &lt;/element&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                             &lt;element name="Lisainfo"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
     *                                       &lt;element name="Sugu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    @XmlType(name = "", propOrder = {
        "isikud"
    })
    public static class Pohiosa {

        @XmlElement(name = "Isikud", required = true)
        protected RRExtDocumentDataKooseluRequest.Pohiosa.Isikud isikud;

        /**
         * Gets the value of the isikud property.
         * 
         * @return
         *     possible object is
         *     {@link RRExtDocumentDataKooseluRequest.Pohiosa.Isikud }
         *     
         */
        public RRExtDocumentDataKooseluRequest.Pohiosa.Isikud getIsikud() {
            return isikud;
        }

        /**
         * Sets the value of the isikud property.
         * 
         * @param value
         *     allowed object is
         *     {@link RRExtDocumentDataKooseluRequest.Pohiosa.Isikud }
         *     
         */
        public void setIsikud(RRExtDocumentDataKooseluRequest.Pohiosa.Isikud value) {
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
         *         &lt;element name="Isik" maxOccurs="unbounded"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
         *                   &lt;element name="ValisriigiIK" maxOccurs="unbounded" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodifee"/&gt;
         *                             &lt;element name="ValisriigiIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                           &lt;/sequence&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                   &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="MuudEesnimed" maxOccurs="unbounded" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="MuuEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                           &lt;/sequence&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                   &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="MuudPerenimed" maxOccurs="unbounded" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="MuuPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                           &lt;/sequence&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                   &lt;element name="PohiKodakondsus"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Riigi"/&gt;
         *                           &lt;/sequence&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                   &lt;element name="Kodakondsused"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="Kodakondsus" maxOccurs="unbounded" minOccurs="0"&gt;
         *                               &lt;complexType&gt;
         *                                 &lt;complexContent&gt;
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                                     &lt;sequence&gt;
         *                                       &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Riigi"/&gt;
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
         *                   &lt;element name="Lisainfo"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
         *                             &lt;element name="Sugu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
            protected List<RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik> isik;

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
             * {@link RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik }
             * 
             * 
             */
            public List<RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik> getIsik() {
                if (isik == null) {
                    isik = new ArrayList<RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik>();
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
             *         &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
             *         &lt;element name="ValisriigiIK" maxOccurs="unbounded" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodifee"/&gt;
             *                   &lt;element name="ValisriigiIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                 &lt;/sequence&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="MuudEesnimed" maxOccurs="unbounded" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="MuuEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                 &lt;/sequence&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *         &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="MuudPerenimed" maxOccurs="unbounded" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="MuuPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                 &lt;/sequence&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *         &lt;element name="PohiKodakondsus"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Riigi"/&gt;
             *                 &lt;/sequence&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *         &lt;element name="Kodakondsused"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="Kodakondsus" maxOccurs="unbounded" minOccurs="0"&gt;
             *                     &lt;complexType&gt;
             *                       &lt;complexContent&gt;
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                           &lt;sequence&gt;
             *                             &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Riigi"/&gt;
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
             *         &lt;element name="Lisainfo"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
             *                   &lt;element name="Sugu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
                "valisriigiIK",
                "eesnimi",
                "muudEesnimed",
                "perenimi",
                "muudPerenimed",
                "pohiKodakondsus",
                "kodakondsused",
                "lisainfo"
            })
            public static class Isik {

                @XmlElement(name = "Isikukood", required = true)
                protected String isikukood;
                @XmlElement(name = "ValisriigiIK")
                protected List<RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.ValisriigiIK> valisriigiIK;
                @XmlElement(name = "Eesnimi", required = true)
                protected String eesnimi;
                @XmlElement(name = "MuudEesnimed")
                protected List<RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.MuudEesnimed> muudEesnimed;
                @XmlElement(name = "Perenimi", required = true)
                protected String perenimi;
                @XmlElement(name = "MuudPerenimed")
                protected List<RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.MuudPerenimed> muudPerenimed;
                @XmlElement(name = "PohiKodakondsus", required = true)
                protected RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.PohiKodakondsus pohiKodakondsus;
                @XmlElement(name = "Kodakondsused", required = true)
                protected RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.Kodakondsused kodakondsused;
                @XmlElement(name = "Lisainfo", required = true)
                protected RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.Lisainfo lisainfo;

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
                 * Gets the value of the valisriigiIK property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the valisriigiIK property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getValisriigiIK().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.ValisriigiIK }
                 * 
                 * 
                 */
                public List<RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.ValisriigiIK> getValisriigiIK() {
                    if (valisriigiIK == null) {
                        valisriigiIK = new ArrayList<RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.ValisriigiIK>();
                    }
                    return this.valisriigiIK;
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
                 * Gets the value of the muudEesnimed property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the muudEesnimed property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getMuudEesnimed().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.MuudEesnimed }
                 * 
                 * 
                 */
                public List<RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.MuudEesnimed> getMuudEesnimed() {
                    if (muudEesnimed == null) {
                        muudEesnimed = new ArrayList<RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.MuudEesnimed>();
                    }
                    return this.muudEesnimed;
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
                 * Gets the value of the muudPerenimed property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the muudPerenimed property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getMuudPerenimed().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.MuudPerenimed }
                 * 
                 * 
                 */
                public List<RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.MuudPerenimed> getMuudPerenimed() {
                    if (muudPerenimed == null) {
                        muudPerenimed = new ArrayList<RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.MuudPerenimed>();
                    }
                    return this.muudPerenimed;
                }

                /**
                 * Gets the value of the pohiKodakondsus property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.PohiKodakondsus }
                 *     
                 */
                public RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.PohiKodakondsus getPohiKodakondsus() {
                    return pohiKodakondsus;
                }

                /**
                 * Sets the value of the pohiKodakondsus property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.PohiKodakondsus }
                 *     
                 */
                public void setPohiKodakondsus(RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.PohiKodakondsus value) {
                    this.pohiKodakondsus = value;
                }

                /**
                 * Gets the value of the kodakondsused property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.Kodakondsused }
                 *     
                 */
                public RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.Kodakondsused getKodakondsused() {
                    return kodakondsused;
                }

                /**
                 * Sets the value of the kodakondsused property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.Kodakondsused }
                 *     
                 */
                public void setKodakondsused(RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.Kodakondsused value) {
                    this.kodakondsused = value;
                }

                /**
                 * Gets the value of the lisainfo property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.Lisainfo }
                 *     
                 */
                public RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.Lisainfo getLisainfo() {
                    return lisainfo;
                }

                /**
                 * Sets the value of the lisainfo property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.Lisainfo }
                 *     
                 */
                public void setLisainfo(RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.Lisainfo value) {
                    this.lisainfo = value;
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
                 *         &lt;element name="Kodakondsus" maxOccurs="unbounded" minOccurs="0"&gt;
                 *           &lt;complexType&gt;
                 *             &lt;complexContent&gt;
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *                 &lt;sequence&gt;
                 *                   &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Riigi"/&gt;
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
                    "kodakondsus"
                })
                public static class Kodakondsused {

                    @XmlElement(name = "Kodakondsus")
                    protected List<RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.Kodakondsused.Kodakondsus> kodakondsus;

                    /**
                     * Gets the value of the kodakondsus property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the kodakondsus property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getKodakondsus().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.Kodakondsused.Kodakondsus }
                     * 
                     * 
                     */
                    public List<RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.Kodakondsused.Kodakondsus> getKodakondsus() {
                        if (kodakondsus == null) {
                            kodakondsus = new ArrayList<RRExtDocumentDataKooseluRequest.Pohiosa.Isikud.Isik.Kodakondsused.Kodakondsus>();
                        }
                        return this.kodakondsus;
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
                     *         &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Riigi"/&gt;
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
                    public static class Kodakondsus {

                        @XmlElement(name = "Riik", required = true)
                        protected Riigi riik;

                        /**
                         * Gets the value of the riik property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link Riigi }
                         *     
                         */
                        public Riigi getRiik() {
                            return riik;
                        }

                        /**
                         * Sets the value of the riik property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link Riigi }
                         *     
                         */
                        public void setRiik(Riigi value) {
                            this.riik = value;
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
                 *         &lt;element name="Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
                 *         &lt;element name="Sugu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
                    "synnikoht",
                    "synniaeg",
                    "sugu"
                })
                public static class Lisainfo {

                    @XmlElement(name = "Synnikoht")
                    protected String synnikoht;
                    @XmlElement(name = "Synniaeg", required = true)
                    protected String synniaeg;
                    @XmlElement(name = "Sugu")
                    protected String sugu;

                    /**
                     * Gets the value of the synnikoht property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSynnikoht() {
                        return synnikoht;
                    }

                    /**
                     * Sets the value of the synnikoht property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSynnikoht(String value) {
                        this.synnikoht = value;
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
                     *     {@link String }
                     *     
                     */
                    public String getSugu() {
                        return sugu;
                    }

                    /**
                     * Sets the value of the sugu property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSugu(String value) {
                        this.sugu = value;
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
                 *         &lt;element name="MuuEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                    "muuEesnimi"
                })
                public static class MuudEesnimed {

                    @XmlElement(name = "MuuEesnimi", required = true)
                    protected String muuEesnimi;

                    /**
                     * Gets the value of the muuEesnimi property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getMuuEesnimi() {
                        return muuEesnimi;
                    }

                    /**
                     * Sets the value of the muuEesnimi property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setMuuEesnimi(String value) {
                        this.muuEesnimi = value;
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
                 *         &lt;element name="MuuPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                    "muuPerenimi"
                })
                public static class MuudPerenimed {

                    @XmlElement(name = "MuuPerenimi", required = true)
                    protected String muuPerenimi;

                    /**
                     * Gets the value of the muuPerenimi property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getMuuPerenimi() {
                        return muuPerenimi;
                    }

                    /**
                     * Sets the value of the muuPerenimi property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setMuuPerenimi(String value) {
                        this.muuPerenimi = value;
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
                 *         &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Riigi"/&gt;
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
                    protected Riigi riik;

                    /**
                     * Gets the value of the riik property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Riigi }
                     *     
                     */
                    public Riigi getRiik() {
                        return riik;
                    }

                    /**
                     * Sets the value of the riik property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Riigi }
                     *     
                     */
                    public void setRiik(Riigi value) {
                        this.riik = value;
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
                 *         &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodifee"/&gt;
                 *         &lt;element name="ValisriigiIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                    "valisriigiIK"
                })
                public static class ValisriigiIK {

                    @XmlElement(name = "Riik", required = true)
                    protected Kodifee riik;
                    @XmlElement(name = "ValisriigiIK", required = true)
                    protected String valisriigiIK;

                    /**
                     * Gets the value of the riik property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Kodifee }
                     *     
                     */
                    public Kodifee getRiik() {
                        return riik;
                    }

                    /**
                     * Sets the value of the riik property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Kodifee }
                     *     
                     */
                    public void setRiik(Kodifee value) {
                        this.riik = value;
                    }

                    /**
                     * Gets the value of the valisriigiIK property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getValisriigiIK() {
                        return valisriigiIK;
                    }

                    /**
                     * Sets the value of the valisriigiIK property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setValisriigiIK(String value) {
                        this.valisriigiIK = value;
                    }

                }

            }

        }

    }

}
