package src.model;

import java.util.ArrayList;
import java.util.List;

public class Section {
    private int number;
    private String text;
    private List<Choice> choices;

    public Section(int number, String text) {
        this.number = number;
        this.text = text;
        this.choices = new ArrayList<>();
    }

    public void addChoice(Choice choice) {
        choices.add(choice);
    }

    public int getNumber() {
        return number;
    }

    public String getText() {
        return text;
    }

    public List<Choice> getChoices() {
        return choices;
    }
} 