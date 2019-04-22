
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR408integratsioonResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR408integratsioonResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Veakood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="Veatekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikuid"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Isikud" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Isikud.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.IsikuOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Surmakuupaev" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Koda" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.EelKoda" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.EelKodakondsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Elukohatyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.ElukohaAadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud.Sideandmed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR408integratsioonResponseType", propOrder = {
    "veakood",
    "veatekst",
    "isikuid"
})
public class RR408IntegratsioonResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Veakood", required = true)
    protected BigInteger veakood;
    @XmlElement(name = "Veatekst", required = true)
    protected String veatekst;
    @XmlElement(name = "Isikuid", required = true)
    protected RR408IntegratsioonResponseType.Isikuid isikuid;

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
     * Gets the value of the isikuid property.
     * 
     * @return
     *     possible object is
     *     {@link RR408IntegratsioonResponseType.Isikuid }
     *     
     */
    public RR408IntegratsioonResponseType.Isikuid getIsikuid() {
        return isikuid;
    }

    /**
     * Sets the value of the isikuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR408IntegratsioonResponseType.Isikuid }
     *     
     */
    public void setIsikuid(RR408IntegratsioonResponseType.Isikuid value) {
        this.isikuid = value;
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
     *         &lt;element name="Isikud" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Isikud.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.IsikuOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Surmakuupaev" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Koda" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.EelKoda" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.EelKodakondsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Elukohatyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.ElukohaAadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud.Sideandmed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "isikud"
    })
    public static class Isikuid {

        @XmlElement(name = "Isikud")
        protected List<RR408IntegratsioonResponseType.Isikuid.Isikud> isikud;

        /**
         * Gets the value of the isikud property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the isikud property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIsikud().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR408IntegratsioonResponseType.Isikuid.Isikud }
         * 
         * 
         */
        public List<RR408IntegratsioonResponseType.Isikuid.Isikud> getIsikud() {
            if (isikud == null) {
                isikud = new ArrayList<RR408IntegratsioonResponseType.Isikuid.Isikud>();
            }
            return this.isikud;
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
         *         &lt;element name="Isikud.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.IsikuOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Surmakuupaev" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Koda" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.EelKoda" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.EelKodakondsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Elukohatyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.ElukohaAadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud.Sideandmed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "isikudIsikukood",
            "isikudEesnimi",
            "isikudPerenimi",
            "isikudIsikuOlek",
            "isikudSurmakuupaev",
            "isikudKoda",
            "isikudKodakondsus",
            "isikudEelKoda",
            "isikudEelKodakondsus",
            "isikudElukohatyyp",
            "isikudElukohaAadress",
            "isikudPostiindeks",
            "isikudSideandmed"
        })
        public static class Isikud {

            @XmlElement(name = "Isikud.Isikukood", required = true)
            protected String isikudIsikukood;
            @XmlElement(name = "Isikud.Eesnimi", required = true)
            protected String isikudEesnimi;
            @XmlElement(name = "Isikud.Perenimi", required = true)
            protected String isikudPerenimi;
            @XmlElement(name = "Isikud.IsikuOlek", required = true)
            protected String isikudIsikuOlek;
            @XmlElement(name = "Isikud.Surmakuupaev", required = true)
            protected String isikudSurmakuupaev;
            @XmlElement(name = "Isikud.Koda", required = true)
            protected String isikudKoda;
            @XmlElement(name = "Isikud.Kodakondsus", required = true)
            protected String isikudKodakondsus;
            @XmlElement(name = "Isikud.EelKoda", required = true)
            protected String isikudEelKoda;
            @XmlElement(name = "Isikud.EelKodakondsus", required = true)
            protected String isikudEelKodakondsus;
            @XmlElement(name = "Isikud.Elukohatyyp", required = true)
            protected String isikudElukohatyyp;
            @XmlElement(name = "Isikud.ElukohaAadress", required = true)
            protected String isikudElukohaAadress;
            @XmlElement(name = "Isikud.Postiindeks", required = true)
            protected String isikudPostiindeks;
            @XmlElement(name = "Isikud.Sideandmed", required = true)
            protected String isikudSideandmed;

            /**
             * Gets the value of the isikudIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudIsikukood() {
                return isikudIsikukood;
            }

            /**
             * Sets the value of the isikudIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudIsikukood(String value) {
                this.isikudIsikukood = value;
            }

            /**
             * Gets the value of the isikudEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudEesnimi() {
                return isikudEesnimi;
            }

            /**
             * Sets the value of the isikudEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudEesnimi(String value) {
                this.isikudEesnimi = value;
            }

            /**
             * Gets the value of the isikudPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudPerenimi() {
                return isikudPerenimi;
            }

            /**
             * Sets the value of the isikudPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudPerenimi(String value) {
                this.isikudPerenimi = value;
            }

            /**
             * Gets the value of the isikudIsikuOlek property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudIsikuOlek() {
                return isikudIsikuOlek;
            }

            /**
             * Sets the value of the isikudIsikuOlek property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudIsikuOlek(String value) {
                this.isikudIsikuOlek = value;
            }

            /**
             * Gets the value of the isikudSurmakuupaev property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudSurmakuupaev() {
                return isikudSurmakuupaev;
            }

            /**
             * Sets the value of the isikudSurmakuupaev property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudSurmakuupaev(String value) {
                this.isikudSurmakuupaev = value;
            }

            /**
             * Gets the value of the isikudKoda property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudKoda() {
                return isikudKoda;
            }

            /**
             * Sets the value of the isikudKoda property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudKoda(String value) {
                this.isikudKoda = value;
            }

            /**
             * Gets the value of the isikudKodakondsus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudKodakondsus() {
                return isikudKodakondsus;
            }

            /**
             * Sets the value of the isikudKodakondsus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudKodakondsus(String value) {
                this.isikudKodakondsus = value;
            }

            /**
             * Gets the value of the isikudEelKoda property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudEelKoda() {
                return isikudEelKoda;
            }

            /**
             * Sets the value of the isikudEelKoda property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudEelKoda(String value) {
                this.isikudEelKoda = value;
            }

            /**
             * Gets the value of the isikudEelKodakondsus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudEelKodakondsus() {
                return isikudEelKodakondsus;
            }

            /**
             * Sets the value of the isikudEelKodakondsus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudEelKodakondsus(String value) {
                this.isikudEelKodakondsus = value;
            }

            /**
             * Gets the value of the isikudElukohatyyp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudElukohatyyp() {
                return isikudElukohatyyp;
            }

            /**
             * Sets the value of the isikudElukohatyyp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudElukohatyyp(String value) {
                this.isikudElukohatyyp = value;
            }

            /**
             * Gets the value of the isikudElukohaAadress property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudElukohaAadress() {
                return isikudElukohaAadress;
            }

            /**
             * Sets the value of the isikudElukohaAadress property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudElukohaAadress(String value) {
                this.isikudElukohaAadress = value;
            }

            /**
             * Gets the value of the isikudPostiindeks property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudPostiindeks() {
                return isikudPostiindeks;
            }

            /**
             * Sets the value of the isikudPostiindeks property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudPostiindeks(String value) {
                this.isikudPostiindeks = value;
            }

            /**
             * Gets the value of the isikudSideandmed property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudSideandmed() {
                return isikudSideandmed;
            }

            /**
             * Sets the value of the isikudSideandmed property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudSideandmed(String value) {
                this.isikudSideandmed = value;
            }

        }

    }

}
