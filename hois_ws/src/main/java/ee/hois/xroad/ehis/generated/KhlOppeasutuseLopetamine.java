
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for khlOppeasutuseLopetamine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="khlOppeasutuseLopetamine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="muutusKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="lopudokumendiNr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cumLaude" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
 *         &lt;element name="eestikeelneAkademOiend" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlOiendType" minOccurs="0"/&gt;
 *         &lt;element name="inglisekeelneAkademOiend" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlOiendType" minOccurs="0"/&gt;
 *         &lt;element name="klAkadKraad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klSpetsialiseerumine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "khlOppeasutuseLopetamine", propOrder = {
    "muutusKp",
    "lopudokumendiNr",
    "cumLaude",
    "eestikeelneAkademOiend",
    "inglisekeelneAkademOiend",
    "klAkadKraad",
    "klSpetsialiseerumine"
})
public class KhlOppeasutuseLopetamine {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar muutusKp;
    @XmlElement(required = true)
    protected String lopudokumendiNr;
    protected String cumLaude;
    protected KhlOiendType eestikeelneAkademOiend;
    protected KhlOiendType inglisekeelneAkademOiend;
    protected String klAkadKraad;
    protected String klSpetsialiseerumine;

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
     * Gets the value of the cumLaude property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCumLaude() {
        return cumLaude;
    }

    /**
     * Sets the value of the cumLaude property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCumLaude(String value) {
        this.cumLaude = value;
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

    /**
     * Gets the value of the klAkadKraad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlAkadKraad() {
        return klAkadKraad;
    }

    /**
     * Sets the value of the klAkadKraad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlAkadKraad(String value) {
        this.klAkadKraad = value;
    }

    /**
     * Gets the value of the klSpetsialiseerumine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlSpetsialiseerumine() {
        return klSpetsialiseerumine;
    }

    /**
     * Sets the value of the klSpetsialiseerumine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlSpetsialiseerumine(String value) {
        this.klSpetsialiseerumine = value;
    }

}
