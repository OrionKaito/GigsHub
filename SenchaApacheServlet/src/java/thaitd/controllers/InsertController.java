package thaitd.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import thaitd.daos.RegistrationDAO;

/**
 *
 * @author ThaiT
 */
public class InsertController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String fullname = request.getParameter("fullname");
            String role = request.getParameter("role");
            RegistrationDAO dao = new RegistrationDAO();
            boolean check = dao.insert(username, password, fullname, role);
            System.out.println(check);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
