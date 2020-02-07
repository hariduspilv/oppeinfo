
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for huviOppeasutus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="huviOppeasutus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="koolId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;sequence&gt;
 *           &lt;element name="oppur" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}huviOppur" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element name="pedagoog" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}huviPedagoog" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element name="oppurKustuta" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}huviKustutaOppurPeda" minOccurs="0"/&gt;
 *           &lt;element name="pedagoogKustuta" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}huviKustutaOppurPeda" minOccurs="0"/&gt;
 *         &lt;/sequence&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "huviOppeasutus", propOrder = {
    "koolId",
    "oppur",
    "pedagoog",
    "oppurKustuta",
    "pedagoogKustuta"
})
public class HuviOppeasutus {

    @XmlElement(required = true)
    protected BigInteger koolId;
    protected List<HuviOppur> oppur;
    protected List<HuviPedagoog> pedagoog;
    protected HuviKustutaOppurPeda oppurKustuta;
    protected HuviKustutaOppurPeda pedagoogKustuta;

    /**
     * Gets the value of the koolId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKoolId() {
        return koolId;
    }

    /**
     * Sets the value of the koolId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKoolId(BigInteger value) {
        this.koolId = value;
    }

    /**
     * Gets the value of the oppur property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oppur property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOppur().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HuviOppur }
     * 
     * 
     */
    public List<HuviOppur> getOppur() {
        if (oppur == null) {
            oppur = new ArrayList<HuviOppur>();
        }
        return this.oppur;
    }

    /**
     * Gets the value of the pedagoog property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pedagoog property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPedagoog().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HuviPedagoog }
     * 
     * 
     */
    public List<HuviPedagoog> getPedagoog() {
        if (pedagoog == null) {
            pedagoog = new ArrayList<HuviPedagoog>();
        }
        return this.pedagoog;
    }

    /**
     * Gets the value of the oppurKustuta property.
     * 
     * @return
     *     possible object is
     *     {@link HuviKustutaOppurPeda }
     *     
     */
    public HuviKustutaOppurPeda getOppurKustuta() {
        return oppurKustuta;
    }

    /**
     * Sets the value of the oppurKustuta property.
     * 
     * @param value
     *     allowed object is
     *     {@link HuviKustutaOppurPeda }
     *     
     */
    public void setOppurKustuta(HuviKustutaOppurPeda value) {
        this.oppurKustuta = value;
    }

    /**
     * Gets the value of the pedagoogKustuta property.
     * 
     * @return
     *     possible object is
     *     {@link HuviKustutaOppurPeda }
     *     
     */
    public HuviKustutaOppurPeda getPedagoogKustuta() {
        return pedagoogKustuta;
    }

    /**
     * Sets the value of the pedagoogKustuta property.
     * 
     * @param value
     *     allowed object is
     *     {@link HuviKustutaOppurPeda }
     *     
     */
    public void setPedagoogKustuta(HuviKustutaOppurPeda value) {
        this.pedagoogKustuta = value;
    }

}
