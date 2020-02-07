
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRPORTDOK_KEHTIVUSResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRPORTDOK_KEHTIVUSResponseType"&gt;
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
 *                             &lt;element name="dokument.DokKehtAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dokument.DokKehtLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dokument.DokIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dokument.DokEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dokument.DokPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RRPORTDOK_KEHTIVUSResponseType", propOrder = {
    "veatekst",
    "dokumendid"
})
public class RRPORTDOKKEHTIVUSResponseType
    extends XRoadResponseBaseType
{

    protected String veatekst;
    @XmlElement(required = true)
    protected RRPORTDOKKEHTIVUSResponseType.Dokumendid dokumendid;

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
     *     {@link RRPORTDOKKEHTIVUSResponseType.Dokumendid }
     *     
     */
    public RRPORTDOKKEHTIVUSResponseType.Dokumendid getDokumendid() {
        return dokumendid;
    }

    /**
     * Sets the value of the dokumendid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRPORTDOKKEHTIVUSResponseType.Dokumendid }
     *     
     */
    public void setDokumendid(RRPORTDOKKEHTIVUSResponseType.Dokumendid value) {
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
     *                   &lt;element name="dokument.DokKehtAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dokument.DokKehtLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dokument.DokIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dokument.DokEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dokument.DokPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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

        protected List<RRPORTDOKKEHTIVUSResponseType.Dokumendid.Dokument> dokument;

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
         * {@link RRPORTDOKKEHTIVUSResponseType.Dokumendid.Dokument }
         * 
         * 
         */
        public List<RRPORTDOKKEHTIVUSResponseType.Dokumendid.Dokument> getDokument() {
            if (dokument == null) {
                dokument = new ArrayList<RRPORTDOKKEHTIVUSResponseType.Dokumendid.Dokument>();
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
         *         &lt;element name="dokument.DokKehtAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dokument.DokKehtLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dokument.DokIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dokument.DokEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dokument.DokPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "dokumentDokKehtAlgus",
            "dokumentDokKehtLopp",
            "dokumentDokIsikukood",
            "dokumentDokEesnimi",
            "dokumentDokPerenimi"
        })
        public static class Dokument {

            @XmlElement(name = "dokument.DokLiik", required = true)
            protected String dokumentDokLiik;
            @XmlElement(name = "dokument.DokStaatus", required = true)
            protected String dokumentDokStaatus;
            @XmlElement(name = "dokument.DokKehtAlgus", required = true)
            protected String dokumentDokKehtAlgus;
            @XmlElement(name = "dokument.DokKehtLopp", required = true)
            protected String dokumentDokKehtLopp;
            @XmlElement(name = "dokument.DokIsikukood", required = true)
            protected String dokumentDokIsikukood;
            @XmlElement(name = "dokument.DokEesnimi", required = true)
            protected String dokumentDokEesnimi;
            @XmlElement(name = "dokument.DokPerenimi", required = true)
            protected String dokumentDokPerenimi;

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
             * Gets the value of the dokumentDokKehtAlgus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumentDokKehtAlgus() {
                return dokumentDokKehtAlgus;
            }

            /**
             * Sets the value of the dokumentDokKehtAlgus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumentDokKehtAlgus(String value) {
                this.dokumentDokKehtAlgus = value;
            }

            /**
             * Gets the value of the dokumentDokKehtLopp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumentDokKehtLopp() {
                return dokumentDokKehtLopp;
            }

            /**
             * Sets the value of the dokumentDokKehtLopp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumentDokKehtLopp(String value) {
                this.dokumentDokKehtLopp = value;
            }

            /**
             * Gets the value of the dokumentDokIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumentDokIsikukood() {
                return dokumentDokIsikukood;
            }

            /**
             * Sets the value of the dokumentDokIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumentDokIsikukood(String value) {
                this.dokumentDokIsikukood = value;
            }

            /**
             * Gets the value of the dokumentDokEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumentDokEesnimi() {
                return dokumentDokEesnimi;
            }

            /**
             * Sets the value of the dokumentDokEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumentDokEesnimi(String value) {
                this.dokumentDokEesnimi = value;
            }

            /**
             * Gets the value of the dokumentDokPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumentDokPerenimi() {
                return dokumentDokPerenimi;
            }

            /**
             * Sets the value of the dokumentDokPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumentDokPerenimi(String value) {
                this.dokumentDokPerenimi = value;
            }

        }

    }

}
