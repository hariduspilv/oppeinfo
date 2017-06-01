
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for yhlErivajadusedTugiteenused complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="yhlErivajadusedTugiteenused"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="muutusKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="erivajadused" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlErivajadused" minOccurs="0"/&gt;
 *         &lt;element name="tugiteenused" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlTugiteenused" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "yhlErivajadusedTugiteenused", propOrder = {
    "muutusKp",
    "erivajadused",
    "tugiteenused"
})
public class YhlErivajadusedTugiteenused {

    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar muutusKp;
    protected YhlErivajadused erivajadused;
    protected YhlTugiteenused tugiteenused;

    /**
     * Gets the value of the muutusKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMuutusKp() {
        return muutusKp;
    }

    /**
     * Sets the value of the muutusKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMuutusKp(XMLGregorianCalendar value) {
        this.muutusKp = value;
    }

    /**
     * Gets the value of the erivajadused property.
     * 
     * @return
     *     possible object is
     *     {@link YhlErivajadused }
     *     
     */
    public YhlErivajadused getErivajadused() {
        return erivajadused;
    }

    /**
     * Sets the value of the erivajadused property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlErivajadused }
     *     
     */
    public void setErivajadused(YhlErivajadused value) {
        this.erivajadused = value;
    }

    /**
     * Gets the value of the tugiteenused property.
     * 
     * @return
     *     possible object is
     *     {@link YhlTugiteenused }
     *     
     */
    public YhlTugiteenused getTugiteenused() {
        return tugiteenused;
    }

    /**
     * Sets the value of the tugiteenused property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlTugiteenused }
     *     
     */
    public void setTugiteenused(YhlTugiteenused value) {
        this.tugiteenused = value;
    }

}
