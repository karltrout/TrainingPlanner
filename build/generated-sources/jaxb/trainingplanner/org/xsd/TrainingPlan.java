//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.10.02 at 04:45:11 PM EDT 
//


package trainingplanner.org.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Athlete" type="{}Athlete"/>
 *         &lt;element name="PhaseStrategy" type="{}IPhaseStrategyType"/>
 *         &lt;element name="TrainingCalendar" type="{}ICalendarType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "athlete",
    "phaseStrategy",
    "trainingCalendar"
})
@XmlRootElement(name = "TrainingPlan")
public class TrainingPlan {

    @XmlElement(name = "Athlete", required = true)
    protected Athlete athlete;
    @XmlElement(name = "PhaseStrategy", required = true)
    protected IPhaseStrategyType phaseStrategy;
    @XmlElement(name = "TrainingCalendar", required = true)
    protected ICalendarType trainingCalendar;
    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the athlete property.
     * 
     * @return
     *     possible object is
     *     {@link Athlete }
     *     
     */
    public Athlete getAthlete() {
        return athlete;
    }

    /**
     * Sets the value of the athlete property.
     * 
     * @param value
     *     allowed object is
     *     {@link Athlete }
     *     
     */
    public void setAthlete(Athlete value) {
        this.athlete = value;
    }

    /**
     * Gets the value of the phaseStrategy property.
     * 
     * @return
     *     possible object is
     *     {@link IPhaseStrategyType }
     *     
     */
    public IPhaseStrategyType getPhaseStrategy() {
        return phaseStrategy;
    }

    /**
     * Sets the value of the phaseStrategy property.
     * 
     * @param value
     *     allowed object is
     *     {@link IPhaseStrategyType }
     *     
     */
    public void setPhaseStrategy(IPhaseStrategyType value) {
        this.phaseStrategy = value;
    }

    /**
     * Gets the value of the trainingCalendar property.
     * 
     * @return
     *     possible object is
     *     {@link ICalendarType }
     *     
     */
    public ICalendarType getTrainingCalendar() {
        return trainingCalendar;
    }

    /**
     * Sets the value of the trainingCalendar property.
     * 
     * @param value
     *     allowed object is
     *     {@link ICalendarType }
     *     
     */
    public void setTrainingCalendar(ICalendarType value) {
        this.trainingCalendar = value;
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

}
