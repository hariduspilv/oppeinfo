
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for khlStipendium complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="khlStipendium"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="klStipendiumLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="stipendiumAlgusKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="stipendiumLoppKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="doktoranditoetusSumma" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutuseKirjeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "khlStipendium", propOrder = {
    "klStipendiumLiik",
    "stipendiumAlgusKp",
    "stipendiumLoppKp",
    "doktoranditoetusSumma",
    "oppeasutuseKirjeId"
})
public class KhlStipendium {

    @XmlElement(required = true)
    protected String klStipendiumLiik;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar stipendiumAlgusKp;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar stipendiumLoppKp;
    protected String doktoranditoetusSumma;
    protected String oppeasutuseKirjeId;

    /**
     * Gets the value of the klStipendiumLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlStipendiumLiik() {
        return klStipendiumLiik;
    }

    /**
     * Sets the value of the klStipendiumLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlStipendiumLiik(String value) {
        this.klStipendiumLiik = value;
    }

    /**
     * Gets the value of the stipendiumAlgusKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStipendiumAlgusKp() {
        return stipendiumAlgusKp;
    }

    /**
     * Sets the value of the stipendiumAlgusKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStipendiumAlgusKp(XMLGregorianCalendar value) {
        this.stipendiumAlgusKp = value;
    }

    /**
     * Gets the value of the stipendiumLoppKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStipendiumLoppKp() {
        return stipendiumLoppKp;
    }

    /**
     * Sets the value of the stipendiumLoppKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStipendiumLoppKp(XMLGregorianCalendar value) {
        this.stipendiumLoppKp = value;
    }

    /**
     * Gets the value of the doktoranditoetusSumma property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDoktoranditoetusSumma() {
        return doktoranditoetusSumma;
    }

    /**
     * Sets the value of the doktoranditoetusSumma property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDoktoranditoetusSumma(String value) {
        this.doktoranditoetusSumma = value;
    }

    /**
     * Gets the value of the oppeasutuseKirjeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutuseKirjeId() {
        return oppeasutuseKirjeId;
    }

    /**
     * Sets the value of the oppeasutuseKirjeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutuseKirjeId(String value) {
        this.oppeasutuseKirjeId = value;
    }

}
