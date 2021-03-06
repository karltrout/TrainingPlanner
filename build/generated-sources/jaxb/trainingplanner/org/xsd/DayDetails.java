//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.10.11 at 07:08:35 PM EDT 
//


package trainingplanner.org.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * extensible day details which should be measureable and trackable.
 * 
 * <p>Java class for dayDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dayDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="wakeupTime" type="{http://www.w3.org/2001/XMLSchema}time"/>
 *         &lt;element name="sleepQuality" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="energyLevel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="moodLevel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;group ref="{trainingPlan}nutrition"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dayDetails", propOrder = {
    "wakeupTime",
    "sleepQuality",
    "energyLevel",
    "moodLevel",
    "protein",
    "fats",
    "carbohydrates"
})
public class DayDetails {

    @XmlElement(required = true)
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar wakeupTime;
    @XmlElement(defaultValue = "0")
    protected int sleepQuality;
    @XmlElement(defaultValue = "0")
    protected int energyLevel;
    protected int moodLevel;
    protected int protein;
    protected int fats;
    protected int carbohydrates;

    /**
     * Gets the value of the wakeupTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getWakeupTime() {
        return wakeupTime;
    }

    /**
     * Sets the value of the wakeupTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setWakeupTime(XMLGregorianCalendar value) {
        this.wakeupTime = value;
    }

    /**
     * Gets the value of the sleepQuality property.
     * 
     */
    public int getSleepQuality() {
        return sleepQuality;
    }

    /**
     * Sets the value of the sleepQuality property.
     * 
     */
    public void setSleepQuality(int value) {
        this.sleepQuality = value;
    }

    /**
     * Gets the value of the energyLevel property.
     * 
     */
    public int getEnergyLevel() {
        return energyLevel;
    }

    /**
     * Sets the value of the energyLevel property.
     * 
     */
    public void setEnergyLevel(int value) {
        this.energyLevel = value;
    }

    /**
     * Gets the value of the moodLevel property.
     * 
     */
    public int getMoodLevel() {
        return moodLevel;
    }

    /**
     * Sets the value of the moodLevel property.
     * 
     */
    public void setMoodLevel(int value) {
        this.moodLevel = value;
    }

    /**
     * Gets the value of the protein property.
     * 
     */
    public int getProtein() {
        return protein;
    }

    /**
     * Sets the value of the protein property.
     * 
     */
    public void setProtein(int value) {
        this.protein = value;
    }

    /**
     * Gets the value of the fats property.
     * 
     */
    public int getFats() {
        return fats;
    }

    /**
     * Sets the value of the fats property.
     * 
     */
    public void setFats(int value) {
        this.fats = value;
    }

    /**
     * Gets the value of the carbohydrates property.
     * 
     */
    public int getCarbohydrates() {
        return carbohydrates;
    }

    /**
     * Sets the value of the carbohydrates property.
     * 
     */
    public void setCarbohydrates(int value) {
        this.carbohydrates = value;
    }

}
