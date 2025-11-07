package com.alen.mcsv_presence.repository;

import com.alen.mcsv_presence.model.UserSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSessionRepository extends CrudRepository<UserSession, String> {
    List<UserSession> findByStatus(String status);
}
