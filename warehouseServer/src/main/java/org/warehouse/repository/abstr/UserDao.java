package org.warehouse.repository.abstr;

import org.warehouse.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends DefaultDao<User> {
    User findByUsername(String name);
    List<User> getByFirstName(String name);
}
