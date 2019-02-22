
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EVapiLooKanneTegevusala_v1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EVapiLooKanneTegevusala_v1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="emtak_kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="versioon" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="nimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/all&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EVapiLooKanneTegevusala_v1", propOrder = {

})
public class EVapiLooKanneTegevusalaV1 {

    @XmlElement(name = "emtak_kood")
    protected String emtakKood;
    protected Integer versioon;
    protected String nimetus;

    /**
     * Gets the value of the emtakKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmtakKood() {
        return emtakKood;
    }

    /**
     * Sets the value of the emtakKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmtakKood(String value) {
        this.emtakKood = value;
    }

    /**
     * Gets the value of the versioon property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVersioon() {
        return versioon;
    }

    /**
     * Sets the value of the versioon property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVersioon(Integer value) {
        this.versioon = value;
    }

    /**
     * Gets the value of the nimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNimetus() {
        return nimetus;
    }

    /**
     * Sets the value of the nimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNimetus(String value) {
        this.nimetus = value;
    }

}
