package servlets.add;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.WarehouseService;
import servlets.AppConfig;

import java.io.IOException;

@WebServlet("/add-warehouse")
public class AddWarehouseServlet extends HttpServlet {
    private WarehouseService warehouseService;

    @Override
    public void init() {
        warehouseService = AppConfig.getWarehouseService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.getRequestDispatcher("/WEB-INF/views/add/add-warehouse.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        try {
            req.setCharacterEncoding("UTF-8");
            String name = req.getParameter("name");
            String address = req.getParameter("address");
            warehouseService.createWarehouse(name, address);
            res.sendRedirect(req.getContextPath() + "/warehouses");
        } catch (IllegalArgumentException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("backLink", "add-warehouse.jsp");
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, res);
        }
    }
}
