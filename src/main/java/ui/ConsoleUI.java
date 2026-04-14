package ui;
import com.sun.jdi.request.WatchpointRequest;
import models.*;
import org.w3c.dom.ls.LSOutput;
import services.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConsoleUI {
    private InputReader reader;
    private Menu menu;
    private CustomerService customerService;
    private ProductService productService;
    private StockService stockService;
    private SupplierService supplierService;
    private WarehouseService warehouseService;
    private SaleService saleService;
    private Write write;

    public ConsoleUI(CustomerService customerService, ProductService productService, StockService stockService, SupplierService supplierService, WarehouseService warehouseService, SaleService saleService) {
        this.customerService = customerService;
        this.productService = productService;
        this.stockService = stockService;
        this.supplierService = supplierService;
        this.warehouseService = warehouseService;
        this.saleService = saleService;
        this.reader = new InputReader();
        this.menu = new Menu();
        this.write = new Write();
    }

    public void start(){
        while (true){
            menu.printMaimMenu();
            int choice = reader.readInt("Выберите пункт: ");
            switch (choice){
                case 1 -> productMenu();
                case 2 -> customerMenu();
                case 3 -> supplierMenu();
                case 4 -> warehouseMenu();
                case 5 -> stockMenu();
                case 6 -> reportMenu();
                case 0 -> {System.out.println("До свидания"); return;}
                default -> System.out.println("Неверный ввод");
            }
        }
    }


    private void productMenu(){
        while(true){
            menu.printEntityMenu("Товар");
            int choice = reader.readInt("Выберите пункт");
            switch (choice){
                case 1 -> addProduct();
                case 2 -> showAllProduct();
                case 3 -> findProductById();
                case 0 -> {return;}
                default -> System.out.println("Неверный код");
            }
        }
    }
    private void addProduct(){
        System.out.println("Создание товара:");
        String name = reader.readString("Наименование: ");
        String catelory = reader.readString("Категория товара: ");
        BigDecimal price = reader.readPrice("Цена товара: ");
        String manufacturer = reader.readString("Производитель:");
        try {
            Product product = productService.createProduct(name, catelory, price, manufacturer);
            System.out.println("Товар добавлен с номером: " + product.getId());
        } catch(IllegalArgumentException e){
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    private void showAllProduct(){
        List<Product> products = productService.findAll();
        write.writeTable(
                "Все товары", products, List.of("Номер", "Название", "Категория", "Цена", "Производитель"),
                p -> p.getId() + " | " + p.getName() + " | " + p.getCategory() + " | " + p.getPrice() + " | " + p.getManufacturer());
    }
    private void findProductById() {
        int id = reader.readInt("Введите номер товара");
        try{
            Product product = productService.findById(id).orElseThrow(() -> new IllegalArgumentException("Товар не найден"));
            List<Product> productList = List.of(product);
            write.writeTable("Результаты поиска", productList, List.of("Номер", "Название", "Категория", "Цена", "Производитель"),
                    p -> p.getId() + " | " + p.getName() + " | " + p.getCategory() + " | " + p.getPrice() + " | " + p.getManufacturer()        );
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка" + e.getMessage());
        }

    }
    private void customerMenu(){
        while(true){
            menu.printEntityMenu("Клиент");
            int choice = reader.readInt("Выберите пункт");
            switch (choice){
                case 1 -> addCustomer();
                case 2 -> showAllCustomers();
                case 3 -> findCustomerById();
                case 0 -> {return;}
                default -> System.out.println("Неверный код");
            }
        }
    }
    private void addCustomer(){
        System.out.println("Создание покупателя:");
        String name = reader.readString("ФИО/название: ");
        String contactInfo = reader.readString("Контактная информация:");
        try {
            Customer customer = customerService.createCustomer(name, contactInfo);
            System.out.println("Покупатель добавлен с номером: " + customer.getId());
        } catch(IllegalArgumentException e){
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    private void showAllCustomers(){
        List<Customer> customers = customerService.findAll();
        write.writeTable(
                "Все клиенты", customers, List.of("Номер", "Фио/Название", "Контактная информация"),
                p -> p.getId() + " | " + p.getName() + " | " + p.getContactInfo());
    }
    private void findCustomerById(){
        int id = reader.readInt("Введите id");
        try {
            Customer customer = customerService.findById(id).orElseThrow(() -> new IllegalArgumentException("Покупатель не найден"));
            List<Customer> customerList = List.of(customer);
            write.writeTable(
                    "Результаты поиска", customerList, List.of("Номер", "Фио/Название", "Контактная информация"),
                    c -> c.getId() + " | " + c.getName() + " | " + c.getContactInfo());
        } catch (IllegalArgumentException e){
            System.out.println("Ошибка " + e.getMessage());
        }
    }

    private void supplierMenu(){
        while(true){
            menu.printEntityMenu("Поставщик");
            int choice = reader.readInt("Выберите пункт");
            switch (choice){
                case 1 -> addSupplier();
                case 2 -> showAllSuppliers();
                case 3 -> findSupplierById();
                case 0 -> {return;}
                default -> System.out.println("Неверный код");
            }
        }
    }
    private void addSupplier(){
        System.out.println("Создание поставщика");
        String name = reader.readString("Название/ФИО: ");
        String contactInfo = reader.readString("Контактная информация: ");
        try{
            Supplier supplier = supplierService.createSupplier(name,contactInfo);
            System.out.println("Поставщик создан с номером " + supplier.getId());
        } catch (IllegalArgumentException e){
            System.out.println("Ошибка " + e.getMessage());
        }
    }
    private void showAllSuppliers(){
        List<Supplier> suppliers = supplierService.findAll();
        write.writeTable(
                "Все поставщики", suppliers, List.of("Номер", "Фио/Название", "Контактная информация"),
                s -> s.getId() + " | " + s.getName() + " | " + s.getContactInfo());
    }
    private void findSupplierById(){
        int id = reader.readInt("Введите номер поставщика ");
        try {
            Supplier supplier = supplierService.findById(id).orElseThrow(()->new IllegalArgumentException("Поставщик не найден"));
            List<Supplier> supplierList = List.of(supplier);
            write.writeTable(
                    "Результаты поиска", supplierList, List.of("Номер", "Фио/Название", "Контактная информация"),
                    s -> s.getId() + " | " + s.getName() + " | " + s.getContactInfo());
        }catch (IllegalArgumentException e){
            System.out.println("Ошибка " + e.getMessage());
        }
    }
    private void warehouseMenu(){
        while (true){
            menu.printEntityMenu("Склад");
            int choice = reader.readInt("Выберите число");
            switch (choice){
                case 1 -> addWarehouse();
                case 2 -> showAllWarehouses();
                case 3 -> findWarehouseById();
                case 0 -> {return;}
                default -> System.out.println("Неверный код");
            }
        }
    }
    private void addWarehouse(){
        System.out.println("Создание склада");
        String name = reader.readString("Название");
        String address = reader.readString("Адрес");
        try{
            Warehouse warehouse = warehouseService.createWarehouse(name, address);
            System.out.println("Склад создан с номером " + warehouse.getId());
        } catch (IllegalArgumentException e ){
            System.out.println(e.getMessage());
        }
    }
    private void showAllWarehouses(){
        List<Warehouse> warehouses = warehouseService.findAll();
        write.writeTable(
                "Все склады", warehouses, List.of("Номер", "Название", "Адрес"),
                w -> w.getId() + " | " + w.getName() + " | " + w.getAddress());
    }
    private void findWarehouseById(){
        int id = reader.readInt("Введите номер склада");
        try {
            Warehouse warehouse = warehouseService.findById(id).orElseThrow(()-> new IllegalArgumentException("Склад не найден"));
            List<Warehouse> warehouseList = List.of(warehouse);
            write.writeTable(
                    "Результаты поиска", warehouseList, List.of("Номер", "Название", "Адрес"),
                    w -> w.getId() + " | " + w.getName() + " | " + w.getAddress());
        } catch (IllegalArgumentException e){
            System.out.println("Ошибка " + e.getMessage());
        }
    }
    private void stockMenu(){
        while (true){
            menu.printStockMenu();
            int choice = reader.readInt("Введите число");
            switch (choice){
                case 1 -> getStatus();
                case 2 -> sellStockItem();
                case 3 -> acceptStockItem();
                case 4 -> showAllStockItems();
                case 5 -> showProductBatches();
                case 0 -> {return;}
                default -> System.out.println("Неверное число");
            }
        }

    }
    private void getStatus(){
        System.out.println("Количество на складе");
        int id = reader.readInt("Введите номер товара: ");
        try {
            String status = stockService.getStockStatus(id);
            System.out.println(status);
        } catch (IllegalArgumentException e){
            System.out.println("Ошибка " + e.getMessage());
        }

    }
    private void sellStockItem(){
        System.out.println("Продажа товара");
        String name = reader.readString("Введите название товара: ");
        List<Product> products = productService.findByName(name);
        if (products.isEmpty()){
            System.out.println("Товаров с таким названием нет");
            return;
        }
        write.writeTable(
                "Все товары", products, List.of("Номер", "Название", "Категория", "Цена", "Производитель"),
                p -> p.getId() + " | " + p.getName() + " | " + p.getCategory() + " | " + p.getPrice() + " | " + p.getManufacturer());
        int id = reader.readInt("Введите номер выбранного товара");
        showAllCustomers();
        int customerId = reader.readInt("Введите id покупателя: ");
        showProductBatches();
        int warehouseId = reader.readInt("Введите id склада: ");
        int count = reader.readInt("Введите количество выбранного товара");
        try{
            Sale sale = saleService.sell(id, customerId, warehouseId, count);
            System.out.println("Продажа оформлена");
            System.out.println("Номер чека " + sale.getId());
            System.out.println("Сумма покупки " + sale.getTotal());
        } catch (IllegalArgumentException e){
            System.out.println("Ошибка " + e.getMessage());
        }
    }
    private void acceptStockItem(){
        System.out.println("Прием товара");
        int id = reader.readInt("Введите номер товара");
        try{
            Product product = productService.findById(id).orElseThrow(() -> new IllegalArgumentException("Товар не найден"));
        }catch (IllegalArgumentException e){
            System.out.println("Ошибка:" + e.getMessage() + ". Создайте товар сначала.");
            addProduct();
            return;
        }
        int supplierId = reader.readInt("Введите номер поставщика");
        int warehouseId = reader.readInt("Введите номер склада");
        int count = reader.readInt("Введите количество");
        try {
            stockService.acceptGoods(id, supplierId, warehouseId, count);
            System.out.println("Товар принят");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка " + e.getMessage());
        }
    }
    private void showAllStockItems(){
        List<StockItem> stockItems = stockService.getAllStock();
        write.writeTable(
                "Все хранимое на складе",
                stockItems,
                List.of("Номер", "Количество", "Товар", "Поставщик", "Склад"),
                item -> {
                    String productName = productService.findById(item.getProductId()).map(Product::getName).orElse("id=" + item.getProductId());
                    String supplierName = supplierService.findById(item.getSupplierId()).map(Supplier :: getName).orElse("id=" + item.getSupplierId());
                    String warehouseName = warehouseService.findById(item.getWarehouseId()).map(Warehouse :: getName).orElse("id=" + item.getWarehouseId());
                    return item.getId() + " | " + item.getCount() + " | " + productName + " | " + supplierName + " | " + warehouseName;
                }
        );
    }
    private void showProductBatches(){
        int id = reader.readInt("Введите номер товара: ");
        List<StockItem> batches = stockService.getAvailable(id);
        write.writeTable(
                "Партии товара",
                batches,
                List.of("ID партии", "Склад", "Поставщик", "Количество"),
                item -> {
                    String warehouseName = warehouseService.findById(item.getWarehouseId()).map(Warehouse :: getName).orElse("id=" + item.getWarehouseId());
                    String supplierName = supplierService.findById(item.getSupplierId()).map(Supplier :: getName).orElse("id=" + item.getSupplierId());
                    return item.getId() + " | " + warehouseName + " | " + supplierName + " | " + item.getCount();
                }
        );
    }
    private void reportMenu(){
        while (true){
            menu.printReportMenu();
            int choice = reader.readInt("Введите число");
            switch (choice){
                case 1 -> showPobuctByCategories();
                case 2 -> stocksOnWarehouse();
                case 3 -> historyOfSales();
                case 0 -> {return;}
                default -> System.out.println("Неверное число");
            }
        }
    }
    private void showPobuctByCategories(){
        System.out.println("Товары по категориям");
        Map<String, List<Product>> grouped = productService.getProductGroupedByCategory();
        for(Map.Entry<String, List<Product>> entry : grouped.entrySet()){
            System.out.println("Категория: " + entry.getKey());
            for (Product product : entry.getValue()){
                System.out.println("Товар: " + product.getName()+ product.getPrice() + "руб");
            }
        }
    }
    private void  stocksOnWarehouse(){
        System.out.println("Остатки по складам");
        List<StockItem> stockItems = stockService.getAllStock();
        if (stockItems.isEmpty()){
            System.out.println("Склады пустые");
            return;
        }
        Map<Integer, List<StockItem>> byWarehouse = stockItems.stream().collect(Collectors.groupingBy(item -> item.getWarehouseId()));
        for (Map.Entry<Integer, List<StockItem>> entry : byWarehouse.entrySet()){
            String warehouseName = warehouseService.findById(entry.getKey()).map(Warehouse :: getName).orElse("Склад id=" + entry.getKey());
            System.out.println("Склад id=" + warehouseName);

            for(StockItem item : entry.getValue()){
                String productName = productService.findById(item.getProductId()).map(Product :: getName).orElse("Товар id=" + entry.getKey());
                String supplierName = supplierService.findById(item.getSupplierId()).map(Supplier :: getName).orElse("Покупатель id=" + entry.getKey());
                System.out.printf(" - %s %d шт. от %s%n", productName, item.getCount(), supplierName);
            }
        }
    }
    private void historyOfSales(){
        System.out.println("История продаж");
        List<Sale> sales = saleService.getAll();
        if (sales.isEmpty()){
            System.out.println("Продаж еще не было");
            return;
        }
        System.out.printf("%-4s | %-7s | %-15s | %-20s%n",
                "Id продажи", "Дата", "Покупатель", "Состав");
        System.out.println("-----|-----------------|-----------------|----------");
        for (Sale sale : sales){
            String customerName = customerService.findById(sale.getCustomerId()).map(Customer :: getName).orElse("id=" + sale.getCustomerId());
            String items = sale.getSaleItems().stream()
                    .map(item -> { String productName = productService.findById(item.getProductId()).map(Product::getName).orElse("id=" + item.getProductId());
                        return productName + " " + item.getCount() + " шт.";
                    })
                    .collect(Collectors.joining(", "));

            System.out.printf("%-4d | %-10s | %-15s | %-30s%n",
                    sale.getId(),
                    sale.getDate().toLocalDate(),
                    customerName,
                    items
            );
        }
    }
}
class Menu{
    void printMaimMenu(){
        System.out.println("Система учета склада:");
        System.out.println("1. Управление товарами");
        System.out.println("2. Управление покупателями");
        System.out.println("3. Управление поставщиками");
        System.out.println("4. Складские операции");
        System.out.println("5. Отчёты");
        System.out.println("0. Выход");
    }
    void printEntityMenu(String entityName) {
        System.out.println(entityName);
        System.out.println("1. Добавить " + entityName.toLowerCase());
        System.out.println("2. Показать все " + entityName.toLowerCase());
        System.out.println("3. Найти " + entityName.toLowerCase() +" по Id");
        System.out.println("0. Назад");
    }
    void printStockMenu() {
        System.out.println("Остатки на складах");
        System.out.println("1. Проверить наличие товара");
        System.out.println("2. Продать товар");
        System.out.println("3. Принять товар");
        System.out.println("4. Показать все остатки");
        System.out.println("5. Показать партии товара");
        System.out.println("0. Назад");
    }
    void printReportMenu(){
        System.out.println("1. Товары по категориям");
        System.out.println("2. Остатки на складах");
        System.out.println("3. История продаж");
        System.out.println("0. Назад");
    }
}
class InputReader{
    private Scanner scanner = new Scanner(System.in);
    int readInt(String promt){
        System.out.println(promt);
        while(!scanner.hasNextInt()){
            System.out.println("Введите число");
            scanner.next();
            System.out.println(promt);
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }
    String readString (String promt){
        System.out.println(promt);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Поле не может быть пустым");
        }
        return input;
    }
    BigDecimal readPrice(String promt){
        System.out.println(promt);
        while(!scanner.hasNextBigDecimal()){
            System.out.println("Введите число");
            scanner.next();
            System.out.println(promt);
        }
        BigDecimal value = scanner.nextBigDecimal();
        scanner.nextLine();
        return value;
    }
}
class Write{
    <T> void writeTable(String tableName, List<T> objects , List<String> colomnName, Function<T, String> make){
        if (objects.isEmpty()){
            System.out.println("Нет данных");
            return;
        }
        System.out.println(tableName);
        for(int i = 0; i<colomnName.size(); i++){
            System.out.print(colomnName.get(i) + " | ");
        }
        System.out.println();
        for(int i = 0; i<colomnName.size(); i++) {
            for (int j = 0; j < colomnName.get(i).length(); j++) {
                System.out.print("-");
            }
            System.out.print("|");
        }
        System.out.println();
        for (T s : objects){
            String word = make.apply(s);
            System.out.print(word + "|" + "\n");
        }
    }
}
