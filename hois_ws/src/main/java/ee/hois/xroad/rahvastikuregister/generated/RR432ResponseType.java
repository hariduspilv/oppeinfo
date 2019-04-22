
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR432ResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR432ResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Viga" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *                             &lt;element name="Isikuandmed.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.ElamisdokumendiLiigiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.ElamisdokumendiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.ElamisdokumendiKehtibAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.ElamisdokumendiKehtibKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR432ResponseType", propOrder = {
    "viga",
    "isikud"
})
public class RR432ResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Viga", required = true)
    protected String viga;
    @XmlElement(name = "Isikud", required = true)
    protected RR432ResponseType.Isikud isikud;

    /**
     * Gets the value of the viga property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViga() {
        return viga;
    }

    /**
     * Sets the value of the viga property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViga(String value) {
        this.viga = value;
    }

    /**
     * Gets the value of the isikud property.
     * 
     * @return
     *     possible object is
     *     {@link RR432ResponseType.Isikud }
     *     
     */
    public RR432ResponseType.Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR432ResponseType.Isikud }
     *     
     */
    public void setIsikud(RR432ResponseType.Isikud value) {
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
     *                   &lt;element name="Isikuandmed.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.ElamisdokumendiLiigiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.ElamisdokumendiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.ElamisdokumendiKehtibAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.ElamisdokumendiKehtibKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        protected List<RR432ResponseType.Isikud.Isikuandmed> isikuandmed;

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
         * {@link RR432ResponseType.Isikud.Isikuandmed }
         * 
         * 
         */
        public List<RR432ResponseType.Isikud.Isikuandmed> getIsikuandmed() {
            if (isikuandmed == null) {
                isikuandmed = new ArrayList<RR432ResponseType.Isikud.Isikuandmed>();
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
         *         &lt;element name="Isikuandmed.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.ElamisdokumendiLiigiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.ElamisdokumendiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.ElamisdokumendiKehtibAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.ElamisdokumendiKehtibKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "isikuandmedPerenimi",
            "isikuandmedEesnimi",
            "isikuandmedKodakondsus",
            "isikuandmedElamisdokumendiLiigiKood",
            "isikuandmedElamisdokumendiLiik",
            "isikuandmedElamisdokumendiKehtibAlates",
            "isikuandmedElamisdokumendiKehtibKuni"
        })
        public static class Isikuandmed {

            @XmlElement(name = "Isikuandmed.Isikukood", required = true)
            protected String isikuandmedIsikukood;
            @XmlElement(name = "Isikuandmed.Perenimi", required = true)
            protected String isikuandmedPerenimi;
            @XmlElement(name = "Isikuandmed.Eesnimi", required = true)
            protected String isikuandmedEesnimi;
            @XmlElement(name = "Isikuandmed.Kodakondsus", required = true)
            protected String isikuandmedKodakondsus;
            @XmlElement(name = "Isikuandmed.ElamisdokumendiLiigiKood", required = true)
            protected String isikuandmedElamisdokumendiLiigiKood;
            @XmlElement(name = "Isikuandmed.ElamisdokumendiLiik", required = true)
            protected String isikuandmedElamisdokumendiLiik;
            @XmlElement(name = "Isikuandmed.ElamisdokumendiKehtibAlates", required = true)
            protected String isikuandmedElamisdokumendiKehtibAlates;
            @XmlElement(name = "Isikuandmed.ElamisdokumendiKehtibKuni", required = true)
            protected String isikuandmedElamisdokumendiKehtibKuni;

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
             * Gets the value of the isikuandmedKodakondsus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedKodakondsus() {
                return isikuandmedKodakondsus;
            }

            /**
             * Sets the value of the isikuandmedKodakondsus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedKodakondsus(String value) {
                this.isikuandmedKodakondsus = value;
            }

            /**
             * Gets the value of the isikuandmedElamisdokumendiLiigiKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedElamisdokumendiLiigiKood() {
                return isikuandmedElamisdokumendiLiigiKood;
            }

            /**
             * Sets the value of the isikuandmedElamisdokumendiLiigiKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedElamisdokumendiLiigiKood(String value) {
                this.isikuandmedElamisdokumendiLiigiKood = value;
            }

            /**
             * Gets the value of the isikuandmedElamisdokumendiLiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedElamisdokumendiLiik() {
                return isikuandmedElamisdokumendiLiik;
            }

            /**
             * Sets the value of the isikuandmedElamisdokumendiLiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedElamisdokumendiLiik(String value) {
                this.isikuandmedElamisdokumendiLiik = value;
            }

            /**
             * Gets the value of the isikuandmedElamisdokumendiKehtibAlates property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedElamisdokumendiKehtibAlates() {
                return isikuandmedElamisdokumendiKehtibAlates;
            }

            /**
             * Sets the value of the isikuandmedElamisdokumendiKehtibAlates property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedElamisdokumendiKehtibAlates(String value) {
                this.isikuandmedElamisdokumendiKehtibAlates = value;
            }

            /**
             * Gets the value of the isikuandmedElamisdokumendiKehtibKuni property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedElamisdokumendiKehtibKuni() {
                return isikuandmedElamisdokumendiKehtibKuni;
            }

            /**
             * Sets the value of the isikuandmedElamisdokumendiKehtibKuni property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedElamisdokumendiKehtibKuni(String value) {
                this.isikuandmedElamisdokumendiKehtibKuni = value;
            }

        }

    }

}
