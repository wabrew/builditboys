package com.builditboys.misc.planning;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import com.builditboys.misc.planning.Plan.CalculationModeEnum;
import com.builditboys.misc.planning.Plan.PlanModeEnum;

public class SimpleXMLPlan {
	
	@Element(required=false)
	String name;
	
	@Element(required=false)
	String description;

	@ElementList(required=false)
	List<SimpleXMLTask> userTasks;
	
	@ElementList(required=false)
	List<SimpleXMLResourcePool> resourcePools;
	
	

	@Element(required=false)
	PlanModeEnum planMode;

	@Element(required=false)
	boolean isAutoCalculated;

	@Element(required=false)
	CalculationModeEnum calculationMode;	

	
	
 	// --------------------------------------------------------------------------------	

	public static SimpleXMLPlan constructXMLPlan (Plan plan) {
		SimpleXMLPlan xmlPlan = new SimpleXMLPlan();
		
		xmlPlan.name = plan.name;
		xmlPlan.description = plan.description;
		
		xmlPlan.userTasks = constructXMLUserTasks(plan);
		xmlPlan.resourcePools = constructXMLResourcePools(plan);
		
		xmlPlan.planMode = plan.planMode;
		xmlPlan.isAutoCalculated = plan.isAutoCalculated;
		xmlPlan.calculationMode = plan.calculationMode;
		
		return xmlPlan;
	}
	
	
	static List<SimpleXMLTask> constructXMLUserTasks (Plan plan) {
		List<SimpleXMLTask> xmlTasks = new ArrayList<SimpleXMLTask>();
		for (Task task: plan.userTasks) {
			xmlTasks.add(SimpleXMLTask.constructXMLTask(task));
		}
		return xmlTasks;
	}
	
	static List<SimpleXMLResourcePool> constructXMLResourcePools (Plan plan) {
		List<SimpleXMLResourcePool> xmlPools = new ArrayList<SimpleXMLResourcePool>();
		
		return xmlPools;
	}

 	// --------------------------------------------------------------------------------	

	public static Plan constructPlan (SimpleXMLPlan xmlPlan) {
		Plan plan = new Plan();

		plan.name = xmlPlan.name;
		plan.description = xmlPlan.description;
		
		plan.userTasks = constructUserTasks(xmlPlan);
		plan.resourcePools = constructResourcePools(xmlPlan);
		
		plan.planMode = xmlPlan.planMode;
		plan.isAutoCalculated = xmlPlan.isAutoCalculated;
		plan.calculationMode = xmlPlan.calculationMode;

		return plan;
	}
	
	static List<Task> constructUserTasks (SimpleXMLPlan xmlPlan) {
		List<Task> tasks = new ArrayList<Task>();
		for (SimpleXMLTask xmlTask: xmlPlan.userTasks) {
			tasks.add(SimpleXMLTask.constructTask(xmlTask));
		}
		
		return tasks;
	}
	
	static List<ResourcePool> constructResourcePools (SimpleXMLPlan xmlPlan) {
		List<ResourcePool> pools = new ArrayList<ResourcePool>();
		
		return pools;
	}



}
