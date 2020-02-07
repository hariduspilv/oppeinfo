
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRPORTAADRESSRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRPORTAADRESSRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="maakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="vald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="asula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="vaikekoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="majaNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="korteriNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="pohjus" type="{http://rr.x-road.eu/producer}pohjuse_valik1"/&gt;
 *         &lt;element name="selgitus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tulemusi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRPORTAADRESSRequestType", propOrder = {
    "maakond",
    "vald",
    "asula",
    "vaikekoht",
    "tanav",
    "nimi",
    "majaNumber",
    "korteriNumber",
    "pohjus",
    "selgitus",
    "tulemusi"
})
public class RRPORTAADRESSRequestType {

    @XmlElement(required = true)
    protected String maakond;
    @XmlElement(required = true)
    protected String vald;
    @XmlElement(required = true)
    protected String asula;
    @XmlElement(required = true)
    protected String vaikekoht;
    @XmlElement(required = true)
    protected String tanav;
    @XmlElement(required = true)
    protected String nimi;
    @XmlElement(required = true)
    protected String majaNumber;
    @XmlElement(required = true)
    protected String korteriNumber;
    @XmlElement(required = true)
    protected String pohjus;
    @XmlElement(required = true)
    protected String selgitus;
    @XmlElement(required = true)
    protected String tulemusi;

    /**
     * Gets the value of the maakond property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaakond() {
        return maakond;
    }

    /**
     * Sets the value of the maakond property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaakond(String value) {
        this.maakond = value;
    }

    /**
     * Gets the value of the vald property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVald() {
        return vald;
    }

    /**
     * Sets the value of the vald property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVald(String value) {
        this.vald = value;
    }

    /**
     * Gets the value of the asula property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsula() {
        return asula;
    }

    /**
     * Sets the value of the asula property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsula(String value) {
        this.asula = value;
    }

    /**
     * Gets the value of the vaikekoht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVaikekoht() {
        return vaikekoht;
    }

    /**
     * Sets the value of the vaikekoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVaikekoht(String value) {
        this.vaikekoht = value;
    }

    /**
     * Gets the value of the tanav property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTanav() {
        return tanav;
    }

    /**
     * Sets the value of the tanav property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTanav(String value) {
        this.tanav = value;
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
     * Gets the value of the majaNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMajaNumber() {
        return majaNumber;
    }

    /**
     * Sets the value of the majaNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMajaNumber(String value) {
        this.majaNumber = value;
    }

    /**
     * Gets the value of the korteriNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKorteriNumber() {
        return korteriNumber;
    }

    /**
     * Sets the value of the korteriNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKorteriNumber(String value) {
        this.korteriNumber = value;
    }

    /**
     * Gets the value of the pohjus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPohjus() {
        return pohjus;
    }

    /**
     * Sets the value of the pohjus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPohjus(String value) {
        this.pohjus = value;
    }

    /**
     * Gets the value of the selgitus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelgitus() {
        return selgitus;
    }

    /**
     * Sets the value of the selgitus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelgitus(String value) {
        this.selgitus = value;
    }

    /**
     * Gets the value of the tulemusi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTulemusi() {
        return tulemusi;
    }

    /**
     * Sets the value of the tulemusi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTulemusi(String value) {
        this.tulemusi = value;
    }

}
