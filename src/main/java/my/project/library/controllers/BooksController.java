package my.project.library.controllers;

import jakarta.validation.Valid;
import my.project.library.model.Book;
import my.project.library.model.Reader;
import my.project.library.services.BooksService;
import my.project.library.services.ReadersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/books")
public class BooksController extends AbstractController<Book, Long> {
    private static final String modelName = "book";
    private static final String pluralModelName = "books";
    private final ReadersService readersService;


    @Autowired
    public BooksController(BooksService booksService,
                           ReadersService readersService) {
        super(booksService, modelName, pluralModelName);
        this.readersService = readersService;
    }


    @Override
    @GetMapping()
    public String showAll(Model model, @RequestParam(value = "page", required = false) Integer page,
                          @RequestParam(value = "count", required = false) Integer booksPerPage,
                          @RequestParam(value = "sort", required = false) Boolean sortByYearOfPublication) {
        return super.showAll(model, page, booksPerPage, sortByYearOfPublication);
    }


    @GetMapping("/{id}")
    public String showById(@ModelAttribute("reader") Reader reader,
                           @PathVariable("id") long bookId, Model model) {
        model.addAttribute("readers", readersService.findAll(null, null, null));
        model.addAttribute("reader", reader);
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
    public String editForm(@PathVariable("id") Long bookId, Model model) {
        return super.editForm(bookId, model);
    }

    @Override
    @PatchMapping("/{id}")
    public String update(@ModelAttribute(modelName) @Valid Book book, BindingResult bindingResult, @PathVariable("id") Long id) {
        return super.update(book, bindingResult, id);
    }

    @Override
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long bookId) {
        return super.delete(bookId);
    }

    @GetMapping("/search")
    public String searchBooks(Model model, @RequestParam(value = "text", required = false) String text) {
        BooksService booksService = (BooksService) service;
        if (text != null) {
            model.addAttribute("foundBooks", booksService.searchBookStartWith(text));
        }
        return String.format("/%s/search", pluralModelName);
    }


    @PatchMapping("/{id}/set_owner")
    public String setOwner(@ModelAttribute("reader") Reader reader,
                           @PathVariable("id") Long bookId) {
        BooksService booksService = (BooksService) service;
        booksService.setOwner(bookId, reader);
        return String.format("redirect:/%s/%d", pluralModelName, bookId);
    }

    @PatchMapping("/{id}/untie_owner")
    public String untieOwner(@PathVariable("id") Long bookId) {
        BooksService booksService = (BooksService) service;
        booksService.setOwner(bookId, null);
        return String.format("redirect:/%s/%d", pluralModelName, bookId);
    }
}
