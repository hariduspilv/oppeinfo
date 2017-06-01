
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tkMajandustegevusteade complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tkMajandustegevusteade"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nr" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="ryhmaNimetus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}strArray" minOccurs="0"/&gt;
 *         &lt;element name="kehtivAlgusKp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kehtivLoppKp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tkMajandustegevusteade", propOrder = {
    "nr",
    "ryhmaNimetus",
    "kehtivAlgusKp",
    "kehtivLoppKp"
})
public class TkMajandustegevusteade {

    protected BigInteger nr;
    protected StrArray ryhmaNimetus;
    protected String kehtivAlgusKp;
    protected String kehtivLoppKp;

    /**
     * Gets the value of the nr property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNr() {
        return nr;
    }

    /**
     * Sets the value of the nr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNr(BigInteger value) {
        this.nr = value;
    }

    /**
     * Gets the value of the ryhmaNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link StrArray }
     *     
     */
    public StrArray getRyhmaNimetus() {
        return ryhmaNimetus;
    }

    /**
     * Sets the value of the ryhmaNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link StrArray }
     *     
     */
    public void setRyhmaNimetus(StrArray value) {
        this.ryhmaNimetus = value;
    }

    /**
     * Gets the value of the kehtivAlgusKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKehtivAlgusKp() {
        return kehtivAlgusKp;
    }

    /**
     * Sets the value of the kehtivAlgusKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKehtivAlgusKp(String value) {
        this.kehtivAlgusKp = value;
    }

    /**
     * Gets the value of the kehtivLoppKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKehtivLoppKp() {
        return kehtivLoppKp;
    }

    /**
     * Sets the value of the kehtivLoppKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKehtivLoppKp(String value) {
        this.kehtivLoppKp = value;
    }

}
