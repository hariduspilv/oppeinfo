
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR471Isik complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR471Isik"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Vanus" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="KirjeStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR471Isik", propOrder = {
    "eesnimi",
    "perenimi",
    "isikukood",
    "vanus",
    "isikuStaatus",
    "kirjeStaatus"
})
public class RR471Isik {

    @XmlElement(name = "Eesnimi", required = true)
    protected String eesnimi;
    @XmlElement(name = "Perenimi", required = true)
    protected String perenimi;
    @XmlElement(name = "Isikukood", required = true)
    protected String isikukood;
    @XmlElement(name = "Vanus")
    protected int vanus;
    @XmlElement(name = "IsikuStaatus", required = true)
    protected String isikuStaatus;
    @XmlElement(name = "KirjeStaatus", required = true)
    protected String kirjeStaatus;

    /**
     * Gets the value of the eesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEesnimi() {
        return eesnimi;
    }

    /**
     * Sets the value of the eesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEesnimi(String value) {
        this.eesnimi = value;
    }

    /**
     * Gets the value of the perenimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerenimi() {
        return perenimi;
    }

    /**
     * Sets the value of the perenimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerenimi(String value) {
        this.perenimi = value;
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
     * Gets the value of the vanus property.
     * 
     */
    public int getVanus() {
        return vanus;
    }

    /**
     * Sets the value of the vanus property.
     * 
     */
    public void setVanus(int value) {
        this.vanus = value;
    }

    /**
     * Gets the value of the isikuStaatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuStaatus() {
        return isikuStaatus;
    }

    /**
     * Sets the value of the isikuStaatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuStaatus(String value) {
        this.isikuStaatus = value;
    }

    /**
     * Gets the value of the kirjeStaatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKirjeStaatus() {
        return kirjeStaatus;
    }

    /**
     * Sets the value of the kirjeStaatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKirjeStaatus(String value) {
        this.kirjeStaatus = value;
    }

}
