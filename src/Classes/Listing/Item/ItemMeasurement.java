package Classes.Listing.Item;

public class ItemMeasurement {
    public long categoryMeasurementId;
    public long itemMeasurementId;
    public double itemMeasurementValue;

    public ItemMeasurement(long categoryMeasurementId, long itemMeasurementId, double itemMeasurementValue) {
        this.categoryMeasurementId = categoryMeasurementId;
        this.itemMeasurementId = itemMeasurementId;
        this.itemMeasurementValue = itemMeasurementValue;
    }
}
