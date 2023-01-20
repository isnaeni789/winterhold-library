package com.winterhold.dto.account;

import com.winterhold.validator.PasswordComparer;
import com.winterhold.validator.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@PasswordComparer(message = "Confirm Password harus match")
public class RegisterDTO {

    @UniqueUsername(message = "Username sudah ada di database")
    @NotBlank(message = "Username tidak boleh kosong")
    @Size(max = 20, message = "Username tidak boleh lebih dari 20 karakter.")
    private String username;

    @NotBlank(message = "Password tidak boleh kosong")
    @Size(min = 8, message = "Password minimal 8 karakter")
    private String password;

    @NotBlank(message = "Password harus dikonfirmasi")
    @Size(min = 8, message = "Password minimal 8 karakter")
    private String confirmPassword;

}
