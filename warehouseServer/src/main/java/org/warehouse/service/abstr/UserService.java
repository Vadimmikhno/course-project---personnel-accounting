package org.warehouse.service.abstr;

import org.warehouse.model.User;

import java.util.Optional;

public interface UserService extends DefaultService<User> {
    Optional<User> findByUsername(String username);
}
