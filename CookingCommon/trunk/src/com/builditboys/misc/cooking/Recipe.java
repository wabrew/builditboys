package com.builditboys.misc.cooking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.builditboys.misc.planning.Plan;

class Recipe {
	
	// A short name of the recipe
	final String name;
	
	// A description of the recipe
	String description;
	
	// The result that is produced by the recipe
	String products;
	
	// Optional nutritional facts
	String nutritionFacts;
	
	// A plan that describes how to make the recipe
	Plan preparationPlan;
	
	// A description of how to serve the result of the recipe
	String servingInstructions;
	
	// Acknowledgements, contributors, ...
	List<Acknowledgement> acknowledgements = new ArrayList<Acknowledgement>();
	
	// Tags to help locate key features of the recipe
	Collection<Tag> tags = new ArrayList<Tag>();
	
	// Random notes about the recipe
	List<Note> notes = new ArrayList<Note>();
	
	// Similar or related recipes
	List<String> relatedRecipeNames = new ArrayList<String>();
	
	// Files that have photos of the recipe
	List<Photo> photos = new ArrayList<Photo>();
	

	// --------------------------------------------------------------------------------
	// Constructors
	
	Recipe (String name) {
		this.name = name;
	}
	
	// --------------------------------------------------------------------------------
	// Save / restore
	
	void save(String fileName) throws Exception {
		SimpleXMLRecipe.saveRecipe(this, fileName);
	}

	static Recipe restore(String fileName) throws Exception {
		return SimpleXMLRecipe.restoreRecipe(fileName);
	}
	

/*	

	// --------------------------------------------------------------------------------
// compute from the ingredients in each step
//	List<Ingredient> ingredients;
	
	// --------------------------------------------------------------------------------

	Set<Task> allPreparationSteps () {
		return preparationPlan.getTasks();
	}

	// --------------------------------------------------------------------------------
	long getNominalPreparationTime () {
		return preparationPlan.getNominalDuration();
	}
	
	long getActiveNominalPreparationTime () {
		List<Task> activeTasks = preparationPlan.getActiveTaskList();
		long accumulated = 0;
		
		for (Task tsk: activeTasks) {
			accumulated += tsk.getNominalDuration();
		}
		return accumulated;
	}
	
*/
	
	// --------------------------------------------------------------------------------
	// Printing
	
	void pln (String str) {
		System.out.println(str);
	}
	
	void p (String str) {
		System.out.print(str);
	}
	
	void show () {
		pln("Recipe name: " + name);
		pln("Description: " + description);
		pln("Products: " + products);
		pln("Nutrition facts: " + nutritionFacts);	
		preparationPlan.show();	
		pln("Serving instructions: " + servingInstructions);
		pln("Acknowledgements: ");
		for (Acknowledgement ack: acknowledgements) {
			p("  ");
			pln(ack.toString());
		}
		pln("Tags: ");
		for (Tag tag: tags) {
			p("  ");
			pln(tag.toString());
		}
		pln("Notes: ");
		for (Note note: notes) {
			p("  ");
			pln(note.toString());
		}
		pln("Related recipes: ");
		for (String rcp: relatedRecipeNames) {
			p("  ");
			pln(rcp);
		}
		pln("Photos: ");
		for (Photo photo: photos) {
			p("  ");
			pln(photo.toString());
		}
	}
	
}
