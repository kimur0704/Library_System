package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.MainController;

public class MainMenuView extends JFrame {
    private JButton searchMenuBtn;
    private JButton loanMenuBtn;
    private JButton adminMenuBtn;
    private MainController controller;

    public MainMenuView() { // 생성자
        setTitle("메인 메뉴"); // 타이틀 달기
        setSize(480, 150); // 프레임 크기 480x150 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임 종료버튼 클릭 시 응용프로그램 종료 설정
        setLocationRelativeTo(null); // 프레임을 화면 중앙에 출력
        setLayout(new FlowLayout()); // 배치관리자를 FlowLayout으로 설정

        searchMenuBtn = new JButton("도서 검색"); // 버튼 생성
        loanMenuBtn = new JButton("대출 / 반납 관리");
        adminMenuBtn = new JButton("도서/회원 관리(관리자)"); 

        add(searchMenuBtn); // 버튼을 프레임에 부착
        add(loanMenuBtn);
        add(adminMenuBtn); 

        // 익명 클래스로 Action 이벤트 리스너 작성
        searchMenuBtn.addActionListener(new ActionListener() { // Action 리스너 달기
            @Override
            public void actionPerformed(ActionEvent e) { // Action 이벤트 발생시 호출됨
                if (controller != null) controller.navigateToSearch();
            }
        });

        loanMenuBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller != null) controller.navigateToLoan();
            }
        });

        adminMenuBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller != null) controller.navigateToAdmin();
            }
        });
    }

    public void setController(MainController controller) { // 컨트롤러 연결
        this.controller = controller;
    }

    // 관리자 버튼이 일반 회원에게 안 뜨게 숨겨주는 메서드
    public void hideAdminButton() {
        if (adminMenuBtn != null) {
            adminMenuBtn.setVisible(false);
        }
    }
}