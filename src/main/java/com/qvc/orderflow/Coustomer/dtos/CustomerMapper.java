package com.qvc.orderflow.Coustomer.dtos;

import com.qvc.orderflow.Address.dtos.AddressMapper;
import com.qvc.orderflow.Coustomer.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = {AddressMapper.class})
public interface CustomerMapper {
    CustomerDto customerToCustomerDto(Customer customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "addresses", source = "newAddress", qualifiedByName = "mapAddressToList")
    Customer toEntity(NewCustomerRequestDto dto);
}
