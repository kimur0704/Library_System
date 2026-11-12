package model;

// 대출 가능한 모든 아이템의 공통 행위를 정의한 인터페이스 (다형성 및 확장성 확보; Book에 국한되지 않도록 추상화)
public interface Borrowable {
    void borrowItem();    // 대출 처리
    void returnItem();    // 반납 처리
    boolean isBorrowed(); // 대출 여부 확인
    String getTitle();    // 대출 대상 제목
}