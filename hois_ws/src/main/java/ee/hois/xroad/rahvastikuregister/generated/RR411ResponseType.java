
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR411ResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR411ResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MaakonnaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="VallaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AsulaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR411ResponseType", propOrder = {
    "isikukood",
    "maakonnaKood",
    "vallaKood",
    "asulaKood"
})
public class RR411ResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Isikukood", required = true)
    protected String isikukood;
    @XmlElement(name = "MaakonnaKood", required = true)
    protected String maakonnaKood;
    @XmlElement(name = "VallaKood", required = true)
    protected String vallaKood;
    @XmlElement(name = "AsulaKood", required = true)
    protected String asulaKood;

    /**
     * Gets the value of the isikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukood() {
        return isikukood;
    }

    /**
     * Sets the value of the isikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukood(String value) {
        this.isikukood = value;
    }

    /**
     * Gets the value of the maakonnaKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaakonnaKood() {
        return maakonnaKood;
    }

    /**
     * Sets the value of the maakonnaKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaakonnaKood(String value) {
        this.maakonnaKood = value;
    }

    /**
     * Gets the value of the vallaKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVallaKood() {
        return vallaKood;
    }

    /**
     * Sets the value of the vallaKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVallaKood(String value) {
        this.vallaKood = value;
    }

    /**
     * Gets the value of the asulaKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsulaKood() {
        return asulaKood;
    }

    /**
     * Sets the value of the asulaKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsulaKood(String value) {
        this.asulaKood = value;
    }

}
