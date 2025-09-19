package com.anderson.prueba;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.anderson.prueba.models.Product;

public class ProductManager {

    public static void main(String[] args) {
        ArrayList<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Laptop");
        product1.setPrice(800);
        product1.setCategory("tecnologia");
        product1.setStock(5);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Mouse");
        product2.setPrice(25);
        product2.setCategory("tecnologia");
        product2.setStock(0);

        Product product3 = new Product();
        product3.setId(3L);
        product3.setName("Silla");
        product3.setPrice(150);
        product3.setCategory("muebles");
        product3.setStock(3);

        Product product4 = new Product();
        product4.setId(4L);
        product4.setName("Monitor");
        product4.setPrice(300);
        product4.setCategory("tecnologia");
        product4.setStock(7);

        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);

        filterProducst(products);
        sortProducts(products);
        findMostExpensiveProductByCategory(products);
    }

    // Función que filtre productos disponibles (stock > 0) y calcule el valor total

    /**
     * 
     * Este método toma una lista de productos, filtra aquellos sin stock,
     * e imprime los detalles de cada producto disponible en la consola incluyendo
     * ID, nombre, precio, categoría y cantidad en stock.
     * 
     * @param products ArrayList que contiene objetos Product para ser filtrados y mostrados
     */
    static void filterProducst(ArrayList<Product> products) {
        System.out.println("--- MOSTRANDO PRODUCTOS FILTRADOS ---");
        List<Product> filteredProducts = products.stream().filter(p -> p.getStock() >= 1).collect(Collectors.toList());
        
        double totalValue = 0;
        
        System.out.println("┌─────┬─────────────────┬─────────┬─────────────┬───────┬─────────────┐");
        System.out.println("│ ID  │ Nombre          │ Precio  │ Categoría   │ Stock │ Valor Total │");
        System.out.println("├─────┼─────────────────┼─────────┼─────────────┼───────┼─────────────┤");
        
        for (Product p : filteredProducts) {
            double productTotal = p.getPrice() * p.getStock();
            totalValue += productTotal;
            
            System.out.printf("│ %-3d │ %-15s │ $%-6d │ %-11s │ %-5d │ $%-10.0f │%n",
                p.getId().intValue(), 
                p.getName(), 
                p.getPrice(), 
                p.getCategory(), 
                p.getStock(),
                productTotal
            );
        }
        
        System.out.println("└─────┴─────────────────┴─────────┴─────────────┴───────┴─────────────┘");
        System.out.printf("Valor total de productos filtrados: $%.0f%n%n", totalValue);
    }

    //Función que ordene productos por precio (menor a mayor) y valide que todos los precios sean positivos
    /**
     * 
     * Este método filtra los productos que tienen stock mayor a 0, los ordena
     * por precio de forma ascendente y luego imprime sus detalles en la consola.
     * 
     * @param products Lista de productos a filtrar, ordenar y mostrar
     */
    static void sortProducts(ArrayList<Product> products) {
        System.out.println("--- MOSTRANDO PRODUCTOS ORGANIZADOS (POR PRECIO) ---");
        List<Product> sortedProducts = products.stream()
        .filter(p -> p.getStock() > 0)
        .sorted(Comparator.comparing(Product::getPrice)).toList();

        System.out.println("┌─────┬─────────────────┬─────────┬─────────────┬───────┐");
        System.out.println("│ ID  │ Nombre          │ Precio  │ Categoría   │ Stock │");
        System.out.println("├─────┼─────────────────┼─────────┼─────────────┼───────┤");

        for (Product p : sortedProducts) {
            System.out.printf("│ %-3d │ %-15s │ $%-6d │ %-11s │ %-5d │%n",
                p.getId().intValue(), 
                p.getName(), 
                p.getPrice(), 
                p.getCategory(), 
                p.getStock()
            );
        }
        
        System.out.println("└─────┴─────────────────┴─────────┴─────────────┴───────┘");
        System.out.println();
    }

    //Función que encuentre el producto más caro de cada categoría
    /**
     * 
     * Este método agrupa los productos por su categoría e identifica el producto con el precio más alto
     * dentro de cada categoría. Luego imprime información detallada sobre cada producto más caro
     * incluyendo ID, nombre, precio y cantidad en stock.
     * 
     * 
     * @param products ArrayList que contiene objetos Product para ser analizados. Cada producto debe tener
     *                propiedades válidas de categoría, id, nombre, precio y stock.
     * 
     * El método imprime los resultados directamente a System.out con etiquetas en español.
     *           Si una categoría no tiene productos, esa categoría
     *           será omitida debido a la verificación Optional.isPresent().
     * 
     */
    static void findMostExpensiveProductByCategory(ArrayList<Product> products) {
        System.out.println("--- MOSTRANDO PRODUCTO MÁS CARO POR CATEGORÍA ---");
        Map<String, Optional<Product>> mostExpensiveByCategory = products.stream()
                .collect(Collectors.groupingBy(Product::getCategory,
                        Collectors.maxBy(Comparator.comparing(Product::getPrice))));

        System.out.println("┌─────────────┬─────┬─────────────────┬─────────┬───────┐");
        System.out.println("│ Categoría   │ ID  │ Nombre          │ Precio  │ Stock │");
        System.out.println("├─────────────┼─────┼─────────────────┼─────────┼───────┤");

        for (Map.Entry<String, Optional<Product>> entry : mostExpensiveByCategory.entrySet()) {
            String category = entry.getKey();
            Optional<Product> productOpt = entry.getValue();

            if (productOpt.isPresent()) {
                Product product = productOpt.get();
                System.out.printf("│ %-11s │ %-3d │ %-15s │ $%-6d │ %-5d │%n",
                    category,
                    product.getId().intValue(),
                    product.getName(),
                    product.getPrice(),
                    product.getStock()
                );
            }
        }
        
        System.out.println("└─────────────┴─────┴─────────────────┴─────────┴───────┘");
        System.out.println();
    }
}