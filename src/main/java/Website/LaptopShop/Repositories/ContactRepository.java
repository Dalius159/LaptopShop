package Website.LaptopShop.Repositories;

import Website.LaptopShop.Entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ContactRepository extends JpaRepository<Contact, Long>, QuerydslPredicateExecutor<Contact> {}
