package com.qvc.orderflow.Address;

import com.qvc.orderflow.Coustomer.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    private Integer id;

    @Column(name = "address_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AddressType addressType;


    @Column(name = "vorname")
    private String firstName;

    @Size(max = 50)
    @Column(name = "nachname")
    private String lastName;

    @Size(max = 100)
    @Column(name = "firma")
    private String companyName;


    @Column(name = "ist_paketshop")
    private Boolean isParcelShop = false;

    @Column(name = "paketshop_anbieter")
    @Enumerated(EnumType.STRING)
    private ParcelShopProvider parcelShopProvider;

    @Size(max = 20)
    @Column(name = "paketshop_id", length = 20)
    private String parcelShopId;


    @Column(name = "strasse")
    private String street;


    @Column(name = "hausnummer")
    private String houseNumber;

    @Column(name = "adresszusatz")
    private String addressSupplement;


    @Column(name = "plz")
    private String plz;


    @Column(name = "ort")
    private String city;

    @Size(max = 50)
    @Column(name = "bundesland")
    private String federalState;


    @Column(name = "land")
    private String country;


    @Column(name = "laendercode", columnDefinition = "CHAR(2)")
    private String countryCode;


    @Column(name = "is_default")
    private Boolean isDefault = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JoinColumn(name = "customer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;
}