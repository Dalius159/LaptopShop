package Website.LaptopShop.DTO;

import lombok.Data;
@Data
public class SearchProductObject {
    private String categoryID;
    private String manufacturerID;
    private String price;

    // sap xep theo gia
    private String sortByPrice;
    private String[] keyword;
    private String sort;

    // sort by category and manufacturer
    private String category;
    private String manufacturer;

    // sort by ram, batteryCapacity_mAh, OS
    private String operatingSystem;
    private String ram;
    private int batteryCapacity_mAh;

    public SearchProductObject() {
        categoryID = "";
        manufacturerID = "";
        price = "";
        sortByPrice = "ASC";
    }

    @Override
    public String toString() {
        return "SearchObject [categoryID=" + categoryID + ", manufacturerID=" + manufacturerID + ", price=" + price + "]";
    }
}
