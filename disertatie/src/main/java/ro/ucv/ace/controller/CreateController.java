package ro.ucv.ace.controller;

import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.ucv.ace.senticnet.SenticNet;
import ro.ucv.ace.senticnet.SenticNetRepository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/senticnet")
public class CreateController {

    private static final Pattern PATTERN = Pattern.compile("senticnet\\['(.+)'] = \\['(.+)', '(.+)', '(.+)', '(.+)', '(.+)', '(.+)', '(.+)', '(.+)', '(.+)', '(.+)', '(.+)', '(.+)', '(.+)']");

    @Autowired
    private SenticNetRepository senticNetRepository;

    @GetMapping(value = "/create")
    public ResponseEntity<String> create() throws IOException, URISyntaxException {

        Path path = Paths.get(this.getClass().getClassLoader()
                .getResource("senticnet5.py").toURI());

        Stream<String> lines = Files.lines(path);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        List<SenticNet> senticNets = lines.map(line -> toSenticNet(line, atomicInteger))
                .collect(Collectors.toList());
        System.out.println(senticNets.size());

        List<List<SenticNet>> chuncks = ListUtils.partition(senticNets, 1000);
        chuncks.forEach(chunck -> senticNetRepository.saveAll(chunck));

        return ResponseEntity.ok("OK");
    }

    private SenticNet toSenticNet(String line, AtomicInteger atomicInteger) {
        System.out.println(String.format("[%s] Processing line: %s", atomicInteger.getAndIncrement(), line));
        Matcher matcher = PATTERN.matcher(line);
        if (!matcher.matches()) {
            throw new RuntimeException("Line does not match: " + line);
        }
        SenticNet senticNet = new SenticNet();

        List<String> semantics = new ArrayList<>();
        semantics.add(matcher.group(10));
        semantics.add(matcher.group(11));
        semantics.add(matcher.group(12));
        semantics.add(matcher.group(13));
        semantics.add(matcher.group(14));

        senticNet.setPleasantness(Double.parseDouble(matcher.group(2)));
        senticNet.setAttention(Double.parseDouble(matcher.group(3)));
        senticNet.setSensitivity(Double.parseDouble(matcher.group(4)));
        senticNet.setAptitude(Double.parseDouble(matcher.group(5)));
        senticNet.setPrimaryMood(matcher.group(6));
        senticNet.setSecondaryMood(matcher.group(7));
        senticNet.setPolarityLabel(matcher.group(8));
        senticNet.setPolarityValue(Double.parseDouble(matcher.group(9)));
        senticNet.setSemantics(semantics);
        senticNet.setConcept(matcher.group(1));

        return senticNet;
    }
}
