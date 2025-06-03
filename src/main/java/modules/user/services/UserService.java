package modules.user.services;

import modules.user.dto.UserCreateRequestDTO;
import modules.user.models.User;
import shared.BaseService;

public interface UserService extends BaseService<User> {
    public User login(User user);
    public User createUserAndPersona(UserCreateRequestDTO entity);
    public User updateUserAndPersona(UserCreateRequestDTO entity);
}
