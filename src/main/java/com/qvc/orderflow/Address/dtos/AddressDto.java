package com.qvc.orderflow.Address.dtos;

import com.qvc.orderflow.Address.AddressType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class AddressDto {
    private int id;
    @Enumerated(EnumType.STRING)
    private AddressType addressType;
    private String houseNumber;
    private String plz;
    private String street;
    private String city;
    private String country;
    private String firstName;
    private String lastName;
    private String companyName;
    private Boolean isParcelShop = false;

}
