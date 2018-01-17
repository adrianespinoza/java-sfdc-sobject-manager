package aveh.sobjects;

import java.util.List;
import java.util.Map;

public class Fields {
    /**
    fullName;
    externalId;
    label;
    type;
    picklist;
    defaultValue;
    length;
    required;
    unique;
    referenceTo;
    relationshipLabel;
    relationshipName;
    */
    public Map<String,Object> fieldDescription;

    public void showCharacateristic() {
        System.out.println("***************************************************");
        for (String desc : fieldDescription.keySet()) {
            System.out.println("-- " + desc + " --");
            if (desc.equals("picklist")) {
                @SuppressWarnings("unchecked")
                List<PicklistValues> plvalues = (List<PicklistValues>) fieldDescription.get(desc);
                for (PicklistValues plv : plvalues) {
                    System.out.println("---> fullName: " + plv.fullName + " default: " + plv.isdefault);
                }
            } else {
                System.out.println("---> " + fieldDescription.get(desc));
            }
        }
        System.out.println("***************************************************");
    }
    public class PicklistValues {
        public String fullName;
        public String isdefault;
    }

}
