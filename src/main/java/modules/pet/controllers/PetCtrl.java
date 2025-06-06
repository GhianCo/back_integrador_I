package modules.pet.controllers;

import modules.pet.dto.PetCreateRequestDTO;
import modules.pet.dto.PetDetailsDTO;
import modules.pet.models.Pet;
import shared.BaseService;


public interface PetCtrl extends BaseService<Pet> {
    public PetDetailsDTO createPet(PetCreateRequestDTO entity);
    public PetDetailsDTO updatePet(PetCreateRequestDTO entity);
}
