
package ee.hois.xroad.rtip.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for kliendiSaldoResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="kliendiSaldoResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="LINE_ITEMS" type="{http://rtk-v6.x-road.eu}ZepnSaldoItems"/&gt;
 *         &lt;element name="LOGID" type="{http://rtk-v6.x-road.eu}char100"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "kliendiSaldoResponseType", propOrder = {
    "lineitems",
    "logid"
})
public class KliendiSaldoResponseType {

    @XmlElement(name = "LINE_ITEMS", required = true)
    protected ZepnSaldoItems lineitems;
    @XmlElement(name = "LOGID", required = true)
    protected String logid;

    /**
     * Gets the value of the lineitems property.
     * 
     * @return
     *     possible object is
     *     {@link ZepnSaldoItems }
     *     
     */
    public ZepnSaldoItems getLINEITEMS() {
        return lineitems;
    }

    /**
     * Sets the value of the lineitems property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZepnSaldoItems }
     *     
     */
    public void setLINEITEMS(ZepnSaldoItems value) {
        this.lineitems = value;
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

}
