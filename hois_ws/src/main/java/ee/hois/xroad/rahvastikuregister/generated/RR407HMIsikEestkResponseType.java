
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR407HMIsikEestkResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR407HMIsikEestkResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikupnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikuenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SurmaKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="KodakondsusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="KodakondsusNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EmakeelKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EmakeelNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RiikKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MkKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Maakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ValdKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Vald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AsulaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Asula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Maja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Korter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EndineRiikKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EndineRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EndineMkKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EndineMaakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EndineValdKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EndineVald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EndineAsulaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EndineAsula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EndineTanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EndineMaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EndineKorter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EndineElukohtAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EndineElukohtLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EestkosteOn" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="ElamislubaOn" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="Veakood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="Veatekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR407HMIsikEestkResponseType", propOrder = {
    "isikukood",
    "isikupnimi",
    "isikuenimi",
    "surmaKuup",
    "kodakondsusKood",
    "kodakondsusNimetus",
    "emakeelKood",
    "emakeelNimetus",
    "riikKood",
    "riik",
    "mkKood",
    "maakond",
    "valdKood",
    "vald",
    "asulaKood",
    "asula",
    "tanav",
    "maja",
    "korter",
    "endineRiikKood",
    "endineRiik",
    "endineMkKood",
    "endineMaakond",
    "endineValdKood",
    "endineVald",
    "endineAsulaKood",
    "endineAsula",
    "endineTanav",
    "endineMaja",
    "endineKorter",
    "endineElukohtAlgus",
    "endineElukohtLopp",
    "eestkosteOn",
    "elamislubaOn",
    "veakood",
    "veatekst"
})
public class RR407HMIsikEestkResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Isikukood", required = true)
    protected String isikukood;
    @XmlElement(name = "Isikupnimi", required = true)
    protected String isikupnimi;
    @XmlElement(name = "Isikuenimi", required = true)
    protected String isikuenimi;
    @XmlElement(name = "SurmaKuup", required = true)
    protected String surmaKuup;
    @XmlElement(name = "KodakondsusKood", required = true)
    protected String kodakondsusKood;
    @XmlElement(name = "KodakondsusNimetus", required = true)
    protected String kodakondsusNimetus;
    @XmlElement(name = "EmakeelKood", required = true)
    protected String emakeelKood;
    @XmlElement(name = "EmakeelNimetus", required = true)
    protected String emakeelNimetus;
    @XmlElement(name = "RiikKood", required = true)
    protected String riikKood;
    @XmlElement(name = "Riik", required = true)
    protected String riik;
    @XmlElement(name = "MkKood", required = true)
    protected String mkKood;
    @XmlElement(name = "Maakond", required = true)
    protected String maakond;
    @XmlElement(name = "ValdKood", required = true)
    protected String valdKood;
    @XmlElement(name = "Vald", required = true)
    protected String vald;
    @XmlElement(name = "AsulaKood", required = true)
    protected String asulaKood;
    @XmlElement(name = "Asula", required = true)
    protected String asula;
    @XmlElement(name = "Tanav", required = true)
    protected String tanav;
    @XmlElement(name = "Maja", required = true)
    protected String maja;
    @XmlElement(name = "Korter", required = true)
    protected String korter;
    @XmlElement(name = "EndineRiikKood", required = true)
    protected String endineRiikKood;
    @XmlElement(name = "EndineRiik", required = true)
    protected String endineRiik;
    @XmlElement(name = "EndineMkKood", required = true)
    protected String endineMkKood;
    @XmlElement(name = "EndineMaakond", required = true)
    protected String endineMaakond;
    @XmlElement(name = "EndineValdKood", required = true)
    protected String endineValdKood;
    @XmlElement(name = "EndineVald", required = true)
    protected String endineVald;
    @XmlElement(name = "EndineAsulaKood", required = true)
    protected String endineAsulaKood;
    @XmlElement(name = "EndineAsula", required = true)
    protected String endineAsula;
    @XmlElement(name = "EndineTanav", required = true)
    protected String endineTanav;
    @XmlElement(name = "EndineMaja", required = true)
    protected String endineMaja;
    @XmlElement(name = "EndineKorter", required = true)
    protected String endineKorter;
    @XmlElement(name = "EndineElukohtAlgus", required = true)
    protected String endineElukohtAlgus;
    @XmlElement(name = "EndineElukohtLopp", required = true)
    protected String endineElukohtLopp;
    @XmlElement(name = "EestkosteOn", required = true)
    protected BigInteger eestkosteOn;
    @XmlElement(name = "ElamislubaOn", required = true)
    protected BigInteger elamislubaOn;
    @XmlElement(name = "Veakood", required = true)
    protected BigInteger veakood;
    @XmlElement(name = "Veatekst", required = true)
    protected String veatekst;

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
     * Gets the value of the isikupnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikupnimi() {
        return isikupnimi;
    }

    /**
     * Sets the value of the isikupnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikupnimi(String value) {
        this.isikupnimi = value;
    }

    /**
     * Gets the value of the isikuenimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuenimi() {
        return isikuenimi;
    }

    /**
     * Sets the value of the isikuenimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuenimi(String value) {
        this.isikuenimi = value;
    }

    /**
     * Gets the value of the surmaKuup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSurmaKuup() {
        return surmaKuup;
    }

    /**
     * Sets the value of the surmaKuup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSurmaKuup(String value) {
        this.surmaKuup = value;
    }

    /**
     * Gets the value of the kodakondsusKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKodakondsusKood() {
        return kodakondsusKood;
    }

    /**
     * Sets the value of the kodakondsusKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKodakondsusKood(String value) {
        this.kodakondsusKood = value;
    }

    /**
     * Gets the value of the kodakondsusNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKodakondsusNimetus() {
        return kodakondsusNimetus;
    }

    /**
     * Sets the value of the kodakondsusNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKodakondsusNimetus(String value) {
        this.kodakondsusNimetus = value;
    }

    /**
     * Gets the value of the emakeelKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmakeelKood() {
        return emakeelKood;
    }

    /**
     * Sets the value of the emakeelKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmakeelKood(String value) {
        this.emakeelKood = value;
    }

    /**
     * Gets the value of the emakeelNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmakeelNimetus() {
        return emakeelNimetus;
    }

    /**
     * Sets the value of the emakeelNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmakeelNimetus(String value) {
        this.emakeelNimetus = value;
    }

    /**
     * Gets the value of the riikKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiikKood() {
        return riikKood;
    }

    /**
     * Sets the value of the riikKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiikKood(String value) {
        this.riikKood = value;
    }

    /**
     * Gets the value of the riik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiik() {
        return riik;
    }

    /**
     * Sets the value of the riik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiik(String value) {
        this.riik = value;
    }

    /**
     * Gets the value of the mkKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMkKood() {
        return mkKood;
    }

    /**
     * Sets the value of the mkKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMkKood(String value) {
        this.mkKood = value;
    }

    /**
     * Gets the value of the maakond property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaakond() {
        return maakond;
    }

    /**
     * Sets the value of the maakond property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaakond(String value) {
        this.maakond = value;
    }

    /**
     * Gets the value of the valdKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValdKood() {
        return valdKood;
    }

    /**
     * Sets the value of the valdKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValdKood(String value) {
        this.valdKood = value;
    }

    /**
     * Gets the value of the vald property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVald() {
        return vald;
    }

    /**
     * Sets the value of the vald property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVald(String value) {
        this.vald = value;
    }

    /**
     * Gets the value of the asulaKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsulaKood() {
        return asulaKood;
    }

    /**
     * Sets the value of the asulaKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsulaKood(String value) {
        this.asulaKood = value;
    }

    /**
     * Gets the value of the asula property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsula() {
        return asula;
    }

    /**
     * Sets the value of the asula property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsula(String value) {
        this.asula = value;
    }

    /**
     * Gets the value of the tanav property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTanav() {
        return tanav;
    }

    /**
     * Sets the value of the tanav property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTanav(String value) {
        this.tanav = value;
    }

    /**
     * Gets the value of the maja property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaja() {
        return maja;
    }

    /**
     * Sets the value of the maja property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaja(String value) {
        this.maja = value;
    }

    /**
     * Gets the value of the korter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKorter() {
        return korter;
    }

    /**
     * Sets the value of the korter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKorter(String value) {
        this.korter = value;
    }

    /**
     * Gets the value of the endineRiikKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndineRiikKood() {
        return endineRiikKood;
    }

    /**
     * Sets the value of the endineRiikKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndineRiikKood(String value) {
        this.endineRiikKood = value;
    }

    /**
     * Gets the value of the endineRiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndineRiik() {
        return endineRiik;
    }

    /**
     * Sets the value of the endineRiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndineRiik(String value) {
        this.endineRiik = value;
    }

    /**
     * Gets the value of the endineMkKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndineMkKood() {
        return endineMkKood;
    }

    /**
     * Sets the value of the endineMkKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndineMkKood(String value) {
        this.endineMkKood = value;
    }

    /**
     * Gets the value of the endineMaakond property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndineMaakond() {
        return endineMaakond;
    }

    /**
     * Sets the value of the endineMaakond property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndineMaakond(String value) {
        this.endineMaakond = value;
    }

    /**
     * Gets the value of the endineValdKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndineValdKood() {
        return endineValdKood;
    }

    /**
     * Sets the value of the endineValdKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndineValdKood(String value) {
        this.endineValdKood = value;
    }

    /**
     * Gets the value of the endineVald property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndineVald() {
        return endineVald;
    }

    /**
     * Sets the value of the endineVald property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndineVald(String value) {
        this.endineVald = value;
    }

    /**
     * Gets the value of the endineAsulaKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndineAsulaKood() {
        return endineAsulaKood;
    }

    /**
     * Sets the value of the endineAsulaKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndineAsulaKood(String value) {
        this.endineAsulaKood = value;
    }

    /**
     * Gets the value of the endineAsula property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndineAsula() {
        return endineAsula;
    }

    /**
     * Sets the value of the endineAsula property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndineAsula(String value) {
        this.endineAsula = value;
    }

    /**
     * Gets the value of the endineTanav property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndineTanav() {
        return endineTanav;
    }

    /**
     * Sets the value of the endineTanav property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndineTanav(String value) {
        this.endineTanav = value;
    }

    /**
     * Gets the value of the endineMaja property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndineMaja() {
        return endineMaja;
    }

    /**
     * Sets the value of the endineMaja property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndineMaja(String value) {
        this.endineMaja = value;
    }

    /**
     * Gets the value of the endineKorter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndineKorter() {
        return endineKorter;
    }

    /**
     * Sets the value of the endineKorter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndineKorter(String value) {
        this.endineKorter = value;
    }

    /**
     * Gets the value of the endineElukohtAlgus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndineElukohtAlgus() {
        return endineElukohtAlgus;
    }

    /**
     * Sets the value of the endineElukohtAlgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndineElukohtAlgus(String value) {
        this.endineElukohtAlgus = value;
    }

    /**
     * Gets the value of the endineElukohtLopp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndineElukohtLopp() {
        return endineElukohtLopp;
    }

    /**
     * Sets the value of the endineElukohtLopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndineElukohtLopp(String value) {
        this.endineElukohtLopp = value;
    }

    /**
     * Gets the value of the eestkosteOn property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEestkosteOn() {
        return eestkosteOn;
    }

    /**
     * Sets the value of the eestkosteOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEestkosteOn(BigInteger value) {
        this.eestkosteOn = value;
    }

    /**
     * Gets the value of the elamislubaOn property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getElamislubaOn() {
        return elamislubaOn;
    }

    /**
     * Sets the value of the elamislubaOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setElamislubaOn(BigInteger value) {
        this.elamislubaOn = value;
    }

    /**
     * Gets the value of the veakood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVeakood() {
        return veakood;
    }

    /**
     * Sets the value of the veakood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVeakood(BigInteger value) {
        this.veakood = value;
    }

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

}
