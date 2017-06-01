
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oasEhisKoodid complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oasEhisKoodid"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oasEhisKood" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" maxOccurs="1000" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oasEhisKoodid", propOrder = {
    "oasEhisKood"
})
public class OasEhisKoodid {

    @XmlSchemaType(name = "positiveInteger")
    protected List<BigInteger> oasEhisKood;

    /**
     * Gets the value of the oasEhisKood property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oasEhisKood property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOasEhisKood().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BigInteger }
     * 
     * 
     */
    public List<BigInteger> getOasEhisKood() {
        if (oasEhisKood == null) {
            oasEhisKood = new ArrayList<BigInteger>();
        }
        return this.oasEhisKood;
    }

}
