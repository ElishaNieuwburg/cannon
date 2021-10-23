package cannon;

import app.StartDesktopApp;
import manager.ai.AIRegistry;

public class runLudii {

    public static void main(final String[] args) {
        AIRegistry.registerAI("My AI", () -> {return new myAI();}, (game) -> {return true;});
        StartDesktopApp.main(new String[0]);
    }
}

