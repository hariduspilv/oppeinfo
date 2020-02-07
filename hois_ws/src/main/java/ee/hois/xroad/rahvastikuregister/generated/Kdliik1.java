
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for kdliik1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="kdliik1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="KdID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="KdElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="KdLyhikeNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "kdliik1", propOrder = {
    "kdID",
    "kdElemendiKood",
    "kdLyhikeNimi"
})
public class Kdliik1 {

    @XmlElement(name = "KdID", required = true)
    protected BigInteger kdID;
    @XmlElement(name = "KdElemendiKood", required = true)
    protected String kdElemendiKood;
    @XmlElement(name = "KdLyhikeNimi", required = true)
    protected String kdLyhikeNimi;

    /**
     * Gets the value of the kdID property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKdID() {
        return kdID;
    }

    /**
     * Sets the value of the kdID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKdID(BigInteger value) {
        this.kdID = value;
    }

    /**
     * Gets the value of the kdElemendiKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKdElemendiKood() {
        return kdElemendiKood;
    }

    /**
     * Sets the value of the kdElemendiKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKdElemendiKood(String value) {
        this.kdElemendiKood = value;
    }

    /**
     * Gets the value of the kdLyhikeNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKdLyhikeNimi() {
        return kdLyhikeNimi;
    }

    /**
     * Sets the value of the kdLyhikeNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKdLyhikeNimi(String value) {
        this.kdLyhikeNimi = value;
    }

}
