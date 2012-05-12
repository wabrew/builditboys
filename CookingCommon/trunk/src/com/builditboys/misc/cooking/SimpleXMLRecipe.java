package com.builditboys.misc.cooking;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.builditboys.misc.planning.SimpleXMLPlan;
import com.builditboys.misc.units.AbstractUnit;


@Root
class SimpleXMLRecipe {
	
	@Element
	String name;
	
	@Element(required=false,data=true)
	String description;
	
	@Element(required=false)
	String products;

	@Element(required=false)
	String nutritionFacts;

	@Element(required=false)
	SimpleXMLPlan preparationPlan;

	@Element(required=false)
	String servingInstructions;
		
	@ElementList(required=false)
	List<SimpleXMLAcknowledgement> acknowledgements;
		
	@ElementList(required=false)
	Collection<SimpleXMLTag> tags;
	
	@ElementList(required=false)
	List<SimpleXMLNote> notes;
	
	@ElementList(required=false)
	List<String> relatedRecipeNames;
	
	@ElementList(required=false)
	List<SimpleXMLPhoto> photos;
	

	static Serializer serializer = new Persister();
	static File recipeFolder = new File("Z:/Users/Bill/recipes");
	
	// Hack, when reading a recipe file, you need to be able to lookup units.
	// However, the class loader might not have loaded the appropriate units
	// classes before you start the restore.  This forces the cooking units
	// class to be loaded.  Also seems to load the other units.
	
	static {
		AbstractUnit dummy = CookingUnits.CUP;
	}

	// --------------------------------------------------------------------------------
	// Input
	
	static Recipe restoreRecipe (String fileName) throws Exception {
		SimpleXMLRecipe xmlRecipe = read(fileName);
		return constructRecipe(xmlRecipe);
	}
	
	static SimpleXMLRecipe read (String fileName) throws Exception {
		File file = new File(recipeFolder, fileName);
		return serializer.read(SimpleXMLRecipe.class, file);		
	}
	
	// --------------------------------------------------------------------------------
	// Output
	
	static void saveRecipe (Recipe recipe, String fileName) throws Exception {
		SimpleXMLRecipe xmlRecipe = constructXMLRecipe(recipe);
		write(xmlRecipe, fileName);
	}

	static void write (SimpleXMLRecipe xmlRecipe, String fileName) throws Exception {
		File file = new File(recipeFolder, fileName);
		serializer.write(xmlRecipe, file);
	}
	
	// --------------------------------------------------------------------------------

	static Recipe constructRecipe (SimpleXMLRecipe xmlRecipe) {
		Recipe recipe = new Recipe(xmlRecipe.name);
		
//		recipe.name = xmlRecipe.name;
		recipe.description = xmlRecipe.description;
		recipe.products = xmlRecipe.products;
		recipe.nutritionFacts = xmlRecipe.nutritionFacts;
		recipe.preparationPlan = SimpleXMLPlan.constructPlan(xmlRecipe.preparationPlan);
		recipe.servingInstructions = xmlRecipe.servingInstructions;
		recipe.acknowledgements = constructAcknowledgements(xmlRecipe);
		recipe.tags = constructTags(xmlRecipe);
		recipe.notes = constructNotes(xmlRecipe);
		recipe.relatedRecipeNames = xmlRecipe.relatedRecipeNames;
		recipe.photos = constructPhotos(xmlRecipe);
		
		return recipe;
	}
	
	static List<Acknowledgement> constructAcknowledgements (SimpleXMLRecipe xmlRecipe) {
		List<Acknowledgement> acknowledgements = new ArrayList<Acknowledgement>();
		for (SimpleXMLAcknowledgement acknowledgement: xmlRecipe.acknowledgements) {
			acknowledgements.add(SimpleXMLAcknowledgement.constructAcknowledgement(acknowledgement));
		}
		return acknowledgements;
	}
	
	static Collection<Tag> constructTags (SimpleXMLRecipe xmlRecipe) {
		Collection<Tag> tags = new ArrayList<Tag>();
		for (SimpleXMLTag tag: xmlRecipe.tags) {
			tags.add(SimpleXMLTag.constructTag(tag));
		}
		return tags;
	}

	static List<Note> constructNotes (SimpleXMLRecipe xmlRecipe) {
		List<Note> notes = new ArrayList<Note>();
		for (SimpleXMLNote note: xmlRecipe.notes) {
			notes.add(SimpleXMLNote.constructNote(note));
		}
		return notes;
	}

	static List<Photo> constructPhotos (SimpleXMLRecipe xmlRecipe) {
		List<Photo> photos = new ArrayList<Photo>();
		for (SimpleXMLPhoto Photo: xmlRecipe.photos) {
			photos.add(SimpleXMLPhoto.constructPhoto(Photo));
		}
		return photos;
	}

	// --------------------------------------------------------------------------------

	static SimpleXMLRecipe constructXMLRecipe (Recipe recipe) {
		SimpleXMLRecipe xmlRecipe = new SimpleXMLRecipe();
		
		xmlRecipe.name = recipe.name;
		xmlRecipe.description = recipe.description;
		xmlRecipe.products = recipe.products;
		xmlRecipe.nutritionFacts = recipe.nutritionFacts;
		xmlRecipe.preparationPlan = SimpleXMLPlan.constructXMLPlan(recipe.preparationPlan);
		xmlRecipe.servingInstructions = recipe.servingInstructions;
		xmlRecipe.acknowledgements = constructXMLAcknowledgements(recipe);
		xmlRecipe.tags = constructXMLTags(recipe);
		xmlRecipe.notes = constructXMLNotes(recipe);
		xmlRecipe.relatedRecipeNames = recipe.relatedRecipeNames;
		xmlRecipe.photos = constructXMLPhotos(recipe);
		
		return xmlRecipe;		
	}
	
	static List<SimpleXMLAcknowledgement> constructXMLAcknowledgements (Recipe recipe) {
		List<SimpleXMLAcknowledgement> acknowledgements = new ArrayList<SimpleXMLAcknowledgement>();
		for (Acknowledgement acknowledgement: recipe.acknowledgements) {
			acknowledgements.add(SimpleXMLAcknowledgement.constructXMLAcknowledgement(acknowledgement));
		}
		return acknowledgements;
	}
	
	static Collection<SimpleXMLTag> constructXMLTags (Recipe recipe) {
		Collection<SimpleXMLTag> tags = new ArrayList<SimpleXMLTag>();
		for (Tag tag: recipe.tags) {
			tags.add(SimpleXMLTag.constructXMLTag(tag));
		}
		return tags;
	}

	static List<SimpleXMLNote> constructXMLNotes (Recipe recipe) {
		List<SimpleXMLNote> notes = new ArrayList<SimpleXMLNote>();
		for (Note note: recipe.notes) {
			notes.add(SimpleXMLNote.constructXMLNote(note));
		}
		return notes;
	}
	
	static List<SimpleXMLPhoto> constructXMLPhotos (Recipe recipe) {
		List<SimpleXMLPhoto> photos = new ArrayList<SimpleXMLPhoto>();
		for (Photo photo: recipe.photos) {
			photos.add(SimpleXMLPhoto.constructXMLPhoto(photo));
		}
		return photos;
	}

	
	// --------------------------------------------------------------------------------
	// Printing
	
	void show () {
		System.out.println("XML Recipe: " + name);
		System.out.println("Description: " + description);
	}

}
