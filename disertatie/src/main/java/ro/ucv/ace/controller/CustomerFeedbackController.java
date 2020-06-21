package ro.ucv.ace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;
import ro.ucv.ace.brandmanagement.CustomerFeedbackResponse;
import ro.ucv.ace.brandmanagement.TwitterCustomerFeedback;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class CustomerFeedbackController {

    @Autowired
    private TwitterCustomerFeedback twitterCustomerFeedback;

    @GetMapping("/customerFeedback")
    public String greeting(@RequestParam(name = "accountName", required = false) String accountName,
                           @RequestParam(name = "tweetId", required = false) String tweetId,
                           Model model) {
        if (StringUtils.isEmpty(accountName) || StringUtils.isEmpty(tweetId)) {
            model.addAttribute("start", true);
        } else {
            model.addAttribute("start", false);
            List<CustomerFeedbackResponse> customerFeedbackResponses = twitterCustomerFeedback.getTwitterResponsesAndComputePolarities(accountName, tweetId);
            Map<String, List<CustomerFeedbackResponse>> partitionedResponse = customerFeedbackResponses.stream()
                    .collect(Collectors.groupingBy(CustomerFeedbackResponse::getPolarity));

            List<CustomerFeedbackResponse> positiveResponses = partitionedResponse.get("POSITIVE");
            List<CustomerFeedbackResponse> negativeResponses = partitionedResponse.get("NEGATIVE");
            model.addAttribute("positiveResponses", positiveResponses);
            model.addAttribute("positiveResponsesSize", positiveResponses.size());
            model.addAttribute("negativeResponses", negativeResponses);
            model.addAttribute("negativeResponsesSize", negativeResponses.size());

        }

        return "customerFeedback";
    }
}
