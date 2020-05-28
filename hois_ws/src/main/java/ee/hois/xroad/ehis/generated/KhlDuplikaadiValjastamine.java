
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for khlDuplikaadiValjastamine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="khlDuplikaadiValjastamine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="muutusKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="lopudokumendiNr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="eestikeelneAkademOiend" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlOiendType" minOccurs="0"/&gt;
 *         &lt;element name="inglisekeelneAkademOiend" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlOiendType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "khlDuplikaadiValjastamine", propOrder = {
    "muutusKp",
    "lopudokumendiNr",
    "eestikeelneAkademOiend",
    "inglisekeelneAkademOiend"
})
public class KhlDuplikaadiValjastamine {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar muutusKp;
    @XmlElement(required = true)
    protected String lopudokumendiNr;
    protected KhlOiendType eestikeelneAkademOiend;
    protected KhlOiendType inglisekeelneAkademOiend;

    /**
     * Gets the value of the muutusKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMuutusKp() {
        return muutusKp;
    }

    /**
     * Sets the value of the muutusKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMuutusKp(XMLGregorianCalendar value) {
        this.muutusKp = value;
    }

    /**
     * Gets the value of the lopudokumendiNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLopudokumendiNr() {
        return lopudokumendiNr;
    }

    /**
     * Sets the value of the lopudokumendiNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLopudokumendiNr(String value) {
        this.lopudokumendiNr = value;
    }

    /**
     * Gets the value of the eestikeelneAkademOiend property.
     * 
     * @return
     *     possible object is
     *     {@link KhlOiendType }
     *     
     */
    public KhlOiendType getEestikeelneAkademOiend() {
        return eestikeelneAkademOiend;
    }

    /**
     * Sets the value of the eestikeelneAkademOiend property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlOiendType }
     *     
     */
    public void setEestikeelneAkademOiend(KhlOiendType value) {
        this.eestikeelneAkademOiend = value;
    }

    /**
     * Gets the value of the inglisekeelneAkademOiend property.
     * 
     * @return
     *     possible object is
     *     {@link KhlOiendType }
     *     
     */
    public KhlOiendType getInglisekeelneAkademOiend() {
        return inglisekeelneAkademOiend;
    }

    /**
     * Sets the value of the inglisekeelneAkademOiend property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlOiendType }
     *     
     */
    public void setInglisekeelneAkademOiend(KhlOiendType value) {
        this.inglisekeelneAkademOiend = value;
    }

}
