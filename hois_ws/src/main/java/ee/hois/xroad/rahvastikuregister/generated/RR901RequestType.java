
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RR901RequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR901RequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Syndmus" type="{http://rr.x-road.eu/producer}RIASyndmusType"/&gt;
 *         &lt;element name="ObjektiTyyp" type="{http://rr.x-road.eu/producer}RIAKontaktType"/&gt;
 *         &lt;element name="ObjektiVaartus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MuudatuseKpv" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR901RequestType", propOrder = {
    "isikukood",
    "syndmus",
    "objektiTyyp",
    "objektiVaartus",
    "muudatuseKpv"
})
public class RR901RequestType {

    @XmlElement(name = "Isikukood", required = true)
    protected String isikukood;
    @XmlElement(name = "Syndmus", required = true)
    @XmlSchemaType(name = "string")
    protected RIASyndmusType syndmus;
    @XmlElement(name = "ObjektiTyyp", required = true)
    @XmlSchemaType(name = "string")
    protected RIAKontaktType objektiTyyp;
    @XmlElement(name = "ObjektiVaartus", required = true)
    protected String objektiVaartus;
    @XmlElement(name = "MuudatuseKpv", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar muudatuseKpv;

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
     * Gets the value of the syndmus property.
     * 
     * @return
     *     possible object is
     *     {@link RIASyndmusType }
     *     
     */
    public RIASyndmusType getSyndmus() {
        return syndmus;
    }

    /**
     * Sets the value of the syndmus property.
     * 
     * @param value
     *     allowed object is
     *     {@link RIASyndmusType }
     *     
     */
    public void setSyndmus(RIASyndmusType value) {
        this.syndmus = value;
    }

    /**
     * Gets the value of the objektiTyyp property.
     * 
     * @return
     *     possible object is
     *     {@link RIAKontaktType }
     *     
     */
    public RIAKontaktType getObjektiTyyp() {
        return objektiTyyp;
    }

    /**
     * Sets the value of the objektiTyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link RIAKontaktType }
     *     
     */
    public void setObjektiTyyp(RIAKontaktType value) {
        this.objektiTyyp = value;
    }

    /**
     * Gets the value of the objektiVaartus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjektiVaartus() {
        return objektiVaartus;
    }

    /**
     * Sets the value of the objektiVaartus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjektiVaartus(String value) {
        this.objektiVaartus = value;
    }

    /**
     * Gets the value of the muudatuseKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMuudatuseKpv() {
        return muudatuseKpv;
    }

    /**
     * Sets the value of the muudatuseKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMuudatuseKpv(XMLGregorianCalendar value) {
        this.muudatuseKpv = value;
    }

}
