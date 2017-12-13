
package ee.hois.xroad.kutseregister.generated;

import java.time.LocalDate;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Kutse andja poolt väljastatud tunnistuste päring
 * 
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="asutus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="valjastatudalgus" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="valjastatudlopp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *       &lt;/all&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "kutseAndjaTunnistusedParing")
public class KutseAndjaTunnistusedParing {

    protected String asutus;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate valjastatudalgus;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate valjastatudlopp;

    /**
     * Gets the value of the asutus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsutus() {
        return asutus;
    }

    /**
     * Sets the value of the asutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsutus(String value) {
        this.asutus = value;
    }

    /**
     * Gets the value of the valjastatudalgus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getValjastatudalgus() {
        return valjastatudalgus;
    }

    /**
     * Sets the value of the valjastatudalgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValjastatudalgus(LocalDate value) {
        this.valjastatudalgus = value;
    }

    /**
     * Gets the value of the valjastatudlopp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getValjastatudlopp() {
        return valjastatudlopp;
    }

    /**
     * Sets the value of the valjastatudlopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValjastatudlopp(LocalDate value) {
        this.valjastatudlopp = value;
    }

}
