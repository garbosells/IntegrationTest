import Classes.Listing.Item.*;
import Classes.Template.Attribute;
import Classes.Template.Size;
import Classes.Template.Template;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Index {
    static Template template;
    static Listing newListing;
    static Scanner input;
    static Methods methods;
    public static void main(String[] args) throws IOException {
        methods = new Methods();
        int choice = 4;
        input = new Scanner(System.in);
        do {
            ChoiceMenu();
            choice = input.nextInt();
            switch (choice) {
                case 0:
                    DisplayAllListings();
                    break;
                case 1:
                    System.out.println("Enter the id of the item you would like to retrieve");
                    DisplayListingById(input.nextInt());
                    break;
                case 2:
                    System.out.println("What is the id of the listing you would like to update?");
                    UpdateListing(input.nextInt());
                    break;
                case 3:
                    SaveListing();
                    break;
            }
        }while(choice != 4);
    }


    private static void ChoiceMenu(){
        System.out.println("Choose what you would you like to do:");
        System.out.println("0: Get all listings");
        System.out.println("1: Get a listing by id");
        System.out.println("2: Update an Existing listing");
        System.out.println("3: Save a new listing");
        System.out.println("4: I'm done");
    }

    private static void SubChoiceMenu(){
        System.out.println("What would you like to update for this listing?");
        System.out.println("a: Short Description");

        System.out.println("b: Long Description");
    }

    private static void DisplayAllListings() throws IOException{
        var listings = methods.GetAllListings();
        listings.forEach(l-> {
           System.out.println(l);
        });
    }

    private static void DisplayListingById(int id) throws IOException{
        System.out.println(methods.GetListingById(id).toString());
    }

    private static void UpdateListing(int id) throws IOException{
        SubChoiceMenu();
        Listing lu = methods.GetListingById(id);
        switch(input.next()){
            case "a":
                System.out.println("Enter the new Short Description");
                input.nextLine();
                lu.inventoryItem.shortDescription = input.nextLine();
                break;
            case "b":
                System.out.println("Enter the new Long Description");
                input.nextLine();
                lu.inventoryItem.longDescription = input.nextLine();
                break;
        }
        lu.inventoryItem.updatedByUserId=0;
        methods.PutListing(lu);
    }

    private static void SaveListing() throws IOException{
        newListing = new Listing();
        newListing.inventoryItem = new Item();
        newListing.inventoryItem.createdDateTime = new Date();
        newListing.inventoryItem.updatedDateTime = new Date();
        var subcategoryId = GetCategoryAndSubcategory();
        template = methods.GetTemplateBySubcategoryId(subcategoryId); //should actually be done asychronously
        newListing.inventoryItem.categoryId = template.category.id;
        newListing.inventoryItem.subcategoryId = template.subcategory.id;
        newListing.inventoryItem.createdByUserId = 0;
        newListing.inventoryItem.updatedByUserId = 0;
        GetAndSetMeasurements();
        GetAndSetSize();
        GetEra();
        GetColors();
        GetMaterial();
        GetSubcategoryAttributes();
        System.out.println("You're entering a new item in " + template.subcategory.description);
        //Get the short description (listing title)
        System.out.println("Enter the listing title: ");
        newListing.inventoryItem.shortDescription=input.nextLine();
        System.out.println("Enter the listing text (long description):");
        newListing.inventoryItem.longDescription=input.nextLine();
        methods.PostListing(newListing);
    }

    private static long GetCategoryAndSubcategory() throws IOException {
        var categories = methods.GetCategories();
        System.out.println("Select a category. Enter the ID.");
        categories.forEach(c -> {
            System.out.println(c.id + ": " + c.description);
        });
        var categoryId = input.nextLong();
        input.nextLine();
        var subcategories = methods.GetSubcategoriesByCategoryId(categoryId);

        System.out.println("Select a subcategory. Enter the ID");
        subcategories.forEach(c -> {
            System.out.println(c.id + ": " + c.description);
        });
        var subcategoryId = input.nextLong();
        input.nextLine();

        return subcategoryId;
    };

    /*
    * Size will need to be a special component in the UI.
    * It should consist of two spinners.
    * The first will prompt the user to enter the "size type" - this could be US numeric, letter, UK sizes, etc.
    * The size types are provided in the template.
    *
    * Once the user has chosen the "type," the second spinner should be populated with options for values.
    * The user cannot enter a manual value, so this is all that is needed.
    *
    * Once the user has made a selection, the Item object's generalAttributes.size should be set with the sizeTypeId and sizeValueId
    * */
    private static void GetAndSetSize() {
        System.out.println("What type of size? Enter the ID");
        template.sizeOptions.forEach((s) -> {
            System.out.println(s.sizeTypeId + ": " + s.sizeTypeDescription);
        });

        var sizeTypeId = input.nextLong();
        Size sizeType = template.sizeOptions.stream().filter((s) -> s.sizeTypeId == sizeTypeId).findFirst().get();
        input.nextLine();

        System.out.println("What size? Enter the ID");
        sizeType.recommendations.forEach(s -> {
            System.out.println(s.id + ": Size " + s.description);
        });
        var sizeValueId = input.nextLong();
        input.nextLine();

        //Set the size
        newListing.inventoryItem.size = new ItemSize(sizeTypeId, sizeValueId);
    }

    private static void GetAndSetMeasurements() {
        var measurementTemplates = template.measurements;
        System.out.println("New enter each of these measurements. Numbers and decimal points only, please.");
        measurementTemplates.forEach(m -> {
            System.out.println(m.description + " (" + m.hint + "):");
            var value = input.nextDouble();
            input.nextLine();

            //if we were editing an existing item/measurement, the id would already exist
            //since this is a new item, there is no id for this. it is set by the database
            newListing.inventoryItem.measurements.add(new ItemMeasurement(m.id, null, value));
        });
    }

    private static void GetColors() {
        var colors = template.generalAttributes.primaryColor.recommendations;
        System.out.println("Select a primary color by the ID. If N/A, enter -1:");
        colors.forEach(c -> {
            System.out.println(c.id + ": " + c.description);
        });
        var primaryColorId = input.nextLong();
        input.nextLine();
        if(primaryColorId >= 0) {
            var primaryColor = new ItemAttribute();
            primaryColor.attributeRecommendationId = primaryColorId;
            newListing.inventoryItem.generalItemAttributes.primaryColor = primaryColor;

            System.out.println("Enter the ID for the secondary color from the list above. If N/A, enter -1:");
            var secondaryColorId = input.nextLong();
            input.nextLine();
            if(secondaryColorId >= 0) {
                var secondaryColor = new ItemAttribute();
                secondaryColor.attributeRecommendationId = secondaryColorId;
                newListing.inventoryItem.generalItemAttributes.secondaryColor = secondaryColor;
            }
        }
    }

    private static void GetEra() {
        var eras = template.generalAttributes.era;
        System.out.println("Select the era in which this item was made, by ID:");
        eras.recommendations.forEach((e) -> {
            System.out.println(e.id + ": " + e.description);
        });
        var eraId = input.nextLong();
        var newEra = new ItemAttribute();
        newEra.attributeRecommendationId = eraId;
        newListing.inventoryItem.generalItemAttributes.era = newEra;
    }

    private static void GetMaterial() {
        var materials = template.generalAttributes.material.recommendations;
        System.out.println("Enter the material by ID. If N/A, enter -1:");
        materials.forEach(m -> {
            System.out.println(m.id + ": " + m.description);
        });
        var materialId = input.nextLong();
        input.nextLine();
        if(materialId >= 0) {
            var material = new ItemAttribute();
            material.attributeRecommendationId = materialId;
            newListing.inventoryItem.generalItemAttributes.material = material;
        }
    }

    private static void GetSubcategoryAttributes() {
        var subcategoryAttributes = template.subcategory.attributes;
        if(!subcategoryAttributes.isEmpty()) {
            System.out.println("For each attribute, enter the ID of the desired value.");
            subcategoryAttributes.forEach(a -> {
                ItemAttribute attribute = null;
                if(a.uiInputId == 0) {
                    attribute = GetAttributeValueFromSpinner(a);
                } else if (a.uiInputId == 5) {
                    attribute = GetAttributeValueFromSeekbar(a);
                }
                if(attribute != null)
                    newListing.inventoryItem.attributes.add(attribute);
            });
        }
    }

    private static ItemAttribute GetAttributeValueFromSpinner(Attribute attribute) {
        var recommendations = attribute.recommendations;
        System.out.println("Attribute: " + attribute.displayText);
        recommendations.forEach(r -> {
            System.out.println(r.id + ": " + r.description);
        });
        var selectionId = input.nextLong();
        input.nextLine();
        if(selectionId >= 0) {
            var itemAttribute = new ItemAttribute();
            itemAttribute.attributeRecommendationId = selectionId;
            itemAttribute.subcategoryAttributeId = attribute.id;
            return  itemAttribute;
        }
        return null;
    }

    private static ItemAttribute GetAttributeValueFromSeekbar(Attribute attribute) {
        System.out.println("Attribute: " + attribute.description);
        System.out.println("T/F?");
        var selection = input.nextLine();
        var value = selection.charAt(0) == 'T' || selection.charAt(0) == 't';
        var itemAttribute = new ItemAttribute();
        itemAttribute.itemAttributeValue = value ? "T" : "F";
        itemAttribute.subcategoryAttributeId = attribute.id;
        return itemAttribute;
    }
}
