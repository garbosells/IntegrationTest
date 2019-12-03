package Classes.Listing.Item;

public class Listing {
    public long ListingId;
    public Item inventoryItem;
    public String toString(){
        return "Listing # "+ListingId+'\n'+
                inventoryItem.toString();
    }
}

