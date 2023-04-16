
# Recommendation System API

#### You are required to develop the following solution:
#### A structure that will contain the amount of customers that have bought a specific
#### product. This structure will contain the following info:
- PRODUCT: The product identifier
- QTY: The total amount of customers that bought this product

#### A structure that will associate every product to each other (N*N), every
#### association will contain the following info:
- PRODUCT: The product identifier
- RELATED: Another product identifier that has been bought at least once
by the same customer
- QTY: The amount of customers that bought both products
- M1: QTY / Total amount of customers that bought PRODUCT
M1 is meant to be the correlation factor and its value is always going to be between 0
and 1.

#### The input will be given in the following JSON format:
- {'sales':[{'PRODUCT': 1,'USER': 1},{'PRODUCT': 2,'USER': 1},{'PRODUCT':
1,'USER': 2},{'PRODUCT': 3,'USER': 1},{'PRODUCT': 1,'USER': 1},{'PRODUCT':
1,'USER': 3},{'PRODUCT': 4,'USER': 3}]}

#### Where every object inside the ‘sales’ list represents a sale, having PRODUCT being the
#### identifier of the product that has been sold and USER being the identifier of the
#### customer who has performed that specific purchase

## STACK
- JAVA 17
- Spring Boot 3
- Maven
- Open Api 3
- Spring Cache
- JUnit5

## Documentação da API

#### process json file

```http
  POST /recommendation-system
```

| Parâmetro | Tipo            | Descrição                                        |
|:----------|:----------------|:-------------------------------------------------|
| `file`    | `multipartFile` | **Required**. Enter your json recomendation file |



#### processSalesFile(multipartFile)

Receive one multipart file recommendation file and return one recommendation report

## Rodando localmente

### Maven

```bash
  mvn springboot:run
```

### Intellij

```bash
  application run -> BootApplication.class
```

After initialization you can access open api ui

```bash
  http://localhost:8080/swagger-ui/index.html
```

## Rodando os testes

Running Tests 

```bash
 mvn test
```


## Features

- Process recommendation file

