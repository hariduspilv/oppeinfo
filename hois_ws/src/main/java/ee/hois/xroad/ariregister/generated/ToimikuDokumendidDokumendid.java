
package ee.hois.xroad.ariregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for toimiku_dokumendid_dokumendid complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="toimiku_dokumendid_dokumendid"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dokument" type="{http://arireg.x-road.eu/producer/}toimiku_dokumendid_dokument" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "toimiku_dokumendid_dokumendid", propOrder = {
    "dokument"
})
public class ToimikuDokumendidDokumendid {

    protected List<ToimikuDokumendidDokument> dokument;

    /**
     * Gets the value of the dokument property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dokument property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDokument().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ToimikuDokumendidDokument }
     * 
     * 
     */
    public List<ToimikuDokumendidDokument> getDokument() {
        if (dokument == null) {
            dokument = new ArrayList<ToimikuDokumendidDokument>();
        }
        return this.dokument;
    }

}
