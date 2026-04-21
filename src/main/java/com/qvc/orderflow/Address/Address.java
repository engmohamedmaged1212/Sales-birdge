package com.qvc.orderflow.Address;

import com.qvc.orderflow.Coustomer.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@Entity
@Table(name = "addresses")
@NoArgsConstructor @AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_type", nullable = false)
    private AddressType addressType;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Size(max = 100)
    @Column(name = "company_name")
    private String companyName;

    @Column(name = "is_parcel_shop", nullable = false)
    private Boolean isParcelShop = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "parcel_shop_provider")
    private ParcelShopProvider parcelShopProvider;

    @Size(max = 20)
    @Column(name = "parcel_shop_id")
    private String parcelShopId;

    @Column(name = "street", nullable = false, length = 100)
    private String street;

    @Column(name = "house_number", nullable = false, length = 10)
    private String houseNumber;

    @Column(name = "address_supplement", length = 100)
    private String addressSupplement;

    @Column(name = "plz", nullable = false, length = 10)
    private String plz;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Size(max = 50)
    @Column(name = "state")
    private String state;

    @Column(name = "country", nullable = false, length = 50)
    private String country = "Germany";

    @Column(name = "country_code", nullable = false, columnDefinition = "CHAR(2)")
    private String countryCode = "DE";

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public enum AddressType { billing, shipping }
}