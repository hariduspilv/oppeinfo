
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for iaOppimine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="iaOppimine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oppetase" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeasutusNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeasutusId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="oppekava" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klOppekava" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klassiLiik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klKlassiLiik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "iaOppimine", propOrder = {
    "oppetase",
    "oppeasutusNimi",
    "oppeasutusId",
    "oppekava",
    "klOppekava",
    "klassiLiik",
    "klKlassiLiik"
})
public class IaOppimine {

    @XmlElement(required = true)
    protected String oppetase;
    @XmlElement(required = true)
    protected String oppeasutusNimi;
    @XmlElement(required = true)
    protected BigInteger oppeasutusId;
    protected String oppekava;
    protected String klOppekava;
    protected String klassiLiik;
    protected String klKlassiLiik;

    /**
     * Gets the value of the oppetase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppetase() {
        return oppetase;
    }

    /**
     * Sets the value of the oppetase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppetase(String value) {
        this.oppetase = value;
    }

    /**
     * Gets the value of the oppeasutusNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutusNimi() {
        return oppeasutusNimi;
    }

    /**
     * Sets the value of the oppeasutusNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutusNimi(String value) {
        this.oppeasutusNimi = value;
    }

    /**
     * Gets the value of the oppeasutusId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOppeasutusId() {
        return oppeasutusId;
    }

    /**
     * Sets the value of the oppeasutusId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOppeasutusId(BigInteger value) {
        this.oppeasutusId = value;
    }

    /**
     * Gets the value of the oppekava property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekava() {
        return oppekava;
    }

    /**
     * Sets the value of the oppekava property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekava(String value) {
        this.oppekava = value;
    }

    /**
     * Gets the value of the klOppekava property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlOppekava() {
        return klOppekava;
    }

    /**
     * Sets the value of the klOppekava property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlOppekava(String value) {
        this.klOppekava = value;
    }

    /**
     * Gets the value of the klassiLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlassiLiik() {
        return klassiLiik;
    }

    /**
     * Sets the value of the klassiLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlassiLiik(String value) {
        this.klassiLiik = value;
    }

    /**
     * Gets the value of the klKlassiLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlKlassiLiik() {
        return klKlassiLiik;
    }

    /**
     * Sets the value of the klKlassiLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlKlassiLiik(String value) {
        this.klKlassiLiik = value;
    }

}
