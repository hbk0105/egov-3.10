import java.util.HashMap;
import java.util.Map;

public class TestClass {


        // 필수 파라미터 검증
        public static void validateRequired(String parameterName, String value) {
            if (value == null || value.trim().isEmpty()) {
                throw new IllegalArgumentException(parameterName + "는 필수입니다.");
            }
        }

        public static void validateRequired(String parameterName, Integer value) {
            if (value == null) {
                throw new IllegalArgumentException(parameterName + "는 필수입니다.");
            }
        }


    public static void processOrder(Map<String, Object> orderParams) {
        String customerName = (String) orderParams.get("customerName");
        Integer orderAmount = (Integer) orderParams.get("orderAmount");

        // 필수 파라미터 검증
        validateRequired("고객 이름", customerName);
        validateRequired("주문 금액", orderAmount);

        // 주문 처리 로직
        System.out.println("Processing order for " + customerName);
    }

    public static void main(String[] args) {

        Map<String, Object> orderParams = new HashMap<>();
        //orderParams.put("customerName","감자");
        orderParams.put("orderAmount",1000);
        processOrder(orderParams);
    }
}

