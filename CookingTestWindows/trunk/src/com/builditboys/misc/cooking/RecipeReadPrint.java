package com.builditboys.misc.cooking;

import com.builditboys.misc.units.TimeUnits;

public class RecipeReadPrint {
	
	static public void main (String args[]) throws Exception {
		testRestore();
	}
	
	static void testRestore() throws Exception {
		Recipe restoredRecipe;
		
		System.out.println(System.currentTimeMillis());
		
		restoredRecipe = Recipe.restore("example-recipe3.xml");
		restoredRecipe.preparationPlan.setup();
		restoredRecipe.preparationPlan.calculate();
		restoredRecipe.preparationPlan.setTimeStringMaker(new TimeStringMaker());
		restoredRecipe.preparationPlan.setRelativeTimeAdjustment(8.0, TimeUnits.HOUR);
		restoredRecipe.show();


	}

}
