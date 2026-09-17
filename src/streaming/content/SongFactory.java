package streaming.content;

public class SongFactory extends ContentFactory {
     @Override
    public AudioContent createContent(String title, String artist) {
         return new Song(title, artist);
     }
}
