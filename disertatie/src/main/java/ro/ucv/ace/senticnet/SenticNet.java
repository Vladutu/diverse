package ro.ucv.ace.senticnet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SenticNet {
    @Id
    private String concept;
    private double pleasantness;
    private double attention;
    private double sensitivity;
    private double aptitude;
    private String primaryMood;
    private String secondaryMood;
    private String polarityLabel;
    private double polarityValue;
    @ElementCollection
    private List<String> semantics;
}
