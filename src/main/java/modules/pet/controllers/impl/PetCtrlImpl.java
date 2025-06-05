package modules.pet.controllers.impl;

import java.util.List;
import modules.pet.controllers.PetCtrl;
import modules.pet.dao.PetDao;
import modules.pet.dto.PetCreateRequestDTO;
import modules.pet.models.Pet;
import org.json.JSONObject;
import shared.DaoFactory;
import shared.JsonMapper;
import shared.PaginationResult;

public class PetCtrlImpl implements PetCtrl {

    PetDao petDao;

    public PetCtrlImpl() {
        this.instanceConn();
    }

    private void instanceConn() {
        DaoFactory daoFactory = DaoFactory.getInstance();
        petDao = daoFactory.getPetDao();
    }

    @Override
    public void crear(Pet pet) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Pet createPet(PetCreateRequestDTO jsonRequest) {
        Pet petToCreate = JsonMapper.mapJsonToDto(new JSONObject(jsonRequest), Pet.class);
        int petIdCreate = petDao.create(petToCreate);

        petToCreate.setPet_id(petIdCreate);

        return petToCreate;
    }

    @Override
    public Pet updatePet(PetCreateRequestDTO jsonRequest) {

        if (jsonRequest == null || jsonRequest.getPet_id() == 0) {
            throw new IllegalArgumentException("El ID del servicio es necesario para actualizar.");
        }

        Pet petToUpdate = JsonMapper.mapJsonToDto(new JSONObject(jsonRequest), Pet.class);
        petToUpdate.setPet_id(jsonRequest.getPet_id());

        petDao.update(petToUpdate);

        return petToUpdate;
    }

    @Override
    public Pet buscar(Object id) {
        return petDao.find(id);
    }

    @Override
    public List<Pet> listar() {
        return petDao.findAll();
    }

    @Override
    public void update(Pet pet) {
        petDao.update(pet);
    }

    @Override
    public void borrar(Object id) {
        petDao.delete(id);
    }

    @Override
    public PaginationResult paginate(String query, int page, int perPage) {
        return petDao.paginate(query, page, perPage);
    }
}
