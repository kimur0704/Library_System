package model;

public class Book implements Borrowable{
    // 도서 정보 (제목, 저자, 대출여부)
    private String title;
    private String author;
    private boolean isBorrowed;

    // 생성자: 초기 상태는 대출 가능인 false로 설정
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isBorrowed = false; // 초기 상태는 대출 가능
    }

    // 제목, 저자, 대출 상태 관리 
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    
    // 여러 스레드가 동시에 대출 상태를 읽는 동안 데이터 정합성을 유지하기 위해 synchronized 추가
    public synchronized boolean isBorrowed() { return isBorrowed; }

    // 대출 및 반납 상태 처리
    // 동시에 두 명의 사용자가 같은 책을 대출하려고 할 때 자원 충돌을 막기 위해 synchronized 추가
    public synchronized void borrowItem() { this.isBorrowed = true; } // 대출 상태로 변경하기
    
    // 반납과 대출 요청이 동시에 얽히는 것을 방지하기 위해 synchronized 추가
    public synchronized void returnItem() { this.isBorrowed = false; } // 반납 상태로 변경하기

   // 도서 정보 수정 (개별 setter 대신 멀티스레드 동기화를 적용한 통합 수정 메서드로 변경)
    public synchronized void updateInfo(String title, String author) {
        this.title = title;
        this.author = author;
    }
    
}