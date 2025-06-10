package modules.customer.controllers.impl;

import java.util.List;
import modules.customer.controllers.CustomerCtrl;
import modules.customer.dao.CustomerDao;
import modules.customer.dto.CustomerCreateRequestDTO;
import modules.customer.models.Customer;
import modules.service.dao.ServiceDao;
import org.json.JSONObject;
import shared.DaoFactory;
import shared.JsonMapper;
import shared.PaginationResult;
import shared.dao.person.PersonDao;
import shared.exceptions.InvalidArg;
import shared.models.Person;
import shared.utils.EmailUtils;

public class CustomerCtrlImpl implements CustomerCtrl {

    CustomerDao customerDao;
    PersonDao personDao;

    public CustomerCtrlImpl() {
        this.instanceConn();
    }

    public CustomerCtrlImpl(CustomerDao customerDao, PersonDao personDao) {
        this.customerDao = customerDao;
        this.personDao = personDao;
    }

    private void instanceConn() {
        DaoFactory daoFactory = DaoFactory.getInstance();
        customerDao = daoFactory.getCustomerDao();
        personDao = daoFactory.getPersonDao();
    }

    @Override
    public void crear(Customer customer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Customer createCustomerAndPerson(CustomerCreateRequestDTO jsonRequest) {
        Person personToCreate = JsonMapper.mapJsonToDto(new JSONObject(jsonRequest), Person.class);

        if (personToCreate.getDni().length() != 8) {
            throw new InvalidArg("El DNI enviado no es tiene el formato correcto.");
        }
        if (!EmailUtils.isValidEmail(personToCreate.getEmail())) {
            throw new InvalidArg("El correo enviado no es válido.");
        }

        int personIdCreate = personDao.create(personToCreate);

        Customer customerToCreate = JsonMapper.mapJsonToDto(new JSONObject(jsonRequest), Customer.class);
        customerToCreate.setPerson_id(personIdCreate);

        int customerIdCreate = customerDao.create(customerToCreate);

        customerToCreate.setCustomer_id(customerIdCreate);
        customerToCreate.setPerson_id(personIdCreate);
        customerToCreate.setName(personToCreate.getName());
        customerToCreate.setLastname(personToCreate.getLastname());
        customerToCreate.setDni(personToCreate.getDni());
        customerToCreate.setEmail(personToCreate.getEmail());
        customerToCreate.setPhone(personToCreate.getPhone());
        customerToCreate.setAddress(personToCreate.getAddress());

        return customerToCreate;
    }

    @Override
    public Customer updateCustomerAndPerson(CustomerCreateRequestDTO jsonRequest) {
        Person personToUpdate = JsonMapper.mapJsonToDto(new JSONObject(jsonRequest), Person.class);
        if (personToUpdate.getDni().length() != 8) {
            throw new InvalidArg("El DNI enviado no es tiene el formato correcto.");
        }
        if (!EmailUtils.isValidEmail(personToUpdate.getEmail())) {
            throw new InvalidArg("El correo enviado no es válido.");
        }
        Customer customerToUpdate = JsonMapper.mapJsonToDto(new JSONObject(jsonRequest), Customer.class);

        personToUpdate.setPerson_id(jsonRequest.getPerson_id());
        personDao.update(personToUpdate);

        customerToUpdate.setCustomer_id(jsonRequest.getCustomer_id());
        customerDao.update(customerToUpdate);

        customerToUpdate.setName(personToUpdate.getName());
        customerToUpdate.setLastname(personToUpdate.getLastname());
        customerToUpdate.setDni(personToUpdate.getDni());
        customerToUpdate.setEmail(personToUpdate.getEmail());
        customerToUpdate.setPhone(personToUpdate.getPhone());
        customerToUpdate.setAddress(personToUpdate.getAddress());

        return customerToUpdate;
    }

    @Override
    public Customer buscar(Object id) {
        return customerDao.find(id);
    }

    @Override
    public List<Customer> listar() {
        return customerDao.findAll();
    }

    @Override
    public void update(Customer customer) {
        customerDao.update(customer);
    }

    @Override
    public void borrar(Object id) {
        customerDao.delete(id);
    }

    @Override
    public PaginationResult paginate(String query, int page, int perPage) {
        return customerDao.paginate(query, page, perPage);
    }
}
