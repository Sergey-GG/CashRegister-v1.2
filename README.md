# Cash Register - [EN]

Cash register is software developed for educational purposes during my studies at VNTU. 
The application simulates the operation of store cash registers.
The program is controlled using special commands — option keys.
All control commands are displayed in the console when the application starts.

Application functionality:
            - Adding new products;
            - Deleting outdated products;
            - Purchasing products;
            - Viewing all products;
            - Ordering product delivery;
            - Automatically restocking products if their quantity is below the minimum allowed;
            - Logging all receipts in a special file checks-logs;
            - The purchase amount is added to the cash register and stored there (cass.txt);
            - If the amount in the cash register exceeds the set limit, a collection is carried out and the money is transferred to a bank account (bank.txt);
            - All available products (their ID, name, weight, and price) are stored in database files:
                        milkProducts.txt — milk products,
                        meatProducts.txt — meat products;
            - The delivery cost is 75% of the product’s selling price, and the amount is deducted from the bank account.
            
Technologies used in development:
            - use of a configuration class to manage Spring Core tools;
            - use of @Component annotations for automatic bean creation;
            - use of @Scope annotations to change bean scope;
            - use of @Value annotations to assign values to fields via .properties file;
            - use of @Autowired and @Qualifier annotations for dependency injection;
            - use of beans for object creation;
            - use of Inversion of Control (IoC);
            - use of Init, Destroy, and Factory methods;
            - exception handling.


# Cash Register - [UA]

Cash register — це програмне забезпечення, розроблене в освітніх цілях під час навчання у ВНТУ. 
Додаток імітує роботу касових апаратів у магазині.
Керування програмою здійснюється за допомогою спеціальних команд — ключів певних опцій.
Усі команди керування відображаються в консолі під час запуску програми.

Функціонал програми:
            - Додавання нових товарів;
            - Видалення застарілих товарів;
            - Купівля товарів;
            - Перегляд усіх товарів;
            - Замовлення доставки товарів;
            - Автоматичне поповнення запасів товару, якщо його кількість менша за мінімально допустиму;
            - Логування всіх чеків у спеціальному файлі checks-logs;
            - Сума з покупки надходить у касу й зберігається там (файл cass.txt);
            - Якщо сума в касі перевищує встановлене значення, виконується інкасація і гроші переводяться на банківський рахунок (файл bank.txt);
            - Усі товари, що є в продажу (їхні ID, назва, вага та ціна), зберігаються у базах даних:
                        milkProducts.txt — молочні продукти,
                        meatProducts.txt — м’ясні продукти;
            - Вартість доставки товару становить 75% від його ціни продажу, гроші списуються з банківського рахунку.

Під час розробки додатку використовувалися такі технології:
            - використання конфігураційного класу для керування засобами Spring Core;
            - використання анотацій @Component для автоматичного створення бінів;
            - використання анотацій @Scope для зміни області видимості бінів;
            - використання анотацій @Value для присвоєння значень полям через файл .properties;
            - використання анотацій @Autowired і @Qualifier для впровадження залежностей;
            - використання бінів для створення об'єктів;
            - використання інверсії керування (IoC);
            - використання методів Init, Destroy і Factory;
            - обробка виключень.
