/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thaitd.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import thaitd.connection.MyConnection;
import thaitd.dtos.RegistrationDTO;

/**
 *
 * @author ThaiT
 */
public class RegistrationDAO {

    private PreparedStatement preStm;
    private Connection conn;
    private ResultSet rs;

    private void closeConnection() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (preStm != null) {
                preStm.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean insert(String username, String password, String fullname, String role) throws Exception {
        boolean check = false;
        try {
            String sql = "INSERT INTO dbo.Registration VALUES (?,?,?,?)";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, username);
            preStm.setString(2, password);
            preStm.setString(3, fullname);
            preStm.setString(4, role);
            check = preStm.executeUpdate() > 0;
        } catch (SQLException e) {
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean update(String username, String password, String fullname, String role) throws Exception {
        boolean check = false;
        try {
            String sql = "UPDATE dbo.Registration SET Password=?, Fullname=?, Role=? WHERE Username=?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(4, username);
            preStm.setString(1, password);
            preStm.setString(2, fullname);
            preStm.setString(3, role);
            check = preStm.executeUpdate() > 0;
        } catch (SQLException e) {
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean delete(String username) throws Exception {
        boolean check = false;
        try {
            String sql = "DELETE dbo.Registration WHERE Username=?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, username);
            check = preStm.executeUpdate() > 0;
        } catch (SQLException e) {
        } finally {
            closeConnection();
        }
        return check;
    }

    public List<RegistrationDTO> getAllAccount() {
        List<RegistrationDTO> result = null;
        try {
            String sql = "SELECT Username, Fullname, Role FROM dbo.Registration";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            String username = null;
            String fullname = null;
            String role = null;
            result = new ArrayList<>();
            while (rs.next()) {
                username = rs.getString("Username");
                fullname = rs.getString("Fullname");
                role = rs.getString("Role");
                RegistrationDTO dto = new RegistrationDTO(username, fullname, role);
                dto.setLeaf(true);
                result.add(dto);
            }
        } catch (SQLException e) {
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<RegistrationDTO> findByFullname(String search) {
        List<RegistrationDTO> result = null;
        try {
            String sql = "SELECT Username, Fullname, Role FROM dbo.Registration WHERE Fullname LIKE ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, "%" + search + "%");
            rs = preStm.executeQuery();
            String username, fullname, role;
            result = new ArrayList<>();
            while (rs.next()) {
                username = rs.getString("Username");
                fullname = rs.getString("Fullname");
                role = rs.getString("Role");
                RegistrationDTO dto = new RegistrationDTO(username, fullname, role);
                dto.setLeaf(true);
                result.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }
}
