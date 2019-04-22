
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR421KMOElKodElukohaRegistreerimineResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR421KMOElKodElukohaRegistreerimineResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="YksikIsik" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Isik" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
 *                             &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                             &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="EestiElukohaAlgus" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                             &lt;element name="Elamisoigus" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="KehtibAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                                       &lt;element name="KehtibKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
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
 *                             &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
 *                             &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                             &lt;element name="Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Seisund" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isaeesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR421KMOElKodElukohaRegistreerimineResponseType", propOrder = {
    "yksikIsik",
    "isikud"
})
public class RR421KMOElKodElukohaRegistreerimineResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "YksikIsik")
    protected RR421KMOElKodElukohaRegistreerimineResponseType.YksikIsik yksikIsik;
    @XmlElement(name = "Isikud")
    protected RR421KMOElKodElukohaRegistreerimineResponseType.Isikud isikud;

    /**
     * Gets the value of the yksikIsik property.
     * 
     * @return
     *     possible object is
     *     {@link RR421KMOElKodElukohaRegistreerimineResponseType.YksikIsik }
     *     
     */
    public RR421KMOElKodElukohaRegistreerimineResponseType.YksikIsik getYksikIsik() {
        return yksikIsik;
    }

    /**
     * Sets the value of the yksikIsik property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR421KMOElKodElukohaRegistreerimineResponseType.YksikIsik }
     *     
     */
    public void setYksikIsik(RR421KMOElKodElukohaRegistreerimineResponseType.YksikIsik value) {
        this.yksikIsik = value;
    }

    /**
     * Gets the value of the isikud property.
     * 
     * @return
     *     possible object is
     *     {@link RR421KMOElKodElukohaRegistreerimineResponseType.Isikud }
     *     
     */
    public RR421KMOElKodElukohaRegistreerimineResponseType.Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR421KMOElKodElukohaRegistreerimineResponseType.Isikud }
     *     
     */
    public void setIsikud(RR421KMOElKodElukohaRegistreerimineResponseType.Isikud value) {
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
     *                   &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
     *                   &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                   &lt;element name="Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Seisund" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isaeesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        protected List<RR421KMOElKodElukohaRegistreerimineResponseType.Isikud.Isik> isik;

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
         * {@link RR421KMOElKodElukohaRegistreerimineResponseType.Isikud.Isik }
         * 
         * 
         */
        public List<RR421KMOElKodElukohaRegistreerimineResponseType.Isikud.Isik> getIsik() {
            if (isik == null) {
                isik = new ArrayList<RR421KMOElKodElukohaRegistreerimineResponseType.Isikud.Isik>();
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
         *         &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
         *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *         &lt;element name="Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Seisund" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isaeesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            @XmlElement(name = "Isaeesnimi", required = true)
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
     *         &lt;element name="Isik" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
     *                   &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                   &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="EestiElukohaAlgus" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                   &lt;element name="Elamisoigus" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="KehtibAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                             &lt;element name="KehtibKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
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
    public static class YksikIsik {

        @XmlElement(name = "Isik")
        protected RR421KMOElKodElukohaRegistreerimineResponseType.YksikIsik.Isik isik;

        /**
         * Gets the value of the isik property.
         * 
         * @return
         *     possible object is
         *     {@link RR421KMOElKodElukohaRegistreerimineResponseType.YksikIsik.Isik }
         *     
         */
        public RR421KMOElKodElukohaRegistreerimineResponseType.YksikIsik.Isik getIsik() {
            return isik;
        }

        /**
         * Sets the value of the isik property.
         * 
         * @param value
         *     allowed object is
         *     {@link RR421KMOElKodElukohaRegistreerimineResponseType.YksikIsik.Isik }
         *     
         */
        public void setIsik(RR421KMOElKodElukohaRegistreerimineResponseType.YksikIsik.Isik value) {
            this.isik = value;
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
         *         &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *         &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="EestiElukohaAlgus" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *         &lt;element name="Elamisoigus" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="KehtibAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *                   &lt;element name="KehtibKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
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
            "synniaeg",
            "perenimi",
            "eesnimi",
            "eestiElukohaAlgus",
            "elamisoigus"
        })
        public static class Isik {

            @XmlElement(name = "Isikukood", required = true)
            protected String isikukood;
            @XmlElement(name = "Synniaeg")
            protected String synniaeg;
            @XmlElement(name = "Perenimi", required = true)
            protected String perenimi;
            @XmlElement(name = "Eesnimi", required = true)
            protected String eesnimi;
            @XmlElement(name = "EestiElukohaAlgus")
            protected String eestiElukohaAlgus;
            @XmlElement(name = "Elamisoigus")
            protected RR421KMOElKodElukohaRegistreerimineResponseType.YksikIsik.Isik.Elamisoigus elamisoigus;

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
             * Gets the value of the eestiElukohaAlgus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEestiElukohaAlgus() {
                return eestiElukohaAlgus;
            }

            /**
             * Sets the value of the eestiElukohaAlgus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEestiElukohaAlgus(String value) {
                this.eestiElukohaAlgus = value;
            }

            /**
             * Gets the value of the elamisoigus property.
             * 
             * @return
             *     possible object is
             *     {@link RR421KMOElKodElukohaRegistreerimineResponseType.YksikIsik.Isik.Elamisoigus }
             *     
             */
            public RR421KMOElKodElukohaRegistreerimineResponseType.YksikIsik.Isik.Elamisoigus getElamisoigus() {
                return elamisoigus;
            }

            /**
             * Sets the value of the elamisoigus property.
             * 
             * @param value
             *     allowed object is
             *     {@link RR421KMOElKodElukohaRegistreerimineResponseType.YksikIsik.Isik.Elamisoigus }
             *     
             */
            public void setElamisoigus(RR421KMOElKodElukohaRegistreerimineResponseType.YksikIsik.Isik.Elamisoigus value) {
                this.elamisoigus = value;
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
             *         &lt;element name="KehtibAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
             *         &lt;element name="KehtibKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
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
                "kehtibAlates",
                "kehtibKuni"
            })
            public static class Elamisoigus {

                @XmlElement(name = "KehtibAlates")
                protected String kehtibAlates;
                @XmlElement(name = "KehtibKuni")
                protected String kehtibKuni;

                /**
                 * Gets the value of the kehtibAlates property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKehtibAlates() {
                    return kehtibAlates;
                }

                /**
                 * Sets the value of the kehtibAlates property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKehtibAlates(String value) {
                    this.kehtibAlates = value;
                }

                /**
                 * Gets the value of the kehtibKuni property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKehtibKuni() {
                    return kehtibKuni;
                }

                /**
                 * Sets the value of the kehtibKuni property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKehtibKuni(String value) {
                    this.kehtibKuni = value;
                }

            }

        }

    }

}
