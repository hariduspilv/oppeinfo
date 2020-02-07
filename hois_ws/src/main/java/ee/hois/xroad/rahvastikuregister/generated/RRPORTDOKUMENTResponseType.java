
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRPORTDOKUMENTResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRPORTDOKUMENTResponseType"&gt;
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
 *                             &lt;element name="dokument.DokLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dokument.DokStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dokument.DokSeeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dokument.DokNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dokument.DokValjaantud" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dokument.DokValjaandja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dokument.DokKehtivAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dokument.DokKehtivLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *                                                 &lt;element name="Isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="Isik.eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RRPORTDOKUMENTResponseType", propOrder = {
    "veatekst",
    "dokumendid"
})
public class RRPORTDOKUMENTResponseType
    extends XRoadResponseBaseType
{

    protected String veatekst;
    @XmlElement(required = true)
    protected RRPORTDOKUMENTResponseType.Dokumendid dokumendid;

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
     *     {@link RRPORTDOKUMENTResponseType.Dokumendid }
     *     
     */
    public RRPORTDOKUMENTResponseType.Dokumendid getDokumendid() {
        return dokumendid;
    }

    /**
     * Sets the value of the dokumendid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRPORTDOKUMENTResponseType.Dokumendid }
     *     
     */
    public void setDokumendid(RRPORTDOKUMENTResponseType.Dokumendid value) {
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
     *                   &lt;element name="dokument.DokLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dokument.DokStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dokument.DokSeeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dokument.DokNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dokument.DokValjaantud" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dokument.DokValjaandja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dokument.DokKehtivAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dokument.DokKehtivLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
     *                                       &lt;element name="Isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="Isik.eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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

        protected List<RRPORTDOKUMENTResponseType.Dokumendid.Dokument> dokument;

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
         * {@link RRPORTDOKUMENTResponseType.Dokumendid.Dokument }
         * 
         * 
         */
        public List<RRPORTDOKUMENTResponseType.Dokumendid.Dokument> getDokument() {
            if (dokument == null) {
                dokument = new ArrayList<RRPORTDOKUMENTResponseType.Dokumendid.Dokument>();
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
         *         &lt;element name="dokument.DokLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dokument.DokStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dokument.DokSeeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dokument.DokNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dokument.DokValjaantud" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dokument.DokValjaandja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dokument.DokKehtivAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dokument.DokKehtivLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
         *                             &lt;element name="Isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="Isik.eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "dokumentDokLiik",
            "dokumentDokStaatus",
            "dokumentDokSeeria",
            "dokumentDokNumber",
            "dokumentDokValjaantud",
            "dokumentDokValjaandja",
            "dokumentDokKehtivAlgus",
            "dokumentDokKehtivLopp",
            "isikud"
        })
        public static class Dokument {

            @XmlElement(name = "dokument.DokLiik", required = true)
            protected String dokumentDokLiik;
            @XmlElement(name = "dokument.DokStaatus", required = true)
            protected String dokumentDokStaatus;
            @XmlElement(name = "dokument.DokSeeria", required = true)
            protected String dokumentDokSeeria;
            @XmlElement(name = "dokument.DokNumber", required = true)
            protected String dokumentDokNumber;
            @XmlElement(name = "dokument.DokValjaantud", required = true)
            protected String dokumentDokValjaantud;
            @XmlElement(name = "dokument.DokValjaandja", required = true)
            protected String dokumentDokValjaandja;
            @XmlElement(name = "dokument.DokKehtivAlgus", required = true)
            protected String dokumentDokKehtivAlgus;
            @XmlElement(name = "dokument.DokKehtivLopp", required = true)
            protected String dokumentDokKehtivLopp;
            @XmlElement(name = "Isikud")
            protected RRPORTDOKUMENTResponseType.Dokumendid.Dokument.Isikud isikud;

            /**
             * Gets the value of the dokumentDokLiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumentDokLiik() {
                return dokumentDokLiik;
            }

            /**
             * Sets the value of the dokumentDokLiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumentDokLiik(String value) {
                this.dokumentDokLiik = value;
            }

            /**
             * Gets the value of the dokumentDokStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumentDokStaatus() {
                return dokumentDokStaatus;
            }

            /**
             * Sets the value of the dokumentDokStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumentDokStaatus(String value) {
                this.dokumentDokStaatus = value;
            }

            /**
             * Gets the value of the dokumentDokSeeria property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumentDokSeeria() {
                return dokumentDokSeeria;
            }

            /**
             * Sets the value of the dokumentDokSeeria property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumentDokSeeria(String value) {
                this.dokumentDokSeeria = value;
            }

            /**
             * Gets the value of the dokumentDokNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumentDokNumber() {
                return dokumentDokNumber;
            }

            /**
             * Sets the value of the dokumentDokNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumentDokNumber(String value) {
                this.dokumentDokNumber = value;
            }

            /**
             * Gets the value of the dokumentDokValjaantud property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumentDokValjaantud() {
                return dokumentDokValjaantud;
            }

            /**
             * Sets the value of the dokumentDokValjaantud property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumentDokValjaantud(String value) {
                this.dokumentDokValjaantud = value;
            }

            /**
             * Gets the value of the dokumentDokValjaandja property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumentDokValjaandja() {
                return dokumentDokValjaandja;
            }

            /**
             * Sets the value of the dokumentDokValjaandja property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumentDokValjaandja(String value) {
                this.dokumentDokValjaandja = value;
            }

            /**
             * Gets the value of the dokumentDokKehtivAlgus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumentDokKehtivAlgus() {
                return dokumentDokKehtivAlgus;
            }

            /**
             * Sets the value of the dokumentDokKehtivAlgus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumentDokKehtivAlgus(String value) {
                this.dokumentDokKehtivAlgus = value;
            }

            /**
             * Gets the value of the dokumentDokKehtivLopp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumentDokKehtivLopp() {
                return dokumentDokKehtivLopp;
            }

            /**
             * Sets the value of the dokumentDokKehtivLopp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumentDokKehtivLopp(String value) {
                this.dokumentDokKehtivLopp = value;
            }

            /**
             * Gets the value of the isikud property.
             * 
             * @return
             *     possible object is
             *     {@link RRPORTDOKUMENTResponseType.Dokumendid.Dokument.Isikud }
             *     
             */
            public RRPORTDOKUMENTResponseType.Dokumendid.Dokument.Isikud getIsikud() {
                return isikud;
            }

            /**
             * Sets the value of the isikud property.
             * 
             * @param value
             *     allowed object is
             *     {@link RRPORTDOKUMENTResponseType.Dokumendid.Dokument.Isikud }
             *     
             */
            public void setIsikud(RRPORTDOKUMENTResponseType.Dokumendid.Dokument.Isikud value) {
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
             *                   &lt;element name="Isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="Isik.eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                protected List<RRPORTDOKUMENTResponseType.Dokumendid.Dokument.Isikud.Isik> isik;

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
                 * {@link RRPORTDOKUMENTResponseType.Dokumendid.Dokument.Isikud.Isik }
                 * 
                 * 
                 */
                public List<RRPORTDOKUMENTResponseType.Dokumendid.Dokument.Isikud.Isik> getIsik() {
                    if (isik == null) {
                        isik = new ArrayList<RRPORTDOKUMENTResponseType.Dokumendid.Dokument.Isikud.Isik>();
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
                 *         &lt;element name="Isik.eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                    "isikEesnimi"
                })
                public static class Isik {

                    @XmlElement(name = "Isik.Isikukood", required = true)
                    protected String isikIsikukood;
                    @XmlElement(name = "Isik.Perenimi", required = true)
                    protected String isikPerenimi;
                    @XmlElement(name = "Isik.eesnimi", required = true)
                    protected String isikEesnimi;

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

                }

            }

        }

    }

}
