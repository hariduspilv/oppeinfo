
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR458isikuTDkontrollResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR458isikuTDkontrollResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="veatekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
 *                             &lt;element name="Dokument.Seeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokument.Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokument.Valjaandja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokument.Kuupaev" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokument.KehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokument.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokument.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokument.Perekonnanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokument.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR458isikuTDkontrollResponseType", propOrder = {
    "veatekst",
    "dokumendid"
})
public class RR458IsikuTDkontrollResponseType
    extends XRoadResponseBaseType
{

    protected String veatekst;
    @XmlElement(name = "Dokumendid", required = true)
    protected RR458IsikuTDkontrollResponseType.Dokumendid dokumendid;

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
     *     {@link RR458IsikuTDkontrollResponseType.Dokumendid }
     *     
     */
    public RR458IsikuTDkontrollResponseType.Dokumendid getDokumendid() {
        return dokumendid;
    }

    /**
     * Sets the value of the dokumendid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR458IsikuTDkontrollResponseType.Dokumendid }
     *     
     */
    public void setDokumendid(RR458IsikuTDkontrollResponseType.Dokumendid value) {
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
     *                   &lt;element name="Dokument.Seeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokument.Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokument.Valjaandja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokument.Kuupaev" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokument.KehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokument.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokument.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokument.Perekonnanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokument.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        protected List<RR458IsikuTDkontrollResponseType.Dokumendid.Dokument> dokument;

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
         * {@link RR458IsikuTDkontrollResponseType.Dokumendid.Dokument }
         * 
         * 
         */
        public List<RR458IsikuTDkontrollResponseType.Dokumendid.Dokument> getDokument() {
            if (dokument == null) {
                dokument = new ArrayList<RR458IsikuTDkontrollResponseType.Dokumendid.Dokument>();
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
         *         &lt;element name="Dokument.Seeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokument.Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokument.Valjaandja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokument.Kuupaev" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokument.KehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokument.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokument.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokument.Perekonnanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokument.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "dokumentSeeria",
            "dokumentNumber",
            "dokumentValjaandja",
            "dokumentKuupaev",
            "dokumentKehtivKuni",
            "dokumentStaatus",
            "dokumentIsikukood",
            "dokumentPerekonnanimi",
            "dokumentEesnimi"
        })
        public static class Dokument {

            @XmlElement(name = "Dokument.Nimetus", required = true)
            protected String dokumentNimetus;
            @XmlElement(name = "Dokument.Seeria", required = true)
            protected String dokumentSeeria;
            @XmlElement(name = "Dokument.Number", required = true)
            protected String dokumentNumber;
            @XmlElement(name = "Dokument.Valjaandja", required = true)
            protected String dokumentValjaandja;
            @XmlElement(name = "Dokument.Kuupaev", required = true)
            protected String dokumentKuupaev;
            @XmlElement(name = "Dokument.KehtivKuni", required = true)
            protected String dokumentKehtivKuni;
            @XmlElement(name = "Dokument.Staatus", required = true)
            protected String dokumentStaatus;
            @XmlElement(name = "Dokument.Isikukood", required = true)
            protected String dokumentIsikukood;
            @XmlElement(name = "Dokument.Perekonnanimi", required = true)
            protected String dokumentPerekonnanimi;
            @XmlElement(name = "Dokument.Eesnimi", required = true)
            protected String dokumentEesnimi;

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
             * Gets the value of the dokumentIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumentIsikukood() {
                return dokumentIsikukood;
            }

            /**
             * Sets the value of the dokumentIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumentIsikukood(String value) {
                this.dokumentIsikukood = value;
            }

            /**
             * Gets the value of the dokumentPerekonnanimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumentPerekonnanimi() {
                return dokumentPerekonnanimi;
            }

            /**
             * Sets the value of the dokumentPerekonnanimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumentPerekonnanimi(String value) {
                this.dokumentPerekonnanimi = value;
            }

            /**
             * Gets the value of the dokumentEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumentEesnimi() {
                return dokumentEesnimi;
            }

            /**
             * Sets the value of the dokumentEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumentEesnimi(String value) {
                this.dokumentEesnimi = value;
            }

        }

    }

}
