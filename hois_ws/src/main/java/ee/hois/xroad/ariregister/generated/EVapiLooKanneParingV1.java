
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EVapiLooKanneParing_v1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EVapiLooKanneParing_v1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="partner_auth" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="lisa_inglk_dokumendid" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="kande_id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="on_paranduskanne" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="eelmine_menetlus_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="esitaja_nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="esitaja_kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="viitenumber_loiv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kande_sisu" type="{http://arireg.x-road.eu/producer/}EVapiLooKanneKandeSisu_v1"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EVapiLooKanneParing_v1", propOrder = {
    "partnerAuth",
    "lisaInglkDokumendid",
    "kandeId",
    "onParanduskanne",
    "eelmineMenetlusId",
    "esitajaNimi",
    "esitajaKood",
    "viitenumberLoiv",
    "kandeSisu"
})
public class EVapiLooKanneParingV1 {

    @XmlElement(name = "partner_auth", required = true)
    protected String partnerAuth;
    @XmlElement(name = "lisa_inglk_dokumendid")
    protected Boolean lisaInglkDokumendid;
    @XmlElement(name = "kande_id", required = true)
    protected String kandeId;
    @XmlElement(name = "on_paranduskanne")
    protected Boolean onParanduskanne;
    @XmlElement(name = "eelmine_menetlus_id")
    protected String eelmineMenetlusId;
    @XmlElement(name = "esitaja_nimi", required = true)
    protected String esitajaNimi;
    @XmlElement(name = "esitaja_kood", required = true)
    protected String esitajaKood;
    @XmlElement(name = "viitenumber_loiv", required = true)
    protected String viitenumberLoiv;
    @XmlElement(name = "kande_sisu", required = true)
    protected EVapiLooKanneKandeSisuV1 kandeSisu;

    /**
     * Gets the value of the partnerAuth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerAuth() {
        return partnerAuth;
    }

    /**
     * Sets the value of the partnerAuth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerAuth(String value) {
        this.partnerAuth = value;
    }

    /**
     * Gets the value of the lisaInglkDokumendid property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLisaInglkDokumendid() {
        return lisaInglkDokumendid;
    }

    /**
     * Sets the value of the lisaInglkDokumendid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLisaInglkDokumendid(Boolean value) {
        this.lisaInglkDokumendid = value;
    }

    /**
     * Gets the value of the kandeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKandeId() {
        return kandeId;
    }

    /**
     * Sets the value of the kandeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKandeId(String value) {
        this.kandeId = value;
    }

    /**
     * Gets the value of the onParanduskanne property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOnParanduskanne() {
        return onParanduskanne;
    }

    /**
     * Sets the value of the onParanduskanne property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOnParanduskanne(Boolean value) {
        this.onParanduskanne = value;
    }

    /**
     * Gets the value of the eelmineMenetlusId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEelmineMenetlusId() {
        return eelmineMenetlusId;
    }

    /**
     * Sets the value of the eelmineMenetlusId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEelmineMenetlusId(String value) {
        this.eelmineMenetlusId = value;
    }

    /**
     * Gets the value of the esitajaNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsitajaNimi() {
        return esitajaNimi;
    }

    /**
     * Sets the value of the esitajaNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsitajaNimi(String value) {
        this.esitajaNimi = value;
    }

    /**
     * Gets the value of the esitajaKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsitajaKood() {
        return esitajaKood;
    }

    /**
     * Sets the value of the esitajaKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsitajaKood(String value) {
        this.esitajaKood = value;
    }

    /**
     * Gets the value of the viitenumberLoiv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViitenumberLoiv() {
        return viitenumberLoiv;
    }

    /**
     * Sets the value of the viitenumberLoiv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViitenumberLoiv(String value) {
        this.viitenumberLoiv = value;
    }

    /**
     * Gets the value of the kandeSisu property.
     * 
     * @return
     *     possible object is
     *     {@link EVapiLooKanneKandeSisuV1 }
     *     
     */
    public EVapiLooKanneKandeSisuV1 getKandeSisu() {
        return kandeSisu;
    }

    /**
     * Sets the value of the kandeSisu property.
     * 
     * @param value
     *     allowed object is
     *     {@link EVapiLooKanneKandeSisuV1 }
     *     
     */
    public void setKandeSisu(EVapiLooKanneKandeSisuV1 value) {
        this.kandeSisu = value;
    }

}
