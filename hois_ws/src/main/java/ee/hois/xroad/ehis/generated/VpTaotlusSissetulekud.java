
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="hoolealune" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="hoolealuneFailid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}failInfoDto" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="isikInfoDtos" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}isikInfoDto" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="lisatudFailid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}failInfoDto" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "hoolealune",
    "hoolealuneFailid",
    "isikInfoDtos",
    "lisatudFailid"
})
@XmlRootElement(name = "vpTaotlusSissetulekud")
public class VpTaotlusSissetulekud {

    @XmlElement(required = true)
    protected String taotlejaIsikukood;
    protected long taotluseId;
    protected boolean hoolealune;
    @XmlElement(nillable = true)
    protected List<FailInfoDto> hoolealuneFailid;
    @XmlElement(nillable = true)
    protected List<IsikInfoDto> isikInfoDtos;
    @XmlElement(nillable = true)
    protected List<FailInfoDto> lisatudFailid;

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
     * Gets the value of the hoolealune property.
     * 
     */
    public boolean isHoolealune() {
        return hoolealune;
    }

    /**
     * Sets the value of the hoolealune property.
     * 
     */
    public void setHoolealune(boolean value) {
        this.hoolealune = value;
    }

    /**
     * Gets the value of the hoolealuneFailid property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hoolealuneFailid property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHoolealuneFailid().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FailInfoDto }
     * 
     * 
     */
    public List<FailInfoDto> getHoolealuneFailid() {
        if (hoolealuneFailid == null) {
            hoolealuneFailid = new ArrayList<FailInfoDto>();
        }
        return this.hoolealuneFailid;
    }

    /**
     * Gets the value of the isikInfoDtos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the isikInfoDtos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIsikInfoDtos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IsikInfoDto }
     * 
     * 
     */
    public List<IsikInfoDto> getIsikInfoDtos() {
        if (isikInfoDtos == null) {
            isikInfoDtos = new ArrayList<IsikInfoDto>();
        }
        return this.isikInfoDtos;
    }

    /**
     * Gets the value of the lisatudFailid property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lisatudFailid property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLisatudFailid().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FailInfoDto }
     * 
     * 
     */
    public List<FailInfoDto> getLisatudFailid() {
        if (lisatudFailid == null) {
            lisatudFailid = new ArrayList<FailInfoDto>();
        }
        return this.lisatudFailid;
    }

}
