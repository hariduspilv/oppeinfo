
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for yhlUjumine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="yhlUjumine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ettenaahtudTundideArv" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/&gt;
 *         &lt;element name="labitudTundideArv" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/&gt;
 *         &lt;element name="klGrupiSuurus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klUjumisoskuseTase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="treener" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlTreener" minOccurs="0"/&gt;
 *         &lt;element name="abistajad" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlAbistajad" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "yhlUjumine", propOrder = {
    "ettenaahtudTundideArv",
    "labitudTundideArv",
    "klGrupiSuurus",
    "klUjumisoskuseTase",
    "treener",
    "abistajad"
})
public class YhlUjumine {

    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger ettenaahtudTundideArv;
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger labitudTundideArv;
    protected String klGrupiSuurus;
    protected String klUjumisoskuseTase;
    protected YhlTreener treener;
    protected YhlAbistajad abistajad;

    /**
     * Gets the value of the ettenaahtudTundideArv property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEttenaahtudTundideArv() {
        return ettenaahtudTundideArv;
    }

    /**
     * Sets the value of the ettenaahtudTundideArv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEttenaahtudTundideArv(BigInteger value) {
        this.ettenaahtudTundideArv = value;
    }

    /**
     * Gets the value of the labitudTundideArv property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLabitudTundideArv() {
        return labitudTundideArv;
    }

    /**
     * Sets the value of the labitudTundideArv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLabitudTundideArv(BigInteger value) {
        this.labitudTundideArv = value;
    }

    /**
     * Gets the value of the klGrupiSuurus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlGrupiSuurus() {
        return klGrupiSuurus;
    }

    /**
     * Sets the value of the klGrupiSuurus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlGrupiSuurus(String value) {
        this.klGrupiSuurus = value;
    }

    /**
     * Gets the value of the klUjumisoskuseTase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlUjumisoskuseTase() {
        return klUjumisoskuseTase;
    }

    /**
     * Sets the value of the klUjumisoskuseTase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlUjumisoskuseTase(String value) {
        this.klUjumisoskuseTase = value;
    }

    /**
     * Gets the value of the treener property.
     * 
     * @return
     *     possible object is
     *     {@link YhlTreener }
     *     
     */
    public YhlTreener getTreener() {
        return treener;
    }

    /**
     * Sets the value of the treener property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlTreener }
     *     
     */
    public void setTreener(YhlTreener value) {
        this.treener = value;
    }

    /**
     * Gets the value of the abistajad property.
     * 
     * @return
     *     possible object is
     *     {@link YhlAbistajad }
     *     
     */
    public YhlAbistajad getAbistajad() {
        return abistajad;
    }

    /**
     * Sets the value of the abistajad property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlAbistajad }
     *     
     */
    public void setAbistajad(YhlAbistajad value) {
        this.abistajad = value;
    }

}
