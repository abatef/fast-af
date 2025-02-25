package com.snd.fileupload.repositories;

import com.snd.fileupload.models.User;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User getUserByUsername(@Size(max = 15) String username);
}
