package modules.pet;

import jakarta.ws.rs.DELETE;
import shared.ActionPayload;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import modules.pet.controllers.impl.PetCtrlImpl;
import modules.pet.dto.PetCreateRequestDTO;
import modules.pet.models.Pet;
import shared.PaginationResult;

@Path("pet")
public class PetResource {

    @GET
    @Path("getAll")
    public Response getAll(
            @DefaultValue("") @QueryParam("query") String query,
            @DefaultValue("1") @QueryParam("page") String page,
            @DefaultValue("10") @QueryParam("perPage") String perPage
    ) {

        PetCtrlImpl petCtrl = new PetCtrlImpl();
        PaginationResult data = petCtrl.paginate(query, Integer.parseInt(page), Integer.parseInt(perPage));

        return Response
                .status(Response.Status.OK)
                .entity(new ActionPayload(200, data.getData(), "Lista de mascotas", data.getPagination()))
                .build();
    }
    
    @GET
    @Path("{id}")
    public Response getOne(@PathParam("id") String petId) {
        PetCtrlImpl petpet = new PetCtrlImpl();
        Pet pet = petpet.buscar(petId);
        return Response
                .status(Response.Status.OK)
                .entity(new ActionPayload(200, pet, "Mascota encontrado"))
                .build();
    }

    @POST
    public Response create(PetCreateRequestDTO petCreateRequestDTO) {
        PetCtrlImpl petpet = new PetCtrlImpl();
        return Response
                .status(Response.Status.OK)
                .entity(new ActionPayload(201, petpet.createPet(petCreateRequestDTO), "Mascota creada exitosamente"))
                .build();
    }

    @POST
    @Path("{id}")
    public Response update(@PathParam("id") int petId, PetCreateRequestDTO petCreateRequestDTO) {
        PetCtrlImpl petpet = new PetCtrlImpl();
        petCreateRequestDTO.setPet_id(petId);
        return Response
                .status(Response.Status.OK)
                .entity(new ActionPayload(200, petpet.updatePet(petCreateRequestDTO), "Mascota actualizada exitosamente"))
                .build();
    }
    
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int petId) {
        PetCtrlImpl petpet = new PetCtrlImpl();
        petpet.borrar(petId);
        return Response
                .status(Response.Status.OK)
                .entity(new ActionPayload(200, petId, "Mascota eliminada exitosamente"))
                .build();
    }
}
