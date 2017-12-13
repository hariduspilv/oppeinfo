
package ee.hois.xroad.rtip.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tellimusResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tellimusResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="INVOICE" type="{http://rtk-v6.x-road.eu}char10"/&gt;
 *         &lt;element name="LOGID" type="{http://rtk-v6.x-road.eu}char100"/&gt;
 *         &lt;element name="RETURN" type="{http://rtk-v6.x-road.eu}ZmmPpaTReturn"/&gt;
 *         &lt;element name="SALESDOCUMENT" type="{http://rtk-v6.x-road.eu}char10"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tellimusResponseType", propOrder = {
    "invoice",
    "logid",
    "_return",
    "salesdocument"
})
public class TellimusResponseType {

    @XmlElement(name = "INVOICE", required = true)
    protected String invoice;
    @XmlElement(name = "LOGID", required = true)
    protected String logid;
    @XmlElement(name = "RETURN", required = true)
    protected ZmmPpaTReturn _return;
    @XmlElement(name = "SALESDOCUMENT", required = true)
    protected String salesdocument;

    /**
     * Gets the value of the invoice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINVOICE() {
        return invoice;
    }

    /**
     * Sets the value of the invoice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINVOICE(String value) {
        this.invoice = value;
    }

    /**
     * Gets the value of the logid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLOGID() {
        return logid;
    }

    /**
     * Sets the value of the logid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLOGID(String value) {
        this.logid = value;
    }

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link ZmmPpaTReturn }
     *     
     */
    public ZmmPpaTReturn getRETURN() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZmmPpaTReturn }
     *     
     */
    public void setRETURN(ZmmPpaTReturn value) {
        this._return = value;
    }

    /**
     * Gets the value of the salesdocument property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSALESDOCUMENT() {
        return salesdocument;
    }

    /**
     * Sets the value of the salesdocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSALESDOCUMENT(String value) {
        this.salesdocument = value;
    }

}
