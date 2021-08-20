package br.com.gonzales.misionhispana;

import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Base64InputStream;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStorageDirectory;

public class MainActivity extends AppCompatActivity {

    JustificationAttachment attachment;
    File attachFile;
    Button saveBtn, sendBtn;
    EditText typeEditTxt, bodyEditTxt;
    Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        saveBtn = findViewById(R.id.save_btn);
        sendBtn = findViewById(R.id.send_btn);
        typeEditTxt = findViewById(R.id.type_edit_txt);
        bodyEditTxt = findViewById(R.id.body_edit_txt);

        gson = new Gson();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attachment = new JustificationAttachment(typeEditTxt.getText().toString(), bodyEditTxt.getText().toString());
                Log.d("TESTE", "Salvo: " + attachment);

                String attachmentStringJson = gson.toJson(attachment);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd");
                Date now = new Date();
                String fileName = formatter.format(now) + ".txt"; //like 2016_01_12.txt

                try{
                    File root = new File(getExternalFilesDir(null).getAbsolutePath() + "/Attachments");
//            Log.d("TESTE", root.getAbsolutePath());
                    if (!root.exists()){
                        root.mkdirs();
                    }
                    attachFile = new File(root, fileName);

                    FileWriter writer = new FileWriter(attachFile,true);
                    writer.append(attachmentStringJson+"\n");
                    writer.flush();
                    writer.close();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(attachFile));
                    String line;

                    while ((line = br.readLine()) != null) {
                        JustificationAttachment attach = gson.fromJson(line, JustificationAttachment.class);
                        //Enviar "attach"" para o servidor
                        Log.d("TESTE", "Enviado: " + attach);

//                        Log.d("TESTE", "line ->" + line);
                    }
                    br.close();
                }
                catch (IOException e) {
                    //You'll need to add proper error handling here
                }

                deleteRecursive(attachFile);

//        Toast.makeText(this, text.toString(), Toast.LENGTH_LONG).show();
            }
        });




    }

    void deleteRecursive(File fileOrDirectory) {

        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        fileOrDirectory.delete();

    }
}
