package com.builditboys.misc.cooking;

import com.builditboys.misc.planning.Plan;
import com.builditboys.misc.planning.ResourceClaim;
import com.builditboys.misc.planning.MaterialClaim;
import com.builditboys.misc.planning.Task;
import com.builditboys.misc.planning.Task.TaskKindEnum;
import com.builditboys.misc.units.TimeUnits;

public class RecipeSaveRestoreTest {
	
	static public void main (String args[]) throws Exception {
		testSaveRestore();
	}
	
	static void testSaveRestore() throws Exception {
		Recipe originalRecipe = new Recipe("Recipe Name");
		Recipe restoredRecipe;
		
		Plan preparationPlan = new Plan();
		Task step1 = new Task("Step 1");
		Task step2 = new Task("Step 2");
		
		step1.setName("Step 1 Name");
		step1.setDescription("Step 1 Description");
		step1.setKind(TaskKindEnum.ACTIVE);
		step1.setNominalDuration(Task.minuteDuration(2.0));
		step1.addResourceClaim(new ResourceClaim("mixer", 1.0));
		step1.addResourceClaim(new ResourceClaim("large bowl", 1.0));
		step1.addMaterialClaim(new MaterialClaim("Flour", 1.0, CookingUnits.CUP));
		
		step2.setName("Step 2 Name");
		step2.setDescription("Step 2 Description");
		step2.setKind(TaskKindEnum.ACTIVE);
		step2.setNominalDuration(Task.minuteDuration(1.0));
		step2.addResourceClaim(new ResourceClaim("oven", 1.0));
		step2.addResourceClaim(new ResourceClaim("oven mitt", 1.0));
		step2.addMaterialClaim(new MaterialClaim("salt", 1.0, CookingUnits.TABLESPOON));
		
		step1.addSuccessorIdentifier("Step 2");
		step2.addPredecessorIdentifier("Step 1");
		
		preparationPlan.addTask(step1);
		preparationPlan.addTask(step2);
		preparationPlan.setup();
		preparationPlan.calculate();
		
//		recipe.name = "Recipe Name";
		originalRecipe.description = "Recipe Description";
		originalRecipe.products = "Products";
		originalRecipe.nutritionFacts = "Nutrition Facts";
		originalRecipe.preparationPlan = preparationPlan;
		originalRecipe.servingInstructions = "Serving Instructions";
		originalRecipe.acknowledgements.add(new Acknowledgement("Jane Volk-Brew", "JaneCo", "Card box", "URL"));
		originalRecipe.tags.add(new Tag("meal", "dinner"));
		originalRecipe.tags.add(new Tag("key ingredient", "food"));
		originalRecipe.tags.add(new Tag("rating", "excellent"));
		originalRecipe.notes.add(new Note("Jane", 22, "This is a note"));
		originalRecipe.relatedRecipeNames.add("some other recipe");
		originalRecipe.photos.add(new Photo("The final product -- looks good", "some file", 200, 200));
		
		
		originalRecipe.show();
		originalRecipe.save("example-recipe.xml");
		
		restoredRecipe = Recipe.restore("example-recipe.xml");
		restoredRecipe.preparationPlan.setup();
		restoredRecipe.preparationPlan.calculate();
		restoredRecipe.preparationPlan.setTimeStringMaker(new TimeStringMaker());
		restoredRecipe.preparationPlan.setRelativeTimeAdjustment(8.0, TimeUnits.HOUR);
		restoredRecipe.show();
		
	}

}
