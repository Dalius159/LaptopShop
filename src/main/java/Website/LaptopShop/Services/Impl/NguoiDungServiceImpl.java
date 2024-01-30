package Website.LaptopShop.Services.Impl;

import Website.LaptopShop.Entities.NguoiDung;
import Website.LaptopShop.Entities.VaiTro;
import Website.LaptopShop.Repositories.NguoiDungRepository;
import Website.LaptopShop.Repositories.VaiTroRepository;
import Website.LaptopShop.Services.NguoiDungService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import Website.LaptopShop.DTO.TaiKhoanDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class NguoiDungServiceImpl implements NguoiDungService {
    @Autowired
    private NguoiDungRepository nguoiDungRepo;

    @Autowired
    private VaiTroRepository vaiTroRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public NguoiDung findByEmail(String email) {
        return nguoiDungRepo.findByEmail(email);
    }

    @Override
    public NguoiDung saveUserForMember(NguoiDung nd) {
        nd.setPassword(bCryptPasswordEncoder.encode(nd.getPassword()));
        Set<VaiTro> setVaiTro = new HashSet<>();
        setVaiTro.add(vaiTroRepo.findByTenVaiTro("ROLE_MEMBER"));
        nd.setVaiTro(setVaiTro);
        return nguoiDungRepo.save(nd);
    }

    @Override
    public NguoiDung findById(long id) {
        NguoiDung nd = nguoiDungRepo.findById(id).get();
        return nd;
    }

    @Override
    public NguoiDung updateUser(NguoiDung nd) {
        return nguoiDungRepo.save(nd);
    }

    @Override
    public void changePass(NguoiDung nd, String newPass) {
		nd.setPassword(bCryptPasswordEncoder.encode(newPass));
		nguoiDungRepo.save(nd);
	}

    @Override
    public Page<NguoiDung> getNguoiDungByVaiTro(Set<VaiTro> vaiTro, int page) {
        return nguoiDungRepo.findByVaiTroIn(vaiTro, PageRequest.of(page - 1, 6));
    }

    @Override
    public NguoiDung saveUserForAdmin(TaiKhoanDTO dto) {
        NguoiDung nd = new NguoiDung();
        nd.setHoTen(dto.getHoTen());
        nd.setDiaChi(dto.getDiaChi());
        nd.setEmail(dto.getEmail());
        nd.setSoDienThoai(dto.getSdt());
        nd.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));

        Set<VaiTro> vaiTro  = new HashSet<>();
        vaiTro.add(vaiTroRepo.findByTenVaiTro(dto.getTenVaiTro()));
        nd.setVaiTro(vaiTro);

        return nguoiDungRepo.save(nd);
    }

    @Override
    public void deleteById(long id) {
        nguoiDungRepo.deleteById(id);
    }
}
