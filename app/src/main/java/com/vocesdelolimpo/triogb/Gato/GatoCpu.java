package com.vocesdelolimpo.triogb.Gato;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;


import com.vocesdelolimpo.triogb.R;

public class GatoCpu extends AppCompatActivity implements View.OnClickListener  {
    TextView tv1,tv2;
    Button[][] arreglo=new Button[3][3];
    int pPoints=0,cPoints=0,empate=0;
    boolean pTurn=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gato_cpu);
        tv1=findViewById(R.id.user1);
        tv2=findViewById(R.id.invitado);
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                String  btnID="button_"+i+j;
                int idFin=getResources().getIdentifier(btnID,"id",getPackageName());
                arreglo[i][j]=findViewById(idFin);
                arreglo[i][j].setOnClickListener(this);
                arreglo[i][j].setText("");
            }
        }
    }

    public void limpiar(){
        empate=0;
        pTurn=true;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                arreglo[i][j].setText("");
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(((Button)view).getText().toString().equals("")){
            ((Button)view).setText("O");
            empate++;
        }
        if(verificar()&&pTurn){
            pPoints++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tv1.setText("Player: "+pPoints);
                    limpiar();
                }
            },1000);
            tv1.setText("Player: Ganador");
        }else if(empate==9){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tv1.setText("Player: "+pPoints);
                    tv2.setText("CPU: "+cPoints);
                }
            },1000);
            tv1.setText("Player: Empate!");
            tv2.setText("CPU: Empate!");
            limpiar();
        }else{
            pTurn=!pTurn;
            cpu();
        }
    }

    public boolean verificar(){
        return     arreglo[0][0].getText() == arreglo[0][1].getText() && arreglo[0][0].getText() == arreglo[0][2].getText() && arreglo[0][0].getText() != ""
                || arreglo[1][0].getText() == arreglo[1][1].getText() && arreglo[1][0].getText() == arreglo[1][2].getText() && arreglo[1][0].getText() != ""
                || arreglo[2][0].getText() == arreglo[2][1].getText() && arreglo[2][0].getText() == arreglo[2][2].getText() && arreglo[2][0].getText() != ""
                || arreglo[0][0].getText() == arreglo[1][0].getText() && arreglo[0][0].getText() == arreglo[2][0].getText() && arreglo[0][0].getText() != ""
                || arreglo[0][1].getText() == arreglo[1][1].getText() && arreglo[0][1].getText() == arreglo[2][1].getText() && arreglo[0][1].getText() != ""
                || arreglo[0][2].getText() == arreglo[1][2].getText() && arreglo[0][2].getText() == arreglo[2][2].getText() && arreglo[0][2].getText() != ""
                || arreglo[0][0].getText() == arreglo[1][1].getText() && arreglo[0][0].getText() == arreglo[2][2].getText() && arreglo[0][0].getText() != ""
                || arreglo[0][2].getText() == arreglo[1][1].getText() && arreglo[0][2].getText() == arreglo[2][0].getText() && arreglo[0][2].getText() != "";
    }

    public void cpu(){
        Random random = new Random();
        int i,j;
        do {
            i = random.nextInt(3);
            j = random.nextInt(3);
        } while (arreglo[i][j].getText() != "");
        final int fI=i,fJ=j;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                arreglo[fI][fJ].setText("X");
                empate++;
                if(verificar()&&!pTurn){
                    cPoints++;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tv2.setText("CPU: "+cPoints);
                            limpiar();
                        }
                    },1000);
                    tv2.setText("CPU: Ganador");
                }else if (empate == 9) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tv1.setText("Player: "+pPoints);
                            tv2.setText("CPU: "+cPoints);
                        }
                    },1000);
                    tv1.setText("Player: Empate!");
                    tv2.setText("CPU: Empate!");
                    limpiar();
                }else pTurn=!pTurn;
            }
        }, 500);
    }
}