package com.builditboys.misc.planning;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import com.builditboys.misc.planning.Task.DurationKindEnum;
import com.builditboys.misc.planning.Task.TaskActionEnum;
import com.builditboys.misc.planning.Task.TaskKindEnum;
import com.builditboys.misc.planning.Task.TaskTimeEnum;

public class Plan {

	static public enum PlanModeEnum {
		PLANNING, ACTUAL
	};

	static public enum CalculationModeEnum {
		CRITICAL_PATH, EAGER_PATH, BOTH
	};
	
	
	
	static enum CalculationResultEnum { 
		NOMINAL, CRITICAL_SHORTEST, CRITICAL_LONGEST, EAGER_SHORTEST, EAGER_LONGEST };
		
	// These are the actions you can perform on a plan. 
	static enum PlanActionEnum { 
		SETUP,
		SET_TASKS, ADD_TASK, REMOVE_TASK,
		SET_RESOURCE_POOLS, ADD_RESOURCE_POOL, REMOVE_RESOURCE_POOL,
		MODE,
		RESET,
		IS_AUTOCALCULATED,
		CALCULATION_MODE
	}

	// ----------
	// Basic plan properties
		
	String name;
	String description;

	Set<Task> userTasks = new HashSet<Task>();
	
	Collection<ResourcePool> resourcePools = new LinkedList<ResourcePool>();
	
	

	PlanModeEnum planMode = PlanModeEnum.PLANNING;

	boolean isAutoCalculated = false;

	CalculationModeEnum calculationMode = CalculationModeEnum.CRITICAL_PATH;	
	
	// ----------
	// Computed plan properties
	
	Task startTask = new Task(TaskKindEnum.PASSIVE, "-Start-Task-", "Created Start Task", 0);
	Task finishTask = new Task(TaskKindEnum.PASSIVE, "-Finish-Task-", "Created Finish Task", 0);

	boolean isSetUp = false;

	boolean isCalculated;
	
	Task pinnedTask;

	List<Task> taskSchedule = new LinkedList<Task>();

	List<Task> activeTaskSchedule = new LinkedList<Task>();

	// ----------
	// Internals
	
	Set<Task> allTasks = new HashSet<Task>();
	
	Map<String, Task> taskLookup = new HashMap<String, Task>();
	Map<String, ResourcePool> resourcePoolLookup = new HashMap<String, ResourcePool>();

	Set<CalculationResultEnum> calculatedResultKinds = new HashSet<CalculationResultEnum>();

	long resultAlignmentOffsets[] = new long[CalculationResultEnum.values().length];
	
	long absoluteTimeReference;

	
	// --------------------------------------------------------------------------------
	// Constructors
	
	public Plan() {
	}

	public Plan(String nm, String desc) {
		name = nm;
		description = desc;
	}

	// --------------------------------------------------------------------------------
	// Some basic public accessors
	
	// ----------

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	// ----------

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	// --------------------------------------------------------------------------------
	// Getting, adding and removing tasks
	
	public Set<Task> getTasks() {
		return userTasks;
	}
	
	public void setTasks (Set<Task> tasks) {
		unSetupTasks();
		this.userTasks = tasks;
		handlePlanChange(PlanActionEnum.SET_TASKS);
	}
	
	public void addTask(Task tsk) {
		userTasks.add(tsk);
// handled in setup
//		taskLookup.put(tsk.name, tsk);
//		tsk.setContainingPlan(this);
		handlePlanChange(PlanActionEnum.ADD_TASK);
	}

	public void removeTask(Task tsk) {
		userTasks.remove(tsk);
// handled in unsetup
//		taskLookup.remove(tsk.name);
//		tsk.setContainingPlan(null);
		handlePlanChange(PlanActionEnum.REMOVE_TASK);
	}

	public Task lookupTaskByName(String nm) {
		return taskLookup.get(nm);
	}
	
	public List<Task> getNewTaskList () {
		return new LinkedList<Task>(allTasks);
	}
	
	public List<Task> getActiveTaskList () {
		buildActiveTaskList();
		return activeTaskSchedule;
	}
	
	public Task getStartTask() {
		return startTask;
	}

	public Task getFinishTask() {
		return finishTask;
	}

 	// --------------------------------------------------------------------------------

