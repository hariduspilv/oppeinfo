
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DigiteeriDokumendidParing_v1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DigiteeriDokumendidParing_v1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ettevotja_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="esitaja_nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="esitaja_eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="esitaja_kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="esitaja_epost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tellimise_viis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sidussysteemi_tellimuse_identifikaator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dokument_id" type="{http://www.w3.org/2001/XMLSchema}integer" maxOccurs="1000"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DigiteeriDokumendidParing_v1", propOrder = {
    "ettevotjaId",
    "esitajaNimi",
    "esitajaEesnimi",
    "esitajaKood",
    "esitajaEpost",
    "tellimiseViis",
    "sidussysteemiTellimuseIdentifikaator",
    "dokumentId"
})
public class DigiteeriDokumendidParingV1 {

    @XmlElement(name = "ettevotja_id", required = true)
    protected BigInteger ettevotjaId;
    @XmlElement(name = "esitaja_nimi", required = true)
    protected String esitajaNimi;
    @XmlElement(name = "esitaja_eesnimi", required = true)
    protected String esitajaEesnimi;
    @XmlElement(name = "esitaja_kood", required = true)
    protected String esitajaKood;
    @XmlElement(name = "esitaja_epost")
    protected String esitajaEpost;
    @XmlElement(name = "tellimise_viis")
    protected String tellimiseViis;
    @XmlElement(name = "sidussysteemi_tellimuse_identifikaator")
    protected String sidussysteemiTellimuseIdentifikaator;
    @XmlElement(name = "dokument_id", required = true)
    protected List<BigInteger> dokumentId;

    /**
     * Gets the value of the ettevotjaId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEttevotjaId() {
        return ettevotjaId;
    }

    /**
     * Sets the value of the ettevotjaId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEttevotjaId(BigInteger value) {
        this.ettevotjaId = value;
    }

    /**
     * Gets the value of the esitajaNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsitajaNimi() {
        return esitajaNimi;
    }

    /**
     * Sets the value of the esitajaNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsitajaNimi(String value) {
        this.esitajaNimi = value;
    }

    /**
     * Gets the value of the esitajaEesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsitajaEesnimi() {
        return esitajaEesnimi;
    }

    /**
     * Sets the value of the esitajaEesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsitajaEesnimi(String value) {
        this.esitajaEesnimi = value;
    }

    /**
     * Gets the value of the esitajaKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsitajaKood() {
        return esitajaKood;
    }

    /**
     * Sets the value of the esitajaKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsitajaKood(String value) {
        this.esitajaKood = value;
    }

    /**
     * Gets the value of the esitajaEpost property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsitajaEpost() {
        return esitajaEpost;
    }

    /**
     * Sets the value of the esitajaEpost property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsitajaEpost(String value) {
        this.esitajaEpost = value;
    }

    /**
     * Gets the value of the tellimiseViis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTellimiseViis() {
        return tellimiseViis;
    }

    /**
     * Sets the value of the tellimiseViis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTellimiseViis(String value) {
        this.tellimiseViis = value;
    }

    /**
     * Gets the value of the sidussysteemiTellimuseIdentifikaator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSidussysteemiTellimuseIdentifikaator() {
        return sidussysteemiTellimuseIdentifikaator;
    }

    /**
     * Sets the value of the sidussysteemiTellimuseIdentifikaator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSidussysteemiTellimuseIdentifikaator(String value) {
        this.sidussysteemiTellimuseIdentifikaator = value;
    }

    /**
     * Gets the value of the dokumentId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dokumentId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDokumentId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BigInteger }
     * 
     * 
     */
    public List<BigInteger> getDokumentId() {
        if (dokumentId == null) {
            dokumentId = new ArrayList<BigInteger>();
        }
        return this.dokumentId;
    }

}
