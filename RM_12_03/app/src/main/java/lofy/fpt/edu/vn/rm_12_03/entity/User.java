package lofy.fpt.edu.vn.rm_12_03.entity;

public class User {
    public String userId;
    public String userImg;
    public String userShortName;
    public String userFullName;
    public String userNickname;
    public String userDOB;
    public String userHomeTown;
    public String userRelationship;
    public boolean userGender;
    public String userMaritalStatus;
    public String userPhoneNumber;
    public String userFacebookLink;
    public String userEmail;
    public String userAddress;
    public String userJob;

    public String getUserId() {
        return userId;
    }

    public String getUserImg() {
        return userImg;
    }

    public String getUserShortName() {
        return userShortName;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public String getUserDOB() {
        return userDOB;
    }

    public String getUserHomeTown() {
        return userHomeTown;
    }

    public String getUserRelationship() {
        return userRelationship;
    }

    public boolean isUserGender() {
        return userGender;
    }

    public String getUserMaritalStatus() {
        return userMaritalStatus;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public String getUserFacebookLink() {
        return userFacebookLink;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getUserJob() {
        return userJob;
    }


    public static final class UserBuilder {
        public String userId;
        public String userImg;
        public String userShortName;
        public String userFullName;
        public String userNickname;
        public String userDOB;
        public String userHomeTown;
        public String userRelationship;
        public boolean userGender;
        public String userMaritalStatus;
        public String userPhoneNumber;
        public String userFacebookLink;
        public String userEmail;
        public String userAddress;
        public String userJob;

        private UserBuilder() {
        }

        public static UserBuilder anUser() {
            return new UserBuilder();
        }

        public UserBuilder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public UserBuilder withUserImg(String userImg) {
            this.userImg = userImg;
            return this;
        }

        public UserBuilder withUserShortName(String userShortName) {
            this.userShortName = userShortName;
            return this;
        }

        public UserBuilder withUserFullName(String userFullName) {
            this.userFullName = userFullName;
            return this;
        }

        public UserBuilder withUserNickname(String userNickname) {
            this.userNickname = userNickname;
            return this;
        }

        public UserBuilder withUserDOB(String userDOB) {
            this.userDOB = userDOB;
            return this;
        }

        public UserBuilder withUserHomeTown(String userHomeTown) {
            this.userHomeTown = userHomeTown;
            return this;
        }

        public UserBuilder withUserRelationship(String userRelationship) {
            this.userRelationship = userRelationship;
            return this;
        }

        public UserBuilder withUserGender(boolean userGender) {
            this.userGender = userGender;
            return this;
        }

        public UserBuilder withUserMaritalStatus(String userMaritalStatus) {
            this.userMaritalStatus = userMaritalStatus;
            return this;
        }

        public UserBuilder withUserPhoneNumber(String userPhoneNumber) {
            this.userPhoneNumber = userPhoneNumber;
            return this;
        }

        public UserBuilder withUserFacebookLink(String userFacebookLink) {
            this.userFacebookLink = userFacebookLink;
            return this;
        }

        public UserBuilder withUserEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public UserBuilder withUserAddress(String userAddress) {
            this.userAddress = userAddress;
            return this;
        }

        public UserBuilder withUserJob(String userJob) {
            this.userJob = userJob;
            return this;
        }

        public User build() {
            User user = new User();
            user.userFullName = this.userFullName;
            user.userAddress = this.userAddress;
            user.userHomeTown = this.userHomeTown;
            user.userGender = this.userGender;
            user.userRelationship = this.userRelationship;
            user.userShortName = this.userShortName;
            user.userPhoneNumber = this.userPhoneNumber;
            user.userId = this.userId;
            user.userFacebookLink = this.userFacebookLink;
            user.userMaritalStatus = this.userMaritalStatus;
            user.userJob = this.userJob;
            user.userEmail = this.userEmail;
            user.userDOB = this.userDOB;
            user.userNickname = this.userNickname;
            user.userImg = this.userImg;
            return user;
        }
    }
}
