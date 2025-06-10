package modules.user.dao.impl;

import modules.user.models.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modules.user.dao.UserDao;
import shared.Pagination;
import shared.PaginationResult;
import shared.conn.DBConn;

public class UserDaoImpl implements UserDao {

    private Connection connection;
    private ResultSet resultSet;
    private CallableStatement callableStatement;
    private Statement statement;

    @Override
    public int create(User entity) {
        int id = 0;
        try {

            connection = DBConn.getConnection();

            String sql = "insert into user (person_id, nick, pass, role) "
                    + "values (?,?,?,?)";

            PreparedStatement pst = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            pst.setInt(1, entity.getPerson_id());
            pst.setString(2, entity.getNick());
            pst.setString(3, entity.getPass());
            pst.setString(4, entity.getRole());

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
    public User find(Object userId) {
        User user = null;

        try {

            connection = DBConn.getConnection();

            String sql = "select u.user_id, u.person_id, u.role, u.nick, u.pass, u.role, p.name, p.lastname, p.dni, p.phone, p.email, p.address from user u, person p where u.person_id = p.person_id and u.active = 1 and u.user_id = " + userId + " limit 1";

            statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                int user_id = resultSet.getInt("user_id");
                int person_id = resultSet.getInt("person_id");
                String nick = resultSet.getString("nick");
                String pass = resultSet.getString("pass");
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastname");
                String dni = resultSet.getString("dni");
                String role = resultSet.getString("role");
                String telefono = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String direccion = resultSet.getString("address");

                user = new User(user_id, person_id, name, lastname, dni, role);
                user.setNick(nick);
                user.setPass(pass);
                user.setPhone(telefono);
                user.setEmail(email);
                user.setAddress(direccion);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            try {
                System.out.println(ex.getMessage());
                resultSet.close();
                callableStatement.close();
                connection.close();
            } catch (SQLException exp) {
                System.out.println(exp.getMessage());
            }
        }
        return user;
    }

    @Override
    public ArrayList<User> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(User entity) {
        try {

            connection = DBConn.getConnection();

            String sql = "update user set nick = ?, pass = ?, role = ? where user_id = ?";

            PreparedStatement pst = connection.prepareStatement(sql);

            pst.setString(1, entity.getNick());
            pst.setString(2, entity.getPass());
            pst.setString(3, entity.getRole());

            pst.setDouble(4, entity.getUser_id());

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

            String sql = "update user set active = 0 where user_id = " + id;
            statement = connection.createStatement();
            statement.executeUpdate(sql);

            statement.close();
            connection.close();
        } catch (SQLException ex) {
            try {
                System.out.println(ex.getMessage());
                resultSet.close();
                callableStatement.close();
                connection.close();
            } catch (SQLException exp) {
                System.out.println(exp.getMessage());
            }
        }
    }

    public User login(User user) {
        User user_login = new User();
        try {

            connection = DBConn.getConnection();
            String sql = "select u.user_id, u.role, u.nick, u.pass, p.name, p.lastname, p.dni, p.email from user u, person p where u.person_id = p.person_id and nick='" + user.getNick() + "' and pass='" + user.getPass()+ "' and u.active = 1";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                user_login.setUser_id(resultSet.getInt(1));
                user_login.setRole(resultSet.getString(2));
                user_login.setNick(resultSet.getString(3));
                user_login.setPass(resultSet.getString(4));
                user_login.setName(resultSet.getString(5));
                user_login.setLastname(resultSet.getString(6));
                user_login.setDni(resultSet.getString(7));
                user_login.setEmail(resultSet.getString(8));
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

        return user_login;

    }

    @Override
    public PaginationResult<ArrayList<User>, Pagination> paginate(String query, int page, int perPage) {
        ArrayList<User> listadeUsers = new ArrayList<>();
        int totalRecords = 0;

        String sql = "SELECT SQL_CALC_FOUND_ROWS u.user_id, u.person_id, p.name, p.lastname, p.dni, p.email, u.active, u.role, u.nick, u.pass, p.phone, p.address "
                + "FROM user u "
                + "JOIN person p ON u.person_id = p.person_id "
                + "WHERE u.active = 1 "
                + (query != null && !query.isEmpty() ? " AND (p.name LIKE ? OR p.lastname LIKE ? OR p.dni LIKE ?)" : "")
                + " ORDER BY u.user_id LIMIT ? OFFSET ?";

        try (Connection connection = DBConn.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            int paramIndex = 1;
            if (query != null && !query.isEmpty()) {
                preparedStatement.setString(paramIndex++, "%" + query + "%");
                preparedStatement.setString(paramIndex++, "%" + query + "%");
                preparedStatement.setString(paramIndex++, "%" + query + "%");
            }

            preparedStatement.setInt(paramIndex++, perPage);
            preparedStatement.setInt(paramIndex++, (page - 1) * perPage);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("user_id");
                    int person_id = resultSet.getInt("person_id");
                    String nombres = resultSet.getString("name");
                    String apellidos = resultSet.getString("lastname");
                    String dni = resultSet.getString("dni");
                    String role = resultSet.getString("role");

                    User usu = new User(id, person_id, nombres, apellidos, dni, role);
                    usu.setEmail(resultSet.getString("email"));
                    usu.setActive(resultSet.getString("active"));
                    usu.setNick(resultSet.getString("nick"));
                    usu.setPass(resultSet.getString("pass"));
                    usu.setPhone(resultSet.getString("phone"));
                    usu.setAddress(resultSet.getString("address"));

                    listadeUsers.add(usu);
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

        return new PaginationResult<>(listadeUsers, pagination);

    }

}