	// Resources
	
	public Collection<ResourcePool> getResourcePools() {
		return resourcePools;
	}

	public void setResourcePools(Collection<ResourcePool> resourcePools) {
		unSetupResourcePools();
		this.resourcePools = resourcePools;
		handlePlanChange(PlanActionEnum.SET_RESOURCE_POOLS);
	}
	
	public void addResourcePool(ResourcePool pool) {
		resourcePools.add(pool);
// handled in setup
//		resourcePoolLookup.put(pool.getName(), pool);
		handlePlanChange(PlanActionEnum.ADD_RESOURCE_POOL);
	}

	public void removeResourcePool(ResourcePool pool) {
		resourcePools.remove(pool);
// handled in setup
//		resourcePoolLookup.remove(pool.getName());
		handlePlanChange(PlanActionEnum.REMOVE_RESOURCE_POOL);
	}

	public ResourcePool lookupResourcePoolByName(String nm) {
		return resourcePoolLookup.get(nm);
	}


	// --------------------------------------------------------------------------------
	// Comparators
	
	static int compareLong (long l1, long l2) {
		if (l1 < l2) {
			return -1;
		}
		else if (l1 > l2) {
			return +1;
		}
		else return 0;
	}
	
	// ----------
	
	public class NominalStartTimeAscendingComparator implements Comparator<Task> {
		public int compare(Task tsk1, Task tsk2) {
			return Plan.compareLong(tsk1.nominalStartTime, tsk2.nominalStartTime);
		}
	}
	
	public Comparator<Task> nominalStartTimeAscendingComparator = new NominalStartTimeAscendingComparator();

	// ----------
	
	public class NominalFinishTimeAscendingComparator implements Comparator<Task> {
		public int compare(Task tsk1, Task tsk2) {
			return Plan.compareLong(tsk1.nominalFinishTime, tsk2.nominalFinishTime);
		}
	}
	
	public Comparator<Task> nominalFinishTimeAscendingComparator = new NominalFinishTimeAscendingComparator();

	// ----------
	
	public class EarliestStartTimeAscendingComparator implements Comparator<Task> {
		public int compare(Task tsk1, Task tsk2) {
			return Plan.compareLong(tsk1.earliestStartTime, tsk2.earliestStartTime);
		}
	}
	
	public Comparator<Task> EarliestStartTimeAscendingComparator = new EarliestStartTimeAscendingComparator();

	// ----------
	
	public class EarliestFinishTimeAscendingComparator implements Comparator<Task> {
		public int compare(Task tsk1, Task tsk2) {
			return Plan.compareLong(tsk1.earliestFinishTime, tsk2.earliestFinishTime);
		}
	}
	
	public Comparator<Task> EarliestFinishTimeAscendingComparator = new EarliestFinishTimeAscendingComparator();

	// ----------
	
	public class LatestStartTimeAscendingComparator implements Comparator<Task> {
		public int compare(Task tsk1, Task tsk2) {
			return Plan.compareLong(tsk1.latestStartTime, tsk2.latestStartTime);
		}
	}
	
	public Comparator<Task> LatestStartTimeAscendingComparator = new LatestStartTimeAscendingComparator();

	// ----------
	
	public class LatestFinishTimeAscendingComparator implements Comparator<Task> {
		public int compare(Task tsk1, Task tsk2) {
			return Plan.compareLong(tsk1.latestFinishTime, tsk2.latestFinishTime);
		}
	}
	
	public Comparator<Task> LatestFinishTimeAscendingComparator = new LatestFinishTimeAscendingComparator();

	// --------------------------------------------------------------------------------
	// Pinning

	public boolean isPinned () {
		return pinnedTask != null;
	}
	
	public void unPin () {
		if (pinnedTask != null) {
			pinnedTask.unPin();   // will trigger an un pin back to the plan
		}
	}
	
	public Task getPinnedTask() {
		return pinnedTask;
	}
	
	// --------------------------------------------------------------------------------

	public PlanModeEnum getPlanMode() {
		return planMode;
	}

	public void setPlanMode(PlanModeEnum planMode) {
		this.planMode = planMode;
		handlePlanChange(PlanActionEnum.MODE);
	}

	// --------------------------------------------------------------------------------

