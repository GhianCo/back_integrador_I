package modules.customer;

import jakarta.ws.rs.DELETE;
import shared.ActionPayload;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import modules.customer.controllers.impl.CustomerCtrlImpl;
import modules.customer.dto.CustomerCreateRequestDTO;
import modules.customer.models.Customer;
import shared.PaginationResult;

@Path("customer")
public class CustomerResource {

    @GET
    @Path("getAll")
    public Response getAll(
            @DefaultValue("") @QueryParam("query") String query,
            @DefaultValue("1") @QueryParam("page") String page,
            @DefaultValue("10") @QueryParam("perPage") String perPage
    ) {

        CustomerCtrlImpl customerCtrl = new CustomerCtrlImpl();
        PaginationResult data = customerCtrl.paginate(query, Integer.parseInt(page), Integer.parseInt(perPage));

        return Response
                .status(Response.Status.OK)
                .entity(new ActionPayload(200, data.getData(), "Lista de clientes", data.getPagination()))
                .build();
    }
    
    @GET
    @Path("{id}")
    public Response getOne(@PathParam("id") String customerId) {
        CustomerCtrlImpl customercustomer = new CustomerCtrlImpl();
        Customer customer = customercustomer.buscar(customerId);
        return Response
                .status(Response.Status.OK)
                .entity(new ActionPayload(200, customer, "Cliente encontrado"))
                .build();
    }

    @POST
    public Response create(CustomerCreateRequestDTO customerCreateRequestDTO) {
        CustomerCtrlImpl customercustomer = new CustomerCtrlImpl();
        Customer data = customercustomer.createCustomerAndPerson(customerCreateRequestDTO);
        return Response
                .status(Response.Status.OK)
                .entity(new ActionPayload(201, data, "Cliente creado exitosamente"))
                .build();
    }

    @POST
    @Path("{id}")
    public Response update(@PathParam("id") int customerId, CustomerCreateRequestDTO customerCreateRequestDTO) {
        CustomerCtrlImpl customercustomer = new CustomerCtrlImpl();
        customerCreateRequestDTO.setCustomer_id(customerId);
        Customer data = customercustomer.updateCustomerAndPerson(customerCreateRequestDTO);
        return Response
                .status(Response.Status.OK)
                .entity(new ActionPayload(200, data, "Cliente actualizado exitosamente"))
                .build();
    }
    
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int customerId) {
        CustomerCtrlImpl customercustomer = new CustomerCtrlImpl();
        customercustomer.borrar(customerId);
        return Response
                .status(Response.Status.OK)
                .entity(new ActionPayload(200, customerId, "Cliente eliminado exitosamente"))
                .build();
    }
}
