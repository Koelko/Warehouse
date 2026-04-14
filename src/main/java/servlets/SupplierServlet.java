package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.SupplierService;

import java.io.IOException;


@WebServlet("/suppliers")
public class SupplierServlet extends HttpServlet {
    private SupplierService supplierService;

    @Override
    public void init() {
        supplierService = AppConfig.getSupplierService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        try {
            req.setAttribute("suppliers", supplierService.findAll());
            req.getRequestDispatcher("/WEB-INF/views/suppliers.jsp").forward(req, res);
        } catch (IllegalArgumentException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            req.setAttribute("errorMessage", "Ошибка: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/suppliers.jsp").forward(req, res);
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.setAttribute("errorMessage", "Ошибка: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/suppliers.jsp").forward(req, res);
        }
    }

}
