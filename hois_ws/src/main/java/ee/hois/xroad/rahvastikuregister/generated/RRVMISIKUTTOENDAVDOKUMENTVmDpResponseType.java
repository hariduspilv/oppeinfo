
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRVMISIKUTTOENDAVDOKUMENTVmDpResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRVMISIKUTTOENDAVDOKUMENTVmDpResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RRVMISIKUTTOENDAVDOKUMENTVmDp" type="{http://rr.x-road.eu/producer}RRExtDocumentDataResponse"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRVMISIKUTTOENDAVDOKUMENTVmDpResponseType", propOrder = {
    "rrvmisikuttoendavdokumentVmDp"
})
public class RRVMISIKUTTOENDAVDOKUMENTVmDpResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "RRVMISIKUTTOENDAVDOKUMENTVmDp", required = true)
    protected RRExtDocumentDataResponse rrvmisikuttoendavdokumentVmDp;

    /**
     * Gets the value of the rrvmisikuttoendavdokumentVmDp property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentDataResponse }
     *     
     */
    public RRExtDocumentDataResponse getRRVMISIKUTTOENDAVDOKUMENTVmDp() {
        return rrvmisikuttoendavdokumentVmDp;
    }

    /**
     * Sets the value of the rrvmisikuttoendavdokumentVmDp property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentDataResponse }
     *     
     */
    public void setRRVMISIKUTTOENDAVDOKUMENTVmDp(RRExtDocumentDataResponse value) {
        this.rrvmisikuttoendavdokumentVmDp = value;
    }

}
