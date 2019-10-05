import Classes.Listing.Item.Item;
import Classes.Listing.Item.ItemMeasurement;
import Classes.Listing.Item.ItemSize;
import Classes.Template.Size;
import Classes.Template.Template;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class Index {
    static Template template;
    static Item newItem;
    static Scanner input;
    static Methods methods;
    public static void main(String[] args) throws IOException {
        methods = new Methods();
        newItem = new Item();

        input = new Scanner(System.in);
        //this is a new item being created, set its creation datetime
        newItem.createdDateTime = new Date();
        newItem.updatedDateTime = newItem.createdDateTime;

        //set the user who created/updated the item based on who is logged in
        //note - this is the userId, not userLoginId
        newItem.createdByUserId = 0;
        newItem.updatedByUserId = newItem.createdByUserId;

        //This is a new item. The id should be set by the database (autoincrementing)
        //If this were an existing item being edited, then we would have a value here that would not be modified ever
        newItem.id = -1;

        var subcategoryId = GetCategoryAndSubcategory();
        template = methods.GetTemplateBySubcategoryId(subcategoryId); //should actually be done asychronously

        //set category/subcategory
        newItem.categoryId = template.category.id;
        newItem.subcategoryId = template.subcategory.id;

        System.out.println("You're entering a new item in " + template.subcategory.description);

        //Get the short description (listing title)
        System.out.println("Enter the listing title: ");
        newItem.shortDescription = input.nextLine();

        if(template.category.hasSizing)
            GetAndSetSize();
        if(template.category.hasMeasurements)
            GetAndSetMeasurements();

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
        newItem.generalItemAttributes.size = new ItemSize(sizeTypeId, sizeValueId);
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
            newItem.generalItemAttributes.measurements.add(new ItemMeasurement(m.id, -1, value));
        });
    }
}
