package com.builditboys.misc.planning;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import com.builditboys.misc.planning.Task.TaskKindEnum;
import com.builditboys.misc.planning.Task.TaskVarianceEnum;

class SimpleXMLTask {
	
	@Element
	String name;
	
	@Element(required=false)
	String description;

	@Element(required=false)
	TaskKindEnum kind;

	@Element(required=false)
	TaskVarianceEnum varianceReason;

	@ElementList(required=false)
	Set<String> predecessorNames;
	
	@ElementList(required=false)		
	Set<String> successorNames;

	@ElementList(required=false)
	List<SimpleXMLResourceClaim> resourceClaims;

	@ElementList(required=false)
	List<SimpleXMLMaterialClaim> materialClaims;

	@Element(required=false)
	long shortestDuration;
	
	@Element(required=false)
	long nominalDuration;
	
	@Element(required=false)
	long longestDuration;

	@Element(required=false)
	boolean hasAfterFinishConstrint = false;
	
	@Element(required=false)
	long afterFinishMaxWait;
	
 	// --------------------------------------------------------------------------------	

	static SimpleXMLTask constructXMLTask (Task task) {
		SimpleXMLTask xmlTask = new SimpleXMLTask();
		
		xmlTask.name = task.name;
		xmlTask.description = task.name;
		xmlTask.kind = task.kind;
		xmlTask.varianceReason = task.varianceReason;
		xmlTask.predecessorNames = task.predecessorNames;
		xmlTask.successorNames = task.successorNames;
		xmlTask.resourceClaims = constructXMLResourceClaims(task);
		xmlTask.materialClaims = constructXMLMaterialClaims(task);
		xmlTask.shortestDuration = task.shortestDuration;
		xmlTask.nominalDuration = task.nominalDuration;
		xmlTask.longestDuration = task.longestDuration;
		xmlTask.hasAfterFinishConstrint = task.hasAfterFinishConstrint;
		xmlTask.afterFinishMaxWait = task.afterFinishMaxWait;

		return xmlTask;
	}
	
	static List<SimpleXMLResourceClaim> constructXMLResourceClaims (Task task) {
		List<SimpleXMLResourceClaim> xmlClaims = new ArrayList<SimpleXMLResourceClaim>();
		for (ResourceClaim claim: task.resourceClaims) {
			xmlClaims.add(SimpleXMLResourceClaim.constructXMLResourceClaim(claim));
		}
		return xmlClaims;
	}
	
	static List<SimpleXMLMaterialClaim> constructXMLMaterialClaims (Task task) {
		List<SimpleXMLMaterialClaim> xmlClaims = new ArrayList<SimpleXMLMaterialClaim>();
		for (MaterialClaim claim: task.materialClaims) {
			xmlClaims.add(SimpleXMLMaterialClaim.constructXMLMaterialClaim(claim));
		}
		return xmlClaims;
	}
	
 	// --------------------------------------------------------------------------------	

	static Task constructTask (SimpleXMLTask xmlTask) {
		Task task = new Task(xmlTask.name);
		
//		task.name = xmlTask.name;
		task.description = xmlTask.name;
		task.kind = xmlTask.kind;
		task.varianceReason = xmlTask.varianceReason;
		task.predecessorNames = xmlTask.predecessorNames;
		task.successorNames = xmlTask.successorNames;	
		task.resourceClaims = constructResourceClaims(xmlTask);
		task.materialClaims = constructMaterialClaims(xmlTask);		
		task.shortestDuration = xmlTask.shortestDuration;
		task.nominalDuration = xmlTask.nominalDuration;
		task.longestDuration = xmlTask.longestDuration;
		task.hasAfterFinishConstrint = xmlTask.hasAfterFinishConstrint;
		task.afterFinishMaxWait = xmlTask.afterFinishMaxWait;

		return task;
	}

	static List<ResourceClaim> constructResourceClaims (SimpleXMLTask xmlTask) {
		List<ResourceClaim> claims = new ArrayList<ResourceClaim>();
		for (SimpleXMLResourceClaim xmlClaim: xmlTask.resourceClaims) {
			claims.add(SimpleXMLResourceClaim.constructResourceClaim(xmlClaim));
		}

		return claims;
	}
	
	static List<MaterialClaim> constructMaterialClaims (SimpleXMLTask xmlTask) {
		List<MaterialClaim> claims = new ArrayList<MaterialClaim>();
		for (SimpleXMLMaterialClaim xmlClaim: xmlTask.materialClaims) {
			claims.add(SimpleXMLMaterialClaim.constructMaterialClaim(xmlClaim));
		}

		return claims;
	}
	
	

}
