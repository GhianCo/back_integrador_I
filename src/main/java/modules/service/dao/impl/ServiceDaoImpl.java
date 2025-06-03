/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modules.service.dao.impl;

import modules.service.dao.ServiceDao;
import modules.service.models.Service;
import shared.conn.DBConn;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import shared.Pagination;
import shared.PaginationResult;

public class ServiceDaoImpl implements ServiceDao {

    private Connection connection;
    private ResultSet resultSet;
    private Statement statement;

    @Override
    public int create(Service entity) {
        int id = 0;
        try {
            connection = DBConn.getConnection();

            String sql = "INSERT INTO service (name, description, price) "
                    + "VALUES (?, ?, ?)";

            PreparedStatement pst = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, entity.getName());
            pst.setString(2, entity.getDescription());
            pst.setDouble(3, entity.getPrice());

            // Ejecutar la inserción
            pst.executeUpdate();

            // Obtener el ID generado
            resultSet = pst.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1); // El ID generado es recuperado aquí
            }

            // Asignar el ID al objeto Service
            entity.setService_id(id);

            pst.close();
            resultSet.close();
            connection.close();
        } catch (SQLException ex) {
            try {
                System.out.println(ex.getMessage());
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException exp) {
                System.out.println(exp.getMessage());
            }
        }
        return id;
    }

    @Override
    public Service find(Object serviceId) {
        Service service = null;

        try {

            connection = DBConn.getConnection();

            String sql = "select * from service where service_id = " + serviceId + " limit 1";

            statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                int service_id = resultSet.getInt("service_id");
                String name = resultSet.getString("name");
                String descripcion = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                String active = resultSet.getString("active");

                service = new Service(service_id, name, descripcion, price, active);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            try {
                System.out.println(ex.getMessage());
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException exp) {
                System.out.println(exp.getMessage());
            }
        }
        return service;
    }

    @Override
    public ArrayList<Service> findAll() {
        ArrayList<Service> services = new ArrayList<>();

        try {
            connection = DBConn.getConnection();

            String sql = "select * from service where active = '1'";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int service_id = resultSet.getInt("service_id");
                String name = resultSet.getString("name");
                String descripcion = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                String active = resultSet.getString("active");

                services.add(new Service(service_id, name, descripcion, price, active));
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return services;
    }

    @Override
    public void update(Service entity) {
        try {

            connection = DBConn.getConnection();

            String sql = "update service set name = ?, description = ?, price = ?, active = ? where service_id = ?";

            PreparedStatement pst = connection.prepareStatement(sql);

            pst.setString(1, entity.getName());
            pst.setString(2, entity.getDescription());
            pst.setDouble(3, entity.getPrice());
            pst.setString(4, entity.getActive());
            pst.setInt(5, entity.getService_id());

            pst.executeUpdate();

            pst.close();
            connection.close();
        } catch (SQLException ex) {
            try {
                System.out.println(ex.getMessage());
                connection.close();
            } catch (SQLException exp) {
                System.out.println(exp.getMessage());
            }
        }
    }

    @Override
    public void delete(Object id) {
        try {

            connection = DBConn.getConnection();

            String sql = "update service set active = 0 where service_id = " + id;
            statement = connection.createStatement();
            statement.executeUpdate(sql);

            statement.close();
            connection.close();
        } catch (SQLException ex) {
            try {
                System.out.println(ex.getMessage());
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException exp) {
                System.out.println(exp.getMessage());
            }
        }
    }

    @Override
    public PaginationResult<ArrayList<Service>, Pagination> paginate(String query, int page, int perPage) {
        ArrayList<Service> listaServices = new ArrayList<>();
        int totalRecords = 0;

        String sql = "SELECT SQL_CALC_FOUND_ROWS * "
                + "FROM service "
                + "WHERE active = 1 "
                + (query != null && !query.isEmpty() ? " AND (name LIKE ? OR description LIKE ?)" : "")
                + " ORDER BY service_id LIMIT ? OFFSET ?";

        try (Connection connection = DBConn.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            int paramIndex = 1;
            if (query != null && !query.isEmpty()) {
                preparedStatement.setString(paramIndex++, "%" + query + "%");
                preparedStatement.setString(paramIndex++, "%" + query + "%");
            }

            preparedStatement.setInt(paramIndex++, perPage);
            preparedStatement.setInt(paramIndex++, (page - 1) * perPage);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int service_id = resultSet.getInt("service_id");
                    String name = resultSet.getString("name");
                    String descripcion = resultSet.getString("description");
                    double price = resultSet.getDouble("price");
                    String active = resultSet.getString("active");

                    listaServices.add(new Service(service_id, name, descripcion, price, active));
                }
            }

            try (Statement stmt = connection.createStatement()) {
                ResultSet countResultSet = stmt.executeQuery("SELECT FOUND_ROWS()");
                if (countResultSet.next()) {
                    totalRecords = countResultSet.getInt(1);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return new PaginationResult<>(null, null);
        }

        int totalPages = (int) Math.ceil((double) totalRecords / perPage);
        Pagination pagination = new Pagination(totalRecords, totalPages, page, perPage);

        return new PaginationResult<>(listaServices, pagination);
    }
}
