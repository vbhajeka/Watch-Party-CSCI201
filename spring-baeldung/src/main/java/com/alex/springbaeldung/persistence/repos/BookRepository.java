package com.alex.springbaeldung.persistence.repos;

import com.alex.springbaeldung.persistence.models.Book;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface BookRepository extends CrudRepository<Book,Long> {

    List<Book> findByTitle(String title);

}
