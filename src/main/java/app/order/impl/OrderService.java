package app.order.impl;

import app.order.OrderRepository;
import framework.annotations.spring.Autowired;
import framework.annotations.spring.Bean;
import framework.annotations.spring.Qualifier;
import framework.annotations.spring.Scope;

@Bean(scope = Scope.PROTOTYPE)
public class OrderService {

    @Qualifier("orderRepository")
    @Autowired(verbose = true)
    private OrderRepository orderRepository;

    public boolean placeOrder() {
        return orderRepository.saveOrder();
    }
}
