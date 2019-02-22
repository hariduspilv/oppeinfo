
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ky_menetlusinfo_v2_Query complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ky_menetlusinfo_v2_Query"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="sonumi_id" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="kr_menetluse_nr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="maaruse_andmed" type="{http://arireg.x-road.eu/producer/}ky_menetlusinfo_v2_Maarus"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ky_menetlusinfo_v2_Query", propOrder = {
    "sonumiId",
    "krMenetluseNr",
    "maaruseAndmed"
})
public class KyMenetlusinfoV2Query {

    @XmlElement(name = "sonumi_id")
    protected BigInteger sonumiId;
    @XmlElement(name = "kr_menetluse_nr", required = true)
    protected String krMenetluseNr;
    @XmlElement(name = "maaruse_andmed", required = true)
    protected KyMenetlusinfoV2Maarus maaruseAndmed;

    /**
     * Gets the value of the sonumiId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSonumiId() {
        return sonumiId;
    }

    /**
     * Sets the value of the sonumiId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSonumiId(BigInteger value) {
        this.sonumiId = value;
    }

    /**
     * Gets the value of the krMenetluseNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKrMenetluseNr() {
        return krMenetluseNr;
    }

    /**
     * Sets the value of the krMenetluseNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKrMenetluseNr(String value) {
        this.krMenetluseNr = value;
    }

    /**
     * Gets the value of the maaruseAndmed property.
     * 
     * @return
     *     possible object is
     *     {@link KyMenetlusinfoV2Maarus }
     *     
     */
    public KyMenetlusinfoV2Maarus getMaaruseAndmed() {
        return maaruseAndmed;
    }

    /**
     * Sets the value of the maaruseAndmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link KyMenetlusinfoV2Maarus }
     *     
     */
    public void setMaaruseAndmed(KyMenetlusinfoV2Maarus value) {
        this.maaruseAndmed = value;
    }

}
