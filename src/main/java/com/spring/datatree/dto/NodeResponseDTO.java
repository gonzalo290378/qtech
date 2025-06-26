package com.spring.datatree.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeResponseDTO {

    private Long id;

    private String name;

    private Long parent_id;

    private List<Long> childrenIds;

}
