
package ee.hois.xroad.rtip.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Array complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Array"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="T_DATA" type="{http://rtk-v6.x-road.eu}ZDOCIMPORT_01S" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Array", propOrder = {
    "tdata"
})
@XmlSeeAlso({
    ee.hois.xroad.rtip.generated.FinantskandeImportRequestType.TDATA.class
})
public class Array {

    @XmlElement(name = "T_DATA", required = true)
    protected List<ZDOCIMPORT01S> tdata;

    /**
     * Gets the value of the tdata property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tdata property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTDATA().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ZDOCIMPORT01S }
     * 
     * 
     */
    public List<ZDOCIMPORT01S> getTDATA() {
        if (tdata == null) {
            tdata = new ArrayList<ZDOCIMPORT01S>();
        }
        return this.tdata;
    }

}
