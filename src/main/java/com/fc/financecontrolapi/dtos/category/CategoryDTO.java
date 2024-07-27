package com.fc.financecontrolapi.dtos.category;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    @NotBlank
    private String name;
    private String description;
    private Boolean isActive;

    public CategoryDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
