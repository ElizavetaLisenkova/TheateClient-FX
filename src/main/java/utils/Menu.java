package main.java.utils;

public enum Menu {
    Home("Главная страница"),
    Performances("Представления"),
    Tickets("Билеты"),
    Actors("Актеры"),
    Troups("Труппы"),
    Halls("Залы"),
    Author("Об авторе");

    private String title;

    Menu(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getFxml() {
        return String.format("%s.fxml", name());
    }
}
