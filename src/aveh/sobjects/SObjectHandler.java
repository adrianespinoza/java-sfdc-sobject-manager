package aveh.sobjects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aveh.utils.SObjectXmlParser;

public class SObjectHandler {
    private static Map<String, SObject> sobjectsMap;
    private static String path = "./sfsobjects/";

    public static SObject getSObject(String sObjectApiName) {
        SObject sObjResult;
        String fullPath = path + sObjectApiName + ".object";
        if (sobjectsMap != null) {
            if (sobjectsMap.containsKey(sObjectApiName)) {
                sObjResult = sobjectsMap.get(sObjectApiName);
            } else {
                sObjResult = SObjectXmlParser.readSObject(sObjectApiName, fullPath);
            }
        } else {
            sobjectsMap = new HashMap<String, SObject>();
            sObjResult = SObjectXmlParser.readSObject(sObjectApiName, fullPath);
        }
        sobjectsMap.put(sObjectApiName, sObjResult);
        return sObjResult;
    }

    public static void showSObejcts() {
        for (SObject obj : sobjectsMap.values()) {
            System.out.println(obj.fullName);
            showFields(obj.fields);
        }
    }

    private static void showFields(List<Fields> fields) {
        for (Fields f : fields) {
            f.showCharacateristic();
        }
    }
}
