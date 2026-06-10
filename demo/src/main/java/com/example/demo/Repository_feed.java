
package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Repository_feed extends JpaRepository<Feed, Long> {
    // Pronto! Essa interface já herda métodos como salvar, deletar, buscar, etc.
}
