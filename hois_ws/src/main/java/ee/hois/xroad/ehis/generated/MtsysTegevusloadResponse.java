
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="asutus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}mtsysAsutus" minOccurs="0"/&gt;
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
    "asutus"
})
@XmlRootElement(name = "mtsysTegevusloadResponse")
public class MtsysTegevusloadResponse {

    protected String infotekst;
    protected MtsysAsutus asutus;

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
     * Gets the value of the asutus property.
     * 
     * @return
     *     possible object is
     *     {@link MtsysAsutus }
     *     
     */
    public MtsysAsutus getAsutus() {
        return asutus;
    }

    /**
     * Sets the value of the asutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link MtsysAsutus }
     *     
     */
    public void setAsutus(MtsysAsutus value) {
        this.asutus = value;
    }

}
