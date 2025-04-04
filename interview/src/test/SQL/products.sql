-- Use 'show tables' to see tables

-- Given table sales:


--  CREATE TABLE sales (
--         product VARCHAR(100) NOT NULL,
--         year INT
--     );

-- Identify most selling product in each year.

-- Examle of sales table:

-- +-----------------------------------------------------+------+
-- | product                                             | year |
-- +-----------------------------------------------------+------+
-- | ThisWorx for Car Vacuum Cleaner                     | 2022 |
-- | Zulay Kitchen Original Milk Frother                 | 2024 |
-- | Bedsure Satin Pillowcase for Hair and Skin          | 2021 |
-- | Bedsure Satin Pillowcase for Hair and Skin          | 2024 |
-- | Blink Home Security Video Doorbell                  | 2021 |

    -- insert into sales values('ThisWorx for Car Vacuum Cleaner', 2022);
    -- insert into sales values('Zulay Kitchen Original Milk Frother', 2024);
    -- insert into sales values('Bedsure Satin Pillowcase for Hair and Skin', 2021);
    -- insert into sales values('Bedsure Satin Pillowcase for Hair and Skin', 2024);
    -- insert into sales values('Blink Home Security Video Doorbell', 2021);
    -- insert into sales values('Silonn Countertop Ice Maker', 2023);
    -- insert into sales values('Zulay Kitchen Original Milk Frother', 2024);
    -- insert into sales values('Shashibo Shape Shifting Box', 2024);
    -- insert into sales values('Bedsure Satin Pillowcase for Hair and Skin', 2022);
    -- insert into sales values('iOttie Dash &amp; Windshield Car Mount Phone Holder', 2024);
    -- insert into sales values('Pulidiki Car Cleaning Gel', 2021);
    -- insert into sales values('Blink Home Security Video Doorbell', 2021);
    -- insert into sales values('iOttie Dash &amp; Windshield Car Mount Phone Holder', 2021);
    -- insert into sales values('Delomo Pet Deshedding Brush Glove', 2021);
    -- insert into sales values('Silonn Countertop Ice Maker', 2023);
    -- insert into sales values('Repel Windproof Travel Umbrella', 2023);
    -- insert into sales values('Repel Windproof Travel Umbrella', 2023);
    -- insert into sales values('Silonn Countertop Ice Maker', 2024);
    -- insert into sales values('Delomo Pet Deshedding Brush Glove', 2023);
    -- insert into sales values('Repel Windproof Travel Umbrella', 2024);
    -- insert into sales values('Repel Windproof Travel Umbrella', 2022);
    -- insert into sales values('Repel Windproof Travel Umbrella', 2024);
    -- insert into sales values('Silonn Countertop Ice Maker', 2022);
    -- insert into sales values('Silonn Countertop Ice Maker', 2024);
    -- insert into sales values('Zulay Kitchen Original Milk Frother', 2023);
    -- insert into sales values('ThisWorx for Car Vacuum Cleaner', 2021);
    -- insert into sales values('Zulay Kitchen Original Milk Frother', 2024);
    -- insert into sales values('ThisWorx for Car Vacuum Cleaner', 2021);
    -- insert into sales values('Bedsure Satin Pillowcase for Hair and Skin', 2024);
    -- insert into sales values('iOttie Dash &amp; Windshield Car Mount Phone Holder', 2023);


-- Identify most selling product in each year.

--SELECT MAX(cuenta),product, year
--  FROM  (select count(*) as cuenta, product, year
--    FROM sales
--    GROUP BY product,year)
--    as table1
--GROUP BY product, year;


select count(*) as cuenta, product, year
    FROM sales
     where year = 2024;


