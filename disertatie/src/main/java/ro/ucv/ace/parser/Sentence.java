package ro.ucv.ace.parser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sentence {

    private List<Word> words = new ArrayList<>();

    private List<Dependency> dependencies = new ArrayList<>();

    private ParseNode parseNode;
}
