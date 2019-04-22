
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR425KMOTaotlejaAndmeteParingResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR425KMOTaotlejaAndmeteParingResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="YksikIsik" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Isik"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="IsikuID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                             &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                             &lt;element name="Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Seisund" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="MuudEesnimed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="MuudPerenimed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="SynnijargneNimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Isanimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Kodakondsus" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Pereseis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Elukoht" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                       &lt;element name="Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                       &lt;element name="KehtibAlatesKp" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Sideandmed" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                       &lt;element name="Telefon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
 *                   &lt;element name="Dokumendid" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Dokument" maxOccurs="unbounded"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                       &lt;element name="Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="Seeria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                       &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="ValjaantudKp" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                                       &lt;element name="KehtivAlatesKp" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                                       &lt;element name="KehtivKuniKp" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                                       &lt;element name="Asutus" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="RiigiNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="AsutusTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                       &lt;element name="Olek"&gt;
 *                                         &lt;simpleType&gt;
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                                             &lt;enumeration value="Kehtiv"/&gt;
 *                                             &lt;enumeration value="Kehtetu"/&gt;
 *                                             &lt;enumeration value="Tyhistatud"/&gt;
 *                                             &lt;enumeration value="Tyhine"/&gt;
 *                                             &lt;enumeration value="AbieluLoppenud"/&gt;
 *                                           &lt;/restriction&gt;
 *                                         &lt;/simpleType&gt;
 *                                       &lt;/element&gt;
 *                                       &lt;element name="Isikud" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="Isik" maxOccurs="unbounded"&gt;
 *                                                   &lt;complexType&gt;
 *                                                     &lt;complexContent&gt;
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                                         &lt;sequence&gt;
 *                                                           &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                           &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                           &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                           &lt;element name="VanaEesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                           &lt;element name="VanaPerenimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                           &lt;element name="Roll" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                           &lt;element name="Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
 *                   &lt;element name="Suhted" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Suhe" maxOccurs="unbounded"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                       &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                       &lt;element name="RollSuhtes" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="SuhteOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="Alates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                                       &lt;element name="Kuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
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
 *         &lt;element name="Isikud" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Isik" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="IsikuID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                             &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                             &lt;element name="Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Seisund" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isaeesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
