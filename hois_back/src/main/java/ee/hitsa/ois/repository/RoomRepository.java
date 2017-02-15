package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
