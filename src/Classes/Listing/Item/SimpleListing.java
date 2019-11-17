package Classes.Listing.Item;

import java.util.Date;

public class SimpleListing {
    /**
     * The ID of the listing. This is the primary key identifying the listing in the database.
     * <p>
     *     NOTE: This will be NULL when creating a new item in the app. It is not set until the listing is saved.
     * </p>
     *
     * @setby The database (should be set to autoincrement)
     * @setwhen A new item is saved to the database
     * @modifywhen Never.
     * @modifiedby No one.
     */
    public long listingId;
    /**
     * The date/time when the item was originally created.
     *
     * @setby The frontend Android app when saving a new item, using <code>new Date()</code>
     * @setwhen Creating a new item in the frontend Android application.
     * @modifywhen Never. This should not be modified once set.
     * @modifiedby No one.
     */
    public Date createdDateTime;
    /**
     * The date/time when the item was last modified.
     *
     * @setby The frontend Android app when saving a new item, using <code>new Date()</code>
     * @setwhen Creating a new item on the frontend Android application.
     * @modifywhen Saving an existing item in the frontend Android application.
     * @modifiedby The Android application.
     */
    public Date updatedDateTime;
    /**
     * The ID of the user who created the item originally.
     *<p>
     * NOTE: this is not the <code>userLoginId</code> (which is a <code>GUID</code> or <code>string</code>). It is the <code>userId</code>, which is a <code>long</code>.
     *</p>
     * @setby The frontend Android app when saving a new item, using <code>userId</code> of the user who is logged in
     * @setwhen Creating a new item on the frontend Android application.
     * @modifywhen Never. This should not be modified once set.
     * @modifiedby No one.
     */
    public long createdByUserId;
    /**
     * The ID of the user who last modified the item.
     *<p>
     * NOTE: this is not the <code>userLoginId</code> (which is a <code>GUID</code> or <code>string</code>). It is the <code>userId</code>, which is a <code>long</code>.
     *</p>
     * @setby The frontend Android app when saving a new item, using <code>userId</code> of the user who is logged in
     * @setwhen Creating a new item on the frontend Android application.
     * @modifywhen Saving an existing item in the frontend Android application.
     * @modifiedby The Android application.
     */
    public long updatedByUserId;
    /**
     * The ID of the item. This is the primary key identifying the item in the database.
     * <p>
     *     NOTE: This will be NULL when creating a new item in the app. It is not set until the item is saved.
     * </p>
     *
     * @setby The database (should be set to autoincrement)
     * @setwhen A new item is saved to the database
     * @modifywhen Never.
     * @modifiedby No one.
     */
    /**
     * The ID of the category that the item belongs to.
     * @setby The Android app on item creation (the Category is set by the user, the ID is saved)
     * @setwhen Creating an item in the Android app
     * @modifywhen Never. Once the category is set, it should not be modified.
     * @modifiedby No one.
     */
    public long categoryId;
    /**
     * The ID of the subcategory that the item belongs to.
     * @setby The Android app on item creation (the Subcategory is set by the user, the ID is saved)
     * @setwhen Creating an item in the Android app
     * @modifywhen Never. Once the subcategory is set, it should not be modified.
     * @modifiedby No one.
     */
    public long subcategoryId;
    /**
     * This field will become the title of the listing once it is posted to Ebay/Etsy
     * @setby The user in the frontend Android app
     * @setwhen Creating an item in the Android app
     * @modifywhen Editing an item in the Android app.
     * @modifiedby The user.
     */
    public String shortDescription;
    /**
     * This field will become the text body of the listing once it is posted to Ebay/Etsy
     * @setby The user in the frontend Android app
     * @setwhen Creating an item in the Android app
     * @modifywhen Editing an item in the Android app.
     * @modifiedby The user.
     */
    public String longDescription;

    public void SetShortDescription(String newSD){
        shortDescription=newSD;
    }

    public void SetLongDescription(String newLD){
        longDescription=newLD;
    }

    public void UpdateUserId(int id){
        updatedByUserId=id;
    }

    public String toString(){
        /*+ "Created at:"+ createdDateTime.toString()+'\n'
          + "Last Updated at:"+ updatedDateTime.toString()+'\n'*/
        return "Listing Id: " + listingId +'\n'
                + "Created By User Id: " + createdByUserId +'\n'
                + "Updated By User Id: " + updatedByUserId +'\n'
                + "Item Cateogory ID: " + categoryId +'\n'
                + "Item Subcategory ID: " + subcategoryId + '\n'
                + "Short Description: " + shortDescription +'\n'
                + "Long Description: " + longDescription+'\n';
    }
}
