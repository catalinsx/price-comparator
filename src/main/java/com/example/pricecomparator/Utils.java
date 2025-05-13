package com.example.pricecomparator;

import com.example.pricecomparator.Model.Discount;
import com.example.pricecomparator.Model.Product;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class  Utils {
    public List<Product> readProducts(String filename){
        String base = filename.substring(0, filename.lastIndexOf('.'));
        String[] partsOfBase = base.split("_");
        String store = partsOfBase[0];
        LocalDate date = LocalDate.parse(partsOfBase[1]);

        InputStream inputStream = getClass().getResourceAsStream("/csv/" + filename);
        if(inputStream == null){
            throw new IllegalArgumentException("File not found");
        }


        List<Product> products = new ArrayList<>();

        try{
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(';')
                    .build();

            CSVReader reader = new CSVReaderBuilder(inputStreamReader)
                    .withCSVParser(parser)
                    .withSkipLines(1)  // skip the header
                    .build();



            String[] columns;
            while((columns = reader.readNext()) != null){
                Product product = new Product();

                product.setProduct_id(columns[0].trim());
                product.setProduct_name(columns[1].trim());
                product.setProduct_category(columns[2].trim());
                product.setBrand(columns[3].trim());
                product.setPackage_quantity(Double.parseDouble(columns[4].trim())); // to be watched
                product.setPackage_unit(columns[5].trim());
                product.setPrice(Double.parseDouble(columns[6].trim()));
                product.setCurrency(columns[7].trim());
                product.setStore(store);
                product.setDate(date);

                products.add(product);
            }

        } catch (Exception exception){
            System.out.println("Parsing error" + exception);
        }
        return products;
    }

    public List<Discount> readDiscounts(String filename){
        String base = filename.substring(0, filename.lastIndexOf('.'));
        String[] partsOfBase = base.split("_");
        String store = partsOfBase[0];

        InputStream inputStream = getClass().getResourceAsStream("/csv/" + filename);
        if(inputStream == null){
            throw new IllegalArgumentException("File not found" + filename);
        }

        List<Discount> discounts = new ArrayList<>();

        try{
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(';')
                    .build();
            CSVReader csvReader = new CSVReaderBuilder(inputStreamReader)
                    .withSkipLines(1)
                    .withCSVParser(parser)
                    .build();

            String[] columns;
            while((columns = csvReader.readNext()) != null){
                Discount discount = new Discount();

                discount.setProduct_id(columns[0].trim());
                discount.setProduct_name(columns[1].trim());
                discount.setBrand(columns[2].trim());
                discount.setPackage_quantity(Double.parseDouble(columns[3].trim()));
                discount.setPackage_unit(columns[4].trim());
                discount.setProduct_category(columns[5].trim());
                discount.setFrom_date(LocalDate.parse(columns[6].trim()));
                discount.setTo_date(LocalDate.parse(columns[7].trim()));
                discount.setPercentage_of_discount(Double.parseDouble(columns[8].trim()));
                discount.setStore(store);

                discounts.add(discount);
            }
        } catch(Exception exception) {
            System.out.println("Parser error" + exception);
        }
        return discounts;
    }
}
