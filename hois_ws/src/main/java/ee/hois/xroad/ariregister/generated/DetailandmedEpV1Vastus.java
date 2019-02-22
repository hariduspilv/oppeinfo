
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.activation.DataHandler;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for detailandmed_ep_v1_Vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="detailandmed_ep_v1_Vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="viide_manusele" type="{http://ws-i.org/profiles/basic/1.1/xsd}swaRef" minOccurs="0"/&gt;
 *         &lt;element name="kirjete_arv" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="tellimuse_id" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detailandmed_ep_v1_Vastus", propOrder = {
    "viideManusele",
    "kirjeteArv",
    "tellimuseId"
})
public class DetailandmedEpV1Vastus {

    @XmlElementRef(name = "viide_manusele", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<DataHandler> viideManusele;
    @XmlElementRef(name = "kirjete_arv", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<BigInteger> kirjeteArv;
    @XmlElementRef(name = "tellimuse_id", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<BigInteger> tellimuseId;

    /**
     * Gets the value of the viideManusele property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link DataHandler }{@code >}
     *     
     */
    public JAXBElement<DataHandler> getViideManusele() {
        return viideManusele;
    }

    /**
     * Sets the value of the viideManusele property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link DataHandler }{@code >}
     *     
     */
    public void setViideManusele(JAXBElement<DataHandler> value) {
        this.viideManusele = value;
    }

    /**
     * Gets the value of the kirjeteArv property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public JAXBElement<BigInteger> getKirjeteArv() {
        return kirjeteArv;
    }

    /**
     * Sets the value of the kirjeteArv property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public void setKirjeteArv(JAXBElement<BigInteger> value) {
        this.kirjeteArv = value;
    }

    /**
     * Gets the value of the tellimuseId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public JAXBElement<BigInteger> getTellimuseId() {
        return tellimuseId;
    }

    /**
     * Sets the value of the tellimuseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public void setTellimuseId(JAXBElement<BigInteger> value) {
        this.tellimuseId = value;
    }

}
