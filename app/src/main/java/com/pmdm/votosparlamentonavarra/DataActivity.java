package com.pmdm.votosparlamentonavarra;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import static com.pmdm.votosparlamentonavarra.Ctes.COLORES;
import static com.pmdm.votosparlamentonavarra.Ctes.PARTIDOS_1979;
import static com.pmdm.votosparlamentonavarra.Ctes.PARTIDOS_2007;
import static com.pmdm.votosparlamentonavarra.Ctes.PARTIDOS_2011;
import static com.pmdm.votosparlamentonavarra.Ctes.PARTIDOS_2015;

public class DataActivity extends Activity {

    public Params pars;
    public Municipio m;
    public int year;
    public int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        pars = Params.getInstance();
        year = pars.year;
        code = pars.code;
        m  = pars.getMunicipio(year, code);

//        if (m!=null) Log.d("VPN",m.toString());
//        else  Log.d("VPN","null "+year+" "+code);

        LinearLayout layout = (LinearLayout)findViewById(R.id.LayoutData);
        Lienzo fondo = new Lienzo(this);
        layout.addView(fondo);
    }

    class Lienzo extends View {

        public Lienzo(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas){
            String[] partidos = new String[]{};
            if (year==1979) partidos = PARTIDOS_1979;
            if (year==2007) partidos = PARTIDOS_2007;
            if (year==2011) partidos = PARTIDOS_2011;
            if (year==2015) partidos = PARTIDOS_2015;
            int N = partidos.length;

            int width = canvas.getWidth();
            int height = canvas.getHeight();
            int w = width - 40;

            canvas.drawRGB(255,0,0);

            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            //Nombre municipio
            paint.setTextSize(40);
            paint.setTypeface(Typeface.MONOSPACE);
            canvas.drawText(m.nombreofi, 20, 50, paint);
            //Agno
            canvas.drawText(String.valueOf(year), 20, height - 20, paint);
            //Valores tipo de votos
            int v = m.getVotosOk(year);
            int b = m.getVotosBlanco(year);
            int n = m.getVotosNulo(year);
            int g = m.getAbstencion(year);
            int t = v+b+n+g;
            String str = "";
            str += v + ".v - " + b + ".b - " + n + ".n - " + g + ".g";
            paint.setTextSize(20);
            canvas.drawText(str, 30, 140, paint);
            //Partidos
            int c = w - 5*12;
            //Colores partidos
            for(int i=0;(i<5)&&(i<N);i++){
                paint.setColor(COLORES[i]);
                canvas.drawRect(10 + 20*(i) + (c*i)/5, 160,
                        10 + 20*(i+1) + (c*(i+1))/5 - 12 , 202, paint);
            }
            if (N>5){
                for(int i=5;(i<10)&&(i<N);i++){
                    paint.setColor(COLORES[i]);
                    canvas.drawRect(10 + 20*(i-5) + (c*(i-5))/5, 210,
                            10 + 20*(i+1-5) + (c*(i+1-5))/5 - 12 , 252, paint);
                }
            }
            if (N>10){
                for(int i=10;(i<15)&&(i<N);i++){
                    paint.setColor(COLORES[i]);
                    canvas.drawRect(10 + 20*(i-10) + (c*(i-10))/5, 260,
                            10 + 20*(i+1-10) + (c*(i+1-10))/5 - 12 , 302, paint);
                }
            }
            //Nombres partidos
            paint.setColor(Color.BLACK);
            for(int i=0;(i<5)&&(i<N);i++){
                str = partidos[i].length()>7?partidos[i].substring(0,7):partidos[i];
                canvas.drawText(str, 10 + 20*(i) + (c*i)/5, 180, paint);
                str = "" + m.get(partidos[i]);
                canvas.drawText(str, 10 + 20*(i) + (c*i)/5, 200, paint);
            }
            if (N>5){
                for(int i=5;(i<10)&&(i<N);i++){
                    str = partidos[i].length()>7?partidos[i].substring(0,7):partidos[i];
                    canvas.drawText(str, 10 + 20*(i-5) + (c*(i-5))/5, 230, paint);
                    str = "" + m.get(partidos[i]);
                    canvas.drawText(str, 10 + 20*(i-5) + (c*(i-5))/5, 250, paint);
                }
            }
            if (N>10){
                for(int i=10;(i<15)&&(i<N);i++){
                    str = partidos[i].length()>7?partidos[i].substring(0,7):partidos[i];
                    canvas.drawText(str, 10 + 20*(i-10) + (c*(i-10))/5, 280, paint);
                    str = "" + m.get(partidos[i]);
                    canvas.drawText(str, 10 + 20*(i-10) + (c*(i-10))/5, 300, paint);
                }
            }
            //Tipo de votos
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.GREEN);
            canvas.drawRect(20, 80, 20 + v*w/t , 120, paint);
            paint.setColor(Color.WHITE);
            canvas.drawRect(20 + v*w/t, 80, 20 + v*w/t + b*w/t , 120, paint);
            paint.setColor(Color.BLACK);
            canvas.drawRect(20 + v*w/t + b*w/t, 80, 20 + v*w/t + b*w/t + n*w/t , 120, paint);
            paint.setColor(Color.GRAY);
            canvas.drawRect(20 + v*w/t + b*w/t + n*w/t , 80, 20 + v*w/t + b*w/t + n*w/t + g*w/t , 120, paint);
            //Circulo, queso, quesitos
            int h = height - 302 - 40;
            paint.setAntiAlias(true);
            RectF rectF = null;
            int a = 0;
            if (h>w){
                a = (width - w)/2;
                rectF = new RectF(a, height - w - (h-w)/2 - 20, a + w,  height - (h-w)/2 - 20 );
            }else{
                a = 20 + (w - h)/2;
                rectF = new RectF(a, height - h - 20, a + h,  height - 20 );
            }
            //Log.d("TEST","width="+width+" height="+height+" w="+w+" h="+h+" a="+a);
            canvas.drawOval(rectF, paint);
            //Quesitos
            float gi = -90;
            float gx = 0;
            paint.setStyle(Paint.Style.FILL);
            for(int i=0;i<partidos.length;i++){
                paint.setColor(COLORES[i]);
                gx = (float)(m.get(partidos[i]) * 360) / (float)v;
                canvas.drawArc(rectF, gi, gx, true, paint);
                gi += gx;
            }
            //Circulo
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            canvas.drawOval(rectF, paint);
            //Rectangulo
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(20, 80, width - 20, 120, paint);
        }
    }
}
