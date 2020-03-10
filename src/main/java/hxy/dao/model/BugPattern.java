package hxy.dao.model;

/**
 * @author eric
 * @program translate
 * @description
 * @date 2020/3/10
 */
public class BugPattern {
    String bugPatthern;
    String shortDescription;
    String details;

    public String getBugPatthern() {
        return bugPatthern;
    }

    public void setBugPatthern(String bugPatthern) {
        this.bugPatthern = bugPatthern;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
