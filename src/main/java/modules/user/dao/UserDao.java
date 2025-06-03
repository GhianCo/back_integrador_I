package modules.user.dao;

import modules.user.models.User;
import shared.EntityDao;

public interface UserDao extends EntityDao<User> {
    public User login(User user);
}
