package servlets.add;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.ProductService;
import servlets.AppConfig;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/add-product")
public class AddProductServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() {
        productService = AppConfig.getProductService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws  IOException, ServletException{
        req.getRequestDispatcher("/WEB-INF/views/add/add-product.jsp").forward(req, res);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        try {
            String name = req.getParameter("name");
            String category = req.getParameter("category");
            BigDecimal price = new BigDecimal(req.getParameter("price"));
            String manufacturer = req.getParameter("manufacturer");
            productService.createProduct(name, category, price, manufacturer);
            res.sendRedirect(req.getContextPath() + "/products");
        } catch (IllegalArgumentException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("backLink", req.getContextPath()  + "/add-product");
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, res);
        }
    }
}
