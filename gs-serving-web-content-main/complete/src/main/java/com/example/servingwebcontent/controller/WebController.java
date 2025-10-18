package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.Category;
import com.example.servingwebcontent.model.Customer;
import com.example.servingwebcontent.model.Invoice;
import com.example.servingwebcontent.model.Product;
import com.example.servingwebcontent.model.Supplier;
import com.example.servingwebcontent.model.User;
import com.example.servingwebcontent.model.Order;
import com.example.servingwebcontent.service.CategoryService;
import com.example.servingwebcontent.service.CustomerService;
import com.example.servingwebcontent.service.InvoiceService;
import com.example.servingwebcontent.service.ProductService;
import com.example.servingwebcontent.service.SupplierService;
import com.example.servingwebcontent.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
public class WebController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final SupplierService supplierService;
    private final CustomerService customerService;
    private final InvoiceService invoiceService;
    private final com.example.servingwebcontent.service.OrderService orderService;
    private final UserService userService;

    public WebController(ProductService productService, CategoryService categoryService,
                         SupplierService supplierService, CustomerService customerService,
                         InvoiceService invoiceService, UserService userService,
                         com.example.servingwebcontent.service.OrderService orderService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.supplierService = supplierService;
        this.customerService = customerService;
        this.invoiceService = invoiceService;
        this.userService = userService;
        this.orderService = orderService;
    }

    // Landing page (public)
    @GetMapping({"/"})
    public String index() {
        return "index";
    }

    // Products (protected UI)
    @GetMapping({"/products"})
    public String products(Model model) {
        model.addAttribute("products", productService.findAll());
        return "products";
    }

    // Login page (Thymeleaf)
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/products/new")
    public String productForm(Model model) {
        model.addAttribute("product", new Product("", 0, java.math.BigDecimal.ZERO));
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("suppliers", supplierService.findAll());
        return "product-form";
    }

    @PostMapping("/products/save")
    public String productSave(@ModelAttribute Product product, @RequestParam(required = false) Long categoryId,
                              @RequestParam(required = false) Long supplierId,
                              @RequestParam(required = false) String price) {
        if (categoryId != null) {
            categoryService.findById(categoryId).ifPresent(product::setCategory);
        }
        if (supplierId != null) {
            supplierService.findById(supplierId).ifPresent(product::setSupplier);
        }
        if (price != null && !price.isBlank()) {
            try {
                product.setPrice(new BigDecimal(price));
            } catch (Exception ignored) {
            }
        }
        productService.save(product);
        return "redirect:/products";
    }

    // Categories
    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "categories";
    }

    @GetMapping("/categories/new")
    public String categoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "category-form";
    }

    @PostMapping("/categories/save")
    public String categorySave(@ModelAttribute Category category) {
        categoryService.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/categories/delete/{id}")
    public String categoryDelete(@PathVariable Long id) {
        categoryService.deleteById(id);
        return "redirect:/categories";
    }

    // Suppliers
    @GetMapping("/suppliers")
    public String suppliers(Model model) {
        model.addAttribute("suppliers", supplierService.findAll());
        return "suppliers";
    }

    @GetMapping("/suppliers/new")
    public String supplierForm(Model model) {
        model.addAttribute("supplier", new Supplier());
        return "supplier-form";
    }

    @PostMapping("/suppliers/save")
    public String supplierSave(@ModelAttribute Supplier supplier) {
        supplierService.save(supplier);
        return "redirect:/suppliers";
    }

    @GetMapping("/suppliers/delete/{id}")
    public String supplierDelete(@PathVariable Long id) {
        supplierService.deleteById(id);
        return "redirect:/suppliers";
    }

    // Customers
    @GetMapping("/customers")
    public String customers(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "customers";
    }

    @GetMapping("/customers/new")
    public String customerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer-form";
    }

    @PostMapping("/customers/save")
    public String customerSave(@ModelAttribute Customer customer) {
        customerService.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/customers/delete/{id}")
    public String customerDelete(@PathVariable Long id) {
        customerService.deleteById(id);
        return "redirect:/customers";
    }

    // Orders (list only)
    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "orders";
    }

    // Invoices
    @GetMapping("/invoices")
    public String invoices(Model model) {
        model.addAttribute("invoices", invoiceService.findAll());
        return "invoices";
    }

    // Users
    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/users/new")
    public String userForm(Model model) {
        model.addAttribute("userDto", new com.example.servingwebcontent.dto.UserCreateDTO());
        return "user-form";
    }

    @PostMapping("/users/save")
    public String userSave(@ModelAttribute com.example.servingwebcontent.dto.UserCreateDTO dto) {
        userService.createFromDto(dto);
        return "redirect:/users";
    }
}

