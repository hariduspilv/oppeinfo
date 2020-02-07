
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR415RequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR415RequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PerioodiAlgus" type="{http://rr.x-road.eu/producer}date"/&gt;
 *         &lt;element name="PerioodiLopp" type="{http://rr.x-road.eu/producer}date"/&gt;
 *         &lt;element name="KOV" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Linnaosa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR415RequestType", propOrder = {
    "perioodiAlgus",
    "perioodiLopp",
    "kov",
    "linnaosa"
})
public class RR415RequestType {

    @XmlElement(name = "PerioodiAlgus", required = true)
    protected String perioodiAlgus;
    @XmlElement(name = "PerioodiLopp", required = true)
    protected String perioodiLopp;
    @XmlElement(name = "KOV", required = true)
    protected String kov;
    @XmlElement(name = "Linnaosa")
    protected String linnaosa;

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
     * Gets the value of the kov property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKOV() {
        return kov;
    }

    /**
     * Sets the value of the kov property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKOV(String value) {
        this.kov = value;
    }

    /**
     * Gets the value of the linnaosa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinnaosa() {
        return linnaosa;
    }

    /**
     * Sets the value of the linnaosa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinnaosa(String value) {
        this.linnaosa = value;
    }

}
