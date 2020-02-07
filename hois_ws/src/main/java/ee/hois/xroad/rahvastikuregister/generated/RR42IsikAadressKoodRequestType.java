
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR42isikAadressKoodRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR42isikAadressKoodRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="maakond" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="vald" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="asula" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tanavtalu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="majaNumbrivahemik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="korteriNumbrivahemik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="staatus" type="{http://rr.x-road.eu/producer}isiku_staatus" minOccurs="0"/&gt;
 *         &lt;element name="isikukoodFragment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="i_syndaastad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR42isikAadressKoodRequestType", propOrder = {
    "maakond",
    "vald",
    "asula",
    "tanavtalu",
    "majaNumbrivahemik",
    "korteriNumbrivahemik",
    "staatus",
    "isikukoodFragment",
    "iSyndaastad"
})
public class RR42IsikAadressKoodRequestType {

    protected String maakond;
    protected String vald;
    protected String asula;
    protected String tanavtalu;
    protected String majaNumbrivahemik;
    protected String korteriNumbrivahemik;
    @XmlSchemaType(name = "string")
    protected IsikuStaatus staatus;
    protected String isikukoodFragment;
    @XmlElement(name = "i_syndaastad")
    protected String iSyndaastad;

    /**
     * Gets the value of the maakond property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaakond() {
        return maakond;
    }

    /**
     * Sets the value of the maakond property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaakond(String value) {
        this.maakond = value;
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
     * Gets the value of the asula property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsula() {
        return asula;
    }

    /**
     * Sets the value of the asula property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsula(String value) {
        this.asula = value;
    }

    /**
     * Gets the value of the tanavtalu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTanavtalu() {
        return tanavtalu;
    }

    /**
     * Sets the value of the tanavtalu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTanavtalu(String value) {
        this.tanavtalu = value;
    }

    /**
     * Gets the value of the majaNumbrivahemik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMajaNumbrivahemik() {
        return majaNumbrivahemik;
    }

    /**
     * Sets the value of the majaNumbrivahemik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMajaNumbrivahemik(String value) {
        this.majaNumbrivahemik = value;
    }

    /**
     * Gets the value of the korteriNumbrivahemik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKorteriNumbrivahemik() {
        return korteriNumbrivahemik;
    }

    /**
     * Sets the value of the korteriNumbrivahemik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKorteriNumbrivahemik(String value) {
        this.korteriNumbrivahemik = value;
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
     * Gets the value of the isikukoodFragment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukoodFragment() {
        return isikukoodFragment;
    }

    /**
     * Sets the value of the isikukoodFragment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukoodFragment(String value) {
        this.isikukoodFragment = value;
    }

    /**
     * Gets the value of the iSyndaastad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getISyndaastad() {
        return iSyndaastad;
    }

    /**
     * Sets the value of the iSyndaastad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setISyndaastad(String value) {
        this.iSyndaastad = value;
    }

}
