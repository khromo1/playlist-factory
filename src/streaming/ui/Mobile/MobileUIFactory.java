package streaming.ui.Mobile;

import streaming.ui.ProgressBar;
import streaming.ui.UIFactory;

public class MobileUIFactory implements UIFactory {
    @Override
    public MobilePlayButton createPlayButton() {
        return  new MobilePlayButton();
    }
    @Override
    public ProgressBar createProgressBar() {
        return new MobileProgressBar();
    }
}
