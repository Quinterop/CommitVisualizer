package up.visulog.gitrawdata;

public class CommitBuilder {
    private final String id;
    private String author;
    private String date;
    private String description;
    private String mergedFrom;
    private String stat;

    public CommitBuilder(String id) {
        this.id = id;
    }

    public CommitBuilder setAuthor(String author) {
        this.author = author;
        return this;
    }

    public CommitBuilder setDate(String date) {
        this.date = date;
        return this;
    }

    public CommitBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public CommitBuilder setMergedFrom(String mergedFrom) {
        this.mergedFrom = mergedFrom;
        return this;
    }
    
    public CommitBuilder setStat(String stat) {
    	this.stat = stat;
    	return this;
    }

    public Commit createCommit() {
        return new Commit(id, author, date, description, mergedFrom, stat);
    }
}