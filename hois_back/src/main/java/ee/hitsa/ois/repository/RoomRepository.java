package ee.hitsa.ois.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ee.hitsa.ois.domain.Room;

public interface RoomRepository extends CrudRepository<Room, Long>, JpaSpecificationExecutor<Room> {

    @Query("select r from Room r where r.building.id=?1")
    Page<Room> findAllByBuilding_id(Long buildingId, Pageable pageable);
}
