package servlets.update;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Customer;
import services.CustomerService;
import servlets.AppConfig;

import java.io.IOException;

@WebServlet("/update-customer")
public class UpdateCustomerServlet extends HttpServlet {
    private CustomerService customerService;
    @Override
    public void init(){
        customerService = AppConfig.getCustomerService();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            String contactInfo = req.getParameter("contactInfo");
            Customer customer = new Customer(id, name, contactInfo);
            customerService.update(customer);
            res.sendRedirect(req.getContextPath() + "/customers");

        } catch (NumberFormatException e) {
            req.setAttribute("error", "Неверный формат числа в поле: " + e.getMessage());
            forwardBackToForm(req, res);

        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            forwardBackToForm(req, res);

        } catch (Exception e) {
            req.setAttribute("error", "Ошибка при обновлении: " + e.getMessage());
            forwardBackToForm(req, res);
        }
    }
    private void forwardBackToForm(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setAttribute("name", req.getParameter("name"));
        req.setAttribute("contactInfo", req.getParameter("contactInfo"));
        req.setAttribute("id", req.getParameter("id"));

        req.getRequestDispatcher("/WEB-INF/views/edit/edit-customer.jsp").forward(req, res);
    }
}
