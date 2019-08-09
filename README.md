# Trading App
## Overview
- [Trading App](#trading-app)
  - [Overview](#overview)
  - [Introduction](#introduction)
  - [Quick Start](#quick-start)
  - [REST API Usage](#rest-api-usage)
    - [Swagger](#swagger)
    - [App controller](#app-controller)
    - [Quote Controller](#quote-controller)
    - [Trader Controller](#trader-controller)
    - [Order Controller](#order-controller)
    - [Dashboard controller](#dashboard-controller)
  - [Architecture](#architecture)
    - [Component diagram](#component-diagram)
    - [Controller layer](#controller-layer)
    - [Service layer](#service-layer)
    - [Dao layer](#dao-layer)
    - [SpringBoot](#springboot)
    - [PSQL and IEX](#psql-and-iex)
  - [Improvements](#improvements)
<!-- toc -->

## Introduction
- This Trading App is an online stock trading simulation REST API which can register accounts, update amounts, quote & update stock info 
and trade stocks with virtual amounts.
- New traders or people who are interested in trading could utilize this REST API to get familiar with the basic trading stock procedures like viewing stock info, 
buying and selling stocks. Front-end and mobile developer could utilize this REST API to build a more comprehensive website and a mobile app.
- Trading App is a MicroService which is implemented with the following technologies:
  - [SpringBoot](https://spring.io/projects/spring-boot)
  - [PSQL database](https://www.postgresql.org/)
  - [IEX market data](https://iexcloud.io/)
  - [Swagger](https://swagger.io/)
## Quick Start
- Prerequisite:
  - CentOS 7
  - Java 8
  - Docker (17.05 or higher which support multi-stage build)
  - IEX token for getting market data (https://iexcloud.io/docs/api/)
- PSQL init:
```shell script
#start docker
sudo systemctl status docker || sudo systemctl start docker || sleep 5
#pull PostgreSQL image
sudo docker pull postgres
#create docker volume to persist db data
sudo docker volume ls | grep "pgdata" || sudo docker volume create pgdata || sleep 1
#install psql CLI
sudo yum install -y postgresql
```
- git clone and mvn build
```shell script
sudo yum install -y git
git clone https://github.com/steve-ma-jrvs/Trading-App.git
cd Trading-App
sudo yum install -y maven
mvn -f pom.xml clean package -DskipTests
```
- Start Springboot app using a shell script
```shell script
cd scripts
sudo bash run_trading_app.sh {PSQL_HOST} {PSQL_USER} {PSQL_PASSWORD} {IEX_PUB_TOKEN}
#verify health
curl localhost:8080/health
#verify Swagger UI from your browser
localhost:8080/swagger-ui.html
```
  - `run_trading_app.sh` env vars:
    - {PSQL_HOST} : `localhost`
    - {PSQL_USER} : `postgres`
    - {PSQL_PASSWORD} : your password
    - {IEX_PUB_TOKEN} : your IEX_TOKEN
- Consume REST API
  - Swagger-UI interface diagram
  ![image1](https://github.com/steve-ma-jrvs/Trading-App/blob/master/images/Swagger%20API.png)
  - REST API Implementation
    - By default, Ticker `AAPL` is added in the dailyList
    - We could retrieve the dailyList quote by URL
  ![image2](https://github.com/steve-ma-jrvs/Trading-App/blob/master/images/REST%20API.png)
- trading_app docker diagram including:
    - Diagram
    ![image3](https://github.com/steve-ma-jrvs/Trading-App/blob/master/images/DockerHub.png)
    - bridge network
        - create network bridge between SpringBoot app and postgreSQL
    - containers
        - PSQL container
        - Trading_App container
    - label commands
        - Implementation details plz visit the following REPO
        - [Cloud DevOps](https://github.com/steve-ma-jrvs/cloud_DevOps)
## REST API Usage
### Swagger
- Swagger is a powerful yet easy-to-use suite of API developer tools for teams and individuals, enabling development across the entire API lifecycle, from design and documentation, to test and deployment.
- Swagger provides a set of tools that help programmers generate client or server code and install self-generated documentation for web services.
### App controller
- GET `/health` to make sure SpringBoot app is up and running
### Quote Controller
- Quote controller implements `QuoteServices`. Basically, it could retrieve the market data coming from IEX which is an external market data source with the `MarketDataDao` and the `IexQuote` domain, 
convert the IEX data to the `Quote` data and save it in the Quote table with the `QuoteDao`. Also it could show and update the data from the Quote table and add new data to the Quote table as well.
- Endpoints in this controller
  - GET `/quote/dailyList`: list all securities that are available to trading in this trading system
  - GET `/quote/iex/ticker/{ticker}`: Show iexQuote for a given ticker/symbol from IEX which is an external market data source
  - POST `/quote/tickerId/{tickerId}`: Add a new ticker/symbol to the quote table, so trader can trade this security.
  - PUT `/quote/`: Manually update a quote in the quote table
  - PUT `/quote/iexMarketData`: Update all quotes from IEX
### Trader Controller
- Trader controller implements `FundTransferService` and `RegisterService`. It could manage trader and account information such as create a new account and delete an exist account.
Also, it could deposit and withdraw fund from a given account.
- Endpoints in this controller
  - DELETE `/trader/traderId/{traderId}`: Delete a trader
  - POST `/trader/`: Create a trader and an account with DTO
  - POST `/trader/firstname/{firstname}/lastname/{lastname}/dob/{dob}/country/{country}/email/{email}`: Create a trader and an account with url
  - PUT `/trader/deposit/traderId/{traderId}/amount/{amount}`: Deposit a fund
  - PUT `/trader/withdraw/traderId/{traderId}/amount/{amount}`: Withdraw a fund
### Order Controller
- Order controller implements `OrderService`. It create a new market order with `MardetOrderDTO`.
- Endpoints in this controller
  - POST `/order/marketOrder`: Submit a market order with DTO
### Dashboard controller
- Trader controller implements `DashboardService`. It could show the `PortfolioView` and `TraderAccountView` with the given traderID.
- Endpoints in this controller
  - GET `/dashboard/portfolio/traderId/{traderId}`: Show portfolio by trader ID
  - GET `/dashboard/profile/traderId/{traderId}`: Show trader profile by trader ID

## Architecture
### Component diagram
![image](https://github.com/steve-ma-jrvs/Trading-App/blob/master/images/TradingApp%20Architecture.png)
### Controller layer
- Controllers receive input, and generate output. They would handle the navigation between the different views.
- In this app, it has 5 controllers in total
  - `AppController`
  - `DashboardController`
  - `OrderController`
  - `QuoteController`
  - `TraderController`
- Each of them implement different services
### Service layer
- The service layer stands on DAO to handle business requirements.
- Inside the service, we could design different business logic such as implementing validations, constraints and so on.
- In this app, it has 5 services in total
  - `FundTransferService`
  - `DashboardService`
  - `OrderService`
  - `QuoteService`
  - `RegisterService`
### Dao layer
- DAO stands for data access object. It provides a CRUD interface for a single entity.
- In this app, it will handle the data transaction with the PSQL database by utilizing `DataSource` and `Connection Manager`.
- It has 6 DAOs in total
    - `AccountDao`
    - `MarketDataDao`
    - `PositionDao`
    - `QuoteDao`
    - `SecurityOrderDao`
    - `TraderDao`
### SpringBoot
- In this app, we use the Tomcat which is the embedded servlet container used by SpringBoot to connect the web.
- Inversion of Control is a principle which the control of objects or portions of a program is transferred to a container or framework.
- In SpringBoot, the Spring container will create the objects, wire them together, configure them, and manage their complete lifecycle from creation till destruction.

### PSQL and IEX
- PostgreSQL(PSQL) is the database we used in this app.
- PSQL is a powerful, open source object-relational database system that uses and extends the SQL language combined with many features that safely store and scale the most complicated data workloads.
- IEX cloud API(https://iexcloud.io/docs/api/#introduction), the financial data source we used in this app. 
- The IEX Cloud API is based on REST, has resource-oriented URLs, returns JSON-encoded responses, and returns standard HTTP response codes.

## Improvements
- No UI frontend design. Swagger UI is great but it has limitation. Could build a customize UI to replace Swagger
- Could add more services and make the app more varieties
- No backup data
- No demo example and navigation
- Trading stock part is only covered simple calculation (long position), short position is not implemented
