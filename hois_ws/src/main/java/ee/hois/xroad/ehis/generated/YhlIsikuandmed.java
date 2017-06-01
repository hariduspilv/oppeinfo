
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for yhlIsikuandmed complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="yhlIsikuandmed"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="klEmakeel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="onHoolealune" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
 *         &lt;element name="onOrb" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
 *         &lt;element name="tegelikElukoht" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlElukoht" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "yhlIsikuandmed", propOrder = {
    "klEmakeel",
    "onHoolealune",
    "onOrb",
    "tegelikElukoht"
})
public class YhlIsikuandmed {

    protected String klEmakeel;
    protected String onHoolealune;
    protected String onOrb;
    protected YhlElukoht tegelikElukoht;

    /**
     * Gets the value of the klEmakeel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlEmakeel() {
        return klEmakeel;
    }

    /**
     * Sets the value of the klEmakeel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlEmakeel(String value) {
        this.klEmakeel = value;
    }

    /**
     * Gets the value of the onHoolealune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnHoolealune() {
        return onHoolealune;
    }

    /**
     * Sets the value of the onHoolealune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnHoolealune(String value) {
        this.onHoolealune = value;
    }

    /**
     * Gets the value of the onOrb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnOrb() {
        return onOrb;
    }

    /**
     * Sets the value of the onOrb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnOrb(String value) {
        this.onOrb = value;
    }

    /**
     * Gets the value of the tegelikElukoht property.
     * 
     * @return
     *     possible object is
     *     {@link YhlElukoht }
     *     
     */
    public YhlElukoht getTegelikElukoht() {
        return tegelikElukoht;
    }

    /**
     * Sets the value of the tegelikElukoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlElukoht }
     *     
     */
    public void setTegelikElukoht(YhlElukoht value) {
        this.tegelikElukoht = value;
    }

}
