package com.fimsolution.group.app.repository.f2f;

import com.fimsolution.group.app.model.business.f2f.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EventsRepository extends JpaRepository<Event, String> {
}
