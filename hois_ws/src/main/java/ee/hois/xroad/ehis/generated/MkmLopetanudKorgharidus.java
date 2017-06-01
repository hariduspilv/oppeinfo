
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mkmLopetanudKorgharidus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mkmLopetanudKorgharidus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dokumendiNr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dokumendiNimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppekava" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppekavaEng" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lopetamiseKp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutusEng" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutusRegnr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mkmLopetanudKorgharidus", propOrder = {
    "dokumendiNr",
    "dokumendiNimetus",
    "oppekava",
    "oppekavaEng",
    "lopetamiseKp",
    "oppeasutus",
    "oppeasutusEng",
    "oppeasutusRegnr"
})
public class MkmLopetanudKorgharidus {

    protected String dokumendiNr;
    protected String dokumendiNimetus;
    protected String oppekava;
    protected String oppekavaEng;
    protected String lopetamiseKp;
    protected String oppeasutus;
    protected String oppeasutusEng;
    protected String oppeasutusRegnr;

    /**
     * Gets the value of the dokumendiNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiNr() {
        return dokumendiNr;
    }

    /**
     * Sets the value of the dokumendiNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiNr(String value) {
        this.dokumendiNr = value;
    }

    /**
     * Gets the value of the dokumendiNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiNimetus() {
        return dokumendiNimetus;
    }

    /**
     * Sets the value of the dokumendiNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiNimetus(String value) {
        this.dokumendiNimetus = value;
    }

    /**
     * Gets the value of the oppekava property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekava() {
        return oppekava;
    }

    /**
     * Sets the value of the oppekava property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekava(String value) {
        this.oppekava = value;
    }

    /**
     * Gets the value of the oppekavaEng property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavaEng() {
        return oppekavaEng;
    }

    /**
     * Sets the value of the oppekavaEng property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavaEng(String value) {
        this.oppekavaEng = value;
    }

    /**
     * Gets the value of the lopetamiseKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLopetamiseKp() {
        return lopetamiseKp;
    }

    /**
     * Sets the value of the lopetamiseKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLopetamiseKp(String value) {
        this.lopetamiseKp = value;
    }

    /**
     * Gets the value of the oppeasutus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutus() {
        return oppeasutus;
    }

    /**
     * Sets the value of the oppeasutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutus(String value) {
        this.oppeasutus = value;
    }

    /**
     * Gets the value of the oppeasutusEng property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutusEng() {
        return oppeasutusEng;
    }

    /**
     * Sets the value of the oppeasutusEng property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutusEng(String value) {
        this.oppeasutusEng = value;
    }

    /**
     * Gets the value of the oppeasutusRegnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutusRegnr() {
        return oppeasutusRegnr;
    }

    /**
     * Sets the value of the oppeasutusRegnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutusRegnr(String value) {
        this.oppeasutusRegnr = value;
    }

}