	public void setup() {
		setupTasks();
		setupResourcePools();
		
		setupStartFinish();
		setupPredecessorsSuccessors();
		
		setupResourceClaims();

		reset();
		isSetUp = true;
		handlePlanChange(PlanActionEnum.SETUP);
	}

	// --------------------------------------------------------------------------------

	void unSetupTasks () {
		taskLookup.clear();
		for (Task tsk: userTasks) {
			tsk.setContainingPlan(null);
		}
	}
	
	void setupTasks () {
		taskLookup.clear();
		for (Task tsk: userTasks) {
			taskLookup.put(tsk.name, tsk);
			tsk.setContainingPlan(this);
			tsk.normalizeDurations();
		}
		allTasks.clear();
		allTasks.addAll(userTasks);
		
		taskLookup.put(startTask.name, startTask);
		startTask.setContainingPlan(this);
		allTasks.add(startTask);
		
		taskLookup.put(finishTask.name, finishTask);
		finishTask.setContainingPlan(this);	
		allTasks.add(finishTask);
	}
	
	void unSetupResourcePools () {
		resourcePoolLookup.clear();
	}
	
	void setupResourcePools () {
		resourcePoolLookup.clear();
		for (ResourcePool pool: resourcePools) {
			resourcePoolLookup.put(pool.getName(), pool);
		}
	}
	
	void setupStartFinish() {
		List<Task> starters = new ArrayList<Task>(allTasks.size());
		List<Task> finishers = new ArrayList<Task>(allTasks.size());

		for (Task tsk : userTasks) {
			if (tsk.predecessorNames.isEmpty()) {
				starters.add(tsk);
			}
			if (tsk.successorNames.isEmpty()) {
				finishers.add(tsk);
			}
		}
		System.out.println(starters + " " + finishers);

		Set<String> startNames = startTask.successorNames;
		startNames.clear();
		for (Task tsk : starters) {
			startNames.add(tsk.name);
		}
		Set<String> finishNames = finishTask.predecessorNames;
		finishNames.clear();
		for (Task tsk : finishers) {
			finishNames.add(tsk.name);
		}
	}

	void setupPredecessorsSuccessors() {
		Task candidate;

		for (Task tsk : allTasks) {
			tsk.predecessors.clear();
			tsk.successors.clear();
		}

		for (Task tsk : allTasks) {
			for (String nm : tsk.predecessorNames) {
				candidate = taskLookup.get(nm);
				if (candidate == null) {
					throw new IllegalStateException("Bad predecessor name"
													+ tsk.name + " / " + nm);
				} else {
					tsk.predecessors.add(candidate);
// don't disturb user specified information
//					candidate.successorNames.add(tsk.name);
					candidate.successors.add(tsk);
				}
			}
		}

		for (Task tsk : allTasks) {
			for (String nm : tsk.successorNames) {
				candidate = taskLookup.get(nm);
				if (candidate == null) {
					throw new IllegalStateException("Bad successor name"
													+ tsk.name + " / " + nm);
				} else {
					tsk.successors.add(candidate);
// don't disturb user specified information
//					candidate.predecessorNames.add(tsk.name);
					candidate.predecessors.add(tsk);
				}
			}
		}

		for (Task tsk : allTasks) {
			System.out.println(tsk + " predecessors " + tsk.predecessors);
			System.out.println(tsk + " successors " + tsk.successors);
		}
	}
	

	void setupResourceClaims () {
		ResourcePool candidate;
		for (Task tsk: allTasks) {
			for (ResourceClaim resourceClaim: tsk.resourceClaims){
				candidate = resourcePoolLookup.get(resourceClaim.getName());
					if (candidate != null) {
						resourceClaim.fullfillingPool = candidate;
					}
					else {
						throw new IllegalStateException("Cannot find resource " + resourceClaim.getName());
					}
				}
			}

	}


	// --------------------------------------------------------------------------------

	public CalculationModeEnum getCalculationMode() {
		return calculationMode;
	}

	public void setCalculationMode(CalculationModeEnum calculationMode) {
		this.calculationMode = calculationMode;
		handlePlanChange(PlanActionEnum.CALCULATION_MODE);
	}

	public boolean isAutoCalculated() {
		return isAutoCalculated;
	}

