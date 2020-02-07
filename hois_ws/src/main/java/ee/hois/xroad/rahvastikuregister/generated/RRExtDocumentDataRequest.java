
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRExtDocumentDataRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRExtDocumentDataRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Tegevus"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="V"/&gt;
 *               &lt;enumeration value="K"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Dokument"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Liik" type="{http://rr.x-road.eu/producer}Kodif2"/&gt;
 *                   &lt;element name="DokumendiSeeria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="DokumendiNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="ValjaandmiseKP" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                   &lt;element name="JoustumiseKP" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                   &lt;element name="KehtivKuniKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                   &lt;element name="KehtetuAlatesKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                   &lt;element name="KoostanudAsutus"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodif2"/&gt;
 *                             &lt;element name="AsutuseRegNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="AsutuseKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="AsutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
 *         &lt;element name="Menetlus" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="OtsuseNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="Alus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="OtsuseKuupaev" type="{http://rr.x-road.eu/producer}date"/&gt;
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
 *                   &lt;element name="Isik" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
 *                             &lt;element name="ValisriigiIK"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodif2"/&gt;
 *                                       &lt;element name="ValisriigiIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="EesnimiRR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="MuudEesnimed" minOccurs="0"&gt;
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
 *                             &lt;element name="PerenimiRR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="MuudPerenimed" minOccurs="0"&gt;
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
 *                             &lt;element name="PohiKodakondsus" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodif2"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Kodakondsused" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodif2" maxOccurs="unbounded"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Lisainfo" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                       &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
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
 *         &lt;element name="Elukoht"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="RiigiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Tase1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="Tase2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="Tase3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="Tase4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="Tase5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="Tase6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="Tase7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="Tase8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="PostiIndeks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="AlgusKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="EestiElukoht" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Tase1" type="{http://rr.x-road.eu/producer}Kodif2"/&gt;
 *                   &lt;element name="Tase2" type="{http://rr.x-road.eu/producer}Kodif2" minOccurs="0"/&gt;
 *                   &lt;element name="Tase3" type="{http://rr.x-road.eu/producer}Kodif2" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Sideandmed"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Kontakt"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Liik" type="{http://rr.x-road.eu/producer}Kodif2"/&gt;
 *                             &lt;element name="KontaktiTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AlatesKP" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                             &lt;element name="KuniKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
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
 *         &lt;element name="Avaldaja"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
 *                   &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RRExtDocumentDataRequest", propOrder = {
    "tegevus",
    "dokument",
    "menetlus",
    "isikud",
    "elukoht",
    "eestiElukoht",
    "sideandmed",
    "avaldaja"
})
public class RRExtDocumentDataRequest {

