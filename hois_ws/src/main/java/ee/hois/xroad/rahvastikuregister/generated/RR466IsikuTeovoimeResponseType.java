
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR466isikuTeovoimeResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR466isikuTeovoimeResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Veatekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsikuTeovoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DokumendiMarkus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR466isikuTeovoimeResponseType", propOrder = {
    "veatekst",
    "isikuTeovoime",
    "dokumendiMarkus"
})
public class RR466IsikuTeovoimeResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Veatekst")
    protected String veatekst;
    @XmlElement(name = "IsikuTeovoime", required = true)
    protected String isikuTeovoime;
    @XmlElement(name = "DokumendiMarkus", required = true)
    protected String dokumendiMarkus;

    /**
     * Gets the value of the veatekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVeatekst() {
        return veatekst;
    }

    /**
     * Sets the value of the veatekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVeatekst(String value) {
        this.veatekst = value;
    }

    /**
     * Gets the value of the isikuTeovoime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuTeovoime() {
        return isikuTeovoime;
    }

    /**
     * Sets the value of the isikuTeovoime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuTeovoime(String value) {
        this.isikuTeovoime = value;
    }

    /**
     * Gets the value of the dokumendiMarkus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiMarkus() {
        return dokumendiMarkus;
    }

    /**
     * Sets the value of the dokumendiMarkus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiMarkus(String value) {
        this.dokumendiMarkus = value;
    }

}
