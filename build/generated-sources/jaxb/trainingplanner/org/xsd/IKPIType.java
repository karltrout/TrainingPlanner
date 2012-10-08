//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.10.08 at 05:20:49 PM EDT 
//


package trainingplanner.org.xsd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * hold the list of test values for a particular sportsType. contains measurement types and values
 * 
 * <p>Java class for IKPIType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IKPIType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SportsType" type="{trainingPlan}sportTypes"/>
 *         &lt;element name="startValue" type="{trainingPlan}KpiValueType"/>
 *         &lt;element name="goalValue" type="{trainingPlan}KpiValueType"/>
 *         &lt;element name="currentValue" type="{trainingPlan}KpiValueType"/>
 *         &lt;element name="Measurement">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="measureType" type="{trainingPlan}measureTypes" default="pace" />
 *                 &lt;attribute name="distanceMeasure" type="{trainingPlan}distanceMeasures" default="meter" />
 *                 &lt;attribute name="timeMeasure" type="{trainingPlan}timeMeasures" default="second" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="kpiRecords">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="kpiRecord" type="{trainingPlan}KpiValueType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="parentId" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IKPIType", propOrder = {
    "sportsType",
    "startValue",
    "goalValue",
    "currentValue",
    "measurement",
    "kpiRecords"
})
public class IKPIType {

    @XmlElement(name = "SportsType", required = true, defaultValue = "Other")
    protected SportTypes sportsType;
    @XmlElement(required = true)
    protected KpiValueType startValue;
    @XmlElement(required = true)
    protected KpiValueType goalValue;
    @XmlElement(required = true)
    protected KpiValueType currentValue;
    @XmlElement(name = "Measurement", required = true)
    protected IKPIType.Measurement measurement;
    @XmlElement(required = true)
    protected IKPIType.KpiRecords kpiRecords;
    @XmlAttribute(name = "parentId")
    @XmlSchemaType(name = "anySimpleType")
    protected String parentId;
    @XmlAttribute(name = "id", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String id;

    /**
     * Gets the value of the sportsType property.
     * 
     * @return
     *     possible object is
     *     {@link SportTypes }
     *     
     */
    public SportTypes getSportsType() {
        return sportsType;
    }

    /**
     * Sets the value of the sportsType property.
     * 
     * @param value
     *     allowed object is
     *     {@link SportTypes }
     *     
     */
    public void setSportsType(SportTypes value) {
        this.sportsType = value;
    }

    /**
     * Gets the value of the startValue property.
     * 
     * @return
     *     possible object is
     *     {@link KpiValueType }
     *     
     */
    public KpiValueType getStartValue() {
        return startValue;
    }

    /**
     * Sets the value of the startValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link KpiValueType }
     *     
     */
    public void setStartValue(KpiValueType value) {
        this.startValue = value;
    }

    /**
     * Gets the value of the goalValue property.
     * 
     * @return
     *     possible object is
     *     {@link KpiValueType }
     *     
     */
    public KpiValueType getGoalValue() {
        return goalValue;
    }

    /**
     * Sets the value of the goalValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link KpiValueType }
     *     
     */
    public void setGoalValue(KpiValueType value) {
        this.goalValue = value;
    }

    /**
     * Gets the value of the currentValue property.
     * 
     * @return
     *     possible object is
     *     {@link KpiValueType }
     *     
     */
    public KpiValueType getCurrentValue() {
        return currentValue;
    }

    /**
     * Sets the value of the currentValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link KpiValueType }
     *     
     */
    public void setCurrentValue(KpiValueType value) {
        this.currentValue = value;
    }

    /**
     * Gets the value of the measurement property.
     * 
     * @return
     *     possible object is
     *     {@link IKPIType.Measurement }
     *     
     */
    public IKPIType.Measurement getMeasurement() {
        return measurement;
    }

    /**
     * Sets the value of the measurement property.
     * 
     * @param value
     *     allowed object is
     *     {@link IKPIType.Measurement }
     *     
     */
    public void setMeasurement(IKPIType.Measurement value) {
        this.measurement = value;
    }

    /**
     * Gets the value of the kpiRecords property.
     * 
     * @return
     *     possible object is
     *     {@link IKPIType.KpiRecords }
     *     
     */
    public IKPIType.KpiRecords getKpiRecords() {
        return kpiRecords;
    }

    /**
     * Sets the value of the kpiRecords property.
     * 
     * @param value
     *     allowed object is
     *     {@link IKPIType.KpiRecords }
     *     
     */
    public void setKpiRecords(IKPIType.KpiRecords value) {
        this.kpiRecords = value;
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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="kpiRecord" type="{trainingPlan}KpiValueType" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "kpiRecord"
    })
    public static class KpiRecords {

        @XmlElement(required = true)
        protected List<KpiValueType> kpiRecord;

        /**
         * Gets the value of the kpiRecord property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the kpiRecord property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getKpiRecord().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link KpiValueType }
         * 
         * 
         */
        public List<KpiValueType> getKpiRecord() {
            if (kpiRecord == null) {
                kpiRecord = new ArrayList<KpiValueType>();
            }
            return this.kpiRecord;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="measureType" type="{trainingPlan}measureTypes" default="pace" />
     *       &lt;attribute name="distanceMeasure" type="{trainingPlan}distanceMeasures" default="meter" />
     *       &lt;attribute name="timeMeasure" type="{trainingPlan}timeMeasures" default="second" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Measurement {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "measureType")
        protected MeasureTypes measureType;
        @XmlAttribute(name = "distanceMeasure")
        protected DistanceMeasures distanceMeasure;
        @XmlAttribute(name = "timeMeasure")
        protected TimeMeasures timeMeasure;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the measureType property.
         * 
         * @return
         *     possible object is
         *     {@link MeasureTypes }
         *     
         */
        public MeasureTypes getMeasureType() {
            if (measureType == null) {
                return MeasureTypes.PACE;
            } else {
                return measureType;
            }
        }

        /**
         * Sets the value of the measureType property.
         * 
         * @param value
         *     allowed object is
         *     {@link MeasureTypes }
         *     
         */
        public void setMeasureType(MeasureTypes value) {
            this.measureType = value;
        }

        /**
         * Gets the value of the distanceMeasure property.
         * 
         * @return
         *     possible object is
         *     {@link DistanceMeasures }
         *     
         */
        public DistanceMeasures getDistanceMeasure() {
            if (distanceMeasure == null) {
                return DistanceMeasures.METER;
            } else {
                return distanceMeasure;
            }
        }

        /**
         * Sets the value of the distanceMeasure property.
         * 
         * @param value
         *     allowed object is
         *     {@link DistanceMeasures }
         *     
         */
        public void setDistanceMeasure(DistanceMeasures value) {
            this.distanceMeasure = value;
        }

        /**
         * Gets the value of the timeMeasure property.
         * 
         * @return
         *     possible object is
         *     {@link TimeMeasures }
         *     
         */
        public TimeMeasures getTimeMeasure() {
            if (timeMeasure == null) {
                return TimeMeasures.SECOND;
            } else {
                return timeMeasure;
            }
        }

        /**
         * Sets the value of the timeMeasure property.
         * 
         * @param value
         *     allowed object is
         *     {@link TimeMeasures }
         *     
         */
        public void setTimeMeasure(TimeMeasures value) {
            this.timeMeasure = value;
        }

    }

}
