package it.adesso.awesomepizza.service;

import it.adesso.awesomepizza.repository.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderProductService {
    @Autowired
    private OrderProductRepository orderProductRepository;
}
