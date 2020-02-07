
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for khlKorgharidusLisa complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="khlKorgharidusLisa"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oppimaAsumKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="esimSemestriLoppKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="kursus" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/&gt;
 *         &lt;element name="oppekava" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/&gt;
 *         &lt;choice&gt;
 *           &lt;element name="oppetooToimumiskoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *           &lt;element name="opibValismaal" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="klOppekeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klOppevorm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klOppekoormus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klRahastAllikas" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="eelmOppeasutusLopetAasta" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisAastaType" minOccurs="0"/&gt;
 *         &lt;element name="eelmOppeasutusOppevorm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutusLopetValismaal" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutusLopetValismaalRiik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutusLopetValismaalOppeasutus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutusFil" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="riiklKoolitustellimusAasta" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisAastaType" minOccurs="0"/&gt;
 *         &lt;element name="klEelnevHaridus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klYhiselamu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="omandabHaridustKinnipidamisasutuses" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
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
@XmlType(name = "khlKorgharidusLisa", propOrder = {
    "oppimaAsumKp",
    "esimSemestriLoppKp",
    "kursus",
    "oppekava",
    "oppetooToimumiskoht",
    "opibValismaal",
    "klOppekeel",
    "klOppevorm",
    "klOppekoormus",
    "klRahastAllikas",
    "eelmOppeasutusLopetAasta",
    "eelmOppeasutusOppevorm",
    "oppeasutusLopetValismaal",
    "oppeasutusLopetValismaalRiik",
    "oppeasutusLopetValismaalOppeasutus",
    "oppeasutusFil",
    "riiklKoolitustellimusAasta",
    "klEelnevHaridus",
    "klYhiselamu",
    "omandabHaridustKinnipidamisasutuses",
    "lisainfo1",
    "lisainfo2",
    "lisainfo3"
})
public class KhlKorgharidusLisa {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar oppimaAsumKp;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar esimSemestriLoppKp;
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger kursus;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger oppekava;
    protected String oppetooToimumiskoht;
    protected String opibValismaal;
    @XmlElement(required = true)
    protected String klOppekeel;
    @XmlElement(required = true)
    protected String klOppevorm;
    @XmlElement(required = true)
    protected String klOppekoormus;
    @XmlElement(required = true)
    protected String klRahastAllikas;
    @XmlSchemaType(name = "positiveInteger")
    protected Integer eelmOppeasutusLopetAasta;
    protected String eelmOppeasutusOppevorm;
    protected String oppeasutusLopetValismaal;
    protected String oppeasutusLopetValismaalRiik;
    protected String oppeasutusLopetValismaalOppeasutus;
    protected String oppeasutusFil;
    @XmlSchemaType(name = "positiveInteger")
    protected Integer riiklKoolitustellimusAasta;
    @XmlElement(required = true)
    protected String klEelnevHaridus;
    protected String klYhiselamu;
    protected String omandabHaridustKinnipidamisasutuses;
    protected String lisainfo1;
    protected String lisainfo2;
    protected String lisainfo3;

    /**
     * Gets the value of the oppimaAsumKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOppimaAsumKp() {
        return oppimaAsumKp;
    }

    /**
     * Sets the value of the oppimaAsumKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOppimaAsumKp(XMLGregorianCalendar value) {
        this.oppimaAsumKp = value;
    }

    /**
     * Gets the value of the esimSemestriLoppKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEsimSemestriLoppKp() {
        return esimSemestriLoppKp;
    }

    /**
     * Sets the value of the esimSemestriLoppKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEsimSemestriLoppKp(XMLGregorianCalendar value) {
        this.esimSemestriLoppKp = value;
    }

    /**
     * Gets the value of the kursus property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKursus() {
        return kursus;
    }

    /**
     * Sets the value of the kursus property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKursus(BigInteger value) {
        this.kursus = value;
    }

    /**
     * Gets the value of the oppekava property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOppekava() {
        return oppekava;
    }

    /**
     * Sets the value of the oppekava property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOppekava(BigInteger value) {
        this.oppekava = value;
    }

    /**
     * Gets the value of the oppetooToimumiskoht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppetooToimumiskoht() {
        return oppetooToimumiskoht;
    }

    /**
     * Sets the value of the oppetooToimumiskoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppetooToimumiskoht(String value) {
        this.oppetooToimumiskoht = value;
    }

    /**
     * Gets the value of the opibValismaal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOpibValismaal() {
        return opibValismaal;
    }

    /**
     * Sets the value of the opibValismaal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOpibValismaal(String value) {
        this.opibValismaal = value;
    }

    /**
     * Gets the value of the klOppekeel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlOppekeel() {
        return klOppekeel;
    }

    /**
     * Sets the value of the klOppekeel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlOppekeel(String value) {
        this.klOppekeel = value;
    }

    /**
     * Gets the value of the klOppevorm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlOppevorm() {
        return klOppevorm;
    }

    /**
     * Sets the value of the klOppevorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlOppevorm(String value) {
        this.klOppevorm = value;
    }

    /**
     * Gets the value of the klOppekoormus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlOppekoormus() {
        return klOppekoormus;
    }

    /**
     * Sets the value of the klOppekoormus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlOppekoormus(String value) {
        this.klOppekoormus = value;
    }

    /**
     * Gets the value of the klRahastAllikas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlRahastAllikas() {
        return klRahastAllikas;
    }

    /**
     * Sets the value of the klRahastAllikas property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlRahastAllikas(String value) {
        this.klRahastAllikas = value;
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

    /**
     * Gets the value of the oppeasutusFil property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutusFil() {
        return oppeasutusFil;
    }

    /**
     * Sets the value of the oppeasutusFil property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutusFil(String value) {
        this.oppeasutusFil = value;
    }

    /**
     * Gets the value of the riiklKoolitustellimusAasta property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRiiklKoolitustellimusAasta() {
        return riiklKoolitustellimusAasta;
    }

    /**
     * Sets the value of the riiklKoolitustellimusAasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRiiklKoolitustellimusAasta(Integer value) {
        this.riiklKoolitustellimusAasta = value;
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
