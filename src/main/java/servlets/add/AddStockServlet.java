package servlets.add;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.ProductService;
import services.StockService;
import services.SupplierService;
import services.WarehouseService;
import servlets.AppConfig;

import java.io.IOException;

@WebServlet("/add-stock")
public class AddStockServlet extends HttpServlet {
    private StockService stockService;
    private ProductService productService;
    private SupplierService supplierService;
    private WarehouseService warehouseService;

    @Override
    public void init() {
        stockService = AppConfig.getStockService();
        productService = AppConfig.getProductService();
        supplierService = AppConfig.getSupplierService();
        warehouseService = AppConfig.getWarehouseService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setAttribute("products", productService.findAll());
        req.setAttribute("warehouses", warehouseService.findAll());
        req.setAttribute("suppliers", supplierService.findAll());
        req.getRequestDispatcher("/WEB-INF/views/add/add-stock.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        int productId = Integer.parseInt(req.getParameter("productId"));
        int supplierId = Integer.parseInt(req.getParameter("supplierId"));
        int warehouseId = Integer.parseInt(req.getParameter("warehouseId"));
        int count = Integer.parseInt(req.getParameter("count"));
        try {
            stockService.acceptGoods(productId, supplierId, warehouseId, count);
            res.sendRedirect(req.getContextPath() + "/stock");
        } catch (IllegalArgumentException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("backLink", req.getContextPath() + "/add-stock");
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, res);
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.setAttribute("errorMessage", "Ошибка: " + e.getMessage());
            req.setAttribute("backLink", req.getContextPath() + "/add-stock");
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, res);
        }

    }
}
