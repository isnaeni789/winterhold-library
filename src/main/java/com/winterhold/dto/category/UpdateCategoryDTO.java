package com.winterhold.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryDTO {
    @NotBlank(message = "Nama kategori tidak boleh kosong")
    @Size(max = 100, message = "Nama kategori maksimal 100 karakter")
    private String name;

    @NotNull(message = "Floor tidak boleh null")
    @Min(value = 1)
    private Integer floor;

    @NotBlank(message = "Isle tidak boleh kosong")
    @Size(max = 10, message = "Maksimal 10 karakter")
    private String isle;

    @NotBlank(message = "Bay tidak boleh kosong")
    @Size(max = 10, message = "Maksimal 10 karakter")
    private String bay;
}
