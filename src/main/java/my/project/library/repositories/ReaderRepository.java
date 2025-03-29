package my.project.library.repositories;

import my.project.library.model.Book;
import my.project.library.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {

    List<Reader> findByFioContaining(String text);
}
