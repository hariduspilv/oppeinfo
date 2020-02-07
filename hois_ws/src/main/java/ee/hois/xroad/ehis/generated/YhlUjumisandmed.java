
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for yhlUjumisandmed complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="yhlUjumisandmed"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="muutusKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;choice&gt;
 *           &lt;element name="kustutaKoik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *           &lt;element name="ujumine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlUjumine" minOccurs="0"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "yhlUjumisandmed", propOrder = {
    "muutusKp",
    "kustutaKoik",
    "ujumine"
})
public class YhlUjumisandmed {

    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar muutusKp;
    protected String kustutaKoik;
    protected YhlUjumine ujumine;

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
     * Gets the value of the kustutaKoik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKustutaKoik() {
        return kustutaKoik;
    }

    /**
     * Sets the value of the kustutaKoik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKustutaKoik(String value) {
        this.kustutaKoik = value;
    }

    /**
     * Gets the value of the ujumine property.
     * 
     * @return
     *     possible object is
     *     {@link YhlUjumine }
     *     
     */
    public YhlUjumine getUjumine() {
        return ujumine;
    }

    /**
     * Sets the value of the ujumine property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlUjumine }
     *     
     */
    public void setUjumine(YhlUjumine value) {
        this.ujumine = value;
    }

}
