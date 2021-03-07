package com.gina.flowerShop.web.controller;

import com.gina.flowerShop.model.Category;
import com.gina.flowerShop.repository.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class CategoryController {
    private CategoryRepository categoryRepository;

    public CategoryController( CategoryRepository categoryRepository) {

        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/provider/add/category/form")
    public String displayAddCategory(Model model){
        model.addAttribute("category", new Category());
        return "provider-add-category";
    }

    @PostMapping("/provider/add/category")
    public String addCategory(@Valid @ModelAttribute("category") Category category,
                              BindingResult result, Model model, RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            return "provider-add-category";
        }
        if(categoryRepository.countByCategoryName(category.getCategoryName())==1L){
            redirectAttributes.addFlashAttribute("message", "Exista deja o categorie cu numele: "+category.getCategoryName());
            return "redirect:/provider/categories/list";
        }
        categoryRepository.save(category);
        Category newCategory = new Category();
        model.addAttribute("category", newCategory);

        return "provider-add-category";
    }

    @GetMapping("/provider/categories/list")
    public String getAllCategories(Model model){
        model.addAttribute("categories", categoryRepository.findAll());

        return "provider-categories-list";
    }

    @GetMapping("/provider/update/category/form/{idCategory}")
    public String displayUpdateCategory(@PathVariable("idCategory")Long idCategory, Model model){
        final Optional<Category> optionalCategory = categoryRepository.findById(idCategory);
        if(optionalCategory.isPresent()){
            final Category category = optionalCategory.get();
            model.addAttribute("category", category);

        }else{
            new IllegalArgumentException("Invalid id "+idCategory);
        }
        return "provider-update-category";
    }

    @PostMapping("/provider/update/category/{idCategory}")
    public String updateCategory(@PathVariable("idCategory")Long idCategory,
                                 @Valid @ModelAttribute("category") Category category,
                                 BindingResult result, Model model, RedirectAttributes redirectAttributes){

        if(result.hasErrors()){
            return "provider-update-category";
        }
        redirectAttributes.addFlashAttribute("message", "Categoria "+ category.getCategoryName()+" s-a editat cu success");
        categoryRepository.save(category);


        return "redirect:/provider/categories/list";
    }
}
