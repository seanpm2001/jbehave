/*
 * Created on 01-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.codegen.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class ContextDetails {
    private ScenarioDetails scenario;
    private List givens = new ArrayList();
    
    public ScenarioDetails scenario() {
        return scenario;
    }
    
    public void setScenario(ScenarioDetails scenario) {
        this.scenario = scenario;
    }
    
    public void addGiven(BasicDetails given) {
        givens.add(given);
    }
    
    public List getGivens() {
        return givens;
    }
    
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof ContextDetails)) return false;
        
        ContextDetails that = (ContextDetails)obj;
        return this.givens.equals(that.givens);
        
    }
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (Iterator iter = givens.iterator(); iter.hasNext();) {
            buffer.append(iter.next().toString());
        }
        return buffer.toString();
    }
}
