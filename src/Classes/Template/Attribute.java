package Classes.Template;

import java.util.List;

public class Attribute {
    public Long id; //nullable
    public String description;
    public String displayText;
    public String hintText;
    public String valueType;
    public boolean isRequired;
    public long uiInputId;
    public Integer valueLimit; //nullable
    public List<Recommendation> recommendations;
}
