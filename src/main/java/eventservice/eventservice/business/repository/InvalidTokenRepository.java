package eventservice.eventservice.business.repository;

import eventservice.eventservice.business.repository.model.InvalidTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidTokenRepository extends JpaRepository<InvalidTokenEntity, Long> {
}
