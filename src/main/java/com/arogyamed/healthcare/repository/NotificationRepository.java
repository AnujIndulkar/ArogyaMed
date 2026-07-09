package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.Notification;
import com.arogyamed.healthcare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUser(User user);

}
