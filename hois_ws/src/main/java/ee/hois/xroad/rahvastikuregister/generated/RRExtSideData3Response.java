
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRExtSideData3Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRExtSideData3Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Isik"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="vead"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="viga" maxOccurs="unbounded"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="tekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RRExtSideData3Response", propOrder = {
    "isik"
})
public class RRExtSideData3Response {

    @XmlElement(name = "Isik", required = true)
    protected RRExtSideData3Response.Isik isik;

    /**
     * Gets the value of the isik property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtSideData3Response.Isik }
     *     
     */
    public RRExtSideData3Response.Isik getIsik() {
        return isik;
    }

    /**
     * Sets the value of the isik property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtSideData3Response.Isik }
     *     
     */
    public void setIsik(RRExtSideData3Response.Isik value) {
        this.isik = value;
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
     *         &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="vead"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="viga" maxOccurs="unbounded"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="tekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "isikukood",
        "eesnimi",
        "perenimi",
        "vead"
    })
    public static class Isik {

        @XmlElement(name = "Isikukood", required = true)
        protected String isikukood;
        @XmlElement(name = "Eesnimi", required = true)
        protected String eesnimi;
        @XmlElement(name = "Perenimi", required = true)
        protected String perenimi;
        @XmlElement(required = true)
        protected RRExtSideData3Response.Isik.Vead vead;

        /**
         * Gets the value of the isikukood property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIsikukood() {
            return isikukood;
        }

        /**
         * Sets the value of the isikukood property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsikukood(String value) {
            this.isikukood = value;
        }

        /**
         * Gets the value of the eesnimi property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEesnimi() {
            return eesnimi;
        }

        /**
         * Sets the value of the eesnimi property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEesnimi(String value) {
            this.eesnimi = value;
        }

        /**
         * Gets the value of the perenimi property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPerenimi() {
            return perenimi;
        }

        /**
         * Sets the value of the perenimi property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPerenimi(String value) {
            this.perenimi = value;
        }

        /**
         * Gets the value of the vead property.
         * 
         * @return
         *     possible object is
         *     {@link RRExtSideData3Response.Isik.Vead }
         *     
         */
        public RRExtSideData3Response.Isik.Vead getVead() {
            return vead;
        }

        /**
         * Sets the value of the vead property.
         * 
         * @param value
         *     allowed object is
         *     {@link RRExtSideData3Response.Isik.Vead }
         *     
         */
        public void setVead(RRExtSideData3Response.Isik.Vead value) {
            this.vead = value;
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
         *         &lt;element name="viga" maxOccurs="unbounded"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="tekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "viga"
        })
        public static class Vead {

            @XmlElement(required = true)
            protected List<RRExtSideData3Response.Isik.Vead.Viga> viga;

            /**
             * Gets the value of the viga property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the viga property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getViga().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link RRExtSideData3Response.Isik.Vead.Viga }
             * 
             * 
             */
            public List<RRExtSideData3Response.Isik.Vead.Viga> getViga() {
                if (viga == null) {
                    viga = new ArrayList<RRExtSideData3Response.Isik.Vead.Viga>();
                }
                return this.viga;
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
             *         &lt;element name="kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="tekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                "kood",
                "tekst"
            })
            public static class Viga {

                @XmlElement(required = true)
                protected String kood;
                @XmlElement(required = true)
                protected String tekst;

                /**
                 * Gets the value of the kood property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKood() {
                    return kood;
                }

                /**
                 * Sets the value of the kood property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKood(String value) {
                    this.kood = value;
                }

                /**
                 * Gets the value of the tekst property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTekst() {
                    return tekst;
                }

                /**
                 * Sets the value of the tekst property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTekst(String value) {
                    this.tekst = value;
                }

            }

        }

    }

}
