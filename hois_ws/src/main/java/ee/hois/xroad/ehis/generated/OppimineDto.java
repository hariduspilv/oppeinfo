
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for oppimineDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oppimineDto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="asutuseId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="asutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeTyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeTyypKL" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}intOrNothing" minOccurs="0"/&gt;
 *         &lt;element name="oppekavaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekavaKood" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="alustamiseKuupaev" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="oppekoormusTyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekoormusTyypKL" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}intOrNothing" minOccurs="0"/&gt;
 *         &lt;element name="taitmiseProtsent" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *         &lt;element name="akadeemilisePuhkuseAlustamiseKuupaev" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}dateOrNothing" minOccurs="0"/&gt;
 *         &lt;element name="esimeseSemestriLoppKp" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}dateOrNothing" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oppimineDto", propOrder = {
    "asutuseId",
    "asutuseNimi",
    "oppeTyyp",
    "oppeTyypKL",
    "oppekavaNimi",
    "oppekavaKood",
    "alustamiseKuupaev",
    "oppekoormusTyyp",
    "oppekoormusTyypKL",
    "taitmiseProtsent",
    "akadeemilisePuhkuseAlustamiseKuupaev",
    "esimeseSemestriLoppKp"
})
public class OppimineDto {

    protected long asutuseId;
    @XmlElement(required = true)
    protected String asutuseNimi;
    @XmlElement(required = true)
    protected String oppeTyyp;
    @XmlElementRef(name = "oppeTyypKL", type = JAXBElement.class, required = false)
    protected JAXBElement<String> oppeTyypKL;
    @XmlElement(required = true)
    protected String oppekavaNimi;
    protected int oppekavaKood;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar alustamiseKuupaev;
    @XmlElement(required = true)
    protected String oppekoormusTyyp;
    @XmlElementRef(name = "oppekoormusTyypKL", type = JAXBElement.class, required = false)
    protected JAXBElement<String> oppekoormusTyypKL;
    protected float taitmiseProtsent;
    @XmlElementRef(name = "akadeemilisePuhkuseAlustamiseKuupaev", type = JAXBElement.class, required = false)
    protected JAXBElement<String> akadeemilisePuhkuseAlustamiseKuupaev;
    @XmlElementRef(name = "esimeseSemestriLoppKp", type = JAXBElement.class, required = false)
    protected JAXBElement<String> esimeseSemestriLoppKp;

    /**
     * Gets the value of the asutuseId property.
     * 
     */
    public long getAsutuseId() {
        return asutuseId;
    }

    /**
     * Sets the value of the asutuseId property.
     * 
     */
    public void setAsutuseId(long value) {
        this.asutuseId = value;
    }

    /**
     * Gets the value of the asutuseNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsutuseNimi() {
        return asutuseNimi;
    }

    /**
     * Sets the value of the asutuseNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsutuseNimi(String value) {
        this.asutuseNimi = value;
    }

    /**
     * Gets the value of the oppeTyyp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeTyyp() {
        return oppeTyyp;
    }

    /**
     * Sets the value of the oppeTyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeTyyp(String value) {
        this.oppeTyyp = value;
    }

    /**
     * Gets the value of the oppeTyypKL property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOppeTyypKL() {
        return oppeTyypKL;
    }

    /**
     * Sets the value of the oppeTyypKL property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOppeTyypKL(JAXBElement<String> value) {
        this.oppeTyypKL = value;
    }

    /**
     * Gets the value of the oppekavaNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavaNimi() {
        return oppekavaNimi;
    }

    /**
     * Sets the value of the oppekavaNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavaNimi(String value) {
        this.oppekavaNimi = value;
    }

    /**
     * Gets the value of the oppekavaKood property.
     * 
     */
    public int getOppekavaKood() {
        return oppekavaKood;
    }

    /**
     * Sets the value of the oppekavaKood property.
     * 
     */
    public void setOppekavaKood(int value) {
        this.oppekavaKood = value;
    }

    /**
     * Gets the value of the alustamiseKuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAlustamiseKuupaev() {
        return alustamiseKuupaev;
    }

    /**
     * Sets the value of the alustamiseKuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAlustamiseKuupaev(XMLGregorianCalendar value) {
        this.alustamiseKuupaev = value;
    }

    /**
     * Gets the value of the oppekoormusTyyp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekoormusTyyp() {
        return oppekoormusTyyp;
    }

    /**
     * Sets the value of the oppekoormusTyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekoormusTyyp(String value) {
        this.oppekoormusTyyp = value;
    }

    /**
     * Gets the value of the oppekoormusTyypKL property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOppekoormusTyypKL() {
        return oppekoormusTyypKL;
    }

    /**
     * Sets the value of the oppekoormusTyypKL property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOppekoormusTyypKL(JAXBElement<String> value) {
        this.oppekoormusTyypKL = value;
    }

    /**
     * Gets the value of the taitmiseProtsent property.
     * 
     */
    public float getTaitmiseProtsent() {
        return taitmiseProtsent;
    }

    /**
     * Sets the value of the taitmiseProtsent property.
     * 
     */
    public void setTaitmiseProtsent(float value) {
        this.taitmiseProtsent = value;
    }

    /**
     * Gets the value of the akadeemilisePuhkuseAlustamiseKuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAkadeemilisePuhkuseAlustamiseKuupaev() {
        return akadeemilisePuhkuseAlustamiseKuupaev;
    }

    /**
     * Sets the value of the akadeemilisePuhkuseAlustamiseKuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAkadeemilisePuhkuseAlustamiseKuupaev(JAXBElement<String> value) {
        this.akadeemilisePuhkuseAlustamiseKuupaev = value;
    }

    /**
     * Gets the value of the esimeseSemestriLoppKp property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getEsimeseSemestriLoppKp() {
        return esimeseSemestriLoppKp;
    }

    /**
     * Sets the value of the esimeseSemestriLoppKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setEsimeseSemestriLoppKp(JAXBElement<String> value) {
        this.esimeseSemestriLoppKp = value;
    }

}
