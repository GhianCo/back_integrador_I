/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modules.pet.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import modules.pet.dto.PetCreateRequestDTO;
import modules.pet.dto.PetDetailsDTO;
import modules.pet.models.Pet;

/**
 *
 * @author ghianco
 */
public class PetDetailsAssembler {
    public static PetDetailsDTO fromResultSet(ResultSet rs) throws SQLException {
        PetDetailsDTO dto = new PetDetailsDTO();

        dto.setPet_id(rs.getInt("pet_id"));
        dto.setName(rs.getString("name"));
        dto.setEspecie(rs.getString("especie"));
        dto.setBreed(rs.getString("breed"));
        dto.setBirthdate(rs.getString("birthdate"));
        dto.setGender(rs.getString("gender"));
        dto.setActive(rs.getString("p.active"));

        dto.setCustomer_id(rs.getInt("customer_id"));
        dto.setCustomer_fullname(rs.getString("per.name") + " " + rs.getString("per.lastname"));  

        return dto;
    }
    
    
    public static PetDetailsDTO fromCreateUpdateRequest(Pet pet, PetCreateRequestDTO request) {
        PetDetailsDTO dto = new PetDetailsDTO();
        dto.setPet_id(pet.getPet_id());
        dto.setCustomer_id(pet.getCustomer_id());
        dto.setName(pet.getName());
        dto.setEspecie(pet.getEspecie());
        dto.setBreed(pet.getBreed());
        dto.setBirthdate(pet.getBirthdate());
        dto.setGender(pet.getGender());
        dto.setActive(pet.getActive());
        dto.setCustomer_fullname(request.getCustomer_fullname());
        return dto;
    }
}
