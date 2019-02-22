
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for xbrlpdfesita_paring complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="xbrlpdfesita_paring"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="yldandmed" type="{http://arireg.x-road.eu/producer/}xbrlpdfesita_yldandmed"/&gt;
 *         &lt;element name="myygitulu" type="{http://arireg.x-road.eu/producer/}xbrlpdfesita_myygitulu"/&gt;
 *         &lt;element name="laiend_audiitorid" type="{http://arireg.x-road.eu/producer/}xbrlpdfesita_laiend_audiitorid" minOccurs="0"/&gt;
 *         &lt;element name="bilansi_sisud" type="{http://arireg.x-road.eu/producer/}xbrl_aruande_bilansi_sisud" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "xbrlpdfesita_paring", propOrder = {
    "yldandmed",
    "myygitulu",
    "laiendAudiitorid",
    "bilansiSisud"
})
public class XbrlpdfesitaParing {

    @XmlElement(required = true)
    protected XbrlpdfesitaYldandmed yldandmed;
    @XmlElement(required = true, nillable = true)
    protected XbrlpdfesitaMyygitulu myygitulu;
    @XmlElementRef(name = "laiend_audiitorid", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<XbrlpdfesitaLaiendAudiitorid> laiendAudiitorid;
    @XmlElement(name = "bilansi_sisud")
    protected XbrlAruandeBilansiSisud bilansiSisud;

    /**
     * Gets the value of the yldandmed property.
     * 
     * @return
     *     possible object is
     *     {@link XbrlpdfesitaYldandmed }
     *     
     */
    public XbrlpdfesitaYldandmed getYldandmed() {
        return yldandmed;
    }

    /**
     * Sets the value of the yldandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link XbrlpdfesitaYldandmed }
     *     
     */
    public void setYldandmed(XbrlpdfesitaYldandmed value) {
        this.yldandmed = value;
    }

    /**
     * Gets the value of the myygitulu property.
     * 
     * @return
     *     possible object is
     *     {@link XbrlpdfesitaMyygitulu }
     *     
     */
    public XbrlpdfesitaMyygitulu getMyygitulu() {
        return myygitulu;
    }

    /**
     * Sets the value of the myygitulu property.
     * 
     * @param value
     *     allowed object is
     *     {@link XbrlpdfesitaMyygitulu }
     *     
     */
    public void setMyygitulu(XbrlpdfesitaMyygitulu value) {
        this.myygitulu = value;
    }

    /**
     * Gets the value of the laiendAudiitorid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XbrlpdfesitaLaiendAudiitorid }{@code >}
     *     
     */
    public JAXBElement<XbrlpdfesitaLaiendAudiitorid> getLaiendAudiitorid() {
        return laiendAudiitorid;
    }

    /**
     * Sets the value of the laiendAudiitorid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XbrlpdfesitaLaiendAudiitorid }{@code >}
     *     
     */
    public void setLaiendAudiitorid(JAXBElement<XbrlpdfesitaLaiendAudiitorid> value) {
        this.laiendAudiitorid = value;
    }

    /**
     * Gets the value of the bilansiSisud property.
     * 
     * @return
     *     possible object is
     *     {@link XbrlAruandeBilansiSisud }
     *     
     */
    public XbrlAruandeBilansiSisud getBilansiSisud() {
        return bilansiSisud;
    }

    /**
     * Sets the value of the bilansiSisud property.
     * 
     * @param value
     *     allowed object is
     *     {@link XbrlAruandeBilansiSisud }
     *     
     */
    public void setBilansiSisud(XbrlAruandeBilansiSisud value) {
        this.bilansiSisud = value;
    }

}
