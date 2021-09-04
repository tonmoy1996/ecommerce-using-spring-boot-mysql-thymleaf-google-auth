package com.spring.ecommerce.controller;

import com.spring.ecommerce.dto.ProductDTO;
import com.spring.ecommerce.model.Category;
import com.spring.ecommerce.model.Product;
import com.spring.ecommerce.service.CategoryService;
import com.spring.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

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
    public String postCatForm(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin/categories/add";
        }
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


    //product section
    @GetMapping("/products")
    public String getProduct(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        return "products";
    }

    @GetMapping("/products/add")
    public String getProductForm(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "productsAdd";
    }

    @PostMapping("/products/add")
    public String saveProduct(@ModelAttribute("productDTO")
                                      ProductDTO productDTO, @RequestParam("productImage") MultipartFile file,
                              @RequestParam("image") String image) throws IOException {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());
        product.setCategory(categoryService.getcategoryById((long) productDTO.getCategoryId()));
        //image
        String imageUUID;
        if (!file.isEmpty()) {
            imageUUID = String.valueOf(UUID.randomUUID()) + file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDirectory, imageUUID);
            Files.write(fileNameAndPath, file.getBytes());
        } else {
            imageUUID = image;
        }
        product.setImage(imageUUID);
        productService.addProduct(product);
        return "redirect:/admin/products";
    }

}
