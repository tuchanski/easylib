package com.tuchanski.EasyLib.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

import com.tuchanski.EasyLib.models.Library;
import com.tuchanski.EasyLib.models.User;

public interface LibraryRepository extends JpaRepository<Library, UUID> {

    boolean existsByUser(User user);

}
