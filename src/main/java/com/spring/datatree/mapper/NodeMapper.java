package com.spring.datatree.mapper;

import com.spring.datatree.dto.NodeResponseDTO;
import com.spring.datatree.model.Node;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NodeMapper {

    public NodeResponseDTO toDTO(Node node) {
        List<Long> childrenIds = node.getChildren().stream().map(Node::getId).toList();

        return new NodeResponseDTO(
                node.getId(),
                node.getName(),
                node.getParentId() != null ? node.getParentId().getId() : null,
                childrenIds
        );
    }
}
