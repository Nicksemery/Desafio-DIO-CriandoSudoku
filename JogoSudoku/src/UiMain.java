import UI.Custom.Frame.MainFrame;
import UI.Custom.Painel.MainPainel;
import UI.Custom.Screen.MainScreen;

import javax.swing.*;
import java.awt.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class UiMain {

    public static void main(String[] args) {
        final var gameConfig = Stream.of(args)
                .collect(toMap(
                        k -> k.split(";")[0],
                        v -> v.split(";")[1]
                ));
        var mainsScreen = new MainScreen(gameConfig);
        mainsScreen.BuildMainScreen();
    }
}

