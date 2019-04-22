
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR50SurnudIsikuteLeidmineRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR50SurnudIsikuteLeidmineRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Kuupaev" type="{http://rr.x-road.eu/producer}date"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR50SurnudIsikuteLeidmineRequestType", propOrder = {
    "kuupaev"
})
public class RR50SurnudIsikuteLeidmineRequestType {

    @XmlElement(name = "Kuupaev", required = true)
    protected String kuupaev;

    /**
     * Gets the value of the kuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKuupaev() {
        return kuupaev;
    }

    /**
     * Sets the value of the kuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKuupaev(String value) {
        this.kuupaev = value;
    }

}
