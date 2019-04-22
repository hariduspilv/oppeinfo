
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRPORTHOOLDUSOIGUSResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRPORTHOOLDUSOIGUSResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="veatekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Hooldusoigused"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Hooldusoigus" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Hooldusoigused.Teineisikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused.Teinesynniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused.TeinePerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused.TeineEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused.Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused.Algus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused.Lopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused.Olek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused.Sisu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RRPORTHOOLDUSOIGUSResponseType", propOrder = {
    "veatekst",
    "hooldusoigused"
})
public class RRPORTHOOLDUSOIGUSResponseType
    extends XRoadResponseBaseType
{

    protected String veatekst;
    @XmlElement(name = "Hooldusoigused", required = true)
    protected RRPORTHOOLDUSOIGUSResponseType.Hooldusoigused hooldusoigused;

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
     * Gets the value of the hooldusoigused property.
     * 
     * @return
     *     possible object is
     *     {@link RRPORTHOOLDUSOIGUSResponseType.Hooldusoigused }
     *     
     */
    public RRPORTHOOLDUSOIGUSResponseType.Hooldusoigused getHooldusoigused() {
        return hooldusoigused;
    }

    /**
     * Sets the value of the hooldusoigused property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRPORTHOOLDUSOIGUSResponseType.Hooldusoigused }
     *     
     */
    public void setHooldusoigused(RRPORTHOOLDUSOIGUSResponseType.Hooldusoigused value) {
        this.hooldusoigused = value;
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
     *         &lt;element name="Hooldusoigus" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Hooldusoigused.Teineisikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused.Teinesynniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused.TeinePerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused.TeineEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused.Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused.Algus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused.Lopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused.Olek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused.Sisu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "hooldusoigus"
    })
    public static class Hooldusoigused {

        @XmlElement(name = "Hooldusoigus")
        protected List<RRPORTHOOLDUSOIGUSResponseType.Hooldusoigused.Hooldusoigus> hooldusoigus;

        /**
         * Gets the value of the hooldusoigus property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the hooldusoigus property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHooldusoigus().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RRPORTHOOLDUSOIGUSResponseType.Hooldusoigused.Hooldusoigus }
         * 
         * 
         */
        public List<RRPORTHOOLDUSOIGUSResponseType.Hooldusoigused.Hooldusoigus> getHooldusoigus() {
            if (hooldusoigus == null) {
                hooldusoigus = new ArrayList<RRPORTHOOLDUSOIGUSResponseType.Hooldusoigused.Hooldusoigus>();
            }
            return this.hooldusoigus;
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
         *         &lt;element name="Hooldusoigused.Teineisikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigused.Teinesynniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigused.TeinePerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigused.TeineEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigused.Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigused.Algus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigused.Lopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigused.Olek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigused.Sisu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "hooldusoigusedTeineisikukood",
            "hooldusoigusedTeinesynniaeg",
            "hooldusoigusedTeinePerenimi",
            "hooldusoigusedTeineEesnimi",
            "hooldusoigusedLiik",
            "hooldusoigusedAlgus",
            "hooldusoigusedLopp",
            "hooldusoigusedOlek",
            "hooldusoigusedSisu"
        })
        public static class Hooldusoigus {

            @XmlElement(name = "Hooldusoigused.Teineisikukood", required = true)
            protected String hooldusoigusedTeineisikukood;
            @XmlElement(name = "Hooldusoigused.Teinesynniaeg", required = true)
            protected String hooldusoigusedTeinesynniaeg;
            @XmlElement(name = "Hooldusoigused.TeinePerenimi", required = true)
            protected String hooldusoigusedTeinePerenimi;
            @XmlElement(name = "Hooldusoigused.TeineEesnimi", required = true)
            protected String hooldusoigusedTeineEesnimi;
            @XmlElement(name = "Hooldusoigused.Liik", required = true)
            protected String hooldusoigusedLiik;
            @XmlElement(name = "Hooldusoigused.Algus", required = true)
            protected String hooldusoigusedAlgus;
            @XmlElement(name = "Hooldusoigused.Lopp", required = true)
            protected String hooldusoigusedLopp;
            @XmlElement(name = "Hooldusoigused.Olek", required = true)
            protected String hooldusoigusedOlek;
            @XmlElement(name = "Hooldusoigused.Sisu", required = true)
            protected String hooldusoigusedSisu;

            /**
             * Gets the value of the hooldusoigusedTeineisikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusedTeineisikukood() {
                return hooldusoigusedTeineisikukood;
            }

            /**
             * Sets the value of the hooldusoigusedTeineisikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusedTeineisikukood(String value) {
                this.hooldusoigusedTeineisikukood = value;
            }

            /**
             * Gets the value of the hooldusoigusedTeinesynniaeg property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusedTeinesynniaeg() {
                return hooldusoigusedTeinesynniaeg;
            }

            /**
             * Sets the value of the hooldusoigusedTeinesynniaeg property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusedTeinesynniaeg(String value) {
                this.hooldusoigusedTeinesynniaeg = value;
            }

            /**
             * Gets the value of the hooldusoigusedTeinePerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusedTeinePerenimi() {
                return hooldusoigusedTeinePerenimi;
            }

            /**
             * Sets the value of the hooldusoigusedTeinePerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusedTeinePerenimi(String value) {
                this.hooldusoigusedTeinePerenimi = value;
            }

            /**
             * Gets the value of the hooldusoigusedTeineEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusedTeineEesnimi() {
                return hooldusoigusedTeineEesnimi;
            }

            /**
             * Sets the value of the hooldusoigusedTeineEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusedTeineEesnimi(String value) {
                this.hooldusoigusedTeineEesnimi = value;
            }

            /**
             * Gets the value of the hooldusoigusedLiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusedLiik() {
                return hooldusoigusedLiik;
            }

            /**
             * Sets the value of the hooldusoigusedLiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusedLiik(String value) {
                this.hooldusoigusedLiik = value;
            }

            /**
             * Gets the value of the hooldusoigusedAlgus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusedAlgus() {
                return hooldusoigusedAlgus;
            }

            /**
             * Sets the value of the hooldusoigusedAlgus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusedAlgus(String value) {
                this.hooldusoigusedAlgus = value;
            }

            /**
             * Gets the value of the hooldusoigusedLopp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusedLopp() {
                return hooldusoigusedLopp;
            }

            /**
             * Sets the value of the hooldusoigusedLopp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusedLopp(String value) {
                this.hooldusoigusedLopp = value;
            }

            /**
             * Gets the value of the hooldusoigusedOlek property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusedOlek() {
                return hooldusoigusedOlek;
            }

            /**
             * Sets the value of the hooldusoigusedOlek property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusedOlek(String value) {
                this.hooldusoigusedOlek = value;
            }

            /**
             * Gets the value of the hooldusoigusedSisu property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusedSisu() {
                return hooldusoigusedSisu;
            }

            /**
             * Sets the value of the hooldusoigusedSisu property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusedSisu(String value) {
                this.hooldusoigusedSisu = value;
            }

        }

    }

}
