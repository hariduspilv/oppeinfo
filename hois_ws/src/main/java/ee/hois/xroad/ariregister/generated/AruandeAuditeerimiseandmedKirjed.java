
package ee.hois.xroad.ariregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for aruande_auditeerimiseandmed_kirjed complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="aruande_auditeerimiseandmed_kirjed"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="item" type="{http://arireg.x-road.eu/producer/}aruande_auditeerimiseandmed_kirje" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "aruande_auditeerimiseandmed_kirjed", propOrder = {
    "item"
})
public class AruandeAuditeerimiseandmedKirjed {

    protected List<AruandeAuditeerimiseandmedKirje> item;

    /**
     * Gets the value of the item property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the item property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AruandeAuditeerimiseandmedKirje }
     * 
     * 
     */
    public List<AruandeAuditeerimiseandmedKirje> getItem() {
        if (item == null) {
            item = new ArrayList<AruandeAuditeerimiseandmedKirje>();
        }
        return this.item;
    }

}