	public void setIsAutoCalculated(boolean autoCalculated) {
		this.isAutoCalculated = autoCalculated;
		if (!isCalculated) {	
			handlePlanChange(PlanActionEnum.IS_AUTOCALCULATED);
		}
	}

	// --------------------------------------------------------------------------------

	public boolean needsCalculation () {
		return !isCalculated;
	}
	
	public void calculate() {
		calculateBaseSchedules();
		alignAndUpdateResults();
		verifyConstraints();
		buildTaskLists();
		isCalculated = true;
	}
	
	public boolean maybeCalculate() {
		if (isAutoCalculated) {
			if (!isCalculated) {
				calculate();
			}
		}
		return isCalculated;
	}
	
	// --------------------------------------------------------------------------------

	public long getShortestDuration () {
		return finishTask.getEarliestFinish() - startTask.getLatestStart();
	}

	public long getNominalDuration () {
		return finishTask.getNominalFinish() - startTask.getNominalStart();
	}
	
	public long getLongestDuration () {
		return finishTask.getLatestFinish() - startTask.getEarliestStart();
	}
	
	// --------------------------------------------------------------------------------

	void calculateBaseSchedules () {
		calculatedResultKinds.clear();
		
		calculateNominalTimes();

		switch (calculationMode) {
		case CRITICAL_PATH:
			calculateMarginedCriticalPathTimes();
			break;
		case EAGER_PATH:
			calculateMarginedEagerPathTimes();
			break;
		case BOTH:
			calculateMarginedCriticalPathTimes();
			calculateMarginedEagerPathTimes();
			break;
		}
	}
	
	// --------------------------------------------------------------------------------

	void calculateNominalTimes() {
		calculateCriticalPathTimes(DurationKindEnum.NOMINAL,
								   CalculationResultEnum.NOMINAL);
		calculatedResultKinds.add(CalculationResultEnum.NOMINAL);
	}

	void calculateMarginedCriticalPathTimes() {
		calculateCriticalPathTimes(DurationKindEnum.SHORTEST,
								   CalculationResultEnum.CRITICAL_SHORTEST);
		calculatedResultKinds.add(CalculationResultEnum.CRITICAL_SHORTEST);
		
		calculateCriticalPathTimes(DurationKindEnum.LONGEST,
								   CalculationResultEnum.CRITICAL_LONGEST);
		calculatedResultKinds.add(CalculationResultEnum.CRITICAL_LONGEST);
	}

	void calculateCriticalPathTimes(DurationKindEnum durKind,
									CalculationResultEnum resultKind) {
		List<Task> readyList = new LinkedList<Task>();
		List<Task> remainingList = new LinkedList<Task>(allTasks);
		Task candidate;

		prepareToCalculateSuccessors(0);
		readyList.add(finishTask);
		while (!readyList.isEmpty()) {
			candidate = readyList.get(0);
			System.out.print("working on " + candidate + " "
							 + candidate.successors
							 + " " + durKind + " " + resultKind);
			if (candidate.workingLinksRemaining != 0) {
				throw new IllegalStateException("Candidate still has "
												+ candidate.workingLinksRemaining
												+ " unprocessed links");
			}
			candidate.workingStartTime = 
				candidate.workingFinishTime
				- candidate.getDuration(durKind);
			System.out.println(" fin " + candidate.workingFinishTime + " dur " + candidate.getDuration(durKind));
			for (Task pred : candidate.predecessors) {
				pred.workingFinishTime = Math.min(pred.workingFinishTime,
												  candidate.workingStartTime);
				pred.workingLinksRemaining--;
				if (pred.workingLinksRemaining == 0) {
					readyList.add(pred);
				}
			}
			System.out.println(" => Start " + candidate.workingStartTime);
			remainingList.remove(candidate);
			readyList.remove(candidate);
		}
		if (!remainingList.isEmpty()) {
			throw new IllegalStateException("Tasks remaining after calculating critical path");
		}

		adjustTimesToStartTime();
		stashResultTimes(resultKind);
	}

	// --------------------------------------------------------------------------------

	void calculateMarginedEagerPathTimes() {
		throw new IllegalStateException("Eager path not implemented");
	}

	// --------------------------------------------------------------------------------

