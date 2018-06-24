package robbyturnip333.gmail.com.akses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
EditText nama,telepone;
TextView data;
Button in;
public ArrayList<Integer> listNama = new ArrayList<Integer>();
public ArrayList<Integer> listTelepone = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nama=findViewById(R.id.editNama);
        telepone=findViewById(R.id.editTelepon);
        data=findViewById(R.id.data);
        in=findViewById(R.id.tmbh);
    in.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            byte[] nNama=new byte[nama.getText().toString().length()];
            byte[] nTelepon=new byte[telepone.getText().toString().length()];
           listNama.add(nama.getText().toString().length());
           listTelepone.add(telepone.getText().toString().length());
            salinData(nNama, nama.getText().toString());
            salinData(nTelepon, telepone.getText().toString());
            try{
                FileOutputStream fileOutputStream=openFileOutput("telepon.dat",MODE_APPEND);
                DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);

                dataOutputStream.write(nNama);
                dataOutputStream.write(nTelepon);

                fileOutputStream.close();
                Toast.makeText(getBaseContext()," Data Berhasil Disimpan ",Toast.LENGTH_LONG).show();
            }
            catch (IOException e){
                Toast.makeText(getBaseContext()," Kesalahan : "+e.getMessage(),Toast.LENGTH_LONG).show();
            }
            tampilkanData();
            nama.setText(null);
            telepone.setText(null);
        }
    });
        tampilkanData();
    }

    public void salinData(byte[] caw, String data){
        for(int i=0;i<data.length();i++){
            caw[i]=(byte)data.charAt(i);
        }
    }



    public JSONObject makeJSON(String name[], String phone[], int numberof_students) throws JSONException {
        JSONObject obj = null;
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < numberof_students; i++) {
            obj = new JSONObject();
            try {
                obj.put("Nama", name[i]);
                obj.put("Telephone", phone[i]);

            } catch (JSONException e) {

                e.printStackTrace();
            }
            jsonArray.put(obj);
        }

        JSONObject finalobject = new JSONObject();
        finalobject.put("Kontak", jsonArray);
        return finalobject;
    }



    public  void tampilkanData(){
        try{
            FileInputStream fileInputStream=openFileInput("telepon.dat");
            DataInputStream input = new DataInputStream(fileInputStream);
            int a=0;
            String[] finalNama=new String[listNama.size()];
            String[] finalTelepone=new String[listTelepone.size()];
            String info="INFO DATA TELEPONE \n";
            while (input.available()>0){
                byte[] mNama=new byte[listNama.get(a)];
                byte[] mTelepon=new byte[listTelepone.get(a)];
                input.read(mNama);
                input.read(mTelepon);

                String dataNama="";
                for(int i=0;i<mNama.length;i++){
                    dataNama=dataNama+(char)mNama[i];
                }
                finalNama[a]=dataNama;
                String dataTelepone="";
                for(int i=0;i<mTelepon.length;i++){
                    dataTelepone=dataTelepone+(char)mTelepon[i];
                }
                finalTelepone[a]=dataTelepone;
                a++;
            }
            info=info+makeJSON(finalNama,finalTelepone,a);
            data.setText(info);
            fileInputStream.close();
        }catch(IOException e){
            Toast.makeText(getBaseContext()," Kesalahan : "+e.getMessage(),Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
