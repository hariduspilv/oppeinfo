
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR87IsikKoda898AnalResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR87IsikKoda898AnalResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Info" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Veakood" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="Veatekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
 *                             &lt;element name="Isikuandmed.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Surmaaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Kodakondsuskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Kodakondsustekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.EKodakondsuskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.EKodakondsustekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Sideaadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR87IsikKoda898AnalResponseType", propOrder = {
    "info",
    "veakood",
    "veatekst",
    "isikud"
})
public class RR87IsikKoda898AnalResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Info", required = true)
    protected String info;
    @XmlElement(name = "Veakood")
    protected BigInteger veakood;
    @XmlElement(name = "Veatekst")
    protected String veatekst;
    @XmlElement(name = "Isikud", required = true)
    protected RR87IsikKoda898AnalResponseType.Isikud isikud;

    /**
     * Gets the value of the info property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfo() {
        return info;
    }

    /**
     * Sets the value of the info property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfo(String value) {
        this.info = value;
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
     * Gets the value of the isikud property.
     * 
     * @return
     *     possible object is
     *     {@link RR87IsikKoda898AnalResponseType.Isikud }
     *     
     */
    public RR87IsikKoda898AnalResponseType.Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR87IsikKoda898AnalResponseType.Isikud }
     *     
     */
    public void setIsikud(RR87IsikKoda898AnalResponseType.Isikud value) {
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
     *         &lt;element name="Isikuandmed" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Isikuandmed.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Surmaaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Kodakondsuskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Kodakondsustekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.EKodakondsuskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.EKodakondsustekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Sideaadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        protected List<RR87IsikKoda898AnalResponseType.Isikud.Isikuandmed> isikuandmed;

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
         * {@link RR87IsikKoda898AnalResponseType.Isikud.Isikuandmed }
         * 
         * 
         */
        public List<RR87IsikKoda898AnalResponseType.Isikud.Isikuandmed> getIsikuandmed() {
            if (isikuandmed == null) {
                isikuandmed = new ArrayList<RR87IsikKoda898AnalResponseType.Isikud.Isikuandmed>();
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
         *         &lt;element name="Isikuandmed.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Surmaaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Kodakondsuskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Kodakondsustekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.EKodakondsuskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.EKodakondsustekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Sideaadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "isikuandmedIsikukood",
            "isikuandmedEesnimi",
            "isikuandmedPerenimi",
            "isikuandmedIsikuStaatus",
            "isikuandmedSurmaaeg",
            "isikuandmedKodakondsuskood",
            "isikuandmedKodakondsustekstina",
            "isikuandmedEKodakondsuskood",
            "isikuandmedEKodakondsustekstina",
            "isikuandmedAadressTekstina",
            "isikuandmedSideaadress"
        })
        public static class Isikuandmed {

            @XmlElement(name = "Isikuandmed.Isikukood", required = true)
            protected String isikuandmedIsikukood;
            @XmlElement(name = "Isikuandmed.Eesnimi", required = true)
            protected String isikuandmedEesnimi;
            @XmlElement(name = "Isikuandmed.Perenimi", required = true)
            protected String isikuandmedPerenimi;
            @XmlElement(name = "Isikuandmed.IsikuStaatus", required = true)
            protected String isikuandmedIsikuStaatus;
            @XmlElement(name = "Isikuandmed.Surmaaeg", required = true)
            protected String isikuandmedSurmaaeg;
            @XmlElement(name = "Isikuandmed.Kodakondsuskood", required = true)
            protected String isikuandmedKodakondsuskood;
            @XmlElement(name = "Isikuandmed.Kodakondsustekstina", required = true)
            protected String isikuandmedKodakondsustekstina;
            @XmlElement(name = "Isikuandmed.EKodakondsuskood", required = true)
            protected String isikuandmedEKodakondsuskood;
            @XmlElement(name = "Isikuandmed.EKodakondsustekstina", required = true)
            protected String isikuandmedEKodakondsustekstina;
            @XmlElement(name = "Isikuandmed.AadressTekstina", required = true)
            protected String isikuandmedAadressTekstina;
            @XmlElement(name = "Isikuandmed.Sideaadress", required = true)
            protected String isikuandmedSideaadress;

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
             * Gets the value of the isikuandmedKodakondsuskood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedKodakondsuskood() {
                return isikuandmedKodakondsuskood;
            }

            /**
             * Sets the value of the isikuandmedKodakondsuskood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedKodakondsuskood(String value) {
                this.isikuandmedKodakondsuskood = value;
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
             * Gets the value of the isikuandmedEKodakondsuskood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedEKodakondsuskood() {
                return isikuandmedEKodakondsuskood;
            }

            /**
             * Sets the value of the isikuandmedEKodakondsuskood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedEKodakondsuskood(String value) {
                this.isikuandmedEKodakondsuskood = value;
            }

            /**
             * Gets the value of the isikuandmedEKodakondsustekstina property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedEKodakondsustekstina() {
                return isikuandmedEKodakondsustekstina;
            }

            /**
             * Sets the value of the isikuandmedEKodakondsustekstina property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedEKodakondsustekstina(String value) {
                this.isikuandmedEKodakondsustekstina = value;
            }

            /**
             * Gets the value of the isikuandmedAadressTekstina property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedAadressTekstina() {
                return isikuandmedAadressTekstina;
            }

            /**
             * Sets the value of the isikuandmedAadressTekstina property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedAadressTekstina(String value) {
                this.isikuandmedAadressTekstina = value;
            }

            /**
             * Gets the value of the isikuandmedSideaadress property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSideaadress() {
                return isikuandmedSideaadress;
            }

            /**
             * Sets the value of the isikuandmedSideaadress property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSideaadress(String value) {
                this.isikuandmedSideaadress = value;
            }

        }

    }

}
