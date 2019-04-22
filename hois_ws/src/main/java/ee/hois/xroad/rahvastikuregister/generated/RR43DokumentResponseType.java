
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR43dokumentResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR43dokumentResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="veakood" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="veatekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dok"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="item" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="dok.IsikIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dok.IsikEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dok.IsikPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dok.IsikStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dok.DokRoll" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dok.DokIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dok.DokEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dok.DokPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dok.DokVanaEnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dok.DokVanaPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dok.DokTyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dok.DokRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dok.DokSeeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dok.DokNr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dok.DokSyndKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dok.DokKehtAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dok.DokKehtLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dok.DokValjastamisKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dok.DokValjastaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR43dokumentResponseType", propOrder = {
    "veakood",
    "veatekst",
    "dok"
})
public class RR43DokumentResponseType
    extends XRoadResponseBaseType
{

    protected BigInteger veakood;
    protected String veatekst;
    @XmlElement(required = true)
    protected RR43DokumentResponseType.Dok dok;

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
     * Gets the value of the dok property.
     * 
     * @return
     *     possible object is
     *     {@link RR43DokumentResponseType.Dok }
     *     
     */
    public RR43DokumentResponseType.Dok getDok() {
        return dok;
    }

    /**
     * Sets the value of the dok property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR43DokumentResponseType.Dok }
     *     
     */
    public void setDok(RR43DokumentResponseType.Dok value) {
        this.dok = value;
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
     *         &lt;element name="item" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="dok.IsikIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dok.IsikEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dok.IsikPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dok.IsikStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dok.DokRoll" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dok.DokIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dok.DokEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dok.DokPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dok.DokVanaEnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dok.DokVanaPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dok.DokTyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dok.DokRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dok.DokSeeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dok.DokNr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dok.DokSyndKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dok.DokKehtAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dok.DokKehtLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dok.DokValjastamisKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dok.DokValjastaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "item"
    })
    public static class Dok {

        protected List<RR43DokumentResponseType.Dok.Item> item;

        /**
         * Gets the value of the item property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the item property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR43DokumentResponseType.Dok.Item }
         * 
         * 
         */
        public List<RR43DokumentResponseType.Dok.Item> getItem() {
            if (item == null) {
                item = new ArrayList<RR43DokumentResponseType.Dok.Item>();
            }
            return this.item;
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
         *         &lt;element name="dok.IsikIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dok.IsikEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dok.IsikPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dok.IsikStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dok.DokRoll" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dok.DokIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dok.DokEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dok.DokPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dok.DokVanaEnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dok.DokVanaPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dok.DokTyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dok.DokRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dok.DokSeeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dok.DokNr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dok.DokSyndKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dok.DokKehtAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dok.DokKehtLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dok.DokValjastamisKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dok.DokValjastaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "dokIsikIsikukood",
            "dokIsikEesnimi",
            "dokIsikPerenimi",
            "dokIsikStaatus",
            "dokDokRoll",
            "dokDokIsikukood",
            "dokDokEesnimi",
            "dokDokPerenimi",
            "dokDokVanaEnimi",
            "dokDokVanaPerenimi",
            "dokDokTyyp",
            "dokDokRiik",
            "dokDokSeeria",
            "dokDokNr",
            "dokDokSyndKuup",
            "dokDokKehtAlgus",
            "dokDokKehtLopp",
            "dokDokValjastamisKuup",
            "dokDokValjastaja",
            "dokDokStaatus"
        })
        public static class Item {

            @XmlElement(name = "dok.IsikIsikukood", required = true)
            protected String dokIsikIsikukood;
            @XmlElement(name = "dok.IsikEesnimi", required = true)
            protected String dokIsikEesnimi;
            @XmlElement(name = "dok.IsikPerenimi", required = true)
            protected String dokIsikPerenimi;
            @XmlElement(name = "dok.IsikStaatus", required = true)
            protected String dokIsikStaatus;
            @XmlElement(name = "dok.DokRoll", required = true)
            protected String dokDokRoll;
            @XmlElement(name = "dok.DokIsikukood", required = true)
            protected String dokDokIsikukood;
            @XmlElement(name = "dok.DokEesnimi", required = true)
            protected String dokDokEesnimi;
            @XmlElement(name = "dok.DokPerenimi", required = true)
            protected String dokDokPerenimi;
            @XmlElement(name = "dok.DokVanaEnimi", required = true)
            protected String dokDokVanaEnimi;
            @XmlElement(name = "dok.DokVanaPerenimi", required = true)
            protected String dokDokVanaPerenimi;
            @XmlElement(name = "dok.DokTyyp", required = true)
            protected String dokDokTyyp;
            @XmlElement(name = "dok.DokRiik", required = true)
            protected String dokDokRiik;
            @XmlElement(name = "dok.DokSeeria", required = true)
            protected String dokDokSeeria;
            @XmlElement(name = "dok.DokNr", required = true)
            protected String dokDokNr;
            @XmlElement(name = "dok.DokSyndKuup", required = true)
            protected String dokDokSyndKuup;
            @XmlElement(name = "dok.DokKehtAlgus", required = true)
            protected String dokDokKehtAlgus;
            @XmlElement(name = "dok.DokKehtLopp", required = true)
            protected String dokDokKehtLopp;
            @XmlElement(name = "dok.DokValjastamisKuup", required = true)
            protected String dokDokValjastamisKuup;
            @XmlElement(name = "dok.DokValjastaja", required = true)
            protected String dokDokValjastaja;
            @XmlElement(name = "dok.DokStaatus", required = true)
            protected String dokDokStaatus;

            /**
             * Gets the value of the dokIsikIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokIsikIsikukood() {
                return dokIsikIsikukood;
            }

            /**
             * Sets the value of the dokIsikIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokIsikIsikukood(String value) {
                this.dokIsikIsikukood = value;
            }

            /**
             * Gets the value of the dokIsikEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokIsikEesnimi() {
                return dokIsikEesnimi;
            }

            /**
             * Sets the value of the dokIsikEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokIsikEesnimi(String value) {
                this.dokIsikEesnimi = value;
            }

            /**
             * Gets the value of the dokIsikPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokIsikPerenimi() {
                return dokIsikPerenimi;
            }

            /**
             * Sets the value of the dokIsikPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokIsikPerenimi(String value) {
                this.dokIsikPerenimi = value;
            }

            /**
             * Gets the value of the dokIsikStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokIsikStaatus() {
                return dokIsikStaatus;
            }

            /**
             * Sets the value of the dokIsikStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokIsikStaatus(String value) {
                this.dokIsikStaatus = value;
            }

            /**
             * Gets the value of the dokDokRoll property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokRoll() {
                return dokDokRoll;
            }

            /**
             * Sets the value of the dokDokRoll property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokRoll(String value) {
                this.dokDokRoll = value;
            }

            /**
             * Gets the value of the dokDokIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokIsikukood() {
                return dokDokIsikukood;
            }

            /**
             * Sets the value of the dokDokIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokIsikukood(String value) {
                this.dokDokIsikukood = value;
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
             * Gets the value of the dokDokVanaEnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokVanaEnimi() {
                return dokDokVanaEnimi;
            }

            /**
             * Sets the value of the dokDokVanaEnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokVanaEnimi(String value) {
                this.dokDokVanaEnimi = value;
            }

            /**
             * Gets the value of the dokDokVanaPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokVanaPerenimi() {
                return dokDokVanaPerenimi;
            }

            /**
             * Sets the value of the dokDokVanaPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokVanaPerenimi(String value) {
                this.dokDokVanaPerenimi = value;
            }

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
             * Gets the value of the dokDokRiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokRiik() {
                return dokDokRiik;
            }

            /**
             * Sets the value of the dokDokRiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokRiik(String value) {
                this.dokDokRiik = value;
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
             * Gets the value of the dokDokSyndKuup property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokSyndKuup() {
                return dokDokSyndKuup;
            }

            /**
             * Sets the value of the dokDokSyndKuup property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokSyndKuup(String value) {
                this.dokDokSyndKuup = value;
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
             * Gets the value of the dokDokValjastaja property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokValjastaja() {
                return dokDokValjastaja;
            }

            /**
             * Sets the value of the dokDokValjastaja property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokValjastaja(String value) {
                this.dokDokValjastaja = value;
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

}
