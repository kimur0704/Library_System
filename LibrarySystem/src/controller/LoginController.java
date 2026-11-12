package controller;

// 코드의 명확성을 높이고 이름 충돌을 방지하기 위해 *(와일드카드) 임포트 대신 명시적으로 임포트
import model.LibraryManager;
import model.User;
import view.LoginView;
import view.MainMenuView;

public class LoginController {
    // 컨트롤러가 뷰와 모델을 중재하기 위한 핵심 연결 고리 (정보 은닉)
    private LoginView view;
    private LibraryManager model;

    // 생성자: 필요한 View와 Model을 외부에서 주입받음 (의존성 주입)
    public LoginController(LoginView view, LibraryManager model) {
        this.view = view;
        this.model = model;
    }

    // 사용자 로그인 요청 처리
    public void handleLogin(String id, String password) {
        // 1. 입력값 공백 검증 (유효성 체크)
        if (id.isEmpty() || password.isEmpty()) {
            view.showMessage("ID와 비밀번호를 모두 입력해주세요.");
            return;
        }

        try {
            // 2. Model에서 입력된 ID로 회원 정보 조회 (메서드명: FindUserById)
            User user = model.findUserById(id);

            // 3. 회원 존재 여부 및 비밀번호 일치 확인
            if (user != null && user.getPassword().equals(password)) {
                view.showMessage(user.getName() + "님, 환영합니다!");
                view.dispose(); // 로그인 창 닫기
                
                // 4. 메인 메뉴 화면 전환 및 데이터 인계
                MainMenuView mainView = new MainMenuView();
                // 다음 컨트롤러에 공통 모델과 현재 로그인한 유저 정보 전달
                MainController mainController = new MainController(mainView, model, user);
                
                // 메인 화면의 각 메뉴 버튼 클릭 시, 메인 컨트롤러가 화면을 라우팅(전환)하도록 연결
                mainView.setController(mainController);
                mainView.setVisible(true); // 메인 화면 표시
                
            } else {
                view.showMessage("ID 또는 비밀번호가 일치하지 않습니다.");
            }
        } catch (Exception e) {
            // 예외 발생 시 시스템 다운 방지 및 안내
            view.showMessage("로그인 처리 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}