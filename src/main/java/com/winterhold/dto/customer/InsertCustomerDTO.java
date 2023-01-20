package com.winterhold.dto.customer;

import com.winterhold.validator.UniqueMembershipNumber;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsertCustomerDTO {

    @UniqueMembershipNumber(message = "Memembership Number sudah ada di database")
    @NotBlank(message = "Memembership Number tidak boleh kosong")
    @Size(max = 20, message = "Memembership Number maksimal 20 karakter")
    private String membershipNumber;

    @NotBlank(message = "Nama depan tidak boleh kosong")
    @Size(max = 50, message = "Maksimal 50 karakter")
    private String firstName;

    @Size(max = 50, message = "Maksimal 50 karakter")
    private String lastName;

    @NotNull(message = "Tanggal lahir tidak boleh kosong")
    @Past(message = "Customer tidak mungkin lahir di masa depan.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotNull(message = "Gender tidak boleh kosong")
    @Size(max = 10, message = "Maksimal 10 karakter")
    private String gender;

    @Size(max = 20, message = "Maksimal 20 karakter")
    private String phone;

    @Size(max = 500, message = "Maksimal 500 karakter")
    private String address;

    private LocalDate membershipExpireDate;

}
