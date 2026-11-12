package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileManager {
    
    //파일 입출력을 통한 데이터 저장 기능
    //예외 발생 시 상위 레이어(Controller)로 위임
    public void saveData(List<Borrowable> books) throws IOException {
        String fileName = "library_data.txt";
        
        // BufferedWriter와 FileWriter를 사용하여 동적 데이터를 문자열 단위로 기록
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("--- 도서관 시스템 데이터 저장 로그 ---\n");
            
            // 시스템에 존재하는 실제 도서 데이터를 규칙적으로 파일에 기록
            for (Borrowable item : books) {
                if (item instanceof Book) {
                    Book book = (Book) item;
                    // 제목/저자/대출상태 포맷으로 행 분리 저장
                    writer.write(book.getTitle() + "/" + book.getAuthor() + "/" + book.isBorrowed() + "\n");
                }
            }
        } 
    }

    //파일 시스템에 저장된 원본 데이터 로드 기능
    //시스템 불변성 및 데이터 오염 방지를 위해 원본 데이터를 읽어 리스트 형태로 반환하거나 활용하도록 설계
    //파일 내용을 문자열로 반환
    public String loadData() throws IOException {
        String fileName = "library_data.txt";
        StringBuilder sb = new StringBuilder();

        // FileReader를 BufferedReader로 감싸서 입출력 최적화 및 라인 단위 읽기 구현
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            
            // 파일의 끝에 도달할 때까지 한 줄씩(Line 단위) 읽어오기
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    sb.append(line).append("\n");
                }
            }
        }
        return sb.toString(); // 읽어온 원본 데이터를 문자열로 반환하여 View/Controller가 출력 방식을 결정하도록 함
    }
}