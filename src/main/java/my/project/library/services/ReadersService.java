package my.project.library.services;


import my.project.library.model.Book;
import my.project.library.model.Reader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
public class ReadersService extends AbstractService<Reader, Long> {

    protected ReadersService(@Qualifier("readerRepository") JpaRepository<Reader, Long> repository) {
        super(repository);
    }

    @Override
    String getSortingField() {
        return "birthYear";
    }

    @Override
    public Reader findById(Long id) {
        Reader reader = super.findById(id);
        for (Book book : reader.getBorrowedBooks()) {
            if (book.getOwner() != null) {
                if (LocalDateTime.now().isAfter(book.getDayOfCapture().plusDays(10))) {
                    book.setOverdue(true);
                }
            }
        }
        return reader;
    }
}
