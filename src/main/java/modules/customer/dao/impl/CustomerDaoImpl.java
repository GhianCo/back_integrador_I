package modules.customer.dao.impl;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import shared.conn.DBConn;
import shared.Pagination;
import shared.PaginationResult;

import java.sql.*;
import java.util.ArrayList;
import modules.customer.dao.CustomerDao;
import modules.customer.models.Customer;

public class CustomerDaoImpl implements CustomerDao {

    @Override
    public int create(Customer entity) {
        Preconditions.checkNotNull(entity, "El cliente no puede ser null");
        int id = 0;

        String sql = "INSERT INTO customer (person_id) VALUES (?)";

        try (Connection connection = DBConn.getConnection(); PreparedStatement pst = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pst.setInt(1, entity.getPerson_id());

            pst.executeUpdate();

            try (ResultSet resultSet = pst.getGeneratedKeys()) {
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
            }

        } catch (SQLException ex) {
            System.out.println("Error al crear cliente: " + ex.getMessage());
        }

        return id;
    }

    @Override
    public Customer find(Object customerId) {
        String sql = "select c.customer_id, c.person_id, p.name, p.lastname, p.dni, p.email, p.phone, p.address, c.active from customer c, person p where c.person_id = p.person_id and c.active = 1 and c.customer_id = ? limit 1";

        try (Connection connection = DBConn.getConnection(); PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setObject(1, customerId);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {

                    int customer_id = rs.getInt("customer_id");
                    int person_id = rs.getInt("person_id");
                    String name = rs.getString("name");
                    String lastname = rs.getString("lastname");
                    String dni = rs.getString("dni");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");
                    String active = rs.getString("active");

                    Customer customer = new Customer(customer_id, person_id);
                    customer.setName(name);
                    customer.setLastname(lastname);
                    customer.setDni(dni);
                    customer.setEmail(email);
                    customer.setPhone(phone);
                    customer.setAddress(address);
                    customer.setActive(active);

                    return customer;
                }
            }

        } catch (SQLException ex) {
            System.out.println("Error al buscar cliente: " + ex.getMessage());
        }

        return null;
    }

    @Override
    public ArrayList<Customer> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Customer entity) {
        Preconditions.checkNotNull(entity, "El cliente no puede ser null");

        String sql = "UPDATE customer SET person_id = ? WHERE customer_id = ?";

        try (Connection connection = DBConn.getConnection(); PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setInt(1, entity.getPerson_id());
            pst.setInt(2, entity.getCustomer_id());

            pst.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error al actualizar cliente: " + ex.getMessage());
        }
    }

    @Override
    public void delete(Object id) {
        String sql = "UPDATE customer SET active = 0 WHERE customer_id = ?";

        try (Connection connection = DBConn.getConnection(); PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setObject(1, id);
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error al eliminar cliente: " + ex.getMessage());
        }
    }

    @Override
    public PaginationResult<ArrayList<Customer>, Pagination> paginate(String query, int page, int perPage) {
        ArrayList<Customer> listaCustomers = new ArrayList<>();
        int totalRecords = 0;

        StringBuilder sqlBuilder = new StringBuilder(
                "SELECT SQL_CALC_FOUND_ROWS * FROM customer c, person p WHERE p.person_id = c.person_id and c.active = 1"
        );

        if (!Strings.isNullOrEmpty(query)) {
            sqlBuilder.append(" AND (p.name LIKE ? OR p.lastname LIKE ? OR p.dni LIKE ?)");
        }

        sqlBuilder.append(" ORDER BY customer_id LIMIT ? OFFSET ?");

        try (Connection connection = DBConn.getConnection(); PreparedStatement pst = connection.prepareStatement(sqlBuilder.toString())) {

            int paramIndex = 1;
            if (!Strings.isNullOrEmpty(query)) {
                pst.setString(paramIndex++, "%" + query + "%");
                pst.setString(paramIndex++, "%" + query + "%");
                pst.setString(paramIndex++, "%" + query + "%");
            }

            pst.setInt(paramIndex++, perPage);
            pst.setInt(paramIndex++, (page - 1) * perPage);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int customer_id = rs.getInt("customer_id");
                    int person_id = rs.getInt("person_id");
                    String name = rs.getString("name");
                    String lastname = rs.getString("lastname");
                    String dni = rs.getString("dni");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");
                    String active = rs.getString("active");

                    Customer customer = new Customer(customer_id, person_id);
                    customer.setName(name);
                    customer.setLastname(lastname);
                    customer.setDni(dni);
                    customer.setEmail(email);
                    customer.setPhone(phone);
                    customer.setAddress(address);
                    customer.setActive(active);
                    
                    listaCustomers.add(customer);
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

        return new PaginationResult<>(listaCustomers, pagination);
    }
}
