package com.spring.datatree.repository;

import com.spring.datatree.model.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Repository
public interface NodeRepository extends JpaRepository<Node, String> {

    Optional<Node> findByName(String name);

}
