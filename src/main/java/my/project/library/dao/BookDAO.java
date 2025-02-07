package my.project.library.dao;

import my.project.library.model.Book;
import my.project.library.model.Reader;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookDAO extends AbstractDAO<Book> {
    public BookDAO() {
        super(Book.class);
    }

    public Optional<Book> setOwner(long bookId, long readerId) throws HibernateException {
        Book updatedBook = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Book book = session.get(Book.class, bookId);
            if (book != null) {
                if (readerId == -1) {
                    book.setOwner(null);
                    updatedBook = (Book) session.merge(book);

                } else {
                    Reader reader = session.get(Reader.class, readerId);
                    if (reader != null) {
                        book.setOwner(reader);
                        updatedBook = (Book) session.merge(book);
                    }
                }

            }

            session.getTransaction().commit();
        }
        return Optional.ofNullable(updatedBook);
    }
}
