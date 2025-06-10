package modules.customer.controllers.impl;

import modules.customer.dao.CustomerDao;
import modules.customer.dto.CustomerCreateRequestDTO;
import modules.customer.models.Customer;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import shared.dao.person.PersonDao;
import shared.exceptions.InvalidArg;
import shared.models.Person;

public class CustomerCtrlImplTest {

    @Test
    void testCreateCustomer_Exito() {
        PersonDao personDao = mock(PersonDao.class);
        CustomerDao customerDao = mock(CustomerDao.class);
        CustomerCtrlImpl ctrl = new CustomerCtrlImpl(customerDao, personDao);

        CustomerCreateRequestDTO dto = new CustomerCreateRequestDTO();
        dto.setName("Juan");
        dto.setLastname("Pérez");
        dto.setDni("12345678");
        dto.setEmail("juan@mail.com");
        dto.setPhone("987654321");
        dto.setAddress("Calle Falsa 123");

        when(personDao.create(any(Person.class))).thenReturn(5);
        when(customerDao.create(any(Customer.class))).thenReturn(10);

        Customer result = ctrl.createCustomerAndPerson(dto);

        assertEquals("Juan", result.getName());
        assertEquals(5, result.getPerson_id());
        assertEquals(10, result.getCustomer_id());
    }

    @Test
    void testCreateCustomer_CamposVacios() {
        PersonDao personDao = mock(PersonDao.class);
        CustomerDao customerDao = mock(CustomerDao.class);
        CustomerCtrlImpl ctrl = new CustomerCtrlImpl(customerDao, personDao);

        CustomerCreateRequestDTO dto = new CustomerCreateRequestDTO();
        dto.setName("");
        dto.setLastname("");
        dto.setDni("11119999");
        dto.setEmail("a@a.com");
        dto.setPhone("");
        dto.setAddress("");

        when(personDao.create(any(Person.class))).thenReturn(8);
        when(customerDao.create(any(Customer.class))).thenReturn(9);

        Customer result = ctrl.createCustomerAndPerson(dto);

        assertEquals("", result.getName());
        assertEquals(8, result.getPerson_id());
    }

}
