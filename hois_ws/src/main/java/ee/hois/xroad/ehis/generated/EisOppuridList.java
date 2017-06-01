
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for eisOppuridList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eisOppuridList"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="isikukood" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisIsikukood" maxOccurs="500" minOccurs="0"/&gt;
 *           &lt;element name="oppeasutus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}eisOppuridOppeasutused" minOccurs="0"/&gt;
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
@XmlType(name = "eisOppuridList", propOrder = {
    "isikukood",
    "oppeasutus"
})
public class EisOppuridList {

    protected List<String> isikukood;
    protected EisOppuridOppeasutused oppeasutus;

    /**
     * Gets the value of the isikukood property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the isikukood property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIsikukood().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getIsikukood() {
        if (isikukood == null) {
            isikukood = new ArrayList<String>();
        }
        return this.isikukood;
    }

    /**
     * Gets the value of the oppeasutus property.
     * 
     * @return
     *     possible object is
     *     {@link EisOppuridOppeasutused }
     *     
     */
    public EisOppuridOppeasutused getOppeasutus() {
        return oppeasutus;
    }

    /**
     * Sets the value of the oppeasutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link EisOppuridOppeasutused }
     *     
     */
    public void setOppeasutus(EisOppuridOppeasutused value) {
        this.oppeasutus = value;
    }

}
