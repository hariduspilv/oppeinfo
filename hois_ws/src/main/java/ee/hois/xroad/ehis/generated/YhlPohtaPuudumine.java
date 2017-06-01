
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for yhlPohtaPuudumine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="yhlPohtaPuudumine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="aasta" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisAastaType" minOccurs="0"/&gt;
 *         &lt;element name="veerand" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/&gt;
 *         &lt;element name="tunde" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/&gt;
 *         &lt;element name="puudumisi" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "yhlPohtaPuudumine", propOrder = {
    "aasta",
    "veerand",
    "tunde",
    "puudumisi"
})
public class YhlPohtaPuudumine {

    @XmlSchemaType(name = "positiveInteger")
    protected Integer aasta;
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger veerand;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger tunde;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger puudumisi;

    /**
     * Gets the value of the aasta property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAasta() {
        return aasta;
    }

    /**
     * Sets the value of the aasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAasta(Integer value) {
        this.aasta = value;
    }

    /**
     * Gets the value of the veerand property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVeerand() {
        return veerand;
    }

    /**
     * Sets the value of the veerand property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVeerand(BigInteger value) {
        this.veerand = value;
    }

    /**
     * Gets the value of the tunde property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTunde() {
        return tunde;
    }

    /**
     * Sets the value of the tunde property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTunde(BigInteger value) {
        this.tunde = value;
    }

    /**
     * Gets the value of the puudumisi property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPuudumisi() {
        return puudumisi;
    }

    /**
     * Sets the value of the puudumisi property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPuudumisi(BigInteger value) {
        this.puudumisi = value;
    }

}
