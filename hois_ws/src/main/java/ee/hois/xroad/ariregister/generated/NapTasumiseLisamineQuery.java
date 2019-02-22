
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NapTasumiseLisamineQuery complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NapTasumiseLisamineQuery"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NapTasumiseLisamine" type="{http://arireg.x-road.eu/producer/}NapTasumiseLisamineType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NapTasumiseLisamineQuery", propOrder = {
    "napTasumiseLisamine"
})
public class NapTasumiseLisamineQuery {

    @XmlElement(name = "NapTasumiseLisamine", required = true)
    protected NapTasumiseLisamineType napTasumiseLisamine;

    /**
     * Gets the value of the napTasumiseLisamine property.
     * 
     * @return
     *     possible object is
     *     {@link NapTasumiseLisamineType }
     *     
     */
    public NapTasumiseLisamineType getNapTasumiseLisamine() {
        return napTasumiseLisamine;
    }

    /**
     * Sets the value of the napTasumiseLisamine property.
     * 
     * @param value
     *     allowed object is
     *     {@link NapTasumiseLisamineType }
     *     
     */
    public void setNapTasumiseLisamine(NapTasumiseLisamineType value) {
        this.napTasumiseLisamine = value;
    }

}
