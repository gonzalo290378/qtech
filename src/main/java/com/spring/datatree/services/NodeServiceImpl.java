package com.spring.datatree.services;

import com.spring.datatree.dto.NodeRequestDTO;
import com.spring.datatree.dto.NodeResponseDTO;
import com.spring.datatree.mapper.NodeMapper;
import com.spring.datatree.model.Node;
import com.spring.datatree.repository.NodeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NodeServiceImpl implements NodeService {

    private final NodeRepository nodeRepository;
    private final NodeMapper nodeMapper;

    public NodeServiceImpl(NodeRepository nodeRepository, NodeMapper nodeMapper) {
        this.nodeRepository = nodeRepository;
        this.nodeMapper = nodeMapper;
    }

    public NodeResponseDTO addChild(NodeRequestDTO request) {
        Node parent = nodeRepository.findByName(request.getParentName().toUpperCase())
                .orElseThrow(() -> new EntityNotFoundException("Parent not found"));

        Node child = new Node();
        child.setName(request.getChildName().toUpperCase());
        child.setParentId(parent);
        Node saved = nodeRepository.save(child);
        return nodeMapper.toDTO(saved);
    }

    public void deleteNode(String nodeName) {
        Node node = nodeRepository.findByName(nodeName.toUpperCase())
                .orElseThrow(() -> new EntityNotFoundException("Node not found"));
        nodeRepository.delete(node);
    }

    public NodeResponseDTO moveNode(String nodeName, String newParentName) {
        Node node = nodeRepository.findByName(nodeName)
                .orElseThrow(() -> new EntityNotFoundException("Node not found"));
        Node newParent = nodeRepository.findByName(newParentName)
                .orElseThrow(() -> new EntityNotFoundException("New parent not found"));

        node.setParentId(newParent);
        Node updated = nodeRepository.save(node);
        return nodeMapper.toDTO(updated);
    }


    public List<String> getChildren(String parentName) {
        Node parent = nodeRepository.findByName(parentName.toUpperCase())
                .orElseThrow(() -> new EntityNotFoundException("Node not found"));

        List<String> allDescendants = new ArrayList<>();
        collectDescendantNames(parent, allDescendants);
        allDescendants.sort(String::compareTo);
        return allDescendants;
    }

    private void collectDescendantNames(Node node, List<String> result) {
        for (Node child : node.getChildren()) {
            result.add(child.getName());
            collectDescendantNames(child, result);
        }
    }


}