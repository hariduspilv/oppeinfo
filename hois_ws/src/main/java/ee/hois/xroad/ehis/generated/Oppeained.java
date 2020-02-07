
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oppeained complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oppeained"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice maxOccurs="unbounded"&gt;
 *         &lt;element name="oppeaine" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klOppeaine" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oppeained", propOrder = {
    "oppeaineOrKlOppeaine"
})
public class Oppeained {

    @XmlElementRefs({
        @XmlElementRef(name = "klOppeaine", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "oppeaine", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<String>> oppeaineOrKlOppeaine;

    /**
     * Gets the value of the oppeaineOrKlOppeaine property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oppeaineOrKlOppeaine property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOppeaineOrKlOppeaine().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * 
     */
    public List<JAXBElement<String>> getOppeaineOrKlOppeaine() {
        if (oppeaineOrKlOppeaine == null) {
            oppeaineOrKlOppeaine = new ArrayList<JAXBElement<String>>();
        }
        return this.oppeaineOrKlOppeaine;
    }

}
