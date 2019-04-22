
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR42isikAadressKoodResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR42isikAadressKoodResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="veakood" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="veatekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isikud"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="isik" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isik.Perekonnanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isik.Isanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isik.Elukoha_aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="isik.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR42isikAadressKoodResponseType", propOrder = {
    "veakood",
    "veatekst",
    "isikud"
})
public class RR42IsikAadressKoodResponseType
    extends XRoadResponseBaseType
{

    protected BigInteger veakood;
    protected String veatekst;
    @XmlElement(required = true)
    protected RR42IsikAadressKoodResponseType.Isikud isikud;

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
     *     {@link RR42IsikAadressKoodResponseType.Isikud }
     *     
     */
    public RR42IsikAadressKoodResponseType.Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR42IsikAadressKoodResponseType.Isikud }
     *     
     */
    public void setIsikud(RR42IsikAadressKoodResponseType.Isikud value) {
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
     *         &lt;element name="isik" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.Perekonnanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.Isanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.Elukoha_aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="isik.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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

        protected List<RR42IsikAadressKoodResponseType.Isikud.Isik> isik;

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
         * {@link RR42IsikAadressKoodResponseType.Isikud.Isik }
         * 
         * 
         */
        public List<RR42IsikAadressKoodResponseType.Isikud.Isik> getIsik() {
            if (isik == null) {
                isik = new ArrayList<RR42IsikAadressKoodResponseType.Isikud.Isik>();
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
         *         &lt;element name="isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isik.Perekonnanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isik.Isanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isik.Elukoha_aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="isik.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "isikIsikukood",
            "isikPerekonnanimi",
            "isikEesnimi",
            "isikIsanimi",
            "isikElukohaAadress",
            "isikStaatus"
        })
        public static class Isik {

            @XmlElement(name = "isik.Isikukood", required = true)
            protected String isikIsikukood;
            @XmlElement(name = "isik.Perekonnanimi", required = true)
            protected String isikPerekonnanimi;
            @XmlElement(name = "isik.Eesnimi", required = true)
            protected String isikEesnimi;
            @XmlElement(name = "isik.Isanimi", required = true)
            protected String isikIsanimi;
            @XmlElement(name = "isik.Elukoha_aadress", required = true)
            protected String isikElukohaAadress;
            @XmlElement(name = "isik.Staatus", required = true)
            protected String isikStaatus;

            /**
             * Gets the value of the isikIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikIsikukood() {
                return isikIsikukood;
            }

            /**
             * Sets the value of the isikIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikIsikukood(String value) {
                this.isikIsikukood = value;
            }

            /**
             * Gets the value of the isikPerekonnanimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikPerekonnanimi() {
                return isikPerekonnanimi;
            }

            /**
             * Sets the value of the isikPerekonnanimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikPerekonnanimi(String value) {
                this.isikPerekonnanimi = value;
            }

            /**
             * Gets the value of the isikEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEesnimi() {
                return isikEesnimi;
            }

            /**
             * Sets the value of the isikEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEesnimi(String value) {
                this.isikEesnimi = value;
            }

            /**
             * Gets the value of the isikIsanimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikIsanimi() {
                return isikIsanimi;
            }

            /**
             * Sets the value of the isikIsanimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikIsanimi(String value) {
                this.isikIsanimi = value;
            }

            /**
             * Gets the value of the isikElukohaAadress property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikElukohaAadress() {
                return isikElukohaAadress;
            }

            /**
             * Sets the value of the isikElukohaAadress property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikElukohaAadress(String value) {
                this.isikElukohaAadress = value;
            }

            /**
             * Gets the value of the isikStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikStaatus() {
                return isikStaatus;
            }

            /**
             * Sets the value of the isikStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikStaatus(String value) {
                this.isikStaatus = value;
            }

        }

    }

}
