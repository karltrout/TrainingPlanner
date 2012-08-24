/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner.org.extensions;

import trainingplanner.org.xsd.IKPIType;
import trainingplanner.org.xsd.KpiValueType;
/**
 *
 * @author troutk
 */

public class KPI extends IKPIType{
    @Override
    public void setStartValue(KpiValueType value) {
        super.setStartValue(value);
        addKPIValue(value);
    }
    
    public void addKPIValue(KpiValueType value){
        this.getKpiRecords().getKpiRecord().add(value);
        this.setCurrentValue(value);    
    }
}
