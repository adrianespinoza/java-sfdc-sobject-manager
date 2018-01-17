package aveh.main;

import aveh.sobjects.SObject;
import aveh.sobjects.SObjectHandler;

public class Main {
    public static void main(String[] args) {
        SObject obj = SObjectHandler.getSObject("JobHistory__c");
        SObjectHandler.showSObejcts();
    }
}
