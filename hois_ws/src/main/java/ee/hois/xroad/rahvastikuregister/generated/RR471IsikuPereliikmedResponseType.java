
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR471isikuPereliikmedResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR471isikuPereliikmedResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Ema" type="{http://rr.x-road.eu/producer}RR471Isik" minOccurs="0"/&gt;
 *         &lt;element name="Isa" type="{http://rr.x-road.eu/producer}RR471Isik" minOccurs="0"/&gt;
 *         &lt;sequence&gt;
 *           &lt;element name="OdeVend" type="{http://rr.x-road.eu/producer}RR471Isik" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;/sequence&gt;
 *         &lt;sequence&gt;
 *           &lt;element name="Abikaasa" type="{http://rr.x-road.eu/producer}RR471Isik" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;/sequence&gt;
 *         &lt;sequence&gt;
 *           &lt;element name="Laps" type="{http://rr.x-road.eu/producer}RR471Isik" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;/sequence&gt;
 *         &lt;sequence&gt;
 *           &lt;element name="Eestkostetav" type="{http://rr.x-road.eu/producer}RR471Isik" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;/sequence&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR471isikuPereliikmedResponseType", propOrder = {
    "ema",
    "isa",
    "odeVend",
    "abikaasa",
    "laps",
    "eestkostetav"
})
public class RR471IsikuPereliikmedResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Ema")
    protected RR471Isik ema;
    @XmlElement(name = "Isa")
    protected RR471Isik isa;
    @XmlElement(name = "OdeVend")
    protected List<RR471Isik> odeVend;
    @XmlElement(name = "Abikaasa")
    protected List<RR471Isik> abikaasa;
    @XmlElement(name = "Laps")
    protected List<RR471Isik> laps;
    @XmlElement(name = "Eestkostetav")
    protected List<RR471Isik> eestkostetav;

    /**
     * Gets the value of the ema property.
     * 
     * @return
     *     possible object is
     *     {@link RR471Isik }
     *     
     */
    public RR471Isik getEma() {
        return ema;
    }

    /**
     * Sets the value of the ema property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR471Isik }
     *     
     */
    public void setEma(RR471Isik value) {
        this.ema = value;
    }

    /**
     * Gets the value of the isa property.
     * 
     * @return
     *     possible object is
     *     {@link RR471Isik }
     *     
     */
    public RR471Isik getIsa() {
        return isa;
    }

    /**
     * Sets the value of the isa property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR471Isik }
     *     
     */
    public void setIsa(RR471Isik value) {
        this.isa = value;
    }

    /**
     * Gets the value of the odeVend property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the odeVend property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOdeVend().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RR471Isik }
     * 
     * 
     */
    public List<RR471Isik> getOdeVend() {
        if (odeVend == null) {
            odeVend = new ArrayList<RR471Isik>();
        }
        return this.odeVend;
    }

    /**
     * Gets the value of the abikaasa property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the abikaasa property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAbikaasa().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RR471Isik }
     * 
     * 
     */
    public List<RR471Isik> getAbikaasa() {
        if (abikaasa == null) {
            abikaasa = new ArrayList<RR471Isik>();
        }
        return this.abikaasa;
    }

    /**
     * Gets the value of the laps property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the laps property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLaps().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RR471Isik }
     * 
     * 
     */
    public List<RR471Isik> getLaps() {
        if (laps == null) {
            laps = new ArrayList<RR471Isik>();
        }
        return this.laps;
    }

    /**
     * Gets the value of the eestkostetav property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the eestkostetav property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEestkostetav().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RR471Isik }
     * 
     * 
     */
    public List<RR471Isik> getEestkostetav() {
        if (eestkostetav == null) {
            eestkostetav = new ArrayList<RR471Isik>();
        }
        return this.eestkostetav;
    }

}
