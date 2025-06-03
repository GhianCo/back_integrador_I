package shared.dao.person.impl;

import shared.models.Person;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import shared.PaginationResult;
import shared.conn.DBConn;
import shared.dao.person.PersonDao;

public class PersonDaoImpl implements PersonDao {

    private Connection connection;
    private ResultSet resultSet;
    private CallableStatement callableStatement;
    private Statement statement;

    @Override
    public int create(Person entity) {
        int id = 0;
        try {

            connection = DBConn.getConnection();

            String sql = "insert into person (name, lastname, dni, email, phone, address) "
                    + "values (?,?,?,?,?,?)";

            PreparedStatement pst = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            pst.setString(1, entity.getName());
            pst.setString(2, entity.getLastname());
            pst.setString(3, entity.getDni());
            pst.setString(4, entity.getEmail());
            pst.setString(5, entity.getPhone());
            pst.setString(6, entity.getAddress());

            pst.executeUpdate();

            resultSet = pst.getGeneratedKeys();

            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }

            pst.close();
            resultSet.close();
            connection.close();

        } catch (SQLException ex) {
            try {
                System.out.println(ex.getMessage());
                connection.close();
            } catch (SQLException exp) {
                System.out.println(exp.getMessage());
            }
        }
        return id;
    }

    @Override
    public Person find(Object personaId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
    }

    @Override
    public ArrayList<Person> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
    }

    @Override
    public void update(Person entity) {
        try {

            connection = DBConn.getConnection();

            String sql = "update person set name = ?, lastname = ?, dni = ?, email = ?, phone = ?, address = ? where person_id = ?";

            PreparedStatement pst = connection.prepareStatement(sql);

            pst.setString(1, entity.getName());
            pst.setString(2, entity.getLastname());
            pst.setString(3, entity.getDni());
            pst.setString(4, entity.getEmail());
            pst.setString(5, entity.getPhone());
            pst.setString(6, entity.getAddress());

            pst.setString(7, String.valueOf(entity.getPerson_id()));

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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
    }

    @Override
    public PaginationResult<Object, Object> paginate(String query, int page, int perPage) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
    }

}
