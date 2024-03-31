package Website.LaptopShop.API;

import Website.LaptopShop.DTO.ResponseObject;
import Website.LaptopShop.DTO.ProductDTO;
import Website.LaptopShop.DTO.SearchProductObject;
import Website.LaptopShop.Entities.Product;
import Website.LaptopShop.Services.ProductService;
import Website.LaptopShop.Validator.ProductDtoValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/san-pham")
public class ProductAPI {

	@Autowired
	private ProductDtoValidator validator;

	@Autowired
	private ProductService productService;

	@InitBinder("sanPhamDto")
	protected void initialiseBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	// Take all the products according to the criteria, let the enemy take them all
	@GetMapping("/all")
	public Page<Product> getAllProductByFilter(@RequestParam(defaultValue = "1") int page,
											   @RequestParam String categoryID, @RequestParam String manufacturerID, @RequestParam String price, @RequestParam String sortByPrice) {
		SearchProductObject searchObject = new SearchProductObject();
		searchObject.setCategoryID(categoryID);
		searchObject.setManufacturerID(manufacturerID);
		searchObject.setPrice(price);
		searchObject.setSortByPrice(sortByPrice);

		return productService.getAllProductByFilter(searchObject, page-1, 10);
	}

	@GetMapping("/latest")
	public List<Product> getLatestProduct(){
		return productService.getLatestProduct();
	}

	// get product by id
	@GetMapping("/{id}")
	public Product getProductById(@PathVariable long id) {
		return productService.getProductById(id);
	}


	// lấy sản phẩm theo tên
//	@GetMapping("/")
//	public Page<SanPham> getSanPhamById(@RequestParam String tenSanPham, @RequestParam(defaultValue = "1") int page) {
//		return sanPhamService.getSanPhamByTenSanPhamForAdmin(tenSanPham, page-1, 10 );
//	}

	// save product into db
	@PostMapping(value = "/save")
	public ResponseObject addProduct(@ModelAttribute @Valid ProductDTO newProductDto, BindingResult result,
									 HttpServletRequest request) {

		ResponseObject ro = new ResponseObject();

		// if an error occurs( validate)
		if (result.hasErrors()) {
			Map<String, String> errors = result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			errors.forEach((k, v) -> System.out.println(" test: Key : " + k + " Value : " + v));
			ro.setErrorMessages(errors);
			ro.setStatus("fail");
			errors = null;
		} else {
			// save product
			Product sp = productService.save(newProductDto);
			ro.setData(sp);
			saveImageForProduct(sp, newProductDto, request);
			ro.setStatus("success");
		}
		return ro;
	}


	@DeleteMapping("/delete/{id}")
	public String deleteProduct(@PathVariable long id) {
		productService.deleteById(id);
		return "OK !";
	}


	// Save the product image to a folder
	public void saveImageForProduct(Product sp, ProductDTO dto, HttpServletRequest request) {

		MultipartFile productImage = dto.getImage();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		Path path = Paths.get(rootDirectory + "/resources/images/" + sp.getId() + ".png");
		System.out.println(productImage != null && !productImage.isEmpty());
		if (productImage != null && !productImage.isEmpty()) {

			try {
				productImage.transferTo(new File(path.toString()));
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException("Product image saving failed", ex);
			}
		}
	}
}
