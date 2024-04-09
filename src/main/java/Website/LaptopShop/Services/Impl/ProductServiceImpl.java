package Website.LaptopShop.Services.Impl;

import Website.LaptopShop.DTO.ProductDTO;
import Website.LaptopShop.DTO.SearchProductObject;
import Website.LaptopShop.Entities.Product;
import Website.LaptopShop.Entities.QProduct;
import Website.LaptopShop.Repositories.CategoryRepository;
import Website.LaptopShop.Repositories.ManufacturerRepository;
import Website.LaptopShop.Repositories.ProductRepository;
import Website.LaptopShop.Services.ProductService;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRep;

    @Autowired
    private CategoryRepository categoryRep;

    @Autowired
    private ManufacturerRepository manufacturerRep;

    //convert from ProductDTO to object Product in order to add into database
    public Product convertFromProductDTO(ProductDTO dto) {
        Product product = new Product();
        if (!dto.getId().equals("")) {
            product.setId(Long.parseLong(dto.getId()));
        }
        product.setProductName(dto.getProductName());
        product.setCpu(dto.getCpu());
        product.setCategory(categoryRep.findById(dto.getCategoryID()).get());
        product.setManufacturer(manufacturerRep.findById(dto.getManufacturerID()).get());
        product.setPrice(Long.parseLong(dto.getPrice()));
        product.setDesign(dto.getDesign());
        product.setWarrantyInfor(dto.getWarrantyInfor());
        product.setGeneralInfor(dto.getGeneralInfor());
        product.setScreen(dto.getScreen());
        product.setRam(dto.getRam());
        product.setBatteryCapacity_mAh(dto.getBatteryCapacity_mAh());
        product.setWarehouseUnit(Integer.parseInt(dto.getWarehouseUnit()));
        product.setOperatingSystem(dto.getOperatingSystem());

        return product;
    }

    @Override
    public Product save(ProductDTO dto) {return productRep.save(convertFromProductDTO(dto));}

    @Override
    public void deleteById(long id) {
        productRep.deleteById(id);

    }

    public void FilterByPrice(String price, BooleanBuilder builder){
        switch (price) {
            case "Under 400":
                builder.and(QProduct.product.price.lt(400));
                break;

            case "400 - 800":
                builder.and(QProduct.product.price.between(400, 800));
                break;

            case "800 - 1200":
                builder.and(QProduct.product.price.between(800, 1200));
                break;

            case "1200 - 1600":
                builder.and(QProduct.product.price.between(1200, 1600));
                break;

            case "Above 1000":
                builder.and(QProduct.product.price.gt(1600));
                break;
            default:
                break;
        }
    }

    @Override
    public Page<Product> getAllProductByFilter(SearchProductObject object, int page, int limit) {
        BooleanBuilder builder = new BooleanBuilder();
        String price = object.getPrice();

        // sort by price
        Sort sort = Sort.by(Sort.Direction.ASC, "price"); // default increase
        if (object.getSortByPrice().equals("desc")) { // decrease
            sort = Sort.by(Sort.Direction.DESC, "price");
        }

        if (!object.getCategoryID().equals("") && object.getCategoryID() != null) {
            builder.and(QProduct.product.category.eq(categoryRep.findById(Long.parseLong(object.getCategoryID())).get()));
        }

        if (!object.getManufacturerID().equals("") && object.getManufacturerID() != null) {
            builder.and(QProduct.product.manufacturer
                    .eq(manufacturerRep.findById(Long.parseLong(object.getManufacturerID())).get()));
        }

        // filter by price
        FilterByPrice(price, builder);
        return productRep.findAll(builder, PageRequest.of(page, limit, sort));
    }

    @Override
    public List<Product> getLatestProduct() {
        return productRep.findFirst12ByCategoryCategoryNameContainingIgnoreCaseOrderByIdDesc("Laptop");
    }

    public Iterable<Product> getProductByProductNameWithoutPaginate(SearchProductObject object) {
        BooleanBuilder builder = new BooleanBuilder();
        String[] keywords = object.getKeyword();
        String price = object.getPrice();
        // Keyword
        searchKeyword(builder, keywords, price);
        return productRep.findAll(builder);
    }

    private void searchKeyword(BooleanBuilder builder, String[] keywords, String price) {
        builder.and(QProduct.product.productName.like("%" + keywords[0] + "%"));
        if (keywords.length > 1) {
            for (int i = 1; i < keywords.length; i++) {
                builder.and(QProduct.product.productName.like("%" + keywords[i] + "%"));
            }
        }
        FilterByPrice(price, builder);
    }

    @Override
    public Product getProductById(long id) {return productRep.findById(id).get();}

    // find product by keyword, sort, pageginate, filter by price, get 12 product for each page
    @Override
    public Page<Product> getProductByProductName(SearchProductObject object, int page, int resultPerPage) {
        BooleanBuilder builder = new BooleanBuilder();
        String[] keywords = object.getKeyword();
        String sort = object.getSort();
        String price = object.getPrice();
        String category = object.getCategory();
        String manufacturer = object.getManufacturer();
        // Keyword
        searchKeyword(builder, keywords, price);

        // category and manufacturer
        if (category.length()>1) {
            builder.and(QProduct.product.category.categoryName.eq(category));
        }
        if (manufacturer.length()>1) {
            builder.and(QProduct.product.manufacturer.manufacturerName.eq(manufacturer));
        }

        // Sort
        if (sort.equals("newest")) {
            return productRep.findAll(builder, PageRequest.of(page - 1, resultPerPage, Sort.Direction.DESC, "id"));
        } else if (sort.equals("priceAsc")) {
            return productRep.findAll(builder, PageRequest.of(page - 1, resultPerPage, Sort.Direction.ASC, "price"));
        } else if (sort.equals("priceDes")) {
            return productRep.findAll(builder, PageRequest.of(page - 1, resultPerPage, Sort.Direction.DESC, "price"));
        }
        return productRep.findAll(builder, PageRequest.of(page - 1, resultPerPage));
    }

    public List<Product> getAllProductByList(Set<Long> idList) {
        return productRep.findByIdIn(idList);
    }

    @Override
    public Iterable<Product> getProductByCategoryName(String category) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QProduct.product.category.categoryName.eq(category));
        return productRep.findAll(builder);
    }

    @Override
    public Page<Product> getProductByCategory(SearchProductObject object, int page, int resultPerPage) {
        BooleanBuilder builder = new BooleanBuilder();
        String price = object.getPrice();
        String category = object.getCategory();
        String manufacturer = object.getManufacturer();
        String os = object.getOperatingSystem();
        String ram = object.getRam();
        int batteryCapacity_mAh = object.getBatteryCapacity_mAh();

        FilterByPrice(price, builder);

        // category and manufacturer
        if (category.length()>1) {
            builder.and(QProduct.product.category.categoryName.eq(category));
        }
        if (manufacturer.length()>1) {
            builder.and(QProduct.product.manufacturer.manufacturerName.eq(manufacturer));
        }
        if (os.length()>1) {
            builder.and(QProduct.product.operatingSystem.like("%"+os+"%"));
        }
        if (ram.length()>1) {
            builder.and(QProduct.product.ram.like("%"+ram+"%"));
        }
        if (batteryCapacity_mAh>1000) {
            builder.and(QProduct.product.batteryCapacity_mAh.eq(batteryCapacity_mAh));
        }

        return productRep.findAll(builder, PageRequest.of(page - 1, resultPerPage));
    }
}
