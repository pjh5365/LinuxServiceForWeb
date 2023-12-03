package pjh5365.linuxserviceweb.domain.auth;

import java.util.Random;

public class GenerateCode {
    public static String generate() {
        Random random = new Random();

        StringBuilder result = new StringBuilder();
        for(int i = 0; i < 6; i++) {    // 총 6자리 난수 생성
            result.append(random.nextInt(9));  // 0 ~ 9 까지의 숫자 랜덤으로 뽑기
        }

        return result.toString();
    }
}
