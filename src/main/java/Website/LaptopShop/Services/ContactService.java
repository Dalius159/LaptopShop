package Website.LaptopShop.Services;

import Website.LaptopShop.DTO.SearchContactObject;
import Website.LaptopShop.Entities.Contact;
import org.springframework.data.domain.Page;

import java.text.ParseException;

public interface ContactService {
    Page<Contact> getContactByFilter(SearchContactObject object, int page) throws ParseException;

    Contact findById(long id);

    Contact save(Contact contact);

    int countByStatus(String status);
}
