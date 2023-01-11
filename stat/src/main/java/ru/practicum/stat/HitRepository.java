package ru.practicum.stat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.stat.model.Hit;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface HitRepository extends JpaRepository<Hit, Long>, HitRepositoryCustom {
}
