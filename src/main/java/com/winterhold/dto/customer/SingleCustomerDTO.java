package com.winterhold.dto.customer;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data @Builder
public class SingleCustomerDTO {
    private String membershipNumber;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String gender;
    private String phone;
    private String address;
    private LocalDate membershipExpireDate;
}
