
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRExtSideData4Request complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRExtSideData4Request"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Tegevus" type="{http://rr.x-road.eu/producer}Tegevus"/&gt;
 *         &lt;element name="Dokument" type="{http://rr.x-road.eu/producer}Dokument"/&gt;
 *         &lt;element name="Isik" type="{http://rr.x-road.eu/producer}Isik"/&gt;
 *         &lt;element name="Sideandmed" type="{http://rr.x-road.eu/producer}Sideandmed"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRExtSideData4Request", propOrder = {
    "tegevus",
    "dokument",
    "isik",
    "sideandmed"
})
@XmlSeeAlso({
    RRExtSideData3Request.class
})
public class RRExtSideData4Request {

    @XmlElement(name = "Tegevus", required = true)
    @XmlSchemaType(name = "string")
    protected Tegevus tegevus;
    @XmlElement(name = "Dokument", required = true)
    protected Dokument dokument;
    @XmlElement(name = "Isik", required = true)
    protected Isik isik;
    @XmlElement(name = "Sideandmed", required = true)
    protected Sideandmed sideandmed;

    /**
     * Gets the value of the tegevus property.
     * 
     * @return
     *     possible object is
     *     {@link Tegevus }
     *     
     */
    public Tegevus getTegevus() {
        return tegevus;
    }

    /**
     * Sets the value of the tegevus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Tegevus }
     *     
     */
    public void setTegevus(Tegevus value) {
        this.tegevus = value;
    }

    /**
     * Gets the value of the dokument property.
     * 
     * @return
     *     possible object is
     *     {@link Dokument }
     *     
     */
    public Dokument getDokument() {
        return dokument;
    }

    /**
     * Sets the value of the dokument property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dokument }
     *     
     */
    public void setDokument(Dokument value) {
        this.dokument = value;
    }

    /**
     * Gets the value of the isik property.
     * 
     * @return
     *     possible object is
     *     {@link Isik }
     *     
     */
    public Isik getIsik() {
        return isik;
    }

    /**
     * Sets the value of the isik property.
     * 
     * @param value
     *     allowed object is
     *     {@link Isik }
     *     
     */
    public void setIsik(Isik value) {
        this.isik = value;
    }

    /**
     * Gets the value of the sideandmed property.
     * 
     * @return
     *     possible object is
     *     {@link Sideandmed }
     *     
     */
    public Sideandmed getSideandmed() {
        return sideandmed;
    }

    /**
     * Sets the value of the sideandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sideandmed }
     *     
     */
    public void setSideandmed(Sideandmed value) {
        this.sideandmed = value;
    }

}
