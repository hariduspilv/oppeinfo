
package ee.hois.xroad.kutseregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="teade" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kutsetunnistused" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="kutsetunnistus" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}kutsetunnistusV2Type" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="kirjeid" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
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
    "teade",
    "kutsetunnistused",
    "kirjeid"
})
@XmlRootElement(name = "kodanikKutsetunnistusVastus")
public class KodanikKutsetunnistusVastus {

    protected String teade;
    protected KodanikKutsetunnistusVastus.Kutsetunnistused kutsetunnistused;
    protected BigInteger kirjeid;

    /**
     * Gets the value of the teade property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeade() {
        return teade;
    }

    /**
     * Sets the value of the teade property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeade(String value) {
        this.teade = value;
    }

    /**
     * Gets the value of the kutsetunnistused property.
     * 
     * @return
     *     possible object is
     *     {@link KodanikKutsetunnistusVastus.Kutsetunnistused }
     *     
     */
    public KodanikKutsetunnistusVastus.Kutsetunnistused getKutsetunnistused() {
        return kutsetunnistused;
    }

    /**
     * Sets the value of the kutsetunnistused property.
     * 
     * @param value
     *     allowed object is
     *     {@link KodanikKutsetunnistusVastus.Kutsetunnistused }
     *     
     */
    public void setKutsetunnistused(KodanikKutsetunnistusVastus.Kutsetunnistused value) {
        this.kutsetunnistused = value;
    }

    /**
     * Gets the value of the kirjeid property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKirjeid() {
        return kirjeid;
    }

    /**
     * Sets the value of the kirjeid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKirjeid(BigInteger value) {
        this.kirjeid = value;
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
     *         &lt;element name="kutsetunnistus" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}kutsetunnistusV2Type" maxOccurs="unbounded" minOccurs="0"/&gt;
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
        "kutsetunnistus"
    })
    public static class Kutsetunnistused {

        protected List<KutsetunnistusV2Type> kutsetunnistus;

        /**
         * Gets the value of the kutsetunnistus property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the kutsetunnistus property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getKutsetunnistus().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link KutsetunnistusV2Type }
         * 
         * 
         */
        public List<KutsetunnistusV2Type> getKutsetunnistus() {
            if (kutsetunnistus == null) {
                kutsetunnistus = new ArrayList<KutsetunnistusV2Type>();
            }
            return this.kutsetunnistus;
        }

    }

}
