
[Class Designs]:
-model package: contains the three models(user,account,transaction) that are used in this application, and an Id generator.
-database package: contains my database schema, open helper, cursor wrapper and DAO.
-MainActivity: use to log in or go to the register page
-CreateNewUserActivity and CreateNewUserFragment: use to register new user.
-UserHomePageActivity: the homepage, which includes the user info and the accounts list.
-AccountListFragment: use to generate the accounts list.
-CreateNewAccountActivity and CreateNewAccountFragment: use to add new account.
-transaction package: use to handle the transactions and generate the transaction history.
-DateDialogFragment: use to select date (maybe extra credit).

[Tips]
1. In user homepage, you can click on the little icon on the menu bar to log-off.
2. Click the item on account list to access the transaction history.


Wen Zhou 06/13/2016