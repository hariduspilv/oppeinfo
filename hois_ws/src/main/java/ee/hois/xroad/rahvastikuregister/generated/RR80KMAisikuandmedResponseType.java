
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR80KMAisikuandmedResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR80KMAisikuandmedResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Istaatuskd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Istaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Kstaatuskd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Kstaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="TeovoimeKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Teovoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Veakood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="Veatekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Elukohad"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Elukoht" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Elukoht.Riikkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Maakondkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Maakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Linn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Asula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Maja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Korter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Sihtnumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Kuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *         &lt;element name="Postiaadressid"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Postiaadress" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Postiaadress.Riikkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Postiaadress.Maakondkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Postiaadress.Maakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Postiaadress.Linn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Postiaadress.Asula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Postiaadress.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Postiaadress.Maja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Postiaadress.Korter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Postiaadress.Sihtnumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Postiaadress.Alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Postiaadress.Kuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR80KMAisikuandmedResponseType", propOrder = {
    "eesnimi",
    "perenimi",
    "sugu",
    "synniaeg",
    "istaatuskd",
    "istaatus",
    "kstaatuskd",
    "kstaatus",
    "kodakondsus",
    "teovoimeKd",
    "teovoime",
    "veakood",
    "veatekst",
    "elukohad",
    "postiaadressid"
})
public class RR80KMAisikuandmedResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Eesnimi", required = true)
    protected String eesnimi;
    @XmlElement(name = "Perenimi", required = true)
    protected String perenimi;
    @XmlElement(name = "Sugu", required = true)
    protected String sugu;
    @XmlElement(name = "Synniaeg", required = true)
    protected String synniaeg;
    @XmlElement(name = "Istaatuskd", required = true)
    protected String istaatuskd;
    @XmlElement(name = "Istaatus", required = true)
    protected String istaatus;
    @XmlElement(name = "Kstaatuskd", required = true)
    protected String kstaatuskd;
    @XmlElement(name = "Kstaatus", required = true)
    protected String kstaatus;
    @XmlElement(name = "Kodakondsus", required = true)
    protected String kodakondsus;
    @XmlElement(name = "TeovoimeKd", required = true)
    protected String teovoimeKd;
    @XmlElement(name = "Teovoime", required = true)
    protected String teovoime;
    @XmlElement(name = "Veakood", required = true)
    protected BigInteger veakood;
    @XmlElement(name = "Veatekst", required = true)
    protected String veatekst;
    @XmlElement(name = "Elukohad", required = true)
    protected RR80KMAisikuandmedResponseType.Elukohad elukohad;
    @XmlElement(name = "Postiaadressid", required = true)
    protected RR80KMAisikuandmedResponseType.Postiaadressid postiaadressid;

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
     * Gets the value of the istaatuskd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIstaatuskd() {
        return istaatuskd;
    }

    /**
     * Sets the value of the istaatuskd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIstaatuskd(String value) {
        this.istaatuskd = value;
    }

    /**
     * Gets the value of the istaatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIstaatus() {
        return istaatus;
    }

    /**
     * Sets the value of the istaatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIstaatus(String value) {
        this.istaatus = value;
    }

    /**
     * Gets the value of the kstaatuskd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKstaatuskd() {
        return kstaatuskd;
    }

    /**
     * Sets the value of the kstaatuskd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKstaatuskd(String value) {
        this.kstaatuskd = value;
    }

    /**
     * Gets the value of the kstaatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKstaatus() {
        return kstaatus;
    }

    /**
     * Sets the value of the kstaatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKstaatus(String value) {
        this.kstaatus = value;
    }

    /**
     * Gets the value of the kodakondsus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKodakondsus() {
        return kodakondsus;
    }

    /**
     * Sets the value of the kodakondsus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKodakondsus(String value) {
        this.kodakondsus = value;
    }

    /**
     * Gets the value of the teovoimeKd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeovoimeKd() {
        return teovoimeKd;
    }

    /**
     * Sets the value of the teovoimeKd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeovoimeKd(String value) {
        this.teovoimeKd = value;
    }

    /**
     * Gets the value of the teovoime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeovoime() {
        return teovoime;
    }

    /**
     * Sets the value of the teovoime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeovoime(String value) {
        this.teovoime = value;
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
     * Gets the value of the elukohad property.
     * 
     * @return
     *     possible object is
     *     {@link RR80KMAisikuandmedResponseType.Elukohad }
     *     
     */
    public RR80KMAisikuandmedResponseType.Elukohad getElukohad() {
        return elukohad;
    }

    /**
     * Sets the value of the elukohad property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR80KMAisikuandmedResponseType.Elukohad }
     *     
     */
    public void setElukohad(RR80KMAisikuandmedResponseType.Elukohad value) {
        this.elukohad = value;
    }

    /**
     * Gets the value of the postiaadressid property.
     * 
     * @return
     *     possible object is
     *     {@link RR80KMAisikuandmedResponseType.Postiaadressid }
     *     
     */
    public RR80KMAisikuandmedResponseType.Postiaadressid getPostiaadressid() {
        return postiaadressid;
    }

    /**
     * Sets the value of the postiaadressid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR80KMAisikuandmedResponseType.Postiaadressid }
     *     
     */
    public void setPostiaadressid(RR80KMAisikuandmedResponseType.Postiaadressid value) {
        this.postiaadressid = value;
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
     *         &lt;element name="Elukoht" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Elukoht.Riikkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Maakondkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Maakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Linn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Asula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Maja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Korter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Sihtnumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Kuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "elukoht"
    })
    public static class Elukohad {

        @XmlElement(name = "Elukoht")
        protected List<RR80KMAisikuandmedResponseType.Elukohad.Elukoht> elukoht;

        /**
         * Gets the value of the elukoht property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the elukoht property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getElukoht().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR80KMAisikuandmedResponseType.Elukohad.Elukoht }
         * 
         * 
         */
        public List<RR80KMAisikuandmedResponseType.Elukohad.Elukoht> getElukoht() {
            if (elukoht == null) {
                elukoht = new ArrayList<RR80KMAisikuandmedResponseType.Elukohad.Elukoht>();
            }
            return this.elukoht;
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
         *         &lt;element name="Elukoht.Riikkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Maakondkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Maakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Linn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Asula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Maja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Korter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Sihtnumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Kuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "elukohtRiikkd",
            "elukohtMaakondkd",
            "elukohtMaakond",
            "elukohtLinn",
            "elukohtAsula",
            "elukohtTanav",
            "elukohtMaja",
            "elukohtKorter",
            "elukohtSihtnumber",
            "elukohtAlates",
            "elukohtKuni"
        })
        public static class Elukoht {

            @XmlElement(name = "Elukoht.Riikkd", required = true)
            protected String elukohtRiikkd;
            @XmlElement(name = "Elukoht.Maakondkd", required = true)
            protected String elukohtMaakondkd;
            @XmlElement(name = "Elukoht.Maakond", required = true)
            protected String elukohtMaakond;
            @XmlElement(name = "Elukoht.Linn", required = true)
            protected String elukohtLinn;
            @XmlElement(name = "Elukoht.Asula", required = true)
            protected String elukohtAsula;
            @XmlElement(name = "Elukoht.Tanav", required = true)
            protected String elukohtTanav;
            @XmlElement(name = "Elukoht.Maja", required = true)
            protected String elukohtMaja;
            @XmlElement(name = "Elukoht.Korter", required = true)
            protected String elukohtKorter;
            @XmlElement(name = "Elukoht.Sihtnumber", required = true)
            protected String elukohtSihtnumber;
            @XmlElement(name = "Elukoht.Alates", required = true)
            protected String elukohtAlates;
            @XmlElement(name = "Elukoht.Kuni", required = true)
            protected String elukohtKuni;

            /**
             * Gets the value of the elukohtRiikkd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtRiikkd() {
                return elukohtRiikkd;
            }

            /**
             * Sets the value of the elukohtRiikkd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtRiikkd(String value) {
                this.elukohtRiikkd = value;
            }

            /**
             * Gets the value of the elukohtMaakondkd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtMaakondkd() {
                return elukohtMaakondkd;
            }

            /**
             * Sets the value of the elukohtMaakondkd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtMaakondkd(String value) {
                this.elukohtMaakondkd = value;
            }

            /**
             * Gets the value of the elukohtMaakond property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtMaakond() {
                return elukohtMaakond;
            }

            /**
             * Sets the value of the elukohtMaakond property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtMaakond(String value) {
                this.elukohtMaakond = value;
            }

            /**
             * Gets the value of the elukohtLinn property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtLinn() {
                return elukohtLinn;
            }

            /**
             * Sets the value of the elukohtLinn property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtLinn(String value) {
                this.elukohtLinn = value;
            }

            /**
             * Gets the value of the elukohtAsula property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtAsula() {
                return elukohtAsula;
            }

            /**
             * Sets the value of the elukohtAsula property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtAsula(String value) {
                this.elukohtAsula = value;
            }

            /**
             * Gets the value of the elukohtTanav property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtTanav() {
                return elukohtTanav;
            }

            /**
             * Sets the value of the elukohtTanav property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtTanav(String value) {
                this.elukohtTanav = value;
            }

            /**
             * Gets the value of the elukohtMaja property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtMaja() {
                return elukohtMaja;
            }

            /**
             * Sets the value of the elukohtMaja property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtMaja(String value) {
                this.elukohtMaja = value;
            }

            /**
             * Gets the value of the elukohtKorter property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtKorter() {
                return elukohtKorter;
            }

            /**
             * Sets the value of the elukohtKorter property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtKorter(String value) {
                this.elukohtKorter = value;
            }

            /**
             * Gets the value of the elukohtSihtnumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtSihtnumber() {
                return elukohtSihtnumber;
            }

            /**
             * Sets the value of the elukohtSihtnumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtSihtnumber(String value) {
                this.elukohtSihtnumber = value;
            }

            /**
             * Gets the value of the elukohtAlates property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtAlates() {
                return elukohtAlates;
            }

            /**
             * Sets the value of the elukohtAlates property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtAlates(String value) {
                this.elukohtAlates = value;
            }

            /**
             * Gets the value of the elukohtKuni property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtKuni() {
                return elukohtKuni;
            }

            /**
             * Sets the value of the elukohtKuni property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtKuni(String value) {
                this.elukohtKuni = value;
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
     *         &lt;element name="Postiaadress" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Postiaadress.Riikkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Postiaadress.Maakondkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Postiaadress.Maakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Postiaadress.Linn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Postiaadress.Asula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Postiaadress.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Postiaadress.Maja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Postiaadress.Korter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Postiaadress.Sihtnumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Postiaadress.Alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Postiaadress.Kuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "postiaadress"
    })
    public static class Postiaadressid {

        @XmlElement(name = "Postiaadress")
        protected List<RR80KMAisikuandmedResponseType.Postiaadressid.Postiaadress> postiaadress;

        /**
         * Gets the value of the postiaadress property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the postiaadress property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPostiaadress().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR80KMAisikuandmedResponseType.Postiaadressid.Postiaadress }
         * 
         * 
         */
        public List<RR80KMAisikuandmedResponseType.Postiaadressid.Postiaadress> getPostiaadress() {
            if (postiaadress == null) {
                postiaadress = new ArrayList<RR80KMAisikuandmedResponseType.Postiaadressid.Postiaadress>();
            }
            return this.postiaadress;
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
         *         &lt;element name="Postiaadress.Riikkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Postiaadress.Maakondkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Postiaadress.Maakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Postiaadress.Linn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Postiaadress.Asula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Postiaadress.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Postiaadress.Maja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Postiaadress.Korter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Postiaadress.Sihtnumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Postiaadress.Alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Postiaadress.Kuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "postiaadressRiikkd",
            "postiaadressMaakondkd",
            "postiaadressMaakond",
            "postiaadressLinn",
            "postiaadressAsula",
            "postiaadressTanav",
            "postiaadressMaja",
            "postiaadressKorter",
            "postiaadressSihtnumber",
            "postiaadressAlates",
            "postiaadressKuni"
        })
        public static class Postiaadress {

            @XmlElement(name = "Postiaadress.Riikkd", required = true)
            protected String postiaadressRiikkd;
            @XmlElement(name = "Postiaadress.Maakondkd", required = true)
            protected String postiaadressMaakondkd;
            @XmlElement(name = "Postiaadress.Maakond", required = true)
            protected String postiaadressMaakond;
            @XmlElement(name = "Postiaadress.Linn", required = true)
            protected String postiaadressLinn;
            @XmlElement(name = "Postiaadress.Asula", required = true)
            protected String postiaadressAsula;
            @XmlElement(name = "Postiaadress.Tanav", required = true)
            protected String postiaadressTanav;
            @XmlElement(name = "Postiaadress.Maja", required = true)
            protected String postiaadressMaja;
            @XmlElement(name = "Postiaadress.Korter", required = true)
            protected String postiaadressKorter;
            @XmlElement(name = "Postiaadress.Sihtnumber", required = true)
            protected String postiaadressSihtnumber;
            @XmlElement(name = "Postiaadress.Alates", required = true)
            protected String postiaadressAlates;
            @XmlElement(name = "Postiaadress.Kuni", required = true)
            protected String postiaadressKuni;

            /**
             * Gets the value of the postiaadressRiikkd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPostiaadressRiikkd() {
                return postiaadressRiikkd;
            }

            /**
             * Sets the value of the postiaadressRiikkd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPostiaadressRiikkd(String value) {
                this.postiaadressRiikkd = value;
            }

            /**
             * Gets the value of the postiaadressMaakondkd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPostiaadressMaakondkd() {
                return postiaadressMaakondkd;
            }

            /**
             * Sets the value of the postiaadressMaakondkd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPostiaadressMaakondkd(String value) {
                this.postiaadressMaakondkd = value;
            }

            /**
             * Gets the value of the postiaadressMaakond property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPostiaadressMaakond() {
                return postiaadressMaakond;
            }

            /**
             * Sets the value of the postiaadressMaakond property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPostiaadressMaakond(String value) {
                this.postiaadressMaakond = value;
            }

            /**
             * Gets the value of the postiaadressLinn property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPostiaadressLinn() {
                return postiaadressLinn;
            }

            /**
             * Sets the value of the postiaadressLinn property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPostiaadressLinn(String value) {
                this.postiaadressLinn = value;
            }

            /**
             * Gets the value of the postiaadressAsula property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPostiaadressAsula() {
                return postiaadressAsula;
            }

            /**
             * Sets the value of the postiaadressAsula property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPostiaadressAsula(String value) {
                this.postiaadressAsula = value;
            }

            /**
             * Gets the value of the postiaadressTanav property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPostiaadressTanav() {
                return postiaadressTanav;
            }

            /**
             * Sets the value of the postiaadressTanav property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPostiaadressTanav(String value) {
                this.postiaadressTanav = value;
            }

            /**
             * Gets the value of the postiaadressMaja property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPostiaadressMaja() {
                return postiaadressMaja;
            }

            /**
             * Sets the value of the postiaadressMaja property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPostiaadressMaja(String value) {
                this.postiaadressMaja = value;
            }

            /**
             * Gets the value of the postiaadressKorter property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPostiaadressKorter() {
                return postiaadressKorter;
            }

            /**
             * Sets the value of the postiaadressKorter property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPostiaadressKorter(String value) {
                this.postiaadressKorter = value;
            }

            /**
             * Gets the value of the postiaadressSihtnumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPostiaadressSihtnumber() {
                return postiaadressSihtnumber;
            }

            /**
             * Sets the value of the postiaadressSihtnumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPostiaadressSihtnumber(String value) {
                this.postiaadressSihtnumber = value;
            }

            /**
             * Gets the value of the postiaadressAlates property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPostiaadressAlates() {
                return postiaadressAlates;
            }

            /**
             * Sets the value of the postiaadressAlates property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPostiaadressAlates(String value) {
                this.postiaadressAlates = value;
            }

            /**
             * Gets the value of the postiaadressKuni property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPostiaadressKuni() {
                return postiaadressKuni;
            }

            /**
             * Sets the value of the postiaadressKuni property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPostiaadressKuni(String value) {
                this.postiaadressKuni = value;
            }

        }

    }

}
