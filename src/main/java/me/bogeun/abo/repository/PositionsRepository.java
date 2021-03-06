package me.bogeun.abo.repository;

import me.bogeun.abo.domain.Positions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionsRepository extends JpaRepository<Positions, Long> {
}
