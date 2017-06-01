
package ee.hois.xroad.sais2.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AppDismissResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AppDismissResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IsDismissed" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppDismissResponse", propOrder = {
    "isDismissed"
})
public class AppDismissResponse {

    @XmlElement(name = "IsDismissed")
    protected boolean isDismissed;

    /**
     * Gets the value of the isDismissed property.
     * 
     */
    public boolean isIsDismissed() {
        return isDismissed;
    }

    /**
     * Sets the value of the isDismissed property.
     * 
     */
    public void setIsDismissed(boolean value) {
        this.isDismissed = value;
    }

}
