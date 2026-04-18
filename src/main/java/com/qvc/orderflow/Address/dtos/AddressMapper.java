package com.qvc.orderflow.Address.dtos;

import com.qvc.orderflow.Address.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDto toAddressDto(Address address);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "countryCode", source = "countryCode")
    Address toAddress(NewAddressRequestDto dto);
    @Named("mapAddressToList")
    default List<Address> mapAddressToList(NewAddressRequestDto dto) {
        if (dto == null) return Collections.emptyList();
        return Collections.singletonList(toAddress(dto));
    }
}
