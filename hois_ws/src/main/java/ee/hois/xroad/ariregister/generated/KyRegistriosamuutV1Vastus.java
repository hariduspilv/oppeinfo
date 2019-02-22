
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ky_registriosamuut_v1_Vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ky_registriosamuut_v1_Vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="staatus" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="staatus_detailid" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="korteriyhistu" type="{http://arireg.x-road.eu/producer/}ky_registriosamuut_v1_Vastusrida" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ky_registriosamuut_v1_Vastus", propOrder = {
    "staatus",
    "staatusDetailid",
    "korteriyhistu"
})
public class KyRegistriosamuutV1Vastus {

    @XmlElement(required = true)
    protected BigInteger staatus;
    @XmlElement(name = "staatus_detailid", required = true)
    protected String staatusDetailid;
    protected List<KyRegistriosamuutV1Vastusrida> korteriyhistu;

    /**
     * Gets the value of the staatus property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getStaatus() {
        return staatus;
    }

    /**
     * Sets the value of the staatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setStaatus(BigInteger value) {
        this.staatus = value;
    }

    /**
     * Gets the value of the staatusDetailid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStaatusDetailid() {
        return staatusDetailid;
    }

    /**
     * Sets the value of the staatusDetailid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStaatusDetailid(String value) {
        this.staatusDetailid = value;
    }

    /**
     * Gets the value of the korteriyhistu property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the korteriyhistu property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKorteriyhistu().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KyRegistriosamuutV1Vastusrida }
     * 
     * 
     */
    public List<KyRegistriosamuutV1Vastusrida> getKorteriyhistu() {
        if (korteriyhistu == null) {
            korteriyhistu = new ArrayList<KyRegistriosamuutV1Vastusrida>();
        }
        return this.korteriyhistu;
    }

}
