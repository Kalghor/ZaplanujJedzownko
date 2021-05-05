package pl.coderslab.dao;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Admin;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    private static final String CREATE_ADMIN_QUERY = "INSERT INTO admins(first_name, last_name, email, password, superadmin) VALUES (?,?,?,?,?)";
    private static final String GET_PASSWORD_QUERY = "SELECT password FROM admins WHERE email = ?";
    private static final String READ_ADMIN_QUERY = "SELECT * FROM admins WHERE id = ?";
    private static final String READ_ADMIN_BY_EMAIL_QUERY = "SELECT * FROM admins WHERE email = ?";
    private static final String FIND_ALL_ADMINS_QUERY = "SELECT * FROM admins";
    private static final String DELETE_ADMIN_QUERY = "DELETE FROM admins WHERE id = ?";
    private static final String UPDATE_ADMIN_QUERY = "UPDATE admins SET first_name = ?, last_name = ?, email = ? WHERE id = ?";


    public Admin read(int adminId) {
        Admin admin = new Admin();
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement prepStmt = conn.prepareStatement(READ_ADMIN_QUERY)) {

            prepStmt.setInt(1, adminId);
            try (ResultSet rs = prepStmt.executeQuery()) {
                while (rs.next()) {
                    admin.setId(rs.getInt("id"));
                    admin.setFirstName(rs.getString("first_name"));
                    admin.setLastName(rs.getString("last_name"));
                    admin.setEmail(rs.getString("email"));
                    admin.setEmail(rs.getString("email"));
                    admin.setPassword(rs.getString("password"));
                    admin.setSuperadmin(rs.getInt("superadmin"));
                    admin.setEnable(rs.getInt("enable"));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return admin;
    }

    public Admin read(String adminEmail) {
        Admin admin = new Admin();
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement prepStmt = conn.prepareStatement(READ_ADMIN_BY_EMAIL_QUERY)) {

            prepStmt.setString(1, adminEmail);
            try (ResultSet rs = prepStmt.executeQuery()) {
                while (rs.next()) {
                    admin.setId(rs.getInt("id"));
                    admin.setFirstName(rs.getString("first_name"));
                    admin.setLastName(rs.getString("last_name"));
                    admin.setEmail(rs.getString("email"));
                    admin.setEmail(rs.getString("email"));
                    admin.setPassword(rs.getString("password"));
                    admin.setSuperadmin(rs.getInt("superadmin"));
                    admin.setEnable(rs.getInt("enable"));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return admin;
    }

    public List<Admin> findAll() {
        List<Admin> adminList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_ADMINS_QUERY);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Admin adminToAdd = new Admin();
                adminToAdd.setId(rs.getInt("id"));
                adminToAdd.setFirstName(rs.getString("first_name"));
                adminToAdd.setLastName(rs.getString("last_name"));
                adminToAdd.setEmail(rs.getString("email"));
                adminToAdd.setEmail(rs.getString("email"));
                adminToAdd.setPassword(rs.getString("password"));
                adminToAdd.setSuperadmin(rs.getInt("superadmin"));
                adminToAdd.setEnable(rs.getInt("enable"));
                adminList.add(adminToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminList;
    }

    public void delete(Integer adminId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ADMIN_QUERY)) {
            statement.setInt(1, adminId);
            statement.executeUpdate();

            boolean deleted = statement.execute();
            if (!deleted) {
                throw new NotFoundException("Admin not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Admin admin) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ADMIN_QUERY)) {
            statement.setInt(4, admin.getId());
            statement.setString(1, admin.getFirstName());
            statement.setString(2, admin.getLastName());
            statement.setString(3, admin.getEmail());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Admin create(Admin admin) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement prepStmt = conn.prepareStatement(CREATE_ADMIN_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
            prepStmt.setString(1, admin.getFirstName());
            prepStmt.setString(2, admin.getLastName());
            prepStmt.setString(3, admin.getEmail());
            prepStmt.setString(4, hashPassword(admin.getPassword()));
            prepStmt.setInt(5, admin.isSuperadmin());
            int result = prepStmt.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = prepStmt.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    admin.setId(generatedKeys.getInt(1));
                    return admin;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean isValid(String email, String pass) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement prepStmt = conn.prepareStatement(GET_PASSWORD_QUERY)) {
            prepStmt.setString(1, email);
            ResultSet resultSet = prepStmt.executeQuery();

            if (!resultSet.next()) {
                return false;
            } else {
                if (BCrypt.checkpw(pass, resultSet.getString(1))) {
                    return true;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean uniqueEmail(String email){
        try(Connection conn = DbUtil.getConnection();
        PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM admins WHERE email = ?")){
            prepStmt.setString(1, email);
            ResultSet rs = prepStmt.executeQuery();
            return !rs.next();

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    String hashPassword(String pass) {
        return BCrypt.hashpw(pass, BCrypt.gensalt());
    }

}
