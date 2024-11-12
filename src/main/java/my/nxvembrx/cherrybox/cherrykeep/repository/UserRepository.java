package my.nxvembrx.cherrybox.cherrykeep.repository;

import my.nxvembrx.cherrybox.cherrykeep.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
