package streaming.content;

import javax.print.DocFlavor;

public abstract class ContentFactory {
    public abstract AudioContent createContent(String primary, String secondary);
}
