package nttdata.messalhi.forte.dao;

import nttdata.messalhi.forte.entities.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialsDAO extends JpaRepository <UserCredentials, String> {
    Optional<UserCredentials> findByUsername(String username);
    Optional<UserCredentials> getReferenceByUsername(String username);

}