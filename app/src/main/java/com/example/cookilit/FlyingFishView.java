package com.example.cookilit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FlyingFishView  extends View {

    private final Bitmap[] fish = new Bitmap[7];

    private final int fishX=10;
    private int fishY;
    private int fishspeed;

    private int canvasWidth,canvasHeight;

    private boolean touch=false;

    private Bitmap backgroundImage;
    private Paint scorePaint = new Paint();
    private  Bitmap life[]= new Bitmap[2];

    private int yellowX,yellowY,yellowSpeed=6;
    private int greenX,greenY,greenSpeed=10;
    private int redX,redY,redSpeed=10;
    private int score , lifeCounterOfFish;
    public FlyingFishView(Context context) {
        super(context);

        fish[0]= BitmapFactory.decodeResource(getResources(),R.drawable.v1);
        fish[1]= BitmapFactory.decodeResource(getResources(),R.drawable.v2);
        fish[2]= BitmapFactory.decodeResource(getResources(),R.drawable.g2);
        fish[3]= BitmapFactory.decodeResource(getResources(),R.drawable.g13);
        fish[4]= BitmapFactory.decodeResource(getResources(),R.drawable.g4);
        fish[5]= BitmapFactory.decodeResource(getResources(),R.drawable.g15);
        backgroundImage = BitmapFactory.decodeResource(getResources(),R.drawable.gbg5);



        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(58);
        scorePaint.setTypeface(Typeface.DEFAULT);
        scorePaint.setAntiAlias(true);
        life[0]=BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1]=BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);
        fishY = 550;
        score = 0;
        lifeCounterOfFish = 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight =canvas.getHeight();



        canvas.drawBitmap(backgroundImage,0,0,null);

        int minFishY=fish[0].getDensity();
        int maxFishY = canvasHeight - fish[0].getHeight()+15;

        fishY = fishY + fishspeed;
        if (fishY<minFishY){
            fishY=minFishY;
        }
        if (fishY > maxFishY){
            fishY = maxFishY;
        }

        fishspeed = fishspeed+2;

        if (touch){
            canvas.drawBitmap(fish[1],fishX,fishY,null);
            touch = false;

        }
        else {
            canvas.drawBitmap(fish[0],fishX,fishY,null);
        }


        yellowX = yellowX-yellowSpeed;
        if (hitBallChecker(yellowX,yellowY)){
            score = score + 10;
            yellowX = -100;
        }

        if (yellowX < 0){
            yellowX = canvasWidth+21;
            yellowY =(int) Math.floor(Math.random()*(maxFishY-minFishY))+minFishY;
        }
        canvas.drawBitmap(fish[2],yellowX,yellowY,null);


        greenX = greenX-greenSpeed;
        if (hitBallChecker(greenX,greenY)){
            score = score + 15;
            greenX = -100;
        }

        if (greenX< 0){
            greenX = canvasWidth+21;
            greenY =(int) Math.floor(Math.random()*(maxFishY-minFishY))+minFishY;
        }
        canvas.drawBitmap(fish[3],greenX,greenY,null);


        redX = redX-redSpeed;
        if (hitBallChecker(redX,redY)){
            redX = -100;
            lifeCounterOfFish--;
            if (lifeCounterOfFish==0){
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();

                Intent gameOverIntent = new Intent(getContext(),GameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOverIntent.putExtra("score",score);
                getContext().startActivity(gameOverIntent);
            }
        }

        if (redX< 0){
            redX = canvasWidth+21;
            redY =(int) Math.floor(Math.random()*(maxFishY-minFishY))+minFishY;
        }
        canvas.drawBitmap(fish[5],redX,redY,null);

        canvas.drawText("Score:"+score,20,70,scorePaint);

        for (int i=0;i<3;i++){
            int x=(int) (380+life[0].getWidth() * 1.5 * i);
            int y = 30;

            if (i< lifeCounterOfFish){
                canvas.drawBitmap(life[0],x,y,null);
            }else {
                canvas.drawBitmap(life[1],x,y,null);
            }

        }

    }

    public  boolean hitBallChecker(int x, int y){
       if (fishX < x && x < (fishX+fish[0].getWidth()) && fishY < y && y<(fishY+fish[0].getHeight())){
           return  true;
       }
          return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){

            touch = true;
            fishspeed = -22;
        }
        return true;
    }
}
