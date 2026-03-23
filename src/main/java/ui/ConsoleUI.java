package ui;
import com.sun.jdi.request.WatchpointRequest;
import models.*;
import services.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
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

    public ConsoleUI(CustomerService customerService, ProductService productService, StockService stockService, SupplierService supplierService, WarehouseService warehouseService, SaleService saleService) {
        this.customerService = customerService;
        this.productService = productService;
        this.stockService = stockService;
        this.supplierService = supplierService;
        this.warehouseService = warehouseService;
        this.saleService = saleService;
        this.reader = new InputReader();
        this.menu = new Menu();
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
            menu.printProductMenu();
            int choice = reader.readInt("Выберите пункт");
            switch (choice){
                case 1 -> addProduct();
                case 2 -> showAllProduct();
                case 3 -> findProductById();
                case 4 -> removeProduct();
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
        System.out.println("Все товары");
        List<Product> products = productService.findAll();
        if (products.isEmpty()) {
            System.out.println("Товаров нет");
            return;
        }
        System.out.printf("%-4s | %-20s | %-15s | %-10s | %-15s%n",
                "Номер", "Название", "Категория", "Цена", "Производитель");
        System.out.println("-----|----------------------|-----------------|------------|-----------------");
        for (Product p : products) {
            System.out.printf("%-4d | %-20s | %-15s | %-10.2f | %-15s%n",
                    p.getId(), p.getName(), p.getCategory(),
                    p.getPrice(), p.getManufacturer());
        }
    }
    private void findProductById(){
        System.out.println("Результаты поиска");
        int id = reader.readInt("Введите номер товара");
        Product product = productService.findById(id);
        if (product == null) {
            System.out.println("Такого товара нет");
            return;
        }
        System.out.println("Номер: " + product.getId());
        System.out.println("Название: " + product.getName());
        System.out.println("Категория: " + product.getCategory());
        System.out.println("Цена: " + product.getPrice());
        System.out.println("Производитель: " + product.getManufacturer());
    }
    private void removeProduct(){
        int id = reader.readInt("Введите номер товара");
        try{
            productService.remove(id);
            System.out.println("Товар удален");
        }catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    private void customerMenu(){
        while(true){
            menu.printCustomerMenu();
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
        System.out.println("Все клиенты");
        List<Customer> customers = customerService.findAll();
        if (customers.isEmpty()) {
            System.out.println("Клиентов нет");
            return;
        }
        System.out.printf("%-4s | %-20s | %-15s%n",
                "Номер", "ФИО", "Контактная информация");
        System.out.println("-----|----------------------|-----------------");
        for (Customer customer : customers) {
            System.out.printf("%-4d | %-20s | %-15s%n",
                    customer.getId(), customer.getName(), customer.getContactInfo());
        }
    }
    private void findCustomerById(){
        System.out.println("Результаты поиска");
        int id = reader.readInt("Введите id");
        Customer customer = customerService.findById(id);
        if (customer == null) {
            System.out.println("Такого клиента нет");
            return;
        }
        System.out.println("Номер: " + customer.getId());
        System.out.println("ФИО: " + customer.getName());
        System.out.println("Контактная информация: " + customer.getContactInfo());
    }

    private void supplierMenu(){
        while(true){
            menu.printSupplierMenu();
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
        System.out.println("Все поставщики");
        List<Supplier> suppliers = supplierService.findAll();
        if (suppliers.isEmpty()) {
            System.out.println("Клиентов нет");
            return;
        }
        System.out.printf("%-4s | %-20s | %-15s%n",
                "Номер", "ФИО", "Контактная информация");
        System.out.println("-----|----------------------|-----------------");
        for (Supplier supplier : suppliers){
            System.out.printf("%-4d | %-20s | %-15s%n",
                    supplier.getId(), supplier.getName(), supplier.getContactInfo());
        }
    }
    private void findSupplierById(){
        System.out.println("Результаты поиска");
        int id = reader.readInt("Введите номер поставщика ");
        Supplier supplier = supplierService.findById(id);
        if (supplier == null) {
            System.out.println("Такого клиента нет");
            return;
        }
        System.out.println("Номер: " + supplier.getId());
        System.out.println("Название/ФИО: " + supplier.getName());
        System.out.println("Контактная информация: " + supplier.getContactInfo());
    }
    private void warehouseMenu(){
        while (true){
            menu.printWarehouseMenu();
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
            System.out.println("Ошибка " + e.getMessage());
        }
    }
    private void showAllWarehouses(){
        System.out.println("Все склады");
        List<Warehouse> warehouses = warehouseService.findAll();
        if (warehouses.isEmpty()){
            System.out.println("Складов нет");
            return;
        }
        System.out.printf("%-4s | %-20s | %-15s%n",
                "Номер", "Название", "Адрес");
        System.out.println("-----|----------------------|-----------------|------------|-----------------");
        for (Warehouse warehouse : warehouses) {
            System.out.printf("%-4d | %-20s | %-15s%n",
                    warehouse.getId(), warehouse.getName(), warehouse.getAddress());
        }
    }
    private void findWarehouseById(){
        System.out.println("Результаты поиска");
        int id = reader.readInt("Введите номер склада");
        Warehouse warehouse = warehouseService.findById(id);
        if (warehouse == null){
            System.out.println("Такого склада нет");
            return;
        }
        System.out.println("Номер: " + warehouse.getId());
        System.out.println("Название: " + warehouse.getName());
        System.out.println("Адрес: " + warehouse.getAddress());
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
            throw new IllegalArgumentException("Товаров с таким названием нет");
        }
        System.out.printf("%-4s | %-20s | %-15s | %-10s | %-15s%n",
                "Номер", "Название", "Категория", "Цена", "Производитель");
        System.out.println("-----|----------------------|-----------------|------------|-----------------");
        for (Product p : products) {
            System.out.printf("%-4d | %-20s | %-15s | %-10.2f | %-15s%n",
                    p.getId(), p.getName(), p.getCategory(),
                    p.getPrice(), p.getManufacturer());
        }
        int id = reader.readInt("Введите номер выбранного товара");
        int customerId = reader.readInt("Введите id покупателя: ");
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
        Product product = productService.findById(id);
        if (product == null) {
            System.out.println("Такого товара нет, создайте его");
            addProduct();
            product = productService.findById(id);
            if (product == null) {
                System.out.println("Ошибка: товар не создан");
                return;
            }
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
        System.out.println("Все хранимое на складе");
        List<StockItem> stockItems = stockService.getAllStock();
        if (stockItems.isEmpty()){
            System.out.println("Складов нет");
            return;
        }
        System.out.printf("%-4s | %-20s | %-15s | %-10s | %-15s%n",
                "Номер", "Количество", "Товар", "Поставщик", "Склад");
        System.out.println("-----|----------------------|-----------------|------------|-----------------");
        for (StockItem stockItem : stockItems) {
            System.out.printf("%-4s | %-20s | %-15s | %-10s | %-15s%n",
                    stockItem.getId(), stockItem.getCount(), stockItem.getProduct(), stockItem.getSupplier(), stockItem.getWarehouse());
        }
    }
    private void showProductBatches(){
        System.out.println("Партии товара");
        int id = reader.readInt("Введите номер товара: ");
        List<StockItem> batches = stockService.getAvailable(id);
        if (batches.isEmpty()){
            System.out.println("Такой партии нет");
        }
        System.out.printf("%-4s | %-15s | %-15s | %-10s%n",
                "ID партии", "Склад", "Поставщик", "Количество");
        System.out.println("-----|-----------------|-----------------|----------");

        for (StockItem item : batches) {
            System.out.printf("%-4d | %-15s | %-15s | %-10d%n",
                    item.getId(),
                    item.getWarehouse().getName(),
                    item.getSupplier().getName(),
                    item.getCount()
            );
        }
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
        Map<String, List<StockItem>> byWarehouse = stockItems.stream().collect(Collectors.groupingBy(item -> item.getWarehouse().getName()));
        for (Map.Entry<String, List<StockItem>> entry : byWarehouse.entrySet()){
            System.out.println("\nСклад " + entry.getKey());
            for(StockItem item : entry.getValue()){
                System.out.printf(" - %s %d шт. от %s%n", item.getProduct().getName(), item.getCount(), item.getSupplier().getName());
            }
        }
    }
    private void historyOfSales(){
        System.out.println("История продаж");
        List<Sale> sales = saleService.getAll();
        if (sales.isEmpty()){
            System.out.println("Продаж еще не было");
        }
        System.out.printf("%-4s | %-7s | %-15s | %-20s%n",
                "Id продажи", "Дата", "Покупатель", "Состав");
        System.out.println("-----|-----------------|-----------------|----------");
        for (Sale sale : sales){
            String items = sale.getSaleItems().stream()
                    .map(item -> item.getProduct().getName() + " "+ item.getCount() + " шт.")
                    .collect(Collectors.joining(", "));

            System.out.printf("%-4d | %-10s | %-15s | %-30s%n",
                    sale.getId(),
                    sale.getDate().toLocalDate(),
                    sale.getCustomer().getName(),
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
    void printProductMenu(){
        System.out.println("Товары");
        System.out.println("1. Добавить товар");
        System.out.println("2. Показать все товары");
        System.out.println("3. Найти товар по ID");
        System.out.println("4. Удалить товар");
        System.out.println("0. Назад");
    }
    void printCustomerMenu(){
        System.out.println("Покупатели");
        System.out.println("1. Добавить покупателя");
        System.out.println("2. Показать всех покупателей");
        System.out.println("3. Найти покупателей по номеру");
        System.out.println("0. Назад");
    }
    void printSupplierMenu(){
        System.out.println("Поставщики");
        System.out.println("1. Добавить поставщика");
        System.out.println("2. Показать всех поставщиков");
        System.out.println("3. Найти поставщика по номеру");
        System.out.println("0. Назад");
    }
    void printWarehouseMenu(){
        System.out.println("Склады");
        System.out.println("1. Добавить склад");
        System.out.println("2. Показать все склады");
        System.out.println("3. Найти склад по номеру");
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
        return scanner.nextLine();
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
