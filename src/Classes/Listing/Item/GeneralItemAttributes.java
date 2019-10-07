package Classes.Listing.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * A special set of attributes that generally apply to all/most items.
 * The exceptions are size and measurements, which apply only to certain categories. These will be null/empty for items that belong to those categories.
 */
public class GeneralItemAttributes {
    //standard attributes;
    /**
     * The "era" of the item - when the item was manufactured. This is used by the Ebay/Etsy services to determine whether an item should be categorized as "vintage"
     *
     * REQUIRED FIELD
     *
     * @setby The Android app when the user selects the era
     * @setwhen Saving a new item
     * @modifywhen The era is modified on an existing item
     * @modifiedby The user
     */
    public ItemAttribute era;
    /**
     * The "primary color" of the item - the color that the item is mostly composed of.
     *
     * OPTIONAL FIELD
     *
     * @setby The Android app when the user selects the primary color
     * @setwhen Saving a new item
     * @modifywhen The primary color is modified on an existing item
     * @modifiedby The user
     */
    public ItemAttribute primaryColor;
    /**
     * The "secondary color" of the item - applies to multicolored items. Should only be set if there is a primary color set.
     *
     * OPTIONAL FIELD
     *
     * @setby The Android app when the user selects the secondary color
     * @setwhen Saving a new item
     * @modifywhen The secondary color is modified on an existing item
     * @modifiedby The user
     */
    public ItemAttribute secondaryColor;
    /**
     * The "material" of the item - what the item is made of.
     *
     * OPTIONAL FIELD
     *
     * @setby The Android app when the user selects the material
     * @setwhen Saving a new item
     * @modifywhen The material is modified on an existing item
     * @modifiedby The user
     */
    public ItemAttribute material;

    //other fields
    /**
     * The measurements of the item. See ItemMeasurement for details.
     *
     * OPTIONAL FIELDS
     *
     * @setby The Android app when the user enters measurements
     * @setwhen Saving a new item
     * @modifywhen The measurements are modified on an existing item
     * @modifiedby The user
     */
    public List<ItemMeasurement> measurements;
    /**
     * The size of the item. See ItemSize for details.
     *
     * REQUIRED FIELD
     *
     * @setby The Android app when the user selects the size type and size value
     * @setwhen Saving a new item
     * @modifywhen The size type and size value are modified on an existing item
     * @modifiedby The user
     */
    public ItemSize size;

    /**
     * Constructor, initializes the list of measurements
     */
    public GeneralItemAttributes() {
        this.measurements = new ArrayList<ItemMeasurement>();
    }
}
