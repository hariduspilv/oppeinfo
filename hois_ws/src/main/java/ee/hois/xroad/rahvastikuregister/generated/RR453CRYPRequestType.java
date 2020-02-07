
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR453CRYPRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR453CRYPRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isikuperenimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isikueesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="vald" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Staatus" type="{http://rr.x-road.eu/producer}isiku_staatus" minOccurs="0"/&gt;
 *         &lt;element name="mitu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR453CRYPRequestType", propOrder = {
    "isikuperenimi",
    "isikueesnimi",
    "isikukood",
    "vald",
    "staatus",
    "mitu"
})
public class RR453CRYPRequestType {

    protected String isikuperenimi;
    protected String isikueesnimi;
    protected String isikukood;
    protected String vald;
    @XmlElement(name = "Staatus")
    @XmlSchemaType(name = "string")
    protected IsikuStaatus staatus;
    protected String mitu;

    /**
     * Gets the value of the isikuperenimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuperenimi() {
        return isikuperenimi;
    }

    /**
     * Sets the value of the isikuperenimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuperenimi(String value) {
        this.isikuperenimi = value;
    }

    /**
     * Gets the value of the isikueesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikueesnimi() {
        return isikueesnimi;
    }

    /**
     * Sets the value of the isikueesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikueesnimi(String value) {
        this.isikueesnimi = value;
    }

    /**
     * Gets the value of the isikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukood() {
        return isikukood;
    }

    /**
     * Sets the value of the isikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukood(String value) {
        this.isikukood = value;
    }

    /**
     * Gets the value of the vald property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVald() {
        return vald;
    }

    /**
     * Sets the value of the vald property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVald(String value) {
        this.vald = value;
    }

    /**
     * Gets the value of the staatus property.
     * 
     * @return
     *     possible object is
     *     {@link IsikuStaatus }
     *     
     */
    public IsikuStaatus getStaatus() {
        return staatus;
    }

    /**
     * Sets the value of the staatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link IsikuStaatus }
     *     
     */
    public void setStaatus(IsikuStaatus value) {
        this.staatus = value;
    }

    /**
     * Gets the value of the mitu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMitu() {
        return mitu;
    }

    /**
     * Sets the value of the mitu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMitu(String value) {
        this.mitu = value;
    }

}
