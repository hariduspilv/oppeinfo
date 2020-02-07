
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRVMISIKUTTOENDAVDOKUMENTVmDpRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRVMISIKUTTOENDAVDOKUMENTVmDpRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RRVMISIKUTTOENDAVDOKUMENTVmDp" type="{http://rr.x-road.eu/producer}RRExtDocumentDataRequest"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRVMISIKUTTOENDAVDOKUMENTVmDpRequestType", propOrder = {
    "rrvmisikuttoendavdokumentVmDp"
})
public class RRVMISIKUTTOENDAVDOKUMENTVmDpRequestType {

    @XmlElement(name = "RRVMISIKUTTOENDAVDOKUMENTVmDp", required = true)
    protected RRExtDocumentDataRequest rrvmisikuttoendavdokumentVmDp;

    /**
     * Gets the value of the rrvmisikuttoendavdokumentVmDp property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentDataRequest }
     *     
     */
    public RRExtDocumentDataRequest getRRVMISIKUTTOENDAVDOKUMENTVmDp() {
        return rrvmisikuttoendavdokumentVmDp;
    }

    /**
     * Sets the value of the rrvmisikuttoendavdokumentVmDp property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentDataRequest }
     *     
     */
    public void setRRVMISIKUTTOENDAVDOKUMENTVmDp(RRExtDocumentDataRequest value) {
        this.rrvmisikuttoendavdokumentVmDp = value;
    }

}
