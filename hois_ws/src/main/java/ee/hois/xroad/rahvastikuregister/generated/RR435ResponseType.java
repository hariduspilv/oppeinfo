
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR435ResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR435ResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Veakood" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="Veatekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Isik"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Isik.Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
 *                   &lt;element name="Isik.TopeltIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Isik.Synniaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                   &lt;element name="Isik.Surmaaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                   &lt;element name="Isik.SuguKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Isik.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Isik.KirjeStaatusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Isik.KirjeStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Isik.ArhiiviKandmisePohjus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="Isik.Kodakondsused"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Kodakondsus" maxOccurs="unbounded" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Kodakondsus.OnPohiline" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                       &lt;element name="Kodakondsus.KehtivAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                                       &lt;element name="Kodakondsus.KehtivKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                                       &lt;element name="Kodakondsus.ISO3Kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                       &lt;element name="Kodakondsus.Nimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="Isik.TeovoimePiirangud"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Teovoime" maxOccurs="unbounded" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Teovoime.LiikKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="Teovoime.Liik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                       &lt;element name="Teovoime.Markus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                       &lt;element name="Teovoime.KehtivAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                                       &lt;element name="Teovoime.KehtivKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                                       &lt;element name="Teovoime.StaatusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="Teovoime.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="Isik.Aadress" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Aadress.KehtivAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                             &lt;element name="Aadress.Sihtnumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Aadress.Tanav" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Aadress.Maja" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Aadress.Korter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Aadress.Nimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Aadress.Vkoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Aadress.KOV" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Aadress.Asula" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Aadress.Maakond" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Aadress.ISO3Riik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Aadress.Ads_adob_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Aadress.Ads_AdrID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Aadress.Ads_oid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Aadress.Ads_Koodaadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Aadress.Aadresstekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="Isik.Elamislubaoigused"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Elamislubaoigus" maxOccurs="unbounded" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Elamislubaoigus.LiikKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="Elamislubaoigus.Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="Elamislubaoigus.KehtivAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                                       &lt;element name="Elamislubaoigus.KehtivKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                                       &lt;element name="Elamislubaoigus.StaatusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="Elamislubaoigus.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="Isik.SeotudIsikud"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="SeotudIsik" maxOccurs="unbounded" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="SeotudIsik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                       &lt;element name="SeotudIsik.Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="SeotudIsik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                       &lt;element name="SeotudIsik.Surmaaeg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                       &lt;element name="SeotudIsik.Registrikood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                       &lt;element name="SeotudIsik.SeoseTyypKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="SeotudIsik.SeoseTyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="SeotudIsik.StaatusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="SeotudIsik.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="SeotudIsik.KehtivAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                                       &lt;element name="SeotudIsik.KehtivKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                                       &lt;element name="SeotudIsik.Kontaktid"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="Kontakt" maxOccurs="unbounded" minOccurs="0"&gt;
 *                                                   &lt;complexType&gt;
 *                                                     &lt;complexContent&gt;
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                                         &lt;sequence&gt;
 *                                                           &lt;element name="Kontakt.TyypKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                           &lt;element name="Kontakt.Tyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                           &lt;element name="Kontakt.Vaartus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                         &lt;/sequence&gt;
 *                                                       &lt;/restriction&gt;
 *                                                     &lt;/complexContent&gt;
 *                                                   &lt;/complexType&gt;
 *                                                 &lt;/element&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                       &lt;element name="SeotudIsik.Hooldusoigused"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="Hooldusoigus" maxOccurs="unbounded" minOccurs="0"&gt;
 *                                                   &lt;complexType&gt;
 *                                                     &lt;complexContent&gt;
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                                         &lt;sequence&gt;
 *                                                           &lt;element name="Hooldusoigus.PohiIsikuRoll" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                           &lt;element name="Hooldusoigus.TyypKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                           &lt;element name="Hooldusoigus.Tyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                           &lt;element name="Hooldusoigus.KehtivAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                                                           &lt;element name="Hooldusoigus.KehtivKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                                                           &lt;element name="Hooldusoigus.StaatusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                           &lt;element name="Hooldusoigus.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                         &lt;/sequence&gt;
 *                                                       &lt;/restriction&gt;
 *                                                     &lt;/complexContent&gt;
 *                                                   &lt;/complexType&gt;
 *                                                 &lt;/element&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR435ResponseType", propOrder = {
    "veakood",
    "veatekst",
    "isik"
})
public class RR435ResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Veakood")
    protected BigInteger veakood;
    @XmlElement(name = "Veatekst")
    protected String veatekst;
    @XmlElement(name = "Isik", required = true)
    protected RR435ResponseType.Isik isik;

    /**
     * Gets the value of the veakood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVeakood() {
        return veakood;
    }

    /**
     * Sets the value of the veakood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVeakood(BigInteger value) {
        this.veakood = value;
    }

    /**
     * Gets the value of the veatekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVeatekst() {
        return veatekst;
    }

    /**
     * Sets the value of the veatekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVeatekst(String value) {
        this.veatekst = value;
    }

    /**
     * Gets the value of the isik property.
     * 
     * @return
     *     possible object is
     *     {@link RR435ResponseType.Isik }
     *     
     */
    public RR435ResponseType.Isik getIsik() {
        return isik;
    }

    /**
     * Sets the value of the isik property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR435ResponseType.Isik }
     *     
     */
    public void setIsik(RR435ResponseType.Isik value) {
        this.isik = value;
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
     *         &lt;element name="Isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Isik.Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
     *         &lt;element name="Isik.TopeltIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Isik.Synniaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
     *         &lt;element name="Isik.Surmaaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *         &lt;element name="Isik.SuguKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Isik.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Isik.KirjeStaatusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Isik.KirjeStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Isik.ArhiiviKandmisePohjus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="Isik.Kodakondsused"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Kodakondsus" maxOccurs="unbounded" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Kodakondsus.OnPohiline" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                             &lt;element name="Kodakondsus.KehtivAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                             &lt;element name="Kodakondsus.KehtivKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                             &lt;element name="Kodakondsus.ISO3Kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                             &lt;element name="Kodakondsus.Nimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="Isik.TeovoimePiirangud"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Teovoime" maxOccurs="unbounded" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Teovoime.LiikKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Teovoime.Liik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                             &lt;element name="Teovoime.Markus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                             &lt;element name="Teovoime.KehtivAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                             &lt;element name="Teovoime.KehtivKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                             &lt;element name="Teovoime.StaatusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Teovoime.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="Isik.Aadress" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Aadress.KehtivAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                   &lt;element name="Aadress.Sihtnumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Aadress.Tanav" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Aadress.Maja" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Aadress.Korter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Aadress.Nimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Aadress.Vkoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Aadress.KOV" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Aadress.Asula" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Aadress.Maakond" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Aadress.ISO3Riik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Aadress.Ads_adob_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Aadress.Ads_AdrID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Aadress.Ads_oid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Aadress.Ads_Koodaadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Aadress.Aadresstekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="Isik.Elamislubaoigused"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Elamislubaoigus" maxOccurs="unbounded" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Elamislubaoigus.LiikKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Elamislubaoigus.Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Elamislubaoigus.KehtivAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                             &lt;element name="Elamislubaoigus.KehtivKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                             &lt;element name="Elamislubaoigus.StaatusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Elamislubaoigus.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="Isik.SeotudIsikud"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="SeotudIsik" maxOccurs="unbounded" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="SeotudIsik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                             &lt;element name="SeotudIsik.Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="SeotudIsik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                             &lt;element name="SeotudIsik.Surmaaeg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                             &lt;element name="SeotudIsik.Registrikood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                             &lt;element name="SeotudIsik.SeoseTyypKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="SeotudIsik.SeoseTyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="SeotudIsik.StaatusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="SeotudIsik.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="SeotudIsik.KehtivAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                             &lt;element name="SeotudIsik.KehtivKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                             &lt;element name="SeotudIsik.Kontaktid"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="Kontakt" maxOccurs="unbounded" minOccurs="0"&gt;
     *                                         &lt;complexType&gt;
     *                                           &lt;complexContent&gt;
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                               &lt;sequence&gt;
     *                                                 &lt;element name="Kontakt.TyypKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                                 &lt;element name="Kontakt.Tyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                                 &lt;element name="Kontakt.Vaartus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                               &lt;/sequence&gt;
     *                                             &lt;/restriction&gt;
     *                                           &lt;/complexContent&gt;
     *                                         &lt;/complexType&gt;
     *                                       &lt;/element&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                             &lt;element name="SeotudIsik.Hooldusoigused"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="Hooldusoigus" maxOccurs="unbounded" minOccurs="0"&gt;
     *                                         &lt;complexType&gt;
     *                                           &lt;complexContent&gt;
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                               &lt;sequence&gt;
     *                                                 &lt;element name="Hooldusoigus.PohiIsikuRoll" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                                 &lt;element name="Hooldusoigus.TyypKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                                 &lt;element name="Hooldusoigus.Tyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                                 &lt;element name="Hooldusoigus.KehtivAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                                                 &lt;element name="Hooldusoigus.KehtivKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                                                 &lt;element name="Hooldusoigus.StaatusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                                 &lt;element name="Hooldusoigus.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                               &lt;/sequence&gt;
     *                                             &lt;/restriction&gt;
     *                                           &lt;/complexContent&gt;
     *                                         &lt;/complexType&gt;
     *                                       &lt;/element&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
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
        "isikEesnimi",
        "isikPerenimi",
        "isikIsikukood",
        "isikTopeltIsikukood",
        "isikSynniaeg",
        "isikSurmaaeg",
        "isikSuguKood",
        "isikSugu",
        "isikKirjeStaatusKood",
        "isikKirjeStaatus",
        "isikArhiiviKandmisePohjus",
        "isikKodakondsused",
        "isikTeovoimePiirangud",
        "isikAadress",
        "isikElamislubaoigused",
        "isikSeotudIsikud"
    })
    public static class Isik {

        @XmlElement(name = "Isik.Eesnimi", required = true)
        protected String isikEesnimi;
        @XmlElement(name = "Isik.Perenimi", required = true)
        protected String isikPerenimi;
        @XmlElement(name = "Isik.Isikukood", required = true)
        protected String isikIsikukood;
        @XmlElement(name = "Isik.TopeltIsikukood", required = true)
        protected String isikTopeltIsikukood;
        @XmlElement(name = "Isik.Synniaeg", required = true)
        protected String isikSynniaeg;
        @XmlElement(name = "Isik.Surmaaeg")
        protected String isikSurmaaeg;
        @XmlElement(name = "Isik.SuguKood", required = true)
        protected String isikSuguKood;
        @XmlElement(name = "Isik.Sugu", required = true)
        protected String isikSugu;
        @XmlElement(name = "Isik.KirjeStaatusKood", required = true)
        protected String isikKirjeStaatusKood;
        @XmlElement(name = "Isik.KirjeStaatus", required = true)
        protected String isikKirjeStaatus;
        @XmlElement(name = "Isik.ArhiiviKandmisePohjus")
        protected String isikArhiiviKandmisePohjus;
        @XmlElement(name = "Isik.Kodakondsused", required = true)
        protected RR435ResponseType.Isik.IsikKodakondsused isikKodakondsused;
        @XmlElement(name = "Isik.TeovoimePiirangud", required = true)
        protected RR435ResponseType.Isik.IsikTeovoimePiirangud isikTeovoimePiirangud;
        @XmlElement(name = "Isik.Aadress")
        protected RR435ResponseType.Isik.IsikAadress isikAadress;
        @XmlElement(name = "Isik.Elamislubaoigused", required = true)
        protected RR435ResponseType.Isik.IsikElamislubaoigused isikElamislubaoigused;
        @XmlElement(name = "Isik.SeotudIsikud", required = true)
        protected RR435ResponseType.Isik.IsikSeotudIsikud isikSeotudIsikud;

        /**
         * Gets the value of the isikEesnimi property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIsikEesnimi() {
            return isikEesnimi;
        }

        /**
         * Sets the value of the isikEesnimi property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsikEesnimi(String value) {
            this.isikEesnimi = value;
        }

        /**
         * Gets the value of the isikPerenimi property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIsikPerenimi() {
            return isikPerenimi;
        }

        /**
         * Sets the value of the isikPerenimi property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsikPerenimi(String value) {
            this.isikPerenimi = value;
        }

        /**
         * Gets the value of the isikIsikukood property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIsikIsikukood() {
            return isikIsikukood;
        }

        /**
         * Sets the value of the isikIsikukood property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsikIsikukood(String value) {
            this.isikIsikukood = value;
        }

        /**
         * Gets the value of the isikTopeltIsikukood property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIsikTopeltIsikukood() {
            return isikTopeltIsikukood;
        }

        /**
         * Sets the value of the isikTopeltIsikukood property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsikTopeltIsikukood(String value) {
            this.isikTopeltIsikukood = value;
        }

        /**
         * Gets the value of the isikSynniaeg property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIsikSynniaeg() {
            return isikSynniaeg;
        }

        /**
         * Sets the value of the isikSynniaeg property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsikSynniaeg(String value) {
            this.isikSynniaeg = value;
        }

        /**
         * Gets the value of the isikSurmaaeg property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIsikSurmaaeg() {
            return isikSurmaaeg;
        }

        /**
         * Sets the value of the isikSurmaaeg property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsikSurmaaeg(String value) {
            this.isikSurmaaeg = value;
        }

        /**
         * Gets the value of the isikSuguKood property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIsikSuguKood() {
            return isikSuguKood;
        }

        /**
         * Sets the value of the isikSuguKood property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsikSuguKood(String value) {
            this.isikSuguKood = value;
        }

        /**
         * Gets the value of the isikSugu property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIsikSugu() {
            return isikSugu;
        }

        /**
         * Sets the value of the isikSugu property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsikSugu(String value) {
            this.isikSugu = value;
        }

        /**
         * Gets the value of the isikKirjeStaatusKood property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIsikKirjeStaatusKood() {
            return isikKirjeStaatusKood;
        }

        /**
         * Sets the value of the isikKirjeStaatusKood property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsikKirjeStaatusKood(String value) {
            this.isikKirjeStaatusKood = value;
        }

        /**
         * Gets the value of the isikKirjeStaatus property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIsikKirjeStaatus() {
            return isikKirjeStaatus;
        }

        /**
         * Sets the value of the isikKirjeStaatus property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsikKirjeStaatus(String value) {
            this.isikKirjeStaatus = value;
        }

        /**
         * Gets the value of the isikArhiiviKandmisePohjus property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIsikArhiiviKandmisePohjus() {
            return isikArhiiviKandmisePohjus;
        }

        /**
         * Sets the value of the isikArhiiviKandmisePohjus property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsikArhiiviKandmisePohjus(String value) {
            this.isikArhiiviKandmisePohjus = value;
        }

        /**
         * Gets the value of the isikKodakondsused property.
         * 
         * @return
         *     possible object is
         *     {@link RR435ResponseType.Isik.IsikKodakondsused }
         *     
         */
        public RR435ResponseType.Isik.IsikKodakondsused getIsikKodakondsused() {
            return isikKodakondsused;
        }

        /**
         * Sets the value of the isikKodakondsused property.
         * 
         * @param value
         *     allowed object is
         *     {@link RR435ResponseType.Isik.IsikKodakondsused }
         *     
         */
        public void setIsikKodakondsused(RR435ResponseType.Isik.IsikKodakondsused value) {
            this.isikKodakondsused = value;
        }

        /**
         * Gets the value of the isikTeovoimePiirangud property.
         * 
         * @return
         *     possible object is
         *     {@link RR435ResponseType.Isik.IsikTeovoimePiirangud }
         *     
         */
        public RR435ResponseType.Isik.IsikTeovoimePiirangud getIsikTeovoimePiirangud() {
            return isikTeovoimePiirangud;
        }

        /**
         * Sets the value of the isikTeovoimePiirangud property.
         * 
         * @param value
         *     allowed object is
         *     {@link RR435ResponseType.Isik.IsikTeovoimePiirangud }
         *     
         */
        public void setIsikTeovoimePiirangud(RR435ResponseType.Isik.IsikTeovoimePiirangud value) {
            this.isikTeovoimePiirangud = value;
        }

        /**
         * Gets the value of the isikAadress property.
         * 
         * @return
         *     possible object is
         *     {@link RR435ResponseType.Isik.IsikAadress }
         *     
         */
        public RR435ResponseType.Isik.IsikAadress getIsikAadress() {
            return isikAadress;
        }

        /**
         * Sets the value of the isikAadress property.
         * 
         * @param value
         *     allowed object is
         *     {@link RR435ResponseType.Isik.IsikAadress }
         *     
         */
        public void setIsikAadress(RR435ResponseType.Isik.IsikAadress value) {
            this.isikAadress = value;
        }

        /**
         * Gets the value of the isikElamislubaoigused property.
         * 
         * @return
         *     possible object is
         *     {@link RR435ResponseType.Isik.IsikElamislubaoigused }
         *     
         */
        public RR435ResponseType.Isik.IsikElamislubaoigused getIsikElamislubaoigused() {
            return isikElamislubaoigused;
        }

        /**
         * Sets the value of the isikElamislubaoigused property.
         * 
         * @param value
         *     allowed object is
         *     {@link RR435ResponseType.Isik.IsikElamislubaoigused }
         *     
         */
        public void setIsikElamislubaoigused(RR435ResponseType.Isik.IsikElamislubaoigused value) {
            this.isikElamislubaoigused = value;
        }

        /**
         * Gets the value of the isikSeotudIsikud property.
         * 
         * @return
         *     possible object is
         *     {@link RR435ResponseType.Isik.IsikSeotudIsikud }
         *     
         */
        public RR435ResponseType.Isik.IsikSeotudIsikud getIsikSeotudIsikud() {
            return isikSeotudIsikud;
        }

        /**
         * Sets the value of the isikSeotudIsikud property.
         * 
         * @param value
         *     allowed object is
         *     {@link RR435ResponseType.Isik.IsikSeotudIsikud }
         *     
         */
        public void setIsikSeotudIsikud(RR435ResponseType.Isik.IsikSeotudIsikud value) {
            this.isikSeotudIsikud = value;
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
         *         &lt;element name="Aadress.KehtivAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *         &lt;element name="Aadress.Sihtnumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Aadress.Tanav" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Aadress.Maja" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Aadress.Korter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Aadress.Nimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Aadress.Vkoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Aadress.KOV" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Aadress.Asula" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Aadress.Maakond" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Aadress.ISO3Riik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Aadress.Ads_adob_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Aadress.Ads_AdrID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Aadress.Ads_oid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Aadress.Ads_Koodaadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Aadress.Aadresstekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
            "aadressKehtivAlates",
            "aadressSihtnumber",
            "aadressTanav",
            "aadressMaja",
            "aadressKorter",
            "aadressNimi",
            "aadressVkoht",
            "aadressKOV",
            "aadressAsula",
            "aadressMaakond",
            "aadressISO3Riik",
            "aadressAdsAdobId",
            "aadressAdsAdrID",
            "aadressAdsOid",
            "aadressAdsKoodaadress",
            "aadressAadresstekst"
        })
        public static class IsikAadress {

            @XmlElement(name = "Aadress.KehtivAlates")
            protected String aadressKehtivAlates;
            @XmlElement(name = "Aadress.Sihtnumber")
            protected String aadressSihtnumber;
            @XmlElement(name = "Aadress.Tanav")
            protected String aadressTanav;
            @XmlElement(name = "Aadress.Maja")
            protected String aadressMaja;
            @XmlElement(name = "Aadress.Korter")
            protected String aadressKorter;
            @XmlElement(name = "Aadress.Nimi")
            protected String aadressNimi;
            @XmlElement(name = "Aadress.Vkoht")
            protected String aadressVkoht;
            @XmlElement(name = "Aadress.KOV")
            protected String aadressKOV;
            @XmlElement(name = "Aadress.Asula")
            protected String aadressAsula;
            @XmlElement(name = "Aadress.Maakond")
            protected String aadressMaakond;
            @XmlElement(name = "Aadress.ISO3Riik")
            protected String aadressISO3Riik;
            @XmlElement(name = "Aadress.Ads_adob_id")
            protected String aadressAdsAdobId;
            @XmlElement(name = "Aadress.Ads_AdrID")
            protected String aadressAdsAdrID;
            @XmlElement(name = "Aadress.Ads_oid")
            protected String aadressAdsOid;
            @XmlElement(name = "Aadress.Ads_Koodaadress")
            protected String aadressAdsKoodaadress;
            @XmlElement(name = "Aadress.Aadresstekst")
            protected String aadressAadresstekst;

            /**
             * Gets the value of the aadressKehtivAlates property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressKehtivAlates() {
                return aadressKehtivAlates;
            }

            /**
             * Sets the value of the aadressKehtivAlates property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressKehtivAlates(String value) {
                this.aadressKehtivAlates = value;
            }

            /**
             * Gets the value of the aadressSihtnumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressSihtnumber() {
                return aadressSihtnumber;
            }

            /**
             * Sets the value of the aadressSihtnumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressSihtnumber(String value) {
                this.aadressSihtnumber = value;
            }

            /**
             * Gets the value of the aadressTanav property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressTanav() {
                return aadressTanav;
            }

            /**
             * Sets the value of the aadressTanav property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressTanav(String value) {
                this.aadressTanav = value;
            }

            /**
             * Gets the value of the aadressMaja property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressMaja() {
                return aadressMaja;
            }

            /**
             * Sets the value of the aadressMaja property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressMaja(String value) {
                this.aadressMaja = value;
            }

            /**
             * Gets the value of the aadressKorter property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressKorter() {
                return aadressKorter;
            }

            /**
             * Sets the value of the aadressKorter property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressKorter(String value) {
                this.aadressKorter = value;
            }

            /**
             * Gets the value of the aadressNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressNimi() {
                return aadressNimi;
            }

            /**
             * Sets the value of the aadressNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressNimi(String value) {
                this.aadressNimi = value;
            }

            /**
             * Gets the value of the aadressVkoht property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressVkoht() {
                return aadressVkoht;
            }

            /**
             * Sets the value of the aadressVkoht property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressVkoht(String value) {
                this.aadressVkoht = value;
            }

            /**
             * Gets the value of the aadressKOV property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressKOV() {
                return aadressKOV;
            }

            /**
             * Sets the value of the aadressKOV property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressKOV(String value) {
                this.aadressKOV = value;
            }

            /**
             * Gets the value of the aadressAsula property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressAsula() {
                return aadressAsula;
            }

            /**
             * Sets the value of the aadressAsula property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressAsula(String value) {
                this.aadressAsula = value;
            }

            /**
             * Gets the value of the aadressMaakond property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressMaakond() {
                return aadressMaakond;
            }

            /**
             * Sets the value of the aadressMaakond property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressMaakond(String value) {
                this.aadressMaakond = value;
            }

            /**
             * Gets the value of the aadressISO3Riik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressISO3Riik() {
                return aadressISO3Riik;
            }

            /**
             * Sets the value of the aadressISO3Riik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressISO3Riik(String value) {
                this.aadressISO3Riik = value;
            }

            /**
             * Gets the value of the aadressAdsAdobId property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressAdsAdobId() {
                return aadressAdsAdobId;
            }

            /**
             * Sets the value of the aadressAdsAdobId property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressAdsAdobId(String value) {
                this.aadressAdsAdobId = value;
            }

            /**
             * Gets the value of the aadressAdsAdrID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressAdsAdrID() {
                return aadressAdsAdrID;
            }

            /**
             * Sets the value of the aadressAdsAdrID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressAdsAdrID(String value) {
                this.aadressAdsAdrID = value;
            }

            /**
             * Gets the value of the aadressAdsOid property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressAdsOid() {
                return aadressAdsOid;
            }

            /**
             * Sets the value of the aadressAdsOid property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressAdsOid(String value) {
                this.aadressAdsOid = value;
            }

            /**
             * Gets the value of the aadressAdsKoodaadress property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressAdsKoodaadress() {
                return aadressAdsKoodaadress;
            }

            /**
             * Sets the value of the aadressAdsKoodaadress property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressAdsKoodaadress(String value) {
                this.aadressAdsKoodaadress = value;
            }

            /**
             * Gets the value of the aadressAadresstekst property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressAadresstekst() {
                return aadressAadresstekst;
            }

            /**
             * Sets the value of the aadressAadresstekst property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressAadresstekst(String value) {
                this.aadressAadresstekst = value;
            }

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
         *         &lt;element name="Elamislubaoigus" maxOccurs="unbounded" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Elamislubaoigus.LiikKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Elamislubaoigus.Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Elamislubaoigus.KehtivAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *                   &lt;element name="Elamislubaoigus.KehtivKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *                   &lt;element name="Elamislubaoigus.StaatusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Elamislubaoigus.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
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
            "elamislubaoigus"
        })
        public static class IsikElamislubaoigused {

            @XmlElement(name = "Elamislubaoigus")
            protected List<RR435ResponseType.Isik.IsikElamislubaoigused.Elamislubaoigus> elamislubaoigus;

            /**
             * Gets the value of the elamislubaoigus property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the elamislubaoigus property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getElamislubaoigus().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link RR435ResponseType.Isik.IsikElamislubaoigused.Elamislubaoigus }
             * 
             * 
             */
            public List<RR435ResponseType.Isik.IsikElamislubaoigused.Elamislubaoigus> getElamislubaoigus() {
                if (elamislubaoigus == null) {
                    elamislubaoigus = new ArrayList<RR435ResponseType.Isik.IsikElamislubaoigused.Elamislubaoigus>();
                }
                return this.elamislubaoigus;
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
             *         &lt;element name="Elamislubaoigus.LiikKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Elamislubaoigus.Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Elamislubaoigus.KehtivAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
             *         &lt;element name="Elamislubaoigus.KehtivKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
             *         &lt;element name="Elamislubaoigus.StaatusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Elamislubaoigus.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                "elamislubaoigusLiikKood",
                "elamislubaoigusLiik",
                "elamislubaoigusKehtivAlates",
                "elamislubaoigusKehtivKuni",
                "elamislubaoigusStaatusKood",
                "elamislubaoigusStaatus"
            })
            public static class Elamislubaoigus {

                @XmlElement(name = "Elamislubaoigus.LiikKood", required = true)
                protected String elamislubaoigusLiikKood;
                @XmlElement(name = "Elamislubaoigus.Liik", required = true)
                protected String elamislubaoigusLiik;
                @XmlElement(name = "Elamislubaoigus.KehtivAlates")
                protected String elamislubaoigusKehtivAlates;
                @XmlElement(name = "Elamislubaoigus.KehtivKuni")
                protected String elamislubaoigusKehtivKuni;
                @XmlElement(name = "Elamislubaoigus.StaatusKood", required = true)
                protected String elamislubaoigusStaatusKood;
                @XmlElement(name = "Elamislubaoigus.Staatus", required = true)
                protected String elamislubaoigusStaatus;

                /**
                 * Gets the value of the elamislubaoigusLiikKood property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getElamislubaoigusLiikKood() {
                    return elamislubaoigusLiikKood;
                }

                /**
                 * Sets the value of the elamislubaoigusLiikKood property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setElamislubaoigusLiikKood(String value) {
                    this.elamislubaoigusLiikKood = value;
                }

                /**
                 * Gets the value of the elamislubaoigusLiik property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getElamislubaoigusLiik() {
                    return elamislubaoigusLiik;
                }

                /**
                 * Sets the value of the elamislubaoigusLiik property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setElamislubaoigusLiik(String value) {
                    this.elamislubaoigusLiik = value;
                }

                /**
                 * Gets the value of the elamislubaoigusKehtivAlates property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getElamislubaoigusKehtivAlates() {
                    return elamislubaoigusKehtivAlates;
                }

                /**
                 * Sets the value of the elamislubaoigusKehtivAlates property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setElamislubaoigusKehtivAlates(String value) {
                    this.elamislubaoigusKehtivAlates = value;
                }

                /**
                 * Gets the value of the elamislubaoigusKehtivKuni property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getElamislubaoigusKehtivKuni() {
                    return elamislubaoigusKehtivKuni;
                }

                /**
                 * Sets the value of the elamislubaoigusKehtivKuni property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setElamislubaoigusKehtivKuni(String value) {
                    this.elamislubaoigusKehtivKuni = value;
                }

                /**
                 * Gets the value of the elamislubaoigusStaatusKood property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getElamislubaoigusStaatusKood() {
                    return elamislubaoigusStaatusKood;
                }

                /**
                 * Sets the value of the elamislubaoigusStaatusKood property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setElamislubaoigusStaatusKood(String value) {
                    this.elamislubaoigusStaatusKood = value;
                }

                /**
                 * Gets the value of the elamislubaoigusStaatus property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getElamislubaoigusStaatus() {
                    return elamislubaoigusStaatus;
                }

                /**
                 * Sets the value of the elamislubaoigusStaatus property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setElamislubaoigusStaatus(String value) {
                    this.elamislubaoigusStaatus = value;
                }

            }

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
         *         &lt;element name="Kodakondsus" maxOccurs="unbounded" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Kodakondsus.OnPohiline" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                   &lt;element name="Kodakondsus.KehtivAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *                   &lt;element name="Kodakondsus.KehtivKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *                   &lt;element name="Kodakondsus.ISO3Kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                   &lt;element name="Kodakondsus.Nimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
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
            "kodakondsus"
        })
        public static class IsikKodakondsused {

            @XmlElement(name = "Kodakondsus")
            protected List<RR435ResponseType.Isik.IsikKodakondsused.Kodakondsus> kodakondsus;

            /**
             * Gets the value of the kodakondsus property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the kodakondsus property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getKodakondsus().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link RR435ResponseType.Isik.IsikKodakondsused.Kodakondsus }
             * 
             * 
             */
            public List<RR435ResponseType.Isik.IsikKodakondsused.Kodakondsus> getKodakondsus() {
                if (kodakondsus == null) {
                    kodakondsus = new ArrayList<RR435ResponseType.Isik.IsikKodakondsused.Kodakondsus>();
                }
                return this.kodakondsus;
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
             *         &lt;element name="Kodakondsus.OnPohiline" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *         &lt;element name="Kodakondsus.KehtivAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
             *         &lt;element name="Kodakondsus.KehtivKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
             *         &lt;element name="Kodakondsus.ISO3Kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *         &lt;element name="Kodakondsus.Nimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
                "kodakondsusOnPohiline",
                "kodakondsusKehtivAlates",
                "kodakondsusKehtivKuni",
                "kodakondsusISO3Kood",
                "kodakondsusNimetus"
            })
            public static class Kodakondsus {

                @XmlElement(name = "Kodakondsus.OnPohiline")
                protected String kodakondsusOnPohiline;
                @XmlElement(name = "Kodakondsus.KehtivAlates")
                protected String kodakondsusKehtivAlates;
                @XmlElement(name = "Kodakondsus.KehtivKuni")
                protected String kodakondsusKehtivKuni;
                @XmlElement(name = "Kodakondsus.ISO3Kood")
                protected String kodakondsusISO3Kood;
                @XmlElement(name = "Kodakondsus.Nimetus")
                protected String kodakondsusNimetus;

                /**
                 * Gets the value of the kodakondsusOnPohiline property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKodakondsusOnPohiline() {
                    return kodakondsusOnPohiline;
                }

                /**
                 * Sets the value of the kodakondsusOnPohiline property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKodakondsusOnPohiline(String value) {
                    this.kodakondsusOnPohiline = value;
                }

                /**
                 * Gets the value of the kodakondsusKehtivAlates property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKodakondsusKehtivAlates() {
                    return kodakondsusKehtivAlates;
                }

                /**
                 * Sets the value of the kodakondsusKehtivAlates property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKodakondsusKehtivAlates(String value) {
                    this.kodakondsusKehtivAlates = value;
                }

                /**
                 * Gets the value of the kodakondsusKehtivKuni property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKodakondsusKehtivKuni() {
                    return kodakondsusKehtivKuni;
                }

                /**
                 * Sets the value of the kodakondsusKehtivKuni property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKodakondsusKehtivKuni(String value) {
                    this.kodakondsusKehtivKuni = value;
                }

                /**
                 * Gets the value of the kodakondsusISO3Kood property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKodakondsusISO3Kood() {
                    return kodakondsusISO3Kood;
                }

                /**
                 * Sets the value of the kodakondsusISO3Kood property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKodakondsusISO3Kood(String value) {
                    this.kodakondsusISO3Kood = value;
                }

                /**
                 * Gets the value of the kodakondsusNimetus property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKodakondsusNimetus() {
                    return kodakondsusNimetus;
                }

                /**
                 * Sets the value of the kodakondsusNimetus property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKodakondsusNimetus(String value) {
                    this.kodakondsusNimetus = value;
                }

            }

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
         *         &lt;element name="SeotudIsik" maxOccurs="unbounded" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="SeotudIsik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                   &lt;element name="SeotudIsik.Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="SeotudIsik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                   &lt;element name="SeotudIsik.Surmaaeg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                   &lt;element name="SeotudIsik.Registrikood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                   &lt;element name="SeotudIsik.SeoseTyypKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="SeotudIsik.SeoseTyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="SeotudIsik.StaatusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="SeotudIsik.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="SeotudIsik.KehtivAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *                   &lt;element name="SeotudIsik.KehtivKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *                   &lt;element name="SeotudIsik.Kontaktid"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="Kontakt" maxOccurs="unbounded" minOccurs="0"&gt;
         *                               &lt;complexType&gt;
         *                                 &lt;complexContent&gt;
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                                     &lt;sequence&gt;
         *                                       &lt;element name="Kontakt.TyypKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                                       &lt;element name="Kontakt.Tyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                                       &lt;element name="Kontakt.Vaartus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                                     &lt;/sequence&gt;
         *                                   &lt;/restriction&gt;
         *                                 &lt;/complexContent&gt;
         *                               &lt;/complexType&gt;
         *                             &lt;/element&gt;
         *                           &lt;/sequence&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                   &lt;element name="SeotudIsik.Hooldusoigused"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="Hooldusoigus" maxOccurs="unbounded" minOccurs="0"&gt;
         *                               &lt;complexType&gt;
         *                                 &lt;complexContent&gt;
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                                     &lt;sequence&gt;
         *                                       &lt;element name="Hooldusoigus.PohiIsikuRoll" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                                       &lt;element name="Hooldusoigus.TyypKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                                       &lt;element name="Hooldusoigus.Tyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                                       &lt;element name="Hooldusoigus.KehtivAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *                                       &lt;element name="Hooldusoigus.KehtivKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *                                       &lt;element name="Hooldusoigus.StaatusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                                       &lt;element name="Hooldusoigus.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                                     &lt;/sequence&gt;
         *                                   &lt;/restriction&gt;
         *                                 &lt;/complexContent&gt;
         *                               &lt;/complexType&gt;
         *                             &lt;/element&gt;
         *                           &lt;/sequence&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
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
            "seotudIsik"
        })
        public static class IsikSeotudIsikud {

            @XmlElement(name = "SeotudIsik")
            protected List<RR435ResponseType.Isik.IsikSeotudIsikud.SeotudIsik> seotudIsik;

            /**
             * Gets the value of the seotudIsik property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the seotudIsik property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getSeotudIsik().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link RR435ResponseType.Isik.IsikSeotudIsikud.SeotudIsik }
             * 
             * 
             */
            public List<RR435ResponseType.Isik.IsikSeotudIsikud.SeotudIsik> getSeotudIsik() {
                if (seotudIsik == null) {
                    seotudIsik = new ArrayList<RR435ResponseType.Isik.IsikSeotudIsikud.SeotudIsik>();
                }
                return this.seotudIsik;
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
             *         &lt;element name="SeotudIsik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *         &lt;element name="SeotudIsik.Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="SeotudIsik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *         &lt;element name="SeotudIsik.Surmaaeg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *         &lt;element name="SeotudIsik.Registrikood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *         &lt;element name="SeotudIsik.SeoseTyypKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="SeotudIsik.SeoseTyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="SeotudIsik.StaatusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="SeotudIsik.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="SeotudIsik.KehtivAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
             *         &lt;element name="SeotudIsik.KehtivKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
             *         &lt;element name="SeotudIsik.Kontaktid"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="Kontakt" maxOccurs="unbounded" minOccurs="0"&gt;
             *                     &lt;complexType&gt;
             *                       &lt;complexContent&gt;
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                           &lt;sequence&gt;
             *                             &lt;element name="Kontakt.TyypKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                             &lt;element name="Kontakt.Tyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                             &lt;element name="Kontakt.Vaartus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                           &lt;/sequence&gt;
             *                         &lt;/restriction&gt;
             *                       &lt;/complexContent&gt;
             *                     &lt;/complexType&gt;
             *                   &lt;/element&gt;
             *                 &lt;/sequence&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *         &lt;element name="SeotudIsik.Hooldusoigused"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="Hooldusoigus" maxOccurs="unbounded" minOccurs="0"&gt;
             *                     &lt;complexType&gt;
             *                       &lt;complexContent&gt;
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                           &lt;sequence&gt;
             *                             &lt;element name="Hooldusoigus.PohiIsikuRoll" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                             &lt;element name="Hooldusoigus.TyypKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                             &lt;element name="Hooldusoigus.Tyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                             &lt;element name="Hooldusoigus.KehtivAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
             *                             &lt;element name="Hooldusoigus.KehtivKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
             *                             &lt;element name="Hooldusoigus.StaatusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                             &lt;element name="Hooldusoigus.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                           &lt;/sequence&gt;
             *                         &lt;/restriction&gt;
             *                       &lt;/complexContent&gt;
             *                     &lt;/complexType&gt;
             *                   &lt;/element&gt;
             *                 &lt;/sequence&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
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
                "seotudIsikEesnimi",
                "seotudIsikNimi",
                "seotudIsikIsikukood",
                "seotudIsikSurmaaeg",
                "seotudIsikRegistrikood",
                "seotudIsikSeoseTyypKood",
                "seotudIsikSeoseTyyp",
                "seotudIsikStaatusKood",
                "seotudIsikStaatus",
                "seotudIsikKehtivAlates",
                "seotudIsikKehtivKuni",
                "seotudIsikKontaktid",
                "seotudIsikHooldusoigused"
            })
            public static class SeotudIsik {

                @XmlElement(name = "SeotudIsik.Eesnimi")
                protected String seotudIsikEesnimi;
                @XmlElement(name = "SeotudIsik.Nimi", required = true)
                protected String seotudIsikNimi;
                @XmlElement(name = "SeotudIsik.Isikukood")
                protected String seotudIsikIsikukood;
                @XmlElement(name = "SeotudIsik.Surmaaeg")
                protected String seotudIsikSurmaaeg;
                @XmlElement(name = "SeotudIsik.Registrikood")
                protected String seotudIsikRegistrikood;
                @XmlElement(name = "SeotudIsik.SeoseTyypKood", required = true)
                protected String seotudIsikSeoseTyypKood;
                @XmlElement(name = "SeotudIsik.SeoseTyyp", required = true)
                protected String seotudIsikSeoseTyyp;
                @XmlElement(name = "SeotudIsik.StaatusKood", required = true)
                protected String seotudIsikStaatusKood;
                @XmlElement(name = "SeotudIsik.Staatus", required = true)
                protected String seotudIsikStaatus;
                @XmlElement(name = "SeotudIsik.KehtivAlates")
                protected String seotudIsikKehtivAlates;
                @XmlElement(name = "SeotudIsik.KehtivKuni")
                protected String seotudIsikKehtivKuni;
                @XmlElement(name = "SeotudIsik.Kontaktid", required = true)
                protected RR435ResponseType.Isik.IsikSeotudIsikud.SeotudIsik.SeotudIsikKontaktid seotudIsikKontaktid;
                @XmlElement(name = "SeotudIsik.Hooldusoigused", required = true)
                protected RR435ResponseType.Isik.IsikSeotudIsikud.SeotudIsik.SeotudIsikHooldusoigused seotudIsikHooldusoigused;

                /**
                 * Gets the value of the seotudIsikEesnimi property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSeotudIsikEesnimi() {
                    return seotudIsikEesnimi;
                }

                /**
                 * Sets the value of the seotudIsikEesnimi property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSeotudIsikEesnimi(String value) {
                    this.seotudIsikEesnimi = value;
                }

                /**
                 * Gets the value of the seotudIsikNimi property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSeotudIsikNimi() {
                    return seotudIsikNimi;
                }

                /**
                 * Sets the value of the seotudIsikNimi property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSeotudIsikNimi(String value) {
                    this.seotudIsikNimi = value;
                }

                /**
                 * Gets the value of the seotudIsikIsikukood property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSeotudIsikIsikukood() {
                    return seotudIsikIsikukood;
                }

                /**
                 * Sets the value of the seotudIsikIsikukood property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSeotudIsikIsikukood(String value) {
                    this.seotudIsikIsikukood = value;
                }

                /**
                 * Gets the value of the seotudIsikSurmaaeg property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSeotudIsikSurmaaeg() {
                    return seotudIsikSurmaaeg;
                }

                /**
                 * Sets the value of the seotudIsikSurmaaeg property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSeotudIsikSurmaaeg(String value) {
                    this.seotudIsikSurmaaeg = value;
                }

                /**
                 * Gets the value of the seotudIsikRegistrikood property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSeotudIsikRegistrikood() {
                    return seotudIsikRegistrikood;
                }

                /**
                 * Sets the value of the seotudIsikRegistrikood property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSeotudIsikRegistrikood(String value) {
                    this.seotudIsikRegistrikood = value;
                }

                /**
                 * Gets the value of the seotudIsikSeoseTyypKood property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSeotudIsikSeoseTyypKood() {
                    return seotudIsikSeoseTyypKood;
                }

                /**
                 * Sets the value of the seotudIsikSeoseTyypKood property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSeotudIsikSeoseTyypKood(String value) {
                    this.seotudIsikSeoseTyypKood = value;
                }

                /**
                 * Gets the value of the seotudIsikSeoseTyyp property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSeotudIsikSeoseTyyp() {
                    return seotudIsikSeoseTyyp;
                }

                /**
                 * Sets the value of the seotudIsikSeoseTyyp property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSeotudIsikSeoseTyyp(String value) {
                    this.seotudIsikSeoseTyyp = value;
                }

                /**
                 * Gets the value of the seotudIsikStaatusKood property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSeotudIsikStaatusKood() {
                    return seotudIsikStaatusKood;
                }

                /**
                 * Sets the value of the seotudIsikStaatusKood property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSeotudIsikStaatusKood(String value) {
                    this.seotudIsikStaatusKood = value;
                }

                /**
                 * Gets the value of the seotudIsikStaatus property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSeotudIsikStaatus() {
                    return seotudIsikStaatus;
                }

                /**
                 * Sets the value of the seotudIsikStaatus property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSeotudIsikStaatus(String value) {
                    this.seotudIsikStaatus = value;
                }

                /**
                 * Gets the value of the seotudIsikKehtivAlates property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSeotudIsikKehtivAlates() {
                    return seotudIsikKehtivAlates;
                }

                /**
                 * Sets the value of the seotudIsikKehtivAlates property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSeotudIsikKehtivAlates(String value) {
                    this.seotudIsikKehtivAlates = value;
                }

                /**
                 * Gets the value of the seotudIsikKehtivKuni property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSeotudIsikKehtivKuni() {
                    return seotudIsikKehtivKuni;
                }

                /**
                 * Sets the value of the seotudIsikKehtivKuni property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSeotudIsikKehtivKuni(String value) {
                    this.seotudIsikKehtivKuni = value;
                }

                /**
                 * Gets the value of the seotudIsikKontaktid property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RR435ResponseType.Isik.IsikSeotudIsikud.SeotudIsik.SeotudIsikKontaktid }
                 *     
                 */
                public RR435ResponseType.Isik.IsikSeotudIsikud.SeotudIsik.SeotudIsikKontaktid getSeotudIsikKontaktid() {
                    return seotudIsikKontaktid;
                }

                /**
                 * Sets the value of the seotudIsikKontaktid property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RR435ResponseType.Isik.IsikSeotudIsikud.SeotudIsik.SeotudIsikKontaktid }
                 *     
                 */
                public void setSeotudIsikKontaktid(RR435ResponseType.Isik.IsikSeotudIsikud.SeotudIsik.SeotudIsikKontaktid value) {
                    this.seotudIsikKontaktid = value;
                }

                /**
                 * Gets the value of the seotudIsikHooldusoigused property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RR435ResponseType.Isik.IsikSeotudIsikud.SeotudIsik.SeotudIsikHooldusoigused }
                 *     
                 */
                public RR435ResponseType.Isik.IsikSeotudIsikud.SeotudIsik.SeotudIsikHooldusoigused getSeotudIsikHooldusoigused() {
                    return seotudIsikHooldusoigused;
                }

                /**
                 * Sets the value of the seotudIsikHooldusoigused property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RR435ResponseType.Isik.IsikSeotudIsikud.SeotudIsik.SeotudIsikHooldusoigused }
                 *     
                 */
                public void setSeotudIsikHooldusoigused(RR435ResponseType.Isik.IsikSeotudIsikud.SeotudIsik.SeotudIsikHooldusoigused value) {
                    this.seotudIsikHooldusoigused = value;
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
                 *         &lt;element name="Hooldusoigus" maxOccurs="unbounded" minOccurs="0"&gt;
                 *           &lt;complexType&gt;
                 *             &lt;complexContent&gt;
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *                 &lt;sequence&gt;
                 *                   &lt;element name="Hooldusoigus.PohiIsikuRoll" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *                   &lt;element name="Hooldusoigus.TyypKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *                   &lt;element name="Hooldusoigus.Tyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *                   &lt;element name="Hooldusoigus.KehtivAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
                 *                   &lt;element name="Hooldusoigus.KehtivKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
                 *                   &lt;element name="Hooldusoigus.StaatusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *                   &lt;element name="Hooldusoigus.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *                 &lt;/sequence&gt;
                 *               &lt;/restriction&gt;
                 *             &lt;/complexContent&gt;
                 *           &lt;/complexType&gt;
                 *         &lt;/element&gt;
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
                    "hooldusoigus"
                })
                public static class SeotudIsikHooldusoigused {

                    @XmlElement(name = "Hooldusoigus")
                    protected List<RR435ResponseType.Isik.IsikSeotudIsikud.SeotudIsik.SeotudIsikHooldusoigused.Hooldusoigus> hooldusoigus;

                    /**
                     * Gets the value of the hooldusoigus property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the hooldusoigus property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getHooldusoigus().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link RR435ResponseType.Isik.IsikSeotudIsikud.SeotudIsik.SeotudIsikHooldusoigused.Hooldusoigus }
                     * 
                     * 
                     */
                    public List<RR435ResponseType.Isik.IsikSeotudIsikud.SeotudIsik.SeotudIsikHooldusoigused.Hooldusoigus> getHooldusoigus() {
                        if (hooldusoigus == null) {
                            hooldusoigus = new ArrayList<RR435ResponseType.Isik.IsikSeotudIsikud.SeotudIsik.SeotudIsikHooldusoigused.Hooldusoigus>();
                        }
                        return this.hooldusoigus;
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
                     *         &lt;element name="Hooldusoigus.PohiIsikuRoll" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                     *         &lt;element name="Hooldusoigus.TyypKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                     *         &lt;element name="Hooldusoigus.Tyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                     *         &lt;element name="Hooldusoigus.KehtivAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
                     *         &lt;element name="Hooldusoigus.KehtivKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
                     *         &lt;element name="Hooldusoigus.StaatusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                     *         &lt;element name="Hooldusoigus.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                        "hooldusoigusPohiIsikuRoll",
                        "hooldusoigusTyypKood",
                        "hooldusoigusTyyp",
                        "hooldusoigusKehtivAlates",
                        "hooldusoigusKehtivKuni",
                        "hooldusoigusStaatusKood",
                        "hooldusoigusStaatus"
                    })
                    public static class Hooldusoigus {

                        @XmlElement(name = "Hooldusoigus.PohiIsikuRoll", required = true)
                        protected String hooldusoigusPohiIsikuRoll;
                        @XmlElement(name = "Hooldusoigus.TyypKood", required = true)
                        protected String hooldusoigusTyypKood;
                        @XmlElement(name = "Hooldusoigus.Tyyp", required = true)
                        protected String hooldusoigusTyyp;
                        @XmlElement(name = "Hooldusoigus.KehtivAlates")
                        protected String hooldusoigusKehtivAlates;
                        @XmlElement(name = "Hooldusoigus.KehtivKuni")
                        protected String hooldusoigusKehtivKuni;
                        @XmlElement(name = "Hooldusoigus.StaatusKood", required = true)
                        protected String hooldusoigusStaatusKood;
                        @XmlElement(name = "Hooldusoigus.Staatus", required = true)
                        protected String hooldusoigusStaatus;

                        /**
                         * Gets the value of the hooldusoigusPohiIsikuRoll property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getHooldusoigusPohiIsikuRoll() {
                            return hooldusoigusPohiIsikuRoll;
                        }

                        /**
                         * Sets the value of the hooldusoigusPohiIsikuRoll property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setHooldusoigusPohiIsikuRoll(String value) {
                            this.hooldusoigusPohiIsikuRoll = value;
                        }

                        /**
                         * Gets the value of the hooldusoigusTyypKood property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getHooldusoigusTyypKood() {
                            return hooldusoigusTyypKood;
                        }

                        /**
                         * Sets the value of the hooldusoigusTyypKood property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setHooldusoigusTyypKood(String value) {
                            this.hooldusoigusTyypKood = value;
                        }

                        /**
                         * Gets the value of the hooldusoigusTyyp property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getHooldusoigusTyyp() {
                            return hooldusoigusTyyp;
                        }

                        /**
                         * Sets the value of the hooldusoigusTyyp property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setHooldusoigusTyyp(String value) {
                            this.hooldusoigusTyyp = value;
                        }

                        /**
                         * Gets the value of the hooldusoigusKehtivAlates property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getHooldusoigusKehtivAlates() {
                            return hooldusoigusKehtivAlates;
                        }

                        /**
                         * Sets the value of the hooldusoigusKehtivAlates property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setHooldusoigusKehtivAlates(String value) {
                            this.hooldusoigusKehtivAlates = value;
                        }

                        /**
                         * Gets the value of the hooldusoigusKehtivKuni property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getHooldusoigusKehtivKuni() {
                            return hooldusoigusKehtivKuni;
                        }

                        /**
                         * Sets the value of the hooldusoigusKehtivKuni property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setHooldusoigusKehtivKuni(String value) {
                            this.hooldusoigusKehtivKuni = value;
                        }

                        /**
                         * Gets the value of the hooldusoigusStaatusKood property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getHooldusoigusStaatusKood() {
                            return hooldusoigusStaatusKood;
                        }

                        /**
                         * Sets the value of the hooldusoigusStaatusKood property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setHooldusoigusStaatusKood(String value) {
                            this.hooldusoigusStaatusKood = value;
                        }

                        /**
                         * Gets the value of the hooldusoigusStaatus property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getHooldusoigusStaatus() {
                            return hooldusoigusStaatus;
                        }

                        /**
                         * Sets the value of the hooldusoigusStaatus property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setHooldusoigusStaatus(String value) {
                            this.hooldusoigusStaatus = value;
                        }

                    }

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
                 *         &lt;element name="Kontakt" maxOccurs="unbounded" minOccurs="0"&gt;
                 *           &lt;complexType&gt;
                 *             &lt;complexContent&gt;
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *                 &lt;sequence&gt;
                 *                   &lt;element name="Kontakt.TyypKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *                   &lt;element name="Kontakt.Tyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *                   &lt;element name="Kontakt.Vaartus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *                 &lt;/sequence&gt;
                 *               &lt;/restriction&gt;
                 *             &lt;/complexContent&gt;
                 *           &lt;/complexType&gt;
                 *         &lt;/element&gt;
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
                    "kontakt"
                })
                public static class SeotudIsikKontaktid {

                    @XmlElement(name = "Kontakt")
                    protected List<RR435ResponseType.Isik.IsikSeotudIsikud.SeotudIsik.SeotudIsikKontaktid.Kontakt> kontakt;

                    /**
                     * Gets the value of the kontakt property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the kontakt property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getKontakt().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link RR435ResponseType.Isik.IsikSeotudIsikud.SeotudIsik.SeotudIsikKontaktid.Kontakt }
                     * 
                     * 
                     */
                    public List<RR435ResponseType.Isik.IsikSeotudIsikud.SeotudIsik.SeotudIsikKontaktid.Kontakt> getKontakt() {
                        if (kontakt == null) {
                            kontakt = new ArrayList<RR435ResponseType.Isik.IsikSeotudIsikud.SeotudIsik.SeotudIsikKontaktid.Kontakt>();
                        }
                        return this.kontakt;
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
                     *         &lt;element name="Kontakt.TyypKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                     *         &lt;element name="Kontakt.Tyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                     *         &lt;element name="Kontakt.Vaartus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                        "kontaktTyypKood",
                        "kontaktTyyp",
                        "kontaktVaartus"
                    })
                    public static class Kontakt {

                        @XmlElement(name = "Kontakt.TyypKood", required = true)
                        protected String kontaktTyypKood;
                        @XmlElement(name = "Kontakt.Tyyp", required = true)
                        protected String kontaktTyyp;
                        @XmlElement(name = "Kontakt.Vaartus", required = true)
                        protected String kontaktVaartus;

                        /**
                         * Gets the value of the kontaktTyypKood property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getKontaktTyypKood() {
                            return kontaktTyypKood;
                        }

                        /**
                         * Sets the value of the kontaktTyypKood property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setKontaktTyypKood(String value) {
                            this.kontaktTyypKood = value;
                        }

                        /**
                         * Gets the value of the kontaktTyyp property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getKontaktTyyp() {
                            return kontaktTyyp;
                        }

                        /**
                         * Sets the value of the kontaktTyyp property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setKontaktTyyp(String value) {
                            this.kontaktTyyp = value;
                        }

                        /**
                         * Gets the value of the kontaktVaartus property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getKontaktVaartus() {
                            return kontaktVaartus;
                        }

                        /**
                         * Sets the value of the kontaktVaartus property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setKontaktVaartus(String value) {
                            this.kontaktVaartus = value;
                        }

                    }

                }

            }

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
         *         &lt;element name="Teovoime" maxOccurs="unbounded" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Teovoime.LiikKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Teovoime.Liik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                   &lt;element name="Teovoime.Markus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                   &lt;element name="Teovoime.KehtivAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *                   &lt;element name="Teovoime.KehtivKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *                   &lt;element name="Teovoime.StaatusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Teovoime.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
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
            "teovoime"
        })
        public static class IsikTeovoimePiirangud {

            @XmlElement(name = "Teovoime")
            protected List<RR435ResponseType.Isik.IsikTeovoimePiirangud.Teovoime> teovoime;

            /**
             * Gets the value of the teovoime property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the teovoime property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getTeovoime().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link RR435ResponseType.Isik.IsikTeovoimePiirangud.Teovoime }
             * 
             * 
             */
            public List<RR435ResponseType.Isik.IsikTeovoimePiirangud.Teovoime> getTeovoime() {
                if (teovoime == null) {
                    teovoime = new ArrayList<RR435ResponseType.Isik.IsikTeovoimePiirangud.Teovoime>();
                }
                return this.teovoime;
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
             *         &lt;element name="Teovoime.LiikKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Teovoime.Liik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *         &lt;element name="Teovoime.Markus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *         &lt;element name="Teovoime.KehtivAlates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
             *         &lt;element name="Teovoime.KehtivKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
             *         &lt;element name="Teovoime.StaatusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Teovoime.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                "teovoimeLiikKood",
                "teovoimeLiik",
                "teovoimeMarkus",
                "teovoimeKehtivAlates",
                "teovoimeKehtivKuni",
                "teovoimeStaatusKood",
                "teovoimeStaatus"
            })
            public static class Teovoime {

                @XmlElement(name = "Teovoime.LiikKood", required = true)
                protected String teovoimeLiikKood;
                @XmlElement(name = "Teovoime.Liik")
                protected String teovoimeLiik;
                @XmlElement(name = "Teovoime.Markus")
                protected String teovoimeMarkus;
                @XmlElement(name = "Teovoime.KehtivAlates")
                protected String teovoimeKehtivAlates;
                @XmlElement(name = "Teovoime.KehtivKuni")
                protected String teovoimeKehtivKuni;
                @XmlElement(name = "Teovoime.StaatusKood", required = true)
                protected String teovoimeStaatusKood;
                @XmlElement(name = "Teovoime.Staatus", required = true)
                protected String teovoimeStaatus;

                /**
                 * Gets the value of the teovoimeLiikKood property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTeovoimeLiikKood() {
                    return teovoimeLiikKood;
                }

                /**
                 * Sets the value of the teovoimeLiikKood property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTeovoimeLiikKood(String value) {
                    this.teovoimeLiikKood = value;
                }

                /**
                 * Gets the value of the teovoimeLiik property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTeovoimeLiik() {
                    return teovoimeLiik;
                }

                /**
                 * Sets the value of the teovoimeLiik property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTeovoimeLiik(String value) {
                    this.teovoimeLiik = value;
                }

                /**
                 * Gets the value of the teovoimeMarkus property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTeovoimeMarkus() {
                    return teovoimeMarkus;
                }

                /**
                 * Sets the value of the teovoimeMarkus property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTeovoimeMarkus(String value) {
                    this.teovoimeMarkus = value;
                }

                /**
                 * Gets the value of the teovoimeKehtivAlates property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTeovoimeKehtivAlates() {
                    return teovoimeKehtivAlates;
                }

                /**
                 * Sets the value of the teovoimeKehtivAlates property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTeovoimeKehtivAlates(String value) {
                    this.teovoimeKehtivAlates = value;
                }

                /**
                 * Gets the value of the teovoimeKehtivKuni property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTeovoimeKehtivKuni() {
                    return teovoimeKehtivKuni;
                }

                /**
                 * Sets the value of the teovoimeKehtivKuni property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTeovoimeKehtivKuni(String value) {
                    this.teovoimeKehtivKuni = value;
                }

                /**
                 * Gets the value of the teovoimeStaatusKood property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTeovoimeStaatusKood() {
                    return teovoimeStaatusKood;
                }

                /**
                 * Sets the value of the teovoimeStaatusKood property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTeovoimeStaatusKood(String value) {
                    this.teovoimeStaatusKood = value;
                }

                /**
                 * Gets the value of the teovoimeStaatus property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTeovoimeStaatus() {
                    return teovoimeStaatus;
                }

                /**
                 * Sets the value of the teovoimeStaatus property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTeovoimeStaatus(String value) {
                    this.teovoimeStaatus = value;
                }

            }

        }

    }

}
