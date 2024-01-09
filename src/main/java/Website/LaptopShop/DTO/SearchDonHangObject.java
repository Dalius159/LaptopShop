package Website.LaptopShop.DTO;

import lombok.Data;
@Data
public class SearchDonHangObject {
    private String trangThaiDon;
    private String tuNgay;
    private String denNgay;

    public SearchDonHangObject() {
        trangThaiDon = "";
        tuNgay = "";
        denNgay = "";
    }
}
