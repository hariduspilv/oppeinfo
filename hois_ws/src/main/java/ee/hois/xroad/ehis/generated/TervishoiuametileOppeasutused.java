
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &lt;element name="ehisKood" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/&gt;
 *         &lt;element name="ehakKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klOppetase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="haldajaRegkood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "ehisKood",
    "ehakKood",
    "klOppetase",
    "haldajaRegkood"
})
@XmlRootElement(name = "tervishoiuametileOppeasutused")
public class TervishoiuametileOppeasutused {

    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger ehisKood;
    protected String ehakKood;
    protected String klOppetase;
    protected String haldajaRegkood;

    /**
     * Gets the value of the ehisKood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEhisKood() {
        return ehisKood;
    }

    /**
     * Sets the value of the ehisKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEhisKood(BigInteger value) {
        this.ehisKood = value;
    }

    /**
     * Gets the value of the ehakKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEhakKood() {
        return ehakKood;
    }

    /**
     * Sets the value of the ehakKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEhakKood(String value) {
        this.ehakKood = value;
    }

    /**
     * Gets the value of the klOppetase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlOppetase() {
        return klOppetase;
    }

    /**
     * Sets the value of the klOppetase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlOppetase(String value) {
        this.klOppetase = value;
    }

    /**
     * Gets the value of the haldajaRegkood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHaldajaRegkood() {
        return haldajaRegkood;
    }

    /**
     * Sets the value of the haldajaRegkood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHaldajaRegkood(String value) {
        this.haldajaRegkood = value;
    }

}
