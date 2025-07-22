package UI.Custom.Screen;

import Service.BoardService;
import Service.EventEnum;
import Service.NotifierService;
import UI.Custom.Button.CheckGameStatusButton;
import UI.Custom.Button.FinishGameButton;
import UI.Custom.Button.ResetButton;
import UI.Custom.Frame.MainFrame;
import UI.Custom.Input.NumberText;
import UI.Custom.Painel.MainPainel;
import UI.Custom.Painel.SudokuSector;
import model.Space;

import javax.swing.*;
import java.awt.*;
import java.security.interfaces.RSAKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Service.EventEnum.CLEAR_SPACE;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

public class MainScreen {

    private final static Dimension dimension = new Dimension(600, 600);

    private final BoardService boardService;
    private final NotifierService notifierService;

    private JButton checkGameStatusButton;
    private JButton finishGameButton;
    private JButton resetButton;



    public MainScreen(final Map<String, String> gameConfig) {
        this.boardService = new BoardService(gameConfig);
        this.notifierService = new NotifierService();
    }

    public void BuildMainScreen() {
        JPanel mainPanel = new MainPainel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);
        for (int r=0; r<9; r+=3) {
            var endRow = r+2;
            for (int r2=0; r2<9; r2+=3) {
                var endCol = r2+2;
                var spaces = getSpacesFromSector(boardService.getBoard(), r2, endCol,r, endRow );
                mainPanel.add(generatePanel(spaces));
            }
        }
        addResetButton(mainPanel);
        addCheckGameStatusButton(mainPanel);
        addFinishGameButton(mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();

    }

    private List<Space> getSpacesFromSector(List<List<Space>> spaces, final int initiCol, final int endCol, final int initiRow, final int endRow) {
        List<Space> spaceSector = new ArrayList<>();
        for (int r = initiRow; r<=endRow; r++) {
            for (int c = initiCol; c<=endCol; c++) {
                spaceSector.add(spaces.get(r).get(c));
            }
        }return spaceSector;
    }

    private JPanel generatePanel(final List<Space> spaces) {
        List<NumberText> fields = new ArrayList<>(spaces.stream().map(NumberText::new).toList());
        fields.forEach(t -> notifierService.subscribe(CLEAR_SPACE,t));
        return new SudokuSector(fields);
    }

    private void addFinishGameButton(JPanel mainPanel) {
        finishGameButton = new FinishGameButton(e ->{
            if (boardService.isGameOver()) {
                showMessageDialog(null, "Parabens, jogo completo!");
                resetButton.setEnabled(false);
                checkGameStatusButton.setEnabled(false);
                finishGameButton.setEnabled(false);
            }else {
                showMessageDialog(null, "seu jogo tem alguma inconsistencia, ajuste e tente outra vez");
            }
        });
        mainPanel.add(finishGameButton);
    }

    private void addCheckGameStatusButton(JPanel mainPanel) {
        checkGameStatusButton = new CheckGameStatusButton(e -> {
            var hasErrors =boardService.hasErrors();
            var gameStatus = boardService.getGameStatus();
            var message = switch (gameStatus){
                case NON_STARTED -> "jogo não iniciado";
                case INCOMPLETE -> "Jogo incompleto";
                case COMPLETE -> "Completo";
            };
            message += hasErrors ? " contem erros" : " não contem error";
            showMessageDialog(null, message);
        });

        mainPanel.add(checkGameStatusButton);
    }

    private void addResetButton(JPanel mainPanel) {
       resetButton = new ResetButton(e -> {
            var dialogResult = showConfirmDialog(
                    null,"Deseja realmente reiniciar o jogo?",
                    "Limpar jogo",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (dialogResult == 0) {
                boardService.reset();
                notifierService.notify(CLEAR_SPACE);
            }
        });

        mainPanel.add(resetButton);
    }
}
