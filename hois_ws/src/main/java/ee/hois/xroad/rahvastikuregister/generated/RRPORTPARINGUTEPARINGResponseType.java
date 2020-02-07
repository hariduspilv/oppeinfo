
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRPORTPARINGUTE_PARINGResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRPORTPARINGUTE_PARINGResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Veatekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Paringud"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Paring" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Paring.Kuupaev" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Paring.Kellaaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Paring.Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Paring.Sisend" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Paring.TulemusteArv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Paring.Pohjus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Paring.Selgitus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RRPORTPARINGUTE_PARINGResponseType", propOrder = {
    "veatekst",
    "paringud"
})
public class RRPORTPARINGUTEPARINGResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Veatekst")
    protected String veatekst;
    @XmlElement(name = "Paringud", required = true)
    protected RRPORTPARINGUTEPARINGResponseType.Paringud paringud;

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
     * Gets the value of the paringud property.
     * 
     * @return
     *     possible object is
     *     {@link RRPORTPARINGUTEPARINGResponseType.Paringud }
     *     
     */
    public RRPORTPARINGUTEPARINGResponseType.Paringud getParingud() {
        return paringud;
    }

    /**
     * Sets the value of the paringud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRPORTPARINGUTEPARINGResponseType.Paringud }
     *     
     */
    public void setParingud(RRPORTPARINGUTEPARINGResponseType.Paringud value) {
        this.paringud = value;
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
     *         &lt;element name="Paring" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Paring.Kuupaev" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Paring.Kellaaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Paring.Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Paring.Sisend" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Paring.TulemusteArv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Paring.Pohjus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Paring.Selgitus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "paring"
    })
    public static class Paringud {

        @XmlElement(name = "Paring")
        protected List<RRPORTPARINGUTEPARINGResponseType.Paringud.Paring> paring;

        /**
         * Gets the value of the paring property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the paring property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getParing().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RRPORTPARINGUTEPARINGResponseType.Paringud.Paring }
         * 
         * 
         */
        public List<RRPORTPARINGUTEPARINGResponseType.Paringud.Paring> getParing() {
            if (paring == null) {
                paring = new ArrayList<RRPORTPARINGUTEPARINGResponseType.Paringud.Paring>();
            }
            return this.paring;
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
         *         &lt;element name="Paring.Kuupaev" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Paring.Kellaaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Paring.Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Paring.Sisend" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Paring.TulemusteArv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Paring.Pohjus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Paring.Selgitus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "paringKuupaev",
            "paringKellaaeg",
            "paringLiik",
            "paringSisend",
            "paringTulemusteArv",
            "paringPohjus",
            "paringSelgitus"
        })
        public static class Paring {

            @XmlElement(name = "Paring.Kuupaev", required = true)
            protected String paringKuupaev;
            @XmlElement(name = "Paring.Kellaaeg", required = true)
            protected String paringKellaaeg;
            @XmlElement(name = "Paring.Liik", required = true)
            protected String paringLiik;
            @XmlElement(name = "Paring.Sisend", required = true)
            protected String paringSisend;
            @XmlElement(name = "Paring.TulemusteArv", required = true)
            protected String paringTulemusteArv;
            @XmlElement(name = "Paring.Pohjus", required = true)
            protected String paringPohjus;
            @XmlElement(name = "Paring.Selgitus", required = true)
            protected String paringSelgitus;

            /**
             * Gets the value of the paringKuupaev property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getParingKuupaev() {
                return paringKuupaev;
            }

            /**
             * Sets the value of the paringKuupaev property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setParingKuupaev(String value) {
                this.paringKuupaev = value;
            }

            /**
             * Gets the value of the paringKellaaeg property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getParingKellaaeg() {
                return paringKellaaeg;
            }

            /**
             * Sets the value of the paringKellaaeg property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setParingKellaaeg(String value) {
                this.paringKellaaeg = value;
            }

            /**
             * Gets the value of the paringLiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getParingLiik() {
                return paringLiik;
            }

            /**
             * Sets the value of the paringLiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setParingLiik(String value) {
                this.paringLiik = value;
            }

            /**
             * Gets the value of the paringSisend property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getParingSisend() {
                return paringSisend;
            }

            /**
             * Sets the value of the paringSisend property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setParingSisend(String value) {
                this.paringSisend = value;
            }

            /**
             * Gets the value of the paringTulemusteArv property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getParingTulemusteArv() {
                return paringTulemusteArv;
            }

            /**
             * Sets the value of the paringTulemusteArv property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setParingTulemusteArv(String value) {
                this.paringTulemusteArv = value;
            }

            /**
             * Gets the value of the paringPohjus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getParingPohjus() {
                return paringPohjus;
            }

            /**
             * Sets the value of the paringPohjus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setParingPohjus(String value) {
                this.paringPohjus = value;
            }

            /**
             * Gets the value of the paringSelgitus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getParingSelgitus() {
                return paringSelgitus;
            }

            /**
             * Sets the value of the paringSelgitus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setParingSelgitus(String value) {
                this.paringSelgitus = value;
            }

        }

    }

}
