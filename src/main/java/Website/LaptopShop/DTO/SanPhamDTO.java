package Website.LaptopShop.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class SanPhamDTO {
    private String id;
    private String tenSanPham;
    private String donGia;
    private String donViKho;
    private String thongTinBaoHanh;
    private String thongTinChung;
    private String manHinh;
    private String heDieuHanh;
    private String cpu;
    private String ram;
    private String thietKe;
    private int dungLuongPin;
    private long danhMucId;
    private long nhaSXId;
    private MultipartFile hinhAnh;
}
