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
}
