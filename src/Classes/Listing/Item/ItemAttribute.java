package Classes.Listing.Item;

public class ItemAttribute {
    public Long subcategoryAttributeId; //nullable
    public long itemAttributeId; //not nullable
    public String itemAttributeValue; //could be a string, bool, or number, but we'll store it as a string for simplicity
    public Long attributeRecommendationId; //nullable, used if the user chose a "recommended" value for the attribute
}
