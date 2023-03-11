package app.product.impl;

import framework.annotations.http.GET;
import framework.annotations.http.Path;
import framework.annotations.spring.Autowired;
import framework.annotations.spring.Controller;

@Controller
public class ProductController {
    @Autowired(verbose = true)
    ProductService productService;

    @GET
    @Path("/getProducts")
    public String getMethod(){
        return productService.getProducts();
    }
}
