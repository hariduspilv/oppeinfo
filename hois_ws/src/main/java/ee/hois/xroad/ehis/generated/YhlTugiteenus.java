
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for yhlTugiteenus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="yhlTugiteenus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="klTugiteenus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="rakendVoiVajatav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;element name="oppeaine" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;/sequence&gt;
 *         &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;element name="klOppeaine" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "yhlTugiteenus", propOrder = {
    "klTugiteenus",
    "rakendVoiVajatav",
    "oppeaine",
    "klOppeaine"
})
public class YhlTugiteenus {

    @XmlElement(required = true)
    protected String klTugiteenus;
    @XmlElement(required = true)
    protected String rakendVoiVajatav;
    protected List<String> oppeaine;
    protected List<String> klOppeaine;

    /**
     * Gets the value of the klTugiteenus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlTugiteenus() {
        return klTugiteenus;
    }

    /**
     * Sets the value of the klTugiteenus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlTugiteenus(String value) {
        this.klTugiteenus = value;
    }

    /**
     * Gets the value of the rakendVoiVajatav property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRakendVoiVajatav() {
        return rakendVoiVajatav;
    }

    /**
     * Sets the value of the rakendVoiVajatav property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRakendVoiVajatav(String value) {
        this.rakendVoiVajatav = value;
    }

    /**
     * Gets the value of the oppeaine property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oppeaine property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOppeaine().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOppeaine() {
        if (oppeaine == null) {
            oppeaine = new ArrayList<String>();
        }
        return this.oppeaine;
    }

    /**
     * Gets the value of the klOppeaine property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the klOppeaine property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKlOppeaine().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getKlOppeaine() {
        if (klOppeaine == null) {
            klOppeaine = new ArrayList<String>();
        }
        return this.klOppeaine;
    }

}
