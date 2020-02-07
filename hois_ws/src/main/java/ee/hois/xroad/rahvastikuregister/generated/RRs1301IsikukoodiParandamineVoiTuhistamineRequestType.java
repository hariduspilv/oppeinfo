
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRs1301IsikukoodiParandamineVoiTuhistamineRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRs1301IsikukoodiParandamineVoiTuhistamineRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ikirje_tyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ctellimusnr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cisikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ctlaps_skpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="claps_saeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="claps_sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cema_ikood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cema_pere" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cema_ees" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="iema_riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRs1301IsikukoodiParandamineVoiTuhistamineRequestType", propOrder = {
    "ikirjeTyyp",
    "ctellimusnr",
    "cisikukood",
    "ctlapsSkpv",
    "clapsSaeg",
    "clapsSugu",
    "cemaIkood",
    "cemaPere",
    "cemaEes",
    "iemaRiik"
})
public class RRs1301IsikukoodiParandamineVoiTuhistamineRequestType {

    @XmlElement(name = "ikirje_tyyp", required = true)
    protected String ikirjeTyyp;
    @XmlElement(required = true)
    protected String ctellimusnr;
    @XmlElement(required = true)
    protected String cisikukood;
    @XmlElement(name = "ctlaps_skpv", required = true)
    protected String ctlapsSkpv;
    @XmlElement(name = "claps_saeg", required = true)
    protected String clapsSaeg;
    @XmlElement(name = "claps_sugu", required = true)
    protected String clapsSugu;
    @XmlElement(name = "cema_ikood", required = true)
    protected String cemaIkood;
    @XmlElement(name = "cema_pere", required = true)
    protected String cemaPere;
    @XmlElement(name = "cema_ees", required = true)
    protected String cemaEes;
    @XmlElement(name = "iema_riik", required = true)
    protected String iemaRiik;

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
     * Gets the value of the cisikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCisikukood() {
        return cisikukood;
    }

    /**
     * Sets the value of the cisikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCisikukood(String value) {
        this.cisikukood = value;
    }

    /**
     * Gets the value of the ctlapsSkpv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCtlapsSkpv() {
        return ctlapsSkpv;
    }

    /**
     * Sets the value of the ctlapsSkpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCtlapsSkpv(String value) {
        this.ctlapsSkpv = value;
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
     * Gets the value of the clapsSugu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClapsSugu() {
        return clapsSugu;
    }

    /**
     * Sets the value of the clapsSugu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClapsSugu(String value) {
        this.clapsSugu = value;
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
     * Gets the value of the iemaRiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIemaRiik() {
        return iemaRiik;
    }

    /**
     * Sets the value of the iemaRiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIemaRiik(String value) {
        this.iemaRiik = value;
    }

}
