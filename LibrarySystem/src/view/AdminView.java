package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import controller.AdminController;

public class AdminView extends JFrame {
    
    // 도서 관리용 UI 컴포넌트 선언
    private JTextField bookTitleField;
    private JTextField bookAuthorField;
    private JTextField newBookTitleField;
    private JTextField newBookAuthorField;
    private JButton addBookBtn;
    private JButton removeBookBtn;
    private JButton updateBookBtn;

    // 회원 관리용 UI 컴포넌트 선언
    private JTextField userNumField;
    private JTextField userIdField;
    private JTextField userPwField;
    private JTextField userNameField;
    private JButton searchUserBtn;

    // 하단 제어 버튼 및 컨트롤러 객체 선언
    private JButton backBtn;
    private AdminController controller;

    public AdminView() { // 생성자
        setTitle("도서 및 회원 관리자 시스템"); // 타이틀 달기
        setSize(500, 520); // 프레임 크기 500x520 설정
        setLocationRelativeTo(null); // 프레임을 화면 중앙에 출력
        setLayout(new BorderLayout(10, 10)); // 배치관리자를 BorderLayout으로 설정

        // 메인 탭 그리드 패널 구성
        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 10, 10)); // 2x1 분할 컨테이너 생성
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 패널 여백 설정

        // 도서 관리 섹션 레이아웃 구성
        JPanel bookPanel = new JPanel(new GridLayout(5, 2, 5, 5)); // 5x2 분할 컨테이너 생성
        bookPanel.setBorder(BorderFactory.createTitledBorder("도서 관리 (추가 / 삭제 / 수정)")); // 타이틀 테두리 설정
        
        bookPanel.add(new JLabel("도서 제목:"));
        bookTitleField = new JTextField(); // 빈 텍스트필드 생성
        bookPanel.add(bookTitleField); // 텍스트필드를 컨테이너에 부착
        
        bookPanel.add(new JLabel("도서 저자:"));
        bookAuthorField = new JTextField();
        bookPanel.add(bookAuthorField);

        bookPanel.add(new JLabel("수정용 새 제목:"));
        newBookTitleField = new JTextField();
        bookPanel.add(newBookTitleField);

        bookPanel.add(new JLabel("수정용 새 저자:"));
        newBookAuthorField = new JTextField();
        bookPanel.add(newBookAuthorField);
        
        addBookBtn = new JButton("추가"); // 버튼 생성
        removeBookBtn = new JButton("삭제");
        updateBookBtn = new JButton("수정");
        
        JPanel bookBtnPanel = new JPanel(new GridLayout(1, 3, 5, 5)); // 1x3 분할 컨테이너 생성
        bookBtnPanel.add(addBookBtn); // 버튼을 컨테이너에 부착
        bookBtnPanel.add(removeBookBtn);
        bookBtnPanel.add(updateBookBtn);
        
        bookPanel.add(new JLabel("도서 작업:"));
        bookPanel.add(bookBtnPanel);

        // 회원 관리 섹션 레이아웃 구성
        JPanel userPanel = new JPanel(new GridLayout(5, 2, 5, 5)); // 5x2 분할 컨테이너 생성
        userPanel.setBorder(BorderFactory.createTitledBorder("회원 정보 조회"));
        
        userPanel.add(new JLabel("회원 번호 (숫자):"));
        userNumField = new JTextField();
        userPanel.add(userNumField);
        
        userPanel.add(new JLabel("회원 ID:"));
        userIdField = new JTextField();
        userPanel.add(userIdField);
        
        userPanel.add(new JLabel("비밀번호:"));
        userPwField = new JTextField();
        userPanel.add(userPwField);
        
        userPanel.add(new JLabel("회원 이름:"));
        userNameField = new JTextField();
        userPanel.add(userNameField);

        searchUserBtn = new JButton("회원 조회");
        userPanel.add(new JLabel("회원 ID 입력 후 조회:"));
        userPanel.add(searchUserBtn);

        mainPanel.add(bookPanel); // 도서 패널을 메인 패널에 부착
        mainPanel.add(userPanel); // 회원 패널을 메인 패널에 부착
        add(mainPanel, BorderLayout.CENTER); // 메인 컨테이너를 프레임의 CENTER에 부착

        // 하단 복귀 패널 구성
        JPanel bottomPanel = new JPanel(); // 하단 패널 생성
        backBtn = new JButton("메인 메뉴로 돌아가기");
        bottomPanel.add(backBtn); // 뒤로가기 버튼 부착
        add(bottomPanel, BorderLayout.SOUTH); // 하단 컨테이너를 프레임의 SOUTH에 부착

        // 익명 클래스로 Action 이벤트 리스너 작성 (도서 관리)
        addBookBtn.addActionListener(new ActionListener() { // Action 리스너 달기 (도서 추가)
            @Override
            public void actionPerformed(ActionEvent e) { // Action 이벤트 발생시 호출됨
                if (controller != null) controller.handleAddBook(bookTitleField.getText(), bookAuthorField.getText());
            }
        });

        removeBookBtn.addActionListener(new ActionListener() { // Action 리스너 달기 (도서 삭제)
            @Override
            public void actionPerformed(ActionEvent e) { 
                if (controller != null) controller.handleRemoveBook(bookTitleField.getText());
            }
        });

        updateBookBtn.addActionListener(new ActionListener() { // Action 리스너 달기 (도서 수정)
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller != null) controller.handleUpdateBook(bookTitleField.getText(), newBookTitleField.getText(), newBookAuthorField.getText());
            }
        });

        // 익명 클래스로 Action 이벤트 리스너 작성 (회원 관리)
        searchUserBtn.addActionListener(new ActionListener() { // Action 리스너 달기 (회원 조회)
            @Override
            public void actionPerformed(ActionEvent e) { 
                if (controller != null) controller.handleSearchUser(userIdField.getText());
            }
        });

        // 뒤로가기 이벤트 바인딩
        backBtn.addActionListener(new ActionListener() { // Action 리스너 달기 (뒤로가기)
            @Override
            public void actionPerformed(ActionEvent e) { 
                if (controller != null) controller.handleBack();
            }
        });
    }

    public void setController(AdminController controller) { // 뷰와 컨트롤러 객체 연결
        this.controller = controller; 
    }
    
    public void showMessage(String msg) { 
        JOptionPane.showMessageDialog(this, msg); // 메시지 다이얼로그 출력
    }

    public void setUserFields(String userNum, String pw, String name) { // 조회 결과 데이터를 텍스트필드에 출력
        userNumField.setText(userNum);
        userPwField.setText(pw);
        userNameField.setText(name);
    }
    
    public void clearFields() { // 입력 텍스트필드 초기화
        bookTitleField.setText(""); bookAuthorField.setText("");
        newBookTitleField.setText(""); newBookAuthorField.setText("");
        userNumField.setText(""); userIdField.setText(""); userPwField.setText(""); userNameField.setText("");
    }
}