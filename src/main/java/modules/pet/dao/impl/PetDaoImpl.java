package modules.pet.dao.impl;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import shared.conn.DBConn;
import shared.Pagination;
import shared.PaginationResult;

import java.sql.*;
import java.util.ArrayList;
import modules.pet.assembler.PetDetailsAssembler;
import modules.pet.dao.PetDao;
import modules.pet.dto.PetDetailsDTO;
import modules.pet.models.Pet;

public class PetDaoImpl implements PetDao {

    @Override
    public int create(Pet entity) {
        Preconditions.checkNotNull(entity, "La mascota no puede ser null");
        int id = 0;

        String sql = "INSERT INTO pet (customer_id, name, especie, breed, birthdate, gender) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConn.getConnection(); PreparedStatement pst = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pst.setInt(1, entity.getCustomer_id());
            pst.setString(2, entity.getName());
            pst.setString(3, entity.getEspecie());
            pst.setString(4, entity.getBreed());
            pst.setString(5, entity.getBirthdate());
            pst.setString(6, entity.getGender());

            pst.executeUpdate();

            try (ResultSet resultSet = pst.getGeneratedKeys()) {
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                    entity.setPet_id(id);
                }
            }

        } catch (SQLException ex) {
            System.out.println("Error al crear mascota: " + ex.getMessage());
        }

        return id;
    }

    @Override
    public Pet find(Object petId) {
        String sql = "SELECT * FROM pet WHERE pet_id = ? LIMIT 1";

        try (Connection connection = DBConn.getConnection(); PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setObject(1, petId);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Pet(
                            rs.getInt("pet_id"),
                            rs.getInt("customer_id"),
                            rs.getString("name"),
                            rs.getString("especie"),
                            rs.getString("breed"),
                            rs.getString("birthdate"),
                            rs.getString("gender"),
                            rs.getString("active")
                    );
                }
            }

        } catch (SQLException ex) {
            System.out.println("Error al buscar mascota: " + ex.getMessage());
        }

        return null;
    }

    @Override
    public ArrayList<Pet> findAll() {
        ArrayList<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM pet WHERE active = '1'";

        try (Connection connection = DBConn.getConnection(); Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                pets.add(new Pet(
                        rs.getInt("pet_id"),
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("especie"),
                        rs.getString("breed"),
                        rs.getString("birthdate"),
                        rs.getString("gender"),
                        rs.getString("active")
                ));
            }

        } catch (SQLException ex) {
            System.out.println("Error al listar mascotas: " + ex.getMessage());
        }

        return pets;
    }

    @Override
    public void update(Pet entity) {
        Preconditions.checkNotNull(entity, "La mascota no puede ser null");

        String sql = "UPDATE pet SET customer_id = ?, name = ?, especie = ?, breed = ?, birthdate = ?, gender = ?, active = ? WHERE pet_id = ?";

        try (Connection connection = DBConn.getConnection(); PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setInt(1, entity.getCustomer_id());
            pst.setString(2, entity.getName());
            pst.setString(3, entity.getEspecie());
            pst.setString(4, entity.getBreed());
            pst.setString(5, entity.getBirthdate());
            pst.setString(6, entity.getGender());
            pst.setString(7, entity.getActive());
            pst.setInt(8, entity.getPet_id());

            pst.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error al actualizar mascota: " + ex.getMessage());
        }
    }

    @Override
    public void delete(Object id) {
        String sql = "UPDATE pet SET active = 0 WHERE pet_id = ?";

        try (Connection connection = DBConn.getConnection(); PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setObject(1, id);
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error al eliminar mascota: " + ex.getMessage());
        }
    }

    @Override
    public PaginationResult<ArrayList<PetDetailsDTO>, Pagination> paginate(String query, int page, int perPage) {
        ArrayList<PetDetailsDTO> listaPets = new ArrayList<>();
        int totalRecords = 0;

        StringBuilder sqlBuilder = new StringBuilder(
                "SELECT SQL_CALC_FOUND_ROWS * FROM pet p, customer c, person per WHERE p.customer_id = c.customer_id and c.person_id = per.person_id and p.active = 1"
        );

        if (!Strings.isNullOrEmpty(query)) {
            sqlBuilder.append(" AND (p.name LIKE ? OR p.especie LIKE ?)");
        }

        sqlBuilder.append(" ORDER BY p.pet_id LIMIT ? OFFSET ?");

        try (Connection connection = DBConn.getConnection(); PreparedStatement pst = connection.prepareStatement(sqlBuilder.toString())) {

            int paramIndex = 1;
            if (!Strings.isNullOrEmpty(query)) {
                pst.setString(paramIndex++, "%" + query + "%");
                pst.setString(paramIndex++, "%" + query + "%");
            }

            pst.setInt(paramIndex++, perPage);
            pst.setInt(paramIndex++, (page - 1) * perPage);

            PetDetailsAssembler assembler = new PetDetailsAssembler();
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    listaPets.add(assembler.fromResultSet(rs));
                }
            }

            try (Statement stmt = connection.createStatement(); ResultSet countSet = stmt.executeQuery("SELECT FOUND_ROWS()")) {
                if (countSet.next()) {
                    totalRecords = countSet.getInt(1);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return new PaginationResult<>(null, null);
        }

        int totalPages = (int) Math.ceil((double) totalRecords / perPage);
        Pagination pagination = new Pagination(totalRecords, totalPages, page, perPage);

        return new PaginationResult<>(listaPets, pagination);
    }
}
