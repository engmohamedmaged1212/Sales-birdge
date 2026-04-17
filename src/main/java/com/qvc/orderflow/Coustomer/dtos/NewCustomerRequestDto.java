package com.qvc.orderflow.Coustomer.dtos;

import com.qvc.orderflow.Address.dtos.NewAddressRequestDto;
import com.qvc.orderflow.Coustomer.Gender;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NewCustomerRequestDto {
    @NotNull(message = "Gender is required")
    private Gender gender;
    @NotBlank(message = "First name pls")
    private String firstName;
    @NotBlank(message = "Last name plz")
    private String lastName;
    @NotNull(message = "Birth date is required")
    @Past(message = "The date must be in the past ")
    private LocalDate dateOfBirth;
    @NotBlank(message = "Phone number")
    private String phoneNumber;
    @Valid
    @NotNull(message = "Address is required")
    private NewAddressRequestDto newAddress;

}
