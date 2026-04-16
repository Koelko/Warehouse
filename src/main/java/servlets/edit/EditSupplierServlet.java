package servlets.edit;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.SupplierService;
import models.Supplier;
import servlets.AppConfig;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/edit-supplier")
public class EditSupplierServlet extends HttpServlet {
    private SupplierService supplierService;
    @Override
    public void init(){
        supplierService = AppConfig.getSupplierService();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        try{
            int id = Integer.parseInt(req.getParameter("id"));
            Optional<Supplier> supplier  = supplierService.findById(id);

            if (supplier.isPresent()) {
                req.setAttribute("supplier", supplier.get());
            } else {
                req.setAttribute("error", "Поставщик не найден");
            }

            req.getRequestDispatcher("/WEB-INF/views/edit/edit-supplier.jsp").forward(req, res);

        } catch (Exception e) {
            req.setAttribute("error", "Ошибка: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/edit/edit-supplier.jsp").forward(req, res);
        }
    }
}
