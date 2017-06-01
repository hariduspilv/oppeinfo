
package ee.hois.xroad.sais2.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfFormFieldOption complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfFormFieldOption"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FormFieldOption" type="{http://sais2.x-road.ee/producer/}FormFieldOption" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfFormFieldOption", propOrder = {
    "formFieldOption"
})
public class ArrayOfFormFieldOption {

    @XmlElement(name = "FormFieldOption")
    protected List<FormFieldOption> formFieldOption;

    /**
     * Gets the value of the formFieldOption property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the formFieldOption property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFormFieldOption().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FormFieldOption }
     * 
     * 
     */
    public List<FormFieldOption> getFormFieldOption() {
        if (formFieldOption == null) {
            formFieldOption = new ArrayList<FormFieldOption>();
        }
        return this.formFieldOption;
    }

}
