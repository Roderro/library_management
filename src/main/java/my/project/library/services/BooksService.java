package my.project.library.services;

import jakarta.persistence.EntityNotFoundException;
import my.project.library.model.Book;
import my.project.library.model.Reader;
import my.project.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;



@Service
public class BooksService extends AbstractService<Book, Long> {


    @Autowired
    protected BooksService(@Qualifier("bookRepository") JpaRepository<Book, Long> repository) {
        super(repository);
    }

    @Override
    String getSortingField() {
        return "yearOfPublication";
    }

    public List<Book> searchReaderStartWith(String text) {
        BookRepository readerRepository = (BookRepository) repository;
        return readerRepository.findByNameStartingWith(text);
    }

    @Transactional
    public void setOwner(Long id, Reader reader) {
        Book book = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Не найдена книга с id = " + id));
        book.setDayOfCapture(LocalDateTime.now());
        book.setOwner(reader);
    }
}
