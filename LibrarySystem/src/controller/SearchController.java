package controller;

import model.Book;
import model.LibraryManager;
import view.SearchView;
import java.util.List;

public class SearchController {
    private SearchView view;
    private LibraryManager model;

    // 생성자: 필요한 검색 화면(View)과 도서 창고(Model)를 외부에서 주입받아 연결
    public SearchController(SearchView view, LibraryManager model) {
        this.view = view;
        this.model = model;
    }

    // 사용자의 도서 검색 요청 처리 (제목 및 저자 기준)
    public void handleSearch(String keyword) {
        // 1. 입력값 유효성 검증 (공백 입력 방지 + Null 안정성 확보)
        if (keyword == null || keyword.trim().isEmpty()) {
            view.showMessage("검색어를 입력해주세요.");
            return;
        }

        try {
            // 2. 데이터 창고(Model)의 검색 기능을 호출하여 결과 목록(List)을 인계
            List<Book> searchResult = model.searchBook(keyword.trim());

            // 3. 검색 결과 유무에 따른 화면(View) 업데이트
            if (searchResult.isEmpty()) {
                view.showMessage("검색 결과가 없습니다.");
                view.clearTable(); // 테이블에 남아있던 이전 검색 결과 데이터 초기화
            } else {
                // 검색된 도서 리스트를 화면의 테이블에 실시간 출력
                view.updateBookTable(searchResult); 
            }
        } catch (Exception e) {
            // 시스템 예외 발생 시 프로그램 강제 종료 방지 및 에러 안내
            view.showMessage("검색 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 창 닫기 및 메인 메뉴 화면 복귀 처리 (SearchView를 띄울 때 기존 MainMenuView를 백그라운드에 유지하였기에 가능)
    public void handleBack() {
        if (view != null) {
            view.dispose(); // 현재 검색 창을 메모리 및 화면에서 안전하게 제거
        }
    }
}