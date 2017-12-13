
package ee.hois.xroad.kutseregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Muutunud kutsestandardite vastus
 * 
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="kutsestandardid" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="kutsestandard" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}muutunudKutsestandardType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="viga" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/all&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "muutunudKutsestandardidVastus")
public class MuutunudKutsestandardidVastus {

    @XmlElement(namespace = "")
    protected MuutunudKutsestandardidVastus.Kutsestandardid kutsestandardid;
    protected String viga;

    /**
     * Gets the value of the kutsestandardid property.
     * 
     * @return
     *     possible object is
     *     {@link MuutunudKutsestandardidVastus.Kutsestandardid }
     *     
     */
    public MuutunudKutsestandardidVastus.Kutsestandardid getKutsestandardid() {
        return kutsestandardid;
    }

    /**
     * Sets the value of the kutsestandardid property.
     * 
     * @param value
     *     allowed object is
     *     {@link MuutunudKutsestandardidVastus.Kutsestandardid }
     *     
     */
    public void setKutsestandardid(MuutunudKutsestandardidVastus.Kutsestandardid value) {
        this.kutsestandardid = value;
    }

    /**
     * Gets the value of the viga property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViga() {
        return viga;
    }

    /**
     * Sets the value of the viga property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViga(String value) {
        this.viga = value;
    }


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
     *         &lt;element name="kutsestandard" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}muutunudKutsestandardType" maxOccurs="unbounded" minOccurs="0"/&gt;
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
        "kutsestandard"
    })
    public static class Kutsestandardid {

        @XmlElement(namespace = "")
        protected List<MuutunudKutsestandardType> kutsestandard;

        /**
         * Gets the value of the kutsestandard property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the kutsestandard property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getKutsestandard().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link MuutunudKutsestandardType }
         * 
         * 
         */
        public List<MuutunudKutsestandardType> getKutsestandard() {
            if (kutsestandard == null) {
                kutsestandard = new ArrayList<MuutunudKutsestandardType>();
            }
            return this.kutsestandard;
        }

    }

}
