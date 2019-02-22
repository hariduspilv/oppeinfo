
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * Asutustele kontoliikumistest teavitamise formaat
 * 
 * <p>Java class for MokaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MokaType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="Treasury" type="{http://arireg.x-road.eu/producer/}Treasury"/&gt;
 *         &lt;element name="Payer" type="{http://arireg.x-road.eu/producer/}Payer"/&gt;
 *         &lt;element name="Receiver" type="{http://arireg.x-road.eu/producer/}Receiver"/&gt;
 *         &lt;element name="Payment" type="{http://arireg.x-road.eu/producer/}Payment"/&gt;
 *         &lt;element name="TreasuryClassif" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PaymentDesc" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="0"/&gt;
 *               &lt;maxLength value="255"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/all&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MokaType", propOrder = {

})
public class MokaType {

    @XmlElement(name = "Treasury", required = true)
    protected Treasury treasury;
    @XmlElement(name = "Payer", required = true)
    protected Payer payer;
    @XmlElement(name = "Receiver", required = true)
    protected Receiver receiver;
    @XmlElement(name = "Payment", required = true)
    protected Payment payment;
    @XmlElementRef(name = "TreasuryClassif", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> treasuryClassif;
    @XmlElement(name = "PaymentDesc")
    protected String paymentDesc;

    /**
     * Gets the value of the treasury property.
     * 
     * @return
     *     possible object is
     *     {@link Treasury }
     *     
     */
    public Treasury getTreasury() {
        return treasury;
    }

    /**
     * Sets the value of the treasury property.
     * 
     * @param value
     *     allowed object is
     *     {@link Treasury }
     *     
     */
    public void setTreasury(Treasury value) {
        this.treasury = value;
    }

    /**
     * Gets the value of the payer property.
     * 
     * @return
     *     possible object is
     *     {@link Payer }
     *     
     */
    public Payer getPayer() {
        return payer;
    }

    /**
     * Sets the value of the payer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Payer }
     *     
     */
    public void setPayer(Payer value) {
        this.payer = value;
    }

    /**
     * Gets the value of the receiver property.
     * 
     * @return
     *     possible object is
     *     {@link Receiver }
     *     
     */
    public Receiver getReceiver() {
        return receiver;
    }

    /**
     * Sets the value of the receiver property.
     * 
     * @param value
     *     allowed object is
     *     {@link Receiver }
     *     
     */
    public void setReceiver(Receiver value) {
        this.receiver = value;
    }

    /**
     * Gets the value of the payment property.
     * 
     * @return
     *     possible object is
     *     {@link Payment }
     *     
     */
    public Payment getPayment() {
        return payment;
    }

    /**
     * Sets the value of the payment property.
     * 
     * @param value
     *     allowed object is
     *     {@link Payment }
     *     
     */
    public void setPayment(Payment value) {
        this.payment = value;
    }

    /**
     * Gets the value of the treasuryClassif property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTreasuryClassif() {
        return treasuryClassif;
    }

    /**
     * Sets the value of the treasuryClassif property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTreasuryClassif(JAXBElement<String> value) {
        this.treasuryClassif = value;
    }

    /**
     * Gets the value of the paymentDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentDesc() {
        return paymentDesc;
    }

    /**
     * Sets the value of the paymentDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentDesc(String value) {
        this.paymentDesc = value;
    }

}
