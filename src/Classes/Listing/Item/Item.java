package Classes.Listing.Item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Item {
    public Date createdDateTime;
    public Date updatedDateTime;
    public long createdByUserId;
    public long updatedByUserId;
    public long id;
    public String shortDescription;
    public String longDescription;
    public long categoryId;
    public long subcategoryId;
    public String[] tags;
    public GeneralItemAttributes generalItemAttributes;
    //dynamic attributes
    public List<ItemAttribute> attributes;

    public Item() {
        this.generalItemAttributes = new GeneralItemAttributes();
        this.attributes = new ArrayList<ItemAttribute>();
    }

    public void print() {
        System.out.println("Item Cateogory ID: " + categoryId);
        System.out.println("Item Subcategory ID: " + subcategoryId);
        System.out.println("Short Description: " + shortDescription);
        if(generalItemAttributes.primaryColor != null)
            System.out.println("Primary Color ID: " + generalItemAttributes.primaryColor.attributeRecommendationId);
        if(generalItemAttributes.secondaryColor != null)
            System.out.println("Secondary Color ID: " + generalItemAttributes.secondaryColor.attributeRecommendationId);
        if(generalItemAttributes.size != null) {
            System.out.println("Size Type ID: " + generalItemAttributes.size.sizeTypeId);
            System.out.println("Size Value ID: " + generalItemAttributes.size.sizeValueId);
        }
        if(!generalItemAttributes.measurements.isEmpty()) {
            System.out.println("Measurements by ID and value:");
            generalItemAttributes.measurements.forEach(m -> {
                System.out.println(m.categoryMeasurementId + ": " + m.itemMeasurementValue);
            });
        }
        if(generalItemAttributes.material != null)
            System.out.println("Material ID: " + generalItemAttributes.material.attributeRecommendationId);
        if(generalItemAttributes.era != null)
            System.out.println("Era ID: " + generalItemAttributes.era.attributeRecommendationId);
        if(!attributes.isEmpty())
            attributes.forEach(a -> {
                System.out.println("Subcategory Attribute ID " + a.subcategoryAttributeId + " has value:");
                System.out.println(a.attributeRecommendationId == null ? a.itemAttributeValue : a.attributeRecommendationId);
            });
    }
}
