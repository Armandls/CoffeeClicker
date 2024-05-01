package Presentation.Controllers;
import Business.UserManager;
import Persistance.Exception.ConnectionErrorException;
import Presentation.FrameController;
import Presentation.Views.GameView;
import Presentation.Views.LoginView;
import Presentation.Views.StoresView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class GameController implements ActionListener {


    private FrameController frame;

    private GameView gameView;

    private UserManager userManager;

    public GameController(FrameController frame, UserManager userManager) throws IOException {
        this.frame = frame;
        this.userManager = userManager;
    }

    public void setView(GameView view) {
        this.gameView = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "profile":
                System.out.println("profile");
                gameView.stop();
                gameView.showProfile();
                break;

            case "config":
                System.out.println("config");
                gameView.stop();
                gameView.showConfig();
                break;

            case "phone":
                System.out.println("phone");
                gameView.toggleStore();
                break;

            case "click":
                System.out.println("click");
                gameView.increase();
                break;
            case "profileClose":
                System.out.println("profileClose");
                gameView.start();
            
                gameView.hideProfile();
                break;
            case "configClose":
                System.out.println("configClose");
                gameView.start();
                gameView.hideConfig();
                break;
            case "deleteAccount":
                System.out.println("Delete Account");
                try {
                    deleteAccount();
                } catch (ConnectionErrorException ex) {
                    throw new RuntimeException(ex);
                }
                frame.swapPanel("login");
                break;
            case "logout":
                System.out.println("Logout");
                logout();
                frame.swapPanel("login");
                break;
        }
    }

    void logout() {
        userManager.restartValuesUser();
    }

    void deleteAccount() throws ConnectionErrorException {
        userManager.deleteUser();
    }
}
