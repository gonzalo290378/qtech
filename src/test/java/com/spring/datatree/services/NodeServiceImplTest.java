package com.spring.datatree.services;

import com.spring.datatree.dto.NodeRequestDTO;
import com.spring.datatree.dto.NodeResponseDTO;
import com.spring.datatree.mapper.NodeMapper;
import com.spring.datatree.model.Node;
import com.spring.datatree.repository.NodeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class NodeServiceImplTest {

    @Mock
    private NodeRepository nodeRepository;

    @Mock
    private NodeMapper nodeMapper;

    @InjectMocks
    private NodeServiceImpl nodeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddChild_Success() {
        Node parent = new Node();
        parent.setName("A");

        Node child = new Node();
        child.setName("B");
        child.setParentId(parent);

        NodeRequestDTO request = new NodeRequestDTO("B", "A");
        Node saved = new Node(1L, "A", parent, new ArrayList<>());
        NodeResponseDTO responseDTO = new NodeResponseDTO(2L, "B", 1L, null);

        when(nodeRepository.findByName("A")).thenReturn(Optional.of(parent));
        when(nodeRepository.save(any(Node.class))).thenReturn(saved);
        when(nodeMapper.toDTO(saved)).thenReturn(responseDTO);

        NodeResponseDTO result = nodeService.addChild(request);

        assertEquals("B", result.getName());
        verify(nodeRepository).findByName("A");
        verify(nodeRepository).save(any(Node.class));
    }

    @Test
    void testAddChild_ParentNotFound() {
        NodeRequestDTO request = new NodeRequestDTO("B", "A");
        when(nodeRepository.findByName("A")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> nodeService.addChild(request));
    }

    @Test
    void testDeleteNode_Success() {
        Node node = new Node();
        node.setName("A");

        when(nodeRepository.findByName("A")).thenReturn(Optional.of(node));

        nodeService.deleteNode("A");

        verify(nodeRepository).delete(node);
    }

    @Test
    void testDeleteNode_NotFound() {
        when(nodeRepository.findByName("A")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> nodeService.deleteNode("A"));
    }


    @Test
    void testMoveNode_NodeNotFound() {
        when(nodeRepository.findByName("D")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> nodeService.moveNode("D", "A"));
    }

    @Test
    void testMoveNode_NewParentNotFound() {
        Node node = new Node();
        node.setName("D");

        when(nodeRepository.findByName("D")).thenReturn(Optional.of(node));
        when(nodeRepository.findByName("A")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> nodeService.moveNode("D", "A"));
    }

    @Test
    void testGetChildren_WithDescendants() {
        Node root = new Node();
        root.setName("A");

        Node child1 = new Node();
        child1.setName("B");
        child1.setParentId(root);

        Node child2 = new Node();
        child2.setName("C");
        child2.setParentId(root);

        Node grandChild = new Node();
        grandChild.setName("D");
        grandChild.setParentId(root);

        child1.setChildren(List.of(grandChild));
        root.setChildren(List.of(child1, child2));

        when(nodeRepository.findByName("A")).thenReturn(Optional.of(root));

        List<String> result = nodeService.getChildren("A");
        result.sort(String::compareTo);
        assertEquals(List.of("B", "C", "D"), result);
    }

    @Test
    void testGetChildren_ParentNotFound() {
        when(nodeRepository.findByName("ROOT")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> nodeService.getChildren("root"));
    }
}