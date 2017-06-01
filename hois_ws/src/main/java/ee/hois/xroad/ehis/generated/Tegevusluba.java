
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tegevusluba complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tegevusluba"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="tyyp" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="menetlusStaatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="loaNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="loomiseKp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tyhistamiseKp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kehtivAlates" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tegevusluba", propOrder = {
    "id",
    "tyyp",
    "liik",
    "menetlusStaatus",
    "loaNumber",
    "loomiseKp",
    "tyhistamiseKp",
    "kehtivAlates",
    "kehtivKuni"
})
public class Tegevusluba {

    protected BigInteger id;
    protected int tyyp;
    @XmlElement(required = true)
    protected String liik;
    protected String menetlusStaatus;
    protected String loaNumber;
    protected String loomiseKp;
    protected String tyhistamiseKp;
    protected String kehtivAlates;
    protected String kehtivKuni;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setId(BigInteger value) {
        this.id = value;
    }

    /**
     * Gets the value of the tyyp property.
     * 
     */
    public int getTyyp() {
        return tyyp;
    }

    /**
     * Sets the value of the tyyp property.
     * 
     */
    public void setTyyp(int value) {
        this.tyyp = value;
    }

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
     * Gets the value of the menetlusStaatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMenetlusStaatus() {
        return menetlusStaatus;
    }

    /**
     * Sets the value of the menetlusStaatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMenetlusStaatus(String value) {
        this.menetlusStaatus = value;
    }

    /**
     * Gets the value of the loaNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoaNumber() {
        return loaNumber;
    }

    /**
     * Sets the value of the loaNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoaNumber(String value) {
        this.loaNumber = value;
    }

    /**
     * Gets the value of the loomiseKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoomiseKp() {
        return loomiseKp;
    }

    /**
     * Sets the value of the loomiseKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoomiseKp(String value) {
        this.loomiseKp = value;
    }

    /**
     * Gets the value of the tyhistamiseKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTyhistamiseKp() {
        return tyhistamiseKp;
    }

    /**
     * Sets the value of the tyhistamiseKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTyhistamiseKp(String value) {
        this.tyhistamiseKp = value;
    }

    /**
     * Gets the value of the kehtivAlates property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKehtivAlates() {
        return kehtivAlates;
    }

    /**
     * Sets the value of the kehtivAlates property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKehtivAlates(String value) {
        this.kehtivAlates = value;
    }

    /**
     * Gets the value of the kehtivKuni property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKehtivKuni() {
        return kehtivKuni;
    }

    /**
     * Sets the value of the kehtivKuni property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKehtivKuni(String value) {
        this.kehtivKuni = value;
    }

}
