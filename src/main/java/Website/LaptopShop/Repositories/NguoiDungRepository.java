package Website.LaptopShop.Repositories;

import Website.LaptopShop.Entities.NguoiDung;
import Website.LaptopShop.Entities.VaiTro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface NguoiDungRepository extends JpaRepository<NguoiDung, Long> {
    NguoiDung findByEmail(String email);
    Page<NguoiDung> findByVaiTroIn(Set<VaiTro> vaiTro, Pageable of);
    List<NguoiDung> findByVaiTroIn(Set<VaiTro> vaiTro);
}
