
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRExtSideData1Request complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRExtSideData1Request"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Tegevus" type="{http://rr.x-road.eu/producer}Tegevus"/&gt;
 *         &lt;element name="Dokument" type="{http://rr.x-road.eu/producer}Dokument"/&gt;
 *         &lt;element name="Isikud" type="{http://rr.x-road.eu/producer}Isikud"/&gt;
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
@XmlType(name = "RRExtSideData1Request", propOrder = {
    "tegevus",
    "dokument",
    "isikud",
    "sideandmed"
})
@XmlSeeAlso({
    RRExtSideData2Request.class
})
public class RRExtSideData1Request {

    @XmlElement(name = "Tegevus", required = true)
    @XmlSchemaType(name = "string")
    protected Tegevus tegevus;
    @XmlElement(name = "Dokument", required = true)
    protected Dokument dokument;
    @XmlElement(name = "Isikud", required = true)
    protected Isikud isikud;
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
     * Gets the value of the isikud property.
     * 
     * @return
     *     possible object is
     *     {@link Isikud }
     *     
     */
    public Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link Isikud }
     *     
     */
    public void setIsikud(Isikud value) {
        this.isikud = value;
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
