package app.order.impl;

import com.google.gson.Gson;
import framework.annotations.http.POST;
import framework.annotations.http.Path;
import framework.annotations.spring.Autowired;
import framework.annotations.spring.Controller;

@Controller
public class OrderController {

    @Autowired(verbose = true)
    OrderService orderService;

    @POST
    @Path("/orderProducts")
    public String postMethod(){
        boolean isOrdered = orderService.placeOrder();
        Gson gson = new Gson();
        if (isOrdered) {
            return gson.toJson("Products have been ordered");
        } else {
            return gson.toJson("Products have not been ordered");
        }
    }
}
