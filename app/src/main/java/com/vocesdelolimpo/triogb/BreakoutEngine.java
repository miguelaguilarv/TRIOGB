package com.vocesdelolimpo.triogb;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class BreakoutEngine extends SurfaceView implements Runnable{

    private Thread gameThread = null;

    private SurfaceHolder ourHolder;

    //Un boolean que se va a establece o quitar cuando el juego este corriendo o no.
    private volatile boolean playing;

    // Al iniciar el juego esta pausado
    private boolean paused = true;

    //Objetos Paint y Canvas
    private Canvas canvas;
    private Paint paint;

    //Que tal ancha y alta es la pantalla
    private int screenX;
    private int screenY;

    //Variable para rastrear el frame rate del juego
    private long fps;
    //Ayuda a calcular los fps
    private long timeThisFrame;

    //Plataforma del jugador
    Bat bat;

    //Una esfera... cuadrada
    Ball ball;

    //Hasta 200 ladrillos
    Brick[] bricks = new Brick[200];
    int numBricks = 0;

    //Sonidos
    SoundPool soundPool;
    int beep1ID;
    int beep2ID;
    int beep3ID;
    int loselifeID;
    int explodeID;

//    private SoundPool soundPool;
//    private int beep1ID,beep2ID, beep3ID, loselifeID, explodeID;


    //Puntaje
    public int score = 0;

    public int scoreF = 0;
    //Vidas
    int lives = 3;


    public BreakoutEngine(Context context, int x, int y) {
        super(context);

        //Se inicializan los objetos ourHolder y paint
        ourHolder = getHolder();
        paint = new Paint();

        //Inicializa screenX y screenY porque x e y son locales
        screenX = x;
        screenY = y;

        //Inicializa la plataforma del jugadro.
        bat = new Bat(screenX, screenY);

        //Crea la pelota
        ball = new Ball();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            AudioAttributes audioAttributes = new AudioAttributes.Builder()
//                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
//                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                    .build();
//            soundPool = new SoundPool.Builder()
//                    .setMaxStreams(6)
//                    .setAudioAttributes(audioAttributes)
//                    .build();
//        }else{
//            soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
//        }
//
//        beep1ID = soundPool.load(this, R.raw.explode, 1);

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);

        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            //Se guardan los sonidos en memoria para que esten listo para su uso
            descriptor = assetManager.openFd("beep1.ogg");
            beep1ID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("beep2.ogg");
            beep2ID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("beep3.ogg");
            beep3ID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("loselife.ogg");
            loselifeID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("raw/explode.mp3");
            explodeID = soundPool.load(descriptor, 0);


        }catch (IOException e){

            Log.e("Error", "Falla en cargar el sonido");
        }



        restart();

    }

    //Corre cuando el SO llama a onPause en el metodo BreaoutActivity
    public void pause(){
        playing = false;
        try {
            gameThread.join();
        }catch (InterruptedException e){

            Log.e("Error: ", "joining thread");

        }


    }
    //Corre cuando el SO llama a onResume en el metodo BreaoutActivity
    public void resume(){

        playing = true;
        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() {
        while (playing){

            //Captura el tiempo actual en milisegundos dentro de startFrameTIme
            long startFrameTime = System.currentTimeMillis();

            //Se actualiza el frame
            if (!paused){
                update();
            }

            //Dibuja el frame (cuadro)
            draw();

            //Al calcular los fps de este cuadro, se puede usar el resultado para animaciones de tiempo y mas.
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if(timeThisFrame >= 1 ){

                fps = 1000 / timeThisFrame;

            }
        }
    }

    private void update(){
        //Mueve la plataforma si es requerido
        bat.update(fps);

        //Actualiza la pelota
        ball.update(fps);


        //Colision con el ladrillo
        for(int i = 0; i < numBricks; i++){

            if (bricks[i].getVisibility()){

                if(RectF.intersects(bricks[i].getRect(),ball.getRect())) {
                    bricks[i].setInvisible();
                    ball.reverseYVelocity();
                    score = score + 10;
                    soundPool.play(explodeID, 1, 1, 0, 0, 1);
                }
            }
        }

        //Colision con la plataforma
        if (RectF.intersects(bat.getRect(),ball.getRect())){
            ball.setRandomXVelocity();
            ball.reverseYVelocity();
            ball.clearObstacleY(bat.getRect().top -2);
            soundPool.play(beep1ID,1,1,0,0,1);
        }

        if(ball.getRect().bottom > screenY){
            ball.reverseYVelocity();
            ball.clearObstacleY(screenY - 2);


            ball.reset(screenX, screenY);
            bat.reset(screenX);

            // Pierde una vida
            lives --;
            soundPool.play(loselifeID, 1, 1, 0, 0, 1);

            if(lives == 0){
                paused = true;

                scoreF = score;
                String s ="";
                s = (int)scoreF+"";
                Bundle bundle;
                bundle = new Bundle();
                bundle.putString("puntaje", s);
                Intent i = new Intent(getContext(), PuntajeBreakout.class);
                i.putExtras(bundle);
                getContext().startActivity(i);
            }

        }

        // La pelota rebota en el techo de la pantalla
        if(ball.getRect().top < 0){
            ball.reverseYVelocity();
            ball.clearObstacleY(12);
            soundPool.play(beep2ID, 1, 1, 0, 0, 1);
        }

        // rebota al chocar en la pared izq
        if(ball.getRect().left < 0){
            ball.reverseXVelocity();
            ball.clearObstacleX(2);
            soundPool.play(beep3ID, 1, 1, 0, 0, 1);
        }

        // rebota al chocar en la pared der
        if(ball.getRect().right > screenX - 10){
            ball.reverseXVelocity();
            ball.clearObstacleX(screenX - 22);
            soundPool.play(beep3ID, 1, 1, 0, 0, 1);
        }

        // Pausa el juego si se limpian todos los ladrillos
        if(score == numBricks * 10){

            if (lives == 3){
                paused = true;
                scoreF = score + 15;
                String s ="";
                s = (int)scoreF+"";
                Bundle bundle;
                bundle = new Bundle();
                bundle.putString("puntaje", s);
                Intent i = new Intent(getContext(), PuntajeBreakout.class);
                i.putExtras(bundle);
                getContext().startActivity(i);

                restart();
            }else if(lives == 2){

                scoreF = score + 5;
                String s ="";
                s = (int)scoreF+"";
                Bundle bundle;
                bundle = new Bundle();
                bundle.putString("puntaje", s);
                Intent i = new Intent(getContext(), PuntajeBreakout.class);
                i.putExtras(bundle);
                getContext().startActivity(i);

                restart();

            }

            paused = true;
            scoreF = score;
            String s ="";
            s = (int)scoreF+"";
            Bundle bundle;
            bundle = new Bundle();
            bundle.putString("puntaje", s);
            Intent i = new Intent(getContext(), PuntajeBreakout.class);
            i.putExtras(bundle);
            getContext().startActivity(i);

            restart();
        }


    }

    void restart(){
        //Pone la pelota de vuelta al inicio
        ball.reset(screenX, screenY);
        bat.reset(screenX);

        int brickWidth = screenX / 8;
        int brickHeight = screenY / 10;

        numBricks = 0;

        for (int column = 0; column < 8; column++){
            for (int row = 0; row < 3; row++){
                bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight);
                numBricks++;
            }
        }
        score = 0;
        lives = 3;

    }

    private void draw(){

        if (ourHolder.getSurface().isValid()){
            //Bloquea el canvas listo para dibujar
            canvas = ourHolder.lockCanvas();

            //Se pinta el color de fondo
            canvas.drawColor(Color.argb(255, 0, 0, 50));

            //Dibuja toodo en la pantalla
            //Dibuja la plataforma
            paint.setColor(Color.argb(255, 255, 255, 255));
            canvas.drawRect(bat.getRect(), paint);

            //Se elige el color para dibujar
            paint.setColor(Color.argb(255, 175, 238, 77));
            //Dibuja la pelota
            canvas.drawRect(ball.getRect(), paint);

            //Cambia el color del pincel para pintar los ladrillos
            paint.setColor(Color.argb(255, 238, 234, 77));

            //Dibuja los ladrillos si son visibles
            for (int i = 0; i < numBricks; i++){
                if (bricks[i].getVisibility()){
                    canvas.drawRect(bricks[i].getRect(), paint);
                }
            }

            paint.setColor(Color.argb(255,255,255,255));

            paint.setTextSize(50);
            canvas.drawText("Puntaje: " +score+ "       Vidas: "+lives, 10, 80, paint);

            //Muestra lo que se ha pintado
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    //La clase SurfaceView implementa onTouchListener, asi que podemos
    //sobreescribir este metodo y detectar los toques en la pantalla
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){

            //El jugador ha tocado la pantlla
            case MotionEvent.ACTION_DOWN:

                paused = false;

                if (motionEvent.getX() > screenX / 2){
                    bat.setMovementState(bat.RIGHT);
                }
                else{
                    bat.setMovementState(bat.LEFT);
                }
                break;

            //El jugador no esta tocando la pantalla
            case MotionEvent.ACTION_UP:
                bat.setMovementState(bat.STOPPED);
                break;

        }
        return true;
    }


}