package model;

import java.time.LocalDate;

public class Loan {
    // 대출 정보 (도서, 대출일, 반납 예정일, 반납 여부)
    private Borrowable item;
    private LocalDate loanDate;
    private LocalDate dueDate; // <-- 반납 예정일 추가
    private boolean isReturned;

    // 대출 당일(오늘)을 기준으로 14일의 반납 기한 설정
    public Loan(Borrowable item) {
        this.item = item;
        this.loanDate = LocalDate.now(); // 오늘 대출
        this.dueDate = this.loanDate.plusDays(14);
        this.isReturned = false;
    }

    // 대출 정보 조회 및 반납 상태 설정
    public Borrowable getItem() { return item; }
    public LocalDate getLoanDate() { return loanDate; }
    public LocalDate getDueDate() { return dueDate; }
    public boolean isReturned() { return isReturned; }
    public void setReturned(boolean returned) { this.isReturned = returned; }
}