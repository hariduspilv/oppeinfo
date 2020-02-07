
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oppeasutus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oppeasutus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="koolId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;sequence&gt;
 *           &lt;element name="pedagoog" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}pedagoog" maxOccurs="100000" minOccurs="0"/&gt;
 *           &lt;element name="pedagoogKustutaAlam" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}pedagoogKustutaAlam" maxOccurs="100000" minOccurs="0"/&gt;
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
@XmlType(name = "oppeasutus", propOrder = {
    "koolId",
    "pedagoog",
    "pedagoogKustutaAlam"
})
public class Oppeasutus {

    protected String koolId;
    protected List<Pedagoog> pedagoog;
    protected List<PedagoogKustutaAlam> pedagoogKustutaAlam;

    /**
     * Gets the value of the koolId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKoolId() {
        return koolId;
    }

    /**
     * Sets the value of the koolId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKoolId(String value) {
        this.koolId = value;
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
     * {@link Pedagoog }
     * 
     * 
     */
    public List<Pedagoog> getPedagoog() {
        if (pedagoog == null) {
            pedagoog = new ArrayList<Pedagoog>();
        }
        return this.pedagoog;
    }

    /**
     * Gets the value of the pedagoogKustutaAlam property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pedagoogKustutaAlam property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPedagoogKustutaAlam().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PedagoogKustutaAlam }
     * 
     * 
     */
    public List<PedagoogKustutaAlam> getPedagoogKustutaAlam() {
        if (pedagoogKustutaAlam == null) {
            pedagoogKustutaAlam = new ArrayList<PedagoogKustutaAlam>();
        }
        return this.pedagoogKustutaAlam;
    }

}
