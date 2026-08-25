import java.time.LocalDateTime;

interface PaymentProcessor{
        PaymentResult process(double amount);
}

class PaymentResult{
    private String transactionId;
    private double amount;
    private String status;
    private LocalDateTime timestamp;
    PaymentResult(String transactionId, double amount, String status, LocalDateTime timestamp){
            this.transactionId=transactionId;
            this.amount=amount;
            this.status=status;
            this.timestamp=timestamp;

    }
}
class OnlinePaymentProcessor
        implements PaymentProcessor {

    public PaymentResult process(double amount) {
        
    }
}
class PaymentService {

    private PaymentProcessor processor;

}

public class Main {
    
}
