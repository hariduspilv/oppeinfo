
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ariregistri_toimik_Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ariregistri_toimik_Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ariregistri_kood" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="dokumendid" type="{http://arireg.x-road.eu/producer/}ariregistri_toimik_dokumendid"/&gt;
 *         &lt;element name="otsused" type="{http://arireg.x-road.eu/producer/}ariregistri_toimik_otsused"/&gt;
 *         &lt;element name="hoiatused" type="{http://arireg.x-road.eu/producer/}ariregistri_toimik_hoiatused"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ariregistri_toimik_Response", propOrder = {
    "ariregistriKood",
    "dokumendid",
    "otsused",
    "hoiatused"
})
public class AriregistriToimikResponse {

    @XmlElement(name = "ariregistri_kood")
    protected int ariregistriKood;
    @XmlElement(required = true)
    protected AriregistriToimikDokumendid dokumendid;
    @XmlElement(required = true)
    protected AriregistriToimikOtsused otsused;
    @XmlElement(required = true)
    protected AriregistriToimikHoiatused hoiatused;

    /**
     * Gets the value of the ariregistriKood property.
     * 
     */
    public int getAriregistriKood() {
        return ariregistriKood;
    }

    /**
     * Sets the value of the ariregistriKood property.
     * 
     */
    public void setAriregistriKood(int value) {
        this.ariregistriKood = value;
    }

    /**
     * Gets the value of the dokumendid property.
     * 
     * @return
     *     possible object is
     *     {@link AriregistriToimikDokumendid }
     *     
     */
    public AriregistriToimikDokumendid getDokumendid() {
        return dokumendid;
    }

    /**
     * Sets the value of the dokumendid property.
     * 
     * @param value
     *     allowed object is
     *     {@link AriregistriToimikDokumendid }
     *     
     */
    public void setDokumendid(AriregistriToimikDokumendid value) {
        this.dokumendid = value;
    }

    /**
     * Gets the value of the otsused property.
     * 
     * @return
     *     possible object is
     *     {@link AriregistriToimikOtsused }
     *     
     */
    public AriregistriToimikOtsused getOtsused() {
        return otsused;
    }

    /**
     * Sets the value of the otsused property.
     * 
     * @param value
     *     allowed object is
     *     {@link AriregistriToimikOtsused }
     *     
     */
    public void setOtsused(AriregistriToimikOtsused value) {
        this.otsused = value;
    }

    /**
     * Gets the value of the hoiatused property.
     * 
     * @return
     *     possible object is
     *     {@link AriregistriToimikHoiatused }
     *     
     */
    public AriregistriToimikHoiatused getHoiatused() {
        return hoiatused;
    }

    /**
     * Sets the value of the hoiatused property.
     * 
     * @param value
     *     allowed object is
     *     {@link AriregistriToimikHoiatused }
     *     
     */
    public void setHoiatused(AriregistriToimikHoiatused value) {
        this.hoiatused = value;
    }

}