@XmlType(name = "RR425KMOTaotlejaAndmeteParingResponseType", propOrder = {
    "yksikIsik",
    "isikud"
})
public class RR425KMOTaotlejaAndmeteParingResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "YksikIsik")
    protected RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik yksikIsik;
    @XmlElement(name = "Isikud")
    protected RR425KMOTaotlejaAndmeteParingResponseType.Isikud isikud;

    /**
     * Gets the value of the yksikIsik property.
     * 
     * @return
     *     possible object is
     *     {@link RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik }
     *     
     */
    public RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik getYksikIsik() {
        return yksikIsik;
    }

    /**
     * Sets the value of the yksikIsik property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik }
     *     
     */
    public void setYksikIsik(RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik value) {
        this.yksikIsik = value;
    }

    /**
     * Gets the value of the isikud property.
     * 
     * @return
     *     possible object is
     *     {@link RR425KMOTaotlejaAndmeteParingResponseType.Isikud }
     *     
     */
    public RR425KMOTaotlejaAndmeteParingResponseType.Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR425KMOTaotlejaAndmeteParingResponseType.Isikud }
     *     
     */
    public void setIsikud(RR425KMOTaotlejaAndmeteParingResponseType.Isikud value) {
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
     *         &lt;element name="Isik" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="IsikuID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
     *                   &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                   &lt;element name="Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Seisund" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isaeesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
        protected List<RR425KMOTaotlejaAndmeteParingResponseType.Isikud.Isik> isik;

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
         * {@link RR425KMOTaotlejaAndmeteParingResponseType.Isikud.Isik }
         * 
         * 
         */
        public List<RR425KMOTaotlejaAndmeteParingResponseType.Isikud.Isik> getIsik() {
            if (isik == null) {
                isik = new ArrayList<RR425KMOTaotlejaAndmeteParingResponseType.Isikud.Isik>();
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
         *         &lt;element name="IsikuID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
         *         &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *         &lt;element name="Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Seisund" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isaeesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
            "perenimi",
            "synniaeg",
            "sugu",
            "seisund",
            "isaeesnimi"
        })
        public static class Isik {

            @XmlElement(name = "IsikuID", required = true)
            protected BigInteger isikuID;
            @XmlElement(name = "Isikukood", required = true)
            protected String isikukood;
            @XmlElement(name = "Eesnimi", required = true)
            protected String eesnimi;
            @XmlElement(name = "Perenimi", required = true)
            protected String perenimi;
            @XmlElement(name = "Synniaeg")
            protected String synniaeg;
            @XmlElement(name = "Sugu", required = true)
            protected String sugu;
            @XmlElement(name = "Seisund", required = true)
            protected String seisund;
            @XmlElement(name = "Isaeesnimi")
            protected String isaeesnimi;

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

            /**
             * Gets the value of the seisund property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeisund() {
                return seisund;
            }

            /**
             * Sets the value of the seisund property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeisund(String value) {
                this.seisund = value;
            }

            /**
             * Gets the value of the isaeesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsaeesnimi() {
                return isaeesnimi;
            }

            /**
             * Sets the value of the isaeesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsaeesnimi(String value) {
                this.isaeesnimi = value;
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
     *         &lt;element name="Isik"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="IsikuID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
     *                   &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
     *                   &lt;element name="Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Seisund" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="MuudEesnimed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="MuudPerenimed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="SynnijargneNimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Isanimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Kodakondsus" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Pereseis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Elukoht" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                             &lt;element name="Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                             &lt;element name="KehtibAlatesKp" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Sideandmed" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                             &lt;element name="Telefon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
     *         &lt;element name="Dokumendid" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Dokument" maxOccurs="unbounded"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                             &lt;element name="Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Seeria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                             &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="ValjaantudKp" type="{http://rr.x-road.eu/producer}date"/&gt;
     *                             &lt;element name="KehtivAlatesKp" type="{http://rr.x-road.eu/producer}date"/&gt;
     *                             &lt;element name="KehtivKuniKp" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                             &lt;element name="Asutus" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="RiigiNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="AsutusTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                             &lt;element name="Olek"&gt;
     *                               &lt;simpleType&gt;
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *                                   &lt;enumeration value="Kehtiv"/&gt;
     *                                   &lt;enumeration value="Kehtetu"/&gt;
     *                                   &lt;enumeration value="Tyhistatud"/&gt;
     *                                   &lt;enumeration value="Tyhine"/&gt;
     *                                   &lt;enumeration value="AbieluLoppenud"/&gt;
     *                                 &lt;/restriction&gt;
     *                               &lt;/simpleType&gt;
     *                             &lt;/element&gt;
     *                             &lt;element name="Isikud" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="Isik" maxOccurs="unbounded"&gt;
     *                                         &lt;complexType&gt;
     *                                           &lt;complexContent&gt;
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                               &lt;sequence&gt;
     *                                                 &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                                 &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                                 &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                                 &lt;element name="VanaEesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                                 &lt;element name="VanaPerenimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                                 &lt;element name="Roll" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                                 &lt;element name="Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
     *         &lt;element name="Suhted" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Suhe" maxOccurs="unbounded"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                             &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                             &lt;element name="RollSuhtes" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="SuhteOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Alates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                             &lt;element name="Kuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
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
        "isik",
        "dokumendid",
        "suhted"
    })
    public static class YksikIsik {

        @XmlElement(name = "Isik", required = true)
        protected RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Isik isik;
        @XmlElement(name = "Dokumendid")
        protected RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Dokumendid dokumendid;
        @XmlElement(name = "Suhted")
        protected RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Suhted suhted;

        /**
         * Gets the value of the isik property.
         * 
         * @return
         *     possible object is
         *     {@link RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Isik }
         *     
         */
        public RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Isik getIsik() {
            return isik;
        }

        /**
         * Sets the value of the isik property.
         * 
         * @param value
         *     allowed object is
         *     {@link RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Isik }
         *     
         */
        public void setIsik(RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Isik value) {
            this.isik = value;
        }

        /**
         * Gets the value of the dokumendid property.
         * 
         * @return
         *     possible object is
         *     {@link RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Dokumendid }
         *     
         */
        public RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Dokumendid getDokumendid() {
            return dokumendid;
        }

        /**
         * Sets the value of the dokumendid property.
         * 
         * @param value
         *     allowed object is
         *     {@link RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Dokumendid }
         *     
         */
        public void setDokumendid(RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Dokumendid value) {
            this.dokumendid = value;
        }

        /**
         * Gets the value of the suhted property.
         * 
         * @return
         *     possible object is
         *     {@link RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Suhted }
         *     
         */
        public RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Suhted getSuhted() {
            return suhted;
        }

        /**
         * Sets the value of the suhted property.
         * 
         * @param value
         *     allowed object is
         *     {@link RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Suhted }
         *     
         */
        public void setSuhted(RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Suhted value) {
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
         *         &lt;element name="Dokument" maxOccurs="unbounded"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                   &lt;element name="Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Seeria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                   &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="ValjaantudKp" type="{http://rr.x-road.eu/producer}date"/&gt;
         *                   &lt;element name="KehtivAlatesKp" type="{http://rr.x-road.eu/producer}date"/&gt;
         *                   &lt;element name="KehtivKuniKp" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *                   &lt;element name="Asutus" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="RiigiNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="AsutusTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                           &lt;/sequence&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                   &lt;element name="Olek"&gt;
         *                     &lt;simpleType&gt;
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
         *                         &lt;enumeration value="Kehtiv"/&gt;
         *                         &lt;enumeration value="Kehtetu"/&gt;
         *                         &lt;enumeration value="Tyhistatud"/&gt;
         *                         &lt;enumeration value="Tyhine"/&gt;
         *                         &lt;enumeration value="AbieluLoppenud"/&gt;
         *                       &lt;/restriction&gt;
         *                     &lt;/simpleType&gt;
         *                   &lt;/element&gt;
         *                   &lt;element name="Isikud" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="Isik" maxOccurs="unbounded"&gt;
         *                               &lt;complexType&gt;
         *                                 &lt;complexContent&gt;
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                                     &lt;sequence&gt;
         *                                       &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                                       &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                                       &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                                       &lt;element name="VanaEesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                                       &lt;element name="VanaPerenimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                                       &lt;element name="Roll" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                                       &lt;element name="Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
            "dokument"
        })
        public static class Dokumendid {

            @XmlElement(name = "Dokument", required = true)
            protected List<RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Dokumendid.Dokument> dokument;

            /**
             * Gets the value of the dokument property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the dokument property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getDokument().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Dokumendid.Dokument }
             * 
             * 
             */
            public List<RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Dokumendid.Dokument> getDokument() {
                if (dokument == null) {
                    dokument = new ArrayList<RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Dokumendid.Dokument>();
                }
                return this.dokument;
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
             *         &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *         &lt;element name="Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Seeria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *         &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="ValjaantudKp" type="{http://rr.x-road.eu/producer}date"/&gt;
             *         &lt;element name="KehtivAlatesKp" type="{http://rr.x-road.eu/producer}date"/&gt;
             *         &lt;element name="KehtivKuniKp" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
             *         &lt;element name="Asutus" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="RiigiNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="AsutusTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                 &lt;/sequence&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *         &lt;element name="Olek"&gt;
             *           &lt;simpleType&gt;
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
             *               &lt;enumeration value="Kehtiv"/&gt;
             *               &lt;enumeration value="Kehtetu"/&gt;
             *               &lt;enumeration value="Tyhistatud"/&gt;
             *               &lt;enumeration value="Tyhine"/&gt;
             *               &lt;enumeration value="AbieluLoppenud"/&gt;
             *             &lt;/restriction&gt;
             *           &lt;/simpleType&gt;
             *         &lt;/element&gt;
             *         &lt;element name="Isikud" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="Isik" maxOccurs="unbounded"&gt;
             *                     &lt;complexType&gt;
             *                       &lt;complexContent&gt;
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                           &lt;sequence&gt;
             *                             &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                             &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                             &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                             &lt;element name="VanaEesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                             &lt;element name="VanaPerenimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                             &lt;element name="Roll" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                             &lt;element name="Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
                "nimetus",
                "liik",
                "seeria",
                "number",
                "valjaantudKp",
                "kehtivAlatesKp",
                "kehtivKuniKp",
                "asutus",
                "olek",
                "isikud"
            })
            public static class Dokument {

                @XmlElement(name = "Nimetus")
                protected String nimetus;
                @XmlElement(name = "Liik", required = true)
                protected String liik;
                @XmlElement(name = "Seeria")
                protected String seeria;
                @XmlElement(name = "Number", required = true)
                protected String number;
                @XmlElement(name = "ValjaantudKp", required = true)
                protected String valjaantudKp;
                @XmlElement(name = "KehtivAlatesKp", required = true)
                protected String kehtivAlatesKp;
                @XmlElement(name = "KehtivKuniKp")
                protected String kehtivKuniKp;
                @XmlElement(name = "Asutus")
                protected RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Dokumendid.Dokument.Asutus asutus;
                @XmlElement(name = "Olek", required = true)
                protected String olek;
                @XmlElement(name = "Isikud")
                protected RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Dokumendid.Dokument.Isikud isikud;

                /**
                 * Gets the value of the nimetus property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getNimetus() {
                    return nimetus;
                }

                /**
                 * Sets the value of the nimetus property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setNimetus(String value) {
                    this.nimetus = value;
                }

                /**
                 * Gets the value of the liik property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getLiik() {
                    return liik;
                }

                /**
                 * Sets the value of the liik property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setLiik(String value) {
                    this.liik = value;
                }

                /**
                 * Gets the value of the seeria property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSeeria() {
                    return seeria;
                }

                /**
                 * Sets the value of the seeria property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSeeria(String value) {
                    this.seeria = value;
                }

                /**
                 * Gets the value of the number property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getNumber() {
                    return number;
                }

                /**
                 * Sets the value of the number property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setNumber(String value) {
                    this.number = value;
                }

                /**
                 * Gets the value of the valjaantudKp property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getValjaantudKp() {
                    return valjaantudKp;
                }

                /**
                 * Sets the value of the valjaantudKp property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setValjaantudKp(String value) {
                    this.valjaantudKp = value;
                }

                /**
                 * Gets the value of the kehtivAlatesKp property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKehtivAlatesKp() {
                    return kehtivAlatesKp;
                }

                /**
                 * Sets the value of the kehtivAlatesKp property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKehtivAlatesKp(String value) {
                    this.kehtivAlatesKp = value;
                }

                /**
                 * Gets the value of the kehtivKuniKp property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKehtivKuniKp() {
                    return kehtivKuniKp;
                }

                /**
                 * Sets the value of the kehtivKuniKp property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKehtivKuniKp(String value) {
                    this.kehtivKuniKp = value;
                }

                /**
                 * Gets the value of the asutus property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Dokumendid.Dokument.Asutus }
                 *     
                 */
                public RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Dokumendid.Dokument.Asutus getAsutus() {
                    return asutus;
                }

                /**
                 * Sets the value of the asutus property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Dokumendid.Dokument.Asutus }
                 *     
                 */
                public void setAsutus(RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Dokumendid.Dokument.Asutus value) {
                    this.asutus = value;
                }

                /**
                 * Gets the value of the olek property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getOlek() {
                    return olek;
                }

                /**
                 * Sets the value of the olek property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setOlek(String value) {
                    this.olek = value;
                }

                /**
                 * Gets the value of the isikud property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Dokumendid.Dokument.Isikud }
                 *     
                 */
                public RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Dokumendid.Dokument.Isikud getIsikud() {
                    return isikud;
                }

                /**
                 * Sets the value of the isikud property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Dokumendid.Dokument.Isikud }
                 *     
                 */
                public void setIsikud(RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Dokumendid.Dokument.Isikud value) {
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
                 *         &lt;element name="RiigiNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="AsutusTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                    "riigiNimetus",
                    "asutusTekst"
                })
                public static class Asutus {

                    @XmlElement(name = "RiigiNimetus", required = true)
                    protected String riigiNimetus;
                    @XmlElement(name = "AsutusTekst", required = true)
                    protected String asutusTekst;

                    /**
                     * Gets the value of the riigiNimetus property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getRiigiNimetus() {
                        return riigiNimetus;
                    }

                    /**
                     * Sets the value of the riigiNimetus property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setRiigiNimetus(String value) {
                        this.riigiNimetus = value;
                    }

                    /**
                     * Gets the value of the asutusTekst property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getAsutusTekst() {
                        return asutusTekst;
                    }

                    /**
                     * Sets the value of the asutusTekst property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setAsutusTekst(String value) {
                        this.asutusTekst = value;
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
                 *                   &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *                   &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *                   &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *                   &lt;element name="VanaEesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *                   &lt;element name="VanaPerenimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *                   &lt;element name="Roll" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *                   &lt;element name="Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
                    protected List<RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Dokumendid.Dokument.Isikud.Isik> isik;

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
                     * {@link RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Dokumendid.Dokument.Isikud.Isik }
                     * 
                     * 
                     */
                    public List<RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Dokumendid.Dokument.Isikud.Isik> getIsik() {
                        if (isik == null) {
                            isik = new ArrayList<RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Dokumendid.Dokument.Isikud.Isik>();
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
                     *         &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                     *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                     *         &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                     *         &lt;element name="VanaEesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                     *         &lt;element name="VanaPerenimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                     *         &lt;element name="Roll" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                     *         &lt;element name="Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
                        "vanaEesnimi",
                        "vanaPerenimi",
                        "roll",
                        "kodakondsus"
                    })
                    public static class Isik {

                        @XmlElement(name = "Isikukood")
                        protected String isikukood;
                        @XmlElement(name = "Eesnimi", required = true)
                        protected String eesnimi;
                        @XmlElement(name = "Perenimi", required = true)
                        protected String perenimi;
                        @XmlElement(name = "VanaEesnimi")
                        protected String vanaEesnimi;
                        @XmlElement(name = "VanaPerenimi")
                        protected String vanaPerenimi;
                        @XmlElement(name = "Roll", required = true)
                        protected String roll;
                        @XmlElement(name = "Kodakondsus")
                        protected String kodakondsus;

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
                         * Gets the value of the vanaEesnimi property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getVanaEesnimi() {
                            return vanaEesnimi;
                        }

                        /**
                         * Sets the value of the vanaEesnimi property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setVanaEesnimi(String value) {
                            this.vanaEesnimi = value;
                        }

                        /**
                         * Gets the value of the vanaPerenimi property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getVanaPerenimi() {
                            return vanaPerenimi;
                        }

                        /**
                         * Sets the value of the vanaPerenimi property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setVanaPerenimi(String value) {
                            this.vanaPerenimi = value;
                        }

                        /**
                         * Gets the value of the roll property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getRoll() {
                            return roll;
                        }

                        /**
                         * Sets the value of the roll property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setRoll(String value) {
                            this.roll = value;
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
         *         &lt;element name="IsikuID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
         *         &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
         *         &lt;element name="Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Seisund" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="MuudEesnimed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="MuudPerenimed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="SynnijargneNimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Isanimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Kodakondsus" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="Pereseis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Elukoht" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                   &lt;element name="Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                   &lt;element name="KehtibAlatesKp" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="Sideandmed" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                   &lt;element name="Telefon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
            "isikuID",
            "isikukood",
            "eesnimi",
            "perenimi",
            "synniaeg",
            "sugu",
            "seisund",
            "muudEesnimed",
            "muudPerenimed",
            "synnijargneNimi",
            "isanimi",
            "kodakondsus",
            "pereseis",
            "synnikoht",
            "elukoht",
            "sideandmed"
        })
        public static class Isik {

            @XmlElement(name = "IsikuID", required = true)
            protected BigInteger isikuID;
            @XmlElement(name = "Isikukood")
            protected String isikukood;
            @XmlElement(name = "Eesnimi", required = true)
            protected String eesnimi;
            @XmlElement(name = "Perenimi", required = true)
            protected String perenimi;
            @XmlElement(name = "Synniaeg", required = true)
            protected String synniaeg;
            @XmlElement(name = "Sugu", required = true)
            protected String sugu;
            @XmlElement(name = "Seisund", required = true)
            protected String seisund;
            @XmlElement(name = "MuudEesnimed")
            protected String muudEesnimed;
            @XmlElement(name = "MuudPerenimed")
            protected String muudPerenimed;
            @XmlElement(name = "SynnijargneNimi")
            protected String synnijargneNimi;
            @XmlElement(name = "Isanimi")
            protected String isanimi;
            @XmlElement(name = "Kodakondsus")
            protected RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Isik.Kodakondsus kodakondsus;
            @XmlElement(name = "Pereseis")
            protected String pereseis;
            @XmlElement(name = "Synnikoht")
            protected String synnikoht;
            @XmlElement(name = "Elukoht")
            protected RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Isik.Elukoht elukoht;
            @XmlElement(name = "Sideandmed")
            protected RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Isik.Sideandmed sideandmed;

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

            /**
             * Gets the value of the seisund property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeisund() {
                return seisund;
            }

            /**
             * Sets the value of the seisund property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeisund(String value) {
                this.seisund = value;
            }

            /**
             * Gets the value of the muudEesnimed property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMuudEesnimed() {
                return muudEesnimed;
            }

            /**
             * Sets the value of the muudEesnimed property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMuudEesnimed(String value) {
                this.muudEesnimed = value;
            }

            /**
             * Gets the value of the muudPerenimed property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMuudPerenimed() {
                return muudPerenimed;
            }

            /**
             * Sets the value of the muudPerenimed property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMuudPerenimed(String value) {
                this.muudPerenimed = value;
            }

            /**
             * Gets the value of the synnijargneNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSynnijargneNimi() {
                return synnijargneNimi;
            }

            /**
             * Sets the value of the synnijargneNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSynnijargneNimi(String value) {
                this.synnijargneNimi = value;
            }

            /**
             * Gets the value of the isanimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsanimi() {
                return isanimi;
            }

            /**
             * Sets the value of the isanimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsanimi(String value) {
                this.isanimi = value;
            }

            /**
             * Gets the value of the kodakondsus property.
             * 
             * @return
             *     possible object is
             *     {@link RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Isik.Kodakondsus }
             *     
             */
            public RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Isik.Kodakondsus getKodakondsus() {
                return kodakondsus;
            }

            /**
             * Sets the value of the kodakondsus property.
             * 
             * @param value
             *     allowed object is
             *     {@link RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Isik.Kodakondsus }
             *     
             */
            public void setKodakondsus(RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Isik.Kodakondsus value) {
                this.kodakondsus = value;
            }

            /**
             * Gets the value of the pereseis property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPereseis() {
                return pereseis;
            }

            /**
             * Sets the value of the pereseis property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPereseis(String value) {
                this.pereseis = value;
            }

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
             * Gets the value of the elukoht property.
             * 
             * @return
             *     possible object is
             *     {@link RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Isik.Elukoht }
             *     
             */
            public RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Isik.Elukoht getElukoht() {
                return elukoht;
            }

            /**
             * Sets the value of the elukoht property.
             * 
             * @param value
             *     allowed object is
             *     {@link RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Isik.Elukoht }
             *     
             */
            public void setElukoht(RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Isik.Elukoht value) {
                this.elukoht = value;
            }

            /**
             * Gets the value of the sideandmed property.
             * 
             * @return
             *     possible object is
             *     {@link RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Isik.Sideandmed }
             *     
             */
            public RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Isik.Sideandmed getSideandmed() {
                return sideandmed;
            }

            /**
             * Sets the value of the sideandmed property.
             * 
             * @param value
             *     allowed object is
             *     {@link RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Isik.Sideandmed }
             *     
             */
            public void setSideandmed(RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Isik.Sideandmed value) {
                this.sideandmed = value;
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
             *         &lt;element name="AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *         &lt;element name="Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *         &lt;element name="KehtibAlatesKp" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
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
                "aadressTekstina",
                "postiindeks",
                "kehtibAlatesKp"
            })
            public static class Elukoht {

                @XmlElement(name = "AadressTekstina")
                protected String aadressTekstina;
                @XmlElement(name = "Postiindeks")
                protected String postiindeks;
                @XmlElement(name = "KehtibAlatesKp")
                protected String kehtibAlatesKp;

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
                 * Gets the value of the kehtibAlatesKp property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKehtibAlatesKp() {
                    return kehtibAlatesKp;
                }

                /**
                 * Sets the value of the kehtibAlatesKp property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKehtibAlatesKp(String value) {
                    this.kehtibAlatesKp = value;
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
             *         &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                "kood",
                "nimetus"
            })
            public static class Kodakondsus {

                @XmlElement(name = "Kood", required = true)
                protected String kood;
                @XmlElement(name = "Nimetus", required = true)
                protected String nimetus;

                /**
                 * Gets the value of the kood property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKood() {
                    return kood;
                }

                /**
                 * Sets the value of the kood property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKood(String value) {
                    this.kood = value;
                }

                /**
                 * Gets the value of the nimetus property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getNimetus() {
                    return nimetus;
                }

                /**
                 * Sets the value of the nimetus property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setNimetus(String value) {
                    this.nimetus = value;
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
             *         &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *         &lt;element name="Telefon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
                "email",
                "telefon"
            })
            public static class Sideandmed {

                @XmlElement(name = "Email")
                protected String email;
                @XmlElement(name = "Telefon")
                protected String telefon;

                /**
                 * Gets the value of the email property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getEmail() {
                    return email;
                }

                /**
                 * Sets the value of the email property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setEmail(String value) {
                    this.email = value;
                }

                /**
                 * Gets the value of the telefon property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTelefon() {
                    return telefon;
                }

                /**
                 * Sets the value of the telefon property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTelefon(String value) {
                    this.telefon = value;
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
         *         &lt;element name="Suhe" maxOccurs="unbounded"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                   &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                   &lt;element name="RollSuhtes" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="SuhteOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Alates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *                   &lt;element name="Kuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
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

            @XmlElement(name = "Suhe", required = true)
            protected List<RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Suhted.Suhe> suhe;

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
             * {@link RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Suhted.Suhe }
             * 
             * 
             */
            public List<RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Suhted.Suhe> getSuhe() {
                if (suhe == null) {
                    suhe = new ArrayList<RR425KMOTaotlejaAndmeteParingResponseType.YksikIsik.Suhted.Suhe>();
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
             *         &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *         &lt;element name="RollSuhtes" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="SuhteOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Alates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
             *         &lt;element name="Kuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
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
                "kodakondsus",
                "rollSuhtes",
                "suhteOlek",
                "alates",
                "kuni"
            })
            public static class Suhe {

                @XmlElement(name = "Isikukood")
                protected String isikukood;
                @XmlElement(name = "Eesnimi", required = true)
                protected String eesnimi;
                @XmlElement(name = "Perenimi", required = true)
                protected String perenimi;
                @XmlElement(name = "Kodakondsus")
                protected String kodakondsus;
                @XmlElement(name = "RollSuhtes", required = true)
                protected String rollSuhtes;
                @XmlElement(name = "SuhteOlek", required = true)
                protected String suhteOlek;
                @XmlElement(name = "Alates")
                protected String alates;
                @XmlElement(name = "Kuni")
                protected String kuni;

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
                 * Gets the value of the rollSuhtes property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getRollSuhtes() {
                    return rollSuhtes;
                }

                /**
                 * Sets the value of the rollSuhtes property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setRollSuhtes(String value) {
                    this.rollSuhtes = value;
                }

                /**
                 * Gets the value of the suhteOlek property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSuhteOlek() {
                    return suhteOlek;
                }

                /**
                 * Sets the value of the suhteOlek property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSuhteOlek(String value) {
                    this.suhteOlek = value;
                }

                /**
                 * Gets the value of the alates property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAlates() {
                    return alates;
                }

                /**
                 * Sets the value of the alates property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAlates(String value) {
                    this.alates = value;
                }

                /**
                 * Gets the value of the kuni property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKuni() {
                    return kuni;
                }

                /**
                 * Sets the value of the kuni property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKuni(String value) {
                    this.kuni = value;
                }

            }

        }

    }

}
