package com.spring.ecommerce.controller;

import com.spring.ecommerce.model.Category;
import com.spring.ecommerce.service.CategoryService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public String adminHome() {
        return "adminHome";
    }

    @GetMapping("/categories")
    public String getCatagory(Model model) {
        model.addAttribute("categories", categoryService.getAllCategory());
        return "categories";
    }

    //category function
    @GetMapping("/categories/add")
    public String getCatForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("btnType", "Create");
        return "categoriesAdd";
    }

    @PostMapping("/categories/add")
    public String postCatForm(@ModelAttribute("category") Category category, RedirectAttributes redirectAttributes) {
        if (category.getId() != null) {
            Category existing = categoryService.getcategoryById(category.getId());
            existing.setName(category.getName());
            categoryService.updateCategory(existing);
            redirectAttributes.addFlashAttribute("success", "Category Updated Successfully");
            return "redirect:/admin/categories";
        }

        redirectAttributes.addFlashAttribute("success", "Category Added Successfully");
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/update/{id}")
    public String updateCategory(@PathVariable("id") Long id, Model model) {
        model.addAttribute("category", categoryService.getcategoryById(id));
        model.addAttribute("btnType", "Update");
        return "categoriesAdd";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Category category = categoryService.getcategoryById(id);
        if (category == null) {
            System.out.println("kkk");
            redirectAttributes.addFlashAttribute("error", "Category Not Found");
        }
        categoryService.deleteCategory(id);
        redirectAttributes.addFlashAttribute("success", "Category Deleted Successfully");
        return "redirect:/admin/categories";
    }


    //product function
    @GetMapping("/products")
    public String getProduct() {
        return "products";
    }

    @GetMapping("/products/add")
    public String getProductForm() {
        return "productsAdd";
    }
}
