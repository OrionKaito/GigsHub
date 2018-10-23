/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thaitd.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import thaitd.daos.RegistrationDAO;
import thaitd.dtos.RegistrationDTO;

/**
 *
 * @author ThaiT
 */
public class SearchAllController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
    
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            JSONArray arrayObj = new JSONArray();
            RegistrationDAO dao = new RegistrationDAO();
            List<RegistrationDTO> result = dao.getAllAccount();
            for (int i = 0; i < result.size(); i++) {
                RegistrationDTO account = result.get(i);
                JSONObject itemObj = JSONObject.fromObject(account);
                arrayObj.add(itemObj);
            }
            out.println(arrayObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
