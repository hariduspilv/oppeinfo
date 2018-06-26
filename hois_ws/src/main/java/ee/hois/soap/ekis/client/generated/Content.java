
package ee.hois.soap.ekis.client.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for content complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="content"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id_code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="first_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="last_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="start_date" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="end_date" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="load" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="curricula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="form" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="group" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="finsource" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outerschool" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="lang" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kudos" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="degree" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="eap" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="stip_type" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="stip_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="stip_amount" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="repr_first_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="repr_last_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="curricula_mer_code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="study_level" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="curricula_study_period" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="course" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="teacher_first_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="teacher_last_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="occupation" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="specialization" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="avg_grade" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "content", propOrder = {
    "idCode",
    "firstName",
    "lastName",
    "startDate",
    "endDate",
    "reason",
    "load",
    "curricula",
    "form",
    "group",
    "finsource",
    "outerschool",
    "lang",
    "kudos",
    "degree",
    "eap",
    "stipType",
    "stipName",
    "stipAmount",
    "reprFirstName",
    "reprLastName",
    "curriculaMerCode",
    "studyLevel",
    "curriculaStudyPeriod",
    "course",
    "teacherFirstName",
    "teacherLastName",
    "occupation",
    "specialization",
    "avgGrade"
})
public class Content {

    @XmlElement(name = "id_code", required = true)
    protected String idCode;
    @XmlElement(name = "first_name", required = true)
    protected String firstName;
    @XmlElement(name = "last_name", required = true)
    protected String lastName;
    @XmlElement(name = "start_date", required = true)
    protected String startDate;
    @XmlElement(name = "end_date", required = true)
    protected String endDate;
    @XmlElement(required = true)
    protected String reason;
    @XmlElement(required = true)
    protected String load;
    @XmlElement(required = true)
    protected String curricula;
    @XmlElement(required = true)
    protected String form;
    @XmlElement(required = true)
    protected String group;
    @XmlElement(required = true)
    protected String finsource;
    @XmlElement(required = true)
    protected String outerschool;
    @XmlElement(required = true)
    protected String lang;
    @XmlElement(required = true)
    protected String kudos;
    @XmlElement(required = true)
    protected String degree;
    @XmlElement(required = true)
    protected String eap;
    @XmlElement(name = "stip_type", required = true)
    protected String stipType;
    @XmlElement(name = "stip_name", required = true)
    protected String stipName;
    @XmlElement(name = "stip_amount", required = true)
    protected String stipAmount;
    @XmlElement(name = "repr_first_name", required = true)
    protected String reprFirstName;
    @XmlElement(name = "repr_last_name", required = true)
    protected String reprLastName;
    @XmlElement(name = "curricula_mer_code", required = true)
    protected String curriculaMerCode;
    @XmlElement(name = "study_level", required = true)
    protected String studyLevel;
    @XmlElement(name = "curricula_study_period")
    protected int curriculaStudyPeriod;
    protected int course;
    @XmlElement(name = "teacher_first_name", required = true)
    protected String teacherFirstName;
    @XmlElement(name = "teacher_last_name", required = true)
    protected String teacherLastName;
    @XmlElement(required = true)
    protected String occupation;
    @XmlElement(required = true)
    protected String specialization;
    @XmlElement(name = "avg_grade", required = true)
    protected String avgGrade;

