
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR470isikukoodid16kuni26aRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR470isikukoodid16kuni26aRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EHAKKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR470isikukoodid16kuni26aRequestType", propOrder = {
    "ehakKood"
})
public class RR470Isikukoodid16Kuni26ARequestType {

    @XmlElement(name = "EHAKKood")
    protected String ehakKood;

    /**
     * Gets the value of the ehakKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEHAKKood() {
        return ehakKood;
    }

    /**
     * Sets the value of the ehakKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEHAKKood(String value) {
        this.ehakKood = value;
    }

}
