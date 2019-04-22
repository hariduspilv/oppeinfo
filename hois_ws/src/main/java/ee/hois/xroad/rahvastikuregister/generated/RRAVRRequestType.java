
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRAVRRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRAVRRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Alates" type="{http://rr.x-road.eu/producer}date"/&gt;
 *         &lt;element name="Kuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *         &lt;element name="MuudatuseKood" type="{http://rr.x-road.eu/producer}Muudatus" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRAVRRequestType", propOrder = {
    "alates",
    "kuni",
    "muudatuseKood"
})
public class RRAVRRequestType {

    @XmlElement(name = "Alates", required = true)
    protected String alates;
    @XmlElement(name = "Kuni")
    protected String kuni;
    @XmlElement(name = "MuudatuseKood")
    protected String muudatuseKood;

    /**
     * Gets the value of the alates property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlates() {
        return alates;
    }

    /**
     * Sets the value of the alates property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlates(String value) {
        this.alates = value;
    }

    /**
     * Gets the value of the kuni property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKuni() {
        return kuni;
    }

    /**
     * Sets the value of the kuni property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKuni(String value) {
        this.kuni = value;
    }

    /**
     * Gets the value of the muudatuseKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMuudatuseKood() {
        return muudatuseKood;
    }

    /**
     * Sets the value of the muudatuseKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMuudatuseKood(String value) {
        this.muudatuseKood = value;
    }

}
