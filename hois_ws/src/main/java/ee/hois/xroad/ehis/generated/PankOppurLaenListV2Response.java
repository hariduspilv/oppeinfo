
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="veakood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="teated"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="teade" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="contentOut" type="{http://www.w3.org/2001/XMLSchema}anyURI"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "veakood",
    "teated",
    "contentOut"
})
@XmlRootElement(name = "pankOppurLaenListV2Response")
public class PankOppurLaenListV2Response {

    @XmlElement(required = true)
    protected BigInteger veakood;
    @XmlElement(required = true)
    protected PankOppurLaenListV2Response.Teated teated;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "anyURI")
    protected String contentOut;

    /**
     * Gets the value of the veakood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVeakood() {
        return veakood;
    }

    /**
     * Sets the value of the veakood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVeakood(BigInteger value) {
        this.veakood = value;
    }

    /**
     * Gets the value of the teated property.
     * 
     * @return
     *     possible object is
     *     {@link PankOppurLaenListV2Response.Teated }
     *     
     */
    public PankOppurLaenListV2Response.Teated getTeated() {
        return teated;
    }

    /**
     * Sets the value of the teated property.
     * 
     * @param value
     *     allowed object is
     *     {@link PankOppurLaenListV2Response.Teated }
     *     
     */
    public void setTeated(PankOppurLaenListV2Response.Teated value) {
        this.teated = value;
    }

    /**
     * Gets the value of the contentOut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContentOut() {
        return contentOut;
    }

    /**
     * Sets the value of the contentOut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentOut(String value) {
        this.contentOut = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="teade" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "teade"
    })
    public static class Teated {

        @XmlElement(nillable = true)
        protected List<String> teade;

        /**
         * Gets the value of the teade property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the teade property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTeade().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getTeade() {
            if (teade == null) {
                teade = new ArrayList<String>();
            }
            return this.teade;
        }

    }

}
