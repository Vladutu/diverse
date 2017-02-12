package ro.ucv.ace.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ro.ucv.ace.entity.Replay;
import ro.ucv.ace.entity.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geo on 12.02.2017.
 */
@Component
public class ReviewParserImpl implements ReviewParser {

    private class ReplayDto {
        String childId;
        String name;
        String body;

        public ReplayDto(String childId, String name, String body) {
            this.childId = childId;
            this.name = name;
            this.body = body;
        }
    }

    @Override
    public List<Review> parse(String source) {
        List<Review> reviews = new ArrayList<>();
        Document document = Jsoup.parse(source);
        Elements reviewElems = document.select("div.a-section.review");

        for (Element reviewElem : reviewElems) {
            Review review = parse(reviewElem);
            List<Replay> goodReplays = splitReplays(document, review.getReplays());
            review.setReplays(goodReplays);
            reviews.add(review);
        }

        return reviews;
    }

    private List<Replay> splitReplays(Document document, List<Replay> replays) {
        Elements hiddenReplays = document.select("div.a-popover-content");
        List<Replay> goodReplays = new ArrayList<>();
        List<ReplayDto> replayDtos = getReplayDtos(hiddenReplays);

        List<Replay> toBeRemoved = new ArrayList<>();
        for (ReplayDto replayDto : replayDtos) {
            Replay parent = replays.stream().filter(r -> r.hasAuthorAndBody(replayDto.name, replayDto.body)).findAny().get();
            Replay child = getChildReplay(document, replayDto.childId, replays);
            parent.addReplay(child);
            toBeRemoved.add(child);
        }
        for (Replay replay : replays) {
            if (!toBeRemoved.contains(replay)) {
                goodReplays.add(replay);
            }
        }

        return goodReplays;
    }

    private Replay getChildReplay(Document document, String childId, List<Replay> replays) {
        Element select = document.select("div#" + childId).first();
        String author = getReplayAuthor(select);
        String body = getReplayBody(select);

        return replays.stream().filter(r -> r.hasAuthorAndBody(author, body)).findAny().get();
    }

    private List<ReplayDto> getReplayDtos(Elements hiddenReplays) {
        List<ReplayDto> replayDtos = new ArrayList<>();
        for (Element hiddenReplay : hiddenReplays) {
            ReplayDto replayDto = null;
            try {
                String name = hiddenReplay.select("span.review-parent-comment-author").first().text();
                String body = hiddenReplay.select("span.review-parent-comment-text").first().text();

                Element divId = hiddenReplay.select("div.a-section").first();
                String idWIthGarbage = divId.attr("id");
                String childId = idWIthGarbage.split("-")[1];
                replayDto = new ReplayDto(childId, name, body);
                replayDtos.add(replayDto);
            } catch (Exception e) {
                //error on parsing -> skip
                //TODO log this with logger
                e.printStackTrace();
            }
        }
        return replayDtos;
    }

    private Review parse(Element reviewElem) {
        Double productRating = getProductRating(reviewElem);
        String title = getReviewTitle(reviewElem);
        String body = getReviewBody(reviewElem);
        String author = getReviewAuthor(reviewElem);
        String authorUrl = getReviewAuthorUrl(reviewElem);
        String date = getReviewDate(reviewElem);
        boolean verifiedPurchase = isVerifiedPurchase(reviewElem);
        Integer helpfulVotes = getHelpfulVotes(reviewElem);
        List<Replay> replays = getReplays(reviewElem);

        return new Review(productRating, title, body, author, authorUrl, date, verifiedPurchase, helpfulVotes, replays);
    }

    private List<Replay> getReplays(Element reviewElem) {
        List<Replay> replays = new ArrayList<>();
        Elements replayDivs = reviewElem.select("div.a-section.a-spacing-small.a-spacing-top-small.review-comment");

        for (Element replayDiv : replayDivs) {
            Replay replay = getReplay(replayDiv);
            replays.add(replay);
        }

        return replays;
    }

    private Replay getReplay(Element replayDiv) {
        String author = getReplayAuthor(replayDiv);
        String authorUrl = getReplayAuthorUrl(replayDiv);
        String body = getReplayBody(replayDiv);
        String date = getReplayDate(replayDiv);

        return new Replay(author, authorUrl, body, date);
    }

    private String getReplayDate(Element replayDiv) {
        Element select = replayDiv.select("span.comment-time-stamp").first();
        if (select != null) {
            return select.text();
        }

        return null;
    }

    private String getReplayBody(Element replayDiv) {
        Element select = replayDiv.select("span.review-comment-text").first();
        if (select != null) {
            return select.text();
        }

        return null;
    }

    private String getReplayAuthorUrl(Element replayDiv) {
        Element select = replayDiv.select("a.author").first();
        if (select != null) {
            return "https://www.amazon.com" + select.attr("href");
        }

        return null;
    }

    private String getReplayAuthor(Element replayDiv) {
        Element select = replayDiv.select("a.author").first();
        if (select != null) {
            return select.text();
        }

        return null;
    }

    private Integer getHelpfulVotes(Element reviewElem) {
        Element select = reviewElem.select("span.review-votes").first();
        if (select != null) {
            String votes = select.text().split(" ")[0];
            if (votes.equals("One")) {
                return 1;
            }
            return Integer.valueOf(votes);
        }

        return null;
    }

    private boolean isVerifiedPurchase(Element reviewElem) {
        Elements selects = reviewElem.select("span.a-size-mini.a-color-state.a-text-bold");

        for (Element select : selects) {
            String avp = select.attr("data-hook");
            if (avp != null && avp.equals("avp-badge")) {
                return true;
            }
        }

        return false;
    }

    private String getReviewDate(Element reviewElem) {
        Element select = reviewElem.select("span.review-date").first();
        if (select != null) {
            String dateWithOn = select.text();
            return dateWithOn.substring(3);
        }

        return null;
    }

    private String getReviewAuthorUrl(Element reviewElem) {
        Element select = reviewElem.select("a.author").first();
        if (select != null) {
            return "https://www.amazon.com" + select.attr("href");
        }

        return null;
    }

    private String getReviewAuthor(Element reviewElem) {
        Element select = reviewElem.select("a.author").first();
        if (select != null) {
            return select.text();
        }

        return null;
    }

    private String getReviewBody(Element reviewElem) {
        Element select = reviewElem.select("span.review-text").first();
        if (select != null) {
            return select.text();
        }

        return null;
    }


    private String getReviewTitle(Element reviewElem) {
        Element select = reviewElem.select("a.review-title").first();
        if (select != null) {
            return select.text();
        }

        return null;
    }

    private Double getProductRating(Element reviewElem) {
        Element select = reviewElem.select("i.a-icon.a-icon-star.review-rating").first();
        if (select != null) {
            String str = select.text();
            return Double.valueOf(str.split(" ")[0]);
        }

        return null;
    }
}
