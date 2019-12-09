# WhereFood
Report reference: https://drive.google.com/file/d/12V3cMAtmQKiE7qSktXowONkYrbx_6yE7/view?usp=sharing 
## WhereFood has 3 seperate compopents:
1. WhereFood Mobile Application: 
Git reference: https://github.com/hongkha336/WhereFood-Mobile-Client.git
2. WhereFood API Server:
Git reference: https://github.com/hongkha336/WhereFood-API-Server.git
3. WhereFood Web Management for Admin (Optional):
Git reference: https://github.com/doanvanhiep/WhereFood_Web_Admin.git
## WhereFood demonstration
Step 1: Download [Database Script](https://trello-attachments.s3.amazonaws.com/5d88b1f5037ae6478d5bf238/5dab45285280e81f72df4327/7a39b46706e4a4cbd7efcf9c21c1d7c1/wherefood-db-script-4.0.sql) and install it.</br>
Step 2: Deploy [WhereFood API Server](https://github.com/hongkha336/WhereFood-API-Server.git) without any specical configuration.</br>
Step 3: In [WhereFood Mobile Aplication](https://github.com/hongkha336/WhereFood-Mobile-Client.git) change 
```bash
public static String GLOBAL_URL = YOUR_SERVER_URL;
``` 
from directory: WhereFood-Mobile-Client/app/src/main/java/com/wtf/wherefood/Share/AppConfig.java
</br>
Step 4:(Optional) [Change WhereFood Web Management](https://github.com/doanvanhiep/WhereFood_Web_Admin.git), change the APP_URL to YOUR_SERVER_URL




