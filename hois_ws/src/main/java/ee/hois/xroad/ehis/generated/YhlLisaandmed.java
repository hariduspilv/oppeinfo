
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for yhlLisaandmed complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="yhlLisaandmed"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="liikumiseViis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klVahetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "yhlLisaandmed", propOrder = {
    "liikumiseViis",
    "klVahetus"
})
public class YhlLisaandmed {

    protected String liikumiseViis;
    protected String klVahetus;

    /**
     * Gets the value of the liikumiseViis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLiikumiseViis() {
        return liikumiseViis;
    }

    /**
     * Sets the value of the liikumiseViis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLiikumiseViis(String value) {
        this.liikumiseViis = value;
    }

    /**
     * Gets the value of the klVahetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlVahetus() {
        return klVahetus;
    }

    /**
     * Sets the value of the klVahetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlVahetus(String value) {
        this.klVahetus = value;
    }

}
