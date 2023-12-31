package org.example.service;

import org.example.repository.QuoteRepository;

import java.util.Scanner;

public class QuoteService {
    QuoteRepository quoteRepository = new QuoteRepository();

    public void execute() {
        System.out.println("== 명언 앱 ==");
        while (true) {
            System.out.print("명령) ");
            Scanner sc = new Scanner(System.in);
            String cmd = sc.nextLine();
            if (cmd.contains("삭제")) {

            }
            if (cmd.equals("종료")) {
                break;
            } else if (cmd.equals("등록")) {
                quoteRepository.save();
            } else if (cmd.equals("목록")) {
                quoteRepository.findAll();
            } else if (cmd.equals("빌드")) {
                quoteRepository.build();
            } else if (cmd.startsWith("삭제?id=")) {
                quoteRepository.checkDeleteAndDelete(cmd);
            } else if (cmd.startsWith("수정?id=")) {
                quoteRepository.checkEditAndEdit(cmd);
            }
        }
    }
}
