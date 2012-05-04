package com.builditboys.misc.planning;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.builditboys.misc.planning.Plan;
import com.builditboys.misc.planning.Plan.CalculationResultEnum;

public class Task {

	public static enum TaskKindEnum {
		ACTIVE, PASSIVE
	};
	
	public static enum TaskVarianceEnum {
		UNCERTAIN, DESCRETIONARY
	};

	public static enum TaskTimeEnum {
		START, FINISH
	};
	
	public enum TimeAdjustmentEnum { 
		DURATION, RELATIVE, ABSOLUTE
	};
	

	

	static enum DurationKindEnum {
		SHORTEST, NOMINAL, LONGEST
	};

	static enum TimeKindEnum {
		EARLIEST, NOMINAL, LATEST
	};
	
	// These are the actions you can perform on a task.  They
	// are communicated to the plan.
	static enum TaskActionEnum { 
		// You can change these
		KIND,
		RESOURCE_CLAIMS,
		VARIANCE_REASON, 
		PREDECESSOR_NAMES, SUCCESSOR_NAMES, 
		SHORTEST_DURATION, NOMINAL_DURATION, LONGEST_DURATION,
		AFTER_FINISH_MAX_WAIT,
		
		// You can do these
		RESET,
		PIN, UNPIN,
		START, FINISH };

	
	// ----------
	// User specified (mostly non changing) properties of tasks

	final String name;
	String description;

	TaskKindEnum kind = TaskKindEnum.ACTIVE;

	TaskVarianceEnum varianceReason = TaskVarianceEnum.UNCERTAIN;

	Set<String> predecessorNames = new HashSet<String>();
	Set<String> successorNames = new HashSet<String>();

	List<ResourceClaim> resourceClaims = new ArrayList<ResourceClaim>();

	List<MaterialClaim> materialClaims = new ArrayList<MaterialClaim>();

	long shortestDuration = -1;
	long nominalDuration = -1;
	long longestDuration = -1;

	boolean hasAfterFinishConstrint = false;
	long afterFinishMaxWait;

	

	// ----------
	// Computed or dynamic properties of tasks
	
	Plan containingPlan;

	Collection<Task> predecessors = new ArrayList<Task>();
	Collection<Task> successors = new ArrayList<Task>();

	boolean isPinned = false;
	TaskTimeEnum pinTimeKind;
	
	long earliestStartTime;
	long nominalStartTime;
	long latestStartTime;

	long earliestFinishTime;
	long nominalFinishTime;
	long latestFinishTime;

	boolean isStarted = false;
	boolean isFinished = false;
	long actualStartTime;
	long actualFinishTime;
	

	// ----------
	// Temporary values used during planning
	
	long workingStartTime;
	long workingFinishTime;
	
	long calculatedStartTimes[] = new long[CalculationResultEnum.values().length];
	long calculatedFinishTimes[] = new long[CalculationResultEnum.values().length];
	
	int workingLinksRemaining;

	// --------------------------------------------------------------------------------
	// Constructors
	
	public Task(String nm) {
		name = nm;
	}

	public Task(TaskKindEnum knd, String nm, String desc,
				long sDuration, long nDuration, long lDuration) {
		if (nm == null) {
			throw new IllegalArgumentException("Cannot have null task name");
		}
		kind = knd;
		name = nm;
		description = desc;
		shortestDuration = sDuration;
		nominalDuration = nDuration;
		longestDuration = lDuration;
	}

	public Task(TaskKindEnum knd, String nm, String desc, long nDuration) {
		kind = knd;
		name = nm;
		description = desc;
		shortestDuration = nDuration;
		nominalDuration = nDuration;
		longestDuration = nDuration;
	}

	// --------------------------------------------------------------------------------
	// Some basic public accessors
	
	// ----------
	
	public String getName() {
		return name;
	}
	
	// ----------
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// ----------

	public TaskKindEnum getKind() {
		return kind;
	}

	public void setKind(TaskKindEnum kind) {
		this.kind = kind;
		handleChange(TaskActionEnum.KIND);
	}

	// ----------

	public List<ResourceClaim> getResourceClaims() {
		return resourceClaims;
	}

