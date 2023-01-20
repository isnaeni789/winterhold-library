package com.winterhold.dto.author;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorGridDTO {
    private Long id;
    private String fullName;
    private Long age;
    private String status;
    private String education;

    public AuthorGridDTO(Long id, String fullName, LocalDate birthDate, LocalDate deceasedDate,
                         String education) {
        this.id =id;
        this.fullName = fullName;
        this.age = calculateAge(birthDate, deceasedDate);
        this.status = generateStatus(birthDate, deceasedDate);
        this.education = education;
    }

    private Long calculateAge(LocalDate birthDate, LocalDate deceasedDate){
        return (deceasedDate == null) ? ChronoUnit.YEARS.between(birthDate, LocalDate.now())
                : ChronoUnit.YEARS.between(birthDate, deceasedDate);
    }

    private String generateStatus(LocalDate birthDate, LocalDate deceasedDate){
        return (deceasedDate == null) ? "Alive" : "Deceased";
    }
}
