package examples.validation;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlets.AppConfig;

import java.io.IOException;

@WebServlet("/demo/chain-validation")
public class ChainDemoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html;charset=UTF-8");
        try{
            int testProductId = 1;
            if (req.getParameter("productId") != null) {
                try {
                    testProductId = Integer.parseInt(req.getParameter("productId"));
                } catch (NumberFormatException e) {
                }
            }
            Context context = new Context();
            context.setProductId(testProductId);
            context.setCustomerId(1);
            context.setSupplierId(1);
            context.setWarehouseId(1);
            Validator validator = new ProductValidator(AppConfig.getProductRepository());
            validator.linkWith(new CustomerValidator(AppConfig.getCustomerRepository()))
                    .linkWith(new SupplierValidator(AppConfig.getSupplierRepository())).linkWith(new WarehouseValidator(AppConfig.getWarehouseRepository()));
            validator.validate(context);
            var out = res.getWriter();
            out.println("<html><body>");
            out.println("<h1>Валидация прошла успешно!</h1>");
            out.println("<p>Товар: " +
                    (context.getProduct() != null ? context.getProduct().getName() : "не установлен") +
                    "</p>");
            out.println("<p>Покупатель: " +
                    (context.getCustomer() != null ? context.getCustomer().getName() : "не установлен") +
                    "</p>");
            out.println("<p>Поставщик: " +
                    (context.getSupplier() != null ? context.getSupplier().getName() : "не установлен") +
                    "</p>");
            out.println("<p>Склад: " +
                    (context.getWarehouse() != null ? context.getWarehouse().getName() : "не установлен") +
                    "</p>");
            out.println("<br><a href='" + req.getContextPath() + "/demo/chain-validation?productId=999'>Протестировать ошибку</a>");
            out.println("</body></html>");
        }catch (Exception e) {
            var out = res.getWriter();
            out.println("<html><body>");
            out.println("<h1>Ошибка валидации:</h1>");
            out.println("<p style='color:red'>" + e.getMessage() + "</p>");
            out.println("<br><a href='" + req.getContextPath() + "/demo/chain-validation'>Назад</a>");
            out.println("</body></html>");
        }
    }
}
