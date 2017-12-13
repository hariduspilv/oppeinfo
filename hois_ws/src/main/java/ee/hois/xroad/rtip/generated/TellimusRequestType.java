
package ee.hois.xroad.rtip.generated;

import java.time.LocalDate;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for tellimusRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tellimusRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AUART" type="{http://rtk-v6.x-road.eu}char4"/&gt;
 *         &lt;element name="BUDAT" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="CREDITREF" type="{http://rtk-v6.x-road.eu}char10" minOccurs="0"/&gt;
 *         &lt;element name="FKDAT" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="KIDNO" type="{http://rtk-v6.x-road.eu}char30" minOccurs="0"/&gt;
 *         &lt;element name="KUNNR" type="{http://rtk-v6.x-road.eu}char10"/&gt;
 *         &lt;element name="LINE_ITEMS" type="{http://rtk-v6.x-road.eu}ZepnOrderItems"/&gt;
 *         &lt;element name="SPART" type="{http://rtk-v6.x-road.eu}char2" minOccurs="0"/&gt;
 *         &lt;element name="VKBUR" type="{http://rtk-v6.x-road.eu}char4" minOccurs="0"/&gt;
 *         &lt;element name="VKORG" type="{http://rtk-v6.x-road.eu}char4" minOccurs="0"/&gt;
 *         &lt;element name="VTWEG" type="{http://rtk-v6.x-road.eu}char2" minOccurs="0"/&gt;
 *         &lt;element name="XBLNR" type="{http://rtk-v6.x-road.eu}char10" minOccurs="0"/&gt;
 *         &lt;element name="ZTERM" type="{http://rtk-v6.x-road.eu}char4"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tellimusRequestType", propOrder = {
    "auart",
    "budat",
    "creditref",
    "fkdat",
    "kidno",
    "kunnr",
    "lineitems",
    "spart",
    "vkbur",
    "vkorg",
    "vtweg",
    "xblnr",
    "zterm"
})
public class TellimusRequestType {

    @XmlElement(name = "AUART", required = true)
    protected String auart;
    @XmlElement(name = "BUDAT", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate budat;
    @XmlElement(name = "CREDITREF")
    protected String creditref;
    @XmlElement(name = "FKDAT", required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate fkdat;
    @XmlElement(name = "KIDNO")
    protected String kidno;
    @XmlElement(name = "KUNNR", required = true)
    protected String kunnr;
    @XmlElement(name = "LINE_ITEMS", required = true)
    protected ZepnOrderItems lineitems;
    @XmlElement(name = "SPART")
    protected String spart;
    @XmlElement(name = "VKBUR")
    protected String vkbur;
    @XmlElement(name = "VKORG")
    protected String vkorg;
    @XmlElement(name = "VTWEG")
    protected String vtweg;
    @XmlElement(name = "XBLNR")
    protected String xblnr;
    @XmlElement(name = "ZTERM", required = true)
    protected String zterm;

    /**
     * Gets the value of the auart property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAUART() {
        return auart;
    }

    /**
     * Sets the value of the auart property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAUART(String value) {
        this.auart = value;
    }

    /**
     * Gets the value of the budat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getBUDAT() {
        return budat;
    }

    /**
     * Sets the value of the budat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBUDAT(LocalDate value) {
        this.budat = value;
    }

    /**
     * Gets the value of the creditref property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCREDITREF() {
        return creditref;
    }

    /**
     * Sets the value of the creditref property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCREDITREF(String value) {
        this.creditref = value;
    }

    /**
     * Gets the value of the fkdat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getFKDAT() {
        return fkdat;
    }

    /**
     * Sets the value of the fkdat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFKDAT(LocalDate value) {
        this.fkdat = value;
    }

    /**
     * Gets the value of the kidno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKIDNO() {
        return kidno;
    }

    /**
     * Sets the value of the kidno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKIDNO(String value) {
        this.kidno = value;
    }

    /**
     * Gets the value of the kunnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKUNNR() {
        return kunnr;
    }

    /**
     * Sets the value of the kunnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKUNNR(String value) {
        this.kunnr = value;
    }

    /**
     * Gets the value of the lineitems property.
     * 
     * @return
     *     possible object is
     *     {@link ZepnOrderItems }
     *     
     */
    public ZepnOrderItems getLINEITEMS() {
        return lineitems;
    }

    /**
     * Sets the value of the lineitems property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZepnOrderItems }
     *     
     */
    public void setLINEITEMS(ZepnOrderItems value) {
        this.lineitems = value;
    }

    /**
     * Gets the value of the spart property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSPART() {
        return spart;
    }

    /**
     * Sets the value of the spart property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSPART(String value) {
        this.spart = value;
    }

    /**
     * Gets the value of the vkbur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVKBUR() {
        return vkbur;
    }

    /**
     * Sets the value of the vkbur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVKBUR(String value) {
        this.vkbur = value;
    }

    /**
     * Gets the value of the vkorg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVKORG() {
        return vkorg;
    }

    /**
     * Sets the value of the vkorg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVKORG(String value) {
        this.vkorg = value;
    }

    /**
     * Gets the value of the vtweg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVTWEG() {
        return vtweg;
    }

    /**
     * Sets the value of the vtweg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVTWEG(String value) {
        this.vtweg = value;
    }

    /**
     * Gets the value of the xblnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXBLNR() {
        return xblnr;
    }

    /**
     * Sets the value of the xblnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXBLNR(String value) {
        this.xblnr = value;
    }

    /**
     * Gets the value of the zterm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZTERM() {
        return zterm;
    }

    /**
     * Sets the value of the zterm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZTERM(String value) {
        this.zterm = value;
    }

}
