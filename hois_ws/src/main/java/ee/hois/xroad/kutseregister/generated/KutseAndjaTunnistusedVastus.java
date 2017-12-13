
package ee.hois.xroad.kutseregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Kutse andja poolt väljastatud tunnistused
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
 *         &lt;element name="vigadearv" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="vead" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}veadType" minOccurs="0"/&gt;
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
@XmlRootElement(name = "kutseAndjaTunnistusedVastus")
public class KutseAndjaTunnistusedVastus {

    protected KutseAndjaTunnistusedVastus.Kutsetunnistused kutsetunnistused;
    protected BigInteger kirjeid;
    @XmlElement(required = true)
    protected BigInteger vigadearv;
    protected VeadType vead;

    /**
     * Gets the value of the kutsetunnistused property.
     * 
     * @return
     *     possible object is
     *     {@link KutseAndjaTunnistusedVastus.Kutsetunnistused }
     *     
     */
    public KutseAndjaTunnistusedVastus.Kutsetunnistused getKutsetunnistused() {
        return kutsetunnistused;
    }

    /**
     * Sets the value of the kutsetunnistused property.
     * 
     * @param value
     *     allowed object is
     *     {@link KutseAndjaTunnistusedVastus.Kutsetunnistused }
     *     
     */
    public void setKutsetunnistused(KutseAndjaTunnistusedVastus.Kutsetunnistused value) {
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
     * Gets the value of the vigadearv property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVigadearv() {
        return vigadearv;
    }

    /**
     * Sets the value of the vigadearv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVigadearv(BigInteger value) {
        this.vigadearv = value;
    }

    /**
     * Gets the value of the vead property.
     * 
     * @return
     *     possible object is
     *     {@link VeadType }
     *     
     */
    public VeadType getVead() {
        return vead;
    }

    /**
     * Sets the value of the vead property.
     * 
     * @param value
     *     allowed object is
     *     {@link VeadType }
     *     
     */
    public void setVead(VeadType value) {
        this.vead = value;
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
