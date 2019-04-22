
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR464isikuSuhtedRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR464isikuSuhtedRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SuhteTyyp" type="{http://rr.x-road.eu/producer}suhte_tyyp" minOccurs="0"/&gt;
 *         &lt;element name="SuhteStaatus" type="{http://rr.x-road.eu/producer}suhte_staatus" minOccurs="0"/&gt;
 *         &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
 *         &lt;element name="pohjus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR464isikuSuhtedRequestType", propOrder = {
    "suhteTyyp",
    "suhteStaatus",
    "isikukood",
    "pohjus"
})
public class RR464IsikuSuhtedRequestType {

    @XmlElement(name = "SuhteTyyp")
    @XmlSchemaType(name = "string")
    protected SuhteTyyp suhteTyyp;
    @XmlElement(name = "SuhteStaatus")
    @XmlSchemaType(name = "string")
    protected SuhteStaatus suhteStaatus;
    @XmlElement(name = "Isikukood", required = true)
    protected String isikukood;
    protected String pohjus;

    /**
     * Gets the value of the suhteTyyp property.
     * 
     * @return
     *     possible object is
     *     {@link SuhteTyyp }
     *     
     */
    public SuhteTyyp getSuhteTyyp() {
        return suhteTyyp;
    }

    /**
     * Sets the value of the suhteTyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link SuhteTyyp }
     *     
     */
    public void setSuhteTyyp(SuhteTyyp value) {
        this.suhteTyyp = value;
    }

    /**
     * Gets the value of the suhteStaatus property.
     * 
     * @return
     *     possible object is
     *     {@link SuhteStaatus }
     *     
     */
    public SuhteStaatus getSuhteStaatus() {
        return suhteStaatus;
    }

    /**
     * Sets the value of the suhteStaatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link SuhteStaatus }
     *     
     */
    public void setSuhteStaatus(SuhteStaatus value) {
        this.suhteStaatus = value;
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

}
