package my.project.library.dao;

import my.project.library.model.Reader;
import org.springframework.stereotype.Component;

@Component
public class ReaderDAO extends AbstractDAO<Reader> {

    public ReaderDAO() {
        super(Reader.class);
    }
}
