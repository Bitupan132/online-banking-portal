package xyz.team1.exceptions;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(String messsage){
        super(messsage);
    }
}
