
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR427KMOTaotlejagaSeotudIsikudResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR427KMOTaotlejagaSeotudIsikudResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SeotudIsikud" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="SeotudIsik" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="IsikuID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                             &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode" minOccurs="0"/&gt;
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
 *                             &lt;element name="Rahvus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Pereseis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Elukoht" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *                             &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode" minOccurs="0"/&gt;
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
@XmlType(name = "RR427KMOTaotlejagaSeotudIsikudResponseType", propOrder = {
    "seotudIsikud",
    "isikud"
})
public class RR427KMOTaotlejagaSeotudIsikudResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "SeotudIsikud")
    protected RR427KMOTaotlejagaSeotudIsikudResponseType.SeotudIsikud seotudIsikud;
    @XmlElement(name = "Isikud")
    protected RR427KMOTaotlejagaSeotudIsikudResponseType.Isikud isikud;

    /**
     * Gets the value of the seotudIsikud property.
     * 
     * @return
     *     possible object is
     *     {@link RR427KMOTaotlejagaSeotudIsikudResponseType.SeotudIsikud }
     *     
     */
    public RR427KMOTaotlejagaSeotudIsikudResponseType.SeotudIsikud getSeotudIsikud() {
        return seotudIsikud;
    }

    /**
     * Sets the value of the seotudIsikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR427KMOTaotlejagaSeotudIsikudResponseType.SeotudIsikud }
     *     
     */
    public void setSeotudIsikud(RR427KMOTaotlejagaSeotudIsikudResponseType.SeotudIsikud value) {
        this.seotudIsikud = value;
    }

    /**
     * Gets the value of the isikud property.
     * 
     * @return
     *     possible object is
     *     {@link RR427KMOTaotlejagaSeotudIsikudResponseType.Isikud }
     *     
     */
    public RR427KMOTaotlejagaSeotudIsikudResponseType.Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR427KMOTaotlejagaSeotudIsikudResponseType.Isikud }
     *     
     */
    public void setIsikud(RR427KMOTaotlejagaSeotudIsikudResponseType.Isikud value) {
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
     *                   &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode" minOccurs="0"/&gt;
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
        protected List<RR427KMOTaotlejagaSeotudIsikudResponseType.Isikud.Isik> isik;

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
         * {@link RR427KMOTaotlejagaSeotudIsikudResponseType.Isikud.Isik }
         * 
         * 
         */
        public List<RR427KMOTaotlejagaSeotudIsikudResponseType.Isikud.Isik> getIsik() {
            if (isik == null) {
                isik = new ArrayList<RR427KMOTaotlejagaSeotudIsikudResponseType.Isikud.Isik>();
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
         *         &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode" minOccurs="0"/&gt;
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
            @XmlElement(name = "Isikukood")
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
     *         &lt;element name="SeotudIsik" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="IsikuID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
     *                   &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode" minOccurs="0"/&gt;
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
     *                   &lt;element name="Rahvus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Pereseis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Elukoht" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "seotudIsik"
    })
    public static class SeotudIsikud {

        @XmlElement(name = "SeotudIsik", required = true)
        protected List<RR427KMOTaotlejagaSeotudIsikudResponseType.SeotudIsikud.SeotudIsik> seotudIsik;

        /**
         * Gets the value of the seotudIsik property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the seotudIsik property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSeotudIsik().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR427KMOTaotlejagaSeotudIsikudResponseType.SeotudIsikud.SeotudIsik }
         * 
         * 
         */
        public List<RR427KMOTaotlejagaSeotudIsikudResponseType.SeotudIsikud.SeotudIsik> getSeotudIsik() {
            if (seotudIsik == null) {
                seotudIsik = new ArrayList<RR427KMOTaotlejagaSeotudIsikudResponseType.SeotudIsikud.SeotudIsik>();
            }
            return this.seotudIsik;
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
         *         &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode" minOccurs="0"/&gt;
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
         *         &lt;element name="Rahvus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Pereseis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Elukoht" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "rahvus",
            "pereseis",
            "synnikoht",
            "elukoht",
            "sideandmed"
        })
        public static class SeotudIsik {

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
            protected RR427KMOTaotlejagaSeotudIsikudResponseType.SeotudIsikud.SeotudIsik.Kodakondsus kodakondsus;
            @XmlElement(name = "Rahvus")
            protected String rahvus;
            @XmlElement(name = "Pereseis")
            protected String pereseis;
            @XmlElement(name = "Synnikoht")
            protected String synnikoht;
            @XmlElement(name = "Elukoht")
            protected RR427KMOTaotlejagaSeotudIsikudResponseType.SeotudIsikud.SeotudIsik.Elukoht elukoht;
            @XmlElement(name = "Sideandmed")
            protected RR427KMOTaotlejagaSeotudIsikudResponseType.SeotudIsikud.SeotudIsik.Sideandmed sideandmed;

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
             *     {@link RR427KMOTaotlejagaSeotudIsikudResponseType.SeotudIsikud.SeotudIsik.Kodakondsus }
             *     
             */
            public RR427KMOTaotlejagaSeotudIsikudResponseType.SeotudIsikud.SeotudIsik.Kodakondsus getKodakondsus() {
                return kodakondsus;
            }

            /**
             * Sets the value of the kodakondsus property.
             * 
             * @param value
             *     allowed object is
             *     {@link RR427KMOTaotlejagaSeotudIsikudResponseType.SeotudIsikud.SeotudIsik.Kodakondsus }
             *     
             */
            public void setKodakondsus(RR427KMOTaotlejagaSeotudIsikudResponseType.SeotudIsikud.SeotudIsik.Kodakondsus value) {
                this.kodakondsus = value;
            }

            /**
             * Gets the value of the rahvus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRahvus() {
                return rahvus;
            }

            /**
             * Sets the value of the rahvus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRahvus(String value) {
                this.rahvus = value;
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
             *     {@link RR427KMOTaotlejagaSeotudIsikudResponseType.SeotudIsikud.SeotudIsik.Elukoht }
             *     
             */
            public RR427KMOTaotlejagaSeotudIsikudResponseType.SeotudIsikud.SeotudIsik.Elukoht getElukoht() {
                return elukoht;
            }

            /**
             * Sets the value of the elukoht property.
             * 
             * @param value
             *     allowed object is
             *     {@link RR427KMOTaotlejagaSeotudIsikudResponseType.SeotudIsikud.SeotudIsik.Elukoht }
             *     
             */
            public void setElukoht(RR427KMOTaotlejagaSeotudIsikudResponseType.SeotudIsikud.SeotudIsik.Elukoht value) {
                this.elukoht = value;
            }

            /**
             * Gets the value of the sideandmed property.
             * 
             * @return
             *     possible object is
             *     {@link RR427KMOTaotlejagaSeotudIsikudResponseType.SeotudIsikud.SeotudIsik.Sideandmed }
             *     
             */
            public RR427KMOTaotlejagaSeotudIsikudResponseType.SeotudIsikud.SeotudIsik.Sideandmed getSideandmed() {
                return sideandmed;
            }

            /**
             * Sets the value of the sideandmed property.
             * 
             * @param value
             *     allowed object is
             *     {@link RR427KMOTaotlejagaSeotudIsikudResponseType.SeotudIsikud.SeotudIsik.Sideandmed }
             *     
             */
            public void setSideandmed(RR427KMOTaotlejagaSeotudIsikudResponseType.SeotudIsikud.SeotudIsik.Sideandmed value) {
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
             *         &lt;element name="AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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

                @XmlElement(name = "AadressTekstina", required = true)
                protected String aadressTekstina;
                @XmlElement(name = "Postiindeks", required = true)
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

    }

}
