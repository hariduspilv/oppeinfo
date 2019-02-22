
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for osa_kitsendusedType_v4 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="osa_kitsendusedType_v4"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="jarjekoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kirjeldus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "osa_kitsendusedType_v4", propOrder = {
    "liik",
    "jarjekoht",
    "kirjeldus"
})
public class OsaKitsendusedTypeV4 {

    @XmlElement(required = true)
    protected String liik;
    protected String jarjekoht;
    @XmlElement(required = true)
    protected String kirjeldus;

    /**
     * Gets the value of the liik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLiik() {
        return liik;
    }

    /**
     * Sets the value of the liik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLiik(String value) {
        this.liik = value;
    }

    /**
     * Gets the value of the jarjekoht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJarjekoht() {
        return jarjekoht;
    }

    /**
     * Sets the value of the jarjekoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJarjekoht(String value) {
        this.jarjekoht = value;
    }

    /**
     * Gets the value of the kirjeldus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKirjeldus() {
        return kirjeldus;
    }

    /**
     * Sets the value of the kirjeldus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKirjeldus(String value) {
        this.kirjeldus = value;
    }

}
