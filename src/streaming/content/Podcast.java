package streaming.content;

public class Podcast implements AudioContent {
    private final String episodeTitle;
    private final String host;

    public Podcast(String episodeTitle, String host) {
        this.episodeTitle = episodeTitle;
        this.host = host;
    }
    @Override
    public void play() {
        System.out.println("Playing podcast: " + episodeTitle + " by " + host);
    }
}
