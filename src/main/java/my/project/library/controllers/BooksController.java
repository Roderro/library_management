package my.project.library.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import my.project.library.dao.BookDAO;
import my.project.library.dao.DAO;
import my.project.library.dao.ReaderDAO;
import my.project.library.model.Book;
import my.project.library.model.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;


@Controller
@RequestMapping("/books")
public class BooksController extends AbstractController<Book> {
    private static final String modelName = "book";
    private static final String pluralModelName = "books";
    private final ReaderDAO readerDAO;


    @Autowired
    public BooksController(BookDAO dao, ReaderDAO readerDAO) {
        super(dao, modelName, pluralModelName);
        this.readerDAO = readerDAO;
    }


    @Override
    @GetMapping()
    public String showAll(Model model) {
        return super.showAll(model);
    }


    @GetMapping("/{id}")
    public String showById(@ModelAttribute("reader") Reader reader,
                           @PathVariable("id") long bookId, Model model) {
        model.addAttribute("readers", readerDAO.getAll().orElse(Collections.emptyList()));
        model.addAttribute("reader",reader);
        return super.showById(bookId, model);
    }

    @Override
    @GetMapping("/add")
    public String addForm(@ModelAttribute(modelName) Book book) {
        return super.addForm(book);
    }

    @Override
    @PostMapping()
    public String create(@ModelAttribute(modelName) @Valid Book book, BindingResult bindingResult) {
        return super.create(book, bindingResult);
    }

    @Override
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") long bookId, Model model) {
        return super.editForm(bookId, model);
    }

    @Override
    @PatchMapping("/{id}")
    public String update(@ModelAttribute(modelName) @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") long bookId) {
        return super.update(book, bindingResult, bookId);
    }

    @Override
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long bookId) {
        return super.delete(bookId);
    }

    @PatchMapping("/{id}/set_owner")
    public String setOwner(@ModelAttribute("reader") Reader reader,
                           @PathVariable("id") long bookId) {
        BookDAO bookDAO = (BookDAO) dao;
        bookDAO.setOwner(bookId, reader.getId());
        return String.format("redirect:/%s/%d", pluralModelName, bookId);
    }

    @PatchMapping("/{id}/untie_owner")
    public String untieOwner(@PathVariable("id") long bookId) {
        BookDAO bookDAO = (BookDAO) dao;
        bookDAO.setOwner(bookId, -1);
        return String.format("redirect:/%s/%d", pluralModelName, bookId);
    }
}
