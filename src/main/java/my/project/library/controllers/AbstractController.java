package my.project.library.controllers;

import my.project.library.services.AbstractService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.*;

public abstract class AbstractController<T, ID> {
    protected final AbstractService<T, ID> service;
    protected final String modelName;
    protected final String pluralModelName;

    public AbstractController(AbstractService<T, ID> Service, String modelName, String pluralModelName) {
        this.service = Service;
        this.modelName = modelName;
        this.pluralModelName = pluralModelName;
    }


    public String showAll(Model model, Integer page, Integer itemForPage, Boolean sort) {
        List<T> items = service.findAll(page, itemForPage, sort);
        model.addAttribute(pluralModelName, items);
        return String.format("/%s/out_everyone", pluralModelName);
    }


    public String showById(ID id, Model model) {
        T item = service.findById(id);
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
        service.save(entity);
        return String.format("redirect:/%s", pluralModelName);
    }


    public String editForm(ID id, Model model) {
        T entity = service.findById(id);
        model.addAttribute(modelName, entity);
        return String.format("%s/edit", pluralModelName);
    }


    public String update(T entity, BindingResult bindingResult, ID id) {
        if (bindingResult.hasErrors()) {
            return String.format("%s/edit", pluralModelName);
        }
        service.update(entity);
        return String.format("redirect:/%s", pluralModelName);
    }


    public String delete(ID id) {
        service.deleteById(id);
        return String.format("redirect:/%s", pluralModelName);
    }
}
