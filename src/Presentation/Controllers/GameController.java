package Presentation.Controllers;
import Presentation.FrameController;
import Presentation.Views.GameView;
import Presentation.Views.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameController implements ActionListener {


    private FrameController frame;

    private GameView gameView;

    public GameController(FrameController frame) {
        this.frame = frame;
    }

    public void setView(GameView view) {
        this.gameView = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "profile":
                System.out.println("profile");
                break;

            case "config":
                System.out.println("config");
                break;

            case "phone":
                System.out.println("phone");
                break;

            case "click":
                System.out.println("click");
                gameView.increase();
                break;
        }
    }


}
