
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR63isikAadrDokResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR63isikAadrDokResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="veakood" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="veatekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Isikud"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Isikuandmed" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Isikuandmed.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Surmaaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Sünniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Kodakondsuskoodina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Kodakondsustekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.MuudPerenimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.MuudEesnimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *                   &lt;element name="Dokumendiandmed" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Dokumendiandmed.Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Seeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Kuupaev" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Asutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.KehtivAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.KehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.IsikuIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Perekonnanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *         &lt;element name="Elukohad"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Elukohaandmed" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Elukohaandmed.Riigikood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.Riiginimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.MaakonnaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.MaakonnaNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.VallaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.VallaNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.KylaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.KylaNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.Majanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.Korterinr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.Algusekuupaev" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.AadressiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.IsikuIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR63isikAadrDokResponseType", propOrder = {
    "veakood",
    "veatekst",
    "isikud",
    "dokumendid",
    "elukohad"
})
public class RR63IsikAadrDokResponseType
    extends XRoadResponseBaseType
{

    protected BigInteger veakood;
    protected String veatekst;
    @XmlElement(name = "Isikud", required = true)
    protected RR63IsikAadrDokResponseType.Isikud isikud;
    @XmlElement(name = "Dokumendid", required = true)
    protected RR63IsikAadrDokResponseType.Dokumendid dokumendid;
    @XmlElement(name = "Elukohad", required = true)
    protected RR63IsikAadrDokResponseType.Elukohad elukohad;

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
     *     {@link RR63IsikAadrDokResponseType.Isikud }
     *     
     */
    public RR63IsikAadrDokResponseType.Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR63IsikAadrDokResponseType.Isikud }
     *     
     */
    public void setIsikud(RR63IsikAadrDokResponseType.Isikud value) {
        this.isikud = value;
    }

    /**
     * Gets the value of the dokumendid property.
     * 
     * @return
     *     possible object is
     *     {@link RR63IsikAadrDokResponseType.Dokumendid }
     *     
     */
    public RR63IsikAadrDokResponseType.Dokumendid getDokumendid() {
        return dokumendid;
    }

    /**
     * Sets the value of the dokumendid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR63IsikAadrDokResponseType.Dokumendid }
     *     
     */
    public void setDokumendid(RR63IsikAadrDokResponseType.Dokumendid value) {
        this.dokumendid = value;
    }

    /**
     * Gets the value of the elukohad property.
     * 
     * @return
     *     possible object is
     *     {@link RR63IsikAadrDokResponseType.Elukohad }
     *     
     */
    public RR63IsikAadrDokResponseType.Elukohad getElukohad() {
        return elukohad;
    }

    /**
     * Sets the value of the elukohad property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR63IsikAadrDokResponseType.Elukohad }
     *     
     */
    public void setElukohad(RR63IsikAadrDokResponseType.Elukohad value) {
        this.elukohad = value;
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
     *                   &lt;element name="Dokumendiandmed.Kuupaev" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Asutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.KehtivAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.KehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.IsikuIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Perekonnanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        protected List<RR63IsikAadrDokResponseType.Dokumendid.Dokumendiandmed> dokumendiandmed;

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
         * {@link RR63IsikAadrDokResponseType.Dokumendid.Dokumendiandmed }
         * 
         * 
         */
        public List<RR63IsikAadrDokResponseType.Dokumendid.Dokumendiandmed> getDokumendiandmed() {
            if (dokumendiandmed == null) {
                dokumendiandmed = new ArrayList<RR63IsikAadrDokResponseType.Dokumendid.Dokumendiandmed>();
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
         *         &lt;element name="Dokumendiandmed.Kuupaev" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Asutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.KehtivAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.KehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.IsikuIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Perekonnanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "dokumendiandmedKuupaev",
            "dokumendiandmedAsutus",
            "dokumendiandmedKehtivAlates",
            "dokumendiandmedKehtivKuni",
            "dokumendiandmedIsikuIsikukood",
            "dokumendiandmedIsikukood",
            "dokumendiandmedPerekonnanimi",
            "dokumendiandmedEesnimi"
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
            @XmlElement(name = "Dokumendiandmed.Kuupaev", required = true)
            protected String dokumendiandmedKuupaev;
            @XmlElement(name = "Dokumendiandmed.Asutus", required = true)
            protected String dokumendiandmedAsutus;
            @XmlElement(name = "Dokumendiandmed.KehtivAlates", required = true)
            protected String dokumendiandmedKehtivAlates;
            @XmlElement(name = "Dokumendiandmed.KehtivKuni", required = true)
            protected String dokumendiandmedKehtivKuni;
            @XmlElement(name = "Dokumendiandmed.IsikuIsikukood", required = true)
            protected String dokumendiandmedIsikuIsikukood;
            @XmlElement(name = "Dokumendiandmed.Isikukood", required = true)
            protected String dokumendiandmedIsikukood;
            @XmlElement(name = "Dokumendiandmed.Perekonnanimi", required = true)
            protected String dokumendiandmedPerekonnanimi;
            @XmlElement(name = "Dokumendiandmed.Eesnimi", required = true)
            protected String dokumendiandmedEesnimi;

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
             * Gets the value of the dokumendiandmedKuupaev property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedKuupaev() {
                return dokumendiandmedKuupaev;
            }

            /**
             * Sets the value of the dokumendiandmedKuupaev property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedKuupaev(String value) {
                this.dokumendiandmedKuupaev = value;
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
             * Gets the value of the dokumendiandmedIsikuIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedIsikuIsikukood() {
                return dokumendiandmedIsikuIsikukood;
            }

            /**
             * Sets the value of the dokumendiandmedIsikuIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedIsikuIsikukood(String value) {
                this.dokumendiandmedIsikuIsikukood = value;
            }

            /**
             * Gets the value of the dokumendiandmedIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedIsikukood() {
                return dokumendiandmedIsikukood;
            }

            /**
             * Sets the value of the dokumendiandmedIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedIsikukood(String value) {
                this.dokumendiandmedIsikukood = value;
            }

            /**
             * Gets the value of the dokumendiandmedPerekonnanimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedPerekonnanimi() {
                return dokumendiandmedPerekonnanimi;
            }

            /**
             * Sets the value of the dokumendiandmedPerekonnanimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedPerekonnanimi(String value) {
                this.dokumendiandmedPerekonnanimi = value;
            }

            /**
             * Gets the value of the dokumendiandmedEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedEesnimi() {
                return dokumendiandmedEesnimi;
            }

            /**
             * Sets the value of the dokumendiandmedEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedEesnimi(String value) {
                this.dokumendiandmedEesnimi = value;
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
     *         &lt;element name="Elukohaandmed" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Elukohaandmed.Riigikood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.Riiginimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.MaakonnaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.MaakonnaNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.VallaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.VallaNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.KylaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.KylaNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.Majanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.Korterinr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.Algusekuupaev" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.AadressiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.IsikuIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "elukohaandmed"
    })
    public static class Elukohad {

        @XmlElement(name = "Elukohaandmed")
        protected List<RR63IsikAadrDokResponseType.Elukohad.Elukohaandmed> elukohaandmed;

        /**
         * Gets the value of the elukohaandmed property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the elukohaandmed property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getElukohaandmed().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR63IsikAadrDokResponseType.Elukohad.Elukohaandmed }
         * 
         * 
         */
        public List<RR63IsikAadrDokResponseType.Elukohad.Elukohaandmed> getElukohaandmed() {
            if (elukohaandmed == null) {
                elukohaandmed = new ArrayList<RR63IsikAadrDokResponseType.Elukohad.Elukohaandmed>();
            }
            return this.elukohaandmed;
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
         *         &lt;element name="Elukohaandmed.Riigikood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.Riiginimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.MaakonnaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.MaakonnaNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.VallaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.VallaNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.KylaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.KylaNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.Majanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.Korterinr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.Algusekuupaev" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.AadressiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.IsikuIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "elukohaandmedRiigikood",
            "elukohaandmedRiiginimetus",
            "elukohaandmedMaakonnaKood",
            "elukohaandmedMaakonnaNimetus",
            "elukohaandmedVallaKood",
            "elukohaandmedVallaNimetus",
            "elukohaandmedKylaKood",
            "elukohaandmedKylaNimetus",
            "elukohaandmedTanav",
            "elukohaandmedMajanr",
            "elukohaandmedKorterinr",
            "elukohaandmedAadressTekstina",
            "elukohaandmedAlgusekuupaev",
            "elukohaandmedAadressiLiik",
            "elukohaandmedIsikuIsikukood"
        })
        public static class Elukohaandmed {

            @XmlElement(name = "Elukohaandmed.Riigikood", required = true)
            protected String elukohaandmedRiigikood;
            @XmlElement(name = "Elukohaandmed.Riiginimetus", required = true)
            protected String elukohaandmedRiiginimetus;
            @XmlElement(name = "Elukohaandmed.MaakonnaKood", required = true)
            protected String elukohaandmedMaakonnaKood;
            @XmlElement(name = "Elukohaandmed.MaakonnaNimetus", required = true)
            protected String elukohaandmedMaakonnaNimetus;
            @XmlElement(name = "Elukohaandmed.VallaKood", required = true)
            protected String elukohaandmedVallaKood;
            @XmlElement(name = "Elukohaandmed.VallaNimetus", required = true)
            protected String elukohaandmedVallaNimetus;
            @XmlElement(name = "Elukohaandmed.KylaKood", required = true)
            protected String elukohaandmedKylaKood;
            @XmlElement(name = "Elukohaandmed.KylaNimetus", required = true)
            protected String elukohaandmedKylaNimetus;
            @XmlElement(name = "Elukohaandmed.Tanav", required = true)
            protected String elukohaandmedTanav;
            @XmlElement(name = "Elukohaandmed.Majanr", required = true)
            protected String elukohaandmedMajanr;
            @XmlElement(name = "Elukohaandmed.Korterinr", required = true)
            protected String elukohaandmedKorterinr;
            @XmlElement(name = "Elukohaandmed.AadressTekstina", required = true)
            protected String elukohaandmedAadressTekstina;
            @XmlElement(name = "Elukohaandmed.Algusekuupaev", required = true)
            protected String elukohaandmedAlgusekuupaev;
            @XmlElement(name = "Elukohaandmed.AadressiLiik", required = true)
            protected String elukohaandmedAadressiLiik;
            @XmlElement(name = "Elukohaandmed.IsikuIsikukood", required = true)
            protected String elukohaandmedIsikuIsikukood;

            /**
             * Gets the value of the elukohaandmedRiigikood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedRiigikood() {
                return elukohaandmedRiigikood;
            }

            /**
             * Sets the value of the elukohaandmedRiigikood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedRiigikood(String value) {
                this.elukohaandmedRiigikood = value;
            }

            /**
             * Gets the value of the elukohaandmedRiiginimetus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedRiiginimetus() {
                return elukohaandmedRiiginimetus;
            }

            /**
             * Sets the value of the elukohaandmedRiiginimetus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedRiiginimetus(String value) {
                this.elukohaandmedRiiginimetus = value;
            }

            /**
             * Gets the value of the elukohaandmedMaakonnaKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedMaakonnaKood() {
                return elukohaandmedMaakonnaKood;
            }

            /**
             * Sets the value of the elukohaandmedMaakonnaKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedMaakonnaKood(String value) {
                this.elukohaandmedMaakonnaKood = value;
            }

            /**
             * Gets the value of the elukohaandmedMaakonnaNimetus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedMaakonnaNimetus() {
                return elukohaandmedMaakonnaNimetus;
            }

            /**
             * Sets the value of the elukohaandmedMaakonnaNimetus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedMaakonnaNimetus(String value) {
                this.elukohaandmedMaakonnaNimetus = value;
            }

            /**
             * Gets the value of the elukohaandmedVallaKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedVallaKood() {
                return elukohaandmedVallaKood;
            }

            /**
             * Sets the value of the elukohaandmedVallaKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedVallaKood(String value) {
                this.elukohaandmedVallaKood = value;
            }

            /**
             * Gets the value of the elukohaandmedVallaNimetus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedVallaNimetus() {
                return elukohaandmedVallaNimetus;
            }

            /**
             * Sets the value of the elukohaandmedVallaNimetus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedVallaNimetus(String value) {
                this.elukohaandmedVallaNimetus = value;
            }

            /**
             * Gets the value of the elukohaandmedKylaKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedKylaKood() {
                return elukohaandmedKylaKood;
            }

            /**
             * Sets the value of the elukohaandmedKylaKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedKylaKood(String value) {
                this.elukohaandmedKylaKood = value;
            }

            /**
             * Gets the value of the elukohaandmedKylaNimetus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedKylaNimetus() {
                return elukohaandmedKylaNimetus;
            }

            /**
             * Sets the value of the elukohaandmedKylaNimetus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedKylaNimetus(String value) {
                this.elukohaandmedKylaNimetus = value;
            }

            /**
             * Gets the value of the elukohaandmedTanav property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedTanav() {
                return elukohaandmedTanav;
            }

            /**
             * Sets the value of the elukohaandmedTanav property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedTanav(String value) {
                this.elukohaandmedTanav = value;
            }

            /**
             * Gets the value of the elukohaandmedMajanr property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedMajanr() {
                return elukohaandmedMajanr;
            }

            /**
             * Sets the value of the elukohaandmedMajanr property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedMajanr(String value) {
                this.elukohaandmedMajanr = value;
            }

            /**
             * Gets the value of the elukohaandmedKorterinr property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedKorterinr() {
                return elukohaandmedKorterinr;
            }

            /**
             * Sets the value of the elukohaandmedKorterinr property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedKorterinr(String value) {
                this.elukohaandmedKorterinr = value;
            }

            /**
             * Gets the value of the elukohaandmedAadressTekstina property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedAadressTekstina() {
                return elukohaandmedAadressTekstina;
            }

            /**
             * Sets the value of the elukohaandmedAadressTekstina property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedAadressTekstina(String value) {
                this.elukohaandmedAadressTekstina = value;
            }

            /**
             * Gets the value of the elukohaandmedAlgusekuupaev property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedAlgusekuupaev() {
                return elukohaandmedAlgusekuupaev;
            }

            /**
             * Sets the value of the elukohaandmedAlgusekuupaev property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedAlgusekuupaev(String value) {
                this.elukohaandmedAlgusekuupaev = value;
            }

            /**
             * Gets the value of the elukohaandmedAadressiLiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedAadressiLiik() {
                return elukohaandmedAadressiLiik;
            }

            /**
             * Sets the value of the elukohaandmedAadressiLiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedAadressiLiik(String value) {
                this.elukohaandmedAadressiLiik = value;
            }

            /**
             * Gets the value of the elukohaandmedIsikuIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedIsikuIsikukood() {
                return elukohaandmedIsikuIsikukood;
            }

            /**
             * Sets the value of the elukohaandmedIsikuIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedIsikuIsikukood(String value) {
                this.elukohaandmedIsikuIsikukood = value;
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
     *         &lt;element name="Isikuandmed" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Isikuandmed.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Surmaaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.S�nniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Kodakondsuskoodina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Kodakondsustekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.MuudPerenimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.MuudEesnimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "isikuandmed"
    })
    public static class Isikud {

        @XmlElement(name = "Isikuandmed")
        protected List<RR63IsikAadrDokResponseType.Isikud.Isikuandmed> isikuandmed;

        /**
         * Gets the value of the isikuandmed property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the isikuandmed property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIsikuandmed().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR63IsikAadrDokResponseType.Isikud.Isikuandmed }
         * 
         * 
         */
        public List<RR63IsikAadrDokResponseType.Isikud.Isikuandmed> getIsikuandmed() {
            if (isikuandmed == null) {
                isikuandmed = new ArrayList<RR63IsikAadrDokResponseType.Isikud.Isikuandmed>();
            }
            return this.isikuandmed;
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
         *         &lt;element name="Isikuandmed.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Surmaaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Sünniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Kodakondsuskoodina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Kodakondsustekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.MuudPerenimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.MuudEesnimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "isikuandmedPerenimi",
            "isikuandmedEesnimi",
            "isikuandmedIsikuStaatus",
            "isikuandmedSurmaaeg",
            "isikuandmedIsikukood",
            "isikuandmedSynniaeg",
            "isikuandmedKodakondsuskoodina",
            "isikuandmedKodakondsustekstina",
            "isikuandmedMuudPerenimed",
            "isikuandmedMuudEesnimed"
        })
        public static class Isikuandmed {

            @XmlElement(name = "Isikuandmed.Perenimi", required = true)
            protected String isikuandmedPerenimi;
            @XmlElement(name = "Isikuandmed.Eesnimi", required = true)
            protected String isikuandmedEesnimi;
            @XmlElement(name = "Isikuandmed.IsikuStaatus", required = true)
            protected String isikuandmedIsikuStaatus;
            @XmlElement(name = "Isikuandmed.Surmaaeg", required = true)
            protected String isikuandmedSurmaaeg;
            @XmlElement(name = "Isikuandmed.Isikukood", required = true)
            protected String isikuandmedIsikukood;
            @XmlElement(name = "Isikuandmed.Sünniaeg", required = true)
            protected String isikuandmedSynniaeg;
            @XmlElement(name = "Isikuandmed.Kodakondsuskoodina", required = true)
            protected String isikuandmedKodakondsuskoodina;
            @XmlElement(name = "Isikuandmed.Kodakondsustekstina", required = true)
            protected String isikuandmedKodakondsustekstina;
            @XmlElement(name = "Isikuandmed.MuudPerenimed", required = true)
            protected String isikuandmedMuudPerenimed;
            @XmlElement(name = "Isikuandmed.MuudEesnimed", required = true)
            protected String isikuandmedMuudEesnimed;

            /**
             * Gets the value of the isikuandmedPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedPerenimi() {
                return isikuandmedPerenimi;
            }

            /**
             * Sets the value of the isikuandmedPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedPerenimi(String value) {
                this.isikuandmedPerenimi = value;
            }

            /**
             * Gets the value of the isikuandmedEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedEesnimi() {
                return isikuandmedEesnimi;
            }

            /**
             * Sets the value of the isikuandmedEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedEesnimi(String value) {
                this.isikuandmedEesnimi = value;
            }

            /**
             * Gets the value of the isikuandmedIsikuStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedIsikuStaatus() {
                return isikuandmedIsikuStaatus;
            }

            /**
             * Sets the value of the isikuandmedIsikuStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedIsikuStaatus(String value) {
                this.isikuandmedIsikuStaatus = value;
            }

            /**
             * Gets the value of the isikuandmedSurmaaeg property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSurmaaeg() {
                return isikuandmedSurmaaeg;
            }

            /**
             * Sets the value of the isikuandmedSurmaaeg property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSurmaaeg(String value) {
                this.isikuandmedSurmaaeg = value;
            }

            /**
             * Gets the value of the isikuandmedIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedIsikukood() {
                return isikuandmedIsikukood;
            }

            /**
             * Sets the value of the isikuandmedIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedIsikukood(String value) {
                this.isikuandmedIsikukood = value;
            }

            /**
             * Gets the value of the isikuandmedS�nniaeg property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSynniaeg() {
                return isikuandmedSynniaeg;
            }

            /**
             * Sets the value of the isikuandmedS�nniaeg property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSynniaeg(String value) {
                this.isikuandmedSynniaeg = value;
            }

            /**
             * Gets the value of the isikuandmedKodakondsuskoodina property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedKodakondsuskoodina() {
                return isikuandmedKodakondsuskoodina;
            }

            /**
             * Sets the value of the isikuandmedKodakondsuskoodina property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedKodakondsuskoodina(String value) {
                this.isikuandmedKodakondsuskoodina = value;
            }

            /**
             * Gets the value of the isikuandmedKodakondsustekstina property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedKodakondsustekstina() {
                return isikuandmedKodakondsustekstina;
            }

            /**
             * Sets the value of the isikuandmedKodakondsustekstina property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedKodakondsustekstina(String value) {
                this.isikuandmedKodakondsustekstina = value;
            }

            /**
             * Gets the value of the isikuandmedMuudPerenimed property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedMuudPerenimed() {
                return isikuandmedMuudPerenimed;
            }

            /**
             * Sets the value of the isikuandmedMuudPerenimed property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedMuudPerenimed(String value) {
                this.isikuandmedMuudPerenimed = value;
            }

            /**
             * Gets the value of the isikuandmedMuudEesnimed property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedMuudEesnimed() {
                return isikuandmedMuudEesnimed;
            }

            /**
             * Sets the value of the isikuandmedMuudEesnimed property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedMuudEesnimed(String value) {
                this.isikuandmedMuudEesnimed = value;
            }

        }

    }

}
