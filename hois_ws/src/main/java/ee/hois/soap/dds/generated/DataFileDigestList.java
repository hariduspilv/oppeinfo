
package ee.hois.soap.dds.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DataFileDigestList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DataFileDigestList"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DataFileDigest" type="{http://www.sk.ee/DigiDocService/DigiDocService_2_3.wsdl}DataFileDigest" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataFileDigestList", propOrder = {
    "dataFileDigest"
})
public class DataFileDigestList {

    @XmlElement(name = "DataFileDigest")
    protected List<DataFileDigest> dataFileDigest;

    /**
     * Gets the value of the dataFileDigest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataFileDigest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataFileDigest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataFileDigest }
     * 
     * 
     */
    public List<DataFileDigest> getDataFileDigest() {
        if (dataFileDigest == null) {
            dataFileDigest = new ArrayList<DataFileDigest>();
        }
        return this.dataFileDigest;
    }

}
