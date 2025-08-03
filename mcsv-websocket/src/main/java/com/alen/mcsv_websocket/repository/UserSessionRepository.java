package com.alen.mcsv_websocket.repository;

import com.alen.mcsv_websocket.model.UserSession;
import org.springframework.data.repository.CrudRepository;

public interface UserSessionRepository extends CrudRepository<UserSession, String> {
}
