package Website.LaptopShop.Services;

import Website.LaptopShop.DTO.TaiKhoanDTO;
import Website.LaptopShop.Entities.NguoiDung;
import Website.LaptopShop.Entities.VaiTro;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface NguoiDungService {
    NguoiDung findByEmail(String email);

    NguoiDung saveUserForMember(NguoiDung nd);

    NguoiDung findById(long id);

    NguoiDung updateUser(NguoiDung nd);

    void changePass(NguoiDung nd, String newPass);

    Page<NguoiDung> getNguoiDungByVaiTro(Set<VaiTro> vaiTro, int page);

    NguoiDung saveUserForAdmin(TaiKhoanDTO dto);

    void deleteById(long id);
}
