package modules.pet.controllers;

import modules.pet.dto.PetCreateRequestDTO;
import modules.pet.models.Pet;
import shared.BaseService;


public interface PetCtrl extends BaseService<Pet> {
    public Pet createPet(PetCreateRequestDTO entity);
    public Pet updatePet(PetCreateRequestDTO entity);
}
