package my.project.library.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import my.project.library.dao.AbstractDAO;
import my.project.library.model.Book;
import my.project.library.model.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

public abstract class AbstractController<T> {
    protected final AbstractDAO<T> dao;
    protected final String modelName;
    protected final String pluralModelName;

    public AbstractController(AbstractDAO<T> dao, String modelName, String pluralModelName) {
        this.dao = dao;
        this.modelName = modelName;
        this.pluralModelName = pluralModelName;
    }


    public String showAll(Model model) {
        List<T> items = dao.getAll().orElse(Collections.emptyList());
        model.addAttribute(pluralModelName, items);
        return String.format("/%s/out_everyone", pluralModelName);
    }


    public String showById(@PathVariable("id") long id, Model model) {
        T item = dao.getById(id)
                .orElseThrow(() -> new EntityNotFoundException("Не найдена " + modelName + "с id = " + id));
        model.addAttribute(modelName, item);
        return String.format("/%s/out_by_id", pluralModelName);
    }


    public String addForm(T entity) {
        return String.format("%s/add", pluralModelName);
    }


    public String create(T entity,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return String.format("%s/add", pluralModelName);
        }
        dao.add(entity);
        return String.format("redirect:/%s", pluralModelName);
    }


    public String editForm(long id, Model model) {
        T entity = dao.getById(id)
                .orElseThrow(() -> new EntityNotFoundException("Не найдена " + modelName + "с id = " + id));
        model.addAttribute(modelName, entity);
        return String.format("%s/edit", pluralModelName);
    }


    public String update(T entity, BindingResult bindingResult,
                         long id) {
        if (bindingResult.hasErrors()) {
            return String.format("%s/edit", pluralModelName);
        }
        dao.update(entity);
        return String.format("redirect:/%s", pluralModelName);
    }


    public String delete(long id) {
        dao.deleteByID(id);
        return String.format("redirect:/%s", pluralModelName);
    }
}
