package modules.customer.controllers.impl;

import java.util.List;
import modules.customer.controllers.CustomerCtrl;
import modules.customer.dao.CustomerDao;
import modules.customer.dto.CustomerCreateRequestDTO;
import modules.customer.models.Customer;
import org.json.JSONObject;
import shared.DaoFactory;
import shared.JsonMapper;
import shared.PaginationResult;
import shared.dao.person.PersonDao;
import shared.models.Person;

public class CustomerCtrlImpl implements CustomerCtrl {

    CustomerDao customerDao;
    PersonDao personDao;

    public CustomerCtrlImpl() {
        this.instanceConn();
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
        Person personToCreate = JsonMapper.mapJsonToDto(new JSONObject(jsonRequest), Person.class);
        Customer customerToCreate = JsonMapper.mapJsonToDto(new JSONObject(jsonRequest), Customer.class);

        personToCreate.setPerson_id(jsonRequest.getPerson_id());
        personDao.update(personToCreate);

        customerToCreate.setCustomer_id(jsonRequest.getCustomer_id());
        customerDao.update(customerToCreate);

        return customerToCreate;
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
