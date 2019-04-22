
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Dokument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Dokument"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Liik" type="{http://rr.x-road.eu/producer}Kodif1"/&gt;
 *         &lt;element name="DokumendiNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="KoostanudAsutus"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodif1"/&gt;
 *                   &lt;element name="Registrikood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="AsutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="AmetnikuIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AmetnikuNimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dokument", propOrder = {
    "liik",
    "dokumendiNumber",
    "koostanudAsutus",
    "ametnikuIsikukood",
    "ametnikuNimed"
})
public class Dokument {

    @XmlElement(name = "Liik", required = true)
    protected Kodif1 liik;
    @XmlElement(name = "DokumendiNumber", required = true)
    protected String dokumendiNumber;
    @XmlElement(name = "KoostanudAsutus", required = true)
    protected Dokument.KoostanudAsutus koostanudAsutus;
    @XmlElement(name = "AmetnikuIsikukood", required = true)
    protected String ametnikuIsikukood;
    @XmlElement(name = "AmetnikuNimed", required = true)
    protected String ametnikuNimed;

    /**
     * Gets the value of the liik property.
     * 
     * @return
     *     possible object is
     *     {@link Kodif1 }
     *     
     */
    public Kodif1 getLiik() {
        return liik;
    }

    /**
     * Sets the value of the liik property.
     * 
     * @param value
     *     allowed object is
     *     {@link Kodif1 }
     *     
     */
    public void setLiik(Kodif1 value) {
        this.liik = value;
    }

    /**
     * Gets the value of the dokumendiNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiNumber() {
        return dokumendiNumber;
    }

    /**
     * Sets the value of the dokumendiNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiNumber(String value) {
        this.dokumendiNumber = value;
    }

    /**
     * Gets the value of the koostanudAsutus property.
     * 
     * @return
     *     possible object is
     *     {@link Dokument.KoostanudAsutus }
     *     
     */
    public Dokument.KoostanudAsutus getKoostanudAsutus() {
        return koostanudAsutus;
    }

    /**
     * Sets the value of the koostanudAsutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dokument.KoostanudAsutus }
     *     
     */
    public void setKoostanudAsutus(Dokument.KoostanudAsutus value) {
        this.koostanudAsutus = value;
    }

    /**
     * Gets the value of the ametnikuIsikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmetnikuIsikukood() {
        return ametnikuIsikukood;
    }

    /**
     * Sets the value of the ametnikuIsikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmetnikuIsikukood(String value) {
        this.ametnikuIsikukood = value;
    }

    /**
     * Gets the value of the ametnikuNimed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmetnikuNimed() {
        return ametnikuNimed;
    }

    /**
     * Sets the value of the ametnikuNimed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmetnikuNimed(String value) {
        this.ametnikuNimed = value;
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
     *         &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodif1"/&gt;
     *         &lt;element name="Registrikood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="AsutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
        "riik",
        "registrikood",
        "asutuseNimi"
    })
    public static class KoostanudAsutus {

        @XmlElement(name = "Riik", required = true)
        protected Kodif1 riik;
        @XmlElement(name = "Registrikood", required = true)
        protected String registrikood;
        @XmlElement(name = "AsutuseNimi")
        protected String asutuseNimi;

        /**
         * Gets the value of the riik property.
         * 
         * @return
         *     possible object is
         *     {@link Kodif1 }
         *     
         */
        public Kodif1 getRiik() {
            return riik;
        }

        /**
         * Sets the value of the riik property.
         * 
         * @param value
         *     allowed object is
         *     {@link Kodif1 }
         *     
         */
        public void setRiik(Kodif1 value) {
            this.riik = value;
        }

        /**
         * Gets the value of the registrikood property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRegistrikood() {
            return registrikood;
        }

        /**
         * Sets the value of the registrikood property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRegistrikood(String value) {
            this.registrikood = value;
        }

        /**
         * Gets the value of the asutuseNimi property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAsutuseNimi() {
            return asutuseNimi;
        }

        /**
         * Sets the value of the asutuseNimi property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAsutuseNimi(String value) {
            this.asutuseNimi = value;
        }

    }

}
