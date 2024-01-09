package Website.LaptopShop.Repositories;

import Website.LaptopShop.Entities.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Set;

public interface SanPhamRepository extends JpaRepository<SanPham, Long>, QuerydslPredicateExecutor<SanPham> {
    List<SanPham> findFirst12ByDanhMucTenDanhMucContainingIgnoreCaseOrderByIdDesc(String danhMuc);
    List<SanPham> findByIdIn(Set<Long> idList);
}
