package it.adesso.awesomepizza.service;

import it.adesso.awesomepizza.repository.OrderRepository;
import it.adesso.awesomepizza.repository.OrderStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderStateService {
    @Autowired
    private OrderStateRepository orderStateRepository;
}
