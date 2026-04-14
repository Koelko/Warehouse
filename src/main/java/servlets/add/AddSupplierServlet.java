package servlets.add;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.SupplierService;
import servlets.AppConfig;

import java.io.IOException;

@WebServlet("/add-supplier")
public class AddSupplierServlet extends HttpServlet {
    private SupplierService supplierService;
    @Override
    public void init(){
        supplierService = AppConfig.getSupplierService();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
        req.getRequestDispatcher("/WEB-INF/views/add/add-supplier.jsp").forward(req, res);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String contactInfo = req.getParameter("contactInfo");
        try{
            supplierService.createSupplier(name, contactInfo);
            res.sendRedirect(req.getContextPath() + "/suppliers");
        }catch (IllegalArgumentException e){
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("backLink", "add-supplier.jsp");
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, res);
        }
    }
}
