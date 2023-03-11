package app.product.impl;

import app.product.ProductRepository;
import com.google.gson.Gson;
import framework.annotations.spring.Component;
import framework.annotations.spring.Qualifier;

import java.util.List;

@Qualifier("productRepository")
@Component
public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public String get() {
        System.out.println("[SERVER] SELECT * FROM Products");
        Gson gson = new Gson();
        return gson.toJson(List.of("Samsung TV - 400$", "PS5 - 800$", "JBL speaker - 200$"));
    }
}
