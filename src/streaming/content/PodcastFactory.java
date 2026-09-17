package streaming.content;

public abstract class PodcastFactory extends ContentFactory {
    @Override
    public AudioContent createContent(String episodeTitle, String host) {
        return new Podcast(episodeTitle, host);
    }
}