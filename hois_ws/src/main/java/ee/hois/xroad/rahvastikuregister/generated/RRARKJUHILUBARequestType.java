
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRARKJUHILUBARequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRARKJUHILUBARequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RRARKJUHILUBA" type="{http://rr.x-road.eu/producer}RRExtDocumentDataRequest"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRARKJUHILUBARequestType", propOrder = {
    "rrarkjuhiluba"
})
public class RRARKJUHILUBARequestType {

    @XmlElement(name = "RRARKJUHILUBA", required = true)
    protected RRExtDocumentDataRequest rrarkjuhiluba;

    /**
     * Gets the value of the rrarkjuhiluba property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentDataRequest }
     *     
     */
    public RRExtDocumentDataRequest getRRARKJUHILUBA() {
        return rrarkjuhiluba;
    }

    /**
     * Sets the value of the rrarkjuhiluba property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentDataRequest }
     *     
     */
    public void setRRARKJUHILUBA(RRExtDocumentDataRequest value) {
        this.rrarkjuhiluba = value;
    }

}
