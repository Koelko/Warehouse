package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Product;
import models.StockItem;
import models.Supplier;
import models.Warehouse;
import services.ProductService;
import services.StockService;
import services.SupplierService;
import services.WarehouseService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/stock")
public class StockServlet extends HttpServlet {
    private StockService stockService;
    private ProductService productService;
    private WarehouseService warehouseService;
    private SupplierService supplierService;
    @Override
    public void init(){
        stockService = AppConfig.getStockService();
        productService = AppConfig.getProductService();
        warehouseService = AppConfig.getWarehouseService();
        supplierService = AppConfig.getSupplierService();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        try{
            List<StockItem> stockItems = stockService.getAllStock();
            Map<Integer, Product> productMap = new HashMap<>();
            Map<Integer, Warehouse> warehouseMap = new HashMap<>();
            Map<Integer, Supplier> supplierMap = new HashMap<>();
            for(StockItem item : stockItems){
                productMap.putIfAbsent(item.getProductId(), productService.findById(item.getProductId()).orElse(null));
                warehouseMap.putIfAbsent(item.getWarehouseId(), warehouseService.findById(item.getWarehouseId()).orElse(null));
                supplierMap.putIfAbsent(item.getSupplierId(),supplierService.findById(item.getSupplierId()).orElse(null));
            }
            req.setAttribute("stock", stockItems);
            req.setAttribute("productMap", productMap);
            req.setAttribute("warehouseMap", warehouseMap);
            req.setAttribute("supplierMap", supplierMap);
            req.getRequestDispatcher("/WEB-INF/views/stock.jsp").forward(req, res);
        } catch (IllegalArgumentException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            req.setAttribute("errorMessage", "Ошибка: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/stock.jsp").forward(req, res);
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.setAttribute("errorMessage", "Ошибка: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/stock.jsp").forward(req, res);
        }
    }
}
