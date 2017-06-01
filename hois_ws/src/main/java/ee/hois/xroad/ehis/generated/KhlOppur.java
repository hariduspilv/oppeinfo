
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for khlOppur complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="khlOppur"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="lisamine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlLisamine"/&gt;
 *           &lt;element name="muutmine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlMuutmine"/&gt;
 *           &lt;element name="muutmineId" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlMuutmineIdList"/&gt;
 *           &lt;element name="muutmineIsikukood" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlMuutmineIsikukood"/&gt;
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
@XmlType(name = "khlOppur", propOrder = {
    "lisamine",
    "muutmine",
    "muutmineId",
    "muutmineIsikukood"
})
public class KhlOppur {

    protected KhlLisamine lisamine;
    protected KhlMuutmine muutmine;
    protected KhlMuutmineIdList muutmineId;
    protected KhlMuutmineIsikukood muutmineIsikukood;

    /**
     * Gets the value of the lisamine property.
     * 
     * @return
     *     possible object is
     *     {@link KhlLisamine }
     *     
     */
    public KhlLisamine getLisamine() {
        return lisamine;
    }

    /**
     * Sets the value of the lisamine property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlLisamine }
     *     
     */
    public void setLisamine(KhlLisamine value) {
        this.lisamine = value;
    }

    /**
     * Gets the value of the muutmine property.
     * 
     * @return
     *     possible object is
     *     {@link KhlMuutmine }
     *     
     */
    public KhlMuutmine getMuutmine() {
        return muutmine;
    }

    /**
     * Sets the value of the muutmine property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlMuutmine }
     *     
     */
    public void setMuutmine(KhlMuutmine value) {
        this.muutmine = value;
    }

    /**
     * Gets the value of the muutmineId property.
     * 
     * @return
     *     possible object is
     *     {@link KhlMuutmineIdList }
     *     
     */
    public KhlMuutmineIdList getMuutmineId() {
        return muutmineId;
    }

    /**
     * Sets the value of the muutmineId property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlMuutmineIdList }
     *     
     */
    public void setMuutmineId(KhlMuutmineIdList value) {
        this.muutmineId = value;
    }

    /**
     * Gets the value of the muutmineIsikukood property.
     * 
     * @return
     *     possible object is
     *     {@link KhlMuutmineIsikukood }
     *     
     */
    public KhlMuutmineIsikukood getMuutmineIsikukood() {
        return muutmineIsikukood;
    }

    /**
     * Sets the value of the muutmineIsikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlMuutmineIsikukood }
     *     
     */
    public void setMuutmineIsikukood(KhlMuutmineIsikukood value) {
        this.muutmineIsikukood = value;
    }

}
