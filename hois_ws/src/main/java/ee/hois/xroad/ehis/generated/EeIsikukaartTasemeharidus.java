
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for eeIsikukaartTasemeharidus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eeIsikukaartTasemeharidus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kvalDokument" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kvalVastavus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="erialaOppekava" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lopetanud" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="dokument" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eeIsikukaartTasemeharidus", propOrder = {
    "kvalDokument",
    "kvalVastavus",
    "oppeasutus",
    "erialaOppekava",
    "lopetanud",
    "dokument"
})
public class EeIsikukaartTasemeharidus {

    protected String kvalDokument;
    protected String kvalVastavus;
    protected String oppeasutus;
    protected String erialaOppekava;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lopetanud;
    protected String dokument;

    /**
     * Gets the value of the kvalDokument property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKvalDokument() {
        return kvalDokument;
    }

    /**
     * Sets the value of the kvalDokument property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKvalDokument(String value) {
        this.kvalDokument = value;
    }

    /**
     * Gets the value of the kvalVastavus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKvalVastavus() {
        return kvalVastavus;
    }

    /**
     * Sets the value of the kvalVastavus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKvalVastavus(String value) {
        this.kvalVastavus = value;
    }

    /**
     * Gets the value of the oppeasutus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutus() {
        return oppeasutus;
    }

    /**
     * Sets the value of the oppeasutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutus(String value) {
        this.oppeasutus = value;
    }

    /**
     * Gets the value of the erialaOppekava property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErialaOppekava() {
        return erialaOppekava;
    }

    /**
     * Sets the value of the erialaOppekava property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErialaOppekava(String value) {
        this.erialaOppekava = value;
    }

    /**
     * Gets the value of the lopetanud property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLopetanud() {
        return lopetanud;
    }

    /**
     * Sets the value of the lopetanud property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLopetanud(XMLGregorianCalendar value) {
        this.lopetanud = value;
    }

    /**
     * Gets the value of the dokument property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokument() {
        return dokument;
    }

    /**
     * Sets the value of the dokument property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokument(String value) {
        this.dokument = value;
    }

}
