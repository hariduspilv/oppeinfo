
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for eeIsikukaartOppeaine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eeIsikukaartOppeaine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oppeaine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kooliaste" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="maht" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="kvalVastavus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eeIsikukaartOppeaine", propOrder = {
    "oppeaine",
    "kooliaste",
    "maht",
    "kvalVastavus"
})
public class EeIsikukaartOppeaine {

    protected String oppeaine;
    protected String kooliaste;
    protected Double maht;
    protected String kvalVastavus;

    /**
     * Gets the value of the oppeaine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeaine() {
        return oppeaine;
    }

    /**
     * Sets the value of the oppeaine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeaine(String value) {
        this.oppeaine = value;
    }

    /**
     * Gets the value of the kooliaste property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKooliaste() {
        return kooliaste;
    }

    /**
     * Sets the value of the kooliaste property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKooliaste(String value) {
        this.kooliaste = value;
    }

    /**
     * Gets the value of the maht property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMaht() {
        return maht;
    }

    /**
     * Sets the value of the maht property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMaht(Double value) {
        this.maht = value;
    }

    /**
     * Gets the value of the kvalVastavus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKvalVastavus() {
        return kvalVastavus;
    }

    /**
     * Sets the value of the kvalVastavus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKvalVastavus(String value) {
        this.kvalVastavus = value;
    }

}
