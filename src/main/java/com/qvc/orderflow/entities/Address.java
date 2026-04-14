package com.qvc.orderflow.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
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
    private String vorname;

    @Size(max = 50)
    @Column(name = "nachname")
    private String nachname;

    @Size(max = 100)
    @Column(name = "firma")
    private String firma;


    @Column(name = "ist_paketshop")
    private Boolean istPaketshop = false;

    @Column(name = "paketshop_anbieter")
    @Enumerated(EnumType.STRING)
    private PaketshopAnbieter paketshopAnbieter;

    @Size(max = 20)
    @Column(name = "paketshop_id", length = 20)
    private String paketshopId;


    @Column(name = "strasse")
    private String strasse;


    @Column(name = "hausnummer")
    private String hausnummer;

    @Column(name = "adresszusatz")
    private String adresszusatz;


    @Column(name = "plz")
    private String plz;


    @Column(name = "ort")
    private String ort;

    @Size(max = 50)
    @Column(name = "bundesland")
    private String bundesland;


    @Column(name = "land")
    private String land;


    @Column(name = "laendercode", columnDefinition = "CHAR(2)")
    private String laendercode;


    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JoinColumn(name = "customer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;
}