package nttdata.messalhi.forte.dao;

import nttdata.messalhi.forte.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoDAO extends JpaRepository<UserInfo, String> {
    Optional<UserInfo> findByUsername(String username);

    Optional<UserInfo> getReferenceByUsername(String username);
}