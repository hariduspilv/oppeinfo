
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRKooseluRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRKooseluRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RRExtDocumentDataKooselu" type="{http://rr.x-road.eu/producer}RRExtDocumentDataKooseluRequest"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRKooseluRequestType", propOrder = {
    "rrExtDocumentDataKooselu"
})
public class RRKooseluRequestType {

    @XmlElement(name = "RRExtDocumentDataKooselu", required = true)
    protected RRExtDocumentDataKooseluRequest rrExtDocumentDataKooselu;

    /**
     * Gets the value of the rrExtDocumentDataKooselu property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentDataKooseluRequest }
     *     
     */
    public RRExtDocumentDataKooseluRequest getRRExtDocumentDataKooselu() {
        return rrExtDocumentDataKooselu;
    }

    /**
     * Sets the value of the rrExtDocumentDataKooselu property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentDataKooseluRequest }
     *     
     */
    public void setRRExtDocumentDataKooselu(RRExtDocumentDataKooseluRequest value) {
        this.rrExtDocumentDataKooselu = value;
    }

}
