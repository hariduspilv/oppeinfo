
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for yhlYldharidusMuuda complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="yhlYldharidusMuuda"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="isikuandmeteMuutus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlIsikuandmed"/&gt;
 *           &lt;element name="voorkeeleMuutus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlVoorkeeled"/&gt;
 *           &lt;element name="jargmisesseKlassiYleviimine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlJargmiseKlassi"/&gt;
 *           &lt;element name="klassiOppekavaMuutus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlKlassiOkMuutus"/&gt;
 *           &lt;element name="oppekeeleMuutus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlOppekeelMuutus"/&gt;
 *           &lt;element name="oppevormiMuutus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlOppevormMuutus"/&gt;
 *           &lt;element name="oppepuhkuseAlgus" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *           &lt;element name="oppepuhkuseLopp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *           &lt;element name="teisteAndmeteMuutus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlTeisAndMuutus"/&gt;
 *           &lt;element name="oppeasutusestLahkumine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlOppeasLahkumine"/&gt;
 *           &lt;element name="puudulikeAastahinneteMuutus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlPuudulikudAastahinded"/&gt;
 *           &lt;element name="erivajadusteTugiteenusteMuutus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlErivajadusedTugiteenused"/&gt;
 *           &lt;element name="vanemaNousolekudMuutus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlVanemaNousolekud"/&gt;
 *           &lt;element name="kutseoppeMuutus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlKutseope"/&gt;
 *           &lt;element name="ujumisandmeteMuutus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlUjumisandmed"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "yhlYldharidusMuuda", propOrder = {
    "isikuandmeteMuutus",
    "voorkeeleMuutus",
    "jargmisesseKlassiYleviimine",
    "klassiOppekavaMuutus",
    "oppekeeleMuutus",
    "oppevormiMuutus",
    "oppepuhkuseAlgus",
    "oppepuhkuseLopp",
    "teisteAndmeteMuutus",
    "oppeasutusestLahkumine",
    "puudulikeAastahinneteMuutus",
    "erivajadusteTugiteenusteMuutus",
    "vanemaNousolekudMuutus",
    "kutseoppeMuutus",
    "ujumisandmeteMuutus"
})
public class YhlYldharidusMuuda {

    protected YhlIsikuandmed isikuandmeteMuutus;
    protected YhlVoorkeeled voorkeeleMuutus;
    protected YhlJargmiseKlassi jargmisesseKlassiYleviimine;
    protected YhlKlassiOkMuutus klassiOppekavaMuutus;
    protected YhlOppekeelMuutus oppekeeleMuutus;
    protected YhlOppevormMuutus oppevormiMuutus;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar oppepuhkuseAlgus;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar oppepuhkuseLopp;
    protected YhlTeisAndMuutus teisteAndmeteMuutus;
    protected YhlOppeasLahkumine oppeasutusestLahkumine;
    protected YhlPuudulikudAastahinded puudulikeAastahinneteMuutus;
    protected YhlErivajadusedTugiteenused erivajadusteTugiteenusteMuutus;
    protected YhlVanemaNousolekud vanemaNousolekudMuutus;
    protected YhlKutseope kutseoppeMuutus;
    protected YhlUjumisandmed ujumisandmeteMuutus;

    /**
     * Gets the value of the isikuandmeteMuutus property.
     * 
     * @return
     *     possible object is
     *     {@link YhlIsikuandmed }
     *     
     */
    public YhlIsikuandmed getIsikuandmeteMuutus() {
        return isikuandmeteMuutus;
    }

    /**
     * Sets the value of the isikuandmeteMuutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlIsikuandmed }
     *     
     */
    public void setIsikuandmeteMuutus(YhlIsikuandmed value) {
        this.isikuandmeteMuutus = value;
    }

    /**
     * Gets the value of the voorkeeleMuutus property.
     * 
     * @return
     *     possible object is
     *     {@link YhlVoorkeeled }
     *     
     */
    public YhlVoorkeeled getVoorkeeleMuutus() {
        return voorkeeleMuutus;
    }

    /**
     * Sets the value of the voorkeeleMuutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlVoorkeeled }
     *     
     */
    public void setVoorkeeleMuutus(YhlVoorkeeled value) {
        this.voorkeeleMuutus = value;
    }

    /**
     * Gets the value of the jargmisesseKlassiYleviimine property.
     * 
     * @return
     *     possible object is
     *     {@link YhlJargmiseKlassi }
     *     
     */
    public YhlJargmiseKlassi getJargmisesseKlassiYleviimine() {
        return jargmisesseKlassiYleviimine;
    }

    /**
     * Sets the value of the jargmisesseKlassiYleviimine property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlJargmiseKlassi }
     *     
     */
    public void setJargmisesseKlassiYleviimine(YhlJargmiseKlassi value) {
        this.jargmisesseKlassiYleviimine = value;
    }

    /**
     * Gets the value of the klassiOppekavaMuutus property.
     * 
     * @return
     *     possible object is
     *     {@link YhlKlassiOkMuutus }
     *     
     */
    public YhlKlassiOkMuutus getKlassiOppekavaMuutus() {
        return klassiOppekavaMuutus;
    }

    /**
     * Sets the value of the klassiOppekavaMuutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlKlassiOkMuutus }
     *     
     */
    public void setKlassiOppekavaMuutus(YhlKlassiOkMuutus value) {
        this.klassiOppekavaMuutus = value;
    }

