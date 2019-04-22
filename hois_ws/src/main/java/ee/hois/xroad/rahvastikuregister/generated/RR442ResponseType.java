
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR442ResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR442ResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Avaldaja"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="IsikuID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                   &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="IsikuAadressid" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="IsikuAadress" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="AkpKoodRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AkpLyhikeNimetusRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AkpKoodMaakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AkpLyhikeNimetusMaakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AkpKoodLinnVald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AkpLyhikeNimetusLinnVald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AkpKoodAsulaLinnaosa" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AkpLyhikeNimetusAsulaLinnaosa" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AkpKoodVaikekoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AkpLyhikeNimetusVaikekoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AkpKoodTanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AkpLyhikeNimetusTanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AkpKoodNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AkpLyhikeNimetusNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AkpKoodMaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AkpLyhikeNimetusMaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AkpKoodKorter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AkpLyhikeNimetusKorter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AadressTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AadressiLiik" type="{http://rr.x-road.eu/producer}kdliik"/&gt;
 *                             &lt;element name="AlatesKP" type="{http://rr.x-road.eu/producer}date"/&gt;
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
 *         &lt;element name="Kontaktid" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Kontakt" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Liik" type="{http://rr.x-road.eu/producer}kdliik"/&gt;
 *                             &lt;element name="Tekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AlatesKP" type="{http://rr.x-road.eu/producer}date"/&gt;
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
@XmlType(name = "RR442ResponseType", propOrder = {
    "avaldaja",
    "isikuAadressid",
    "kontaktid"
})
public class RR442ResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Avaldaja", required = true)
    protected RR442ResponseType.Avaldaja avaldaja;
    @XmlElement(name = "IsikuAadressid")
    protected RR442ResponseType.IsikuAadressid isikuAadressid;
    @XmlElement(name = "Kontaktid")
    protected RR442ResponseType.Kontaktid kontaktid;

    /**
     * Gets the value of the avaldaja property.
     * 
     * @return
     *     possible object is
     *     {@link RR442ResponseType.Avaldaja }
     *     
     */
    public RR442ResponseType.Avaldaja getAvaldaja() {
        return avaldaja;
    }

    /**
     * Sets the value of the avaldaja property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR442ResponseType.Avaldaja }
     *     
     */
    public void setAvaldaja(RR442ResponseType.Avaldaja value) {
        this.avaldaja = value;
    }

    /**
     * Gets the value of the isikuAadressid property.
     * 
     * @return
     *     possible object is
     *     {@link RR442ResponseType.IsikuAadressid }
     *     
     */
    public RR442ResponseType.IsikuAadressid getIsikuAadressid() {
        return isikuAadressid;
    }

    /**
     * Sets the value of the isikuAadressid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR442ResponseType.IsikuAadressid }
     *     
     */
    public void setIsikuAadressid(RR442ResponseType.IsikuAadressid value) {
        this.isikuAadressid = value;
    }

    /**
     * Gets the value of the kontaktid property.
     * 
     * @return
     *     possible object is
     *     {@link RR442ResponseType.Kontaktid }
     *     
     */
    public RR442ResponseType.Kontaktid getKontaktid() {
        return kontaktid;
    }

    /**
     * Sets the value of the kontaktid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR442ResponseType.Kontaktid }
     *     
     */
    public void setKontaktid(RR442ResponseType.Kontaktid value) {
        this.kontaktid = value;
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
     *         &lt;element name="IsikuID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
     *         &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "isikuID",
        "isikukood",
        "eesnimi",
        "perenimi"
    })
    public static class Avaldaja {

        @XmlElement(name = "IsikuID", required = true)
        protected BigInteger isikuID;
        @XmlElement(name = "Isikukood", required = true)
        protected String isikukood;
        @XmlElement(name = "Eesnimi", required = true)
        protected String eesnimi;
        @XmlElement(name = "Perenimi", required = true)
        protected String perenimi;

        /**
         * Gets the value of the isikuID property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getIsikuID() {
            return isikuID;
        }

        /**
         * Sets the value of the isikuID property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setIsikuID(BigInteger value) {
            this.isikuID = value;
        }

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
     *         &lt;element name="IsikuAadress" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="AkpKoodRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AkpLyhikeNimetusRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AkpKoodMaakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AkpLyhikeNimetusMaakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AkpKoodLinnVald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AkpLyhikeNimetusLinnVald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AkpKoodAsulaLinnaosa" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AkpLyhikeNimetusAsulaLinnaosa" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AkpKoodVaikekoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AkpLyhikeNimetusVaikekoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AkpKoodTanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AkpLyhikeNimetusTanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AkpKoodNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AkpLyhikeNimetusNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AkpKoodMaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AkpLyhikeNimetusMaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AkpKoodKorter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AkpLyhikeNimetusKorter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AadressTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AadressiLiik" type="{http://rr.x-road.eu/producer}kdliik"/&gt;
     *                   &lt;element name="AlatesKP" type="{http://rr.x-road.eu/producer}date"/&gt;
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
        "isikuAadress"
    })
    public static class IsikuAadressid {

        @XmlElement(name = "IsikuAadress")
        protected List<RR442ResponseType.IsikuAadressid.IsikuAadress> isikuAadress;

        /**
         * Gets the value of the isikuAadress property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the isikuAadress property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIsikuAadress().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR442ResponseType.IsikuAadressid.IsikuAadress }
         * 
         * 
         */
        public List<RR442ResponseType.IsikuAadressid.IsikuAadress> getIsikuAadress() {
            if (isikuAadress == null) {
                isikuAadress = new ArrayList<RR442ResponseType.IsikuAadressid.IsikuAadress>();
            }
            return this.isikuAadress;
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
         *         &lt;element name="AkpKoodRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AkpLyhikeNimetusRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AkpKoodMaakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AkpLyhikeNimetusMaakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AkpKoodLinnVald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AkpLyhikeNimetusLinnVald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AkpKoodAsulaLinnaosa" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AkpLyhikeNimetusAsulaLinnaosa" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AkpKoodVaikekoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AkpLyhikeNimetusVaikekoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AkpKoodTanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AkpLyhikeNimetusTanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AkpKoodNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AkpLyhikeNimetusNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AkpKoodMaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AkpLyhikeNimetusMaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AkpKoodKorter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AkpLyhikeNimetusKorter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AadressTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AadressiLiik" type="{http://rr.x-road.eu/producer}kdliik"/&gt;
         *         &lt;element name="AlatesKP" type="{http://rr.x-road.eu/producer}date"/&gt;
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
            "akpKoodRiik",
            "akpLyhikeNimetusRiik",
            "akpKoodMaakond",
            "akpLyhikeNimetusMaakond",
            "akpKoodLinnVald",
            "akpLyhikeNimetusLinnVald",
            "akpKoodAsulaLinnaosa",
            "akpLyhikeNimetusAsulaLinnaosa",
            "akpKoodVaikekoht",
            "akpLyhikeNimetusVaikekoht",
            "akpKoodTanav",
            "akpLyhikeNimetusTanav",
            "akpKoodNimi",
            "akpLyhikeNimetusNimi",
            "akpKoodMaja",
            "akpLyhikeNimetusMaja",
            "akpKoodKorter",
            "akpLyhikeNimetusKorter",
            "postiindeks",
            "aadressTekst",
            "aadressiLiik",
            "alatesKP"
        })
        public static class IsikuAadress {

            @XmlElement(name = "AkpKoodRiik", required = true)
            protected String akpKoodRiik;
            @XmlElement(name = "AkpLyhikeNimetusRiik", required = true)
            protected String akpLyhikeNimetusRiik;
            @XmlElement(name = "AkpKoodMaakond", required = true)
            protected String akpKoodMaakond;
            @XmlElement(name = "AkpLyhikeNimetusMaakond", required = true)
            protected String akpLyhikeNimetusMaakond;
            @XmlElement(name = "AkpKoodLinnVald", required = true)
            protected String akpKoodLinnVald;
            @XmlElement(name = "AkpLyhikeNimetusLinnVald", required = true)
            protected String akpLyhikeNimetusLinnVald;
            @XmlElement(name = "AkpKoodAsulaLinnaosa", required = true)
            protected String akpKoodAsulaLinnaosa;
            @XmlElement(name = "AkpLyhikeNimetusAsulaLinnaosa", required = true)
            protected String akpLyhikeNimetusAsulaLinnaosa;
            @XmlElement(name = "AkpKoodVaikekoht", required = true)
            protected String akpKoodVaikekoht;
            @XmlElement(name = "AkpLyhikeNimetusVaikekoht", required = true)
            protected String akpLyhikeNimetusVaikekoht;
            @XmlElement(name = "AkpKoodTanav", required = true)
            protected String akpKoodTanav;
            @XmlElement(name = "AkpLyhikeNimetusTanav", required = true)
            protected String akpLyhikeNimetusTanav;
            @XmlElement(name = "AkpKoodNimi", required = true)
            protected String akpKoodNimi;
            @XmlElement(name = "AkpLyhikeNimetusNimi", required = true)
            protected String akpLyhikeNimetusNimi;
            @XmlElement(name = "AkpKoodMaja", required = true)
            protected String akpKoodMaja;
            @XmlElement(name = "AkpLyhikeNimetusMaja", required = true)
            protected String akpLyhikeNimetusMaja;
            @XmlElement(name = "AkpKoodKorter", required = true)
            protected String akpKoodKorter;
            @XmlElement(name = "AkpLyhikeNimetusKorter", required = true)
            protected String akpLyhikeNimetusKorter;
            @XmlElement(name = "Postiindeks", required = true)
            protected String postiindeks;
            @XmlElement(name = "AadressTekst", required = true)
            protected String aadressTekst;
            @XmlElement(name = "AadressiLiik", required = true)
            protected Kdliik aadressiLiik;
            @XmlElement(name = "AlatesKP", required = true)
            protected String alatesKP;

            /**
             * Gets the value of the akpKoodRiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAkpKoodRiik() {
                return akpKoodRiik;
            }

            /**
             * Sets the value of the akpKoodRiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAkpKoodRiik(String value) {
                this.akpKoodRiik = value;
            }

            /**
             * Gets the value of the akpLyhikeNimetusRiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAkpLyhikeNimetusRiik() {
                return akpLyhikeNimetusRiik;
            }

            /**
             * Sets the value of the akpLyhikeNimetusRiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAkpLyhikeNimetusRiik(String value) {
                this.akpLyhikeNimetusRiik = value;
            }

            /**
             * Gets the value of the akpKoodMaakond property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAkpKoodMaakond() {
                return akpKoodMaakond;
            }

            /**
             * Sets the value of the akpKoodMaakond property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAkpKoodMaakond(String value) {
                this.akpKoodMaakond = value;
            }

            /**
             * Gets the value of the akpLyhikeNimetusMaakond property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAkpLyhikeNimetusMaakond() {
                return akpLyhikeNimetusMaakond;
            }

            /**
             * Sets the value of the akpLyhikeNimetusMaakond property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAkpLyhikeNimetusMaakond(String value) {
                this.akpLyhikeNimetusMaakond = value;
            }

            /**
             * Gets the value of the akpKoodLinnVald property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAkpKoodLinnVald() {
                return akpKoodLinnVald;
            }

            /**
             * Sets the value of the akpKoodLinnVald property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAkpKoodLinnVald(String value) {
                this.akpKoodLinnVald = value;
            }

            /**
             * Gets the value of the akpLyhikeNimetusLinnVald property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAkpLyhikeNimetusLinnVald() {
                return akpLyhikeNimetusLinnVald;
            }

            /**
             * Sets the value of the akpLyhikeNimetusLinnVald property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAkpLyhikeNimetusLinnVald(String value) {
                this.akpLyhikeNimetusLinnVald = value;
            }

            /**
             * Gets the value of the akpKoodAsulaLinnaosa property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAkpKoodAsulaLinnaosa() {
                return akpKoodAsulaLinnaosa;
            }

            /**
             * Sets the value of the akpKoodAsulaLinnaosa property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAkpKoodAsulaLinnaosa(String value) {
                this.akpKoodAsulaLinnaosa = value;
            }

            /**
             * Gets the value of the akpLyhikeNimetusAsulaLinnaosa property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAkpLyhikeNimetusAsulaLinnaosa() {
                return akpLyhikeNimetusAsulaLinnaosa;
            }

            /**
             * Sets the value of the akpLyhikeNimetusAsulaLinnaosa property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAkpLyhikeNimetusAsulaLinnaosa(String value) {
                this.akpLyhikeNimetusAsulaLinnaosa = value;
            }

            /**
             * Gets the value of the akpKoodVaikekoht property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAkpKoodVaikekoht() {
                return akpKoodVaikekoht;
            }

            /**
             * Sets the value of the akpKoodVaikekoht property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAkpKoodVaikekoht(String value) {
                this.akpKoodVaikekoht = value;
            }

            /**
             * Gets the value of the akpLyhikeNimetusVaikekoht property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAkpLyhikeNimetusVaikekoht() {
                return akpLyhikeNimetusVaikekoht;
            }

            /**
             * Sets the value of the akpLyhikeNimetusVaikekoht property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAkpLyhikeNimetusVaikekoht(String value) {
                this.akpLyhikeNimetusVaikekoht = value;
            }

            /**
             * Gets the value of the akpKoodTanav property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAkpKoodTanav() {
                return akpKoodTanav;
            }

            /**
             * Sets the value of the akpKoodTanav property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAkpKoodTanav(String value) {
                this.akpKoodTanav = value;
            }

            /**
             * Gets the value of the akpLyhikeNimetusTanav property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAkpLyhikeNimetusTanav() {
                return akpLyhikeNimetusTanav;
            }

            /**
             * Sets the value of the akpLyhikeNimetusTanav property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAkpLyhikeNimetusTanav(String value) {
                this.akpLyhikeNimetusTanav = value;
            }

            /**
             * Gets the value of the akpKoodNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAkpKoodNimi() {
                return akpKoodNimi;
            }

            /**
             * Sets the value of the akpKoodNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAkpKoodNimi(String value) {
                this.akpKoodNimi = value;
            }

            /**
             * Gets the value of the akpLyhikeNimetusNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAkpLyhikeNimetusNimi() {
                return akpLyhikeNimetusNimi;
            }

            /**
             * Sets the value of the akpLyhikeNimetusNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAkpLyhikeNimetusNimi(String value) {
                this.akpLyhikeNimetusNimi = value;
            }

            /**
             * Gets the value of the akpKoodMaja property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAkpKoodMaja() {
                return akpKoodMaja;
            }

            /**
             * Sets the value of the akpKoodMaja property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAkpKoodMaja(String value) {
                this.akpKoodMaja = value;
            }

            /**
             * Gets the value of the akpLyhikeNimetusMaja property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAkpLyhikeNimetusMaja() {
                return akpLyhikeNimetusMaja;
            }

            /**
             * Sets the value of the akpLyhikeNimetusMaja property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAkpLyhikeNimetusMaja(String value) {
                this.akpLyhikeNimetusMaja = value;
            }

            /**
             * Gets the value of the akpKoodKorter property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAkpKoodKorter() {
                return akpKoodKorter;
            }

            /**
             * Sets the value of the akpKoodKorter property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAkpKoodKorter(String value) {
                this.akpKoodKorter = value;
            }

            /**
             * Gets the value of the akpLyhikeNimetusKorter property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAkpLyhikeNimetusKorter() {
                return akpLyhikeNimetusKorter;
            }

            /**
             * Sets the value of the akpLyhikeNimetusKorter property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAkpLyhikeNimetusKorter(String value) {
                this.akpLyhikeNimetusKorter = value;
            }

            /**
             * Gets the value of the postiindeks property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPostiindeks() {
                return postiindeks;
            }

            /**
             * Sets the value of the postiindeks property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPostiindeks(String value) {
                this.postiindeks = value;
            }

            /**
             * Gets the value of the aadressTekst property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressTekst() {
                return aadressTekst;
            }

            /**
             * Sets the value of the aadressTekst property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressTekst(String value) {
                this.aadressTekst = value;
            }

            /**
             * Gets the value of the aadressiLiik property.
             * 
             * @return
             *     possible object is
             *     {@link Kdliik }
             *     
             */
            public Kdliik getAadressiLiik() {
                return aadressiLiik;
            }

            /**
             * Sets the value of the aadressiLiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link Kdliik }
             *     
             */
            public void setAadressiLiik(Kdliik value) {
                this.aadressiLiik = value;
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
     *         &lt;element name="Kontakt" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Liik" type="{http://rr.x-road.eu/producer}kdliik"/&gt;
     *                   &lt;element name="Tekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AlatesKP" type="{http://rr.x-road.eu/producer}date"/&gt;
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
    public static class Kontaktid {

        @XmlElement(name = "Kontakt")
        protected List<RR442ResponseType.Kontaktid.Kontakt> kontakt;

        /**
         * Gets the value of the kontakt property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the kontakt property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getKontakt().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR442ResponseType.Kontaktid.Kontakt }
         * 
         * 
         */
        public List<RR442ResponseType.Kontaktid.Kontakt> getKontakt() {
            if (kontakt == null) {
                kontakt = new ArrayList<RR442ResponseType.Kontaktid.Kontakt>();
            }
            return this.kontakt;
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
         *         &lt;element name="Liik" type="{http://rr.x-road.eu/producer}kdliik"/&gt;
         *         &lt;element name="Tekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AlatesKP" type="{http://rr.x-road.eu/producer}date"/&gt;
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
            "tekst",
            "alatesKP"
        })
        public static class Kontakt {

            @XmlElement(name = "Liik", required = true)
            protected Kdliik liik;
            @XmlElement(name = "Tekst", required = true)
            protected String tekst;
            @XmlElement(name = "AlatesKP", required = true)
            protected String alatesKP;

            /**
             * Gets the value of the liik property.
             * 
             * @return
             *     possible object is
             *     {@link Kdliik }
             *     
             */
            public Kdliik getLiik() {
                return liik;
            }

            /**
             * Sets the value of the liik property.
             * 
             * @param value
             *     allowed object is
             *     {@link Kdliik }
             *     
             */
            public void setLiik(Kdliik value) {
                this.liik = value;
            }

            /**
             * Gets the value of the tekst property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTekst() {
                return tekst;
            }

            /**
             * Sets the value of the tekst property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTekst(String value) {
                this.tekst = value;
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

        }

    }

}
