
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ettevotja_muudatus_kandevaline complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevotja_muudatus_kandevaline"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kandevaline_tyyp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ettevotja_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ettevotja_ariregistri_kood" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="ettevotja_arinimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ettevotja_oiguslik_vorm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kirje_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ettevotja_muudatus_kandevaline", propOrder = {
    "kandevalineTyyp",
    "ettevotjaId",
    "ettevotjaAriregistriKood",
    "ettevotjaArinimi",
    "ettevotjaOiguslikVorm",
    "kirjeId"
})
public class EttevotjaMuudatusKandevaline {

    @XmlElement(name = "kandevaline_tyyp")
    protected String kandevalineTyyp;
    @XmlElement(name = "ettevotja_id")
    protected String ettevotjaId;
    @XmlElement(name = "ettevotja_ariregistri_kood")
    protected Integer ettevotjaAriregistriKood;
    @XmlElement(name = "ettevotja_arinimi")
    protected String ettevotjaArinimi;
    @XmlElement(name = "ettevotja_oiguslik_vorm")
    protected String ettevotjaOiguslikVorm;
    @XmlElement(name = "kirje_id")
    protected String kirjeId;

    /**
     * Gets the value of the kandevalineTyyp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKandevalineTyyp() {
        return kandevalineTyyp;
    }

    /**
     * Sets the value of the kandevalineTyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKandevalineTyyp(String value) {
        this.kandevalineTyyp = value;
    }

    /**
     * Gets the value of the ettevotjaId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEttevotjaId() {
        return ettevotjaId;
    }

    /**
     * Sets the value of the ettevotjaId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEttevotjaId(String value) {
        this.ettevotjaId = value;
    }

    /**
     * Gets the value of the ettevotjaAriregistriKood property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEttevotjaAriregistriKood() {
        return ettevotjaAriregistriKood;
    }

    /**
     * Sets the value of the ettevotjaAriregistriKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEttevotjaAriregistriKood(Integer value) {
        this.ettevotjaAriregistriKood = value;
    }

    /**
     * Gets the value of the ettevotjaArinimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEttevotjaArinimi() {
        return ettevotjaArinimi;
    }

    /**
     * Sets the value of the ettevotjaArinimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEttevotjaArinimi(String value) {
        this.ettevotjaArinimi = value;
    }

    /**
     * Gets the value of the ettevotjaOiguslikVorm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEttevotjaOiguslikVorm() {
        return ettevotjaOiguslikVorm;
    }

    /**
     * Sets the value of the ettevotjaOiguslikVorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEttevotjaOiguslikVorm(String value) {
        this.ettevotjaOiguslikVorm = value;
    }

    /**
     * Gets the value of the kirjeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKirjeId() {
        return kirjeId;
    }

    /**
     * Sets the value of the kirjeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKirjeId(String value) {
        this.kirjeId = value;
    }

}
