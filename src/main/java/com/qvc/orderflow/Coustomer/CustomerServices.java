package com.qvc.orderflow.Coustomer;

import com.qvc.orderflow.Coustomer.dtos.CustomerDto;
import com.qvc.orderflow.Coustomer.dtos.CustomerMapper;
import com.qvc.orderflow.Coustomer.dtos.NewCustomerRequestDto;
import com.qvc.orderflow.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomerServices {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final UserRepository userRepository;

    public CustomerDto createCustomer(NewCustomerRequestDto request){
        var customer = customerMapper.toEntity(request);
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId =(Long) authentication.getPrincipal();
        var user = userRepository.findById(userId);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy(user.get());
        if (customer.getAddresses() != null) {
            customer.getAddresses().forEach(addr -> addr.setCustomer(customer));
        }
        customerRepository.save(customer);
        return customerMapper.customerToCustomerDto(customer);
    }
}
