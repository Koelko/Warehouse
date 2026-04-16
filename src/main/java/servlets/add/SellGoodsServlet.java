package servlets.add;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.CustomerService;
import services.ProductService;
import services.SaleService;
import services.WarehouseService;
import servlets.AppConfig;

import java.io.IOException;

@WebServlet("/sell-goods")
public class SellGoodsServlet extends HttpServlet {
    private SaleService saleService;
    private ProductService productService;
    private CustomerService customerService;
    private WarehouseService warehouseService;
    @Override
    public void init(){
        saleService = AppConfig.getSaleService();
        productService = AppConfig.getProductService();
        customerService = AppConfig.getCustomerService();
        warehouseService = AppConfig.getWarehouseService();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
        req.setAttribute("products", productService.findAll());
        req.setAttribute("customers", customerService.findAll());
        req.setAttribute("warehouses", warehouseService.findAll());
        req.getRequestDispatcher("/WEB-INF/views/add/sell-goods.jsp").forward(req, res);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        int productId = Integer.parseInt(req.getParameter("productId"));
        int count = Integer.parseInt(req.getParameter("count"));
        int customerId = Integer.parseInt(req.getParameter("customerId"));
        int warehouseId = Integer.parseInt(req.getParameter("warehouseId"));
        try{
            saleService.sell(productId,customerId, warehouseId, count);
            res.sendRedirect(req.getContextPath() + "/sales");
        } catch (IllegalArgumentException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            req.setAttribute("errorMessage", "Ошибка: " + e.getMessage());
            req.setAttribute("backLink", req.getContextPath() + "/sell-goods");
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, res);
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.setAttribute("errorMessage", "Ошибка: " + e.getMessage());
            req.setAttribute("backLink", req.getContextPath() + "/sell-goods");
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, res);
        }

    }
}
