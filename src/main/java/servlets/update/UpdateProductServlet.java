package servlets.update;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Product;
import services.ProductService;
import servlets.AppConfig;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/update-product")
public class UpdateProductServlet extends HttpServlet {
    private ProductService productService;
    @Override
    public void init(){
        productService = AppConfig.getProductService();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            String category = req.getParameter("category");
            BigDecimal price = new BigDecimal(req.getParameter("price"));
            String manufacturer = req.getParameter("manufacturer");
            Product product = new Product(id, name, category, price, manufacturer);
            productService.update(product);
            res.sendRedirect(req.getContextPath() + "/products");

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
        req.setAttribute("category", req.getParameter("category"));
        req.setAttribute("price", req.getParameter("price"));
        req.setAttribute("manufacturer", req.getParameter("manufacturer"));
        req.setAttribute("id", req.getParameter("id"));

        req.getRequestDispatcher("/WEB-INF/views/edit/edit-product.jsp").forward(req, res);
    }
}
