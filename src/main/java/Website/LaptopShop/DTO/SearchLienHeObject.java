package Website.LaptopShop.DTO;

import lombok.Data;
@Data
public class SearchLienHeObject {
    private String trangThaiLienHe;
    private String tuNgay;
    private String denNgay;

    public SearchLienHeObject() {
        trangThaiLienHe = "";
        tuNgay = "";
        denNgay = "";
    }
}
