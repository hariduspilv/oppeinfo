
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="taotlejaIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="taotluseId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="hoiatusDto" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}hoiatusDto" minOccurs="0"/&gt;
 *         &lt;element name="taotlusInfoDto" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}taotlusInfoDto" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "taotlejaIsikukood",
    "taotluseId",
    "hoiatusDto",
    "taotlusInfoDto"
})
@XmlRootElement(name = "vpTaotlusEsitamineResponse")
public class VpTaotlusEsitamineResponse {

    @XmlElement(required = true)
    protected String taotlejaIsikukood;
    protected long taotluseId;
    @XmlElementRef(name = "hoiatusDto", type = JAXBElement.class, required = false)
    protected JAXBElement<HoiatusDto> hoiatusDto;
    @XmlElementRef(name = "taotlusInfoDto", type = JAXBElement.class, required = false)
    protected JAXBElement<TaotlusInfoDto> taotlusInfoDto;

    /**
     * Gets the value of the taotlejaIsikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaotlejaIsikukood() {
        return taotlejaIsikukood;
    }

    /**
     * Sets the value of the taotlejaIsikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaotlejaIsikukood(String value) {
        this.taotlejaIsikukood = value;
    }

    /**
     * Gets the value of the taotluseId property.
     * 
     */
    public long getTaotluseId() {
        return taotluseId;
    }

    /**
     * Sets the value of the taotluseId property.
     * 
     */
    public void setTaotluseId(long value) {
        this.taotluseId = value;
    }

    /**
     * Gets the value of the hoiatusDto property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link HoiatusDto }{@code >}
     *     
     */
    public JAXBElement<HoiatusDto> getHoiatusDto() {
        return hoiatusDto;
    }

    /**
     * Sets the value of the hoiatusDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link HoiatusDto }{@code >}
     *     
     */
    public void setHoiatusDto(JAXBElement<HoiatusDto> value) {
        this.hoiatusDto = value;
    }

    /**
     * Gets the value of the taotlusInfoDto property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link TaotlusInfoDto }{@code >}
     *     
     */
    public JAXBElement<TaotlusInfoDto> getTaotlusInfoDto() {
        return taotlusInfoDto;
    }

    /**
     * Sets the value of the taotlusInfoDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link TaotlusInfoDto }{@code >}
     *     
     */
    public void setTaotlusInfoDto(JAXBElement<TaotlusInfoDto> value) {
        this.taotlusInfoDto = value;
    }

}
