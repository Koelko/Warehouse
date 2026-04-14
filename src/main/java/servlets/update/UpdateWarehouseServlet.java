package servlets.update;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Warehouse;
import services.WarehouseService;
import servlets.AppConfig;

import java.io.IOException;
@WebServlet("/update-warehouse")
public class UpdateWarehouseServlet extends HttpServlet {
    private WarehouseService warehouseService;
    @Override
    public void init(){
        warehouseService = AppConfig.getWarehouseService();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            String address = req.getParameter("address");
            Warehouse warehouse = new Warehouse(id, name, address);
            warehouseService.update(warehouse);
            res.sendRedirect(req.getContextPath() + "/warehouses");

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
        req.setAttribute("address", req.getParameter("address"));
        req.setAttribute("id", req.getParameter("id"));

        req.getRequestDispatcher("/WEB-INF/views/edit/edit-warehouse.jsp").forward(req, res);
    }
}
