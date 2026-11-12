package controller;

import model.LibraryManager;
import model.User;
import view.MainMenuView;
import view.SearchView;
import view.LoanView;
import view.AdminView;

public class MainController {
    private MainMenuView view;
    private LibraryManager model;
    private User currentUser; // 로그인 성공 후 인계받은 현재 사용자 세션 정보

    // 생성자: 상위(Login)로부터 메인 뷰, 공통 모델, 유저 정보를 주입받아 연결
    public MainController(MainMenuView view, LibraryManager model, User user) {
        this.view = view;
        this.model = model;
        this.currentUser = user;
        
        // 메인 뷰와 메인 컨트롤러를 상호 연결하여 화면의 버튼 이벤트 수신 통로 개설
        this.view.setController(this);
        
        // [권한 제어] 현재 로그인한 사용자가 관리자(admin)가 아니면 화면에서 관리자 버튼을 숨김
        if (currentUser != null && !"admin".equals(currentUser.getId())) {
            this.view.hideAdminButton();
        }
    }

    // 도서 검색 화면으로 전환 (라우팅)
    public void navigateToSearch() {
        SearchView searchView = new SearchView();
        // 검색 기능은 유저 정보가 필요 없으므로 데이터 창고(model)만 인계
        SearchController searchController = new SearchController(searchView, model);
        
        searchView.setController(searchController); // SearchController와 연결
        searchView.setVisible(true);
    }

    // 도서 대출/반납 화면으로 전환 (라우팅)
    public void navigateToLoan() {
        LoanView loanView = new LoanView();
        // 대출/반납은 대상자가 필요하므로 현재 세션 유저(currentUser)를 함께 인계
        LoanController loanController = new LoanController(loanView, model, currentUser);
        
        loanView.setController(loanController); // loanController와 연결
        loanController.refreshLoanList(); // 대출 창이 열리기 전에 기존 대출 현황 실시간 갱신
        loanView.setVisible(true);
    }

    // 도서 및 회원 관리자 화면으로 전환 (라우팅)
    public void navigateToAdmin() {
        AdminView adminView = new AdminView();
        // 전체 시스템 제어를 위해 공통 데이터 창고(model) 인계
        AdminController adminController = new AdminController(adminView, model);
        
        adminView.setController(adminController); // adminController와 연결
        adminView.setVisible(true);
    }
}