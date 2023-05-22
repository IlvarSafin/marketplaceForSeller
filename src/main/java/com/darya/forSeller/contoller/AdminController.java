package com.darya.forSeller.contoller;

import com.darya.forSeller.entity.Product;
import com.darya.forSeller.repository.ProductRepository;
import com.darya.forSeller.repository.SellerRepository;
import com.darya.forSeller.service.ProductService;
import com.darya.forSeller.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final SellerService sellerService;
    private final SellerRepository sellerRepository;

    @Autowired
    public AdminController(ProductService productService, ProductRepository productRepository, SellerService sellerService, SellerRepository sellerRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.sellerService = sellerService;
        this.sellerRepository = sellerRepository;
    }

    @GetMapping("/notConfirmeds")
    public String notConfirmeds(Model model){
        model.addAttribute("products", productRepository.findAllByStatus(false));
        return "notConfirmeds";
    }

    @GetMapping("/notConfirmed/{id}")
    public String notConfirmeds(Model model,
                                @PathVariable("id") int id){
        model.addAttribute("product", productRepository.findById(id)
                .orElseThrow(()->new RuntimeException("product not found")));
        return "notConfirmed";
    }

    @PostMapping("/notConfirmed/{id}")
    public String changeStatus(@PathVariable("id") int id){
        Product product = productRepository.findById(id)
                .orElseThrow(()->new RuntimeException("product not found"));
        product.setStatus(true);
        productRepository.save(product);
        return "redirect:/admin/notConfirmeds";
    }
}