    @XmlElement(name = "Tegevus", required = true)
    protected String tegevus;
    @XmlElement(name = "Dokument", required = true)
    protected RRExtDocumentDataRequest.Dokument dokument;
    @XmlElement(name = "Menetlus")
    protected RRExtDocumentDataRequest.Menetlus menetlus;
    @XmlElement(name = "Isikud", required = true)
    protected RRExtDocumentDataRequest.Isikud isikud;
    @XmlElement(name = "Elukoht", required = true)
    protected RRExtDocumentDataRequest.Elukoht elukoht;
    @XmlElement(name = "EestiElukoht")
    protected RRExtDocumentDataRequest.EestiElukoht eestiElukoht;
    @XmlElement(name = "Sideandmed", required = true)
    protected RRExtDocumentDataRequest.Sideandmed sideandmed;
    @XmlElement(name = "Avaldaja", required = true)
    protected RRExtDocumentDataRequest.Avaldaja avaldaja;

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
     * Gets the value of the dokument property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentDataRequest.Dokument }
     *     
     */
    public RRExtDocumentDataRequest.Dokument getDokument() {
        return dokument;
    }

    /**
     * Sets the value of the dokument property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentDataRequest.Dokument }
     *     
     */
    public void setDokument(RRExtDocumentDataRequest.Dokument value) {
        this.dokument = value;
    }

    /**
     * Gets the value of the menetlus property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentDataRequest.Menetlus }
     *     
     */
    public RRExtDocumentDataRequest.Menetlus getMenetlus() {
        return menetlus;
    }

    /**
     * Sets the value of the menetlus property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentDataRequest.Menetlus }
     *     
     */
    public void setMenetlus(RRExtDocumentDataRequest.Menetlus value) {
        this.menetlus = value;
    }

    /**
     * Gets the value of the isikud property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentDataRequest.Isikud }
     *     
     */
    public RRExtDocumentDataRequest.Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentDataRequest.Isikud }
     *     
     */
    public void setIsikud(RRExtDocumentDataRequest.Isikud value) {
        this.isikud = value;
    }

    /**
     * Gets the value of the elukoht property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentDataRequest.Elukoht }
     *     
     */
    public RRExtDocumentDataRequest.Elukoht getElukoht() {
        return elukoht;
    }

    /**
     * Sets the value of the elukoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentDataRequest.Elukoht }
     *     
     */
    public void setElukoht(RRExtDocumentDataRequest.Elukoht value) {
        this.elukoht = value;
    }

    /**
     * Gets the value of the eestiElukoht property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentDataRequest.EestiElukoht }
     *     
     */
    public RRExtDocumentDataRequest.EestiElukoht getEestiElukoht() {
        return eestiElukoht;
    }

    /**
     * Sets the value of the eestiElukoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentDataRequest.EestiElukoht }
     *     
     */
    public void setEestiElukoht(RRExtDocumentDataRequest.EestiElukoht value) {
        this.eestiElukoht = value;
    }

    /**
     * Gets the value of the sideandmed property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentDataRequest.Sideandmed }
     *     
     */
    public RRExtDocumentDataRequest.Sideandmed getSideandmed() {
        return sideandmed;
    }

    /**
     * Sets the value of the sideandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentDataRequest.Sideandmed }
     *     
     */
    public void setSideandmed(RRExtDocumentDataRequest.Sideandmed value) {
        this.sideandmed = value;
    }

    /**
     * Gets the value of the avaldaja property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentDataRequest.Avaldaja }
     *     
     */
    public RRExtDocumentDataRequest.Avaldaja getAvaldaja() {
        return avaldaja;
    }

    /**
     * Sets the value of the avaldaja property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentDataRequest.Avaldaja }
     *     
     */
    public void setAvaldaja(RRExtDocumentDataRequest.Avaldaja value) {
        this.avaldaja = value;
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
     *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "perenimi"
    })
    public static class Avaldaja {

        @XmlElement(name = "Isikukood", required = true)
        protected String isikukood;
        @XmlElement(name = "Eesnimi", required = true)
        protected String eesnimi;
        @XmlElement(name = "Perenimi", required = true)
        protected String perenimi;

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
     *         &lt;element name="Liik" type="{http://rr.x-road.eu/producer}Kodif2"/&gt;
     *         &lt;element name="DokumendiSeeria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="DokumendiNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="ValjaandmiseKP" type="{http://rr.x-road.eu/producer}date"/&gt;
     *         &lt;element name="JoustumiseKP" type="{http://rr.x-road.eu/producer}date"/&gt;
     *         &lt;element name="KehtivKuniKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *         &lt;element name="KehtetuAlatesKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *         &lt;element name="KoostanudAsutus"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodif2"/&gt;
     *                   &lt;element name="AsutuseRegNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="AsutuseKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="AsutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
        "kehtivKuniKP",
        "kehtetuAlatesKP",
        "koostanudAsutus",
        "ametnikuIsikukood",
        "ametnikuNimed"
    })
    public static class Dokument {

        @XmlElement(name = "Liik", required = true)
        protected Kodif2 liik;
        @XmlElement(name = "DokumendiSeeria")
        protected String dokumendiSeeria;
        @XmlElement(name = "DokumendiNumber", required = true)
        protected String dokumendiNumber;
        @XmlElement(name = "ValjaandmiseKP", required = true)
        protected String valjaandmiseKP;
        @XmlElement(name = "JoustumiseKP", required = true)
        protected String joustumiseKP;
        @XmlElement(name = "KehtivKuniKP")
        protected String kehtivKuniKP;
        @XmlElement(name = "KehtetuAlatesKP")
        protected String kehtetuAlatesKP;
        @XmlElement(name = "KoostanudAsutus", required = true)
        protected RRExtDocumentDataRequest.Dokument.KoostanudAsutus koostanudAsutus;
        @XmlElement(name = "AmetnikuIsikukood", required = true)
        protected String ametnikuIsikukood;
        @XmlElement(name = "AmetnikuNimed", required = true)
        protected String ametnikuNimed;

        /**
         * Gets the value of the liik property.
         * 
         * @return
         *     possible object is
         *     {@link Kodif2 }
         *     
         */
        public Kodif2 getLiik() {
            return liik;
        }

        /**
         * Sets the value of the liik property.
         * 
         * @param value
         *     allowed object is
         *     {@link Kodif2 }
         *     
         */
        public void setLiik(Kodif2 value) {
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
         *     {@link RRExtDocumentDataRequest.Dokument.KoostanudAsutus }
         *     
         */
        public RRExtDocumentDataRequest.Dokument.KoostanudAsutus getKoostanudAsutus() {
            return koostanudAsutus;
        }

        /**
         * Sets the value of the koostanudAsutus property.
         * 
         * @param value
         *     allowed object is
         *     {@link RRExtDocumentDataRequest.Dokument.KoostanudAsutus }
         *     
         */
        public void setKoostanudAsutus(RRExtDocumentDataRequest.Dokument.KoostanudAsutus value) {
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
         *         &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodif2"/&gt;
         *         &lt;element name="AsutuseRegNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="AsutuseKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="AsutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
            "asutuseNimi"
        })
        public static class KoostanudAsutus {

            @XmlElement(name = "Riik", required = true)
            protected Kodif2 riik;
            @XmlElement(name = "AsutuseRegNumber")
            protected String asutuseRegNumber;
            @XmlElement(name = "AsutuseKood")
            protected String asutuseKood;
            @XmlElement(name = "AsutuseNimi")
            protected String asutuseNimi;

            /**
             * Gets the value of the riik property.
             * 
             * @return
             *     possible object is
             *     {@link Kodif2 }
             *     
             */
            public Kodif2 getRiik() {
                return riik;
            }

            /**
             * Sets the value of the riik property.
             * 
             * @param value
             *     allowed object is
             *     {@link Kodif2 }
             *     
             */
            public void setRiik(Kodif2 value) {
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
     *         &lt;element name="Tase1" type="{http://rr.x-road.eu/producer}Kodif2"/&gt;
     *         &lt;element name="Tase2" type="{http://rr.x-road.eu/producer}Kodif2" minOccurs="0"/&gt;
     *         &lt;element name="Tase3" type="{http://rr.x-road.eu/producer}Kodif2" minOccurs="0"/&gt;
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
        "tase1",
        "tase2",
        "tase3"
    })
    public static class EestiElukoht {

        @XmlElement(name = "Tase1", required = true)
        protected Kodif2 tase1;
        @XmlElement(name = "Tase2")
        protected Kodif2 tase2;
        @XmlElement(name = "Tase3")
        protected Kodif2 tase3;

        /**
         * Gets the value of the tase1 property.
         * 
         * @return
         *     possible object is
         *     {@link Kodif2 }
         *     
         */
        public Kodif2 getTase1() {
            return tase1;
        }

        /**
         * Sets the value of the tase1 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Kodif2 }
         *     
         */
        public void setTase1(Kodif2 value) {
            this.tase1 = value;
        }

        /**
         * Gets the value of the tase2 property.
         * 
         * @return
         *     possible object is
         *     {@link Kodif2 }
         *     
         */
        public Kodif2 getTase2() {
            return tase2;
        }

        /**
         * Sets the value of the tase2 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Kodif2 }
         *     
         */
        public void setTase2(Kodif2 value) {
            this.tase2 = value;
        }

        /**
         * Gets the value of the tase3 property.
         * 
         * @return
         *     possible object is
         *     {@link Kodif2 }
         *     
         */
        public Kodif2 getTase3() {
            return tase3;
        }

        /**
         * Sets the value of the tase3 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Kodif2 }
         *     
         */
        public void setTase3(Kodif2 value) {
            this.tase3 = value;
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
     *         &lt;element name="RiigiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Tase1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="Tase2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="Tase3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="Tase4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="Tase5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="Tase6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="Tase7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="Tase8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="PostiIndeks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="AlgusKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
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
        "tase1",
        "tase2",
        "tase3",
        "tase4",
        "tase5",
        "tase6",
        "tase7",
        "tase8",
        "postiIndeks",
        "algusKP"
    })
    public static class Elukoht {

        @XmlElement(name = "RiigiKood", required = true)
        protected String riigiKood;
        @XmlElement(name = "Tase1")
        protected String tase1;
        @XmlElement(name = "Tase2")
        protected String tase2;
        @XmlElement(name = "Tase3")
        protected String tase3;
        @XmlElement(name = "Tase4")
        protected String tase4;
        @XmlElement(name = "Tase5")
        protected String tase5;
        @XmlElement(name = "Tase6")
        protected String tase6;
        @XmlElement(name = "Tase7")
        protected String tase7;
        @XmlElement(name = "Tase8")
        protected String tase8;
        @XmlElement(name = "PostiIndeks")
        protected String postiIndeks;
        @XmlElement(name = "AlgusKP")
        protected String algusKP;

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
         * Gets the value of the tase1 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTase1() {
            return tase1;
        }

        /**
         * Sets the value of the tase1 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTase1(String value) {
            this.tase1 = value;
        }

        /**
         * Gets the value of the tase2 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTase2() {
            return tase2;
        }

        /**
         * Sets the value of the tase2 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTase2(String value) {
            this.tase2 = value;
        }

        /**
         * Gets the value of the tase3 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTase3() {
            return tase3;
        }

        /**
         * Sets the value of the tase3 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTase3(String value) {
            this.tase3 = value;
        }

        /**
         * Gets the value of the tase4 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTase4() {
            return tase4;
        }

        /**
         * Sets the value of the tase4 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTase4(String value) {
            this.tase4 = value;
        }

        /**
         * Gets the value of the tase5 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTase5() {
            return tase5;
        }

        /**
         * Sets the value of the tase5 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTase5(String value) {
            this.tase5 = value;
        }

        /**
         * Gets the value of the tase6 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTase6() {
            return tase6;
        }

        /**
         * Sets the value of the tase6 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTase6(String value) {
            this.tase6 = value;
        }

        /**
         * Gets the value of the tase7 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTase7() {
            return tase7;
        }

        /**
         * Sets the value of the tase7 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTase7(String value) {
            this.tase7 = value;
        }

        /**
         * Gets the value of the tase8 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTase8() {
            return tase8;
        }

        /**
         * Sets the value of the tase8 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTase8(String value) {
            this.tase8 = value;
        }

        /**
         * Gets the value of the postiIndeks property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPostiIndeks() {
            return postiIndeks;
        }

        /**
         * Sets the value of the postiIndeks property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPostiIndeks(String value) {
            this.postiIndeks = value;
        }

        /**
         * Gets the value of the algusKP property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAlgusKP() {
            return algusKP;
        }

        /**
         * Sets the value of the algusKP property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAlgusKP(String value) {
            this.algusKP = value;
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
     *         &lt;element name="Isik" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
     *                   &lt;element name="ValisriigiIK"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodif2"/&gt;
     *                             &lt;element name="ValisriigiIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="EesnimiRR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="MuudEesnimed" minOccurs="0"&gt;
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
     *                   &lt;element name="PerenimiRR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="MuudPerenimed" minOccurs="0"&gt;
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
     *                   &lt;element name="PohiKodakondsus" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodif2"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Kodakondsused" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodif2" maxOccurs="unbounded"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Lisainfo" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                             &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
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
        protected List<RRExtDocumentDataRequest.Isikud.Isik> isik;

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
         * {@link RRExtDocumentDataRequest.Isikud.Isik }
         * 
         * 
         */
        public List<RRExtDocumentDataRequest.Isikud.Isik> getIsik() {
            if (isik == null) {
                isik = new ArrayList<RRExtDocumentDataRequest.Isikud.Isik>();
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
         *         &lt;element name="ValisriigiIK"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodif2"/&gt;
         *                   &lt;element name="ValisriigiIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="EesnimiRR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="MuudEesnimed" minOccurs="0"&gt;
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
         *         &lt;element name="PerenimiRR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="MuudPerenimed" minOccurs="0"&gt;
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
         *         &lt;element name="PohiKodakondsus" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodif2"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="Kodakondsused" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodif2" maxOccurs="unbounded"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="Lisainfo" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                   &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
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
            "eesnimiRR",
            "muudEesnimed",
            "perenimi",
            "perenimiRR",
            "muudPerenimed",
            "pohiKodakondsus",
            "kodakondsused",
            "lisainfo"
        })
        public static class Isik {

            @XmlElement(name = "Isikukood", required = true)
            protected String isikukood;
            @XmlElement(name = "ValisriigiIK", required = true)
            protected RRExtDocumentDataRequest.Isikud.Isik.ValisriigiIK valisriigiIK;
            @XmlElement(name = "Eesnimi", required = true)
            protected String eesnimi;
            @XmlElement(name = "EesnimiRR", required = true)
            protected String eesnimiRR;
            @XmlElement(name = "MuudEesnimed")
            protected RRExtDocumentDataRequest.Isikud.Isik.MuudEesnimed muudEesnimed;
            @XmlElement(name = "Perenimi", required = true)
            protected String perenimi;
            @XmlElement(name = "PerenimiRR", required = true)
            protected String perenimiRR;
            @XmlElement(name = "MuudPerenimed")
            protected RRExtDocumentDataRequest.Isikud.Isik.MuudPerenimed muudPerenimed;
            @XmlElement(name = "PohiKodakondsus")
            protected RRExtDocumentDataRequest.Isikud.Isik.PohiKodakondsus pohiKodakondsus;
            @XmlElement(name = "Kodakondsused")
            protected RRExtDocumentDataRequest.Isikud.Isik.Kodakondsused kodakondsused;
            @XmlElement(name = "Lisainfo")
            protected RRExtDocumentDataRequest.Isikud.Isik.Lisainfo lisainfo;

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
             * @return
             *     possible object is
             *     {@link RRExtDocumentDataRequest.Isikud.Isik.ValisriigiIK }
             *     
             */
            public RRExtDocumentDataRequest.Isikud.Isik.ValisriigiIK getValisriigiIK() {
                return valisriigiIK;
            }

            /**
             * Sets the value of the valisriigiIK property.
             * 
             * @param value
             *     allowed object is
             *     {@link RRExtDocumentDataRequest.Isikud.Isik.ValisriigiIK }
             *     
             */
            public void setValisriigiIK(RRExtDocumentDataRequest.Isikud.Isik.ValisriigiIK value) {
                this.valisriigiIK = value;
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
             * Gets the value of the eesnimiRR property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEesnimiRR() {
                return eesnimiRR;
            }

            /**
             * Sets the value of the eesnimiRR property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEesnimiRR(String value) {
                this.eesnimiRR = value;
            }

            /**
             * Gets the value of the muudEesnimed property.
             * 
             * @return
             *     possible object is
             *     {@link RRExtDocumentDataRequest.Isikud.Isik.MuudEesnimed }
             *     
             */
            public RRExtDocumentDataRequest.Isikud.Isik.MuudEesnimed getMuudEesnimed() {
                return muudEesnimed;
            }

            /**
             * Sets the value of the muudEesnimed property.
             * 
             * @param value
             *     allowed object is
             *     {@link RRExtDocumentDataRequest.Isikud.Isik.MuudEesnimed }
             *     
             */
            public void setMuudEesnimed(RRExtDocumentDataRequest.Isikud.Isik.MuudEesnimed value) {
                this.muudEesnimed = value;
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
             * Gets the value of the perenimiRR property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPerenimiRR() {
                return perenimiRR;
            }

            /**
             * Sets the value of the perenimiRR property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPerenimiRR(String value) {
                this.perenimiRR = value;
            }

            /**
             * Gets the value of the muudPerenimed property.
             * 
             * @return
             *     possible object is
             *     {@link RRExtDocumentDataRequest.Isikud.Isik.MuudPerenimed }
             *     
             */
            public RRExtDocumentDataRequest.Isikud.Isik.MuudPerenimed getMuudPerenimed() {
                return muudPerenimed;
            }

            /**
             * Sets the value of the muudPerenimed property.
             * 
             * @param value
             *     allowed object is
             *     {@link RRExtDocumentDataRequest.Isikud.Isik.MuudPerenimed }
             *     
             */
            public void setMuudPerenimed(RRExtDocumentDataRequest.Isikud.Isik.MuudPerenimed value) {
                this.muudPerenimed = value;
            }

            /**
             * Gets the value of the pohiKodakondsus property.
             * 
             * @return
             *     possible object is
             *     {@link RRExtDocumentDataRequest.Isikud.Isik.PohiKodakondsus }
             *     
             */
            public RRExtDocumentDataRequest.Isikud.Isik.PohiKodakondsus getPohiKodakondsus() {
                return pohiKodakondsus;
            }

            /**
             * Sets the value of the pohiKodakondsus property.
             * 
             * @param value
             *     allowed object is
             *     {@link RRExtDocumentDataRequest.Isikud.Isik.PohiKodakondsus }
             *     
             */
            public void setPohiKodakondsus(RRExtDocumentDataRequest.Isikud.Isik.PohiKodakondsus value) {
                this.pohiKodakondsus = value;
            }

            /**
             * Gets the value of the kodakondsused property.
             * 
             * @return
             *     possible object is
             *     {@link RRExtDocumentDataRequest.Isikud.Isik.Kodakondsused }
             *     
             */
            public RRExtDocumentDataRequest.Isikud.Isik.Kodakondsused getKodakondsused() {
                return kodakondsused;
            }

            /**
             * Sets the value of the kodakondsused property.
             * 
             * @param value
             *     allowed object is
             *     {@link RRExtDocumentDataRequest.Isikud.Isik.Kodakondsused }
             *     
             */
            public void setKodakondsused(RRExtDocumentDataRequest.Isikud.Isik.Kodakondsused value) {
                this.kodakondsused = value;
            }

            /**
             * Gets the value of the lisainfo property.
             * 
             * @return
             *     possible object is
             *     {@link RRExtDocumentDataRequest.Isikud.Isik.Lisainfo }
             *     
             */
            public RRExtDocumentDataRequest.Isikud.Isik.Lisainfo getLisainfo() {
                return lisainfo;
            }

            /**
             * Sets the value of the lisainfo property.
             * 
             * @param value
             *     allowed object is
             *     {@link RRExtDocumentDataRequest.Isikud.Isik.Lisainfo }
             *     
             */
            public void setLisainfo(RRExtDocumentDataRequest.Isikud.Isik.Lisainfo value) {
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
             *         &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodif2" maxOccurs="unbounded"/&gt;
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
            public static class Kodakondsused {

                @XmlElement(name = "Riik", required = true)
                protected List<Kodif2> riik;

                /**
                 * Gets the value of the riik property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the riik property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getRiik().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Kodif2 }
                 * 
                 * 
                 */
                public List<Kodif2> getRiik() {
                    if (riik == null) {
                        riik = new ArrayList<Kodif2>();
                    }
                    return this.riik;
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
             *         &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
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
                @XmlElement(name = "Synniaeg")
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
             *         &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodif2"/&gt;
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
                protected Kodif2 riik;

                /**
                 * Gets the value of the riik property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Kodif2 }
                 *     
                 */
                public Kodif2 getRiik() {
                    return riik;
                }

                /**
                 * Sets the value of the riik property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Kodif2 }
                 *     
                 */
                public void setRiik(Kodif2 value) {
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
             *         &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodif2"/&gt;
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
                protected Kodif2 riik;
                @XmlElement(name = "ValisriigiIK", required = true)
                protected String valisriigiIK;

                /**
                 * Gets the value of the riik property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Kodif2 }
                 *     
                 */
                public Kodif2 getRiik() {
                    return riik;
                }

                /**
                 * Sets the value of the riik property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Kodif2 }
                 *     
                 */
                public void setRiik(Kodif2 value) {
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
     *         &lt;element name="OtsuseNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="Alus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="OtsuseKuupaev" type="{http://rr.x-road.eu/producer}date"/&gt;
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
        "otsuseNumber",
        "alus",
        "otsuseKuupaev"
    })
    public static class Menetlus {

        @XmlElement(name = "OtsuseNumber")
        protected String otsuseNumber;
        @XmlElement(name = "Alus")
        protected String alus;
        @XmlElement(name = "OtsuseKuupaev", required = true)
        protected String otsuseKuupaev;

        /**
         * Gets the value of the otsuseNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOtsuseNumber() {
            return otsuseNumber;
        }

        /**
         * Sets the value of the otsuseNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOtsuseNumber(String value) {
            this.otsuseNumber = value;
        }

        /**
         * Gets the value of the alus property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAlus() {
            return alus;
        }

        /**
         * Sets the value of the alus property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAlus(String value) {
            this.alus = value;
        }

        /**
         * Gets the value of the otsuseKuupaev property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOtsuseKuupaev() {
            return otsuseKuupaev;
        }

        /**
         * Sets the value of the otsuseKuupaev property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOtsuseKuupaev(String value) {
            this.otsuseKuupaev = value;
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
     *         &lt;element name="Kontakt"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Liik" type="{http://rr.x-road.eu/producer}Kodif2"/&gt;
     *                   &lt;element name="KontaktiTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AlatesKP" type="{http://rr.x-road.eu/producer}date"/&gt;
     *                   &lt;element name="KuniKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
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
        "kontakt"
    })
    public static class Sideandmed {

        @XmlElement(name = "Kontakt", required = true)
        protected RRExtDocumentDataRequest.Sideandmed.Kontakt kontakt;

        /**
         * Gets the value of the kontakt property.
         * 
         * @return
         *     possible object is
         *     {@link RRExtDocumentDataRequest.Sideandmed.Kontakt }
         *     
         */
        public RRExtDocumentDataRequest.Sideandmed.Kontakt getKontakt() {
            return kontakt;
        }

        /**
         * Sets the value of the kontakt property.
         * 
         * @param value
         *     allowed object is
         *     {@link RRExtDocumentDataRequest.Sideandmed.Kontakt }
         *     
         */
        public void setKontakt(RRExtDocumentDataRequest.Sideandmed.Kontakt value) {
            this.kontakt = value;
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
         *         &lt;element name="Liik" type="{http://rr.x-road.eu/producer}Kodif2"/&gt;
         *         &lt;element name="KontaktiTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AlatesKP" type="{http://rr.x-road.eu/producer}date"/&gt;
         *         &lt;element name="KuniKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
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
            "kontaktiTekst",
            "alatesKP",
            "kuniKP"
        })
        public static class Kontakt {

            @XmlElement(name = "Liik", required = true)
            protected Kodif2 liik;
            @XmlElement(name = "KontaktiTekst", required = true)
            protected String kontaktiTekst;
            @XmlElement(name = "AlatesKP", required = true)
            protected String alatesKP;
            @XmlElement(name = "KuniKP")
            protected String kuniKP;

            /**
             * Gets the value of the liik property.
             * 
             * @return
             *     possible object is
             *     {@link Kodif2 }
             *     
             */
            public Kodif2 getLiik() {
                return liik;
            }

            /**
             * Sets the value of the liik property.
             * 
             * @param value
             *     allowed object is
             *     {@link Kodif2 }
             *     
             */
            public void setLiik(Kodif2 value) {
                this.liik = value;
            }

            /**
             * Gets the value of the kontaktiTekst property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktiTekst() {
                return kontaktiTekst;
            }

            /**
             * Sets the value of the kontaktiTekst property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktiTekst(String value) {
                this.kontaktiTekst = value;
            }

            /**
             * Gets the value of the alatesKP property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAlatesKP() {
                return alatesKP;
            }

            /**
             * Sets the value of the alatesKP property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAlatesKP(String value) {
                this.alatesKP = value;
            }

            /**
             * Gets the value of the kuniKP property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKuniKP() {
                return kuniKP;
            }

            /**
             * Sets the value of the kuniKP property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKuniKP(String value) {
                this.kuniKP = value;
            }

        }

    }

}
