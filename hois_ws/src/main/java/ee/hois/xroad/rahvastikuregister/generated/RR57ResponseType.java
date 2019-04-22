
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR57ResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR57ResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Isikud"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Isik" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Olek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Surmakuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Elukoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Maja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Korter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Elukohaalgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR57ResponseType", propOrder = {
    "isikud"
})
public class RR57ResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Isikud", required = true)
    protected RR57ResponseType.Isikud isikud;

    /**
     * Gets the value of the isikud property.
     * 
     * @return
     *     possible object is
     *     {@link RR57ResponseType.Isikud }
     *     
     */
    public RR57ResponseType.Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR57ResponseType.Isikud }
     *     
     */
    public void setIsikud(RR57ResponseType.Isikud value) {
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
     *         &lt;element name="Isik" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Olek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Surmakuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Elukoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Maja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Korter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Elukohaalgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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

        @XmlElement(name = "Isik")
        protected List<RR57ResponseType.Isikud.Isik> isik;

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
         * {@link RR57ResponseType.Isikud.Isik }
         * 
         * 
         */
        public List<RR57ResponseType.Isikud.Isik> getIsik() {
            if (isik == null) {
                isik = new ArrayList<RR57ResponseType.Isikud.Isik>();
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
         *         &lt;element name="Isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Olek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Surmakuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Elukoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Maja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Korter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Elukohaalgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "isikPerenimi",
            "isikEesnimi",
            "isikOlek",
            "isikSurmakuup",
            "isikRiik",
            "isikElukoht",
            "isikTanav",
            "isikMaja",
            "isikKorter",
            "isikElukohaalgus"
        })
        public static class Isik {

            @XmlElement(name = "Isik.Isikukood", required = true)
            protected String isikIsikukood;
            @XmlElement(name = "Isik.Perenimi", required = true)
            protected String isikPerenimi;
            @XmlElement(name = "Isik.Eesnimi", required = true)
            protected String isikEesnimi;
            @XmlElement(name = "Isik.Olek", required = true)
            protected String isikOlek;
            @XmlElement(name = "Isik.Surmakuup", required = true)
            protected String isikSurmakuup;
            @XmlElement(name = "Isik.Riik", required = true)
            protected String isikRiik;
            @XmlElement(name = "Isik.Elukoht", required = true)
            protected String isikElukoht;
            @XmlElement(name = "Isik.Tanav", required = true)
            protected String isikTanav;
            @XmlElement(name = "Isik.Maja", required = true)
            protected String isikMaja;
            @XmlElement(name = "Isik.Korter", required = true)
            protected String isikKorter;
            @XmlElement(name = "Isik.Elukohaalgus", required = true)
            protected String isikElukohaalgus;

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
             * Gets the value of the isikPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikPerenimi() {
                return isikPerenimi;
            }

            /**
             * Sets the value of the isikPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikPerenimi(String value) {
                this.isikPerenimi = value;
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
             * Gets the value of the isikOlek property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikOlek() {
                return isikOlek;
            }

            /**
             * Sets the value of the isikOlek property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikOlek(String value) {
                this.isikOlek = value;
            }

            /**
             * Gets the value of the isikSurmakuup property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSurmakuup() {
                return isikSurmakuup;
            }

            /**
             * Sets the value of the isikSurmakuup property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSurmakuup(String value) {
                this.isikSurmakuup = value;
            }

            /**
             * Gets the value of the isikRiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikRiik() {
                return isikRiik;
            }

            /**
             * Sets the value of the isikRiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikRiik(String value) {
                this.isikRiik = value;
            }

            /**
             * Gets the value of the isikElukoht property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikElukoht() {
                return isikElukoht;
            }

            /**
             * Sets the value of the isikElukoht property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikElukoht(String value) {
                this.isikElukoht = value;
            }

            /**
             * Gets the value of the isikTanav property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikTanav() {
                return isikTanav;
            }

            /**
             * Sets the value of the isikTanav property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikTanav(String value) {
                this.isikTanav = value;
            }

            /**
             * Gets the value of the isikMaja property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikMaja() {
                return isikMaja;
            }

            /**
             * Sets the value of the isikMaja property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikMaja(String value) {
                this.isikMaja = value;
            }

            /**
             * Gets the value of the isikKorter property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikKorter() {
                return isikKorter;
            }

            /**
             * Sets the value of the isikKorter property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikKorter(String value) {
                this.isikKorter = value;
            }

            /**
             * Gets the value of the isikElukohaalgus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikElukohaalgus() {
                return isikElukohaalgus;
            }

            /**
             * Sets the value of the isikElukohaalgus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikElukohaalgus(String value) {
                this.isikElukohaalgus = value;
            }

        }

    }

}
