# <p align="center">Price Comparator - Java Spring Boot Backend project</p>

<p align="center">Price Comparator is a backend application built using Java and Spring Boot. It provides a RESTful API for comparing product prices from different supermarkets (Lidl, Kaufland, Profi)</p>

---

## Features

- **Best Discounts** -> Display the products with the highest percentage discounts.
- **New Discounts** -> Show recently introduced discounts based on discount start dates.
- **Basket Split** -> Split a shopping basket to minimize total cost across multiple stores.
- **Price History** -> Provide historical price data.
- **Product Substitutes** -> Suggest alternative products based on lower unit price (price per kg/l).
- **Custom Price Alert** -> Notify when a product's price drops below a user defined target price.

 ---

## API Endpoints

You can test all endpoints using Swagger UI at: http://localhost:8080/swagger-ui/index.html

### Product Endpoints – `/api/product`

- `GET /api/product/{productId}/substitutes` -> Get substitute products for a specific product
- `POST /api/product/basket/optimize` ->  Optimize basket split for cost (JSON body: list of product IDs)
- `GET /api/product/{productId}/price-history?store={store}` -> Get historical price data for a product (optional `store` filter)
- `GET /api/product/summary ` -> Get summary of available products

---

### Discount Endpoints – `/api/discount`

- `GET /api/discount/best-discount?limit={n}` -> Return the top `n` best discounts (default = 10)
- `GET /api/discount/new-discount`  -> Get discounts recently added (based on date)

---

### Alert Endpoints – `/api/alerts`

- `POST /api/alerts` -> Add a price alert (JSON body: `userId`, `product`, `target`)
- `GET /api/alerts?userId={userId}` -> Get alerts configured by a specific user
  
---

## Technologies Used

- Java 21
- Spring Boot
- H2 Database (in-memory)
- Gradle (build tool)
- Swagger (API documentation)
- OpenCSV

---

1. Clone the repository:
```bash
git clone <repo-url>
cd price-comparator
```


2. Start the application:
```bash
./gradlew bootRun
```

