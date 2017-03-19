package ro.ucv.ace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ucv.ace.entity.Replay;
import ro.ucv.ace.entity.Review;
import ro.ucv.ace.repository.ReviewRepository;

import java.util.List;

/**
 * Created by Geo on 18.03.2017.
 */
@Service
@Transactional
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public void updateReplaysReviewParent() {
        reviewRepository.getAll().forEach(review -> {
            System.out.println("Computing review with id " + review.getId());
            List<Replay> replays = review.getReplays();
            replays.forEach(r -> setReviewOnReplay(r, review));
        });
    }

    private void setReviewOnReplay(Replay replay, Review review) {
        if (replay.getReview() == null) {
            replay.setReview(review);
        }
        for (Replay r : replay.getReplays()) {
            setReviewOnReplay(r, review);
        }
    }
}
