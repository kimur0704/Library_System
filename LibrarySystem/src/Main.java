import model.LibraryManager;
import view.LoginView;
import controller.LoginController;

public class Main {
    public static void main(String[] args) { // 메인 메소드 시작

        LibraryManager model = new LibraryManager(); // Model 객체 생성
        
        LoginView loginView = new LoginView(); // View 객체 생성

        LoginController loginController = new LoginController(loginView, model); // Controller 객체 생성
        loginView.setController(loginController); // View와 Controller 연결

        loginView.setVisible(true); // 프레임을 화면에 출력
    }
}