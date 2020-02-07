
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR431ResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR431ResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RiigisViibimiseAlus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Ema" type="{http://rr.x-road.eu/producer}RR431Isik" minOccurs="0"/&gt;
 *         &lt;element name="Isa" type="{http://rr.x-road.eu/producer}RR431Isik" minOccurs="0"/&gt;
 *         &lt;sequence&gt;
 *           &lt;element name="OdeVend" type="{http://rr.x-road.eu/producer}RR431Isik" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;/sequence&gt;
 *         &lt;sequence&gt;
 *           &lt;element name="PoolodePoolvend" type="{http://rr.x-road.eu/producer}RR431Isik" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;/sequence&gt;
 *         &lt;sequence&gt;
 *           &lt;element name="Abikaasa" type="{http://rr.x-road.eu/producer}RR431Isik" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;/sequence&gt;
 *         &lt;sequence&gt;
 *           &lt;element name="LapseEestkoste" type="{http://rr.x-road.eu/producer}RR431Isik" maxOccurs="unbounded" minOccurs="0"/&gt;
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
@XmlType(name = "RR431ResponseType", propOrder = {
    "riigisViibimiseAlus",
    "ema",
    "isa",
    "odeVend",
    "poolodePoolvend",
    "abikaasa",
    "lapseEestkoste"
})
public class RR431ResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "RiigisViibimiseAlus")
    protected String riigisViibimiseAlus;
    @XmlElement(name = "Ema")
    protected RR431Isik ema;
    @XmlElement(name = "Isa")
    protected RR431Isik isa;
    @XmlElement(name = "OdeVend")
    protected List<RR431Isik> odeVend;
    @XmlElement(name = "PoolodePoolvend")
    protected List<RR431Isik> poolodePoolvend;
    @XmlElement(name = "Abikaasa")
    protected List<RR431Isik> abikaasa;
    @XmlElement(name = "LapseEestkoste")
    protected List<RR431Isik> lapseEestkoste;

    /**
     * Gets the value of the riigisViibimiseAlus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiigisViibimiseAlus() {
        return riigisViibimiseAlus;
    }

    /**
     * Sets the value of the riigisViibimiseAlus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiigisViibimiseAlus(String value) {
        this.riigisViibimiseAlus = value;
    }

    /**
     * Gets the value of the ema property.
     * 
     * @return
     *     possible object is
     *     {@link RR431Isik }
     *     
     */
    public RR431Isik getEma() {
        return ema;
    }

    /**
     * Sets the value of the ema property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR431Isik }
     *     
     */
    public void setEma(RR431Isik value) {
        this.ema = value;
    }

    /**
     * Gets the value of the isa property.
     * 
     * @return
     *     possible object is
     *     {@link RR431Isik }
     *     
     */
    public RR431Isik getIsa() {
        return isa;
    }

    /**
     * Sets the value of the isa property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR431Isik }
     *     
     */
    public void setIsa(RR431Isik value) {
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
     * {@link RR431Isik }
     * 
     * 
     */
    public List<RR431Isik> getOdeVend() {
        if (odeVend == null) {
            odeVend = new ArrayList<RR431Isik>();
        }
        return this.odeVend;
    }

    /**
     * Gets the value of the poolodePoolvend property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the poolodePoolvend property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPoolodePoolvend().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RR431Isik }
     * 
     * 
     */
    public List<RR431Isik> getPoolodePoolvend() {
        if (poolodePoolvend == null) {
            poolodePoolvend = new ArrayList<RR431Isik>();
        }
        return this.poolodePoolvend;
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
     * {@link RR431Isik }
     * 
     * 
     */
    public List<RR431Isik> getAbikaasa() {
        if (abikaasa == null) {
            abikaasa = new ArrayList<RR431Isik>();
        }
        return this.abikaasa;
    }

    /**
     * Gets the value of the lapseEestkoste property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lapseEestkoste property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLapseEestkoste().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RR431Isik }
     * 
     * 
     */
    public List<RR431Isik> getLapseEestkoste() {
        if (lapseEestkoste == null) {
            lapseEestkoste = new ArrayList<RR431Isik>();
        }
        return this.lapseEestkoste;
    }

}
