
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR427KMOTaotlejagaSeotudIsikudRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR427KMOTaotlejagaSeotudIsikudRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="KMOTaotlejagaSeotudIsikud" type="{http://rr.x-road.eu/producer}KMOIsikuTuvastamine"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR427KMOTaotlejagaSeotudIsikudRequestType", propOrder = {
    "kmoTaotlejagaSeotudIsikud"
})
public class RR427KMOTaotlejagaSeotudIsikudRequestType {

    @XmlElement(name = "KMOTaotlejagaSeotudIsikud", required = true)
    protected KMOIsikuTuvastamine kmoTaotlejagaSeotudIsikud;

    /**
     * Gets the value of the kmoTaotlejagaSeotudIsikud property.
     * 
     * @return
     *     possible object is
     *     {@link KMOIsikuTuvastamine }
     *     
     */
    public KMOIsikuTuvastamine getKMOTaotlejagaSeotudIsikud() {
        return kmoTaotlejagaSeotudIsikud;
    }

    /**
     * Sets the value of the kmoTaotlejagaSeotudIsikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link KMOIsikuTuvastamine }
     *     
     */
    public void setKMOTaotlejagaSeotudIsikud(KMOIsikuTuvastamine value) {
        this.kmoTaotlejagaSeotudIsikud = value;
    }

}
