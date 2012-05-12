package com.builditboys.misc.cooking;

import com.builditboys.misc.planning.Plan;
import com.builditboys.misc.planning.Task;
import static com.builditboys.misc.planning.Task.*;


public class NoKneadBreadTest {
	
	public static void main (String args[]) {
		testSchedule();
	}

	static void testSchedule () {
		Plan schedule = new Plan("No Knead Bread", "Recipe for making No Knead Bread");
		Task q = new Task("Q",
				  "Q",
				  "",
				  Task.minuteDuration(1.9),
				  Task.minuteDuration(2.0),
				  Task.minuteDuration(2.1));
		Task a = new Task("A",
				  "A",
				  "",
				  Task.minuteDuration(2.9),
				  Task.minuteDuration(3.0),
				  Task.minuteDuration(3.1));
		Task x = new Task("X",
				  "X",
				  "",
				  Task.minuteDuration(3.9),
				  Task.minuteDuration(4.0),
				  Task.minuteDuration(4.1));
		Task f = new Task("F",
				  "F",
				  "",
				  Task.minuteDuration(1.9),
				  Task.minuteDuration(2.0),
				  Task.minuteDuration(2.1));
		schedule.addTask(q);
		schedule.addTask(a);
		schedule.addTask(x);
		schedule.addTask(f);
		schedule.setup();
		
//		schedule.calculate();
//		schedule.show();
		
//		schedule.calculate();
//		schedule.show();
		
		f.pinNominalStart();
		schedule.calculate();
		schedule.show();
		
//		schedule.unPin();
//		schedule.calculate();
//		schedule.show();
		
//		schedule.showActive();
		
	}

	/*
	static void testBread () {
		Schedule recipe = new Schedule("No Knead Bread", "Recipe for making No Knead Bread");
		Task mixIngrediants = new Task(TaskKindEnum.ACTIVE,
											   "Mix Ingrediants",
											   "Mix the following ingrediants:\n  1 cup bread flour\n  1 cup water",
											   Task.minuteDuration(6.0),
											   Task.minuteDuration(7.0),
											   Task.minuteDuration(10.0));
		Task firstRise = new Task(TaskKindEnum.PASSIVE,
				   						  "First rise",
				   						  "Let the bread dough rise in a covered bowl.",
				   						  Task.hourDuration(5.0),
				   						  Task.hourDuration(5.0),
				   						  Task.hourDuration(6.0));
		
		recipe.addTask(mixIngrediants);
		recipe.addTask(firstRise);
		
		recipe.Show();
	}
	
*/
	
}

