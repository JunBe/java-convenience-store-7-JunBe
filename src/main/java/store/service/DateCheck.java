package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import store.util.MarkdownLoader;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DateCheck {
    public boolean checkNowCanGet(String promotion) {
        List<List<String>> lists = promotionInfo();
        List<String> promotionInfo = new ArrayList<>();
        for (List<String> list : lists) {
            if (list.get(0).equals(promotion)) {
                promotionInfo = list;
                break;
            }
        }

        /** name,buy,get,start_date,end_date
         * [[탄산2+1,2,1,2024-01-01,2024-12-31],
         * [MD추천상품,1,1,2024-01-01,2024-12-31],
         * [반짝할인,1,1,2024-11-01,2024-11-30]]
         */
        if (!promotionInfo.isEmpty()) {
            LocalDateTime now = DateTimes.now();
            String[] start = promotionInfo.get(3).split("-");
            String[] end = promotionInfo.get(4).split("-");

            LocalDateTime startDate = LocalDateTime.of(Integer.parseInt(start[0]),Integer.parseInt(start[1]),Integer.parseInt(start[2]),0,0,0);
            LocalDateTime endDate = LocalDateTime.of(Integer.parseInt(end[0]), Integer.parseInt(end[1]), Integer.parseInt(end[2]), 23, 59, 59);
            if (now.isAfter(startDate) && now.isBefore(endDate)) {
                return true;
            }
        }

        return false;
    }

    public List<List<String>> promotionInfo() {
        String markdownContent = MarkdownLoader.loadMarkdown("promotions.md");
        List<List<String>> promotionInfo = Arrays.stream(markdownContent.split("\n"))
                .map(line -> Arrays.asList(line.split(",")))
                .collect(Collectors.toList());
        promotionInfo.remove(0);
        return promotionInfo;
    }
}
