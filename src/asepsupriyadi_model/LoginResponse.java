/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asepsupriyadi_model;

/**
 *
 * @author TUF
 */
public class LoginResponse {

    private String message;
    private boolean status;

    public LoginResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    public LoginResponse(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}