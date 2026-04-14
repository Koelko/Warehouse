package servlets.edit;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Product;
import services.ProductService;
import servlets.AppConfig;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/edit-product")
public class EditProductServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() {
        productService = AppConfig.getProductService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        try{
            int id = Integer.parseInt(req.getParameter("id"));
            Optional<Product> product = productService.findById(id);

            if (product.isPresent()) {
                req.setAttribute("product", product.get());
            } else {
                req.setAttribute("error", "Товар не найден");
            }

            req.getRequestDispatcher("/WEB-INF/views/edit/edit-product.jsp").forward(req, res);

        } catch (Exception e) {
            req.setAttribute("error", "Ошибка: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/edit/edit-product.jsp").forward(req, res);
        }
    }
}
