/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modules.pet.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import modules.pet.dto.PetDetailsDTO;

/**
 *
 * @author ghianco
 */
public class PetDetailsAssembler {
    public PetDetailsDTO fromResultSet(ResultSet rs) throws SQLException {
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
}
