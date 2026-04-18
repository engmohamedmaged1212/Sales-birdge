package com.qvc.orderflow.Address;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServices {
    private final AddressRepository addressRepository;

}
