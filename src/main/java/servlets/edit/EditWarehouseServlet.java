package servlets.edit;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Warehouse;
import services.WarehouseService;
import servlets.AppConfig;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/edit-warehouse")
public class EditWarehouseServlet extends HttpServlet {
    private WarehouseService warehouseService;
    @Override
    public void init(){
        warehouseService = AppConfig.getWarehouseService();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        try{
            int id = Integer.parseInt(req.getParameter("id"));
           Optional<Warehouse> warehouse  = warehouseService.findById(id);

            if (warehouse.isPresent()) {
                req.setAttribute("warehouse", warehouse.get());
            } else {
                req.setAttribute("error", "Склад не найден");
            }

            req.getRequestDispatcher("/WEB-INF/views/edit/edit-warehouse.jsp").forward(req, res);

        } catch (Exception e) {
            req.setAttribute("error", "Ошибка: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/edit/edit-warehouse.jsp").forward(req, res);
        }
    }
}
