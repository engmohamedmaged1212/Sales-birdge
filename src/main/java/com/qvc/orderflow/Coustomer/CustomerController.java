package com.qvc.orderflow.Coustomer;

import com.qvc.orderflow.Coustomer.dtos.CustomerMapper;
import com.qvc.orderflow.Coustomer.dtos.NewCustomerRequestDto;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerServices customerServices;
    private final CustomerMapper customerMapper;
    @PostMapping("/create")
    public ResponseEntity<?> createNewCustomer(@Valid @RequestBody NewCustomerRequestDto request) {
        var customer = customerServices.createCustomer(request);
        return ResponseEntity.ok(customer);
    }
}
