package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.CustomerService;

import java.io.IOException;


@WebServlet("/customers")
public class CustomerServlet extends HttpServlet {
    private CustomerService customerService;
    @Override
    public void init(){
        customerService = AppConfig.getCustomerService();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        try {
            req.setAttribute("customers", customerService.findAll());
            req.getRequestDispatcher("/WEB-INF/views/customers.jsp").forward(req, res);
        } catch (IllegalArgumentException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            req.setAttribute("errorMessage", "Ошибка: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/customers.jsp").forward(req, res);
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.setAttribute("errorMessage", "Ошибка: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/customers.jsp").forward(req, res);
        }
    }
}
