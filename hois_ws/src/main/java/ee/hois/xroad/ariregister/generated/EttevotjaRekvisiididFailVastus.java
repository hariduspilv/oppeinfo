
package ee.hois.xroad.ariregister.generated;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttachmentRef;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ettevotja_rekvisiidid_fail_vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevotja_rekvisiidid_fail_vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="faili_loomise_kuupaev" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="leitud_ettevotjate_arv" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="faili_nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="viide_manusele" type="{http://ws-i.org/profiles/basic/1.1/xsd}swaRef"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ettevotja_rekvisiidid_fail_vastus", propOrder = {
    "failiLoomiseKuupaev",
    "leitudEttevotjateArv",
    "failiNimi",
    "viideManusele"
})
public class EttevotjaRekvisiididFailVastus {

    @XmlElement(name = "faili_loomise_kuupaev", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar failiLoomiseKuupaev;
    @XmlElement(name = "leitud_ettevotjate_arv")
    protected int leitudEttevotjateArv;
    @XmlElement(name = "faili_nimi", required = true)
    protected String failiNimi;
    @XmlElement(name = "viide_manusele", required = true, type = String.class)
    @XmlAttachmentRef
    @XmlSchemaType(name = "anyURI")
    protected DataHandler viideManusele;

    /**
     * Gets the value of the failiLoomiseKuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFailiLoomiseKuupaev() {
        return failiLoomiseKuupaev;
    }

    /**
     * Sets the value of the failiLoomiseKuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFailiLoomiseKuupaev(XMLGregorianCalendar value) {
        this.failiLoomiseKuupaev = value;
    }

    /**
     * Gets the value of the leitudEttevotjateArv property.
     * 
     */
    public int getLeitudEttevotjateArv() {
        return leitudEttevotjateArv;
    }

    /**
     * Sets the value of the leitudEttevotjateArv property.
     * 
     */
    public void setLeitudEttevotjateArv(int value) {
        this.leitudEttevotjateArv = value;
    }

    /**
     * Gets the value of the failiNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFailiNimi() {
        return failiNimi;
    }

    /**
     * Sets the value of the failiNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFailiNimi(String value) {
        this.failiNimi = value;
    }

    /**
     * Gets the value of the viideManusele property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public DataHandler getViideManusele() {
        return viideManusele;
    }

    /**
     * Sets the value of the viideManusele property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViideManusele(DataHandler value) {
        this.viideManusele = value;
    }

}
