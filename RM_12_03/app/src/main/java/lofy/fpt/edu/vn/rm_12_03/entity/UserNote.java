package lofy.fpt.edu.vn.rm_12_03.entity;

public class UserNote {
    public String userId;
    public String noteContent;
    public String NoteTime;

    public String getUserId() {
        return userId;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public String getNoteTime() {
        return NoteTime;
    }


    public static final class UserNoteBuilder {
        public String userId;
        public String noteContent;
        public String NoteTime;

        private UserNoteBuilder() {
        }

        public static UserNoteBuilder anUserNote() {
            return new UserNoteBuilder();
        }

        public UserNoteBuilder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public UserNoteBuilder withNoteContent(String noteContent) {
            this.noteContent = noteContent;
            return this;
        }

        public UserNoteBuilder withNoteTime(String NoteTime) {
            this.NoteTime = NoteTime;
            return this;
        }

        public UserNote build() {
            UserNote userNote = new UserNote();
            userNote.NoteTime = this.NoteTime;
            userNote.noteContent = this.noteContent;
            userNote.userId = this.userId;
            return userNote;
        }
    }
}
