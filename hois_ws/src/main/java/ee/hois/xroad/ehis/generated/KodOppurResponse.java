
package ee.hois.xroad.ehis.generated;

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
 *         &lt;element name="yldandmed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yldandmed"/&gt;
 *         &lt;element name="tegelikElukoht" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tegelikElukoht"/&gt;
 *         &lt;element name="yldhOppetoo" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yldhOppetoo"/&gt;
 *         &lt;element name="korgkOppetooList" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}kodOppurOppetood"/&gt;
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
    "yldandmed",
    "tegelikElukoht",
    "yldhOppetoo",
    "korgkOppetooList"
})
@XmlRootElement(name = "kodOppurResponse")
public class KodOppurResponse {

    @XmlElement(required = true, nillable = true)
    protected Yldandmed yldandmed;
    @XmlElement(required = true, nillable = true)
    protected TegelikElukoht tegelikElukoht;
    @XmlElement(required = true, nillable = true)
    protected YldhOppetoo yldhOppetoo;
    @XmlElement(required = true, nillable = true)
    protected KodOppurOppetood korgkOppetooList;

    /**
     * Gets the value of the yldandmed property.
     * 
     * @return
     *     possible object is
     *     {@link Yldandmed }
     *     
     */
    public Yldandmed getYldandmed() {
        return yldandmed;
    }

    /**
     * Sets the value of the yldandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Yldandmed }
     *     
     */
    public void setYldandmed(Yldandmed value) {
        this.yldandmed = value;
    }

    /**
     * Gets the value of the tegelikElukoht property.
     * 
     * @return
     *     possible object is
     *     {@link TegelikElukoht }
     *     
     */
    public TegelikElukoht getTegelikElukoht() {
        return tegelikElukoht;
    }

    /**
     * Sets the value of the tegelikElukoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link TegelikElukoht }
     *     
     */
    public void setTegelikElukoht(TegelikElukoht value) {
        this.tegelikElukoht = value;
    }

    /**
     * Gets the value of the yldhOppetoo property.
     * 
     * @return
     *     possible object is
     *     {@link YldhOppetoo }
     *     
     */
    public YldhOppetoo getYldhOppetoo() {
        return yldhOppetoo;
    }

    /**
     * Sets the value of the yldhOppetoo property.
     * 
     * @param value
     *     allowed object is
     *     {@link YldhOppetoo }
     *     
     */
    public void setYldhOppetoo(YldhOppetoo value) {
        this.yldhOppetoo = value;
    }

    /**
     * Gets the value of the korgkOppetooList property.
     * 
     * @return
     *     possible object is
     *     {@link KodOppurOppetood }
     *     
     */
    public KodOppurOppetood getKorgkOppetooList() {
        return korgkOppetooList;
    }

    /**
     * Sets the value of the korgkOppetooList property.
     * 
     * @param value
     *     allowed object is
     *     {@link KodOppurOppetood }
     *     
     */
    public void setKorgkOppetooList(KodOppurOppetood value) {
        this.korgkOppetooList = value;
    }

}