	void prepareToCalculatePredecessors(long val) {
		for (Task tsk : allTasks) {
			tsk.workingLinksRemaining = tsk.predecessors.size();
			tsk.workingStartTime = val;
			tsk.workingFinishTime = val;
		}
	}

	void prepareToCalculateSuccessors(long val) {
		for (Task tsk : allTasks) {
			tsk.workingLinksRemaining = tsk.successors.size();
			tsk.workingStartTime = val;
			tsk.workingFinishTime = val;
		}
	}

	// --------------------------------------------------------------------------------

	void adjustTimesToStartTime() {
		long startStartTime = startTask.workingStartTime;
		for (Task tsk : allTasks) {
			tsk.workingStartTime -= startStartTime;
			tsk.workingFinishTime -= startStartTime;
		}
	}
	
	void stashResultTimes (CalculationResultEnum resultKind) {
		for (Task tsk: allTasks) {
			tsk.calculatedStartTimes[resultKind.ordinal()] = tsk.workingStartTime;
			tsk.calculatedFinishTimes[resultKind.ordinal()] = tsk.workingFinishTime;
		}
	}

	// --------------------------------------------------------------------------------

	void verifyConstraints() {
		for (Task tsk: allTasks) {
			if (tsk.hasAfterFinishConstrint) {
				long thisFinish = tsk.nominalFinishTime;
				long maxWait = tsk.afterFinishMaxWait;
				for (Task succ: tsk.successors) {
					if ((succ.nominalStartTime - thisFinish) > maxWait) {
						throw new IllegalStateException("Wait time violation");
					}	
				}
			}			
		}
	}
	
	// --------------------------------------------------------------------------------

	void alignAndUpdateResults () {
		calculateAlignment();
		updateNominalTimes();
		prepareToCalculateEarliestLatestTimes();
		updateEarliestLatestTimes();
	}
	
	void calculateAlignment() {
		Task referenceTimeTask;
		TaskTimeEnum refTime;
		
		if (pinnedTask != null) {
			referenceTimeTask = pinnedTask;
		}
		else {
			referenceTimeTask = startTask;
		}
		refTime = referenceTimeTask.pinTimeKind;
		if (refTime == null) {
			refTime = TaskTimeEnum.START;
		}
		
		for (CalculationResultEnum resultKind: calculatedResultKinds) {
			switch (refTime) {
			case START:
				resultAlignmentOffsets[resultKind.ordinal()] 
						= referenceTimeTask.calculatedStartTimes[CalculationResultEnum.NOMINAL.ordinal()];
				break;
			case FINISH:
				resultAlignmentOffsets[resultKind.ordinal()] 
						= referenceTimeTask.calculatedFinishTimes[CalculationResultEnum.NOMINAL.ordinal()];
				break;
			default:
				throw new IllegalStateException("Unknown pin time " + refTime);
			}
		}
		
		System.out.println("Alignments: " + resultAlignmentOffsets + " " + referenceTimeTask);
		for (CalculationResultEnum resultKind: calculatedResultKinds) {
			System.out.println(resultAlignmentOffsets[resultKind.ordinal()]);
		}

	}

	void updateNominalTimes() {
		for (Task tsk : allTasks) {
			tsk.updateNominalTimes(resultAlignmentOffsets);
		}
	}

	void prepareToCalculateEarliestLatestTimes() {
		for (Task tsk : allTasks) {
			tsk.prepareToCalculateEarliestLatestTimes();
		}
	}

	void updateEarliestLatestTimes() {
		for (Task tsk : allTasks) {
			tsk.updateEarliestLatestTimes(resultAlignmentOffsets, calculatedResultKinds);
		}
	}
	
	// --------------------------------------------------------------------------------

	void reset() {
		if (allTasks != null) {
			for (Task tsk : allTasks) {
				tsk.reset();
			}
		}
		pinnedTask = null;
		taskSchedule.clear();
		handlePlanChange(PlanActionEnum.RESET);
	}

	// --------------------------------------------------------------------------------

