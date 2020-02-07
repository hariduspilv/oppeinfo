
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR441ResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR441ResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ttIsikuid"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="ttIsikud" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="ttIsikud.cIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud.cPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud.cEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud.cMPerenimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud.cMEesnimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud.cRiikKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud.cRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud.cIsanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud.cSugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud.cSynniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud.cSurmKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud.cTeoVoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud.cIsStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud.cKirjeStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud.cEKRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud.cEKMaak" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud.cEKVald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud.cEKAsula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud.cEKTanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud.cEKIndeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud.cEKAlgKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud.cEKVallaKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud.cEKAadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud.cSynniRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud.cSaabusEestiKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR441ResponseType", propOrder = {
    "ttIsikuid"
})
public class RR441ResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(required = true)
    protected RR441ResponseType.TtIsikuid ttIsikuid;

    /**
     * Gets the value of the ttIsikuid property.
     * 
     * @return
     *     possible object is
     *     {@link RR441ResponseType.TtIsikuid }
     *     
     */
    public RR441ResponseType.TtIsikuid getTtIsikuid() {
        return ttIsikuid;
    }

    /**
     * Sets the value of the ttIsikuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR441ResponseType.TtIsikuid }
     *     
     */
    public void setTtIsikuid(RR441ResponseType.TtIsikuid value) {
        this.ttIsikuid = value;
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
     *         &lt;element name="ttIsikud" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="ttIsikud.cIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud.cPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud.cEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud.cMPerenimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud.cMEesnimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud.cRiikKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud.cRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud.cIsanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud.cSugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud.cSynniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud.cSurmKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud.cTeoVoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud.cIsStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud.cKirjeStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud.cEKRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud.cEKMaak" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud.cEKVald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud.cEKAsula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud.cEKTanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud.cEKIndeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud.cEKAlgKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud.cEKVallaKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud.cEKAadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud.cSynniRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud.cSaabusEestiKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "ttIsikud"
    })
    public static class TtIsikuid {

        protected List<RR441ResponseType.TtIsikuid.TtIsikud> ttIsikud;

        /**
         * Gets the value of the ttIsikud property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ttIsikud property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTtIsikud().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR441ResponseType.TtIsikuid.TtIsikud }
         * 
         * 
         */
        public List<RR441ResponseType.TtIsikuid.TtIsikud> getTtIsikud() {
            if (ttIsikud == null) {
                ttIsikud = new ArrayList<RR441ResponseType.TtIsikuid.TtIsikud>();
            }
            return this.ttIsikud;
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
         *         &lt;element name="ttIsikud.cIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud.cPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud.cEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud.cMPerenimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud.cMEesnimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud.cRiikKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud.cRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud.cIsanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud.cSugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud.cSynniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud.cSurmKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud.cTeoVoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud.cIsStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud.cKirjeStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud.cEKRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud.cEKMaak" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud.cEKVald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud.cEKAsula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud.cEKTanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud.cEKIndeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud.cEKAlgKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud.cEKVallaKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud.cEKAadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud.cSynniRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud.cSaabusEestiKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "ttIsikudCIsikukood",
            "ttIsikudCPerenimi",
            "ttIsikudCEesnimi",
            "ttIsikudCMPerenimed",
            "ttIsikudCMEesnimed",
            "ttIsikudCRiikKood",
            "ttIsikudCRiik",
            "ttIsikudCIsanimi",
            "ttIsikudCSugu",
            "ttIsikudCSynniaeg",
            "ttIsikudCSurmKpv",
            "ttIsikudCTeoVoime",
            "ttIsikudCIsStaatus",
            "ttIsikudCKirjeStaatus",
            "ttIsikudCEKRiik",
            "ttIsikudCEKMaak",
            "ttIsikudCEKVald",
            "ttIsikudCEKAsula",
            "ttIsikudCEKTanav",
            "ttIsikudCEKIndeks",
            "ttIsikudCEKAlgKpv",
            "ttIsikudCEKVallaKpv",
            "ttIsikudCEKAadress",
            "ttIsikudCSynniRiik",
            "ttIsikudCSaabusEestiKpv"
        })
        public static class TtIsikud {

            @XmlElement(name = "ttIsikud.cIsikukood", required = true)
            protected String ttIsikudCIsikukood;
            @XmlElement(name = "ttIsikud.cPerenimi", required = true)
            protected String ttIsikudCPerenimi;
            @XmlElement(name = "ttIsikud.cEesnimi", required = true)
            protected String ttIsikudCEesnimi;
            @XmlElement(name = "ttIsikud.cMPerenimed", required = true)
            protected String ttIsikudCMPerenimed;
            @XmlElement(name = "ttIsikud.cMEesnimed", required = true)
            protected String ttIsikudCMEesnimed;
            @XmlElement(name = "ttIsikud.cRiikKood", required = true)
            protected String ttIsikudCRiikKood;
            @XmlElement(name = "ttIsikud.cRiik", required = true)
            protected String ttIsikudCRiik;
            @XmlElement(name = "ttIsikud.cIsanimi", required = true)
            protected String ttIsikudCIsanimi;
            @XmlElement(name = "ttIsikud.cSugu", required = true)
            protected String ttIsikudCSugu;
            @XmlElement(name = "ttIsikud.cSynniaeg", required = true)
            protected String ttIsikudCSynniaeg;
            @XmlElement(name = "ttIsikud.cSurmKpv", required = true)
            protected String ttIsikudCSurmKpv;
            @XmlElement(name = "ttIsikud.cTeoVoime", required = true)
            protected String ttIsikudCTeoVoime;
            @XmlElement(name = "ttIsikud.cIsStaatus", required = true)
            protected String ttIsikudCIsStaatus;
            @XmlElement(name = "ttIsikud.cKirjeStaatus", required = true)
            protected String ttIsikudCKirjeStaatus;
            @XmlElement(name = "ttIsikud.cEKRiik", required = true)
            protected String ttIsikudCEKRiik;
            @XmlElement(name = "ttIsikud.cEKMaak", required = true)
            protected String ttIsikudCEKMaak;
            @XmlElement(name = "ttIsikud.cEKVald", required = true)
            protected String ttIsikudCEKVald;
            @XmlElement(name = "ttIsikud.cEKAsula", required = true)
            protected String ttIsikudCEKAsula;
            @XmlElement(name = "ttIsikud.cEKTanav", required = true)
            protected String ttIsikudCEKTanav;
            @XmlElement(name = "ttIsikud.cEKIndeks", required = true)
            protected String ttIsikudCEKIndeks;
            @XmlElement(name = "ttIsikud.cEKAlgKpv", required = true)
            protected String ttIsikudCEKAlgKpv;
            @XmlElement(name = "ttIsikud.cEKVallaKpv", required = true)
            protected String ttIsikudCEKVallaKpv;
            @XmlElement(name = "ttIsikud.cEKAadress", required = true)
            protected String ttIsikudCEKAadress;
            @XmlElement(name = "ttIsikud.cSynniRiik", required = true)
            protected String ttIsikudCSynniRiik;
            @XmlElement(name = "ttIsikud.cSaabusEestiKpv", required = true)
            protected String ttIsikudCSaabusEestiKpv;

            /**
             * Gets the value of the ttIsikudCIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikudCIsikukood() {
                return ttIsikudCIsikukood;
            }

            /**
             * Sets the value of the ttIsikudCIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikudCIsikukood(String value) {
                this.ttIsikudCIsikukood = value;
            }

            /**
             * Gets the value of the ttIsikudCPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikudCPerenimi() {
                return ttIsikudCPerenimi;
            }

            /**
             * Sets the value of the ttIsikudCPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikudCPerenimi(String value) {
                this.ttIsikudCPerenimi = value;
            }

            /**
             * Gets the value of the ttIsikudCEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikudCEesnimi() {
                return ttIsikudCEesnimi;
            }

            /**
             * Sets the value of the ttIsikudCEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikudCEesnimi(String value) {
                this.ttIsikudCEesnimi = value;
            }

            /**
             * Gets the value of the ttIsikudCMPerenimed property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikudCMPerenimed() {
                return ttIsikudCMPerenimed;
            }

            /**
             * Sets the value of the ttIsikudCMPerenimed property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikudCMPerenimed(String value) {
                this.ttIsikudCMPerenimed = value;
            }

            /**
             * Gets the value of the ttIsikudCMEesnimed property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikudCMEesnimed() {
                return ttIsikudCMEesnimed;
            }

            /**
             * Sets the value of the ttIsikudCMEesnimed property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikudCMEesnimed(String value) {
                this.ttIsikudCMEesnimed = value;
            }

            /**
             * Gets the value of the ttIsikudCRiikKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikudCRiikKood() {
                return ttIsikudCRiikKood;
            }

            /**
             * Sets the value of the ttIsikudCRiikKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikudCRiikKood(String value) {
                this.ttIsikudCRiikKood = value;
            }

            /**
             * Gets the value of the ttIsikudCRiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikudCRiik() {
                return ttIsikudCRiik;
            }

            /**
             * Sets the value of the ttIsikudCRiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikudCRiik(String value) {
                this.ttIsikudCRiik = value;
            }

            /**
             * Gets the value of the ttIsikudCIsanimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikudCIsanimi() {
                return ttIsikudCIsanimi;
            }

            /**
             * Sets the value of the ttIsikudCIsanimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikudCIsanimi(String value) {
                this.ttIsikudCIsanimi = value;
            }

            /**
             * Gets the value of the ttIsikudCSugu property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikudCSugu() {
                return ttIsikudCSugu;
            }

            /**
             * Sets the value of the ttIsikudCSugu property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikudCSugu(String value) {
                this.ttIsikudCSugu = value;
            }

            /**
             * Gets the value of the ttIsikudCSynniaeg property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikudCSynniaeg() {
                return ttIsikudCSynniaeg;
            }

            /**
             * Sets the value of the ttIsikudCSynniaeg property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikudCSynniaeg(String value) {
                this.ttIsikudCSynniaeg = value;
            }

            /**
             * Gets the value of the ttIsikudCSurmKpv property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikudCSurmKpv() {
                return ttIsikudCSurmKpv;
            }

            /**
             * Sets the value of the ttIsikudCSurmKpv property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikudCSurmKpv(String value) {
                this.ttIsikudCSurmKpv = value;
            }

            /**
             * Gets the value of the ttIsikudCTeoVoime property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikudCTeoVoime() {
                return ttIsikudCTeoVoime;
            }

            /**
             * Sets the value of the ttIsikudCTeoVoime property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikudCTeoVoime(String value) {
                this.ttIsikudCTeoVoime = value;
            }

            /**
             * Gets the value of the ttIsikudCIsStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikudCIsStaatus() {
                return ttIsikudCIsStaatus;
            }

            /**
             * Sets the value of the ttIsikudCIsStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikudCIsStaatus(String value) {
                this.ttIsikudCIsStaatus = value;
            }

            /**
             * Gets the value of the ttIsikudCKirjeStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikudCKirjeStaatus() {
                return ttIsikudCKirjeStaatus;
            }

            /**
             * Sets the value of the ttIsikudCKirjeStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikudCKirjeStaatus(String value) {
                this.ttIsikudCKirjeStaatus = value;
            }

            /**
             * Gets the value of the ttIsikudCEKRiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikudCEKRiik() {
                return ttIsikudCEKRiik;
            }

            /**
             * Sets the value of the ttIsikudCEKRiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikudCEKRiik(String value) {
                this.ttIsikudCEKRiik = value;
            }

            /**
             * Gets the value of the ttIsikudCEKMaak property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikudCEKMaak() {
                return ttIsikudCEKMaak;
            }

            /**
             * Sets the value of the ttIsikudCEKMaak property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikudCEKMaak(String value) {
                this.ttIsikudCEKMaak = value;
            }

            /**
             * Gets the value of the ttIsikudCEKVald property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikudCEKVald() {
                return ttIsikudCEKVald;
            }

            /**
             * Sets the value of the ttIsikudCEKVald property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikudCEKVald(String value) {
                this.ttIsikudCEKVald = value;
            }

            /**
             * Gets the value of the ttIsikudCEKAsula property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikudCEKAsula() {
                return ttIsikudCEKAsula;
            }

            /**
             * Sets the value of the ttIsikudCEKAsula property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikudCEKAsula(String value) {
                this.ttIsikudCEKAsula = value;
            }

            /**
             * Gets the value of the ttIsikudCEKTanav property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikudCEKTanav() {
                return ttIsikudCEKTanav;
            }

            /**
             * Sets the value of the ttIsikudCEKTanav property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikudCEKTanav(String value) {
                this.ttIsikudCEKTanav = value;
            }

            /**
             * Gets the value of the ttIsikudCEKIndeks property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikudCEKIndeks() {
                return ttIsikudCEKIndeks;
            }

            /**
             * Sets the value of the ttIsikudCEKIndeks property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikudCEKIndeks(String value) {
                this.ttIsikudCEKIndeks = value;
            }

            /**
             * Gets the value of the ttIsikudCEKAlgKpv property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikudCEKAlgKpv() {
                return ttIsikudCEKAlgKpv;
            }

            /**
             * Sets the value of the ttIsikudCEKAlgKpv property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikudCEKAlgKpv(String value) {
                this.ttIsikudCEKAlgKpv = value;
            }

            /**
             * Gets the value of the ttIsikudCEKVallaKpv property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikudCEKVallaKpv() {
                return ttIsikudCEKVallaKpv;
            }

            /**
             * Sets the value of the ttIsikudCEKVallaKpv property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikudCEKVallaKpv(String value) {
                this.ttIsikudCEKVallaKpv = value;
            }

            /**
             * Gets the value of the ttIsikudCEKAadress property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikudCEKAadress() {
                return ttIsikudCEKAadress;
            }

            /**
             * Sets the value of the ttIsikudCEKAadress property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikudCEKAadress(String value) {
                this.ttIsikudCEKAadress = value;
            }

            /**
             * Gets the value of the ttIsikudCSynniRiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikudCSynniRiik() {
                return ttIsikudCSynniRiik;
            }

            /**
             * Sets the value of the ttIsikudCSynniRiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikudCSynniRiik(String value) {
                this.ttIsikudCSynniRiik = value;
            }

            /**
             * Gets the value of the ttIsikudCSaabusEestiKpv property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikudCSaabusEestiKpv() {
                return ttIsikudCSaabusEestiKpv;
            }

            /**
             * Sets the value of the ttIsikudCSaabusEestiKpv property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikudCSaabusEestiKpv(String value) {
                this.ttIsikudCSaabusEestiKpv = value;
            }

        }

    }

}
