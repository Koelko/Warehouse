package servlets.update;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Supplier;
import services.SupplierService;
import servlets.AppConfig;

import java.io.IOException;

@WebServlet("/update-supplier")
public class UpdateSupplierServlet extends HttpServlet {
    private SupplierService supplierService;

    @Override
    public void init() {
        supplierService = AppConfig.getSupplierService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        try {
            req.setCharacterEncoding("UTF-8");
            int id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            String contactInfo = req.getParameter("contactInfo");
            Supplier supplier = new Supplier(id, name, contactInfo);

            supplierService.update(supplier);
            res.sendRedirect(req.getContextPath() + "/suppliers");
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

        req.getRequestDispatcher("/WEB-INF/views/edit/edit-supplier.jsp").forward(req, res);
    }
}