    /**
     * Gets the value of the oppekeeleMuutus property.
     * 
     * @return
     *     possible object is
     *     {@link YhlOppekeelMuutus }
     *     
     */
    public YhlOppekeelMuutus getOppekeeleMuutus() {
        return oppekeeleMuutus;
    }

    /**
     * Sets the value of the oppekeeleMuutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlOppekeelMuutus }
     *     
     */
    public void setOppekeeleMuutus(YhlOppekeelMuutus value) {
        this.oppekeeleMuutus = value;
    }

    /**
     * Gets the value of the oppevormiMuutus property.
     * 
     * @return
     *     possible object is
     *     {@link YhlOppevormMuutus }
     *     
     */
    public YhlOppevormMuutus getOppevormiMuutus() {
        return oppevormiMuutus;
    }

    /**
     * Sets the value of the oppevormiMuutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlOppevormMuutus }
     *     
     */
    public void setOppevormiMuutus(YhlOppevormMuutus value) {
        this.oppevormiMuutus = value;
    }

    /**
     * Gets the value of the oppepuhkuseAlgus property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOppepuhkuseAlgus() {
        return oppepuhkuseAlgus;
    }

    /**
     * Sets the value of the oppepuhkuseAlgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOppepuhkuseAlgus(XMLGregorianCalendar value) {
        this.oppepuhkuseAlgus = value;
    }

    /**
     * Gets the value of the oppepuhkuseLopp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOppepuhkuseLopp() {
        return oppepuhkuseLopp;
    }

    /**
     * Sets the value of the oppepuhkuseLopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOppepuhkuseLopp(XMLGregorianCalendar value) {
        this.oppepuhkuseLopp = value;
    }

    /**
     * Gets the value of the teisteAndmeteMuutus property.
     * 
     * @return
     *     possible object is
     *     {@link YhlTeisAndMuutus }
     *     
     */
    public YhlTeisAndMuutus getTeisteAndmeteMuutus() {
        return teisteAndmeteMuutus;
    }

    /**
     * Sets the value of the teisteAndmeteMuutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlTeisAndMuutus }
     *     
     */
    public void setTeisteAndmeteMuutus(YhlTeisAndMuutus value) {
        this.teisteAndmeteMuutus = value;
    }

    /**
     * Gets the value of the oppeasutusestLahkumine property.
     * 
     * @return
     *     possible object is
     *     {@link YhlOppeasLahkumine }
     *     
     */
    public YhlOppeasLahkumine getOppeasutusestLahkumine() {
        return oppeasutusestLahkumine;
    }

    /**
     * Sets the value of the oppeasutusestLahkumine property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlOppeasLahkumine }
     *     
     */
    public void setOppeasutusestLahkumine(YhlOppeasLahkumine value) {
        this.oppeasutusestLahkumine = value;
    }

    /**
     * Gets the value of the puudulikeAastahinneteMuutus property.
     * 
     * @return
     *     possible object is
     *     {@link YhlPuudulikudAastahinded }
     *     
     */
    public YhlPuudulikudAastahinded getPuudulikeAastahinneteMuutus() {
        return puudulikeAastahinneteMuutus;
    }

    /**
     * Sets the value of the puudulikeAastahinneteMuutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlPuudulikudAastahinded }
     *     
     */
    public void setPuudulikeAastahinneteMuutus(YhlPuudulikudAastahinded value) {
        this.puudulikeAastahinneteMuutus = value;
    }

    /**
     * Gets the value of the erivajadusteTugiteenusteMuutus property.
     * 
     * @return
     *     possible object is
     *     {@link YhlErivajadusedTugiteenused }
     *     
     */
    public YhlErivajadusedTugiteenused getErivajadusteTugiteenusteMuutus() {
        return erivajadusteTugiteenusteMuutus;
    }

    /**
     * Sets the value of the erivajadusteTugiteenusteMuutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlErivajadusedTugiteenused }
     *     
     */
    public void setErivajadusteTugiteenusteMuutus(YhlErivajadusedTugiteenused value) {
        this.erivajadusteTugiteenusteMuutus = value;
    }

    /**
     * Gets the value of the vanemaNousolekudMuutus property.
     * 
     * @return
     *     possible object is
     *     {@link YhlVanemaNousolekud }
     *     
     */
    public YhlVanemaNousolekud getVanemaNousolekudMuutus() {
        return vanemaNousolekudMuutus;
    }

    /**
     * Sets the value of the vanemaNousolekudMuutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlVanemaNousolekud }
     *     
     */
    public void setVanemaNousolekudMuutus(YhlVanemaNousolekud value) {
        this.vanemaNousolekudMuutus = value;
    }

    /**
     * Gets the value of the kutseoppeMuutus property.
     * 
     * @return
     *     possible object is
     *     {@link YhlKutseope }
     *     
     */
    public YhlKutseope getKutseoppeMuutus() {
        return kutseoppeMuutus;
    }

    /**
     * Sets the value of the kutseoppeMuutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlKutseope }
     *     
     */
    public void setKutseoppeMuutus(YhlKutseope value) {
        this.kutseoppeMuutus = value;
    }

    /**
     * Gets the value of the ujumisandmeteMuutus property.
     * 
     * @return
     *     possible object is
     *     {@link YhlUjumisandmed }
     *     
     */
    public YhlUjumisandmed getUjumisandmeteMuutus() {
        return ujumisandmeteMuutus;
    }

    /**
     * Sets the value of the ujumisandmeteMuutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlUjumisandmed }
     *     
     */
    public void setUjumisandmeteMuutus(YhlUjumisandmed value) {
        this.ujumisandmeteMuutus = value;
    }

}
