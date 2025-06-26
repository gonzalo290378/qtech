package com.spring.datatree.controller;

import com.spring.datatree.dto.NodeRequestDTO;
import com.spring.datatree.dto.NodeResponseDTO;
import com.spring.datatree.services.NodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nodes")
public class NodeController {

    private final NodeService nodeService;

    public NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @PostMapping()
    public ResponseEntity<NodeResponseDTO> addChild(
            @RequestBody NodeRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(nodeService.addChild(dto));
    }

    @DeleteMapping("/{nodeName}")
    public ResponseEntity<Void> deleteNode(@PathVariable String nodeName) {
        nodeService.deleteNode(nodeName);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{nodeName}/move/{newParentName}")
    public ResponseEntity<NodeResponseDTO> moveNode(
            @PathVariable String nodeName,
            @PathVariable String newParentName) {
        return ResponseEntity.ok(nodeService.moveNode(nodeName, newParentName));
    }

    @GetMapping()
    public ResponseEntity<List<String>> getChildrenNodes(@RequestParam String parentId) {
        return ResponseEntity.ok(nodeService.getChildren(parentId));
    }
}
