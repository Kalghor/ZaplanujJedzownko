package pl.coderslab.web;

import pl.coderslab.dao.AdminDAO;
import pl.coderslab.model.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/register")
public class Register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Admin admin = new Admin(request.getParameter("name"),request.getParameter("surname"),
                request.getParameter("email"),request.getParameter("password"));
        AdminDAO ad = new AdminDAO();
        if(ad.uniqueEmail(request.getParameter("email"))) {
            ad.create(admin);
            response.sendRedirect("/login");
        }else {
            request.setAttribute("regError","Email jest już zajęty");
            getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}
