
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR459isikuDokumentRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR459isikuDokumentRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DokumendiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DokumendiOlek" type="{http://rr.x-road.eu/producer}dokumendi_olekxtee"/&gt;
 *         &lt;element name="DokumendiSeeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DokumendiNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Dokumentvaljaantud" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DokumentKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Perekonnanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DokumendiOtsing" type="{http://rr.x-road.eu/producer}dokumendi_otsingxtee"/&gt;
 *         &lt;element name="Pohjus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR459isikuDokumentRequestType", propOrder = {
    "dokumendiLiik",
    "dokumendiOlek",
    "dokumendiSeeria",
    "dokumendiNumber",
    "dokumentvaljaantud",
    "dokumentKuni",
    "isikukood",
    "perekonnanimi",
    "eesnimi",
    "dokumendiOtsing",
    "pohjus"
})
public class RR459IsikuDokumentRequestType {

    @XmlElement(name = "DokumendiLiik", required = true)
    protected String dokumendiLiik;
    @XmlElement(name = "DokumendiOlek", required = true)
    @XmlSchemaType(name = "string")
    protected DokumendiOlekxtee dokumendiOlek;
    @XmlElement(name = "DokumendiSeeria", required = true)
    protected String dokumendiSeeria;
    @XmlElement(name = "DokumendiNumber", required = true)
    protected String dokumendiNumber;
    @XmlElement(name = "Dokumentvaljaantud", required = true)
    protected String dokumentvaljaantud;
    @XmlElement(name = "DokumentKuni", required = true)
    protected String dokumentKuni;
    @XmlElement(name = "Isikukood", required = true)
    protected String isikukood;
    @XmlElement(name = "Perekonnanimi", required = true)
    protected String perekonnanimi;
    @XmlElement(name = "Eesnimi", required = true)
    protected String eesnimi;
    @XmlElement(name = "DokumendiOtsing", required = true)
    protected String dokumendiOtsing;
    @XmlElement(name = "Pohjus")
    protected String pohjus;

    /**
     * Gets the value of the dokumendiLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiLiik() {
        return dokumendiLiik;
    }

    /**
     * Sets the value of the dokumendiLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiLiik(String value) {
        this.dokumendiLiik = value;
    }

    /**
     * Gets the value of the dokumendiOlek property.
     * 
     * @return
     *     possible object is
     *     {@link DokumendiOlekxtee }
     *     
     */
    public DokumendiOlekxtee getDokumendiOlek() {
        return dokumendiOlek;
    }

    /**
     * Sets the value of the dokumendiOlek property.
     * 
     * @param value
     *     allowed object is
     *     {@link DokumendiOlekxtee }
     *     
     */
    public void setDokumendiOlek(DokumendiOlekxtee value) {
        this.dokumendiOlek = value;
    }

    /**
     * Gets the value of the dokumendiSeeria property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiSeeria() {
        return dokumendiSeeria;
    }

    /**
     * Sets the value of the dokumendiSeeria property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiSeeria(String value) {
        this.dokumendiSeeria = value;
    }

    /**
     * Gets the value of the dokumendiNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiNumber() {
        return dokumendiNumber;
    }

    /**
     * Sets the value of the dokumendiNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiNumber(String value) {
        this.dokumendiNumber = value;
    }

    /**
     * Gets the value of the dokumentvaljaantud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumentvaljaantud() {
        return dokumentvaljaantud;
    }

    /**
     * Sets the value of the dokumentvaljaantud property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumentvaljaantud(String value) {
        this.dokumentvaljaantud = value;
    }

    /**
     * Gets the value of the dokumentKuni property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumentKuni() {
        return dokumentKuni;
    }

    /**
     * Sets the value of the dokumentKuni property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumentKuni(String value) {
        this.dokumentKuni = value;
    }

    /**
     * Gets the value of the isikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukood() {
        return isikukood;
    }

    /**
     * Sets the value of the isikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukood(String value) {
        this.isikukood = value;
    }

    /**
     * Gets the value of the perekonnanimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerekonnanimi() {
        return perekonnanimi;
    }

    /**
     * Sets the value of the perekonnanimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerekonnanimi(String value) {
        this.perekonnanimi = value;
    }

    /**
     * Gets the value of the eesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEesnimi() {
        return eesnimi;
    }

    /**
     * Sets the value of the eesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEesnimi(String value) {
        this.eesnimi = value;
    }

    /**
     * Gets the value of the dokumendiOtsing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiOtsing() {
        return dokumendiOtsing;
    }

    /**
     * Sets the value of the dokumendiOtsing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiOtsing(String value) {
        this.dokumendiOtsing = value;
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

}
