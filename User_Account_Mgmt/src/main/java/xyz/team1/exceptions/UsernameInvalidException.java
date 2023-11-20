package xyz.team1.exceptions;

public class UsernameInvalidException extends RuntimeException{
    public UsernameInvalidException(String message) {
        super(message);
    }
}
