package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Customer;
import models.Product;
import models.Sale;
import models.SaleItem;
import services.CustomerService;
import services.ProductService;
import services.SaleService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/sales")
public class SaleServlet extends HttpServlet {
    private SaleService saleService;
    private CustomerService customerService;
    private ProductService productService;
    @Override
    public void init(){
        saleService = AppConfig.getSaleService();
        customerService = AppConfig.getCustomerService();
        productService = AppConfig.getProductService();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        try{
            List<Sale> sales = saleService.getAll();
            Map<Integer, Customer> customerMap = new HashMap<>();
            Map<Integer, Product> productMap = new HashMap<>();
            for (Sale sale : sales) {
                customerMap.putIfAbsent(sale.getCustomerId(), customerService.findById(sale.getCustomerId()).orElse(null));
                for(SaleItem item : sale.getSaleItems()) {
                    productMap.putIfAbsent(item.getProductId(), productService.findById(item.getProductId()).orElse(null));
                }
            }
            req.setAttribute("sales", sales);
            req.setAttribute("customerMap", customerMap);
            req.setAttribute("productMap", productMap);
            req.getRequestDispatcher("/WEB-INF/views/sales.jsp").forward(req, res);
        } catch (IllegalArgumentException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            req.setAttribute("errorMessage", "Ошибка: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/sales.jsp").forward(req, res);
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.setAttribute("errorMessage", "Ошибка: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/sales.jsp").forward(req, res);
        }
    }

}