	public void setResourceClaims(List<ResourceClaim> resourceClaims) {
		this.resourceClaims= resourceClaims;
		handleChange(TaskActionEnum.RESOURCE_CLAIMS);
	}
	
	public void addResourceClaim(ResourceClaim resourceClaim) {
		resourceClaims.add(resourceClaim);
		handleChange(TaskActionEnum.RESOURCE_CLAIMS);
	}
	
	public void removeResourceClaim(ResourceClaim resourceClaim) {
		resourceClaims.remove(resourceClaim);
		handleChange(TaskActionEnum.RESOURCE_CLAIMS);
	}

	// ----------

	public List<MaterialClaim> getMaterialClaims() {
		return materialClaims;
	}

	public void setMaterialClaims(List<MaterialClaim> materialClaims) {
		this.materialClaims= materialClaims;
		handleChange(TaskActionEnum.RESOURCE_CLAIMS);
	}
	
	public void addMaterialClaim(MaterialClaim materialClaim) {
		materialClaims.add(materialClaim);
		handleChange(TaskActionEnum.RESOURCE_CLAIMS);
	}
	
	public void removeMaterialClaim(MaterialClaim materialClaim) {
		materialClaims.remove(materialClaim);
		handleChange(TaskActionEnum.RESOURCE_CLAIMS);
	}

	// ----------

	public TaskVarianceEnum getVarianceReason() {
		return varianceReason;
	}

	public void setVarianceReason(TaskVarianceEnum varianceReason) {
		this.varianceReason = varianceReason;
		handleChange(TaskActionEnum.VARIANCE_REASON);
	}

	// ----------

	public Set<String> getPredecessorNames() {
		return predecessorNames;
	}

	public void setPredecessorNames(Set<String> predecessorNames) {
		this.predecessorNames = predecessorNames;
		handleChange(TaskActionEnum.PREDECESSOR_NAMES);
	}
	
	public void addPredecessorName(String predName) {
		predecessorNames.add(predName);
		handleChange(TaskActionEnum.PREDECESSOR_NAMES);
	}
	
	public void removePredecessorName(String predName) {
		predecessorNames.remove(predName);
		handleChange(TaskActionEnum.PREDECESSOR_NAMES);
	}
	
	public Collection<Task> getPredecessors () {
		return predecessors;
	}

	// ----------

	public Set<String> getSuccessorNames() {
		return successorNames;
	}

	public void setSuccessorNames(Set<String> successorNames) {
		this.successorNames = successorNames;
		handleChange(TaskActionEnum.SUCCESSOR_NAMES);
	}
	
	public void addSuccessorName(String succName) {
		successorNames.add(succName);
		handleChange(TaskActionEnum.SUCCESSOR_NAMES);
	}
	
	public void removeSuccessorName(String succName) {
		successorNames.remove(succName);
		handleChange(TaskActionEnum.SUCCESSOR_NAMES);
	}
	
	public Collection<Task> getSuccessors () {
		return successors;
	}

	// ----------

	public long getShortestDuration() {
		return shortestDuration;
	}

	public void setShortestDuration(long shortestDuration) {
		this.shortestDuration = shortestDuration;
		handleChange(TaskActionEnum.SHORTEST_DURATION);
	}

	// ----------

	public long getNominalDuration() {
		return nominalDuration;
	}

	public void setNominalDuration(long nominalDuration) {
		this.nominalDuration = nominalDuration;
		handleChange(TaskActionEnum.NOMINAL_DURATION);
	}
	
	// ----------

	public long getLongestDuration() {
		return longestDuration;
	}

	public void setLongestDuration(long longestDuration) {
		this.longestDuration = longestDuration;
		handleChange(TaskActionEnum.LONGEST_DURATION);
	}

	// ----------

	public boolean hasAfterFinishConstrint() {
		return hasAfterFinishConstrint;
	}

	public void setHasAfterFinishConstrint(boolean hasAfterFinishConstrint) {
		this.hasAfterFinishConstrint = hasAfterFinishConstrint;
		handleChange(TaskActionEnum.AFTER_FINISH_MAX_WAIT);
	}

	public long getAfterFinishMaxWait() {
		return afterFinishMaxWait;
	}

