
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for yhlVanemaNousolek complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="yhlVanemaNousolek"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="nousolek" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlVanemaNousolekKirje"/&gt;
 *           &lt;element name="mitteNousolek" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlVanemaNousolekKirje"/&gt;
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
@XmlType(name = "yhlVanemaNousolek", propOrder = {
    "nousolek",
    "mitteNousolek"
})
public class YhlVanemaNousolek {

    protected YhlVanemaNousolekKirje nousolek;
    protected YhlVanemaNousolekKirje mitteNousolek;

    /**
     * Gets the value of the nousolek property.
     * 
     * @return
     *     possible object is
     *     {@link YhlVanemaNousolekKirje }
     *     
     */
    public YhlVanemaNousolekKirje getNousolek() {
        return nousolek;
    }

    /**
     * Sets the value of the nousolek property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlVanemaNousolekKirje }
     *     
     */
    public void setNousolek(YhlVanemaNousolekKirje value) {
        this.nousolek = value;
    }

    /**
     * Gets the value of the mitteNousolek property.
     * 
     * @return
     *     possible object is
     *     {@link YhlVanemaNousolekKirje }
     *     
     */
    public YhlVanemaNousolekKirje getMitteNousolek() {
        return mitteNousolek;
    }

    /**
     * Sets the value of the mitteNousolek property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlVanemaNousolekKirje }
     *     
     */
    public void setMitteNousolek(YhlVanemaNousolekKirje value) {
        this.mitteNousolek = value;
    }

}
