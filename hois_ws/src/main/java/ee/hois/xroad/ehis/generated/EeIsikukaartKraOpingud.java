
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for eeIsikukaartKraOpingud complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eeIsikukaartKraOpingud"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="preStudiesEducationLevelId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="maxGraduatedClass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kraOping" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}eeIsikukaartKraOping" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eeIsikukaartKraOpingud", propOrder = {
    "preStudiesEducationLevelId",
    "maxGraduatedClass",
    "kraOping"
})
public class EeIsikukaartKraOpingud {

    protected String preStudiesEducationLevelId;
    protected String maxGraduatedClass;
    protected List<EeIsikukaartKraOping> kraOping;

    /**
     * Gets the value of the preStudiesEducationLevelId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreStudiesEducationLevelId() {
        return preStudiesEducationLevelId;
    }

    /**
     * Sets the value of the preStudiesEducationLevelId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreStudiesEducationLevelId(String value) {
        this.preStudiesEducationLevelId = value;
    }

    /**
     * Gets the value of the maxGraduatedClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxGraduatedClass() {
        return maxGraduatedClass;
    }

    /**
     * Sets the value of the maxGraduatedClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxGraduatedClass(String value) {
        this.maxGraduatedClass = value;
    }

    /**
     * Gets the value of the kraOping property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the kraOping property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKraOping().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EeIsikukaartKraOping }
     * 
     * 
     */
    public List<EeIsikukaartKraOping> getKraOping() {
        if (kraOping == null) {
            kraOping = new ArrayList<EeIsikukaartKraOping>();
        }
        return this.kraOping;
    }

}
