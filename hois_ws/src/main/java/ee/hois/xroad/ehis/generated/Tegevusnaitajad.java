
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tegevusnaitajad complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tegevusnaitajad"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tnInfotekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tegevusnaitaja" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tegevusnaitaja" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tegevusnaitajad", propOrder = {
    "tnInfotekst",
    "tegevusnaitaja"
})
public class Tegevusnaitajad {

    protected String tnInfotekst;
    protected List<Tegevusnaitaja> tegevusnaitaja;

    /**
     * Gets the value of the tnInfotekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTnInfotekst() {
        return tnInfotekst;
    }

    /**
     * Sets the value of the tnInfotekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTnInfotekst(String value) {
        this.tnInfotekst = value;
    }

    /**
     * Gets the value of the tegevusnaitaja property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tegevusnaitaja property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTegevusnaitaja().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Tegevusnaitaja }
     * 
     * 
     */
    public List<Tegevusnaitaja> getTegevusnaitaja() {
        if (tegevusnaitaja == null) {
            tegevusnaitaja = new ArrayList<Tegevusnaitaja>();
        }
        return this.tegevusnaitaja;
    }

}
