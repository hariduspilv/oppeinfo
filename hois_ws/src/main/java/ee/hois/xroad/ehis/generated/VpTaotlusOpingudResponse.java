
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="taotluseId" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}longOrNothing" minOccurs="0"/&gt;
 *         &lt;element name="hoiatusDto" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}HoiatusDto" minOccurs="0"/&gt;
 *         &lt;element name="oppimineDtos" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}OppimineDto" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="taotluseAjalugu" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}TaotlusInfoDto" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "oppimineDtos",
    "taotluseAjalugu"
})
@XmlRootElement(name = "vpTaotlusOpingudResponse")
public class VpTaotlusOpingudResponse {

    @XmlElement(required = true)
    protected String taotlejaIsikukood;
    @XmlElementRef(name = "taotluseId", type = JAXBElement.class, required = false)
    protected JAXBElement<String> taotluseId;
    @XmlElementRef(name = "hoiatusDto", type = JAXBElement.class, required = false)
    protected JAXBElement<HoiatusDto> hoiatusDto;
    @XmlElement(nillable = true)
    protected List<OppimineDto> oppimineDtos;
    @XmlElement(nillable = true)
    protected List<TaotlusInfoDto> taotluseAjalugu;

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
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTaotluseId() {
        return taotluseId;
    }

    /**
     * Sets the value of the taotluseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTaotluseId(JAXBElement<String> value) {
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
     * Gets the value of the oppimineDtos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oppimineDtos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOppimineDtos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OppimineDto }
     * 
     * 
     */
    public List<OppimineDto> getOppimineDtos() {
        if (oppimineDtos == null) {
            oppimineDtos = new ArrayList<OppimineDto>();
        }
        return this.oppimineDtos;
    }

    /**
     * Gets the value of the taotluseAjalugu property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the taotluseAjalugu property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTaotluseAjalugu().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TaotlusInfoDto }
     * 
     * 
     */
    public List<TaotlusInfoDto> getTaotluseAjalugu() {
        if (taotluseAjalugu == null) {
            taotluseAjalugu = new ArrayList<TaotlusInfoDto>();
        }
        return this.taotluseAjalugu;
    }

}
