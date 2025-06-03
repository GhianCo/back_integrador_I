package modules.service;

import jakarta.ws.rs.DELETE;
import shared.ActionPayload;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import modules.service.controllers.impl.ServiceCtrlImpl;
import modules.service.dto.ServiceCreateRequestDTO;
import shared.PaginationResult;
import modules.service.models.Service;

@Path("service")
public class ServiceResource {

    @GET
    @Path("getAll")
    public Response getAll(
            @DefaultValue("") @QueryParam("query") String query,
            @DefaultValue("1") @QueryParam("page") String page,
            @DefaultValue("10") @QueryParam("perPage") String perPage
    ) {

        ServiceCtrlImpl serviceCtrl = new ServiceCtrlImpl();
        PaginationResult data = serviceCtrl.paginate(query, Integer.parseInt(page), Integer.parseInt(perPage));

        return Response
                .status(Response.Status.OK)
                .entity(new ActionPayload(200, data.getData(), "Lista de servicios", data.getPagination()))
                .build();
    }
    
    @GET
    @Path("{id}")
    public Response getOne(@PathParam("id") String serviceId) {
        ServiceCtrlImpl serviceservice = new ServiceCtrlImpl();
        Service service = serviceservice.buscar(serviceId);
        return Response
                .status(Response.Status.OK)
                .entity(new ActionPayload(200, service, "Servicio encontrado"))
                .build();
    }

    @POST
    public Response create(ServiceCreateRequestDTO serviceCreateRequestDTO) {
        ServiceCtrlImpl serviceservice = new ServiceCtrlImpl();
        Service data = serviceservice.createService(serviceCreateRequestDTO);
        return Response
                .status(Response.Status.OK)
                .entity(new ActionPayload(201, data, "Servicio creado exitosamente"))
                .build();
    }

    @POST
    @Path("{id}")
    public Response update(@PathParam("id") int serviceId, ServiceCreateRequestDTO serviceCreateRequestDTO) {
        ServiceCtrlImpl serviceservice = new ServiceCtrlImpl();
        serviceCreateRequestDTO.setService_id(serviceId);
        Service data = serviceservice.updateService(serviceCreateRequestDTO);
        return Response
                .status(Response.Status.OK)
                .entity(new ActionPayload(200, data, "Servicio actualizado exitosamente"))
                .build();
    }
    
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int serviceId) {
        ServiceCtrlImpl serviceservice = new ServiceCtrlImpl();
        serviceservice.borrar(serviceId);
        return Response
                .status(Response.Status.OK)
                .entity(new ActionPayload(200, serviceId, "Servicio eliminado exitosamente"))
                .build();
    }
}
