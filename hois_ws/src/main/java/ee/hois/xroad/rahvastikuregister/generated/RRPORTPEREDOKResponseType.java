
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRPORTPEREDOKResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRPORTPEREDOKResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="veatekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dokumendid"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="dokument" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="dokument.Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dokument.SeeriaNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dokument.kuupaev" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dokument.Valjaandja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dokument.Valjaandja_Pikk" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dokument.KehtivAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dokument.KehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dokument.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikud" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Isik" maxOccurs="unbounded"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="Isik.isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="Isik.synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="Isik.perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="Isik.eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="Isik.osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
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
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRPORTPEREDOKResponseType", propOrder = {
    "veatekst",
    "dokumendid"
})
public class RRPORTPEREDOKResponseType
    extends XRoadResponseBaseType
{

    protected String veatekst;
    @XmlElement(required = true)
    protected RRPORTPEREDOKResponseType.Dokumendid dokumendid;

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
     * Gets the value of the dokumendid property.
     * 
     * @return
     *     possible object is
     *     {@link RRPORTPEREDOKResponseType.Dokumendid }
     *     
     */
    public RRPORTPEREDOKResponseType.Dokumendid getDokumendid() {
        return dokumendid;
    }

    /**
     * Sets the value of the dokumendid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRPORTPEREDOKResponseType.Dokumendid }
     *     
     */
    public void setDokumendid(RRPORTPEREDOKResponseType.Dokumendid value) {
        this.dokumendid = value;
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
     *         &lt;element name="dokument" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="dokument.Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dokument.SeeriaNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dokument.kuupaev" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dokument.Valjaandja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dokument.Valjaandja_Pikk" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dokument.KehtivAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dokument.KehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dokument.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikud" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Isik" maxOccurs="unbounded"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="Isik.isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="Isik.synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="Isik.perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="Isik.eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="Isik.osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    @XmlType(name = "", propOrder = {
        "dokument"
    })
    public static class Dokumendid {

        protected List<RRPORTPEREDOKResponseType.Dokumendid.Dokument> dokument;

        /**
         * Gets the value of the dokument property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the dokument property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDokument().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RRPORTPEREDOKResponseType.Dokumendid.Dokument }
         * 
         * 
         */
        public List<RRPORTPEREDOKResponseType.Dokumendid.Dokument> getDokument() {
            if (dokument == null) {
                dokument = new ArrayList<RRPORTPEREDOKResponseType.Dokumendid.Dokument>();
            }
            return this.dokument;
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
         *         &lt;element name="dokument.Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dokument.SeeriaNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dokument.kuupaev" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dokument.Valjaandja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dokument.Valjaandja_Pikk" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dokument.KehtivAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dokument.KehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dokument.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikud" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Isik" maxOccurs="unbounded"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="Isik.isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="Isik.synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="Isik.perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="Isik.eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="Isik.osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "dokumentNimetus",
            "dokumentSeeriaNumber",
            "dokumentKuupaev",
            "dokumentValjaandja",
            "dokumentValjaandjaPikk",
            "dokumentKehtivAlates",
            "dokumentKehtivKuni",
            "dokumentStaatus",
            "isikud"
        })
        public static class Dokument {

            @XmlElement(name = "dokument.Nimetus", required = true)
            protected String dokumentNimetus;
            @XmlElement(name = "dokument.SeeriaNumber", required = true)
            protected String dokumentSeeriaNumber;
            @XmlElement(name = "dokument.kuupaev", required = true)
            protected String dokumentKuupaev;
            @XmlElement(name = "dokument.Valjaandja", required = true)
            protected String dokumentValjaandja;
            @XmlElement(name = "dokument.Valjaandja_Pikk", required = true)
            protected String dokumentValjaandjaPikk;
            @XmlElement(name = "dokument.KehtivAlates", required = true)
            protected String dokumentKehtivAlates;
            @XmlElement(name = "dokument.KehtivKuni", required = true)
            protected String dokumentKehtivKuni;
            @XmlElement(name = "dokument.Staatus", required = true)
            protected String dokumentStaatus;
            @XmlElement(name = "Isikud")
            protected RRPORTPEREDOKResponseType.Dokumendid.Dokument.Isikud isikud;

            /**
             * Gets the value of the dokumentNimetus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumentNimetus() {
                return dokumentNimetus;
            }

            /**
             * Sets the value of the dokumentNimetus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumentNimetus(String value) {
                this.dokumentNimetus = value;
            }

            /**
             * Gets the value of the dokumentSeeriaNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumentSeeriaNumber() {
                return dokumentSeeriaNumber;
            }

            /**
             * Sets the value of the dokumentSeeriaNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumentSeeriaNumber(String value) {
                this.dokumentSeeriaNumber = value;
            }

            /**
             * Gets the value of the dokumentKuupaev property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumentKuupaev() {
                return dokumentKuupaev;
            }

            /**
             * Sets the value of the dokumentKuupaev property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumentKuupaev(String value) {
                this.dokumentKuupaev = value;
            }

            /**
             * Gets the value of the dokumentValjaandja property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumentValjaandja() {
                return dokumentValjaandja;
            }

            /**
             * Sets the value of the dokumentValjaandja property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumentValjaandja(String value) {
                this.dokumentValjaandja = value;
            }

            /**
             * Gets the value of the dokumentValjaandjaPikk property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumentValjaandjaPikk() {
                return dokumentValjaandjaPikk;
            }

            /**
             * Sets the value of the dokumentValjaandjaPikk property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumentValjaandjaPikk(String value) {
                this.dokumentValjaandjaPikk = value;
            }

            /**
             * Gets the value of the dokumentKehtivAlates property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumentKehtivAlates() {
                return dokumentKehtivAlates;
            }

            /**
             * Sets the value of the dokumentKehtivAlates property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumentKehtivAlates(String value) {
                this.dokumentKehtivAlates = value;
            }

            /**
             * Gets the value of the dokumentKehtivKuni property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumentKehtivKuni() {
                return dokumentKehtivKuni;
            }

            /**
             * Sets the value of the dokumentKehtivKuni property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumentKehtivKuni(String value) {
                this.dokumentKehtivKuni = value;
            }

            /**
             * Gets the value of the dokumentStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumentStaatus() {
                return dokumentStaatus;
            }

            /**
             * Sets the value of the dokumentStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumentStaatus(String value) {
                this.dokumentStaatus = value;
            }

            /**
             * Gets the value of the isikud property.
             * 
             * @return
             *     possible object is
             *     {@link RRPORTPEREDOKResponseType.Dokumendid.Dokument.Isikud }
             *     
             */
            public RRPORTPEREDOKResponseType.Dokumendid.Dokument.Isikud getIsikud() {
                return isikud;
            }

            /**
             * Sets the value of the isikud property.
             * 
             * @param value
             *     allowed object is
             *     {@link RRPORTPEREDOKResponseType.Dokumendid.Dokument.Isikud }
             *     
             */
            public void setIsikud(RRPORTPEREDOKResponseType.Dokumendid.Dokument.Isikud value) {
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
             *         &lt;element name="Isik" maxOccurs="unbounded"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="Isik.isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="Isik.synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="Isik.perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="Isik.eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="Isik.osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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

                @XmlElement(name = "Isik", required = true)
                protected List<RRPORTPEREDOKResponseType.Dokumendid.Dokument.Isikud.Isik> isik;

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
                 * {@link RRPORTPEREDOKResponseType.Dokumendid.Dokument.Isikud.Isik }
                 * 
                 * 
                 */
                public List<RRPORTPEREDOKResponseType.Dokumendid.Dokument.Isikud.Isik> getIsik() {
                    if (isik == null) {
                        isik = new ArrayList<RRPORTPEREDOKResponseType.Dokumendid.Dokument.Isikud.Isik>();
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
                 *         &lt;element name="Isik.isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="Isik.synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="Isik.perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="Isik.eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="Isik.osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                    "isikSynniaeg",
                    "isikPerenimi",
                    "isikEesnimi",
                    "isikOsalus"
                })
                public static class Isik {

                    @XmlElement(name = "Isik.isikukood", required = true)
                    protected String isikIsikukood;
                    @XmlElement(name = "Isik.synniaeg", required = true)
                    protected String isikSynniaeg;
                    @XmlElement(name = "Isik.perenimi", required = true)
                    protected String isikPerenimi;
                    @XmlElement(name = "Isik.eesnimi", required = true)
                    protected String isikEesnimi;
                    @XmlElement(name = "Isik.osalus", required = true)
                    protected String isikOsalus;

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
                     * Gets the value of the isikSynniaeg property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getIsikSynniaeg() {
                        return isikSynniaeg;
                    }

                    /**
                     * Sets the value of the isikSynniaeg property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setIsikSynniaeg(String value) {
                        this.isikSynniaeg = value;
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
                     * Gets the value of the isikOsalus property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getIsikOsalus() {
                        return isikOsalus;
                    }

                    /**
                     * Sets the value of the isikOsalus property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setIsikOsalus(String value) {
                        this.isikOsalus = value;
                    }

                }

            }

        }

    }

}
