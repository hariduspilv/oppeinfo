
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for xbrlAruandeNaitajad_v1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="xbrlAruandeNaitajad_v1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}xbrl_aruande_naitajad_paring"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "xbrlAruandeNaitajad_v1", propOrder = {
    "keha"
})
public class XbrlAruandeNaitajadV1 {

    @XmlElement(required = true)
    protected XbrlAruandeNaitajadParing keha;

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link XbrlAruandeNaitajadParing }
     *     
     */
    public XbrlAruandeNaitajadParing getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link XbrlAruandeNaitajadParing }
     *     
     */
    public void setKeha(XbrlAruandeNaitajadParing value) {
        this.keha = value;
    }

}
