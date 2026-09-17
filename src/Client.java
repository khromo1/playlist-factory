import streaming.content.*;
import streaming.ui.*;
import streaming.ui.Mobile.MobileUIFactory;

public class Client {
    public static void main(String[] args) {
        ContentFactory songFactory = new SongFactory();
        AudioContent song = songFactory.createContent("Let It Happen", "Tame Impala");
        song.play();

        ContentFactory podcastFactory = new PodcastFactory();
        AudioContent podcast = podcastFactory.createContent("Design Patterns 101", "Tech Talks");
        podcast.play();

        UIFactory mobileFactory = new MobileUIFactory();
        renderPLayerUI(mobileFactory);
        UIFactory desktopFactory = new DesktopUIFactory();
        renderPLayerUI(desktopFactory);
    }
    private static void renderPLayerUI(UIFactory factory) {
        PlayButton button = factory.createPlayButton();
        ProgressBar bar = factory.createProgressBar();
        button.render();
        bar.render();
    }
}
