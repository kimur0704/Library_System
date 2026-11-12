package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    // 회원 정보 및 대출 목록
    private int userNumber;
    private String id;
    private String password;
    private String name;
    private List<Loan> loanedBooks; // 현재 대출 중인 도서 목록

    // 회원 정보 설정 및 대출 리스트 초기화
    public User(int userNumber, String id, String password, String name) {
        this.userNumber = userNumber;   // 회원번호
        this.id = id;                   // 아이디
        this.password = password;       // 비밀번호
        this.name = name;               // 이름
        this.loanedBooks = new ArrayList<>();
    }

    // 회원 정보 관리
    public int getUserNumber() { return userNumber; }
    public String getId() { return id; }
    public String getPassword() { return password; }
    public String getName() { return name; }

    // 외부에서 대출 리스트 조작하지 못하도록 캡슐화하여 보호
    public List<Loan> getLoanedBooks() { 
        return Collections.unmodifiableList(loanedBooks); 
    }

    // 회원별 도서 대출/반납 목록 추가 및 삭제
    public void addLoanBook(Loan loan) {
        this.loanedBooks.add(loan);
    }

    public void removeLoanBook(Borrowable item) {
        this.loanedBooks.removeIf(loan -> loan.getItem().getTitle().equals(item.getTitle()));
    }
    
}