
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRExtSideData3Request complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRExtSideData3Request"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}RRExtSideData4Request"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AadressAndmed" type="{http://rr.x-road.eu/producer}AadressAndmed"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRExtSideData3Request", propOrder = {
    "aadressAndmed"
})
public class RRExtSideData3Request
    extends RRExtSideData4Request
{

    @XmlElement(name = "AadressAndmed", required = true)
    protected AadressAndmed aadressAndmed;

    /**
     * Gets the value of the aadressAndmed property.
     * 
     * @return
     *     possible object is
     *     {@link AadressAndmed }
     *     
     */
    public AadressAndmed getAadressAndmed() {
        return aadressAndmed;
    }

    /**
     * Sets the value of the aadressAndmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link AadressAndmed }
     *     
     */
    public void setAadressAndmed(AadressAndmed value) {
        this.aadressAndmed = value;
    }

}
