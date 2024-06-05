package fr.supinfo.league.season;

public class SeasonNotEmptyException extends Exception {
    public SeasonNotEmptyException(String label) {
        super(label);
    }
}
