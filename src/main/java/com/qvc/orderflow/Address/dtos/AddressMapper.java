package com.qvc.orderflow.Address.dtos;

import com.qvc.orderflow.Address.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDto toAddressDto(Address address);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    Address toAddress(NewAddressRequestDto dto);
}
