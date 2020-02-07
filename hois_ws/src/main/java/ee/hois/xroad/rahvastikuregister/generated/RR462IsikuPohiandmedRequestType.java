
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR462isikuPohiandmedRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR462isikuPohiandmedRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="vald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="staatus" type="{http://rr.x-road.eu/producer}isiku_staatusxtee1"/&gt;
 *         &lt;element name="pohjus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tulemusi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR462isikuPohiandmedRequestType", propOrder = {
    "isikukood",
    "perenimi",
    "eesnimi",
    "vald",
    "staatus",
    "pohjus",
    "tulemusi"
})
public class RR462IsikuPohiandmedRequestType {

    @XmlElement(required = true)
    protected String isikukood;
    @XmlElement(required = true)
    protected String perenimi;
    @XmlElement(required = true)
    protected String eesnimi;
    @XmlElement(required = true)
    protected String vald;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected IsikuStaatusxtee1 staatus;
    protected String pohjus;
    @XmlElement(required = true)
    protected String tulemusi;

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
     *     {@link IsikuStaatusxtee1 }
     *     
     */
    public IsikuStaatusxtee1 getStaatus() {
        return staatus;
    }

    /**
     * Sets the value of the staatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link IsikuStaatusxtee1 }
     *     
     */
    public void setStaatus(IsikuStaatusxtee1 value) {
        this.staatus = value;
    }

    /**
     * Gets the value of the pohjus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPohjus() {
        return pohjus;
    }

    /**
     * Sets the value of the pohjus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPohjus(String value) {
        this.pohjus = value;
    }

    /**
     * Gets the value of the tulemusi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTulemusi() {
        return tulemusi;
    }

    /**
     * Sets the value of the tulemusi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTulemusi(String value) {
        this.tulemusi = value;
    }

}
