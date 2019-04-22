
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRPORTSEOSEDResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRPORTSEOSEDResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Veatekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Suhted"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Suhe" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Suhe.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Suhtetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Olek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Suhtealgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Suhtelopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RRPORTSEOSEDResponseType", propOrder = {
    "veatekst",
    "suhted"
})
public class RRPORTSEOSEDResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Veatekst")
    protected String veatekst;
    @XmlElement(name = "Suhted", required = true)
    protected RRPORTSEOSEDResponseType.Suhted suhted;

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
     * Gets the value of the suhted property.
     * 
     * @return
     *     possible object is
     *     {@link RRPORTSEOSEDResponseType.Suhted }
     *     
     */
    public RRPORTSEOSEDResponseType.Suhted getSuhted() {
        return suhted;
    }

    /**
     * Sets the value of the suhted property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRPORTSEOSEDResponseType.Suhted }
     *     
     */
    public void setSuhted(RRPORTSEOSEDResponseType.Suhted value) {
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
     *         &lt;element name="Suhe" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Suhe.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Suhtetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Olek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Suhtealgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Suhtelopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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

        @XmlElement(name = "Suhe")
        protected List<RRPORTSEOSEDResponseType.Suhted.Suhe> suhe;

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
         * {@link RRPORTSEOSEDResponseType.Suhted.Suhe }
         * 
         * 
         */
        public List<RRPORTSEOSEDResponseType.Suhted.Suhe> getSuhe() {
            if (suhe == null) {
                suhe = new ArrayList<RRPORTSEOSEDResponseType.Suhted.Suhe>();
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
         *         &lt;element name="Suhe.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Suhtetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Olek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Suhtealgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Suhtelopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "suheIsikukood",
            "suheSynniaeg",
            "suhePerenimi",
            "suheEesnimi",
            "suheSuhtetyyp",
            "suheOlek",
            "suheSuhtealgus",
            "suheSuhtelopp"
        })
        public static class Suhe {

            @XmlElement(name = "Suhe.Isikukood", required = true)
            protected String suheIsikukood;
            @XmlElement(name = "Suhe.Synniaeg", required = true)
            protected String suheSynniaeg;
            @XmlElement(name = "Suhe.Perenimi", required = true)
            protected String suhePerenimi;
            @XmlElement(name = "Suhe.Eesnimi", required = true)
            protected String suheEesnimi;
            @XmlElement(name = "Suhe.Suhtetyyp", required = true)
            protected String suheSuhtetyyp;
            @XmlElement(name = "Suhe.Olek", required = true)
            protected String suheOlek;
            @XmlElement(name = "Suhe.Suhtealgus", required = true)
            protected String suheSuhtealgus;
            @XmlElement(name = "Suhe.Suhtelopp", required = true)
            protected String suheSuhtelopp;

            /**
             * Gets the value of the suheIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheIsikukood() {
                return suheIsikukood;
            }

            /**
             * Sets the value of the suheIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheIsikukood(String value) {
                this.suheIsikukood = value;
            }

            /**
             * Gets the value of the suheSynniaeg property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheSynniaeg() {
                return suheSynniaeg;
            }

            /**
             * Sets the value of the suheSynniaeg property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheSynniaeg(String value) {
                this.suheSynniaeg = value;
            }

            /**
             * Gets the value of the suhePerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuhePerenimi() {
                return suhePerenimi;
            }

            /**
             * Sets the value of the suhePerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuhePerenimi(String value) {
                this.suhePerenimi = value;
            }

            /**
             * Gets the value of the suheEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheEesnimi() {
                return suheEesnimi;
            }

            /**
             * Sets the value of the suheEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheEesnimi(String value) {
                this.suheEesnimi = value;
            }

            /**
             * Gets the value of the suheSuhtetyyp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheSuhtetyyp() {
                return suheSuhtetyyp;
            }

            /**
             * Sets the value of the suheSuhtetyyp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheSuhtetyyp(String value) {
                this.suheSuhtetyyp = value;
            }

            /**
             * Gets the value of the suheOlek property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheOlek() {
                return suheOlek;
            }

            /**
             * Sets the value of the suheOlek property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheOlek(String value) {
                this.suheOlek = value;
            }

            /**
             * Gets the value of the suheSuhtealgus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheSuhtealgus() {
                return suheSuhtealgus;
            }

            /**
             * Sets the value of the suheSuhtealgus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheSuhtealgus(String value) {
                this.suheSuhtealgus = value;
            }

            /**
             * Gets the value of the suheSuhtelopp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheSuhtelopp() {
                return suheSuhtelopp;
            }

            /**
             * Sets the value of the suheSuhtelopp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheSuhtelopp(String value) {
                this.suheSuhtelopp = value;
            }

        }

    }

}
