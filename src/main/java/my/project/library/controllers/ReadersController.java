package my.project.library.controllers;

import jakarta.validation.Valid;
import my.project.library.model.Reader;
import my.project.library.services.ReadersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/readers")
public class ReadersController extends AbstractController<Reader, Long> {
    private static final String modelName = "reader";
    private static final String pluralModelName = "readers";

    @Autowired
    public ReadersController(ReadersService readersService) {
        super(readersService, modelName, pluralModelName);
    }

    @Override
    @GetMapping()
    public String showAll(Model model, @RequestParam(value = "page", required = false) Integer page,
                          @RequestParam(value = "count", required = false) Integer readersPerPage,
                          @RequestParam(value = "sort", required = false) Boolean sortByBirthYear) {
        return super.showAll(model, page, readersPerPage, sortByBirthYear);
    }


    @Override
    @GetMapping("/{id}")
    public String showById(@PathVariable("id") Long readerId, Model model) {
        return super.showById(readerId, model);
    }

    @Override
    @GetMapping("/add")
    public String addForm(@ModelAttribute("reader") Reader reader) {
        return super.addForm(reader);
    }

    @Override
    @PostMapping
    public String create(@ModelAttribute("reader") @Valid Reader reader, BindingResult bindingResult) {
        return super.create(reader, bindingResult);
    }

    @Override
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long readerId, Model model) {
        return super.editForm(readerId, model);
    }

    @Override
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("reader") @Valid Reader reader, BindingResult bindingResult,
                         @PathVariable("id") Long id) {
        return super.update(reader, bindingResult, id);
    }

    @Override
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long readerId) {
        return super.delete(readerId);
    }


}
