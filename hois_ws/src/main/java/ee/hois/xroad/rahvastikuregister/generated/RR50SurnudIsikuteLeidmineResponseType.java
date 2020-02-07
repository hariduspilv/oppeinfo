
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR50SurnudIsikuteLeidmineResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR50SurnudIsikuteLeidmineResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="surnu"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="item" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="surnu.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="surnu.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="surnu.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="surnu.Surmakuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="surnu.sdliik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="surnu.sdnumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="surnu.sdkuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="surnu.sdasutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="surnu.kmadliik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="surnu.kmadnumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="surnu.kmadkuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="surnu.kmadasutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR50SurnudIsikuteLeidmineResponseType", propOrder = {
    "surnu"
})
public class RR50SurnudIsikuteLeidmineResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(required = true)
    protected RR50SurnudIsikuteLeidmineResponseType.Surnu surnu;

    /**
     * Gets the value of the surnu property.
     * 
     * @return
     *     possible object is
     *     {@link RR50SurnudIsikuteLeidmineResponseType.Surnu }
     *     
     */
    public RR50SurnudIsikuteLeidmineResponseType.Surnu getSurnu() {
        return surnu;
    }

    /**
     * Sets the value of the surnu property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR50SurnudIsikuteLeidmineResponseType.Surnu }
     *     
     */
    public void setSurnu(RR50SurnudIsikuteLeidmineResponseType.Surnu value) {
        this.surnu = value;
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
     *         &lt;element name="item" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="surnu.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="surnu.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="surnu.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="surnu.Surmakuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="surnu.sdliik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="surnu.sdnumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="surnu.sdkuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="surnu.sdasutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="surnu.kmadliik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="surnu.kmadnumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="surnu.kmadkuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="surnu.kmadasutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "item"
    })
    public static class Surnu {

        protected List<RR50SurnudIsikuteLeidmineResponseType.Surnu.Item> item;

        /**
         * Gets the value of the item property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the item property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR50SurnudIsikuteLeidmineResponseType.Surnu.Item }
         * 
         * 
         */
        public List<RR50SurnudIsikuteLeidmineResponseType.Surnu.Item> getItem() {
            if (item == null) {
                item = new ArrayList<RR50SurnudIsikuteLeidmineResponseType.Surnu.Item>();
            }
            return this.item;
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
         *         &lt;element name="surnu.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="surnu.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="surnu.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="surnu.Surmakuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="surnu.sdliik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="surnu.sdnumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="surnu.sdkuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="surnu.sdasutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="surnu.kmadliik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="surnu.kmadnumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="surnu.kmadkuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="surnu.kmadasutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "surnuIsikukood",
            "surnuPerenimi",
            "surnuEesnimi",
            "surnuSurmakuup",
            "surnuSdliik",
            "surnuSdnumber",
            "surnuSdkuup",
            "surnuSdasutus",
            "surnuKmadliik",
            "surnuKmadnumber",
            "surnuKmadkuup",
            "surnuKmadasutus"
        })
        public static class Item {

            @XmlElement(name = "surnu.Isikukood", required = true)
            protected String surnuIsikukood;
            @XmlElement(name = "surnu.Perenimi", required = true)
            protected String surnuPerenimi;
            @XmlElement(name = "surnu.Eesnimi", required = true)
            protected String surnuEesnimi;
            @XmlElement(name = "surnu.Surmakuup", required = true)
            protected String surnuSurmakuup;
            @XmlElement(name = "surnu.sdliik", required = true)
            protected String surnuSdliik;
            @XmlElement(name = "surnu.sdnumber", required = true)
            protected String surnuSdnumber;
            @XmlElement(name = "surnu.sdkuup", required = true)
            protected String surnuSdkuup;
            @XmlElement(name = "surnu.sdasutus", required = true)
            protected String surnuSdasutus;
            @XmlElement(name = "surnu.kmadliik", required = true)
            protected String surnuKmadliik;
            @XmlElement(name = "surnu.kmadnumber", required = true)
            protected String surnuKmadnumber;
            @XmlElement(name = "surnu.kmadkuup", required = true)
            protected String surnuKmadkuup;
            @XmlElement(name = "surnu.kmadasutus", required = true)
            protected String surnuKmadasutus;

            /**
             * Gets the value of the surnuIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSurnuIsikukood() {
                return surnuIsikukood;
            }

            /**
             * Sets the value of the surnuIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSurnuIsikukood(String value) {
                this.surnuIsikukood = value;
            }

            /**
             * Gets the value of the surnuPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSurnuPerenimi() {
                return surnuPerenimi;
            }

            /**
             * Sets the value of the surnuPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSurnuPerenimi(String value) {
                this.surnuPerenimi = value;
            }

            /**
             * Gets the value of the surnuEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSurnuEesnimi() {
                return surnuEesnimi;
            }

            /**
             * Sets the value of the surnuEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSurnuEesnimi(String value) {
                this.surnuEesnimi = value;
            }

            /**
             * Gets the value of the surnuSurmakuup property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSurnuSurmakuup() {
                return surnuSurmakuup;
            }

            /**
             * Sets the value of the surnuSurmakuup property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSurnuSurmakuup(String value) {
                this.surnuSurmakuup = value;
            }

            /**
             * Gets the value of the surnuSdliik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSurnuSdliik() {
                return surnuSdliik;
            }

            /**
             * Sets the value of the surnuSdliik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSurnuSdliik(String value) {
                this.surnuSdliik = value;
            }

            /**
             * Gets the value of the surnuSdnumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSurnuSdnumber() {
                return surnuSdnumber;
            }

            /**
             * Sets the value of the surnuSdnumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSurnuSdnumber(String value) {
                this.surnuSdnumber = value;
            }

            /**
             * Gets the value of the surnuSdkuup property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSurnuSdkuup() {
                return surnuSdkuup;
            }

            /**
             * Sets the value of the surnuSdkuup property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSurnuSdkuup(String value) {
                this.surnuSdkuup = value;
            }

            /**
             * Gets the value of the surnuSdasutus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSurnuSdasutus() {
                return surnuSdasutus;
            }

            /**
             * Sets the value of the surnuSdasutus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSurnuSdasutus(String value) {
                this.surnuSdasutus = value;
            }

            /**
             * Gets the value of the surnuKmadliik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSurnuKmadliik() {
                return surnuKmadliik;
            }

            /**
             * Sets the value of the surnuKmadliik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSurnuKmadliik(String value) {
                this.surnuKmadliik = value;
            }

            /**
             * Gets the value of the surnuKmadnumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSurnuKmadnumber() {
                return surnuKmadnumber;
            }

            /**
             * Sets the value of the surnuKmadnumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSurnuKmadnumber(String value) {
                this.surnuKmadnumber = value;
            }

            /**
             * Gets the value of the surnuKmadkuup property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSurnuKmadkuup() {
                return surnuKmadkuup;
            }

            /**
             * Sets the value of the surnuKmadkuup property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSurnuKmadkuup(String value) {
                this.surnuKmadkuup = value;
            }

            /**
             * Gets the value of the surnuKmadasutus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSurnuKmadasutus() {
                return surnuKmadasutus;
            }

            /**
             * Sets the value of the surnuKmadasutus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSurnuKmadasutus(String value) {
                this.surnuKmadasutus = value;
            }

        }

    }

}
