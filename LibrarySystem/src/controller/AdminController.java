package controller;

import model.Book;
import model.LibraryManager;
import model.User;
import view.AdminView;

public class AdminController {
    // 의존성 필드 (화면 View, 데이터 Model)
    private AdminView view;
    private LibraryManager model;

    // 생성자: 제어에 필요한 관리자 뷰와 전체 데이터 창고 모델을 주입받아 연결
    public AdminController(AdminView view, LibraryManager model) {
        this.view = view;
        this.model = model;
    }

    // [Create] 새로운 도서 추가 요청 처리
    public void handleAddBook(String title, String author) {
        // 1. 입력값 유효성 검증 (공백 방지)
        if (title.trim().isEmpty() || author.trim().isEmpty()) {
            view.showMessage("도서 제목과 저자를 모두 입력해주세요.");
            return;
        }
        
        try {
            // 2. 입력받은 정보로 새 Book 객체를 생성하여 model에 등록 (하위 레이어 예외 감시)
            model.addBook(new Book(title.trim(), author.trim()));
            
            // 3. 완료 알림 및 입력 필드 초기화
            view.showMessage("'" + title.trim() + "' 도서가 도서관에 등록되었습니다.");
            view.clearFields();
        } catch (Exception e) {
            // [View 연동] 파일 시스템 오류 발생 시 콘솔 출력을 지양하고 Swing 팝업창으로 에러를 알림 (MVC 패턴 준수)
            view.showMessage("[파일 시스템 오류] 도서 등록 중 데이터 저장에 실패했습니다:\n" + e.getMessage());
        }
    }

    // [Delete] 기존 도서 삭제 요청 처리
    public void handleRemoveBook(String title) {
        // 1. 입력값 유효성 검증
        if (title.trim().isEmpty()) {
            view.showMessage("삭제할 도서 제목을 입력해주세요.");
            return;
        }
        
        try {
            // 2. 창고(Model)에서 제목으로 삭제 연산 수행 후 성공 여부 반환
            boolean success = model.removeBook(title.trim());
            
            // 3. 결과에 따른 화면 제어
            if (success) {
                view.showMessage("'" + title.trim() + "' 도서가 성공적으로 삭제되었습니다.");
                view.clearFields(); // 입력 필드 초기화
            } else {
                view.showMessage("해당 도서를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            // [View 연동] 데이터 반영 실패 시 팝업창으로 에러 알림
            view.showMessage("[파일 시스템 오류] 도서 삭제 중 데이터 저장에 실패했습니다:\n" + e.getMessage());
        }
    }

    // [Update] 도서 정보(제목, 저자) 수정 요청 처리
    public void handleUpdateBook(String originTitle, String newTitle, String newAuthor) {
        // 1. 입력값 유효성 검증
        if (originTitle.trim().isEmpty() || newTitle.trim().isEmpty() || newAuthor.trim().isEmpty()) {
            view.showMessage("기존 도서 제목, 새 제목, 새 저자를 모두 입력해주세요.");
            return;
        }
        
        try {
            // 2. 창고(Model)에 대상 도서 제목과 변경할 정보를 넘겨 수정 처리
            boolean success = model.updateBook(originTitle.trim(), newTitle.trim(), newAuthor.trim());
            
            // 3. 결과에 따른 화면 제어
            if (success) {
                view.showMessage("'" + originTitle.trim() + "' 도서 정보가 성공적으로 수정되었습니다.");
                view.clearFields(); // 입력 필드 초기화
            } else {
                view.showMessage("해당 도서를 찾을 수 없어 수정에 실패했습니다.");
            }
        } catch (Exception e) {
            // [View 연동] 데이터 반영 실패 시 팝업창으로 에러 알림
            view.showMessage("[파일 시스템 오류] 도서 정보 수정 중 데이터 저장에 실패했습니다:\n" + e.getMessage());
        }
    }

    // [Read] 특정 회원 정보 조회 요청 처리
    public void handleSearchUser(String id) {
        // 1. 입력값 유효성 검증
        if (id.trim().isEmpty()) {
            view.showMessage("조회할 회원의 ID를 입력해주세요.");
            return;
        }
        
        // 2. 창고(Model)에서 ID로 회원 객체 조회 (회원 조회의 경우 파일 저장을 하지 않으므로 기존 로직 유지)
        User user = model.findUserById(id.trim());
        
        // 3. 회원 존재 여부에 따라 화면 제어 및 데이터 표시
        if (user != null) {
            // 조회가 완료되면 해당 회원의 정보를 화면의 각 텍스트 필드에 자동으로 채워줌
            view.setUserFields(String.valueOf(user.getUserNumber()), user.getPassword(), user.getName());
            view.showMessage("회원 정보 조회가 완료되었습니다.");
        } else {
            view.showMessage("해당 ID의 회원을 찾을 수 없습니다.");
            view.clearFields();
        }
    }

    // 창 닫기 및 메인 메뉴 화면 복귀 처리 (AdminView를 띄울 때 기존 MainMenuView를 백그라운드에 유지하였기에 가능)
    public void handleBack() {
        if (view != null) {
            view.dispose(); // 현재 관리자 창을 메모리 및 화면에서 안전하게 제거
        }
    }
}