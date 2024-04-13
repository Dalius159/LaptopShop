package Website.LaptopShop.DTO;

import lombok.Data;
import java.util.List;

@Data
public class UpdateOrderDeliver {
    private long orderID;
    private String deliverNote;
    private List<UpdateOrderDetails> updateOrderDetailsList;

    @Data
    public static class UpdateOrderDetails {
        private long detailsID;
        private int receivedQuantity;
    }
}
