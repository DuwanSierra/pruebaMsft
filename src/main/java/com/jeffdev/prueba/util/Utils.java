package com.jeffdev.prueba.util;

import com.jeffdev.prueba.objects.LoginObj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Utils {


    public LoginObj readFile(String path, String username) {

        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            archivo = new File(path);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            LoginObj loginObj = null;
            while ((linea = br.readLine()) != null && loginObj==null){
                if(linea.equals(username)){
                    loginObj = new LoginObj();
                    loginObj.setUsername(username);
                    loginObj.setPassword(br.readLine());
                }
            }
            return loginObj;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }
}
