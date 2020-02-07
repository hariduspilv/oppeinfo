
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR408integratsioonRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR408integratsioonRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PerioodiAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PerioodiLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ElamisKoda" type="{http://rr.x-road.eu/producer}doktyyp"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR408integratsioonRequestType", propOrder = {
    "perioodiAlgus",
    "perioodiLopp",
    "elamisKoda"
})
public class RR408IntegratsioonRequestType {

    @XmlElement(name = "PerioodiAlgus", required = true)
    protected String perioodiAlgus;
    @XmlElement(name = "PerioodiLopp", required = true)
    protected String perioodiLopp;
    @XmlElement(name = "ElamisKoda", required = true)
    protected String elamisKoda;

    /**
     * Gets the value of the perioodiAlgus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerioodiAlgus() {
        return perioodiAlgus;
    }

    /**
     * Sets the value of the perioodiAlgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerioodiAlgus(String value) {
        this.perioodiAlgus = value;
    }

    /**
     * Gets the value of the perioodiLopp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerioodiLopp() {
        return perioodiLopp;
    }

    /**
     * Sets the value of the perioodiLopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerioodiLopp(String value) {
        this.perioodiLopp = value;
    }

    /**
     * Gets the value of the elamisKoda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElamisKoda() {
        return elamisKoda;
    }

    /**
     * Sets the value of the elamisKoda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElamisKoda(String value) {
        this.elamisKoda = value;
    }

}
