package modules.customer.controllers;

import modules.customer.dto.CustomerCreateRequestDTO;
import modules.customer.models.Customer;
import shared.BaseService;

public interface CustomerCtrl extends BaseService<Customer> {

    public Customer createCustomerAndPerson(CustomerCreateRequestDTO entity);
    public Customer updateCustomerAndPerson(CustomerCreateRequestDTO entity);
}
