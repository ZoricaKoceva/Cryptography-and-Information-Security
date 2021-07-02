package InformationSecurity;
import InformationSecurity.TSA;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Simulator {
    public static void main(String[] args) throws IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, BadPaddingException {
        try {
            Scanner sc = new Scanner(System.in);
            String fileName = sc.next();
            File file = new File(fileName);
            if(file.createNewFile()){
                //Креираме фајл и запишуваме во него
                FileWriter fileWriter = new FileWriter(fileName);
                String str="";
                while(!(str = sc.next()).equals("!")){
                    fileWriter.append(str).append("\n");
                }
                fileWriter.close();
            }
            StringBuilder sb = new StringBuilder();
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()){
                sb.append(myReader.nextLine());
            }
            myReader.close();
            //креираме инстанца од TimestampingAuthority(TSA)
            TSA tsa = new TSA(sb.toString());
            //повикување на digitalTSATimestamp методот од класата TSA
            //како на слика 1 соодетно го враќа текстот + дигиталниот timestamp и самиот timestamp
            System.out.println("Timestamp Authority: data, digital signed timestamp and timestamp date");
            System.out.println(tsa.digitalTSATimestamp());
            //валидација на TSA
            System.out.println("Is Timestamp valid?");
            System.out.println(tsa.validateTrustedTimestamp());

        } catch (NoSuchAlgorithmException | IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
