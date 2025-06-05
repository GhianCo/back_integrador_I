/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shared;

import modules.pet.dao.PetDao;
import modules.pet.dao.impl.PetDaoImpl;
import modules.service.dao.ServiceDao;
import modules.service.dao.impl.ServiceDaoImpl;
import modules.user.dao.UserDao;
import modules.user.dao.impl.UserDaoImpl;
import shared.dao.person.PersonDao;
import shared.dao.person.impl.PersonDaoImpl;

/**
 *
 * @author ghianco
 */
public class DaoFactory {

    private DaoFactory() {
    }

    public static DaoFactory getInstance() {
        return DaoFactoryHolder.INSTANCE;
    }

    private static class DaoFactoryHolder {

        private static final DaoFactory INSTANCE = new DaoFactory();
    }

    public ServiceDao getServiceDao() {
        return new ServiceDaoImpl();
    }

    public PersonDao getPersonDao() {
        return new PersonDaoImpl();
    }

    public UserDao getUserDao() {
        return new UserDaoImpl();
    }

    public PetDao getPetDao() {
        return new PetDaoImpl();
    }

}
