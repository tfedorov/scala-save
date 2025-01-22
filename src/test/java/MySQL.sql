-- Description of application:
--  Tables city_region, orders
-- Task 1: Show total prices by region, from biggest to lowest
-- Task 2: Show total prices by region where revenue > 500,  from  lowest to biggest
-- Task 3: Show each city with its nearest lower-priced city in the same region eg:
--  --   |------------------------------------------------------|
--  --   |Region|City         | Price  | nearest lower-priced city|
--  --   |------------------------------------------------------|
--  --   |West  |San Francisco| 249.99 | Seattle
--  --   |West  |Seattle      | 219.99 | Los Angeles
--  --   |West  |Los Angeles  | 199.99 | NULL
--  --   |------------------------------------------------------|

CREATE TABLE city_region (
    city_name VARCHAR(50) NOT NULL PRIMARY KEY, -- City name
    state_code VARCHAR(2) NOT NULL,            -- Abbreviation of the state (e.g., CA, TX, NY)
    region VARCHAR(50) NOT NULL                -- Historical region (e.g., Midwest, West, Northeast)
);

-- Insert data into the 'city_region' table
INSERT INTO city_region (state_code, city_name, region) VALUES
('CA', 'Los Angeles', 'West'),
('CA', 'San Francisco', 'West'),
('TX', 'Austin', 'South'),
('TX', 'Houston', 'South'),
('NY', 'New York', 'Northeast'),
('IL', 'Chicago', 'Midwest'),
('OH', 'Columbus', 'Midwest'),
('WA', 'Seattle', 'West'),
('MA', 'Boston', 'Northeast');

-- Create the 'order' table
CREATE TABLE orders (
    city VARCHAR(50) NOT NULL, -- City name
    price DECIMAL(10, 2) NOT NULL -- Order price
);

-- Insert data into the 'orders' table
INSERT INTO orders (city, price) VALUES
('Los Angeles', 199.99),
('San Francisco', 249.99),
('Austin', 149.99),
('Houston', 179.99),
('San Diego', 229.99),
('New York', 299.99),
('Chicago', 189.99),
('Columbus', 159.99),
('Seattle', 219.99),
('Boston', 259.99);


