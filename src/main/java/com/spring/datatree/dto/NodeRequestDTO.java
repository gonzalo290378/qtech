package com.spring.datatree.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeRequestDTO {

    @NotBlank(message = "Child Node name is required")
    private String childName;

    @NotBlank(message = "Parent Node name is required")
    private String parentName;


}
