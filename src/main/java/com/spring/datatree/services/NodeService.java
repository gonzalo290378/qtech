package com.spring.datatree.services;

import com.spring.datatree.dto.NodeRequestDTO;
import com.spring.datatree.dto.NodeResponseDTO;

import java.util.List;

public interface NodeService {

    NodeResponseDTO addChild(NodeRequestDTO request);

    void deleteNode(String nodeId);

    //NodeResponseDTO moveNode(String nodeId, String newParentId);

    List<String> getChildren(String parentId);


}
