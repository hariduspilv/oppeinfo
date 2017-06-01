
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="infotekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tegevusloaAndmed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tegevuslubaDetail"/&gt;
 *         &lt;element name="kontaktandmed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}mtsysTaotlusKontaktandmed" minOccurs="0"/&gt;
 *         &lt;element name="peatamised" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}peatamised" minOccurs="0"/&gt;
 *         &lt;element name="dokumendid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}dokumendid" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "infotekst",
    "tegevusloaAndmed",
    "kontaktandmed",
    "peatamised",
    "dokumendid"
})
@XmlRootElement(name = "mtsysTegevuslubaResponse")
public class MtsysTegevuslubaResponse {

    protected String infotekst;
    @XmlElement(required = true)
    protected TegevuslubaDetail tegevusloaAndmed;
    protected MtsysTaotlusKontaktandmed kontaktandmed;
    protected Peatamised peatamised;
    protected Dokumendid dokumendid;

    /**
     * Gets the value of the infotekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfotekst() {
        return infotekst;
    }

    /**
     * Sets the value of the infotekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfotekst(String value) {
        this.infotekst = value;
    }

    /**
     * Gets the value of the tegevusloaAndmed property.
     * 
     * @return
     *     possible object is
     *     {@link TegevuslubaDetail }
     *     
     */
    public TegevuslubaDetail getTegevusloaAndmed() {
        return tegevusloaAndmed;
    }

    /**
     * Sets the value of the tegevusloaAndmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link TegevuslubaDetail }
     *     
     */
    public void setTegevusloaAndmed(TegevuslubaDetail value) {
        this.tegevusloaAndmed = value;
    }

    /**
     * Gets the value of the kontaktandmed property.
     * 
     * @return
     *     possible object is
     *     {@link MtsysTaotlusKontaktandmed }
     *     
     */
    public MtsysTaotlusKontaktandmed getKontaktandmed() {
        return kontaktandmed;
    }

    /**
     * Sets the value of the kontaktandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link MtsysTaotlusKontaktandmed }
     *     
     */
    public void setKontaktandmed(MtsysTaotlusKontaktandmed value) {
        this.kontaktandmed = value;
    }

    /**
     * Gets the value of the peatamised property.
     * 
     * @return
     *     possible object is
     *     {@link Peatamised }
     *     
     */
    public Peatamised getPeatamised() {
        return peatamised;
    }

    /**
     * Sets the value of the peatamised property.
     * 
     * @param value
     *     allowed object is
     *     {@link Peatamised }
     *     
     */
    public void setPeatamised(Peatamised value) {
        this.peatamised = value;
    }

    /**
     * Gets the value of the dokumendid property.
     * 
     * @return
     *     possible object is
     *     {@link Dokumendid }
     *     
     */
    public Dokumendid getDokumendid() {
        return dokumendid;
    }

    /**
     * Sets the value of the dokumendid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dokumendid }
     *     
     */
    public void setDokumendid(Dokumendid value) {
        this.dokumendid = value;
    }

}
