
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for esrOppeasutus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="esrOppeasutus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="asutusId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="regnr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="omandivormKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pidajaNimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pidajaLiikKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pidajaRegnr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="asutamiseAasta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="registrKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="sulgemiseKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="andmeteKinnitamiseKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="maakondEhak" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="omavalitsusEhak" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="asustusyksusEhak" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="asulatyypEhak" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="postiindeks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="telefon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="epost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="veebileht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppekava" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}esrOppekava" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "esrOppeasutus", propOrder = {
    "asutusId",
    "regnr",
    "nimetus",
    "omandivormKood",
    "pidajaNimi",
    "pidajaLiikKood",
    "pidajaRegnr",
    "asutamiseAasta",
    "registrKp",
    "sulgemiseKp",
    "andmeteKinnitamiseKp",
    "maakondEhak",
    "omavalitsusEhak",
    "asustusyksusEhak",
    "asulatyypEhak",
    "aadress",
    "postiindeks",
    "telefon",
    "epost",
    "veebileht",
    "oppekava"
})
public class EsrOppeasutus {

    @XmlElement(required = true)
    protected BigInteger asutusId;
    protected String regnr;
    @XmlElement(required = true)
    protected String nimetus;
    protected String omandivormKood;
    protected String pidajaNimi;
    protected String pidajaLiikKood;
    protected String pidajaRegnr;
    protected String asutamiseAasta;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar registrKp;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar sulgemiseKp;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar andmeteKinnitamiseKp;
    protected String maakondEhak;
    protected String omavalitsusEhak;
    protected String asustusyksusEhak;
    protected String asulatyypEhak;
    protected String aadress;
    protected String postiindeks;
    protected String telefon;
    protected String epost;
    protected String veebileht;
    @XmlElement(required = true)
    protected List<EsrOppekava> oppekava;

    /**
     * Gets the value of the asutusId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAsutusId() {
        return asutusId;
    }

    /**
     * Sets the value of the asutusId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAsutusId(BigInteger value) {
        this.asutusId = value;
    }

    /**
     * Gets the value of the regnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegnr() {
        return regnr;
    }

    /**
     * Sets the value of the regnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegnr(String value) {
        this.regnr = value;
    }

    /**
     * Gets the value of the nimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNimetus() {
        return nimetus;
    }

    /**
     * Sets the value of the nimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNimetus(String value) {
        this.nimetus = value;
    }

    /**
     * Gets the value of the omandivormKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOmandivormKood() {
        return omandivormKood;
    }

    /**
     * Sets the value of the omandivormKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOmandivormKood(String value) {
        this.omandivormKood = value;
    }

    /**
     * Gets the value of the pidajaNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPidajaNimi() {
        return pidajaNimi;
    }

    /**
     * Sets the value of the pidajaNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPidajaNimi(String value) {
        this.pidajaNimi = value;
    }

    /**
     * Gets the value of the pidajaLiikKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPidajaLiikKood() {
        return pidajaLiikKood;
    }

    /**
     * Sets the value of the pidajaLiikKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPidajaLiikKood(String value) {
        this.pidajaLiikKood = value;
    }

    /**
     * Gets the value of the pidajaRegnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPidajaRegnr() {
        return pidajaRegnr;
    }

    /**
     * Sets the value of the pidajaRegnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPidajaRegnr(String value) {
        this.pidajaRegnr = value;
    }

    /**
     * Gets the value of the asutamiseAasta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsutamiseAasta() {
        return asutamiseAasta;
    }

    /**
     * Sets the value of the asutamiseAasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsutamiseAasta(String value) {
        this.asutamiseAasta = value;
    }

    /**
     * Gets the value of the registrKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRegistrKp() {
        return registrKp;
    }

    /**
     * Sets the value of the registrKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRegistrKp(XMLGregorianCalendar value) {
        this.registrKp = value;
    }

    /**
     * Gets the value of the sulgemiseKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSulgemiseKp() {
        return sulgemiseKp;
    }

    /**
     * Sets the value of the sulgemiseKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSulgemiseKp(XMLGregorianCalendar value) {
        this.sulgemiseKp = value;
    }

    /**
     * Gets the value of the andmeteKinnitamiseKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAndmeteKinnitamiseKp() {
        return andmeteKinnitamiseKp;
    }

    /**
     * Sets the value of the andmeteKinnitamiseKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAndmeteKinnitamiseKp(XMLGregorianCalendar value) {
        this.andmeteKinnitamiseKp = value;
    }

    /**
     * Gets the value of the maakondEhak property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaakondEhak() {
        return maakondEhak;
    }

    /**
     * Sets the value of the maakondEhak property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaakondEhak(String value) {
        this.maakondEhak = value;
    }

    /**
     * Gets the value of the omavalitsusEhak property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOmavalitsusEhak() {
        return omavalitsusEhak;
    }

    /**
     * Sets the value of the omavalitsusEhak property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOmavalitsusEhak(String value) {
        this.omavalitsusEhak = value;
    }

    /**
     * Gets the value of the asustusyksusEhak property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsustusyksusEhak() {
        return asustusyksusEhak;
    }

    /**
     * Sets the value of the asustusyksusEhak property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsustusyksusEhak(String value) {
        this.asustusyksusEhak = value;
    }

    /**
     * Gets the value of the asulatyypEhak property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsulatyypEhak() {
        return asulatyypEhak;
    }

    /**
     * Sets the value of the asulatyypEhak property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsulatyypEhak(String value) {
        this.asulatyypEhak = value;
    }

    /**
     * Gets the value of the aadress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadress() {
        return aadress;
    }

    /**
     * Sets the value of the aadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadress(String value) {
        this.aadress = value;
    }

    /**
     * Gets the value of the postiindeks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostiindeks() {
        return postiindeks;
    }

    /**
     * Sets the value of the postiindeks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostiindeks(String value) {
        this.postiindeks = value;
    }

    /**
     * Gets the value of the telefon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefon() {
        return telefon;
    }

    /**
     * Sets the value of the telefon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefon(String value) {
        this.telefon = value;
    }

    /**
     * Gets the value of the epost property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEpost() {
        return epost;
    }

    /**
     * Sets the value of the epost property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEpost(String value) {
        this.epost = value;
    }

    /**
     * Gets the value of the veebileht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVeebileht() {
        return veebileht;
    }

    /**
     * Sets the value of the veebileht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVeebileht(String value) {
        this.veebileht = value;
    }

    /**
     * Gets the value of the oppekava property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oppekava property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOppekava().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EsrOppekava }
     * 
     * 
     */
    public List<EsrOppekava> getOppekava() {
        if (oppekava == null) {
            oppekava = new ArrayList<EsrOppekava>();
        }
        return this.oppekava;
    }

}
