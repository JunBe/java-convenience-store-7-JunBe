package store.view.io;

import java.util.List;

public class Output {
    public void announceProduct(List<List<String>> stock) {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.");
        System.out.println();
        // 상품 목록 꺼내오기 (products.md)
        for (List<String> info : stock) {
            System.out.print("- ");
            for (int i = 0; i < info.size(); i++) {
                if (!info.get(i).equals("null")) {
                    System.out.print(info.get(i) + " ");
                }
            }
            System.out.println();
        }
        System.out.println();

    }

    public void receipt() {
        System.out.println("==============W 편의점================");
        System.out.println("상품명\t\t수량\t금액");
        System.out.println("콜라\t\t3 \t3,000");
        System.out.println("=============증\t정===============");
        System.out.println("콜라1");
        System.out.println("====================================");
    }
}
