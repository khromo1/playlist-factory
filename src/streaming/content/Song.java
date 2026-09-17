package streaming.content;

public class Song implements AudioContent {
    private final String title;
    private final String artist;

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }
    @Override
    public void play() {
        System.out.println("Playlist song: " + title + " by " + artist);
    }

}
