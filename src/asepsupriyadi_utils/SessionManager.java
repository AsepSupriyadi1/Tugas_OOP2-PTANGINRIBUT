/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asepsupriyadi_utils;

import asepsupriyadi_database.koneksi_asepsupriyadi;
import asepsupriyadi_frame.fmenu_asepsupriyadi;
import asepsupriyadi_frame.form_login_asepsupriyadi;
import asepsupriyadi_model.LoginResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author TUF
 */
public class SessionManager {

    private static boolean isLoggedIn = false;

    public static boolean isLoggedIn() {
        return isLoggedIn;
    }

    public static LoginResponse login(String reqUsername, String reqPassword) {
        try {
            Connection conn = koneksi_asepsupriyadi.getKoneksi();
            Statement stm = conn.createStatement();
            ResultSet sql = stm.executeQuery("SELECT * FROM tb_akun WHERE username='" + reqUsername + "'");

            if (sql.next()) {
                String actualPassword = AES.decrypt(sql.getString("password"));
                System.out.println("ACTUAL " + actualPassword);

                if (reqPassword.equals(actualPassword)) {
                    isLoggedIn = true;
                    return new LoginResponse(isLoggedIn);
                } else {
                    return new LoginResponse("username and password salah !", false);
                }
            } else {
                return new LoginResponse("username dan password tidak tersedia", false);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new LoginResponse("terjadi kesalahan", false);
        }
    }

    public static void logout() {
        isLoggedIn = false;
    }
}
