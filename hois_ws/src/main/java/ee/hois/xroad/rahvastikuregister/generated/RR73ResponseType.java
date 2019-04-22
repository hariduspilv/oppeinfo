
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR73ResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR73ResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="outIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Aadress" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="outMK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="outMKnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="outVK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="outVKnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="outAK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="outAKnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="outTanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="outMaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="outKorter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="outAadrTXT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="AadressiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="outValRK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outValRKkirjeldus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outValJSK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outValJSKnm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outJkNrNimekiri" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outValOV" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outValOVnm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outValHAALET" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outValEELHAALET" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outValMKinfo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outValLisainfo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="poiVeaKood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="pocVeaTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="VR"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="VRandmed" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="VRandmed.Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="VRandmed.Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="VRandmed.Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="VRandmed.Riiknm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="VRandmed.KiriTaot" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="VRandmed.KiriAadr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="VRandmed.KiriKP" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="VRandmed.KiriValKP" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="VRandmed.Haalet" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="VRandmed.HaaletAadr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="VRandmed.HaaletAjad" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="VRandmed.Kontakt" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="VRandmed.KontaktAnd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR73ResponseType", propOrder = {
    "outIsikukood",
    "outPerenimi",
    "outEesnimi",
    "aadress",
    "outValRK",
    "outValRKkirjeldus",
    "outValJSK",
    "outValJSKnm",
    "outJkNrNimekiri",
    "outValOV",
    "outValOVnm",
    "outValHAALET",
    "outValEELHAALET",
    "outValMKinfo",
    "outValLisainfo",
    "poiVeaKood",
    "pocVeaTekst",
    "vr"
})
public class RR73ResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(required = true)
    protected String outIsikukood;
    @XmlElement(required = true)
    protected String outPerenimi;
    @XmlElement(required = true)
    protected String outEesnimi;
    @XmlElement(name = "Aadress")
    protected List<RR73ResponseType.Aadress> aadress;
    @XmlElement(required = true)
    protected String outValRK;
    @XmlElement(required = true)
    protected String outValRKkirjeldus;
    @XmlElement(required = true)
    protected String outValJSK;
    @XmlElement(required = true)
    protected String outValJSKnm;
    @XmlElement(required = true)
    protected String outJkNrNimekiri;
    @XmlElement(required = true)
    protected String outValOV;
    @XmlElement(required = true)
    protected String outValOVnm;
    @XmlElement(required = true)
    protected String outValHAALET;
    @XmlElement(required = true)
    protected String outValEELHAALET;
    @XmlElement(required = true)
    protected String outValMKinfo;
    @XmlElement(required = true)
    protected String outValLisainfo;
    @XmlElement(required = true)
    protected BigInteger poiVeaKood;
    @XmlElement(required = true)
    protected String pocVeaTekst;
    @XmlElement(name = "VR", required = true)
    protected RR73ResponseType.VR vr;

    /**
     * Gets the value of the outIsikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutIsikukood() {
        return outIsikukood;
    }

    /**
     * Sets the value of the outIsikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutIsikukood(String value) {
        this.outIsikukood = value;
    }

    /**
     * Gets the value of the outPerenimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutPerenimi() {
        return outPerenimi;
    }

    /**
     * Sets the value of the outPerenimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutPerenimi(String value) {
        this.outPerenimi = value;
    }

    /**
     * Gets the value of the outEesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutEesnimi() {
        return outEesnimi;
    }

    /**
     * Sets the value of the outEesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutEesnimi(String value) {
        this.outEesnimi = value;
    }

    /**
     * Gets the value of the aadress property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the aadress property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAadress().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RR73ResponseType.Aadress }
     * 
     * 
     */
    public List<RR73ResponseType.Aadress> getAadress() {
        if (aadress == null) {
            aadress = new ArrayList<RR73ResponseType.Aadress>();
        }
        return this.aadress;
    }

    /**
     * Gets the value of the outValRK property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutValRK() {
        return outValRK;
    }

    /**
     * Sets the value of the outValRK property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutValRK(String value) {
        this.outValRK = value;
    }

    /**
     * Gets the value of the outValRKkirjeldus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutValRKkirjeldus() {
        return outValRKkirjeldus;
    }

    /**
     * Sets the value of the outValRKkirjeldus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutValRKkirjeldus(String value) {
        this.outValRKkirjeldus = value;
    }

    /**
     * Gets the value of the outValJSK property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutValJSK() {
        return outValJSK;
    }

    /**
     * Sets the value of the outValJSK property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutValJSK(String value) {
        this.outValJSK = value;
    }

    /**
     * Gets the value of the outValJSKnm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutValJSKnm() {
        return outValJSKnm;
    }

    /**
     * Sets the value of the outValJSKnm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutValJSKnm(String value) {
        this.outValJSKnm = value;
    }

    /**
     * Gets the value of the outJkNrNimekiri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutJkNrNimekiri() {
        return outJkNrNimekiri;
    }

    /**
     * Sets the value of the outJkNrNimekiri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutJkNrNimekiri(String value) {
        this.outJkNrNimekiri = value;
    }

    /**
     * Gets the value of the outValOV property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutValOV() {
        return outValOV;
    }

    /**
     * Sets the value of the outValOV property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutValOV(String value) {
        this.outValOV = value;
    }

    /**
     * Gets the value of the outValOVnm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutValOVnm() {
        return outValOVnm;
    }

    /**
     * Sets the value of the outValOVnm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutValOVnm(String value) {
        this.outValOVnm = value;
    }

    /**
     * Gets the value of the outValHAALET property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutValHAALET() {
        return outValHAALET;
    }

    /**
     * Sets the value of the outValHAALET property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutValHAALET(String value) {
        this.outValHAALET = value;
    }

    /**
     * Gets the value of the outValEELHAALET property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutValEELHAALET() {
        return outValEELHAALET;
    }

    /**
     * Sets the value of the outValEELHAALET property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutValEELHAALET(String value) {
        this.outValEELHAALET = value;
    }

    /**
     * Gets the value of the outValMKinfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutValMKinfo() {
        return outValMKinfo;
    }

    /**
     * Sets the value of the outValMKinfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutValMKinfo(String value) {
        this.outValMKinfo = value;
    }

    /**
     * Gets the value of the outValLisainfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutValLisainfo() {
        return outValLisainfo;
    }

    /**
     * Sets the value of the outValLisainfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutValLisainfo(String value) {
        this.outValLisainfo = value;
    }

    /**
     * Gets the value of the poiVeaKood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPoiVeaKood() {
        return poiVeaKood;
    }

    /**
     * Sets the value of the poiVeaKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPoiVeaKood(BigInteger value) {
        this.poiVeaKood = value;
    }

    /**
     * Gets the value of the pocVeaTekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPocVeaTekst() {
        return pocVeaTekst;
    }

    /**
     * Sets the value of the pocVeaTekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPocVeaTekst(String value) {
        this.pocVeaTekst = value;
    }

    /**
     * Gets the value of the vr property.
     * 
     * @return
     *     possible object is
     *     {@link RR73ResponseType.VR }
     *     
     */
    public RR73ResponseType.VR getVR() {
        return vr;
    }

    /**
     * Sets the value of the vr property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR73ResponseType.VR }
     *     
     */
    public void setVR(RR73ResponseType.VR value) {
        this.vr = value;
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
     *         &lt;element name="outMK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="outMKnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="outVK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="outVKnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="outAK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="outAKnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="outTanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="outMaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="outKorter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="outAadrTXT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="AadressiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "outMK",
        "outMKnimi",
        "outVK",
        "outVKnimi",
        "outAK",
        "outAKnimi",
        "outTanav",
        "outMaja",
        "outKorter",
        "outAadrTXT",
        "aadressiLiik"
    })
    public static class Aadress {

        @XmlElement(required = true)
        protected String outMK;
        @XmlElement(required = true)
        protected String outMKnimi;
        @XmlElement(required = true)
        protected String outVK;
        @XmlElement(required = true)
        protected String outVKnimi;
        @XmlElement(required = true)
        protected String outAK;
        @XmlElement(required = true)
        protected String outAKnimi;
        @XmlElement(required = true)
        protected String outTanav;
        @XmlElement(required = true)
        protected String outMaja;
        @XmlElement(required = true)
        protected String outKorter;
        @XmlElement(required = true)
        protected String outAadrTXT;
        @XmlElement(name = "AadressiLiik", required = true)
        protected String aadressiLiik;

        /**
         * Gets the value of the outMK property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOutMK() {
            return outMK;
        }

        /**
         * Sets the value of the outMK property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOutMK(String value) {
            this.outMK = value;
        }

        /**
         * Gets the value of the outMKnimi property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOutMKnimi() {
            return outMKnimi;
        }

        /**
         * Sets the value of the outMKnimi property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOutMKnimi(String value) {
            this.outMKnimi = value;
        }

        /**
         * Gets the value of the outVK property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOutVK() {
            return outVK;
        }

        /**
         * Sets the value of the outVK property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOutVK(String value) {
            this.outVK = value;
        }

        /**
         * Gets the value of the outVKnimi property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOutVKnimi() {
            return outVKnimi;
        }

        /**
         * Sets the value of the outVKnimi property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOutVKnimi(String value) {
            this.outVKnimi = value;
        }

        /**
         * Gets the value of the outAK property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOutAK() {
            return outAK;
        }

        /**
         * Sets the value of the outAK property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOutAK(String value) {
            this.outAK = value;
        }

        /**
         * Gets the value of the outAKnimi property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOutAKnimi() {
            return outAKnimi;
        }

        /**
         * Sets the value of the outAKnimi property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOutAKnimi(String value) {
            this.outAKnimi = value;
        }

        /**
         * Gets the value of the outTanav property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOutTanav() {
            return outTanav;
        }

        /**
         * Sets the value of the outTanav property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOutTanav(String value) {
            this.outTanav = value;
        }

        /**
         * Gets the value of the outMaja property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOutMaja() {
            return outMaja;
        }

        /**
         * Sets the value of the outMaja property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOutMaja(String value) {
            this.outMaja = value;
        }

        /**
         * Gets the value of the outKorter property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOutKorter() {
            return outKorter;
        }

        /**
         * Sets the value of the outKorter property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOutKorter(String value) {
            this.outKorter = value;
        }

        /**
         * Gets the value of the outAadrTXT property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOutAadrTXT() {
            return outAadrTXT;
        }

        /**
         * Sets the value of the outAadrTXT property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOutAadrTXT(String value) {
            this.outAadrTXT = value;
        }

        /**
         * Gets the value of the aadressiLiik property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAadressiLiik() {
            return aadressiLiik;
        }

        /**
         * Sets the value of the aadressiLiik property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAadressiLiik(String value) {
            this.aadressiLiik = value;
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
     *         &lt;element name="VRandmed" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="VRandmed.Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="VRandmed.Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="VRandmed.Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="VRandmed.Riiknm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="VRandmed.KiriTaot" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="VRandmed.KiriAadr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="VRandmed.KiriKP" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="VRandmed.KiriValKP" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="VRandmed.Haalet" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="VRandmed.HaaletAadr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="VRandmed.HaaletAjad" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="VRandmed.Kontakt" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="VRandmed.KontaktAnd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "vRandmed"
    })
    public static class VR {

        @XmlElement(name = "VRandmed")
        protected List<RR73ResponseType.VR.VRandmed> vRandmed;

        /**
         * Gets the value of the vRandmed property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the vRandmed property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getVRandmed().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR73ResponseType.VR.VRandmed }
         * 
         * 
         */
        public List<RR73ResponseType.VR.VRandmed> getVRandmed() {
            if (vRandmed == null) {
                vRandmed = new ArrayList<RR73ResponseType.VR.VRandmed>();
            }
            return this.vRandmed;
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
         *         &lt;element name="VRandmed.Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="VRandmed.Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="VRandmed.Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="VRandmed.Riiknm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="VRandmed.KiriTaot" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="VRandmed.KiriAadr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="VRandmed.KiriKP" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="VRandmed.KiriValKP" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="VRandmed.Haalet" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="VRandmed.HaaletAadr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="VRandmed.HaaletAjad" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="VRandmed.Kontakt" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="VRandmed.KontaktAnd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "vRandmedKood",
            "vRandmedNimetus",
            "vRandmedRiik",
            "vRandmedRiiknm",
            "vRandmedKiriTaot",
            "vRandmedKiriAadr",
            "vRandmedKiriKP",
            "vRandmedKiriValKP",
            "vRandmedHaalet",
            "vRandmedHaaletAadr",
            "vRandmedHaaletAjad",
            "vRandmedKontakt",
            "vRandmedKontaktAnd"
        })
        public static class VRandmed {

            @XmlElement(name = "VRandmed.Kood", required = true)
            protected String vRandmedKood;
            @XmlElement(name = "VRandmed.Nimetus", required = true)
            protected String vRandmedNimetus;
            @XmlElement(name = "VRandmed.Riik", required = true)
            protected String vRandmedRiik;
            @XmlElement(name = "VRandmed.Riiknm", required = true)
            protected String vRandmedRiiknm;
            @XmlElement(name = "VRandmed.KiriTaot", required = true)
            protected String vRandmedKiriTaot;
            @XmlElement(name = "VRandmed.KiriAadr", required = true)
            protected String vRandmedKiriAadr;
            @XmlElement(name = "VRandmed.KiriKP", required = true)
            protected String vRandmedKiriKP;
            @XmlElement(name = "VRandmed.KiriValKP", required = true)
            protected String vRandmedKiriValKP;
            @XmlElement(name = "VRandmed.Haalet", required = true)
            protected String vRandmedHaalet;
            @XmlElement(name = "VRandmed.HaaletAadr", required = true)
            protected String vRandmedHaaletAadr;
            @XmlElement(name = "VRandmed.HaaletAjad", required = true)
            protected String vRandmedHaaletAjad;
            @XmlElement(name = "VRandmed.Kontakt", required = true)
            protected String vRandmedKontakt;
            @XmlElement(name = "VRandmed.KontaktAnd", required = true)
            protected String vRandmedKontaktAnd;

            /**
             * Gets the value of the vRandmedKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getVRandmedKood() {
                return vRandmedKood;
            }

            /**
             * Sets the value of the vRandmedKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setVRandmedKood(String value) {
                this.vRandmedKood = value;
            }

            /**
             * Gets the value of the vRandmedNimetus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getVRandmedNimetus() {
                return vRandmedNimetus;
            }

            /**
             * Sets the value of the vRandmedNimetus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setVRandmedNimetus(String value) {
                this.vRandmedNimetus = value;
            }

            /**
             * Gets the value of the vRandmedRiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getVRandmedRiik() {
                return vRandmedRiik;
            }

            /**
             * Sets the value of the vRandmedRiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setVRandmedRiik(String value) {
                this.vRandmedRiik = value;
            }

            /**
             * Gets the value of the vRandmedRiiknm property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getVRandmedRiiknm() {
                return vRandmedRiiknm;
            }

            /**
             * Sets the value of the vRandmedRiiknm property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setVRandmedRiiknm(String value) {
                this.vRandmedRiiknm = value;
            }

            /**
             * Gets the value of the vRandmedKiriTaot property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getVRandmedKiriTaot() {
                return vRandmedKiriTaot;
            }

            /**
             * Sets the value of the vRandmedKiriTaot property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setVRandmedKiriTaot(String value) {
                this.vRandmedKiriTaot = value;
            }

            /**
             * Gets the value of the vRandmedKiriAadr property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getVRandmedKiriAadr() {
                return vRandmedKiriAadr;
            }

            /**
             * Sets the value of the vRandmedKiriAadr property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setVRandmedKiriAadr(String value) {
                this.vRandmedKiriAadr = value;
            }

            /**
             * Gets the value of the vRandmedKiriKP property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getVRandmedKiriKP() {
                return vRandmedKiriKP;
            }

            /**
             * Sets the value of the vRandmedKiriKP property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setVRandmedKiriKP(String value) {
                this.vRandmedKiriKP = value;
            }

            /**
             * Gets the value of the vRandmedKiriValKP property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getVRandmedKiriValKP() {
                return vRandmedKiriValKP;
            }

            /**
             * Sets the value of the vRandmedKiriValKP property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setVRandmedKiriValKP(String value) {
                this.vRandmedKiriValKP = value;
            }

            /**
             * Gets the value of the vRandmedHaalet property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getVRandmedHaalet() {
                return vRandmedHaalet;
            }

            /**
             * Sets the value of the vRandmedHaalet property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setVRandmedHaalet(String value) {
                this.vRandmedHaalet = value;
            }

            /**
             * Gets the value of the vRandmedHaaletAadr property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getVRandmedHaaletAadr() {
                return vRandmedHaaletAadr;
            }

            /**
             * Sets the value of the vRandmedHaaletAadr property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setVRandmedHaaletAadr(String value) {
                this.vRandmedHaaletAadr = value;
            }

            /**
             * Gets the value of the vRandmedHaaletAjad property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getVRandmedHaaletAjad() {
                return vRandmedHaaletAjad;
            }

            /**
             * Sets the value of the vRandmedHaaletAjad property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setVRandmedHaaletAjad(String value) {
                this.vRandmedHaaletAjad = value;
            }

            /**
             * Gets the value of the vRandmedKontakt property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getVRandmedKontakt() {
                return vRandmedKontakt;
            }

            /**
             * Sets the value of the vRandmedKontakt property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setVRandmedKontakt(String value) {
                this.vRandmedKontakt = value;
            }

            /**
             * Gets the value of the vRandmedKontaktAnd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getVRandmedKontaktAnd() {
                return vRandmedKontaktAnd;
            }

            /**
             * Sets the value of the vRandmedKontaktAnd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setVRandmedKontaktAnd(String value) {
                this.vRandmedKontaktAnd = value;
            }

        }

    }

}
