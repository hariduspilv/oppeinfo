package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import ee.hitsa.ois.domain.Room;

public interface RoomRepository extends CrudRepository<Room, Long>, JpaSpecificationExecutor<Room> {
}
