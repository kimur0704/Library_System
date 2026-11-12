package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.Book;
import model.Borrowable;
import controller.SearchController;

public class SearchView extends JFrame {
    private JTextField searchField;
    private JButton searchBtn;
    private JButton backBtn; 
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private SearchController controller;

    public SearchView() { // 생성자
        setTitle("도서 검색 화면"); // 타이틀 달기
        setSize(450, 350); // 프레임 크기 450x350 설정
        setLocationRelativeTo(null); // 프레임을 화면 중앙에 출력
        setLayout(new BorderLayout()); // 배치관리자를 BorderLayout으로 설정

        JPanel topPanel = new JPanel(); // 검색 상단 패널 생성
        topPanel.add(new JLabel("검색어:"));
        searchField = new JTextField(15); // 15열 크기의 텍스트필드 생성
        searchBtn = new JButton("검색"); // 버튼 생성
        
        topPanel.add(searchField); // 텍스트필드를 컨테이너에 부착
        topPanel.add(searchBtn); // 버튼을 컨테이너에 부착
        add(topPanel, BorderLayout.NORTH); // 상단 컨테이너를 프레임의 NORTH에 부착

        String[] columns = {"도서 제목", "저자", "대출 가능 여부"}; // 테이블 열 이름 배열 생성
        
        tableModel = new DefaultTableModel(columns, 0) { // 기본 테이블 모델 생성
            @Override
            public boolean isCellEditable(int row, int column) { // 셀 편집 여부 설정
                return false; // 셀 내용 편집 불가 설정
            }
        };
        
        resultTable = new JTable(tableModel); // 테이블 생성
        add(new JScrollPane(resultTable), BorderLayout.CENTER); // 스크롤팬에 테이블을 담아 프레임의 CENTER에 부착

        JPanel bottomPanel = new JPanel(); // 하단 패널 생성
        backBtn = new JButton("메인 메뉴로 돌아가기"); // 버튼 생성
        bottomPanel.add(backBtn); // 버튼을 컨테이너에 부착
        add(bottomPanel, BorderLayout.SOUTH); // 하단 컨테이너를 프레임의 SOUTH에 부착

        // 익명 클래스로 Action 이벤트 리스너 작성
        searchBtn.addActionListener(new ActionListener() { // Action 리스너 달기
            @Override
            public void actionPerformed(ActionEvent e) { // Action 이벤트 발생시 호출됨
                if (controller != null) controller.handleSearch(searchField.getText());
            }
        });

        backBtn.addActionListener(new ActionListener() { // Action 리스너 달기
            @Override
            public void actionPerformed(ActionEvent e) { // Action 이벤트 발생시 호출됨
                if (controller != null) controller.handleBack(); 
            }
        });
    }

    public void setController(SearchController controller) { // 컨트롤러 연결
        this.controller = controller;
    }

    public void updateBookTable(List<? extends Borrowable> books) { // 검색 결과 테이블 갱신
        tableModel.setRowCount(0); // 테이블 초기화
        for (Borrowable item : books) {
            String status = item.isBorrowed() ? "대출 중" : "대출 가능";
            
            // 만약 구체 클래스가 Book이라면 저자 정보까지 가져오고, 아니라면 알 수 없음 처리
            String author = "알 수 없음";
            if (item instanceof Book) {
                author = ((Book) item).getAuthor();
            }
            
            tableModel.addRow(new Object[]{item.getTitle(), author, status}); // 테이블에 행 추가
        }
    }

    public void clearTable() { 
        tableModel.setRowCount(0); // 테이블 초기화
    }
    
    public void showMessage(String msg) { // 메시지 다이얼로그 출력
        JOptionPane.showMessageDialog(this, msg);
    }
}