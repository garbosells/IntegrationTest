package Classes.Listing.Item;

import java.util.ArrayList;
import java.util.List;

public class GeneralItemAttributes {
    //standard attributes;
    public ItemAttribute era;
    public ItemAttribute primaryColor;
    public ItemAttribute secondaryColor;
    public ItemAttribute material;

    //other fields
    public List<ItemMeasurement> measurements;
    public ItemSize size;

    public GeneralItemAttributes() {
        this.measurements = new ArrayList<ItemMeasurement>();
    }
}
