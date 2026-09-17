package streaming.ui;

public class DesktopUIFactory implements UIFactory {
    @Override
    public PlayButton createPlayButton() {
        return new DesktopPlayButton();
    }
    @Override
    public ProgressBar createProgressBar() {
        return new DesktopProgressBar();
    }
}
