package lofy.fpt.edu.vn.rm_12_03.entity;

public class UserActivity {

    public String userId;
    public String activityContent;
    public String activityTime;

    public String getUserId() {
        return userId;
    }

    public String getActivityContent() {
        return activityContent;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public static final class UserActivityBuilder {
        public String userId;
        public String activityContent;
        public String activityTime;

        private UserActivityBuilder() {
        }

        public static UserActivityBuilder anUserActivity() {
            return new UserActivityBuilder();
        }

        public UserActivityBuilder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public UserActivityBuilder withActivityContent(String activityContent) {
            this.activityContent = activityContent;
            return this;
        }

        public UserActivityBuilder withActivityTime(String activityTime) {
            this.activityTime = activityTime;
            return this;
        }

        public UserActivity build() {
            UserActivity userActivity = new UserActivity();
            userActivity.activityContent = this.activityContent;
            userActivity.userId = this.userId;
            userActivity.activityTime = this.activityTime;
            return userActivity;
        }
    }
}
