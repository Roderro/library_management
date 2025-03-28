package my.project.library.services;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional(readOnly = true)
public abstract class AbstractService<T, ID> {
    protected final JpaRepository<T, ID> repository;


    protected AbstractService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }


    public List<T> findAll(Integer page, Integer itemForPage, Boolean sort) {
        if (sort == null || !sort) {
            if (itemForPage == null) {
                return repository.findAll();
            } else if (page == null) {
                return repository.findAll(PageRequest.ofSize(itemForPage)).getContent();
            }
            return repository.findAll(PageRequest.of(page, itemForPage)).getContent();
        } else {
            Sort sortFunc = Sort.by(getSortingField());
            if (itemForPage == null) {
                return repository.findAll(sortFunc);
            } else if (page == null) {
                return repository.findAll(PageRequest.ofSize(itemForPage).withSort(sortFunc)).getContent();
            }
            return repository.findAll(PageRequest.of(page, itemForPage, sortFunc)).getContent();
        }
    }


    public T findById(ID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Не найдена сущность с id = " + id));
    }

    @Transactional
    public void save(T entity) {
        repository.save(entity);
    }

    @Transactional
    public void update(T entity) {
        repository.save(entity);
    }

    @Transactional
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    abstract String getSortingField();
}
