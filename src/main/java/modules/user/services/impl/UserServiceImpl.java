package modules.user.services.impl;

import modules.user.models.User;
import java.util.ArrayList;
import shared.dao.person.PersonDao;
import modules.user.dao.UserDao;
import modules.user.dto.UserCreateRequestDTO;
import modules.user.services.UserService;
import org.json.JSONObject;
import shared.DaoFactory;
import shared.JsonMapper;
import shared.PaginationResult;
import shared.models.Person;
import shared.dao.person.PersonDao;

public class UserServiceImpl implements UserService {

    UserDao userDao;
    PersonDao personDao;

    public UserServiceImpl() {
        this.instanceConn();
    }

    private void instanceConn() {
        DaoFactory daoFactory = DaoFactory.getInstance();
        userDao = daoFactory.getUserDao();
        personDao = daoFactory.getPersonDao();
    }

    @Override
    public void crear(User user) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public User createUserAndPersona(UserCreateRequestDTO jsonRequest) {
        Person personaToCreate = JsonMapper.mapJsonToDto(new JSONObject(jsonRequest), Person.class);
        int personaIdCreate = personDao.create(personaToCreate);

        User userToCreate = JsonMapper.mapJsonToDto(new JSONObject(jsonRequest), User.class);
        userToCreate.setPerson_id(personaIdCreate);

        int userIdCreate = userDao.create(userToCreate);

        userToCreate.setUser_id(userIdCreate);
        userToCreate.setPerson_id(personaIdCreate);
        userToCreate.setName(personaToCreate.getName());
        userToCreate.setLastname(personaToCreate.getLastname());
        userToCreate.setDni(personaToCreate.getDni());
        userToCreate.setEmail(personaToCreate.getEmail());
        userToCreate.setPhone(personaToCreate.getPhone());

        return userToCreate;
    }

    @Override
    public User updateUserAndPersona(UserCreateRequestDTO jsonRequest) {
        Person personaToCreate = JsonMapper.mapJsonToDto(new JSONObject(jsonRequest), Person.class);
        User userToCreate = JsonMapper.mapJsonToDto(new JSONObject(jsonRequest), User.class);

        personaToCreate.setPerson_id(jsonRequest.getPerson_id());
        personDao.update(personaToCreate);
                
        userToCreate.setUser_id(jsonRequest.getUser_id());
        userDao.update(userToCreate);

        return userToCreate;
    }

    @Override
    public User buscar(Object id) {
        return userDao.find(id);
    }

    @Override
    public ArrayList<User> listar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(User user) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void borrar(Object id) {
        userDao.delete(id);
    }

    @Override
    public User login(User login) {
        User user = userDao.login(login);
        return user;
    }

    @Override
    public PaginationResult paginate(String query, int page, int perPage) {
        return userDao.paginate(query, page, perPage);
    }

}
