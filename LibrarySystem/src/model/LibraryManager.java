package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LibraryManager {
    // 도서 및 회원 목록 관리를 위한 의존성 필드 선언
    private List<Borrowable> books;
    private List<User> users;
    private FileManager fileManager;

    // 생성자: 리스트 초기화 및 초기 데이터 파일 저장 수행
    public LibraryManager() {
        books = new ArrayList<>();
        users = new ArrayList<>();
        fileManager = new FileManager();
        
        // 도서 더미 데이터
        books.add(new Book("자바의 정석", "남궁성"));
        books.add(new Book("객체지향의 사실과 오해", "조영호"));
        // user 더미 데이터
        users.add(new User(1001, "admin", "1234", "관리자"));
        users.add(new User(1, "name", "0000", "철수"));
        
        // 초기 구동 시 더미 데이터를 파일에 최초 저장함
        try {
            fileManager.saveData(books);
        } catch (IOException e) {
            throw new RuntimeException("[시스템 치명적 오류] 초기 데이터 파일 저장 실패", e);
        }
    }

    // 제목 및 저자 기반 도서 검색 기능
    public List<Book> searchBook(String keyword) {
        List<Book> result = new ArrayList<>();
        for (Borrowable item : books) {
            if (item instanceof Book) {
                Book book = (Book) item;
                if (book.getTitle().contains(keyword) || book.getAuthor().contains(keyword)) {
                    result.add(book);
                }
            }
        }
        return result;
    }

    // 제목 기반 단일 도서 검색 기능
    public Borrowable findBookByTitle(String title) {
        for (Borrowable item : books) {
            if (item.getTitle().equals(title)) {
                return item;
            }
        }
        return null;
    }

    // ID 기반 특정 회원 조회 기능
    public User findUserById(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    // 도서 대출 처리 및 파일 시스템 동기화 기능
    // 상위 컨트롤러 레이어에서 예외 처리를 전담하도록 제어권을 위임
    public boolean borrowBook(User user, Borrowable item) throws IOException {
        if (!item.isBorrowed()) {
            item.borrowItem();
            if (item instanceof Book) {
                user.addLoanBook(new Loan(item));
            }
            fileManager.saveData(books); // 변경 사항 반영을 위해 파일 쓰기 즉시 동기화
            return true;
        }
        return false;
    }

    // 도서 반납 처리 및 파일 시스템 동기화 기능
    // 상위 컨트롤러 레이어에서 예외 처리를 전담하도록 제어권을 위임
    public boolean returnBook(User user, Borrowable item) throws IOException {
        if (item.isBorrowed()) {
            item.returnItem();
            if (item instanceof Book) {
                user.removeLoanBook((Book) item);
            }
            fileManager.saveData(books); // 변경 사항 반영을 위해 파일 쓰기 즉시 동기화
            return true;
        }
        return false;
    }

    // 새로운 도서 추가 및 파일 시스템 동기화 기능
    // 파일 I/O 예외를 상위 레이어로 위임
    public void addBook(Borrowable item) throws IOException {
        books.add(item);
        fileManager.saveData(books); // 도서 추가 상태를 데이터 파일에 동기화
    }

    // 기존 도서 삭제 및 파일 시스템 동기화 기능
    // 파일 I/O 예외를 상위 레이어로 위임
    public boolean removeBook(String title) throws IOException {
        Borrowable item = findBookByTitle(title);
        if (item != null) {
            books.remove(item);
            fileManager.saveData(books); // 도서 삭제 상태를 데이터 파일에 동기화
            return true;
        }
        return false;
    }   

    // 도서 정보 수정 및 파일 시스템 동기화 기능
    // 파일 I/O 예외를 상위 레이어로 위임함
    public boolean updateBook(String originTitle, String newTitle, String newAuthor) throws IOException {
        Borrowable item = findBookByTitle(originTitle);
        if (item != null && item instanceof Book) {
            Book book = (Book) item;
            book.updateInfo(newTitle, newAuthor);
            fileManager.saveData(books); // 도서 정보 변경 상태를 데이터 파일에 동기화
            return true;
        }
        return false;
    }
}