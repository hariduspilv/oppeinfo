
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRAVRResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRAVRResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Veakood" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="Veatekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Abikaasad" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Abikaasa" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Abikaasa.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Abikaasa.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Abikaasa.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Abikaasa.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Abikaasa.Kodakondsused" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Kodakondsus" type="{http://rr.x-road.eu/producer}Kodak" maxOccurs="unbounded" minOccurs="0"/&gt;
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
 *                   &lt;element name="Muudatus.Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Muudatus.JoustumiseKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RRAVRResponseType", propOrder = {
    "veakood",
    "veatekst",
    "abikaasad"
})
public class RRAVRResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Veakood")
    protected Integer veakood;
    @XmlElement(name = "Veatekst")
    protected String veatekst;
    @XmlElement(name = "Abikaasad")
    protected List<RRAVRResponseType.Abikaasad> abikaasad;

    /**
     * Gets the value of the veakood property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVeakood() {
        return veakood;
    }

    /**
     * Sets the value of the veakood property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVeakood(Integer value) {
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
     * Gets the value of the abikaasad property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the abikaasad property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAbikaasad().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RRAVRResponseType.Abikaasad }
     * 
     * 
     */
    public List<RRAVRResponseType.Abikaasad> getAbikaasad() {
        if (abikaasad == null) {
            abikaasad = new ArrayList<RRAVRResponseType.Abikaasad>();
        }
        return this.abikaasad;
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
     *         &lt;element name="Abikaasa" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Abikaasa.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Abikaasa.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Abikaasa.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Abikaasa.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Abikaasa.Kodakondsused" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Kodakondsus" type="{http://rr.x-road.eu/producer}Kodak" maxOccurs="unbounded" minOccurs="0"/&gt;
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
     *         &lt;element name="Muudatus.Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Muudatus.JoustumiseKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "abikaasa",
        "muudatusLiik",
        "muudatusJoustumiseKpv"
    })
    public static class Abikaasad {

        @XmlElement(name = "Abikaasa", required = true)
        protected List<RRAVRResponseType.Abikaasad.Abikaasa> abikaasa;
        @XmlElement(name = "Muudatus.Liik", required = true)
        protected String muudatusLiik;
        @XmlElement(name = "Muudatus.JoustumiseKpv", required = true)
        protected String muudatusJoustumiseKpv;

        /**
         * Gets the value of the abikaasa property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the abikaasa property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAbikaasa().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RRAVRResponseType.Abikaasad.Abikaasa }
         * 
         * 
         */
        public List<RRAVRResponseType.Abikaasad.Abikaasa> getAbikaasa() {
            if (abikaasa == null) {
                abikaasa = new ArrayList<RRAVRResponseType.Abikaasad.Abikaasa>();
            }
            return this.abikaasa;
        }

        /**
         * Gets the value of the muudatusLiik property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMuudatusLiik() {
            return muudatusLiik;
        }

        /**
         * Sets the value of the muudatusLiik property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMuudatusLiik(String value) {
            this.muudatusLiik = value;
        }

        /**
         * Gets the value of the muudatusJoustumiseKpv property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMuudatusJoustumiseKpv() {
            return muudatusJoustumiseKpv;
        }

        /**
         * Sets the value of the muudatusJoustumiseKpv property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMuudatusJoustumiseKpv(String value) {
            this.muudatusJoustumiseKpv = value;
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
         *         &lt;element name="Abikaasa.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Abikaasa.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Abikaasa.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Abikaasa.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Abikaasa.Kodakondsused" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Kodakondsus" type="{http://rr.x-road.eu/producer}Kodak" maxOccurs="unbounded" minOccurs="0"/&gt;
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
            "abikaasaPerenimi",
            "abikaasaEesnimi",
            "abikaasaIsikukood",
            "abikaasaSynniaeg",
            "abikaasaKodakondsused"
        })
        public static class Abikaasa {

            @XmlElement(name = "Abikaasa.Perenimi", required = true)
            protected String abikaasaPerenimi;
            @XmlElement(name = "Abikaasa.Eesnimi", required = true)
            protected String abikaasaEesnimi;
            @XmlElement(name = "Abikaasa.Isikukood", required = true)
            protected String abikaasaIsikukood;
            @XmlElement(name = "Abikaasa.Synniaeg", required = true)
            protected String abikaasaSynniaeg;
            @XmlElement(name = "Abikaasa.Kodakondsused")
            protected RRAVRResponseType.Abikaasad.Abikaasa.AbikaasaKodakondsused abikaasaKodakondsused;

            /**
             * Gets the value of the abikaasaPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAbikaasaPerenimi() {
                return abikaasaPerenimi;
            }

            /**
             * Sets the value of the abikaasaPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAbikaasaPerenimi(String value) {
                this.abikaasaPerenimi = value;
            }

            /**
             * Gets the value of the abikaasaEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAbikaasaEesnimi() {
                return abikaasaEesnimi;
            }

            /**
             * Sets the value of the abikaasaEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAbikaasaEesnimi(String value) {
                this.abikaasaEesnimi = value;
            }

            /**
             * Gets the value of the abikaasaIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAbikaasaIsikukood() {
                return abikaasaIsikukood;
            }

            /**
             * Sets the value of the abikaasaIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAbikaasaIsikukood(String value) {
                this.abikaasaIsikukood = value;
            }

            /**
             * Gets the value of the abikaasaSynniaeg property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAbikaasaSynniaeg() {
                return abikaasaSynniaeg;
            }

            /**
             * Sets the value of the abikaasaSynniaeg property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAbikaasaSynniaeg(String value) {
                this.abikaasaSynniaeg = value;
            }

            /**
             * Gets the value of the abikaasaKodakondsused property.
             * 
             * @return
             *     possible object is
             *     {@link RRAVRResponseType.Abikaasad.Abikaasa.AbikaasaKodakondsused }
             *     
             */
            public RRAVRResponseType.Abikaasad.Abikaasa.AbikaasaKodakondsused getAbikaasaKodakondsused() {
                return abikaasaKodakondsused;
            }

            /**
             * Sets the value of the abikaasaKodakondsused property.
             * 
             * @param value
             *     allowed object is
             *     {@link RRAVRResponseType.Abikaasad.Abikaasa.AbikaasaKodakondsused }
             *     
             */
            public void setAbikaasaKodakondsused(RRAVRResponseType.Abikaasad.Abikaasa.AbikaasaKodakondsused value) {
                this.abikaasaKodakondsused = value;
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
             *         &lt;element name="Kodakondsus" type="{http://rr.x-road.eu/producer}Kodak" maxOccurs="unbounded" minOccurs="0"/&gt;
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
                "kodakondsus"
            })
            public static class AbikaasaKodakondsused {

                @XmlElement(name = "Kodakondsus")
                protected List<Kodak> kodakondsus;

                /**
                 * Gets the value of the kodakondsus property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the kodakondsus property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getKodakondsus().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Kodak }
                 * 
                 * 
                 */
                public List<Kodak> getKodakondsus() {
                    if (kodakondsus == null) {
                        kodakondsus = new ArrayList<Kodak>();
                    }
                    return this.kodakondsus;
                }

            }

        }

    }

}
