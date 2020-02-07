
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NumberOfNewbornsByDateResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NumberOfNewbornsByDateResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="girls" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="boys" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NumberOfNewbornsByDateResponseType", propOrder = {
    "girls",
    "boys"
})
public class NumberOfNewbornsByDateResponseType {

    @XmlElement(required = true)
    protected String girls;
    @XmlElement(required = true)
    protected String boys;

    /**
     * Gets the value of the girls property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGirls() {
        return girls;
    }

    /**
     * Sets the value of the girls property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGirls(String value) {
        this.girls = value;
    }

    /**
     * Gets the value of the boys property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBoys() {
        return boys;
    }

    /**
     * Sets the value of the boys property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBoys(String value) {
        this.boys = value;
    }

}
