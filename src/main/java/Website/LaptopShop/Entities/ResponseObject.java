package Website.LaptopShop.Entities;

import lombok.Data;

import java.util.Map;

@Data
public class ResponseObject {
    private Object data;
    private Map<String, String> errorMessages = null ;
    private String status;

    @Override
    public String toString() {
        return "ResponseObject [status=" + status + "]";
    }
}
