
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR44isikKodanikResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR44isikKodanikResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isikud"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="isikuandmed" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="isikuandmed.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isikuandmed.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isikuandmed.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isikuandmed.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isikuandmed.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isikuandmed.Eesti_aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isikuandmed.tdoktyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isikuandmed.tdokseeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isikuandmed.tdoknr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isikuandmed.tdokvaljakp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isikuandmed.tdokkehta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isikuandmed.tdokkehtl" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isikuandmed.tdokstaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isikuandmed.Rahvus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isikuandmed.Emakeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isikuandmed.Haridus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isikuandmed.Tegevusala" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *         &lt;element name="Suhted"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Suhteandmed" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Suhteandmed.ssuhtetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhteandmed.sIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhteandmed.sPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhteandmed.sEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhteandmed.sIsanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhteandmed.sEesti_aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhteandmed.sStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR44isikKodanikResponseType", propOrder = {
    "isikud",
    "suhted"
})
public class RR44IsikKodanikResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(required = true)
    protected RR44IsikKodanikResponseType.Isikud isikud;
    @XmlElement(name = "Suhted", required = true)
    protected RR44IsikKodanikResponseType.Suhted suhted;

    /**
     * Gets the value of the isikud property.
     * 
     * @return
     *     possible object is
     *     {@link RR44IsikKodanikResponseType.Isikud }
     *     
     */
    public RR44IsikKodanikResponseType.Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR44IsikKodanikResponseType.Isikud }
     *     
     */
    public void setIsikud(RR44IsikKodanikResponseType.Isikud value) {
        this.isikud = value;
    }

    /**
     * Gets the value of the suhted property.
     * 
     * @return
     *     possible object is
     *     {@link RR44IsikKodanikResponseType.Suhted }
     *     
     */
    public RR44IsikKodanikResponseType.Suhted getSuhted() {
        return suhted;
    }

    /**
     * Sets the value of the suhted property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR44IsikKodanikResponseType.Suhted }
     *     
     */
    public void setSuhted(RR44IsikKodanikResponseType.Suhted value) {
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
     *         &lt;element name="isikuandmed" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="isikuandmed.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isikuandmed.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isikuandmed.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isikuandmed.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isikuandmed.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isikuandmed.Eesti_aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isikuandmed.tdoktyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isikuandmed.tdokseeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isikuandmed.tdoknr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isikuandmed.tdokvaljakp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isikuandmed.tdokkehta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isikuandmed.tdokkehtl" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isikuandmed.tdokstaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isikuandmed.Rahvus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isikuandmed.Emakeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isikuandmed.Haridus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isikuandmed.Tegevusala" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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

        protected List<RR44IsikKodanikResponseType.Isikud.Isikuandmed> isikuandmed;

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
         * {@link RR44IsikKodanikResponseType.Isikud.Isikuandmed }
         * 
         * 
         */
        public List<RR44IsikKodanikResponseType.Isikud.Isikuandmed> getIsikuandmed() {
            if (isikuandmed == null) {
                isikuandmed = new ArrayList<RR44IsikKodanikResponseType.Isikud.Isikuandmed>();
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
         *         &lt;element name="isikuandmed.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isikuandmed.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isikuandmed.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isikuandmed.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isikuandmed.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isikuandmed.Eesti_aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isikuandmed.tdoktyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isikuandmed.tdokseeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isikuandmed.tdoknr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isikuandmed.tdokvaljakp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isikuandmed.tdokkehta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isikuandmed.tdokkehtl" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isikuandmed.tdokstaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isikuandmed.Rahvus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isikuandmed.Emakeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isikuandmed.Haridus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isikuandmed.Tegevusala" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "isikuandmedSugu",
            "isikuandmedSynniaeg",
            "isikuandmedStaatus",
            "isikuandmedEestiAadress",
            "isikuandmedTdoktyyp",
            "isikuandmedTdokseeria",
            "isikuandmedTdoknr",
            "isikuandmedTdokvaljakp",
            "isikuandmedTdokkehta",
            "isikuandmedTdokkehtl",
            "isikuandmedTdokstaatus",
            "isikuandmedRahvus",
            "isikuandmedEmakeel",
            "isikuandmedHaridus",
            "isikuandmedTegevusala"
        })
        public static class Isikuandmed {

            @XmlElement(name = "isikuandmed.Perenimi", required = true)
            protected String isikuandmedPerenimi;
            @XmlElement(name = "isikuandmed.Eesnimi", required = true)
            protected String isikuandmedEesnimi;
            @XmlElement(name = "isikuandmed.Sugu", required = true)
            protected String isikuandmedSugu;
            @XmlElement(name = "isikuandmed.Synniaeg", required = true)
            protected String isikuandmedSynniaeg;
            @XmlElement(name = "isikuandmed.Staatus", required = true)
            protected String isikuandmedStaatus;
            @XmlElement(name = "isikuandmed.Eesti_aadress", required = true)
            protected String isikuandmedEestiAadress;
            @XmlElement(name = "isikuandmed.tdoktyyp", required = true)
            protected String isikuandmedTdoktyyp;
            @XmlElement(name = "isikuandmed.tdokseeria", required = true)
            protected String isikuandmedTdokseeria;
            @XmlElement(name = "isikuandmed.tdoknr", required = true)
            protected String isikuandmedTdoknr;
            @XmlElement(name = "isikuandmed.tdokvaljakp", required = true)
            protected String isikuandmedTdokvaljakp;
            @XmlElement(name = "isikuandmed.tdokkehta", required = true)
            protected String isikuandmedTdokkehta;
            @XmlElement(name = "isikuandmed.tdokkehtl", required = true)
            protected String isikuandmedTdokkehtl;
            @XmlElement(name = "isikuandmed.tdokstaatus", required = true)
            protected String isikuandmedTdokstaatus;
            @XmlElement(name = "isikuandmed.Rahvus", required = true)
            protected String isikuandmedRahvus;
            @XmlElement(name = "isikuandmed.Emakeel", required = true)
            protected String isikuandmedEmakeel;
            @XmlElement(name = "isikuandmed.Haridus", required = true)
            protected String isikuandmedHaridus;
            @XmlElement(name = "isikuandmed.Tegevusala", required = true)
            protected String isikuandmedTegevusala;

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
             * Gets the value of the isikuandmedSugu property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSugu() {
                return isikuandmedSugu;
            }

            /**
             * Sets the value of the isikuandmedSugu property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSugu(String value) {
                this.isikuandmedSugu = value;
            }

            /**
             * Gets the value of the isikuandmedSynniaeg property.
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
             * Sets the value of the isikuandmedSynniaeg property.
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
             * Gets the value of the isikuandmedStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedStaatus() {
                return isikuandmedStaatus;
            }

            /**
             * Sets the value of the isikuandmedStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedStaatus(String value) {
                this.isikuandmedStaatus = value;
            }

            /**
             * Gets the value of the isikuandmedEestiAadress property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedEestiAadress() {
                return isikuandmedEestiAadress;
            }

            /**
             * Sets the value of the isikuandmedEestiAadress property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedEestiAadress(String value) {
                this.isikuandmedEestiAadress = value;
            }

            /**
             * Gets the value of the isikuandmedTdoktyyp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedTdoktyyp() {
                return isikuandmedTdoktyyp;
            }

            /**
             * Sets the value of the isikuandmedTdoktyyp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedTdoktyyp(String value) {
                this.isikuandmedTdoktyyp = value;
            }

            /**
             * Gets the value of the isikuandmedTdokseeria property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedTdokseeria() {
                return isikuandmedTdokseeria;
            }

            /**
             * Sets the value of the isikuandmedTdokseeria property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedTdokseeria(String value) {
                this.isikuandmedTdokseeria = value;
            }

            /**
             * Gets the value of the isikuandmedTdoknr property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedTdoknr() {
                return isikuandmedTdoknr;
            }

            /**
             * Sets the value of the isikuandmedTdoknr property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedTdoknr(String value) {
                this.isikuandmedTdoknr = value;
            }

            /**
             * Gets the value of the isikuandmedTdokvaljakp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedTdokvaljakp() {
                return isikuandmedTdokvaljakp;
            }

            /**
             * Sets the value of the isikuandmedTdokvaljakp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedTdokvaljakp(String value) {
                this.isikuandmedTdokvaljakp = value;
            }

            /**
             * Gets the value of the isikuandmedTdokkehta property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedTdokkehta() {
                return isikuandmedTdokkehta;
            }

            /**
             * Sets the value of the isikuandmedTdokkehta property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedTdokkehta(String value) {
                this.isikuandmedTdokkehta = value;
            }

            /**
             * Gets the value of the isikuandmedTdokkehtl property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedTdokkehtl() {
                return isikuandmedTdokkehtl;
            }

            /**
             * Sets the value of the isikuandmedTdokkehtl property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedTdokkehtl(String value) {
                this.isikuandmedTdokkehtl = value;
            }

            /**
             * Gets the value of the isikuandmedTdokstaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedTdokstaatus() {
                return isikuandmedTdokstaatus;
            }

            /**
             * Sets the value of the isikuandmedTdokstaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedTdokstaatus(String value) {
                this.isikuandmedTdokstaatus = value;
            }

            /**
             * Gets the value of the isikuandmedRahvus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedRahvus() {
                return isikuandmedRahvus;
            }

            /**
             * Sets the value of the isikuandmedRahvus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedRahvus(String value) {
                this.isikuandmedRahvus = value;
            }

            /**
             * Gets the value of the isikuandmedEmakeel property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedEmakeel() {
                return isikuandmedEmakeel;
            }

            /**
             * Sets the value of the isikuandmedEmakeel property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedEmakeel(String value) {
                this.isikuandmedEmakeel = value;
            }

            /**
             * Gets the value of the isikuandmedHaridus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedHaridus() {
                return isikuandmedHaridus;
            }

            /**
             * Sets the value of the isikuandmedHaridus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedHaridus(String value) {
                this.isikuandmedHaridus = value;
            }

            /**
             * Gets the value of the isikuandmedTegevusala property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedTegevusala() {
                return isikuandmedTegevusala;
            }

            /**
             * Sets the value of the isikuandmedTegevusala property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedTegevusala(String value) {
                this.isikuandmedTegevusala = value;
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
     *         &lt;element name="Suhteandmed" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Suhteandmed.ssuhtetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhteandmed.sIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhteandmed.sPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhteandmed.sEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhteandmed.sIsanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhteandmed.sEesti_aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhteandmed.sStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "suhteandmed"
    })
    public static class Suhted {

        @XmlElement(name = "Suhteandmed")
        protected List<RR44IsikKodanikResponseType.Suhted.Suhteandmed> suhteandmed;

        /**
         * Gets the value of the suhteandmed property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the suhteandmed property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSuhteandmed().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR44IsikKodanikResponseType.Suhted.Suhteandmed }
         * 
         * 
         */
        public List<RR44IsikKodanikResponseType.Suhted.Suhteandmed> getSuhteandmed() {
            if (suhteandmed == null) {
                suhteandmed = new ArrayList<RR44IsikKodanikResponseType.Suhted.Suhteandmed>();
            }
            return this.suhteandmed;
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
         *         &lt;element name="Suhteandmed.ssuhtetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhteandmed.sIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhteandmed.sPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhteandmed.sEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhteandmed.sIsanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhteandmed.sEesti_aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhteandmed.sStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "suhteandmedSsuhtetyyp",
            "suhteandmedSIsikukood",
            "suhteandmedSPerenimi",
            "suhteandmedSEesnimi",
            "suhteandmedSIsanimi",
            "suhteandmedSEestiAadress",
            "suhteandmedSStaatus"
        })
        public static class Suhteandmed {

            @XmlElement(name = "Suhteandmed.ssuhtetyyp", required = true)
            protected String suhteandmedSsuhtetyyp;
            @XmlElement(name = "Suhteandmed.sIsikukood", required = true)
            protected String suhteandmedSIsikukood;
            @XmlElement(name = "Suhteandmed.sPerenimi", required = true)
            protected String suhteandmedSPerenimi;
            @XmlElement(name = "Suhteandmed.sEesnimi", required = true)
            protected String suhteandmedSEesnimi;
            @XmlElement(name = "Suhteandmed.sIsanimi", required = true)
            protected String suhteandmedSIsanimi;
            @XmlElement(name = "Suhteandmed.sEesti_aadress", required = true)
            protected String suhteandmedSEestiAadress;
            @XmlElement(name = "Suhteandmed.sStaatus", required = true)
            protected String suhteandmedSStaatus;

            /**
             * Gets the value of the suhteandmedSsuhtetyyp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuhteandmedSsuhtetyyp() {
                return suhteandmedSsuhtetyyp;
            }

            /**
             * Sets the value of the suhteandmedSsuhtetyyp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuhteandmedSsuhtetyyp(String value) {
                this.suhteandmedSsuhtetyyp = value;
            }

            /**
             * Gets the value of the suhteandmedSIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuhteandmedSIsikukood() {
                return suhteandmedSIsikukood;
            }

            /**
             * Sets the value of the suhteandmedSIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuhteandmedSIsikukood(String value) {
                this.suhteandmedSIsikukood = value;
            }

            /**
             * Gets the value of the suhteandmedSPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuhteandmedSPerenimi() {
                return suhteandmedSPerenimi;
            }

            /**
             * Sets the value of the suhteandmedSPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuhteandmedSPerenimi(String value) {
                this.suhteandmedSPerenimi = value;
            }

            /**
             * Gets the value of the suhteandmedSEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuhteandmedSEesnimi() {
                return suhteandmedSEesnimi;
            }

            /**
             * Sets the value of the suhteandmedSEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuhteandmedSEesnimi(String value) {
                this.suhteandmedSEesnimi = value;
            }

            /**
             * Gets the value of the suhteandmedSIsanimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuhteandmedSIsanimi() {
                return suhteandmedSIsanimi;
            }

            /**
             * Sets the value of the suhteandmedSIsanimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuhteandmedSIsanimi(String value) {
                this.suhteandmedSIsanimi = value;
            }

            /**
             * Gets the value of the suhteandmedSEestiAadress property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuhteandmedSEestiAadress() {
                return suhteandmedSEestiAadress;
            }

            /**
             * Sets the value of the suhteandmedSEestiAadress property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuhteandmedSEestiAadress(String value) {
                this.suhteandmedSEestiAadress = value;
            }

            /**
             * Gets the value of the suhteandmedSStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuhteandmedSStaatus() {
                return suhteandmedSStaatus;
            }

            /**
             * Sets the value of the suhteandmedSStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuhteandmedSStaatus(String value) {
                this.suhteandmedSStaatus = value;
            }

        }

    }

}
