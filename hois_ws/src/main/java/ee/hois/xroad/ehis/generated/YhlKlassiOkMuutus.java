
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for yhlKlassiOkMuutus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="yhlKlassiOkMuutus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="muutusKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="klOppekava" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klKlass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="paralleeliTunnus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="liitklassiTunnus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klKlassAste" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klKlassLiik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pohikooliVimKlass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="gymVimKlass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="jaabKlassikursustKordama" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
 *         &lt;element name="omandabHaridKinnipidamisasutuses" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
 *         &lt;element name="lisainfo1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lisainfo2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lisainfo3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "yhlKlassiOkMuutus", propOrder = {
    "muutusKp",
    "klOppekava",
    "klKlass",
    "paralleeliTunnus",
    "liitklassiTunnus",
    "klKlassAste",
    "klKlassLiik",
    "pohikooliVimKlass",
    "gymVimKlass",
    "jaabKlassikursustKordama",
    "omandabHaridKinnipidamisasutuses",
    "lisainfo1",
    "lisainfo2",
    "lisainfo3"
})
public class YhlKlassiOkMuutus {

    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar muutusKp;
    protected String klOppekava;
    protected String klKlass;
    protected String paralleeliTunnus;
    protected String liitklassiTunnus;
    protected String klKlassAste;
    protected String klKlassLiik;
    protected String pohikooliVimKlass;
    protected String gymVimKlass;
    protected String jaabKlassikursustKordama;
    protected String omandabHaridKinnipidamisasutuses;
    protected String lisainfo1;
    protected String lisainfo2;
    protected String lisainfo3;

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
     * Gets the value of the klOppekava property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlOppekava() {
        return klOppekava;
    }

    /**
     * Sets the value of the klOppekava property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlOppekava(String value) {
        this.klOppekava = value;
    }

    /**
     * Gets the value of the klKlass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlKlass() {
        return klKlass;
    }

    /**
     * Sets the value of the klKlass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlKlass(String value) {
        this.klKlass = value;
    }

    /**
     * Gets the value of the paralleeliTunnus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParalleeliTunnus() {
        return paralleeliTunnus;
    }

    /**
     * Sets the value of the paralleeliTunnus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParalleeliTunnus(String value) {
        this.paralleeliTunnus = value;
    }

    /**
     * Gets the value of the liitklassiTunnus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLiitklassiTunnus() {
        return liitklassiTunnus;
    }

    /**
     * Sets the value of the liitklassiTunnus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLiitklassiTunnus(String value) {
        this.liitklassiTunnus = value;
    }

    /**
     * Gets the value of the klKlassAste property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlKlassAste() {
        return klKlassAste;
    }

    /**
     * Sets the value of the klKlassAste property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlKlassAste(String value) {
        this.klKlassAste = value;
    }

    /**
     * Gets the value of the klKlassLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlKlassLiik() {
        return klKlassLiik;
    }

    /**
     * Sets the value of the klKlassLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlKlassLiik(String value) {
        this.klKlassLiik = value;
    }

    /**
     * Gets the value of the pohikooliVimKlass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPohikooliVimKlass() {
        return pohikooliVimKlass;
    }

    /**
     * Sets the value of the pohikooliVimKlass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPohikooliVimKlass(String value) {
        this.pohikooliVimKlass = value;
    }

    /**
     * Gets the value of the gymVimKlass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGymVimKlass() {
        return gymVimKlass;
    }

    /**
     * Sets the value of the gymVimKlass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGymVimKlass(String value) {
        this.gymVimKlass = value;
    }

    /**
     * Gets the value of the jaabKlassikursustKordama property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJaabKlassikursustKordama() {
        return jaabKlassikursustKordama;
    }

    /**
     * Sets the value of the jaabKlassikursustKordama property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJaabKlassikursustKordama(String value) {
        this.jaabKlassikursustKordama = value;
    }

    /**
     * Gets the value of the omandabHaridKinnipidamisasutuses property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOmandabHaridKinnipidamisasutuses() {
        return omandabHaridKinnipidamisasutuses;
    }

    /**
     * Sets the value of the omandabHaridKinnipidamisasutuses property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOmandabHaridKinnipidamisasutuses(String value) {
        this.omandabHaridKinnipidamisasutuses = value;
    }

    /**
     * Gets the value of the lisainfo1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLisainfo1() {
        return lisainfo1;
    }

    /**
     * Sets the value of the lisainfo1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLisainfo1(String value) {
        this.lisainfo1 = value;
    }

    /**
     * Gets the value of the lisainfo2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLisainfo2() {
        return lisainfo2;
    }

    /**
     * Sets the value of the lisainfo2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLisainfo2(String value) {
        this.lisainfo2 = value;
    }

    /**
     * Gets the value of the lisainfo3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLisainfo3() {
        return lisainfo3;
    }

    /**
     * Sets the value of the lisainfo3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLisainfo3(String value) {
        this.lisainfo3 = value;
    }

}
