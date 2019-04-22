
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR84IsikuSeosedResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR84IsikuSeosedResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="XTeeVeaTeade" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsikuEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuSugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuSynniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuElukoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Veakood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="Veatekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *                             &lt;element name="Suhe.Suhtetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.sIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.sPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.sEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.sIsikuElukoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.sIsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.SuhStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR84IsikuSeosedResponseType", propOrder = {
    "xTeeVeaTeade",
    "isikuEesnimi",
    "isikuPerenimi",
    "isikuSugu",
    "isikuSynniaeg",
    "isikuStaatus",
    "isikuElukoht",
    "veakood",
    "veatekst",
    "suhted"
})
public class RR84IsikuSeosedResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "XTeeVeaTeade")
    protected String xTeeVeaTeade;
    @XmlElement(name = "IsikuEesnimi", required = true)
    protected String isikuEesnimi;
    @XmlElement(name = "IsikuPerenimi", required = true)
    protected String isikuPerenimi;
    @XmlElement(name = "IsikuSugu", required = true)
    protected String isikuSugu;
    @XmlElement(name = "IsikuSynniaeg", required = true)
    protected String isikuSynniaeg;
    @XmlElement(name = "IsikuStaatus", required = true)
    protected String isikuStaatus;
    @XmlElement(name = "IsikuElukoht", required = true)
    protected String isikuElukoht;
    @XmlElement(name = "Veakood", required = true)
    protected BigInteger veakood;
    @XmlElement(name = "Veatekst", required = true)
    protected String veatekst;
    @XmlElement(name = "Suhted", required = true)
    protected RR84IsikuSeosedResponseType.Suhted suhted;

    /**
     * Gets the value of the xTeeVeaTeade property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXTeeVeaTeade() {
        return xTeeVeaTeade;
    }

    /**
     * Sets the value of the xTeeVeaTeade property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXTeeVeaTeade(String value) {
        this.xTeeVeaTeade = value;
    }

    /**
     * Gets the value of the isikuEesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuEesnimi() {
        return isikuEesnimi;
    }

    /**
     * Sets the value of the isikuEesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuEesnimi(String value) {
        this.isikuEesnimi = value;
    }

    /**
     * Gets the value of the isikuPerenimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuPerenimi() {
        return isikuPerenimi;
    }

    /**
     * Sets the value of the isikuPerenimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuPerenimi(String value) {
        this.isikuPerenimi = value;
    }

    /**
     * Gets the value of the isikuSugu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuSugu() {
        return isikuSugu;
    }

    /**
     * Sets the value of the isikuSugu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuSugu(String value) {
        this.isikuSugu = value;
    }

    /**
     * Gets the value of the isikuSynniaeg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuSynniaeg() {
        return isikuSynniaeg;
    }

    /**
     * Sets the value of the isikuSynniaeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuSynniaeg(String value) {
        this.isikuSynniaeg = value;
    }

    /**
     * Gets the value of the isikuStaatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuStaatus() {
        return isikuStaatus;
    }

    /**
     * Sets the value of the isikuStaatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuStaatus(String value) {
        this.isikuStaatus = value;
    }

    /**
     * Gets the value of the isikuElukoht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuElukoht() {
        return isikuElukoht;
    }

    /**
     * Sets the value of the isikuElukoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuElukoht(String value) {
        this.isikuElukoht = value;
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
     * Gets the value of the suhted property.
     * 
     * @return
     *     possible object is
     *     {@link RR84IsikuSeosedResponseType.Suhted }
     *     
     */
    public RR84IsikuSeosedResponseType.Suhted getSuhted() {
        return suhted;
    }

    /**
     * Sets the value of the suhted property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR84IsikuSeosedResponseType.Suhted }
     *     
     */
    public void setSuhted(RR84IsikuSeosedResponseType.Suhted value) {
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
     *                   &lt;element name="Suhe.Suhtetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.sIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.sPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.sEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.sIsikuElukoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.sIsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.SuhStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        protected List<RR84IsikuSeosedResponseType.Suhted.Suhe> suhe;

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
         * {@link RR84IsikuSeosedResponseType.Suhted.Suhe }
         * 
         * 
         */
        public List<RR84IsikuSeosedResponseType.Suhted.Suhe> getSuhe() {
            if (suhe == null) {
                suhe = new ArrayList<RR84IsikuSeosedResponseType.Suhted.Suhe>();
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
         *         &lt;element name="Suhe.Suhtetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.sIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.sPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.sEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.sIsikuElukoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.sIsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.SuhStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "suheSuhtetyyp",
            "suheSIsikukood",
            "suheSPerenimi",
            "suheSEesnimi",
            "suheSIsikuElukoht",
            "suheSIsikuStaatus",
            "suheSuhStaatus"
        })
        public static class Suhe {

            @XmlElement(name = "Suhe.Suhtetyyp", required = true)
            protected String suheSuhtetyyp;
            @XmlElement(name = "Suhe.sIsikukood", required = true)
            protected String suheSIsikukood;
            @XmlElement(name = "Suhe.sPerenimi", required = true)
            protected String suheSPerenimi;
            @XmlElement(name = "Suhe.sEesnimi", required = true)
            protected String suheSEesnimi;
            @XmlElement(name = "Suhe.sIsikuElukoht", required = true)
            protected String suheSIsikuElukoht;
            @XmlElement(name = "Suhe.sIsikuStaatus", required = true)
            protected String suheSIsikuStaatus;
            @XmlElement(name = "Suhe.SuhStaatus", required = true)
            protected String suheSuhStaatus;

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
             * Gets the value of the suheSIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheSIsikukood() {
                return suheSIsikukood;
            }

            /**
             * Sets the value of the suheSIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheSIsikukood(String value) {
                this.suheSIsikukood = value;
            }

            /**
             * Gets the value of the suheSPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheSPerenimi() {
                return suheSPerenimi;
            }

            /**
             * Sets the value of the suheSPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheSPerenimi(String value) {
                this.suheSPerenimi = value;
            }

            /**
             * Gets the value of the suheSEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheSEesnimi() {
                return suheSEesnimi;
            }

            /**
             * Sets the value of the suheSEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheSEesnimi(String value) {
                this.suheSEesnimi = value;
            }

            /**
             * Gets the value of the suheSIsikuElukoht property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheSIsikuElukoht() {
                return suheSIsikuElukoht;
            }

            /**
             * Sets the value of the suheSIsikuElukoht property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheSIsikuElukoht(String value) {
                this.suheSIsikuElukoht = value;
            }

            /**
             * Gets the value of the suheSIsikuStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheSIsikuStaatus() {
                return suheSIsikuStaatus;
            }

            /**
             * Sets the value of the suheSIsikuStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheSIsikuStaatus(String value) {
                this.suheSIsikuStaatus = value;
            }

            /**
             * Gets the value of the suheSuhStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheSuhStaatus() {
                return suheSuhStaatus;
            }

            /**
             * Sets the value of the suheSuhStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheSuhStaatus(String value) {
                this.suheSuhStaatus = value;
            }

        }

    }

}
