package streaming.content;

public abstract class ContentFactory {
    public abstract AudioContent createContent(String primary, String secondary);
}
