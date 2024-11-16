package com.fimsolution.group.app.repository.f2f;

import com.fimsolution.group.app.model.business.f2f.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface ScheduleRepository extends JpaRepository<Schedule, String> {

    List<Schedule> findByObligationId(String id);

}
