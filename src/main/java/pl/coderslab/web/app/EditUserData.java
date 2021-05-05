package pl.coderslab.web.app;

import pl.coderslab.dao.AdminDAO;
import pl.coderslab.model.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/app/user/edit")
public class EditUserData extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("loggedIn");

        getServletContext().getRequestDispatcher("/app/userEdit.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        AdminDAO ad = new AdminDAO();
        int userID =Integer.parseInt(request.getParameter("id"));
        Admin admin = new Admin(userID, request.getParameter("name"),
                request.getParameter("surname"), request.getParameter("email"));
        ad.update(admin);
        session.setAttribute("loggedIn",ad.read(userID));
        response.sendRedirect("/app");
    }
}
