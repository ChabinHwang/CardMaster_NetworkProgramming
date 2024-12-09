package src.cardmaster.main_signup_login;

import src.cardmaster.Baccarat_game.Baccarat;
import src.cardmaster.Casinowar_game.Casinowar;
import src.cardmaster.blackjack_game.Blackjack;
import src.cardmaster.chat.ChatPanel;
import src.cardmaster.main_menu.MenuFrame;
import src.cardmaster.main_menu.MenuPanel;
import src.cardmaster.main_signup_login.handler.*;

import javax.accessibility.Accessible;
import javax.swing.*;
import java.awt.event.ActionListener;

public class ClientUI extends JFrame {
    static public ClientNetwork net;
    String ip;
    public ClientUI(String ip){
        this.ip = ip;
        setUIcomponent();
        addListners();
        net = new ClientNetwork(this);
    }
    public SignUpScreen signUp;
    public HomeScreen homeScreen;
    public LoginScreen loginScreen;
    public MenuFrame menuFrame;
    public Casinowar casinowar;
    public Blackjack blackjack;
    public Baccarat baccarat;

    private void addListners(){
        ActionListener blh = new BtLoginHandler(this);
        homeScreen.loginButton.addActionListener(blh);

        ActionListener bsh = new BtSignUpHandler(this);
        homeScreen.signupButton.addActionListener(bsh);

        ActionListener bleh = new BtLoginEnterHandler(this);
        loginScreen.loginButton.addActionListener(bleh);

        ActionListener blch = new BtLoginCancelHandler(this);
        loginScreen.backButton.addActionListener(blch);

        ActionListener bsfh = new BtSignUpFInishHandler(this);
        signUp.signUpButton.addActionListener(bsfh);

        ActionListener bsch = new BtSignUpCancelHandler(this);
        signUp.backButton.addActionListener(bsch);

        ActionListener bebj = new BtEnterBlackJackHandler(this);
        menuFrame.menuPanel.blackjackCard.joinButton.addActionListener(bebj);

        ActionListener beb = new BtEnterBaccaratHandler(this);
        menuFrame.menuPanel.baccaratCard.joinButton.addActionListener(beb);

        ActionListener bec = new BtEnterCasinoHandler(this);
        menuFrame.menuPanel.casinowarCard.joinButton.addActionListener(bec);

        ActionListener bs = new BtStartHandler(this);
        casinowar.startButton.addActionListener(bs);
        blackjack.startButton.addActionListener(bs);
        baccarat.startButton.addActionListener(bs);

        ActionListener bbjb = new BtBlackJackBetHandler(this);
        blackjack.betButton.addActionListener(bbjb);
        ActionListener bcb = new BtCasinoWarBetHandler(this);
        casinowar.betButton.addActionListener(bcb);
        ActionListener bbb = new BtBaccaratBetHandler(this);
        baccarat.betButton.addActionListener(bbb);

        ActionListener bbjc = new BtBlackJackChatHandler(this);
        ((ChatPanel)blackjack.chatPanel).sendButton.addActionListener(bbjc);
        ActionListener bbc = new BtBaccaratChatHandler(this);
        ((ChatPanel)baccarat.chatPanel).sendButton.addActionListener(bbc);
        ActionListener bcc = new BtCasinoChatHandler(this);
        ((ChatPanel)casinowar.chatPanel).sendButton.addActionListener(bcc);

        ActionListener bbjh = new BtBlackJackHitHandler(this);
        blackjack.hitButton.addActionListener(bbjh);
        ActionListener bbjs = new BtBlackJackStandHandler(this);
        blackjack.standButton.addActionListener(bbjs);

        ActionListener bcwe = new BtCasinoWarEnterHandler(this);
        casinowar.warButton.addActionListener(bcwe);
        ActionListener bcsh = new BtCasinoWarSurrenderHandler(this);
        casinowar.surrenderButton.addActionListener(bcsh);

        ActionListener bbqh = new BtBaccaratQuitHandler(this);
        baccarat.quitButton.addActionListener(bbqh);
        ActionListener bbjqh = new BtBlackJackQuitHandler(this);
        blackjack.quitButton.addActionListener(bbjqh);
        ActionListener bcwqh = new BtCasinoWarQuitHandler(this);
        casinowar.quitButton.addActionListener(bcwqh);
    }

    private void setUIcomponent(){
        setTitle("main");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        homeScreen = new HomeScreen();
        signUp = new SignUpScreen();
        loginScreen = new LoginScreen();
        menuFrame = new MenuFrame();
        casinowar = new Casinowar();
        blackjack = new Blackjack();
        baccarat = new Baccarat();
        setContentPane(homeScreen);
    }
}
