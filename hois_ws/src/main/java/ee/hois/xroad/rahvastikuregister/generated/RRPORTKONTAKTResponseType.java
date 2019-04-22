
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRPORTKONTAKTResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRPORTKONTAKTResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Veatekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Kontaktid"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Kontakt" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Kontakt.Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Kontakt.Kontaktandmed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Kontakt.Alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Kontakt.Asutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RRPORTKONTAKTResponseType", propOrder = {
    "veatekst",
    "kontaktid"
})
public class RRPORTKONTAKTResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Veatekst")
    protected String veatekst;
    @XmlElement(name = "Kontaktid", required = true)
    protected RRPORTKONTAKTResponseType.Kontaktid kontaktid;

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
     * Gets the value of the kontaktid property.
     * 
     * @return
     *     possible object is
     *     {@link RRPORTKONTAKTResponseType.Kontaktid }
     *     
     */
    public RRPORTKONTAKTResponseType.Kontaktid getKontaktid() {
        return kontaktid;
    }

    /**
     * Sets the value of the kontaktid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRPORTKONTAKTResponseType.Kontaktid }
     *     
     */
    public void setKontaktid(RRPORTKONTAKTResponseType.Kontaktid value) {
        this.kontaktid = value;
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
     *         &lt;element name="Kontakt" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Kontakt.Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Kontakt.Kontaktandmed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Kontakt.Alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Kontakt.Asutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "kontakt"
    })
    public static class Kontaktid {

        @XmlElement(name = "Kontakt")
        protected List<RRPORTKONTAKTResponseType.Kontaktid.Kontakt> kontakt;

        /**
         * Gets the value of the kontakt property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the kontakt property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getKontakt().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RRPORTKONTAKTResponseType.Kontaktid.Kontakt }
         * 
         * 
         */
        public List<RRPORTKONTAKTResponseType.Kontaktid.Kontakt> getKontakt() {
            if (kontakt == null) {
                kontakt = new ArrayList<RRPORTKONTAKTResponseType.Kontaktid.Kontakt>();
            }
            return this.kontakt;
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
         *         &lt;element name="Kontakt.Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Kontakt.Kontaktandmed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Kontakt.Alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Kontakt.Asutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "kontaktLiik",
            "kontaktKontaktandmed",
            "kontaktAlates",
            "kontaktAsutus"
        })
        public static class Kontakt {

            @XmlElement(name = "Kontakt.Liik", required = true)
            protected String kontaktLiik;
            @XmlElement(name = "Kontakt.Kontaktandmed", required = true)
            protected String kontaktKontaktandmed;
            @XmlElement(name = "Kontakt.Alates", required = true)
            protected String kontaktAlates;
            @XmlElement(name = "Kontakt.Asutus", required = true)
            protected String kontaktAsutus;

            /**
             * Gets the value of the kontaktLiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktLiik() {
                return kontaktLiik;
            }

            /**
             * Sets the value of the kontaktLiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktLiik(String value) {
                this.kontaktLiik = value;
            }

            /**
             * Gets the value of the kontaktKontaktandmed property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktKontaktandmed() {
                return kontaktKontaktandmed;
            }

            /**
             * Sets the value of the kontaktKontaktandmed property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktKontaktandmed(String value) {
                this.kontaktKontaktandmed = value;
            }

            /**
             * Gets the value of the kontaktAlates property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktAlates() {
                return kontaktAlates;
            }

            /**
             * Sets the value of the kontaktAlates property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktAlates(String value) {
                this.kontaktAlates = value;
            }

            /**
             * Gets the value of the kontaktAsutus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktAsutus() {
                return kontaktAsutus;
            }

            /**
             * Sets the value of the kontaktAsutus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktAsutus(String value) {
                this.kontaktAsutus = value;
            }

        }

    }

}
