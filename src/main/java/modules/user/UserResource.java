package modules.user;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import modules.user.dto.UserCreateRequestDTO;
import modules.user.models.User;
import modules.user.services.impl.UserServiceImpl;
import shared.ActionPayload;
import shared.PaginationResult;

@Path("user")
public class UserResource {

    @GET
    @Path("getAll")
    public Response getAll(
            @DefaultValue("") @QueryParam("query") String query,
            @DefaultValue("1") @QueryParam("page") String page,
            @DefaultValue("10") @QueryParam("perPage") String perPage
    ) {
        UserServiceImpl userservice = new UserServiceImpl();
        PaginationResult data = userservice.paginate(query, Integer.parseInt(page), Integer.parseInt(perPage));

        return Response
                .status(Response.Status.OK)
                .entity(new ActionPayload(200, data.getData(), "Lista de usuarios", data.getPagination()))
                .build();
    }

    @GET
    @Path("{id}")
    public Response getOne(@PathParam("id") String userId) {
        UserServiceImpl userservice = new UserServiceImpl();
        User user = userservice.buscar(userId);
        return Response
                .status(Response.Status.OK)
                .entity(new ActionPayload(200, user, "Usuario encontrado"))
                .build();
    }

    @POST
    public Response create(UserCreateRequestDTO userCreateRequestDTO) {
        UserServiceImpl userservice = new UserServiceImpl();
        User data = userservice.createUserAndPersona(userCreateRequestDTO);
        return Response
                .status(Response.Status.OK)
                .entity(new ActionPayload(201, data, "Usuario creado exitosamente"))
                .build();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") int userId, UserCreateRequestDTO userCreateRequestDTO) {
        UserServiceImpl userservice = new UserServiceImpl();
        userCreateRequestDTO.setUser_id(userId);
        User data = userservice.updateUserAndPersona(userCreateRequestDTO);
        return Response
                .status(Response.Status.OK)
                .entity(new ActionPayload(200, data, "Usuario actualizado exitosamente"))
                .build();
    }
    
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int userId) {
        UserServiceImpl userservice = new UserServiceImpl();
        userservice.borrar(userId);
        return Response
                .status(Response.Status.OK)
                .entity(new ActionPayload(200, userId, "Usuario eliminado exitosamente"))
                .build();
    }
}
