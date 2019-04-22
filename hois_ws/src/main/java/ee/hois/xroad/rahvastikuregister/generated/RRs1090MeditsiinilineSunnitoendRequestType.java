
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRs1090MeditsiinilineSunnitoendRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRs1090MeditsiinilineSunnitoendRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ikirje_tyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ctellimusnr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="claps_kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="claps_saeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cema_ikood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cema_pere" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cema_ees" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="iriik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="imaak" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ivald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="iasula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="caadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ctoend" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cttoend_kpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRs1090MeditsiinilineSunnitoendRequestType", propOrder = {
    "ikirjeTyyp",
    "ctellimusnr",
    "clapsKood",
    "clapsSaeg",
    "cemaIkood",
    "cemaPere",
    "cemaEes",
    "iriik",
    "imaak",
    "ivald",
    "iasula",
    "caadress",
    "ctoend",
    "cttoendKpv"
})
public class RRs1090MeditsiinilineSunnitoendRequestType {

    @XmlElement(name = "ikirje_tyyp", required = true)
    protected String ikirjeTyyp;
    @XmlElement(required = true)
    protected String ctellimusnr;
    @XmlElement(name = "claps_kood", required = true)
    protected String clapsKood;
    @XmlElement(name = "claps_saeg", required = true)
    protected String clapsSaeg;
    @XmlElement(name = "cema_ikood", required = true)
    protected String cemaIkood;
    @XmlElement(name = "cema_pere", required = true)
    protected String cemaPere;
    @XmlElement(name = "cema_ees", required = true)
    protected String cemaEes;
    @XmlElement(required = true)
    protected String iriik;
    @XmlElement(required = true)
    protected String imaak;
    @XmlElement(required = true)
    protected String ivald;
    @XmlElement(required = true)
    protected String iasula;
    @XmlElement(required = true)
    protected String caadress;
    @XmlElement(required = true)
    protected String ctoend;
    @XmlElement(name = "cttoend_kpv", required = true)
    protected String cttoendKpv;

    /**
     * Gets the value of the ikirjeTyyp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIkirjeTyyp() {
        return ikirjeTyyp;
    }

    /**
     * Sets the value of the ikirjeTyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIkirjeTyyp(String value) {
        this.ikirjeTyyp = value;
    }

    /**
     * Gets the value of the ctellimusnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCtellimusnr() {
        return ctellimusnr;
    }

    /**
     * Sets the value of the ctellimusnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCtellimusnr(String value) {
        this.ctellimusnr = value;
    }

    /**
     * Gets the value of the clapsKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClapsKood() {
        return clapsKood;
    }

    /**
     * Sets the value of the clapsKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClapsKood(String value) {
        this.clapsKood = value;
    }

    /**
     * Gets the value of the clapsSaeg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClapsSaeg() {
        return clapsSaeg;
    }

    /**
     * Sets the value of the clapsSaeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClapsSaeg(String value) {
        this.clapsSaeg = value;
    }

    /**
     * Gets the value of the cemaIkood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCemaIkood() {
        return cemaIkood;
    }

    /**
     * Sets the value of the cemaIkood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCemaIkood(String value) {
        this.cemaIkood = value;
    }

    /**
     * Gets the value of the cemaPere property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCemaPere() {
        return cemaPere;
    }

    /**
     * Sets the value of the cemaPere property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCemaPere(String value) {
        this.cemaPere = value;
    }

    /**
     * Gets the value of the cemaEes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCemaEes() {
        return cemaEes;
    }

    /**
     * Sets the value of the cemaEes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCemaEes(String value) {
        this.cemaEes = value;
    }

    /**
     * Gets the value of the iriik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIriik() {
        return iriik;
    }

    /**
     * Sets the value of the iriik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIriik(String value) {
        this.iriik = value;
    }

    /**
     * Gets the value of the imaak property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImaak() {
        return imaak;
    }

    /**
     * Sets the value of the imaak property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImaak(String value) {
        this.imaak = value;
    }

    /**
     * Gets the value of the ivald property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIvald() {
        return ivald;
    }

    /**
     * Sets the value of the ivald property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIvald(String value) {
        this.ivald = value;
    }

    /**
     * Gets the value of the iasula property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIasula() {
        return iasula;
    }

    /**
     * Sets the value of the iasula property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIasula(String value) {
        this.iasula = value;
    }

    /**
     * Gets the value of the caadress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCaadress() {
        return caadress;
    }

    /**
     * Sets the value of the caadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCaadress(String value) {
        this.caadress = value;
    }

    /**
     * Gets the value of the ctoend property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCtoend() {
        return ctoend;
    }

    /**
     * Sets the value of the ctoend property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCtoend(String value) {
        this.ctoend = value;
    }

    /**
     * Gets the value of the cttoendKpv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCttoendKpv() {
        return cttoendKpv;
    }

    /**
     * Sets the value of the cttoendKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCttoendKpv(String value) {
        this.cttoendKpv = value;
    }

}
