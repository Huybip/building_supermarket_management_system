package com.example.servingwebcontent;

import com.example.servingwebcontent.dto.UserCreateDTO;
import com.example.servingwebcontent.model.Category;
import com.example.servingwebcontent.model.Customer;
import com.example.servingwebcontent.model.Product;
import com.example.servingwebcontent.model.Supplier;
import com.example.servingwebcontent.service.CategoryService;
import com.example.servingwebcontent.service.CustomerService;
import com.example.servingwebcontent.service.ProductService;
import com.example.servingwebcontent.service.SupplierService;
import com.example.servingwebcontent.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Profile("h2")
public class SampleDataLoader implements CommandLineRunner {
    private final CategoryService categoryService;
    private final SupplierService supplierService;
    private final ProductService productService;
    private final CustomerService customerService;
    private final UserService userService;

    public SampleDataLoader(CategoryService categoryService, SupplierService supplierService,
                            ProductService productService, CustomerService customerService,
                            UserService userService) {
        this.categoryService = categoryService;
        this.supplierService = supplierService;
        this.productService = productService;
        this.customerService = customerService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            if (categoryService.findAll().isEmpty()) {
                Category c1 = new Category(); c1.setName("Fruits"); c1.setDescription("Fresh fruits"); categoryService.save(c1);
                Category c2 = new Category(); c2.setName("Dairy"); c2.setDescription("Milk & dairy"); categoryService.save(c2);

                Supplier s1 = new Supplier(); s1.setName("LocalFarm"); s1.setContactName("Anh"); s1.setPhone("0123456789"); s1.setEmail("farm@example.com"); supplierService.save(s1);

                Product p1 = new Product("Apple", 100, BigDecimal.valueOf(0.5)); p1.setCategory(c1); p1.setSupplier(s1); productService.save(p1);
                Product p2 = new Product("Milk", 50, BigDecimal.valueOf(1.2)); p2.setCategory(c2); p2.setSupplier(s1); productService.save(p2);

                Customer cust = new Customer(); cust.setName("Nguyen Van A"); cust.setEmail("a@example.com"); cust.setPhone("0987654321"); customerService.save(cust);

                UserCreateDTO admin = new UserCreateDTO();
                admin.setUsername("admin");
                admin.setPassword("admin123");
                admin.setRole("ADMIN");
                admin.setFullName("Administrator");
                admin.setPhone("000");
                admin.setAddress("admin@example.com");
                try {
                    userService.createFromDto(admin);
                } catch (Exception ignored) {
                }
            }
        } catch (Exception e) {
            // If schema isn't ready or some DDL errors happened, log and continue startup
            System.err.println("SampleDataLoader: setup skipped due to: " + e.getMessage());
        }
    }
}
