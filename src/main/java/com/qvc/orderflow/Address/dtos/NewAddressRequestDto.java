package com.qvc.orderflow.Address.dtos;

import com.qvc.orderflow.Address.AddressType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

@Data
public class NewAddressRequestDto {
    private AddressType addressType;
    @NotBlank(message = "Street is required ")
    private String street;
    @NotBlank(message = "City is required")
    private String city;
    @NotBlank(message = "Country is required")
    private String country;
    @NotBlank(message = "plz is required")
    private String plz;
    @NotBlank(message = "House Number is required")
    private String houseNumber;
    @NotBlank(message = "code")
    private String countryCode;

}
