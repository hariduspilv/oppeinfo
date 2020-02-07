
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR461pereDokumentResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR461pereDokumentResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Dokumendid"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Dokument" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Dokument.Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokument.Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokument.Seeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokument.Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokument.Kuupaev" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokument.Valjaandja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokument.KehtivAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokument.KehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokument.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *                                                 &lt;element name="Isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="Isik.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="Isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="Isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="Isik.VanaPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="Isik.VanaEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="Isik.Osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR461pereDokumentResponseType", propOrder = {
    "dokumendid"
})
public class RR461PereDokumentResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Dokumendid", required = true)
    protected RR461PereDokumentResponseType.Dokumendid dokumendid;

    /**
     * Gets the value of the dokumendid property.
     * 
     * @return
     *     possible object is
     *     {@link RR461PereDokumentResponseType.Dokumendid }
     *     
     */
    public RR461PereDokumentResponseType.Dokumendid getDokumendid() {
        return dokumendid;
    }

    /**
     * Sets the value of the dokumendid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR461PereDokumentResponseType.Dokumendid }
     *     
     */
    public void setDokumendid(RR461PereDokumentResponseType.Dokumendid value) {
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
     *         &lt;element name="Dokument" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Dokument.Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokument.Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokument.Seeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokument.Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokument.Kuupaev" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokument.Valjaandja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokument.KehtivAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokument.KehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokument.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
     *                                       &lt;element name="Isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="Isik.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="Isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="Isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="Isik.VanaPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="Isik.VanaEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="Isik.Osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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

        @XmlElement(name = "Dokument")
        protected List<RR461PereDokumentResponseType.Dokumendid.Dokument> dokument;

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
         * {@link RR461PereDokumentResponseType.Dokumendid.Dokument }
         * 
         * 
         */
        public List<RR461PereDokumentResponseType.Dokumendid.Dokument> getDokument() {
            if (dokument == null) {
                dokument = new ArrayList<RR461PereDokumentResponseType.Dokumendid.Dokument>();
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
         *         &lt;element name="Dokument.Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokument.Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokument.Seeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokument.Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokument.Kuupaev" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokument.Valjaandja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokument.KehtivAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokument.KehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokument.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
         *                             &lt;element name="Isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="Isik.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="Isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="Isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="Isik.VanaPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="Isik.VanaEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="Isik.Osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "dokumentKood",
            "dokumentSeeria",
            "dokumentNumber",
            "dokumentKuupaev",
            "dokumentValjaandja",
            "dokumentKehtivAlates",
            "dokumentKehtivKuni",
            "dokumentStaatus",
            "isikud"
        })
        public static class Dokument {

            @XmlElement(name = "Dokument.Nimetus", required = true)
            protected String dokumentNimetus;
            @XmlElement(name = "Dokument.Kood", required = true)
            protected String dokumentKood;
            @XmlElement(name = "Dokument.Seeria", required = true)
            protected String dokumentSeeria;
            @XmlElement(name = "Dokument.Number", required = true)
            protected String dokumentNumber;
            @XmlElement(name = "Dokument.Kuupaev", required = true)
            protected String dokumentKuupaev;
            @XmlElement(name = "Dokument.Valjaandja", required = true)
            protected String dokumentValjaandja;
            @XmlElement(name = "Dokument.KehtivAlates", required = true)
            protected String dokumentKehtivAlates;
            @XmlElement(name = "Dokument.KehtivKuni", required = true)
            protected String dokumentKehtivKuni;
            @XmlElement(name = "Dokument.Staatus", required = true)
            protected String dokumentStaatus;
            @XmlElement(name = "Isikud")
            protected RR461PereDokumentResponseType.Dokumendid.Dokument.Isikud isikud;

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
             * Gets the value of the dokumentKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumentKood() {
                return dokumentKood;
            }

            /**
             * Sets the value of the dokumentKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumentKood(String value) {
                this.dokumentKood = value;
            }

            /**
             * Gets the value of the dokumentSeeria property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumentSeeria() {
                return dokumentSeeria;
            }

            /**
             * Sets the value of the dokumentSeeria property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumentSeeria(String value) {
                this.dokumentSeeria = value;
            }

            /**
             * Gets the value of the dokumentNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumentNumber() {
                return dokumentNumber;
            }

            /**
             * Sets the value of the dokumentNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumentNumber(String value) {
                this.dokumentNumber = value;
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
             *     {@link RR461PereDokumentResponseType.Dokumendid.Dokument.Isikud }
             *     
             */
            public RR461PereDokumentResponseType.Dokumendid.Dokument.Isikud getIsikud() {
                return isikud;
            }

            /**
             * Sets the value of the isikud property.
             * 
             * @param value
             *     allowed object is
             *     {@link RR461PereDokumentResponseType.Dokumendid.Dokument.Isikud }
             *     
             */
            public void setIsikud(RR461PereDokumentResponseType.Dokumendid.Dokument.Isikud value) {
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
             *                   &lt;element name="Isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="Isik.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="Isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="Isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="Isik.VanaPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="Isik.VanaEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="Isik.Osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                protected List<RR461PereDokumentResponseType.Dokumendid.Dokument.Isikud.Isik> isik;

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
                 * {@link RR461PereDokumentResponseType.Dokumendid.Dokument.Isikud.Isik }
                 * 
                 * 
                 */
                public List<RR461PereDokumentResponseType.Dokumendid.Dokument.Isikud.Isik> getIsik() {
                    if (isik == null) {
                        isik = new ArrayList<RR461PereDokumentResponseType.Dokumendid.Dokument.Isikud.Isik>();
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
                 *         &lt;element name="Isik.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="Isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="Isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="Isik.VanaPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="Isik.VanaEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="Isik.Osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                    "isikVanaPerenimi",
                    "isikVanaEesnimi",
                    "isikOsalus"
                })
                public static class Isik {

                    @XmlElement(name = "Isik.Isikukood", required = true)
                    protected String isikIsikukood;
                    @XmlElement(name = "Isik.Synniaeg", required = true)
                    protected String isikSynniaeg;
                    @XmlElement(name = "Isik.Perenimi", required = true)
                    protected String isikPerenimi;
                    @XmlElement(name = "Isik.Eesnimi", required = true)
                    protected String isikEesnimi;
                    @XmlElement(name = "Isik.VanaPerenimi", required = true)
                    protected String isikVanaPerenimi;
                    @XmlElement(name = "Isik.VanaEesnimi", required = true)
                    protected String isikVanaEesnimi;
                    @XmlElement(name = "Isik.Osalus", required = true)
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
                     * Gets the value of the isikVanaPerenimi property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getIsikVanaPerenimi() {
                        return isikVanaPerenimi;
                    }

                    /**
                     * Sets the value of the isikVanaPerenimi property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setIsikVanaPerenimi(String value) {
                        this.isikVanaPerenimi = value;
                    }

                    /**
                     * Gets the value of the isikVanaEesnimi property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getIsikVanaEesnimi() {
                        return isikVanaEesnimi;
                    }

                    /**
                     * Sets the value of the isikVanaEesnimi property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setIsikVanaEesnimi(String value) {
                        this.isikVanaEesnimi = value;
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
