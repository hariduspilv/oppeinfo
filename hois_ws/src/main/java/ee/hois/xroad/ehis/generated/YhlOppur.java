
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for yhlOppur complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="yhlOppur"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="isikukood" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisIsikukood"/&gt;
 *           &lt;element name="isikuandmed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlOppurIsikuandmed"/&gt;
 *           &lt;element name="oppurKoolId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;/choice&gt;
 *         &lt;choice&gt;
 *           &lt;element name="lisamine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlYldharidusLisa"/&gt;
 *           &lt;element name="muutmine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlYldharidusMuuda"/&gt;
 *           &lt;element name="muutmineId" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlMuutmineIds"/&gt;
 *           &lt;element name="muutmineIsikukood" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlMuutmineIsikukood"/&gt;
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
@XmlType(name = "yhlOppur", propOrder = {
    "isikukood",
    "isikuandmed",
    "oppurKoolId",
    "lisamine",
    "muutmine",
    "muutmineId",
    "muutmineIsikukood"
})
public class YhlOppur {

    protected String isikukood;
    protected YhlOppurIsikuandmed isikuandmed;
    protected String oppurKoolId;
    protected YhlYldharidusLisa lisamine;
    protected YhlYldharidusMuuda muutmine;
    protected YhlMuutmineIds muutmineId;
    protected YhlMuutmineIsikukood muutmineIsikukood;

    /**
     * Gets the value of the isikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukood() {
        return isikukood;
    }

    /**
     * Sets the value of the isikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukood(String value) {
        this.isikukood = value;
    }

    /**
     * Gets the value of the isikuandmed property.
     * 
     * @return
     *     possible object is
     *     {@link YhlOppurIsikuandmed }
     *     
     */
    public YhlOppurIsikuandmed getIsikuandmed() {
        return isikuandmed;
    }

    /**
     * Sets the value of the isikuandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlOppurIsikuandmed }
     *     
     */
    public void setIsikuandmed(YhlOppurIsikuandmed value) {
        this.isikuandmed = value;
    }

    /**
     * Gets the value of the oppurKoolId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppurKoolId() {
        return oppurKoolId;
    }

    /**
     * Sets the value of the oppurKoolId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppurKoolId(String value) {
        this.oppurKoolId = value;
    }

    /**
     * Gets the value of the lisamine property.
     * 
     * @return
     *     possible object is
     *     {@link YhlYldharidusLisa }
     *     
     */
    public YhlYldharidusLisa getLisamine() {
        return lisamine;
    }

    /**
     * Sets the value of the lisamine property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlYldharidusLisa }
     *     
     */
    public void setLisamine(YhlYldharidusLisa value) {
        this.lisamine = value;
    }

    /**
     * Gets the value of the muutmine property.
     * 
     * @return
     *     possible object is
     *     {@link YhlYldharidusMuuda }
     *     
     */
    public YhlYldharidusMuuda getMuutmine() {
        return muutmine;
    }

    /**
     * Sets the value of the muutmine property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlYldharidusMuuda }
     *     
     */
    public void setMuutmine(YhlYldharidusMuuda value) {
        this.muutmine = value;
    }

    /**
     * Gets the value of the muutmineId property.
     * 
     * @return
     *     possible object is
     *     {@link YhlMuutmineIds }
     *     
     */
    public YhlMuutmineIds getMuutmineId() {
        return muutmineId;
    }

    /**
     * Sets the value of the muutmineId property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlMuutmineIds }
     *     
     */
    public void setMuutmineId(YhlMuutmineIds value) {
        this.muutmineId = value;
    }

    /**
     * Gets the value of the muutmineIsikukood property.
     * 
     * @return
     *     possible object is
     *     {@link YhlMuutmineIsikukood }
     *     
     */
    public YhlMuutmineIsikukood getMuutmineIsikukood() {
        return muutmineIsikukood;
    }

    /**
     * Sets the value of the muutmineIsikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlMuutmineIsikukood }
     *     
     */
    public void setMuutmineIsikukood(YhlMuutmineIsikukood value) {
        this.muutmineIsikukood = value;
    }

}
