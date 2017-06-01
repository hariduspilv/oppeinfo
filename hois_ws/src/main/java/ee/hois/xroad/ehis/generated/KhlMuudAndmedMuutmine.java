
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for khlMuudAndmedMuutmine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="khlMuudAndmedMuutmine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="muutusKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="klEelnevHaridus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klYhiselamu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="omandabHaridustKinnipidamisasutuses" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
 *         &lt;element name="eelmOppeasutusLopetAasta" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisAastaType" minOccurs="0"/&gt;
 *         &lt;element name="eelmOppeasutusOppevorm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutusLopetValismaal" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutusLopetValismaalRiik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutusLopetValismaalOppeasutus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "khlMuudAndmedMuutmine", propOrder = {
    "muutusKp",
    "klEelnevHaridus",
    "klYhiselamu",
    "omandabHaridustKinnipidamisasutuses",
    "eelmOppeasutusLopetAasta",
    "eelmOppeasutusOppevorm",
    "oppeasutusLopetValismaal",
    "oppeasutusLopetValismaalRiik",
    "oppeasutusLopetValismaalOppeasutus"
})
public class KhlMuudAndmedMuutmine {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar muutusKp;
    protected String klEelnevHaridus;
    protected String klYhiselamu;
    protected String omandabHaridustKinnipidamisasutuses;
    @XmlSchemaType(name = "positiveInteger")
    protected Integer eelmOppeasutusLopetAasta;
    protected String eelmOppeasutusOppevorm;
    protected String oppeasutusLopetValismaal;
    protected String oppeasutusLopetValismaalRiik;
    protected String oppeasutusLopetValismaalOppeasutus;

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
     * Gets the value of the klEelnevHaridus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlEelnevHaridus() {
        return klEelnevHaridus;
    }

    /**
     * Sets the value of the klEelnevHaridus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlEelnevHaridus(String value) {
        this.klEelnevHaridus = value;
    }

    /**
     * Gets the value of the klYhiselamu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlYhiselamu() {
        return klYhiselamu;
    }

    /**
     * Sets the value of the klYhiselamu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlYhiselamu(String value) {
        this.klYhiselamu = value;
    }

    /**
     * Gets the value of the omandabHaridustKinnipidamisasutuses property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOmandabHaridustKinnipidamisasutuses() {
        return omandabHaridustKinnipidamisasutuses;
    }

    /**
     * Sets the value of the omandabHaridustKinnipidamisasutuses property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOmandabHaridustKinnipidamisasutuses(String value) {
        this.omandabHaridustKinnipidamisasutuses = value;
    }

    /**
     * Gets the value of the eelmOppeasutusLopetAasta property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEelmOppeasutusLopetAasta() {
        return eelmOppeasutusLopetAasta;
    }

    /**
     * Sets the value of the eelmOppeasutusLopetAasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEelmOppeasutusLopetAasta(Integer value) {
        this.eelmOppeasutusLopetAasta = value;
    }

    /**
     * Gets the value of the eelmOppeasutusOppevorm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEelmOppeasutusOppevorm() {
        return eelmOppeasutusOppevorm;
    }

    /**
     * Sets the value of the eelmOppeasutusOppevorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEelmOppeasutusOppevorm(String value) {
        this.eelmOppeasutusOppevorm = value;
    }

    /**
     * Gets the value of the oppeasutusLopetValismaal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutusLopetValismaal() {
        return oppeasutusLopetValismaal;
    }

    /**
     * Sets the value of the oppeasutusLopetValismaal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutusLopetValismaal(String value) {
        this.oppeasutusLopetValismaal = value;
    }

    /**
     * Gets the value of the oppeasutusLopetValismaalRiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutusLopetValismaalRiik() {
        return oppeasutusLopetValismaalRiik;
    }

    /**
     * Sets the value of the oppeasutusLopetValismaalRiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutusLopetValismaalRiik(String value) {
        this.oppeasutusLopetValismaalRiik = value;
    }

    /**
     * Gets the value of the oppeasutusLopetValismaalOppeasutus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutusLopetValismaalOppeasutus() {
        return oppeasutusLopetValismaalOppeasutus;
    }

    /**
     * Sets the value of the oppeasutusLopetValismaalOppeasutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutusLopetValismaalOppeasutus(String value) {
        this.oppeasutusLopetValismaalOppeasutus = value;
    }

}
