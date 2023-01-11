package ru.practicum.explorewithme.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.explorewithme.user.model.User;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM users as u WHERE u.id IN (:usersIds)")
    List<User> findUsersInList(@Param("usersIds") List<Long> usersIds);

    Page<User> findAll(Pageable pageable);
}