	public void setAfterFinishMaxWait(long afterFinishMaxWait) {
		this.afterFinishMaxWait = afterFinishMaxWait;
		handleChange(TaskActionEnum.AFTER_FINISH_MAX_WAIT);
	}

	// --------------------------------------------------------------------------------
	// Pinning a Task
	
	public boolean isPinned() {
		return isPinned;
	}

	public TaskTimeEnum getPinTimeKind() {
		return pinTimeKind;
	}
	

	
	public void unPin() {
		retractPin();
		handleChange(TaskActionEnum.UNPIN);
	}
	
	void retractPin() {
		isPinned = false;
		pinTimeKind = null;
	}

	public void pinNominalStart() {
		isPinned = true;
		pinTimeKind = TaskTimeEnum.START;
		handleChange(TaskActionEnum.PIN);
	}

	public void pinNominalFinish() {
		isPinned = true;
		pinTimeKind = TaskTimeEnum.FINISH;
		handleChange(TaskActionEnum.PIN);
	}

	// --------------------------------------------------------------------------------
	// Starting and Finishing a task
	
	// Starting a task or finishing a task marks it as such and also
	// changes how it is used in the scheduling calculation
	
	public boolean isStarted() {
		return isStarted;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public long getActualStartTime() {
		return actualStartTime;
	}

	public long getActualFinishTime() {
		return actualFinishTime;
	}

	public void startTask (long when) {
		actualStartTime = when;
		isStarted = true;
		handleChange(TaskActionEnum.START);
	}
	
	public void finishTask (long when) {
		actualFinishTime = when;
		isStarted = true;  // in case you never marked it
		isFinished = true;
		handleChange(TaskActionEnum.FINISH);
	}
	
	// --------------------------------------------------------------------------------
	// Getting the various start and finish times
	
	public long getNominalStartTime(TimeAdjustmentEnum adjustment) {
		return adjustedTime(nominalStartTime, adjustment);
	}
		
	public long getNominalFinishTime(TimeAdjustmentEnum adjustment) {
		return adjustedTime(nominalFinishTime, adjustment);
	}

	public long getEarliestStartTime(TimeAdjustmentEnum adjustment) {
		return adjustedTime(earliestStartTime, adjustment);
	}

	public long getEarliestFinishTime(TimeAdjustmentEnum adjustment) {
		return adjustedTime(earliestFinishTime, adjustment);
	}

	public long getLatestStartTime(TimeAdjustmentEnum adjustment) {
		return adjustedTime(latestStartTime, adjustment);
	}

	public long getLatestFinishTime(TimeAdjustmentEnum adjustment) {
		return adjustedTime(latestFinishTime, adjustment);
	}
	
	long adjustedTime (long time, TimeAdjustmentEnum adjustment) {
		return containingPlan.adjustedTaskTime(time, adjustment);
	}

	// --------------------------------------------------------------------------------
	// Time strings
		
	public String getNominalStartTimeString(TimeAdjustmentEnum adjustment) {
		return formatTimeString(nominalStartTime, adjustment);
	}

	public String getNominalFinishTimeString(TimeAdjustmentEnum adjustment) {
		return formatTimeString(nominalFinishTime, adjustment);
	}
	
	public String getEarliestStartTimeString(TimeAdjustmentEnum adjustment) {
		return formatTimeString(earliestStartTime, adjustment);
	}

	public String getEarliestFinishTimeString(TimeAdjustmentEnum adjustment) {
		return formatTimeString(earliestFinishTime, adjustment);
	}

	public String getLatestStartTimeString(TimeAdjustmentEnum adjustment) {
		return formatTimeString(latestStartTime, adjustment);
	}

	public String getLatestFinishTimeString(TimeAdjustmentEnum adjustment) {
		return formatTimeString(latestFinishTime, adjustment);
	}

	String formatTimeString (long time, TimeAdjustmentEnum adjustment) {
		return containingPlan.formatTaskTimeString(time, adjustment);
	}
	
	// --------------------------------------------------------------------------------
	// A few duration utilities
	
	public static long minuteDuration(double minutes) {
		return (long) (minutes * (60.0 * 1000.0));
	}
	
	public static double durationMinutes (long duration) {
		return (double) (((double) duration) / (60.0 *1000.0));
	}

	public static long hourDuration(double hours) {
		return minuteDuration(hours * 60.0);
	}

	public static long dayDuration(double days) {
		return hourDuration(days * 24.0);
	}

	// --------------------------------------------------------------------------------

	public double showableDuration (long time) {
		return (double) Math.round((((double) time) / (60.0 * 1000.0)) * 10.0) / 10.0;
	}
	
	public double showableTime (long time) {
		return (double) Math.round((((double) time) / (60.0 * 1000.0)) * 10.0) / 10.0;
	}

	// --------------------------------------------------------------------------------
	// Internal accessors
	
	Plan getContainingPlan() {
		return containingPlan;
	}

	void setContainingPlan(Plan containingPlan) {
		this.containingPlan = containingPlan;
	}

	// --------------------------------------------------------------------------------
	// Duration fix up
	
	void normalizeDurations () {
		if (nominalDuration < 0) {
			throw new IllegalStateException("Negative duration value for " + name);
		}
		if (shortestDuration < 0) {
			shortestDuration = nominalDuration;
		}
		if (longestDuration < 0) {
			longestDuration = nominalDuration;
		}
	}

	
	// --------------------------------------------------------------------------------
	// Handler for various task actions
	
	void handleChange(TaskActionEnum action) {
		if (containingPlan != null) {
			containingPlan.handleTaskChange(this, action);
		}
	}

	// --------------------------------------------------------------------------------
	// Reset
	
	void reset() {
		isPinned = false;
		pinTimeKind = null;

		earliestStartTime = 0;
		nominalStartTime = 0;
		latestStartTime = 0;

		earliestFinishTime = 0;
		nominalFinishTime = 0;
		latestFinishTime = 0;

		isStarted = false;
		isFinished = false;
		actualStartTime = 0;
		actualFinishTime = 0;
		
		workingStartTime = 0;
		workingFinishTime = 0;
		
		int len = CalculationResultEnum.values().length;
		for (int i = 0; i < len; i++) {
			calculatedStartTimes[i] = 0;
			calculatedFinishTimes[i] = 0;
		}
		
		workingLinksRemaining = 0;
		
		handleChange(TaskActionEnum.RESET);
	}

	// --------------------------------------------------------------------------------
	// Indirect gets and puts for duration
	
	long getDuration(DurationKindEnum which) {
		if (isFinished) {
			return 0;
		} else {
			switch (which) {
			case SHORTEST:
				return shortestDuration;
			case NOMINAL:
				return nominalDuration;
			case LONGEST:
				return longestDuration;
			default:
				throw new IllegalArgumentException("Unknown duration kind");
			}
		}
	}

	void setDuration(DurationKindEnum which, long dur) {
		switch (which) {
		case SHORTEST:
			shortestDuration = dur;
			break;
		case NOMINAL:
			nominalDuration = dur;
			break;
		case LONGEST:
			longestDuration = dur;
			break;
		default:
			throw new IllegalArgumentException("Unknown duration kind");
		}
	}

	// --------------------------------------------------------------------------------
	// Indirect gets and puts for times
	
	long getStartTime(TimeKindEnum which) {
		switch (which) {
		case EARLIEST:
			return earliestStartTime;
		case NOMINAL:
			return nominalStartTime;
		case LATEST:
			return latestStartTime;
		default:
			throw new IllegalArgumentException("Unknown time kind");
		}
	}

	void setStartTime(TimeKindEnum which, long time) {
		switch (which) {
		case EARLIEST:
			earliestStartTime = time;
			break;
		case NOMINAL:
			nominalStartTime = time;
			break;
		case LATEST:
			latestStartTime = time;
			break;
		default:
			throw new IllegalArgumentException("Unknown time kind");
		}
	}

	long getFinishTime(TimeKindEnum which) {
		switch (which) {
		case EARLIEST:
			return earliestFinishTime;
		case NOMINAL:
			return nominalFinishTime;
		case LATEST:
			return latestFinishTime;
		default:
			throw new IllegalArgumentException("Unknown time kind");
		}
	}

	void setFinishTime(TimeKindEnum which, long time) {
		switch (which) {
		case EARLIEST:
			earliestFinishTime = time;
			break;
		case NOMINAL:
			nominalFinishTime = time;
			break;
		case LATEST:
			latestFinishTime = time;
			break;
		default:
			throw new IllegalArgumentException("Unknown time kind");
		}
	}

	// --------------------------------------------------------------------------------
	// A few utilities used by the scheduler
	
	void prepareToCalculateEarliestLatestTimes () {
		earliestStartTime = Long.MAX_VALUE;
		latestStartTime = Long.MIN_VALUE;

		earliestFinishTime = Long.MAX_VALUE;
		latestFinishTime = Long.MIN_VALUE;
	}

	void updateNominalTimes(long resultAlignmentOffsets[]) {
		nominalStartTime = calculatedStartTimes[CalculationResultEnum.NOMINAL.ordinal()]
				           - resultAlignmentOffsets[CalculationResultEnum.NOMINAL.ordinal()];
		nominalFinishTime = calculatedFinishTimes[CalculationResultEnum.NOMINAL.ordinal()]
					        - resultAlignmentOffsets[CalculationResultEnum.NOMINAL.ordinal()];
	}

	void updateEarliestLatestTimes(long resultAlignmentOffsets[],
											 Set<CalculationResultEnum> calculatedResultKinds) {
		long calculatedStartTime;
		long calculatedFinishTime;
		
		System.out.println("Task " + name + " times:");
		System.out.println(" " + showableTime(earliestStartTime) + " "
							   + showableTime(earliestFinishTime) + " "
							   + showableTime(latestStartTime) + " "
							   + showableTime(latestFinishTime));
		for (CalculationResultEnum resultKind: calculatedResultKinds) {
			calculatedStartTime = calculatedStartTimes[resultKind.ordinal()]
								  - resultAlignmentOffsets[resultKind.ordinal()];;
			calculatedFinishTime = calculatedFinishTimes[resultKind.ordinal()]
					               - resultAlignmentOffsets[resultKind.ordinal()];;
			
			earliestStartTime = Math.min(earliestStartTime, calculatedStartTime);
			latestStartTime = Math.max(latestStartTime, calculatedStartTime);

			earliestFinishTime = Math.min(earliestFinishTime, calculatedFinishTime);
			latestFinishTime = Math.max(latestFinishTime, calculatedFinishTime);

			System.out.println(" " + showableTime(earliestStartTime) + " "
					   			   + showableTime(earliestFinishTime) + " "
					   			   + showableTime(latestStartTime) + " "
					   			   + showableTime(latestFinishTime));
		}
	}


	// --------------------------------------------------------------------------------
	// Printing
	
	public String toString() {
		return "Task \"" + name + "\"";
	}

	public void Show(String prefix1, String prefixn) {
		System.out.println(prefix1 + " Task: " + name);
		System.out.println(prefixn + " " + "Description: " + description);
		System.out.println(prefixn + " " + "Kind: " + kind);
		System.out.println(prefixn + " " + "Durations: " 
						   + showableDuration(shortestDuration) + " "
						   + showableDuration(nominalDuration) + " "
						   + showableDuration(longestDuration));
		System.out.println(prefixn + " " + "Pinned: " + isPinned);
		System.out.println(prefixn + " " + "Variance reason: " + varianceReason);
		System.out.println(prefixn + " " + "Predecessors: " + predecessorNames);
		System.out.println(prefixn + " " + "Successors: " + successorNames);
		System.out.println(prefixn + " " + "Start:  " 
						   + showableTime(earliestStartTime) + " " 
				           + showableTime(nominalStartTime) + " " 
						   + showableTime(latestStartTime) + " / "
					       + showableTime(actualStartTime) + "   "
					       + getNominalStartTimeString(TimeAdjustmentEnum.RELATIVE));
		System.out.println(prefixn + " " + "Finish: " 
					       + showableTime(earliestFinishTime) + " " 
					       + showableTime(nominalFinishTime) + " " 
					       + showableTime(latestFinishTime) + " / "
					       + showableTime(actualFinishTime) + "   "
					       + getNominalFinishTimeString(TimeAdjustmentEnum.RELATIVE));
	}

}
