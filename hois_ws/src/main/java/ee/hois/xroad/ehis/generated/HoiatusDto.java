
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HoiatusDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HoiatusDto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="errorMessages" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}Message" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="warningMessages" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}Message" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="successMessages" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}Message" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HoiatusDto", propOrder = {
    "errorMessages",
    "warningMessages",
    "successMessages"
})
public class HoiatusDto {

    @XmlElement(nillable = true)
    protected List<Message> errorMessages;
    @XmlElement(nillable = true)
    protected List<Message> warningMessages;
    @XmlElement(nillable = true)
    protected List<Message> successMessages;

    /**
     * Gets the value of the errorMessages property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errorMessages property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrorMessages().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Message }
     * 
     * 
     */
    public List<Message> getErrorMessages() {
        if (errorMessages == null) {
            errorMessages = new ArrayList<Message>();
        }
        return this.errorMessages;
    }

    /**
     * Gets the value of the warningMessages property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the warningMessages property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWarningMessages().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Message }
     * 
     * 
     */
    public List<Message> getWarningMessages() {
        if (warningMessages == null) {
            warningMessages = new ArrayList<Message>();
        }
        return this.warningMessages;
    }

    /**
     * Gets the value of the successMessages property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the successMessages property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSuccessMessages().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Message }
     * 
     * 
     */
    public List<Message> getSuccessMessages() {
        if (successMessages == null) {
            successMessages = new ArrayList<Message>();
        }
        return this.successMessages;
    }

}
