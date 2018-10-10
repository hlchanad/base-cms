# Base CMS [WIP]
This is a base CMS writing with Spring Boot 2. Since I am using Oracle Database, 
the application config or (may be some?) SQL may flavor Oracle. Please be 
reminded that you may need to alter it to fix yourself. I may try to see if 
MySQL or other database works with it. I will try to work on base api server 
after this one :)

Functions I would like to have as a base CMS :

- User login
- User permission on different page/ action
- Easy but extensible for CRUD (hopefully like < 5 minutes for each new table)

More functions may be added if I have some new ideas.

## Starting the server
Replace the database config in `/resources/application.properties`. 
Run the application with the flag `spring.profiles.active=local`. 
That's it!

## Credits
I am using this theme https://themeforest.net/item/pages-admin-dashboard-template-web-app/9694847. Please support them if you like this awesome theme :)
