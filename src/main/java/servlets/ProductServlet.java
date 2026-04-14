package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.ProductService;

import java.io.IOException;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private ProductService productService;
    @Override
    public void init(){
        productService = AppConfig.getProductService();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        try{
            req.setAttribute("products", productService.findAll());
            req.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(req, res);
        } catch (IllegalArgumentException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            req.setAttribute("errorMessage", "Ошибка: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(req, res);
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.setAttribute("errorMessage", "Ошибка: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(req, res);
        }
    }

}
