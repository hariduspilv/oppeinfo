
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AddrComp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AddrComp"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;sequence&gt;
 *           &lt;element name="AkpID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *           &lt;element name="AkpKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *           &lt;element name="AkpPikkNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;/sequence&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddrComp", propOrder = {
    "akpID",
    "akpKood",
    "akpPikkNimetus"
})
public class AddrComp {

    @XmlElement(name = "AkpID")
    protected int akpID;
    @XmlElement(name = "AkpKood", required = true)
    protected String akpKood;
    @XmlElement(name = "AkpPikkNimetus", required = true)
    protected String akpPikkNimetus;

    /**
     * Gets the value of the akpID property.
     * 
     */
    public int getAkpID() {
        return akpID;
    }

    /**
     * Sets the value of the akpID property.
     * 
     */
    public void setAkpID(int value) {
        this.akpID = value;
    }

    /**
     * Gets the value of the akpKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAkpKood() {
        return akpKood;
    }

    /**
     * Sets the value of the akpKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAkpKood(String value) {
        this.akpKood = value;
    }

    /**
     * Gets the value of the akpPikkNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAkpPikkNimetus() {
        return akpPikkNimetus;
    }

    /**
     * Sets the value of the akpPikkNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAkpPikkNimetus(String value) {
        this.akpPikkNimetus = value;
    }

}
