package com.campus.Campus.Connect.repository;

import com.campus.Campus.Connect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
