
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRs1305IsikukoodiTellimineResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRs1305IsikukoodiTellimineResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ttSonum2" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="ttSonum2.dtellimusnr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="ttSonum2.dlaps_kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="ttSonum2.dema_ikood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="ttSonum2.cema_pere" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="ttSonum2.cema_ees" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="ttSonum2.iriik" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *                   &lt;element name="ttSonum2.imaak" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *                   &lt;element name="ttSonum2.ivald" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *                   &lt;element name="ttSonum2.iasula" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *                   &lt;element name="ttSonum2.caadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="ttSonum2.iviga_kood" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *                   &lt;element name="ttSonum2.cviga_tekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ttSonum2_a" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="ttSonum2_a.dtellimusnr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="ttSonum2_a.dlaps_kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="ttSonum2_a.dema_ikood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="ttSonum2_a.cema_pere" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="ttSonum2_a.cema_ees" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="ttSonum2_a.iriik" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *                   &lt;element name="ttSonum2_a.imaak" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *                   &lt;element name="ttSonum2_a.ivald" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *                   &lt;element name="ttSonum2_a.iasula" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *                   &lt;element name="ttSonum2_a.caadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="ttSonum2_a.cVkpv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="ttSonum2_a.iviga_kood" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *                   &lt;element name="ttSonum2_a.cviga_tekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
@XmlType(name = "RRs1305IsikukoodiTellimineResponseType", propOrder = {
    "ttSonum2",
    "ttSonum2A"
})
public class RRs1305IsikukoodiTellimineResponseType
    extends XRoadResponseBaseType
{

    protected List<RRs1305IsikukoodiTellimineResponseType.TtSonum2> ttSonum2;
    @XmlElement(name = "ttSonum2_a")
    protected List<RRs1305IsikukoodiTellimineResponseType.TtSonum2A> ttSonum2A;

    /**
     * Gets the value of the ttSonum2 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ttSonum2 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTtSonum2().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RRs1305IsikukoodiTellimineResponseType.TtSonum2 }
     * 
     * 
     */
    public List<RRs1305IsikukoodiTellimineResponseType.TtSonum2> getTtSonum2() {
        if (ttSonum2 == null) {
            ttSonum2 = new ArrayList<RRs1305IsikukoodiTellimineResponseType.TtSonum2>();
        }
        return this.ttSonum2;
    }

    /**
     * Gets the value of the ttSonum2A property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ttSonum2A property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTtSonum2A().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RRs1305IsikukoodiTellimineResponseType.TtSonum2A }
     * 
     * 
     */
    public List<RRs1305IsikukoodiTellimineResponseType.TtSonum2A> getTtSonum2A() {
        if (ttSonum2A == null) {
            ttSonum2A = new ArrayList<RRs1305IsikukoodiTellimineResponseType.TtSonum2A>();
        }
        return this.ttSonum2A;
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
     *         &lt;element name="ttSonum2.dtellimusnr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="ttSonum2.dlaps_kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="ttSonum2.dema_ikood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="ttSonum2.cema_pere" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="ttSonum2.cema_ees" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="ttSonum2.iriik" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
     *         &lt;element name="ttSonum2.imaak" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
     *         &lt;element name="ttSonum2.ivald" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
     *         &lt;element name="ttSonum2.iasula" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
     *         &lt;element name="ttSonum2.caadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="ttSonum2.iviga_kood" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
     *         &lt;element name="ttSonum2.cviga_tekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "ttSonum2Dtellimusnr",
        "ttSonum2DlapsKood",
        "ttSonum2DemaIkood",
        "ttSonum2CemaPere",
        "ttSonum2CemaEes",
        "ttSonum2Iriik",
        "ttSonum2Imaak",
        "ttSonum2Ivald",
        "ttSonum2Iasula",
        "ttSonum2Caadress",
        "ttSonum2IvigaKood",
        "ttSonum2CvigaTekst"
    })
    public static class TtSonum2 {

        @XmlElement(name = "ttSonum2.dtellimusnr")
        protected String ttSonum2Dtellimusnr;
        @XmlElement(name = "ttSonum2.dlaps_kood")
        protected String ttSonum2DlapsKood;
        @XmlElement(name = "ttSonum2.dema_ikood")
        protected String ttSonum2DemaIkood;
        @XmlElement(name = "ttSonum2.cema_pere")
        protected String ttSonum2CemaPere;
        @XmlElement(name = "ttSonum2.cema_ees")
        protected String ttSonum2CemaEes;
        @XmlElement(name = "ttSonum2.iriik")
        protected BigInteger ttSonum2Iriik;
        @XmlElement(name = "ttSonum2.imaak")
        protected BigInteger ttSonum2Imaak;
        @XmlElement(name = "ttSonum2.ivald")
        protected BigInteger ttSonum2Ivald;
        @XmlElement(name = "ttSonum2.iasula")
        protected BigInteger ttSonum2Iasula;
        @XmlElement(name = "ttSonum2.caadress")
        protected String ttSonum2Caadress;
        @XmlElement(name = "ttSonum2.iviga_kood")
        protected BigInteger ttSonum2IvigaKood;
        @XmlElement(name = "ttSonum2.cviga_tekst", required = true)
        protected String ttSonum2CvigaTekst;

        /**
         * Gets the value of the ttSonum2Dtellimusnr property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTtSonum2Dtellimusnr() {
            return ttSonum2Dtellimusnr;
        }

        /**
         * Sets the value of the ttSonum2Dtellimusnr property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTtSonum2Dtellimusnr(String value) {
            this.ttSonum2Dtellimusnr = value;
        }

        /**
         * Gets the value of the ttSonum2DlapsKood property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTtSonum2DlapsKood() {
            return ttSonum2DlapsKood;
        }

        /**
         * Sets the value of the ttSonum2DlapsKood property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTtSonum2DlapsKood(String value) {
            this.ttSonum2DlapsKood = value;
        }

        /**
         * Gets the value of the ttSonum2DemaIkood property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTtSonum2DemaIkood() {
            return ttSonum2DemaIkood;
        }

        /**
         * Sets the value of the ttSonum2DemaIkood property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTtSonum2DemaIkood(String value) {
            this.ttSonum2DemaIkood = value;
        }

        /**
         * Gets the value of the ttSonum2CemaPere property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTtSonum2CemaPere() {
            return ttSonum2CemaPere;
        }

        /**
         * Sets the value of the ttSonum2CemaPere property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTtSonum2CemaPere(String value) {
            this.ttSonum2CemaPere = value;
        }

        /**
         * Gets the value of the ttSonum2CemaEes property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTtSonum2CemaEes() {
            return ttSonum2CemaEes;
        }

        /**
         * Sets the value of the ttSonum2CemaEes property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTtSonum2CemaEes(String value) {
            this.ttSonum2CemaEes = value;
        }

        /**
         * Gets the value of the ttSonum2Iriik property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getTtSonum2Iriik() {
            return ttSonum2Iriik;
        }

        /**
         * Sets the value of the ttSonum2Iriik property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setTtSonum2Iriik(BigInteger value) {
            this.ttSonum2Iriik = value;
        }

        /**
         * Gets the value of the ttSonum2Imaak property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getTtSonum2Imaak() {
            return ttSonum2Imaak;
        }

        /**
         * Sets the value of the ttSonum2Imaak property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setTtSonum2Imaak(BigInteger value) {
            this.ttSonum2Imaak = value;
        }

        /**
         * Gets the value of the ttSonum2Ivald property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getTtSonum2Ivald() {
            return ttSonum2Ivald;
        }

        /**
         * Sets the value of the ttSonum2Ivald property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setTtSonum2Ivald(BigInteger value) {
            this.ttSonum2Ivald = value;
        }

        /**
         * Gets the value of the ttSonum2Iasula property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getTtSonum2Iasula() {
            return ttSonum2Iasula;
        }

        /**
         * Sets the value of the ttSonum2Iasula property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setTtSonum2Iasula(BigInteger value) {
            this.ttSonum2Iasula = value;
        }

        /**
         * Gets the value of the ttSonum2Caadress property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTtSonum2Caadress() {
            return ttSonum2Caadress;
        }

        /**
         * Sets the value of the ttSonum2Caadress property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTtSonum2Caadress(String value) {
            this.ttSonum2Caadress = value;
        }

        /**
         * Gets the value of the ttSonum2IvigaKood property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getTtSonum2IvigaKood() {
            return ttSonum2IvigaKood;
        }

        /**
         * Sets the value of the ttSonum2IvigaKood property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setTtSonum2IvigaKood(BigInteger value) {
            this.ttSonum2IvigaKood = value;
        }

        /**
         * Gets the value of the ttSonum2CvigaTekst property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTtSonum2CvigaTekst() {
            return ttSonum2CvigaTekst;
        }

        /**
         * Sets the value of the ttSonum2CvigaTekst property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTtSonum2CvigaTekst(String value) {
            this.ttSonum2CvigaTekst = value;
        }

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
     *         &lt;element name="ttSonum2_a.dtellimusnr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="ttSonum2_a.dlaps_kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="ttSonum2_a.dema_ikood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="ttSonum2_a.cema_pere" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="ttSonum2_a.cema_ees" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="ttSonum2_a.iriik" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
     *         &lt;element name="ttSonum2_a.imaak" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
     *         &lt;element name="ttSonum2_a.ivald" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
     *         &lt;element name="ttSonum2_a.iasula" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
     *         &lt;element name="ttSonum2_a.caadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="ttSonum2_a.cVkpv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="ttSonum2_a.iviga_kood" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
     *         &lt;element name="ttSonum2_a.cviga_tekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
        "ttSonum2ADtellimusnr",
        "ttSonum2ADlapsKood",
        "ttSonum2ADemaIkood",
        "ttSonum2ACemaPere",
        "ttSonum2ACemaEes",
        "ttSonum2AIriik",
        "ttSonum2AImaak",
        "ttSonum2AIvald",
        "ttSonum2AIasula",
        "ttSonum2ACaadress",
        "ttSonum2ACVkpv",
        "ttSonum2AIvigaKood",
        "ttSonum2ACvigaTekst"
    })
    public static class TtSonum2A {

        @XmlElement(name = "ttSonum2_a.dtellimusnr")
        protected String ttSonum2ADtellimusnr;
        @XmlElement(name = "ttSonum2_a.dlaps_kood")
        protected String ttSonum2ADlapsKood;
        @XmlElement(name = "ttSonum2_a.dema_ikood")
        protected String ttSonum2ADemaIkood;
        @XmlElement(name = "ttSonum2_a.cema_pere")
        protected String ttSonum2ACemaPere;
        @XmlElement(name = "ttSonum2_a.cema_ees")
        protected String ttSonum2ACemaEes;
        @XmlElement(name = "ttSonum2_a.iriik")
        protected BigInteger ttSonum2AIriik;
        @XmlElement(name = "ttSonum2_a.imaak")
        protected BigInteger ttSonum2AImaak;
        @XmlElement(name = "ttSonum2_a.ivald")
        protected BigInteger ttSonum2AIvald;
        @XmlElement(name = "ttSonum2_a.iasula")
        protected BigInteger ttSonum2AIasula;
        @XmlElement(name = "ttSonum2_a.caadress")
        protected String ttSonum2ACaadress;
        @XmlElement(name = "ttSonum2_a.cVkpv")
        protected String ttSonum2ACVkpv;
        @XmlElement(name = "ttSonum2_a.iviga_kood")
        protected BigInteger ttSonum2AIvigaKood;
        @XmlElement(name = "ttSonum2_a.cviga_tekst")
        protected String ttSonum2ACvigaTekst;

        /**
         * Gets the value of the ttSonum2ADtellimusnr property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTtSonum2ADtellimusnr() {
            return ttSonum2ADtellimusnr;
        }

        /**
         * Sets the value of the ttSonum2ADtellimusnr property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTtSonum2ADtellimusnr(String value) {
            this.ttSonum2ADtellimusnr = value;
        }

        /**
         * Gets the value of the ttSonum2ADlapsKood property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTtSonum2ADlapsKood() {
            return ttSonum2ADlapsKood;
        }

        /**
         * Sets the value of the ttSonum2ADlapsKood property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTtSonum2ADlapsKood(String value) {
            this.ttSonum2ADlapsKood = value;
        }

        /**
         * Gets the value of the ttSonum2ADemaIkood property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTtSonum2ADemaIkood() {
            return ttSonum2ADemaIkood;
        }

        /**
         * Sets the value of the ttSonum2ADemaIkood property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTtSonum2ADemaIkood(String value) {
            this.ttSonum2ADemaIkood = value;
        }

        /**
         * Gets the value of the ttSonum2ACemaPere property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTtSonum2ACemaPere() {
            return ttSonum2ACemaPere;
        }

        /**
         * Sets the value of the ttSonum2ACemaPere property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTtSonum2ACemaPere(String value) {
            this.ttSonum2ACemaPere = value;
        }

        /**
         * Gets the value of the ttSonum2ACemaEes property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTtSonum2ACemaEes() {
            return ttSonum2ACemaEes;
        }

        /**
         * Sets the value of the ttSonum2ACemaEes property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTtSonum2ACemaEes(String value) {
            this.ttSonum2ACemaEes = value;
        }

        /**
         * Gets the value of the ttSonum2AIriik property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getTtSonum2AIriik() {
            return ttSonum2AIriik;
        }

        /**
         * Sets the value of the ttSonum2AIriik property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setTtSonum2AIriik(BigInteger value) {
            this.ttSonum2AIriik = value;
        }

        /**
         * Gets the value of the ttSonum2AImaak property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getTtSonum2AImaak() {
            return ttSonum2AImaak;
        }

        /**
         * Sets the value of the ttSonum2AImaak property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setTtSonum2AImaak(BigInteger value) {
            this.ttSonum2AImaak = value;
        }

        /**
         * Gets the value of the ttSonum2AIvald property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getTtSonum2AIvald() {
            return ttSonum2AIvald;
        }

        /**
         * Sets the value of the ttSonum2AIvald property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setTtSonum2AIvald(BigInteger value) {
            this.ttSonum2AIvald = value;
        }

        /**
         * Gets the value of the ttSonum2AIasula property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getTtSonum2AIasula() {
            return ttSonum2AIasula;
        }

        /**
         * Sets the value of the ttSonum2AIasula property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setTtSonum2AIasula(BigInteger value) {
            this.ttSonum2AIasula = value;
        }

        /**
         * Gets the value of the ttSonum2ACaadress property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTtSonum2ACaadress() {
            return ttSonum2ACaadress;
        }

        /**
         * Sets the value of the ttSonum2ACaadress property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTtSonum2ACaadress(String value) {
            this.ttSonum2ACaadress = value;
        }

        /**
         * Gets the value of the ttSonum2ACVkpv property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTtSonum2ACVkpv() {
            return ttSonum2ACVkpv;
        }

        /**
         * Sets the value of the ttSonum2ACVkpv property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTtSonum2ACVkpv(String value) {
            this.ttSonum2ACVkpv = value;
        }

        /**
         * Gets the value of the ttSonum2AIvigaKood property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getTtSonum2AIvigaKood() {
            return ttSonum2AIvigaKood;
        }

        /**
         * Sets the value of the ttSonum2AIvigaKood property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setTtSonum2AIvigaKood(BigInteger value) {
            this.ttSonum2AIvigaKood = value;
        }

        /**
         * Gets the value of the ttSonum2ACvigaTekst property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTtSonum2ACvigaTekst() {
            return ttSonum2ACvigaTekst;
        }

        /**
         * Sets the value of the ttSonum2ACvigaTekst property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTtSonum2ACvigaTekst(String value) {
            this.ttSonum2ACvigaTekst = value;
        }

    }

}
