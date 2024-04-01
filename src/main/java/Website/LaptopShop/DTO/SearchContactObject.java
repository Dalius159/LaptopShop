package Website.LaptopShop.DTO;

import lombok.Data;
@Data
public class SearchContactObject {
    private String contactStatus;
    private String fromDate;
    private String toDate;

    public SearchContactObject() {
        contactStatus = "";
        fromDate = "";
        toDate = "";
    }
}
