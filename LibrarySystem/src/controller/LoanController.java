package controller;

import model.LibraryManager;
import model.User;
import model.Borrowable;
import view.LoanView;

public class LoanController {
    // 의존성 필드 (화면 View, 데이터 Model, 세션 User)
    private LoanView view;
    private LibraryManager model;
    private User currentUser; // 대출/반납의 주체가 되는 현재 사용자 세션 정보

    // 생성자: 화면(View), 데이터 창고(Model), 유저(User) 정보를 외부에서 주입받아 연결함
    public LoanController(LoanView view, LibraryManager model, User user) {
        this.view = view;
        this.model = model;
        this.currentUser = user;
    }

    // 현재 로그인한 사용자의 대출 현황 목록을 실시간으로 새로고침하여 화면에 표시함
    public void refreshLoanList() {
        if (currentUser != null && view != null) {
            // 현재 로그인한 유저가 빌린 책 목록 데이터를 가져와서, 대출 화면 표(테이블)에 최신 정보로 채워 넣음
            view.displayLoanedBooks(currentUser.getLoanedBooks());
        }
    }

    // [Create/Update] 도서 대출 요청 처리
    public void handleBorrow(String bookTitle) {
        // 1. 입력값 유효성 검증 (공백 입력 방지 및 Null 안정성 확보)
        if (bookTitle == null || bookTitle.trim().isEmpty()) {
            view.showMessage("대출할 도서 제목을 입력해주세요.");
            return;
        }

        try {
            // 2. 창고(Model)에서 제목으로 대출 가능한 아이템 조회 (다형성 활용: Borrowable 인터페이스 타입)
            Borrowable item = model.findBookByTitle(bookTitle.trim());

            if (item == null) {
                view.showMessage("도서 정보를 찾을 수 없습니다.");
                return;
            }

            if (item.isBorrowed()) {
                view.showMessage("이미 대출 중인 도서입니다.");
                return;
            }

            // 3. 비즈니스 로직 수행 (model에 현재 유저와 대출 대상 아이템을 넘겨 대출 처리 및 하위 레이어 예외 감시)
            boolean success = model.borrowBook(currentUser, item);

            // 4. 대출 결과에 따른 화면 제어 및 데이터 갱신
            if (success) {
                view.showMessage("'" + bookTitle.trim() + "' 도서가 성공적으로 대출되었습니다.");
                refreshLoanList(); // 대출 성공 반영을 위해 목록 새로고침 수행
            } else {
                view.showMessage("대출 처리에 실패했습니다.");
            }
        } catch (Exception e) {
            // [View 연동] 파일 시스템 오류 발생 시 콘솔 출력을 지양하고 Swing 팝업창으로 에러를 알림 (MVC 패턴 준수)
            view.showMessage("[파일 시스템 오류] 대출 처리 중 데이터 반영에 실패했습니다:\n" + e.getMessage());
        }
    }

    // [Delete/Update] 도서 반납 요청 처리
    public void handleReturn(String bookTitle) {
        // 1. 입력값 유효성 검증 (공백 입력 방지)
        if (bookTitle == null || bookTitle.trim().isEmpty()) {
            view.showMessage("반납할 도서 제목을 입력해주세요.");
            return;
        }

        try {
            // 2. 창고(Model)에서 반납할 아이템 정보 조회 (Borrowable 인터페이스 타입)
            Borrowable item = model.findBookByTitle(bookTitle.trim());

            if (item == null) {
                view.showMessage("도서 정보를 찾을 수 없습니다.");
                return;
            }

            // 3. 비즈니스 로직 수행 (model에 현재 유저와 반납 대상 아이템을 넘겨 반납 처리 및 하위 레이어 예외 감시)
            boolean success = model.returnBook(currentUser, item);

            // 4. 반납 결과에 따른 화면 제어 및 데이터 갱신
            if (success) {
                view.showMessage("'" + bookTitle.trim() + "' 도서가 성공적으로 반납되었습니다.");
                refreshLoanList(); // 반납 성공 반영을 위해 목록 새로고침 수행
            } else {
                view.showMessage("반납 처리에 실패했습니다. 본인이 대출한 내역이 맞는지 확인해주세요.");
            }
        } catch (Exception e) {
            // [View 연동] 파일 시스템 오류 발생 시 콘솔 출력을 지양하고 Swing 팝업창으로 에러를 알림 (MVC 패턴 준수)
            view.showMessage("[파일 시스템 오류] 반납 처리 중 데이터 반영에 실패했습니다:\n" + e.getMessage());
        }
    }

    // 창 닫기 및 메인 메뉴 화면 복귀 처리 (LoanView를 띄울 때 기존 MainMenuView를 백그라운드에 유지하였기에 가능함)
    public void handleBack() {
        if (view != null) {
            view.dispose(); // 현재 대출/반납 창을 메모리 및 화면에서 안전하게 제거함
        }
    }
}