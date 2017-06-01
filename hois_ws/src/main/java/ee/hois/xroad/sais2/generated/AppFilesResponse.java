
package ee.hois.xroad.sais2.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AppFilesResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AppFilesResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FileList" type="{http://sais2.x-road.ee/producer/}ArrayOfApplicationFile" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppFilesResponse", propOrder = {
    "fileList"
})
public class AppFilesResponse {

    @XmlElement(name = "FileList")
    protected ArrayOfApplicationFile fileList;

    /**
     * Gets the value of the fileList property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfApplicationFile }
     *     
     */
    public ArrayOfApplicationFile getFileList() {
        return fileList;
    }

    /**
     * Sets the value of the fileList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfApplicationFile }
     *     
     */
    public void setFileList(ArrayOfApplicationFile value) {
        this.fileList = value;
    }

}
