package dev.bmtech.libraryexpensetracker.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository interface that handles directly interacting with
 * the transaction database without having to write queries
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
