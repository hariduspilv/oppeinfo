
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ettevotja_dokumentide_loetelu_vastus_v2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevotja_dokumentide_loetelu_vastus_v2"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ettevotja_dokumendid" type="{http://arireg.x-road.eu/producer/}ettevotja_dokument" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="lehekylgede_arv" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ettevotja_dokumentide_loetelu_vastus_v2", propOrder = {
    "ettevotjaDokumendid",
    "lehekylgedeArv"
})
public class EttevotjaDokumentideLoeteluVastusV2 {

    @XmlElement(name = "ettevotja_dokumendid")
    protected List<EttevotjaDokument> ettevotjaDokumendid;
    @XmlElement(name = "lehekylgede_arv")
    protected BigInteger lehekylgedeArv;

    /**
     * Gets the value of the ettevotjaDokumendid property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ettevotjaDokumendid property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEttevotjaDokumendid().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EttevotjaDokument }
     * 
     * 
     */
    public List<EttevotjaDokument> getEttevotjaDokumendid() {
        if (ettevotjaDokumendid == null) {
            ettevotjaDokumendid = new ArrayList<EttevotjaDokument>();
        }
        return this.ettevotjaDokumendid;
    }

    /**
     * Gets the value of the lehekylgedeArv property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLehekylgedeArv() {
        return lehekylgedeArv;
    }

    /**
     * Sets the value of the lehekylgedeArv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLehekylgedeArv(BigInteger value) {
        this.lehekylgedeArv = value;
    }

}
