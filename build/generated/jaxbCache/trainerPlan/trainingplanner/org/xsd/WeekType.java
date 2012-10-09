//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.10.08 at 10:10:50 PM EDT 
//


package trainingplanner.org.xsd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Week details
 * 
 * <p>Java class for WeekType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WeekType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="trainingPlanWeek" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="trainingType" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;group ref="{trainingPlan}days"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="parentId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="weekNumber" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WeekType", propOrder = {
    "trainingPlanWeek",
    "trainingType",
    "dayType"
})
public class WeekType {

    protected int trainingPlanWeek;
    @XmlElement(required = true)
    protected Object trainingType;
    @XmlElement(name = "DayType", required = true)
    protected List<DayType> dayType;
    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "parentId")
    protected String parentId;
    @XmlAttribute(name = "weekNumber")
    protected Integer weekNumber;

    /**
     * Gets the value of the trainingPlanWeek property.
     * 
     */
    public int getTrainingPlanWeek() {
        return trainingPlanWeek;
    }

    /**
     * Sets the value of the trainingPlanWeek property.
     * 
     */
    public void setTrainingPlanWeek(int value) {
        this.trainingPlanWeek = value;
    }

    /**
     * Gets the value of the trainingType property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getTrainingType() {
        return trainingType;
    }

    /**
     * Sets the value of the trainingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setTrainingType(Object value) {
        this.trainingType = value;
    }

    /**
     * Gets the value of the dayType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dayType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDayType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DayType }
     * 
     * 
     */
    public List<DayType> getDayType() {
        if (dayType == null) {
            dayType = new ArrayList<DayType>();
        }
        return this.dayType;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the parentId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * Sets the value of the parentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentId(String value) {
        this.parentId = value;
    }

    /**
     * Gets the value of the weekNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWeekNumber() {
        return weekNumber;
    }

    /**
     * Sets the value of the weekNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWeekNumber(Integer value) {
        this.weekNumber = value;
    }

}
