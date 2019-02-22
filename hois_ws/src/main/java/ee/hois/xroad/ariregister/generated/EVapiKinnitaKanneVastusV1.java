
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EVapiKinnitaKanneVastus_v1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EVapiKinnitaKanneVastus_v1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="teade" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="menetlus_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/all&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EVapiKinnitaKanneVastus_v1", propOrder = {

})
public class EVapiKinnitaKanneVastusV1 {

    @XmlElement(required = true)
    protected String teade;
    @XmlElement(name = "menetlus_id")
    protected String menetlusId;

    /**
     * Gets the value of the teade property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeade() {
        return teade;
    }

    /**
     * Sets the value of the teade property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeade(String value) {
        this.teade = value;
    }

    /**
     * Gets the value of the menetlusId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMenetlusId() {
        return menetlusId;
    }

    /**
     * Sets the value of the menetlusId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMenetlusId(String value) {
        this.menetlusId = value;
    }

}
