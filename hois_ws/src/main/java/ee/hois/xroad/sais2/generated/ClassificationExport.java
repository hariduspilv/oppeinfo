
package ee.hois.xroad.sais2.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClassificationExport complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClassificationExport"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ClassificationTypes" type="{http://sais2.x-road.ee/producer/}ArrayOfClassificationTypeItem" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClassificationExport", propOrder = {
    "classificationTypes"
})
public class ClassificationExport {

    @XmlElement(name = "ClassificationTypes")
    protected ArrayOfClassificationTypeItem classificationTypes;

    /**
     * Gets the value of the classificationTypes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfClassificationTypeItem }
     *     
     */
    public ArrayOfClassificationTypeItem getClassificationTypes() {
        return classificationTypes;
    }

    /**
     * Sets the value of the classificationTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfClassificationTypeItem }
     *     
     */
    public void setClassificationTypes(ArrayOfClassificationTypeItem value) {
        this.classificationTypes = value;
    }

}
