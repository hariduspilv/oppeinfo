
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRARKJUHILUBAResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRARKJUHILUBAResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RRARKJUHILUBA" type="{http://rr.x-road.eu/producer}RRExtDocumentDataResponse"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRARKJUHILUBAResponseType", propOrder = {
    "rrarkjuhiluba"
})
public class RRARKJUHILUBAResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "RRARKJUHILUBA", required = true)
    protected RRExtDocumentDataResponse rrarkjuhiluba;

    /**
     * Gets the value of the rrarkjuhiluba property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentDataResponse }
     *     
     */
    public RRExtDocumentDataResponse getRRARKJUHILUBA() {
        return rrarkjuhiluba;
    }

    /**
     * Sets the value of the rrarkjuhiluba property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentDataResponse }
     *     
     */
    public void setRRARKJUHILUBA(RRExtDocumentDataResponse value) {
        this.rrarkjuhiluba = value;
    }

}
