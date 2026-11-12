package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.Loan;
import controller.LoanController;

public class LoanView extends JFrame {
    private JTextField titleField;
    private JButton borrowBtn;
    private JButton returnBtn;
    private JButton backBtn;
    private JTable loanTable;
    private DefaultTableModel tableModel;
    private LoanController controller;

    public LoanView() { // 생성자
        setTitle("도서 대출 / 반납 시스템"); // 타이틀 달기
        setSize(550, 400); // 프레임 크기 550x400 설정
        setLocationRelativeTo(null); // 프레임을 화면 중앙에 출력
        setLayout(new BorderLayout()); // 배치관리자를 BorderLayout으로 설정

        JPanel inputPanel = new JPanel(); // 패널 생성 (기본 FlowLayout)
        inputPanel.add(new JLabel("도서 제목:"));
        titleField = new JTextField(15); // 15열 크기의 텍스트필드 생성
        borrowBtn = new JButton("대출"); // 버튼 생성
        returnBtn = new JButton("반납");
        
        inputPanel.add(titleField); // 텍스트필드를 컨테이너에 부착
        inputPanel.add(borrowBtn); // 버튼을 컨테이너에 부착
        inputPanel.add(returnBtn);
        add(inputPanel, BorderLayout.NORTH); // 입력 컨테이너를 프레임의 NORTH에 부착

        String[] columns = {"대출한 도서", "대출일", "반납 예정일"}; // 테이블 열 이름 배열 생성

        tableModel = new DefaultTableModel(columns, 0) { // 기본 테이블 모델 생성
            @Override
            public boolean isCellEditable(int row, int column) { // 셀 편집 여부 설정
                return false; // 셀 내용 편집이 불가하도록 설정
            }
        };
        
        loanTable = new JTable(tableModel); // 테이블 생성
        add(new JScrollPane(loanTable), BorderLayout.CENTER); // 스크롤팬에 테이블을 담아 프레임의 CENTER에 부착

        JPanel bottomPanel = new JPanel();
        backBtn = new JButton("메인 메뉴로 돌아가기");
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH); // 버튼 컨테이너를 프레임의 SOUTH에 부착

        // 익명 클래스로 Action 이벤트 리스너 작성
        borrowBtn.addActionListener(new ActionListener() { // Action 리스너 달기
            @Override
            public void actionPerformed(ActionEvent e) { // Action 이벤트 발생시 호출됨
                if (controller != null) controller.handleBorrow(titleField.getText());
            }
        });

        returnBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller != null) controller.handleReturn(titleField.getText());
            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller != null) controller.handleBack();
            }
        });
    }

    public void setController(LoanController controller) {
        this.controller = controller;
    }

    public void displayLoanedBooks(List<Loan> loans) { // 대출 목록 갱신
        tableModel.setRowCount(0); // 테이블 초기화
        for (Loan loan : loans) {
            tableModel.addRow(new Object[] { // 테이블에 행 추가
                loan.getItem().getTitle(), 
                loan.getLoanDate().toString(),
                loan.getDueDate().toString()
            });
        }
    }

    public void showMessage(String msg) { // 메시지 다이얼로그 출력
        JOptionPane.showMessageDialog(this, msg);
    }
}