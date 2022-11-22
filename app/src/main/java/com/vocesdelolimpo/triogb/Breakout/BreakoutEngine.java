package com.vocesdelolimpo.triogb.Breakout;


import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.vocesdelolimpo.triogb.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class BreakoutEngine extends SurfaceView implements Runnable{

    private Thread gameThread = null;
    private SurfaceHolder ourHolder;
    private boolean segundo = false;


    //Un boolean que se va a establece o quitar cuando el juego este corriendo o no.
    private volatile boolean playing;

    // Al iniciar el juego esta pausado
    private boolean paused = true;

    //Objetos Paint y Canvas
    Resources res = getResources();
    private Bitmap bitmapFire = BitmapFactory.decodeResource(res, R.drawable.fire);
    private Bitmap bitmapFire2 = BitmapFactory.decodeResource(res, R.drawable.fire2);
    private Bitmap bitmapFire3 = BitmapFactory.decodeResource(res, R.drawable.fire3);

    private Bitmap bitmapBall = BitmapFactory.decodeResource(res, R.drawable.dragonball);
    private Bitmap bitmapBat = BitmapFactory.decodeResource(res, R.drawable.bat);
    private Canvas canvas;
    private Paint paint;

    private Paint fire1;

    //Que tal ancha y alta es la pantalla
    private int screenX;
    private int screenY;


    private int screenZ;
    private Random random;

    //Variable para rastrear el frame rate del juego
    private long fps, fps2;
    //Ayuda a calcular los fps
    private long timeThisFrame;

    Fire fire, fire2, fire3, fire4;

    //Plataforma del jugador
    Bat bat;

    //Una esfera... cuadrada
    Ball ball;

    //Lista de Ladrillos

    ArrayList<Brick> bricks = new ArrayList<>();


    int numBricks = 0;

    //Sonidos
    SoundPool soundPool;
    int beep1ID;
    int beep2ID;
    int beep3ID;
    int loselifeID;
    int explodeID;


    //Puntaje
    public int score = 0;
    public int cantBricks = 0;

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
        ball = new Ball(screenX, screenY);

        randomFire();
        randomFire2();
        randomFire3();
        randomFire4();


        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);

        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            //Se guardan los sonidos en memoria para que esten listo para su uso
            descriptor = assetManager.openFd("beep1ID.mp3");
            beep1ID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("Beep2ID.mp3");
            beep2ID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("Beep3ID.mp3");
            beep3ID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("loselifeID.mp3");
            loselifeID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("explodeID.mp3");
            explodeID = soundPool.load(descriptor, 0);


        }catch (IOException e){

            Log.e("Error", "Falla en cargar el sonido");
        }
        restart();

    }

    private void randomFire(){
        random = new Random();
        screenZ = screenX - random.nextInt(1000);

        fire = new Fire(screenZ, screenY);
    }
    private void randomFire2(){
        random = new Random();
        screenZ = screenX - random.nextInt(800);
        fire2 = new Fire(screenZ, screenY/3);
    }
    private void randomFire3(){
        random = new Random();
        screenZ = screenX - random.nextInt(800);
        fire3 = new Fire(screenZ/3, screenY + 150);
    }
    private void randomFire4(){
        random = new Random();
        screenZ = screenX - random.nextInt(1000);
        fire4 = new Fire(screenZ/2 - 500, screenY - 300);
    }

    public void setLives(int lives){
        this.lives = lives;
    }

    public void setScore(int score){
        this.score = score;
    }


    public void setSegundo(boolean segundo) {
        this.segundo = segundo;
    }

    public void setCantBricks(int cantBricks){
        this.cantBricks = cantBricks;
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
                fps2 = 600 / timeThisFrame;
            }
        }
    }

    private void update(){
        //Mueve la plataforma si es requerido
        bat.update(fps);

        //Actualiza la pelota
        if (segundo == false){
            ball.update(fps);
        }else{
            ball.update(fps2);
            fire.update(fps);
            fire2.update(fps);
            fire3.update(fps);
            fire4.update(fps);
        }


        //Colision con el ladrillo
        for(int i = 0; i < numBricks; i++){

            if (bricks.get(i).getVisibility()){

                if(RectF.intersects(bricks.get(i).getRect(),ball.getRect())) {
                    bricks.get(i).setInvisible();
                    ball.reverseYVelocity();
                    score = score + 10;
                    cantBricks = cantBricks + 10;
                    soundPool.play(loselifeID, 1, 1, 0, 0, 1);
                }
            }
        }

        if(RectF.intersects(bat.getRect(),fire.getRect())){
            bat.setMovementState(bat.STOPPED);
        }
        if(RectF.intersects(bat.getRect(),fire2.getRect())){
            bat.setMovementState(bat.STOPPED);
        }
        if(RectF.intersects(bat.getRect(),fire3.getRect())){
            bat.setMovementState(bat.STOPPED);
        }
        if(RectF.intersects(bat.getRect(),fire4.getRect())){
            bat.setMovementState(bat.STOPPED);
        }

        //Colision con la plataforma
        if (RectF.intersects(bat.getRect(),ball.getRect())){

            float paddleMiddle = (bat.getRect().left + bat.getRect().right) / 2;
            float ballMiddle = (ball.getRect().left + ball.getRect().right) / 2;
            ball.setXVelocity(paddleMiddle, ballMiddle);
//            ball.setRandomXVelocity();
            ball.reverseYVelocity();
            ball.clearObstacleY(bat.getRect().top -2);
            soundPool.play(beep1ID,1,1,0,0,1);
        }
        if(fire.getRect().bottom > screenY){
            randomFire();
        }
        if(fire2.getRect().bottom > screenY){
            randomFire2();
        }
        if(fire3.getRect().bottom > screenY){
            randomFire3();
        }

        if(ball.getRect().bottom > screenY){

            ball.reset(screenX, screenY);
            bat.reset(screenX);
            ball.reverseYVelocity();
            ball.clearObstacleY(screenY - 150);

            // Pierde una vida
            lives --;
            soundPool.play(beep3ID, 1, 1, 0, 0, 1);

            if(lives == 0){
                paused = true;
                enviarAPuntajePierde();
                restart();
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
            soundPool.play(explodeID, 1, 1, 0, 0, 1);
        }

        if (bat.getRect().left <= 0){
            bat.setMovementState(bat.STOPPED);
        }

        if (bat.getRect().right > screenX - 10){
            bat.setMovementState(bat.STOPPED);
        }

        // rebota al chocar en la pared der
        if(ball.getRect().right > screenX - 10){
            ball.reverseXVelocity();
            ball.clearObstacleX(screenX - 22);
            soundPool.play(explodeID, 1, 1, 0, 0, 1);
        }

        // Pausa el juego si se limpian todos los ladrillos
        if(cantBricks == numBricks * 10){

            paused = true;
            enviarAPuntaje();
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
                bricks.add(numBricks,new Brick(row, column, brickWidth, brickHeight));
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
            canvas.drawColor(Color.argb(200, 0, 0, 50));

            //Bitmap resize3 = Bitmap.createScaledBitmap(bitmap,screenX ,screenY,true);
            //canvas.drawBitmap(resize3, 0,0, paint);

            //Dibuja toodo en la pantalla
            //Dibuja la plataforma
            //paint.setColor(Color.argb(255, 0, 0, 50));
            paint.setColor(Color.TRANSPARENT);
            Bitmap resize = Bitmap.createScaledBitmap(bitmapBat, (int)bat.getRect().width(), (int)bat.getRect().height(), true);
            canvas.drawRect(bat.getRect(), paint);
            canvas.drawBitmap(resize,bat.getRect().left,bat.getRect().top,null);

            //Se elige el color para dibujar
            paint.setColor(Color.argb(255, 0, 0, 50));
            //Dibuja la pelota
            Bitmap resize2 = Bitmap.createScaledBitmap(bitmapBall, (int)ball.getRect().width(), (int)ball.getRect().height(), true);
            canvas.drawRect(ball.getRect(), paint);
            canvas.drawBitmap(resize2,ball.getRect().left, ball.getRect().bottom, null);

            if (segundo == true){

                paint.setColor(Color.TRANSPARENT);
                Bitmap resize5 = Bitmap.createScaledBitmap(bitmapFire, (int)fire.getRect().width(), (int)fire.getRect().height(), true);
                Bitmap resize6 = Bitmap.createScaledBitmap(bitmapFire2, (int)fire2.getRect().width(), (int)fire2.getRect().height(), true);
                Bitmap resize7 = Bitmap.createScaledBitmap(bitmapFire3, (int)fire3.getRect().width(), (int)fire3.getRect().height(), true);

                canvas.drawRect(fire.getRect(), paint);
                canvas.drawBitmap(resize5,fire.getRect().left, fire.getRect().top, null);
                canvas.drawRect(fire2.getRect(), paint);
                canvas.drawBitmap(resize5,fire.getRect().left, fire.getRect().top, null);

                canvas.drawRect(fire3.getRect(), paint);
                canvas.drawBitmap(resize6,fire2.getRect().left, fire2.getRect().top, null);

                canvas.drawRect(fire4.getRect(), paint);
                canvas.drawBitmap(resize7,fire3.getRect().left, fire3.getRect().top, null);


            }

            //Cambia el color del pincel para pintar los ladrillos
            paint.setColor(Color.argb(255, 238, 234, 77));

            //Dibuja los ladrillos si son visibles
            for (int i = 0; i < numBricks; i++){
                if (bricks.get(i).getVisibility()){
                    canvas.drawRect(bricks.get(i).getRect(), paint);
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
    private void enviarAPuntajePierde(){

        int scoreP = score;
        int vidas = lives;

        Bundle bundle;
        bundle = new Bundle();
        bundle.putInt("puntaje", scoreP);
        bundle.putInt("vidas", vidas);


        Intent i = new Intent(getContext(), PuntajeBreakout.class);
        i.putExtra("puntaje", scoreP);
        i.putExtra("vidas", vidas);
        getContext().startActivity(i);
    }
    private void enviarAPuntaje(){

        int score1 = score;
        int vidas = lives;

        Bundle bundle;
        bundle = new Bundle();
        bundle.putInt("puntaje", score1);
        bundle.putInt("vidas", vidas);

        if(segundo == false){


            Intent i = new Intent(getContext(), Breakout2Activity.class);
            i.putExtra("puntaje", score1);
            i.putExtra("vidas", vidas);
            getContext().startActivity(i);

        }else{
            if (vidas == 3){
                score1 = score + 30;

            }else{
                score1 = score;
            }
            Intent i = new Intent(getContext(), PuntajeBreakout.class);
            i.putExtra("puntaje", score1);
            i.putExtra("vidas", vidas);
            getContext().startActivity(i);

        }


    }


}
