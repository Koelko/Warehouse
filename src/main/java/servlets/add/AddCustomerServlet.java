package servlets.add;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.CustomerService;
import servlets.AppConfig;

import java.io.IOException;

@WebServlet("/add-customer")
public class AddCustomerServlet extends HttpServlet {
    private CustomerService customerService;
    @Override
    public void init(){
        customerService = AppConfig.getCustomerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
        req.getRequestDispatcher("/WEB-INF/views/add/add-customer.jsp").forward(req, res);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        try {
            String name = req.getParameter("name");
            String contactInfo = req.getParameter("contactInfo");
            customerService.createCustomer(name, contactInfo);
            res.sendRedirect(req.getContextPath() + "/customers");

        } catch (Exception e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("backLink", req.getContextPath() + "/add-customer");
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, res);
        }
    }
}
