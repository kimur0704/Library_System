package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.LoginController;

public class LoginView extends JFrame {
    private JTextField idField;
    private JPasswordField passField;
    private JButton loginButton;
    private LoginController controller;

    public LoginView() { // 생성자
        setTitle("도서관 시스템"); // 타이틀 달기
        setSize(300, 200); // 프레임 크기 300x200 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임 종료버튼 클릭 시 응용프로그램 종료 설정
        setLocationRelativeTo(null); // 프레임을 화면 중앙에 출력
        
        setLayout(new BorderLayout(10, 10)); // 배치관리자를 BorderLayout으로 설정

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // 2x2 분할로 배치하는 컨테이너 생성
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        
        inputPanel.add(new JLabel("  ID:"));
        idField = new JTextField(); // 빈 텍스트필드 생성
        inputPanel.add(idField);

        inputPanel.add(new JLabel("  Password:"));
        passField = new JPasswordField(); 
        inputPanel.add(passField);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // 중앙 정렬하는 FlowLayout 컨테이너 생성
        loginButton = new JButton("로그인"); // 버튼 생성
        buttonPanel.add(loginButton); // 버튼을 컨테이너에 부착

        add(inputPanel, BorderLayout.CENTER); // 입력 컨테이너를 프레임의 CENTER에 부착
        add(buttonPanel, BorderLayout.SOUTH); // 버튼 컨테이너를 프레임의 SOUTH에 부착

        // 익명 클래스로 Action 이벤트 리스너 작성
        loginButton.addActionListener(new ActionListener() { // Action 리스너 달기
            @Override
            public void actionPerformed(ActionEvent e) { // Action 이벤트 발생시 호출됨
                if (controller != null) {
                    String id = idField.getText();
                    String password = new String(passField.getPassword());
                    controller.handleLogin(id, password);
                }
            }
        });
    }

    public void setController(LoginController controller) { // view와 controller 객체 연결
        this.controller = controller;
    }

    public void showMessage(String msg) { // 메시지 다이얼로그 출력
        JOptionPane.showMessageDialog(this, msg);
    }
}