    /**
     * Gets the value of the idCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdCode() {
        return idCode;
    }

    /**
     * Sets the value of the idCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdCode(String value) {
        this.idCode = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartDate(String value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndDate(String value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

    /**
     * Gets the value of the load property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoad() {
        return load;
    }

    /**
     * Sets the value of the load property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoad(String value) {
        this.load = value;
    }

    /**
     * Gets the value of the curricula property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurricula() {
        return curricula;
    }

    /**
     * Sets the value of the curricula property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurricula(String value) {
        this.curricula = value;
    }

    /**
     * Gets the value of the form property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForm() {
        return form;
    }

    /**
     * Sets the value of the form property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForm(String value) {
        this.form = value;
    }

    /**
     * Gets the value of the group property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroup() {
        return group;
    }

    /**
     * Sets the value of the group property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroup(String value) {
        this.group = value;
    }

    /**
     * Gets the value of the finsource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFinsource() {
        return finsource;
    }

    /**
     * Sets the value of the finsource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFinsource(String value) {
        this.finsource = value;
    }

    /**
     * Gets the value of the outerschool property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOuterschool() {
        return outerschool;
    }

    /**
     * Sets the value of the outerschool property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOuterschool(String value) {
        this.outerschool = value;
    }

    /**
     * Gets the value of the lang property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets the value of the lang property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLang(String value) {
        this.lang = value;
    }

    /**
     * Gets the value of the kudos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKudos() {
        return kudos;
    }

    /**
     * Sets the value of the kudos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKudos(String value) {
        this.kudos = value;
    }

    /**
     * Gets the value of the degree property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDegree() {
        return degree;
    }

    /**
     * Sets the value of the degree property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDegree(String value) {
        this.degree = value;
    }

    /**
     * Gets the value of the eap property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEap() {
        return eap;
    }

    /**
     * Sets the value of the eap property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEap(String value) {
        this.eap = value;
    }

    /**
     * Gets the value of the stipType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStipType() {
        return stipType;
    }

    /**
     * Sets the value of the stipType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStipType(String value) {
        this.stipType = value;
    }

    /**
     * Gets the value of the stipName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStipName() {
        return stipName;
    }

    /**
     * Sets the value of the stipName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStipName(String value) {
        this.stipName = value;
    }

    /**
     * Gets the value of the stipAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStipAmount() {
        return stipAmount;
    }

    /**
     * Sets the value of the stipAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStipAmount(String value) {
        this.stipAmount = value;
    }

    /**
     * Gets the value of the reprFirstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReprFirstName() {
        return reprFirstName;
    }

    /**
     * Sets the value of the reprFirstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReprFirstName(String value) {
        this.reprFirstName = value;
    }

    /**
     * Gets the value of the reprLastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReprLastName() {
        return reprLastName;
    }

    /**
     * Sets the value of the reprLastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReprLastName(String value) {
        this.reprLastName = value;
    }

    /**
     * Gets the value of the curriculaMerCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurriculaMerCode() {
        return curriculaMerCode;
    }

    /**
     * Sets the value of the curriculaMerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurriculaMerCode(String value) {
        this.curriculaMerCode = value;
    }

    /**
     * Gets the value of the studyLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStudyLevel() {
        return studyLevel;
    }

    /**
     * Sets the value of the studyLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStudyLevel(String value) {
        this.studyLevel = value;
    }

    /**
     * Gets the value of the curriculaStudyPeriod property.
     * 
     */
    public int getCurriculaStudyPeriod() {
        return curriculaStudyPeriod;
    }

    /**
     * Sets the value of the curriculaStudyPeriod property.
     * 
     */
    public void setCurriculaStudyPeriod(int value) {
        this.curriculaStudyPeriod = value;
    }

    /**
     * Gets the value of the course property.
     * 
     */
    public int getCourse() {
        return course;
    }

    /**
     * Sets the value of the course property.
     * 
     */
    public void setCourse(int value) {
        this.course = value;
    }

    /**
     * Gets the value of the teacherFirstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeacherFirstName() {
        return teacherFirstName;
    }

    /**
     * Sets the value of the teacherFirstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeacherFirstName(String value) {
        this.teacherFirstName = value;
    }

    /**
     * Gets the value of the teacherLastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeacherLastName() {
        return teacherLastName;
    }

    /**
     * Sets the value of the teacherLastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeacherLastName(String value) {
        this.teacherLastName = value;
    }

    /**
     * Gets the value of the occupation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * Sets the value of the occupation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOccupation(String value) {
        this.occupation = value;
    }

    /**
     * Gets the value of the specialization property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialization() {
        return specialization;
    }

    /**
     * Sets the value of the specialization property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialization(String value) {
        this.specialization = value;
    }

    /**
     * Gets the value of the avgGrade property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAvgGrade() {
        return avgGrade;
    }

    /**
     * Sets the value of the avgGrade property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAvgGrade(String value) {
        this.avgGrade = value;
    }

}
