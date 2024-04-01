package Website.LaptopShop.DTO;

import lombok.Data;
@Data
public class SearchOrderObject {
    private String orderStatus;
    private String fromDate;
    private String toDate;

    public SearchOrderObject() {
        orderStatus = "";
        fromDate = "";
        toDate = "";
    }
}
