
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tkTegevusluba complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tkTegevusluba"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="liik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kategooria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="keeletase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ministriKaskkirjaNr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ministriKaskkirjaKp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kehtivAlgusKp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kehtivLoppKp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tkTegevusluba", propOrder = {
    "nr",
    "liik",
    "nimi",
    "kategooria",
    "keeletase",
    "ministriKaskkirjaNr",
    "ministriKaskkirjaKp",
    "kehtivAlgusKp",
    "kehtivLoppKp"
})
public class TkTegevusluba {

    protected String nr;
    protected String liik;
    protected String nimi;
    protected String kategooria;
    protected String keeletase;
    protected String ministriKaskkirjaNr;
    protected String ministriKaskkirjaKp;
    protected String kehtivAlgusKp;
    protected String kehtivLoppKp;

    /**
     * Gets the value of the nr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNr() {
        return nr;
    }

    /**
     * Sets the value of the nr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNr(String value) {
        this.nr = value;
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
     * Gets the value of the nimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * Sets the value of the nimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNimi(String value) {
        this.nimi = value;
    }

    /**
     * Gets the value of the kategooria property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKategooria() {
        return kategooria;
    }

    /**
     * Sets the value of the kategooria property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKategooria(String value) {
        this.kategooria = value;
    }

    /**
     * Gets the value of the keeletase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeeletase() {
        return keeletase;
    }

    /**
     * Sets the value of the keeletase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeeletase(String value) {
        this.keeletase = value;
    }

    /**
     * Gets the value of the ministriKaskkirjaNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMinistriKaskkirjaNr() {
        return ministriKaskkirjaNr;
    }

    /**
     * Sets the value of the ministriKaskkirjaNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMinistriKaskkirjaNr(String value) {
        this.ministriKaskkirjaNr = value;
    }

    /**
     * Gets the value of the ministriKaskkirjaKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMinistriKaskkirjaKp() {
        return ministriKaskkirjaKp;
    }

    /**
     * Sets the value of the ministriKaskkirjaKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMinistriKaskkirjaKp(String value) {
        this.ministriKaskkirjaKp = value;
    }

    /**
     * Gets the value of the kehtivAlgusKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKehtivAlgusKp() {
        return kehtivAlgusKp;
    }

    /**
     * Sets the value of the kehtivAlgusKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKehtivAlgusKp(String value) {
        this.kehtivAlgusKp = value;
    }

    /**
     * Gets the value of the kehtivLoppKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKehtivLoppKp() {
        return kehtivLoppKp;
    }

    /**
     * Sets the value of the kehtivLoppKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKehtivLoppKp(String value) {
        this.kehtivLoppKp = value;
    }

}
