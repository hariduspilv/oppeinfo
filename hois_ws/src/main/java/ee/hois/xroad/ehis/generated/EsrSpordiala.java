
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for esrSpordiala complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="esrSpordiala"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="spordialaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="distsipliinKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "esrSpordiala", propOrder = {
    "spordialaKood",
    "distsipliinKood"
})
public class EsrSpordiala {

    @XmlElement(required = true)
    protected String spordialaKood;
    protected String distsipliinKood;

    /**
     * Gets the value of the spordialaKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpordialaKood() {
        return spordialaKood;
    }

    /**
     * Sets the value of the spordialaKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpordialaKood(String value) {
        this.spordialaKood = value;
    }

    /**
     * Gets the value of the distsipliinKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistsipliinKood() {
        return distsipliinKood;
    }

    /**
     * Sets the value of the distsipliinKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistsipliinKood(String value) {
        this.distsipliinKood = value;
    }

}
