package servlets.edit;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Customer;
import services.CustomerService;
import servlets.AppConfig;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/edit-customer")
public class EditCustomerServlet extends HttpServlet {
    private CustomerService customerService;
    @Override
    public void init(){
        customerService = AppConfig.getCustomerService();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Optional<Customer> customer = customerService.findById(id);

            if (customer.isPresent()) {
                req.setAttribute("customer", customer.get());
            } else {
                req.setAttribute("error", "Покупатель не найден");
            }

            req.getRequestDispatcher("/WEB-INF/views/edit/edit-customer.jsp").forward(req, res);

        } catch (Exception e) {
            req.setAttribute("error", "Ошибка: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/edit/edit-customer.jsp").forward(req, res);
        }
    }
}
