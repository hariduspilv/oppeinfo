
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="aruandeId" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutusId" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="aasta" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="klStaatus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisKlassifikaator" minOccurs="0"/&gt;
 *         &lt;element name="esitamiseKp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kommentaar" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="opperyhmad" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}opperyhmad" minOccurs="0"/&gt;
 *         &lt;element name="naitajad" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}naitajad" minOccurs="0"/&gt;
 *         &lt;element name="infotekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="saabMuuta" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="aadressid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}aadressid" minOccurs="0"/&gt;
 *         &lt;element name="tegevusnaitajaInfotekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="csvFail" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}contentBase64" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "aruandeId",
    "oppeasutusId",
    "aasta",
    "klStaatus",
    "esitamiseKp",
    "kommentaar",
    "opperyhmad",
    "naitajad",
    "infotekst",
    "saabMuuta",
    "aadressid",
    "tegevusnaitajaInfotekst",
    "csvFail"
})
@XmlRootElement(name = "mtsysTegevusnaitajaResponse")
public class MtsysTegevusnaitajaResponse {

    protected BigInteger aruandeId;
    protected BigInteger oppeasutusId;
    protected BigInteger aasta;
    protected EhisKlassifikaator klStaatus;
    protected String esitamiseKp;
    protected String kommentaar;
    protected Opperyhmad opperyhmad;
    protected Naitajad naitajad;
    protected String infotekst;
    @XmlElement(defaultValue = "false")
    protected Boolean saabMuuta;
    protected Aadressid aadressid;
    protected String tegevusnaitajaInfotekst;
    protected ContentBase64 csvFail;

    /**
     * Gets the value of the aruandeId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAruandeId() {
        return aruandeId;
    }

    /**
     * Sets the value of the aruandeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAruandeId(BigInteger value) {
        this.aruandeId = value;
    }

    /**
     * Gets the value of the oppeasutusId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOppeasutusId() {
        return oppeasutusId;
    }

    /**
     * Sets the value of the oppeasutusId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOppeasutusId(BigInteger value) {
        this.oppeasutusId = value;
    }

    /**
     * Gets the value of the aasta property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAasta() {
        return aasta;
    }

    /**
     * Sets the value of the aasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAasta(BigInteger value) {
        this.aasta = value;
    }

    /**
     * Gets the value of the klStaatus property.
     * 
     * @return
     *     possible object is
     *     {@link EhisKlassifikaator }
     *     
     */
    public EhisKlassifikaator getKlStaatus() {
        return klStaatus;
    }

    /**
     * Sets the value of the klStaatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link EhisKlassifikaator }
     *     
     */
    public void setKlStaatus(EhisKlassifikaator value) {
        this.klStaatus = value;
    }

    /**
     * Gets the value of the esitamiseKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsitamiseKp() {
        return esitamiseKp;
    }

    /**
     * Sets the value of the esitamiseKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsitamiseKp(String value) {
        this.esitamiseKp = value;
    }

    /**
     * Gets the value of the kommentaar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKommentaar() {
        return kommentaar;
    }

    /**
     * Sets the value of the kommentaar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKommentaar(String value) {
        this.kommentaar = value;
    }

    /**
     * Gets the value of the opperyhmad property.
     * 
     * @return
     *     possible object is
     *     {@link Opperyhmad }
     *     
     */
    public Opperyhmad getOpperyhmad() {
        return opperyhmad;
    }

    /**
     * Sets the value of the opperyhmad property.
     * 
     * @param value
     *     allowed object is
     *     {@link Opperyhmad }
     *     
     */
    public void setOpperyhmad(Opperyhmad value) {
        this.opperyhmad = value;
    }

    /**
     * Gets the value of the naitajad property.
     * 
     * @return
     *     possible object is
     *     {@link Naitajad }
     *     
     */
    public Naitajad getNaitajad() {
        return naitajad;
    }

    /**
     * Sets the value of the naitajad property.
     * 
     * @param value
     *     allowed object is
     *     {@link Naitajad }
     *     
     */
    public void setNaitajad(Naitajad value) {
        this.naitajad = value;
    }

    /**
     * Gets the value of the infotekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfotekst() {
        return infotekst;
    }

    /**
     * Sets the value of the infotekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfotekst(String value) {
        this.infotekst = value;
    }

    /**
     * Gets the value of the saabMuuta property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSaabMuuta() {
        return saabMuuta;
    }

    /**
     * Sets the value of the saabMuuta property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSaabMuuta(Boolean value) {
        this.saabMuuta = value;
    }

    /**
     * Gets the value of the aadressid property.
     * 
     * @return
     *     possible object is
     *     {@link Aadressid }
     *     
     */
    public Aadressid getAadressid() {
        return aadressid;
    }

    /**
     * Sets the value of the aadressid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Aadressid }
     *     
     */
    public void setAadressid(Aadressid value) {
        this.aadressid = value;
    }

    /**
     * Gets the value of the tegevusnaitajaInfotekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTegevusnaitajaInfotekst() {
        return tegevusnaitajaInfotekst;
    }

    /**
     * Sets the value of the tegevusnaitajaInfotekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTegevusnaitajaInfotekst(String value) {
        this.tegevusnaitajaInfotekst = value;
    }

    /**
     * Gets the value of the csvFail property.
     * 
     * @return
     *     possible object is
     *     {@link ContentBase64 }
     *     
     */
    public ContentBase64 getCsvFail() {
        return csvFail;
    }

    /**
     * Sets the value of the csvFail property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContentBase64 }
     *     
     */
    public void setCsvFail(ContentBase64 value) {
        this.csvFail = value;
    }

}
