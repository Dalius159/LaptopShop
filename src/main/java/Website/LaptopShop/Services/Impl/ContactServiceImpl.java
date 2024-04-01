package Website.LaptopShop.Services.Impl;

import Website.LaptopShop.DTO.SearchContactObject;
import Website.LaptopShop.Entities.Contact;
import Website.LaptopShop.Entities.QContact;
import Website.LaptopShop.Repositories.ContactRepository;
import Website.LaptopShop.Services.ContactService;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactRepository Rep;

    @Override
    public Page<Contact> getContactByFilter(SearchContactObject object, int page) throws ParseException {
        BooleanBuilder builder = new BooleanBuilder();

        String status = object.getContactStatus();
        String fromDate = object.getFromDate();
        String toDate = object.getToDate();
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");

        if (!status.equals("")) {
            builder.and(QContact.contact.status.eq(status));
        }

        if (!fromDate.equals("") && fromDate != null) {
            builder.and(QContact.contact.contactDate.goe(formatDate.parse(fromDate)));
        }

        if (!toDate.equals("") && toDate != null) {
            builder.and(QContact.contact.contactDate.loe(formatDate.parse(toDate)));
        }

        return Rep.findAll(builder, PageRequest.of(page - 1, 2));
    }

    @Override
    public Contact findById(long id) {
        return Rep.findById(id).get();
    }

    @Override
    public Contact save(Contact contact) {
        return Rep.save(contact);
    }
}
