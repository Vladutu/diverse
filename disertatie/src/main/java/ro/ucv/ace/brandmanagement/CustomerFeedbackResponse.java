package ro.ucv.ace.brandmanagement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerFeedbackResponse {

    private String tweetResponse;
    private String polarity;
}
