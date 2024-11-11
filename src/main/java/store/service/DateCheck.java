package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import store.util.MarkdownLoader;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DateCheck {
    private static final int YEAR = 0;
    private static final int MONTH = 1;
    private static final int DAY = 2;
    private static final int START_DATE = 3;
    private static final int END_DATE = 4;

    public boolean checkNowCanGet(String promotion) {
        List<List<String>> lists = promotionInfo();
        List<String> promotionInfo = new ArrayList<>();
        for (List<String> list : lists) {
            if (list.get(0).equals(promotion)) {
                promotionInfo = list;
                break;
            }
        }
        
        if (isPromotionActive(promotionInfo)) return true;
        return false;
    }

    private static boolean isPromotionActive(List<String> promotionInfo) {
        if (!promotionInfo.isEmpty()) {
            LocalDateTime now = DateTimes.now();
            String[] start = promotionInfo.get(START_DATE).split("-");
            String[] end = promotionInfo.get(END_DATE).split("-");
            LocalDateTime startDate = LocalDateTime.of(Integer.parseInt(start[YEAR]),Integer.parseInt(start[MONTH]),Integer.parseInt(start[DAY]),0,0,0);
            LocalDateTime endDate = LocalDateTime.of(Integer.parseInt(end[YEAR]), Integer.parseInt(end[MONTH]), Integer.parseInt(end[DAY]), 23, 59, 59);
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