	void handlePlanChange (PlanActionEnum action) {
		if (isSetUp) {
			switch (action) {
			case SETUP:
				break;
				
			case SET_TASKS:
			case ADD_TASK:
			case REMOVE_TASK:
				setup();
				break;
				
			case SET_RESOURCE_POOLS:
			case ADD_RESOURCE_POOL:
			case REMOVE_RESOURCE_POOL:
				setup();
				break;
				
			case CALCULATION_MODE:
				break;
				
			case IS_AUTOCALCULATED:
				break;
				
			case RESET:
				setup();
				break;
				
			default:
				throw new IllegalStateException("Unhandled plan change.");
			}
			
			isCalculated = false;
			maybeCalculate();
		}
	}
	
	// --------------------------------------------------------------------------------
	// Handler for when you change some properties of a task
	
	void handleTaskChange(Task changedOne, TaskActionEnum action) {
		switch (action) {

		// ----------
		// Things you can change
		
		case PREDECESSOR_NAMES:
		case SUCCESSOR_NAMES:
			setup();
			break;
			
		case RESOURCE_CLAIMS:
			setup();
			break;

		case KIND:
		case VARIANCE_REASON:
		case SHORTEST_DURATION: 
		case NOMINAL_DURATION:
		case LONGEST_DURATION:
		case AFTER_FINISH_MAX_WAIT:
			break;
		
		// ----------
		// Things can do
			
		case PIN:
			pinnedTask = changedOne;
			absoluteTimeReference = pinnedTask.pinTime;
			for (Task tsk: allTasks) {
				if (tsk != changedOne) {
					tsk.retractPin();
				}
			}
			break;
			
		case UNPIN:
			pinnedTask = null;
			absoluteTimeReference = 0;
			break;
			
		case START:
		case FINISH:
			markPredecessorsFinished(changedOne);
			break;
				
		case RESET:
			break;
			
		default:
			throw new IllegalStateException("Unhandle task change");
		}
		
		isCalculated = false;
		maybeCalculate();
	}
	
	// --------------------------------------------------------------------------------
	// Mark dependent tasks as finished

	// If you take some action on a task, that implies that you finished all
	// of its predecessors so mark them. If you finished that task itself,
	// then mark it finished also.

	void markPredecessorsFinished(Task tsk) {
		for (Task pred : tsk.predecessors) {
			if (!pred.isFinished) {
				pred.isFinished = true;
				markPredecessorsFinished(pred);
			}
		}
	}

	// --------------------------------------------------------------------------------
	// Called by tasks when they want their absolute time
	
	long adjustedTaskTime(long time) {
		return time + absoluteTimeReference;
	}

	// --------------------------------------------------------------------------------
	// Task lists
	
	void buildTaskLists () {
		buildCompleteTaskList();
		buildActiveTaskList ();
	}
	
	// --------------------------------------------------------------------------------
	// Managing the active task list
	
	void buildCompleteTaskList () {
		taskSchedule.clear();
		taskSchedule.addAll(allTasks);
		Collections.sort(taskSchedule, nominalStartTimeAscendingComparator);
	}
	
	void updateTaskList () {
		Collections.sort(taskSchedule, nominalStartTimeAscendingComparator);
	}
	
	// --------------------------------------------------------------------------------
	// Managing the active task list
	
	void buildActiveTaskList () {
		activeTaskSchedule.clear();
		for (Task tsk: allTasks) {
			if (tsk.kind == TaskKindEnum.ACTIVE) {
				activeTaskSchedule.add(tsk);
			}
		}
		Collections.sort(activeTaskSchedule, nominalStartTimeAscendingComparator);
	}
	
	void updateActiveTaskList () {
		Collections.sort(activeTaskSchedule, nominalStartTimeAscendingComparator);
	}
	
	
	// --------------------------------------------------------------------------------
	// Printing
	
	public void show() {
		System.out.println("Plan: " + name);
		System.out.println("Description: " + description);
		int i = 1;
		for (Task act : allTasks) {
			Formatter formatter = new Formatter();
			act.Show(formatter.format("%2d. ", i).toString(), "    ");
			i++;
		}
	}
	
	public void showActive() {
		System.out.println("Plan: " + name);
		System.out.println("Description: " + description);
		int i = 1;
		for (Task act : activeTaskSchedule) {
			Formatter formatter = new Formatter();
			act.Show(formatter.format("%2d. ", i).toString(), "    ");
			i++;
		}
	}


}
