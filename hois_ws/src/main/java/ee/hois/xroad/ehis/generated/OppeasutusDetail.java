
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oppeasutusDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oppeasutusDetail"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="yldandmed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}mtsysOppeasutusAndmed" minOccurs="0"/&gt;
 *         &lt;element name="aadress" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}aadress" minOccurs="0"/&gt;
 *         &lt;element name="kontaktandmed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}mtsysOppeasutusKontaktandmed" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oppeasutusDetail", propOrder = {
    "yldandmed",
    "aadress",
    "kontaktandmed"
})
public class OppeasutusDetail {

    protected MtsysOppeasutusAndmed yldandmed;
    protected Aadress aadress;
    @XmlElementRef(name = "kontaktandmed", type = JAXBElement.class, required = false)
    protected JAXBElement<MtsysOppeasutusKontaktandmed> kontaktandmed;

    /**
     * Gets the value of the yldandmed property.
     * 
     * @return
     *     possible object is
     *     {@link MtsysOppeasutusAndmed }
     *     
     */
    public MtsysOppeasutusAndmed getYldandmed() {
        return yldandmed;
    }

    /**
     * Sets the value of the yldandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link MtsysOppeasutusAndmed }
     *     
     */
    public void setYldandmed(MtsysOppeasutusAndmed value) {
        this.yldandmed = value;
    }

    /**
     * Gets the value of the aadress property.
     * 
     * @return
     *     possible object is
     *     {@link Aadress }
     *     
     */
    public Aadress getAadress() {
        return aadress;
    }

    /**
     * Sets the value of the aadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Aadress }
     *     
     */
    public void setAadress(Aadress value) {
        this.aadress = value;
    }

    /**
     * Gets the value of the kontaktandmed property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link MtsysOppeasutusKontaktandmed }{@code >}
     *     
     */
    public JAXBElement<MtsysOppeasutusKontaktandmed> getKontaktandmed() {
        return kontaktandmed;
    }

    /**
     * Sets the value of the kontaktandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link MtsysOppeasutusKontaktandmed }{@code >}
     *     
     */
    public void setKontaktandmed(JAXBElement<MtsysOppeasutusKontaktandmed> value) {
        this.kontaktandmed = value;
    }

}
