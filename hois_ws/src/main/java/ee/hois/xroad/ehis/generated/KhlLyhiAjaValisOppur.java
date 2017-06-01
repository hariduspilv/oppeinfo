
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for khlLyhiAjaValisOppur complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="khlLyhiAjaValisOppur"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="salvestamine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlLyhiAjaValisOppurSalvestamine"/&gt;
 *           &lt;element name="kustutamine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlLyhiAjaValisOppurKustutamine"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "khlLyhiAjaValisOppur", propOrder = {
    "salvestamine",
    "kustutamine"
})
public class KhlLyhiAjaValisOppur {

    protected KhlLyhiAjaValisOppurSalvestamine salvestamine;
    protected KhlLyhiAjaValisOppurKustutamine kustutamine;

    /**
     * Gets the value of the salvestamine property.
     * 
     * @return
     *     possible object is
     *     {@link KhlLyhiAjaValisOppurSalvestamine }
     *     
     */
    public KhlLyhiAjaValisOppurSalvestamine getSalvestamine() {
        return salvestamine;
    }

    /**
     * Sets the value of the salvestamine property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlLyhiAjaValisOppurSalvestamine }
     *     
     */
    public void setSalvestamine(KhlLyhiAjaValisOppurSalvestamine value) {
        this.salvestamine = value;
    }

    /**
     * Gets the value of the kustutamine property.
     * 
     * @return
     *     possible object is
     *     {@link KhlLyhiAjaValisOppurKustutamine }
     *     
     */
    public KhlLyhiAjaValisOppurKustutamine getKustutamine() {
        return kustutamine;
    }

    /**
     * Sets the value of the kustutamine property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlLyhiAjaValisOppurKustutamine }
     *     
     */
    public void setKustutamine(KhlLyhiAjaValisOppurKustutamine value) {
        this.kustutamine = value;
    }

}
