
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR410KeskkonnateabeRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR410KeskkonnateabeRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SeisugaKPV" type="{http://rr.x-road.eu/producer}date"/&gt;
 *         &lt;element name="EhakTyybid" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Koodid" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR410KeskkonnateabeRequestType", propOrder = {
    "seisugaKPV",
    "ehakTyybid",
    "koodid"
})
public class RR410KeskkonnateabeRequestType {

    @XmlElement(name = "SeisugaKPV", required = true)
    protected String seisugaKPV;
    @XmlElement(name = "EhakTyybid", required = true)
    protected String ehakTyybid;
    @XmlElement(name = "Koodid", required = true)
    protected String koodid;

    /**
     * Gets the value of the seisugaKPV property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeisugaKPV() {
        return seisugaKPV;
    }

    /**
     * Sets the value of the seisugaKPV property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeisugaKPV(String value) {
        this.seisugaKPV = value;
    }

    /**
     * Gets the value of the ehakTyybid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEhakTyybid() {
        return ehakTyybid;
    }

    /**
     * Sets the value of the ehakTyybid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEhakTyybid(String value) {
        this.ehakTyybid = value;
    }

    /**
     * Gets the value of the koodid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKoodid() {
        return koodid;
    }

    /**
     * Sets the value of the koodid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKoodid(String value) {
        this.koodid = value;
    }

}
