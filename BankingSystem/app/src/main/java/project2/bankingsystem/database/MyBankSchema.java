package project2.bankingsystem.database;

/**
 * Created by zhouwen on 6/11/16.
 */
public class MyBankSchema {
    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "wen_project02.db";

    public static final class UserTable{
        public static final String NAME = "users";

        public static final class Cols {
            public static final String FIRST_NAME = "first_name";
            public static final String LAST_NAME = "last_name";
            public static final String MIDDLE_NAME = "middle_name";
            public static final String BIRTH_DATE = "birth_date";
            public static final String USERNAME = "username";
            public static final String PASSWORD = "password";
            public static final String PROFILE_PIC = "profile_picture";
            //public static final String CHECKING_ACCOUNTS = "checking_accounts";
            //public static final String SAVINGS_ACCOUNT = "savings_account";
            public static final String DEFAULT_ACCOUNT_Id = "default_account_id";
            //public static final String ACCOUNTS = "all_accounts";
        }
    }

    public static final class AccountTable{
        public static final String NAME = "accounts";

        public static final class Cols {
            public static final String BALANCE = "balance";
            public static final String ACCOUNT_ID = "account_id";
            public static final String IS_EFFECTIVE = "is_effective";
            public static final String IS_SAVINGSACCOUNT = "is_savings_account";
            public static final String IS_DEFAULT = "is_default";
            public static final String OWNER_USERNAME = "owner";
            //public static final String TRANSACTION_LIST = "transaction_list";
        }
    }

    public static final class TransactionTable{
        public static final String NAME = "transactions";

        public static final class Cols{
            public static final String TYPE = "type";
            public static final String TRANSACTION_DATE = "transaction_date";
            public static final String AMOUNT = "amount";
            public static final String SOURCE_ACCOUNT_ID = "source_account";
            public static final String DESTINATION_ACCOUNT_ID = "destination_account";
            public static final String SOURCE_ACCOUNT_USERNAME = "source_account_username";
            public static final String DESTINATION_ACCOUNT_USERNAME ="destination_account_username";
        }
    }

    public static final class IdTable{
        public static final String NAME = "unique_id";

        public static final class Cols{
            public static final String LAST_ID = "last_id";
        }
    }
}
