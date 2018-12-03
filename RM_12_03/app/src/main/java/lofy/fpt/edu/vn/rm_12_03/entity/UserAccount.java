package lofy.fpt.edu.vn.rm_12_03.entity;

public class UserAccount {
    public String accountId;
    public String accountName;
    public String accountPassword;

    public String getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public static final class UserAccountBuilder {
        public String accountId;
        public String accountName;
        public String accountPassword;

        public UserAccountBuilder() {
        }

        public static UserAccountBuilder anUserAccount() {
            return new UserAccountBuilder();
        }

        public UserAccountBuilder withAccountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public UserAccountBuilder withAccountName(String accountName) {
            this.accountName = accountName;
            return this;
        }

        public UserAccountBuilder withAccountPassword(String accountPassword) {
            this.accountPassword = accountPassword;
            return this;
        }

        public UserAccount build() {
            UserAccount userAccount = new UserAccount();
            userAccount.accountPassword = this.accountPassword;
            userAccount.accountId = this.accountId;
            userAccount.accountName = this.accountName;
            return userAccount;
        }
    }
}


